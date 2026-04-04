package ai.gebo.architecture.patterns;

import java.util.List;

public abstract class GAbstractInMemoryClusteredRuntimeConfigurationDao<ConfigTypes>
		extends GAbstractRuntimeConfigurationDao<ConfigTypes>
		implements IGInMemoryClusteredRuntimeConfigurationDao<ConfigTypes> {

	public GAbstractInMemoryClusteredRuntimeConfigurationDao(List<ConfigTypes> staticConfigs) {
		super(staticConfigs, null);

	}

	@Override
	public void add(ConfigTypes configType) {
		String _code = getCodeOf(configType);
		ConfigTypes existing = this.findByCode(_code);
		if (existing == null) {
			synchronized (staticConfigs) {
				staticConfigs.add(configType);
			}
		} else
			refreshEntry(configType);
	}

	@Override
	public ConfigTypes removeByCode(String code) {
		synchronized (staticConfigs) {
			int index = -1;
			for (int i = 0; i < staticConfigs.size(); i++) {
				ConfigTypes entry = staticConfigs.get(i);
				String _code = getCodeOf(entry);
				if (_code != null && code != null && _code.equals(code)) {
					index = i;
					break;
				}

			}
			return index < 0 ? null : staticConfigs.remove(index);
		}
	}

	@Override
	public void refreshEntry(ConfigTypes configType) {
		String code = getCodeOf(configType);
		synchronized (staticConfigs) {
			int index = -1;
			for (int i = 0; i < staticConfigs.size(); i++) {
				ConfigTypes entry = staticConfigs.get(i);
				String _code = getCodeOf(entry);
				if (_code != null && code != null && _code.equals(code)) {
					index = i;
					break;
				}

			}
			if (index >= 0) {
				staticConfigs.set(index, configType);
			} else {
				staticConfigs.add(configType);
			}
		}
	}

	@Override
	public void remove(ConfigTypes configType) {
		boolean foundByReference = false;
		synchronized (staticConfigs) {
			int index = -1;
			for (int i = 0; i < staticConfigs.size(); i++) {
				ConfigTypes entry = staticConfigs.get(i);
				foundByReference = entry == configType;
				if (foundByReference) {
					index = i;
					break;
				}

			}
			if (foundByReference) {
				staticConfigs.remove(index);
			} else {
				String _code = getCodeOf(configType);
				removeByCode(_code);
			}
		}
	}

}
