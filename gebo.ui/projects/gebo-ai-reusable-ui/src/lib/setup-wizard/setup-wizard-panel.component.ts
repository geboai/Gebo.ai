/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */




import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, Type } from "@angular/core";
import { SetupWizardItem } from "./setup-wizard-step";
import { SetupStatus, SetupWizardService } from "./setup-wizard.service";
import { BaseWizardSectionComponent } from "./base-wizard-section.component";
import { SetupWizardComunicationService } from "./setup-wizard-comunication.service";
import { MenuItem, ToastMessageOptions, MessageService } from "primeng/api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../controls/field-host-component-iface/field-host-component-iface";
import { GeboAITranslationService } from "../controls/field-translation-container/gebo-translation.service";
import { map, Observable, of, Subscription } from "rxjs";
import { findMatchingTranlations, UIExistingText } from "../controls/field-translation-container/text-language-resources";
/**
 * AI generated comments
 * This module provides a setup wizard panel component for guiding users through a multi-step setup process
 * with precondition validation, status tracking, and navigation.
 */

/**
 * Utility function that filters elements from the first array that are also found in the second array.
 * @param v1 The source array to filter from
 * @param v2 The array containing elements to find
 * @returns A new array with elements from v1 that are also in v2
 */
function filterNotContained<T>(v1: T[], v2: T[]): T[] {
    return v1?.filter(c => v2.find(x => x === c));
}
const moduleId: string = "SetupWizardPanelModule";
const fieldHostId: string = "SetupWizardPanelComponent";
/**
 * Component that renders a wizard panel to guide users through a sequence of setup steps.
 * This component manages the navigation, state, and validation of a multi-step setup process.
 * It communicates with services to track setup status and displays appropriate messages to the user.
 */
@Component({
    selector: "gebo-setup-wizard-panel-component",
    templateUrl: "setup-wizard-panel.component.html",
    providers: [SetupWizardComunicationService, MessageService, {
        provide: GEBO_AI_MODULE, useValue: "SetupWizardPanelModule", multi: false
    }, {
            provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("SetupWizardPanelComponent")
        }],
    standalone: false,

})
export class SetupWizardPanelComponent implements OnInit, OnChanges {
    /** Flag indicating if data is being loaded */
    public loading: boolean = false;
    /** Collection of messages to be displayed to the user */
    public userMessages: ToastMessageOptions[] = [];
    /** List of all wizard steps available to the user */
    public wizardsEntries: SetupWizardItem[] = [];
    /** Currently selected wizard item */
    public actualItem?: SetupWizardItem;
    /** Component type for the currently active wizard section */
    public wizardComponent?: Type<BaseWizardSectionComponent>;
    /** Current overall setup status */
    public actualSetupStatus?: SetupStatus;
    /** Breadcrumb navigation items */
    public bcItems: MenuItem[] = [];
    /** Home breadcrumb item with navigation back to main wizard */
    public home: MenuItem = {
        label: "Setup home",
        command: (item) => {
            this.closeWizard();
        }
    };
    /** Title to display for the wizard panel */
    @Input() title: string = "Choose a setup section";
    /** ID of the step to display initially */
    @Input() stepId?: string;
    /** Event emitter for notifying parent components about setup status changes */
    @Output() setupStatusRefresh: EventEmitter<SetupStatus> = new EventEmitter();

