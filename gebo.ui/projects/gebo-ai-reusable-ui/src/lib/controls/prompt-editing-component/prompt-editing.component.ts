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
 * Editor for a single {@link GPromptTemplateConfig}. Implements both
 * ControlValueAccessor (value in/out via Angular forms) and Validator (so the
 * host form control reflects the completeness rules of a prompt template).
 * The system/user templates are edited with two separate Monaco editors and the
 * documented placeholders (loaded as a {@link GPromptUseInfo} via its use code)
 * are highlighted and cross-checked against the edited text.
 */
import { ChangeDetectorRef, Component, forwardRef, Input, OnDestroy, OnInit, ViewEncapsulation } from "@angular/core";
import {
    AbstractControl, ControlValueAccessor, FormControl, FormGroup, NG_VALIDATORS, NG_VALUE_ACCESSOR,
    ValidationErrors, Validator, Validators
} from "@angular/forms";
import { GeboAdminPromptsControllerService, GPromptPlaceholderInfo, GPromptTemplateConfig, GPromptUseInfo } from "@Gebo.ai/gebo-ai-rest-api";
import { Subscription } from "rxjs";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../field-host-component-iface/field-host-component-iface";

/**
 * Highlight CSS class applied by Monaco to placeholder occurrences.
 */
const PLACEHOLDER_HIGHLIGHT_CLASS = "gebo-prompt-placeholder-highlight";

/**
 * CSS class applied by Monaco to the placeholders nothing fills.
 */
const UNKNOWN_PLACEHOLDER_CLASS = "gebo-prompt-placeholder-unknown";

/**
 * A placeholder as the backend reads it from a template ({name}, the name trimmed).
 */
const PLACEHOLDER_PATTERN = /\{([^{}]+)\}/g;

/**
 * The name inside the braces trimmed as Java's String.trim() does: only the
 * characters up to U+0020, so a name means here what it means to the save.
 */
function trimAsJava(name: string): string {
    return name.replace(/^[\u0000- ]+|[\u0000- ]+$/g, "");
}

/**
 * Component for editing an AI prompt template configuration.
 * Every field except the (read-only) prompt use code is editable; the system and
 * user prompt templates are edited via Monaco, with the documented placeholders
 * highlighted. The control is valid only when every mandatory field is filled and
 * all documented placeholders appear in the concatenated template text; with
 * checkUnknownPlaceholders, also only when the templates name nothing the
 * prompt templates save would refuse.
 */
