package ai.gebo.sharepoint.search.api;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.Data;

@Data
@JsonClassDescription("Filters on SharePoint/OneDrive content attributes (translated to KQL queryString)")
public class SharePointContentAttributeFilter {

    @JsonClassDescription("Group of keywords search matching policy")
    public enum TextMatchMode { ANY, ALL }

    @JsonClassDescription("What kind of things to retrieve")
    public enum ContentKind {
        DOCUMENT,   // files in libraries / OneDrive
        PAGE        // modern site pages (Site Pages)
    }

    @JsonPropertyDescription("Which kinds of content to search. If null -> defaults to [DOCUMENT,PAGE].")
    private List<ContentKind> contentKinds = null;

    @JsonPropertyDescription("Free-text terms/phrases (searched across content).")
    private List<String> textTerms = null;
    private TextMatchMode textTermsMatchMode = null;

    @JsonPropertyDescription("Title/name terms/phrases.")
    private List<String> titleTerms = null;
    private TextMatchMode titleTermsMatchMode = null;

    @JsonPropertyDescription("Optional: restrict to specific SharePoint site URLs via path: (best-effort scoping).")
    private List<String> siteUrls = null;
    private TextMatchMode siteUrlsMatchMode = null;

    @JsonPropertyDescription("Optional: restrict to specific path prefixes via path: (best-effort scoping).")
    private List<String> pathPrefixes = null;
    private TextMatchMode pathPrefixesMatchMode = null;

    @JsonPropertyDescription("Optional generic managed-property equals constraints. Key = managed property; values = exact values.")
    private Map<String, List<String>> managedPropertyEquals = null;

    @JsonPropertyDescription("Optional generic managed-property contains constraints. Key = managed property; values = phrases/terms.")
    private Map<String, List<String>> managedPropertyContains = null;

    @JsonPropertyDescription("Match mode for the values inside managedPropertyEquals/Contains. If null -> ANY.")
    private TextMatchMode managedPropertiesValuesMatchMode = null;
}