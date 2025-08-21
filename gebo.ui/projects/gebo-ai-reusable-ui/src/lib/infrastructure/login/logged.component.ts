/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

import { Component, OnInit } from "@angular/core";
import { LoginService } from "./login.service";
import { ActivatedRoute } from "@angular/router";

@Component({
    selector: "gebo-ai-logged",
    template: "Ok logged in gebo!! {{profile}}",
    standalone: false
})
export class LoggedComponent implements OnInit{
    profile:any={};
    public constructor(public loginService: LoginService,private activatedRouter:ActivatedRoute) {

    }
    ngOnInit(): void {
        
    }
}