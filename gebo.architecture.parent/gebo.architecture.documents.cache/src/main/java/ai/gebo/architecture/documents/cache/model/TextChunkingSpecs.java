package ai.gebo.architecture.documents.cache.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode

public class TextChunkingSpecs extends AbstractChunkingSpecs {
	public final static int MAX_CHUNKS_NUMBERS = 100000;
	int defaultChunkSize = 512;
	// The minimum size of each text chunk in characters
	int minChunkSizeChars = 350;
	// Discard chunks shorter than this
	int minChunkLengthToEmbed = 5;
	// The maximum number of chunks to generate from a text
	int maxNumChunks = 10000;
	boolean keepSeparator = true;

	public TextChunkingSpecs() {
		super(DocumentChunkType.TEXT);

	}

	public static TextChunkingSpecs of(int chunkingSize, int minChunksLength, int maxNumChunks) {
		TextChunkingSpecs specs = new TextChunkingSpecs();
		specs.defaultChunkSize = chunkingSize;
		specs.minChunkSizeChars = chunkingSize * 5;
		specs.minChunkLengthToEmbed = minChunksLength;
		specs.maxNumChunks = maxNumChunks;
		specs.keepSeparator = true;
		return specs;
	}

	public static TextChunkingSpecs of(int chunkingSize) {
		return of(chunkingSize, 20, MAX_CHUNKS_NUMBERS);
	}
}
