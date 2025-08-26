package ai.gebo.fastsetup.llms.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class RuntimeConfigurationContainer<LibraryModel, ParentReference, RuntimeConfiguration> {
	private ParentReference parentModel = null;
	private LibraryModel libraryModel = null;
	private List<RuntimeConfiguration> runtimeConfigs = new ArrayList<RuntimeConfiguration>();
}
