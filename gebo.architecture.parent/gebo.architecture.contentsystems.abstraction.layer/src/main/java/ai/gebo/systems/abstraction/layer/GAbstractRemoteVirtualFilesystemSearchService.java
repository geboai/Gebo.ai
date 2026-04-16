package ai.gebo.systems.abstraction.layer;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.search.model.BaseSearchResultsExtractionDataType;
import ai.gebo.architecture.search.model.CatalogueSample;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.knlowledgebase.model.projects.GVirtualFilesystemProjectEndpoint;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystem;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystemType;
import ai.gebo.model.OperationStatus;
import ai.gebo.model.base.TypedInputStream;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.systems.abstraction.layer.model.AbstractNativePositionObject;
import ai.gebo.systems.abstraction.layer.model.AbstractNavigationCoordinates;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class GAbstractRemoteVirtualFilesystemSearchService<ExtractionResultDataType extends BaseSearchResultsExtractionDataType, SystemType extends GContentManagementSystem, EndpointType extends GVirtualFilesystemProjectEndpoint, ImplementativePositionObjectType extends AbstractNativePositionObject, PositionsCoordinateType extends AbstractNavigationCoordinates, ResourceReferenceType extends IGRemoteVirtualFilesystemResourceReference, ConsumingServiceType extends IGRemoteVirtualFilesystemConsumingService<SystemType, EndpointType, ResourceReferenceType>, BrowsingContext extends IGVirtualFileSystemContext>
		implements ISearchService<ExtractionResultDataType> {

	protected final GAbstractRemoteVirtualFilesystemConsumingService<SystemType, EndpointType, ImplementativePositionObjectType, PositionsCoordinateType, ResourceReferenceType> virtualFileSystemConsumingService;
	protected final GAbstractRemoteVirtualFilesystemContentManagementSystemHandler<SystemType, EndpointType, ResourceReferenceType, ConsumingServiceType> contentManagementSystemHandler;
	protected final IGVirtualFilesystemBrowsingService<BrowsingContext> browsingService;
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Override
	public SearchableSystemMetaData findSystemById(String systemId) {
		final String prologue = getMessagingModuleId() + "." + getMessagingSystemId()
				+ ISearchService.SYSTEM_TYPE_CODE_CONFIG_CODE_SEPARATOR;
		List<SystemType> configs = contentManagementSystemHandler.getConfigurations();
		if (configs != null && !configs.isEmpty()) {
			GContentManagementSystemType ctype = contentManagementSystemHandler.getHandledSystemType();

			Optional<SystemType> found = configs.stream().filter(x -> (prologue + x.getCode()).equals(systemId))
					.findFirst();
			if (found.isEmpty())
				return null;

			SearchableSystemMetaData metaData = new SearchableSystemMetaData<GContentManagementSystemType, SystemType>();
			metaData.setSystemType(ctype);
			metaData.setSystemConfigurationReference(found.get());
			metaData.setCode(ctype.getCode() + "-" + found.get().getCode());
			metaData.setDescription(virtualFileSystemConsumingService.describeSystem(found.get()));
			return metaData;
		}
		return null;
	}

	@Override
	public String getId() {

		return contentManagementSystemHandler.getMessagingModuleId() + "."
				+ contentManagementSystemHandler.getMessagingSystemId() + ".searchService";
	}

	@Override
	public boolean isEnabled() {

		return !contentManagementSystemHandler.getConfigurations().isEmpty();
	}

	@Override
	public List<SearchableSystemMetaData> getSearchableSystems() {
		List<SystemType> configs = contentManagementSystemHandler.getConfigurations();
		if (configs != null && !configs.isEmpty()) {
			return configs.stream().map(x -> {
				SearchableSystemMetaData metaData = new SearchableSystemMetaData<GContentManagementSystemType, SystemType>();
				GContentManagementSystemType ctype = contentManagementSystemHandler.getHandledSystemType();
				metaData.setSystemType(ctype);
				metaData.setSystemConfigurationReference(x);
				final String prologue = getMessagingModuleId() + "." + getMessagingSystemId()
						+ ISearchService.SYSTEM_TYPE_CODE_CONFIG_CODE_SEPARATOR;
				metaData.setCode(prologue + x.getCode());
				metaData.setDescription(virtualFileSystemConsumingService.describeSystem(x));
				return metaData;
			}).toList();
		}
		return List.of();
	}

	@Override
	public TypedInputStream loadSearchResult(SearchResult result) throws IOException, SearchServiceException {
		VFilesystemReference navigationReference = result.getNavigationReference();
		if (navigationReference == null)
			throw new RuntimeException("NavigationReference cannot be null");
		PositionsCoordinateType navigationPosition;
		Map<String, Object> environment = null;
		SearchableSystemMetaData system = null;
		SystemType actualSystem = null;
		try {

			system = findSystemBySearchResult(result);
			actualSystem = (SystemType) system.getSystemConfigurationReference();
			environment = virtualFileSystemConsumingService.createEnvironment(actualSystem);
			navigationPosition = virtualFileSystemConsumingService.toNavigationPosition(navigationReference,
					environment);
			List<ImplementativePositionObjectType> nativeCoordinates = virtualFileSystemConsumingService
					.toResourcesNativeCoordinates(navigationPosition, actualSystem, environment);
			ResourceReferenceType remoteReference = virtualFileSystemConsumingService.getResourceHandle(system,
					navigationPosition, nativeCoordinates, environment);
			if (remoteReference == null) {
				LOGGER.error("Returned null remoteReference for: " + result);
				return null;
			}
			final ImplementativePositionObjectType nativeReference = nativeCoordinates.size() > 0
					? nativeCoordinates.get(nativeCoordinates.size() - 1)
					: null;
			final InputStream stream = virtualFileSystemConsumingService.streamResource(actualSystem, remoteReference,
					environment);
			final TypedInputStream outStream = TypedInputStream.of(stream,
					nativeReference != null ? nativeReference.getResourceContentType() : null, null);
			return outStream;
		} catch (GeboContentHandlerSystemException e) {
			throw new SearchServiceException("Exception in loadSearchResult(..)", e);
		} finally {
			try {
				virtualFileSystemConsumingService.clearEnvironment(environment, actualSystem, null);
			} catch (Throwable th) {
			}
		}

	}

	private List<SearchResult> cloneUnique(List<SearchResult> results, Map<String, Boolean> dupCheck) {
		List<SearchResult> out = new ArrayList<SearchResult>();
		for (SearchResult searchResult : results) {
			if (!isIn(searchResult, dupCheck)) {
				SearchResult cloned = new SearchResult(searchResult);
				out.add(cloned);
				putIn(searchResult, dupCheck);
				cloned.setChilds(cloneUnique(searchResult.getChilds(), dupCheck));
			}
		}
		return out;
	}

	private boolean isIn(SearchResult result, Map<String, Boolean> d) {
		String key = getKey(result);
		return d.containsKey(key);
	}

	private void putIn(SearchResult result, Map<String, Boolean> d) {
		String key = getKey(result);
		d.put(key, true);
	}

	@Override
	public List<CatalogueSample> getCataloguesListSample(String configurationCode) throws SearchServiceException {
		SearchableSystemMetaData system = findSystemById(configurationCode);
		if (system == null)
			throw new SearchServiceException("Unknown system " + configurationCode);
		try {
			OperationStatus<List<GVirtualFilesystemRoot>> roots = browsingService
					.getRoots(createBrowsingContext((SystemType) system.getSystemConfigurationReference()));
			if (!roots.isHasErrorMessages() && roots.getResult() != null) {
				return roots.getResult().stream().map(x -> new CatalogueSample(x.getCode(), x.getDescription()))
						.toList();
			} else {
				roots.getMessages().forEach(x -> {
					LOGGER.error(x.getSummary() + " - " + x.getDetail());
				});
				throw new SearchServiceException("Problems browsing system:" + configurationCode);
			}
		} catch (VirtualFilesystemBrowsingException e) {
			throw new SearchServiceException("Problems browsing system:" + configurationCode, e);
		}

	}

	protected abstract BrowsingContext createBrowsingContext(SystemType systemType);

	private String getKey(SearchResult result) {
		return result.getNavigationReference() != null && result.getNavigationReference().root != null
				? result.getNavigationReference().root.getCode() + (result.getNavigationReference().path != null
						? "-<" + result.getNavigationReference().path.absolutePath
						: "")
				: UUID.randomUUID().toString();
	}
}
