package ai.gebo.architecture.search.service;

import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class LinkTypeGuesser {

	// Multi-part extensions we want to preserve as a single "extension"
	private static final Set<String> MULTIPART_EXT = Set.of("tar.gz", "tar.bz2", "tar.xz", "tar.zst");

	// Minimal, practical mapping (extend as needed)
	private static final Map<String, String> EXT_TO_CONTENT_TYPE = Map.ofEntries(Map.entry("pdf", "application/pdf"),
			Map.entry("txt", "text/plain"), Map.entry("csv", "text/csv"), Map.entry("tsv", "text/tab-separated-values"),
			Map.entry("json", "application/json"), Map.entry("jsonl", "application/x-ndjson"),
			Map.entry("xml", "application/xml"), Map.entry("yaml", "application/yaml"),
			Map.entry("yml", "application/yaml"), Map.entry("html", "text/html"), Map.entry("htm", "text/html"),
			Map.entry("md", "text/markdown"), Map.entry("rtf", "application/rtf"),

			Map.entry("doc", "application/msword"),
			Map.entry("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
			Map.entry("xls", "application/vnd.ms-excel"),
			Map.entry("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
			Map.entry("ppt", "application/vnd.ms-powerpoint"),
			Map.entry("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"),

			Map.entry("zip", "application/zip"), Map.entry("gz", "application/gzip"),
			Map.entry("bz2", "application/x-bzip2"), Map.entry("xz", "application/x-xz"),
			Map.entry("tar", "application/x-tar"), Map.entry("7z", "application/x-7z-compressed"),

			Map.entry("png", "image/png"), Map.entry("jpg", "image/jpeg"), Map.entry("jpeg", "image/jpeg"),
			Map.entry("gif", "image/gif"), Map.entry("webp", "image/webp"), Map.entry("svg", "image/svg+xml"),

			Map.entry("mp3", "audio/mpeg"), Map.entry("wav", "audio/wav"), Map.entry("mp4", "video/mp4"),
			Map.entry("mkv", "video/x-matroska"),

			Map.entry("java", "text/x-java-source"), Map.entry("py", "text/x-python"),
			Map.entry("js", "text/javascript"), Map.entry("ts", "text/typescript"), Map.entry("css", "text/css"),
			Map.entry("yaml", "application/yaml"));

	public static String tryArgueContentType(String link) {
		if (link == null || link.isBlank())
			return null;

		String ext = tryArgueExtension(link);
		if (ext == null) {
			// As a fallback, try the JDK built-in filename-based guesser
			String cleaned = cleanUrlToPathLikeName(link);
			if (cleaned != null) {
				String guessed = URLConnection.guessContentTypeFromName(cleaned);
				return normalizeContentType(guessed);
			}
			return null;
		}

		String contentType = EXT_TO_CONTENT_TYPE.get(ext);
		if (contentType != null)
			return contentType;

		// Final fallback: ask JDK guesser using a synthetic filename
		String syntheticName = "file." + ext;
		String guessed = URLConnection.guessContentTypeFromName(syntheticName);
		return normalizeContentType(guessed);
	}

	public static String tryArgueExtension(String link) {
		if (link == null || link.isBlank())
			return null;

		String path = extractPathPart(link);
		if (path == null || path.isBlank())
			return null;

		// decode percent-encoding (best-effort)
		path = urlDecodeBestEffort(path);

		// isolate last path segment
		int slash = path.lastIndexOf('/');
		String lastSegment = (slash >= 0) ? path.substring(slash + 1) : path;
		if (lastSegment.isBlank())
			return null;

		// If it looks like a folder or an endpoint without filename, give up
		// e.g. /download or /api/files?id=123 (query already stripped, but path can
		// still be endpoint)
		if (!lastSegment.contains("."))
			return null;

		String lowered = lastSegment.toLowerCase(Locale.ROOT);

		// strip common suffix garbage (rare but happens)
		// e.g. "file.pdf/" shouldn't happen, but be defensive
		lowered = lowered.replaceAll("/+$", "");

		// multi-part extensions first
		for (String mp : MULTIPART_EXT) {
			if (lowered.endsWith("." + mp)) {
				return mp; // e.g. "tar.gz"
			}
		}

		// single extension
		int dot = lowered.lastIndexOf('.');
		if (dot < 0 || dot == lowered.length() - 1)
			return null;

		String ext = lowered.substring(dot + 1);

		// remove trailing punctuation sometimes present in copied links
		ext = ext.replaceAll("[^a-z0-9]+$", "");
		if (ext.isBlank())
			return null;

		// sanity: keep extensions short-ish
		if (ext.length() > 10)
			return null;

		return ext;
	}

	// ---------------- helpers ----------------

	/** Remove query/fragment and return the path portion (as in URL path). */
	private static String extractPathPart(String link) {
		String s = link.trim();

		// Remove fragment
		int hash = s.indexOf('#');
		if (hash >= 0)
			s = s.substring(0, hash);

		// Remove query
		int q = s.indexOf('?');
		if (q >= 0)
			s = s.substring(0, q);

		// At this point, s is like "https://host/a/b/file.pdf" or sometimes just
		// "/a/b/file.pdf"
		// Remove scheme+host if present
		int schemeIdx = s.indexOf("://");
		if (schemeIdx >= 0) {
			int pathStart = s.indexOf('/', schemeIdx + 3);
			if (pathStart < 0)
				return ""; // URL with no path
			s = s.substring(pathStart);
		}

		return s;
	}

	private static String urlDecodeBestEffort(String s) {
		try {
			// URLDecoder treats '+' as space; in paths '+' is literal but usually encoded
			// as %2B.
			// This is still “best effort” and generally acceptable for guessing extensions.
			return URLDecoder.decode(s, StandardCharsets.UTF_8);
		} catch (Exception e) {
			return s;
		}
	}

	/**
	 * Create something name-like usable by URLConnection.guessContentTypeFromName,
	 * based on the URL path segment.
	 */
	private static String cleanUrlToPathLikeName(String link) {
		String path = extractPathPart(link);
		if (path == null)
			return null;
		path = urlDecodeBestEffort(path);

		int slash = path.lastIndexOf('/');
		String lastSegment = (slash >= 0) ? path.substring(slash + 1) : path;
		if (lastSegment.isBlank())
			return null;
		return lastSegment;
	}

	private static String normalizeContentType(String ct) {
		if (ct == null)
			return null;
		// strip charset if present
		int semi = ct.indexOf(';');
		if (semi >= 0)
			ct = ct.substring(0, semi);
		ct = ct.trim();
		return ct.isBlank() ? null : ct;
	}
}
