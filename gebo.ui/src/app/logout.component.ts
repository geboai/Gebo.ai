/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

import { Component, OnInit } from "@angular/core";
import { LoginService } from "../../projects/gebo-ai-reusable-ui/src/lib/infrastructure/login/login.service";
@Component({
    selector: "gebo-ai-logout-component",
    template: "<h2>logout in progress</h2>",
    standalone: false
})
export class LogoutComponent implements OnInit{
    constructor(private loginService:LoginService) {

    }
    ngOnInit(): void {
        this.loginService.logout();
    }
}