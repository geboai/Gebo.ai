package ai.gebo.architecture.rag_threasholds_autotune.service.impl.model;

import java.util.UUID;

public class AutoTuneQuestion {
	public String id = UUID.randomUUID().toString();
	public String text = null;
	public AutoTuneQueryHardness hardness = AutoTuneQueryHardness.MEDIUM;
	public String documentId = null;
}