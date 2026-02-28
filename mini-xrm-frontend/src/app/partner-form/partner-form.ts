import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSelectModule } from '@angular/material/select';
import { Router, ActivatedRoute } from '@angular/router';

import { PartnerService } from '../../../mini-xrm-client-angular/api/partner.service';
import { PartnerTagService } from '../../../mini-xrm-client-angular/api/partnerTag.service';
import { CreateOrUpdatePartnerRequestView } from '../../../mini-xrm-client-angular/model/createOrUpdatePartnerRequestView';

@Component({
selector: 'app-partner-form',
	imports: [CommonModule, ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatProgressSpinnerModule, MatSelectModule],
	templateUrl: './partner-form.html',
	styleUrl: './partner-form.scss',
})
export class PartnerForm implements OnInit {

	form: FormGroup;
	loading = false;
	saving = false;
	partnerId?: number;
	tags: Array<any> = [];

	constructor(
		private partnerService: PartnerService,
		private partnerTagService: PartnerTagService,
		private fb: FormBuilder,
		private route: ActivatedRoute,
		private router: Router,
		private cdr: ChangeDetectorRef
	) {
		this.form = this.fb.group({
			name: ['', Validators.maxLength(150)],
			taxNumber: ['', Validators.maxLength(20)],
			headquarters: ['', Validators.maxLength(150)],
			status: ['ACTIVE', Validators.required],
			tagIds: [[]]
		});
	}

	ngOnInit(): void {
		const idParam = this.route.snapshot.paramMap.get('id');
		const id = idParam ? Number(idParam) : NaN;
		if (!isNaN(id)) {
			this.partnerId = id;
			this.loadPartner(id);
		}
		this.loadTags();
	}

	private loadTags() {
		this.partnerTagService.listPartnerTags().subscribe({
			next: res => {
				this.tags = (res && res.content) || [];
				this.cdr.markForCheck();
			},
			error: () => {
				this.tags = [];
				this.cdr.markForCheck();
			}
		});
	}

	private loadPartner(id: number) {
		this.loading = true;
		this.partnerService.loadPartner({ partnerId: id }).subscribe({
			next: partner => {
				this.form.patchValue({
					name: partner.name,
					taxNumber: partner.taxNumber,
					headquarters: partner.headquarters,
					status: partner.status,
					tagIds: (partner.tags || []).map(t => (t as any).id)
				});
				this.loading = false;
				this.cdr.markForCheck();
			},
			error: () => {
				this.loading = false;
				this.cdr.markForCheck();
			}
		});
	}

	save() {
		if (this.form.invalid) {
			this.form.markAllAsTouched();
			return;
		}
		this.saving = true;

		const payload: CreateOrUpdatePartnerRequestView = {
			name: this.form.value.name,
			taxNumber: this.form.value.taxNumber,
			headquarters: this.form.value.headquarters,
			status: this.form.value.status,
			tagIds: this.form.value.tagIds || []
		};

		const obs = this.partnerId
			? this.partnerService.updatePartner({ partnerId: this.partnerId, createOrUpdatePartnerRequestView: payload })
			: this.partnerService.createPartner({ createOrUpdatePartnerRequestView: payload });

		obs.subscribe({
			next: () => {
				this.saving = false;
				this.router.navigate(['partner-list']);
			},
			error: () => {
				this.saving = false;
				this.cdr.markForCheck();
			}
		});
	}

	protected cancel() {
		this.router.navigate(['/']);
	}

}
