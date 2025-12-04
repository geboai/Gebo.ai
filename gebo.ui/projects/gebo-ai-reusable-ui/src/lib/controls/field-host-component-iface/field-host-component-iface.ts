import { InjectionToken } from '@angular/core';
/******************************************************************
 * With the following putting in the implementation of GeboAIThisComponent that implements GeboAIFieldHost:
 * providers: [{ provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIThisComponent) }]
 */
export interface GeboAIFieldHost {
  getEntityName(): string;

};
class GeboAIFieldHostImpl implements GeboAIFieldHost {
  constructor(private entityName: string) {

  }
  getEntityName(): string {
    return this.entityName;
  }
}

export function fieldHostComponentName(entityName: string): GeboAIFieldHost {
  return new GeboAIFieldHostImpl(entityName);
}
export const GEBO_AI_FIELD_HOST = new InjectionToken<GeboAIFieldHost>('GEBO_AI_FIELD_HOST');
export const GEBO_AI_MODULE = new InjectionToken<string>("GEBO_AI_MODULE");
