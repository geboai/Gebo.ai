package ai.gebo.architecture.graphrag.extraction.repositories;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import ai.gebo.application.messaging.workflow.model.WorkflowContext;
import ai.gebo.architecture.graphrag.extraction.model.GraphRagExtractionConfig;
import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
@ConditionalOnProperty(prefix = "ai.gebo.neo4j", name = "enabled", havingValue = "true")
public interface GraphRagExtractionConfigRepository extends IGBaseMongoDBRepository<GraphRagExtractionConfig> {
	@Override
	default Class<GraphRagExtractionConfig> getManagedType() {
		return GraphRagExtractionConfig.class;
	}

	public List<GraphRagExtractionConfig> findByEndpointClassNameAndEndpointCode(String className, String code);

	public List<GraphRagExtractionConfig> findByKnowledgeBaseCode(String knowledgeBaseCode);

	public List<GraphRagExtractionConfig> findByKnowledgeBaseCodeAndProjectCodeIsNull(String knowledgeBaseCode);

	public List<GraphRagExtractionConfig> findByKnowledgeBaseCodeAndProjectCode(String knowledgeBaseCode,
			String projectCode);

	public List<GraphRagExtractionConfig> findByDefaultConfiguration(Boolean trueValue);

	public long countByEndpointClassNameAndEndpointCode(String className, String code);

	public long countByKnowledgeBaseCodeAndProjectCodeIsNull(String knowledgeBaseCode);

	public long countByKnowledgeBaseCodeAndProjectCode(String knowledgeBaseCode, String projectCode);

	public default boolean hasConfiguration(WorkflowContext context) {
		if (context == null)
			return countByDefaultConfiguration(true) > 0;
		return ((context.getKnowledgeBaseCode() != null
				&& countByKnowledgeBaseCodeAndProjectCodeIsNull(context.getKnowledgeBaseCode()) > 0)
				|| (context.getKnowledgeBaseCode() != null && context.getProjectCode() != null
						&& countByKnowledgeBaseCodeAndProjectCode(context.getKnowledgeBaseCode(),
								context.getProjectCode()) > 0)
				|| (context.getDataSource() != null && context.getDataSource().getClassName() != null
						&& context.getDataSource().getCode() != null
						&& countByEndpointClassNameAndEndpointCode(context.getDataSource().getClassName(),
								context.getDataSource().getCode()) > 0));
	}

	public long countByDefaultConfiguration(Boolean trueValue);

	public List<GraphRagExtractionConfig> findByEndpointCodeAndEndpointClassName(String code, String className);
}
