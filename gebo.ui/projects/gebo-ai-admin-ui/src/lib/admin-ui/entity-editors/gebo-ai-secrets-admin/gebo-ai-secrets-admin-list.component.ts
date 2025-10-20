/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */




import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from "@angular/core";
import { SecretInfo, SecretsControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GeboActionPerformedEvent, GeboActionPerformedType, GeboActionType, GeboUIActionRequest, GeboUIActionRoutingService } from "@Gebo.ai/reusable-ui";

/**
 * AI generated comments
 * 
 * This component displays and manages a list of secrets for a specified context.
 * It allows creating new secrets and refreshing the list when changes occur.
 * The component interacts with the SecretsControllerService to fetch secrets data
 * and uses GeboUIActionRoutingService to handle user actions.
 */
@Component({
    selector: "gebo-ai-secrets-admin-list-component",
    templateUrl: "gebo-ai-secrets-admin-list.component.html",
    standalone: false, providers: [{
        provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("GeboAiSecretsAdminListComponent"),
        multi: true
    }]
})
export class GeboAiSecretsAdminListComponent implements OnChanges, OnInit {
    /** The context code for which secrets should be loaded */
    @Input() contextCode?: string;

    /** Types of secrets that can be created in this component */
    @Input() allowedTypes: SecretInfo.SecretTypeEnum[] = [SecretInfo.SecretTypeEnum.SSHKEY, SecretInfo.SecretTypeEnum.TOKEN, SecretInfo.SecretTypeEnum.USERNAMEPASSWORD];

    /** Flag indicating whether secrets are currently being loaded */
    public loading: boolean = false;

    /** Collection of secret information objects displayed in the list */
    secretsInfo: SecretInfo[] = [];

    /** Event emitter that notifies parent components when the secrets list changes */
    @Output() secretsListChange: EventEmitter<boolean> = new EventEmitter();

    /**
     * Constructor initializes the required services
     * @param secretsControllerService Service for interacting with secrets API
     * @param geboUIActionRoutingService Service for routing UI actions
     */
    constructor(private secretsControllerService: SecretsControllerService, private geboUIActionRoutingService: GeboUIActionRoutingService) {

    }

    /**
     * Fetches secrets from the API for the current context code
     * Sets loading state during API call and clears the secrets list
     * if no context code is provided
     */
    private loadSecrets(): void {

        if (this.contextCode) {
            this.loading = true;
            this.secretsControllerService.getSecretsByContextCode(this.contextCode).subscribe(
                {

                    next: (secretsInfo) => {
                        this.secretsInfo = secretsInfo;
                    },
                    error: (error) => { },
                    complete: () => {
                        this.loading = false;
                    }
                }
            );
        } else {
            this.secretsInfo = [];
        }
    }

    /**
     * Lifecycle hook that responds to input property changes
     * Reloads secrets when the context code changes
     */
    ngOnChanges(changes: SimpleChanges): void {
        if (this.contextCode && changes["contextCode"]) {
            this.loadSecrets();
        }
    }

    /**
     * Lifecycle hook for component initialization
     */
    ngOnInit(): void {

    }

    /**
     * Creates a new secret for the current context
     * Triggers a UI action to display the creation form and handles
     * the action performed event to reload the secrets list when complete
     */
    newSecret(): void {
        const newSecret: { code?: string; description?: string; secretType?: SecretInfo.SecretTypeEnum; contextCode?: string } = {
            contextCode: this.contextCode
        };
        const createAction: GeboUIActionRequest = {
            actionType: GeboActionType.NEW,
            context: {},
            contextType: "SecretsList",
            target: newSecret,
            targetType: "Secret",
            targetFormInputs: { allowedTypes: this.allowedTypes },
            onActionPerformed: (event: GeboActionPerformedEvent) => {
                this.loadSecrets();
                if (event.actionType === GeboActionPerformedType.DELETED || event.actionType === GeboActionPerformedType.INSERTED) {
                    this.secretsListChange.emit(true);
                }
            }
        };
        this.geboUIActionRoutingService.routeEvent(createAction);
    }

}