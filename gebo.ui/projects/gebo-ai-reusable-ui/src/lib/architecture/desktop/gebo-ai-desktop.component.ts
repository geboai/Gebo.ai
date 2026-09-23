/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

import { Component, Input, OnDestroy, OnInit, Optional } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { UserInfo } from '@Gebo.ai/heimdall';
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
export class GeboAIDesktopComponent implements OnInit, OnDestroy {
  @Input() version: string = '';
  public userLogged: boolean = false;
  public userInfo?: UserInfo;
  public menuItems: MegaMenuItem[] = [];
  private subscription?: Subscription;
  private bodyClassObserver?: MutationObserver;
  private scrollLockSweepScheduled: boolean = false;

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

  /**
   * Guards against a leaked body scroll-lock. A modal p-dialog adds `p-overflow-hidden` to the
   * document body (freezing page scroll) and only removes it through its leave animation - but
   * several of ours are torn down via structural directives / dynamic component destruction, so
   * that cleanup never runs and the lock stays stuck, killing vertical scroll on every screen.
   *
   * We watch the body class and, whenever the lock is present while no modal dialog is actually
   * open (PrimeNG marks the open ones with data-p-scrollblocker-active), drop it. The check is
   * deferred a little so a genuine modal opening - which sets that marker in the same render tick
   * as the class - is never unlocked from under it.
   */
  private installBodyScrollLockGuard(): void {
    if (typeof document === "undefined" || typeof MutationObserver === "undefined") {
      return;
    }
    this.bodyClassObserver = new MutationObserver(() => this.scheduleScrollLockSweep());
    this.bodyClassObserver.observe(document.body, { attributes: true, attributeFilter: ["class"] });
    this.scheduleScrollLockSweep();
  }

  private scheduleScrollLockSweep(): void {
    if (this.scrollLockSweepScheduled) {
      return;
    }
    this.scrollLockSweepScheduled = true;
    setTimeout(() => {
      this.scrollLockSweepScheduled = false;
      if (document.body.classList.contains("p-overflow-hidden")
        && document.querySelectorAll('[data-p-scrollblocker-active="true"]').length === 0) {
        document.body.classList.remove("p-overflow-hidden");
        document.body.style.removeProperty("--scrollbar-width");
      }
    }, 120);
  }

  ngOnDestroy(): void {
    this.bodyClassObserver?.disconnect();
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
    this.installBodyScrollLockGuard();

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
