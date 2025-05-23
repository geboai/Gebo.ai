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
import { BASE_PATH, ContentMetaInfo, ContentMetaInfosControllerService, ContentObject, IngestionFileType, IngestionFileTypesLibraryControllerService } from "@Gebo.ai/gebo-ai-rest-api";

/**
 * URL for the content controller API endpoint
 */
const contentControllerUrl: string = "/api/users/ContentController";

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
  
  /** Flag to determine if the viewer should be active */
  @Input() activate: boolean = false;
  
  /** Controls the visibility of the content viewer */
  public visible: boolean = false;
  
  /** Indicates if content is being loaded */
  public loading: boolean = false;
  
  /** Metadata information about the content */
  public contentMetaInfo?: ContentMetaInfo;
  
  /** Information about the file type of the content */
  public ingestionFileType?: IngestionFileType;
  
  /** Programming language of the source code content */
  public sourceCodeLanguage: string = "";
  
  /** Flag indicating if the content is source code */
  public sourceCodeContent: boolean = false;
  
  /** Flag indicating if the content is browsable web content */
  public browsableContent: boolean = false;
  
  /** Flag indicating if the content should be downloaded rather than viewed */
  public downloadableContent: boolean = false;
  
  /** Flag indicating if the content is plain text */
  public plainTextContent: boolean = false;
  
  /** Flag indicating if the content is a PDF document */
  public pdfContent: boolean = false;
  
  /** The actual content object with data */
  public contentObject?: ContentObject;
  
  /** URL for external content viewing */
  public externalContentUrl: string = "javascript:void(0)";

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
  public closeMe(evt:any) {
    this.visible=false;
    this.close.emit(true);
  }

  /**
   * Processes the content metadata to determine how to display the content
   * Sets flags for different content types and prepares the external content URL
   */
  private handleMetaInfo() {
    if (this.contentMetaInfo) {
      if (this.contentMetaInfo.extension) {
        this.loading = true;
        this.ingestionMetaInfoService.getIngestionFileTypeByExtension(this.contentMetaInfo.extension).subscribe({
          next: (fileType) => {
            this.externalContentUrl = this.path + contentControllerUrl + "?code=" + encodeURIComponent(this.code);
            this.ingestionFileType = fileType;
            if (this.ingestionFileType.uiViewable === true) {
              this.sourceCodeContent = fileType?.treatAs === 'sourceCode';
              this.plainTextContent = fileType?.treatAs === 'plainText';
              this.pdfContent = fileType?.extensions?.find(x => x === '.pdf') ? true : false;

              this.browsableContent = this.contentMetaInfo?.moduleId === 'webcrawler-module';
              if (this.browsableContent && this.contentMetaInfo?.url) {
                this.externalContentUrl = this.contentMetaInfo.url;
              }
            }
            this.downloadableContent = fileType?.uiViewable === false;
            this.visible =fileType?.uiViewable === true;
            console.log("Content: " + this.externalContentUrl);
            
            console.log("content viewer sourceCodeContent:" + this.sourceCodeContent + " sourceCodeContent:" + this.sourceCodeContent + " plainTextContent:" + this.plainTextContent + " externalContent:" + this.downloadableContent + " browsableContent:" + this.browsableContent + " pdfContent:" + this.pdfContent);
            console.log("content viewer url:" + this.externalContentUrl);
            
            this.loading = true;
            if (this.sourceCodeContent || this.plainTextContent) {
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
  }

}