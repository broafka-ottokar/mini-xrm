import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatChipsModule } from '@angular/material/chips';
import { ActivatedRoute } from '@angular/router';

import { PartnerService } from '../../../mini-xrm-client-angular/api/partner.service';
import { PartnerDetailsPanel } from "./partner-details-panel/partner-details-panel";
import { PartnerActivityListPanel } from "./partner-activity-list-panel/partner-activity-list-panel";

@Component({
	selector: 'app-partner-details',
	imports: [CommonModule, MatChipsModule, PartnerDetailsPanel, PartnerActivityListPanel],
	templateUrl: './partner-details.html',
	styleUrl: './partner-details.scss',
})
export class PartnerDetails implements OnInit {

	constructor(private route: ActivatedRoute, private partnerService: PartnerService, private cdr: ChangeDetectorRef) { }

	partnerId?: number;
	
	ngOnInit(): void {
		const idParam = this.route.snapshot.paramMap.get('id');
		const id = idParam ? Number(idParam) : NaN;
		if (!isNaN(id)) {
			this.partnerId = id;
		}
	}

}
