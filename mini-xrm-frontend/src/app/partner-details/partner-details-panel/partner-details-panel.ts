import { Component, OnInit, Input, signal, WritableSignal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatChipsModule } from '@angular/material/chips';
import { finalize } from 'rxjs/operators';

import { PartnerService } from '../../../../mini-xrm-client-angular/api/partner.service';
import { PartnerView } from '../../../../mini-xrm-client-angular/model/partnerView';
import { PartnerStatusView } from '../../../../mini-xrm-client-angular/model/partnerStatusView';
import { getLogger } from '../../logging/logger';

@Component({
	selector: 'app-partner-details-panel',
	imports: [CommonModule, MatChipsModule],
	templateUrl: './partner-details-panel.html',
	styleUrl: './partner-details-panel.scss',
})
export class PartnerDetailsPanel implements OnInit {

	private readonly logger = getLogger('component.PartnerDetailsPanel');

	@Input() partnerId!: number;

	protected partner: WritableSignal<PartnerView | undefined> = signal<PartnerView | undefined>(undefined);
	protected loading: WritableSignal<boolean> = signal<boolean>(false);

	constructor(private partnerService: PartnerService) { }

	ngOnInit(): void {
		this.load(this.partnerId);
	}

	protected load(id: number) {
		this.loading.set(true);
		this.partnerService
			.loadPartner({ partnerId: id })
			.pipe(finalize(() => {
				this.loading.set(false);
			}))
			.subscribe({
				next: (p) => this.partner.set(p),
				error: (err) => {
					this.logger.error(() => 'Failed to load partner details', err);
					this.partner.set(undefined);
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
