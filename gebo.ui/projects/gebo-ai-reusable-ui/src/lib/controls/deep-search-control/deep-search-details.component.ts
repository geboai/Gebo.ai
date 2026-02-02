import { Component, Input, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { DeepSearchDataSourceDocumentResult, DeepSearchDocumentAnalisysResultStep, GeboDeepSearchControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { forkJoin, Observable } from "rxjs";
interface DeepSearchItem {
    dataSourceDescription: string;
    reference: string;
    url?: string;
    content: string;
    itemType:"data-source"|"knowledge-base"
}
@Component({
    selector: "gebo-ai-deep-search-details",
    templateUrl: "deep-search-details.component.html",
    standalone: false
})
export class GeboAIDeepSearchDetailsComponent implements OnInit, OnChanges {
    @Input() deepSearchRequestCode?: string;
    protected data: DeepSearchItem[] = [];
    protected loading: boolean = false;
    constructor(private deepSearchService: GeboDeepSearchControllerService) {

    }
    ngOnInit(): void {

    }
    loadData(code: string) {
        const ob = this.deepSearchService.getMyDeepSearchDataSourceDocumentResultsByRequestCode(code);
        const ob1 = this.deepSearchService.getMyDeepSearchesSteps(code);
        const observables: [Observable<DeepSearchDataSourceDocumentResult[]>, Observable<DeepSearchDocumentAnalisysResultStep[]>] = [ob, ob1];
        this.loading = true;
        forkJoin(observables).subscribe({
            next: (entries) => {
                const items: DeepSearchItem[] = [];
                if (entries && entries[0]) {
                    entries[0].forEach(entry => {
                        if (entry.analyzedResult) {
                            const item: DeepSearchItem = {
                                dataSourceDescription: entry.dataSourceDescription,
                                reference: entry.analyzedSearchResult.descriptiveText,
                                url: entry.analyzedSearchResult?.resultReference.uri,
                                content: entry.analyzedResult,
                                itemType:"data-source"
                            };
                            items.push(item);
                        }
                    });
                }
                if (entries && entries[1]) {
                    entries[1].forEach(entry => {
                        if (entry.fragment) {
                            const item: DeepSearchItem = {
                                dataSourceDescription: "Gebo.ai knowledge base",
                                reference: entry.documentCode,
                                url: undefined,
                                content: entry.fragment,
                                itemType:"knowledge-base"
                            };
                            items.push(item);
                        }
                    });
                }
            },
            complete: () => {
                this.loading = false;
            }
        })
    }
    ngOnChanges(changes: SimpleChanges): void {
        if (changes["deepSearchRequestCode"] && this.deepSearchRequestCode) {
            this.loadData(this.deepSearchRequestCode);
        }
    }

}