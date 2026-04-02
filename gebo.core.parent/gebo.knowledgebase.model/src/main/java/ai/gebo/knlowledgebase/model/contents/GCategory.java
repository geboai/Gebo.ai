package ai.gebo.knlowledgebase.model.contents;

import java.util.List;

import org.springframework.data.mongodb.core.index.HashIndexed;

import ai.gebo.acl.IAclGrantedResource;
import ai.gebo.model.base.GBaseObject;
import lombok.Data;

@Data
public class GCategory extends GBaseObject implements IAclGrantedResource {
	private String parentCategory = null;
	private TranslatedAttribute multilanguageDescription = null;
	@HashIndexed
	private List<Integer> aclAliases = null;
}
