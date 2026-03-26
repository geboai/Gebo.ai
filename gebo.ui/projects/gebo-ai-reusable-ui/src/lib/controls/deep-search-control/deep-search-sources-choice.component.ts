import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { FormControl, FormGroup, ValidatorFn } from '@angular/forms';
import { DeepSearchUISettings, GBaseObject, GeboChatControllerService, GeboDeepSearchControllerService, GeboRagChatControllerService } from '@Gebo.ai/gebo-ai-rest-api';
import { forkJoin, Observable } from 'rxjs';

export interface IChooseSources {
    deepSearchDataSources?: string[];
    knowledgeBases?: string[];
}

const atLeastAKnowledgeBaseOrSystemValidator: ValidatorFn = (ctrl) => {
    const fg: FormGroup = ctrl as FormGroup;
    const value: IChooseSources = fg.value;
    const hasKBS: boolean = (value?.knowledgeBases ? true : false) && ((value?.knowledgeBases?.length && value?.knowledgeBases?.length > 0) ? true : false);
    const hasSRC: boolean = (value?.deepSearchDataSources ? true : false) && ((value?.deepSearchDataSources?.length && value?.deepSearchDataSources?.length > 0) ? true : false);
    let out: any | null = null;
    if (!(hasKBS || hasSRC)) {
        out = {
            noSourceSelected: "noSrc"
        };
    }
    return out;
};

@Component({
    selector: 'gebo-ai-deep-search-sources-choice',
    templateUrl: './deep-search-sources-choice.component.html',
    standalone: false
})
export class GeboAIDeepSearchSoucesChoiceComponent implements OnInit, OnChanges {
    @Input() chatProfileCode?: string;

    @Input() nextRequestMode: "standard-chat" | "deep-search" = "standard-chat";
    @Input() programmaticStreaming: boolean = false;

    @Input() visible: boolean = false;
    @Output() visibleChange = new EventEmitter<boolean>();

    @Input() deepSearchDataSources?: string[];
    @Output() deepSearchDataSourcesChange = new EventEmitter<string[]>();

    @Input() knowledgeBases?: string[];
    @Output() knowledgeBasesChange = new EventEmitter<string[]>();

    @Output() onChoosed = new EventEmitter<IChooseSources>();
    @Output() onSkip = new EventEmitter<void>();

    protected choosableDataSources: GBaseObject[] = [];
    protected choosableKnowledgeBases: GBaseObject[] = [];
    protected loadingRelatedBackend: boolean = false;
    
    protected deepSearchUISettings: DeepSearchUISettings = {
        deepSearchUIAllowChooseSources: false,
        externalSourcesEnabled: false
    };

    protected chooseDeepSearchDataSourcesFormGroup: FormGroup = new FormGroup({
        deepSearchDataSources: new FormControl(),
        knowledgeBases: new FormControl()
    });

    constructor(
        private deepSearchControllerService: GeboDeepSearchControllerService,
        private ragChatService: GeboRagChatControllerService,
        private chatService: GeboChatControllerService
    ) {
        this.chooseDeepSearchDataSourcesFormGroup.setValidators(atLeastAKnowledgeBaseOrSystemValidator);
    }

    ngOnInit(): void {
    }

    ngOnChanges(changes: SimpleChanges): void {
        if (changes["nextRequestMode"] && this.nextRequestMode === "deep-search" && !this.programmaticStreaming) {
            this.chooseDataSources();
        }
    }

    private chooseSources(): Observable<[GBaseObject[], GBaseObject[], DeepSearchUISettings]> {
        const kbObservable: Observable<GBaseObject[]> = (this.chatProfileCode) ? this.ragChatService.getVisibleKnowledgeBasesByProfileCode(this.chatProfileCode) : this.chatService.getVisibleKnowledgeBases();
        return forkJoin([this.deepSearchControllerService.getDeepSearchDataSources(), kbObservable, this.deepSearchControllerService.getDeepSearchUISettings()]);
    }

    private chooseDataSources(): void {
        this.chooseDeepSearchDataSourcesFormGroup.reset();
        this.loadingRelatedBackend = true;
        this.chooseSources().subscribe({
            next: (dsList) => {
                this.choosableDataSources = dsList[0];
                this.choosableKnowledgeBases = dsList[1];
                if (dsList[2]) {
                    this.deepSearchUISettings = dsList[2];
                }
                const defaultSelection: IChooseSources = {
                    deepSearchDataSources: this.choosableDataSources ? this.choosableDataSources.map(x => x.code) as string[] : [],
                    knowledgeBases: this.choosableKnowledgeBases ? this.choosableKnowledgeBases.map(x => x.code) as string[] : []
                };
                if (this.deepSearchUISettings.externalSourcesEnabled !== true) {
                    defaultSelection.deepSearchDataSources = [];
                }
                
                this.chooseDeepSearchDataSourcesFormGroup.patchValue(defaultSelection);
                this.loadingRelatedBackend = false;
                let totalChoices: number = 0;
                if (this.choosableDataSources?.length) {
                    totalChoices += this.choosableDataSources.length;
                }
                if (this.choosableKnowledgeBases?.length) {
                    totalChoices += this.choosableKnowledgeBases.length;
                }
                //Choice UI has to be viewed only where options for data sources are more than one
                if (totalChoices > 1 && this.deepSearchUISettings.deepSearchUIAllowChooseSources === true) {
                    this.visible = true;
                    this.visibleChange.emit(true);
                } else {
                    this.doChoosedSources();
                }
            },
            complete: () => {
                this.loadingRelatedBackend = false;
            }
        });
    }

    protected doChoosedSources(): void {
        this.visible = false;
        this.visibleChange.emit(false);
        const choosedDataSourcesObject: IChooseSources = this.chooseDeepSearchDataSourcesFormGroup.value;
        const selectedDeepSearchDataSources = choosedDataSourcesObject?.deepSearchDataSources || [];
        const selectedKnowledgeBases = choosedDataSourcesObject?.knowledgeBases || [];
        
        this.deepSearchDataSourcesChange.emit(selectedDeepSearchDataSources);
        this.knowledgeBasesChange.emit(selectedKnowledgeBases);
        this.onChoosed.emit(choosedDataSourcesObject);
    }

    protected skipDeepSearchOnDataSourceChoice(): void {
        this.visible = false;
        this.visibleChange.emit(false);
        this.onSkip.emit();
    }
}
