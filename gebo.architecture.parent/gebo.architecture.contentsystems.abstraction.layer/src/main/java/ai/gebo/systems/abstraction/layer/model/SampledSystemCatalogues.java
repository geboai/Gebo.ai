package ai.gebo.systems.abstraction.layer.model;

import java.util.ArrayList;
import java.util.List;

import ai.gebo.architecture.search.model.CatalogueSample;
import ai.gebo.model.base.GBaseObject;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Persisted, periodically refreshed snapshot of the catalogues sampled from a
 * single configured searchable system. It is the cache backing
 * {@link ai.gebo.architecture.search.service.ISearchService#getCachedCatalogues(String)}
 * for the content/virtual-filesystem search services, keyed by the messaging
 * module/system plus the system configuration code.
 */
@Data
public class SampledSystemCatalogues extends GBaseObject {
	private static final String SEPARATOR = "-|-";
	@NotNull
	private String messagingModuleId = null;
	@NotNull
	private String messagingSystemId = null;
	@NotNull
	private String handlerId = null;
	@NotNull
	private String systemConfigurationCode = null;
	@NotNull
	@NotEmpty
	private List<CatalogueSample> catalogs = new ArrayList<CatalogueSample>();

	public void recalculateCode() {
		this.setCode(messagingModuleId + SEPARATOR + messagingSystemId + SEPARATOR + systemConfigurationCode);
	}
}
