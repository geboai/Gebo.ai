import { Component, forwardRef, Injector, OnInit } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { GenericOpenAIAPIRankerModelChoice , GenericOpenAIAPIRankerModelConfig, GenericOpenAiRankerModelsConfigurationControllerService, GenericOpenAIRankerModelTypeConfig, SecretInfo, SecretsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboFormGroupsService, GeboUIActionRequest, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { map, Observable, of } from "rxjs";
import { newSecretActionRequest } from "../utils/gebo-ai-create-secret-action-request-factory";
import { isValidUrl } from "../utils/url-ok";

@Component({
    templateUrl: "gebo-ai-generic-openai-api-ranker-admin.component.html",
    selector: "gebo-ai-generic-openai-api-ranker-component",
    standalone: false,
    providers: [ { provide: GEBO_AI_MODULE, useValue: "GeboAiLargeLanguageModelsModule", multi: false }, 
        { provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIGenericOpenaAIAPiRankerAdminComponent),
        multi: false
    }]
})
export class GeboAIGenericOpenaAIAPiRankerAdminComponent extends BaseEntityEditingComponent<GenericOpenAIAPIRankerModelConfig> implements OnInit{
    protected override entityName: string = "GenericOpenAIAPIRankerModelConfig";
    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        userModified: new FormControl(),
        userCreated: new FormControl(),
        dateModified: new FormControl(),
        dateCreated: new FormControl(),
        modelTypeCode: new FormControl(),
        defaultModel: new FormControl(),
        apiSecretCode: new FormControl(),
        choosedModel: new FormControl(),
        baseUrl: new FormControl(),
        contextLength: new FormControl(),
        maxDocumentsPerRequest: new FormControl(),
        maxDocumentTokens: new FormControl(),
        responseReserveTokens: new FormControl(),
        fullServiceUrl: new FormControl(),
        relativeServiceUrl: new FormControl()
    });
    /**
         * Tracks previous form values for comparison to detect changes
         */
    private oldValue: any = {};

    /**
     * Stores available model choices for the selected API configuration
     */
    modelChoicesData: GenericOpenAIAPIRankerModelChoice[] = [];

    /**
     * Observable for retrieving available identity/secret information
     */
    public identitiesObservable?: Observable<SecretInfo[]> = of([]);

    /**
     * Action request for creating a new secret
     */
    public newSecretAction?: GeboUIActionRequest;



    /**
     * Available model types for OpenAI API configurations
     */
    public modelTypes?: GenericOpenAIRankerModelTypeConfig[];

    /**
     * Currently selected model type
     */
    public modelType?: GenericOpenAIRankerModelTypeConfig;
    constructor(injector: Injector,
        geboFormGroupsService: GeboFormGroupsService,
        confirmationService: ConfirmationService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        outputForwardingService: GeboUIOutputForwardingService,
        private genericOpenAIAPIRankerService: GenericOpenAiRankerModelsConfigurationControllerService,
        private secretControllerService: SecretsControllerService) {
        super(injector, geboFormGroupsService, confirmationService, geboUIActionRoutingService, outputForwardingService);
        this.manageOperationStatus = true;
        this.formGroup.valueChanges.subscribe(newValue => {
            if (!newValue.baseUrl && !newValue.apiSecretCode) {
                this.modelChoicesData = [];
            } else if ((newValue.baseUrl !== this.oldValue.baseUrl && isValidUrl(newValue.baseUrl)) || newValue.apiSecretCode !== this.oldValue.apiSecretCode) {
                this.loadModels(newValue);
            }
            this.oldValue = newValue;

        });
    }
    override ngOnInit(): void {
        super.ngOnInit();
        this.loadingRelatedBackend=true;
        this.genericOpenAIAPIRankerService.getGenericOpenAIRankerModelTypes().subscribe({
            next:(values)=>{
                this.modelTypes=values;
                if (this.entity?.modelTypeCode) {
                    this.modelType=this.modelTypes.find(x=>x.code===this.entity?.modelTypeCode);
                }
                if (this.modelType?.optionalAuthentication===true) {
                    this.formGroup.controls["apiSecretCode"].setValidators(Validators.required);
                }
                this.formGroup.updateValueAndValidity();
                if (this.entity)
                this.refreshProviderModel(this.entity);
            },
            complete:()=>{
                this.loadingRelatedBackend=false;
            }
        })
        
    }
    /**
         * Updates model provider information based on the selected model type
         * Sets up identities observable and configures baseUrl if necessary
         * @param actualValue The current model configuration
         */
    private refreshProviderModel(actualValue: GenericOpenAIAPIRankerModelConfig) {
        if (actualValue?.modelTypeCode && this.modelTypes) {
            this.modelType = this.modelTypes.find(x => x.code === actualValue?.modelTypeCode);
            if (this.modelType?.providerId) {
                this.identitiesObservable = this.secretControllerService.getSecretsByContextCode(this.modelType?.providerId);
                this.newSecretAction = newSecretActionRequest(this.modelType?.providerId, this.entityName, this.entity);
                if ((!this.formGroup.value?.baseUrl) && this.modelType?.baseUrl) {
                    this.formGroup.controls["baseUrl"].setValue(this.modelType?.baseUrl);
                }
            }
        }

    }

    /**
     * Loads available models from the OpenAI API using the provided configuration
     * @param newValue Object containing baseUrl and apiSecretCode used to authenticate with the API
     */
    private loadModels(newValue: { baseUrl?: string, apiSecretCode?: string }) {
        this.loadingRelatedBackend = true;
        this.genericOpenAIAPIRankerService.getGenericOpenAIAPIRankerModels(newValue).subscribe({
            next: (r) => {
                this.updateLastOperationStatus(r as any);
                this.modelChoicesData = r.result ? r.result : []
            },
            complete: () => {
                this.loadingRelatedBackend = false;
            }
        });
    }

    /**
     * Called when persistent data is loaded, refreshes provider model and loads available models
     * @param actualValue The loaded model configuration
     */
    protected override onLoadedPersistentData(actualValue: GenericOpenAIAPIRankerModelConfig): void {
        this.refreshProviderModel(actualValue);
        this.loadModels(actualValue);
    }

    /**
     * Called when creating a new entity, initializes provider model data
     * @param actualValue The new model configuration
     */
    protected override onNewData(actualValue: GenericOpenAIAPIRankerModelConfig): void {
        this.refreshProviderModel(actualValue);
    }

    override findByCode(code: string): Observable<GenericOpenAIAPIRankerModelConfig | null> {
        return this.genericOpenAIAPIRankerService.findGenericOpenAIAPIRankerModelConfigByCode(code);
    }
    override save(value: GenericOpenAIAPIRankerModelConfig): Observable<GenericOpenAIAPIRankerModelConfig> {
        return this.genericOpenAIAPIRankerService.updateGenericOpenAIAPIRankerModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }
    override insert(value: GenericOpenAIAPIRankerModelConfig): Observable<GenericOpenAIAPIRankerModelConfig> {
        return this.genericOpenAIAPIRankerService.insertGenericOpenAIAPIRankerModelConfig(value).pipe(map(result => {
            this.updateLastOperationStatus(result);
            if (!result?.result) return value;
            return result.result;
        }));
    }
    override delete(value: GenericOpenAIAPIRankerModelConfig): Observable<boolean> {
        return this.genericOpenAIAPIRankerService.deleteGenericOpenAIAPIRankerModelConfig(value).pipe(map(x=>{
            this.updateLastOperationStatus(x as any);
            return x.result===true;
        }))
    }
    override canBeDeleted(value: GenericOpenAIAPIRankerModelConfig): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({canBeDeleted:true,message:""});
    }

}