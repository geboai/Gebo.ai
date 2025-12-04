package ai.gebo.llms.mistralai.model;

import java.util.List;

import lombok.Data;

@Data
public class MistralBaseModelCard {
	String id = null;
	MistralModelCapabilities capabilities = null;
	String job;
	String root;
	String object;
	Long created;
	String owned_by;
	String name;
	String description;
	Integer max_context_length;
	List<String> aliases;
	String deprecation;
	String deprecation_replacement_model;
	Double default_model_temperature;
	Boolean archived = null;
}