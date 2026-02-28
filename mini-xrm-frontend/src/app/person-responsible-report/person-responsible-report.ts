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

@Component({
	selector: 'app-person-responsible-report',
	imports: [CommonModule, MatTableModule, MatPaginatorModule, MatProgressSpinnerModule, MatChipsModule],
	templateUrl: './person-responsible-report.html',
	styleUrl: './person-responsible-report.scss',
})
export class PersonResponsibleReport implements OnInit, AfterViewInit {

	protected displayedColumns = ['personResponsible', 'totalDurationMinutes', 'partnerCount'];
	protected dataSource = new MatTableDataSource<PersonResponsibleReportItemView>([]);
	protected loading = false;
	protected totalElements = 0;
	protected pageSize = 10;

	@ViewChild(MatPaginator) paginator!: MatPaginator;

	constructor(private reportService: ReportService, private cdr: ChangeDetectorRef) { }

	ngOnInit(): void {
		this.loadPage(0, this.pageSize);
	}

	ngAfterViewInit(): void {
		this.dataSource.paginator = this.paginator;
	}

	protected loadPage(page: number, pageSize: number) {
		this.loading = true;
		this.reportService
			.reportPersonResponsible({ page, pageSize })
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
		this.loadPage(event.pageIndex, event.pageSize);
	}

}
