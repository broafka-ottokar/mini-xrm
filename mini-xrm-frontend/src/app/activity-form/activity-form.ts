import { Component, OnInit, ChangeDetectorRef, signal, WritableSignal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators, ValidatorFn, AbstractControl } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { Router, ActivatedRoute } from '@angular/router';
import { finalize } from 'rxjs/operators';

import { ActivityService } from '../../../mini-xrm-client-angular/api/activity.service';
import { ActivityView } from '../../../mini-xrm-client-angular/model/activityView';
import { PartnerService } from '../../../mini-xrm-client-angular/api/partner.service';
import { PartnerVView } from '../../../mini-xrm-client-angular/model/partnerVView';
import { getLogger } from '../logging/logger';

@Component({
	selector: 'app-activity-form',
	imports: [CommonModule, ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatProgressSpinnerModule, MatSelectModule, MatSnackBarModule],
	templateUrl: './activity-form.html',
	styleUrl: './activity-form.scss',
})
export class ActivityForm implements OnInit {
	private readonly logger = getLogger('component.ActivityForm');
	protected form!: FormGroup;
	protected loading: WritableSignal<boolean> = signal(false);
	protected saving: WritableSignal<boolean> = signal(false);
	protected editingId?: number;
	protected partners: PartnerVView[] = [];

	constructor(
		private fb: FormBuilder,
		private activityService: ActivityService,
		private partnerService: PartnerService,
		private route: ActivatedRoute,
		private router: Router,
		private cdr: ChangeDetectorRef,
		private snackBar: MatSnackBar
	) {}

	ngOnInit(): void {
		this.form = this.fb.group({
			subject: ['', Validators.maxLength(150)],
			type: ['', Validators.maxLength(50)],
			description: ['', Validators.maxLength(100_000)],
			durationMinutes: [0, [Validators.required, Validators.min(1)]],
			personResponsible: ['', [Validators.required, Validators.maxLength(150)]],
			partnerId: [null, [Validators.required, this.partnerActiveValidator()]],
		});

		this.partnerService.searchPartners({ page: 0, pageSize: 200 }).subscribe({
			next: (res) => {
				this.partners = res.content ?? [];
				this.form.get('partnerId')?.updateValueAndValidity();
				this.cdr.markForCheck();
			},
			error: (err) => {
				this.logger.error(() => 'Failed to load partners for activity form', err);
				this.partners = [];
				this.cdr.markForCheck();
				this.snackBar.open('Failed to load partners', 'Close', { duration: 5000 });
			}
		});

		const idParam = this.route.snapshot.paramMap.get('id');
		const id = idParam ? Number(idParam) : NaN;
		if (!isNaN(id)) {
			this.loadActivity(id);
		}
	}

	protected loadActivity(id: number) {
		this.loading.set(true);
		this.activityService
			.loadActivity({ activityId: id })
			.pipe(finalize(() => {
				this.loading.set(false);
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
					// Re-run partner validation now that we set the value from the loaded activity
					this.form.get('partnerId')?.updateValueAndValidity();
				},
				error: (err) => {
					this.handleActivityLoadError(err);
				}
			});
	}

	protected handleActivityLoadError(err: any) {
		this.logger.error(() => 'Failed to load activity', err);
		this.loading.set(false);
		this.cdr.markForCheck();
		this.snackBar.open('Failed to load activity', 'Close', { duration: 5000 });
	}

	protected save() {
		if (this.form.invalid) {
			this.form.markAllAsTouched();
			return;
		}

		const payload = this.form.value;
		this.saving.set(true);

		if (this.editingId != null) {
			this.activityService
				.updateActivity({
					activityId: this.editingId,
					createOrUpdateActivityRequestView: payload
				})
					.pipe(finalize(() => {
							this.saving.set(false);
							this.cdr.markForCheck();
						}))
				.subscribe({
					next: () => {
						this.handleActivitySaved(payload);
					},
					error: (err) => {
						this.handleActivitySaveError(err);
					}
				});
		} else {
			this.activityService
				.createActivity({
					createOrUpdateActivityRequestView: payload
				})
					.pipe(finalize(() => {
							this.saving.set(false);
							this.cdr.markForCheck();
						}))
				.subscribe({
					next: () => {
						this.handleActivitySaved(payload);
					},
					error: (err) => {
						this.handleActivitySaveError(err);
					}
				});
		}
	}

	handleActivitySaveError(err: any) {
		this.logger.error(() => 'Failed to create activity', err);
		this.snackBar.open('Failed to create activity', 'Close', { duration: 5000 });
	}

	handleActivitySaved(payload: any) {
		this.snackBar.open('Activity saved', 'Close', { duration: 3000 });
		this.router.navigate(['/partner-details', payload.partnerId]);
	}

	protected cancel() {
		const partnerId = this.form.value.partnerId;
		if (partnerId) {
			this.router.navigate(['/partner-details', partnerId]);
		} else {
			this.router.navigate(['/']);
		}
	}

	protected partnerActiveValidator(): ValidatorFn {
		return (control: AbstractControl) => {
			if (this.editingId) {
				return null;
			} else {
				const id = control.value;
				if (id === null || id === undefined) {
					return null;
				}
				const pid = Number(id);
				const p = this.partners.find(x => (x.id === pid));
				if (!p) {
					return null;
				}
				return String(p.status).toUpperCase() === 'INACTIVE' ? { inactivePartner: true } : null;
			}
		};
	}

}
