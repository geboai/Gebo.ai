/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */




import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, Type } from "@angular/core";
import { SetupWizardGrouping, SetupWizardItem } from "./setup-wizard-step";
import { SetupStatus, SetupWizardGroupItem, SetupWizardService } from "./setup-wizard.service";
import { BaseWizardSectionComponent } from "./base-wizard-section.component";
import { SetupWizardComunicationService } from "./setup-wizard-comunication.service";
import { MenuItem, ToastMessageOptions } from "primeng/api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../controls/field-host-component-iface/field-host-component-iface";
import { GeboAITranslationService } from "../controls/field-translation-container/gebo-translation.service";
import { map, Observable, Subscription } from "rxjs";
import { findMatchingTranlations, UIExistingText, UILanguageResources } from "../controls/field-translation-container/text-language-resources";
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
interface MandatoryUIEntry { config: SetupWizardItem, wizardComponent: Type<BaseWizardSectionComponent> };
/**
 * Builds the translatable texts (label and description) of wizard items or groups,
 * using the entry id as componentId under the SetupWizardPanelComponent entity.
 */
function labelTexts<T extends { label: string, description?: string }>(entries: T[], idOf: (entry: T) => string): UIExistingText[] {
    const texts: UIExistingText[] = [];
    entries.forEach(entry => {
        const id = idOf(entry);
        if (id) {
            texts.push({ moduleId: moduleId, componentId: id, entityId: fieldHostId, fieldId: "label", key: "label", text: entry.label });
            if (entry.description) {
                texts.push({ moduleId: moduleId, componentId: id, entityId: fieldHostId, fieldId: "description", key: "description", text: entry.description });
            }
        }
    });
    return texts;
}
/**
 * Returns copies of the entries with the matching translations applied
 */
function applyTranslations<T>(entries: T[], idOf: (entry: T) => string, found?: UIExistingText[]): T[] {
    const outVector: T[] = entries.map(x => ({ ...x }));
    if (found && found.length) {
        outVector.forEach(entry => {
            found.filter(item => item.componentId === idOf(entry))?.forEach(item => {
                if (item.translation) {
                    (entry as any)[item.fieldId] = item.translation;
                }
            });
        });
    }
    return outVector;
}
/**
 * Component that renders a wizard panel to guide users through a sequence of setup steps.
 * This component manages the navigation, state, and validation of a multi-step setup process.
 * It communicates with services to track setup status and displays appropriate messages to the user.
 */
