import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { DeepSearchDocumentAnalisysResultStep, DeepSearchRequest, DeepSearchResponse, GResponseDocumentRef } from "@Gebo.ai/gebo-ai-rest-api";
import { GeboAIStreamDeepSearchService } from "./stream-deep-search.service";
import { IGeboChatMessage } from "../../services/base-streaming.service";
@Component({
    selector: "gebo-ai-deep-search-component",
    templateUrl: "deep-search.component.html",
    standalone: false
})
export class GeboAIDeepSearchComponent implements OnInit, OnChanges {
    @Input() currentDeepSearchRequest?: DeepSearchRequest;
    @Input() mode: "full-ui" | "progress-only" = "full-ui";
    @Output() deepSearchResponseReceived: EventEmitter<DeepSearchResponse> = new EventEmitter();
    protected streamingResponse: boolean = false;
    protected get loading(): boolean {
        return this.streamingResponse === true;
    }
    protected formGroup: FormGroup = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        query: new FormControl(),
        knowledgeBases: new FormControl()

    });
    protected deepSearchResponse?: DeepSearchResponse;
    protected analisysStep?: {
        documentRef?: GResponseDocumentRef,
        analisysPortion?: string
    };
    constructor(private deepSearchStreamService: GeboAIStreamDeepSearchService) {

    }
    ngOnInit(): void {

    }
    private onMessage(msg: IGeboChatMessage | string) {
        if (typeof msg === "string") {

        } else {
            switch(msg.contentObjectType) {
                case "DeepSearchStep": {
                    this.analisysStep=msg.content;
                }break;
                case "DeepSearchResponse": {
                    this.deepSearchResponse=msg.content;
                    this.deepSearchResponseReceived.emit(this.deepSearchResponse);
                }break;
                case "GUserMessage": {

                }break;
            }
            if (msg.lastMessage === true) {
                this.streamingResponse = false;
            }
        }
    }
    onError(err: any) {
        this.streamingResponse = false;
    }
    ngOnChanges(changes: SimpleChanges): void {
        if (changes["currentDeepSearchRequest"] && this.currentDeepSearchRequest) {
            this.formGroup.patchValue(this.currentDeepSearchRequest);
            if (this.mode === "progress-only") {
                this.doStreamDeepSearch();
            }
        }
    }
    protected doStreamDeepSearch(): void {
        const data: DeepSearchRequest = this.formGroup.value;
        this.deepSearchStreamService.streamDeepSearch(data, (msg: IGeboChatMessage | string) => {
            this.onMessage(msg);
        }, (err) => {
            this.onError(err);
        });
    }

}