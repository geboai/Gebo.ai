import { Component, Input, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { LLMGeneratedResource } from "@Gebo.ai/gebo-ai-rest-api";
import { EnrichedDocumentReferenceViewRetrieveService } from "../content-viewer/enriched-document-reference-view.service";

@Component({
    templateUrl: "llm-generated-document-ref.component.html",
    selector: "llm-generated-document-ref",
    standalone: false
})
export class GeboAIGeneratedDocumentRefComponent implements OnInit, OnChanges {
    @Input() ref?: LLMGeneratedResource;
    protected extendedRef?: LLMGeneratedResource;
    constructor(private enrichedDocumentReferenceViewService: EnrichedDocumentReferenceViewRetrieveService) {

    }
    ngOnInit(): void {

    }
    async ngOnChanges(changes: SimpleChanges) {
        if (this.ref && changes["ref"]) {
            this.extendedRef = await this.enrichedDocumentReferenceViewService.enrichLLMGeneratedResource(this.ref);
        }
    }

}