import { NgModule, ModuleWithProviders, SkipSelf, Optional } from '@angular/core';
import { Configuration } from './configuration';
import { HttpClient } from '@angular/common/http';


import { JobStatusControllerService } from './api/jobStatusController.service';
import { LlmsUsageAdminLevelControllerService } from './api/llmsUsageAdminLevelController.service';
import { LlmsUsageUserLevelControllerService } from './api/llmsUsageUserLevelController.service';
import { WorkflowStatsAdminLevelControllerService } from './api/workflowStatsAdminLevelController.service';

@NgModule({
  imports:      [],
  declarations: [],
  exports:      [],
  providers: [
    JobStatusControllerService,
    LlmsUsageAdminLevelControllerService,
    LlmsUsageUserLevelControllerService,
    WorkflowStatsAdminLevelControllerService ]
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
