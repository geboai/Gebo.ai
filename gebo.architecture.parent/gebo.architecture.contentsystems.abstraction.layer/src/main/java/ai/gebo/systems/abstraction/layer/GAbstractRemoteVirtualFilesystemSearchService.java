package ai.gebo.systems.abstraction.layer;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.search.model.BaseSearchResultsExtractionDataType;
import ai.gebo.architecture.search.model.SearchQuery;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.model.SearchWithResults;
import ai.gebo.architecture.search.model.SearchableSystemMetaData;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.knlowledgebase.model.projects.GVirtualFilesystemProjectEndpoint;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystem;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystemType;
import ai.gebo.model.base.TypedInputStream;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.systems.abstraction.layer.model.AbstractNativePositionObject;
import ai.gebo.systems.abstraction.layer.model.AbstractNavigationCoordinates;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class GAbstractRemoteVirtualFilesystemSearchService<ExtractionResultDataType extends BaseSearchResultsExtractionDataType, SystemType extends GContentManagementSystem, EndpointType extends GVirtualFilesystemProjectEndpoint, ImplementativePositionObjectType extends AbstractNativePositionObject, PositionsCoordinateType extends AbstractNavigationCoordinates, ResourceReferenceType extends IGRemoteVirtualFilesystemResourceReference, ConsumingServiceType extends IGRemoteVirtualFilesystemConsumingService<SystemType, EndpointType, ResourceReferenceType>>
		implements ISearchService<ExtractionResultDataType> {

	protected final GAbstractRemoteVirtualFilesystemConsumingService<SystemType, EndpointType, ImplementativePositionObjectType, PositionsCoordinateType, ResourceReferenceType> virtualFileSystemConsumingService;
	protected final GAbstractRemoteVirtualFilesystemContentManagementSystemHandler<SystemType, EndpointType, ResourceReferenceType, ConsumingServiceType> contentManagementSystemHandler;

	@Override
	public SearchableSystemMetaData findSystemById(String systemId) {
		List<SystemType> configs = contentManagementSystemHandler.getConfigurations();
		if (configs != null && !configs.isEmpty()) {
			GContentManagementSystemType ctype = contentManagementSystemHandler.getHandledSystemType();
			final String prologue = getMessagingModuleId() + "." + getMessagingSystemId()
					+ SYSTEM_TYPE_CODE_CONFIG_CODE_SEPARATOR;
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
				metaData.setCode(ctype.getCode() + SYSTEM_TYPE_CODE_CONFIG_CODE_SEPARATOR + x.getCode());
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
		try {
			navigationPosition = virtualFileSystemConsumingService.toNavigationPosition(navigationReference);
			SearchableSystemMetaData system = findSystemBySearchResult(result);
			SystemType actualSystem = (SystemType) system.getSystemConfigurationReference();
			Map<String, Object> environment = virtualFileSystemConsumingService.createEnvironment(actualSystem);
			List<ImplementativePositionObjectType> nativeCoordinates = virtualFileSystemConsumingService
					.toNativeCoordinates(navigationPosition, actualSystem, environment);
			ResourceReferenceType remoteReference = virtualFileSystemConsumingService.getResourceHandle(system,
					navigationPosition, nativeCoordinates, environment);
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
		}

	}

	@Override
	public List<SearchWithResults> cleanAndRemoveDuplicated(List<SearchWithResults> queryResults) {

		final Map<String, Boolean> dupCheck = new HashMap<String, Boolean>();
		List<SearchWithResults> out = new ArrayList<SearchWithResults>();
		for (SearchWithResults searchWithResults : queryResults) {
			SearchWithResults cloned = new SearchWithResults();
			cloned.setSearchQuery(searchWithResults.getSearchQuery());
			cloned.setResults(cloneUnique(searchWithResults.getResults(), dupCheck));
			if (!cloned.getResults().isEmpty())
				out.add(cloned);
		}
		return out;
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

	private String getKey(SearchResult result) {
		return result.getNavigationReference() != null && result.getNavigationReference().root != null
				? result.getNavigationReference().root.getCode() + (result.getNavigationReference().path != null
						? "-<" + result.getNavigationReference().path.absolutePath
						: "")
				: UUID.randomUUID().toString();
	}
}
