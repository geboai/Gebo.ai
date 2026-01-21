package ai.gebo.llms.deepsearch.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.core.index.HashIndexed;

import ai.gebo.model.base.GBaseObject;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeepSearchDocumentAnalisysResultStep extends GBaseObject {
	@NotNull
	@HashIndexed
	String deepsearchCode = null;
	@NotNull
	String fragment = null;
	@NotNull
	@HashIndexed
	String documentCode = null;
	@NotNull
	@Order
	Integer index = null;
	@NotNull
	@NotEmpty
	List<String> fragmentsCodes = new ArrayList<String>();
	private double processPercentage=0.0;
}
