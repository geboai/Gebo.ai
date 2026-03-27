import { Component, forwardRef, Injector } from "@angular/core";
import { AbstractControl, FormArray, FormControl, FormGroup } from "@angular/forms";
import { ChatModelsControllerService, ConfigurationEntry, DeepSearchConfig, DeepSearchDataSourceAccess, GBaseChatModelConfig, GBaseObject, GeboDeepSearchAdminControllerService, GObjectRefGBaseModelConfig, GroupInfo, UserInfo, UserInfos, UsersAdminControllerService, UsersGroup } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { forkJoin, map, Observable, of } from "rxjs";

export interface DataSourceAccessRow {
    dataSource: GBaseObject;
    formGroup: FormGroup;
    availableUsers: UserInfos[];
}
@Component({
    selector: "gebo-ai-deep-search-admin-component",
    templateUrl: "gebo-deep-search-admin.component.html",
    standalone: false,
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIDeepSearchConfigAdminModule", multi: false },
        {
            provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIDeepSearchConfigAdminComponent), multi: true
        }]
})
export class GeboAIDeepSearchConfigAdminComponent extends BaseEntityEditingComponent<DeepSearchConfig> {
    protected override entityName: string = "DeepSearchConfig";
    protected minThreashold: number = 0.5;
    protected maxThreashold: number = 1.0;
    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        searchType: new FormControl(),
        userModified: new FormControl(),
        userCreated: new FormControl(),
        dateModified: new FormControl(),
        dateCreated: new FormControl(),
        consolidationPrompt: new FormControl(),
        analisysPrompt: new FormControl(),
        ragQueryOptions: new FormGroup({
            topK: new FormControl(),
            similarityThreashold: new FormControl(),
            maxTokens: new FormControl(),
            completeness: new FormControl()
        }),
        firstHopSimilarityThreashold: new FormControl(),
        secondHopSimilarityThreashold: new FormControl(),
        graphRagTopN: new FormControl(),
        tokensLimit: new FormControl(),
        defaultConfig: new FormControl(),
        accessibleGroups: new FormControl(),
        accessibleUsers: new FormControl(),
        accessibleToAll: new FormControl(),
        dataSourcesAccesses: new FormArray([]),
        perDataSourceConfigured: new FormControl()

    });
    protected defaultConfiguration?: DeepSearchConfig;
    protected searchType?: DeepSearchConfig.SearchTypeEnum = "MULTI_HOP";
    protected searchTypeOptions: { code: DeepSearchConfig.SearchTypeEnum, description: string }[] = [{ code: "MULTI_HOP", description: "Multi hop search" }, { code: "SINGLE_HOP", description: "Single semantic search" }];
    protected users: UserInfos[] = [];
    protected groups: UsersGroup[] = [];
    protected dataSources: GBaseObject[] = [];
    protected dsAccessRows: DataSourceAccessRow[] = [];
    constructor(injector: Injector,
        geboFormGroupsService: GeboFormGroupsService,
        confirmationService: ConfirmationService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        outputForwardingService: GeboUIOutputForwardingService,
        private deepSearchConfigService: GeboDeepSearchAdminControllerService,
        private usersService: UsersAdminControllerService) {
        super(injector, geboFormGroupsService, confirmationService, geboUIActionRoutingService, outputForwardingService);
        this.formGroup.controls["searchType"].valueChanges.subscribe({
            next: (value) => {
                this.searchType = value;
            }
        });
        this.formGroup.controls["perDataSourceConfigured"].valueChanges.subscribe({
            next: (value) => {
                this.toggleAccessModes(value === true);
            }
        });

        this.formGroup.controls["accessibleToAll"].valueChanges.subscribe({
            next: (value) => {
                if (!this.formGroup.controls["perDataSourceConfigured"].value) {
                    if (value) {
                        this.formGroup.controls['accessibleGroups'].disable();
                        this.formGroup.controls['accessibleUsers'].disable();
                    } else {
                        this.formGroup.controls['accessibleGroups'].enable();
                        this.formGroup.controls['accessibleUsers'].enable();
                    }
                }
            }
        });
    }
    override ngOnInit(): void {
        super.ngOnInit();
        this.loadingRelatedBackend = true;

        const ug: [Observable<UserInfos[]>, Observable<UsersGroup[]>, Observable<GBaseObject[]>] = [this.usersService.getAllUsers(), this.usersService.getAllGroups(), this.deepSearchConfigService.getConfigurableDataSources()];
        forkJoin(ug).subscribe({
            next: (data) => {
                this.users = data[0];
                this.groups = data[1];
                this.dataSources = data[2];
                this.buildDsAccessRows(this.formGroup.value.dataSourcesAccesses);
                this.toggleAccessModes(this.formGroup.controls["perDataSourceConfigured"].value === true);
            },
            complete: () => {
                this.loadingRelatedBackend = false;
            }
        });
    }
    protected getform(frm: string): FormGroup {
        return this.formGroup.controls[frm] as FormGroup;
    }
    protected override onLoadedPersistentData(actualValue: DeepSearchConfig): void {
        this.buildDsAccessRows(actualValue.dataSourcesAccesses);
        this.toggleAccessModes(actualValue.perDataSourceConfigured === true);
    }
    protected override onNewData(actualValue: DeepSearchConfig): void {
        this.buildDsAccessRows(actualValue.dataSourcesAccesses);
        this.toggleAccessModes(actualValue.perDataSourceConfigured === true);
    }
    override findByCode(code: string): Observable<DeepSearchConfig | null> {
        return this.deepSearchConfigService.getDeepSearchDefaultConfig();
    }

    private toggleAccessModes(perDataSource: boolean) {
        const accessesArray = this.formGroup.controls['dataSourcesAccesses'] as FormArray;
        const accessibleToAllCtrl = this.formGroup.controls['accessibleToAll'];
        const accessibleGroupsCtrl = this.formGroup.controls['accessibleGroups'];
        const accessibleUsersCtrl = this.formGroup.controls['accessibleUsers'];

        if (perDataSource) {
            accessibleToAllCtrl.disable();
            accessibleGroupsCtrl.disable();
            accessibleUsersCtrl.disable();

            accessesArray.enable();
            // Re-apply inner constraints sequentially
            this.dsAccessRows.forEach(row => {
                const rowGroup = row.formGroup;
                if (rowGroup.controls['accessibleToAll'].value) {
                    rowGroup.controls['accessibleGroups'].disable();
                    rowGroup.controls['accessibleUsers'].disable();
                }
            });
        } else {
            accessibleToAllCtrl.enable();
            if (accessibleToAllCtrl.value) {
                accessibleGroupsCtrl.disable();
                accessibleUsersCtrl.disable();
            } else {
                accessibleGroupsCtrl.enable();
                accessibleUsersCtrl.enable();
            }
            accessesArray.disable();
        }
    }

    protected buildDsAccessRows(incomingAccesses?: DeepSearchDataSourceAccess[]) {
        if (!this.dataSources || this.dataSources.length === 0) return;

        let currentAccesses: DeepSearchDataSourceAccess[] = incomingAccesses || this.formGroup.value.dataSourcesAccesses || [];

        let accessesArray = this.formGroup.controls['dataSourcesAccesses'] as FormArray;
        if (!accessesArray) {
            accessesArray = new FormArray<FormGroup>([]);
            this.formGroup.setControl('dataSourcesAccesses', accessesArray);
        }
        accessesArray.clear();

        this.dsAccessRows = this.dataSources.map(ds => {
            let access = currentAccesses.find(a => a.dataSourceId === ds.code);
            if (!access) {
                access = {
                    dataSourceId: ds.code,
                    accessibleToAll: true,
                    accessibleGroups: [],
                    accessibleUsers: []
                };
            }

            const group = new FormGroup({
                dataSourceId: new FormControl(access.dataSourceId),
                accessibleToAll: new FormControl(access.accessibleToAll),
                accessibleGroups: new FormControl(access.accessibleGroups),
                accessibleUsers: new FormControl(access.accessibleUsers)
            });

            accessesArray.push(group);

            const row: DataSourceAccessRow = {
                dataSource: ds,
                formGroup: group,
                availableUsers: this.users || []
            };

            group.controls['accessibleGroups'].valueChanges.subscribe(() => {
                this.updateAvailableUsers(row);
            });

            group.controls['accessibleToAll'].valueChanges.subscribe((accessibleToAll) => {
                if (accessibleToAll) {
                    group.controls['accessibleGroups'].disable();
                    group.controls['accessibleUsers'].disable();
                } else {
                    group.controls['accessibleGroups'].enable();
                    group.controls['accessibleUsers'].enable();
                }
            });

            // Set initial disabled state
            if (access.accessibleToAll) {
                group.controls['accessibleGroups'].disable();
                group.controls['accessibleUsers'].disable();
            }

            this.updateAvailableUsers(row);
            return row;
        });
    }

    private updateAvailableUsers(row: DataSourceAccessRow) {
        const accessibleGroups = row.formGroup.controls['accessibleGroups'].value;
        const accessibleUsersControl = row.formGroup.controls['accessibleUsers'];

        if (!accessibleGroups || accessibleGroups.length === 0) {
            row.availableUsers = this.users || [];
            return;
        }

        const excludedUserIds = new Set<string>();
        accessibleGroups.forEach((groupCode: string) => {
            const group = this.groups.find(g => g.code === groupCode);
            if (group && group.userIds) {
                group.userIds.forEach(uid => excludedUserIds.add(uid));
            }
        });

        row.availableUsers = (this.users || []).filter(u => u.username && !excludedUserIds.has(u.username));

        const currentAccessibleUsers = accessibleUsersControl.value;
        if (currentAccessibleUsers && currentAccessibleUsers.length > 0) {
            const allowedJids = row.availableUsers.map(u => u.username);
            const filteredUsers = currentAccessibleUsers.filter((u: string) => u && allowedJids.includes(u));
            if (filteredUsers.length !== currentAccessibleUsers.length) {
                accessibleUsersControl.setValue(filteredUsers, { emitEvent: false });
            }
        }
    }

    private prepareForSave(value: DeepSearchConfig): DeepSearchConfig {
        if (!value.perDataSourceConfigured) {
            value.dataSourcesAccesses = undefined;
        }
        return value;
    }

    override save(value: DeepSearchConfig): Observable<DeepSearchConfig> {
        return this.deepSearchConfigService.updateDeepSearchConfig(this.prepareForSave(value));
    }
    override insert(value: DeepSearchConfig): Observable<DeepSearchConfig> {
        return this.deepSearchConfigService.insertDeepSearchConfig(this.prepareForSave(value));
    }
    override delete(value: DeepSearchConfig): Observable<boolean> {
        return this.deepSearchConfigService.deleteDeepSearchConfig(value).pipe(map(val => true));
    }
    override canBeDeleted(value: DeepSearchConfig): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }

}