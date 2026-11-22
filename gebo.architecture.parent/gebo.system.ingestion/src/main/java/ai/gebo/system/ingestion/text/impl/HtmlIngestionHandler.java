/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.system.ingestion.text.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import ai.gebo.document.model.GeboDocument;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.system.ingestion.DocumentTitles;
import ai.gebo.system.ingestion.GeboIngestionException;
import ai.gebo.system.ingestion.IGIngestionHandlerConfigDao;
import ai.gebo.system.ingestion.impl.GAbstractConfiguredHandler;

/**
 * HTML Ingestion Handler Service for processing HTML content.
 * This handler extracts text from HTML documents for further processing.
 * AI generated comments
 */
@Service
public class HtmlIngestionHandler extends GAbstractConfiguredHandler {

    /**
     * Constructor for HtmlIngestionHandler.
     * 
     * @param dao Data access object for ingestion handler configuration
     */
	public HtmlIngestionHandler(IGIngestionHandlerConfigDao dao) {
		super(dao);
	}

    /**
     * Returns the code identifier for this handler.
     * 
     * @return String identifier for this handler
     */
	@Override
	public String getCode() {
		return "HtmlIngestionHandler";
	}

    /**
     * Processes HTML content into Document objects.
     * Extracts text from the HTML body tag using JSoup and creates a Document with the extracted text,
     * the page's title and description in its metadata (see {@link DocumentTitles#fromHtml}).
     * 
     * @param reference Reference to the document being processed
     * @param is InputStream containing the HTML content
     * @param metadata Map of metadata associated with the document
     * @return Stream of Document objects created from the HTML content
     * @throws GeboIngestionException If an error occurs during ingestion
     * @throws IOException If an I/O error occurs
     */
	@Override
	public Stream<Document> handleContent(GDocumentReference reference, InputStream is, Map<String, Object> metadata)
			throws GeboIngestionException, IOException {
		try {
			enrichMetaData(reference, metadata);
			List<Document> docs = new ArrayList<Document>();
			// the page's own address, when it has one on the web: the addresses the page
			// names relative to itself are resolved against it
			final String pageAddress = reference != null && reference.getUri() != null
					&& reference.getUri().toLowerCase().startsWith("http") ? reference.getUri() : "";
			final org.jsoup.nodes.Document page = Jsoup.parse(is, "UTF-8", pageAddress);
			DocumentTitles.fromHtml(page, metadata);
			String text = page.select("body").text();
			docs.add(new Document(text, metadata));
			if (ingestsImages()) {
				// the pictures the page carries in itself, and the ones it names by an address:
				// one that is not reachable is omitted and the page is read all the same
				final List<ai.gebo.system.ingestion.EmbeddedPicture> pictures = new ArrayList<>();
				final long deadline = System.currentTimeMillis() + LINKED_MEDIA_MILLIS_PER_PAGE;
				final int mostFetched = 2 * Math.max(0, imagesConfig().getMaxImagesPerDocument());
				final java.util.Set<String> asked = new java.util.HashSet<>();
				int fetched = 0;
				for (org.jsoup.nodes.Element img : page.select("img[src]")) {
					final String src = img.attr("src");
					final String alt = img.attr("alt");
					if (src.startsWith("data:")) {
						final int comma = src.indexOf(',');
						final String header = comma > 0 ? src.substring("data:".length(), comma) : "";
						if (!header.startsWith("image/") || !header.endsWith(";base64")) {
							continue;
						}
						try {
							pictures.add(new ai.gebo.system.ingestion.EmbeddedPicture(alt.isBlank() ? null : alt,
									header.substring(0, header.indexOf(';')),
									java.util.Base64.getMimeDecoder().decode(src.substring(comma + 1))));
						} catch (IllegalArgumentException notBase64) {
							// not a readable picture: the page's text is read all the same
						}
						continue;
					}
					final String address = img.absUrl("src");
					if (address.isBlank() || !asked.add(address) || fetched >= mostFetched
							|| System.currentTimeMillis() > deadline) {
						continue;
					}
					fetched++;
					final LinkedMediaFetcher.Fetched picture = LinkedMediaFetcher.fetch(address,
							imagesConfig().getMaxImageBytes());
					if (picture != null) {
						pictures.add(new ai.gebo.system.ingestion.EmbeddedPicture(
								alt.isBlank() ? lastSegment(address) : alt, picture.contentType(), picture.data()));
					}
				}
				docs.addAll(imageDocuments(reference, pictures, metadata));
			}
			if (ingestsRecordings() || ingestsVideos()) {
				docs.addAll(linkedRecordingsAndVideos(reference, page, metadata));
			}
			return docs.stream();
		} finally {
			try {
				is.close();
			} catch (Throwable t) {
                // Silently ignore exceptions on close
			}
		}
	}

