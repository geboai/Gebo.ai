package ai.gebo.architecture.documents.cache.model;

public enum DocumentChunkType {
	/** A piece of the document's text. */
	TEXT,
	/** An image of the document, whole: the chunk's data are its bytes in base64, its mime type the image's. */
	IMAGE,
	/** A recording, whole, held as an image is. */
	AUDIO,
	/** A video, whole, held as an image is. */
	VIDEO;

	/** Whether the chunk holds a file as it is (an image, a recording, a video) and not text. */
	public boolean isMedia() {
		return this != TEXT;
	}

	/** The kind of chunk a media of this mime type is held as; null for a type that is none of them. */
	public static DocumentChunkType ofMediaMimeType(String mimeType) {
		final String type = mimeType != null ? mimeType.toLowerCase() : "";
		if (type.startsWith("image/")) {
			return IMAGE;
		}
		if (type.startsWith("audio/")) {
			return AUDIO;
		}
		if (type.startsWith("video/")) {
			return VIDEO;
		}
		return null;
	}
}
