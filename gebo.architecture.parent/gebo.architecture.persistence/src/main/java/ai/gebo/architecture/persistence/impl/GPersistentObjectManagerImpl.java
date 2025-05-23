/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.persistence.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.architecture.persistence.IGCodeGeneratorEntityHandler;
import ai.gebo.architecture.persistence.IGCodeGeneratorEntityHandlerRepositoryPattern;
import ai.gebo.architecture.persistence.IGMongoRepositoriesImplementationRepositoryPattern;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.model.annotations.GObjectReference;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.model.base.GObjectRef;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * Implementation of the IGPersistentObjectManager interface.
 * <p>
 * Provides methods for managing persistent objects in a MongoDB repository,
 * including handling dependencies, code generation, and CRUD operations.
 * </p>
 * AI generated comments
 */
@Service
@Scope("singleton")
public class GPersistentObjectManagerImpl implements IGPersistentObjectManager {
	static Logger LOGGER = LoggerFactory.getLogger(GPersistentObjectManagerImpl.class);
	protected IGMongoRepositoriesImplementationRepositoryPattern repoPattern;
	protected IGCodeGeneratorEntityHandlerRepositoryPattern codeGenerationRepoPattern = null;

	/**
	 * Represents an item in a dependency tree, containing the type and lists of 
	 * dependants and dependencies. 
	 */
	public static class DependencyTreeItem {
		public Class<? extends GBaseObject> type;
		public List<DependencyTreeItemEdge> dependants = new ArrayList<DependencyTreeItemEdge>();
		public List<DependencyTreeItemEdge> dependencies = new ArrayList<DependencyTreeItemEdge>();
	}

	/**
	 * Represents an edge in a dependency tree, connecting two items and holding 
	 * accessor methods.
	 */
	public static class DependencyTreeItemEdge {
		public DependencyTreeItem from = null;
		public DependencyTreeItem to = null;
		public Field field = null;
		public Method getAccessor = null, setAccessor = null;
	}

	static Map<Class<? extends GBaseObject>, DependencyTreeItem> descriptors = new HashMap<Class<? extends GBaseObject>, GPersistentObjectManagerImpl.DependencyTreeItem>();

	/**
	 * Creates a new GPersistentObjectManagerImpl with the specified repository 
	 * patterns.
	 *
	 * @param repoPattern               the repository pattern to use
	 * @param codeGenerationRepoPattern the code generation repository pattern to use
	 * @throws GeboPersistenceException if an error occurs during initialization
	 */
	public GPersistentObjectManagerImpl(@Autowired IGMongoRepositoriesImplementationRepositoryPattern repoPattern,
			IGCodeGeneratorEntityHandlerRepositoryPattern codeGenerationRepoPattern) throws GeboPersistenceException {
		this.repoPattern = repoPattern;
		this.codeGenerationRepoPattern = codeGenerationRepoPattern;
		this.initialize();
	}

	/**
	 * Create in memory graph of dependencies
	 * 
	 * @throws GeboPersistenceException if a problem occurs while creating the graph
	 */
	private void initialize() throws GeboPersistenceException {
		if (descriptors.isEmpty()) {
			List<IGBaseMongoDBRepository> handlers = repoPattern.getImplementations();
			for (IGBaseMongoDBRepository handler : handlers) {
				if (handler.getManagedType() == null)
					throw new GeboPersistenceException(
							"There is a mongo repository with getManagedType() returning null");
				if (!descriptors.containsKey(handler.getManagedType())) {
					descriptors.put(handler.getManagedType(),
							createDependencyTreeItem(handler.getManagedType(), descriptors));
				}
			}
		}
	}

