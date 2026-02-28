export * from './activity.service';
import { ActivityService } from './activity.service';
export * from './partner.service';
import { PartnerService } from './partner.service';
export * from './partnerTag.service';
import { PartnerTagService } from './partnerTag.service';
export * from './report.service';
import { ReportService } from './report.service';
export const APIS = [ActivityService, PartnerService, PartnerTagService, ReportService];
