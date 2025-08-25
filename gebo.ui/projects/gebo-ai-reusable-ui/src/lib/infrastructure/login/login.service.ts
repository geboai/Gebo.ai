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
 * This file contains the implementation of the LoginService which handles user authentication, 
 * user profile management, and session control in the application.
 */

import { Inject, Injectable } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { AuthControllerService, BASE_PATH, ChangePasswordParam, ChangePasswordResponse, Oauth2ClientAuthorizativeInfo, Oauth2ClientConfig, AuthProvidersControllerService, SecurityHeaderData, UserControllerService, UserInfo, TokenRenewControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { ToastMessageOptions } from "primeng/api";
import { map, Observable, of, Subject, Subscription } from "rxjs";
import { getAuth, resetAuth } from "../gebo-credentials";
import { AuthConfig } from 'angular-oauth2-oidc';
import { Oauth2LoginService } from "./oauth2/oauth2-login.service";


/**
 * Interface representing the result of a login operation
 * Contains optional user information and an array of toast messages to display
 */
export interface LoginOperationResult {
  userInfo?: UserInfo;
  messages: ToastMessageOptions[];
};


/**
 * Service responsible for handling user authentication and related functionality.
 * Manages user sessions, login/logout operations, and password changes.
 */
@Injectable({ providedIn: "root" })
export class LoginService {
  private actualAuthConfig?: AuthConfig;
  private actualAuthProviderId?: string;
  private subscription?: Subscription;
  private tokenRenewPollingPeriodMsec: number = 60000;

  /**
   * Constructor for the LoginService
   * @param activatedRouter Current active route for navigation purposes
   * @param router Router for navigating between routes
   * @param authControllerService Service for authentication operations
   * @param userController Service for user profile operations
   */
  public constructor(
    @Inject(BASE_PATH) private basePath: string,
    private oauth2Service: Oauth2LoginService,
    private activatedRouter: ActivatedRoute,
    private router: Router,
    private authControllerService: AuthControllerService,
    private oauth2ProvidersService: AuthProvidersControllerService,
    private tokenRenewService: TokenRenewControllerService,
    private userController: UserControllerService) {
    this.oauth2Service.authDataSubject.subscribe({
      next: (data) => {
        this.authDataSubject.next(data);
      }
    }
    );
    this.startTokenRenew();
  }
  private startTokenRenew(): void {
    setTimeout(() => {
      this.pollTokenRenew();
    }, this.tokenRenewPollingPeriodMsec);
  }
  private pollTokenRenew(): void {
    const currentAuth = getAuth();
    if (currentAuth) {
      this.tokenRenewService.renew().subscribe({
        next: (renewed) => {
          this.authDataSubject.next(renewed);
          this.startTokenRenew();
        }, error: (e) => {

        }
      });
    }
  }
  public authDataSubject: Subject<SecurityHeaderData | undefined> = new Subject<SecurityHeaderData | undefined>();
  /**
   * Subject that emits the current logged-in user information or undefined when logged out
   */
  logged: Subject<UserInfo | undefined> = new Subject();

  /**
   * Authenticates a user with the provided credentials on local JWT
   * @param credentials Object containing username and password
   * @returns Observable of LoginOperationResult with user info and status messages
   */
  public login(credentials: { username: string, password: string }): Observable<LoginOperationResult> {
    //I reset actual credentials
    try { resetAuth(); } catch (e) { }
    return this.authControllerService.authenticateUser({
      username: credentials.username,
      password: credentials.password
    }).pipe(map(result => {

      const logged: boolean = result?.result?.securityHeaderData?.token ? true : false;
      if (logged && result?.result && result?.result?.userInfo) {
        this.authDataSubject.next(result.result.securityHeaderData);
        this.logged.next(result?.result?.userInfo);
      } else {
        this.authDataSubject.next(undefined);
        this.logged.next(undefined);
      }
      return {
        messages: result.messages as ToastMessageOptions[],
        userInfo: result?.result?.userInfo
      };
    }));
  }
  /*****
   * Check if we are in oauth2 landing page
   */
  public isOauth2LandingPage(): boolean {
    const loc = document.location.href;

    return (loc.indexOf('oauth2-land') >= 0 ? true : false);
  }

  /**
   * Retrieves the current user's profile information
   * @returns Observable of UserInfo containing the user's profile data
   */
  public loadUserProfile(): Observable<UserInfo> {
    return this.userController.getCurrentUser();
  }

  /**
   * Changes the user's password
   * @param c ChangePasswordParam object containing old and new password information
   * @returns Observable of ChangePasswordResponse with the operation result
   */
  public changePassword(c: ChangePasswordParam): Observable<ChangePasswordResponse> {
    return this.userController.changePassword(c);
  }

  /**
   * Logs out the current user by removing credentials from local storage,
   * notifying subscribers that user is logged out, and redirecting to the chat page
   */
  public logout(): void {
    resetAuth();
    this.logged.next(undefined);
    this.authDataSubject.next(undefined);
    this.router.navigate(["/", "ui", "chat"], { relativeTo: this.activatedRouter });
  }
  /**********************
   * Load list of oauth2 providers enabled for the user login (UI data without technical oauth2 critical infos)
   */
  public getOauth2LoginOptions(): Observable<Oauth2ClientAuthorizativeInfo[]> {
    return this.oauth2ProvidersService.listAvailableProvidersConfig();
  }


  /*****************************************************************************
   * Loads the angular-oauth2-oidc AuthConfig configurations using backend specific 
   * provider (get by id) infos
   */
  private loadProviderConfig(authProviderId: string): Observable<Oauth2ClientConfig> {
    return this.oauth2ProvidersService.getProviderClientConfig(authProviderId);
  }
  /******************************************************
   * Start the interactive oauth2 login for actual user by choosed Oauth2ClientAuthorizativeInfo
   */
  public loginWithOauth2(clicked: Oauth2ClientAuthorizativeInfo): Observable<boolean> {
    if (clicked.registrationId) {
      sessionStorage.setItem("authProviderId", clicked.registrationId);
      return this.loadProviderConfig(clicked.registrationId).pipe(map(authConfig => {
        resetAuth();

        this.oauth2Service.oauth2Login(authConfig, () => { this.onSuccessfullLogin(); }, () => { this.onUnsuccessfullLogin(); });
        return true;
      }));

    } else return of(false);
  }

  private onSuccessfullLogin(): void {


    this.router.navigate(['ui', 'reloader'], { relativeTo: this.activatedRouter });
  }
  private onUnsuccessfullLogin(): void {
    resetAuth();
    this.router.navigate(['ui', 'login'], { relativeTo: this.activatedRouter });

  }
  /****************************
   * Called from the reloaded SPA application redirected URL component to get accessToken
   */
  public oauth2RedirectionLanding(): void {
    const authProviderId = sessionStorage.getItem("authProviderId");

    if (authProviderId) {
      this.loadProviderConfig(authProviderId).subscribe((authConfig) => {

        this.oauth2Service.oauth2RedirectLanding(authConfig, () => { this.onSuccessfullLogin(); }, () => { this.onUnsuccessfullLogin(); });
      });
    }

  }
}