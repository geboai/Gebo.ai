/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.microsoft.sharepoint.cloud.model;

// This class maps to each "site" object returned by Graph:
public class CloudSharepointSite {
	/**
     * The GUID of the site (site collection or sub-site).
     * In _api/site, often just "Id".
     * In _api/web, also "Id".
     */
	private String id;
	private String name;
	private String displayName;
	private String webUrl;

    


    /**
     * Title of the web (sub-site).
     * From _api/web -> "Title".
     * (Note: _api/site doesn't directly have a Title.)
     */
    private String title;

    /**
     * Description of the web (sub-site).
     * From _api/web -> "Description".
     */
    private String description;

    /**
     * The absolute URL of the site or sub-site.
     * - From _api/site -> "Url"
     * - From _api/web -> "Url"
     */
    private String url;

    /**
     * The site collection's primary owner (site collection admin).
     * From _api/site -> "Owner".
     * This is often a user or login name reference.
     */
    private String owner;

    /**
     * The secondary contact for a site collection, if defined.
     * From _api/site -> "SecondaryContact".
     */
    private String secondaryContact;

    /**
     * If this site is the root web of the site collection.
     * From _api/web -> "IsRootWeb".
     */
    private Boolean isRootWeb;

    /**
     * The template used to create the web.
     * From _api/web -> "WebTemplate" (e.g. "STS", "BLOG", "CENTRALADMIN", etc.).
     */
    private String webTemplate;

    /**
     * Numeric configuration for the web template.
     * From _api/web -> "Configuration" (e.g., 0, 1, etc.).
     */
    private Integer configuration;

    /**
     * The default language code (LCID) for the web/sub-site.
     * From _api/web -> "Language" (e.g., 1033 for English).
     */
    private Integer language;

    /**
     * Whether the web is configured for multilingual UI.
     * From _api/web -> "IsMultilingual".
     */
    private Boolean isMultilingual;

    /**
     * The date/time this web was created.
     * From _api/web -> "Created" (ISO 8601 string).
     */
    private String created;

    /**
     * The date/time this web (or its content) was last modified.
     * From _api/web -> "LastItemModifiedDate" or "Modified".
     */
    private String lastItemModifiedDate;

    /**
     * The master page URL for classic SharePoint branding.
     * From _api/web -> "MasterUrl".
     */
    private String masterUrl;

    /**
     * The custom master page URL for classic SharePoint branding.
     * From _api/web -> "CustomMasterUrl".
     */
    private String customMasterUrl;

    /**
     * URL of the site logo.
     * From _api/web -> "SiteLogoUrl".
     */
    private String siteLogoUrl;

    /**
     * The GUID of the Microsoft 365 Group (if the site is group-connected).
     * From _api/site -> "GroupId".
     */
    private String groupId;

	// Add more fields as needed

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String d) {
		this.displayName = d;
	}

	public String getWebUrl() {
		return webUrl;
	}

	public void setWebUrl(String w) {
		this.webUrl = w;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getSecondaryContact() {
		return secondaryContact;
	}

	public void setSecondaryContact(String secondaryContact) {
		this.secondaryContact = secondaryContact;
	}

	public Boolean getIsRootWeb() {
		return isRootWeb;
	}

	public void setIsRootWeb(Boolean isRootWeb) {
		this.isRootWeb = isRootWeb;
	}

	public String getWebTemplate() {
		return webTemplate;
	}

	public void setWebTemplate(String webTemplate) {
		this.webTemplate = webTemplate;
	}

	public Integer getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Integer configuration) {
		this.configuration = configuration;
	}

	public Integer getLanguage() {
		return language;
	}

	public void setLanguage(Integer language) {
		this.language = language;
	}

	public Boolean getIsMultilingual() {
		return isMultilingual;
	}

	public void setIsMultilingual(Boolean isMultilingual) {
		this.isMultilingual = isMultilingual;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getLastItemModifiedDate() {
		return lastItemModifiedDate;
	}

	public void setLastItemModifiedDate(String lastItemModifiedDate) {
		this.lastItemModifiedDate = lastItemModifiedDate;
	}

	public String getMasterUrl() {
		return masterUrl;
	}

	public void setMasterUrl(String masterUrl) {
		this.masterUrl = masterUrl;
	}

	public String getCustomMasterUrl() {
		return customMasterUrl;
	}

	public void setCustomMasterUrl(String customMasterUrl) {
		this.customMasterUrl = customMasterUrl;
	}

	public String getSiteLogoUrl() {
		return siteLogoUrl;
	}

	public void setSiteLogoUrl(String siteLogoUrl) {
		this.siteLogoUrl = siteLogoUrl;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
}