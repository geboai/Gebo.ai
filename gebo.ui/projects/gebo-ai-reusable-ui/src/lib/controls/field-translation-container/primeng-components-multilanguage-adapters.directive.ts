import { Directive, Host, InjectionToken, Optional } from "@angular/core";
import { Button } from "primeng/button";
import { Fieldset } from "primeng/fieldset";
import { Panel } from "primeng/panel";

export interface LabelTarget {
  set(key: string, value: string): void;
}

export const GEBO_MULILANGUAGE_TARGET = new InjectionToken<LabelTarget>('GEBO_MULILANGUAGE_TARGET');
//gebo-ai-label
@Directive({
  selector: 'p-button[gebo-ai-label]',
  providers: [{ provide: GEBO_MULILANGUAGE_TARGET, useExisting: PButtonLabelTarget }],
  standalone:false
})
export class PButtonLabelTarget implements LabelTarget {
  constructor(@Optional() @Host() private btn: Button) { }
  set(key: string, value: string) {
    if (!this.btn) return;
    //setInput(this.btn, key, value);
    (this.btn as any)[key] = value;
  }
} 
@Directive({
  selector: 'p-fieldset[gebo-ai-label]',
  providers: [{ provide: GEBO_MULILANGUAGE_TARGET, useExisting: PFieldSetLabelTarget }],
  standalone:false
})
export class PFieldSetLabelTarget implements LabelTarget {
  constructor(@Optional() @Host() private btn: Fieldset) { }
  set(key: string, value: string) {
    if (!this.btn) return;
    //setInput(this.btn, key, value);
    (this.btn as any)[key] = value;
  }
} 
@Directive({
  selector: 'p-panel[gebo-ai-label]',
  providers: [{ provide: GEBO_MULILANGUAGE_TARGET, useExisting: PPanelLabelTarget }],
  standalone:false
})
export class PPanelLabelTarget implements LabelTarget {
  constructor(@Optional() @Host() private btn: Panel) { }
  set(key: string, value: string) {
    if (!this.btn) return;
    //setInput(this.btn, key, value);
    (this.btn as any)[key] = value;
  }
} 