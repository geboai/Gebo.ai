package ai.gebo.architecture.search.service.impl;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.icu.ICUFoldingFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.search.service.IKeywordMatcherService;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ChunkKeywordGateService: - Normalizza testo e keyword con ICU
 * (language-agnostic-ish) - Tokenizza e fa match su token set - Supporta
 * keyword singole e frasi (multiword): match se tutti i token della frase sono
 * presenti
 */
@Service
public class KeywordMatcherServiceImpl implements IKeywordMatcherService {

	/**
	 * Analyzer minimale: tokenizzazione su whitespace + ICU folding. Nota: se vuoi
	 * tokenizzare anche su punteggiatura, puoi usare StandardTokenizer al posto di
	 * WhitespaceTokenizer.
	 */
	private final Analyzer analyzer = new Analyzer() {
		@Override
		protected TokenStreamComponents createComponents(String fieldName) {
			WhitespaceTokenizer tokenizer = new WhitespaceTokenizer();
			TokenStream ts = new ICUFoldingFilter(tokenizer);
			return new TokenStreamComponents(tokenizer, ts);
		}
	};

	/**
	 * Cache per non rianalizzare sempre le stesse keyword (utile se le keyword si
	 * ripetono dentro la stessa request o across request). La chiave è la keyword
	 * "raw".
	 */
	private final Map<String, List<String>> keywordTokenCache = new ConcurrentHashMap<>();

	/**
	 * Parametri di gating: - minHits: quante keyword devono matchare per
	 * considerare il chunk (se vuoi "almeno una keyword", metti 1)
	 */
	public boolean isMatching(List<String> generatedKeywords, String chunkText) {
		return isMatching(generatedKeywords, chunkText, 1);
	}

	public boolean isMatching(List<String> generatedKeywords, String chunkText, int minHits) {
		if (chunkText == null || chunkText.isBlank())
			return false;
		if (generatedKeywords == null || generatedKeywords.isEmpty())
			return true; // se non ho keyword, non filtro

		// 1) tokenizza+normalizza chunk -> set
		Set<String> chunkTokens = analyzeToTokenSet(chunkText);
		if (chunkTokens.isEmpty())
			return false;

		int hits = 0;

		// 2) per ogni keyword/frase: normalizza in token e matcha
		for (String kw : generatedKeywords) {
			if (kw == null)
				continue;
			String trimmed = kw.trim();
			if (trimmed.isEmpty())
				continue;

			List<String> kwTokens = keywordTokenCache.computeIfAbsent(trimmed, k -> analyzeToTokenList(k));

			if (kwTokens.isEmpty())
				continue;

			// keyword singola: match diretto
			if (kwTokens.size() == 1) {
				if (chunkTokens.contains(kwTokens.get(0))) {
					hits++;
				}
			} else {
				// frase: match se tutti i token sono presenti (approccio robusto, veloce)
				boolean allPresent = true;
				for (String t : kwTokens) {
					if (!chunkTokens.contains(t)) {
						allPresent = false;
						break;
					}
				}
				if (allPresent)
					hits++;
			}

			if (hits >= minHits)
				return true; // early exit
		}

		return false;
	}

	private Set<String> analyzeToTokenSet(String text) {
		List<String> tokens = analyzeToTokenList(text);
		if (tokens.isEmpty())
			return Collections.emptySet();
		// HashSet dimensionato per ridurre rehash
		Set<String> set = new HashSet<>(Math.max(16, (int) (tokens.size() / 0.75f) + 1));
		set.addAll(tokens);
		return set;
	}

	private List<String> analyzeToTokenList(String text) {
		try (TokenStream ts = analyzer.tokenStream("f", new StringReader(text))) {
			CharTermAttribute term = ts.addAttribute(CharTermAttribute.class);
			ts.reset();

			ArrayList<String> out = new ArrayList<>(64);
			while (ts.incrementToken()) {
				String tok = term.toString();
				// piccole pulizie aggiuntive
				if (tok.isBlank())
					continue;
				out.add(tok);
			}
			ts.end();
			return out;
		} catch (IOException e) {
			// StringReader non dovrebbe lanciare IOException in pratica, ma gestiamo
			// comunque.
			return Collections.emptyList();
		}
	}
}