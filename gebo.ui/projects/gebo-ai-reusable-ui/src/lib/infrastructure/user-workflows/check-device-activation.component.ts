import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { UserWorkflowsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { getAuth } from "../gebo-credentials";
@Component({
    selector:"gebo-ai-check-device-activation-component",
    template:"",
    standalone:false
})
export class GeboAICheckDeviceActivationComponent implements OnInit{
    constructor(private service:UserWorkflowsControllerService, private router: Router) {

    }
    ngOnInit(): void {
        this.service.getUserWorkflowsConfig().subscribe({
            next:(cfg)=>{
                if (cfg?.activationWorkflowEnabled===true){
                    const auth=getAuth();
                    if (auth) {
                        this.goToChat();
                    }else {
                        this.goToActivate();
                    }
                }else {
                    this.goToLogin();
                }
            },
            complete:()=>{

            }
        });
    }
    goToActivate() {
        const _relative:string="/ui/user-workflows/start?type=ACTIVATION";
        this.router.navigateByUrl(_relative);
    }
     goToLogin() {
        this.router.navigate(['/ui/login']);
    }
    goToChat() {
        this.router.navigate(['/ui/login']);
    }

}