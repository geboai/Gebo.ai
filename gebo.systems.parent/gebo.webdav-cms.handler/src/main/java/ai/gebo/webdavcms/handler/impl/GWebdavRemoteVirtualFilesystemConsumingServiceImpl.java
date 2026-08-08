package ai.gebo.webdavcms.handler.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.sardine.DavResource;
import com.github.sardine.Sardine;

import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumer;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.contenthandling.interfaces.IGUserMessagesConsumer;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.knlowledgebase.model.contents.GAbstractVirtualFilesystemObject;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.restintegration.abstraction.layer.GeboRestIntegrationException;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemConsumingService;
import ai.gebo.systems.abstraction.layer.IGContentsAccessErrorConsumer;
import ai.gebo.systems.abstraction.layer.model.ContentsAccessError;
import ai.gebo.systems.abstraction.layer.model.ContentsAccessError.ContentsAccessedObjectType;
import ai.gebo.webdavcms.handler.GWebdavContentManagementSystem;
import ai.gebo.webdavcms.handler.GWebdavProjectEndpoint;
import ai.gebo.webdavcms.handler.GWebdavResourceReference;
import ai.gebo.webdavcms.handler.IGWebdavVirtualFilesystemConsumingService;
import ai.gebo.webdavcms.handler.model.WebdavNativePositionObject;
import ai.gebo.webdavcms.handler.model.WebdavNavigationCoordinates;
import ai.gebo.webdavcms.handler.model.WebdavPathComponent;
import ai.gebo.webdavcms.handler.model.WebdavPathNodeType;

