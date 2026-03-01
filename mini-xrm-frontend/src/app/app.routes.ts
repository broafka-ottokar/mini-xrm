import { Routes } from '@angular/router';
import { PartnerList } from './partner-list/partner-list';
import { PartnerDetails } from './partner-details/partner-details';
import { PersonResponsibleReport } from './person-responsible-report/person-responsible-report';
import { ActivityForm } from './activity-form/activity-form';
import { PartnerForm } from './partner-form/partner-form';

export const routes: Routes = [
	{ path: 'partner-list', component: PartnerList },
	{ path: 'partner-details/:id', component: PartnerDetails },
	{ path: 'partner/new', component: PartnerForm },
	{ path: 'partner/:id', component: PartnerForm },
	{ path: 'activity/new', component: ActivityForm },
	{ path: 'activity/:id', component: ActivityForm },
	{ path: 'report', component: PersonResponsibleReport },
	{ path: '', redirectTo: 'partner-list', pathMatch: 'full' },
];