	/**
	 * Inspects a class for @GObjectReference annotations in fields and creates a 
	 * graph of dependencies node.
	 * 
	 * @param managedType  the class to inspect
	 * @param descriptors2 the map of existing descriptors
	 * @return the created DependencyTreeItem for the managed type
	 * @throws GeboPersistenceException if an error occurs during inspection
	 */
	private DependencyTreeItem createDependencyTreeItem(Class<? extends GBaseObject> managedType,
			Map<Class<? extends GBaseObject>, DependencyTreeItem> descriptors2) throws GeboPersistenceException {
		LOGGER.info("Begin createDependencyTreeItem(" + managedType.getName() + ",....)");
		DependencyTreeItem item = new DependencyTreeItem();
		item.type = managedType;
		Field[] fields = managedType.getFields();
		// Iterate over fields to find and process GObjectReference annotations
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];

			GObjectReference objectReference = field.getAnnotation(GObjectReference.class);
			if (objectReference == null)
				continue;
			Class<? extends GBaseObject> referredType = objectReference.referencedType();
			LOGGER.info("Inspecting relation between " + managedType.getName() + " ==> " + referredType.getName());
			DependencyTreeItem toItem = descriptors2.get(referredType);
			if (toItem == null) {
				descriptors2.put(referredType, toItem = createDependencyTreeItem(referredType, descriptors2));
			}
			DependencyTreeItemEdge edge = new DependencyTreeItemEdge();
			edge.from = item;
			edge.to = toItem;
			edge.field = field;
			String propertyName = field.getName();
			char capitalized[] = propertyName.toCharArray();
			if (capitalized.length > 0) {
				capitalized[0] = Character.toUpperCase(capitalized[0]);
			}
			propertyName = new String(capitalized);
			try {
				edge.getAccessor = managedType.getMethod("get" + propertyName);
				edge.setAccessor = managedType.getMethod("set" + propertyName, String.class);

			} catch (NoSuchMethodException | SecurityException e) {
				throw new GeboPersistenceException("Problems getting accessors for property =>" + propertyName
						+ " on class=>" + managedType.getName(), e);
			}
			item.dependencies.add(edge);
			toItem.dependants.add(edge);
		}
		LOGGER.info("End createDependencyTreeItem(" + managedType.getName() + ",....)");
		return item;
	}

	/**
	 * Ensures the unique code for the given element by checking existence and
	 * generating if needed.
	 *
	 * @param <T>     the type of object extending GBaseObject
	 * @param element the object to ensure a unique code for
	 * @return the updated element with a unique code set
	 * @throws GeboPersistenceException if any error occurs during code generation or
	 *                                  lookup
	 */
	@Override
	public <T extends GBaseObject> T ensureUniqueCode(T element) throws GeboPersistenceException {
		IGBaseMongoDBRepository handler = repoPattern.findByManagedType(element.getClass());
		if (handler == null)
			throw new GeboPersistenceException("Cannot find a repository for entity:" + element.getClass().getName());
		if (!isRequiresCode(element)) {
			throw new GeboPersistenceException("Cannot create a code for entity:" + element.getClass().getName()
					+ " code already being:" + element.getCode());
		}
		String code = this.generateUniqueCode(element, handler);
		element.setCode(code);
		return element;
	}

	static final int codeMaxLength = 80;

	/**
	 * Generates a unique code for an element using its description and ensuring
	 * uniqueness in the repository.
	 *
	 * @param <T>     the type of object extending GBaseObject
	 * @param element the object to generate a code for
	 * @param handler the repository handler for the object's type
	 * @return the generated unique code
	 * @throws GeboPersistenceException if the object's description is null or if any
	 *                                  repository access issues occur
	 */
	private <T extends GBaseObject> String generateUniqueCode(T element, IGBaseMongoDBRepository<T> handler)
			throws GeboPersistenceException {
		String description = element.getDescription();
		if (description == null || description.trim().length() == 0)
			throw new GeboPersistenceException(
					"Object of class=>" + element.getClass().getName() + " with no description");
		description = description.toLowerCase();
		char buffer[] = description.toCharArray();
		StringBuffer sb = new StringBuffer();
		// Filter and transform description into a code-friendly string
		for (int i = 0; i < buffer.length; i++) {
			char c = buffer[i];
			if (!Character.isDigit(c) && !Character.isLetter(c) && c != '-' && c != '_' && c != '.') {
				if (Character.isSpace(c)) {
					sb.append('-');
				}
			} else {
				sb.append(c);
			}
		}
		String usedCode = sb.toString();
		IGCodeGeneratorEntityHandler codeGenerationHandler = codeGenerationRepoPattern.getAppliableFor(element);
		if (codeGenerationHandler != null) {
			usedCode = codeGenerationHandler.generateCode(element, usedCode);
		}
		// Ensure code uniqueness
		while (handler.existsById(usedCode)) {
			usedCode = usedCode + "-" + getRandomString();
		}
		return usedCode;
	}

	/**
	 * Generates a random string using UUID.
	 *
	 * @return a random string
	 */
	private String getRandomString() {
		return UUID.randomUUID().toString();
	}

	/**
	 * Checks if a code is required for the given object.
	 *
	 * @param object the object to check
	 * @return true if the object's code is null; false otherwise
	 */
	@Override
	public boolean isRequiresCode(GBaseObject object) {
		return object.getCode() == null;
	}

	/**
	 * Determines if the object can be deleted based on its relations.
	 *
	 * @param object the object to check
	 * @return true if the object has no related entities; false otherwise
	 * @throws GeboPersistenceException if an error occurs during relation counting
	 */
	@Override
	public boolean isDeletable(GBaseObject object) throws GeboPersistenceException {
		Map<Class<? extends GBaseObject>, Integer> map = this.getCountRelated(object);
		return map == null || map.isEmpty();
	}

	/**
	 * Updates an existing element by setting modified date and user information,
	 * then saving it.
	 *
	 * @param <T>     the type of object extending GBaseObject
	 * @param element the element to update
	 * @return the updated element
	 * @throws GeboPersistenceException if any error occurs during update
	 */
	@Override
	public <T extends GBaseObject> T update(T element) throws GeboPersistenceException {
		IGBaseMongoDBRepository<T> handler = repoPattern.findByManagedType(element.getClass());
		if (handler == null)
			throw new GeboPersistenceException("No handler known for type=>" + element.getClass().getName());
		if (isRequiresCode(element)) {
			throw new GeboPersistenceException("Trying update type=>" + element.getClass().getName() + " without code");
		}
		element.setDateModified(new Date());
		element.setUserModified(getActualUID());
		return handler.save(element);
	}

	/**
	 * Retrieves the actual user ID from the security context.
	 *
	 * @return the actual user ID, or "anonymous" if not available
	 */
	private String getActualUID() {
		if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null
				&& SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null
				&& SecurityContextHolder.getContext().getAuthentication().getName() != null) {
			return SecurityContextHolder.getContext().getAuthentication().getName();

		}
		return "anonymous";
	}

	/**
	 * Deletes an element if it is deletable, otherwise throws an exception.
	 *
	 * @param <T>     the type of object extending GBaseObject
	 * @param element the element to delete
	 * @throws GeboPersistenceException if the object is not deletable or any error
	 *                                  occurs
	 */
	@Override
	public <T extends GBaseObject> void delete(T element) throws GeboPersistenceException {
		IGBaseMongoDBRepository<T> handler = repoPattern.findByManagedType(element.getClass());
		if (handler == null)
			throw new GeboPersistenceException("No handler known for type=>" + element.getClass().getName());
		if (isDeletable(element)) {
			handler.delete(element);
		} else
			throw new GeboPersistenceException("Cannot delete object of type=>" + element.getClass().getName()
					+ " because it has related elements");
	}

	/**
	 * Gets a count of related objects for a given object.
	 *
	 * @param object the object to check
	 * @return a map of related object types and their counts
	 * @throws GeboPersistenceException if an error occurs during counting
	 */
	@Override
	public Map<Class<? extends GBaseObject>, Integer> getCountRelated(GBaseObject object)
			throws GeboPersistenceException {
		Stream<? extends GBaseObject> stream = this.streamRelated(object);
		final Map<Class<? extends GBaseObject>, Integer> map = new HashMap<Class<? extends GBaseObject>, Integer>();
		// Count occurrences of each related object type in the stream
		stream.forEach((x) -> {
			Integer count = map.get(x.getClass());
			if (count == null)
				count = 0;
			map.put(x.getClass(), new Integer(count.intValue() + 1));
		});
		return map;
	}

	/**
	 * Streams related objects for a given object based on its dependency tree.
	 *
	 * @param object the object to stream related objects for
	 * @return a stream of related objects
	 * @throws GeboPersistenceException if object has no code or other issues occur
	 */
	@Override
	public Stream<? extends GBaseObject> streamRelated(GBaseObject object) throws GeboPersistenceException {
		String code = object.getCode();
		if (code == null)
			throw new GeboPersistenceException(
					"There is no meaning on accessing object related to a null code on type=>"
							+ object.getClass().getName());
		DependencyTreeItem descriptor = descriptors.get(object.getClass());
		if (descriptor == null)
			throw new GeboPersistenceException("No described entity=>" + object.getClass().getName());
		Stream<? extends GBaseObject> stream = Stream.of();
		// Process dependency tree to find related objects
		for (DependencyTreeItemEdge dependantEdge : descriptor.dependants) {
			Class<? extends GBaseObject> dependantTypeClass = dependantEdge.from.type;
			try {

				GBaseObject example = dependantTypeClass.newInstance();
				dependantEdge.setAccessor.invoke(example, code);
				IGBaseMongoDBRepository mongoRepo = repoPattern.findByManagedType(dependantTypeClass);
				List<? extends GBaseObject> list = mongoRepo.findAll(Example.of(example));
				stream = Stream.concat(stream, list.stream());
			} catch (InstantiationException | IllegalAccessException e) {
				throw new GeboPersistenceException("Cannot create entity=>" + dependantTypeClass.getName(), e);
			} catch (IllegalArgumentException | InvocationTargetException e) {
				throw new GeboPersistenceException("Cannot invokate method " + dependantEdge.setAccessor.getName()
						+ " on entity=>" + dependantTypeClass.getName(), e);
			}
		}
		return stream;
	}

	/**
	 * Inserts a new element, generating a unique code if necessary and setting 
	 * creation metadata.
	 *
	 * @param <T>     the type of object extending GBaseObject
	 * @param element the element to insert
	 * @return the inserted element
	 * @throws GeboPersistenceException if code generation fails or any insert issues
	 *                                  occur
	 */
	@Override
	public <T extends GBaseObject> T insert(T element) throws GeboPersistenceException {
		IGBaseMongoDBRepository<T> handler = repoPattern.findByManagedType(element.getClass());
		if (handler == null)
			throw new GeboPersistenceException("No handler known for type=>" + element.getClass().getName());
		if (isRequiresCode(element)) {
			String code = this.generateUniqueCode(element, handler);

			element.setCode(code);
		} else
			throw new GeboPersistenceException("Tryiong inserting an object of type=>" + element.getClass().getName()
					+ " with code=>" + element.getCode());
		element.setDateCreated(new Date());
		element.setUserCreated(getActualUID());
		return handler.insert(element);
	}

	/**
	 * Finds objects by querying by example (QBE).
	 *
	 * @param <T>     the type of object extending GBaseObject
	 * @param element the example object to query by
	 * @return a list of found objects matching the query
	 * @throws GeboPersistenceException if a handler for the type is not found or if 
	 *                                  any query issues occur
	 */
	@Override
	public <T extends GBaseObject> List<T> findByQbe(T element) throws GeboPersistenceException {
		IGBaseMongoDBRepository<T> handler = repoPattern.findByManagedType(element.getClass());
		if (handler == null)
			throw new GeboPersistenceException("No handler known for type=>" + element.getClass().getName());
		return handler.findByQbe(element);
	}

	/**
	 * Finds all objects of a given type.
	 *
	 * @param <T>  the type of object extending GBaseObject
	 * @param type the class of the type to find
	 * @return a list of all objects of the given type
	 * @throws GeboPersistenceException if a handler for the type is not found
	 */
	@Override
	public <T extends GBaseObject> List<T> findAll(Class<T> type) throws GeboPersistenceException {
		IGBaseMongoDBRepository<T> handler = repoPattern.findByManagedType(type);
		if (handler == null)
			throw new GeboPersistenceException("No handler known for type=>" + type.getName());
		return handler.findAll();
	}

	/**
	 * Finds an object by its ID.
	 *
	 * @param <T>  the type of object extending GBaseObject
	 * @param type the class of the object type
	 * @param id   the ID of the object to find
	 * @return the found object or null if not found
	 * @throws GeboPersistenceException if a handler for the type is not found or
	 *                                  if any retrieval issues occur
	 */
	@Override
	public <T extends GBaseObject> T findById(Class<T> type, String id) throws GeboPersistenceException {
		IGBaseMongoDBRepository<T> handler = repoPattern.findByManagedType(type);
		if (handler == null)
			throw new GeboPersistenceException("No handler known for type=>" + type.getName());
		Optional<T> value = handler.findById(id);
		return value.isPresent() ? value.get() : null;
	}

	/**
	 * Finds an object by reference and its expected type.
	 * 
	 * @param <T>       the class type of the expected object
	 * @param reference the reference object containing the class name and code
	 * @param type      the expected class type of the result
	 * @return the object found by the reference
	 * @throws GeboPersistenceException if the reference is not valid or the object is
	 *                                  not found
	 */
	@Override
	public <T extends GBaseObject> T findByReference(GObjectRef<T> reference, Class<T> type)
			throws GeboPersistenceException {
		if (reference.getClassName() == null || reference.getCode() == null)
			throw new GeboPersistenceException("GObjectRef cannot have null code or className");
		try {
			Class<T> _type = (Class<T>) Class.forName(reference.getClassName());

			if (!type.isAssignableFrom(_type))
				throw new GeboPersistenceException(
						"Referenced type:" + _type.getName() + " incompatible with: " + type.getName());
			IGBaseMongoDBRepository<T> repo = repoPattern.findByManagedType(_type);
			Optional<T> instance = repo.findById(reference.getCode());
			if (instance.isEmpty())
				throw new GeboPersistenceException(
						"Object of type: " + _type.getName() + " not found with code=" + reference.getCode());
			return instance.get();
		} catch (ClassNotFoundException e) {
			throw new GeboPersistenceException("Class not found:" + reference.getClassName(), e);
		}

	}

	/**
	 * Finds all objects that extend a given type.
	 *
	 * @param <T>  the type of object extending GBaseObject
	 * @param type the base class type to find extensions of
	 * @return a list of all objects extending the given type
	 * @throws GeboPersistenceException if any retrieval issues occur
	 */
	@Override
	public <T extends GBaseObject> List<T> findAllExtendingType(Class<T> type) throws GeboPersistenceException {
		List<T> instances = new ArrayList<T>();
		List<IGBaseMongoDBRepository> impls = repoPattern.getImplementations();
		for (IGBaseMongoDBRepository igBaseMongoDBRepository : impls) {
			if (type.isAssignableFrom(igBaseMongoDBRepository.getManagedType())) {
				instances.addAll(igBaseMongoDBRepository.findAll());
			}
		}
		return instances;
	}

	/**
	 * Finds a pageable list of all objects of a given type.
	 *
	 * @param <T>  the type of object extending GBaseObject
	 * @param type the class of the type to find
	 * @param page the pagination information
	 * @return a page of found objects
	 * @throws GeboPersistenceException if a handler for the type is not found or
	 *                                  if any query issues occur
	 */
	@Override
	public <T extends GBaseObject> Page<T> findAll(Class<T> type, Pageable page) throws GeboPersistenceException {
		IGBaseMongoDBRepository handler = repoPattern.findByManagedType(type);
		if (handler == null)
			throw new GeboPersistenceException("Cannot find a repository for entity:" + type.getName());
		return handler.findAll(page);
	}

	/**
	 * Finds all objects matching a query-by-example with pagination.
	 *
	 * @param <T>  the type of object extending GBaseObject
	 * @param qbe  the example object to query by
	 * @param page the pagination information
	 * @return a page of objects matching the example
	 * @throws GeboPersistenceException if a handler for the type is not found or
	 *                                  if any query issues occur
	 */
	@Override
	public <T extends GBaseObject> Page<T> findAllByQbe(T qbe, Pageable page) throws GeboPersistenceException {
		IGBaseMongoDBRepository handler = repoPattern.findByManagedType(qbe.getClass());
		if (handler == null)
			throw new GeboPersistenceException("Cannot find a repository for entity:" + qbe.getClass().getName());
		return handler.findAll(Example.of(qbe), page);
	}

	/**
	 * Finds all objects of a given type by their IDs.
	 *
	 * @param <T>  the type of object extending GBaseObject
	 * @param type the class of the type to find
	 * @param ids  the IDs of the objects to find
	 * @return a list of found objects
	 * @throws GeboPersistenceException if a handler for the type is not found or
	 *                                  if any retrieval issues occur
	 */
	@Override
	public <T extends GBaseObject> List<T> findAllByIds(Class<T> type, Iterable<String> ids)
			throws GeboPersistenceException {
		IGBaseMongoDBRepository handler = repoPattern.findByManagedType(type);
		if (handler == null)
			throw new GeboPersistenceException("Cannot find a repository for entity:" + type.getName());
		return handler.findAllById(ids);
	}

	/**
	 * Finds all objects matching a query set by a filter setting function.
	 *
	 * @param <T>         the type of object extending GBaseObject
	 * @param type        the class of the type to find
	 * @param filterSetter a consumer to set filters on the query
	 * @return a list of objects matching the filter
	 * @throws GeboPersistenceException if any errors occur during execution
	 */
	@Override
	public <T extends GBaseObject> List<T> findAllByQbeSettingFunction(Class<T> type, Consumer<T> filterSetter)
			throws GeboPersistenceException {
		List<T> values = new ArrayList<T>();
		List<IGBaseMongoDBRepository> handlers = repoPattern.getImplementations().stream().filter(x -> {
			return type.isAssignableFrom(x.getManagedType());
		}).toList();
		// Generate filter set queries
		for (IGBaseMongoDBRepository handler : handlers) {
			try {
				T filter = (T) handler.getManagedType().newInstance();
				filterSetter.accept(filter);
				values.addAll(handler.findByQbe(filter));
			} catch (InstantiationException | IllegalAccessException e) {
			}
		}
		return values;
	}

	/**
	 * Counts all objects extending a given type.
	 *
	 * @param <T>  the type of object extending GBaseObject
	 * @param type the base class type to count extensions of
	 * @return the total count of objects extending the given type
	 * @throws GeboPersistenceException if any counting issues occur
	 */
	@Override
	public <T extends GBaseObject> long countAllExtendingType(Class<T> type) throws GeboPersistenceException {
		List<IGBaseMongoDBRepository> handlers = repoPattern.getImplementations().stream().filter(x -> {
			return type.isAssignableFrom(x.getManagedType());
		}).toList();
		long total = 0l;
		// Sum counts of all repositories for the relevant types
		for (IGBaseMongoDBRepository igBaseMongoDBRepository : handlers) {
			total += igBaseMongoDBRepository.count();
		}
		return total;
	}

	/**
	 * Counts all objects of a given type.
	 *
	 * @param <T>  the type of object extending GBaseObject
	 * @param type the class of the type to count
	 * @return the total count of objects of the given type
	 * @throws GeboPersistenceException if a handler for the type is not found or if 
	 *                                  any counting issues occur
	 */
	@Override
	public <T extends GBaseObject> long countAll(Class<T> type) throws GeboPersistenceException {
		IGBaseMongoDBRepository handler = repoPattern.findByManagedType(type);
		if (handler == null)
			throw new GeboPersistenceException("Cannot find a repository for entity:" + type.getName());

		return handler.count();
	}

	/**
	 * Finds a specific type by ID in a repository for its parent type.
	 *
	 * @param <SpecificType> the specific type of object to find
	 * @param <BaseType>     the base type of object
	 * @param type           the class of the base type
	 * @param specificType   the class of the specific type
	 * @param code           the code (ID) of the specific type
	 * @return the found specific type object
	 * @throws GeboPersistenceException if any issues occur during retrieval
	 */
	@Override
	public <SpecificType extends BaseType, BaseType extends GBaseObject> SpecificType findByIdInParentTypeRepository(
			Class<BaseType> type, Class<SpecificType> specificType, String code) throws GeboPersistenceException {
		return (SpecificType) findById(type, code);
	}

	/**
	 * Inserts a specific type object into a repository for its parent type.
	 *
	 * @param <SpecificType> the specific type of object to insert
	 * @param <BaseType>     the base type of object
	 * @param type           the class of the base type
	 * @param data           the specific type object to insert
	 * @return the inserted specific type object
	 * @throws GeboPersistenceException if any issues occur during insertion
	 */
	@Override
	public <SpecificType extends BaseType, BaseType extends GBaseObject> SpecificType insertByParentTypeRepository(
			Class<BaseType> type, @NotNull @Valid SpecificType data) throws GeboPersistenceException {
		IGBaseMongoDBRepository handler = repoPattern.findByManagedType(type);
		if (handler == null)
			throw new GeboPersistenceException("Cannot find a repository for entity:" + type.getName());
		if (isRequiresCode(data)) {
			String code = this.generateUniqueCode(data, handler);

			data.setCode(code);
		}
		return (SpecificType) handler.insert(data);
	}

	/**
	 * Updates a specific type object in a repository for its parent type.
	 *
	 * @param <SpecificType> the specific type of object to update
	 * @param <BaseType>     the base type of object
	 * @param type           the class of the base type
	 * @param data           the specific type object to update
	 * @return the updated specific type object
	 * @throws GeboPersistenceException if any issues occur during the update
	 */
	@Override
	public <SpecificType extends BaseType, BaseType extends GBaseObject> SpecificType updateByParentTypeRepository(
			Class<BaseType> type, @NotNull @Valid SpecificType data) throws GeboPersistenceException {
		IGBaseMongoDBRepository handler = repoPattern.findByManagedType(type);
		if (handler == null)
			throw new GeboPersistenceException("Cannot find a repository for entity:" + type.getName());
		return (SpecificType) handler.save(data);
	}

	/**
	 * Deletes a specific type object from the repository for its parent type.
	 *
	 * @param <SpecificType> the specific type of object to delete
	 * @param <BaseType>     the base type of object
	 * @param type           the class of the base type
	 * @param data           the specific type object to delete
	 * @throws GeboPersistenceException if any issues occur during deletion
	 */
	@Override
	public <SpecificType extends BaseType, BaseType extends GBaseObject> void deleteByParentTypeRepository(
			Class<BaseType> type, @NotNull @Valid SpecificType data) throws GeboPersistenceException {
		IGBaseMongoDBRepository handler = repoPattern.findByManagedType(type);
		if (handler == null)
			throw new GeboPersistenceException("Cannot find a repository for entity:" + type.getName());
		handler.delete(data);

	}

}