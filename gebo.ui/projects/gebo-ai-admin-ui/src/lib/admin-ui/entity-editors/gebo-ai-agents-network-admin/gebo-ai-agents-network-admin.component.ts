import { Injector } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { GAgentsNetwork, GeboAgentsNetworkAdminControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { map, Observable, of } from "rxjs";

export class GeboAIAgentsNetworkAdminComponent extends BaseEntityEditingComponent<GAgentsNetwork> {
    protected override entityName: string="GAgentsNetwork";
    override formGroup: FormGroup<any>=new FormGroup({
        code: new FormControl(),
            description: new FormControl(),
            userModified: new FormControl(),
            userCreated: new FormControl(),
            dateModified: new FormControl(),
            dateCreated: new FormControl(),
            maxLoopIteration: new FormControl(),
            scenarioDescription: new FormControl(),
            agents: new FormControl(),
            readOnly: new FormControl(),
            defaultUserInteractionNetwork: new FormControl()
    });
    constructor(injector: Injector, geboFormGroupsService: GeboFormGroupsService, confirmationService: ConfirmationService, geboUIActionRoutingService: GeboUIActionRoutingService, outputForwardingService: GeboUIOutputForwardingService,private service:GeboAgentsNetworkAdminControllerService) {
        super(injector,geboFormGroupsService,confirmationService,geboUIActionRoutingService);
        this.manageOperationStatus=true;
    }
    override findByCode(code: string): Observable<GAgentsNetwork | null> {
       return this.service.getAgentsNetworkByCode(code);
    }
    override save(value: GAgentsNetwork): Observable<GAgentsNetwork> {
        return this.service.updateAgentsNetwork(value).pipe(map(_value=>{
            this.assignBackendMessages(_value.messages);
            return _value.result?_value.result:value;
        }));
    }
    override insert(value: GAgentsNetwork): Observable<GAgentsNetwork> {
        return this.service.insertAgentsNetwork(value).pipe(map(_value=>{
            this.assignBackendMessages(_value.messages);
            return _value.result?_value.result:value;
        }));
    }
    override delete(value: GAgentsNetwork): Observable<boolean> {
        return this.service.deleteAgentsNetwork(value).pipe(map(_value=>{
            this.assignBackendMessages(_value.messages);
            return true;
        }));
    }
    override canBeDeleted(value: GAgentsNetwork): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({"canBeDeleted":false,"message":""});
    }
    
}