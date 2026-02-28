import { Component, OnInit, ChangeDetectorRef, Input, OnChanges, SimpleChanges, AfterViewInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatPaginator, PageEvent, MatPaginatorModule } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatTableModule } from '@angular/material/table';
import { finalize } from 'rxjs/operators';

import { ActivityService } from '../../../../mini-xrm-client-angular/api/activity.service';
import { ActivityView } from '../../../../mini-xrm-client-angular/model/activityView';
import { MatChipsModule } from '@angular/material/chips';
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";

@Component({
	selector: 'app-partner-activity-list-panel',
	imports: [CommonModule, MatChipsModule, MatProgressSpinnerModule, MatPaginatorModule, MatTableModule],
	templateUrl: './partner-activity-list-panel.html',
	styleUrl: './partner-activity-list-panel.scss',
})
export class PartnerActivityListPanel implements OnInit, OnChanges, AfterViewInit {

	@Input() partnerId!: number;

	protected displayedColumns = ['subject', 'type', 'durationMinutes', 'personResponsible'];
	protected dataSource = new MatTableDataSource<ActivityView>([]);
	protected loading = false;
	protected totalElements = 0;
	protected pageSize = 10;

	@ViewChild(MatPaginator) paginator!: MatPaginator;

	constructor(private activityService: ActivityService, private cdr: ChangeDetectorRef) { }

	ngOnInit(): void {}

	ngAfterViewInit(): void {
		this.dataSource.paginator = this.paginator;
	}

	ngOnChanges(changes: SimpleChanges): void {
		if (changes['partnerId'] && changes['partnerId'].currentValue != null) {
			const id = Number(changes['partnerId'].currentValue);
			if (!isNaN(id)) this.loadPage(id, 0, this.pageSize);
		}
	}

	protected loadPage(partnerId: number, page: number, pageSize: number) {
		this.loading = true;
		this.activityService
			.listActivitiesByPartnerId({ partnerId, page, pageSize })
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
					this.dataSource.data = [];
					this.totalElements = 0;
				}
			});
	}

	protected onPage(event: PageEvent) {
		this.loadPage(this.partnerId, event.pageIndex, event.pageSize);
	}

}
