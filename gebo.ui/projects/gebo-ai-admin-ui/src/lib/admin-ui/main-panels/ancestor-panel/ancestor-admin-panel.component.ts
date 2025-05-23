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
 * This component serves as a parent/ancestor panel in the admin interface.
 * It provides a base structure for admin panels, with a method to reload viewed data.
 * The component is not standalone and can be used within a parent module.
 */
import { Component } from "@angular/core";

@Component({
    selector: "gebo-ai-ancestor-admin-panel",
    template: "",
    standalone: false
})
export  class AncestorPanelComponent {
    /**
     * Reloads the currently viewed data in the panel.
     * This method is intended to be overridden by child components
     * to implement specific data refresh functionality.
     */
    public  reloadViewedData(): void{}
}