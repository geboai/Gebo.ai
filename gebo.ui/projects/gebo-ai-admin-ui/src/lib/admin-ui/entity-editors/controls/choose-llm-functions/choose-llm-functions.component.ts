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
 * 
 * This component allows users to select LLM (Large Language Model) functions from a tree structure.
 * It implements ControlValueAccessor to work with Angular forms, allowing it to be used in form controls.
 * The component fetches available functions from a service and displays them in a hierarchical tree view,
 * enabling selection of specific functions which can be used in the context of knowledge bases.
 */
import { Component, forwardRef, Input, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { ControlValueAccessor, FormControl, FormGroup, NG_VALUE_ACCESSOR } from "@angular/forms";
import { FunctionsLookupControllerService, ToolReference } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST } from "@Gebo.ai/reusable-ui";
import { TreeNode } from "primeng/api";

@Component({
    selector: "gebo-ai-choose-llm-functions-component",
    templateUrl: "choose-llm-functions.component.html",
    providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: forwardRef(() => GeboAIChooseLLMFunctionscomponent),
            multi: true
        }, { provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("GeboAIChooseLLMFunctionscomponent") }
    ],
    standalone: false
})
export class GeboAIChooseLLMFunctionscomponent implements ControlValueAccessor, OnInit, OnChanges {
    /**
     * Optional input to determine if the component is being used in a knowledge base context,
     * which affects which functions are available for selection.
     */
    @Input() knowledgeBaseContext?: boolean;

    /**
     * Flag to track if writeValue has been called to prevent premature onChange events
     * during initialization.
     */
    private writeValueHasBeenCalled: boolean = false;

    /**
     * Stores the tree structure of functions that will be displayed in the UI.
     */
    root: TreeNode<any>[] = [];

    /**
     * Form group that manages the selected functions in the tree component.
     */
    formGroup: FormGroup = new FormGroup({
        selected: new FormControl()
    });

    /**
     * Initializes the component with the functions lookup service.
     * @param functionsLookupService Service to fetch available LLM functions
     */
    constructor(private functionsLookupService: FunctionsLookupControllerService) {

    }

    /**
     * Array of selected function names that serves as the component's value.
     */
    private functionsList: string[] = [];

    /**
     * Flag to indicate when the component is loading data.
     */
    loading: boolean = false;

    /**
     * Initializes the component and sets up value change subscription to track
     * selected functions and propagate changes via the ControlValueAccessor interface.
     */
    ngOnInit(): void {
        this.formGroup.controls["selected"].valueChanges.subscribe((x: TreeNode<any>[]) => {
            if (this.writeValueHasBeenCalled) {
                const _newValue: string[] = [];
                if (x) {
                    x.forEach(x => {
                        if (x.leaf === true && x.data.name) {
                            _newValue.push(x.data.name);
                        }
                    });
                }
                this.functionsList = _newValue;
                this.onChange(this.functionsList);
            }
        });
    }

    /**
     * Responds to changes in inputs, specifically knowledgeBaseContext.
     * Fetches the function tree from the service when the context is defined.
     * @param changes SimpleChanges object containing information about changed properties
     */
    ngOnChanges(changes: SimpleChanges): void {
        if (this.knowledgeBaseContext !== undefined) {
            this.loading = true;
            this.functionsLookupService.getAllFunctionsTree(this.knowledgeBaseContext).subscribe({
                next: (trees) => {
                    const root: TreeNode<any>[] = [];
                    if (trees != null) {
                        trees.forEach(x => {
                            const item: TreeNode<any> = {
                                label: x.category?.description,
                                data: x,
                                leaf: false,
                                selectable: false
                            };
                            if (x.toolsReference) {
                                const childs: TreeNode<ToolReference>[] = [];
                                x.toolsReference.forEach(y => {
                                    childs.push({
                                        label: y.description,
                                        data: y,
                                        leaf: true
                                    });
                                });
                                item.children = childs;
                            }
                            root.push(item);
                        });
                    }
                    this.root = root;
                    this.tryDisplay();
                },
                complete: () => {
                    this.loading = false;
                }
            })

        }
    }

    /**
     * Updates the UI selection based on the current functionsList.
     * It finds the tree nodes that match the function names in functionsList
     * and updates the form control without triggering change events.
     */
    private tryDisplay(): void {
        if (this.functionsList && this.functionsList.length && this.root && this.root.length) {
            const childs: TreeNode<any>[] = [];
            this.root.forEach(x => {
                if (x.children) {
                    x.children.forEach(y => {
                        if (this.functionsList.find(s => s === y.data.name)) {
                            childs.push(y);
                        }
                    });
                }
            });
            this.formGroup.setValue({ selected: childs }, { onlySelf: true, emitEvent: false });
        } else {
            this.formGroup.setValue({ selected: [] }, { onlySelf: true, emitEvent: false });
        }
    }

    /**
     * ControlValueAccessor method to write a new value to the component.
     * @param obj The new value for the component (array of function names)
     */
    writeValue(obj: any): void {
        this.functionsList = obj;
        this.tryDisplay();
        this.writeValueHasBeenCalled = true;
    }

    /**
     * Change handler function passed by the form
     */
    onChange: any;

    /**
     * ControlValueAccessor method to register the onChange function
     * @param fn Function to call when the value changes
     */
    registerOnChange(fn: any): void {
        this.onChange = fn;
    }

    /**
     * Touch handler function passed by the form
     */
    onTouched: any;

    /**
     * ControlValueAccessor method to register the onTouched function
     * @param fn Function to call when the control is touched
     */
    registerOnTouched(fn: any): void {
        this.onTouched = fn;
    }

    /**
     * ControlValueAccessor method to enable/disable the component
     * @param isDisabled Boolean indicating if the component should be disabled
     */
    setDisabledState?(isDisabled: boolean): void {

    }
}