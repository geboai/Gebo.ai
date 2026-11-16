import { Component, OnInit, ChangeDetectionStrategy } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { LoginService } from "../../login.service";
@Component({
    selector: "gebo-oauth2-landing-component",
    templateUrl: "oauth2-landing.component.html",
    changeDetection: ChangeDetectionStrategy.Eager,
    standalone: false
})
export class GeboAIOauth2LandingComponent implements OnInit {
    public loading: boolean = false;
    constructor(private activeRoute: ActivatedRoute,
        private router: Router,
        private loginService:LoginService) {

    }
    ngOnInit(): void {
        this.loginService.oauth2RedirectionLanding();
    }

}