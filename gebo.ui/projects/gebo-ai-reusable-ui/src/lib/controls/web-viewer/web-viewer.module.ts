import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CardModule } from 'primeng/card';
import { MessageModule } from 'primeng/message';
import { WebViewerComponent } from './web-viewer.component';

import { BlockUIModule } from 'primeng/blockui';
import { GeboAIFieldTranslationContainerModule } from '../field-translation-container/field-container.module';

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
