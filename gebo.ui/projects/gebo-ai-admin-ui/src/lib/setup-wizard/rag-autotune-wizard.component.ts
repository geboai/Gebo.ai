import { Component, Injectable, OnChanges, OnInit } from "@angular/core";
import { AutotuneVectorStoreInfo, GeboAdminRagAutotuneControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { AbstractStatusService, BaseWizardSectionComponent, SetupWizardComunicationService } from "@Gebo.ai/reusable-ui";
import { map, Observable, of } from "rxjs";
@Injectable({ providedIn: "root" })
export class RagAutotuneStatusService extends AbstractStatusService {
    constructor(private ragAutoTuneService:GeboAdminRagAutotuneControllerService) {
        super();
    }
    public override getBooleanStatus(): Observable<boolean> {
        return this.ragAutoTuneService.getLatestComputedVectorStores().pipe(map(recvd=>{
            return recvd?.length>0 && recvd.filter(x=>x.autotuneResult?true:false).length===recvd.length;
        }));
    }
}
@Component({
    selector:"gebo-ai-rag-autotune-component",
    templateUrl:"rag-autotune-wizard.component.html",
    standalone: false
})
export class GeboAIRagAutotuneWizardComponent extends BaseWizardSectionComponent implements OnInit,OnChanges{
    protected autotunedVectorStores:AutotuneVectorStoreInfo[]=[];
    constructor( setupWizardComunicationService: SetupWizardComunicationService,
        private ragAutoTuneService:GeboAdminRagAutotuneControllerService) {
        super(setupWizardComunicationService);
    }
    public override reloadData(): void {
        this.loading=true;
        this.ragAutoTuneService.getLatestComputedVectorStores().subscribe({
            next:(data)=>{
                this.autotunedVectorStores=data;
            },
            complete:()=>{
                this.loading=false;
            }
        });
    }
    
}