@Component({
    selector: "gebo-ai-prompt-editing-component",
    templateUrl: "prompt-editing.component.html",
    styleUrls: ["prompt-editing.component.css"],
    encapsulation: ViewEncapsulation.None,
    standalone: false,
    providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: forwardRef(() => GeboAIPromptEditingComponent),
            multi: true
        },
        {
            provide: NG_VALIDATORS,
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
export class GeboAIPromptEditingComponent implements ControlValueAccessor, Validator, OnInit, OnDestroy {

    /** The full template being edited; form values are merged back into this object. */
    private currentValue?: GPromptTemplateConfig;

    /** Catalog entry (description + documented placeholders) for the current use code. */
    public useInfo?: GPromptUseInfo;

    /** Documented placeholders of the current prompt use, shown in the reference table. */
    public placeholders: GPromptPlaceholderInfo[] = [];

    /** Documented placeholder codes that are NOT yet present in the template text. */
    public missingPlaceholders: string[] = [];

    /** Placeholders in the template text that nothing fills: the save refuses such a prompt. */
    public unknownPlaceholders: string[] = [];

    /**
     * What the template may name, as the save checks it; null while unknown or
     * when the save does not check the names of such a prompt.
     */
    private allowedPlaceholders: string[] | null = null;

    /** The use and agent flag the allowed names were asked for. */
    private allowedPlaceholdersKey?: string;

    private checkUnknown: boolean = false;

    /**
     * Also flags the placeholders nothing fills, as the prompt templates save
     * does. Off where the edited prompt is not saved as a prompt template (an
     * agent's own loop prompt), which no such check refuses.
     */
    @Input()
    set checkUnknownPlaceholders(check: boolean) {
        this.checkUnknown = check === true;
        this.allowedPlaceholdersKey = undefined;
        this.refreshAllowedPlaceholders();
    }

    get checkUnknownPlaceholders(): boolean {
        return this.checkUnknown;
    }

    /** Loading flag driving the panel block-ui while the use info is fetched. */
    public loading: boolean = false;

    /** Choices for the REQUIRED/NOT_REQUIRED context flag selects. */
    public readonly contextContentOptions = [
        { value: GPromptTemplateConfig.ChatHistoryEnum.REQUIRED, labelCode: "REQUIRED" },
        { value: GPromptTemplateConfig.ChatHistoryEnum.NOTREQUIRED, labelCode: "NOT_REQUIRED" }
    ];

    /** Shared Monaco editor options for the template editors. */
    public options = {
        theme: "vs-dark",
        language: "markdown",
        automaticLayout: true,
        minimap: { enabled: false },
        wordWrap: "on"
    };

    /** Reactive form backing every editable field of the template. */
    formGroup: FormGroup = new FormGroup({
        code: new FormControl<string | null | undefined>({ value: null, disabled: true }),
        promptUse: new FormControl<string | null | undefined>({ value: null, disabled: true }),
        description: new FormControl<string | null | undefined>(null),
        systemPromptTemplate: new FormControl<string | null | undefined>(null),
        userPromptTemplate: new FormControl<string | null | undefined>(null, [Validators.required]),
        toolsResultsPromptTemplate: new FormControl<string | null | undefined>(null),
        chatHistory: new FormControl<GPromptTemplateConfig.ChatHistoryEnum | null | undefined>(null, [Validators.required]),
        contextDocuments: new FormControl<GPromptTemplateConfig.ContextDocumentsEnum | null | undefined>(null, [Validators.required]),
        toolsCalling: new FormControl<GPromptTemplateConfig.ToolsCallingEnum | null | undefined>(null, [Validators.required]),
        langCode: new FormControl<string | null | undefined>(null),
        modelProvider: new FormControl<string | null | undefined>(null),
        modelCode: new FormControl<string | null | undefined>(null),
        promptCategory: new FormControl<string | null | undefined>(null),
        agentPrompt: new FormControl<boolean | null | undefined>(null),
        agentId: new FormControl<string | null | undefined>(null)
    });

    /** The Monaco editor instances, once initialized. */
    private systemEditor?: any;
    private userEditor?: any;
    private toolsResultsEditor?: any;

    /** Monaco decoration ids currently applied to each editor. */
    private systemDecorationIds: string[] = [];
    private userDecorationIds: string[] = [];
    private toolsResultsDecorationIds: string[] = [];

    private formSubscription?: Subscription;

    constructor(private promptsService: GeboAdminPromptsControllerService,
        private changeDetectorRef: ChangeDetectorRef) {
    }

    /**
     * Subscribes to form changes so the value is emitted, the validity recomputed
     * and the placeholder highlighting/reporting kept in sync while editing.
     */
    ngOnInit(): void {
        this.formSubscription = this.formGroup.valueChanges.subscribe(() => {
            this.emitValue();
            this.refreshAllowedPlaceholders();
            this.recomputeMissingPlaceholders();
            this.recomputeUnknownPlaceholders();
            this.applyDecorations(this.systemEditor);
            this.applyDecorations(this.userEditor);
            this.applyDecorations(this.toolsResultsEditor);
            this.onValidatorChange();
        });
    }

    ngOnDestroy(): void {
        this.formSubscription?.unsubscribe();
    }

    /**
     * Writes a template into the editor and loads its documented placeholders.
     * @param obj the GPromptTemplateConfig to edit (or null to clear)
     */
    writeValue(obj: GPromptTemplateConfig | null | undefined): void {
        this.currentValue = obj ? { ...obj } : undefined;
        this.formGroup.patchValue({
            code: obj?.code ?? null,
            promptUse: obj?.promptUse ?? null,
            description: obj?.description ?? null,
            systemPromptTemplate: obj?.systemPromptTemplate ?? null,
            userPromptTemplate: obj?.userPromptTemplate ?? null,
            toolsResultsPromptTemplate: obj?.toolsResultsPromptTemplate ?? null,
            chatHistory: obj?.chatHistory ?? null,
            contextDocuments: obj?.contextDocuments ?? null,
            toolsCalling: obj?.toolsCalling ?? null,
            langCode: obj?.langCode ?? null,
            modelProvider: obj?.modelProvider ?? null,
            modelCode: obj?.modelCode ?? null,
            promptCategory: obj?.promptCategory ?? null,
            agentPrompt: obj?.agentPrompt ?? null,
            agentId: obj?.agentId ?? null
        }, { emitEvent: false });
        this.loadUseInfo(obj?.promptUse);
        this.refreshAllowedPlaceholders();
    }

    /**
     * Asks the backend what the template may name, when the use or the agent
     * flag changed since it was last asked; until it answers nothing is flagged.
     */
    private refreshAllowedPlaceholders(): void {
        const raw = this.formGroup.getRawValue();
        const useCode: string | null | undefined = raw.promptUse;
        const agentPrompt: boolean = raw.agentPrompt === true;
        const key = this.checkUnknown && useCode ? useCode + "|" + agentPrompt : "";
        if (key === this.allowedPlaceholdersKey) {
            return;
        }
        this.allowedPlaceholdersKey = key;
        this.allowedPlaceholders = null;
        this.refreshPlaceholderChecks();
        if (!key || !useCode) {
            return;
        }
        this.promptsService.getAllowedPromptPlaceholders(useCode, agentPrompt).subscribe({
            next: (allowed) => {
                if (this.allowedPlaceholdersKey !== key) {
                    return;
                }
                this.allowedPlaceholders = allowed?.checked ? (allowed.allowed ?? []) : null;
                this.refreshPlaceholderChecks();
                this.changeDetectorRef.markForCheck();
            },
            error: () => {
                // nothing flagged here: the save still checks
            }
        });
    }

    /** Recomputes both placeholder checks, the highlighting and the validity. */
    private refreshPlaceholderChecks(): void {
        this.recomputeMissingPlaceholders();
        this.recomputeUnknownPlaceholders();
        this.applyDecorations(this.systemEditor);
        this.applyDecorations(this.userEditor);
        this.applyDecorations(this.toolsResultsEditor);
        this.onValidatorChange();
    }

    /**
     * Loads the GPromptUseInfo catalog entry for a use code and refreshes the
     * placeholder table, highlighting and validity.
     * @param useCode the prompt use code, or null/undefined to reset
     */
    private loadUseInfo(useCode: string | null | undefined): void {
        if (!useCode) {
            this.useInfo = undefined;
            this.placeholders = [];
            this.recomputeMissingPlaceholders();
            this.applyDecorations(this.systemEditor);
            this.applyDecorations(this.userEditor);
            this.applyDecorations(this.toolsResultsEditor);
            this.onValidatorChange();
            return;
        }
        this.loading = true;
        this.promptsService.findGPromptUseInfoByUseCode(useCode).subscribe({
            next: (info) => {
                this.useInfo = info ?? undefined;
                this.placeholders = info?.placeholders ?? [];
            },
            error: () => {
                this.useInfo = undefined;
                this.placeholders = [];
                this.loading = false;
                this.recomputeMissingPlaceholders();
                this.applyDecorations(this.systemEditor);
                this.applyDecorations(this.userEditor);
                this.applyDecorations(this.toolsResultsEditor);
                this.onValidatorChange();
                this.changeDetectorRef.markForCheck();
            },
            complete: () => {
                this.loading = false;
                this.recomputeMissingPlaceholders();
                this.applyDecorations(this.systemEditor);
                this.applyDecorations(this.userEditor);
                this.applyDecorations(this.toolsResultsEditor);
                this.onValidatorChange();
                this.changeDetectorRef.markForCheck();
            }
        });
    }

    /**
     * Concatenation of the system, user and tools results templates, used for
     * placeholder checks.
     */
    private concatenatedTemplateText(): string {
        const system = this.formGroup.controls["systemPromptTemplate"].value ?? "";
        const user = this.formGroup.controls["userPromptTemplate"].value ?? "";
        const toolsResults = this.formGroup.controls["toolsResultsPromptTemplate"].value ?? "";
        return system + "\n" + user + "\n" + toolsResults;
    }

    /**
     * The placeholder token as it must appear in the template grammar, e.g. {code}.
     */
    private placeholderToken(code: string): string {
        return "{" + code + "}";
    }

    /**
     * Recomputes the list of documented placeholder codes still missing from the
     * concatenated template text.
     */
    private recomputeMissingPlaceholders(): void {
        const text = this.concatenatedTemplateText();
        this.missingPlaceholders = this.placeholders
            .map(p => p.placeholder)
            .filter((code): code is string => !!code)
            .filter(code => text.indexOf(this.placeholderToken(code)) < 0);
    }

    /**
     * The placeholders the system, user and tools results templates name, each
     * template read on its own as the backend reads it.
     */
    private namedPlaceholders(): string[] {
        const names: string[] = [];
        for (const field of ["systemPromptTemplate", "userPromptTemplate", "toolsResultsPromptTemplate"]) {
            const text: string = this.formGroup.controls[field].value ?? "";
            for (const match of text.matchAll(PLACEHOLDER_PATTERN)) {
                const name = trimAsJava(match[1]);
                if (name.length > 0 && names.indexOf(name) < 0) {
                    names.push(name);
                }
            }
        }
        return names;
    }

    /**
     * Recomputes the placeholders the template names that nothing fills, when the
     * names are checked and the backend said what may be named.
     */
    private recomputeUnknownPlaceholders(): void {
        const allowed = this.checkUnknown ? this.allowedPlaceholders : null;
        this.unknownPlaceholders = allowed ? this.namedPlaceholders().filter(name => allowed.indexOf(name) < 0) : [];
    }

    /**
     * Rebuilds the current value from the form and notifies the forms API.
     */
    private emitValue(): void {
        const raw = this.formGroup.getRawValue();
        const value: GPromptTemplateConfig = {
            ...(this.currentValue ?? {} as GPromptTemplateConfig),
            code: raw.code ?? undefined,
            promptUse: raw.promptUse,
            description: raw.description ?? undefined,
            systemPromptTemplate: raw.systemPromptTemplate ?? undefined,
            userPromptTemplate: raw.userPromptTemplate,
            toolsResultsPromptTemplate: raw.toolsResultsPromptTemplate ?? undefined,
            chatHistory: raw.chatHistory,
            contextDocuments: raw.contextDocuments,
            toolsCalling: raw.toolsCalling,
            langCode: raw.langCode ?? undefined,
            modelProvider: raw.modelProvider ?? undefined,
            modelCode: raw.modelCode ?? undefined,
            promptCategory: raw.promptCategory ?? undefined,
            agentPrompt: raw.agentPrompt ?? undefined,
            agentId: raw.agentId ?? undefined
        };
        this.currentValue = value;
        this.onChange(value);
    }

    /**
     * Validates the edited template: every mandatory field must be filled and all
     * documented placeholders must be present in the concatenated template text.
     * @param control the host control (unused; internal state is authoritative)
     * @returns the validation errors, or null when the template is complete
     */
    validate(control: AbstractControl): ValidationErrors | null {
        const errors: ValidationErrors = {};
        if (this.formGroup.invalid) {
            errors["requiredFields"] = true;
        }
        this.recomputeMissingPlaceholders();
        if (this.missingPlaceholders.length > 0) {
            errors["missingPlaceholders"] = { missing: this.missingPlaceholders };
        }
        this.recomputeUnknownPlaceholders();
        if (this.unknownPlaceholders.length > 0) {
            errors["unknownPlaceholders"] = { unknown: this.unknownPlaceholders };
        }
        return Object.keys(errors).length > 0 ? errors : null;
    }

    /**
     * Captures the Monaco editor for the system template.
     * @param editor the Monaco editor instance
     */
    onSystemEditorInit(editor: any): void {
        this.systemEditor = editor;
        this.applyDecorations(this.systemEditor);
    }

    /**
     * Captures the Monaco editor for the user template.
     * @param editor the Monaco editor instance
     */
    onUserEditorInit(editor: any): void {
        this.userEditor = editor;
        this.applyDecorations(this.userEditor);
    }

    /**
     * Captures the Monaco editor for the tools results template.
     * @param editor the Monaco editor instance
     */
    onToolsResultsEditorInit(editor: any): void {
        this.toolsResultsEditor = editor;
        this.applyDecorations(this.toolsResultsEditor);
    }

    /** Whether a decoration pass is running, and the editors still to decorate. */
    private decorating: boolean = false;
    private editorsToDecorate: Set<any> = new Set<any>();

    /**
     * Decorates the given editor. A multi-character edit (a paste) has Monaco emit
     * its content changes while the decorations of the first one are being set, so
     * this is entered again before that pass has kept its decoration ids: the
     * nested request is queued and run after it, with the ids it kept, instead of
     * leaving decorations nobody removes.
     * @param editor the Monaco editor to decorate
     */
    private applyDecorations(editor: any): void {
        if (!editor) {
            return;
        }
        this.editorsToDecorate.add(editor);
        if (this.decorating) {
            return;
        }
        this.decorating = true;
        try {
            while (this.editorsToDecorate.size > 0) {
                const next = this.editorsToDecorate.values().next().value;
                this.editorsToDecorate.delete(next);
                this.decorate(next);
            }
        } finally {
            this.decorating = false;
        }
    }

    /**
     * Highlights every documented placeholder occurrence ({code}) in the given
     * editor, and marks the placeholders nothing fills.
     * @param editor the Monaco editor to decorate
     */
    private decorate(editor: any): void {
        const model = editor.getModel ? editor.getModel() : undefined;
        const monaco = (window as any).monaco;
        if (!model || !monaco) {
            return;
        }
        const decorations: any[] = [];
        for (const placeholder of this.placeholders) {
            const code = placeholder.placeholder;
            if (!code) {
                continue;
            }
            const token = this.placeholderToken(code);
            const matches = model.findMatches(token, false, false, true, null, false);
            for (const match of matches) {
                decorations.push({
                    range: match.range,
                    options: {
                        inlineClassName: PLACEHOLDER_HIGHLIGHT_CLASS,
                        hoverMessage: placeholder.description ? { value: placeholder.description } : undefined
                    }
                });
            }
        }
        if (this.unknownPlaceholders.length > 0) {
            const matches = model.findMatches(PLACEHOLDER_PATTERN.source, false, true, true, null, true);
            for (const match of matches) {
                const name = trimAsJava(match.matches?.[1] ?? "");
                if (this.unknownPlaceholders.indexOf(name) >= 0) {
                    decorations.push({
                        range: match.range,
                        options: { inlineClassName: UNKNOWN_PLACEHOLDER_CLASS }
                    });
                }
            }
        }
        if (editor === this.systemEditor) {
            this.systemDecorationIds = editor.deltaDecorations(this.systemDecorationIds, decorations);
        } else if (editor === this.userEditor) {
            this.userDecorationIds = editor.deltaDecorations(this.userDecorationIds, decorations);
        } else if (editor === this.toolsResultsEditor) {
            this.toolsResultsDecorationIds = editor.deltaDecorations(this.toolsResultsDecorationIds, decorations);
        }
    }

    /** ControlValueAccessor change callback. */
    private onChange: (d: GPromptTemplateConfig) => void = () => { };

    registerOnChange(fn: any): void {
        this.onChange = fn;
    }

    /** ControlValueAccessor touched callback. */
    private onTouched: () => void = () => { };

    registerOnTouched(fn: any): void {
        this.onTouched = fn;
    }

    /** Validator change callback, invoked when the validity may have changed. */
    private onValidatorChange: () => void = () => { };

    registerOnValidatorChange(fn: () => void): void {
        this.onValidatorChange = fn;
    }

    /**
     * Enables/disables the editable controls (the code and use code stay disabled).
     * @param isDisabled whether the editor should be read-only
     */
    setDisabledState(isDisabled: boolean): void {
        if (isDisabled) {
            this.formGroup.disable({ emitEvent: false });
        } else {
            this.formGroup.enable({ emitEvent: false });
            this.formGroup.controls["code"].disable({ emitEvent: false });
            this.formGroup.controls["promptUse"].disable({ emitEvent: false });
        }
    }
}
