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
 * This file contains configuration and service implementation for the userspace files module.
 * It defines entity form configurations and provides a module for handling userspace files
 * functionality within a Gebo.ai application.
 */

import { CommonModule } from "@angular/common";
import { Injectable, NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { BlockUIModule } from "primeng/blockui";
import { ButtonModule } from "primeng/button";
import { DialogModule } from "primeng/dialog";
import { PanelModule } from "primeng/panel";
import { TreeModule } from "primeng/tree";
import { GeboAIUserpaceFilesComponent } from "./userspace-files.component";
import { GeboAIUserspaceFolderComponent } from "./userspace-folder.component";
import { GeboAIUserspaceBrowseComponent } from "./userspace-browse.component";

import { CheckboxModule } from "primeng/checkbox";
import { FileUploadModule } from "primeng/fileupload";
import { GeboAIUserspaceKnowledgebaseComponent } from "./user-knowledgebase.component";
import { GeboUIEntityFormsLauncherService } from "../../architecture/gebo-ui-entity-forms-launcher.service";
import { GeboAIUserspaceFilesUploadComponent } from "./userspace-files-upload.component";
import { InputTextModule } from "primeng/inputtext";
import { MultiSelectModule } from "primeng/multiselect";
import { ChipModule } from "primeng/chip";
import { FieldsetModule } from "primeng/fieldset";
import { GeboUIEntityFormConfig } from "../../architecture/gebo-ui-entity-form-config";
import { GeboAIContentViewerModule } from "../content-viewer/gebo-ai-content-viewer.module";
import { GeboUIArchitectureModule } from "../../architecture/gebo-ui-architecture.module";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../field-host-component-iface/field-host-component-iface";
import { GeboAIFieldTranslationContainerModule } from "../field-translation-container/field-container.module";

/**
 * Configuration object for the UserspaceFolderDto entity.
 * Defines the entity name and its corresponding UI component.
 */
const userspaceFolderDtoConfig: GeboUIEntityFormConfig = {
    entityName: "UserspaceFolderDto",
    entityUI: GeboAIUserspaceFolderComponent
};

/**
 * Configuration object for the UserspaceKnowledgebaseDto entity.
 * Defines the entity name and its corresponding UI component.
 */
const userspaceKnowledgebaseDtoConfig: GeboUIEntityFormConfig = {
    entityName: "UserspaceKnowledgebaseDto",
    entityUI: GeboAIUserspaceKnowledgebaseComponent
};

/**
 * Service responsible for providing entity form configurations for userspace-related entities.
 * Extends the base GeboUIEntityFormsLauncherService to specialize in userspace forms.
 */
@Injectable()
export class UserspaceFormsListService extends GeboUIEntityFormsLauncherService {
    /**
     * Overrides the base method to return configurations specific to userspace entities.
     * @returns An array of GeboUIEntityFormConfig objects for userspace entities
     */
    public override getCurrentConfigurations(): GeboUIEntityFormConfig[] {
        const vector: GeboUIEntityFormConfig[] = [userspaceFolderDtoConfig, userspaceKnowledgebaseDtoConfig];
        return vector;
    }
};

/**
 * Angular module that packages all components, services, and dependencies related to
 * userspace files functionality. It imports necessary Angular and PrimeNG modules and
 * declares components related to userspace file management.
 */
@NgModule({
    imports: [CommonModule, ReactiveFormsModule, FormsModule, PanelModule, DialogModule, TreeModule, BlockUIModule, ButtonModule, GeboAIContentViewerModule, TreeModule, CheckboxModule, FileUploadModule, GeboUIArchitectureModule, InputTextModule, MultiSelectModule, ChipModule, FieldsetModule, GeboAIFieldTranslationContainerModule],
    declarations: [GeboAIUserpaceFilesComponent, GeboAIUserspaceFolderComponent, GeboAIUserspaceKnowledgebaseComponent, GeboAIUserspaceBrowseComponent, GeboAIUserspaceFilesUploadComponent],
    exports: [GeboAIUserpaceFilesComponent],
    providers: [{
        provide: GeboUIEntityFormsLauncherService, useValue: UserspaceFormsListService
    }
    ]
})
export class GeboAIUserspaceFilesModule { }