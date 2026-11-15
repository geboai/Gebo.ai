import { CommonModule } from "@angular/common";
import { ModuleWithProviders, NgModule } from "@angular/core";
import { GeboAIFieldTranslationContainerModule } from "../controls/field-translation-container/field-container.module";
import { MessageService } from "@openng/optimus-ui/api";
import { GeboAINotificationComponent } from "./notification.component";
import { GeboAIDisplayMessagesComponent } from "./display-messages.component";
import { ToastModule } from "@openng/optimus-ui/toast";
import { MessageModule } from "@openng/optimus-ui/message";
import { ButtonModule } from "@openng/optimus-ui/button";
import { GeboAISystemMessagesComponent } from "./system-messages.component";
@NgModule({
  imports: [CommonModule,  GeboAIFieldTranslationContainerModule, ToastModule, MessageModule, ButtonModule],
  declarations: [GeboAINotificationComponent, GeboAIDisplayMessagesComponent, GeboAISystemMessagesComponent],
  exports: [GeboAINotificationComponent, GeboAIDisplayMessagesComponent, GeboAISystemMessagesComponent]
})
export class GeboAINotificationsModule {
  public static forRoot(): ModuleWithProviders<GeboAINotificationsModule> {
    return {
      ngModule: GeboAINotificationsModule,
      providers: [MessageService] 
    };
  }
}