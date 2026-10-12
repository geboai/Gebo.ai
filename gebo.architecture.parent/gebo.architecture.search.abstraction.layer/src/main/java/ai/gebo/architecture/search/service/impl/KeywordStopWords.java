/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.search.service.impl;

import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.ar.ArabicAnalyzer;
import org.apache.lucene.analysis.bg.BulgarianAnalyzer;
import org.apache.lucene.analysis.bn.BengaliAnalyzer;
import org.apache.lucene.analysis.ca.CatalanAnalyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.ckb.SoraniAnalyzer;
import org.apache.lucene.analysis.cz.CzechAnalyzer;
import org.apache.lucene.analysis.da.DanishAnalyzer;
import org.apache.lucene.analysis.de.GermanAnalyzer;
import org.apache.lucene.analysis.el.GreekAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.analysis.et.EstonianAnalyzer;
import org.apache.lucene.analysis.eu.BasqueAnalyzer;
import org.apache.lucene.analysis.fa.PersianAnalyzer;
import org.apache.lucene.analysis.fi.FinnishAnalyzer;
import org.apache.lucene.analysis.fr.FrenchAnalyzer;
import org.apache.lucene.analysis.ga.IrishAnalyzer;
import org.apache.lucene.analysis.gl.GalicianAnalyzer;
import org.apache.lucene.analysis.hi.HindiAnalyzer;
import org.apache.lucene.analysis.hu.HungarianAnalyzer;
import org.apache.lucene.analysis.hy.ArmenianAnalyzer;
import org.apache.lucene.analysis.id.IndonesianAnalyzer;
import org.apache.lucene.analysis.it.ItalianAnalyzer;
import org.apache.lucene.analysis.lt.LithuanianAnalyzer;
import org.apache.lucene.analysis.lv.LatvianAnalyzer;
import org.apache.lucene.analysis.ne.NepaliAnalyzer;
import org.apache.lucene.analysis.nl.DutchAnalyzer;
import org.apache.lucene.analysis.no.NorwegianAnalyzer;
import org.apache.lucene.analysis.pt.PortugueseAnalyzer;
import org.apache.lucene.analysis.ro.RomanianAnalyzer;
import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.analysis.sr.SerbianAnalyzer;
import org.apache.lucene.analysis.sv.SwedishAnalyzer;
import org.apache.lucene.analysis.ta.TamilAnalyzer;
import org.apache.lucene.analysis.te.TeluguAnalyzer;
import org.apache.lucene.analysis.th.ThaiAnalyzer;
import org.apache.lucene.analysis.tr.TurkishAnalyzer;

/**
 * The stop words Lucene ships, by the language codes the ingestion's language
 * detector (Tika, ISO 639-1, sometimes with a region as "zh-CN") writes on the
 * documents. Lucene's packages mostly follow the same codes, but not all of them:
 * Czech is "cz", Chinese, Japanese and Korean share "cjk", and "br" is Brazilian
 * Portuguese in Lucene while it is Breton in ISO 639-1, so it is not mapped.
 */
final class KeywordStopWords {

	private static final Map<String, Supplier<CharArraySet>> BY_LANGUAGE = Map.ofEntries(
			Map.entry("ar", ArabicAnalyzer::getDefaultStopSet), Map.entry("bg", BulgarianAnalyzer::getDefaultStopSet),
			Map.entry("bn", BengaliAnalyzer::getDefaultStopSet), Map.entry("ca", CatalanAnalyzer::getDefaultStopSet),
			Map.entry("zh", CJKAnalyzer::getDefaultStopSet), Map.entry("ja", CJKAnalyzer::getDefaultStopSet),
			Map.entry("ko", CJKAnalyzer::getDefaultStopSet), Map.entry("ckb", SoraniAnalyzer::getDefaultStopSet),
			Map.entry("cs", CzechAnalyzer::getDefaultStopSet), Map.entry("da", DanishAnalyzer::getDefaultStopSet),
			Map.entry("de", GermanAnalyzer::getDefaultStopSet), Map.entry("el", GreekAnalyzer::getDefaultStopSet),
			Map.entry("en", EnglishAnalyzer::getDefaultStopSet), Map.entry("es", SpanishAnalyzer::getDefaultStopSet),
			Map.entry("et", EstonianAnalyzer::getDefaultStopSet), Map.entry("eu", BasqueAnalyzer::getDefaultStopSet),
			Map.entry("fa", PersianAnalyzer::getDefaultStopSet), Map.entry("fi", FinnishAnalyzer::getDefaultStopSet),
			Map.entry("fr", FrenchAnalyzer::getDefaultStopSet), Map.entry("ga", IrishAnalyzer::getDefaultStopSet),
			Map.entry("gl", GalicianAnalyzer::getDefaultStopSet), Map.entry("hi", HindiAnalyzer::getDefaultStopSet),
			Map.entry("hu", HungarianAnalyzer::getDefaultStopSet), Map.entry("hy", ArmenianAnalyzer::getDefaultStopSet),
			Map.entry("id", IndonesianAnalyzer::getDefaultStopSet), Map.entry("it", ItalianAnalyzer::getDefaultStopSet),
			Map.entry("lt", LithuanianAnalyzer::getDefaultStopSet), Map.entry("lv", LatvianAnalyzer::getDefaultStopSet),
			Map.entry("ne", NepaliAnalyzer::getDefaultStopSet), Map.entry("nl", DutchAnalyzer::getDefaultStopSet),
			Map.entry("no", NorwegianAnalyzer::getDefaultStopSet), Map.entry("nb", NorwegianAnalyzer::getDefaultStopSet),
			Map.entry("nn", NorwegianAnalyzer::getDefaultStopSet),
			Map.entry("pt", PortugueseAnalyzer::getDefaultStopSet), Map.entry("ro", RomanianAnalyzer::getDefaultStopSet),
			Map.entry("ru", RussianAnalyzer::getDefaultStopSet), Map.entry("sr", SerbianAnalyzer::getDefaultStopSet),
			Map.entry("sv", SwedishAnalyzer::getDefaultStopSet), Map.entry("ta", TamilAnalyzer::getDefaultStopSet),
			Map.entry("te", TeluguAnalyzer::getDefaultStopSet), Map.entry("th", ThaiAnalyzer::getDefaultStopSet),
			Map.entry("tr", TurkishAnalyzer::getDefaultStopSet));

	private KeywordStopWords() {
	}

	/**
	 * The language a detected code stands for in this table ("zh-CN" is "zh", "IT" is
	 * "it"), null when blank or not known.
	 */
	static String normalized(String languageCode) {
		if (languageCode == null || languageCode.isBlank()) {
			return null;
		}
		final String code = languageCode.trim().toLowerCase(Locale.ROOT);
		if (BY_LANGUAGE.containsKey(code)) {
			return code;
		}
		final int dash = code.indexOf('-');
		final String primary = dash > 0 ? code.substring(0, dash) : code;
		return BY_LANGUAGE.containsKey(primary) ? primary : null;
	}

	/** Lucene's stop words of a normalized language, null when it has none. */
	static CharArraySet of(String normalizedLanguage) {
		final Supplier<CharArraySet> stopSet = normalizedLanguage != null ? BY_LANGUAGE.get(normalizedLanguage) : null;
		return stopSet != null ? stopSet.get() : null;
	}
}
