import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { FileUploadModule } from "primeng/fileupload";
import { GeboAIUploadChatDocumentComponent } from "./upload-chat-document.component";
import { ButtonModule } from "primeng/button";
import { ProgressBarModule } from "primeng/progressbar";
import { DialogModule } from "primeng/dialog";
import { BadgeModule } from "primeng/badge";
import { PanelModule } from "primeng/panel";
import { BlockUIModule } from "primeng/blockui";
import { GeboAIFieldTranslationContainerModule } from "../field-translation-container/field-container.module";
import { CdkDragPlaceholder } from "@angular/cdk/drag-drop";
import { GeboAINotificationsModule } from "../../notifications/notifications.module";

@NgModule({
    imports: [CommonModule, ReactiveFormsModule, FormsModule, FileUploadModule, ButtonModule, ProgressBarModule, DialogModule, BadgeModule, PanelModule, BlockUIModule, GeboAIFieldTranslationContainerModule,GeboAINotificationsModule, GeboAINotificationsModule,CdkDragPlaceholder],
    declarations: [GeboAIUploadChatDocumentComponent],
    exports: [GeboAIUploadChatDocumentComponent]
})
export class GeboAIUploadChatDocumentModule {

}