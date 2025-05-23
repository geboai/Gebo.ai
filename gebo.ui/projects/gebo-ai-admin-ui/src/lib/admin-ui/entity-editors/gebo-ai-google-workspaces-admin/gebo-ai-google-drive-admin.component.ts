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
 * This component provides an admin interface for managing Google Drive systems in the application.
 * It extends BaseEntityEditingComponent with specific functionality for Google Drive system CRUD operations.
 * The component handles form creation, validation, and integration with the Google Drive API services.
 */
import { Component, Injector } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { GGoogleDriveSystem, GoogleDriveSystemsControllerService, SecretInfo, SecretsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseEntityEditingComponent, GeboFormGroupsService, GeboUIActionRequest, GeboUIActionRoutingService, GeboUIOutputForwardingService } from "@Gebo.ai/reusable-ui";
import { ConfirmationService } from "primeng/api";
import { map, Observable, of } from "rxjs";
import { newSecretActionRequest } from "../utils/gebo-ai-create-secret-action-request-factory";

@Component({
    selector: "gebo-ai-google-drive-system-admin-component",
    templateUrl: "gebo-ai-google-drive-admin.component.html",
    standalone: false
})
export class GeboAiGoogleDriveSystemAdminComponent extends BaseEntityEditingComponent<GGoogleDriveSystem> {
  /**
   * The name of the entity type being managed by this component
   */
  protected override entityName: string = "GGoogleDriveSystem";
  
  /**
   * Form group that contains all fields required for Google Drive system configuration
   * Includes system properties like code, description, URI, and credentials
   */
  override formGroup: FormGroup<any> = new FormGroup({
    code: new FormControl(),
    description: new FormControl(),
    creationDate: new FormControl(),
    modificationDate: new FormControl(),
    version: new FormControl(),
    contentManagementSystemType: new FormControl(),
    readonly: new FormControl(),
    baseUri: new FormControl(),
    usedCapabilities: new FormControl(),
    driveAccessSecret: new FormControl()
  });
  
  /**
   * Identity context used for retrieving secrets related to Google Workspace
   */
  private actualIdentityContext: string = "GOOGLE_WORKSPACE_CONTEXT";
  
  /**
   * Observable that provides a list of secret information available for Google Workspace
   */
  identitiesObservable: Observable<SecretInfo[]> = this.secretControllerService.getSecretsByContextCode(this.actualIdentityContext);
  
  /**
   * Action request for creating a new secret for Google Cloud JSON credentials
   */
  public newSecretAction: GeboUIActionRequest = newSecretActionRequest(this.actualIdentityContext, this.actualEntityName, this.entity, ["GOOGLE_CLOUD_JSON_CREDENTIALS"]);
  
  /**
   * Constructor initializes the component with required services and sets up form behavior
   * Enforces that contentManagementSystemType is always 'google-drive-handler'
   */
  constructor(
    injector: Injector,
    geboFormGroupsService: GeboFormGroupsService,
    confirmationService: ConfirmationService,
    private googleDriveSystemsControllerService: GoogleDriveSystemsControllerService,
    private secretControllerService: SecretsControllerService,
    geboUIActionRoutingService: GeboUIActionRoutingService,
    outputForwardingService?: GeboUIOutputForwardingService) {
    super(injector, geboFormGroupsService, confirmationService, geboUIActionRoutingService, outputForwardingService);
    this.formGroup.controls["contentManagementSystemType"].valueChanges.subscribe(x => {
      if (x !== 'google-drive-handler') {
        this.formGroup.controls["contentManagementSystemType"].setValue('google-drive-handler');
      }
    });
    this.manageOperationStatus = true;
  }
  
  /**
   * Retrieves a Google Drive system by its code
   * @param code The unique identifier for the Google Drive system
   * @returns An Observable with the found Google Drive system or null if not found
   */
  override findByCode(code: string): Observable<GGoogleDriveSystem | null> {
    return this.googleDriveSystemsControllerService.findGoogleDriveSystemByCode(code);
  }
  
  /**
   * Updates an existing Google Drive system
   * @param value The Google Drive system with updated values
   * @returns An Observable with the updated Google Drive system
   */
  override save(value: GGoogleDriveSystem): Observable<GGoogleDriveSystem> {
    return this.googleDriveSystemsControllerService.updateGoogleDriveSystem(value).pipe(map(r => {
      this.updateLastOperationStatus(r);
      return r.result ? r.result : {}
    }));
  }
  
  /**
   * Creates a new Google Drive system
   * @param value The Google Drive system to be created
   * @returns An Observable with the newly created Google Drive system
   */
  override insert(value: GGoogleDriveSystem): Observable<GGoogleDriveSystem> {
    return this.googleDriveSystemsControllerService.insertGoogleDriveSystem(value).pipe(map(r => {
      this.updateLastOperationStatus(r);
      return r.result ? r.result : {}
    }));
  }
  
  /**
   * Deletes a Google Drive system
   * @param value The Google Drive system to be deleted
   * @returns An Observable with boolean indicating success or failure
   */
  override delete(value: GGoogleDriveSystem): Observable<boolean> {
    return this.googleDriveSystemsControllerService.deleteGoogleDriveSystem(value);
  }
  
  /**
   * Determines if a Google Drive system can be safely deleted by checking for dependencies
   * @param value The Google Drive system to check
   * @returns An Observable with an object containing deletion status and a message
   */
  override canBeDeleted(value: GGoogleDriveSystem): Observable<{ canBeDeleted: boolean; message: string; }> {
    if (value.code) {
      return this.googleDriveSystemsControllerService.findGoogleDriveEndpointsByQbe({
        driveSystemCode: value.code,

      }).pipe(map(endpoints => {
        if (endpoints && endpoints.length) {
          return { canBeDeleted: false, message: "Existing projects/item in this system" };
        } else {
          return { canBeDeleted: true, message: "This system can be safetly deleted" };
        }
      }));
    } else return of({ canBeDeleted: true, message: "This system can be safetly deleted" });
  }
}