import { NgModule, ModuleWithProviders, SkipSelf, Optional } from '@angular/core';
import { Configuration } from './configuration';
import { HttpClient } from '@angular/common/http';


import { AuthControllerService } from './api/authController.service';
import { AuthProvidersControllerService } from './api/authProvidersController.service';
import { GeboAdvancedSetupStatusControllerService } from './api/geboAdvancedSetupStatusController.service';
import { GeboFastInstallationSetupControllerService } from './api/geboFastInstallationSetupController.service';
import { GeboFastWorkFolderSetupControllerService } from './api/geboFastWorkFolderSetupController.service';
import { GeneratedAdminApiKeyControllerService } from './api/generatedAdminApiKeyController.service';
import { GeneratedUserApiKeyControllerService } from './api/generatedUserApiKeyController.service';
import { OAuth2AdminControllerService } from './api/oAuth2AdminController.service';
import { Oauth2ModuleStatusControllerService } from './api/oauth2ModuleStatusController.service';
import { SecretsControllerService } from './api/secretsController.service';
import { TokenRenewControllerService } from './api/tokenRenewController.service';
import { UserControllerService } from './api/userController.service';
import { UserWorkflowsControllerService } from './api/userWorkflowsController.service';
import { UsersAdminControllerService } from './api/usersAdminController.service';

@NgModule({
  imports:      [],
  declarations: [],
  exports:      [],
  providers: [
    AuthControllerService,
    AuthProvidersControllerService,
    GeboAdvancedSetupStatusControllerService,
    GeboFastInstallationSetupControllerService,
    GeboFastWorkFolderSetupControllerService,
    GeneratedAdminApiKeyControllerService,
    GeneratedUserApiKeyControllerService,
    OAuth2AdminControllerService,
    Oauth2ModuleStatusControllerService,
    SecretsControllerService,
    TokenRenewControllerService,
    UserControllerService,
    UserWorkflowsControllerService,
    UsersAdminControllerService ]
})
export class ApiModule {
    public static forRoot(configurationFactory: () => Configuration): ModuleWithProviders<ApiModule> {
        return {
            ngModule: ApiModule,
            providers: [ { provide: Configuration, useFactory: configurationFactory } ]
        };
    }

    constructor( @Optional() @SkipSelf() parentModule: ApiModule,
                 @Optional() http: HttpClient) {
        if (parentModule) {
            throw new Error('ApiModule is already loaded. Import in your base AppModule only.');
        }
        if (!http) {
            throw new Error('You need to import the HttpClientModule in your AppModule! \n' +
            'See also https://github.com/angular/angular/issues/20575');
        }
    }
}
