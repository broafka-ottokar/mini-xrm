import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSelectModule } from '@angular/material/select';
import { Router, ActivatedRoute } from '@angular/router';
import { finalize } from 'rxjs/operators';

import { ActivityService } from '../../../mini-xrm-client-angular/api/activity.service';
import { ActivityView } from '../../../mini-xrm-client-angular/model/activityView';
import { PartnerService } from '../../../mini-xrm-client-angular/api/partner.service';
import { PartnerView } from '../../../mini-xrm-client-angular/model/partnerView';

@Component({
	selector: 'app-activity-form',
	imports: [CommonModule, ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatProgressSpinnerModule, MatSelectModule],
	templateUrl: './activity-form.html',
	styleUrl: './activity-form.scss',
})
export class ActivityForm implements OnInit {
	protected form!: FormGroup;
	protected loading = false;
	protected editingId?: number;
	protected partners: PartnerView[] = [];

	constructor(
		private fb: FormBuilder,
		private activityService: ActivityService,
		private partnerService: PartnerService,
		private route: ActivatedRoute,
		private router: Router,
		private cdr: ChangeDetectorRef
	) {}

	ngOnInit(): void {
		this.form = this.fb.group({
			subject: ['', Validators.maxLength(150)],
			type: ['', Validators.maxLength(50)],
			description: ['', Validators.maxLength(100_000)],
			durationMinutes: [0, [Validators.required, Validators.min(1)]],
			personResponsible: ['', [Validators.required, Validators.maxLength(150)]],
			partnerId: [null, Validators.required],
		});

		this.partnerService.searchPartners({ page: 0, pageSize: 200 }).subscribe({
			next: (res) => {
				this.partners = res.content ?? [];
				this.cdr.markForCheck();
			},
			error: () => {
				this.partners = [];
				this.cdr.markForCheck();
			}
		});

		const idParam = this.route.snapshot.paramMap.get('id');
		const id = idParam ? Number(idParam) : NaN;
		if (!isNaN(id)) {
			this.loadActivity(id);
		}
	}

	protected loadActivity(id: number) {
		this.loading = true;
		this.activityService
			.loadActivity({ activityId: id })
			.pipe(finalize(() => {
				this.loading = false;
				this.cdr.markForCheck();
			}))
			.subscribe({
				next: (a: ActivityView) => {
					this.editingId = a.id;
					this.form.patchValue({
						subject: a.subject,
						type: a.type,
						description: a.description,
						durationMinutes: a.durationMinutes,
						personResponsible: a.personResponsible,
						partnerId: a.partnerId,
					});
				},
				error: () => {
				}
			});
	}

	protected save() {
		if (this.form.invalid) {
			this.form.markAllAsTouched();
			return;
		}

		const payload = this.form.value;
		this.loading = true;

		if (this.editingId != null) {
			this.activityService
				.updateActivity({ activityId: this.editingId, createOrUpdateActivityRequestView: payload })
				.pipe(finalize(() => { this.loading = false; this.cdr.markForCheck(); }))
				.subscribe({ next: () => this.router.navigate(['/partner-details', payload.partnerId]), error: () => {} });
		} else {
			this.activityService
				.createActivity({ createOrUpdateActivityRequestView: payload })
				.pipe(finalize(() => { this.loading = false; this.cdr.markForCheck(); }))
				.subscribe({ next: () => this.router.navigate(['/partner-details', payload.partnerId]), error: () => {} });
		}
	}

	protected cancel() {
		this.router.navigate(['/']);
	}

}
