import { Component, Inject, OnInit } from "@angular/core";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../../controls/field-host-component-iface/field-host-component-iface";
import { StartWorkflowData, UserWorkflowsControllerService, UserWorkFlowStartResponse } from "@Gebo.ai/gebo-ai-rest-api";
import { ActivatedRoute } from "@angular/router";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { ToastMessageOptions } from "primeng/api";
import { getAuth, resetAuth } from "../gebo-credentials";


@Component({
    selector: 'gebo-ai-user-start-workflows',
    templateUrl: 'user-workflows-start.component.html',
    standalone: false,
    providers: [{
        provide: GEBO_AI_MODULE, useValue: "GeboAIUserWorkflowsModule", multi: false
    }, {
        provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("GeboAIUserWorkflowsStartComponent")
    }]
})
export class GeboAIUserWorkflowsStartComponent implements OnInit {
    protected disabledWorkFlows: boolean = true;
    protected loading: boolean = false;
    protected workflowActivated: boolean = false;
    protected workflowError: boolean = false;
    protected formGroup: FormGroup = new FormGroup({
        email: new FormControl('', [Validators.required, Validators.email]),
        type: new FormControl('', [Validators.required])
    });
    protected chooseWorkflow: boolean = false;
    protected userMessages: ToastMessageOptions[] = [];
    protected processStarted: boolean = false;
    protected typeOptions = [
        { description: 'User activation', code: StartWorkflowData.TypeEnum.ACTIVATION },
        { description: 'Forgot password', code: StartWorkflowData.TypeEnum.FORGOTPASSWORD }
    ];
    protected stepResponse?: UserWorkFlowStartResponse;

    constructor(private service: UserWorkflowsControllerService, private activatedRoute: ActivatedRoute) {

    }

    ngOnInit(): void {
        const auth = getAuth();
        if (auth) {
            resetAuth();
            window.location.reload();
        }
        this.service.getUserWorkflowsConfig().subscribe((value) => {
            this.disabledWorkFlows = !(value?.activationWorkflowEnabled === true || value?.forgotPasswordWorkflowEnabled === true);
            this.chooseWorkflow = value?.activationWorkflowEnabled === true && value?.forgotPasswordWorkflowEnabled === true;

            const typeParam = this.activatedRoute.snapshot.queryParamMap.get('type');
            if (typeParam) {
                this.formGroup.controls['type'].setValue(typeParam);
                this.chooseWorkflow = false;
            } else if (!this.chooseWorkflow) {
                if (value?.activationWorkflowEnabled === true) {
                    this.formGroup.controls['type'].setValue(StartWorkflowData.TypeEnum.ACTIVATION);
                }
                if (value?.forgotPasswordWorkflowEnabled === true) {
                    this.formGroup.controls['type'].setValue(StartWorkflowData.TypeEnum.FORGOTPASSWORD);
                }
            }
        });
    }
    protected doStartWorkflow(workflow?: string) {
        if (this.formGroup.invalid) {
            return;
        }
        const data = this.formGroup.value;
        this.loading = true;
        this.workflowActivated = false;
        this.workflowError = false;
        this.userMessages = [];
        this.service.startUserWorkflow(data).subscribe({
            next: (value) => {
                this.workflowActivated = true;
                this.stepResponse = value;
                this.userMessages = [{
                    severity: 'success',
                    summary: 'Success',
                    detail: 'Workflow started successfully. Please check your email.',
                    life: 20000
                }];
                this.processStarted = true;
            },
            error: (error) => {
                this.workflowError = true;
                this.userMessages = [{
                    severity: 'error',
                    summary: 'Error',
                    detail: 'Failed to start workflow. Please check your inputs and try again.'
                }];
            },
            complete: () => {
                this.loading = false;
            }
        });
    }
}