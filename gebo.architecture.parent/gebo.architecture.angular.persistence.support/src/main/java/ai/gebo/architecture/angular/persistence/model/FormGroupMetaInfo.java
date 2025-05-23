/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.angular.persistence.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Gebo.ai comment agent
 * Represents metadata information for a form group, including description,
 * category, and associated controls.
 */
public class FormGroupMetaInfo {
	
	/** Description of the form group */
	private String description = null;
	
	/** Category name of the class */
	private String classCategoryName = null;
	
	/** Name of the entity represented by the form group */
	private String entityName = null;
	
	/** Name of the class represented by the form group */
	private String className = null;
	
	/** List of form controls associated with the form group */
	List<FormControl> controls = new ArrayList<FormControl>();

	/**
	 * Retrieves the entity name.
	 * 
	 * @return A string representing the entity name.
	 */
	public String getEntityName() {
		return entityName;
	}

	/**
	 * Sets the entity name.
	 * 
	 * @param entityName A string representing the entity name to set.
	 */
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	/**
	 * Retrieves the list of form controls.
	 * 
	 * @return A list of FormControl objects.
	 */
	public List<FormControl> getControls() {
		return controls;
	}

	/**
	 * Sets the form controls.
	 * 
	 * @param controls A list of FormControl objects to be set.
	 */
	public void setControls(List<FormControl> controls) {
		this.controls = controls;
	}

	/**
	 * Retrieves the class name.
	 * 
	 * @return A string representing the class name.
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * Sets the class name.
	 * 
	 * @param className A string representing the class name to set.
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * Retrieves the description of the form group.
	 * 
	 * @return A string representing the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the form group.
	 * 
	 * @param description A string representing the description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Retrieves the class category name.
	 * 
	 * @return A string representing the class category name.
	 */
	public String getClassCategoryName() {
		return classCategoryName;
	}

	/**
	 * Sets the class category name.
	 * 
	 * @param classCategoryName A string representing the class category name to set.
	 */
	public void setClassCategoryName(String classCategoryName) {
		this.classCategoryName = classCategoryName;
	}
}