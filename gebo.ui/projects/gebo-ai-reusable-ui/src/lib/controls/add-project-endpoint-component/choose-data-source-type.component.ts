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
 * 
 * This component allows users to choose a data source type for a project.
 * It extends BaseEntityEditingComponent to handle the editing of ChooseDataSourceType entities,
 * providing UI for selecting the type of data source to be added to a Gebo project.
 */
import { Component, Injector } from "@angular/core";
import { AbstractControl, FormControl, FormGroup } from "@angular/forms";
import { BaseEntityEditingComponent, GeboActionPerformedEvent, GeboFormGroupsService, GeboUIActionRequest, GeboUIActionRoutingService, GeboUIOutputForwardingService, GeboAIPluggableKnowledgeAdminBaseTreeSearchService } from "@Gebo.ai/reusable-ui";
import { Observable, of } from "rxjs";
import { ChooseDataSourceType } from "./choose-data-source-type";
import { ConfirmationService, MenuItem } from "primeng/api";
import { GProject, ProjectsControllerService } from "@Gebo.ai/gebo-ai-rest-api";

/**
 * Component responsible for selecting a data source type for a project.
 * It provides a UI interface to choose from various data source types
 * that can be added to a Gebo AI project.
 */
@Component({
    selector: "gebo-ui-choose-data-source-type-component",
    templateUrl: "choose-data-source-type.component.html",
    standalone: false
})
export class GeboAIChooseDataSourceTypeComponent extends BaseEntityEditingComponent<ChooseDataSourceType> {
    /**
     * The entity name for this component, used for identification in the base component
     */
    protected override entityName: string = "ChooseDataSourceType";
    
    /**
     * Menu items used to display available data source types
     */
    public items: MenuItem[] = [];
    
    /**
     * Form group containing controls for the ChooseDataSourceType entity
     */
    override formGroup: FormGroup<any> = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        projectCode: new FormControl(),
        entityType: new FormControl()
    });
    
    /**
     * The project associated with this data source type selection
     */
    project?: GProject;

    /**
     * Component constructor that injects required services
     */
    constructor(
        injector:Injector,
        geboFormGroupsService: GeboFormGroupsService,
        confirmationService: ConfirmationService,
        geboUIActionRoutingService: GeboUIActionRoutingService,
        private projectsControllerService: ProjectsControllerService,
        private kbTreeSearchService: GeboAIPluggableKnowledgeAdminBaseTreeSearchService,
        outputForwardingService?: GeboUIOutputForwardingService
    ) {
        super(injector,geboFormGroupsService, confirmationService, geboUIActionRoutingService, outputForwardingService);
    }

    /**
     * Lifecycle hook that initializes the component.
     * Sets up form validation to ensure entityType is selected.
     */
    override ngOnInit(): void {
        
        this.formGroup.setValidators((f: AbstractControl) => {
            const value: ChooseDataSourceType = f.value;
            if (value.entityType)
                return null;
            return { required: "R" };
        });
        super.ngOnInit();
    }

    /**
     * Refreshes the UI based on the current ChooseDataSourceType data.
     * Fetches project details if the project code has changed and
     * regenerates the menu with available data source types.
     * 
     * @param actualValue The current ChooseDataSourceType data
     */
    private refreshUI(actualValue: ChooseDataSourceType) {
        if (actualValue.projectCode && !(this.project?.code === actualValue.projectCode)) {
            this.projectsControllerService.findProjectByCode(actualValue.projectCode).subscribe({
                next: (p) => {
                    this.project = p;
                    const actionConsumer = (action: GeboUIActionRequest) => { };
                    const actionsCallback = (event: GeboActionPerformedEvent) => { };
                    this.items = this.kbTreeSearchService.generateAddToProjectMenu(false, this.project, actionConsumer, actionsCallback);
                }
            })
        }
    }

    /**
     * Toggles selection of a data source type in the form.
     * If the same item is clicked again, it deselects it.
     * 
     * @param item The menu item representing a data source type
     */
    toggle(item: MenuItem) {
        const actualValue = this.formGroup.controls["entityType"].value;
        if (item.id === actualValue) {
            this.formGroup.controls["entityType"].setValue(null);
        } else {
            this.formGroup.controls["entityType"].setValue(item.id);
        }
        this.formGroup.updateValueAndValidity();
    }

    /**
     * Called when persistent data is loaded into the component.
     * Refreshes the UI based on the loaded data.
     * 
     * @param actualValue The loaded ChooseDataSourceType data
     */
    protected override onLoadedPersistentData(actualValue: ChooseDataSourceType): void {
        this.refreshUI(actualValue);
    }

    /**
     * Called when new data is created in the component.
     * Refreshes the UI based on the new data.
     * 
     * @param actualValue The new ChooseDataSourceType data
     */
    protected override onNewData(actualValue: ChooseDataSourceType): void {
        this.refreshUI(actualValue);
    }

    /**
     * Finds a ChooseDataSourceType entity by its code.
     * In this implementation, it returns the current entity or null.
     * 
     * @param code The code to search for
     * @returns An Observable with the found entity or null
     */
    override findByCode(code: string): Observable<ChooseDataSourceType | null> {
        return of(this.entity ? this.entity : null);
    }

    /**
     * Saves changes to an existing ChooseDataSourceType entity.
     * In this implementation, it simply returns the value back.
     * 
     * @param value The entity to save
     * @returns An Observable with the saved entity
     */
    override save(value: ChooseDataSourceType): Observable<ChooseDataSourceType> {
        return of(value);
    }

    /**
     * Inserts a new ChooseDataSourceType entity.
     * In this implementation, it simply returns the value back.
     * 
     * @param value The entity to insert
     * @returns An Observable with the inserted entity
     */
    override insert(value: ChooseDataSourceType): Observable<ChooseDataSourceType> {
        return of(value);
    }

    /**
     * Deletes a ChooseDataSourceType entity.
     * In this implementation, it always returns true.
     * 
     * @param value The entity to delete
     * @returns An Observable with a boolean indicating success
     */
    override delete(value: ChooseDataSourceType): Observable<boolean> {
        return of(true);
    }

    /**
     * Checks if a ChooseDataSourceType entity can be deleted.
     * In this implementation, it always returns false.
     * 
     * @param value The entity to check
     * @returns An Observable with an object containing deletion status and message
     */
    override canBeDeleted(value: ChooseDataSourceType): Observable<{ canBeDeleted: boolean; message: string; }> {
        return of({ canBeDeleted: false, message: "" });
    }
} 