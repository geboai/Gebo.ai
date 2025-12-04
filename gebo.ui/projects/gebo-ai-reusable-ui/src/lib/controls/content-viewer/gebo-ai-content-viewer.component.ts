/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */




/**
 * AI generated comments
 * This module provides a content viewer component for the Gebo.ai platform that can display
 * various types of content based on file types. It handles different content formats
 * including source code, plain text, PDF, and browsable web content.
 */
import { HttpClient } from "@angular/common/http";
import { Component, Input, OnChanges, OnInit, SimpleChanges, Inject, Output, EventEmitter } from "@angular/core";
import { BASE_PATH, ContentMetaInfo, ContentMetaInfosControllerService, ContentObject, IngestionFileType, IngestionFileTypesLibraryControllerService, UserUploadedContent } from "@Gebo.ai/gebo-ai-rest-api";
import { EnrichedLLMGeneratedResource, EnrichedUserUploadedContentView } from "./enriched-document-reference-view.service";

/**
 * URL for the content controller API endpoint
 */
const contentControllerUrl: string = "/api/users/ContentController";
const uploadedContentControllerUrl: string = "/api/users/GeboUserChatUploadsController/serveContent/"
const generatedContentControllerUrl: string = "/api/users/GeboLLMGeneratedResourceController/serveLLMGeneratedContent/"
/**
 * @Component GeboAIContentViewerComponent
 * A component responsible for rendering different types of content based on their file type.
 * It supports viewing source code, plain text, PDFs, and web content, with appropriate
 * rendering for each content type.
 */
@Component({
  selector: "gebo-ai-content-viewer",
  templateUrl: "gebo-ai-content-viewer.component.html",
  standalone: false
})
export class GeboAIContentViewerComponent implements OnInit, OnChanges {

  /** Unique identifier for the content to be displayed */
  @Input() code: string = "";

  @Input() userUploadedContent?: EnrichedUserUploadedContentView;

  @Input() generatedContent?: EnrichedLLMGeneratedResource;

  /** Flag to determine if the viewer should be active */
  @Input() activate: boolean = false;

  /** Controls the visibility of the content viewer */
  protected visible: boolean = false;

  /** Indicates if content is being loaded */
  protected loading: boolean = false;

  /** Metadata information about the content */
  protected contentMetaInfo?: ContentMetaInfo;

  /** Information about the file type of the content */
  protected ingestionFileType?: IngestionFileType;

  /** Programming language of the source code content */
  protected sourceCodeLanguage: string = "";

  /** Flag indicating if the content is source code */
  protected sourceCodeContent: boolean = false;

  /** Flag indicating if the content is browsable web content */
  protected browsableContent: boolean = false;

  /** Flag indicating if the content should be downloaded rather than viewed */
  protected downloadableContent: boolean = false;

  /** Flag indicating if the content is plain text */
  protected plainTextContent: boolean = false;

  /** Flag indicating if the content is a PDF document */
  protected pdfContent: boolean = false;

  /** The actual content object with data */
  protected contentObject?: ContentObject;

  /** URL for external content viewing */
  protected externalContentUrl: string = "javascript:void(0)";

  /*** if true the url is served by gebo and requires an authorization token */
  protected contentServedByGebo: boolean = false;
  /** Event emitter that fires when the content viewer is closed */
  @Output() close: EventEmitter<boolean> = new EventEmitter();

  /**
   * Constructor for the GeboAIContentViewerComponent
   * 
   * @param contentMetaControllerService Service for fetching content metadata
   * @param ingestionMetaInfoService Service for getting file type information
   * @param httpClient Angular HTTP client for API requests
   * @param path Base API path injected from application config
   */
  constructor(
    private contentMetaControllerService: ContentMetaInfosControllerService,
    private ingestionMetaInfoService: IngestionFileTypesLibraryControllerService,
    private httpClient: HttpClient,
    @Inject(BASE_PATH) private path: string) {

  }

  /**
   * Angular lifecycle hook that initializes the component
   */
  ngOnInit(): void {

  }

  /**
   * Closes the content viewer and emits a close event
   * 
   * @param evt The event that triggered the close action
   */
  protected closeMe(evt: any) {
    this.visible = false;
    this.close.emit(true);
  }

