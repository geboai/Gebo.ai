import { Component, Inject, Input, OnChanges, OnDestroy, OnInit, SimpleChanges } from "@angular/core";
import { BASE_PATH, LLMGeneratedResource } from "@Gebo.ai/gebo-ai-rest-api";
import { getAuthHeader } from "../../infrastructure/gebo-credentials";
import { EnrichedDocumentReferenceViewRetrieveService, EnrichedLLMGeneratedResource } from "../content-viewer/enriched-document-reference-view.service";

/**
 * Renders a single LLM generated resource (e.g. an image produced by the image
 * generation pipeline route). Image resources are shown inline; any other kind of
 * generated file falls back to a download link.
 *
 * The content is fetched as an authenticated blob (the backend serve endpoint
 * requires the bearer token) and exposed as an object URL.
 */
@Component({
    templateUrl: "llm-generated-document-ref.component.html",
    selector: "llm-generated-document-ref",
    standalone: false
})
export class GeboAIGeneratedDocumentRefComponent implements OnInit, OnChanges, OnDestroy {
    private static readonly IMAGE_EXTENSIONS = [".png", ".jpg", ".jpeg", ".gif", ".webp", ".bmp", ".svg", ".avif"];
    @Input() ref?: LLMGeneratedResource;
    protected extendedRef?: EnrichedLLMGeneratedResource;
    protected objectUrl?: string;
    protected isImage = false;
    protected failed = false;
    constructor(@Inject(BASE_PATH) private basePath: string,
        private enrichedDocumentReferenceViewService: EnrichedDocumentReferenceViewRetrieveService) {

    }
    ngOnInit(): void {

    }
    async ngOnChanges(changes: SimpleChanges) {
        if (this.ref && changes["ref"]) {
            this.extendedRef = await this.enrichedDocumentReferenceViewService.enrichLLMGeneratedResource(this.ref);
            this.isImage = this.detectImage(this.ref);
            await this.loadContent();
        }
    }

    ngOnDestroy(): void {
        this.revoke();
    }

    private detectImage(ref: LLMGeneratedResource): boolean {
        const contentType = (ref.contentType || "").toLowerCase();
        if (contentType.startsWith("image")) {
            return true;
        }
        const extension = (ref.extension || "").toLowerCase();
        return GeboAIGeneratedDocumentRefComponent.IMAGE_EXTENSIONS.indexOf(extension) >= 0;
    }

    private contentUrl(ref: LLMGeneratedResource): string {
        return `${this.basePath}/api/users/GeboLLMGeneratedResourceController/serveLLMGeneratedContent/`
            + `${encodeURIComponent(ref.userContextCode)}/${encodeURIComponent(ref.code || "")}`;
    }

    private async loadContent() {
        this.revoke();
        this.failed = false;
        if (!this.ref || !this.ref.code || !this.ref.userContextCode) {
            return;
        }
        try {
            const response = await fetch(this.contentUrl(this.ref), { headers: getAuthHeader() });
            if (!response.ok) {
                this.failed = true;
                return;
            }
            const blob = await response.blob();
            this.objectUrl = URL.createObjectURL(blob);
        } catch (error) {
            console.error("Error loading generated resource content", error);
            this.failed = true;
        }
    }

    private revoke() {
        if (this.objectUrl) {
            URL.revokeObjectURL(this.objectUrl);
            this.objectUrl = undefined;
        }
    }

}
