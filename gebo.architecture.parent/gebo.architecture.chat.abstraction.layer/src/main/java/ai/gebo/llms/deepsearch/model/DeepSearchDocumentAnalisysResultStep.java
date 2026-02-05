package ai.gebo.llms.deepsearch.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.annotation.Order;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeepSearchDocumentAnalisysResultStep extends BaseDeepSearchDocumentAnalisysResult {
	
	@NotNull
	@Order
	Integer index = null;
	@NotNull
	@NotEmpty
	List<String> fragmentsCodes = new ArrayList<String>();
	
}
