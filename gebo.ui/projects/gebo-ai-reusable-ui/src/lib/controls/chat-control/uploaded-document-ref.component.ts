import { Component, Input, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { UserUploadedContent } from "@Gebo.ai/gebo-ai-rest-api";
import { EnrichedDocumentReferenceViewRetrieveService, EnrichedUserUploadedContentView } from "../content-viewer/enriched-document-reference-view.service";
@Component({
    templateUrl: "uploaded-document-ref.component.html",
    selector: "uploaded-document-ref",
    standalone: false
})
export class GeboAIUploadedDocumentRefComponent implements OnInit, OnChanges {
    @Input() public ref?: UserUploadedContent;
    protected extendedRef?: EnrichedUserUploadedContentView;
    constructor(private enrichedDocumentReferenceViewService: EnrichedDocumentReferenceViewRetrieveService) {

    }
    ngOnInit(): void {

    }
    async ngOnChanges(changes: SimpleChanges) {
        if (changes["ref"] && this.ref) {
            this.extendedRef = await this.enrichedDocumentReferenceViewService.enrichUserUploadedContent(this.ref);
        }
    }
}