import { CommonModule } from "@angular/common";
import { ModuleWithProviders, NgModule } from "@angular/core";
import { MessagesModule } from "primeng/messages";
import { GeboAIFieldTranslationContainerModule } from "../controls/field-translation-container/field-container.module";
import { MessageService } from "primeng/api";
import { GeboAIRootNotificationService } from "./root-notification.service";

@NgModule({
    imports: [CommonModule, MessagesModule, GeboAIFieldTranslationContainerModule],
    providers: [MessageService, GeboAIRootNotificationService]

})
export class GeboAINotificationsModule {
    public static forRoot(): ModuleWithProviders<GeboAINotificationsModule> {
        const m: ModuleWithProviders<GeboAINotificationsModule> = {
            ngModule: GeboAINotificationsModule,
            providers: [MessageService, GeboAIRootNotificationService]
        };
        return m;
    }
}