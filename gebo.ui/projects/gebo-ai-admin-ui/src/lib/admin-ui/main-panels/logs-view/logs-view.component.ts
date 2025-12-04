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
 * This component is responsible for displaying logs within the Gebo.ai application.
 * It extends the AncestorPanelComponent and handles the loading of endpoint data
 * for displaying in a tabbed interface.
 */
import { Component, OnInit } from "@angular/core";
import { FormGroupMetaInfo, GeboAngularFormGroupMetaInfoControllerService, JobLauncherControllerService, LogViewControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboUIActionRoutingService } from "@Gebo.ai/reusable-ui";
import { AncestorPanelComponent } from "../ancestor-panel/ancestor-admin-panel.component";

@Component({
    selector: "gebo-ai-logs-view-component",
    templateUrl: "logs-view.component.html",
    standalone: false,
    providers: [{ provide: GEBO_AI_MODULE, useValue: "GeboAiLogsViewPanelModule", multi: false }, {
        provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("GeboAiLogsViewComponent")
    }]
})
export class GeboAiLogsViewComponent extends AncestorPanelComponent implements OnInit {
    /**
     * Overrides the parent method to reload viewed data.
     * Fetches form group meta information and filters for endpoint types,
     * populating the endpoint tabs array with the retrieved data.
     * Sets loading state during the operation.
     */
    public override reloadViewedData(): void {
        this.loading = true;
        this.geboFormGroupControllerService.getFormGroupsMetaInfos().subscribe({
            next: (fGroups: FormGroupMetaInfo[]) => {
                if (fGroups) {
                    const tabs: { label: string, className: string }[] = [];
                    const endPointTypes = fGroups.filter(x => x.classCategoryName === 'ai.gebo.knlowledgebase.model.projects.GProjectEndpoint');
                    if (endPointTypes) {
                        endPointTypes.forEach(entry => {
                            if (entry.description && entry.className) {
                                tabs.push({ label: entry.description, className: entry.className });
                            }
                        });
                    }
                    this.endpointTabs = tabs;
                }
            },
            complete: () => {
                this.loading = false;
            }
        });
    }
    /**
     * Flag indicating whether data is currently being loaded
     */
    public loading: boolean = false;

    /**
     * Constructor initializes necessary services for the logs view component
     * 
     * @param jobLauncherControllerService Service for launching jobs
     * @param logsControllerService Service for accessing log data
     * @param geboUIActionRoutingService Service for UI action routing
     * @param geboFormGroupControllerService Service for handling form group meta information
     */
    constructor(private jobLauncherControllerService: JobLauncherControllerService,
        private logsControllerService: LogViewControllerService,
        private geboUIActionRoutingService: GeboUIActionRoutingService,
        private geboFormGroupControllerService: GeboAngularFormGroupMetaInfoControllerService) {
        super();
    }

    /**
     * Array to store endpoint tab information including display label and associated class name
     */
    endpointTabs: { label: string, className: string }[] = [];

    /**
     * Lifecycle hook that is called after component initialization
     * Triggers the data loading process
     */
    ngOnInit(): void {
        this.reloadViewedData();
    }
}