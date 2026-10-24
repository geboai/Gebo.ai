/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.ai.app.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode;

import ai.gebo.monolithic.app.Main;
import ai.gebo.webconfig.JacksonConfig;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

/**
 * How the monolith writes a page of results: as its API description says, with the
 * total under {@code page}, which is where the user interface's paginators and the
 * generated clients read it.
 */
class PageJsonShapeTest {

	/** The page serialization the monolith's {@link Main} asks for. */
	@Configuration
	@EnableSpringDataWebSupport(pageSerializationMode = PageSerializationMode.VIA_DTO)
	static class AsTheMonolith {
	}

	@Test
	void theMonolithAsksForPagesAsDtos() {
		assertThat(Main.class.getAnnotation(EnableSpringDataWebSupport.class).pageSerializationMode())
				.isEqualTo(PageSerializationMode.VIA_DTO);
	}

	/**
	 * The application's own mapper replaces Spring Boot's, so Spring Data's module has
	 * to reach it: without it the page goes out as a bare {@code PageImpl}, its total
	 * at the root, and every paginator reads none.
	 */
	@Test
	void aPageIsWrittenWithItsTotalUnderPage() {
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AsTheMonolith.class,
				JacksonConfig.class)) {
			JsonMapper mapper = context.getBean(JsonMapper.class);

			JsonNode json = mapper.readTree(mapper.writeValueAsString(new PageImpl<>(List.of("a", "b"), PageRequest.of(1, 2), 5)));

			assertThat(json.get("content")).hasSize(2);
			assertThat(json.at("/page/totalElements").asLong()).isEqualTo(5);
			assertThat(json.at("/page/totalPages").asInt()).isEqualTo(3);
			assertThat(json.at("/page/number").asInt()).isEqualTo(1);
			assertThat(json.at("/page/size").asInt()).isEqualTo(2);
			assertThat(json.has("totalElements")).as("the total at the root, as a bare PageImpl has it").isFalse();
			assertThat(json.has("pageable")).isFalse();
		}
	}

	/** The application's own date handling still applies next to the modules. */
	@Test
	void datesAreStillReadInTheFormatsTheApplicationAccepts() {
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AsTheMonolith.class,
				JacksonConfig.class)) {
			JsonMapper mapper = context.getBean(JsonMapper.class);

			Date legacy = mapper.readValue("\"2026-01-09 07:37:23\"", Date.class);
			Date iso = mapper.readValue("\"2026-01-09T07:37:23Z\"", Date.class);

			assertThat(legacy).isEqualTo(iso);
		}
	}
}
