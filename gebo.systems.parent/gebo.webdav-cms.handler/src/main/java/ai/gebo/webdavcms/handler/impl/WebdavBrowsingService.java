package ai.gebo.webdavcms.handler.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.sardine.DavResource;
import com.github.sardine.Sardine;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.model.virtualfs.BrowseParam;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.restintegration.abstraction.layer.GeboRestIntegrationException;
import ai.gebo.restintegration.abstraction.layer.RestTemplateWrapperService;
import ai.gebo.systems.abstraction.layer.IGVirtualFilesystemBrowsingService;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;
import ai.gebo.webdavcms.handler.GWebdavBrowsingContext;
import ai.gebo.webdavcms.handler.GWebdavContentManagementSystem;
import ai.gebo.webdavcms.handler.repositories.WebdavContentManagementSystemRepository;

@Service
public class WebdavBrowsingService implements IGVirtualFilesystemBrowsingService<GWebdavBrowsingContext> {
	private static final Logger LOGGER = LoggerFactory.getLogger(WebdavBrowsingService.class);

	@Autowired
	protected WebdavConnectionFactory connectionFactory;

	@Autowired
	protected WebdavContentManagementSystemRepository systemsRepo;

	public WebdavBrowsingService() {
	}

	private GWebdavContentManagementSystem get(String id) {
		Optional<GWebdavContentManagementSystem> r = systemsRepo.findById(id);
		if (r.isPresent())
			return r.get();
		throw new IllegalStateException("Unknown system=>" + id);
	}

	private OperationStatus<List<GVirtualFilesystemRoot>> getRoots(String systemCode)
			throws VirtualFilesystemBrowsingException {
		List<GVirtualFilesystemRoot> contents = new ArrayList<GVirtualFilesystemRoot>();
		try {
			GWebdavContentManagementSystem system = get(systemCode);
			String baseUri = system.getBaseUri();
			if (baseUri != null && !baseUri.isEmpty()) {
				Sardine sardine = connectionFactory.getConnection(system);
				// The WebDAV "root" is simply the configured base URI: a depth-0
				// PROPFIND on it only returns the collection's own DavResource, which
				// isSelfOrParent always filters out, so listing can never surface it.
				// Probe connectivity/credentials, then hand back the base URI itself.
				sardine.exists(baseUri);
				contents.add(WebdavNavigationUtil.encodeRoot(baseUri, system.getDescription()));
			}
		} catch (GeboCryptSecretException e) {
			throw new VirtualFilesystemBrowsingException("Cryptation problem", e);
		} catch (IOException e) {
			GUserMessage message = RestTemplateWrapperService.toMessage(
					new GeboRestIntegrationException(e.getMessage(), e), " WebDAV ", "roots list");
			OperationStatus<List<GVirtualFilesystemRoot>> status = OperationStatus.of(contents);
			status.getMessages().clear();
			status.getMessages().add(message);
			return status;
		}
		return OperationStatus.of(contents);
	}

	private OperationStatus<List<PathInfo>> browsePath(BrowseParam param, String systemCode)
			throws VirtualFilesystemBrowsingException {
		List<PathInfo> paths = new ArrayList<PathInfo>();
		try {
			GWebdavContentManagementSystem system = get(systemCode);
			Sardine sardine = connectionFactory.getConnection(system);
			String href;
			if (param.path != null && param.path.absolutePath != null) {
				href = WebdavNavigationUtil.decodeHrefPath(
						param.path.absolutePath.startsWith(WebdavNavigationUtil.FOLDER_PREFIX)
								? param.path.absolutePath.substring(WebdavNavigationUtil.FOLDER_PREFIX.length())
								: param.path.absolutePath);
			} else if (param.root != null) {
				href = WebdavNavigationUtil.decodeRoot(param.root);
			} else {
				href = system.getBaseUri();
			}

			List<DavResource> resources = sardine.list(href, 1);
			for (DavResource res : resources) {
				if (!isSelfOrParent(res)) {
					if (res.isDirectory()) {
						paths.add(WebdavNavigationUtil.encodeAsFolder(res.getHref().toString(), res.getName()));
					} else {
						paths.add(WebdavNavigationUtil.encodeAsFile(res.getHref().toString(), res.getName()));
					}
				}
			}
		} catch (GeboCryptSecretException e) {
			throw new VirtualFilesystemBrowsingException("Cryptation problem", e);
		} catch (IOException e) {
			LOGGER.warn("Resource not found on " + param.toString(), e);
			OperationStatus<List<PathInfo>> result = OperationStatus.of(paths);
			result.getMessages().clear();
			result.getMessages().add(GUserMessage.warnMessage("WebDAV path with no object found", ""));
			return result;
		}
		return OperationStatus.of(paths);
	}

	private boolean isSelfOrParent(DavResource res) {
		String name = res.getName();
		if (name == null)
			return false;
		String trimmed = name.trim();
		if (trimmed.isEmpty() || ".".equals(trimmed) || "..".equals(trimmed))
			return true;
		String href = res.getHref().toString();
		if (href != null && href.endsWith("/") && href.length() > 1) {
			int lastSlash = href.substring(0, href.length() - 1).lastIndexOf('/');
			if (lastSlash >= 0) {
				String parentHref = href.substring(lastSlash + 1);
				if (parentHref.isEmpty())
					return true;
			}
		}
		return false;
	}

	@Override
	public OperationStatus<List<GVirtualFilesystemRoot>> getRoots(GWebdavBrowsingContext context)
			throws VirtualFilesystemBrowsingException {
		return getRoots(context.getSystemCode());
	}

	@Override
	public OperationStatus<List<PathInfo>> browse(BrowseParam param, GWebdavBrowsingContext context)
			throws VirtualFilesystemBrowsingException {
		return browsePath(param, context.getSystemCode());
	}

	@Override
	public boolean isSupportsNavigationStatus() {
		return false;
	}

	@Override
	public VFilesystemReference getParent(VFilesystemReference reference, GWebdavBrowsingContext context)
			throws VirtualFilesystemBrowsingException {
		return null;
	}
}