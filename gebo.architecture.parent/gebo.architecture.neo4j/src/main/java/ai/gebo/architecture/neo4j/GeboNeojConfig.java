package ai.gebo.architecture.neo4j;

import org.neo4j.cypherdsl.core.renderer.Configuration;
import org.neo4j.cypherdsl.core.renderer.Dialect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
/**
 * Neo4j module configuration, and the single place Neo4j repository scanning is
 * switched on.
 *
 * <p>
 * {@code @EnableNeo4jRepositories} lives HERE rather than on each application
 * class because it has to follow the same flag as everything else neo4j. The
 * ~27 beans of the graphrag stack are gated on
 * {@code ai.gebo.neo4j.enabled}, but they are not all the same kind: the graph
 * SERVICES are components the flag creates, while the graph REPOSITORIES are
 * Spring Data interfaces that only exist if a repository scanner registers
 * them. While this annotation sat only on the monolith's Main, enabling the
 * flag on any other deployable brought the services up without the
 * repositories they inject and the context died at startup on
 * GraphDocumentReferenceRepository. Gated here, the scanner and the beans that
 * need it can no longer drift apart - and with the flag off nothing neo4j is
 * registered at all.
 * </p>
 */
@ConditionalOnProperty(prefix = "ai.gebo.neo4j", name = "enabled", havingValue = "true")
@EnableNeo4jRepositories(basePackages = "ai.gebo")
@org.springframework.context.annotation.Configuration
public class GeboNeojConfig {

	@Bean
	public Configuration cypherDslConfiguration() {
		return Configuration.newConfig().withDialect(Dialect.NEO4J_5).build();
	}

}
