package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.llms.abstraction.layer.model.ChatModelsUses;
import ai.gebo.llms.abstraction.layer.model.GBaseRankerModelConfig;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.BaseLLMSInvokingService;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableRankerModel;
import ai.gebo.llms.abstraction.layer.services.IGRankerModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.config.GeboPromptsLibrary;
import ai.gebo.llms.chat.abstraction.layer.config.GeboRagSearchConfig;
import ai.gebo.llms.chat.abstraction.layer.services.IGRankerService;
import ai.gebo.ranker.model.RankingInput;
import ai.gebo.ranker.model.RankingOutput;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
import ai.gebo.security.services.SecurityAuditTaxonomy;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GRankerServiceImpl extends BaseLLMSInvokingService implements IGRankerService {
	/**
	 * Field the irrelevance filter prompt must use to list the fragments to drop, the
	 * same convention the deep search file analysis prompt uses to have the model name
	 * the fragments it found irrelevant (see
	 * {@code FullReactiveDeepsearchWorker.extractIrrelevantFragmentsFromLine}).
	 */
	private static final String IRRELEVANT_FRAGMENTS_MARKER = "IRRELEVANT=";
	/**
	 * Spelling of the marker in the deep search prompt, which asks for the misspelled
	 * form. Both are accepted here so the answer is understood whatever spelling the
	 * model settles on, since models routinely "fix" one into the other.
	 */
	private static final String IRRELEVANT_FRAGMENTS_MARKER_ALTERNATE = "IRRILEVANT=";
	private static final String COMMA_CHARACTER = ",";
	/**
	 * A batch smaller than this makes no sense: with a tiny context window it is
	 * better to send one fragment per call than to give up on filtering.
	 */
	private static final int MINIMUM_BATCH_TOKENS_BUDGET = 512;

	private final IGRankerModelRuntimeConfigurationDao rankerModelDao;
	private final IGChatModelRuntimeConfigurationDao chatModelsDao;
	private final IGPromptConfigDao promptsDao;
	private final GeboRagSearchConfig ragSearchConfig;
	private final IGSecurityAuditLoggerService securityAuditLoggerService;

	// Takes an already-created SecurityEvent (never calls newSecurityEvent()
	// itself) so newSecurityEvent()'s caller-stack capture points at the two
	// call(...) overloads - the real invocation entry points - not at this
	// shared helper. Metadata-only: model/provider/outcome/latency, never the
	// documents or query text being ranked.
	private void logRankerEvent(SecurityEvent event, IGConfigurableRankerModel rankerModel, long startMillis,
			String outcome) {
		event.setEventType(SecurityAuditTaxonomy.EventType.LLM_INVOCATION);
		event.setCategory(SecurityAuditTaxonomy.Category.LLM_INVOCATION);
		event.setAction(SecurityAuditTaxonomy.Action.LLM_INVOKE_RANK);
		event.setResourceId(rankerModel != null ? rankerModel.getCode() : null);
		if (rankerModel != null && rankerModel.getType() != null) {
			event.getDetails().put("provider", rankerModel.getType().getCode());
		}
		event.getDetails().put("latencyMs", System.currentTimeMillis() - startMillis);
		event.setOutcome(outcome);
		securityAuditLoggerService.log(event);
	}

	// Companion of logRankerEvent for the second stage: the chat model invoked to
	// drop the irrelevant fragments. Metadata-only as well: on top of the model
	// coordinates it records how many fragments entered the filter and how many it
	// discarded, never the fragments themselves nor the query.
	private void logRankerFilterEvent(SecurityEvent event, IGConfigurableChatModel chatModel, long startMillis,
			String outcome, int rankedFragments, int discardedFragments) {
		event.setEventType(SecurityAuditTaxonomy.EventType.LLM_INVOCATION);
		event.setCategory(SecurityAuditTaxonomy.Category.LLM_INVOCATION);
		event.setAction(SecurityAuditTaxonomy.Action.LLM_INVOKE_RANK_FILTER);
		event.setResourceId(chatModel != null ? chatModel.getCode() : null);
		if (chatModel != null && chatModel.getType() != null) {
			event.getDetails().put("provider", chatModel.getType().getCode());
		}
		event.getDetails().put("latencyMs", System.currentTimeMillis() - startMillis);
		event.getDetails().put("rankedFragments", rankedFragments);
		event.getDetails().put("discardedFragments", discardedFragments);
		event.setOutcome(outcome);
		securityAuditLoggerService.log(event);
	}

	@Override
	public AIDocumentsSet call(AIDocumentsSet input, String query, int topK) throws LLMConfigException {
		final int nFragments = input.countFragments();
		if (nFragments <= 0)
			return input;

		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		long startMillis = System.currentTimeMillis();
		IGConfigurableRankerModel rankerModel = rankerModelDao.defaultHandler();
		try {
			if (rankerModel == null)
				throw new LLMConfigException(
						"No ranker model configured, call first isRankerConfigured() to check if there is one");
			RankingInput _input = new RankingInput(query, input.aiDocumentsList(), topK);
			RankingOutput out = rankerModel.getRankerModel().call(_input);
			List<Document> documents = out.getRanked().stream().map(x -> x.getDocument()).toList();
			logRankerEvent(event, rankerModel, startMillis, SecurityAuditTaxonomy.Outcome.SUCCESS);
			return AIDocumentsSet.from(discardIrrelevantDocuments(documents, query));
		} catch (RuntimeException | LLMConfigException e) {
			logRankerEvent(event, rankerModel, startMillis, SecurityAuditTaxonomy.Outcome.FAILURE);
			throw e;
		}
	}

	@Override
	public boolean isRankerConfigured() {

		return rankerModelDao.defaultHandler() != null;
	}

	@Override
	public List<Document> call(List<Document> input, String query, int topK) throws LLMConfigException {
		final int nFragments = input.size();
		if (nFragments <= 0)
			return input;

		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		long startMillis = System.currentTimeMillis();
		IGConfigurableRankerModel rankerModel = rankerModelDao.defaultHandler();
		try {
			if (rankerModel == null)
				throw new LLMConfigException(
						"No ranker model configured, call first isRankerConfigured() to check if there is one");
			RankingInput _input = new RankingInput(query, input, topK);
			RankingOutput out = rankerModel.getRankerModel().call(_input);
			List<Document> documents = out.getRanked().stream().map(x -> x.getDocument()).toList();
			logRankerEvent(event, rankerModel, startMillis, SecurityAuditTaxonomy.Outcome.SUCCESS);
			return discardIrrelevantDocuments(documents, query);
		} catch (RuntimeException | LLMConfigException e) {
			logRankerEvent(event, rankerModel, startMillis, SecurityAuditTaxonomy.Outcome.FAILURE);
			throw e;
		}
	}

	@Override
	public int getRankerConfiguredChunkSize() {
		IGConfigurableRankerModel rankerModel = rankerModelDao.defaultHandler();
		if (rankerModel == null)
			return 512;
		Integer documentTokens = ((GBaseRankerModelConfig) rankerModel.getConfig()).getMaxDocumentTokens();
		return documentTokens == null ? 512 : documentTokens.intValue();
	}

	/**
	 * Second relevance stage, applied on the output of the ranker model: the ranked
	 * fragments are submitted to the internal services chat model, which tells which
	 * of them are completely useless for the query, and those are dropped from the
	 * returned list. The ranker orders by relevance but never removes anything, so
	 * without this stage a result set whose tail is pure noise reaches the answering
	 * model as it is.
	 * <p>
	 * Every fragment below the protected top is judged, batch after batch from the best
	 * ranked to the worst, oversized fragments split into pieces so all of their content
	 * is seen, and a fragment is removed only when the model discarded every one of its
	 * pieces: the rank of a fragment never decides its fate, its own content does. The
	 * best ranked fragments are never submitted at all (see
	 * {@code rankerIrrelevanceFilterProtectedTopFragments}), so a moody verdict cannot
	 * drop the ranker's strongest picks, and the best ranked fragment always survives -
	 * a ranked result set never becomes an empty one.
	 * <p>
	 * Fail-open by design: when the filter cannot run (feature disabled, result set
	 * too small, no chat model configured, prompt missing) or fails for any reason,
	 * the ranked list is returned untouched, which is the behaviour the callers had
	 * before this stage existed.
	 */
	private List<Document> discardIrrelevantDocuments(List<Document> ranked, String query) {
		if (!ragSearchConfig.isRankerIrrelevanceFilterEnabled())
			return ranked;
		if (ranked == null || ranked.size() < ragSearchConfig.getRankerIrrelevanceFilterMinDocuments()) {
			if (LOGGER.isDebugEnabled() && ranked != null) {
				LOGGER.debug("Irrelevance filter skipped, only " + ranked.size() + " ranked fragment(s), minimum is "
						+ ragSearchConfig.getRankerIrrelevanceFilterMinDocuments());
			}
			return ranked;
		}
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		final long startMillis = System.currentTimeMillis();
		IGConfigurableChatModel serviceModel = null;
		try {
			serviceModel = chatModelsDao.findByUsesOrGetDefault(ChatModelsUses.INTERNAL_SERVICES);
			if (serviceModel == null) {
				LOGGER.warn(
						"No INTERNAL_SERVICES or default chat model configured, the ranked fragments are kept as they are");
				return ranked;
			}
			GPromptTemplateConfig prompt = promptsDao
					.findByPromptUse(GeboPromptsLibrary.RANKER_IRRELEVANT_FRAGMENTS_FILTER_PROMPT);
			if (prompt == null) {
				LOGGER.warn("Prompt " + GeboPromptsLibrary.RANKER_IRRELEVANT_FRAGMENTS_FILTER_PROMPT
						+ " not found, the ranked fragments are kept as they are");
				return ranked;
			}
			final Set<Integer> discardedIndices = discardedDocumentIndices(ranked, query, serviceModel, prompt);
			// Only documents the model judged useless in all of their pieces leave, and the
			// best ranked fragment is in the output list in every situation: whatever the
			// model thinks of it, returning nothing at all would leave the callers with no
			// material to work on, and the ranker put that fragment first exactly because it
			// is the closest thing to an answer the retrieval could find.
			final List<Document> kept = new ArrayList<>(ranked.size());
			for (int index = 0; index < ranked.size(); index++) {
				if (index == 0 || !discardedIndices.contains(index)) {
					kept.add(ranked.get(index));
				}
			}
			final int discarded = ranked.size() - kept.size();
			logRankerFilterEvent(event, serviceModel, startMillis, SecurityAuditTaxonomy.Outcome.SUCCESS, ranked.size(),
					discarded);
			if (discarded <= 0)
				return ranked;
			if (kept.size() == 1) {
				LOGGER.warn("The irrelevance filter found no relevant fragment among the " + ranked.size()
						+ " ranked one(s), only the best ranked one is kept");
			} else if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Irrelevance filter discarded " + discarded + " of " + ranked.size()
						+ " ranked fragment(s)");
			}
			return kept;
		} catch (Throwable th) {
			LOGGER.warn("Error filtering out the irrelevant ranked fragments, the ranked list is kept as it is", th);
			logRankerFilterEvent(event, serviceModel, startMillis, SecurityAuditTaxonomy.Outcome.FAILURE, ranked.size(),
					0);
			return ranked;
		}
	}

	/**
	 * Judges every ranked fragment and returns the indices of the ones the model found
	 * useless for the query, so they can be dropped from the ranked list.
	 * <p>
	 * A fragment that does not fit the batch budget is split into budget-sized pieces
	 * first, so no piece ever overflows the model context and no fragment is left
	 * unjudged because of its size. Each piece is submitted for judgement; a fragment
	 * counts as discarded only when the model discarded <em>every</em> one of its
	 * pieces - one kept piece keeps the whole fragment, because a single relevant
	 * passage is enough reason to hand the fragment to the answering model.
	 * <p>
	 * The pieces are walked in batches bounded by the budget and by a maximum piece
	 * count, best ranked first. Every batch is submitted: the walk never stops early
	 * and never infers anything about a piece it did not show to the model. The first
	 * {@code rankerIrrelevanceFilterProtectedTopFragments} fragments are excluded from
	 * the walk entirely and can never be discarded.
	 */
	private Set<Integer> discardedDocumentIndices(List<Document> ranked, String query,
			IGConfigurableChatModel serviceModel, GPromptTemplateConfig prompt) throws LLMConfigException {
		final long batchBudget = batchTokensBudget(serviceModel, prompt, query);
		final int maxFragmentsPerBatch = Math.max(1, ragSearchConfig.getRankerIrrelevanceFilterMaxFragmentsPerBatch());
		final int protectedTop = Math.max(0,
				Math.min(ragSearchConfig.getRankerIrrelevanceFilterProtectedTopFragments(), ranked.size()));
		final IChatRequestContext context = IChatRequestContext.of(query);
		if (protectedTop > 0 && LOGGER.isDebugEnabled()) {
			LOGGER.debug("Irrelevance filter protects the top " + protectedTop + " of " + ranked.size()
					+ " ranked fragment(s) from judgement");
		}

		// break every oversized fragment into budget-sized pieces, mapping each piece
		// back to the index of the fragment it came from. The protected top fragments are
		// never submitted, so the walk starts below them
		final TokenTextSplitter splitter = createTokenSplitter((int) Math.min(Integer.MAX_VALUE, batchBudget));
		final List<Document> pieces = new ArrayList<>();
		final Map<String, Integer> pieceToDocumentIndex = new HashMap<>();
		final Map<Integer, Integer> piecesPerDocument = new HashMap<>();
		for (int index = protectedTop; index < ranked.size(); index++) {
			final Document document = ranked.get(index);
			final List<Document> documentPieces = weight(document) <= batchBudget ? List.of(document)
					: splitToBudget(splitter, document);
			for (Document piece : documentPieces) {
				pieceToDocumentIndex.put(piece.getId(), index);
				pieces.add(piece);
			}
			piecesPerDocument.put(index, documentPieces.size());
		}

		// judge the pieces batch by batch, collecting the ids of the discarded ones
		final Set<String> discardedPieces = new HashSet<>();
		int batchStart = 0;
		while (batchStart < pieces.size()) {
			int batchEnd = batchStart;
			long batchWeight = 0l;
			// cumulate downwards until the budget or the piece cap is reached, always taking
			// at least one piece (which fits the budget by construction)
			while (batchEnd < pieces.size() && (batchEnd - batchStart) < maxFragmentsPerBatch) {
				final long pieceWeight = weight(pieces.get(batchEnd));
				if (batchEnd > batchStart && (batchWeight + pieceWeight) > batchBudget)
					break;
				batchWeight += pieceWeight;
				batchEnd++;
			}
			final List<Document> batch = pieces.subList(batchStart, batchEnd);
			final Set<String> batchDiscarded = askDiscardedFragments(batch, query, serviceModel, prompt, context);
			discardedPieces.addAll(batchDiscarded);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Irrelevance filter judged pieces [" + batchStart + "," + batchEnd + ") of "
						+ pieces.size() + ": " + batchDiscarded.size() + " to discard");
			}
			batchStart = batchEnd;
		}

		// a fragment is discarded only when every one of its pieces was discarded
		final Map<Integer, Integer> discardedPiecesPerDocument = new HashMap<>();
		for (Document piece : pieces) {
			if (discardedPieces.contains(piece.getId())) {
				final int documentIndex = pieceToDocumentIndex.get(piece.getId());
				discardedPiecesPerDocument.merge(documentIndex, 1, Integer::sum);
			}
		}
		final Set<Integer> discardedDocuments = new HashSet<>();
		for (Map.Entry<Integer, Integer> entry : discardedPiecesPerDocument.entrySet()) {
			final int documentIndex = entry.getKey();
			if (entry.getValue() >= piecesPerDocument.get(documentIndex)) {
				discardedDocuments.add(documentIndex);
			}
		}
		return discardedDocuments;
	}

	/**
	 * Splits an oversized fragment into budget-sized pieces. The splitter copies the
	 * fragment metadata onto each piece and gives it its own id, so a piece renders in
	 * the prompt like any other fragment and can be named back by the model; the ids
	 * are mapped to the origin fragment by the caller. A fragment that the splitter
	 * cannot break (no usable text) falls back to itself, so it is still judged rather
	 * than silently dropped.
	 */
	private List<Document> splitToBudget(TokenTextSplitter splitter, Document document) {
		try {
			final List<Document> pieces = splitter.apply(List.of(document));
			if (pieces != null && !pieces.isEmpty()) {
				return pieces;
			}
		} catch (RuntimeException e) {
			LOGGER.warn("Cannot split the oversized fragment " + document.getId()
					+ " for the irrelevance filter, it is judged whole", e);
		}
		return List.of(document);
	}

	/**
	 * Tokens left for the fragments of a single batch: the configured fraction of the
	 * filtering model context window, less what the prompt itself and the query take.
	 */
	private long batchTokensBudget(IGConfigurableChatModel serviceModel, GPromptTemplateConfig prompt, String query) {
		final double fraction = ragSearchConfig.getRankerIrrelevanceFilterContextFraction();
		final long window = Math.round(serviceModel.getContextLength() * fraction);
		final long overhead = prompt.getTokensSize()
				+ (query != null ? ITokensCountable.stringsTokensSize(query) : 0);
		return Math.max(MINIMUM_BATCH_TOKENS_BUDGET, window - overhead);
	}

	/**
	 * Submits one batch of ranked fragments to the filtering model and returns the ids
	 * of the ones it declared completely useless for the query.
	 */
	private Set<String> askDiscardedFragments(List<Document> batch, String query, IGConfigurableChatModel serviceModel,
			GPromptTemplateConfig prompt, IChatRequestContext context) throws LLMConfigException {
		Map<String, Object> params = new HashMap<>();
		params.put(IChatRequestContext.USER_QUESTION_PROMPT_PARAM, query != null ? query : "");
		params.put(IChatRequestContext.DOCUMENTS_PROMPT_PARAM, batch);
		final String answer = callLLM(serviceModel, prompt, context, params);
		return extractDiscardedFragmentIds(answer, batch);
	}

	/**
	 * Reads the IRRELEVANT= line out of the model answer. The ids are matched against
	 * the ones actually submitted in the batch - the fragmentId the document renderer
	 * wrote in the prompt is {@link Document#getId()} - so a hallucinated or mangled
	 * identifier drops the fragment of nobody.
	 */
	private Set<String> extractDiscardedFragmentIds(String answer, List<Document> batch) {
		final Set<String> discarded = new HashSet<>();
		if (answer == null)
			return discarded;
		final int listIndex = discardedListIndex(answer.toUpperCase());
		if (listIndex < 0) {
			LOGGER.warn("The irrelevance filter answer carries no " + IRRELEVANT_FRAGMENTS_MARKER
					+ " line, the whole batch is kept");
			return discarded;
		}
		final int lineEnd = endOfLine(answer, listIndex);
		final String commaSeparatedList = answer.substring(listIndex, lineEnd).trim();
		if (commaSeparatedList.length() == 0)
			return discarded;
		final Set<String> batchIds = new HashSet<>();
		for (Document document : batch) {
			batchIds.add(document.getId());
		}
		final StringTokenizer tokenizer = new StringTokenizer(commaSeparatedList, COMMA_CHARACTER);
		while (tokenizer.hasMoreTokens()) {
			final String fragmentId = tokenizer.nextToken().trim();
			if (batchIds.contains(fragmentId)) {
				discarded.add(fragmentId);
			} else if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("The irrelevance filter returned the unknown fragment id:" + fragmentId + ", ignored");
			}
		}
		return discarded;
	}

	/**
	 * Index the discarded ids list starts at, whichever of the two accepted spellings
	 * of the marker the model used, or -1 when the answer carries neither.
	 */
	private static int discardedListIndex(String upperCaseAnswer) {
		final int markerIndex = upperCaseAnswer.indexOf(IRRELEVANT_FRAGMENTS_MARKER);
		if (markerIndex >= 0)
			return markerIndex + IRRELEVANT_FRAGMENTS_MARKER.length();
		final int alternateIndex = upperCaseAnswer.indexOf(IRRELEVANT_FRAGMENTS_MARKER_ALTERNATE);
		if (alternateIndex >= 0)
			return alternateIndex + IRRELEVANT_FRAGMENTS_MARKER_ALTERNATE.length();
		return -1;
	}

	private static int endOfLine(String text, int from) {
		for (int index = from; index < text.length(); index++) {
			final char character = text.charAt(index);
			if (character == '\r' || character == '\n')
				return index;
		}
		return text.length();
	}

}
