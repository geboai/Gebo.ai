/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.graphrag.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.mock.env.MockEnvironment;

/**
 * Pins that the knowledge graph is read and indexed on the properties it is written
 * with. Spring Data Neo4j writes each node with the field names of its
 * {@code @Node} class ({@code GraphDocumentReference} carries {@code code},
 * {@code knowledgeBaseCode}, {@code projectCode}...), so a hand-written query or a DDL
 * index naming any other property silently matches nothing: the event-alias search
 * filtered on {@code knowledgebase_code} and found no chunk in a knowledge-base scoped
 * search, and the document-reference indexes covered properties no node has.
 */
class GraphQueriesMatchTheNodeModelTest {
	private static final String MODEL_PACKAGE = "ai.gebo.architecture.graphrag.persistence.model";
	private static final String REPOSITORIES_PACKAGE = "ai.gebo.architecture.graphrag.persistence.repositories";
	/** A node bound in a pattern: {@code (alias:label)}. */
	private static final Pattern BINDING = Pattern.compile("\\((\\w+):(\\w+)\\)");
	/** A property read on a bound alias: {@code alias.property}. */
	private static final Pattern PROPERTY = Pattern.compile("\\b(\\w+)\\.(\\w+)\\b");
	/** A DDL index: {@code FOR (n:label) ON (n.a, n.b)}. */
	private static final Pattern INDEX = Pattern.compile(
			"CREATE INDEX\\s+\\w+\\s+IF NOT EXISTS\\s+FOR\\s+\\((\\w+):(\\w+)\\)\\s+ON\\s+\\(([^)]*)\\)");

	private static Map<String, Set<String>> propertiesByLabel() throws Exception {
		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
		scanner.addIncludeFilter(new AnnotationTypeFilter(Node.class));
		Map<String, Set<String>> out = new HashMap<>();
		for (BeanDefinition definition : scanner.findCandidateComponents(MODEL_PACKAGE)) {
			Class<?> type = Class.forName(definition.getBeanClassName());
			Node node = type.getAnnotation(Node.class);
			String label = node.value().length > 0 ? node.value()[0] : node.primaryLabel();
			Set<String> properties = new HashSet<>();
			for (Class<?> c = type; c != null && c != Object.class; c = c.getSuperclass()) {
				for (Field field : c.getDeclaredFields()) {
					properties.add(field.getName());
				}
			}
			out.put(label, properties);
		}
		return out;
	}

	private static List<String> repositoryQueries() throws Exception {
		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false) {
			@Override
			protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
				return beanDefinition.getMetadata().isInterface();
			}
		};
		// the repositories are conditional on the knowledge graph being deployed
		scanner.setEnvironment(new MockEnvironment().withProperty("ai.gebo.neo4j.enabled", "true"));
		scanner.addIncludeFilter((reader, factory) -> true);
		List<String> out = new ArrayList<>();
		for (BeanDefinition definition : scanner.findCandidateComponents(REPOSITORIES_PACKAGE)) {
			for (Method method : Class.forName(definition.getBeanClassName()).getDeclaredMethods()) {
				Query query = method.getAnnotation(Query.class);
				if (query != null) {
					out.add(query.value());
				}
			}
		}
		return out;
	}

	@Test
	void everyQueryReadsTheNodesOnThePropertiesTheyAreWrittenWith() throws Exception {
		Map<String, Set<String>> model = propertiesByLabel();
		List<String> queries = repositoryQueries();
		assertTrue(queries.size() >= 6, "the repository queries are found: " + queries.size());
		List<String> unknown = new ArrayList<>();
		for (String query : queries) {
			Map<String, String> labels = new HashMap<>();
			Matcher binding = BINDING.matcher(query);
			while (binding.find()) {
				labels.put(binding.group(1), binding.group(2));
			}
			Matcher property = PROPERTY.matcher(query);
			while (property.find()) {
				String label = labels.get(property.group(1));
				Set<String> properties = label != null ? model.get(label) : null;
				if (properties != null && !properties.contains(property.group(2))) {
					unknown.add(label + "." + property.group(2));
				}
			}
		}
		assertEquals(List.of(), unknown, "properties no node is written with");
	}

	@Test
	void everyIndexCoversPropertiesTheNodesAreWrittenWith() throws Exception {
		Map<String, Set<String>> model = propertiesByLabel();
		String ddl;
		try (InputStream in = getClass().getResourceAsStream("/neo4j-graphrag-ddl/knowledge-model.ddl")) {
			ddl = new String(in.readAllBytes(), StandardCharsets.UTF_8);
		}
		List<String> unknown = new ArrayList<>();
		int indexes = 0;
		Matcher index = INDEX.matcher(ddl);
		while (index.find()) {
			indexes++;
			String alias = index.group(1);
			Set<String> properties = model.get(index.group(2));
			for (String indexed : index.group(3).split(",")) {
				String name = indexed.trim().substring(alias.length() + 1);
				if (properties == null || !properties.contains(name)) {
					unknown.add(index.group(2) + "." + name);
				}
			}
		}
		assertTrue(indexes >= 3, "the document reference indexes are found: " + indexes);
		assertEquals(List.of(), unknown, "indexed properties no node is written with");
	}
}
