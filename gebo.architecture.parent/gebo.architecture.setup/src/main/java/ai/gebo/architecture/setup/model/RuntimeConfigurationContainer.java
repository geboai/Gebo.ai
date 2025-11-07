package ai.gebo.architecture.setup.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RuntimeConfigurationContainer<LibraryModel, ParentReference, RuntimeConfiguration> {
	@NotNull
	private ParentReference parentModel = null;
	@NotNull
	private LibraryModel libraryModel = null;
	private List<RuntimeConfiguration> runtimeConfigs = new ArrayList<RuntimeConfiguration>();
}
