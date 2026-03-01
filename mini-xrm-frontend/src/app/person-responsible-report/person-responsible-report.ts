import { Component, OnInit, AfterViewInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatChipsModule } from '@angular/material/chips';
import { MatTableDataSource } from '@angular/material/table';
import { finalize } from 'rxjs/operators';
import { ReportService } from '../../../mini-xrm-client-angular/api/report.service';
import { PersonResponsibleReportItemView } from '../../../mini-xrm-client-angular/model/personResponsibleReportItemView';
import { PersonResponsibleReportSortFieldView } from '../../../mini-xrm-client-angular/model/personResponsibleReportSortFieldView';
import { SortDirectionView } from '../../../mini-xrm-client-angular/model/sortDirectionView';
import { getLogger } from '../logging/logger';

@Component({
	selector: 'app-person-responsible-report',
	imports: [CommonModule, MatTableModule, MatPaginatorModule, MatProgressSpinnerModule, MatChipsModule],
	templateUrl: './person-responsible-report.html',
	styleUrl: './person-responsible-report.scss',
})
export class PersonResponsibleReport implements OnInit, AfterViewInit {

	private readonly logger = getLogger('component.PersonResponsibleReport');

	protected displayedColumns = ['personResponsible', 'totalDurationMinutes', 'partnerCount'];
	protected dataSource = new MatTableDataSource<PersonResponsibleReportItemView>([]);
	protected loading = false;
	protected totalElements = 0;
	protected pageSize = 10;
    protected pageIndex = 0;

	protected sortField: PersonResponsibleReportSortFieldView | null = null;
	protected sortDirection: SortDirectionView | null = null;

	@ViewChild(MatPaginator) paginator!: MatPaginator;

	constructor(private reportService: ReportService, private cdr: ChangeDetectorRef) { }

	ngOnInit(): void {
		this.loadPage(0, this.pageSize);
	}

	ngAfterViewInit(): void {
	}

	protected loadPage(page: number, pageSize: number) {
		this.loading = true;
		this.pageIndex = page;
		const params: any = { page, pageSize };
		if (this.sortField) params.sortField = this.sortField;
		if (this.sortDirection) params.sortDirection = this.sortDirection;

		this.reportService
			.reportPersonResponsible(params)
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
					this.logger.error(() => 'Failed to load person responsible report', err);
					this.dataSource.data = [];
					this.totalElements = 0;
				}
			});
	}

	protected toggleSort(field: PersonResponsibleReportSortFieldView) {
		if (this.sortField !== field) {
			this.sortField = field;
			this.sortDirection = 'asc';
		} else {
			if (this.sortDirection === 'asc') this.sortDirection = 'desc';
			else if (this.sortDirection === 'desc') { this.sortField = null; this.sortDirection = null; }
			else this.sortDirection = 'asc';
		}
		this.loadPage(0, this.pageSize);
	}

	protected onPage(event: PageEvent) {
		this.loadPage(event.pageIndex, event.pageSize);
	}

}
