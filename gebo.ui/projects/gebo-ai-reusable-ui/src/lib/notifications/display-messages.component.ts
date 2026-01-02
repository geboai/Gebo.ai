import { Component } from "@angular/core";
import { ToastZIndexService } from "./toast-z-index.service";
/***********************
 * This component will be inserted in the app.component.html
 */
@Component({
    selector: "gebo-ai-display-messages",
    template: "<p-toast key='global' appendTo='body' [baseZIndex]='autoZIndex'  position='top-right' > </p-toast>",
    standalone: false
})
export class GeboAIDisplayMessagesComponent {
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