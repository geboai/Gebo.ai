/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

/*
 * AI generated comments
 * This component provides a context menu for adding various items to a project.
 * It displays menu options either as buttons or standard menu items based on configuration.
 * The component integrates with the Gebo.ai framework for handling actions and routing events.
 */
import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from "@angular/core";
import { GProject, GeboModulesConfigControllerService } from "@Gebo.ai/gebo-ai-rest-api";

import { MenuItem } from "primeng/api";
import { GeboAIEntitiesSettingWizardConfiguration } from "../base-entity-editing-component/entities-modification-wizard";
import { GeboUIActionRoutingService } from "../../architecture/gebo-ui-action-routing.service";
import { GeboAIPluggableKnowledgeAdminBaseTreeSearchService } from "../../services/pluggable-knowledge-base-admin-tree-search.service";
import { GeboActionPerformedEvent, GeboUIActionRequest } from "../../architecture/actions.model";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../field-host-component-iface/field-host-component-iface";

/**
 * Constants defining different module types that can be added to a project
 */
const SHARED_FILESYSTEM_MODULE = "shared-filesystem-module";
const DATABASES_MODULE = "databases-module";
const UPLOADS_MODULE = "uploads-module";
const GIT_MODULE = "git-module";
const WEBCRAWLER_MODULE = "webcrawler-module";
const GOOGLEDRIVE_MODULE = "google-drive-module";
const CONFLUENCE_MODULE = "confluence-module";

/**
 * Component that provides a context menu for adding various items to a project.
 * This can be displayed as buttons, menu items, or as a full page depending on the configuration.
 * It handles the generation of menu items based on the project data and available modules.
 */
@Component({
    selector: "project-add-context-menu",
    templateUrl: "project-add-context-menu.component.html",
    standalone: false,
        providers: [{ provide: GEBO_AI_MODULE, useValue: "ProjectAddContextMenuModule", multi: false },{ provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("ProjectAddContextMenuComponent")}]
})
export class ProjectAddContextMenuComponent implements OnInit, OnChanges {
  /**
   * The project data to which items will be added
   */
  @Input() data?: GProject;
  
  /**
   * Flag to determine if options should be displayed as buttons instead of menu items
   */
  @Input() showAsButtons: boolean = false;
  
  /**
   * Flag to enable/disable sub-project addition functionality
   */
  @Input() subProjectAddEnabled: boolean = true;
  
  /**
   * Flag to display the menu as a full page rather than a dropdown
   */
  @Input() showAsPage: boolean = false;
  
  /**
   * Configuration for wizard steps if this is part of a multi-step wizard
   */
  @Input() wizardStepsConfigurations?: GeboAIEntitiesSettingWizardConfiguration[];
  
  /**
   * The ID of the current wizard step configuration
   */
  @Input() actualWizardStepConfigrationId?: string;
  
  /**
   * Event emitter that triggers when the UI needs to be refreshed after an action
   */
  @Output() refreshUIEvent: EventEmitter<GeboActionPerformedEvent> = new EventEmitter();

  /**
   * Array of menu items to be displayed in the context menu
   */
  public items: MenuItem[] = [];
  
  /**
   * Constructor initializes the required services for action routing and menu generation
   */
  constructor(private actionServices: GeboUIActionRoutingService,
    private geboModulesConfigService: GeboModulesConfigControllerService,
    private kbTreeSearchService: GeboAIPluggableKnowledgeAdminBaseTreeSearchService) {

  }
  
  /**
   * Lifecycle hook that responds to input changes, specifically when project data changes
   */
  ngOnChanges(changes: SimpleChanges): void {
    if (this.data && changes["data"]) {
      this.refreshUI();
    }
  }
  
  /**
   * Refreshes the UI by generating the menu items based on the current project data
   * Uses the knowledge tree search service to generate appropriate menu options
   */
  refreshUI(): void {
    const actionConsumer = (action: GeboUIActionRequest) => {
      this.actionServices.routeEvent(action);
    }
    const callBack = (event: GeboActionPerformedEvent) => {
      this.refreshUIEvent.emit(event)
    };
    if (this.data) {
      this.items = this.kbTreeSearchService.generateAddToProjectMenu(this.subProjectAddEnabled, this.data, actionConsumer, callBack, this.wizardStepsConfigurations, this.actualWizardStepConfigrationId);
    }
  }
  
  /**
   * Lifecycle hook for component initialization
   */
  ngOnInit(): void {

  }


  /**
   * Executes the command associated with a menu item when it is clicked
   * @param cfg The menu item configuration that was selected
   */
  execute(cfg: MenuItem) {
    if (cfg.command)
      cfg.command({ item: cfg });
  }
}