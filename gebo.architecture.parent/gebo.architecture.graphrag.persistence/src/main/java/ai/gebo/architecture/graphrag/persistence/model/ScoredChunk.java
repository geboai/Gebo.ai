package ai.gebo.architecture.graphrag.persistence.model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ScoredChunk {
	public final String chunkId;
	public String documentReferenceId;
	public String knowledgebaseCode;
	public double score = 0.0;
	public final Map<HitType, Long> contributions = new EnumMap<>(HitType.class);
	public final Set<String> anchorMatchedIds = new HashSet<>();
	
	public ScoredChunk(String chunkId) {
		this.chunkId = chunkId;
	}

	public void addContribution(HitType t, long n) {
		contributions.merge(t, n, Long::sum);
	}

	public KnowledgeGraphSearchResult toResult() {
		KnowledgeGraphSearchResult r = new KnowledgeGraphSearchResult();
		r.setChunkId(chunkId);
		r.setDocumentReferenceId(documentReferenceId);
		r.setKnowledgebaseCode(knowledgebaseCode);
		r.setScore(score);
		r.setContributions(contributions.entrySet().stream().map(e -> e.getKey().name() + ":" + e.getValue())
				.collect(Collectors.toList()));
		r.setMatchedAnchorIds(new ArrayList<>(anchorMatchedIds));
		return r;
	}
}