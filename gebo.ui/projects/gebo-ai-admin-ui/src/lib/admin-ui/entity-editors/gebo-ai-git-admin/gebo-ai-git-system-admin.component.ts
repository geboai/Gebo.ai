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
 * This component manages Git system administration in the Gebo.ai application.
 * It extends the BaseEntityEditingComponent to provide CRUD operations for Git content management systems.
 * The component allows users to create, update, and delete Git system configurations
 * and manages related identity secrets.
 */
import { Component, forwardRef, Injector, Input } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { GGitContentManagementSystem, GContentManagementSystemType, GitSystemsControllerService, SecretsControllerService, SecretInfo } from "@Gebo.ai/gebo-ai-rest-api";
import { map, Observable, of } from "rxjs";
import { BaseEntityEditingComponent, GEBO_AI_FIELD_HOST, GeboFormGroupsService, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";

@Component({
    selector: "gebo-ai-git-system-admin-component",
    templateUrl: "gebo-ai-git-system-admin.component.html",
    standalone: false, providers: [{
    provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAiGitSystemAdminComponent),
    multi: true
  }]
})
export class GeboAiGitSystemAdminComponent extends BaseEntityEditingComponent<GGitContentManagementSystem> {
    /**
     * The entity name used for identification in the base component
     */
    protected override entityName: string="GGitContentManagementSystem";

    /**
     * Input property to control whether the Git system reference can be modified
     * When true, the component will prevent modifications to the Git system reference
     */
    @Input() cantModifyGitSystemReference: boolean = false;

    /**
     * Array to store available Git content management system types
     */
    systemTypes: GContentManagementSystemType[] = [];
    
    /**
     * Form group to capture and manage Git system configuration data
     * Includes fields for code, description, baseUri, type, access settings, and identity
     */
    formGroup: FormGroup<any> = new FormGroup(
        {
            code: new FormControl(),
            description: new FormControl(),
            baseUri: new FormControl(),
            contentManagementSystemType: new FormControl(),
            publicAccess: new FormControl(),
            defaultIdentityCode: new FormControl()

        }
    );
    
    /**
     * Flag indicating whether the Git system has public access
     */
    publicAccess: boolean = false;
    
    /**
     * Observable that provides the available Git system types
     */
    contentManagementSystemTypeObservable: Observable<GContentManagementSystemType[]> = this.gitControllerService.getGitSystemTypes();
    
    /**
     * Secret context code used for identity management
     * Follows the format 'git-system:' + codeValue
     */
    secretContextCode:string="'git-system:'";
    
    /**
     * Observable that provides the list of secret identities for the current Git system
     */
    identitiesObservable: Observable<SecretInfo[]> = of([]);

    /**
     * Component constructor
     * Sets up subscriptions to form control value changes to update the secret context
     * and public access flag when their corresponding values change
     * 
     * @param injector Angular injector for dependency injection
     * @param gitControllerService Service for Git system operations
     * @param secretControllerService Service for secret management operations
     * @param geboFGService Service for form group management
     * @param confirmService Service for user confirmations
     * @param geboUIActionRoutingService Service for UI action routing
     * @param outForwardingService Optional service for output forwarding
     */
    constructor(injector:Injector,private gitControllerService: GitSystemsControllerService, private secretControllerService: SecretsControllerService,
        private geboFGService:GeboFormGroupsService,
        confirmService:ConfirmationService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        private outForwardingService?: GeboUIOutputForwardingService) {
        super(injector,geboFGService,confirmService,geboUIActionRoutingService, outForwardingService);
        this.formGroup.controls["code"].valueChanges.subscribe(codeValue=>{
            this.secretContextCode="git-system:" + codeValue;
            this.refreshIdentitiesObservable();
        });
        this.formGroup.controls["publicAccess"].valueChanges.subscribe(publicAccess => {
            this.publicAccess = publicAccess === true;
        });
    }

    /**
     * Refreshes the observable that provides secret identities
     * Uses the current secretContextCode to fetch relevant identities
     */
    public refreshIdentitiesObservable() {
        this.identitiesObservable=this.secretControllerService.getSecretsByContextCode(this.secretContextCode);
    }

    /**
     * Overrides the base method to find a Git system by its code
     * @param code The code of the Git system to find
     * @returns An Observable containing the found Git system or null if not found
     */
    override findByCode(code: string): Observable<GGitContentManagementSystem | null> {
        return this.gitControllerService.getGitSystems().pipe(map(returned => {
            if (!returned) return null;
            const found = returned.find(x => x.code === code);
            return found ? found : null;
        }));
    }

    /**
     * Overrides the base method to save a Git system
     * @param value The Git system to save
     * @returns An Observable containing the saved Git system
     */
    override save(value: GGitContentManagementSystem): Observable<GGitContentManagementSystem> {
        return this.gitControllerService.updateGitSystem(value);
    }

    /**
     * Overrides the base method to delete a Git system
     * @param value The Git system to delete
     * @returns An Observable containing true if deletion is successful
     */
    override delete(value: GGitContentManagementSystem): Observable<boolean> {
        return this.gitControllerService.deleteGitSystem(value).pipe(map(v => true));
    }

    /**
     * Overrides the base method to insert a new Git system
     * @param value The Git system to insert
     * @returns An Observable containing the inserted Git system
     */
    override insert(value: GGitContentManagementSystem): Observable<GGitContentManagementSystem> {
        return this.gitControllerService.insertGitSystem(value);
    }

    /**
     * Overrides the base method to check if a Git system can be deleted
     * Currently always returns true with an empty message
     * @param value The Git system to check
     * @returns An Observable containing the deletion status and message
     */
    override canBeDeleted(value: GGitContentManagementSystem): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: true, message: "" });
    }
}