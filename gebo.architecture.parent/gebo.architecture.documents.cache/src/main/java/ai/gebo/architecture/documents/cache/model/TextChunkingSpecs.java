package ai.gebo.architecture.documents.cache.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class TextChunkingSpecs extends AbstractChunkingSpecs {
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
	
}
