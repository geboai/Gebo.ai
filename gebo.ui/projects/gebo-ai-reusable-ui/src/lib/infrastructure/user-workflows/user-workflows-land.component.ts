import { Component, Inject, OnInit } from "@angular/core";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../../controls/field-host-component-iface/field-host-component-iface";
import { StartWorkflowData, UserWorkflowsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { ActivatedRoute } from "@angular/router";
import { FormControl, FormGroup } from "@angular/forms";


@Component({
    selector: 'gebo-ai-user-land-workflows',
    templateUrl: 'user-workflows-land.component.html',
    standalone: false,
    providers: [{
        provide: GEBO_AI_MODULE, useValue: "GeboAIUserWorkflowsModule", multi: false
    }, {
        provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("GeboAIUserWorkflowsLandComponent")
    }]
})
export class GeboAIUserWorkflowsLandComponent implements OnInit {
    protected disabledWorkFlows: boolean = true;
    protected chooseWorkflow: boolean = false;
    protected formGroup: FormGroup = new FormGroup({ ticket: new FormControl(), email: new FormControl(), password: new FormControl(), confirmPassword: new FormControl() });
    constructor(private service: UserWorkflowsControllerService, private activatedRoute: ActivatedRoute) {

    }

    ngOnInit(): void {
        this.service.getUserWorkflowsConfig().subscribe((value) => {
            this.disabledWorkFlows = !(value?.activationWorkflowEnabled === true || value?.forgotPasswordWorkflowEnabled === true);

        });
    }
    doChangePassword() {
        const data = this.formGroup.value;
        this.service.userChangePasswordWithTicket(data).subscribe({
            next: (value) => {
            },
            error: (error) => {
            },
            complete: () => {
            }
        });
    }




}