package ai.gebo.architecture.search.service.impl;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.icu.ICUFoldingFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
 * <p>
 * The words of a keyword that tell nothing of a text (articles, prepositions...) are
 * ignored: a keyword made of them alone would match nearly every chunk. They are the
 * stop words of the languages the caller names (the chunk's, the keywords'), or of
 * the fallback languages when it names none it knows.
 */
@Service
public class KeywordMatcherServiceImpl implements IKeywordMatcherService {
	private static final Logger LOGGER = LoggerFactory.getLogger(KeywordMatcherServiceImpl.class);
	public static final String FALLBACK_LANGUAGES_PROPERTY = "ai.gebo.search.keyword-stop-words.fallback-languages";
	public static final List<String> DEFAULT_FALLBACK_LANGUAGES = List.of("it", "en");

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

	/** The languages whose stop words apply when the caller names none it knows. */
	private final Set<String> fallbackLanguages;

	/** Each language's stop words, folded as the texts are. */
	private final Map<String, Set<String>> stopWordsByLanguage = new ConcurrentHashMap<>();

	/**
	 * Cache per non rianalizzare sempre le stesse keyword (utile se le keyword si
	 * ripetono dentro la stessa request o across request). La chiave è la keyword
	 * "raw".
	 */
	private final Map<String, List<String>> keywordTokenCache = new ConcurrentHashMap<>();

	public KeywordMatcherServiceImpl() {
		this(DEFAULT_FALLBACK_LANGUAGES);
	}

	@Autowired
	public KeywordMatcherServiceImpl(
			@Value("${" + FALLBACK_LANGUAGES_PROPERTY + ":it,en}") List<String> fallbackLanguages) {
		this.fallbackLanguages = languages(fallbackLanguages);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Keyword matcher fallback stop word languages:" + this.fallbackLanguages + " (configured:"
					+ fallbackLanguages + ")");
		}
	}

	/**
	 * Parametri di gating: - minHits: quante keyword devono matchare per
	 * considerare il chunk (se vuoi "almeno una keyword", metti 1)
	 */
	public boolean isMatching(List<String> generatedKeywords, String chunkText) {
		return isMatching(generatedKeywords, chunkText, 1);
	}

	public boolean isMatching(List<String> generatedKeywords, String chunkText, int minHits) {
		return isMatching(generatedKeywords, chunkText, minHits, List.of());
	}

	@Override
	public boolean isMatching(List<String> generatedKeywords, String chunkText, int minHits,
			Collection<String> languages) {
		if (chunkText == null || chunkText.isBlank())
			return false;
		if (generatedKeywords == null || generatedKeywords.isEmpty())
			return true; // se non ho keyword, non filtro

		// 1) tokenizza+normalizza chunk -> set
		Set<String> chunkTokens = analyzeToTokenSet(chunkText);
		if (chunkTokens.isEmpty())
			return false;

		// 2) the keywords that tell something of a text, as tokens: without the stop
		// words of the languages named, or of the fallback ones
		final Set<String> known = languages(languages);
		final Set<String> applied = known.isEmpty() ? fallbackLanguages : known;
		final Set<String> stopWords = stopWords(applied);
		final List<List<String>> informative = new ArrayList<>(generatedKeywords.size());
		for (String kw : generatedKeywords) {
			if (kw == null)
				continue;
			String trimmed = kw.trim();
			if (trimmed.isEmpty())
				continue;
			final List<String> kwTokens = new ArrayList<>(
					keywordTokenCache.computeIfAbsent(trimmed, k -> analyzeToTokenList(k)));
			kwTokens.removeIf(stopWords::contains);
			if (!kwTokens.isEmpty())
				informative.add(kwTokens);
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("isMatching(...) stop words of " + applied + (known.isEmpty() ? " (fallback)" : "")
					+ " leave the keywords " + informative);
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

	/** The known languages among the codes given, normalized. */
	private static Set<String> languages(Collection<String> codes) {
		final Set<String> known = new LinkedHashSet<>();
		if (codes != null) {
			for (String code : codes) {
				final String language = KeywordStopWords.normalized(code);
				if (language != null) {
					known.add(language);
				}
			}
		}
		return known;
	}

	/** The stop words of the given languages, folded. */
	private Set<String> stopWords(Set<String> languages) {
		if (languages.size() == 1) {
			return stopWordsOf(languages.iterator().next());
		}
		final Set<String> union = new HashSet<>();
		for (String language : languages) {
			union.addAll(stopWordsOf(language));
		}
		return union;
	}

	private Set<String> stopWordsOf(String language) {
		return stopWordsByLanguage.computeIfAbsent(language, key -> {
			final Set<String> folded = new HashSet<>();
			final CharArraySet set = KeywordStopWords.of(key);
			if (set != null) {
				for (Object word : set) {
					folded.addAll(
							analyzeToTokenList(word instanceof char[] chars ? new String(chars) : String.valueOf(word)));
				}
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Keyword matcher loaded " + folded.size() + " stop word(s) of language:" + key);
			}
			return Collections.unmodifiableSet(folded);
		});
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
