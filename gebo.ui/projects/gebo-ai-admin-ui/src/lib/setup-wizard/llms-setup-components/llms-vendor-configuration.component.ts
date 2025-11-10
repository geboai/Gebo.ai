import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from "@angular/core";
import { GBaseModelConfig, GeboFastLlmsSetupControllerService, LLMExistingConfiguration, LLMSModelsPresets, LLMSSetupConfiguration } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboActionPerformedType, GeboActionType, GeboAITranslationService, GeboUIActionRoutingService } from "@Gebo.ai/reusable-ui";
@Component({
    selector: "gebo-ai-llms-vendor-configuration",
    templateUrl: "llms-vendor-configuration.component.html",
    standalone: false,
    providers: [{ provide: GEBO_AI_MODULE, useValue: "GeboSetupWizardsModule", multi: false }, { provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("GeboAILLMSVendorConfiguration"), multi: false }]
})
export class GeboAILLMSVendorConfiguration implements OnInit, OnChanges {
    
    protected openModalCreateModel:boolean=false;
    @Input() vendorConfiguration?: LLMSSetupConfiguration;
    @Output() vendorConfigurationChanged: EventEmitter<LLMSSetupConfiguration> = new EventEmitter();
    protected loading: boolean = false;
    constructor(private geboTranslationService: GeboAITranslationService,
        private setupService: GeboFastLlmsSetupControllerService,
        private geboUIRouterService: GeboUIActionRoutingService) {

    }
    ngOnInit(): void {

    }
    ngOnChanges(changes: SimpleChanges): void {
        if (changes["vendorConfiguration"] && this.vendorConfiguration) {
            //this.vendorConfiguration.parentModel.vendorId
        }
    }
    openModal() {
        this.openModalCreateModel=true;
    }
    doModify(configuration: LLMExistingConfiguration) {
        const objectToOpen: GBaseModelConfig = {
            code: configuration.existingModelConfig.code
        };
        if (configuration.existingModelConfig?.className)
            this.geboUIRouterService.routeEvent({
                actionType: GeboActionType.OPEN,
                context: {},
                contextType: "GeboAILLMSVendorConfiguration",
                targetType: configuration.existingModelConfig.className,
                target: objectToOpen,
                onActionPerformed: (event) => {
                    this.loading = true;
                    this.setupService.getActualLLMSConfiguration().subscribe({
                        next: (updatedSetup) => {
                            const data = updatedSetup.configurations?.find(x => x.parentModel.vendorId === this.vendorConfiguration?.parentModel.vendorId);
                            if (data) {
                                this.vendorConfigurationChanged.emit(data);
                                this.vendorConfiguration = data;
                            }
                        },
                        complete: () => {
                            this.loading = false;
                        }
                    });
                }
            })
    }
}