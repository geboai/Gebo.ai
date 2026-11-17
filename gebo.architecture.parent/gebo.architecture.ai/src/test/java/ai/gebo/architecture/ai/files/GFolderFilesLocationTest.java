package ai.gebo.architecture.ai.files;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import ai.gebo.model.base.TypedInputStream;

class GFolderFilesLocationTest {
	@TempDir
	Path root;

	private static TypedInputStream text(String content) {
		return TypedInputStream.of(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)), "text/plain",
				".txt");
	}

	private static String content(GLibraryFileContent read) throws IOException {
		return new String(read.content().getInputStream().readAllBytes(), StandardCharsets.UTF_8);
	}

	@Test
	void filesAreWrittenListedAndReadWithTheirVersion() throws IOException {
		final GFolderFilesLocation location = new GFolderFilesLocation("working-directory", "a folder", root, true);
		final GLibraryFile written = location.write("reports/q1.xlsx", text("one"), IGFilesLocation.ABSENT);
		assertEquals("working-directory", written.location());
		assertEquals("reports/q1.xlsx", written.path());
		assertEquals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", written.contentType());
		location.write("notes.txt", text("note"), null);
		Files.writeString(root.resolve(".metadata.json"), "{}");
		Files.createDirectories(root.resolve(".hidden"));
		Files.writeString(root.resolve(".hidden/secret.txt"), "x");

		assertEquals(List.of("notes.txt"), location.list(null, false).stream().map(GLibraryFile::path).toList());
		assertEquals(List.of("notes.txt", "reports/q1.xlsx"),
				location.list("", true).stream().map(GLibraryFile::path).toList());
		assertEquals(List.of("reports/q1.xlsx"), location.list("reports", false).stream().map(GLibraryFile::path).toList());

		final GLibraryFileContent read = location.read("reports\\q1.xlsx");
		assertEquals("one", content(read));
		assertEquals(written.version(), read.file().version());
		assertEquals(".xlsx", read.content().getExtension());
		assertNull(location.describe("missing.txt"));
	}

	@Test
	void aWriteNamingAVersionFailsWhenTheFileChangedMeanwhile() throws IOException {
		final GFolderFilesLocation location = new GFolderFilesLocation("lib", "a folder", root, true);
		final GLibraryFile first = location.write("a.txt", text("1"), null);
		assertThrows(GLibraryFileConflictException.class, () -> location.write("a.txt", text("x"), IGFilesLocation.ABSENT));
		// another writer, through another location on the same folder
		Files.writeString(root.resolve("a.txt"), "22");
		Files.setLastModifiedTime(root.resolve("a.txt"), FileTime.from(Instant.now().plusSeconds(5)));
		assertThrows(GLibraryFileConflictException.class, () -> location.write("a.txt", text("3"), first.version()));
		assertEquals("22", Files.readString(root.resolve("a.txt")));
		final GLibraryFile current = location.describe("a.txt");
		assertNotEquals(first.version(), current.version());
		final GLibraryFile rewritten = location.write("a.txt", text("333"), current.version());
		assertEquals(3, rewritten.size());
		Files.delete(root.resolve("a.txt"));
		assertThrows(GLibraryFileConflictException.class, () -> location.write("a.txt", text("4"), rewritten.version()));
		try (var left = Files.list(root)) {
			assertFalse(left.anyMatch(p -> p.getFileName().toString().startsWith(".saving-")), "no file left saving");
		}
	}

	@Test
	void nothingOutsideTheFolderNorTheSourceOwnFilesIsReached() throws IOException {
		final Path inside = Files.createDirectories(root.resolve("lib"));
		Files.writeString(root.resolve("outside.txt"), "out");
		final GFolderFilesLocation location = new GFolderFilesLocation("lib", "a folder", inside, true);
		for (String path : List.of("../outside.txt", "a/../../outside.txt", ".metadata.json", "x/.hidden/y.txt",
				root.resolve("outside.txt").toString())) {
			assertThrows(IllegalArgumentException.class, () -> location.read(path), path);
			assertThrows(IllegalArgumentException.class, () -> location.write(path, text("x"), null), path);
		}
		assertThrows(IllegalArgumentException.class, () -> location.read("missing.txt"));
		assertThrows(IllegalArgumentException.class, () -> location.write("", text("x"), null));
	}

	@Test
	void aReadOnlyLocationRefusesWritesAndSizesAreCapped() throws IOException {
		final GFolderFilesLocation readOnly = new GFolderFilesLocation("kb", "read only", root, false);
		assertThrows(UnsupportedOperationException.class, () -> readOnly.write("a.txt", text("x"), null));
		final GFolderFilesLocation small = new GFolderFilesLocation("lib", "small", root, true, 4);
		assertThrows(IllegalArgumentException.class, () -> small.write("big.txt", text("12345"), null));
		assertFalse(Files.exists(root.resolve("big.txt")));
		Files.writeString(root.resolve("big.txt"), "12345");
		assertThrows(IllegalArgumentException.class, () -> small.read("big.txt"));
		assertArrayEquals("12345".getBytes(StandardCharsets.UTF_8), Files.readAllBytes(root.resolve("big.txt")));
		assertTrue(small.isWritable());
	}
}
