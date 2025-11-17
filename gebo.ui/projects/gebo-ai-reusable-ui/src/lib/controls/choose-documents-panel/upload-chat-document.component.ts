import { Component, EventEmitter, forwardRef, Input, OnChanges, OnInit, Output, SimpleChanges, Inject } from "@angular/core";
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from "@angular/forms";
import { GeboUserChatUploadsControllerService, UserUploadedContent, BASE_PATH, IngestionFileTypesLibraryControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../field-host-component-iface/field-host-component-iface";
import { GeboAITranslationService } from "@Gebo.ai/reusable-ui";
import { PrimeNG } from "primeng/config";
import { MessageService } from "primeng/api";
const urlPostfix: string = "api/users/GeboUserChatUploadsController/chatSessionUpload/";
@Component({
    templateUrl: "upload-chat-document.component.html",
    selector: "gebo-ai-upload-chat-document",
    standalone: false,
    providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: forwardRef(() => GeboAIUploadChatDocumentComponent),
            multi: true
        },
        { provide: GEBO_AI_MODULE, useValue: "GeboAIChooseDocumentsPanelModule", multi: false },
        {
            provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("GeboAIUploadChatDocumentComponent"),
            multi: false
        }
    ],
})
export class GeboAIUploadChatDocumentComponent implements OnInit, OnChanges, ControlValueAccessor {

    @Input() showUpload: boolean = false;
    @Input() userSessionCode?: string;
    //protected uploadedFiles: UserUploadedContent[] = [];
    protected loading: boolean = false;
    protected filesExtensionsFlatList: string = "";
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
    ngOnInit(): void {
        this.loading=true;
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

    onTemplatedUpload() {
        this.messageService.add({ severity: 'info', summary: 'Success', detail: 'File Uploaded', life: 3000 });
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
}