import { Directive, Host, Optional, Self } from "@angular/core";
import { GEBO_MULILANGUAGE_TARGET, LabelTargetParent } from "./primeng-components-multilanguage-adapters.directive";
import { TabPanel, TabPanels } from 'primeng/tabs';
@Directive({
    selector: 'p-tab[gebo-ai-label]',
    providers: [{ provide: GEBO_MULILANGUAGE_TARGET, useExisting: POldTabPanelLabelTarget }],
    standalone: false
})
export class POldTabPanelLabelTarget extends LabelTargetParent {
    constructor(@Optional() @Self() private gal: TabPanel) {
        super(gal);
    }

}