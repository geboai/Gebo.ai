package ai.gebo.architecture.documents.cache.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode

public class TextChunkingSpecs extends AbstractChunkingSpecs {
	public final static int MAX_CHUNKS_NUMBERS = 100000;
	public final static int MIN_CHUNKS_SIZE_CHARS = 10;
	public final static int MIN_CHUNKS_LENGTH_TO_EMBED = 5;
	public final static TextChunkingSpecs DEFAULT_SPECS=new TextChunkingSpecs();
	int defaultChunkSize = 512;
	// The minimum size of each text chunk in characters
	int minChunkSizeChars = MIN_CHUNKS_SIZE_CHARS;
	// Discard chunks shorter than this
	int minChunkLengthToEmbed = MIN_CHUNKS_LENGTH_TO_EMBED;
	// The maximum number of chunks to generate from a text
	int maxNumChunks = MAX_CHUNKS_NUMBERS;
	boolean keepSeparator = true;

	public TextChunkingSpecs() {
		super(DocumentChunkType.TEXT);

	}

	public static TextChunkingSpecs of(int chunkingSize, int minChunksLength, int maxNumChunks) {
		TextChunkingSpecs specs = new TextChunkingSpecs();
		specs.defaultChunkSize = chunkingSize;
		specs.minChunkLengthToEmbed = minChunksLength;
		specs.maxNumChunks = maxNumChunks;
		specs.keepSeparator = true;
		return specs;
	}

	public static TextChunkingSpecs of(int chunkingSize) {
		return of(chunkingSize, MIN_CHUNKS_SIZE_CHARS, MAX_CHUNKS_NUMBERS);
	}

	public static TextChunkingSpecs maximizedLength(int chunkingSize) {
		return of(chunkingSize, MIN_CHUNKS_SIZE_CHARS, MAX_CHUNKS_NUMBERS);
	}
}
