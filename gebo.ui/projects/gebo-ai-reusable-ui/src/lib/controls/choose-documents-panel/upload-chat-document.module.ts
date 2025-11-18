import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { FileUploadModule } from "primeng/fileupload";
import { MessagesModule } from "primeng/messages";
import { ToastModule } from "primeng/toast";
import { GeboAIUploadChatDocumentComponent } from "./upload-chat-document.component";
import { ButtonModule } from "primeng/button";
import { ProgressBarModule } from "primeng/progressbar";
import { DialogModule } from "primeng/dialog";
import { BadgeModule } from "primeng/badge";
import { PanelModule } from "primeng/panel";
import { BlockUIModule } from "primeng/blockui";
import { GeboAIFieldTranslationContainerModule } from "../field-translation-container/field-container.module";

@NgModule({
    imports: [CommonModule, ReactiveFormsModule, FormsModule, FileUploadModule, ToastModule, MessagesModule, ButtonModule, ProgressBarModule, DialogModule, BadgeModule, PanelModule, BlockUIModule, GeboAIFieldTranslationContainerModule],
    declarations: [GeboAIUploadChatDocumentComponent],
    exports: [GeboAIUploadChatDocumentComponent]
})
export class GeboAIUploadChatDocumentModule {

}