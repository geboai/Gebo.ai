import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { FileUploadModule } from "@openng/optimus-ui/fileupload";
import { GeboAIUploadChatDocumentComponent } from "./upload-chat-document.component";
import { ButtonModule } from "@openng/optimus-ui/button";
import { ProgressBarModule } from "@openng/optimus-ui/progressbar";
import { DialogModule } from "@openng/optimus-ui/dialog";
import { BadgeModule } from "@openng/optimus-ui/badge";
import { PanelModule } from "@openng/optimus-ui/panel";
import { BlockUIModule } from "@openng/optimus-ui/blockui";
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