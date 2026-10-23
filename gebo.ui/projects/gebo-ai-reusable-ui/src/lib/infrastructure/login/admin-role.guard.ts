/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import { inject } from "@angular/core";
import { CanActivateFn, Router, UrlTree } from "@angular/router";
import { Observable, of } from "rxjs";
import { catchError, map } from "rxjs/operators";
import { getAuth } from "../gebo-credentials";
import { LoginService } from "./login.service";

/** The role the administration and setup pages are for. */
export const ADMIN_ROLE = "ADMIN";

/**
 * Lets only administrators into a route: an anonymous visitor goes to the login
 * page, a logged-in user without the ADMIN role to the chat. The profile is
 * asked to the backend on each activation, so a role taken away is seen at the
 * next navigation. The backend stays the enforcement: every administrative call
 * refuses a non-administrator whatever the browser shows.
 */
export const adminRoleGuard: CanActivateFn = (): Observable<boolean | UrlTree> => {
    const router = inject(Router);
    const loginService = inject(LoginService);
    if (!getAuth()) {
        return of(router.createUrlTree(["/", "ui", "login"]));
    }
    return loginService.loadUserProfile().pipe(
        map(profile => {
            const isAdmin = !!profile?.roles?.find(role => role === ADMIN_ROLE);
            return isAdmin ? true : router.createUrlTree(["/", "ui", "chat"]);
        }),
        // a failed profile call (an expired token, a backend down): the login page
        catchError(() => of(router.createUrlTree(["/", "ui", "login"])))
    );
};
