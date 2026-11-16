import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CardModule } from '@openng/optimus-ui/card';
import { MessageModule } from '@openng/optimus-ui/message';
import { WebViewerComponent } from './web-viewer.component';

import { BlockUIModule } from '@openng/optimus-ui/blockui';
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
