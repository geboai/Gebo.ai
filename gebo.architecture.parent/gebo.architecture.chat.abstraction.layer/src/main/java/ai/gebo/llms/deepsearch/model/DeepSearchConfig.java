package ai.gebo.llms.deepsearch.model;

import java.util.ArrayList;
import java.util.List;

import ai.gebo.architecture.rag.support.layer.model.RagQueryOptions;
import ai.gebo.model.IGObjectWithSecurity;
import ai.gebo.model.base.GBaseObject;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeepSearchConfig extends GBaseObject implements IGObjectWithSecurity {
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class DeepSearchDataSourceAccess implements IGObjectWithSecurity {
		// List of group IDs or names that have access to this project.
		private List<String> accessibleGroups = null;
		// List of user IDs or usernames that have access to this project.
		private List<String> accessibleUsers = null;
		// Indicates whether the project is accessible to all users.
		private Boolean accessibleToAll = null;
		private String dataSourceId = null;

		public String owner() {
			return "anonymous";
		}
	}

	public static enum SearchType {
		SINGLE_HOP, MULTI_HOP
	}

	@NotNull
	protected SearchType searchType = null;

	protected RagQueryOptions ragQueryOptions;
	protected Double firstHopSimilarityThreashold = null;
	protected Double secondHopSimilarityThreashold = null;
	protected Integer graphRagTopN = null;
	protected Integer tokensLimit = null;
	protected Boolean manualThreasholdsConfiguration = null;
	protected Boolean defaultConfig = null;
	// List of group IDs or names that have access to this project.
	private List<String> accessibleGroups = null;
	// List of user IDs or usernames that have access to this project.
	private List<String> accessibleUsers = null;
	// Indicates whether the project is accessible to all users.
	private Boolean accessibleToAll = null;
	private List<DeepSearchDataSourceAccess> dataSourcesAccesses = new ArrayList<>();
	private Boolean perDataSourceConfigured=null;
	public DeepSearchConfig(DeepSearchConfig c) {
		this(c.searchType, c.ragQueryOptions, c.firstHopSimilarityThreashold, c.secondHopSimilarityThreashold,
				c.graphRagTopN, c.tokensLimit, c.manualThreasholdsConfiguration, c.defaultConfig, c.accessibleGroups,
				c.accessibleUsers, c.accessibleToAll, c.dataSourcesAccesses, c.perDataSourceConfigured);
		this.setCode(c.getCode());
		this.setDescription(c.getDescription());
	}

}
