import {
  ChangeDetectorRef,
  Directive,
  Host,
  Inject,
  InjectionToken,
  Optional,
  Self,
} from '@angular/core';

// PrimeNG component classes (importa solo quelli che usi davvero nel progetto)
import { Button } from 'primeng/button';
import { Fieldset } from 'primeng/fieldset';
import { Panel } from 'primeng/panel';
import { Dialog } from 'primeng/dialog';
import { Sidebar } from 'primeng/sidebar';
import { Card } from 'primeng/card';
import { TabView, TabPanel } from 'primeng/tabview';

import { Accordion, AccordionTab } from 'primeng/accordion';
import { Toolbar } from 'primeng/toolbar';
import { Chip } from 'primeng/chip';
import { Badge } from 'primeng/badge';
import { Tag } from 'primeng/tag';
import { Checkbox } from 'primeng/checkbox';
import { RadioButton } from 'primeng/radiobutton';
import { ToggleButton } from 'primeng/togglebutton';
import { InputText } from 'primeng/inputtext';
import { InputTextarea } from 'primeng/inputtextarea';
import { InputNumber } from 'primeng/inputnumber';
import { Dropdown } from 'primeng/dropdown';
import { MultiSelect } from 'primeng/multiselect';
import { Listbox } from 'primeng/listbox';
import { Calendar } from 'primeng/calendar';
import { Slider } from 'primeng/slider';
import { ProgressBar } from 'primeng/progressbar';
import { Steps } from 'primeng/steps';

import { Galleria } from 'primeng/galleria';
import { BaseComponent } from 'primeng/basecomponent';
// Nota: i componenti *menu-based* (Menubar, Menu, TieredMenu, ContextMenu, MegaMenu, Steps) usano "model" con label dentro gli item;
// li gestiamo più sotto in modo specifico.

// =========== Contract & Token ===========
export interface LabelTarget {
  set(key: string, value: string): void;
}
export const GEBO_MULILANGUAGE_TARGET = new InjectionToken<LabelTarget>('GEBO_MULILANGUAGE_TARGET');

// =========== Utilities ===========
export function safeSet(instance: any, key: string, value: any) {
  try { instance[key] = value; } catch { }
  // best-effort change detection (molti componenti P* hanno cd o changeDetector)
  try { instance.cd?.markForCheck?.(); } catch { }
  try { instance.changeDetector?.markForCheck?.(); } catch { }
}

export class LabelTargetParent implements LabelTarget {
  constructor(private applied?: any) {

  }
  set(key: string, value: string) {
    if (!this.applied) return;
    safeSet(this.applied, key, value);
  }
}
// =========== Button ===========
@Directive({
  selector: 'p-button[gebo-ai-label]',
  providers: [{ provide: GEBO_MULILANGUAGE_TARGET, useExisting: PButtonLabelTarget }],
  standalone: false
})
export class PButtonLabelTarget extends LabelTargetParent {
  constructor(@Optional() @Host() private btn: Button) {
    super(btn);
  }
}

// =========== Fieldset ===========
@Directive({
  selector: 'p-fieldset[gebo-ai-label]',
  providers: [{ provide: GEBO_MULILANGUAGE_TARGET, useExisting: PFieldSetLabelTarget }],
  standalone: false
})
export class PFieldSetLabelTarget extends LabelTargetParent {
  constructor(@Optional() @Host() private fs: Fieldset) {
    super(fs);

  }

}

// =========== Panel ===========
@Directive({
  selector: 'p-panel[gebo-ai-label]',
  providers: [{ provide: GEBO_MULILANGUAGE_TARGET, useExisting: PPanelLabelTarget }],
  standalone: false
})
export class PPanelLabelTarget extends LabelTargetParent {
  constructor(@Optional() @Self() private pnl: Panel) { super(pnl); }
  override set(key: string, value: string): void {
    super.set(key,value);
    if (key==="header") {
      super.set("_header",value);
    }
  }
}

// =========== Dialog ===========
@Directive({
  selector: 'p-dialog[gebo-ai-label]',
  providers: [{ provide: GEBO_MULILANGUAGE_TARGET, useExisting: PDialogLabelTarget }],
  standalone: false
})
export class PDialogLabelTarget extends LabelTargetParent {
  constructor(@Optional() @Host() private dlg: Dialog) { super(dlg); }

}

