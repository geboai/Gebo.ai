package ai.gebo.architecture.graphrag.services.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class Neo4jDdlRunner {

	@Value("classpath:neo4j-graphrag-ddl/knowledge-model.ddl")
	private Resource ddl;

	private final Neo4jClient neo4jClient;

	public Neo4jDdlRunner(Neo4jClient neo4jClient) {
		this.neo4jClient = neo4jClient;
	}

	@Bean
	ApplicationRunner runNeo4jDdl() {
		return args -> executeDdl();
	}

	@Transactional // usa la tx Spring (SDN)
	public void executeDdl() throws IOException {
		String script = new String(ddl.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

		// Semplice splitter su ';' (ignora righe vuote/commenti).
		// Consiglio: separa i comandi con ';' su una riga propria o usa delimitatori
		// '-- #stmt'.
		Arrays.stream(script.split("(?m);\\s*$")).map(String::trim)
				.filter(s -> !s.isBlank() && !s.startsWith("//") && !s.startsWith("--"))
				.forEach(stmt -> neo4jClient.query(stmt).run());
	}
}
