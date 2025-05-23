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
 * This component serves as a launcher for entity forms in the Gebo UI.
 * It's responsible for retrieving entity form configurations from the
 * GeboUIEntityFormsLauncherService and making them available to the template.
 * The component is designed to be a centralized point for launching and
 * managing various entity forms within the application.
 */
import { Component, Inject, Injector, OnChanges, OnInit, Optional, SimpleChanges } from "@angular/core";
import { GEBO_UI_ENTITY_FORM_TOKEN, GeboUIEntityFormConfig } from "./gebo-ui-entity-form-config";
import { GeboUIEntityFormsLauncherService } from "./gebo-ui-entity-forms-launcher.service";

@Component({
    selector: "gebo-ui-entities-forms-launcher-component",
    templateUrl: "gebo-ui-entity-forms-launcher.component.html",
    standalone: false
})
export class GeboUIEntityFormsLauncherComponent implements OnInit, OnChanges {
    /**
     * Stores the configurations obtained from the GeboUIEntityFormsLauncherService
     * These configurations define the entity forms that can be launched
     */
    injectedConfigs: GeboUIEntityFormConfig[] | undefined;
    
    /**
     * Constructor that injects the Angular Injector
     * @param injector Angular's Injector service used to retrieve the GeboUIEntityFormsLauncherService
     */
    constructor(private injector: Injector
    ) {

    }
    
    /**
     * Lifecycle hook that initializes the component
     * Retrieves the current entity form configurations from the GeboUIEntityFormsLauncherService
     * and assigns them to injectedConfigs for use in the template
     */
    ngOnInit(): void {
        const service = this.injector.get(GeboUIEntityFormsLauncherService);
        if (service && service.getCurrentConfigurations) {
            this.injectedConfigs = service.getCurrentConfigurations();
        }
    }
    
    /**
     * Lifecycle hook that responds to changes in the component's input properties
     * Currently implemented without specific logic, can be extended for change detection
     * @param changes SimpleChanges object containing current and previous property values
     */
    ngOnChanges(changes: SimpleChanges): void {

    }

}