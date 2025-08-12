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
 * This component serves as a wrapper for modals in the Gebo UI system. It dynamically loads components
 * inside a modal and manages their lifecycle and interactions. It also provides support for wizard-style 
 * modal interfaces with step navigation.
 */
import { AfterViewInit, Component, ElementRef, EventEmitter, Input, OnChanges, OnDestroy, OnInit, Output, SimpleChanges, Type, ViewChild } from "@angular/core";
import { GeboUIActionRoutingService } from "./gebo-ui-action-routing.service";
import { BaseEntityEditingComponent } from "../controls/base-entity-editing-component/base-entity-editing.component";
import { OpenedModals } from "./opened-modals";
import { GeboUIOutputForwardingService } from "./gebo-ui-output-forwarding.service";

import { MenuItem } from "primeng/api";
import { WizardButtonsBarData } from "../controls/base-entity-editing-component/entities-modification-wizard";

@Component({
    selector: "gebo-ui-modal-wrapper",
    templateUrl: "gebo-ui-modal-wrapper.component.html",
    providers: [GeboUIOutputForwardingService],
    standalone: false
})
export class GeboUIModalOpenerWrapperComponent implements OnChanges, OnInit, AfterViewInit, OnDestroy {
    /**
     * Reference to the container element where the dynamic component will be loaded
     */
    @ViewChild("ngContainerItem") containerItem?: ElementRef<Element>; //NgTemplateOutlet
    
    /**
     * Type of component that should be loaded inside the modal
     */
    @Input() componentType?: Type<BaseEntityEditingComponent<any>>;
    
    /**
     * Modal configuration data including entity and mode information
     */
    @Input() modal?: OpenedModals;
    
    /**
     * Event emitted when a new entity is created in the modal
     */
    @Output() createdNew: EventEmitter<any> = new EventEmitter();
    
    /**
     * Event emitted when an entity is updated in the modal
     */
    @Output() updated: EventEmitter<any> = new EventEmitter();
    
    /**
     * Event emitted when an entity is deleted in the modal
     */
    @Output() deleted: EventEmitter<boolean> = new EventEmitter();
    
    /**
     * Event emitted when the modal action is cancelled
     */
    @Output() cancelAction: EventEmitter<boolean> = new EventEmitter();
    
    /**
     * Collection of inputs to be passed to the dynamically loaded component
     */
    composedInputs: { [key: string]: any } = {}
    
    /**
     * Constructor initializes the action routing and output forwarding services
     */
    public constructor(private actionRouting: GeboUIActionRoutingService, public outForwardingService: GeboUIOutputForwardingService) { }
    
    /**
     * Cleanup method called when component is destroyed
     */
    ngOnDestroy(): void {
        this.outForwardingService.removeHost();
    }
    
    /**
     * Flag indicating if the modal is in wizard mode
     */
    public isWizardMode: boolean = false;
    
    /**
     * Configuration data for the wizard buttons and steps
     */
    public wizardButtonsBarData: WizardButtonsBarData | undefined;

    /**
     * Menu items representing the wizard steps
     */
    public items: MenuItem[] = [];
    
    /**
     * Initializes the wizard mode with the provided configuration
     * @param wizard The wizard configuration data
     */
    public initializeWizardStatus(wizard: WizardButtonsBarData): void {
        this.wizardButtonsBarData = wizard;
        this.isWizardMode = true;
        const items: MenuItem[] = [];
        if (wizard && wizard.wizardStepsConfigurations) {
            wizard.wizardStepsConfigurations.forEach(x => {
                items.push({
                    label: x.description,
                    id: x.id
                });
            });
            this.items = items;
        }
    }
    
    /**
     * Keeps track of the current step index in wizard mode
     */
    private _index: number = -1;
    
    /**
     * Gets the active index of the current wizard step
     */
    public get activeIndex(): number {
        const wizard = this.wizardButtonsBarData;
        if (this._index < 0 && wizard && wizard.actualWizardStepConfigrationId && wizard.wizardStepsConfigurations) {
            this._index = wizard.wizardStepsConfigurations.findIndex(x => x.id === wizard.actualWizardStepConfigrationId);
        }
        return this._index;
    }
    
    /**
     * Navigates to the next step in the wizard
     */
    public goNext(): void {
        if (this.wizardButtonsBarData)
            this.wizardButtonsBarData.previusStepAction()
    }
    
    /**
     * Navigates to the previous step in the wizard
     */
    public goPrev(): void {
        if (this.wizardButtonsBarData)
            this.wizardButtonsBarData.nextStepAction()
    }
    
    /**
     * Gets the icon for the previous step button
     */
    public get previusStepIcon(): string {
        return this.wizardButtonsBarData?.previusStepIcon ? this.wizardButtonsBarData?.previusStepIcon : '';
    }
    
    /**
     * Gets the icon for the next step button
     */
    public get nextStepIcon(): string {
        return this.wizardButtonsBarData?.nextStepIcon ? this.wizardButtonsBarData?.nextStepIcon : '';
    }
    
    /**
     * Gets the label for the previous step button
     */
    public get previusStepLabel(): string {
        return this.wizardButtonsBarData?.previusStepLabel ? this.wizardButtonsBarData?.previusStepLabel : "";
    }
    
    /**
     * Gets the label for the next step button
     */
    public get nextStepLabel(): string {
        return this.wizardButtonsBarData?.nextStepLabel ? this.wizardButtonsBarData?.nextStepLabel : "";
    }
    
    /**
     * Lifecycle hook called after the view has been initialized
     */
    ngAfterViewInit(): void {

    }
    
    /**
     * Lifecycle hook called during component initialization
     * Registers this component with the output forwarding service
     */
    ngOnInit(): void {
        this.outForwardingService.registerHost(this);
    }
    
    /**
     * Lifecycle hook called when component inputs change
     * Updates the composed inputs when the modal configuration changes
     * @param changes SimpleChanges object containing changes to inputs
     */
    ngOnChanges(changes: SimpleChanges): void {
        if (changes["modal"] && this.modal) {
            
            const inputs = this.modal.composedInputs ? { ...this.modal.composedInputs, entity: this.modal.entity, mode: this.modal.mode } : { entity: this.modal.entity, mode: this.modal.mode };
            this.composedInputs = inputs;
        }
    }


}