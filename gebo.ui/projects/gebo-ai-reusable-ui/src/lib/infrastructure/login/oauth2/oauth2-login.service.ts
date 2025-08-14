import { SecurityHeaderData, Oauth2ClientConfig, BASE_PATH } from "@Gebo.ai/gebo-ai-rest-api";
import { Subject, Subscription, window } from "rxjs";
import { getAuth, resetAuth } from "../../gebo-credentials";
import { OAuthService, AuthConfig, OAuthEvent } from 'angular-oauth2-oidc';
import { Inject, Injectable, OnInit } from "@angular/core";
import { CookieService } from "ngx-cookie-service";


export interface IOauth2LoginService {
  authDataSubject: Subject<SecurityHeaderData | undefined>;
  oauth2Login(config: Oauth2ClientConfig, successfullCallBack: () => void, failureCallback: () => void): void;
  oauth2RedirectLanding(config: Oauth2ClientConfig, successfullCallBack: () => void, failureCallback: () => void): void;
}
@Injectable({ providedIn: "root" })
export class GenericOauth2ServerSideLoginService implements IOauth2LoginService {
  constructor(@Inject(BASE_PATH) private basePath: string, private cookieService: CookieService) {

  }
  authDataSubject: Subject<SecurityHeaderData | undefined> = new Subject();
  private createOauth2LoginUrl(config: Oauth2ClientConfig): string {
    const url: URL = new URL(this.basePath);
    const baseBackend = url.origin;
    return baseBackend + "/oauth2/authorization/" + config.registrationId;
  }
  oauth2Login(config: Oauth2ClientConfig, successfullCallBack: () => void, failureCallback: () => void): void {
    sessionStorage.setItem("OAUTH2-LOGIN-TYPE", "BACKEND");
    const url = this.createOauth2LoginUrl(config);
    document.location = url;
  }
  oauth2RedirectLanding(config: Oauth2ClientConfig, successfullCallBack: () => void, failureCallback: () => void): void {
    
    const token = this.cookieService.get("TOKEN");
    if (token) {
      const auth: SecurityHeaderData = {
        authProviderId: config.registrationId,
        authTenantId: "default-tenant",
        authType: "OAUTH2",
        token: token
      };
      this.authDataSubject.next(auth);
      this.cookieService.delete("TOKEN");
      successfullCallBack();
    } else failureCallback();
  }

}
@Injectable({ providedIn: "root" })
export class GenericOauth2ClientLoginService implements IOauth2LoginService {
  private authConfig?: AuthConfig;
  constructor(private oauth2Service: OAuthService) {
    this.oauth2Service.events.subscribe({
      next: (event: OAuthEvent) => {
        console.debug(event);
        if (event.type === "token_received" || event.type === "silently_refreshed") {
          let actualAuth = getAuth();
          const accessToken = this.oauth2Service.getAccessToken();
          const authProviderId = sessionStorage.getItem("authProviderId");
          const tenantId = sessionStorage.getItem("tenantId");
          if (!actualAuth) {
            actualAuth = {
              token: accessToken,
              authType: SecurityHeaderData.AuthTypeEnum.OAUTH2,
              empty: false
            };
            if (authProviderId) {
              actualAuth.authProviderId = authProviderId;
            }
            if (tenantId) {
              actualAuth.authTenantId = tenantId;
            } else {
              actualAuth.authTenantId = "default-tenant";
            }

          } else {
            actualAuth.token = accessToken;
          }
          this.authDataSubject.next(actualAuth);
        } else if (event.type === "logout") {
          this.authDataSubject.next(undefined);
        }
      }
    });
  }
  authDataSubject: Subject<SecurityHeaderData | undefined> = new Subject<SecurityHeaderData | undefined>();
  private translateConfig(value: Oauth2ClientConfig): AuthConfig {
    const authConfig: AuthConfig = {
      issuer: value.issuer, // OAuth2 provider URL
      redirectUri: document.location.origin + '/ui/oauth2-land',
      clientId: value.clientId,
      responseType: 'code',
      scope: 'openid profile email',
      disablePKCE: false,
      showDebugInformation: true,
      useSilentRefresh: true,
      strictDiscoveryDocumentValidation: false,
      useHttpBasicAuth: false // impedisce l’invio del client_secret

    };
    return authConfig;
  }
  oauth2Login(value: Oauth2ClientConfig, successfullCallBack: () => void, failureCallback: () => void): void {
    this.authConfig = this.translateConfig(value);
    this.oauth2Service.setStorage(localStorage);
    this.oauth2Service.configure(this.authConfig);
    this.oauth2Service.setupAutomaticSilentRefresh();
    sessionStorage.setItem("loginStatus", "executing");
    this.oauth2Service.loadDiscoveryDocument().then(() => {

      this.oauth2Service.initCodeFlow();

    });
  }
  oauth2RedirectLanding(value: Oauth2ClientConfig, successfullCallBack: () => void, failureCallback: () => void): void {
    this.authConfig = this.translateConfig(value);
    this.oauth2Service.configure(this.authConfig);
    this.oauth2Service.setStorage(localStorage);

    this.oauth2Service.loadDiscoveryDocument().then(() => {
      this.oauth2Service.tryLoginCodeFlow().then(() => {
        if (this.oauth2Service.hasValidAccessToken()) {
          this.oauth2Service.setupAutomaticSilentRefresh();
          successfullCallBack();
        } else {
          console.error("Access token non ottenuto. Logout forzato.");
          resetAuth();
          failureCallback();

        }
      });
    });
  }
}
@Injectable({ providedIn: "root" })
export class Oauth2LoginService implements IOauth2LoginService {
  constructor(
    private genericOauthClient2LoginService: GenericOauth2ClientLoginService,
    private genericServerSideOauth2LoginService: GenericOauth2ServerSideLoginService) {
  }

