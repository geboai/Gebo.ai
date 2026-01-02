import { CommonModule } from "@angular/common";
import { ModuleWithProviders, NgModule } from "@angular/core";
import { MessagesModule } from "primeng/messages";
import { GeboAIFieldTranslationContainerModule } from "../controls/field-translation-container/field-container.module";
import { MessageService } from "primeng/api";
import { GeboAINotificationComponent } from "./notification.component";
import { GeboAIDisplayMessagesComponent } from "./display-messages.component";
import { ToastModule } from "primeng/toast";
@NgModule({
  imports: [CommonModule, MessagesModule, GeboAIFieldTranslationContainerModule, ToastModule],
  declarations: [GeboAINotificationComponent, GeboAIDisplayMessagesComponent],
  exports: [GeboAINotificationComponent, GeboAIDisplayMessagesComponent]
})
export class GeboAINotificationsModule {
  public static forRoot(): ModuleWithProviders<GeboAINotificationsModule> {
    return {
      ngModule: GeboAINotificationsModule,
      providers: [MessageService] 
    };
  }
}