@Service
public class GWebdavRemoteVirtualFilesystemConsumingServiceImpl extends
		GAbstractRemoteVirtualFilesystemConsumingService<GWebdavContentManagementSystem, GWebdavProjectEndpoint, WebdavNativePositionObject, WebdavNavigationCoordinates, GWebdavResourceReference>
		implements IGWebdavVirtualFilesystemConsumingService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(GWebdavRemoteVirtualFilesystemConsumingServiceImpl.class);

	protected final WebdavConnectionFactory connectionFactory;

	private static final String DAV_CONNECTION = "DAV_CONNECTION";

	public GWebdavRemoteVirtualFilesystemConsumingServiceImpl(IGDocumentReferenceFactory documentFactory,
			WebdavConnectionFactory connectionFactory) {
		super(documentFactory);
		this.connectionFactory = connectionFactory;
	}

	private Sardine getSardine(Map<String, Object> environment) {
		return (Sardine) environment.get(DAV_CONNECTION);
	}

	@Override
	protected Map<String, Object> createEnvironment(GWebdavContentManagementSystem system,
			GWebdavProjectEndpoint endpoint, IGContentsAccessErrorConsumer errorConsumer)
			throws GeboContentHandlerSystemException {
		Map<String, Object> environment = new HashMap<String, Object>();
		try {
			environment.put(DAV_CONNECTION, connectionFactory.getConnection(system));
		} catch (GeboCryptSecretException e) {
			LOGGER.error("Cannot allocate WebDAV connection", e);
			throw new GeboContentHandlerSystemException(e.getMessage(), e);
		}
		return environment;
	}

	@Override
	protected Map<String, Object> createEnvironment(GWebdavContentManagementSystem system)
			throws GeboContentHandlerSystemException {
		Map<String, Object> environment = new HashMap<String, Object>();
		try {
			environment.put(DAV_CONNECTION, connectionFactory.getConnection(system));
		} catch (GeboCryptSecretException e) {
			LOGGER.error("Cannot allocate WebDAV connection", e);
			throw new GeboContentHandlerSystemException(e.getMessage(), e);
		}
		return environment;
	}

	@Override
	protected void clearEnvironment(Map<String, Object> environment, GWebdavContentManagementSystem system,
			GWebdavProjectEndpoint endpoint) throws GeboContentHandlerSystemException {
		environment.clear();
	}

	@Override
	protected WebdavNavigationCoordinates toNavigationPosition(VFilesystemReference path,
			Map<String, Object> environment) throws GeboContentHandlerSystemException {
		WebdavNavigationCoordinates coordinates = new WebdavNavigationCoordinates();
		coordinates.setRoot(path.root);
		if (path.path != null) {
			coordinates.setBrowsingSteps(WebdavNavigationUtil.splitPath(path.path));
			coordinates.setBrowsingStepsCustom(WebdavNavigationUtil.toCustomSteps(coordinates.getBrowsingSteps()));
		}
		return coordinates;
	}

	@Override
	protected List<WebdavNativePositionObject> toNativeCoordinates(WebdavNavigationCoordinates position,
			GWebdavContentManagementSystem system, GWebdavProjectEndpoint endpoint, GVirtualFolder root,
			IGContentConsumer consumer, IGUserMessagesConsumer messagesConsumer,
			IGContentsAccessErrorConsumer errorConsumer, Map<String, Object> environment)
			throws GeboContentHandlerSystemException {
		List<WebdavNativePositionObject> natives = new ArrayList<WebdavNativePositionObject>();
		try {
			WebdavNativePositionObject _root = loadRoot(position.getRoot(), system, environment);
			if (_root == null)
				return natives;
			natives.add(_root);
		} catch (GeboRestIntegrationException e) {
			errorConsumer
					.accept(ContentsAccessError.of(e, ContentsAccessedObjectType.FOLDER, position.toString()));
			throw new GeboContentHandlerSystemException("Cannot load this item", e);
		}
		try {
			if (position.getBrowsingStepsCustom() != null) {
				for (WebdavPathComponent customitem : position.getBrowsingStepsCustom()) {
					WebdavNativePositionObject thisPathEntry = loadPathComponent(customitem, natives, system,
							environment);
					if (thisPathEntry == null) {
						LOGGER.error("The path item:" + customitem.type + " id=" + customitem.id
								+ " does not lead to a loadable entity");
					}
					natives.add(thisPathEntry);
				}
			}
		} catch (GeboRestIntegrationException e) {
			errorConsumer
					.accept(ContentsAccessError.of(e, ContentsAccessedObjectType.FOLDER, position.toString()));
			throw new GeboContentHandlerSystemException("Cannot load this item", e);
		}
		return natives;
	}

	@Override
	protected List<WebdavNativePositionObject> toResourcesNativeCoordinates(WebdavNavigationCoordinates position,
			GWebdavContentManagementSystem system, Map<String, Object> environment)
			throws GeboContentHandlerSystemException {
		List<WebdavNativePositionObject> natives = new ArrayList<WebdavNativePositionObject>();
		if (position.getRoot() != null) {
			try {
				WebdavNativePositionObject _root = loadRoot(position.getRoot(), system, environment);
				if (_root == null)
					return natives;
				natives.add(_root);
			} catch (GeboRestIntegrationException e) {
				throw new GeboContentHandlerSystemException("Cannot load this item", e);
			}
		}
		try {
			if (position.getBrowsingStepsCustom() != null) {
				for (WebdavPathComponent customitem : position.getBrowsingStepsCustom()) {
					WebdavNativePositionObject thisPathEntry = loadPathComponent(customitem, natives, system,
							environment);
					if (thisPathEntry == null) {
						LOGGER.error("The path item:" + customitem.type + " id=" + customitem.id
								+ " does not lead to a loadable entity");
					}
					natives.add(thisPathEntry);
				}
			}
		} catch (GeboRestIntegrationException e) {
			throw new GeboContentHandlerSystemException("Cannot load this item", e);
		}
		return natives;
	}

	private WebdavNativePositionObject loadRoot(GVirtualFilesystemRoot root, GWebdavContentManagementSystem system,
			Map<String, Object> environment) throws GeboRestIntegrationException {
		try {
			Sardine sardine = getSardine(environment);
			String href = WebdavNavigationUtil.decodeRoot(root);
			List<DavResource> resources = sardine.list(href, 0);
			for (DavResource res : resources) {
				String resHref = res.getHref().toString();
				String rootHref = href;
				if (!rootHref.endsWith("/"))
					rootHref += "/";
				if (!resHref.endsWith("/"))
					resHref += "/";
				if (rootHref.equals(resHref) || resHref.equals(href)) {
					return WebdavNativePositionObject.createFolder(res.getHref().toString(), res.getName());
				}
			}
			return WebdavNativePositionObject.createFolder(href, root.getDescription());
		} catch (IOException e) {
			throw new GeboRestIntegrationException("Cannot load root: " + root.getCode(), e);
		}
	}

	private WebdavNativePositionObject loadPathComponent(WebdavPathComponent customitem,
			List<WebdavNativePositionObject> natives, GWebdavContentManagementSystem system,
			Map<String, Object> environment) throws GeboRestIntegrationException {
		try {
			Sardine sardine = getSardine(environment);
			String href = customitem.id;
			List<DavResource> resources = sardine.list(href, 0);
			for (DavResource res : resources) {
				if (res.getHref().toString().equals(href) || res.getHref().toString().equals(href + "/")) {
					if (res.isDirectory()) {
						return WebdavNativePositionObject.createFolder(res.getHref().toString(), res.getName());
					} else {
						return WebdavNativePositionObject.createFile(res.getHref().toString(), res.getName(),
								res.getContentType(), res.getContentLength(),
								res.getModified() != null ? res.getModified() : null);
					}
				}
			}
		} catch (IOException e) {
			throw new GeboRestIntegrationException("Cannot load path component: " + customitem.id, e);
		}
		return null;
	}

	@Override
	protected List<NativeCoordinatePointer> retrieveChilds(List<WebdavNativePositionObject> nativeCoordinates,
			WebdavNavigationCoordinates position, GWebdavContentManagementSystem system,
			GWebdavProjectEndpoint endpoint, IGUserMessagesConsumer messagesConsumer,
			Map<String, Object> environment) throws GeboContentHandlerSystemException {
		List<NativeCoordinatePointer> childs = new ArrayList<NativeCoordinatePointer>();
		WebdavNativePositionObject last = nativeCoordinates.get(nativeCoordinates.size() - 1);
		try {
			Sardine sardine = getSardine(environment);
			String href = last.getCode();
			List<DavResource> resources = sardine.list(href, 1);
			for (DavResource res : resources) {
				String name = res.getName();
				if (name == null || name.trim().isEmpty() || ".".equals(name.trim())
						|| "..".equals(name.trim())) {
					continue;
				}
				NativeCoordinatePointer pointer = new NativeCoordinatePointer();
				pointer.parentCoordinates = new ArrayList<WebdavNativePositionObject>(nativeCoordinates);
				if (res.isDirectory()) {
					pointer.child = WebdavNativePositionObject.createFolder(res.getHref().toString(), res.getName());
				} else {
					pointer.child = WebdavNativePositionObject.createFile(res.getHref().toString(), res.getName(),
							res.getContentType(), res.getContentLength(),
							res.getModified() != null ? res.getModified() : null);
				}
				childs.add(pointer);
			}
		} catch (IOException e) {
			throw new GeboContentHandlerSystemException("Exception accessing WebDAV system", e);
		}
		return childs;
	}

	@Override
	protected WebdavNavigationCoordinates getPositionCoordinate(
			List<WebdavNativePositionObject> childCoordinates, Map<String, Object> environment)
			throws GeboContentHandlerSystemException {
		return WebdavNavigationUtil.toNavigationCoordinates(childCoordinates);
	}

	@Override
	public GWebdavResourceReference getResourceHandle(GWebdavContentManagementSystem system,
			GWebdavProjectEndpoint endpoint, GAbstractVirtualFilesystemObject reference,
			Map<String, Object> cache) throws GeboContentHandlerSystemException {
		GWebdavResourceReference obj = new GWebdavResourceReference();
		Map<String, Object> metainfos = reference.getCustomMetaInfos();
		if (metainfos == null)
			throw new GeboContentHandlerSystemException("customMetaInfos not present in document reference");
		obj.href = getMetaString(WebdavNativePositionObject.WEBDAB_HREF_METAINFO, metainfos);
		obj.contentType = getMetaString(WebdavNativePositionObject.WEBDAB_CONTENT_TYPE_METAINFO, metainfos);
		obj.resourceType = getMetaString(WebdavNativePositionObject.WEBDAB_RESOURCE_METAINFO, metainfos);
		return obj;
	}

	@Override
	protected GWebdavResourceReference getResourceHandle(SearchableSystemMetaData system,
			WebdavNavigationCoordinates navigationPosition, List<WebdavNativePositionObject> nativeCoordinates,
			Map<String, Object> environment) throws GeboContentHandlerSystemException {
		GWebdavResourceReference obj = new GWebdavResourceReference();
		if (!nativeCoordinates.isEmpty()) {
			WebdavNativePositionObject last = nativeCoordinates.get(nativeCoordinates.size() - 1);
			Map<String, Object> metainfos = last.getResourceReferenceMetaInfos();
			if (metainfos != null) {
				obj.href = getMetaString(WebdavNativePositionObject.WEBDAB_HREF_METAINFO, metainfos);
				obj.contentType = getMetaString(WebdavNativePositionObject.WEBDAB_CONTENT_TYPE_METAINFO, metainfos);
				obj.resourceType = getMetaString(WebdavNativePositionObject.WEBDAB_RESOURCE_METAINFO, metainfos);
			}
		}
		return obj;
	}

	private String getMetaString(String key, Map<String, Object> metainfos) {
		return metainfos.containsKey(key) ? metainfos.get(key).toString() : null;
	}

	@Override
	public InputStream streamResource(GWebdavContentManagementSystem system, GWebdavProjectEndpoint endpoint,
			GWebdavResourceReference reference, Map<String, Object> cache)
			throws GeboContentHandlerSystemException, IOException {
		if (!cache.containsKey(DAV_CONNECTION)) {
			Map<String, Object> environment = createEnvironment(system, endpoint,
					IGContentsAccessErrorConsumer.defaultImplementation());
			cache.putAll(environment);
		}
		Sardine sardine = getSardine(cache);
		if (reference.href != null) {
			return sardine.get(resolveHref(reference.href, system.getBaseUri()));
		}
		return InputStream.nullInputStream();
	}

	@Override
	protected InputStream streamResource(GWebdavContentManagementSystem system,
			GWebdavResourceReference reference, Map<String, Object> cache)
			throws GeboContentHandlerSystemException {
		try {
			if (!cache.containsKey(DAV_CONNECTION)) {
				Map<String, Object> environment = createEnvironment(system);
				cache.putAll(environment);
			}
			Sardine sardine = getSardine(cache);
			if (reference.href != null) {
				return sardine.get(resolveHref(reference.href, system.getBaseUri()));
			}
		} catch (IOException e) {
			throw new GeboContentHandlerSystemException("Cannot stream resource: " + reference.href, e);
		}
		return InputStream.nullInputStream();
	}

	private String resolveHref(String href, String baseUri) {
		if (href == null)
			return null;
		if (href.startsWith("http://") || href.startsWith("https://"))
			return href;
		if (baseUri == null)
			return href;
		String base = baseUri.endsWith("/") ? baseUri : baseUri + "/";
		String path = href.startsWith("/") ? href.substring(1) : href;
		return base + path;
	}

	@Override
	protected String describeObject(List<WebdavNativePositionObject> references, GWebdavContentManagementSystem system,
			GWebdavProjectEndpoint endpoint, Map<String, Object> environment) {
		if (references.isEmpty())
			return "<<Incoherent hierarchy>>";
		WebdavNativePositionObject last = references.get(references.size() - 1);
		String objectType = last.isWebdavFolder() ? "Folder" : "File";
		return objectType + " " + last.getName() + " (" + last.getCode() + ")";
	}

	@Override
	protected String describeSystem(GWebdavContentManagementSystem system) {
		return "WebDAV " + system.getDescription();
	}

	@Override
	protected String describeProjectEndpoint(GWebdavContentManagementSystem system,
			GWebdavProjectEndpoint endpoint, Map<String, Object> environment) {
		return "WebDAV contents source " + endpoint.getDescription();
	}

	@Override
	protected GAbstractVirtualFilesystemObject verifyRemoteObjectExistence(GWebdavContentManagementSystem system,
			GWebdavProjectEndpoint endpoint, GAbstractVirtualFilesystemObject doc,
			GWebdavResourceReference reference, Map<String, Object> environment)
			throws GeboContentHandlerSystemException {
		return null;
	}

	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.WEBDAB_CMS_MODULE;
	}
}