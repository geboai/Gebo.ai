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
 * This component provides a prompt editing interface using the Monaco editor.
 * It implements ControlValueAccessor to integrate with Angular forms, allowing
 * it to be used within form controls. The component supports customizable rows,
 * columns, and placeholders for the editor.
 */
import { Component, forwardRef, Input, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { ControlValueAccessor, FormControl, FormGroup, NG_VALUE_ACCESSOR } from "@angular/forms";
import { NgxEditorModel } from "ngx-monaco-editor-v2";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../field-host-component-iface/field-host-component-iface";

/**
 * A component for editing AI prompts using Monaco editor.
 * This component provides a rich text editing experience for prompts
 * and integrates with Angular's form system via ControlValueAccessor.
 */
@Component({
    selector: "gebo-ai-prompt-editing-component",
    templateUrl: "prompt-editing.component.html",
    standalone: false,
    providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: forwardRef(() => GeboAIPromptEditingComponent),
            multi: true
        },
        {
            provide: GEBO_AI_MODULE, useValue: "PromptEditingModule", multi: false
        },
        {
            provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("PromptEditingComponent")
        }
    ],
})
export class GeboAIPromptEditingComponent implements ControlValueAccessor, OnChanges, OnInit {
    /**
     * Stores the current prompt text value
     */
    private promptText?: string;

    /**
     * Configuration options for the Monaco editor
     */
    public options = {
        theme: 'vs-dark',
        language: "java"


    };

    /**
     * Model configuration for the Monaco editor
     */
    public model: NgxEditorModel = {
        value: "",
        language: "java"
    };

    /**
     * Number of rows to display in the editor
     */
    @Input() rows: number = 5;

    /**
     * Number of columns to display in the editor
     */
    @Input() cols: number = 80;

    /**
     * Placeholder definitions that can be used in the prompt
     * Each placeholder has a code, description, and required flag
     */
    @Input() placeHolders: { code: string, description: string, required: boolean }[] = [];

    /**
     * Form group that contains the prompt control
     */
    formGroup: FormGroup = new FormGroup({
        prompt: new FormControl()
    });

    /**
     * Initializes the component
     */
    ngOnInit(): void {

    }

    /**
     * Handles changes to the component's input properties
     * @param changes The changes object containing all changed inputs
     */
    ngOnChanges(changes: SimpleChanges): void {

    }

    /**
     * Writes a value to the editor from the form model
     * @param obj The value to write to the editor
     */
    writeValue(obj: any): void {
        this.promptText = obj;
        this.formGroup.patchValue(
            {
                prompt: this.promptText
            }
        );
    }

    /**
     * Function to call when the input value changes
     */
    private onChange: (d: any) => void = (d: any) => { };

    /**
     * Registers a callback function that is called when the value changes
     * @param fn The callback function
     */
    registerOnChange(fn: any): void {
        this.onChange = fn;
    }

    /**
     * Function to call when the input is touched
     */
    private onTouched: (d: any) => void = (d: any) => { };

    /**
     * Registers a callback function that is called when the input is touched
     * @param fn The callback function
     */
    registerOnTouched(fn: any): void {
        this.onTouched = fn;
    }

    /**
     * Sets the disabled state of the editor
     * @param isDisabled Whether the editor should be disabled
     */
    setDisabledState?(isDisabled: boolean): void {

    }

}