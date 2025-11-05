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
 * This module provides user profile management functionality for the GeboAI application.
 * It sets up routing for the user profile component and imports necessary Angular and PrimeNG modules
 * required for form handling, UI components, and routing.
 */
import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { PasswordModule } from "primeng/password";
import { PanelModule } from "primeng/panel";
import { BlockUIModule } from "primeng/blockui";
import { MessagesModule } from "primeng/messages";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { RouterModule, Routes } from "@angular/router";
import { InputTextModule } from "primeng/inputtext";
import { ButtonModule } from "primeng/button";
import { GeboAIUserProfileComponent } from "./user-profile.component";
import { GeboAIChangePasswordComponent } from "./change-password.component";
import { DialogModule } from "primeng/dialog";
import { GeboAIFieldTransationContainerModule } from "../../controls/field-translation-container/field-container.module";
/**
 * Routes configuration that maps the user profile component to the "ui/currentProfile" path.
 * This allows users to access their profile page through this route.
 */
const routes:Routes=[{
    path:"ui/currentProfile",
    component:GeboAIUserProfileComponent
}];

/**
 * NgModule that bundles all the components and dependencies required for user profile functionality.
 * It imports UI modules from PrimeNG (like Password, Panel, Dialog), form handling modules,
 * and sets up routing. It declares the user profile and password change components.
 */
@NgModule({
    imports:[CommonModule,PasswordModule,InputTextModule,ButtonModule,
        BlockUIModule,
        PanelModule,
        DialogModule,
        MessagesModule,ReactiveFormsModule,FormsModule,RouterModule.forRoot(routes),GeboAIFieldTransationContainerModule],
    declarations:[GeboAIUserProfileComponent,GeboAIChangePasswordComponent]
})
export class GeboAIUserProfileModule {

}