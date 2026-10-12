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
 * Entity editor for a GPromptTemplateConfig. The whole template is edited by a
 * single embedded prompt-editing control (one form control), and the backend
 * lifecycle honours the prompt-templates rules:
 *  - editing (save/delete) is globally gated by the server flag
 *    ai.gebo.prompt-templates.editingEnabled;
 *  - a template declared in the .yml library (configDeclarated) is never mutated
 *    in place: saving it creates a mongo override (insert with the static flag
 *    cleared), which then resolves ahead of the static one;
 *  - only mongo templates can be deleted; static ones cannot.
 */
import { Component, forwardRef, Injector } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { GeboAdminPromptsControllerService, GPromptTemplateConfig } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { map, Observable, of } from "rxjs";

/**
 * Admin editor for prompt template configurations.
 */
@Component({
    selector: "gebo-ai-prompt-admin-component",
    templateUrl: "gebo-ai-prompt-admin.component.html",
    standalone: false, providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIPromptAdminModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIPromptAdminComponent), multi: false }
    ]
})
export class GeboAIPromptAdminComponent extends BaseEntityEditingComponent<GPromptTemplateConfig> {
    /** Name of the entity being managed by this component */
    protected override entityName: string = "GPromptConfig";

    /** Single control holding the whole template, edited by the prompt-editing control. */
    override formGroup: FormGroup<any> = new FormGroup({
        promptTemplate: new FormControl<GPromptTemplateConfig | null>(null)
    });

    /** Whether prompt template editing is enabled for this deployment (server flag). */
    public editingEnabled: boolean = false;

    /** ConfirmationService kept for the delete confirmation dialog. */
    private confirmSvc: ConfirmationService;

    constructor(injector: Injector, geboFormGroupsService: GeboFormGroupsService,
        private geboPromptConfigurationService: GeboAdminPromptsControllerService,
        confirmService: ConfirmationService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        outputForwardingService?: GeboUIOutputForwardingService) {
        super(injector, geboFormGroupsService, confirmService, geboUIActionRoutingService, outputForwardingService);
        this.confirmSvc = confirmService;
    }

    /**
     * Loads the deployment editing flag on top of the base initialization.
     */
    override ngOnInit(): void {
        super.ngOnInit();
        this.geboPromptConfigurationService.isPromptTemplateEditingEnabled().subscribe({
            next: (enabled) => {
                this.editingEnabled = enabled === true;
            }
        });
    }

    /** The template currently held by the form. */
    public get template(): GPromptTemplateConfig | null {
        return this.formGroup.controls["promptTemplate"].value ?? null;
    }

    /** Whether the current template is a static (.yml) one that cannot be mutated/deleted in place. */
    public get isStaticTemplate(): boolean {
        return this.template?.configDeclarated === true;
    }

    /** Save is possible only when editing is enabled and the template is valid. */
    public get saveDisabled(): boolean {
        return this.formGroup.invalid || !this.editingEnabled;
    }

    /** Delete is possible only when editing is enabled, the template is a mongo one, and the backend allows it. */
    public get deleteDisabled(): boolean {
        return !this.editingEnabled || this.isStaticTemplate || !this.canDelete;
    }

    /** Populate the editing control when an existing template is loaded. */
    protected override onLoadedPersistentData(actualValue: GPromptTemplateConfig): void {
        this.formGroup.controls["promptTemplate"].setValue(actualValue);
    }

    /** Populate the editing control for a new template. */
    protected override onNewData(actualValue: GPromptTemplateConfig): void {
        this.formGroup.controls["promptTemplate"].setValue(actualValue);
    }

