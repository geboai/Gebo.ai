/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

import { Injectable } from "@angular/core";
import { UserspaceFolderDto, UserspaceFileDto, UserspaceControllerService, PublishingStatus } from "@Gebo.ai/gebo-ai-rest-api";

import { Observable, concatMap, map, of } from "rxjs";
import { IOperationStatus } from "../base-entity-editing-component/operation-status";
/**
 * AI generated comments
 * Module for managing userspace files and folders, providing functionality for uploading,
 * finding, and publishing userspace content.
 */

/**
 * Interface representing a model for userspace files upload operation.
 * Contains information about the userspace folder, files, and optional code and description.
 */
export interface UserspaceFilesUploadModel {
    /** Unique identifier code for the userspace folder */
    code?: string;
    /** Description of the userspace folder */
    description?: string;
    /** The userspace folder this model represents */
    userspaceFolder: UserspaceFolderDto;
    /** Collection of userspace files associated with this folder */
    userspaceFiles: UserspaceFileDto[];
}

/**
 * Service for managing userspace files upload operations.
 * Provides methods to find, publish, and poll status of userspace files and folders.
 */
@Injectable()
export class UserspaceFilesUploadModelService {

    /**
     * Creates an instance of UserspaceFilesUploadModelService.
     * @param userspaceServiceController The controller service for userspace operations
     */
    constructor(private userspaceServiceController: UserspaceControllerService) {
    }

    /**
     * Finds a userspace folder and its associated files by the folder code.
     * @param code The unique identifier of the userspace folder to find
     * @returns Observable of the complete UserspaceFilesUploadModel including folder and files
     */
    public findByCode(code: string): Observable<UserspaceFilesUploadModel> {
        return this.userspaceServiceController.findUserspaceFolderByCode(code).pipe(concatMap(folderDto => {
            const outModel: UserspaceFilesUploadModel = {
                code: folderDto.code,
                description: folderDto.description,
                userspaceFolder: folderDto,
                userspaceFiles: []
            };
            if (folderDto.uploadCode) {
                const observable = this.userspaceServiceController.listUserspaceFiles(folderDto.uploadCode);
                return observable.pipe(map(rows => {
                    outModel.userspaceFiles = rows;
                    return outModel;
                }));
            } else {
                return of(outModel);
            }

        }));

    }

    /**
     * Publishes a userspace folder and retrieves the updated model after publishing.
     * @param value The UserspaceFilesUploadModel to publish
     * @returns Observable containing operation status and the updated model after publishing
     */
    publish(value: UserspaceFilesUploadModel): Observable<IOperationStatus<UserspaceFilesUploadModel>> {
        return this.userspaceServiceController.publishFolder(value.userspaceFolder).pipe(concatMap(ret => {
            const out: IOperationStatus<UserspaceFilesUploadModel> = ret as IOperationStatus<UserspaceFilesUploadModel>;
            if (value.userspaceFolder.code)
                return this.findByCode(value.userspaceFolder.code).pipe(map(rv => {
                    out.result = rv;
                    return out;
                }));
            else return of(out);
        }));
    }

    /**
     * Polls the publishing status of a userspace folder.
     * @param dto The UserspaceFolderDto to check status for
     * @returns Observable of the current publishing status
     */
    pollStatus(dto:UserspaceFolderDto): Observable<PublishingStatus> {
        return this.userspaceServiceController.getPublishingStatus(dto);
    }

}