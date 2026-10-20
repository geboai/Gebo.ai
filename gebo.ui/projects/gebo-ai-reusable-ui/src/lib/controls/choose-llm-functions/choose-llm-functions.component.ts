/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
import { Component, forwardRef, Input, OnChanges, OnDestroy, OnInit, SimpleChanges } from "@angular/core";
import { AbstractControl, ControlValueAccessor, NG_VALUE_ACCESSOR } from "@angular/forms";
import { FunctionsLookupControllerService, ToolCategoriesTree, ToolReference } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../field-host-component-iface/field-host-component-iface";
import { Observable, Subscription } from "rxjs";

/** A tool of the catalogue, as the picker shows it. */
export interface PickerTool {
    name: string;
    /** What the tool does, told for the admin when the tool says so, else as the model reads it. */
    description: string;
    /** The description the model reads: on hover. */
    modelDescription: string;
    categoryCode: string;
    categoryLabel: string;
    reference: ToolReference;
}

/** A category of the catalogue with its tools. */
export interface PickerCategory {
    code: string;
    label: string;
    /** The colour of the category: the categories take the colours in turn. */
    colorClass: string;
    tools: PickerTool[];
}

/**
 * The picker of the tools an agent, a chat profile or an MCP server may use: the
 * chosen tools are shown as chips, the catalogue lists every registered tool by
 * category with its name and what it does, to be picked one by one, by category,
 * or found by name. The value is the list of the chosen tool names.
 * <p>
 * Given the control that mounts every registered tool ({@link #everyToolsControl}),
 * the picker shows the switch between "every tool" and "chosen tools": with every
 * tool on, the choice is kept but not used and the catalogue is disabled.
 */
@Component({
    selector: "gebo-ai-choose-llm-functions-component",
    templateUrl: "choose-llm-functions.component.html",
    providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: forwardRef(() => GeboAIChooseLLMFunctionscomponent),
            multi: true
        }, { provide: GEBO_AI_MODULE, useValue: "GeboAIChooseLLMFunctionsModule", multi: false }, { provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("GeboAIChooseLLMFunctionscomponent") }
    ],
    standalone: false
})
export class GeboAIChooseLLMFunctionscomponent implements ControlValueAccessor, OnInit, OnChanges, OnDestroy {
    @Input() knowledgeBaseContext?: boolean;
    @Input() escludeExternalTools?: boolean = false;
    /**
     * The control saying that every registered tool is mounted, when the host has
     * one: the picker's switch drives it, and the catalogue is disabled while it is on.
     */
    @Input() everyToolsControl?: AbstractControl<boolean | null | undefined>;

    /** The colours of the categories, in turn: calm tones, the warm ones are kept for warnings. */
    private static readonly CATEGORY_CLASSES = [
        "bg-blue-50 text-blue-700", "bg-teal-50 text-teal-700", "bg-purple-50 text-purple-700",
        "bg-cyan-50 text-cyan-700", "bg-indigo-50 text-indigo-700", "bg-bluegray-50 text-bluegray-700",
        "bg-green-50 text-green-700"
    ];

    categories: PickerCategory[] = [];
    private toolsByName = new Map<string, PickerTool>();
    /** The names chosen, in the order they were chosen. */
    private chosenNames: string[] = [];
    /** The category shown, null for all of them. */
    categoryFilter: string | null = null;
    query: string = "";
    loading: boolean = false;
    disabled: boolean = false;
    private writeValueHasBeenCalled: boolean = false;
    private everyToolsSubscription?: Subscription;

    constructor(private functionsLookupService: FunctionsLookupControllerService) {
    }

    ngOnInit(): void {
        this.watchEveryTools();
    }

    ngOnChanges(changes: SimpleChanges): void {
        if (changes["everyToolsControl"]) {
            this.watchEveryTools();
        }
        if (changes["knowledgeBaseContext"] || changes["escludeExternalTools"]) {
            this.reloadFunctions();
        }
    }

    ngOnDestroy(): void {
        this.everyToolsSubscription?.unsubscribe();
    }

    private watchEveryTools(): void {
        this.everyToolsSubscription?.unsubscribe();
        this.everyToolsSubscription = this.everyToolsControl?.valueChanges.subscribe(value => {
            console.debug("[tool-picker] every tool: " + (value === true));
        });
    }

    // ---------------------------------------------------------------------
    // The catalogue
    // ---------------------------------------------------------------------

    private reloadFunctions(): void {
        this.loading = true;
        const observable: Observable<ToolCategoriesTree[]> = this.escludeExternalTools !== true ? this.functionsLookupService.getAllFunctionsTree(this.knowledgeBaseContext) : this.functionsLookupService.getAllLocalFunctionsTree(this.knowledgeBaseContext);
        observable.subscribe({
            next: (trees) => {
                const categories: PickerCategory[] = [];
                const byName = new Map<string, PickerTool>();
                (trees || []).forEach((tree, index) => {
                    const code = tree.category?.code || String(index);
                    const label = tree.category?.description || code;
                    const category: PickerCategory = {
                        code, label,
                        colorClass: GeboAIChooseLLMFunctionscomponent.CATEGORY_CLASSES[index % GeboAIChooseLLMFunctionscomponent.CATEGORY_CLASSES.length],
                        tools: []
                    };
                    (tree.toolsReference || []).forEach(reference => {
                        if (!reference.name) {
                            return;
                        }
                        const tool: PickerTool = {
                            name: reference.name,
                            description: reference.userUIfunctionDescription || reference.description || "",
                            modelDescription: reference.description || "",
                            categoryCode: code, categoryLabel: label, reference
                        };
                        category.tools.push(tool);
                        byName.set(tool.name, tool);
                    });
                    category.tools.sort((a, b) => a.name.localeCompare(b.name));
                    categories.push(category);
                });
                this.categories = categories;
                this.toolsByName = byName;
                console.debug("[tool-picker] catalogue: " + categories.length + " category(ies), " + byName.size + " tool(s), chosen before: " + this.chosenNames.length);
                this.dropUnknownChoices();
            },
            error: (error) => console.error("Cannot load the tools catalogue", error),
            complete: () => {
                this.loading = false;
            }
        });
    }

