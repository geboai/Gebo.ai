import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { ChangeUsernamePasswordData, GUserMessage, UsersAdminControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { GEBO_AI_FIELD_HOST, GEBO_AI_MODULE,GeboAITranslationService,fieldHostComponentName } from "@Gebo.ai/reusable-ui";
import { ConfirmationService, MessageService, ToastMessageOptions } from "primeng/api";



@Component({
    selector: "gebo-ai-change-user-password-component",
    templateUrl: "gebo-ai-change-user-password.component.html",
    standalone: false, providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIUsersGroupModule", multi: false },
        {
            provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("GeboAIChangeUserPasswordComponent"),
            multi: false
        }]
})
export class GeboAIChangeUserPasswordComponent implements OnInit, OnChanges {
    @Input() openWindow: boolean = false;
    @Input() username?: string;
    @Output() openWindowChange: EventEmitter<boolean> = new EventEmitter();
    protected loading: boolean = false;
    /*  username: string;
    password: string;
    confirmpassword: string;
    currentUserPassword: string;*/
    protected formGroup: FormGroup = new FormGroup({
        username: new FormControl(undefined, Validators.required),
        currentUserPassword: new FormControl(undefined, Validators.required),
        password: new FormControl(undefined, Validators.required),
        confirmpassword: new FormControl(undefined, Validators.required)
    });
    messages:GUserMessage[]=[];
    constructor(
        private userAdminService: UsersAdminControllerService,
        private messageService:MessageService,
        private geboTranslationService:GeboAITranslationService) {

    }
    ngOnChanges(changes: SimpleChanges): void {
        if (this.username && changes["username"]) {
            this.formGroup.controls["username"].setValue(this.username);
        }
    }
    ngOnInit(): void {

    }
    protected doChangePassword():void {
        const data:ChangeUsernamePasswordData=this.formGroup.value;
        this.loading=true;
        this.userAdminService.changeUserPassword(data).subscribe({
            next:(msg)=>{
                this.messages=[msg];
                if (msg.severity==="success") {
                    this.skipWindow();
                }
            },
            complete:()=>{
                this.loading=false;
            }
        });
    }
    protected skipWindow():void {
        this.openWindow=false;
        this.openWindowChange.emit(false);
    }
}