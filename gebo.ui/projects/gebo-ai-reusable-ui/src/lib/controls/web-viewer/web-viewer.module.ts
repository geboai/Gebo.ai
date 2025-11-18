import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CardModule } from 'primeng/card';
import { MessageModule } from 'primeng/message';
import { WebViewerComponent } from './web-viewer.component';
import { GeboAIFieldTranslationContainerModule } from '@Gebo.ai/reusable-ui';

@NgModule({
  declarations: [WebViewerComponent],
  imports: [
    CommonModule,
    CardModule,
    MessageModule,
    GeboAIFieldTranslationContainerModule
  ],
  exports: [WebViewerComponent]
})
export class WebViewerModule {}
