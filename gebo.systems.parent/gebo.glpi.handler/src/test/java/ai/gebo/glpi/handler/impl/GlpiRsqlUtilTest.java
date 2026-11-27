/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.glpi.handler.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.glpi.search.api.GlpiSearchFilter;
import ai.gebo.glpi.search.api.GlpiSearchFilter.GlpiItemKind;
import ai.gebo.glpi.search.api.GlpiSearchFilter.GlpiTicketStatus;
import ai.gebo.glpi.search.api.GlpiSearchFilter.GlpiTicketType;
import ai.gebo.glpi.search.api.GlpiSearchFilter.TextMatchMode;

class GlpiRsqlUtilTest {

	@Test
	void anEntityListingExcludesTheTrash() {
		assertThat(GlpiRsqlUtil.ticketsOfEntity(4)).isEqualTo("entity.id==4;is_deleted==false");
		assertThat(GlpiRsqlUtil.ofEntity(4)).isEqualTo("entity.id==4");
	}

	@Test
	void wordsKeepOnlyLettersAndDigits() {
		assertThat(GlpiRsqlUtil.words("VPN \"drops\"; (every) 5 min, l'accès=*"))
				.containsExactly("VPN", "drops", "every", "5", "min", "l", "accès");
	}

	@Test
	void aTextSearchNeedsEveryWordInTitleOrContent() {
		assertThat(GlpiRsqlUtil.ticketsTextFilter("printer offline")).isEqualTo(
				"is_deleted==false;((name=ilike=*printer*,content=ilike=*printer*);(name=ilike=*offline*,content=ilike=*offline*))");
		assertThat(GlpiRsqlUtil.kbArticlesTextFilter("vpn")).isEqualTo("(name=ilike=*vpn*,content=ilike=*vpn*)");
		assertThat(GlpiRsqlUtil.kbArticlesTextFilter("!!")).isNull();
		assertThat(GlpiRsqlUtil.ticketsTextFilter("")).isEqualTo("is_deleted==false");
	}

	@Test
	void aNativeFilterTranslatesEveryCriterion() {
		GlpiSearchFilter filter = new GlpiSearchFilter();
		filter.setEntityIds(List.of(1L, 2L));
		filter.setTicketIds(List.of(1042L));
		filter.setTitleTerms(List.of("printer", "paper jam"));
		filter.setTitleTermsMatchMode(TextMatchMode.ANY);
		filter.setContentTerms(List.of("toner"));
		filter.setTicketStatuses(List.of(GlpiTicketStatus.NEW, GlpiTicketStatus.PENDING, GlpiTicketStatus.APPROVAL));
		filter.setTicketTypes(List.of(GlpiTicketType.INCIDENT));
		filter.setPriorities(List.of(5, 6));
		filter.setCategoryNames(List.of("Hardware"));
		filter.setCreatedFrom("2026-01-01");
		filter.setCreatedTo("2026-01-31");
		assertThat(GlpiRsqlUtil.ticketsFilter(filter)).isEqualTo("is_deleted==false;entity.id=in=(1,2);id=in=(1042);"
				+ "(name=ilike=*printer*,(name=ilike=*paper*;name=ilike=*jam*));content=ilike=*toner*;"
				+ "status.id=in=(1,4,10);type=in=(1);priority=in=(5,6);category.name=ilike=*Hardware*;"
				+ "date_creation=ge=2026-01-01;date_creation=lt=2026-02-01");
	}

	@Test
	void allTermsAreAndedWhenAsked() {
		GlpiSearchFilter filter = new GlpiSearchFilter();
		filter.setContentTerms(List.of("vpn", "timeout"));
		filter.setContentTermsMatchMode(TextMatchMode.ALL);
		assertThat(GlpiRsqlUtil.kbArticlesFilter(filter)).isEqualTo("(content=ilike=*vpn*;content=ilike=*timeout*)");
	}

	@Test
	void anEmptyFilterReadsEverything() {
		GlpiSearchFilter filter = new GlpiSearchFilter();
		assertThat(GlpiRsqlUtil.ticketsFilter(filter)).isEqualTo("is_deleted==false");
		assertThat(GlpiRsqlUtil.kbArticlesFilter(filter)).isNull();
		assertThat(GlpiRsqlUtil.searchesTickets(filter)).isTrue();
		assertThat(GlpiRsqlUtil.searchesKbArticles(filter)).isTrue();
	}

	@Test
	void ticketOnlyCriteriaKeepTheKnowledgeBaseOut() {
		GlpiSearchFilter filter = new GlpiSearchFilter();
		filter.setTicketStatuses(List.of(GlpiTicketStatus.SOLVED));
		assertThat(GlpiRsqlUtil.searchesKbArticles(filter)).isFalse();
		filter.setItemKinds(List.of(GlpiItemKind.KB_ARTICLE));
		assertThat(GlpiRsqlUtil.searchesKbArticles(filter)).isTrue();
		assertThat(GlpiRsqlUtil.searchesTickets(filter)).isFalse();
	}

	@Test
	void invalidDatesAreIgnored() {
		GlpiSearchFilter filter = new GlpiSearchFilter();
		filter.setCreatedFrom("last week");
		filter.setCreatedTo("2026-03-01T10:00:00Z");
		assertThat(GlpiRsqlUtil.kbArticlesFilter(filter)).isEqualTo("date_creation=lt=2026-03-02");
	}
}
