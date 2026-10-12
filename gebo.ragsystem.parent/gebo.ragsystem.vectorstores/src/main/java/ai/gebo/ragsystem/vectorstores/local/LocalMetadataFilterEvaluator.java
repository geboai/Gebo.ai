/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ragsystem.vectorstores.local;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.ai.vectorstore.filter.Filter.Expression;
import org.springframework.ai.vectorstore.filter.Filter.Group;
import org.springframework.ai.vectorstore.filter.Filter.Key;
import org.springframework.ai.vectorstore.filter.Filter.Operand;
import org.springframework.ai.vectorstore.filter.Filter.Value;

/**
 * Evaluates a Spring AI metadata filter against the metadata of one fragment.
 *
 * <h2>Why this exists instead of Spring AI's own evaluator</h2> Spring AI ships
 * {@code SimpleVectorStoreFilterExpressionEvaluator}, but it compares a
 * MULTI-VALUED metadata attribute against the filter literal as if it were a
 * scalar, and throws:
 *
 * <pre>
 * in(GEBO_ACL_ALIASES, [2, 99])
 *   -&gt; IllegalArgumentException: Cannot compare values of types
 *      java.util.ImmutableCollections$List12 and java.lang.Integer
 * </pre>
 *
 * Several Gebo.ai attributes are lists - {@code GEBO_ACL_ALIASES} and
 * {@code CATEGORIES} - and {@code SemanticSearchMetaDataFilter} filters on the
 * first one whenever ACL aliases are in play. That evaluator is package private
 * and final, and it is reached from a private lambda inside
 * {@code doSimilaritySearch}, so it cannot be replaced from outside: the only way
 * to get correct semantics is to evaluate the filter here and override the search.
 *
 * <h2>The semantics implemented</h2> A list-valued attribute MATCHES when ANY of
 * its elements satisfies the comparison. That makes {@code IN} an intersection
 * test, which is what an ACL alias filter means: "the fragment is readable
 * through at least one of the aliases this caller holds". Numbers are compared
 * numerically, so a filter written with Integers still matches a value that came
 * back from JSON as a Double.
 */
public class LocalMetadataFilterEvaluator {

	/**
	 * Tests one fragment's metadata against a filter.
	 *
	 * @param expression the filter, null matches everything
	 * @param metadata   the fragment metadata, may be null
	 * @return true when the fragment must be kept
	 */
	public boolean evaluate(Expression expression, Map<String, Object> metadata) {
		if (expression == null) {
			return true;
		}
		return evaluateOperand(expression, metadata == null ? Map.of() : metadata);
	}

	/**
	 * Dispatches on the operand type.
	 *
	 * @param operand  the operand to evaluate
	 * @param metadata the fragment metadata
	 * @return the outcome of the operand
	 */
	private boolean evaluateOperand(Operand operand, Map<String, Object> metadata) {
		if (operand instanceof Group group) {
			return evaluateOperand(group.content(), metadata);
		}
		if (operand instanceof Expression expression) {
			return evaluateExpression(expression, metadata);
		}
		throw new IllegalArgumentException(
				"Unsupported filter operand: " + (operand == null ? "null" : operand.getClass().getName()));
	}

