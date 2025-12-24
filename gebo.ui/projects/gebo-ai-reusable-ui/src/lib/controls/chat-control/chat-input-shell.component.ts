import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { GeboChatRequest, GeboChatResponse, GeboChatUserInfo, GUserChatInfo } from '@Gebo.ai/gebo-ai-rest-api';
import { GeboAITranslationService } from '../field-translation-container/gebo-translation.service';
import { findMatchingTranlations, UIExistingText } from '../field-translation-container/text-language-resources';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'gebo-ai-chat-input-shell',
  templateUrl: './chat-input-shell.component.html',
  styleUrls: ['./chat-input-shell.component.scss'],
  standalone: false
})
export class GeboAIChatInputShellComponent implements OnInit,OnChanges {

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
  @Output() loadingChange: EventEmitter<boolean> = new EventEmitter();
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
  @Output() deepSearchChatRequest: EventEmitter<GeboChatRequest> = new EventEmitter();
  @Output() deepsearchChatResponse: EventEmitter<GeboChatResponse> = new EventEmitter();
  protected addBehaviorsMenu: MenuItem[] = [{
    id: "UploadFileMenuItem",
    icon: "pi pi-cloud-upload",
    label: "Upload file(s)",
    command: (event) => {
      this.openUploadDialog();
    }
  }, {
    id: "ChatWithDocsMenuItem",
    icon: "pi pi-search",
    label: "Browse/search company file(s)",
    disabled:true,
    command: (event) => {
      this.openSelectDocsDialog();
    }
  }, {
    id: "DeepSearchItem",
    icon: "pi pi-deep-search",
    label: "Deep search",
    command: (event) => {
      this.nextRequestMode = "deep-search";
      this.chooseModeFormGroup.controls["nextRequestMode"].setValue(this.nextRequestMode);
    }
  }];
  protected currentDeepsearchChatRequest?: GeboChatRequest;
  protected nextRequestMode: "standard-chat" | "deep-search" = "standard-chat";
  protected requestTypeOptions: UIExistingText[] = [{ moduleId: "GeboAIReusableChatModel", entityId: "GeboAIChatInputShellComponent", componentId: "standard-chat", key: "label", fieldId: "label", text: "Chat", translation: "Chat" }, { moduleId: "GeboAIReusableChatModel", entityId: "GeboAIChatInputShellComponent", componentId: "deep-search", key: "label", fieldId: "label", text: "Deep search", translation: "Deep search" }];
  get submitIcon(): string {
    if (this.loading) {
      return 'pi pi-spin pi-spinner';
    }

    return this.nextRequestMode === 'standard-chat'
      ? 'pi pi-search'
      : 'pi pi-deep-search';
  }
  protected chooseModeFormGroup: FormGroup = new FormGroup({
    nextRequestMode: new FormControl()
  });
  constructor(private translationService: GeboAITranslationService) {
    this.chooseModeFormGroup.controls["nextRequestMode"].valueChanges.subscribe(data => { this.nextRequestMode = data; });
    this.setStandardChatMode();
  }
  
  private setStandardChatMode(): void {
    this.nextRequestMode="standard-chat";
    this.chooseModeFormGroup.controls["nextRequestMode"].setValue("standard-chat");
  }

  
  
  ngOnInit(): void {
    this.translationService.translateOnActualLanguage(this.requestTypeOptions).subscribe({
      next: (resources) => {
        if (resources) {
          const matching = findMatchingTranlations(this.requestTypeOptions, resources);
          if (matching && matching.length === this.requestTypeOptions.length) {
            this.requestTypeOptions = matching;
          }
        }
      }
    });
  }
  ngOnChanges(changes: SimpleChanges): void {
      if (changes["ragsystem"]) {
        const item=this.addBehaviorsMenu.find(x=>x.id==="ChatWithDocsMenuItem");
        if (item) {
          item.disabled=!this.ragsystem;
        }
        this.addBehaviorsMenu=[...this.addBehaviorsMenu];
      }
  }
  onSubmit() {
    this.onSendClick();
  }

  onSendClick() {
    if (this.formGroup?.invalid || this.loading) {
      return;
    }
    if (this.nextRequestMode === "deep-search") {
      const request: GeboChatRequest = this.formGroup.value;
      this.deepSearchChatRequest.emit(request);
      this.loadingChange.emit(true);
      this.currentDeepsearchChatRequest = request;
      this.formGroup.controls["query"].setValue(null);
      this.formGroup.controls["userUploadedContents"].setValue([]);
    } else {
      this.messageSubmit.emit();
    }
  }

  onNewSessionCreatedOnUploadInternal(evt: any) {
    this.newSessionCreatedOnUpload.emit(evt);
  }

  onSpeechEventInternal(data: any) {
    this.speechEvent.emit({ data, url: 'noUrl' });
  }

  openUploadDialog() {
    this.nextRequestMode = "standard-chat";
    this.openedUploadDocumentsWindow = true;
    this.openedUploadDocumentsWindowChange.emit(true);
  }

  openSelectDocsDialog() {
    this.nextRequestMode = "standard-chat";
    this.openSelectDocumentsWindow = true;
    this.openSelectDocumentsWindowChange.emit(true);
  }
  onDeepSearchChatResponseReceived(event: GeboChatResponse) {
    this.currentDeepsearchChatRequest = undefined;
    this.deepsearchChatResponse.emit(event);
    this.loading = false;
    this.loadingChange.emit(false);
  }
  onErrorOccurred(event: any) {
    this.currentDeepsearchChatRequest = undefined;
    this.loading = false;
    this.loadingChange.emit(false);
  }
  onSkipDeepSearchEvent(_event: any): void {
    this.setStandardChatMode();
  }
}
