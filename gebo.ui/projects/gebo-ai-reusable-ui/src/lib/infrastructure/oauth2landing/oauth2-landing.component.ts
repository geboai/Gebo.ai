import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { AuthResponse, OAuth2LoginInitiationControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { geboCredenditalString } from "../gebo-credentials";
@Component({
    selector: "gebo-oauth2-landing-component",
    templateUrl: "oauth2-landing.component.html",
    standalone: false
})
export class GeboAIOauth2LandingComponent implements OnInit {
    public loading: boolean = false;
    constructor(private activeRoute: ActivatedRoute,
        private router: Router,
        private oauth2AuthenticationService: OAuth2LoginInitiationControllerService) {

    }
    ngOnInit(): void {
        if (this.activeRoute.snapshot.queryParamMap.has("login_id")) {
            const loginId = this.activeRoute.snapshot.queryParamMap.get("login_id");
            if (loginId) {
                this.loading = true;
                this.oauth2AuthenticationService.endOauthLogin(loginId).subscribe({
                    next: (loginInfo) => {
                        const authResponse: AuthResponse = {
                            accessToken: loginInfo.token,
                            tokenType: "OAUTH2",
                            userInfo: loginInfo.userInfo
                        };
                        sessionStorage.setItem(geboCredenditalString,JSON.stringify(authResponse));
                        this.router.navigate(["..", "reloader"], { relativeTo: this.activeRoute, state: { forceReload: true } });
                    },
                    complete: () => {
                        this.loading = false;
                    }
                });
            }
        }
    }

}