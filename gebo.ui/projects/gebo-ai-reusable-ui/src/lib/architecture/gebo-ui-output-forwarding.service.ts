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
 * This service manages communication between host components and guest components in modal dialogs.
 * It acts as a mediator that forwards events from guest components to host components, handling
 * subscriptions and cleanup to prevent memory leaks.
 */
import { EventEmitter, Injectable } from "@angular/core";

import { Observable, Subscription } from "rxjs";
import { GeboUIModalOpenerWrapperComponent } from "./gebo-ui-modal-wrapper.component";
import { WizardButtonsBarData } from "../controls/base-entity-editing-component/entities-modification-wizard";

/**
 * Service responsible for forwarding events and managing communication between
 * modal components (guest) and their container/wrapper (host).
 * This facilitates data exchange and event propagation between components
 * that may not have direct parent-child relationships.
 */
@Injectable()
export class GeboUIOutputForwardingService {
    /** Reference to the host component (modal wrapper) */
    private host?: GeboUIModalOpenerWrapperComponent;
    
    /** Reference to the guest component with its event emitters and form validity observable */
    private guest?: { createdNew: EventEmitter<any>, updated: EventEmitter<any>, deleted: EventEmitter<boolean>, cancelAction: EventEmitter<boolean>, formInvalid: Observable<boolean> };
    
    /** Subscription for the createdNew event */
    private cNewSub?: Subscription;
    
    /** Subscription for the updated event */
    private updatedSub?: Subscription;
    
    /** Subscription for the deleted event */
    private deletedSub?: Subscription;
    
    /** Subscription for the cancelAction event */
    private cancelActionSub?: Subscription;
    
    /** Subscription for the form invalid state */
    private cancelInvalidSub?: Subscription;
    
    /** Current form validity state */
    public actualFormInvalid: boolean = false;
    
    /** Configuration data for wizard buttons */
    public wizardButtonsBarData: WizardButtonsBarData | undefined;

    /**
     * Sets up event forwarding from guest to host by subscribing to guest events
     * and emitting corresponding events on the host when they occur.
     * This creates a bridge between the two components.
     */
    private registerGuestHostForward() {
        if (this.host && this.guest) {
            this.cNewSub = this.guest.createdNew.subscribe(x => {
                this.host?.createdNew.emit(x);
            });
            this.updatedSub = this.guest.updated.subscribe(x => {
                this.host?.updated.emit(x);
            });
            this.deletedSub = this.guest.deleted.subscribe(x => {
                this.host?.deleted.emit(x);
            });
            this.cancelActionSub = this.guest.cancelAction.subscribe(x => {
                this.host?.cancelAction.emit(x);
            });
            this.cancelInvalidSub = this.guest.formInvalid.subscribe(x => {
                this.actualFormInvalid = x;
            });
        }
    }

    /**
     * Cleans up all subscriptions to prevent memory leaks when
     * the guest-host connection is no longer needed.
     */
    private removeGuestHostForward() {
        if (this.cNewSub) { this.cNewSub.unsubscribe(); this.cNewSub = undefined; }
        if (this.updatedSub) { this.updatedSub.unsubscribe(); this.updatedSub = undefined; }
        if (this.deletedSub) { this.deletedSub.unsubscribe(); this.deletedSub = undefined; }
        if (this.cancelActionSub) { this.cancelActionSub.unsubscribe(); this.cancelActionSub = undefined; }
    }

    /**
     * Registers a host component with the service and sets up the event forwarding.
     * If wizard button data is available, it initializes the wizard status on the host.
     * 
     * @param host The modal wrapper component to register as host
     */
    public registerHost(host: GeboUIModalOpenerWrapperComponent) {
        console.log("registerHost");
        this.host = host;
        this.registerGuestHostForward();
        if (this.guest && this.wizardButtonsBarData) {
            this.host.initializeWizardStatus(this.wizardButtonsBarData);
        }
    }

    /**
     * Registers a guest component with the service and sets up event forwarding.
     * First removes any existing guest to prevent multiple subscriptions.
     * If wizard button data is provided, it initializes the wizard status on the host.
     * 
     * @param guest The component with event emitters to register as guest
     * @param toBeNotified Optional wizard buttons configuration data
     */
    public registerGuest(guest: { createdNew: EventEmitter<any>, updated: EventEmitter<any>, deleted: EventEmitter<boolean>, cancelAction: EventEmitter<boolean>, formInvalid: Observable<boolean> }, toBeNotified?: WizardButtonsBarData) {
        this.removeGuest();
        console.log("registerGuest");
        this.guest = guest;
        this.wizardButtonsBarData = toBeNotified;
        this.registerGuestHostForward();
        if (this.host && this.wizardButtonsBarData) {
            this.host.initializeWizardStatus(this.wizardButtonsBarData);
        }
    }

    /**
     * Removes the host component and cleans up any event forwarding.
     */
    public removeHost() {
        this.host = undefined;
        this.removeGuestHostForward();
    }

    /**
     * Removes the guest component and cleans up any event forwarding.
     */
    public removeGuest() {
        this.guest = undefined;
        this.removeGuestHostForward();
    }

    /**
     * Checks if a host component is currently registered.
     * @returns Boolean indicating whether a host is present
     */
    public get hostIsPresent(): boolean {
        return this.host ? true : false;
    }

    /**
     * Checks if a guest component is currently registered.
     * @returns Boolean indicating whether a guest is present
     */
    public get guestIsPresent(): boolean {
        return this.guest ? true : false;
    }
}