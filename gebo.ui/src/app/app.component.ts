/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */




import { Component, OnInit } from '@angular/core';
import { UserInfo } from '@Gebo.ai/gebo-ai-rest-api';
import { MegaMenuItem } from 'primeng/api';
import { LoginService } from '../../projects/gebo-ai-reusable-ui/src/lib/infrastructure/login/login.service';
import { GeboSetupWizardService } from '@Gebo.ai/gebo-ai-admin-ui';
import { GeboAITranslationService, resetAuth, saveAuth, SetupStatus } from '@Gebo.ai/reusable-ui';
import { PrimeNG } from 'primeng/config';
import { Subscription } from 'rxjs';
const menuItemsProtos: MegaMenuItem[] = [
  { icon: "pi pi-comments", label: "Gebo.ai Chat", routerLink: 'ui/chat', id: "chat" },
  { icon: "pi pi-wrench", label: "Setup", routerLink: "ui/admin-setup", id: "setup" },
  { icon: "pi pi-cog", label: "Gebo.ai admin", routerLink: 'ui/admin', id: "admin" },

  ];
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
  standalone: false
})
export class AppComponent implements OnInit {
  public loading: boolean = false;
  public userLogged: boolean = false;
  public userInfo?: UserInfo;
  public menuItems: MegaMenuItem[] = menuItemsProtos;
  public servicesMenu:MegaMenuItem[]=[{ icon: "pi pi-user", label: "my profile", routerLink: 'ui/currentProfile', id: "currentProfile" },
  { icon: "pi pi-sign-out", label: "logout", routerLink: 'ui/logout', id: "logout" }];
  private blinkState: boolean = false;
  private stopBlink: boolean = true;
  private setupStatus?: SetupStatus;
  constructor(
    private primengConfig: PrimeNG,
    private loginService: LoginService,
    private geboTranslationService: GeboAITranslationService,
    private geboWizardSetupService: GeboSetupWizardService) {
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
  private startBlinkSetupState(): void {
    const setupItem = this.menuItems.find(x => x.id === "setup");
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
    this.menuItems = [...this.menuItems];
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
  private subscription?: Subscription;
  private loadUserAndMenu():void {
    this.loginService.loadUserProfile().subscribe(x => {
        this.userLogged = x ? true : false;
        const items: MegaMenuItem[] = [];
        if (this.userLogged) {
          const isAdmin: boolean = x.roles && x.roles.find(c => c === 'ADMIN') ? true : false;
          if (isAdmin === true) {
            menuItemsProtos.forEach(entry => {
              items.push(entry);
            });

          } else {
            menuItemsProtos.forEach(entry => {
              if (entry.id !== 'admin' && entry.id !== "setup") {
                items.push(entry);
              }
            });
          }

          this.menuItems = items;
          if (this.subscription) {
            this.subscription.unsubscribe();
            this.subscription = undefined;
          }
          this.subscription = this.geboTranslationService.translateMegaMenuItems("AppModule", "AppComponent", this.menuItems).subscribe({
            next: (translated) => {
              if (translated) {
                this.menuItems = translated;
              }
            }
          }
          );
          if (isAdmin === true) {
            this.pollSetupState();
          }
        }
        this.userInfo = x;
      });
  }
  ngOnInit() {
    this.primengConfig.ripple.set(true);

    if (!this.loginService.isOauth2LandingPage()) {
      this.loginService.logged.subscribe(user => {
        this.userLogged = user ? true : false;
      });
      this.loadUserAndMenu();
    }
    this.loginService.loginActivated.subscribe({
      next: (activated) => {
        this.menuItems=[];
        this.userLogged=false;
      }
    });
  }
  title = 'Gebo.ai, the RAG system for software developers';
}
