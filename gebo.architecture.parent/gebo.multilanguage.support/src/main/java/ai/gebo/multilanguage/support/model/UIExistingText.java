package ai.gebo.multilanguage.support.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class UIExistingText {
	public static final String SEPARATOR = "-§-";

	private void computeId() {
		this.id = (moduleId != null && entityId != null && componentId != null && fieldId != null)
				? moduleId.toLowerCase() + SEPARATOR + entityId.toLowerCase() + SEPARATOR + componentId.toLowerCase()
						+ SEPARATOR + fieldId.toLowerCase()
				: UUID.randomUUID().toString();
	}
	@Id
	String id = null;
	@NotNull
	private String moduleId = null;
	@NotNull
	private String entityId = null;
	@NotNull
	private String componentId = null;
	private String key = null;
	@NotNull
	private String fieldId = null;
	@NotNull
	private String text = null;

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
		this.computeId();
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
		this.computeId();
	}

	public String getComponentId() {
		return componentId;
	}

	public void setComponentId(String componentId) {
		this.componentId = componentId;
		this.computeId();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
		this.computeId();
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
		this.computeId();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		this.computeId();
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id=id;
		this.computeId();
	}
}
