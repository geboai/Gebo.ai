/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */




import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { AppComponent } from "./app.component";
import { BrowserModule } from "@angular/platform-browser";
import { HTTP_INTERCEPTORS, HttpClient, provideHttpClient, withInterceptorsFromDi } from "@angular/common/http";
import { BASE_PATH, ApiModule as GeboAiChatApiModule } from '@Gebo.ai/gebo-ai-rest-api';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ConfirmDialogModule } from "primeng/confirmdialog";
import { MegaMenuModule } from 'primeng/megamenu';
import { AuthInterceptor, GeboAIFieldTranslationContainerModule } from "@Gebo.ai/reusable-ui";
import { LoginModule } from "@Gebo.ai/reusable-ui";
import { FastSetupModule } from "@Gebo.ai/reusable-ui";
import { GeboAIUserProfileModule } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { MonacoEditorModule } from 'ngx-monaco-editor-v2';
import { GeboSetupWizardsModule } from "@Gebo.ai/gebo-ai-admin-ui";
import { provideAnimationsAsync } from "@angular/platform-browser/animations/async";
import { providePrimeNG } from "primeng/config";
import Aura from '@primeng/themes/aura';
import { definePreset } from "@primeng/themes";

import { OAuthModule } from 'angular-oauth2-oidc';
import { GeboBackendListService } from "@Gebo.ai/reusable-ui";
import { CookieService } from 'ngx-cookie-service';
import { TranslateLoader, TranslateModule } from "@ngx-translate/core";
import { TRANSLATE_HTTP_LOADER_CONFIG, TranslateHttpLoader } from '@ngx-translate/http-loader';
import { PopoverModule } from 'primeng/popover';
export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader();
}
export function getBaseUrl() {
  let host = document.location.hostname;
  let port = document.location.port;
  let protocol = document.location.protocol;
  if (port === "4200") {
    port = "12999";
  }
  let localBasePath = protocol + "//" + host + ":" + port;
  console.log("Setting basePath: " + localBasePath);
  return localBasePath;
}
export const routes: Routes = [
  { path: 'ui', redirectTo: "ui/chat", pathMatch: "full" },
  { path: '', redirectTo: "ui/chat", pathMatch: "full" },
  { path: 'ui/chat', loadChildren: () => import('@Gebo.ai/gebo-ai-chat-ui').then(m => m.GeboAiChatRoutingModule), pathMatch: 'full' },
  { path: 'ui/admin', loadChildren: () => import('@Gebo.ai/gebo-ai-admin-ui').then(m => m.GeboAiAdminRoutingModule), pathMatch: 'full' },
  { path: 'ui/admin-setup', loadChildren: () => import('@Gebo.ai/gebo-ai-admin-ui').then(m => m.GeboAiSetupRoutingModule), pathMatch: 'full' },


];
const GeboAIPreset = definePreset(Aura, {
  semantic: {
    primary: {

      50: '{blue.50}',
      100: '{blue.100}',
      200: '{blue.200}',
      300: '{blue.300}',
      400: '{blue.400}',
      500: '{blue.500}',
      600: '{blue.600}',
      700: '{blue.700}',
      800: '{blue.800}',
      900: '{blue.900}',
      950: '{blue.950}'
    },
    success: {

      50: '{teal.50}',
      100: '{teal.100}',
      200: '{teal.200}',
      300: '{teal.300}',
      400: '{teal.400}',
      500: '{teal.500}',
      600: '{teal.600}',
      700: '{teal.700}',
      800: '{teal.800}',
      900: '{teal.900}',
      950: '{teal.950}'
    }
  }
});
@NgModule({
  declarations: [AppComponent],
  exports: [AppComponent],
  bootstrap: [AppComponent],
  imports: [CommonModule,
    BrowserModule,
    GeboAiChatApiModule,
    MegaMenuModule,
    LoginModule,
    FastSetupModule,
    BrowserAnimationsModule,
    GeboAIUserProfileModule,
    ConfirmDialogModule,
    MonacoEditorModule.forRoot(),
    TranslateModule.forRoot({
      lang: "en",
      fallbackLang: "en",
      loader: { provide: TranslateLoader, useFactory: HttpLoaderFactory, deps: [HttpClient] }

    }),    
    GeboSetupWizardsModule,
    OAuthModule.forRoot(),
    RouterModule.forRoot(routes), 
    GeboAIFieldTranslationContainerModule.forRoot(),PopoverModule],

  providers: [
    GeboBackendListService,
    CookieService,
    provideAnimationsAsync(),
    providePrimeNG({
      theme: {
        preset: GeboAIPreset,
        options: {
          darkModeSelector: false || 'none',

        }
      }
    }),
    { provide: BASE_PATH, useFactory: getBaseUrl },
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },

    {
      provide: TRANSLATE_HTTP_LOADER_CONFIG,
      useValue: { prefix: '/assets/i18n/', suffix: '.json' }
    }
    ,
    ConfirmationService, provideHttpClient(withInterceptorsFromDi())]
})
export class AppModule {

}
