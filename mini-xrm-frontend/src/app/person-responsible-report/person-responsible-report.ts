import { Component, OnInit, AfterViewInit, ViewChild, signal, WritableSignal } from '@angular/core';
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
	protected dataSource: WritableSignal<MatTableDataSource<PersonResponsibleReportItemView>> = signal(new MatTableDataSource<PersonResponsibleReportItemView>([]));
	protected loading: WritableSignal<boolean> = signal(false);
	protected totalElements: WritableSignal<number> = signal(0);
	protected pageSize: WritableSignal<number> = signal(10);
	protected pageIndex: WritableSignal<number> = signal(0);

	protected sortField: WritableSignal<PersonResponsibleReportSortFieldView | null> = signal(null);
	protected sortDirection: WritableSignal<SortDirectionView | null> = signal(null);

	@ViewChild(MatPaginator) paginator!: MatPaginator;

	constructor(private reportService: ReportService) { }

	ngOnInit(): void {
		this.loadPage(0, this.pageSize());
	}

	ngAfterViewInit(): void {
	}

	protected loadPage(page: number, pageSize: number) {
		this.loading.set(true);
		this.pageIndex.set(page);
		const params: any = { page, pageSize };
		if (this.sortField()) params.sortField = this.sortField();
		if (this.sortDirection()) params.sortDirection = this.sortDirection();

		this.reportService
			.reportPersonResponsible(params)
			.pipe(finalize(() => {
				this.loading.set(false);
			}))
			.subscribe({
				next: (res) => {
					this.dataSource.set(new MatTableDataSource<PersonResponsibleReportItemView>(res.content ?? []));
					this.totalElements.set(res.totalElements ?? 0);
					this.pageSize.set(res.pageSize ?? pageSize);
				},
				error: (err) => {
					this.logger.error(() => 'Failed to load person responsible report', err);
					this.dataSource.set(new MatTableDataSource<PersonResponsibleReportItemView>([]));
					this.totalElements.set(0);
				}
			});
	}

	protected toggleSort(field: PersonResponsibleReportSortFieldView) {
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

	protected onPage(event: PageEvent) {
		this.loadPage(event.pageIndex, event.pageSize);
	}

}