  private subscription?: Subscription;
  authDataSubject: Subject<SecurityHeaderData | undefined> = new Subject();

  oauth2Login(config: Oauth2ClientConfig, successfullCallBack: () => void, failureCallback: () => void): void {
    const service = this.switchOauth2LoginService(config.provider);
    service.oauth2Login(config, successfullCallBack, failureCallback);
  }
  oauth2RedirectLanding(config: Oauth2ClientConfig, successfullCallBack: () => void, failureCallback: () => void): void {
    const service = this.switchOauth2LoginService(config.provider);
    service.oauth2RedirectLanding(config, successfullCallBack, failureCallback);
  }
  private switchOauth2LoginService(provider: Oauth2ClientConfig.ProviderEnum): IOauth2LoginService {
    if (this.subscription) {
      this.subscription.unsubscribe();
      this.subscription = undefined;
    }
    let service: IOauth2LoginService;
    switch (provider) {
      case "google": service = this.genericServerSideOauth2LoginService; break;
      default: service = this.genericOauthClient2LoginService; break;
    }
    this.subscription = service.authDataSubject.subscribe({
      next: (value) => {
        this.authDataSubject.next(value);
      }
    });
    return service;
  }
}

declare const google: any;
@Injectable({ providedIn: "root" })
export class GoogleOauth2Service implements IOauth2LoginService {
  authDataSubject: Subject<SecurityHeaderData | undefined> = new Subject();
  actualAuthConfig?: Oauth2ClientConfig;
  tokenClient: any;
  codeClient: any;
  oauth2Login(config: Oauth2ClientConfig, successfullCallBack: () => void, failureCallback: () => void): void {
    this.actualAuthConfig = config;
    this.runGoogleOauth2Login(config, successfullCallBack, failureCallback);
  }
  oauth2RedirectLanding(config: Oauth2ClientConfig, successfullCallBack: () => void, failureCallback: () => void): void {
    this.actualAuthConfig = config;
  }
  private isGoogleLibraryDefined(): boolean {
    try {
      return google && google.accounts ? true : false;
    } catch (e) {
      return false;
    }
  }
  private runGoogleOauth2Login(authConfig: Oauth2ClientConfig, successfullCallBack: () => void, failureCallback: () => void) {

    if (this.isGoogleLibraryDefined()) {
      this.initGoogleOauthLogin(authConfig, successfullCallBack, failureCallback);
    } else {
      this.actualAuthConfig = authConfig;
      const script = document.createElement('script');
      script.src = 'https://accounts.google.com/gsi/client';
      script.async = true;
      script.defer = true;
      script.onload = () => {
        this.initGoogleOauthLogin(authConfig, successfullCallBack, failureCallback);
      }
      document.head.appendChild(script);
    }
  }
  private initGoogleOauthLogin(authConfig: Oauth2ClientConfig, successfullCallBack: () => void, failureCallback: () => void): void {
    if (!google?.accounts?.oauth2) {
      console.error('Google Identity Services non caricato');
      return;
    }
    this.codeClient = google.accounts.oauth2.initCodeClient({
      client_id: authConfig.clientId,
      scope: 'openid email profile',
      ux_mode: 'popup', // oppure 'redirect'
      redirect_uri: document.location.origin + '/ui/oauth2-land',
      callback: (resp: any) => {
        if (resp.code) {
          console.log('Authorization Code ricevuto:', resp.code);
          // invia il code al backend per scambio token
          const data: SecurityHeaderData = {
            authProviderId: authConfig.registrationId,
            authTenantId: "default-tenant",
            authType: "OAUTH2",
            empty: false,
            token: resp.code
          };
          this.authDataSubject.next(data);
          successfullCallBack();
        } else {
          console.error('Errore nella risposta GIS', resp);
        }
      }
    });
    this.codeClient.requestCode();


    /*this.tokenClient =  google.accounts.oauth2.initTokenClient({
      client_id:  authConfig.clientId,
      scope: 'openid profile email',
      callback: (resp: any) => {
        if (resp.access_token) {
          const data:SecurityHeaderData= {
            authProviderId: authConfig.registrationId,
            authTenantId:"default-tenant",
            authType:"OAUTH2",
            empty:false,
            token:resp.access_token
          };
          this.authDataSubject.next(data);
          successfullCallBack();
        }
      }
    });
    this.tokenClient.requestAccessToken({ prompt:  ''  });*/
    /*google.accounts.id.initialize({
      client_id: authConfig.clientId,
      callback: (res: any) => {
  
      }
    });
    google.accounts.id.prompt();*/
  }

}