    /**
     * Constructor initializes required services for the wizard panel
     * @param setupWizardService Service for managing the setup workflow and state
     * @param setupWizardComunicationService Service for communication between wizard components
     * @param messagesService Service for displaying toast messages
     */
    constructor(
        private setupWizardService: SetupWizardService,
        private setupWizardComunicationService: SetupWizardComunicationService,
        private geboLanguageService: GeboAITranslationService,
        private messagesService: MessageService) {
    }
    private actualLanguage(items: SetupWizardItem[]): Observable<SetupWizardItem[] | undefined> {
        const texts: UIExistingText[] = [];
        items.forEach(item => {
            if (item.wizardSectionId)
                texts.push({
                    moduleId: "SetupWizardPanelModule",
                    componentId: item.wizardSectionId,
                    entityId: "SetupWizardPanelComponent",
                    fieldId: "label",
                    key: "label",
                    text: item.label
                });
            texts.push({
                moduleId: "SetupWizardPanelModule",
                componentId: item.wizardSectionId,
                entityId: "SetupWizardPanelComponent",
                fieldId: "description",
                key: "description",
                text: item.description
            });
        });
        return this.geboLanguageService.translateOnActualLanguage(texts).pipe(map(resources => {
            const outVector: SetupWizardItem[] = [];
            items.forEach(x => {
                outVector.push({ ...x });
            });
            if (resources) {
                const found = findMatchingTranlations(texts, resources);
                if (found && found.length) {
                    outVector.forEach(setupItem => {
                        found.filter(entry => entry.componentId === setupItem.wizardSectionId)?.forEach(item => {
                            if (item.translation) {
                                (setupItem as any)[item.fieldId] = item.translation;
                            }
                        });
                    });
                }
            }
            return outVector;
        }));
    }
    private subscription?: Subscription;
    /**
     * Reloads the current setup status from the service and updates the UI accordingly.
     * Sets appropriate messages based on completion status.
     */
    public reloadStatus(): void {
        this.loading = true;
        if (this.subscription) {
            this.subscription.unsubscribe();
            this.subscription = undefined;
        }
        this.setupWizardService.getActualStatus().subscribe({
            next: (values) => {
                this.wizardsEntries = values;
                this.subscription = this.actualLanguage(this.wizardsEntries).subscribe({
                    next: (entries) => {
                        if (entries) {
                            this.wizardsEntries = entries;
                        }
                    }
                });
                this.actualSetupStatus = this.setupWizardService.calculateSetupStatus(this.wizardsEntries);
                this.setupStatusRefresh.emit(this.actualSetupStatus);
                let messageObservable: Observable<ToastMessageOptions | undefined> | undefined = undefined;
                const completeMessage: ToastMessageOptions = { summary: "Gebo.ai setup mandatory steps done...", detail: "Mandatory setup steps have been completed but some missing steps prevents your organization from experiencing the most from this software", severity: "warn" };
                const completeMessageObservable = this.geboLanguageService.translateMessage(moduleId, fieldHostId + "-mandatory-done", completeMessage);
                const incompleteMessage: ToastMessageOptions = { summary: "Gebo.ai setup is missing some mandatory step", detail: "Please review the red steps of the setup process", severity: "error" };
                const incompletemessageObservable = this.geboLanguageService.translateMessage(moduleId, fieldHostId + "-mandatory-missing", incompleteMessage);
                const okMessage: ToastMessageOptions = { summary: "Gebo.ai setup OK!", detail: "", severity: "success" };
                const okmessageObservable = this.geboLanguageService.translateMessage(moduleId, fieldHostId + "-setup-ok", okMessage);
                this.viewSelectedStep(this.stepId);
                switch (this.actualSetupStatus) {
                    case "complete": {
                        messageObservable = completeMessageObservable;

                    } break;
                    case "incomplete": {
                        messageObservable = incompletemessageObservable;

                    } break;
                    case "full": {
                        messageObservable = okmessageObservable;

                    } break;
                }
                if (messageObservable)
                    this.subscription = messageObservable.subscribe({
                        next: (message) => {
                            this.userMessages = message ? [message] : [];
                        }
                    });
            },
            complete: () => {
                this.loading = false;
            }
        });

    }

    /**
     * Navigates to a specific step in the setup wizard by its ID
     * @param step The ID of the step to view, undefined will return to the main wizard view
     */
    private viewSelectedStep(step?: string): void {
        if (!step) {
            this.actualItem = undefined;
            this.stepId = undefined;
            this.wizardComponent = undefined;

        } else {
            if (this.wizardsEntries && this.wizardsEntries.length) {
                const found = this.wizardsEntries.find(x => x.wizardSectionId === step);
                if (found) {
                    this.openWizard(found);
                }
            }
        }
    }

    /**
     * Angular lifecycle hook that initializes the component and loads initial data
     */
    ngOnInit(): void {
        this.setupWizardComunicationService.setWizardPanel(this);
        this.reloadStatus();
    }

    /**
     * Angular lifecycle hook that responds to changes in @Input properties
     * @param changes Object containing the changed properties
     */
    ngOnChanges(changes: SimpleChanges): void {
        if (this.stepId && changes["stepId"]) {
            this.viewSelectedStep(this.stepId);
        }
        if (this.title && changes["title"]) {
            this.home.label = this.title;
        }
    }

    /**
     * Checks if all required preconditions are met before allowing a step to be opened
     * @param item The wizard step item to check preconditions for
     * @returns Object with precondition status and any warning messages
     */
    private preconditionsCheck(item: SetupWizardItem): { preconditionsOk: boolean, messages: ToastMessageOptions[] } {
        const result: { preconditionsOk: boolean, messages: ToastMessageOptions[] } = { preconditionsOk: false, messages: [] };
        let ok: boolean = true;
        let nrKo: number = 0;
        let toBeSetList: string = "";
        if (item.requredStepsIds) {
            item.requredStepsIds.forEach(stepId => {
                const entry = this.wizardsEntries?.find(x => x.wizardSectionId === stepId);
                if (entry) {
                    if (entry.enabled === true && entry.alreadyCompleted !== true) {
                        ok = false;
                        nrKo++;
                        toBeSetList = toBeSetList + (nrKo > 0 ? "," : "") + entry.label;
                    }


                } else {
                    console.error("Unknown step:" + stepId);
                }
            });
        }
        if (ok !== true) {
            result.messages = [{ severity: "warn", summary: "Missing setups before:" + item.label, detail: "Before setting the " + item.label + toBeSetList + " must be configured." }];
        }

        result.preconditionsOk = ok;
        return result;
    }

    /**
     * Opens a specific wizard step if all preconditions are met
     * @param item The wizard step item to open
     */
    public openWizard(item: SetupWizardItem) {
        const check = this.preconditionsCheck(item);
        this.userMessages = check.messages;
        if (check.preconditionsOk === true) {
            this.bcItems = [{
                label: item.label
            }];
            this.actualItem = item;
            this.wizardComponent = this.actualItem.wizardComponent;
        } else {
            //this.messagesService.addAll(check.messages);
        }
    }

    /**
     * Closes the current wizard step and returns to the main wizard view
     */
    public closeWizard(): void {
        this.bcItems = [];
        this.actualItem = undefined;
        this.stepId = undefined;
        this.wizardComponent = undefined;
        this.reloadStatus();
    }
}