import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { GeboChatRequest, GeboChatResponse, GeboChatUserInfo, GUserChatInfo, PipelineChatMenu, PipelineChatMenuItem } from '@Gebo.ai/gebo-ai-rest-api';
import { GeboAITranslationService } from '../field-translation-container/gebo-translation.service';
import { findMatchingTranlations, UIExistingText } from '../field-translation-container/text-language-resources';
import { MenuItem } from 'primeng/api';

import { IGeboChatMessage } from '../../services/gebo-chat-message';
import { GeboAIChatStreamEventsDisplayComponent } from './chat-stream-events-display.component';
import { PipelineRoutingOption } from './pipeline-routing-option';
function iconByProduct(productId: string): string | undefined {
  let out: string | undefined = undefined;
  /* .pi.pi-git,
.pi.pi-sharepoint,
.pi.pi-onedrive,
.pi.pi-google-workspace,
.pi.pi-google-drive,
.pi.pi-confluence,
.pi.pi-jira  */
  if (productId) {
    switch (productId) {
      case "google-drive": {
        return "pi pi-google-drive";
      }
      case "confluence": {
        return "pi pi-confluence";
      }
      case "jira": {
        return "pi pi-jira";
      } break;
      case "sharepoint": {
        return "pi pi-sharepoint";
      };
      case "google": {
        return "pi pi-google";
      };
    }
  }
  return out;
}


const uploadedDocumentChatMenuItem: PipelineRoutingOption = {
  optionId: "UploadFileMenuItem",
  description: "Uploaded doc. chat",
  chatPipelineProcessId: "ChatWithUploadFile",
  pipelineParams: undefined,
  defaultOption: false
};
const chatWithDocsMenuItem: PipelineRoutingOption = {
  optionId: "ChatWithChoosenDocsMenuItem",
  description: "Choosen doc. chat",
  chatPipelineProcessId: "ChatWithChoosenDocs",
  pipelineParams: undefined,
  defaultOption: false
};


@Component({
  selector: 'gebo-ai-chat-input-shell',
  templateUrl: './chat-input-shell.component.html',
  styleUrls: ['./chat-input-shell.component.scss'],
  standalone: false
})
export class GeboAIChatInputShellComponent implements OnInit, OnChanges {


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
  @Input() streaming: boolean = false;
  @Input() routingChoiceSelector?: string;
  @Output() loadingChange: EventEmitter<boolean> = new EventEmitter();
  // flags per le dialog
  @Input() openSelectDocumentsWindow = false;
  @Output() openSelectDocumentsWindowChange = new EventEmitter<boolean>();

