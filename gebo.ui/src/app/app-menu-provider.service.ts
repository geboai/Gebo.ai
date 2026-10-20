/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

import { Injectable } from '@angular/core';
import { ApplicationMenuProviderService, SetupStatus } from '@Gebo.ai/reusable-ui';
import { UserInfo, GeboMcpServerUserControllerService, GeneratedUserApiKeyControllerService } from '@Gebo.ai/gebo-ai-rest-api';
import { MegaMenuItem } from 'primeng/api';
import { Observable, BehaviorSubject, forkJoin, of } from 'rxjs';
import { catchError, map, shareReplay } from 'rxjs/operators';
import { GeboSetupWizardService } from '@Gebo.ai/gebo-ai-admin-ui';

const editMyProfileMenuItemId: string = "editMyProfileMenuItem";
const setupItemId: string = "setupMenuItem";
const adminItemId: string = "adminMenuItem";
const privilegedMenuIds: string[] = [setupItemId, adminItemId];
const menuItemsProtos: MegaMenuItem[] = [
  { icon: "pi pi-comments", label: "Chat", routerLink: 'ui/chat', id: "chatMenuItem" },
  { icon: "pi pi-wrench", label: "Setup", routerLink: "ui/admin-setup", id: setupItemId },
  { icon: "pi pi-cog", label: "Admin", routerLink: 'ui/admin', id: adminItemId },
  { icon: "pi pi-user", label: "edit profile", routerLink: "ui/currentProfile", id: editMyProfileMenuItemId },
  { icon: "pi pi-sign-out", label: "logout", routerLink: 'ui/logout', id: "logoutMenuItem" }
];

@Injectable({
  providedIn: 'root'
})
export class AppMenuProviderService extends ApplicationMenuProviderService {
  private menuItemsSubject = new BehaviorSubject<MegaMenuItem[]>([]);
  // The one status poll chain: a second start never spawns another one (every
  // 3-minute poll used to add a 1-second blink loop re-rendering the menubar,
  // which saturated the main thread of a page left open).
  private pollTimer?: ReturnType<typeof setTimeout>;
  private static readonly POLL_PERIOD_MS = 3 * 60 * 1000;
  private setupStatus?: SetupStatus;
  private integrationsAllowed$: Observable<boolean> | null = null;

  constructor(
    private geboWizardSetupService: GeboSetupWizardService,
    private geboMcpServerUserControllerService: GeboMcpServerUserControllerService,
    private generatedUserApiKeyControllerService: GeneratedUserApiKeyControllerService
  ) {
    super();
  }

  private checkIntegrationsAllowed(): Observable<boolean> {
    if (!this.integrationsAllowed$) {
      this.integrationsAllowed$ = forkJoin([
        this.geboMcpServerUserControllerService.getUsersCanAccessMcpServersList().pipe(catchError(() => of(false))),
        this.generatedUserApiKeyControllerService.isUserGeneratedApiKeyGenerationAllowed().pipe(catchError(() => of(false)))
      ]).pipe(
        map(([mcpAllowed, apiKeyAllowed]) => mcpAllowed && apiKeyAllowed),
        shareReplay(1)
      );
    }
    return this.integrationsAllowed$;
  }

  public getMenuItems(userInfo?: UserInfo): Observable<MegaMenuItem[]> {
    if (!userInfo) {
      this.integrationsAllowed$ = null;
      this.stopSetupStatePolling();
      this.menuItemsSubject.next([]);
      return this.menuItemsSubject.asObservable();
    }

    const isAdmin: boolean = userInfo.roles && userInfo.roles.find(c => c === 'ADMIN') ? true : false;

    this.checkIntegrationsAllowed().subscribe(allowed => {
      const items: MegaMenuItem[] = [];
      const protos = [...menuItemsProtos];

      if (allowed) {
        const profileIndex = protos.findIndex(x => x.id === editMyProfileMenuItemId);
        const integrationsItem: MegaMenuItem = {
          icon: "pi pi-link",
          label: "Api Keys/MCP",
          routerLink: 'ui/user-integrations',
          id: "userIntegrationsMenuItem"
        };
        if (profileIndex !== -1) {
          protos.splice(profileIndex, 0, integrationsItem);
        } else {
          protos.push(integrationsItem);
        }
      }

      if (isAdmin === true) {
        protos.forEach(entry => {
          items.push({ ...entry });
        });
      } else {
        protos.forEach(entry => {
          if (!privilegedMenuIds.find(x => x === entry.id)) {
            items.push({ ...entry });
          }
        });
      }

      this.menuItemsSubject.next(items);

      if (isAdmin === true) {
        this.pollSetupState();
      }
    });

    return this.menuItemsSubject.asObservable();
  }


  /**
   * Shows the setup state on the Setup menu item, once per status change: only an
   * incomplete setup (a mandatory step missing) blinks red (a CSS animation, see
   * styles.scss: nothing re-renders the menubar every second). A complete setup
   * (all mandatory done, some optional missing) is shown like a full one: the item
   * carries no marker.
   */
  private showSetupState(): void {
    const currentItems = this.menuItemsSubject.getValue();
    const setupItem = currentItems.find(x => x.id === setupItemId);
    if (!setupItem) return;
    if (this.setupStatus === 'incomplete') {
      setupItem.styleClass = "gebo-ai-setup-incomplete";
      setupItem.title = "Setup is incomplete, please consider press here and manage it";
      setupItem.state = { incomplete: true };
    } else {
      setupItem.styleClass = undefined;
      setupItem.title = undefined;
      setupItem.state = undefined;
    }
    console.debug("[app-menu] the Setup item shows the setup status " + this.setupStatus);
    this.menuItemsSubject.next([...currentItems]);
  }

  private pollSetupState(): void {
    // a login or a user info refresh polls now; the 3-minute chain stays single
    if (this.pollTimer !== undefined) {
      clearTimeout(this.pollTimer);
      this.pollTimer = undefined;
    }
    this.geboWizardSetupService.getGlobalSetupStatus().subscribe({
      next: (status) => {
        if (status !== this.setupStatus) {
          console.info("[app-menu] setup status " + (this.setupStatus ?? "unknown") + " -> " + status);
        }
        this.setupStatus = status;
        this.showSetupState();
        if (status !== 'full') {
          this.pollTimer = setTimeout(() => {
            this.pollTimer = undefined;
            this.pollSetupState();
          }, AppMenuProviderService.POLL_PERIOD_MS);
        }
      },
      error: (error) => {
        console.warn("[app-menu] the setup status could not be read, polling again later", error);
        this.pollTimer = setTimeout(() => {
          this.pollTimer = undefined;
          this.pollSetupState();
        }, AppMenuProviderService.POLL_PERIOD_MS);
      }
    });
  }

  /** On logout: no poll survives the user. */
  private stopSetupStatePolling(): void {
    if (this.pollTimer !== undefined) {
      clearTimeout(this.pollTimer);
      this.pollTimer = undefined;
      console.debug("[app-menu] setup status polling stopped");
    }
    this.setupStatus = undefined;
  }
}
