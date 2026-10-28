/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.agent.standardlibraries;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.util.json.schema.JsonSchemaGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.architecture.ai.model.AIResource;
import ai.gebo.architecture.ai.model.AIResourceCategory;
import ai.gebo.architecture.ai.model.AIResourceContent;
import ai.gebo.architecture.ai.model.AIResourceType;
import ai.gebo.architecture.ai.model.AIResourceType.ConsentActions;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryInstance;
import ai.gebo.architecture.ai.model.AIToolsAndResourcesLibraryType;
import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.architecture.ai.service.IGAIToolsAndResourcesLibrarySource;
import ai.gebo.architecture.ai.service.ToolCallbackDeclarationUtil;
import lombok.Data;

/**
 * Working directories: the first library of tools and resources. An instance is
 * a folder of its own under the configured root, with file tools bound to it;
 * an agent builds what it is asked for as files and folders in it, and whoever
 * reads the conversation takes the whole folder away as a zip, at any moment.
 * <p>
 * The folder is the pool every agent of the conversation works on, from the
 * first run to the last. Nothing in it is read or written outside it: every
 * path a tool is given is resolved inside the folder, and one that would lead
 * out is refused. The folder keeps a small metadata file of its own
 * ({@value #METADATA_FILE}), which the tools neither list nor touch.
 */
@Service
@ConditionalOnProperty(prefix = "ai.gebo.libraries.working-directory", name = "enabled", havingValue = "true", matchIfMissing = true)
public class WorkingDirectoryLibrarySource implements IGAIToolsAndResourcesLibrarySource {
	private static final Logger LOGGER = LoggerFactory.getLogger(WorkingDirectoryLibrarySource.class);
	public static final String SOURCE_ID = "working-directories";
	public static final String TYPE_ID = "working-directory";
	/** The one resource of an instance: the folder, as a whole. */
	public static final String FILES_RESOURCE_ID = "files";
	static final String METADATA_FILE = ".gebo-working-directory.json";
	public static final String LIST_FILES_TOOL = "listFiles";
	public static final String READ_FILE_TOOL = "readFile";
	public static final String WRITE_FILE_TOOL = "writeFile";
	public static final String CREATE_FOLDER_TOOL = "createFolder";
	public static final String DELETE_ENTRY_TOOL = "deleteEntry";
	public static final String MOVE_ENTRY_TOOL = "moveEntry";
	private static final String LIST_FILES_DESCRIPTION = "Lists the files and folders of the working directory, or of one of its folders: their path, kind and size. Call it before reading or writing, to know what is there.";
	private static final String READ_FILE_DESCRIPTION = "Reads a text file of the working directory and returns its content.";
	private static final String WRITE_FILE_DESCRIPTION = "Writes a text file in the working directory with the given content, creating it and its folders when missing, replacing it when it exists.";
	private static final String CREATE_FOLDER_DESCRIPTION = "Creates a folder in the working directory, and the folders leading to it.";
	private static final String DELETE_ENTRY_DESCRIPTION = "Deletes a file, or a folder with everything in it, from the working directory.";
	private static final String MOVE_ENTRY_DESCRIPTION = "Moves or renames a file or a folder inside the working directory.";

	private final WorkingDirectoriesConfig config;
	private final ObjectMapper mapper = new ObjectMapper();
	private final AIToolsAndResourcesLibraryType type;

	public WorkingDirectoryLibrarySource(WorkingDirectoriesConfig config) {
		this.config = config;
		this.type = declareType();
		LOGGER.info("Working directories of the agents live under: " + config.rootPath());
	}

	// ------------------------------------------------------------ the type

	/** What a working directory is, declared once: its tools and the one kind of resource it holds. */
	private AIToolsAndResourcesLibraryType declareType() {
		final AIResourceCategory category = new AIResourceCategory();
		category.setId("file-system");
		category.setDescription("Files and folders");
		final AIResourceType files = new AIResourceType();
		files.setId(FILES_RESOURCE_ID);
		files.setDescription("the files and folders of a working directory");
		files.setConsented(List.of(ConsentActions.READ_INFORMATIONS, ConsentActions.WRITE_INFORMATIONS));
		files.setCategory(category);
		files.setDownloadable(true);
		final AIToolsAndResourcesLibraryType declared = new AIToolsAndResourcesLibraryType();
		declared.setId(TYPE_ID);
		declared.setDescription("A working directory: files and folders the agents build and read, with file tools");
		declared.setResourcesTypes(List.of(files));
		declared.setTools(List.of(reference(LIST_FILES_TOOL, LIST_FILES_DESCRIPTION, "List the files", ListParam.class),
				reference(READ_FILE_TOOL, READ_FILE_DESCRIPTION, "Read a file", PathParam.class),
				reference(WRITE_FILE_TOOL, WRITE_FILE_DESCRIPTION, "Write a file", WriteParam.class),
				reference(CREATE_FOLDER_TOOL, CREATE_FOLDER_DESCRIPTION, "Create a folder", PathParam.class),
				reference(DELETE_ENTRY_TOOL, DELETE_ENTRY_DESCRIPTION, "Delete a file or a folder", PathParam.class),
				reference(MOVE_ENTRY_TOOL, MOVE_ENTRY_DESCRIPTION, "Move or rename", MoveParam.class)));
		return declared;
	}

