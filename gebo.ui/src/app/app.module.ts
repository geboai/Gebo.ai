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
import { AuthInterceptor, GeboAIFieldTranslationContainerModule, GeboAIModulesModule, GeboAINotificationsModule, GeboUIArchitectureModule, ApplicationMenuProviderService, GeboUIEntityFormsLauncherService, GeboUIEntityFormsLauncherByInjectionService, GeboUIActionRoutingService } from "@Gebo.ai/reusable-ui";
import { LoginModule } from "@Gebo.ai/reusable-ui";
import { FastSetupModule } from "@Gebo.ai/reusable-ui";
import { GeboAIUserProfileModule, GeboAIUserIntegrationsModule } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { MonacoEditorModule } from 'ngx-monaco-editor-v2';
import { GeboAiAdminModule, GeboAICommonModulesInjectionsModule, GeboSetupWizardsModule } from "@Gebo.ai/gebo-ai-admin-ui";
import { provideAnimationsAsync } from "@angular/platform-browser/animations/async";
import { providePrimeNG } from "primeng/config";
import Aura from '@primeng/themes/aura';
import { definePreset } from "@primeng/themes";

import { OAuthModule } from 'angular-oauth2-oidc';
import { GeboBackendListService } from "@Gebo.ai/reusable-ui";
import { CookieService } from 'ngx-cookie-service';
import { AppMenuProviderService } from './app-menu-provider.service';
import { TranslateLoader, provideTranslateService } from "@ngx-translate/core";
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
  { path: 'ui/chat', loadChildren: () => import('@Gebo.ai/gebo-ai-chat-ui').then(m => m.GeboAiChatRoutingModule) },
  { path: 'ui/admin', loadChildren: () => import('@Gebo.ai/gebo-ai-admin-ui').then(m => m.GeboAiAdminRoutingModule), pathMatch: 'full' },
  { path: 'ui/admin-setup', loadChildren: () => import('@Gebo.ai/gebo-ai-admin-ui').then(m => m.GeboAiSetupRoutingModule), pathMatch: 'full' },
  { path: 'ui/user-workflows', loadChildren: () => import('@Gebo.ai/reusable-ui').then(m => m.GeboAIUserWorkflowsModule) }
];
const GeboAIPreset = definePreset(Aura, {
  primitive: {
    borderRadius: {
      none: '0',
      xs: '2px',
      sm: '4px',
      md: '6px',
      lg: '8px',
      xl: '8px'
    }
  },
  semantic: {
    transitionDuration: '0.12s',
    focusRing: {
      width: '2px',
      style: 'solid',
      color: '{primary.400}',
      offset: '2px',
      shadow: 'none'
    },
    primary: {
      50: '#f1f8fa',
      100: '#dceef3',
      200: '#b9dce7',
      300: '#86c1d2',
      400: '#4e9eb8',
      500: '#247c9b',
      600: '#146084',
      700: '#124f6d',
      800: '#123f57',
      900: '#123649',
      950: '#0a222f'
    },
    formField: {
      paddingX: '0.75rem',
      paddingY: '0.4375rem',
      borderRadius: '6px',
      focusRing: {
        width: '2px',
        style: 'solid',
        color: '{primary.200}',
        offset: '1px',
        shadow: 'none'
      }
    },
    content: { borderRadius: '8px' },
    overlay: {
      select: { borderRadius: '6px', shadow: '0 8px 24px rgba(17, 24, 39, 0.10)' },
      popover: { borderRadius: '6px', shadow: '0 8px 24px rgba(17, 24, 39, 0.10)' },
      modal: { borderRadius: '8px', shadow: '0 16px 40px rgba(17, 24, 39, 0.14)' },
      navigation: { shadow: '0 8px 24px rgba(17, 24, 39, 0.10)' }
    },
    colorScheme: {
      light: {
        surface: {
          0: '#ffffff',
          50: '#fafafa',
          100: '#f3f4f6',
          200: '#e5e7eb',
          300: '#d1d5db',
          400: '#9ca3af',
          500: '#6b7280',
          600: '#4b5563',
          700: '#374151',
          800: '#1f2937',
          900: '#111827',
          950: '#030712'
        },
        primary: {
          color: '{primary.600}',
          contrastColor: '#ffffff',
          hoverColor: '{primary.700}',
          activeColor: '{primary.800}'
        },
        highlight: {
          background: '{primary.50}',
          focusBackground: '{primary.100}',
          color: '{primary.800}',
          focusColor: '{primary.900}'
        },
        formField: {
          background: '#ffffff',
          disabledBackground: '{surface.100}',
          filledBackground: '{surface.50}',
          filledHoverBackground: '{surface.50}',
          filledFocusBackground: '#ffffff',
          borderColor: '{surface.300}',
          hoverBorderColor: '{surface.400}',
          focusBorderColor: '{primary.600}',
          invalidBorderColor: '{red.600}',
          color: '{surface.800}',
          disabledColor: '{surface.500}',
          placeholderColor: '{surface.500}',
          invalidPlaceholderColor: '{red.700}',
          iconColor: '{surface.500}',
          shadow: 'none'
        },
        text: {
          color: '{surface.800}',
          hoverColor: '{surface.900}',
          mutedColor: '{surface.600}',
          hoverMutedColor: '{surface.700}'
        },
        content: {
          background: '#ffffff',
          hoverBackground: '{surface.100}',
          borderColor: '{surface.200}',
          color: '{text.color}',
          hoverColor: '{text.hover.color}'
        },
        mask: { background: 'rgba(17, 24, 39, 0.32)', color: '#ffffff' }
      }
    }
  },
  extend: {
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
    GeboUIArchitectureModule,
    BrowserAnimationsModule,
    GeboAIUserProfileModule,
    GeboAIUserIntegrationsModule,
    ConfirmDialogModule,
    GeboAiAdminModule.forRoot(),
    MonacoEditorModule.forRoot(),
    GeboAINotificationsModule.forRoot(),
    GeboSetupWizardsModule,
    GeboAICommonModulesInjectionsModule.forRoot(),
    GeboAIModulesModule.forRoot(),
    OAuthModule.forRoot(),
    RouterModule.forRoot(routes),
    GeboAIFieldTranslationContainerModule.forRoot(), PopoverModule],

  providers: [
    provideTranslateService({
      lang: "en",
      fallbackLang: "en",
      loader: { provide: TranslateLoader, useFactory: HttpLoaderFactory, deps: [HttpClient] }
    }),
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
    GeboUIActionRoutingService,
    { provide: BASE_PATH, useFactory: getBaseUrl },
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    { provide: ApplicationMenuProviderService, useClass: AppMenuProviderService },
    { provide: GeboUIEntityFormsLauncherService, useClass: GeboUIEntityFormsLauncherByInjectionService },

    {
      provide: TRANSLATE_HTTP_LOADER_CONFIG,
      // v18 of the loader reads the paths from `resources`; a top-level prefix/suffix is
      // silently dropped, leaving it with nothing to request - no i18n file was ever
      // fetched, so switching language changed nothing on screen.
      useValue: { resources: [{ prefix: '/assets/i18n/', suffix: '.json' }] }
    }
    ,
    ConfirmationService, provideHttpClient(withInterceptorsFromDi())]
})
export class AppModule {

}