// =========== Sidebar ===========
@Directive({
  selector: 'p-sidebar[gebo-ai-label]',
  providers: [{ provide: GEBO_MULILANGUAGE_TARGET, useExisting: PSidebarLabelTarget }],
  standalone: false
})
export class PSidebarLabelTarget extends LabelTargetParent {
  constructor(@Optional() @Host() private sb: Sidebar) {
    super(sb);
  }
}

// =========== Card ===========
@Directive({
  selector: 'p-card[gebo-ai-label]',
  providers: [{ provide: GEBO_MULILANGUAGE_TARGET, useExisting: PCardLabelTarget }],
  standalone: false
})
export class PCardLabelTarget extends LabelTargetParent {
  constructor(@Optional() @Host() private card: Card) {
    super(card);
  }
}

// =========== TabView / TabPanel ===========
@Directive({
  selector: 'p-tabpanel[gebo-ai-label]',
  providers: [{ provide: GEBO_MULILANGUAGE_TARGET, useExisting: PTabPanelLabelTarget }],
  standalone: false
})
export class PTabPanelLabelTarget extends LabelTargetParent {
  constructor(@Optional() @Self() private tp: TabPanel) {
    super(tp);
  }

}

// =========== Accordion / AccordionTab ===========
@Directive({
  selector: 'p-accordiontab[gebo-ai-label]',
  providers: [{ provide: GEBO_MULILANGUAGE_TARGET, useExisting: PAccordionTabLabelTarget }],
  standalone: false
})
export class PAccordionTabLabelTarget extends LabelTargetParent {
  constructor(@Optional() @Host() private tab: AccordionTab) {
    super(tab);
  }

}

// =========== Toolbar ===========
@Directive({
  selector: 'p-toolbar[gebo-ai-label]',
  providers: [{ provide: GEBO_MULILANGUAGE_TARGET, useExisting: PToolbarLabelTarget }],
  standalone: false
})
export class PToolbarLabelTarget extends LabelTargetParent {
  constructor(@Optional() @Host() private tb: Toolbar) {
    super(tb);
  }

}

// =========== Badge / Tag / Chip ===========
@Directive({
  selector: 'p-badge[gebo-ai-label]',
  providers: [{ provide: GEBO_MULILANGUAGE_TARGET, useExisting: PBadgeLabelTarget }],
  standalone: false
})
export class PBadgeLabelTarget extends LabelTargetParent {
  constructor(@Optional() @Host() private b: Badge) {
    super(b);
  }

}

@Directive({
  selector: 'p-tag[gebo-ai-label]',
  providers: [{ provide: GEBO_MULILANGUAGE_TARGET, useExisting: PTagLabelTarget }],
  standalone: false
})
export class PTagLabelTarget extends LabelTargetParent {
  constructor(@Optional() @Host() private t: Tag) {
    super(t);
  }

}

@Directive({
  selector: 'p-chip[gebo-ai-label]',
  providers: [{ provide: GEBO_MULILANGUAGE_TARGET, useExisting: PChipLabelTarget }],
  standalone: false
})
export class PChipLabelTarget extends LabelTargetParent {
  constructor(@Optional() @Host() private c: Chip) {
    super(c);
  }
}

// =========== Checkbox / Radio / Toggle ===========
@Directive({
  selector: 'p-checkbox[gebo-ai-label]',
  providers: [{ provide: GEBO_MULILANGUAGE_TARGET, useExisting: PCheckboxLabelTarget }],
  standalone: false
})
export class PCheckboxLabelTarget extends LabelTargetParent {
  constructor(@Optional() @Host() private ckb: Checkbox) {
    super(ckb);
  }

}

@Directive({
  selector: 'p-radiobutton[gebo-ai-label]',
  providers: [{ provide: GEBO_MULILANGUAGE_TARGET, useExisting: PRadioLabelTarget }],
  standalone: false
})
export class PRadioLabelTarget extends LabelTargetParent {
  constructor(@Optional() @Host() private rb: RadioButton) {
    super(rb);
  }

}

@Directive({
  selector: 'p-togglebutton[gebo-ai-label]',
  providers: [{ provide: GEBO_MULILANGUAGE_TARGET, useExisting: PToggleLabelTarget }],
  standalone: false
})
export class PToggleLabelTarget extends LabelTargetParent {
  constructor(@Optional() @Host() private tb: ToggleButton) {
    super(tb);
  }
}

