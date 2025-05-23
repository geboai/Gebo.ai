/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.microsoft.sharepoint.cloud.model;

// POJO for each content type
public class CloudSharepointContentType {
	// For classic REST, Id is often an object with "StringValue"
	private CloudSharepointContentTypeId Id;
	private String Name;
	private String Description;
	private String Group;

	public CloudSharepointContentTypeId getId() {
		return Id;
	}

	public void setId(CloudSharepointContentTypeId id) {
		this.Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		this.Name = name;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String desc) {
		this.Description = desc;
	}

	public String getGroup() {
		return Group;
	}

	public void setGroup(String group) {
		this.Group = group;
	}
}