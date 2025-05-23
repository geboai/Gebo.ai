/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

/**
 * @fileoverview Provides a service for routing UI action requests to appropriate listeners.
 * AI generated comments
 */

import { Injectable } from "@angular/core";
import { GeboUIActionRequestListener, GeboUIActionRequest } from "./actions.model";

/**
 * Service responsible for managing UI action request routing within the application.
 * This acts as a centralized hub where UI action requests can be routed to the 
 * appropriate listeners based on the targetType of the action.
 * The service maintains a registry of listeners and provides methods for registration,
 * removal, and action routing.
 */
@Injectable({providedIn:"root"})
export class GeboUIActionRoutingService {
        /**
         * Array of registered action request listeners
         */
        private listeners:GeboUIActionRequestListener[]=[];
        
        /**
         * Registers a new action request listener to the service
         * @param listener The listener to be registered
         */
        public registerListener(listener:GeboUIActionRequestListener) {
            this.listeners.push(listener);
        }
        
        /**
         * Removes a specific listener from the service
         * @param listener The listener to be removed
         */
        public removeListener(listener:GeboUIActionRequestListener) {
            this.listeners=this.listeners.filter(x=>x!==listener);
        }
        
        /**
         * Routes an action request to the appropriate listener based on targetType
         * @param action The action request to be routed
         * @returns boolean indicating whether the action was successfully handled
         */
        public routeEvent(action:GeboUIActionRequest ):boolean {
            let completed:boolean=false;
            if (this.listeners) {
                // Filter listeners to match the action's targetType
                const filtered=this.listeners?.filter(listener=>listener.targetType===action?.targetType);
                if (filtered && filtered.length) {
                    const listener:GeboUIActionRequestListener=filtered[0];
                    if (completed===false && listener.handleAction(action)) {
                        completed=true;
                    }
                }
                /*
                this.listeners.forEach(listener=>{
                    if (listener.targetType===action?.targetType) {
                        
                    }
                });*/
            }
            return completed;
        }
}