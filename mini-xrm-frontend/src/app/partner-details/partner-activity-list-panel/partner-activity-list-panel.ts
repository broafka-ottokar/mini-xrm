import { Component, OnInit, ChangeDetectorRef, Input, OnChanges, SimpleChanges, AfterViewInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatPaginator, PageEvent, MatPaginatorModule } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatTableModule } from '@angular/material/table';
import { finalize } from 'rxjs/operators';

import { ActivityService } from '../../../../mini-xrm-client-angular/api/activity.service';
import { ActivityView } from '../../../../mini-xrm-client-angular/model/activityView';
import { getLogger } from '../../logging/logger';
import { MatChipsModule } from '@angular/material/chips';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";
import { RouterLinkWithHref } from '@angular/router';

@Component({
	selector: 'app-partner-activity-list-panel',
	imports: [CommonModule, MatChipsModule, MatProgressSpinnerModule, MatPaginatorModule, MatTableModule, MatSnackBarModule, RouterLinkWithHref],
	templateUrl: './partner-activity-list-panel.html',
	styleUrl: './partner-activity-list-panel.scss',
})
export class PartnerActivityListPanel implements OnInit, OnChanges, AfterViewInit {

	private readonly logger = getLogger('component.PartnerActivityListPanel');

	@Input() partnerId!: number;

	protected displayedColumns = ['subject', 'type', 'durationMinutes', 'personResponsible', 'actions'];
	protected dataSource = new MatTableDataSource<ActivityView>([]);
	protected loading = false;
	protected totalElements = 0;
	protected pageSize = 10;
    protected pageIndex = 0;
    protected sortField: string | null = null;
    protected sortDirection: 'asc' | 'desc' | null = null;

	@ViewChild(MatPaginator) paginator!: MatPaginator;

	private readonly HOLD_MS = 1000;
	private holdIntervals = new Map<number, any>();
	protected progressMap: { [id: number]: number } = {};

	constructor(private activityService: ActivityService, private cdr: ChangeDetectorRef, private snackBar: MatSnackBar) { }

	ngOnInit(): void {}

	ngAfterViewInit(): void {
		// Server-side pagination is used for this table; do not attach
		// the MatTableDataSource paginator (that would enable client-side paging).
	}

	ngOnChanges(changes: SimpleChanges): void {
		if (changes['partnerId'] && changes['partnerId'].currentValue != null) {
			const id = Number(changes['partnerId'].currentValue);
			if (!isNaN(id)) this.loadPage(id, 0, this.pageSize);
		}
	}

	protected loadPage(partnerId: number, page: number, pageSize: number) {
		this.loading = true;
		this.pageIndex = page;
		const params: any = { partnerId, page, pageSize };
		if (this.sortField) params.sortField = this.sortField;
		if (this.sortDirection) params.sortDirection = this.sortDirection;
		this.activityService
			.listActivitiesByPartnerId(params)
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
				error: (err) => {
					this.logger.error(() => 'Failed to load partner activities', err);
					this.dataSource.data = [];
					this.totalElements = 0;
				}
			});
	}

	protected toggleSort(field: string) {
		if (this.sortField !== field) {
			this.sortField = field;
			this.sortDirection = 'asc';
		} else {
			if (this.sortDirection === 'asc') this.sortDirection = 'desc';
			else if (this.sortDirection === 'desc') { this.sortField = null; this.sortDirection = null; }
			else this.sortDirection = 'asc';
		}
		this.loadPage(this.partnerId, 0, this.pageSize);
	}

	protected onPage(event: PageEvent) {
		this.loadPage(this.partnerId, event.pageIndex, event.pageSize);
	}

	protected delete(element: ActivityView) {
		this.activityService.deleteActivity({ activityId: element.id! }).subscribe({
			next: () => {
				this.loadPage(this.partnerId, this.paginator.pageIndex, this.paginator.pageSize);
				this.snackBar.open('Activity deleted', 'OK', { duration: 3000 });
			},
			error: (err) => {
				this.logger.error(() => 'Failed to delete activity', err);
				this.snackBar.open('Failed to delete activity', 'OK', { duration: 5000 });
			}
		});
	}

	protected startHoldDelete(element: ActivityView, event: Event) {
		try { event.preventDefault(); } catch { }
		const id = element.id!;
		if (this.holdIntervals.has(id)) return;
		this.progressMap[id] = 0;
		const start = Date.now();
		const stepMs = 50;
		const interval = setInterval(() => {
			const elapsed = Date.now() - start;
			const p = Math.min(100, Math.round((elapsed / this.HOLD_MS) * 100));
			this.progressMap[id] = p;
			this.cdr.markForCheck();
			if (p >= 100) {
				clearInterval(interval);
				this.holdIntervals.delete(id);
				this.delete(element);
				this.progressMap[id] = 0;
			}
		}, stepMs);
		this.holdIntervals.set(id, interval);
	}

	protected endHoldDelete(element: ActivityView, _event: Event) {
		const id = element.id!;
		const interval = this.holdIntervals.get(id);
		if (!interval) return;
		clearInterval(interval);
		this.holdIntervals.delete(id);
		this.progressMap[id] = 0;
		this.cdr.markForCheck();
		this.snackBar.open(`Hold the Delete button for ${this.HOLD_MS/1000} second to confirm deletion.`, 'OK', { duration: 3000 });
	}

}
