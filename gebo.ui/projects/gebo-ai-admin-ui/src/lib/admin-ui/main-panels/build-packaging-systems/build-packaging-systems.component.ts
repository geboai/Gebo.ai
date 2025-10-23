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
 * This file contains components related to Build and Packaging Systems management in the Gebo.ai application.
 * It provides functionality to display and manage different build system types and their configurations.
 */

import { Component, OnInit } from "@angular/core";
import { BuildSystemsControllerService, GBuildSystem,  GBuildSystemType } from "@Gebo.ai/gebo-ai-rest-api";
import { AncestorPanelComponent } from "../ancestor-panel/ancestor-admin-panel.component";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST } from "@Gebo.ai/reusable-ui";

/**
 * Interface representing a node in the build system hierarchy.
 * Contains a build system type and its associated configurations.
 */
interface BuildSystemNode {
    type:GBuildSystemType;
    configs:GBuildSystem[];
}

/**
 * Component for managing build and packaging systems.
 * Extends the AncestorPanelComponent to inherit common admin panel functionality.
 * Displays build system types and their configurations in a hierarchical structure.
 */
@Component({
    selector: "build-packaging-systems-component",
    templateUrl: "build-packaging-systems.component.html",
    standalone: false,
    providers:[{ provide: GEBO_AI_FIELD_HOST, multi: true, useValue: fieldHostComponentName("BuildPackagingSystemsComponent")}]
})
export class BuildPackagingSystemsComponent extends AncestorPanelComponent  implements OnInit{
    /**
     * Overrides the parent method to reload data displayed in the view.
     * Currently implementation is empty.
     */
    public override reloadViewedData(): void {
        
    }

    /**
     * Array of build system nodes to be displayed in the component.
     * Each node contains a build system type and its configurations.
     */
    nodes:BuildSystemNode[]=[];

    /**
     * Constructor that initializes the build systems service.
     * 
     * @param buildSystemsService Service for interacting with build system APIs
     */
    constructor(private buildSystemsService:BuildSystemsControllerService) {
        super()
    }

    /**
     * Returns the display title for a build system node.
     * Uses the type description if available.
     * 
     * @param b The build system node
     * @returns The description string or empty string if not available
     */
    titleOf(b:BuildSystemNode):string {
        let description:string='';
        if (b?.type?.description) {
            description=b.type.description;
        }
        return description;
    }

    /**
     * Initializes the component by loading build system types and their configurations.
     * For each build system type, retrieves the associated configurations and populates the nodes array.
     */
    ngOnInit(): void {
        this.buildSystemsService.getBuildSystemTypes().subscribe({
            next:(types:GBuildSystemType[])=>{
                if (types) {
                    types.forEach(entry=>{
                        const thisEntry:BuildSystemNode={type:entry,configs:[]};
                        this.nodes.push(thisEntry);
                        const code:string=entry.code?entry.code:"";
                        this.buildSystemsService.getBuildSystemConfigs(code).subscribe({
                            next:(value: GBuildSystem[]) =>{
                                thisEntry.configs=value;
                            }
                        });
                    })
                }
            }
        });
            
        
    }
}