	/**
	 * Evaluates a single expression node.
	 *
	 * @param expression the expression
	 * @param metadata   the fragment metadata
	 * @return the outcome of the expression
	 */
	private boolean evaluateExpression(Expression expression, Map<String, Object> metadata) {
		switch (expression.type()) {
		case AND:
			return evaluateOperand(expression.left(), metadata) && evaluateOperand(expression.right(), metadata);
		case OR:
			return evaluateOperand(expression.left(), metadata) || evaluateOperand(expression.right(), metadata);
		case NOT:
			return !evaluateOperand(expression.left(), metadata);
		case EQ:
			return anyValueMatches(metadata, keyOf(expression.left()),
					stored -> sameValue(stored, literalOf(expression.right())));
		case NE:
			// "not equal" holds when NO value of the attribute equals the literal, so
			// a fragment tagged [1,2] is excluded by NE 2 - the mirror of EQ.
			return !anyValueMatches(metadata, keyOf(expression.left()),
					stored -> sameValue(stored, literalOf(expression.right())));
		case GT:
			return anyValueMatches(metadata, keyOf(expression.left()),
					stored -> compareNumbers(stored, literalOf(expression.right()), false, false));
		case GTE:
			return anyValueMatches(metadata, keyOf(expression.left()),
					stored -> compareNumbers(stored, literalOf(expression.right()), false, true));
		case LT:
			return anyValueMatches(metadata, keyOf(expression.left()),
					stored -> compareNumbers(stored, literalOf(expression.right()), true, false));
		case LTE:
			return anyValueMatches(metadata, keyOf(expression.left()),
					stored -> compareNumbers(stored, literalOf(expression.right()), true, true));
		case IN:
			return anyValueMatches(metadata, keyOf(expression.left()),
					stored -> containsValue(asList(literalOf(expression.right())), stored));
		case NIN:
			return !anyValueMatches(metadata, keyOf(expression.left()),
					stored -> containsValue(asList(literalOf(expression.right())), stored));
		case ISNULL:
			return !hasValue(metadata, keyOf(expression.left()));
		case ISNOTNULL:
			return hasValue(metadata, keyOf(expression.left()));
		default:
			throw new IllegalArgumentException("Unsupported filter expression type: " + expression.type());
		}
	}

	/**
	 * Applies a test to the attribute, treating a list-valued attribute as a set of
	 * candidates of which ONE matching is enough.
	 *
	 * @param metadata  the fragment metadata
	 * @param key       the attribute name
	 * @param predicate the test applied to each stored value
	 * @return true when at least one stored value satisfies the test
	 */
	private boolean anyValueMatches(Map<String, Object> metadata, String key, ValuePredicate predicate) {
		Object stored = metadata.get(key);
		if (stored == null) {
			return false;
		}
		if (stored instanceof Collection<?> collection) {
			for (Object element : collection) {
				if (element != null && predicate.test(element)) {
					return true;
				}
			}
			return false;
		}
		if (stored.getClass().isArray()) {
			int length = java.lang.reflect.Array.getLength(stored);
			for (int i = 0; i < length; i++) {
				Object element = java.lang.reflect.Array.get(stored, i);
				if (element != null && predicate.test(element)) {
					return true;
				}
			}
			return false;
		}
		return predicate.test(stored);
	}

	/**
	 * Whether the attribute carries anything at all. An empty list counts as
	 * absent, matching the way the store would have nothing to filter on.
	 *
	 * @param metadata the fragment metadata
	 * @param key      the attribute name
	 * @return true when a value is present
	 */
	private boolean hasValue(Map<String, Object> metadata, String key) {
		Object stored = metadata.get(key);
		if (stored == null) {
			return false;
		}
		if (stored instanceof Collection<?> collection) {
			return !collection.isEmpty();
		}
		return true;
	}

	/**
	 * Equality between a stored value and a filter literal, tolerating the numeric
	 * type drift a JSON round trip introduces.
	 *
	 * @param stored  value read from the fragment metadata
	 * @param literal value written in the filter
	 * @return true when the two denote the same value
	 */
	static boolean sameValue(Object stored, Object literal) {
		if (stored == null || literal == null) {
			return stored == literal;
		}
		if (stored instanceof Number && literal instanceof Number) {
			return ((Number) stored).doubleValue() == ((Number) literal).doubleValue();
		}
		if (stored instanceof Boolean || literal instanceof Boolean) {
			return String.valueOf(stored).equals(String.valueOf(literal));
		}
		if (stored.equals(literal)) {
			return true;
		}
		// Last resort: a canonical text form, so an Instant filtered as a String and
		// a number filtered as text still compare equal.
		return canonical(stored).equals(canonical(literal));
	}

