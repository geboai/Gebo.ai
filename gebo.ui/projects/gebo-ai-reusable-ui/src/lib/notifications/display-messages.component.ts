import { Component, Input } from "@angular/core";
import { ToastZIndexService } from "./toast-z-index.service";
import { NotificationLayerEnum } from "./notification-layer";
/***********************
 * This component will be inserted in the app.component.html
 */
@Component({
    selector: "gebo-ai-display-messages",
    templateUrl: "display-messages.component.html",
    standalone: false
})
export class GeboAIDisplayMessagesComponent {
    @Input() layer:NotificationLayerEnum="GLOBAL";
    protected autoZIndex:number=20000;
    constructor(private zIndexService: ToastZIndexService) {
        this.zIndexService.z$.subscribe({
            next:(z)=>{
                this.autoZIndex=z;
                console.log("auto-z-index:"+z);
            }
        });
    }
}