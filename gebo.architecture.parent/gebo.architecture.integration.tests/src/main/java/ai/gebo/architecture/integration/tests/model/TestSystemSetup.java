package ai.gebo.architecture.integration.tests.model;

import java.util.List;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TestSystemSetup {
	@NotNull
	String username;
	@NotNull
	String password;
	@NotNull
	String vendorId;
	@NotNull
	String vendorUser;
	@NotNull
	String vendorApiKey;
	@NotNull
	String host;
	int port;
	@Nullable
	List<TestLLMSetup> models = null;
}
