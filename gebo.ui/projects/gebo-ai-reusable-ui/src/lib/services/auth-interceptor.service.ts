/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */




import { Inject, Injectable, Optional } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpErrorResponse, HttpSentEvent, HttpHeaderResponse, HttpProgressEvent, HttpResponse, HttpUserEvent } from '@angular/common/http';
import { Observable, of, Subject } from 'rxjs';
import { catchError, tap } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { getAuth, getAuthHeader, resetAuth } from "../infrastructure/gebo-credentials";
import { BASE_PATH } from '@Gebo.ai/gebo-ai-rest-api';
import { ConfirmationService } from 'primeng/api';
import { GeboBackendListService } from './gebo-backends-list.service';

@Injectable({
  providedIn: "root"
})
export class AuthInterceptor implements HttpInterceptor {

  constructor(private router: Router, private actualRoute: ActivatedRoute, private confirmService: ConfirmationService, private geboBackendUrlsService: GeboBackendListService, @Optional() @Inject(BASE_PATH) private basePath: string) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (this.geboBackendUrlsService.isGeboBackend(request)) {
      const currentHeader = getAuthHeader();
      if (currentHeader) {

        request = request.clone({
          setHeaders: currentHeader
        });

      }
    }
    return next.handle(request).pipe(
      catchError((error: any, caught: Observable<HttpSentEvent | HttpHeaderResponse | HttpProgressEvent | HttpResponse<any> | HttpUserEvent<any>>) => {
        if (error instanceof HttpErrorResponse) {
          if (error.status === 401) {
            resetAuth();
            this.router.navigate(["/", "ui", "login"], { relativeTo: this.actualRoute });

          } else if (error.status === 500) {
            const any = error?.message;
            this.confirmService.confirm({
              header: "Backend error happened",
              message: "We apologize for the technical error, please report to assistence@gebo.ai with full log files",

              rejectVisible: false,
              closeOnEscape: true,
              icon: "pi pi-server",
              acceptIcon: "pi pi-thumbs-up",
              acceptLabel: "Ok",
              accept: () => {

              }
            })
          }
        }
        return of(error)
      }));
  };

}