// =========== Input* & Selectors ===========
@Directive({
  selector: 'p-inputtext[gebo-ai-label], input[pInputText][gebo-ai-label]',
  providers: [{ provide: GEBO_MULILANGUAGE_TARGET, useExisting: PInputTextLabelTarget }],
  standalone: false
})
export class PInputTextLabelTarget extends LabelTargetParent {
  constructor(@Optional() @Host() private it: InputText) {
    super(it);
  }

}

@Directive({
  selector: 'p-inputtextarea[gebo-ai-label], textarea[pInputTextarea][gebo-ai-label]',
  providers: [{ provide: GEBO_MULILANGUAGE_TARGET, useExisting: PInputTextareaLabelTarget }],
  standalone: false
})
export class PInputTextareaLabelTarget extends LabelTargetParent {
  constructor(@Optional() @Host() private ta: InputTextarea) {
    super(ta);
  }

}

@Directive({
  selector: 'p-inputnumber[gebo-ai-label]',
  providers: [{ provide: GEBO_MULILANGUAGE_TARGET, useExisting: PInputNumberLabelTarget }],
  standalone: false
})
export class PInputNumberLabelTarget extends LabelTargetParent {
  constructor(@Optional() @Host() private inn: InputNumber) {
    super(inn);
  }

}

@Directive({
  selector: 'p-dropdown[gebo-ai-label]',
  providers: [{ provide: GEBO_MULILANGUAGE_TARGET, useExisting: PDropdownLabelTarget }],
  standalone: false
})
export class PDropdownLabelTarget extends LabelTargetParent {
  constructor(@Optional() @Host() private dd: Dropdown) {
    super(dd);
  }

}

@Directive({
  selector: 'p-multiselect[gebo-ai-label]',
  providers: [{ provide: GEBO_MULILANGUAGE_TARGET, useExisting: PMultiSelectLabelTarget }],
  standalone: false
})
export class PMultiSelectLabelTarget extends LabelTargetParent {
  constructor(@Optional() @Host() private ms: MultiSelect) {
    super(ms);
  }

}

@Directive({
  selector: 'p-listbox[gebo-ai-label]',
  providers: [{ provide: GEBO_MULILANGUAGE_TARGET, useExisting: PListboxLabelTarget }],
  standalone: false
})
export class PListboxLabelTarget extends LabelTargetParent {
  constructor(@Optional() @Host() private lb: Listbox) {
    super(lb);
  }

}

// =========== Calendar ===========
@Directive({
  selector: 'p-calendar[gebo-ai-label]',
  providers: [{ provide: GEBO_MULILANGUAGE_TARGET, useExisting: PCalendarLabelTarget }],
  standalone: false
})
export class PCalendarLabelTarget extends LabelTargetParent {
  constructor(@Optional() @Host() private cal: Calendar) {
    super(cal);
  }

}

// =========== Slider / Progress ===========
@Directive({
  selector: 'p-slider[gebo-ai-label]',
  providers: [{ provide: GEBO_MULILANGUAGE_TARGET, useExisting: PSliderLabelTarget }],
  standalone: false
})
export class PSliderLabelTarget extends LabelTargetParent {
  constructor(@Optional() @Host() private sl: Slider) {
    super(sl);
  }

}

@Directive({
  selector: 'p-progressbar[gebo-ai-label]',
  providers: [{ provide: GEBO_MULILANGUAGE_TARGET, useExisting: PProgressBarLabelTarget }],
  standalone: false
})
export class PProgressBarLabelTarget extends LabelTargetParent {
  constructor(@Optional() @Host() private pb: ProgressBar) {
    super(pb);
  }

}

// =========== Steps / BreadCrumb / Galleria (model-based) ===========
@Directive({
  selector: 'p-steps[gebo-ai-label]',
  providers: [{ provide: GEBO_MULILANGUAGE_TARGET, useExisting: PStepsLabelTarget }],
  standalone: false
})
export class PStepsLabelTarget extends LabelTargetParent {
  constructor(@Optional() @Host() private st: Steps) {
    super(st);
  }

}


@Directive({
  selector: 'p-galleria[gebo-ai-label]',
  providers: [{ provide: GEBO_MULILANGUAGE_TARGET, useExisting: PGalleriaLabelTarget }],
  standalone: false
})
export class PGalleriaLabelTarget extends LabelTargetParent {
  constructor(@Optional() @Host() private gal: Galleria) {
    super(gal);
  }

}


