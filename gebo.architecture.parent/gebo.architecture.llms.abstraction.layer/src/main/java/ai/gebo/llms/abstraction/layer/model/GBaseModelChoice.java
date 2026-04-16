/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.abstraction.layer.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.model.ModelDescription;

import ai.gebo.llms.models.metainfos.ModelMetaInfo;
import ai.gebo.model.base.GBaseVersionableObject;
import lombok.Data;

/**
 * AI generated comments Represents a choice in the GBaseModel hierarchy,
 * providing functionalities to set and get meta information, informative URL,
 * and context length. Extends GBaseVersionableObject to support versioning.
 */
@Data
public class GBaseModelChoice extends GBaseVersionableObject {

	/** Stores meta information about the model */
	private ModelMetaInfo metaInfos = null;

	/** URL containing additional information about the model */
	private String informativeUrl = null;

	/** The context length associated with this model choice */
	private Integer contextLength = null;
	private Object nativeModelMetaInfos = null;

	/**
	 * Default constructor for GBaseModelChoice
	 */
	public GBaseModelChoice() {
		// Default constructor
	}

	/**
	 * Creates a list of model choices from an array of ModelDescription objects.
	 *
	 * @param type              The class type extending GBaseModelChoice to be
	 *                          instantiated and added to list
	 * @param values            An array of ModelDescription instances to create
	 *                          choices from
	 * @param <ModelChoiceType> The type parameter indicating the specific subclass
	 *                          of GBaseModelChoice
	 * @return A list containing instances of ModelChoiceType
	 * @throws RuntimeException if the type doesn't have a public constructor
	 */
	public static <ModelChoiceType extends GBaseModelChoice> List<ModelChoiceType> of(Class<ModelChoiceType> type,
			ModelDescription[] values) {

		// Initialize a list to hold model choices
		List<ModelChoiceType> list = new ArrayList<ModelChoiceType>();
		// Iterate over each model description
		for (ModelDescription modelDescription : values) {
			ModelChoiceType entry;
			try {
				// Instantiate the model choice and set its properties
				entry = type.newInstance();
				entry.setCode(modelDescription.getName());
				entry.setVersion(modelDescription.getVersion());
				entry.setDescription(
						modelDescription.getDescription() != null && !modelDescription.getDescription().trim().isEmpty()
								? modelDescription.getDescription()
								: modelDescription.getName());
				list.add(entry);
			} catch (InstantiationException | IllegalAccessException e) {
				// Handle exceptions if the instantiation fails
				throw new RuntimeException("Maybe type=>" + type.getName() + " does not have a public constructor", e);
			}
		}

		return list;
	}
	

}