import { Component, Input, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { DeepSearchDataSourceDocumentResult, DeepSearchDocumentAnalisysResultStep, DeepSearchResponse, GeboDeepSearchControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { EnrichedDocumentReferenceViewRetrieveService } from "@Gebo.ai/reusable-ui";
import { forkJoin, Observable } from "rxjs";

@Component({
    selector: "gebo-ai-deep-search-details",
    templateUrl: "deep-search-details.component.html",
    styleUrl:"deep-search-details.component.scss",
    standalone: false
})
export class GeboAIDeepSearchDetailsComponent implements OnInit, OnChanges {
    @Input() deepSearchRequestCode?: string;
    protected data: (DeepSearchDataSourceDocumentResult|DeepSearchDocumentAnalisysResultStep)[] = [];
    protected result?:DeepSearchResponse;
    protected loading: boolean = false;
    protected thereAreDetails:boolean=false;
    protected listIsShowed:boolean=false;
    constructor(private deepSearchService: GeboDeepSearchControllerService) {

    }
    ngOnInit(): void {

    }
    toggleShowedList() {
        this.listIsShowed=!this.listIsShowed;
        if (this.listIsShowed) {
            if (this.data.length===0 && this.deepSearchRequestCode) {
                this.loadData(this.deepSearchRequestCode);
            }
        }
    }
    loadData(code: string) {
        const ob = this.deepSearchService.getMyDeepSearchDataSourceDocumentResultsByRequestCode(code);
        const ob1 = this.deepSearchService.getMyDeepSearchesSteps(code);
        const obResponse=this.deepSearchService.getMyDeepSearchResponseByRequestCode(code);
        const observables: [Observable<DeepSearchDataSourceDocumentResult[]>, Observable<DeepSearchDocumentAnalisysResultStep[]>,Observable<DeepSearchResponse>] = [ob, ob1,obResponse];
        this.loading = true;
        forkJoin(observables).subscribe({
            next: (entries) => {
                const items: (DeepSearchDataSourceDocumentResult|DeepSearchDocumentAnalisysResultStep)[] = [];
                if (entries && entries[0]) {
                    entries[0].forEach(entry => {
                        items.push(entry);
                    });
                }
                if (entries && entries[1]) {
                    entries[1].forEach(entry => {
                        items.push(entry);
                    });
                }
                this.result=entries && entries[2]?entries[2]:undefined;
                this.data=items;
                this.thereAreDetails=this.data?.length?true:false;
            },
            complete: () => {
                this.loading = false;
            }
        })
    }
    checkDetailsPresent(code:string) {
        this.deepSearchService.getDeepSearchDocumentsCount(code).subscribe({
            next:(count)=>{
                this.thereAreDetails= count > 0;
            }
        })
    }
    ngOnChanges(changes: SimpleChanges): void {
        if (changes["deepSearchRequestCode"] && this.deepSearchRequestCode) {
            this.checkDetailsPresent(this.deepSearchRequestCode);
        }
    }

}