	/** A tool reference whose input schema is the one the bound tool declares, so the stand-in matches it. */
	private static ToolReference reference(String name, String description, String shownAs, Class<?> paramType) {
		final ToolReference reference = new ToolReference();
		reference.setName(name);
		reference.setDescription(description);
		reference.setUserUIfunctionDescription(shownAs);
		reference.setInputSchema(JsonSchemaGenerator.generateForType(paramType));
		return reference;
	}

	@Override
	public String getId() {
		return SOURCE_ID;
	}

	@Override
	public String getDescription() {
		return "Working directories the agents build files and folders in";
	}

	@Override
	public List<AIToolsAndResourcesLibraryType> getManagedTypes() {
		return List.of(type);
	}

	// ------------------------------------------------------------ the instances

	/** What the folder keeps of its own: whose it is. */
	@Data
	public static class WorkingDirectoryMetadata {
		private String id;
		private String typeId;
		private List<String> owners = new ArrayList<>();
		private Date created;
	}

	private Path folderOf(String instanceId) {
		return config.rootPath().resolve(instanceId).normalize();
	}

	@Override
	public AIToolsAndResourcesLibraryInstance getInstanceById(String id, String owner) {
		if (id == null || id.isBlank()) {
			return null;
		}
		try {
			// an id is a UUID, and nothing else names a folder here
			if (!id.equals(UUID.fromString(id).toString())) {
				return null;
			}
		} catch (IllegalArgumentException notAnId) {
			LOGGER.warn("Not the id of a working directory: " + id);
			return null;
		}
		final Path folder = folderOf(id);
		final Path metadataFile = folder.resolve(METADATA_FILE);
		if (!Files.isDirectory(folder) || !Files.isRegularFile(metadataFile)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("There is no working directory:" + id);
			}
			return null;
		}
		final WorkingDirectoryMetadata metadata;
		try {
			metadata = mapper.readValue(metadataFile.toFile(), WorkingDirectoryMetadata.class);
		} catch (IOException unreadable) {
			LOGGER.error("The metadata of working directory:" + id + " cannot be read", unreadable);
			return null;
		}
		if (owner != null && (metadata.getOwners() == null || !metadata.getOwners().contains(owner))) {
			LOGGER.warn("Working directory:" + id + " is not owned by:" + owner + " (owners: " + metadata.getOwners()
					+ ")");
			return null;
		}
		return instanceOf(folder, metadata);
	}

	@Override
	public AIToolsAndResourcesLibraryInstance createInstance(AIToolsAndResourcesLibraryType type,
			List<String> owners) {
		if (type == null || !TYPE_ID.equals(type.getId())) {
			throw new IllegalArgumentException("Not a working directory type: " + (type != null ? type.getId() : null));
		}
		final WorkingDirectoryMetadata metadata = new WorkingDirectoryMetadata();
		metadata.setId(UUID.randomUUID().toString());
		metadata.setTypeId(TYPE_ID);
		metadata.setOwners(owners != null ? new ArrayList<>(owners) : new ArrayList<>());
		metadata.setCreated(new Date());
		final Path folder = folderOf(metadata.getId());
		try {
			Files.createDirectories(folder);
			mapper.writerWithDefaultPrettyPrinter().writeValue(folder.resolve(METADATA_FILE).toFile(), metadata);
		} catch (IOException notCreated) {
			throw new UncheckedIOException("The working directory " + folder + " could not be created", notCreated);
		}
		LOGGER.info("Created working directory:" + metadata.getId() + " for " + metadata.getOwners() + " at " + folder);
		return instanceOf(folder, metadata);
	}

	@Override
	public void dropInstance(AIToolsAndResourcesLibraryInstance instance) {
		if (instance == null || instance.getId() == null) {
			return;
		}
		final Path folder = folderOf(instance.getId());
		if (!Files.isDirectory(folder) || !Files.isRegularFile(folder.resolve(METADATA_FILE))) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Working directory:" + instance.getId() + " is gone already");
			}
			return;
		}
		try (Stream<Path> walk = Files.walk(folder)) {
			for (Path path : walk.sorted(Comparator.reverseOrder()).toList()) {
				Files.deleteIfExists(path);
			}
		} catch (IOException notDropped) {
			throw new UncheckedIOException("The working directory " + folder + " could not be dropped", notDropped);
		}
		LOGGER.info("Dropped working directory:" + instance.getId());
	}

	/** The instance as the agents and the people see it: the folder and what it holds now. */
	private AIToolsAndResourcesLibraryInstance instanceOf(Path folder, WorkingDirectoryMetadata metadata) {
		final AIToolsAndResourcesLibraryInstance instance = new AIToolsAndResourcesLibraryInstance();
		instance.setId(metadata.getId());
		instance.setLibraryType(type);
		instance.setOwnersId(metadata.getOwners() != null ? new ArrayList<>(metadata.getOwners()) : new ArrayList<>());
		final int files = countFiles(folder);
		instance.setDescription("a working directory of your own, where you build and keep files and folders ("
				+ (files == 0 ? "empty so far" : files + " file(s) in it") + ")");
		final AIResource resource = new AIResource();
		resource.setId(FILES_RESOURCE_ID);
		resource.setResourceType(type.getResourcesTypes().get(0));
		resource.setDescription("everything in the working directory, downloadable as a zip");
		instance.setResources(List.of(resource));
		instance.setTools(type.getTools());
		return instance;
	}

	private static int countFiles(Path folder) {
		try (Stream<Path> walk = Files.walk(folder)) {
			return (int) walk.filter(Files::isRegularFile).filter(p -> !METADATA_FILE.equals(p.getFileName().toString()))
					.count();
		} catch (IOException unreadable) {
			return 0;
		}
	}

	// ------------------------------------------------------------ the download

	@Override
	public AIResourceContent download(AIToolsAndResourcesLibraryInstance instance, String resourceId) {
		if (instance == null || !FILES_RESOURCE_ID.equals(resourceId)) {
			return null;
		}
		final Path folder = folderOf(instance.getId());
		if (!Files.isDirectory(folder)) {
			LOGGER.warn("Working directory:" + instance.getId() + " is gone: nothing to download");
			return null;
		}
		try {
			final Path zip = Files.createTempFile("working-directory-" + instance.getId() + "-", ".zip");
			int entries = 0;
			try (ZipOutputStream out = new ZipOutputStream(Files.newOutputStream(zip));
					Stream<Path> walk = Files.walk(folder)) {
				for (Path path : walk.sorted().toList()) {
					if (path.equals(folder) || METADATA_FILE.equals(path.getFileName().toString())) {
						continue;
					}
					final String name = folder.relativize(path).toString().replace('\\', '/');
					if (Files.isDirectory(path)) {
						out.putNextEntry(new ZipEntry(name + "/"));
						out.closeEntry();
					} else {
						out.putNextEntry(new ZipEntry(name));
						Files.copy(path, out);
						out.closeEntry();
					}
					entries++;
				}
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Working directory:" + instance.getId() + " zipped with " + entries + " entry(ies), "
						+ Files.size(zip) + " byte(s)");
			}
			// the temp file goes with the stream: whoever downloads closes it
			final InputStream content = new java.io.FilterInputStream(Files.newInputStream(zip)) {
				@Override
				public void close() throws IOException {
					try {
						super.close();
					} finally {
						Files.deleteIfExists(zip);
					}
				}
			};
			return new AIResourceContent("application/zip", "working-directory-" + instance.getId() + ".zip", content);
		} catch (IOException notZipped) {
			throw new UncheckedIOException("The working directory " + folder + " could not be zipped", notZipped);
		}
	}

	// ------------------------------------------------------------ the tools

	@JsonClassDescription("Which folder to list")
	@Data
	public static class ListParam {
		@JsonPropertyDescription("Optional path of a folder inside the working directory, relative to it; the working directory itself when not given")
		private String path;
		@JsonPropertyDescription("Optional: true to list the folders' content too, at every depth; false, the default, lists one level")
		private Boolean recursive;
	}

	@JsonClassDescription("A path inside the working directory")
	@Data
	public static class PathParam {
		@JsonPropertyDescription("The path, relative to the working directory, e.g. notes/summary.md")
		private String path;
	}

	@JsonClassDescription("A text file to write")
	@Data
	public static class WriteParam {
		@JsonPropertyDescription("The path of the file, relative to the working directory, e.g. report/chapter-1.md")
		private String path;
		@JsonPropertyDescription("The whole content of the file, as text")
		private String content;
	}

	@JsonClassDescription("What to move, and where")
	@Data
	public static class MoveParam {
		@JsonPropertyDescription("The path of the file or folder to move, relative to the working directory")
		private String from;
		@JsonPropertyDescription("Its new path, relative to the working directory")
		private String to;
	}

	/** One entry of a listing. */
	public record Entry(String path, String kind, long size) {
	}

	/** What a listing says: the folder listed and what is in it. */
	public record Listing(String folder, List<Entry> entries) {
	}

	/** What a change answers: what was done, in a line. */
	public record Done(String done) {
	}

	@Override
	public List<ToolCallback> getToolCallbacks(AIToolsAndResourcesLibraryInstance instance) {
		final Path folder = folderOf(instance.getId());
		final List<ToolCallback> callbacks = new ArrayList<>();
		callbacks.add(ToolCallbackDeclarationUtil.declare(
				(BiFunction<ListParam, ToolContext, Listing>) (param, context) -> listFiles(folder, param),
				LIST_FILES_TOOL, LIST_FILES_DESCRIPTION, ListParam.class, Listing.class));
		callbacks.add(ToolCallbackDeclarationUtil.declare(
				(BiFunction<PathParam, ToolContext, String>) (param, context) -> readFile(folder, param), READ_FILE_TOOL,
				READ_FILE_DESCRIPTION, PathParam.class, String.class));
		callbacks.add(ToolCallbackDeclarationUtil.declare(
				(BiFunction<WriteParam, ToolContext, Done>) (param, context) -> writeFile(folder, param),
				WRITE_FILE_TOOL, WRITE_FILE_DESCRIPTION, WriteParam.class, Done.class));
		callbacks.add(ToolCallbackDeclarationUtil.declare(
				(BiFunction<PathParam, ToolContext, Done>) (param, context) -> createFolder(folder, param),
				CREATE_FOLDER_TOOL, CREATE_FOLDER_DESCRIPTION, PathParam.class, Done.class));
		callbacks.add(ToolCallbackDeclarationUtil.declare(
				(BiFunction<PathParam, ToolContext, Done>) (param, context) -> deleteEntry(folder, param),
				DELETE_ENTRY_TOOL, DELETE_ENTRY_DESCRIPTION, PathParam.class, Done.class));
		callbacks.add(ToolCallbackDeclarationUtil.declare(
				(BiFunction<MoveParam, ToolContext, Done>) (param, context) -> moveEntry(folder, param),
				MOVE_ENTRY_TOOL, MOVE_ENTRY_DESCRIPTION, MoveParam.class, Done.class));
		return callbacks;
	}

	/**
	 * The path inside the folder a tool was given, or an exception the model
	 * reads: nothing outside the working directory is ever reached, and the
	 * folder's own metadata file is nobody's.
	 */
	static Path inside(Path folder, String given) {
		final String relative = given != null ? given.trim() : "";
		final Path resolved = folder.resolve(relative.replace('\\', '/')).toAbsolutePath().normalize();
		if (!resolved.startsWith(folder.toAbsolutePath().normalize())) {
			throw new IllegalArgumentException("The path leads outside the working directory: " + given);
		}
		if (METADATA_FILE.equals(resolved.getFileName() != null ? resolved.getFileName().toString() : null)) {
			throw new IllegalArgumentException("The path is reserved: " + given);
		}
		return resolved;
	}

	private static String relative(Path folder, Path path) {
		final String name = folder.toAbsolutePath().normalize().relativize(path).toString().replace('\\', '/');
		return name.isEmpty() ? "." : name;
	}

	Listing listFiles(Path folder, ListParam param) {
		final Path listed = inside(folder, param != null ? param.getPath() : null);
		if (!Files.isDirectory(listed)) {
			throw new IllegalArgumentException("There is no such folder: " + (param != null ? param.getPath() : "."));
		}
		final boolean recursive = param != null && Boolean.TRUE.equals(param.getRecursive());
		final List<Entry> entries = new ArrayList<>();
		try (Stream<Path> walk = recursive ? Files.walk(listed) : Files.list(listed)) {
			for (Path path : walk.sorted().toList()) {
				if (path.equals(listed) || METADATA_FILE.equals(path.getFileName().toString())) {
					continue;
				}
				entries.add(new Entry(relative(folder, path), Files.isDirectory(path) ? "folder" : "file",
						Files.isDirectory(path) ? 0 : Files.size(path)));
			}
		} catch (IOException unreadable) {
			throw new UncheckedIOException("The folder could not be listed", unreadable);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(LIST_FILES_TOOL + " on " + folder.getFileName() + " path:" + relative(folder, listed)
					+ " recursive:" + recursive + " -> " + entries.size() + " entry(ies)");
		}
		return new Listing(relative(folder, listed), entries);
	}

	String readFile(Path folder, PathParam param) {
		final Path file = inside(folder, param != null ? param.getPath() : null);
		if (!Files.isRegularFile(file)) {
			throw new IllegalArgumentException("There is no such file: " + (param != null ? param.getPath() : null));
		}
		try {
			final String content = Files.readString(file, StandardCharsets.UTF_8);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(READ_FILE_TOOL + " on " + folder.getFileName() + " path:" + relative(folder, file) + " -> "
						+ content.length() + " character(s)");
			}
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("<READ_FILE path=" + relative(folder, file) + ">");
				LOGGER.trace(content);
				LOGGER.trace("</READ_FILE>");
			}
			return content;
		} catch (IOException unreadable) {
			throw new UncheckedIOException("The file could not be read: " + param.getPath(), unreadable);
		}
	}

	Done writeFile(Path folder, WriteParam param) {
		final Path file = inside(folder, param != null ? param.getPath() : null);
		if (file.equals(folder.toAbsolutePath().normalize()) || Files.isDirectory(file)) {
			throw new IllegalArgumentException("Not a file path: " + (param != null ? param.getPath() : null));
		}
		final String content = param.getContent() != null ? param.getContent() : "";
		try {
			Files.createDirectories(file.getParent());
			final boolean existed = Files.exists(file);
			Files.writeString(file, content, StandardCharsets.UTF_8, StandardOpenOption.CREATE,
					StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(WRITE_FILE_TOOL + " on " + folder.getFileName() + " path:" + relative(folder, file) + " "
						+ (existed ? "replaced" : "created") + " with " + content.length() + " character(s)");
			}
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("<WRITE_FILE path=" + relative(folder, file) + ">");
				LOGGER.trace(content);
				LOGGER.trace("</WRITE_FILE>");
			}
			return new Done((existed ? "Replaced " : "Created ") + relative(folder, file) + " (" + content.length()
					+ " characters)");
		} catch (IOException notWritten) {
			throw new UncheckedIOException("The file could not be written: " + param.getPath(), notWritten);
		}
	}

	Done createFolder(Path folder, PathParam param) {
		final Path created = inside(folder, param != null ? param.getPath() : null);
		try {
			Files.createDirectories(created);
		} catch (IOException notCreated) {
			throw new UncheckedIOException("The folder could not be created: " + param.getPath(), notCreated);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(CREATE_FOLDER_TOOL + " on " + folder.getFileName() + " path:" + relative(folder, created));
		}
		return new Done("Created folder " + relative(folder, created));
	}

	Done deleteEntry(Path folder, PathParam param) {
		final Path deleted = inside(folder, param != null ? param.getPath() : null);
		if (deleted.equals(folder.toAbsolutePath().normalize())) {
			throw new IllegalArgumentException("The working directory itself cannot be deleted");
		}
		if (!Files.exists(deleted)) {
			throw new IllegalArgumentException("There is no such file or folder: " + param.getPath());
		}
		int count = 0;
		try (Stream<Path> walk = Files.walk(deleted)) {
			for (Path path : walk.sorted(Comparator.reverseOrder()).toList()) {
				Files.deleteIfExists(path);
				count++;
			}
		} catch (IOException notDeleted) {
			throw new UncheckedIOException("Could not delete: " + param.getPath(), notDeleted);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(DELETE_ENTRY_TOOL + " on " + folder.getFileName() + " path:" + relative(folder, deleted)
					+ " -> " + count + " entry(ies) deleted");
		}
		return new Done("Deleted " + relative(folder, deleted) + (count > 1 ? " (" + count + " entries)" : ""));
	}

	Done moveEntry(Path folder, MoveParam param) {
		final Path from = inside(folder, param != null ? param.getFrom() : null);
		final Path to = inside(folder, param != null ? param.getTo() : null);
		if (!Files.exists(from)) {
			throw new IllegalArgumentException("There is no such file or folder: " + param.getFrom());
		}
		try {
			Files.createDirectories(to.getParent());
			Files.move(from, to, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException notMoved) {
			throw new UncheckedIOException("Could not move " + param.getFrom() + " to " + param.getTo(), notMoved);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(MOVE_ENTRY_TOOL + " on " + folder.getFileName() + " " + relative(folder, from) + " -> "
					+ relative(folder, to));
		}
		return new Done("Moved " + relative(folder, from) + " to " + relative(folder, to));
	}
}