    /**
     * Saves the edited template following the prompt-template rules: a static
     * template is overridden by inserting a mongo copy, a mongo template is
     * updated, and a brand new one is inserted.
     */
    override doSave(successfulActionCallback?: (data: GPromptTemplateConfig) => void): void {
        const value = this.template;
        if (!value || this.saveDisabled) {
            return;
        }
        const persist$: Observable<GPromptTemplateConfig> = (this.isStaticTemplate || !value.code)
            ? this.insert(value)
            : this.save(value);
        this.loadingRelatedBackend = true;
        persist$.subscribe({
            next: (returned) => {
                this.mode = "EDIT";
                this.entity = returned;
                this.formGroup.controls["promptTemplate"].setValue(returned);
                this.refreshCanDelete(returned);
                this.userMessages = [{
                    id: "OK", severity: "success", summary: "Saved with success",
                    detail: "Prompt template saved successfully"
                }];
                this.updated.emit(returned);
                if (successfulActionCallback) {
                    try {
                        successfulActionCallback(returned);
                    } catch (e) {
                        console.error(e);
                    }
                }
                this.cancelAction.emit(true);
            },
            error: (error) => {
                this.userMessages = [{ id: "SERVERERROR", severity: "error", detail: "Server error saving: " + error }];
            },
            complete: () => {
                this.loadingRelatedBackend = false;
            }
        });
    }

    /**
     * Deletes the current mongo template after confirmation.
     */
    override doDelete(successfulActionCallback?: (data: GPromptTemplateConfig) => void): void {
        const value = this.template;
        if (!value || this.deleteDisabled) {
            return;
        }
        this.confirmSvc.confirm({
            icon: "pi pi-exclamation-triangle",
            header: "Delete confirm",
            message: "Are you sure you want to delete this prompt template?",
            closeOnEscape: true,
            accept: () => {
                this.loadingRelatedBackend = true;
                this.delete(value).subscribe({
                    next: (ok) => {
                        if (ok) {
                            if (successfulActionCallback) {
                                try {
                                    successfulActionCallback(value);
                                } catch (e) {
                                    console.error(e);
                                }
                            }
                            this.deleted.emit(true);
                            this.cancelAction.emit(true);
                        } else {
                            this.userMessages = [{ id: "CANNOT_DELETE", severity: "error", detail: "Cannot delete this prompt template" }];
                        }
                    },
                    error: (error) => {
                        this.userMessages = [{ id: "SERVERERROR", severity: "error", detail: "Server error deleting: " + error }];
                    },
                    complete: () => {
                        this.loadingRelatedBackend = false;
                    }
                });
            }
        });
    }

    /** Refreshes the delete-enabled flag for the given template. */
    private refreshCanDelete(value: GPromptTemplateConfig): void {
        this.canBeDeleted(value).subscribe({
            next: (returned) => {
                this.canDelete = returned.canBeDeleted;
            }
        });
    }

    /**
     * Finds a prompt configuration by its code (mongo override preferred over the static one).
     */
    override findByCode(code: string): Observable<GPromptTemplateConfig | null> {
        return this.geboPromptConfigurationService.findPromptConfigByCode(code);
    }

    /**
     * Updates an existing (mongo) prompt configuration.
     */
    override save(value: GPromptTemplateConfig): Observable<GPromptTemplateConfig> {
        return this.geboPromptConfigurationService.updatePromptConfig(value);
    }

    /**
     * Inserts a mongo prompt configuration. When overriding a static template the
     * configDeclarated flag is cleared so the backend accepts the persistence and
     * the override resolves ahead of the static one.
     */
    override insert(value: GPromptTemplateConfig): Observable<GPromptTemplateConfig> {
        const persistable: GPromptTemplateConfig = { ...value, configDeclarated: false };
        return this.geboPromptConfigurationService.insertPromptConfig(persistable);
    }

    /**
     * Deletes a (mongo) prompt configuration.
     */
    override delete(value: GPromptTemplateConfig): Observable<boolean> {
        return this.geboPromptConfigurationService.deletePromptConfig(value).pipe(map(() => true));
    }

    /**
     * Only mongo templates can be deleted; static (.yml) ones cannot.
     */
    override canBeDeleted(value: GPromptTemplateConfig): Observable<{ canBeDeleted: boolean; message: string; }> {
        const isMongo = value?.configDeclarated !== true;
        return of({
            canBeDeleted: isMongo,
            message: isMongo ? "can delete"
                : "This prompt template is declared in the server configuration and cannot be deleted; only a mongo override can."
        });
    }
}
