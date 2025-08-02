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
 * Component for handling user authentication and login functionality.
 * This component renders a login form with username and password fields,
 * manages login state, validates system setup status, and navigates users
 * accordingly based on authentication results or system status.
 */
import { Component, OnInit } from "@angular/core";

import { LoginService } from "./login.service";
import { ToastMessageOptions } from "primeng/api";
import { FormControl, FormGroup } from "@angular/forms";
import { GeboFastInstallationSetupControllerService, Oauth2ClientAuthorizativeInfo } from "@Gebo.ai/gebo-ai-rest-api";
import { ActivatedRoute, Router } from "@angular/router";

@Component({
  selector: "gebo-ai-login-component", templateUrl: "login.component.html",
  standalone: false
})
export class LoginComponent implements OnInit {

  /**
   * Form group containing login credentials input controls
   */
  formGroup: FormGroup = new FormGroup({
    username: new FormControl(),
    password: new FormControl()
  });

  /**
   * Flag indicating whether a login request is in progress
   */
  loading: boolean = false;

  /**
   * Stores user profile information after successful authentication
   */
  profile?: any;

  /**
   * Collection of toast messages to display to the user
   */
  userMessages: ToastMessageOptions[] = [{ summary: "Welcome to gebo.ai", detail: "Enter your username and password to login", severity: "success" }];
  providers: Oauth2ClientAuthorizativeInfo[] = [];

  /**
   * Component constructor initializing necessary services for authentication and navigation
   * 
   * @param loginService Service handling authentication logic
   * @param geboFastSetupControllerService Service to verify system installation status
   * @param router Angular router for navigation
   * @param activatedRoute Current activated route
   */
  public constructor(public loginService: LoginService,
    private geboFastSetupControllerService: GeboFastInstallationSetupControllerService,

    private router: Router,
    private activatedRoute: ActivatedRoute) {

  }

  /**
   * Verifies if the system has been properly set up
   * Redirects to setup page if installation is incomplete
   */
  private checkSystemSetup() {
    this.geboFastSetupControllerService.getInstallationStatus().subscribe(status => {
      if (status.result === false) {
        this.router.navigate(["..", "setup"], { relativeTo: this.activatedRoute });
      } else {
        this.loading = true;
        this.loginService.getOauth2LoginOptions().subscribe({
          next: (providers) => {
            this.providers = providers;
            
          },
          complete: () => {
            this.loading = false;
          }
        });
      }
    });
  }

  /**
   * Lifecycle hook that runs when the component initializes
   * Checks if the system is properly set up before allowing login
   */
  ngOnInit(): void {
    this.checkSystemSetup();

  }

  /**
   * Handles user login form submission
   * Attempts to authenticate the user with provided credentials
   * On success, redirects to the application main page
   * Updates user messages based on authentication result
   */
  login(): void {
    this.loginService.login(this.formGroup.value).subscribe(x => {
      this.userMessages = x.messages;
      if (x.userInfo) {
        this.router.navigate(["..", "reloader"], { relativeTo: this.activatedRoute, state: { forceReload: true } });
      }
    });
  }
  public onOauth2Click(clicked: Oauth2ClientAuthorizativeInfo) {
    if (clicked.registrationId) {
      
      this.loginService.loginWithOauth2(clicked).subscribe({
        next:(value)=>{

        },
        complete:()=>{
          
        }
      });
    }
  }
}