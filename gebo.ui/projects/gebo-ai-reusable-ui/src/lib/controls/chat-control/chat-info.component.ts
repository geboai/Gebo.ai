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
 * This component displays user information in a chat interface.
 * It receives user data through an Input binding and emits a close event when
 * the user information display should be closed/dismissed.
 */
import { Component, EventEmitter, Input, Output } from "@angular/core";
import { GeboChatUserInfo } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../field-host-component-iface/field-host-component-iface";

/**
 * Represents a component that displays chat user information.
 * This component is used to show details about a user in a chat context.
 * It can be closed by the user, triggering the close event.
 */
@Component({
    selector: "gebo-user-chat-info-component",
    templateUrl: "chat-info.component.html",
    standalone: false,
    providers: [{ provide: GEBO_AI_MODULE, useValue: "GeboAIChatControlModule", multi: false },{ provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("GeboChatUserInfoComponent"), multi: false }]
})
export class GeboChatUserInfoComponent {
    /**
     * Input property to receive user information data to be displayed
     * Expected to be of type GeboChatUserInfo from the Gebo.ai API
     */
    @Input() data?: GeboChatUserInfo;

    /**
     * Output event emitter that fires when the user info display should be closed
     * Emits a boolean value (typically true) to signal closure
     */
    @Output() close: EventEmitter<boolean> = new EventEmitter();
}