import { Component, OnInit, ChangeDetectorRef, signal, WritableSignal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSelectModule } from '@angular/material/select';
import { Router, ActivatedRoute } from '@angular/router';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

import { PartnerService } from '../../../mini-xrm-client-angular/api/partner.service';
import { PartnerTagService } from '../../../mini-xrm-client-angular/api/partnerTag.service';
import { CreateOrUpdatePartnerRequestView } from '../../../mini-xrm-client-angular/model/createOrUpdatePartnerRequestView';
import { getLogger } from '../logging/logger';

@Component({
selector: 'app-partner-form',
	imports: [CommonModule, ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatProgressSpinnerModule, MatSelectModule, MatSnackBarModule],
	templateUrl: './partner-form.html',
	styleUrl: './partner-form.scss',
})
export class PartnerForm implements OnInit {

	private readonly logger = getLogger('component.PartnerForm');

	form: FormGroup;
	loading: WritableSignal<boolean> = signal(false);
	saving: WritableSignal<boolean> = signal(false);
	partnerId?: number;
	tags: Array<any> = [];

	constructor(
		private partnerService: PartnerService,
		private partnerTagService: PartnerTagService,
		private fb: FormBuilder,
		private route: ActivatedRoute,
		private router: Router,
		private cdr: ChangeDetectorRef,
		private snackBar: MatSnackBar
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
			error: (err) => {
				this.handlePartnerTagLoadError(err);
			}
		});
	}

	protected handlePartnerTagLoadError(err: any) {
		this.logger.error(() => 'Failed to load partner tags', err);
		this.tags = [];
		this.cdr.markForCheck();
		this.snackBar.open('Failed to load partner tags', 'OK', { duration: 5000 });
	}

	private loadPartner(id: number) {
		this.loading.set(true);
		this.partnerService.loadPartner({ partnerId: id }).subscribe({
			next: partner => {
				this.form.patchValue({
					name: partner.name,
					taxNumber: partner.taxNumber,
					headquarters: partner.headquarters,
					status: partner.status,
					tagIds: (partner.tags || []).map(t => (t as any).id)
				});
				this.loading.set(false);
				this.cdr.markForCheck();
			},
			error: (err) => {
				this.handlePartnerLoadError(err);
			}
		});
	}

	protected handlePartnerLoadError(err: any) {
		this.logger.error(() => 'Failed to load partner', err);
		this.loading.set(false);
		this.cdr.markForCheck();
		this.snackBar.open('Failed to load partner', 'OK', { duration: 5000 });
	}

	save() {
		if (this.form.invalid) {
			this.form.markAllAsTouched();
			return;
		}
		this.saving.set(true);

		const payload: CreateOrUpdatePartnerRequestView = {
			name: this.form.value.name,
			taxNumber: this.form.value.taxNumber,
			headquarters: this.form.value.headquarters,
			status: this.form.value.status,
			tagIds: this.form.value.tagIds || []
		};

		if (this.partnerId) {
			this.partnerService.updatePartner({ partnerId: this.partnerId, createOrUpdatePartnerRequestView: payload }).subscribe({
				next: () => {
					this.handlePartnerSaved(payload);
				},
				error: (err) => {
					this.handlePartnerSaveError(err);
				}
			});
		} else {
			this.partnerService.createPartner({ createOrUpdatePartnerRequestView: payload }).subscribe({
				next: (res) => {
					this.partnerId = res.id;
					this.handlePartnerSaved(payload);
				},
				error: (err) => {
					this.handlePartnerSaveError(err);
				}
			});
		}
	}

	handlePartnerSaveError(err: any) {
		this.logger.error(() => 'Failed to save partner', err);
		this.saving.set(false);
		this.cdr.markForCheck();
		this.snackBar.open('Failed to save partner', 'OK', { duration: 5000 });
	}

	protected handlePartnerSaved(payload: CreateOrUpdatePartnerRequestView) {
		this.saving.set(false);
		this.snackBar.open('Partner saved', 'OK', { duration: 3000 });
		if (this.partnerId) {
			this.router.navigate(['partner-details', this.partnerId]);
		} else {
			this.router.navigate(['/']);
		}
	}

	protected cancel() {
		this.router.navigate(['/']);
	}

}
