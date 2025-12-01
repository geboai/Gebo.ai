/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.architecture.persistence;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import ai.gebo.model.base.GBaseObject;
import ai.gebo.model.base.GObjectRef;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * AI generated comments Interface for managing persistent GBaseObject entities.
 * Provides CRUD operations and utility methods to interact with the persistence
 * layer.
 */
public interface IGPersistentObjectManager {

	/**
	 * Ensures the provided object has a unique code.
	 *
	 * @param element The element to check for a unique code.
	 * @param <T>     The type extending GBaseObject.
	 * @return The element with a unique code.
	 * @throws GeboPersistenceException if unable to ensure uniqueness.
	 */
	public <T extends GBaseObject> T ensureUniqueCode(T element) throws GeboPersistenceException;

	/**
	 * Checks if the given object requires a code.
	 *
	 * @param object The object to check.
	 * @return true if the object requires a code, false otherwise.
	 */
	public boolean isRequiresCode(GBaseObject object);

	/**
	 * Determines if the given object can be deleted.
	 *
	 * @param object The object to check for deletability.
	 * @return true if the object can be deleted, false otherwise.
	 * @throws GeboPersistenceException if an error occurs during the check.
	 */
	public boolean isDeletable(GBaseObject object) throws GeboPersistenceException;

	/**
	 * Retrieves a count of related objects.
	 *
	 * @param object The object whose related objects' count is needed.
	 * @return A map with counts of related objects by their class type.
	 * @throws GeboPersistenceException if an error occurs during retrieval.
	 */
	public Map<Class<? extends GBaseObject>, Integer> getCountRelated(GBaseObject object)
			throws GeboPersistenceException;

	/**
	 * Streams the objects related to the given object.
	 *
	 * @param object The base object to find related objects.
	 * @return A stream of related objects.
	 * @throws GeboPersistenceException if an error occurs during streaming.
	 */
	public Stream<? extends GBaseObject> streamRelated(GBaseObject object) throws GeboPersistenceException;

	/**
	 * Inserts a new element into the persistence layer.
	 *
	 * @param element The element to insert.
	 * @param <T>     The type extending GBaseObject.
	 * @return The inserted element.
	 * @throws GeboPersistenceException if insertion fails.
	 */
	public <T extends GBaseObject> T insert(T element) throws GeboPersistenceException;

	@Transactional
	public <T extends GBaseObject> T transactionalInsert(T element) throws GeboPersistenceException;

	/**
	 * Updates an existing element within the persistence layer.
	 *
	 * @param element The element to update.
	 * @param <T>     The type extending GBaseObject.
	 * @return The updated element.
	 * @throws GeboPersistenceException if update fails.
	 */
	public <T extends GBaseObject> T update(T element) throws GeboPersistenceException;
	
	@Transactional
	public <T extends GBaseObject> T transactionalUpdate(T element) throws GeboPersistenceException;

	/**
	 * Deletes an element from the persistence layer.
	 *
	 * @param element The element to delete.
	 * @param <T>     The type extending GBaseObject.
	 * @throws GeboPersistenceException if deletion fails.
	 */
	public <T extends GBaseObject> void delete(T element) throws GeboPersistenceException;
	
	@Transactional
	public <T extends GBaseObject> void transactionalDelete(T element) throws GeboPersistenceException;

	/**
	 * Finds elements by query-by-example.
	 *
	 * @param element The example element to use for searching.
	 * @param <T>     The type extending GBaseObject.
	 * @return A list of elements matching the example.
	 * @throws GeboPersistenceException if search fails.
	 */
	public <T extends GBaseObject> List<T> findByQbe(T element) throws GeboPersistenceException;

	/**
	 * Finds an element by its ID.
	 *
	 * @param type The class type of the element.
	 * @param id   The ID of the element to find.
	 * @param <T>  The type extending GBaseObject.
	 * @return The found element, or null if no element is found.
	 * @throws GeboPersistenceException if search fails.
	 */
	public <T extends GBaseObject> T findById(Class<T> type, String id) throws GeboPersistenceException;
	
	@Transactional
	public <T extends GBaseObject> T transactionalFindById(Class<T> type, String id) throws GeboPersistenceException;

	/**
	 * Retrieves all elements of a specific type.
	 *
	 * @param type The class type to find all elements of.
	 * @param <T>  The type extending GBaseObject.
	 * @return A list of all elements of the specified type.
	 * @throws GeboPersistenceException if retrieval fails.
	 */
	public <T extends GBaseObject> List<T> findAll(Class<T> type) throws GeboPersistenceException;

	/**
	 * Finds all elements by a collection of IDs.
	 *
	 * @param type The class type of the elements.
	 * @param ids  An iterable of IDs for the elements.
	 * @param <T>  The type extending GBaseObject.
	 * @return A list of found elements.
	 * @throws GeboPersistenceException if retrieval fails.
	 */
	public <T extends GBaseObject> List<T> findAllByIds(Class<T> type, Iterable<String> ids)
			throws GeboPersistenceException;

