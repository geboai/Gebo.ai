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
 * This component represents a test chat interface for the Gebo AI system.
 * It provides a way to integrate and test chat functionality with different chat models.
 * The component responds to external input changes and can be configured to display
 * in an open or closed state.
 */
import { Component, Input, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST } from "@Gebo.ai/reusable-ui";

@Component({
    selector: "gebo-ai-test-chat-component",
    templateUrl: "gebo-ai-test-chat.component.html",
    standalone: false, providers: [{
        provide: GEBO_AI_FIELD_HOST, multi: true, useValue: fieldHostComponentName("GeboAITestChatComponent")
    }]
})
export class GeboAITestChatComponent implements OnInit, OnChanges {
    /**
     * The code identifier for the chat model to be used for the conversation.
     * Different models may have different capabilities or behaviors.
     */
    @Input() chatModelCode?: string;
    
    /**
     * Determines whether the chat window should be displayed in an open state.
     * Default is false (closed).
     */
    @Input() openWindow: boolean = false;

    /**
     * Initializes a new instance of the GeboAITestChatComponent.
     */
    constructor(  ) { 
        
    }
    
    /**
     * Lifecycle hook that is called after data-bound properties are initialized.
     * Used for component initialization logic.
     */
    ngOnInit(): void {

    }
    
    /**
     * Lifecycle hook that is called when any data-bound property of the directive changes.
     * Used to respond to changes in @Input properties.
     * 
     * @param changes - Object containing the changed properties with current and previous values
     */
    ngOnChanges(changes: SimpleChanges): void {

    }
}