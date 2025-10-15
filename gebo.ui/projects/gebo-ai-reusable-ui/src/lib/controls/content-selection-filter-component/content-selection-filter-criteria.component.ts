import { Component, forwardRef, Input } from "@angular/core";
import { FormGroup } from "@angular/forms";
import { GContentSelectionFilterCriteria, IngestionFileType } from "@Gebo.ai/gebo-ai-rest-api";
import { GEBO_AI_FIELD_HOST, GeboAIFieldHost } from "../field-host-component-iface/field-host-component-iface";
const entityName: string = "SelectionFilterCriteria";
@Component({
    selector: "gebo-ai-selection-filter-criteria-component",
    templateUrl: "content-selection-filter-criteria.component.html",
    standalone: false,
    providers: [{
        provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAISelectionFilterCriteriaComponent),
        multi: true
    }]
})
export class GeboAISelectionFilterCriteriaComponent implements GeboAIFieldHost {

    @Input() fg?: FormGroup;
    @Input() fileTypes: { code: string, description: string }[] = [];
    protected filterFormCriterias: { code?: string, description: string }[] = [
        { code: undefined, description: "No operator" },
        { code: GContentSelectionFilterCriteria.NameFilterCriteriaEnum.CONTAINS, description: "Contains" },
        { code: GContentSelectionFilterCriteria.NameFilterCriteriaEnum.ENDSWITH, description: "Ends with" },
        { code: GContentSelectionFilterCriteria.NameFilterCriteriaEnum.STARTSWITH, description: "Starts with" }];
    getEntityName(): string {
        return entityName;
    }
}