import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { GeboChatRequest } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../field-host-component-iface/field-host-component-iface";

@Component({
    selector: "gebo-ai-deep-search-component",
    templateUrl: "deep-search.component.html",
    styleUrls: ["./deep-search.component.scss"],
    standalone: false,
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIDeepSearchModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("GeboAIDeepSearchComponent"), multi: false }
    ]
})
export class GeboAIDeepSearchComponent implements OnInit, OnChanges {
    @Input() currentChatRequest?: GeboChatRequest;
    @Input() nextRequestMode: "standard-chat" | "deep-search" = "standard-chat";
    @Input() chatProfileCode?: string;
    @Output() skipDeepSearchEvent: EventEmitter<boolean> = new EventEmitter();

    protected selectedDeepSearchDataSources?: string[];
    protected selectedKnowledgeBases?: string[];
    protected programmaticStreaming: boolean = false;

    protected formGroup: FormGroup = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        query: new FormControl(),
        knowledgeBases: new FormControl(),
        deepSearchDataSources: new FormControl()
    });

    constructor() {}

    ngOnInit(): void {}

    ngOnChanges(changes: SimpleChanges): void {}

    protected onSourcesChoosed(): void {
        if (!this.currentChatRequest) {
            this.formGroup.controls["deepSearchDataSources"].setValue(this.selectedDeepSearchDataSources);
            this.formGroup.controls["knowledgeBases"].setValue(this.selectedKnowledgeBases);
        }
    }

    protected skipDeepSearchOnDataSourceChoice(): void {
        this.skipDeepSearchEvent.emit(true);
    }
}