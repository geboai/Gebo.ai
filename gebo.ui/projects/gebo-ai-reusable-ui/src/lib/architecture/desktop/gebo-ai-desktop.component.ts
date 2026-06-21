/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

import { Component, Input, OnInit, Optional } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { UserInfo } from '@Gebo.ai/gebo-ai-rest-api';
import { MegaMenuItem } from 'primeng/api';
import { LoginService } from '../../infrastructure/login/login.service';
import { PrimeNG } from 'primeng/config';
import { Subscription } from 'rxjs';
import { GeboAITranslationService } from '../../controls/field-translation-container/gebo-translation.service';
import { resetAuth, saveAuth } from '../../infrastructure/gebo-credentials';
import { ApplicationMenuProviderService } from './application-menu-provider.service';

@Component({
  selector: 'gebo-ai-desktop',
  templateUrl: './gebo-ai-desktop.component.html',
  styleUrls: ['./gebo-ai-desktop.component.scss'],
  standalone: false
})
export class GeboAIDesktopComponent implements OnInit {
  @Input() version: string = '';
  public userLogged: boolean = false;
  public userInfo?: UserInfo;
  public menuItems: MegaMenuItem[] = [];
  private subscription?: Subscription;

  constructor(
    private primengConfig: PrimeNG,
    private loginService: LoginService,
    private geboTranslationService: GeboAITranslationService,
    @Optional() private applicationMenuProviderService?: ApplicationMenuProviderService
  ) {
    this.loginService.authDataSubject.subscribe({
      next: (securityHedaerData) => {
        console.log("Auth refresh");
        if (!securityHedaerData) {
          resetAuth();
        } else {
          saveAuth(securityHedaerData);
        }
      }
    });
  }

  private loadUserAndMenu(): void {
    this.loginService.loadUserProfile().subscribe(x => {
      this.userLogged = (x && !(x instanceof HttpErrorResponse) && x.username) ? true : false;
      this.userInfo = this.userLogged ? x : undefined;

      if (this.userLogged) {
        if (this.subscription) {
          this.subscription.unsubscribe();
          this.subscription = undefined;
        }

        if (this.applicationMenuProviderService) {
          this.subscription = this.applicationMenuProviderService.getMenuItems(this.userInfo).subscribe(items => {
            this.menuItems = items;
            // Translate menu items
            this.geboTranslationService.translateMegaMenuItems("AppModule", "AppComponent", this.menuItems).subscribe({
              next: (translated) => {
                if (translated) {
                  this.menuItems = translated;
                }
              }
            });
          });
        }
      } else {
        this.menuItems = [];
      }
    });
  }

  ngOnInit() {
    this.primengConfig.ripple.set(true);

    if (!this.loginService.isOauth2LandingPage()) {
      this.loginService.logged.subscribe(user => {
        this.userLogged = user ? true : false;
        if (this.userLogged) {
          this.loadUserAndMenu();
        } else {
          this.menuItems = [];
          this.userInfo = undefined;
        }
      });
      this.loadUserAndMenu();
    }
    this.loginService.loginActivated.subscribe({
      next: (activated) => {
        this.menuItems = [];
        this.userLogged = false;
        this.userInfo = undefined;
      }
    });
  }
}
