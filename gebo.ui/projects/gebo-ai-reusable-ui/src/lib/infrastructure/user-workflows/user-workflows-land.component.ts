import { Component, Inject, OnInit } from "@angular/core";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../../controls/field-host-component-iface/field-host-component-iface";
import { StartWorkflowData, UserWorkFlowChangePasswordResponse, UserWorkflowsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { ActivatedRoute, Router } from "@angular/router";
import { AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from "@angular/forms";
import { ToastMessageOptions } from "primeng/api";
import { getAuth, resetAuth } from "../gebo-credentials";

const PASSWORD_PATTERN = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&()[\]{}_.+=~\-#:;,\/\\<>|?^*])[A-Za-z\d@$!%*?&()[\]{}_.+=~\-#:;,\/\\<>|?^*]{10,}$/;

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
    protected loading: boolean = false;
    protected success: boolean = false;
    protected error: boolean = false;
    protected userMessages: ToastMessageOptions[] = [];

    protected formGroup: FormGroup = new FormGroup({
        ticket: new FormControl('', [Validators.required]),
        email: new FormControl('', [Validators.required, Validators.email]),
        password: new FormControl('', [Validators.required, Validators.pattern(PASSWORD_PATTERN)]),
        confirmPassword: new FormControl('', [Validators.required])
    });
    protected stepResult?: UserWorkFlowChangePasswordResponse;

    constructor(private service: UserWorkflowsControllerService, private activatedRoute: ActivatedRoute, private router: Router) {
        const passwordMatchValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
            const group = control as FormGroup;
            const password = group.get('password')?.value;
            const confirmPassword = group.get('confirmPassword')?.value;
            return password === confirmPassword ? null : { passwordMismatch: true };
        };
        this.formGroup.addValidators(passwordMatchValidator);
    }

    ngOnInit(): void {
        const auth = getAuth();
        if (auth) {
            resetAuth();
            window.location.reload();
        }
        this.service.getUserWorkflowsConfig().subscribe((value) => {
            this.disabledWorkFlows = !(value?.activationWorkflowEnabled === true || value?.forgotPasswordWorkflowEnabled === true);
        });

        const ticketParam = this.activatedRoute.snapshot.queryParamMap.get('ticket') || this.activatedRoute.snapshot.paramMap.get('ticket');
        if (ticketParam) {
            this.formGroup.controls['ticket'].setValue(ticketParam);
        }
    }

    goToLogin() {
        this.router.navigate(['/ui/login']);
    }

    doChangePassword() {
        if (this.formGroup.invalid) {
            return;
        }
        const data = this.formGroup.value;
        this.loading = true;
        this.success = false;
        this.error = false;
        this.userMessages = [];
        this.service.userChangePasswordWithTicket(data).subscribe({
            next: (value) => {
                this.success = value?.ok === true;
                this.stepResult = value;
                if (this.success) {
                    this.userMessages = [{
                        id: "wkfSuccess",
                        severity: 'success',
                        summary: 'Success',
                        detail: 'Password changed successfully. You can now login.',
                        life: 20000
                    }];
                    setTimeout(() => {
                        this.goToLogin();
                    }, 4000);
                }
                if (value?.invalidAccountState === true) {
                    this.userMessages = [{
                        id: "invalidAccountState",
                        severity: 'error',
                        summary: 'Error',
                        detail: 'Failed to change password. Please check the e-mail or that your account exists.',
                        life: 20000
                    }];
                }
                if (value?.timeoutReached === true) {
                    this.userMessages = [{
                        id: "timeoutReached",
                        severity: 'error',
                        summary: 'Error',
                        detail: 'Repeat the procedure from the initial step, your ticket validity is expired.',
                        life: 20000
                    }];
                }
                if (value?.invalidToken === true) {
                    this.userMessages = [{
                        id: "invalidToken",
                        severity: 'error',
                        summary: 'Error',
                        detail: 'Try to repeat from the received e-mail to this point, it seems that the ticket is corrupt.',
                        life: 20000
                    }];
                }
            },
            error: (err) => {
                this.error = true;
                this.userMessages = [{
                    severity: 'error',
                    summary: 'Error',
                    detail: 'Failed to change password. Please check the requirements or request a new ticket.',
                    life: 20000
                }];
            },
            complete: () => {
                this.loading = false;
            }
        });
    }
}