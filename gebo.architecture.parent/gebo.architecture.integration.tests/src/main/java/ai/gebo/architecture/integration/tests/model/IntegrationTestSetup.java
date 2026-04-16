package ai.gebo.architecture.integration.tests.model;

import java.util.List;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IntegrationTestSetup {
	@NotNull
	TestSystemSetup systemSetup = null;
	@Nullable
	List<TestSubsystemSetupInfo> subsystems = null;

}
