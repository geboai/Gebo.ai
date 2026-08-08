package ai.gebo.webdavcms.handler.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.StringTokenizer;

import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.webdavcms.handler.model.WebdavNativePositionObject;
import ai.gebo.webdavcms.handler.model.WebdavNavigationCoordinates;
import ai.gebo.webdavcms.handler.model.WebdavPathComponent;
import ai.gebo.webdavcms.handler.model.WebdavPathNodeType;

public class WebdavNavigationUtil {

	public static final String FOLDER_PREFIX = "DAV-FOLDER:";
	public static final String FILE_PREFIX = "DAV-FILE:";
	public static final String HIERARCHY_SPLITTER = "|";

	public static GVirtualFilesystemRoot encodeRoot(String baseUri, String name) {
		GVirtualFilesystemRoot item = new GVirtualFilesystemRoot();
		item.setCode(encodeHrefPath(baseUri));
		item.setDescription(name);
		item.setUri(baseUri);
		return item;
	}

	public static String encodeHrefPath(String href) {
		return Base64.getEncoder().encodeToString(href.getBytes(StandardCharsets.UTF_8));
	}

	public static String decodeHrefPath(String encoded) {
		try {
			return new String(Base64.getDecoder().decode(encoded), StandardCharsets.UTF_8);
		} catch (IllegalArgumentException e) {
			try {
				return URLDecoder.decode(encoded, StandardCharsets.UTF_8.name());
			} catch (UnsupportedEncodingException ex) {
				return encoded;
			}
		}
	}

	public static PathInfo encodeAsFolder(String href, String name) {
		PathInfo info = new PathInfo();
		info.absolutePath = FOLDER_PREFIX + encodeHrefPath(href);
		info.name = name;
		info.folder = true;
		return info;
	}

	public static PathInfo encodeAsFile(String href, String name) {
		PathInfo info = new PathInfo();
		info.absolutePath = FILE_PREFIX + encodeHrefPath(href);
		info.name = name;
		info.folder = false;
		return info;
	}

	public static String decodeRoot(GVirtualFilesystemRoot vfs) {
		return decodeHrefPath(vfs.getCode());
	}

	public static String[] decodeFolders(List<VFilesystemReference> paths) {
		if (paths == null)
			return new String[0];
		return paths.stream().filter(x -> {
			return x.path != null && x.path.absolutePath != null
					&& x.path.absolutePath.startsWith(FOLDER_PREFIX);
		}).map(y -> {
			return decodeHrefPath(y.path.absolutePath.substring(FOLDER_PREFIX.length()));
		}).toList().toArray(new String[0]);
	}

	public static String[] decodeFiles(List<VFilesystemReference> paths) {
		if (paths == null)
			return new String[0];
		return paths.stream().filter(x -> {
			return x.path != null && x.path.absolutePath != null
					&& x.path.absolutePath.startsWith(FILE_PREFIX);
		}).map(y -> {
			return decodeHrefPath(y.path.absolutePath.substring(FILE_PREFIX.length()));
		}).toList().toArray(new String[0]);
	}

	public static PathInfo combine(PathInfo... pathinfos) {
		if (pathinfos != null && pathinfos.length > 0) {
			PathInfo out = new PathInfo();
			for (PathInfo pathInfo : pathinfos) {
				if (out.absolutePath == null || out.absolutePath.trim().length() == 0) {
					out.absolutePath = pathInfo.absolutePath;
					out.name = pathInfo.name;
					out.folder = pathInfo.folder;
				} else {
					out.absolutePath += (HIERARCHY_SPLITTER + pathInfo.absolutePath);
					out.name = pathInfo.name;
					out.folder = pathInfo.folder;
				}
			}
			return out;
		}
		return null;
	}

	public static List<PathInfo> splitPath(PathInfo path) {
		if (path == null || path.absolutePath == null) {
			return List.of();
		}
		StringTokenizer tokenizer = new StringTokenizer(path.absolutePath, HIERARCHY_SPLITTER);
		List<PathInfo> out = new ArrayList<PathInfo>();
		while (tokenizer.hasMoreTokens()) {
			PathInfo thisPath = new PathInfo();
			thisPath.absolutePath = tokenizer.nextToken();
			thisPath.folder = tokenizer.hasMoreTokens();
			thisPath.name = null;
			out.add(thisPath);
		}
		if (!out.isEmpty()) {
			out.get(out.size() - 1).name = path.name;
			out.get(out.size() - 1).folder = path.folder;
		}
		return out;
	}

	public static List<WebdavPathComponent> toCustomSteps(List<PathInfo> browsingSteps) {
		List<WebdavPathComponent> out = new ArrayList<WebdavPathComponent>();
		if (browsingSteps != null)
			for (PathInfo pathInfo : browsingSteps) {
				out.add(toWebdavPathComponent(pathInfo));
			}
		return out;
	}

	private static WebdavPathComponent toWebdavPathComponent(PathInfo pathInfo) {
		WebdavPathComponent component = new WebdavPathComponent();
		if (pathInfo.absolutePath.startsWith(FILE_PREFIX)) {
			component.type = WebdavPathNodeType.FILE;
			component.id = decodeHrefPath(pathInfo.absolutePath.substring(FILE_PREFIX.length()));
		} else if (pathInfo.absolutePath.startsWith(FOLDER_PREFIX)) {
			component.type = WebdavPathNodeType.FOLDER;
			component.id = decodeHrefPath(pathInfo.absolutePath.substring(FOLDER_PREFIX.length()));
		} else {
			throw new RuntimeException("AbsolutePath:" + pathInfo.absolutePath + " not understood");
		}
		return component;
	}

	public static WebdavNavigationCoordinates toNavigationCoordinates(
			List<WebdavNativePositionObject> nativeCoordinates) {
		WebdavNavigationCoordinates coordinates = new WebdavNavigationCoordinates();
		if (!nativeCoordinates.isEmpty()) {
			WebdavNativePositionObject root = nativeCoordinates.get(0);
			coordinates.setRoot(toRoot(root));
			for (int i = 1; i < nativeCoordinates.size(); i++) {
				WebdavNativePositionObject nativeValue = nativeCoordinates.get(i);
				coordinates.getBrowsingSteps().add(toPathInfo(nativeValue));
				coordinates.getBrowsingStepsCustom().add(toCustomPathInfo(nativeValue));
			}
		}
		return coordinates;
	}

	private static WebdavPathComponent toCustomPathInfo(WebdavNativePositionObject nativeValue) {
		WebdavPathComponent component = new WebdavPathComponent();
		component.id = nativeValue.getCode();
		component.type = nativeValue.isWebdavFolder() ? WebdavPathNodeType.FOLDER : WebdavPathNodeType.FILE;
		return component;
	}

	private static PathInfo toPathInfo(WebdavNativePositionObject nativeValue) {
		PathInfo path = new PathInfo();
		String prefix = nativeValue.isWebdavFolder() ? FOLDER_PREFIX : FILE_PREFIX;
		path.absolutePath = prefix + encodeHrefPath(nativeValue.getCode());
		path.name = nativeValue.getName();
		path.folder = nativeValue.isFolder();
		return path;
	}

	private static GVirtualFilesystemRoot toRoot(WebdavNativePositionObject root) {
		GVirtualFilesystemRoot vfsroot = new GVirtualFilesystemRoot();
		vfsroot.setAbsolutePath(root.getCode());
		vfsroot.setDescription(root.getName());
		vfsroot.setUri(root.getUrl());
		return vfsroot;
	}
}