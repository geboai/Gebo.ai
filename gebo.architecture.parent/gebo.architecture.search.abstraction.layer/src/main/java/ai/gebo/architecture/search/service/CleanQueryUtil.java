package ai.gebo.architecture.search.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ai.gebo.architecture.search.model.SearchQuery;

/**
 * Utility per pulire le stringhe di ricerca generate dal LLM in modo che
 * rispettino i vincoli del query extractor:
 *
 * - solo lettere (anche accentate), cifre e spazi; - nessun operatore booleano
 * (AND, OR, NOT, &&, ||); - nessun carattere speciale: " ' + - * ? ~ : ; , . /
 * \ ( ) [ ] { } @ # $ % ^ & = < >
 */
public final class CleanQueryUtil {

	// Operatori testuali da rimuovere completamente (case-insensitive)
	private static final String[] FORBIDDEN_TOKENS = { "AND", "OR", "NOT"
			// puoi aggiungere altro tipo: "NEAR", "XOR", ecc.
	};

	private CleanQueryUtil() {
		// utility class, no instances
	}

	public static SearchQuery cleanQuery(SearchQuery query) {
		SearchQuery _query = new SearchQuery();
		_query.setQueryText(cleanQuery(query.getQueryText()));
		return _query;
	}

	/**
	 * Pulisce una singola query: - rimuove caratteri non ammessi (lascia solo
	 * lettere, cifre e spazi); - normalizza gli spazi (singoli, niente
	 * leading/trailing); - rimuove i token di operatori booleani (AND, OR, NOT, &&,
	 * ||).
	 *
	 * @param rawQuery query generata dal LLM (può essere null)
	 * @return query pulita, oppure null se l'input era null
	 */
	public static String cleanQuery(String rawQuery) {
		if (rawQuery == null) {
			return null;
		}

		// 1) Normalizza tutti gli spazi (tab, newline, ecc.) in spazi singoli
		// e filtra i caratteri non ammessi.
		StringBuilder sb = new StringBuilder(rawQuery.length());
		boolean lastWasSpace = false;

		for (int i = 0; i < rawQuery.length(); i++) {
			char c = rawQuery.charAt(i);

			// Considera qualsiasi whitespace come spazio " "
			if (Character.isWhitespace(c)) {
				if (!lastWasSpace) {
					sb.append(' ');
					lastWasSpace = true;
				}
				continue;
			}

			// Se è lettera o cifra lo teniamo (Character.isLetter gestisce anche lettere
			// accentate)
			if (Character.isLetter(c) || Character.isDigit(c)) {
				sb.append(c);
				lastWasSpace = false;
			}
			// Tutto il resto (segni di punteggiatura, simboli, ecc.) viene scartato
		}

		// 2) Trim finale
		String normalized = sb.toString().trim();
		if (normalized.isEmpty()) {
			return normalized;
		}

		// 3) Rimozione token booleani (AND, OR, NOT, &&, ||)
		// Ora abbiamo solo lettere/cifre/spazi, quindi && e || dovrebbero già essere
		// spariti,
		// ma teniamo comunque il filtering testuale per sicurezza.
		String[] tokens = normalized.split(" +");
		List<String> cleanedTokens = new ArrayList<>(tokens.length);

		outer: for (String token : tokens) {
			if (token.isEmpty()) {
				continue;
			}

			// Se token è un operatore testuale (AND/OR/NOT) lo scartiamo
			for (String forbidden : FORBIDDEN_TOKENS) {
				if (token.equalsIgnoreCase(forbidden)) {
					continue outer;
				}
			}

			cleanedTokens.add(token);
		}

		// 4) Join con singoli spazi
		return String.join(" ", cleanedTokens).trim();
	}

	/**
	 * Pulisce una lista di query.
	 *
	 * @param rawQueries lista di query grezze (può essere null)
	 * @return lista immutabile di query pulite (stessa dimensione dell'input, null
	 *         preservati)
	 */
	public static List<String> cleanQueries(List<String> rawQueries) {
		if (rawQueries == null) {
			return Collections.emptyList();
		}
		List<String> result = new ArrayList<>(rawQueries.size());
		for (String q : rawQueries) {
			result.add(cleanQuery(q));
		}
		return Collections.unmodifiableList(result);
	}
}