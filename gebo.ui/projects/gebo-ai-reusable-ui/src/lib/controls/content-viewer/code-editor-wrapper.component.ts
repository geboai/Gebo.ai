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
 * This module provides a wrapper component for the Monaco code editor.
 * It integrates with Angular's form system and allows for customizing the editor
 * with different languages, themes, and content.
 */
import { AfterViewInit, Component, Input, OnChanges, OnInit, SimpleChanges, ViewChild } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { ContentObject } from "@Gebo.ai/gebo-ai-rest-api";
import { EditorComponent, NgxEditorModel } from "ngx-monaco-editor-v2";

/**
 * Component that wraps the Monaco editor to provide code editing functionality
 * within Angular applications. It supports customization through inputs for language,
 * height, and content. Integrates with Angular's FormGroup for form handling.
 */
@Component({
    selector: "gebo-ai-code-editor",
    template: "<span  [formGroup]='formGroup'> <ngx-monaco-editor #monaco style='height: {{componentHeight}};width: 100%'  [options]='options' formControlName='content' ></ngx-monaco-editor> </span>",
    standalone: false
})
export class CodeEditorWrapperComponent implements OnInit,OnChanges,AfterViewInit{
    
    /**
     * Configuration options for the Monaco editor.
     * Sets the theme and default language.
     */
    public options = {
        theme: 'vs-dark',
        language: "java"
        

    };
    
    /**
     * Model configuration for the Monaco editor.
     * Defines the initial value and language mode.
     */
    public model: NgxEditorModel = {
        value: "",
        language: "java"
    };
    
    /** Input to set the programming language for syntax highlighting */
    @Input() language?: string;
    
    /** Input to set the component's height (defaults to 100%) */
    @Input() componentHeight:string="100%";
    
    /** Input to populate the editor with content */
    @Input() contentObject?:ContentObject;
    
    /**
     * Form group that contains the editor content as a form control.
     * Used to bind the editor to Angular's form system.
     */
    public formGroup: FormGroup = new FormGroup({
        content: new FormControl()
      });
    
    /** Reference to the Monaco editor component */
    @ViewChild("monaco") monaco?:EditorComponent;
    
    /**
     * Initializes the component with default values
     */
    constructor() {
        
    }
    
    /**
     * Lifecycle hook called after the component's view has been initialized.
     * Sets a flag to indicate the editor is inside an Angular context.
     */
    ngAfterViewInit(): void {
        if (this.monaco) {
            this.monaco.insideNg=true;
        }
    }
    
    /**
     * Lifecycle hook called when the component is initialized.
     * Sets a flag to indicate the editor is inside an Angular context.
     */
    ngOnInit(): void {
        if (this.monaco) {
            this.monaco.insideNg=true;
        }
    }
    
    /**
     * Lifecycle hook called when input properties change.
     * Updates the model and options when language changes.
     * Updates form values when contentObject changes.
     * 
     * @param changes Simple changes object containing current and previous values
     */
    ngOnChanges(changes: SimpleChanges): void {
        if (this.language && changes["language"])   {
            this.model={...this.model,language:this.language};
            this.options.language=this.language;
        }
        if (this.contentObject && changes["contentObject"]) {
            this.formGroup.patchValue(this.contentObject);
        }
    }
}