    /** The names chosen that the catalogue does not know are dropped, as the former chooser did. */
    private dropUnknownChoices(): void {
        if (this.toolsByName.size === 0 || !this.writeValueHasBeenCalled) {
            return;
        }
        const known = this.chosenNames.filter(name => this.toolsByName.has(name));
        if (known.length !== this.chosenNames.length) {
            console.debug("[tool-picker] " + (this.chosenNames.length - known.length) + " chosen tool(s) unknown to the catalogue dropped");
            this.chosenNames = known;
            this.emit();
        }
    }

    /** The categories with at least one tool: the others have nothing to pick. */
    get nonEmptyCategories(): PickerCategory[] {
        return this.categories.filter(c => c.tools.length > 0);
    }

    get toolsCount(): number {
        return this.toolsByName.size;
    }

    get everyTools(): boolean {
        return this.everyToolsControl?.value === true;
    }

    /** Whether the catalogue can be used: not disabled, and the choice is the mode. */
    get pickable(): boolean {
        return !this.disabled && !this.everyTools;
    }

    get chosenTools(): PickerTool[] {
        return this.chosenNames.map(name => this.toolsByName.get(name)).filter((t): t is PickerTool => !!t);
    }

    isChosen(tool: PickerTool): boolean {
        return this.chosenNames.includes(tool.name);
    }

    chosenInCategory(category: PickerCategory): number {
        return category.tools.filter(t => this.isChosen(t)).length;
    }

    /** The categories shown, with the tools matching the filter and the query. */
    get visibleCategories(): PickerCategory[] {
        const q = this.query.trim().toLowerCase();
        return this.categories
            .filter(c => this.categoryFilter === null || c.code === this.categoryFilter)
            .map(c => ({ ...c, tools: c.tools.filter(t => !q || t.name.toLowerCase().includes(q) || t.description.toLowerCase().includes(q)) }))
            .filter(c => c.tools.length > 0);
    }

    allChosen(category: PickerCategory): boolean {
        return category.tools.length > 0 && category.tools.every(t => this.isChosen(t));
    }

    // ---------------------------------------------------------------------
    // The choice
    // ---------------------------------------------------------------------

    setMode(everyTools: boolean): void {
        if (!this.everyToolsControl || this.disabled || this.everyTools === everyTools) {
            return;
        }
        console.debug("[tool-picker] mode: " + (everyTools ? "every tool" : "chosen tools"));
        this.everyToolsControl.setValue(everyTools);
        this.everyToolsControl.markAsDirty();
    }

    toggle(tool: PickerTool): void {
        if (!this.pickable) {
            return;
        }
        if (this.isChosen(tool)) {
            this.chosenNames = this.chosenNames.filter(n => n !== tool.name);
        } else {
            this.chosenNames = [...this.chosenNames, tool.name];
        }
        console.debug("[tool-picker] " + tool.name + (this.isChosen(tool) ? " chosen" : " dropped") + ", " + this.chosenNames.length + " chosen");
        this.emit();
    }

    /** Chooses, or drops, every tool of the category shown. */
    setCategory(category: PickerCategory, chosen: boolean): void {
        if (!this.pickable) {
            return;
        }
        const names = category.tools.map(t => t.name);
        this.chosenNames = chosen
            ? [...this.chosenNames, ...names.filter(n => !this.chosenNames.includes(n))]
            : this.chosenNames.filter(n => !names.includes(n));
        console.debug("[tool-picker] category " + category.code + (chosen ? " chosen" : " dropped") + ", " + this.chosenNames.length + " chosen");
        this.emit();
    }

    remove(tool: PickerTool): void {
        if (this.disabled) {
            return;
        }
        this.chosenNames = this.chosenNames.filter(n => n !== tool.name);
        this.emit();
    }

    clear(): void {
        if (!this.pickable || this.chosenNames.length === 0) {
            return;
        }
        this.chosenNames = [];
        console.debug("[tool-picker] choice cleared");
        this.emit();
    }

    private emit(): void {
        if (this.onChange) {
            this.onChange([...this.chosenNames]);
        }
        if (this.onTouched) {
            this.onTouched();
        }
    }

    // ---------------------------------------------------------------------
    // ControlValueAccessor
    // ---------------------------------------------------------------------

    writeValue(obj: any): void {
        this.chosenNames = Array.isArray(obj) ? [...obj] : [];
        this.writeValueHasBeenCalled = true;
        this.dropUnknownChoices();
    }

    onChange: any;
    registerOnChange(fn: any): void {
        this.onChange = fn;
    }

    onTouched: any;
    registerOnTouched(fn: any): void {
        this.onTouched = fn;
    }

    setDisabledState(isDisabled: boolean): void {
        this.disabled = isDisabled;
    }
}
