package ai.gebo.knowledgebase.impl.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.acl.AclGrantType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Document
public class AclEntryRecord {
	@Id
	private Integer id = null;
	@NotNull
	@HashIndexed
	private String aclGrantedUniqueId = null;
	@NotNull
	@HashIndexed
	private AclGrantType grant = null;

}
