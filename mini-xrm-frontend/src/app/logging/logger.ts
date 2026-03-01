import { log4TSProvider } from './log.config';

export function getLogger(name: string) {
  return log4TSProvider.getLogger(name);
}
