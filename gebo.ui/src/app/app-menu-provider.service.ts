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
  private blinkState: boolean = false;
  private stopBlink: boolean = true;
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


  private startBlinkSetupState(): void {
    const currentItems = this.menuItemsSubject.getValue();
    const setupItem = currentItems.find(x => x.id === setupItemId);
    if (!setupItem) return;

    if (this.blinkState === false && this.stopBlink === false) {
      if (this.setupStatus === 'incomplete') {
        setupItem.style = { "text-color": "white", "background-color": "red" };
      } else if (this.setupStatus === 'complete') {
        setupItem.style = { "text-color": "white", "background-color": "orange" };
      }
      setupItem.title = "Setup is incomplete, please consider press here and manage it";
      setupItem.state = { incomplete: true };
      this.blinkState = true;
    } else {
      setupItem.style = undefined;
      setupItem.title = "Setup is incomplete, please consider press here and manage it";
      setupItem.state = { incomplete: true };
      this.blinkState = false;
    }

    this.menuItemsSubject.next([...currentItems]);

    if (!this.stopBlink) {
      setTimeout(() => {
        this.startBlinkSetupState();
      }, 1000);
    }
  }

  private pollSetupState(): void {
    this.geboWizardSetupService.getGlobalSetupStatus().subscribe({
      next: (status) => {
        this.setupStatus = status;
        if (status !== 'full') {
          this.stopBlink = false;
          this.startBlinkSetupState();
          //repeat setup state poll in 3 minutes
          setTimeout(() => {
            this.pollSetupState();
          }, 1000 * 60 * 3);
        } else {
          this.stopBlink = true;
        }
      }
    });
  }
}
