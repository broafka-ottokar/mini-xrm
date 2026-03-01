import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { App } from './app/app';
import { LOG4TS_LOG_CONTROL } from './app/logging/log.config';

// Expose runtime log control to the browser console (optional)
if (typeof window !== 'undefined') {
	(window as any).appLogControl = LOG4TS_LOG_CONTROL;
}

bootstrapApplication(App, appConfig)
	.catch((err) => console.error(err));