	/** The time, in milliseconds, the media a page names by an address may take all together. */
	static final long LINKED_MEDIA_MILLIS_PER_PAGE = 60_000L;
	/** The recordings and videos of one page fetched at most. */
	static final int MAX_LINKED_RECORDINGS_AND_VIDEOS = 10;

	private static String lastSegment(String address) {
		final String path = address.contains("?") ? address.substring(0, address.indexOf('?')) : address;
		final String name = path.substring(path.lastIndexOf('/') + 1);
		return name.isBlank() ? null : name;
	}

	/**
	 * The recordings and the videos a page names by an address (audio, video and their
	 * source elements), each given as a media document when its kind is ingested and it
	 * is within the size of its kind. One that is not reachable is omitted.
	 */
	private List<Document> linkedRecordingsAndVideos(GDocumentReference reference, org.jsoup.nodes.Document page,
			Map<String, Object> metadata) {
		final List<Document> out = new ArrayList<>();
		final long deadline = System.currentTimeMillis() + LINKED_MEDIA_MILLIS_PER_PAGE;
		final java.util.Set<String> asked = new java.util.HashSet<>();
		for (org.jsoup.nodes.Element element : page.select("audio[src], video[src], audio source[src], video source[src]")) {
			final org.jsoup.nodes.Element owner = element.tagName().equals("source") ? element.parent() : element;
			final boolean video = owner != null && owner.tagName().equals("video");
			if (video ? !ingestsVideos() : !ingestsRecordings()) {
				continue;
			}
			final String address = element.absUrl("src");
			if (address.isBlank() || !asked.add(address) || asked.size() > MAX_LINKED_RECORDINGS_AND_VIDEOS
					|| System.currentTimeMillis() > deadline) {
				continue;
			}
			final LinkedMediaFetcher.Fetched media = LinkedMediaFetcher.fetch(address,
					video ? mediaConfig().getMaxVideoBytes() : mediaConfig().getMaxRecordingBytes());
			final String type = media != null ? media.contentType() : null;
			// the kind the server declares must be the kind of the element
			if (type == null || !type.startsWith(video ? "video/" : "audio/")) {
				continue;
			}
			try {
				final Map<String, Object> cloned = new java.util.HashMap<>(
						metadata != null ? metadata : new java.util.HashMap<>());
				final String name = lastSegment(address);
				if (name != null) {
					cloned.put(ai.gebo.system.ingestion.config.ImagesIngestionConfig.IMAGE_NAME_METADATA, name);
				}
				out.add(Document.builder()
						.media(org.springframework.ai.content.Media.builder()
								.mimeType(org.springframework.util.MimeTypeUtils.parseMimeType(type)).data(media.data())
								.name("media").build())
						.metadata(cloned).build());
			} catch (RuntimeException unreadable) {
				// a type that cannot be read: omitted
			}
		}
		return out;
	}

    /**
     * Processes HTML content into a GeboDocument with text-only content.
     * 
     * @param reference Reference to the document being processed
     * @param is InputStream containing the HTML content
     * @param manageMetainfo Map of metadata associated with the document
     * @return GeboDocument containing the processed text-only content
     * @throws GeboIngestionException If an error occurs during ingestion
     * @throws IOException If an I/O error occurs
     */
	@Override
	public GeboDocument handleTextOnlyContent(GDocumentReference reference, InputStream is,
			Map<String, Object> manageMetainfo) throws GeboIngestionException, IOException {
		return internalTextFileFormatTextOnlyContent(reference, "text/html", is, manageMetainfo);
	}
}