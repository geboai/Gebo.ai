import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { GeboChatUserInfo, GUserChatInfo } from '@Gebo.ai/gebo-ai-rest-api';

@Component({
  selector: 'gebo-ai-chat-input-shell',
  templateUrl: './chat-input-shell.component.html',
  styleUrls: ['./chat-input-shell.component.scss'],
  standalone:false
})
export class GeboAIChatInputShellComponent {

  @Input() interactions: any[] | null = null;
  @Input() formGroup!: FormGroup;

  @Input() ragsystem!: boolean;
  @Input() chatUserInfos?: GeboChatUserInfo;
  @Input() knowledgeBaseCodes?: string[] = undefined;

  @Input() chatInfo?: GUserChatInfo;
  @Input() userChatContextCode?: string;

  @Input() capabilities: any;
  @Input() currentAudioTrack: any;
  @Input() loading = false;

  // flags per le dialog
  @Input() openSelectDocumentsWindow = false;
  @Output() openSelectDocumentsWindowChange = new EventEmitter<boolean>();

  @Input() openedUploadDocumentsWindow = false;
  @Output() openedUploadDocumentsWindowChange = new EventEmitter<boolean>();

  // Eventi verso il parent
  @Output() newSessionCreatedOnUpload = new EventEmitter<any>();
  @Output() speechEvent = new EventEmitter<{ data: any; url: string }>();
  @Output() messageSubmit = new EventEmitter<void>();
  @Output() messageSend = new EventEmitter<void>();

  onSubmit() {
    if (this.formGroup?.invalid || this.loading) {
      return;
    }
    this.messageSubmit.emit();
  }

  onSendClick() {
    if (this.formGroup?.invalid || this.loading) {
      return;
    }
    this.messageSend.emit();
  }

  onNewSessionCreatedOnUploadInternal(evt: any) {
    this.newSessionCreatedOnUpload.emit(evt);
  }

  onSpeechEventInternal(data: any) {
    this.speechEvent.emit({ data, url: 'noUrl' });
  }

  openUploadDialog() {
    this.openedUploadDocumentsWindow = true;
    this.openedUploadDocumentsWindowChange.emit(true);
  }

  openSelectDocsDialog() {
    this.openSelectDocumentsWindow = true;
    this.openSelectDocumentsWindowChange.emit(true);
  }
}
