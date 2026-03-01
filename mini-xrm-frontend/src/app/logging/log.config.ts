import { LogLevel } from 'typescript-logging';
import { Log4TSProvider, LOG4TS_LOG_CONTROL } from 'typescript-logging-log4ts-style';

export const log4TSProvider = Log4TSProvider.createProvider('MiniXRMProvider', {
	level: LogLevel.Error,
	groups: [{ identifier: 'matchAll', expression: new RegExp('.+') }]
});

export { LOG4TS_LOG_CONTROL };
