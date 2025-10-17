package ai.gebo.architecture.graphrag.services.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.ai.document.Document;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.graphrag.extraction.model.LLMExtractionResult;
import ai.gebo.architecture.graphrag.extraction.services.IGraphDataExtractionService;
import ai.gebo.architecture.graphrag.persistence.model.ChunkHitRow;
import ai.gebo.architecture.graphrag.persistence.model.ChunkMeta;
import ai.gebo.architecture.graphrag.persistence.model.ChunkNeighborRow;
import ai.gebo.architecture.graphrag.persistence.model.GraphDocumentChunk;
import ai.gebo.architecture.graphrag.persistence.model.GraphExtractionMatching;
import ai.gebo.architecture.graphrag.persistence.model.HitType;
import ai.gebo.architecture.graphrag.persistence.model.KnowledgeGraphSearchResult;
import ai.gebo.architecture.graphrag.persistence.model.ScoredChunk;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphDocumentChunkRepository;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphDocumentReferenceRepository;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphEntityAliasInDocumentChunkRepository;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphEntityInDocumentChunkRepository;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphEventAliasInDocumentChunkRepository;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphEventInDocumentChunkRepository;
import ai.gebo.architecture.graphrag.persistence.repositories.GraphRelationInDocumentChunkRepository;
import ai.gebo.architecture.graphrag.services.IKnowledgeGraphSearchService;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.model.ExtractedDocumentMetaData;
import jakarta.annotation.Nullable;
@ConditionalOnProperty(prefix = "ai.gebo.neo4j", name = "enabled", havingValue = "true")
@Service
public class KnowledgeGraphSearchServiceImpl extends AbstractGraphPersistenceService
		implements IKnowledgeGraphSearchService {

	public KnowledgeGraphSearchServiceImpl(GraphDocumentReferenceRepository docReferenceRepository,
			GraphDocumentChunkRepository docChunkRepository, GraphEntityObjectDao entityObjectDao,
			GraphEntityInDocumentChunkRepository entityInChunkRepository, GraphEventObjectDao eventObjectDao,
			GraphEventInDocumentChunkRepository eventInChunkRepository, GraphRelationObjectDao relationObjectDao,
			GraphRelationInDocumentChunkRepository relationInChunkRepository, GraphEventAliasObjectDao eventAliasDao,
			GraphEntityAliasObjectDao entityAliasDao,
			GraphEntityAliasInDocumentChunkRepository entityAliasChunkRepository,
			GraphEventAliasInDocumentChunkRepository eventAliasChunkRepository,
			IGraphDataExtractionService extractionService) {
		super(docReferenceRepository, docChunkRepository, entityObjectDao, entityInChunkRepository, eventObjectDao,
				eventInChunkRepository, relationObjectDao, relationInChunkRepository, eventAliasDao, entityAliasDao,
				entityAliasChunkRepository, eventAliasChunkRepository, extractionService);

	}

	// --- Pesi tunabili per il ranking ibrido ---
	private static final double W_ENTITY = 1.1;
	private static final double W_EVENT = 1.3;
	private static final double W_ALIAS_ENTITY = 1.0;
	private static final double W_ALIAS_EVENT = 0.9;
	private static final double W_RELATION = 1.2;
	private static final double W_NEIGHBOR_1H = 0.4;
	private static final double W_COVERAGE = 0.6; // % anchor coperti nel chunk
	private static final double W_RECENCY = 0.25; // boost per doc recenti
	private static final double W_SEMANTIC = 0.8; // opzionale: embedding
	private static final double PEN_LENGTH = 0.05; // penalità per chunk molto lunghi

	@Override
	public List<KnowledgeGraphSearchResult> knowledgeGraphSearch(LLMExtractionResult extraction, int topK,
			List<String> knowledgeBasesCodes) {
		GraphExtractionMatching matching = searchMatches(extraction);

		// NB: se non c’è nulla, fai comunque un minimo di recall con 1-hop dai
		// canonical (se presenti)
		Set<String> entityIds = matching.getEntities().stream().map(x -> x.getId()).filter(Objects::nonNull)
				.collect(Collectors.toSet());
		Set<String> eventIds = matching.getEvents().stream().map(x -> x.getId()).filter(Objects::nonNull)
				.collect(Collectors.toSet());
		Set<String> entityAliasIds = matching.getEntityAliases().stream().map(x -> x.getId()).filter(Objects::nonNull)
				.collect(Collectors.toSet());
		Set<String> eventAliasIds = matching.getEventAliases().stream().map(x -> x.getId()).filter(Objects::nonNull)
				.collect(Collectors.toSet());
		Set<String> relationIds = matching.getRelations().stream().map(x -> x.getId()).filter(Objects::nonNull)
				.collect(Collectors.toSet());
		// 2) Recall: prende i chunk candidati
		// 2.a) Diretti sugli alias
		List<ChunkHitRow> entityHits = CollectionUtils.isEmpty(entityIds) ? List.of()
				: entityInChunkRepository.findChunksByEntityIds(entityIds, knowledgeBasesCodes);
		List<ChunkHitRow> eventHits = CollectionUtils.isEmpty(eventIds) ? List.of()
				: eventInChunkRepository.findChunksByEventIds(eventIds, knowledgeBasesCodes);
		List<ChunkHitRow> entityAliasHits = CollectionUtils.isEmpty(entityAliasIds) ? List.of()
				: entityAliasChunkRepository.findChunksByEntityAliasIds(entityAliasIds, knowledgeBasesCodes);
		List<ChunkHitRow> eventAliasHits = CollectionUtils.isEmpty(eventAliasIds) ? List.of()
				: eventAliasChunkRepository.findChunksByEventAliasIds(eventAliasIds, knowledgeBasesCodes);

		// 2.b) Relazioni matchate
		List<ChunkHitRow> relationHits = CollectionUtils.isEmpty(relationIds) ? List.of()
				: relationInChunkRepository.findChunksByRelationIds(relationIds, knowledgeBasesCodes);

		// 2.c) Vicini a 1-hop intorno agli anchor canonical (peso minore)
		List<ChunkNeighborRow> neighborHits = (!CollectionUtils.isEmpty(entityIds)
				|| !CollectionUtils.isEmpty(eventIds))
						? docChunkRepository.findChunksNearAnchors1Hop(entityIds, eventIds, knowledgeBasesCodes)
						: List.of();

		// 3) Aggrega features per chunk
		Map<String, ScoredChunk> scored = new HashMap<>();
		addHits(scored, entityHits, W_ENTITY, HitType.ENTITY);
		addHits(scored, eventHits, W_EVENT, HitType.EVENT);
		addHits(scored, relationHits, W_RELATION, HitType.RELATION);
		addHits(scored, entityAliasHits, W_ALIAS_ENTITY, HitType.ENTITY_ALIAS);
		addHits(scored, eventAliasHits, W_ALIAS_EVENT, HitType.EVENT_ALIAS);
		
		addNeighbors(scored, neighborHits, W_NEIGHBOR_1H);

		// 4) Copertura anchor = quanti anchor dell’input coperti in quel chunk
		int requestedAnchors = entityAliasIds.size() + eventAliasIds.size() + relationIds.size();
		if (requestedAnchors > 0) {
			for (ScoredChunk c : scored.values()) {
				double coverage = (double) c.anchorMatchedIds.size() / (double) requestedAnchors;
				c.score += W_COVERAGE * coverage;
			}
		}

		// 5) Recency / lunghezza / semantica (se disponibile)
		// Recupero metadati minimi dei chunk
		Map<String, ChunkMeta> metas = docChunkRepository.fetchChunkMetas(scored.keySet()).stream()
				.collect(Collectors.toMap(ChunkMeta::getChunkId, Function.identity(), (a, b) -> a));

		for (ScoredChunk c : scored.values()) {
			ChunkMeta meta = metas.get(c.chunkId);
			if (meta != null) {
				c.documentReferenceId = meta.getDocumentReferenceId();
				c.knowledgebaseCode = meta.getKnowledgebaseCode();
			}
		}

		// 5.b) Similarità semantica opzionale (se hai un servizio di embedding
		// indicizzate)
		// double sim = embeddingSearch.similarityToChunk(queryText, c.chunkId);
		// c.score += W_SEMANTIC * sim;

		// 6) Ordina, tronca topK, costruisci il risultato con spiegazioni
		List<KnowledgeGraphSearchResult> results = scored.values().stream()
				.sorted(Comparator.comparingDouble((ScoredChunk c) -> c.score).reversed()).limit(topK)
				.map(ScoredChunk::toResult).collect(Collectors.toList());
		List<KnowledgeGraphSearchResult> values = new ArrayList<KnowledgeGraphSearchResult>();
		for (KnowledgeGraphSearchResult result : results) {
			Optional<GraphDocumentChunk> chunk = this.docChunkRepository.findById(result.getChunkId());
			if (chunk.isPresent()) {
				result.setDocument(new Document(chunk.get().getId(), chunk.get().getText(), chunk.get().getMetaData()));
				result.setExtractedDocumentMetaData(ExtractedDocumentMetaData.of(result.getDocument().getMetadata()));
				values.add(result);
			}
		}
		return values;
	}
	// ----------------- helpers -----------------

	private static void addHits(Map<String, ScoredChunk> out, List<ChunkHitRow> hits, double weight, HitType type) {
		for (ChunkHitRow h : hits) {
			ScoredChunk c = out.computeIfAbsent(h.getChunkId(), ScoredChunk::new);
			c.score += weight * safeLong(h.getOccurrences());
			c.anchorMatchedIds.addAll(h.getMatchedIds());
			c.addContribution(type, safeLong(h.getOccurrences()));
		}
	}

	private static void addNeighbors(Map<String, ScoredChunk> out, List<ChunkNeighborRow> hits, double weight) {
		for (ChunkNeighborRow h : hits) {
			ScoredChunk c = out.computeIfAbsent(h.getChunkId(), ScoredChunk::new);
			c.score += weight * safeLong(h.getNeighborCount());
			c.addContribution(HitType.NEIGHBOR_1H, safeLong(h.getNeighborCount()));
		}
	}

	private static long safeLong(Long v) {
		return v == null ? 0L : v;
	}

	private static double recencyBoost(@Nullable Long docEpochMillis) {
		if (docEpochMillis == null)
			return 0.0;
		long days = Math.max(0, (Instant.now().toEpochMilli() - docEpochMillis) / (1000L * 60 * 60 * 24));
		// es. decadimento esponenziale: <=7gg ~1.0, 30gg ~0.4, 180gg ~0.05
		return Math.exp(-Math.log(10) * (double) days / 30.0);
	}

	private static double lengthPenalty(@Nullable Integer tokenCount) {
		if (tokenCount == null)
			return 0.0;
		// penalizza leggermente oltre 800 token
		int over = Math.max(0, tokenCount - 800);
		return Math.min(1.0, over / 1200.0);
	}

	@Override
	public List<KnowledgeGraphSearchResult> knowledgeGraphSearch(LLMExtractionResult extraction, int topK) {
		return knowledgeGraphSearch(extraction, topK, null);
	}

	@Override
	public List<KnowledgeGraphSearchResult> knowledgeGraphSearch(String query, List<String> knowledgeBases, int topK)
			throws LLMConfigException {
		Map<String, Object> cache = new HashMap<>();
		LLMExtractionResult extraction = extractionService.extract(query, knowledgeBases, cache);
		return knowledgeGraphSearch(extraction, topK, knowledgeBases);
	}

}
