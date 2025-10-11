import { Component, Input } from "@angular/core";
import { FormGroup } from "@angular/forms";
import { GContentSelectionFilterCriteria, IngestionFileType } from "@Gebo.ai/gebo-ai-rest-api";

@Component({
    selector: "gebo-ai-selection-filter-criteria-component",
    templateUrl: "content-selection-filter-criteria.component.html",
    standalone: false
})
export class GeboAISelectionFilterCriteriaComponent {
    @Input() formGroup?: FormGroup;
    @Input() fileTypes: IngestionFileType[] = [];
    protected filterFormCriterias: { code: string, description: string }[] = [
        { code: GContentSelectionFilterCriteria.NameFilterCriteriaEnum.CONTAINS, description: "Contains" },
        { code: GContentSelectionFilterCriteria.NameFilterCriteriaEnum.ENDSWITH, description: "Ends with" },
        { code: GContentSelectionFilterCriteria.NameFilterCriteriaEnum.STARTSWITH, description: "Starts with" }]
}