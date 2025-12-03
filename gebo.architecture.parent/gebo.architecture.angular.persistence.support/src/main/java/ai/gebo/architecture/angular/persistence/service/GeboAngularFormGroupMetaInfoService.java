/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.architecture.angular.persistence.service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.angular.persistence.model.DeletableStatus;
import ai.gebo.architecture.angular.persistence.model.FormControl;
import ai.gebo.architecture.angular.persistence.model.FormGroupMetaInfo;
import ai.gebo.architecture.angular.persistence.model.SimpleGObjectRef;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.architecture.persistence.IGMongoRepositoriesImplementationRepositoryPattern;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.model.annotations.EntityDescription;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.model.base.GObjectRef;
import lombok.AllArgsConstructor;

/**
 * Service class for managing form group metadata information in an Angular
 * application. Provides functionalities to generate metadata based on entity
 * types managed by the repository pattern. Gebo.ai comment agent
 */
@Service
@AllArgsConstructor
public class GeboAngularFormGroupMetaInfoService {

	// Repository pattern for fetching implementations

	private final IGMongoRepositoriesImplementationRepositoryPattern repoOfRepos;
	private final IGPersistentObjectManager persistentObjectManager;

	/**
	 * Generates metadata information for a given class type.
	 *
	 * Analyzes the class type for annotations and getter methods to compile
	 * comprehensive metadata information, including entity name, class name,
	 * category, description, and control properties.
	 *
	 * @param type The class type to analyze.
	 * @return A FormGroupMetaInfo object containing metadata of the specified
	 *         class.
	 */
	private FormGroupMetaInfo metaInfoOf(Class type) {
		FormGroupMetaInfo meta = new FormGroupMetaInfo();
		meta.setEntityName(type.getSimpleName());
		meta.setClassName(type.getName());

		// Check for EntityDescription annotation and set additional metadata
		Annotation annotation = type.getAnnotation(EntityDescription.class);
		if (annotation instanceof EntityDescription) {
			EntityDescription ed = (EntityDescription) annotation;
			meta.setClassCategoryName(ed.entityCategory().getName());
			meta.setDescription(ed.description());
		}

		Method[] methods = type.getMethods();
		if (methods != null) {
			for (Method method : methods) {
				// Check if method is a valid getter method
				if (method.getName().startsWith("get") && !method.getName().equals("getClass")
						&& Modifier.isPublic(method.getModifiers()) && !Modifier.isStatic(method.getModifiers())) {
					// Extract property name from getter method
					String propertyName = method.getName().substring("get".length());
					char pNameBuffer[] = propertyName.toCharArray();
					pNameBuffer[0] = Character.toLowerCase(pNameBuffer[0]);

					// Create a FormControl object for the property
					FormControl fc = new FormControl();
					fc.setPropertyName(new String(pNameBuffer));
					fc.setJavaType(method.getReturnType().getName());

					// Add the FormControl to the metadata
					meta.getControls().add(fc);
				}
			}
		}
		return meta;
	}

	/**
	 * Retrieves form group metadata information for all managed entity types.
	 *
	 * Collects and returns a list of FormGroupMetaInfo objects for each entity type
	 * that is managed by the current repository implementation, utilizing the
	 * generated metadata for entity descriptions and their properties.
	 *
	 * @return A list of FormGroupMetaInfo objects representing each managed entity
	 *         type.
	 */
	public List<FormGroupMetaInfo> getFormGroupsMetaInfos() {
		List<FormGroupMetaInfo> out = new ArrayList<FormGroupMetaInfo>();

		// Retrieve all implementations of the repository pattern
		List<IGBaseMongoDBRepository> implementations = repoOfRepos.getImplementations();

		// Generate metadata for each managed type and collect into list
		out = implementations.stream().map(x -> {
			return metaInfoOf(x.getManagedType());
		}).toList();

		return out;
	}

	public DeletableStatus getDeletableStatus(GObjectRef reference) throws GeboPersistenceException {
		boolean deletable = persistentObjectManager.isDeletable(reference);
		return deletable ? DeletableStatus.DELETABLE_STATUS : DeletableStatus.NOT_DELETABLE_STATUS;
	}

	public DeletableStatus getDeletableStatus(SimpleGObjectRef reference) throws GeboPersistenceException {
		try {
			Class<? extends GBaseObject> completeType = (Class<? extends GBaseObject>) Class
					.forName(reference.getSimpleClassName());
			GObjectRef objectRef = new GObjectRef<>();
			objectRef.setClassName(completeType.getName());
			objectRef.setCode(reference.getCode());
			return getDeletableStatus(objectRef);
		} catch (Throwable th) {
			List<IGBaseMongoDBRepository> implementations = repoOfRepos.findImplementations(
					x -> x.getManagedType().getSimpleName().equals(reference.getSimpleClassName()));
			if (implementations.size() == 1) {
				Class<? extends GBaseObject> completeType = implementations.get(0).getManagedType();
				GObjectRef objectRef = new GObjectRef<>();
				objectRef.setClassName(completeType.getName());
				objectRef.setCode(reference.getCode());
				return getDeletableStatus(objectRef);
			} else
				throw new GeboPersistenceException("Found " + implementations.size() + " repos for simpleClassName="
						+ reference.getSimpleClassName());
		}

	}
}