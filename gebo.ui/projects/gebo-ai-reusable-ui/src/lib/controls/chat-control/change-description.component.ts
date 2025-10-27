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
 * A component that provides a modal dialog for changing the description of a chat.
 * This component allows users to edit the description of a chat through a form interface.
 * It communicates with parent components through input bindings and event emitters.
 */
import { Component, EventEmitter, Input, Output } from "@angular/core";
import { FormGroup } from "@angular/forms";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../field-host-component-iface/field-host-component-iface";

@Component({
    selector: "gebo-ai-chat-change-description",
    templateUrl: "change-description.component.html",
    standalone: false,
    providers: [{ provide: GEBO_AI_MODULE, useValue: "GeboAIChatControlModule", multi: false },{ provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("ChangeDescriptionComponent"), multi: false }]
})
export class ChangeDescriptionComponent {
    /**
     * Controls the visibility of the change description dialog
     * True when the dialog should be visible, false otherwise
     */
    @Input() visible: boolean = false;

    /**
     * Form group containing the form controls for the description edit
     * Expected to be provided by the parent component
     */
    @Input() formGroup?: FormGroup;

    /**
     * Event emitted when the user saves the description changes
     * Emits a boolean value indicating the save action was performed
     */
    @Output() saveAction: EventEmitter<boolean> = new EventEmitter();

    /**
     * Event emitted when the user cancels the description changes
     * Emits a boolean value indicating the cancel action was performed
     */
    @Output() cancelAction: EventEmitter<boolean> = new EventEmitter();
}