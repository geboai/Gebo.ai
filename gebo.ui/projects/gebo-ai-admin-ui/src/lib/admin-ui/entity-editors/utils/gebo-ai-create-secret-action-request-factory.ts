/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

import { GeboTokenContent, SecretInfo } from "@Gebo.ai/gebo-ai-rest-api";
import { GeboActionType, GeboUIActionRequest } from "@Gebo.ai/reusable-ui";
import { SecretWrapper } from "../gebo-ai-secrets-admin/gebo-ai-secrets-admin-edit.component";

/**
 * @file Contains utility functions for creating secret action requests in the Gebo.ai system.
 * AI generated comments
 */

/**
 * Creates a new GeboUIActionRequest for secret creation.
 * 
 * This function constructs a request object for creating a new secret in the system.
 * It configures the action type as 'NEW', sets the appropriate context information,
 * and prepares a Secret wrapper with the specified secret type and context code.
 * 
 * @param secretContext - The context code for the secret being created
 * @param actualContext - The actual context type in which the secret is being created
 * @param contextObject - The context object associated with the secret
 * @param allowedTypes - Array of allowed secret types; defaults to TOKEN if not specified
 * @returns A fully configured GeboUIActionRequest object ready to be processed
 */
export function newSecretActionRequest(secretContext:string,actualContext:string,contextObject:any,allowedTypes: SecretInfo.SecretTypeEnum[]=[SecretInfo.SecretTypeEnum.TOKEN]):GeboUIActionRequest {
    const secretNewObject:SecretWrapper={
        secretType:allowedTypes[0],
        contextCode:secretContext
    };
    const action:GeboUIActionRequest={
        actionType:GeboActionType.NEW,
        contextType:actualContext,
        context:contextObject,
        targetType:"Secret",
        target:secretNewObject,
        targetFormInputs:{
            allowedTypes:allowedTypes
        }
    };
    return action;
}