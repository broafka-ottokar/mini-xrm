import { Component, OnInit, AfterViewInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { RouterLinkWithHref } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatChipsModule } from '@angular/material/chips';
import { MatTableDataSource } from '@angular/material/table';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { finalize } from 'rxjs/operators';

import { PartnerService } from '../../../mini-xrm-client-angular/api/partner.service';
import { PartnerTagService } from '../../../mini-xrm-client-angular/api/partnerTag.service';
import { PartnerView } from '../../../mini-xrm-client-angular/model/partnerView';
import { PartnerStatusView } from '../../../mini-xrm-client-angular/model/partnerStatusView';

@Component({
	selector: 'app-partner-list',
	imports: [CommonModule, MatTableModule, MatPaginatorModule, MatProgressSpinnerModule, MatChipsModule, MatFormFieldModule, MatSelectModule, RouterLinkWithHref],
	templateUrl: './partner-list.html',
	styleUrls: ['./partner-list.scss'],
})
export class PartnerList implements OnInit, AfterViewInit {

	protected displayedColumns = ['name', 'headquarters', 'status', 'tags', 'actions'];
	protected dataSource = new MatTableDataSource<PartnerView>([]);
	protected loading = false;
	protected totalElements = 0;
	protected pageSize = 10;
	protected tags: { id: number; name: string }[] = [];
	protected selectedTagId: number | null = null;

	@ViewChild(MatPaginator) paginator!: MatPaginator;

	constructor(private partnerService: PartnerService, private partnerTagService: PartnerTagService, private cdr: ChangeDetectorRef) { }

	ngOnInit(): void {
		this.loadTags();
		this.loadPage(0, this.pageSize);
	}

	ngAfterViewInit(): void {
		this.dataSource.paginator = this.paginator;
	}

	protected loadPage(page: number, pageSize: number) {
		this.loading = true;
		const params: any = { page, pageSize };
		if (this.selectedTagId != null) params.partnerTagId = this.selectedTagId;
		this.partnerService
			.searchPartners(params)
			.pipe(finalize(() => {
				this.loading = false;
				this.cdr.markForCheck();
			}))
			.subscribe({
				next: (res) => {
					this.dataSource.data = res.content ?? [];
					this.totalElements = res.totalElements ?? 0;
					this.pageSize = res.pageSize ?? pageSize;
				},
				error: () => {
				}
			});
	}

	protected loadTags() {
		this.partnerTagService.listPartnerTags().subscribe({
			next: (res: any) => {
				// PartnerTagsView likely contains `content` or `tags`; try common shapes
				if (Array.isArray(res)) {
					this.tags = res.map((t: any) => ({ id: t.id, name: t.name }));
				} else if (res && Array.isArray(res.tags)) {
					this.tags = res.tags.map((t: any) => ({ id: t.id, name: t.name }));
				} else if (res && Array.isArray(res.content)) {
					this.tags = res.content.map((t: any) => ({ id: t.id, name: t.name }));
				}
			},
			error: () => { this.tags = []; }
		});
	}

	protected onTagChange(tagId: string) {
		const id = tagId === '' ? null : Number(tagId);
		this.selectedTagId = id;
		this.loadPage(0, this.pageSize);
	}

	protected onPage(event: PageEvent) {
		this.loadPage(event.pageIndex, event.pageSize);
	}

	protected statusLabel(status?: PartnerStatusView | string) {
		if (!status) return '';
		switch (String(status)) {
			case 'ACTIVE':
				return 'Active';
			case 'INACTIVE':
				return 'Inactive';
			default:
				throw new Error('Unknown status: ' + status);
		}
	}

	protected delete(element: PartnerView) {
		this.partnerService.deletePartner({ partnerId: element.id! }).subscribe({
			next: () => {
				this.loadPage(this.paginator.pageIndex, this.paginator.pageSize);
			},
			error: () => {
				alert('Failed to delete partner');
			}
		});
	}

}
