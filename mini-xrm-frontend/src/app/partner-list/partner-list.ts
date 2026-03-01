import { Component, OnInit, AfterViewInit, ViewChild, ChangeDetectorRef } from '@angular/core';
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
    protected dataSource = new MatTableDataSource<PartnerVView>([]);
    protected loading = false;
    protected totalElements = 0;
    protected pageSize = 10;
    protected pageIndex = 0;
    protected tags: { id: number; name: string }[] = [];
    protected selectedTagId: number | null = null;
    protected sortField: string | null = null;
    protected sortDirection: 'asc' | 'desc' | null = null;

    @ViewChild(MatPaginator) paginator!: MatPaginator;

    private readonly HOLD_MS = 1000;
    private holdIntervals = new Map<number, any>();
    protected progressMap: { [id: number]: number } = {};

    constructor(private partnerService: PartnerService, private partnerTagService: PartnerTagService, private cdr: ChangeDetectorRef, private snackBar: MatSnackBar) { }

    ngOnInit(): void {
        this.loadTags();
        this.loadPage(0, this.pageSize);
    }

    ngAfterViewInit(): void {
    }

    protected loadPage(page: number, pageSize: number) {
        this.loading = true;
        this.pageIndex = page;
        const params: any = { page, pageSize };
        if (this.selectedTagId != null) params.partnerTagId = this.selectedTagId;
        if (this.sortField) params.sortField = this.sortField;
        if (this.sortDirection) params.sortDirection = this.sortDirection;
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
                error: (err) => {
                    this.logger.error(() => 'Failed to load partners', err);
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
        this.loadPage(0, this.pageSize);
    }

    protected loadTags() {
        this.partnerTagService.listPartnerTags().subscribe({
            next: (res: any) => {
                this.tags = res.content.map((t: any) => ({ id: t.id, name: t.name }));
            },
            error: (err) => { this.logger.error(() => 'Failed to load partner tags', err); this.tags = []; }
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

    protected delete(element: PartnerVView) {
        this.partnerService.deletePartner({ partnerId: element.id! }).subscribe({
            next: () => {
                const id = element.id;
                setTimeout(() => {
                    // defer UI mutations to the next macrotask to avoid
                    // ExpressionChangedAfterItHasBeenCheckedError
                    this.loadPage(this.pageIndex, this.pageSize);
                    if (id != null) this.progressMap[id] = 0;
                    this.cdr.markForCheck();
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
            }
        }, stepMs);
        this.holdIntervals.set(id, interval);
    }

    protected endHoldDelete(element: PartnerVView, _event: Event) {
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
