package ai.gebo.systems.abstraction.layer;

import ai.gebo.architecture.search.model.BaseSearchResultsExtractionDataType;
import ai.gebo.architecture.search.service.ISearchService;

public interface IGVirtualFilesystemBrowsingAndSearchService<VFScontext extends IGVirtualFileSystemContext, CustomSearchResultExtractionDataType extends BaseSearchResultsExtractionDataType>
		extends IGVirtualFilesystemBrowsingService<VFScontext>, ISearchService<CustomSearchResultExtractionDataType> {

}
