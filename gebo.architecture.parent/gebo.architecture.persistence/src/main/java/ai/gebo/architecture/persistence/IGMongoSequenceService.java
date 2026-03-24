package ai.gebo.architecture.persistence;

public interface IGMongoSequenceService {
	public long nextSequence(String sequenceName);
}
