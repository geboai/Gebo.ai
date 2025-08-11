/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

import { Component, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { GeboSetupWizardService } from "@Gebo.ai/gebo-ai-admin-ui";
import { LoginService } from "../login/login.service";

@Component({
    selector: "gebo-ai-reload-forward-component",
    template: "",
    standalone: false
})
export class GeboAIReloadForwardComponent implements OnInit, OnChanges {
    state?:any;
    constructor(private actualRoute: ActivatedRoute, private router: Router, private loginService: LoginService) {
        this.state=router.getCurrentNavigation()?.extras?.state;
    }
    ngOnInit(): void {
        this.loginService.loadUserProfile().subscribe(x => {
            const isAdmin: boolean = x.roles && x.roles.find(c => c === 'ADMIN') ? true : false;
            if (isAdmin === true) { 
                
                if (this.state && this.state["setupDone"] === true) {
                    document.location = "/ui/admin-setup";
                } else
                    document.location = "/ui/admin";
            } else
                document.location = "/ui/chat";
        });

    }
    ngOnChanges(changes: SimpleChanges): void {

    }

}