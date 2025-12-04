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
 * This module provides the login functionality for the application. It encapsulates all components,
 * services, and dependencies required for the login feature.
 * 
 * The module imports various UI components from PrimeNG like Password, Panel, BlockUI, Messages,
 * InputText, and Button for building the login interface. It also imports Angular's form modules
 * for handling form validation and submission.
 * 
 * It sets up a route for '/ui/login' that renders the LoginComponent and provides the LoginService
 * to handle authentication logic.
 */
import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { LoginComponent } from "./login.component";
import { PasswordModule } from "primeng/password";
import { PanelModule } from "primeng/panel";
import { BlockUIModule } from "primeng/blockui";
import { MessagesModule } from "primeng/messages";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { RouterModule, Routes } from "@angular/router";
import { InputTextModule } from "primeng/inputtext";
import { ButtonModule } from "primeng/button";
import { LoginService } from "./login.service";
import { FieldsetModule } from "primeng/fieldset";
import { GenericOauth2ClientLoginService, GenericOauth2ServerSideLoginService } from "./oauth2/oauth2-login.service";
import { GeboAIOauth2LandingComponent } from "./oauth2/oauth2landing/oauth2-landing.component";
import { LoggedComponent } from "./logged.component";
import { LogoutComponent } from "./logout.component";
import { GeboAIReloadForwardComponent } from "./reload-forward.component";
import { GeboAIFieldTranslationContainerModule } from "../../controls/field-translation-container/field-container.module";
/**
 * Defines the route configuration for the login module.
 * Sets up the '/ui/login' path to be handled by the LoginComponent.
 */
const routes: Routes = [
    { path: 'ui/login', component: LoginComponent },
    { path: 'ui/oauth2-land', component: GeboAIOauth2LandingComponent },
    { path: 'ui/logged', component: LoggedComponent },
    { path: 'ui/logout', component: LogoutComponent }, 
    { path: 'ui/reloader', component: GeboAIReloadForwardComponent }];

@NgModule({
    imports: [CommonModule, PasswordModule, InputTextModule, ButtonModule,
        BlockUIModule,
        PanelModule,
        MessagesModule, ReactiveFormsModule, FormsModule, RouterModule.forRoot(routes), FieldsetModule,GeboAIFieldTranslationContainerModule],
    declarations: [LoginComponent, GeboAIOauth2LandingComponent, LoggedComponent, LogoutComponent,GeboAIReloadForwardComponent],
    exports: [LoginComponent],
    providers: [LoginService, GenericOauth2ClientLoginService, GenericOauth2ServerSideLoginService]
})
export class LoginModule { }