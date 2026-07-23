package ai.gebo.architecture.documents.cache.service.impl;

import java.util.Date;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.HashIndexed;

import lombok.Data;

@Data
public class AbstractCachedEntry {
	@Id
	protected String id = null;
	protected Date created = new Date();
	@HashIndexed
	@Order(Ordered.HIGHEST_PRECEDENCE)
	protected Date lastAccessed = new Date();

}