  @Input() openedUploadDocumentsWindow = false;
  @Input() pipelineChatMenu: PipelineChatMenu[] = [];
  protected choosedPipelineRoutingChip?: PipelineRoutingOption;
  @Output() openedUploadDocumentsWindowChange = new EventEmitter<boolean>();
  // Eventi verso il parent
  @Output() newSessionCreatedOnUpload = new EventEmitter<any>();
  @Output() speechEvent = new EventEmitter<{ data: any; url: string }>();
  @Output() messageSubmit = new EventEmitter<void>();
  @Output() messageSend = new EventEmitter<void>();
  @Output() deepSearchChatRequest: EventEmitter<GeboChatRequest> = new EventEmitter();
  @Output() deepsearchChatResponse: EventEmitter<GeboChatResponse> = new EventEmitter();
  @Output() nextRoutingChoice: EventEmitter<PipelineRoutingOption> = new EventEmitter();
  @ViewChild(GeboAIChatStreamEventsDisplayComponent) streamNotificationsComponent!: GeboAIChatStreamEventsDisplayComponent;
  private staticBehaviorsMenuItems: MenuItem[] = [{
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
    disabled: true,
    command: (event) => {
      this.openSelectDocsDialog();
    }
  }];
  protected addBehaviorsMenu: MenuItem[] = this.staticBehaviorsMenuItems;
  private nextPipelineSubmittedParams?: PipelineRoutingOption;
  private allPipelineRoutingOptions: PipelineRoutingOption[] = [];
  protected nextRequestMode: "standard-chat" | "deep-search" = "standard-chat";
  protected requestTypeOptions: UIExistingText[] = [{ moduleId: "GeboAIReusableChatModel", entityId: "GeboAIChatInputShellComponent", componentId: "standard-chat", key: "label", fieldId: "label", text: "Chat", translation: "Chat" }, { moduleId: "GeboAIReusableChatModel", entityId: "GeboAIChatInputShellComponent", componentId: "deep-search", key: "label", fieldId: "label", text: "Deep search", translation: "Deep search" }];
  protected clearNextPipelineRoute(): void {
    this.choosedPipelineRoutingChip = undefined;

  }
  protected setNextPipelineRoute(option: PipelineRoutingOption): void {
    this.choosedPipelineRoutingChip = option;

  }
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
    this.setChatMode("standard-chat");
  }
  private setChatMode(mode: "standard-chat" | "deep-search"): void {
    this.nextRequestMode = mode;
    this.chooseModeFormGroup.controls["nextRequestMode"].setValue(mode);
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
  private recreateMenuAndRouteOptions(): void {
    const newMenu: MenuItem[] = [...this.staticBehaviorsMenuItems];
    const allPipelineRoutingOptions: PipelineRoutingOption[] = [uploadedDocumentChatMenuItem, chatWithDocsMenuItem];
    this.pipelineChatMenu.forEach(menuItem => {
      if (menuItem.items && menuItem.items.length === 1) {
        //if single line menu ==> let's flat it directly as menu item with connected action
        const option: PipelineRoutingOption = this.createRoutingOption(menuItem.items[0]);
        allPipelineRoutingOptions.push(option);
        newMenu.push(this.createMenuLeaf(menuItem.items[0], option));
      } else {
        newMenu.push(this.createSubMenu(menuItem, allPipelineRoutingOptions));
      }

    });
    this.allPipelineRoutingOptions = allPipelineRoutingOptions;
    this.translationService.translateMenuItems("GeboAIChatControlModule", "ChatPipelinesMenu", newMenu).subscribe(items => {
      this.addBehaviorsMenu = items;
    });
    const textResources: UIExistingText[] = [];
    this.allPipelineRoutingOptions.forEach(x => {
      if (x.description) {
        const entry: UIExistingText = {
          moduleId: "GeboAIChatControlModule",
          entityId: "ChatPipelinesMenu",
          componentId: x.optionId,
          fieldId: "label",
          key: "label",
          text: x.description
        };
        textResources.push(entry);
      }
    });
    this.translationService.translateOnActualLanguage(textResources).subscribe(rcs => {
      if (rcs) {
        const data = findMatchingTranlations(textResources, rcs);
        data?.forEach(x => {
          const toBeLabelled = this.allPipelineRoutingOptions.find(y => y.optionId === x.componentId && x.fieldId === 'label');
          if (toBeLabelled) {
            toBeLabelled.description = x.text;
          }
        });
      }

    });

  }
  ngOnChanges(changes: SimpleChanges): void {
    if (changes["pipelineChatMenu"] && this.pipelineChatMenu) {
      this.recreateMenuAndRouteOptions();
    }
    if (changes["streaming"]) {
      if (this.streaming === false) {
        this.setChatMode("standard-chat");
      }
    }
    if (changes["ragsystem"]) {
      const item = this.addBehaviorsMenu.find(x => x.id === "ChatWithDocsMenuItem");
      if (item) {
        item.disabled = !this.ragsystem;
      }
      this.addBehaviorsMenu = [...this.addBehaviorsMenu];
    }
  }
  createRoutingOption(item: PipelineChatMenuItem): PipelineRoutingOption {
    const out: PipelineRoutingOption = {
      chatPipelineProcessId: item.routeOption,
      defaultOption: item.defaultOption === true,
      description: item.description,
      optionId: item.optionId,
      pipelineParams: undefined
    };
    if (item?.parameters && item.parameters.length) {
      const routingParams: any = {

      };
      item.parameters.forEach(par => {
        routingParams[par.parameterName] = par.parameterValue;
      });
      out.pipelineParams = routingParams;
    }
    return out;
  }
  createSubMenu(menuItem: PipelineChatMenu, options: PipelineRoutingOption[]): MenuItem {
    const item: MenuItem = {
      id: menuItem.menuId,
      label: menuItem.description,
      items: []
    };
    menuItem.items.forEach(node => {
      const option: PipelineRoutingOption = this.createRoutingOption(node);
      const menuItem: MenuItem = this.createMenuLeaf(node, option);
      options.push(option);
      item.items?.push(menuItem);
    });
    return item;
  }
  createMenuLeaf(leaf: PipelineChatMenuItem, option: PipelineRoutingOption): MenuItem {
    const item: MenuItem = {
      id: leaf.optionId,
      label: leaf.description,
      icon: leaf?.icon ? leaf.icon : leaf?.productId ? iconByProduct(leaf.productId) : undefined,
      command: () => {
        this.nextPipelineSubmittedParams = option;
        this.nextRoutingChoice.emit(option)
      }
    };

    return item;
  }
  onSubmit() {
    this.onSendClick();
  }

  onSendClick() {
    if (this.formGroup?.invalid || this.loading) {
      return;
    }

    this.messageSubmit.emit();

  }
  onStreamError(error: any) {
    //this.streamNotificationsComponent.onError(error);
  }
  onStreamMessage(recvd: IGeboChatMessage) {
    this.streamNotificationsComponent.onMessage(recvd);
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

  onSkipDeepSearchEvent(_event: any): void {
    this.setStandardChatMode();
  }
}
