package ai.gebo.llms.chat.abstraction.layer.model;

import java.util.Date;

import jakarta.validation.constraints.NotNull;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.HashIndexed;

import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import lombok.Data;

@Data
public class MinimalChatContextCacheItem {
	private static final String SEPARATOR = "-|-";
	@NotNull
	@Id
	String id = null;
	@NotNull
	@HashIndexed
	String lastRequestId = null;
	@NotNull
	@HashIndexed
	String userChatContextCode = null;
	@NotNull
	Date timestamp = new Date();
	@NotNull
	@Order(Ordered.HIGHEST_PRECEDENCE)
	Integer tokensBudget = null;
	@NotNull
	MinimalChatContext item = null;

	public void recalculateId() {
		this.id = userChatContextCode + SEPARATOR + lastRequestId + SEPARATOR + tokensBudget;
	}
}
