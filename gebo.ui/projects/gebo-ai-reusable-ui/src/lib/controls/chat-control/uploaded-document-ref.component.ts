import { Component, Input, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { UserUploadedContent } from "@Gebo.ai/gebo-ai-rest-api";
@Component({
    templateUrl:"uploaded-document-ref.component.html",
    selector:"uploaded-document-ref",
    standalone:false
})
export class GeboAIUploadedDocumentRefComponent implements OnInit,OnChanges{
    @Input() ref?:UserUploadedContent;
    ngOnInit(): void {
        
    }
    ngOnChanges(changes: SimpleChanges): void {
        
    }
}