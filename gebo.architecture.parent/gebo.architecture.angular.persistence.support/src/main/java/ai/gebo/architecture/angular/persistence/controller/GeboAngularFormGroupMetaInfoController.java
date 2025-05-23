/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.angular.persistence.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.angular.persistence.model.FormGroupMetaInfo;
import ai.gebo.architecture.angular.persistence.service.GeboAngularFormGroupMetaInfoService;

/**
 * Gebo.ai comment agent
 * Controller for managing form group meta information.
 * Accessible only by users with the ADMIN role.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(value = "api/admin/AngularFormGroupController")
public class GeboAngularFormGroupMetaInfoController {
    
    /**
     * Service for retrieving form group metadata.
     */
    @Autowired
    GeboAngularFormGroupMetaInfoService formGroupMetaInfoService;

    /**
     * Retrieves a list of form group meta information.
     * 
     * @return List of FormGroupMetaInfo objects.
     */
    @GetMapping(value = "getFormGroupsMetaInfos", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FormGroupMetaInfo> getFormGroupsMetaInfos() {
        // Delegate the call to the service to get the metadata
        return formGroupMetaInfoService.getFormGroupsMetaInfos();
    }
}