@Component({
    selector: "gebo-setup-wizard-panel-component",
    templateUrl: "setup-wizard-panel.component.html",
    styleUrl: "setup-wizard-panel.component.scss",
    providers: [SetupWizardComunicationService, {
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
    /** Wizard steps distributed in their groups, used when grouping is not "none" */
    public wizardGroups: SetupWizardGroupItem[] = [];
    /** How the sections are grouped: flat list, tabs or accordion */
    @Input() public grouping: SetupWizardGrouping = "none";
    /** groupId of the selected tab when grouping is "Tab" */
    public selectedGroupTab?: string;
    /** groupIds of the opened panels when grouping is "Accordion" */
    public openedGroupPanels?: string[];
    /** List of mandatory unsatisfied wizard steps to display in a popup */
    public mandatoryUnsatisfiedEntries: MandatoryUIEntry[] = [];
    public mandatoryUnsatisfiedEntriesWindowOpened: boolean = false;
    public userShowedMandatoryUnsatisfiedEntries:boolean=false;
    @Input() public popupOnMandatoryUnsatisfiedEntriesExisting: boolean = true;
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
        private geboLanguageService: GeboAITranslationService) {
    }
    /**
     * Translates items and group labels on the actual language, emitting again on language changes
     */
    private actualLanguage(items: SetupWizardItem[]): Observable<{ items: SetupWizardItem[], groups: SetupWizardGroupItem[] }> {
        const texts: UIExistingText[] = [
            ...labelTexts(items, x => x.wizardSectionId),
            ...labelTexts(this.setupWizardService.groupItems(items), x => x.groupId)
        ];
        return this.geboLanguageService.translateOnActualLanguage(texts).pipe(map((resources: UILanguageResources | undefined) => {
            const found = resources ? findMatchingTranlations(texts, resources) : undefined;
            const translatedItems = applyTranslations(items, x => x.wizardSectionId, found);
            const translatedGroups = applyTranslations(this.setupWizardService.groupItems(translatedItems), x => x.groupId, found);
            return { items: translatedItems, groups: translatedGroups };
        }));
    }
    /**
     * Keeps the user tab/panels choice across reloads, otherwise selects the groups
     * with missing mandatory setups, or the first group if none.
     */
    private initGroupsSelection(): void {
        const ids = this.wizardGroups.map(g => g.groupId);
        const incompleteIds = this.wizardGroups.filter(g => g.status === "incomplete").map(g => g.groupId);
        if (!this.selectedGroupTab || !ids.includes(this.selectedGroupTab)) {
            this.selectedGroupTab = incompleteIds.length ? incompleteIds[0] : ids[0];
        }
        if (!this.openedGroupPanels) {
            this.openedGroupPanels = incompleteIds.length ? incompleteIds : ids.slice(0, 1);
        }
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
                this.wizardGroups = this.setupWizardService.groupItems(values);
                this.subscription = this.actualLanguage(values).subscribe({
                    next: (translated) => {
                        this.wizardsEntries = translated.items;
                        this.wizardGroups = translated.groups;
                    }
                });
                this.initGroupsSelection();
                this.actualSetupStatus = this.setupWizardService.calculateSetupStatus(this.wizardsEntries);
                this.mandatoryUnsatisfiedEntries = this.wizardsEntries.filter(x => x.mandatory === true && x.alreadyCompleted !== true)?.map(y => {
                    const m: MandatoryUIEntry = {
                        config: y,
                        wizardComponent: y.wizardComponent
                    };
                    return m;
                });
                this.mandatoryUnsatisfiedEntriesWindowOpened=this.userShowedMandatoryUnsatisfiedEntries===false && this.mandatoryUnsatisfiedEntries && this.mandatoryUnsatisfiedEntries.length>0;
                this.setupStatusRefresh.emit(this.actualSetupStatus);
                
                const completeMessage: ToastMessageOptions = {id:"SETUP-DONE_DO_MORE", summary: "Gebo.ai setup mandatory steps done...", detail: "Mandatory setup steps have been completed but some missing steps prevent your organization from getting the most out of this software", severity: "warn" };
                const incompleteMessage: ToastMessageOptions = {id:"SETUP-MISSING_SOME", summary: "Gebo.ai setup is missing some mandatory step", detail: "Please review the red steps of the setup process", severity: "error" };
                const okMessage: ToastMessageOptions = {id:"SETUP-OK", summary: "Gebo.ai setup OK!", detail: "", severity: "success" };
                this.viewSelectedStep(this.stepId);
                switch (this.actualSetupStatus) {
                    case "complete": {
                        this.userMessages=[completeMessage];

                    } break;
                    case "incomplete": {
                        this.userMessages=[incompleteMessage];

                    } break;
                    case "full": {
                        this.userMessages=[okMessage];

                    } break;
                }
                
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
                        toBeSetList = toBeSetList + (nrKo > 1 ? ", " : "") + entry.label;
                    }


                } else {
                    console.error("Unknown step:" + stepId);
                }
            });
        }
        if (ok !== true) {
            result.messages = [{ severity: "warn", summary: "Missing setups before:" + item.label, detail: "Before setting the " + item.label + " the following must be configured: " + toBeSetList }];
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
            this.selectGroupOf(item);
        } else {
            //this.messagesService.addAll(check.messages);
        }
    }

    /**
     * Selects the tab and opens the accordion panel of the group containing the item,
     * so that going back from the section shows it where it is.
     * @param item The wizard step item being opened
     */
    private selectGroupOf(item: SetupWizardItem): void {
        const group = this.wizardGroups.find(g => g.items.some(x => x.wizardSectionId === item.wizardSectionId));
        if (group) {
            this.selectedGroupTab = group.groupId;
            if (!this.openedGroupPanels?.includes(group.groupId)) {
                this.openedGroupPanels = [...(this.openedGroupPanels || []), group.groupId];
            }
        }
    }

    /**
     * Dismisses the auto-raised mandatory-setup popup, same effect as clicking the
     * dialog's own close (X) icon, but reachable through a clearly labeled button
     * for users who don't notice the small header icon.
     */
    public dismissMandatoryPopup(): void {
        this.mandatoryUnsatisfiedEntriesWindowOpened = false;
        this.userShowedMandatoryUnsatisfiedEntries = true;
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
