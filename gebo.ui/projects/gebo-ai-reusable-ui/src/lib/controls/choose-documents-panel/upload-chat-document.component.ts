import { Component, forwardRef, Input, OnChanges, OnInit, SimpleChanges, Inject, Output, EventEmitter } from "@angular/core";
import { ControlValueAccessor, FormGroup, NG_VALUE_ACCESSOR } from "@angular/forms";
import { GeboUserChatUploadsControllerService, BASE_PATH, IngestionFileTypesLibraryControllerService, UserUploadedContent, OperationStatusListUserUploadedContent, GUserMessage } from "@Gebo.ai/gebo-ai-rest-api";
import { GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboAIFieldHost } from "../field-host-component-iface/field-host-component-iface";
import { GeboAITranslationService } from "../field-translation-container/gebo-translation.service";
import { MessageService } from "primeng/api";
import { getAuth } from "../../infrastructure/gebo-credentials";
import { HttpEventType, HttpHeaders } from '@angular/common/http';
import { FileProgressEvent, FileUploadEvent } from "primeng/fileupload";
const urlPostfix: string = "api/users/GeboUserChatUploadsController/chatSessionUpload/";
@Component({
    templateUrl: "upload-chat-document.component.html",
    selector: "gebo-ai-upload-chat-documents-files",
    standalone: false,
    providers: [MessageService,
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: forwardRef(() => GeboAIUploadChatDocumentComponent),
            multi: true
        },
        { provide: GEBO_AI_MODULE, useValue: "GeboAIUploadChatDocumentModule", multi: false },
        {
            provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIUploadChatDocumentComponent),
            multi: false
        }
    ]
})
export class GeboAIUploadChatDocumentComponent implements OnInit, OnChanges, ControlValueAccessor, GeboAIFieldHost {



    @Input() showUpload: boolean = false;
    @Output() showUploadChange: EventEmitter<boolean> = new EventEmitter();
    @Input() userSessionCode?: string;
    protected response?: OperationStatusListUserUploadedContent;
    protected loading: boolean = false;
    protected filesExtensionsFlatList: string = "";
    protected uploadedContents?: UserUploadedContent[] = [];
    protected headers: HttpHeaders = new HttpHeaders();
    protected formGroup: FormGroup<any> = new FormGroup({
    });
    //@Output() uploadedFilesChanged: EventEmitter<UserUploadedContent[]> = new EventEmitter();
    files: any[] = [];

    totalSize: number = 0;

    totalSizePercent: number = 0;

    protected url!: string;

    constructor(
        private contentTypeService: IngestionFileTypesLibraryControllerService,
        private messageService: MessageService,
        private geboAITranslatorService: GeboAITranslationService,
        private chatDocumentsUploadService: GeboUserChatUploadsControllerService,
        @Inject(BASE_PATH) private baseUrl: string) {
        this.url = baseUrl + "/" + urlPostfix;

    }
    getEntityName(): string {
        return "GeboAIUploadChatDocumentComponent";
    }
    ngOnInit(): void {
        const auth = getAuth();
        this.headers = new HttpHeaders({
            "Authorization": "Bearer " + auth?.token
        });
        this.loading = true;
        this.contentTypeService.getAllFileTypes().subscribe({
            next: (value) => {
                const extensions: string[] = [];
                value.forEach(x => {
                    if (x.extensions) {
                        x.extensions.forEach(e => {
                            extensions.push(e);
                        });
                    }
                });
                let extstring: string = "";
                extensions.forEach((e, i) => {
                    extstring += e;
                    if (i < extensions.length - 1) {
                        extstring += ",";
                    }
                });
                this.filesExtensionsFlatList = extstring;
            },
            complete: () => {
                this.loading = false;
            }
        });
    }
    ngOnChanges(changes: SimpleChanges): void {
        if (changes["userSessionCode"] && this.userSessionCode) {
            this.url = this.baseUrl + "/" + urlPostfix + this.userSessionCode;
        }
    }
    writeValue(obj: any): void {
        this.uploadedContents = obj;
    }
    onUpload(evt: FileUploadEvent) {
        if (evt.originalEvent.type === HttpEventType.Response) {
            this.response = evt.originalEvent.body;
            if (this.response?.result) {
                if (this.uploadedContents) {
                    this.uploadedContents = [...this.uploadedContents, ...this.response.result];
                } else {
                    this.uploadedContents = this.response.result;
                }
                this.onChange(this.uploadedContents);
            }
            if (this.response?.messages) {
                this.geboAITranslatorService.translateBackendMessages(this.response?.messages).subscribe({
                    next: (msgs: GUserMessage[] | undefined) => {
                        if (msgs) {
                            this.messageService.addAll(msgs);
                        }
                    }
                });
            }
            if (this.response?.hasErrorMessages !== true) {
                this.showUploadChange.emit(false);
            }
        }
    }

    private onChange: (d: any) => void = (d: any) => { };
    registerOnChange(fn: any): void {
        this.onChange = fn;
    }
    private onTouched: (d: any) => void = (d: any) => { };
    registerOnTouched(fn: any): void {
        this.onTouched = fn;
    }
    setDisabledState?(isDisabled: boolean): void {

    }
    choose(event: any, callback: any) {
        callback();
    }

    onRemoveTemplatingFile(event: any, file: any, removeFileCallback: any, index: any) {
        removeFileCallback(event, index);
        this.totalSize -= parseInt(file.size);
        this.totalSizePercent = this.totalSize / 10;
    }

    onClearTemplatingUpload(clear: any) {
        clear();
        this.totalSize = 0;
        this.totalSizePercent = 0;
    }


    onSelectedFiles(event: any) {
        this.files = event.currentFiles;
        this.files.forEach((file) => {
            this.totalSize += parseInt(file.size);
        });
        this.totalSizePercent = this.totalSize / 10;
    }

    uploadEvent(callback: any) {
        callback();
    }

    formatSize(bytes: number | string | any) {
        const k = 1024;
        const dm = 3;

        const i = Math.floor(Math.log(bytes) / Math.log(k));
        const formattedSize = parseFloat((bytes / Math.pow(k, i)).toFixed(dm));

        return formattedSize
    }
    sendProgress(evt: FileProgressEvent) {
        
    }
    doRemoveUploaded(doc: UserUploadedContent) {
            
    }
}