	/**
	 * Canonical text form used as the equality fallback.
	 *
	 * @param value the value to render
	 * @return its canonical text
	 */
	private static String canonical(Object value) {
		if (value instanceof Number number) {
			double asDouble = number.doubleValue();
			if (asDouble == Math.rint(asDouble) && !Double.isInfinite(asDouble)) {
				return Long.toString((long) asDouble);
			}
			return Double.toString(asDouble);
		}
		if (value instanceof Instant instant) {
			return instant.toString();
		}
		if (value instanceof Date date) {
			return date.toInstant().toString();
		}
		return String.valueOf(value);
	}

	/**
	 * Ordered comparison between a stored value and a bound. Anything that is not a
	 * number on both sides cannot be ordered, and does not match.
	 *
	 * @param stored    value read from the fragment metadata
	 * @param bound     the bound written in the filter
	 * @param lowerThan true for LT / LTE, false for GT / GTE
	 * @param inclusive whether equality satisfies the comparison
	 * @return true when the stored value satisfies the comparison
	 */
	static boolean compareNumbers(Object stored, Object bound, boolean lowerThan, boolean inclusive) {
		Double storedNumber = numberOf(stored);
		Double boundNumber = numberOf(bound);
		if (storedNumber == null || boundNumber == null) {
			return false;
		}
		int comparison = Double.compare(storedNumber.doubleValue(), boundNumber.doubleValue());
		if (comparison == 0) {
			return inclusive;
		}
		return lowerThan ? comparison < 0 : comparison > 0;
	}

	/**
	 * Numeric projection of a value, or null when it is not a number.
	 *
	 * @param value the value
	 * @return the numeric value or null
	 */
	static Double numberOf(Object value) {
		if (value instanceof Number number) {
			return Double.valueOf(number.doubleValue());
		}
		if (value instanceof String text) {
			try {
				return Double.valueOf(text.trim());
			} catch (NumberFormatException notANumber) {
				return null;
			}
		}
		return null;
	}

	/**
	 * Membership of a stored value in the literals of an IN / NIN list.
	 *
	 * @param literals the filter list
	 * @param stored   one stored value
	 * @return true when the stored value is one of the literals
	 */
	private static boolean containsValue(List<Object> literals, Object stored) {
		for (Object literal : literals) {
			if (sameValue(stored, literal)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Flattens a filter literal that may be a collection or an array.
	 *
	 * @param value the literal
	 * @return the elements to match against
	 */
	private static List<Object> asList(Object value) {
		List<Object> out = new ArrayList<>();
		if (value instanceof Collection<?> collection) {
			out.addAll(collection);
		} else if (value != null && value.getClass().isArray()) {
			int length = java.lang.reflect.Array.getLength(value);
			for (int i = 0; i < length; i++) {
				out.add(java.lang.reflect.Array.get(value, i));
			}
		} else {
			out.add(value);
		}
		return out;
	}

	/**
	 * Extracts the attribute name from the left hand side of a comparison.
	 *
	 * @param operand the operand, expected to be a {@link Key}
	 * @return the attribute name
	 */
	private static String keyOf(Operand operand) {
		if (operand instanceof Key key) {
			return key.key();
		}
		throw new IllegalArgumentException("Expected a metadata key on the left side of the filter, got: " + operand);
	}

	/**
	 * Extracts the literal from the right hand side of a comparison.
	 *
	 * @param operand the operand, expected to be a {@link Value}
	 * @return the wrapped literal
	 */
	private static Object literalOf(Operand operand) {
		if (operand instanceof Value value) {
			return value.value();
		}
		throw new IllegalArgumentException("Expected a literal on the right side of the filter, got: " + operand);
	}

	/** Test applied to one stored metadata value. */
	private interface ValuePredicate {

		/**
		 * @param storedValue a single value of the attribute
		 * @return true when it satisfies the comparison
		 */
		boolean test(Object storedValue);
	}
}
