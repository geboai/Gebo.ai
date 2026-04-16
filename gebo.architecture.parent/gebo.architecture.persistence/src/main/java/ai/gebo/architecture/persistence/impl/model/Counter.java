package ai.gebo.architecture.persistence.impl.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "counters")
@Data
public class Counter {
	@Id
	private String id;
	private long seq;
	
}