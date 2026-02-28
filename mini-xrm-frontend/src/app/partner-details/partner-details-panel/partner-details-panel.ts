import { Component, OnInit, ChangeDetectorRef, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatChipsModule } from '@angular/material/chips';
import { finalize } from 'rxjs/operators';

import { PartnerService } from '../../../../mini-xrm-client-angular/api/partner.service';
import { PartnerView } from '../../../../mini-xrm-client-angular/model/partnerView';
import { PartnerStatusView } from '../../../../mini-xrm-client-angular/model/partnerStatusView';

@Component({
	selector: 'app-partner-details-panel',
	imports: [CommonModule, MatChipsModule],
	templateUrl: './partner-details-panel.html',
	styleUrl: './partner-details-panel.scss',
})
export class PartnerDetailsPanel implements OnInit {

	@Input() partnerId!: number;

	protected partner?: PartnerView;
	protected loading = false;

	constructor(private partnerService: PartnerService, private cdr: ChangeDetectorRef) { }

	ngOnInit(): void {
		this.load(this.partnerId);
	}

	protected load(id: number) {
		this.loading = true;
		this.partnerService
			.loadPartner({ partnerId: id })
			.pipe(finalize(() => {
				this.loading = false;
				this.cdr.markForCheck();
			}))
			.subscribe({
				next: (p) => (this.partner = p),
				error: () => {
					this.partner = undefined;
				},
			});
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
