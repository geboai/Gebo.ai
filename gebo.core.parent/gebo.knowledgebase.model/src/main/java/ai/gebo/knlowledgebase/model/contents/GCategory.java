package ai.gebo.knlowledgebase.model.contents;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.index.Indexed;

import ai.gebo.acl.IAclGrantedResource;
import ai.gebo.model.base.GBaseObject;
import lombok.Data;

@Data
@Document
public class GCategory extends GBaseObject implements IAclGrantedResource {

	private TranslatedAttribute multilanguageDescription = null;
	// lists: MongoDB hashes no array, their indexes are multikey ones
	@Indexed
	private List<Integer> aclAliases = null;
	@Indexed
	private List<String> childCategories = null;
	@HashIndexed
	private Boolean rootCategory = null;
}
