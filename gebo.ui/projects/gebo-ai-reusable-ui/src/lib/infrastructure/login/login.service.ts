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
import { AuthControllerService, BASE_PATH, ChangePasswordParam, ChangePasswordResponse, Oauth2ClientAuthorizativeInfo, OAuth2LoginInitiationControllerService, OAuth2ProvidersControllerService, UserControllerService, UserInfo } from "@Gebo.ai/gebo-ai-rest-api";
import { ToastMessageOptions } from "primeng/api";
import { map, Observable, of, Subject } from "rxjs";
import { geboCredenditalString } from "../gebo-credentials";

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

  /**
   * Constructor for the LoginService
   * @param activatedRouter Current active route for navigation purposes
   * @param router Router for navigating between routes
   * @param authControllerService Service for authentication operations
   * @param userController Service for user profile operations
   */
  public constructor(
    @Inject(BASE_PATH) private basePath: string,
    private activatedRouter: ActivatedRoute,
    private router: Router,
    private authControllerService: AuthControllerService,
    private oauth2ProvidersService: OAuth2ProvidersControllerService,
    private oauth2AuthenticationService: OAuth2LoginInitiationControllerService,
    private userController: UserControllerService) {

  }

  /**
   * Subject that emits the current logged-in user information or undefined when logged out
   */
  logged: Subject<UserInfo | undefined> = new Subject();

  /**
   * Authenticates a user with the provided credentials
   * @param credentials Object containing username and password
   * @returns Observable of LoginOperationResult with user info and status messages
   */
  public login(credentials: { username: string, password: string }): Observable<LoginOperationResult> {
    //I reset actual credentials
    try { localStorage.removeItem(geboCredenditalString); } catch (e) { }
    return this.authControllerService.authenticateUser({
      username: credentials.username,
      password: credentials.password
    }).pipe(map(result => {
      if (result?.result) {
        localStorage.setItem(geboCredenditalString, JSON.stringify(result?.result));
      }
      const logged: boolean = result?.result?.accessToken ? true : false;
      if (logged && result?.result && result?.result?.userInfo) {
        this.logged.next(result?.result?.userInfo);
      } else {
        this.logged.next(undefined);
      }
      return {
        messages: result.messages as ToastMessageOptions[],
        userInfo: result?.result?.userInfo
      };
    }));
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
    localStorage.removeItem(geboCredenditalString);
    this.logged.next(undefined);
    this.router.navigate(["/", "ui", "chat"], { relativeTo: this.activatedRouter });
  }

  public getOauth2LoginOptions(): Observable<Oauth2ClientAuthorizativeInfo[]> {
    return this.oauth2ProvidersService.listAvailableProviders();
  }
  public startOauth2Login(clicked: Oauth2ClientAuthorizativeInfo): Observable<{ absoluteLoginUrl: string, ok: boolean }> {
    if (clicked.registrationId) {

      return this.oauth2AuthenticationService.startOauthLogin(clicked.registrationId).pipe(map(data => {
        const outValue: { absoluteLoginUrl: string, ok: boolean } = {
          ok: true,
          absoluteLoginUrl: data.loginAbsoluteUrl
        };
        return outValue;
      }));
    } else {
      return of({ absoluteLoginUrl: "", ok: false });
    }
  }
}