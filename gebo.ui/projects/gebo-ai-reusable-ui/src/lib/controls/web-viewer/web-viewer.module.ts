import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CardModule } from 'primeng/card';
import { MessageModule } from 'primeng/message';
import { WebViewerComponent } from './web-viewer.component';
import { GeboAIFieldTranslationContainerModule } from '@Gebo.ai/reusable-ui';
import { BlockUIModule } from 'primeng/blockui';

@NgModule({
  
  imports: [
    CommonModule,
    CardModule,
    MessageModule,
    BlockUIModule,
    GeboAIFieldTranslationContainerModule
  ],
  declarations: [WebViewerComponent],
  exports: [WebViewerComponent]
})
export class WebViewerModule {}
