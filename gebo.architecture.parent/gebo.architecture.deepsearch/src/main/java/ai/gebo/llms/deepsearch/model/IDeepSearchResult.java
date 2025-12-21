package ai.gebo.llms.deepsearch.model;

public interface IDeepSearchResult {

	public String getResponse();

	public Boolean getSearchResultsEmpty();

	public String getDeepsearchCode();

	public String getDataSourceDescription();

}