  /**
   * Processes the content metadata to determine how to display the content
   * Sets flags for different content types and prepares the external content URL
   */
  private handleMetaInfo() {
    if (this.generatedContent) {
      const fileType = this.generatedContent.fileType;
      if (fileType) {
        this.ingestionFileType = this.generatedContent.fileType;
        if (this.ingestionFileType?.uiViewable === true) {
          this.sourceCodeContent = fileType?.treatAs === 'sourceCode';
          this.plainTextContent = fileType?.treatAs === 'plainText';
          this.pdfContent = fileType?.extensions?.find(x => x === '.pdf') ? true : false;
          this.browsableContent = fileType.extensions && (".html" in fileType.extensions) ? true : false;
          this.externalContentUrl = this.path + generatedContentControllerUrl + this.generatedContent?.userContextCode + "/" + this.generatedContent?.code;
          this.contentServedByGebo = true;
        }
        this.downloadableContent = fileType?.uiViewable === false;
        this.visible = fileType?.uiViewable === true;
        console.log("Content: " + this.externalContentUrl);
        console.log("content viewer sourceCodeContent:" + this.sourceCodeContent + " sourceCodeContent:" + this.sourceCodeContent + " plainTextContent:" + this.plainTextContent + " externalContent:" + this.downloadableContent + " browsableContent:" + this.browsableContent + " pdfContent:" + this.pdfContent);
        console.log("content viewer url:" + this.externalContentUrl);
      }
    }
    if (this.userUploadedContent) {
      const fileType = this.userUploadedContent.fileType;
      if (fileType) {
        this.ingestionFileType = this.userUploadedContent.fileType;
        if (this.ingestionFileType?.uiViewable === true) {
          this.sourceCodeContent = fileType?.treatAs === 'sourceCode';
          this.plainTextContent = fileType?.treatAs === 'plainText';
          this.pdfContent = fileType?.extensions?.find(x => x === '.pdf') ? true : false;
          this.browsableContent = fileType.extensions && (".html" in fileType.extensions) ? true : false;
          this.externalContentUrl = this.path + uploadedContentControllerUrl + this.userUploadedContent?.userContextCode + "/" + this.userUploadedContent?.code;
          this.contentServedByGebo = true;
        }
        this.downloadableContent = fileType?.uiViewable === false;
        this.visible = fileType?.uiViewable === true;
        console.log("Content: " + this.externalContentUrl);
        console.log("content viewer sourceCodeContent:" + this.sourceCodeContent + " sourceCodeContent:" + this.sourceCodeContent + " plainTextContent:" + this.plainTextContent + " externalContent:" + this.downloadableContent + " browsableContent:" + this.browsableContent + " pdfContent:" + this.pdfContent);
        console.log("content viewer url:" + this.externalContentUrl);
      }
    }
    if (this.contentMetaInfo) {
      if (this.contentMetaInfo.extension) {
        this.loading = true;
        this.ingestionMetaInfoService.getIngestionFileTypeByExtension(this.contentMetaInfo.extension).subscribe({
          next: (fileType) => {
            this.externalContentUrl = this.path + contentControllerUrl + "?code=" + encodeURIComponent(this.code);
            this.contentServedByGebo = true;
            this.ingestionFileType = fileType;
            if (this.ingestionFileType.uiViewable === true) {
              this.sourceCodeContent = fileType?.treatAs === 'sourceCode';
              this.plainTextContent = fileType?.treatAs === 'plainText';
              this.pdfContent = fileType?.extensions?.find(x => x === '.pdf') ? true : false;

              this.browsableContent = (this.contentMetaInfo?.moduleId === 'webcrawler-module' || this.contentMetaInfo?.referenceType==="WEB");
              if (this.browsableContent && this.contentMetaInfo?.url) {
                this.externalContentUrl = this.contentMetaInfo.url;
                this.contentServedByGebo = false;
              }
            }
            this.downloadableContent = fileType?.uiViewable === false;
            this.visible = fileType?.uiViewable === true;
            console.log("Content: " + this.externalContentUrl);

            console.log("content viewer sourceCodeContent:" + this.sourceCodeContent + " sourceCodeContent:" + this.sourceCodeContent + " plainTextContent:" + this.plainTextContent + " externalContent:" + this.downloadableContent + " browsableContent:" + this.browsableContent + " pdfContent:" + this.pdfContent);
            console.log("content viewer url:" + this.externalContentUrl);

            
            if ((this.sourceCodeContent || this.plainTextContent) && this.browsableContent!==true) {
              this.loading = true;
              this.contentMetaControllerService.getContentObject(this.code).subscribe({
                next: (value) => {
                  this.contentObject = value;

                  if (value.content && this.ingestionFileType?.programmingLanguage) {
                    this.sourceCodeLanguage = this.ingestionFileType?.programmingLanguage;
                  }
                },
                complete: () => {
                  this.loading = false;
                }
              });
            }
          },
          complete: () => {
            this.loading = false;
          }
        })
      }
    }
  }

  /**
   * Angular lifecycle hook that responds to input changes
   * Fetches content metadata when the content code changes and the component is activated
   * 
   * @param changes SimpleChanges object containing the changed properties
   */
  ngOnChanges(changes: SimpleChanges): void {
    if (this.code && this.code.length && changes["code"] && this.activate === true) {
      this.loading = true;
      this.contentMetaControllerService.getContentMetaInfos(this.code).subscribe({
        next: (value) => {
          this.contentMetaInfo = value;
          this.handleMetaInfo();
        },
        complete: () => {
          this.loading = false;
        }
      });
    }
    if (this.generatedContent && changes["generatedContent"]) {
      this.handleMetaInfo();
    }
    if (this.userUploadedContent && changes["userUploadedContent"]) {
      this.handleMetaInfo();
    }
  }

}