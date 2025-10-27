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
 * This file contains components for configuring vector store settings in a wizard-style interface.
 * It provides services and UI components to manage vector store setup in the Gebo.ai system.
 */

import { Component, Inject, Injectable } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { ComponentVectorStoreStatus, FastVectorStoreSetupData, GeboFastVectorStoreSetupControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { AbstractStatusService, BaseWizardSectionComponent, fieldHostComponentName, GEBO_AI_FIELD_HOST, SetupWizardComunicationService } from "@Gebo.ai/reusable-ui";
import { ToastMessageOptions } from "primeng/api";
import { map, Observable } from "rxjs";

/**
 * Service for managing vector store setup status within the wizard flow.
 * Extends AbstractStatusService to provide standardized status checking methods.
 * Communicates with the backend API to check if vector store is properly configured.
 */
@Injectable()
export class VectorStoreWizardService extends AbstractStatusService {
    constructor(private vectorStoreFastSetupService: GeboFastVectorStoreSetupControllerService) {
        super();
    }

    /**
     * Overrides the parent method to check if the vector store is set up.
     * Retrieves the vector store status from the API and maps the response to a boolean.
     * 
     * @returns Observable<boolean> that emits true if the vector store is set up, false otherwise
     */
    public override getBooleanStatus(): Observable<boolean> {
        return this.vectorStoreFastSetupService.getVectorStoreStatus().pipe(map(x => x.isSetup === true));
    }
}

/**
 * Component for configuring vector store settings in a wizard-style interface.
 * Provides UI controls for setting up different vector store providers like Qdrant.
 * Extends BaseWizardSectionComponent to integrate with the overall setup wizard flow.
 */
@Component({
    selector: "gebo-vectorstore-wizard-component",
    templateUrl: "vectorstore-wizard.component.html",
    standalone: false,
    providers: [{ provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("VectorStoreWizardComponent") }]
})
export class VectorStoreWizardComponent extends BaseWizardSectionComponent {
    /**
     * Form group for vector store configuration with nested form groups for
     * different vector store providers (Redis, Qdrant)
     */
    public formGroup: FormGroup = new FormGroup({
        product: new FormControl(),
        redisConfig: new FormGroup({
            host: new FormControl(),
            port: new FormControl(),
            username: new FormControl(),
            password: new FormControl()
        }),
        qdrantConfig: new FormGroup({
            host: new FormControl(),
            port: new FormControl(),
            apiKey: new FormControl(),
            tls: new FormControl()
        })
    });

    /**
     * Current status of the vector store configuration
     */
    public vectorStoreStatus?: ComponentVectorStoreStatus;

    /**
     * Available vector store products that can be configured
     * Currently only includes Qdrant, with others commented out
     */
    public vectorStoreProducts: { code: ComponentVectorStoreStatus.ProductEnum, label: string, description: string }[] = [{
        code: "QDRANT", label: "Qdrant", description: "is a server or docker installation visit https://qdrant.tech/"
    }/*, { code: "LUCENE", label: "Lucene embedded database", description: "Lucene embedded vector database, for tests only, have severe vector length and performance limitations" },
    { code: "MONGO", label: "Mongo Atlas", description: "use the same NOSQL database of this gebo.ai installation if is an \'Atlas\' version of Mongo" },
    { code: "REDIS", label: "Redis", description: "Use Redis database on a on premise/docker or cloud installation" } */];

    /**
     * Currently selected vector store product
     */
    product?: ComponentVectorStoreStatus.ProductEnum;

    /**
     * Creates an instance of VectorStoreWizardComponent.
     * 
     * @param setupWizardComunicationService Service for communicating with the setup wizard
     * @param vectorStoreFastSetupService Service for interacting with vector store setup API
     */
    constructor(setupWizardComunicationService: SetupWizardComunicationService, private vectorStoreFastSetupService: GeboFastVectorStoreSetupControllerService) {
        super(setupWizardComunicationService);
    }

    /**
     * Loads the current vector store configuration from the backend.
     * Updates the form with the retrieved values and sets the component state.
     * Overrides the parent method to provide specific implementation.
     */
    public override reloadData(): void {
        this.loading = true;
        this.vectorStoreFastSetupService.getVectorStoreStatus().subscribe({
            next: (value) => {
                this.vectorStoreStatus = value;
                this.isSetupCompleted = value?.isSetup === true;
                this.formGroup.patchValue({ product: value.product, qdrantConfig: value.qdrantConfig, redisConfig: value.redisConfig });
                if (value.product)
                    this.productChange(value.product);
            },
            complete: () => {
                this.loading = false;
            }
        });
    }

    /**
     * Handles changes to the selected vector store product.
     * Enables/disables the appropriate configuration form groups based on selection.
     * 
     * @param value The selected vector store product enum value
     */
    productChange(value: ComponentVectorStoreStatus.ProductEnum) {
        this.formGroup.controls["qdrantConfig"].disable();
        this.formGroup.controls["redisConfig"].disable();
        this.product = value;
        if (value && value === "QDRANT") {
            this.formGroup.controls["qdrantConfig"].enable();
        }
        if (value && value === "REDIS") {
            this.formGroup.controls["redisConfig"].enable();
        }
    }

    /**
     * Sends the vector store configuration to the backend API.
     * Updates the component state based on the API response.
     * Handles user feedback messages from the API.
     */
    public updateVectorStoreConfig() {
        const data: FastVectorStoreSetupData = this.formGroup.value;
        if (data.product && data.product !== "QDRANT") {
            data.qdrantConfig = undefined;
        }
        this.loading = true;
        this.vectorStoreFastSetupService.createVectorStoreConfiguration(data).subscribe({
            next: (value) => {
                this.isSetupCompleted = value?.result?.isSetup === true;
                this.userMessages = value?.messages as ToastMessageOptions[];
                this.formGroup.patchValue({ product: value?.result?.product, qdrantConfig: value?.result?.qdrantConfig, redisConfig: value?.result?.redisConfig });
                if (value?.result?.product)
                    this.productChange(value?.result?.product);
            },
            complete: () => {
                this.loading = false;
            }
        });
    }
}