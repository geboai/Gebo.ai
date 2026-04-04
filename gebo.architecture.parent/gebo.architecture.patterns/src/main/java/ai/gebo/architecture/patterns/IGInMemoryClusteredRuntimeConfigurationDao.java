package ai.gebo.architecture.patterns;

public interface IGInMemoryClusteredRuntimeConfigurationDao<ConfigTypes>
		extends IGRuntimeConfigurationDao<ConfigTypes> {
	public void add(ConfigTypes configType);

	public String getCodeOf(ConfigTypes configType);

	public ConfigTypes removeByCode(String code);

	public void refreshEntry(ConfigTypes configType);

	public void remove(ConfigTypes configType);
}
