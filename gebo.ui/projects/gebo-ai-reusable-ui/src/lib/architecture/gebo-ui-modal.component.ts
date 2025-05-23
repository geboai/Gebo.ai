/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

import { Component, EventEmitter, Input, Output } from "@angular/core";
/**
 * A reusable modal dialog component for Angular applications.
 * This component provides a customizable modal overlay that can be shown or hidden.
 * AI generated comments
 */
@Component({
    selector: "gebo-ai-modal",
    templateUrl: "gebo-ui-modal.component.html",
    standalone: false
})
export class GeboUIModalComponent { 
    /** Flag indicating if this is a modal dialog (prevents clicking outside) */
    @Input() modal:boolean=false;
    
    /** Controls the visibility state of the modal */
    @Input() visible:boolean=false;
    
    /** Custom CSS styles for the modal */
    @Input() style:any;
    
    /** Text to display in the modal header */
    @Input() header:string="";
    
    /** Event emitter that outputs the current visibility state */
    @Output("visible") visibleOutput:EventEmitter<boolean>=new EventEmitter();
    
    /** Event emitter triggered when the modal is hidden */
    @Output() onHide:EventEmitter<any>=new EventEmitter();
    
    /** Two-way binding event emitter for the visible property */
    @Output() visibleChange:EventEmitter<boolean>=new EventEmitter();     
}