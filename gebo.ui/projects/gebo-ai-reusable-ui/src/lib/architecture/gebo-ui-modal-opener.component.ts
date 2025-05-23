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
 * This file implements a modal opener component within an Angular application.
 * It handles opening, displaying, and managing modal dialogs for different entity editing operations.
 */

import { Component, EventEmitter, Input, OnChanges, OnDestroy, OnInit, Output, SimpleChanges, Type } from "@angular/core";
import { GeboUIActionRoutingService } from "./gebo-ui-action-routing.service";
import { GeboUIActionRequestListener, GeboActionType, GeboUIActionRequest, GeboActionPerformedCallback, GeboActionPerformedEvent, GeboActionPerformedType } from "./actions.model";
import { BaseEntityEditingComponent } from "../controls/base-entity-editing-component/base-entity-editing.component";
import { OpenedModals } from "./opened-modals";

/**
 * Component responsible for managing modals in the Gebo UI.
 * 
 * This component listens for action requests and opens appropriate modals for entity creation,
 * editing, or viewing. It handles the lifecycle of modals and communicates results back through
 * event emitters and callbacks.
 * 
 * The component implements multiple interfaces:
 * - GeboUIActionRequestListener: To receive and process action requests
 * - OnChanges: To respond to input property changes
 * - OnDestroy: For cleanup operations
 * - OnInit: For initialization logic
 */
@Component({
    selector: "gebo-ui-modal-opener",
    templateUrl: "gebo-ui-modal-opener.component.html",
    standalone: false
})
export class GeboUIModalOpenerComponent implements GeboUIActionRequestListener, OnChanges, OnDestroy, OnInit {
    /** Type of component to render in the modal */
    @Input() componentType?: Type<BaseEntityEditingComponent<any>>;
    
    /** Target entity type identifier */
    @Input() targetType?: string;
    
    /** Currently opened modal information */
    public modal?: OpenedModals;
    
    /** Controls visibility of the modal */
    public visible:boolean=true;
    
    /** Event emitted when a new entity is created */
    @Output() createdNew: EventEmitter<any> = new EventEmitter();
    
    /** Event emitted when an entity is updated */
    @Output() updated: EventEmitter<any> = new EventEmitter();
    
    /** Event emitted when an entity is deleted */
    @Output() deleted: EventEmitter<boolean> = new EventEmitter();
    
    /** Event emitted when a modal action is cancelled */
    @Output() cancelAction: EventEmitter<boolean> = new EventEmitter();
    
    /** Optional callback function that is invoked when actions are performed */
    @Input() actionPerformedCallback?: GeboActionPerformedCallback;
    
    /**
     * Constructor that injects the action routing service
     * @param actionRouting Service for registering and handling UI actions
     */
    public constructor(private actionRouting: GeboUIActionRoutingService) {

    }

    /**
     * Lifecycle hook that initializes the component.
     * 
     * Registers this component with the action routing service and sets up
     * subscriptions to the various event emitters, forwarding events to the
     * actionPerformedCallback when provided.
     */
    ngOnInit(): void {
        this.actionRouting.registerListener(this);
        this.createdNew.subscribe(x => {
            if (this.actionPerformedCallback) {
                const event: GeboActionPerformedEvent = {
                    actionType: GeboActionPerformedType.INSERTED,
                    target: x
                };
                this.actionPerformedCallback(event);
            }
        });
        this.updated.subscribe(x => {
            if (this.actionPerformedCallback) {
                const event: GeboActionPerformedEvent = {
                    actionType: GeboActionPerformedType.SAVED,
                    target: x
                };
                this.actionPerformedCallback(event);
            }
        });
        this.deleted.subscribe(x => {
            if (this.actionPerformedCallback) {
                const event: GeboActionPerformedEvent = {
                    actionType: GeboActionPerformedType.DELETED,
                    target: {}
                };
                this.actionPerformedCallback(event);
            }
        });
        this.cancelAction.subscribe(x => {
            if (this.actionPerformedCallback) {
                const event: GeboActionPerformedEvent = {
                    actionType: GeboActionPerformedType.CLOSING_WINDOW,
                    target: {}
                };
                this.actionPerformedCallback(event);
            }
        });
    }

    /**
     * Lifecycle hook called when the component is being destroyed.
     * Unregisters this component from the action routing service.
     */
    ngOnDestroy(): void {
        this.actionRouting.removeListener(this);
    }

    /**
     * Lifecycle hook called when input properties change.
     * Currently doesn't implement any specific behavior.
     * 
     * @param changes Object containing all the changed input properties
     */
    ngOnChanges(changes: SimpleChanges): void {

    }

    /**
     * Closes the currently open modal and emits a cancel action event.
     * 
     * @param modal The modal to close
     */
    public closeModal(modal: OpenedModals) {
        this.modal = undefined;
        this.cancelAction.emit(true);
    }

    /**
     * Handles incoming action requests by opening appropriate modals.
     * 
     * This method determines the modal mode (NEW, EDIT, or EDIT_OR_NEW) based on
     * the action type, sets up the modal inputs, and stores the callback function.
     * 
     * @param action The action request to handle
     * @returns true if the action was handled, false otherwise
     */
    handleAction(action: GeboUIActionRequest): boolean {
        if (action.target) {
            let mode: "EDIT" | "NEW" | "EDIT_OR_NEW";
            switch (action.actionType) {
                case GeboActionType.NEW: {
                    mode = "NEW";
                } break;
                case GeboActionType.OPEN: {
                    mode = "EDIT";
                } break;
                case GeboActionType.OPEN_OR_NEW: {
                    mode="EDIT_OR_NEW";
                } break;
            }
            let composedInputs:any={

            };
            if (action.targetFormInputs) {
                composedInputs={
                    ...action.targetFormInputs
                };
            }
            if (action.onWizardActionPerformed) {
                composedInputs.onWizardActionPerformed=action.onWizardActionPerformed;
            }
            const object: OpenedModals = {
                mode: mode,
                entity: action.target,
                composedInputs: composedInputs
            };
            this.modal = object;
            this.actionPerformedCallback = action.onActionPerformed;
            return true;
        } else
            return false;
    }

}