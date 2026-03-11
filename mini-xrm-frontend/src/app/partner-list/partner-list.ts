import { Component, OnInit, AfterViewInit, ViewChild, signal, WritableSignal } from '@angular/core';
import { RouterLinkWithHref } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatChipsModule } from '@angular/material/chips';
import { MatTableDataSource } from '@angular/material/table';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatSelectModule } from '@angular/material/select';
import { finalize } from 'rxjs/operators';

import { PartnerService } from '../../../mini-xrm-client-angular/api/partner.service';
import { PartnerTagService } from '../../../mini-xrm-client-angular/api/partnerTag.service';
import { getLogger } from '../logging/logger';
import { PartnerStatusView } from '../../../mini-xrm-client-angular/model/partnerStatusView';
import { PartnerVView } from '../../../mini-xrm-client-angular/model/partnerVView';

@Component({
	selector: 'app-partner-list',
	imports: [CommonModule, MatTableModule, MatPaginatorModule, MatProgressSpinnerModule, MatChipsModule, MatFormFieldModule, MatSelectModule, MatSnackBarModule, RouterLinkWithHref],
	templateUrl: './partner-list.html',
	styleUrls: ['./partner-list.scss'],
})
export class PartnerList implements OnInit, AfterViewInit {

	private readonly logger = getLogger('component.PartnerList');

	protected displayedColumns = ['name', 'headquarters', 'status', 'tags', 'actions'];
	protected dataSource: WritableSignal<MatTableDataSource<PartnerVView>> = signal(new MatTableDataSource<PartnerVView>([]));
	protected loading: WritableSignal<boolean> = signal(false);
	protected totalElements: WritableSignal<number> = signal(0);
	protected pageSize: WritableSignal<number> = signal(10);
	protected pageIndex: WritableSignal<number> = signal(0);
	protected tags: WritableSignal<{ id: number; name: string }[]> = signal([]);
	protected selectedTagId: WritableSignal<number | null> = signal(null);
	protected sortField: WritableSignal<string | null> = signal(null);
	protected sortDirection: WritableSignal<'asc' | 'desc' | null> = signal(null);

	@ViewChild(MatPaginator) paginator!: MatPaginator;

	private readonly HOLD_MS = 1000;
	private holdIntervals = new Map<number, any>();
	protected progressMap: WritableSignal<{ [id: number]: number }> = signal({});

	constructor(private partnerService: PartnerService, private partnerTagService: PartnerTagService, private snackBar: MatSnackBar) { }

	ngOnInit(): void {
		this.loadTags();
		this.loadPage(0, this.pageSize());
	}

	ngAfterViewInit(): void {
	}

	protected loadPage(page: number, pageSize: number) {
		this.loading.set(true);
		this.pageIndex.set(page);
		const params: any = { page, pageSize };
		if (this.selectedTagId() != null) params.partnerTagId = this.selectedTagId();
		if (this.sortField()) params.sortField = this.sortField();
		if (this.sortDirection()) params.sortDirection = this.sortDirection();
		this.partnerService
		.searchPartners(params)
		.pipe(finalize(() => {
		this.loading.set(false);
		}))
		.subscribe({
		next: (res) => {
		this.dataSource.set(new MatTableDataSource<PartnerVView>(res.content ?? []));
		this.totalElements.set(res.totalElements ?? 0);
		this.pageSize.set(res.pageSize ?? pageSize);
		},
		error: (err) => {
		this.logger.error(() => 'Failed to load partners', err);
		}
		});
	}

	protected toggleSort(field: string) {
		if (this.sortField() !== field) {
		this.sortField.set(field);
		this.sortDirection.set('asc');
		} else {
		if (this.sortDirection() === 'asc') this.sortDirection.set('desc');
		else if (this.sortDirection() === 'desc') { this.sortField.set(null); this.sortDirection.set(null); }
		else this.sortDirection.set('asc');
		}
		this.loadPage(0, this.pageSize());
	}

	protected loadTags() {
		this.partnerTagService.listPartnerTags().subscribe({
		next: (res: any) => {
		this.tags.set(res.content.map((t: any) => ({ id: t.id, name: t.name })));
		},
		error: (err) => { this.logger.error(() => 'Failed to load partner tags', err); this.tags.set([]); }
		});
	}

	protected onTagChange(tagId: string) {
		const id = tagId === '' ? null : Number(tagId);
		this.selectedTagId.set(id);
		this.loadPage(0, this.pageSize());
	}

	protected onPage(event: PageEvent) {
		this.loadPage(event.pageIndex, event.pageSize);
	}

	protected delete(element: PartnerVView) {
		this.partnerService.deletePartner({ partnerId: element.id! }).subscribe({
		next: () => {
		const id = element.id;
		setTimeout(() => {
		// defer UI mutations to the next macrotask to avoid
		// ExpressionChangedAfterItHasBeenCheckedError
				this.loadPage(this.pageIndex(), this.pageSize());
				if (id != null) this.progressMap.update(m => { const copy = { ...m }; copy[id] = 0; return copy; });
		}, 0);
		this.snackBar.open('Partner deleted', 'OK', { duration: 3000 });
		},
		error: (err) => {
		this.logger.error(() => 'Failed to delete partner', err);
		this.snackBar.open('Failed to delete partner', 'OK', { duration: 3000 });
		}
		});
	}

	protected startHoldDelete(element: PartnerVView, event: Event) {
		try { event.preventDefault(); } catch { }
		const id = element.id!;
		if (this.holdIntervals.has(id)) {
		return;
		}
		this.progressMap.update(m => { const copy = { ...m }; copy[id] = 0; return copy; });
		const start = Date.now();
		const stepMs = 50;
		const interval = setInterval(() => {
		const elapsed = Date.now() - start;
		const p = Math.min(100, Math.round((elapsed / this.HOLD_MS) * 100));
			this.progressMap.update(m => { const copy = { ...m }; copy[id] = p; return copy; });
		if (p >= 100) {
		clearInterval(interval);
		this.holdIntervals.delete(id);
		this.delete(element);
		}
		}, stepMs);
		this.holdIntervals.set(id, interval);
	}

	protected endHoldDelete(element: PartnerVView, _event: Event) {
		const id = element.id!;
		const interval = this.holdIntervals.get(id);
		if (!interval) {
		return;
		}
		clearInterval(interval);
		this.holdIntervals.delete(id);
		this.progressMap.update(m => { const copy = { ...m }; copy[id] = 0; return copy; });
		this.snackBar.open(`Hold the Delete button for ${this.HOLD_MS/1000} second to confirm deletion.`, 'OK', { duration: 3000 });
	}

}
