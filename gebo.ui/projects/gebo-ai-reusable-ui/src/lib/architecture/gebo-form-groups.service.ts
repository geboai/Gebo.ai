/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

import { FormControl, FormGroup } from '@angular/forms';
import { GeboAngularFormGroupMetaInfoControllerService } from '@Gebo.ai/gebo-ai-rest-api';
import { Injectable } from '@angular/core';
import { map, Observable, of } from 'rxjs';
import { FormControl as FormControlMetaInfo, FormGroupMetaInfo } from '@Gebo.ai/gebo-ai-rest-api';
/**
 * AI generated comments
 * 
 * Service that handles the dynamic creation and management of Angular form groups
 * based on metadata retrieved from a backend service. It ensures that form groups 
 * have all the necessary controls based on entity metadata.
 */
@Injectable({
  providedIn: 'root'
})
export class GeboFormGroupsService {
  /** Tracks whether the service is currently loading data */
  private loading: boolean = false;
  
  /** Static cache of form group metadata retrieved from the backend */
  static formGroups?: FormGroupMetaInfo[];
  
  /**
   * Creates an instance of the GeboFormGroupsService
   * @param backendFormGroupService Service for retrieving form group metadata from backend
   */
  constructor(private backendFormGroupService: GeboAngularFormGroupMetaInfoControllerService) {

  }

  /**
   * Adds missing form controls to a form group based on the entity metadata
   * 
   * This method first checks if form group metadata is already loaded. If not,
   * it fetches it from the backend service. It then identifies and adds any
   * controls that exist in the metadata but are missing from the provided form group.
   * 
   * @param formGroup The Angular form group to which missing controls should be added
   * @param editingEntityName The name of the entity being edited
   * @returns Observable that emits the created controls and optionally the class name
   */
  public addFormGroupMissingControls(formGroup: FormGroup, editingEntityName: string): Observable<{createdControls:FormControlMetaInfo[],className?:string}> {
    if (!GeboFormGroupsService.formGroups) {
     return this.backendFormGroupService.getFormGroupsMetaInfos().pipe(map(fgs => {
        GeboFormGroupsService.formGroups = fgs;
        return this.searchAndAddMissingControls(formGroup, fgs, editingEntityName);
      }));
    } else {
      return of(this.searchAndAddMissingControls(formGroup, GeboFormGroupsService.formGroups, editingEntityName));
    }
  }

  /**
   * Searches for controls in the metadata that are missing from the form group and adds them
   * 
   * This method:
   * 1. Finds the form group metadata for the specified entity
   * 2. Identifies controls that exist in the metadata but not in the form group
   * 3. Creates and adds those missing controls to the form group
   * 
   * @param formGroup The form group to check and update
   * @param fgs Array of form group metadata from the backend
   * @param editingEntityName The name of the entity being edited
   * @returns Object containing created controls and optionally the class name
   */
  searchAndAddMissingControls(formGroup: FormGroup<any>, fgs: FormGroupMetaInfo[], editingEntityName: string): {createdControls:FormControlMetaInfo[],className?:string} {
    const formGroupMeta:FormGroupMetaInfo|undefined=fgs.find(x => x.entityName === editingEntityName);
    const allCtrls: FormControlMetaInfo[] | undefined = formGroupMeta?.controls;
    const missingControls: FormControlMetaInfo[] = [];
    if (allCtrls) {
      allCtrls.forEach(ctrl => {
        // If the control exists in metadata but not in the form group, add it
        if (ctrl.propertyName && !formGroup.controls[ctrl.propertyName]) {
          missingControls.push(ctrl);
          const ctrlNew=new FormControl();
          formGroup.addControl(ctrl.propertyName,ctrlNew);
        }
      });
    }
    return {
      createdControls: missingControls,className:formGroupMeta?.className
    };
  }
}