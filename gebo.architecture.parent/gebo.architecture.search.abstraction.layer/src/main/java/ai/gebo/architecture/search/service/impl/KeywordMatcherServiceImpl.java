package ai.gebo.architecture.search.service.impl;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.icu.ICUFoldingFilter;
import org.apache.lucene.analysis.it.ItalianAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger LOGGER = LoggerFactory.getLogger(KeywordMatcherServiceImpl.class);

	/**
	 * Words split on punctuation too ("contract," is the word "contract"), then case
	 * and accent folded (ICU).
	 */
	private final Analyzer analyzer = new Analyzer() {
		@Override
		protected TokenStreamComponents createComponents(String fieldName) {
			StandardTokenizer tokenizer = new StandardTokenizer();
			TokenStream ts = new ICUFoldingFilter(tokenizer);
			return new TokenStreamComponents(tokenizer, ts);
		}
	};

	/**
	 * Words that tell nothing of a text (articles, prepositions...), folded as the text
	 * is: a keyword made of them alone would match nearly every chunk. Italian and
	 * English, the languages of the knowledge bases and of the models' queries.
	 */
	private final Set<String> stopWords = foldedStopWords();

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

		// 2) the keywords that tell something of a text, as tokens
		final List<List<String>> informative = new ArrayList<>(generatedKeywords.size());
		for (String kw : generatedKeywords) {
			if (kw == null)
				continue;
			String trimmed = kw.trim();
			if (trimmed.isEmpty())
				continue;
			List<String> kwTokens = keywordTokenCache.computeIfAbsent(trimmed, k -> informativeTokens(k));
			if (!kwTokens.isEmpty())
				informative.add(kwTokens);
		}
		if (informative.isEmpty()) {
			// only words that tell nothing: no keyword to filter by, as with no keyword at all
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("isMatching(...) keywords " + generatedKeywords + " are all stop words, the chunk is kept");
			}
			return true;
		}
		// hits asked counting every keyword (e.g. two of more than three): never more than
		// the informative ones, or no chunk could ever match
		final int requiredHits = Math.max(1, Math.min(minHits, informative.size()));

		// 3) a single keyword matches its token, a phrase all of its tokens
		int hits = 0;
		for (List<String> kwTokens : informative) {
			boolean allPresent = true;
			for (String t : kwTokens) {
				if (!chunkTokens.contains(t)) {
					allPresent = false;
					break;
				}
			}
			if (allPresent)
				hits++;
			if (hits >= requiredHits)
				return true; // early exit
		}
		return false;
	}

	/** The tokens of a keyword that tell something of a text. */
	private List<String> informativeTokens(String keyword) {
		final List<String> tokens = new ArrayList<>(analyzeToTokenList(keyword));
		tokens.removeIf(stopWords::contains);
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("informativeTokens(...) keyword '" + keyword + "' matches on " + tokens);
		}
		return tokens;
	}

	private Set<String> foldedStopWords() {
		final Set<String> folded = new HashSet<>();
		for (CharArraySet set : List.of(ItalianAnalyzer.getDefaultStopSet(), EnglishAnalyzer.ENGLISH_STOP_WORDS_SET)) {
			for (Object word : set) {
				folded.addAll(analyzeToTokenList(word instanceof char[] chars ? new String(chars) : String.valueOf(word)));
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Keyword matcher ignores " + folded.size() + " stop word(s)");
		}
		return folded;
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