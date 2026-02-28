import { Component, OnInit, AfterViewInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { RouterLinkWithHref } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatChipsModule } from '@angular/material/chips';
import { MatTableDataSource } from '@angular/material/table';
import { finalize } from 'rxjs/operators';

import { PartnerService } from '../../../mini-xrm-client-angular/api/partner.service';
import { PartnerView } from '../../../mini-xrm-client-angular/model/partnerView';
import { PartnerStatusView } from '../../../mini-xrm-client-angular/model/partnerStatusView';

@Component({
	selector: 'app-partner-list',
	imports: [CommonModule, MatTableModule, MatPaginatorModule, MatProgressSpinnerModule, MatChipsModule, RouterLinkWithHref],
	templateUrl: './partner-list.html',
	styleUrl: './partner-list.scss',
})
export class PartnerList implements OnInit, AfterViewInit {

	protected displayedColumns = ['name', 'headquarters', 'status', 'tags'];
	protected dataSource = new MatTableDataSource<PartnerView>([]);
	protected loading = false;
	protected totalElements = 0;
	protected pageSize = 10;

	@ViewChild(MatPaginator) paginator!: MatPaginator;

	constructor(private partnerService: PartnerService, private cdr: ChangeDetectorRef) { }

	ngOnInit(): void {
		this.loadPage(0, this.pageSize);
	}

	ngAfterViewInit(): void {
		this.dataSource.paginator = this.paginator;
	}

	protected loadPage(page: number, pageSize: number) {
		this.loading = true;
		this.partnerService
			.searchPartners({ page, pageSize })
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
}
