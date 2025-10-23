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
 * This file defines the GeboAiAdminComponent which serves as the main administrative interface for Gebo.ai.
 * It handles user authentication, setup status management, and interfaces with various admin panels.
 */

import { Component, OnInit, ViewChild } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { UserControllerService, UserInfo } from "@Gebo.ai/gebo-ai-rest-api";
import { GeboUIActionRoutingService, GeboActionType, SetupWizardService, SetupStatus, GeboAIModulesService, GeboAIModules, GeboAIPluggableModulesConfigService, GeboAIEnabledModulesConfig, GEBO_AI_FIELD_HOST, fieldHostComponentName } from "@Gebo.ai/reusable-ui";
import { AncestorPanelComponent } from "./main-panels/ancestor-panel/ancestor-admin-panel.component";
import { Button } from "primeng/button";

/**
 * Constant key used for storing setup status in local storage
 */
const LOCAL_STORAGE_SETUP_STATUS = "LocalStorageSetupStatus";

/**
 * Interface defining the structure for the setup status stored in local storage
 * Contains the setup status and whether the setup was abandoned by the user
 */
interface LocalStorageSetupStatus {
  status?: SetupStatus,
  abandoned: boolean
}

/**
 * Main admin component for the Gebo.ai application
 * Handles user authentication, setup workflow, and navigation between admin panels
 * Provides an interface for managing the application's configuration and modules
 */
@Component({
  selector: "gebo-ai-admin-component",
  templateUrl: "gebo-ai-admin.component.html",
  providers: [GeboUIActionRoutingService, { provide: GEBO_AI_FIELD_HOST, multi: true, useValue: fieldHostComponentName("GeboAiAdminComponent") }],
  standalone: false
})
export class GeboAiAdminComponent implements OnInit {
  /** Indicates if the component is in a loading state */
  public loading: boolean = false;

  /** References to the child ancestor panel components for controlling their behavior */
  @ViewChild("child1") child1?: AncestorPanelComponent;
  @ViewChild("child2") child2?: AncestorPanelComponent;
  @ViewChild("child3") child3?: AncestorPanelComponent;
  @ViewChild("child4") child4?: AncestorPanelComponent;
  @ViewChild("child5") child5?: AncestorPanelComponent;
  @ViewChild("child6") child6?: AncestorPanelComponent;
  @ViewChild("child7") child7?: AncestorPanelComponent;

  /** Current authenticated user information */
  user?: UserInfo;

  /** Current setup status: complete, incomplete, or full */
  private setupStatus?: "complete" | "incomplete" | "full";

  /** Flag indicating if the setup window has been opened */
  private setupWindowOpened: boolean = false;

  /** Flag indicating if the setup window has been abandoned/dismissed */
  private setupWindowAbandoned: boolean = false;

  /** Severity level for the setup button UI styling (danger for incomplete setup) */
  public setupButtonSeverity: any = "danger";

  /** Configuration for enabled Gebo AI modules */
  geboConfig: GeboAIEnabledModulesConfig | undefined;

  /**
   * Computed property that determines if the setup window should be visible
   * @returns true if the setup window should be displayed
   */
  get setupWindowVisible(): boolean {
    return this.setupWindowOpened === true && this.setupWindowAbandoned === false;
  }

  /**
   * Constructor initializes all required services for the admin component
   */
  constructor(
    private geboAIModulesService: GeboAIPluggableModulesConfigService,
    private actionServices: GeboUIActionRoutingService,
    private userInfoService: UserControllerService,
    private setupWizardService: SetupWizardService,
    private router: Router,
    private activatedRoute: ActivatedRoute) {

  }

  /**
   * Stores the current setup status and abandonment state in local storage
   */
  private storeStatus(): void {
    const data: LocalStorageSetupStatus = {
      abandoned: this.setupWindowAbandoned,
      status: this.setupStatus
    };
    localStorage.setItem(LOCAL_STORAGE_SETUP_STATUS, JSON.stringify(data));
  }

  /**
   * Initializes the component, loads user information, setup status, and handles query parameters
   * Redirects non-admin users to the chat page
   */
  ngOnInit(): void {
    this.geboConfig = this.geboAIModulesService.getConfig();
    const status = localStorage.getItem(LOCAL_STORAGE_SETUP_STATUS);
    if (status) {
      try {
        const parsed: LocalStorageSetupStatus = JSON.parse(status);
        this.setupStatus = parsed?.status;
        this.setupWindowAbandoned = parsed?.abandoned;
      } catch (e) { }
    }
    this.loading = true;
    this.userInfoService.getCurrentUser().subscribe({
      next: (user) => {
        this.user = user;
        const adminIdx = user?.roles?.findIndex(x => x === 'ADMIN');
        const isAdmin: boolean = adminIdx !== undefined && adminIdx >= 0 ? true : false;
        if (isAdmin == false) {
          this.router.navigate(["..", "chat"], { relativeTo: this.activatedRoute });
        }
      },
      error: (error) => {
        console.error(error);
        this.router.navigate(["..", "chat"], { relativeTo: this.activatedRoute });
      },
      complete: () => {
        this.loading = false;
      }
    });
    // Handle query parameters to open specific entities
    this.activatedRoute.queryParams.subscribe(p => {
      const _entityName = p["openEntity"];
      const _mode = p["mode"];
      if (_entityName && _mode) {
        this.actionServices.routeEvent({
          contextType: "admin",
          context: {},
          actionType: _mode === "NEW" ? GeboActionType.NEW : GeboActionType.OPEN,
          targetType: _entityName,
          target: {},
          onActionPerformed: (action) => {

          }

        });
      }
    });
  }

  /**
   * Updates the setup status based on the status provided by the setup wizard
   * Updates UI elements accordingly
   * @param status The current setup status
   */
  onSetupStatus(status: SetupStatus) {
    this.setupStatus = status;
    this.setupWindowOpened = this.setupStatus === "incomplete";
    this.setupButtonSeverity = this.setupStatus === "incomplete" ? "danger" : "primary";
    this.storeStatus();
  }

  /**
   * Opens the setup wizard window and resets the abandoned flag
   */
  openSetupWindow(): void {
    this.setupWindowAbandoned = false;
    this.setupWindowOpened = true;
    this.storeStatus();
  }

  /**
   * Hides the setup wizard and marks it as abandoned
   */
  hideSetup() {
    this.setupWindowOpened = false;
    this.setupWindowAbandoned = true;
    this.storeStatus();
  }

  /**
   * Called when a specific panel tab is activated
   * Reloads data for the activated child panel component
   * @param index The index of the activated panel
   */
  public activatedIndex(index: number) {
    const child: AncestorPanelComponent = (this as any)["child" + index];
    if (child) {
      child.reloadViewedData();
    }
    /*this.setupWizardService.getSetupStatus().subscribe({
      next:(statusValue)=>{
         this.setupStatus=statusValue;
      },
      complete:()=>{

      }
    });*/
  }
}