	/**
	 * Finds all elements with pagination support.
	 *
	 * @param type The class type of the elements.
	 * @param page The pagination information.
	 * @param <T>  The type extending GBaseObject.
	 * @return A paged list of elements.
	 * @throws GeboPersistenceException if retrieval fails.
	 */
	public <T extends GBaseObject> Page<T> findAll(Class<T> type, Pageable page) throws GeboPersistenceException;

	/**
	 * Finds all elements by query-by-example with pagination support.
	 *
	 * @param qbe  The example object for querying.
	 * @param page The pagination information.
	 * @param <T>  The type extending GBaseObject.
	 * @return A paged list of elements matching the example.
	 * @throws GeboPersistenceException if search fails.
	 */
	public <T extends GBaseObject> Page<T> findAllByQbe(T qbe, Pageable page) throws GeboPersistenceException;

	/**
	 * Finds an element by its reference object.
	 *
	 * @param reference The reference object.
	 * @param type      The class type of the element.
	 * @param <T>       The type extending GBaseObject.
	 * @return The found element, or null if no element is found.
	 * @throws GeboPersistenceException if search fails.
	 */
	public <T extends GBaseObject> T findByReference(GObjectRef<T> reference, Class<T> type)
			throws GeboPersistenceException;

	/**
	 * Finds all elements extending a given type.
	 *
	 * @param type The base class type.
	 * @param <T>  The type extending GBaseObject.
	 * @return A list of all elements extending the specified type.
	 * @throws GeboPersistenceException if retrieval fails.
	 */
	public <T extends GBaseObject> List<T> findAllExtendingType(Class<T> type) throws GeboPersistenceException;

	/**
	 * Finds all elements by query-by-example and a configuration function.
	 *
	 * @param classType    The class type of the elements.
	 * @param filterSetter A consumer function to configure the filtering.
	 * @param <T>          The type extending GBaseObject.
	 * @return A list of elements matching the criteria.
	 * @throws GeboPersistenceException if retrieval fails.
	 */
	public <T extends GBaseObject> List<T> findAllByQbeSettingFunction(Class<T> classType, Consumer<T> filterSetter)
			throws GeboPersistenceException;

	/**
	 * Counts all elements extending a given type.
	 *
	 * @param type The base class type.
	 * @param <T>  The type extending GBaseObject.
	 * @return The count of all elements extending the specified type.
	 * @throws GeboPersistenceException if counting fails.
	 */
	public <T extends GBaseObject> long countAllExtendingType(Class<T> type) throws GeboPersistenceException;

	/**
	 * Counts all elements of a specific type.
	 *
	 * @param type The class type to count elements of.
	 * @param <T>  The type extending GBaseObject.
	 * @return The count of all elements of the specified type.
	 * @throws GeboPersistenceException if counting fails.
	 */
	public <T extends GBaseObject> long countAll(Class<T> type) throws GeboPersistenceException;

	/**
	 * Finds a specific type of element by its ID using a parent type repository.
	 *
	 * @param type           The parent base type class.
	 * @param specificType   The specific type of the element.
	 * @param code           The code used to find the element.
	 * @param <SpecificType> The specific type extending BaseType.
	 * @param <BaseType>     The base type extending GBaseObject.
	 * @return The found specific type element, or null if no element is found.
	 * @throws GeboPersistenceException if retrieval fails.
	 */
	public <SpecificType extends BaseType, BaseType extends GBaseObject> SpecificType findByIdInParentTypeRepository(
			Class<BaseType> type, Class<SpecificType> specificType, String code) throws GeboPersistenceException;

	/**
	 * Inserts a specific type of element using a parent type repository.
	 *
	 * @param type           The parent base type class.
	 * @param data           The specific type element to insert.
	 * @param <SpecificType> The specific type extending BaseType.
	 * @param <BaseType>     The base type extending GBaseObject.
	 * @return The inserted element.
	 * @throws GeboPersistenceException if insertion fails.
	 */
	public <SpecificType extends BaseType, BaseType extends GBaseObject> SpecificType insertByParentTypeRepository(
			Class<BaseType> type, @NotNull @Valid SpecificType data) throws GeboPersistenceException;

	/**
	 * Updates a specific type of element using a parent type repository.
	 *
	 * @param type           The parent base type class.
	 * @param data           The specific type element to update.
	 * @param <SpecificType> The specific type extending BaseType.
	 * @param <BaseType>     The base type extending GBaseObject.
	 * @return The updated element.
	 * @throws GeboPersistenceException if update fails.
	 */
	public <SpecificType extends BaseType, BaseType extends GBaseObject> SpecificType updateByParentTypeRepository(
			Class<BaseType> type, @NotNull @Valid SpecificType data) throws GeboPersistenceException;

	/**
	 * Deletes a specific type of element using a parent type repository.
	 *
	 * @param type           The parent base type class.
	 * @param data           The specific type element to delete.
	 * @param <SpecificType> The specific type extending BaseType.
	 * @param <BaseType>     The base type extending GBaseObject.
	 * @throws GeboPersistenceException if deletion fails.
	 */
	public <SpecificType extends BaseType, BaseType extends GBaseObject> void deleteByParentTypeRepository(
			Class<BaseType> type, @NotNull @Valid SpecificType data) throws GeboPersistenceException;

}