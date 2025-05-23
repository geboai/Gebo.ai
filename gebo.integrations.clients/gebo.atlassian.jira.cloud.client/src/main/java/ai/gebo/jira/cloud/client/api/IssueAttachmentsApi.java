/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.jira.cloud.client.api;

import ai.gebo.jira.cloud.client.invoker.ApiClient;

import ai.gebo.jira.cloud.client.model.Attachment;
import ai.gebo.jira.cloud.client.model.AttachmentArchiveImpl;
import ai.gebo.jira.cloud.client.model.AttachmentArchiveMetadataReadable;
import ai.gebo.jira.cloud.client.model.AttachmentMetadata;
import ai.gebo.jira.cloud.client.model.AttachmentSettings;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-05-05T08:43:30.568333200+02:00[Europe/Rome]")
//@Component("ai.gebo.jira.cloud.client.api.IssueAttachmentsApi")
public class IssueAttachmentsApi {
	private ApiClient apiClient;

	public IssueAttachmentsApi() {
		this(new ApiClient());
	}

	// @Autowired
	public IssueAttachmentsApi(ApiClient apiClient) {
		this.apiClient = apiClient;
	}

	public ApiClient getApiClient() {
		return apiClient;
	}

	public void setApiClient(ApiClient apiClient) {
		this.apiClient = apiClient;
	}

	/**
	 * Add attachment Adds one or more attachments to an issue. Attachments are
	 * posted as multipart/form-data ([RFC
	 * 1867](https://www.ietf.org/rfc/rfc1867.txt)). Note that: * The request must
	 * have a &#x60;X-Atlassian-Token: no-check&#x60; header, if not it is blocked.
	 * See [Special headers](#special-request-headers) for more information. * The
	 * name of the multipart/form-data parameter that contains the attachments must
	 * be &#x60;file&#x60;. The following examples upload a file called *myfile.txt*
	 * to the issue *TEST-123*: #### curl #### curl --location --request POST
	 * &#x27;https://your-domain.atlassian.net/rest/api/3/issue/TEST-123/attachments&#x27;
	 * -u &#x27;email@example.com:&lt;api_token&gt;&#x27; -H
	 * &#x27;X-Atlassian-Token: no-check&#x27; --form
	 * &#x27;file&#x3D;@\&quot;myfile.txt\&quot;&#x27; #### Node.js #### // This
	 * code sample uses the &#x27;node-fetch&#x27; and &#x27;form-data&#x27;
	 * libraries: // https://www.npmjs.com/package/node-fetch //
	 * https://www.npmjs.com/package/form-data const fetch &#x3D;
	 * require(&#x27;node-fetch&#x27;); const FormData &#x3D;
	 * require(&#x27;form-data&#x27;); const fs &#x3D; require(&#x27;fs&#x27;);
	 * const filePath &#x3D; &#x27;myfile.txt&#x27;; const form &#x3D; new
	 * FormData(); const stats &#x3D; fs.statSync(filePath); const fileSizeInBytes
	 * &#x3D; stats.size; const fileStream &#x3D; fs.createReadStream(filePath);
	 * form.append(&#x27;file&#x27;, fileStream, {knownLength: fileSizeInBytes});
	 * fetch(&#x27;https://your-domain.atlassian.net/rest/api/3/issue/TEST-123/attachments&#x27;,
	 * { method: &#x27;POST&#x27;, body: form, headers: { &#x27;Authorization&#x27;:
	 * &#x60;Basic ${Buffer.from( &#x27;email@example.com:&#x27;
	 * ).toString(&#x27;base64&#x27;)}&#x60;, &#x27;Accept&#x27;:
	 * &#x27;application/json&#x27;, &#x27;X-Atlassian-Token&#x27;:
	 * &#x27;no-check&#x27; } }) .then(response &#x3D;&gt; { console.log(
	 * &#x60;Response: ${response.status} ${response.statusText}&#x60; ); return
	 * response.text(); }) .then(text &#x3D;&gt; console.log(text)) .catch(err
	 * &#x3D;&gt; console.error(err)); #### Java #### // This code sample uses the
	 * &#x27;Unirest&#x27; library: // http://unirest.io/java.html HttpResponse
	 * response &#x3D;
	 * Unirest.post(\&quot;https://your-domain.atlassian.net/rest/api/2/issue/{issueIdOrKey}/attachments\&quot;)
	 * .basicAuth(\&quot;email@example.com\&quot;, \&quot;\&quot;)
	 * .header(\&quot;Accept\&quot;, \&quot;application/json\&quot;)
	 * .header(\&quot;X-Atlassian-Token\&quot;, \&quot;no-check\&quot;)
	 * .field(\&quot;file\&quot;, new File(\&quot;myfile.txt\&quot;)) .asJson();
	 * System.out.println(response.getBody()); #### Python #### # This code sample
	 * uses the &#x27;requests&#x27; library: # http://docs.python-requests.org
	 * import requests from requests.auth import HTTPBasicAuth import json url
	 * &#x3D;
	 * \&quot;https://your-domain.atlassian.net/rest/api/2/issue/{issueIdOrKey}/attachments\&quot;
	 * auth &#x3D; HTTPBasicAuth(\&quot;email@example.com\&quot;, \&quot;\&quot;)
	 * headers &#x3D; { \&quot;Accept\&quot;: \&quot;application/json\&quot;,
	 * \&quot;X-Atlassian-Token\&quot;: \&quot;no-check\&quot; } response &#x3D;
	 * requests.request( \&quot;POST\&quot;, url, headers &#x3D; headers, auth
	 * &#x3D; auth, files &#x3D; { \&quot;file\&quot;: (\&quot;myfile.txt\&quot;,
	 * open(\&quot;myfile.txt\&quot;,\&quot;rb\&quot;),
	 * \&quot;application-type\&quot;) } )
	 * print(json.dumps(json.loads(response.text), sort_keys&#x3D;True,
	 * indent&#x3D;4, separators&#x3D;(\&quot;,\&quot;, \&quot;: \&quot;))) #### PHP
	 * #### // This code sample uses the &#x27;Unirest&#x27; library: //
	 * http://unirest.io/php.html
	 * Unirest\\Request::auth(&#x27;email@example.com&#x27;, &#x27;&#x27;); $headers
	 * &#x3D; array( &#x27;Accept&#x27; &#x3D;&gt; &#x27;application/json&#x27;,
	 * &#x27;X-Atlassian-Token&#x27; &#x3D;&gt; &#x27;no-check&#x27; ); $parameters
	 * &#x3D; array( &#x27;file&#x27; &#x3D;&gt; File::add(&#x27;myfile.txt&#x27;)
	 * ); $response &#x3D; Unirest\\Request::post(
	 * &#x27;https://your-domain.atlassian.net/rest/api/2/issue/{issueIdOrKey}/attachments&#x27;,
	 * $headers, $parameters ); var_dump($response) #### Forge #### // This sample
	 * uses Atlassian Forge and the &#x60;form-data&#x60; library. //
	 * https://developer.atlassian.com/platform/forge/ //
	 * https://www.npmjs.com/package/form-data import api from
	 * \&quot;@forge/api\&quot;; import FormData from \&quot;form-data\&quot;; const
	 * form &#x3D; new FormData(); form.append(&#x27;file&#x27;, fileStream,
	 * {knownLength: fileSizeInBytes}); const response &#x3D; await
	 * api.asApp().requestJira(&#x27;/rest/api/2/issue/{issueIdOrKey}/attachments&#x27;,
	 * { method: &#x27;POST&#x27;, body: form, headers: { &#x27;Accept&#x27;:
	 * &#x27;application/json&#x27;, &#x27;X-Atlassian-Token&#x27;:
	 * &#x27;no-check&#x27; } }); console.log(&#x60;Response: ${response.status}
	 * ${response.statusText}&#x60;); console.log(await response.json()); Tip: Use a
	 * client library. Many client libraries have classes for handling multipart
	 * POST operations. For example, in Java, the Apache HTTP Components library
	 * provides a
	 * [MultiPartEntity](http://hc.apache.org/httpcomponents-client-ga/httpmime/apidocs/org/apache/http/entity/mime/MultipartEntity.html)
	 * class for multipart POST operations. This operation can be accessed
	 * anonymously. **[Permissions](#permissions) required:** * *Browse Projects*
	 * and *Create attachments* [ project
	 * permission](https://confluence.atlassian.com/x/yodKLg) for the project that
	 * the issue is in. * If [issue-level
	 * security](https://confluence.atlassian.com/x/J4lKLg) is configured,
	 * issue-level security permission to view the issue.
	 * <p>
	 * <b>200</b> - Returned if the request is successful.
	 * <p>
	 * <b>403</b> - Returned if the user does not have the necessary permission.
	 * <p>
	 * <b>404</b> - Returned if any of the following is true: * the issue is not
	 * found. * the user does not have permission to view the issue.
	 * <p>
	 * <b>413</b> - Returned if any of the following is true: * the attachments
	 * exceed the maximum attachment size for issues. * more than 60 files are
	 * requested to be uploaded. * the per-issue limit for attachments has been
	 * breached. See [Configuring file
	 * attachments](https://confluence.atlassian.com/x/wIXKM) for details.
	 * 
	 * @param issueIdOrKey The ID or key of the issue that attachments are added to.
	 *                     (required)
	 * @return List&lt;Attachment&gt;
	 * @throws RestClientException if an error occurs while attempting to invoke the
	 *                             API
	 */
	public List<Attachment> addAttachment(String issueIdOrKey) throws RestClientException {
		return addAttachmentWithHttpInfo(issueIdOrKey).getBody();
	}

	/**
	 * Add attachment Adds one or more attachments to an issue. Attachments are
	 * posted as multipart/form-data ([RFC
	 * 1867](https://www.ietf.org/rfc/rfc1867.txt)). Note that: * The request must
	 * have a &#x60;X-Atlassian-Token: no-check&#x60; header, if not it is blocked.
	 * See [Special headers](#special-request-headers) for more information. * The
	 * name of the multipart/form-data parameter that contains the attachments must
	 * be &#x60;file&#x60;. The following examples upload a file called *myfile.txt*
	 * to the issue *TEST-123*: #### curl #### curl --location --request POST
	 * &#x27;https://your-domain.atlassian.net/rest/api/3/issue/TEST-123/attachments&#x27;
	 * -u &#x27;email@example.com:&lt;api_token&gt;&#x27; -H
	 * &#x27;X-Atlassian-Token: no-check&#x27; --form
	 * &#x27;file&#x3D;@\&quot;myfile.txt\&quot;&#x27; #### Node.js #### // This
	 * code sample uses the &#x27;node-fetch&#x27; and &#x27;form-data&#x27;
	 * libraries: // https://www.npmjs.com/package/node-fetch //
	 * https://www.npmjs.com/package/form-data const fetch &#x3D;
	 * require(&#x27;node-fetch&#x27;); const FormData &#x3D;
	 * require(&#x27;form-data&#x27;); const fs &#x3D; require(&#x27;fs&#x27;);
	 * const filePath &#x3D; &#x27;myfile.txt&#x27;; const form &#x3D; new
	 * FormData(); const stats &#x3D; fs.statSync(filePath); const fileSizeInBytes
	 * &#x3D; stats.size; const fileStream &#x3D; fs.createReadStream(filePath);
	 * form.append(&#x27;file&#x27;, fileStream, {knownLength: fileSizeInBytes});
	 * fetch(&#x27;https://your-domain.atlassian.net/rest/api/3/issue/TEST-123/attachments&#x27;,
	 * { method: &#x27;POST&#x27;, body: form, headers: { &#x27;Authorization&#x27;:
	 * &#x60;Basic ${Buffer.from( &#x27;email@example.com:&#x27;
	 * ).toString(&#x27;base64&#x27;)}&#x60;, &#x27;Accept&#x27;:
	 * &#x27;application/json&#x27;, &#x27;X-Atlassian-Token&#x27;:
	 * &#x27;no-check&#x27; } }) .then(response &#x3D;&gt; { console.log(
	 * &#x60;Response: ${response.status} ${response.statusText}&#x60; ); return
	 * response.text(); }) .then(text &#x3D;&gt; console.log(text)) .catch(err
	 * &#x3D;&gt; console.error(err)); #### Java #### // This code sample uses the
	 * &#x27;Unirest&#x27; library: // http://unirest.io/java.html HttpResponse
	 * response &#x3D;
	 * Unirest.post(\&quot;https://your-domain.atlassian.net/rest/api/2/issue/{issueIdOrKey}/attachments\&quot;)
	 * .basicAuth(\&quot;email@example.com\&quot;, \&quot;\&quot;)
	 * .header(\&quot;Accept\&quot;, \&quot;application/json\&quot;)
	 * .header(\&quot;X-Atlassian-Token\&quot;, \&quot;no-check\&quot;)
	 * .field(\&quot;file\&quot;, new File(\&quot;myfile.txt\&quot;)) .asJson();
	 * System.out.println(response.getBody()); #### Python #### # This code sample
	 * uses the &#x27;requests&#x27; library: # http://docs.python-requests.org
	 * import requests from requests.auth import HTTPBasicAuth import json url
	 * &#x3D;
	 * \&quot;https://your-domain.atlassian.net/rest/api/2/issue/{issueIdOrKey}/attachments\&quot;
	 * auth &#x3D; HTTPBasicAuth(\&quot;email@example.com\&quot;, \&quot;\&quot;)
	 * headers &#x3D; { \&quot;Accept\&quot;: \&quot;application/json\&quot;,
	 * \&quot;X-Atlassian-Token\&quot;: \&quot;no-check\&quot; } response &#x3D;
	 * requests.request( \&quot;POST\&quot;, url, headers &#x3D; headers, auth
	 * &#x3D; auth, files &#x3D; { \&quot;file\&quot;: (\&quot;myfile.txt\&quot;,
	 * open(\&quot;myfile.txt\&quot;,\&quot;rb\&quot;),
	 * \&quot;application-type\&quot;) } )
	 * print(json.dumps(json.loads(response.text), sort_keys&#x3D;True,
	 * indent&#x3D;4, separators&#x3D;(\&quot;,\&quot;, \&quot;: \&quot;))) #### PHP
	 * #### // This code sample uses the &#x27;Unirest&#x27; library: //
	 * http://unirest.io/php.html
	 * Unirest\\Request::auth(&#x27;email@example.com&#x27;, &#x27;&#x27;); $headers
	 * &#x3D; array( &#x27;Accept&#x27; &#x3D;&gt; &#x27;application/json&#x27;,
	 * &#x27;X-Atlassian-Token&#x27; &#x3D;&gt; &#x27;no-check&#x27; ); $parameters
	 * &#x3D; array( &#x27;file&#x27; &#x3D;&gt; File::add(&#x27;myfile.txt&#x27;)
	 * ); $response &#x3D; Unirest\\Request::post(
	 * &#x27;https://your-domain.atlassian.net/rest/api/2/issue/{issueIdOrKey}/attachments&#x27;,
	 * $headers, $parameters ); var_dump($response) #### Forge #### // This sample
	 * uses Atlassian Forge and the &#x60;form-data&#x60; library. //
	 * https://developer.atlassian.com/platform/forge/ //
	 * https://www.npmjs.com/package/form-data import api from
	 * \&quot;@forge/api\&quot;; import FormData from \&quot;form-data\&quot;; const
	 * form &#x3D; new FormData(); form.append(&#x27;file&#x27;, fileStream,
	 * {knownLength: fileSizeInBytes}); const response &#x3D; await
	 * api.asApp().requestJira(&#x27;/rest/api/2/issue/{issueIdOrKey}/attachments&#x27;,
	 * { method: &#x27;POST&#x27;, body: form, headers: { &#x27;Accept&#x27;:
	 * &#x27;application/json&#x27;, &#x27;X-Atlassian-Token&#x27;:
	 * &#x27;no-check&#x27; } }); console.log(&#x60;Response: ${response.status}
	 * ${response.statusText}&#x60;); console.log(await response.json()); Tip: Use a
	 * client library. Many client libraries have classes for handling multipart
	 * POST operations. For example, in Java, the Apache HTTP Components library
	 * provides a
	 * [MultiPartEntity](http://hc.apache.org/httpcomponents-client-ga/httpmime/apidocs/org/apache/http/entity/mime/MultipartEntity.html)
	 * class for multipart POST operations. This operation can be accessed
	 * anonymously. **[Permissions](#permissions) required:** * *Browse Projects*
	 * and *Create attachments* [ project
	 * permission](https://confluence.atlassian.com/x/yodKLg) for the project that
	 * the issue is in. * If [issue-level
	 * security](https://confluence.atlassian.com/x/J4lKLg) is configured,
	 * issue-level security permission to view the issue.
	 * <p>
	 * <b>200</b> - Returned if the request is successful.
	 * <p>
	 * <b>403</b> - Returned if the user does not have the necessary permission.
	 * <p>
	 * <b>404</b> - Returned if any of the following is true: * the issue is not
	 * found. * the user does not have permission to view the issue.
	 * <p>
	 * <b>413</b> - Returned if any of the following is true: * the attachments
	 * exceed the maximum attachment size for issues. * more than 60 files are
	 * requested to be uploaded. * the per-issue limit for attachments has been
	 * breached. See [Configuring file
	 * attachments](https://confluence.atlassian.com/x/wIXKM) for details.
	 * 
	 * @param issueIdOrKey The ID or key of the issue that attachments are added to.
	 *                     (required)
	 * @return ResponseEntity&lt;List&lt;Attachment&gt;&gt;
	 * @throws RestClientException if an error occurs while attempting to invoke the
	 *                             API
	 */
	public ResponseEntity<List<Attachment>> addAttachmentWithHttpInfo(String issueIdOrKey) throws RestClientException {
		Object postBody = null;
		// verify the required parameter 'issueIdOrKey' is set
		if (issueIdOrKey == null) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
					"Missing the required parameter 'issueIdOrKey' when calling addAttachment");
		}
		// create path and map variables
		final Map<String, Object> uriVariables = new HashMap<String, Object>();
		uriVariables.put("issueIdOrKey", issueIdOrKey);
		String path = UriComponentsBuilder.fromPath("/rest/api/3/issue/{issueIdOrKey}/attachments")
				.buildAndExpand(uriVariables).toUriString();

		final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
		final HttpHeaders headerParams = new HttpHeaders();
		final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

		final String[] accepts = { "application/json" };
		final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
		final String[] contentTypes = { "multipart/form-data" };
		final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

		String[] authNames = ApiClient.AUTH_NAMES;

		ParameterizedTypeReference<List<Attachment>> returnType = new ParameterizedTypeReference<List<Attachment>>() {
		};
		return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept,
				contentType, authNames, returnType);
	}

	/**
	 * Get all metadata for an expanded attachment Returns the metadata for the
	 * contents of an attachment, if it is an archive, and metadata for the
	 * attachment itself. For example, if the attachment is a ZIP archive, then
	 * information about the files in the archive is returned and metadata for the
	 * ZIP archive. Currently, only the ZIP archive format is supported. Use this
	 * operation to retrieve data that is presented to the user, as this operation
	 * returns the metadata for the attachment itself, such as the attachment&#x27;s
	 * ID and name. Otherwise, use [ Get contents metadata for an expanded
	 * attachment](#api-rest-api-3-attachment-id-expand-raw-get), which only returns
	 * the metadata for the attachment&#x27;s contents. This operation can be
	 * accessed anonymously. **[Permissions](#permissions) required:** For the issue
	 * containing the attachment: * *Browse projects* [project
	 * permission](https://confluence.atlassian.com/x/yodKLg) for the project that
	 * the issue is in. * If [issue-level
	 * security](https://confluence.atlassian.com/x/J4lKLg) is configured,
	 * issue-level security permission to view the issue. * If attachments are added
	 * in private comments, the comment-level restriction will be applied.
	 * <p>
	 * <b>200</b> - Returned if the request is successful. If an empty list is
	 * returned in the response, the attachment is empty, corrupt, or not an
	 * archive.
	 * <p>
	 * <b>401</b> - Returned if the authentication credentials are incorrect or
	 * missing.
	 * <p>
	 * <b>403</b> - The user does not have the necessary permission.
	 * <p>
	 * <b>404</b> - Returned if: * the attachment is not found. * attachments are
	 * disabled in the Jira settings.
	 * <p>
	 * <b>409</b> - Returned if the attachment is an archive, but not a supported
	 * archive format.
	 * 
	 * @param id The ID of the attachment. (required)
	 * @return AttachmentArchiveMetadataReadable
	 * @throws RestClientException if an error occurs while attempting to invoke the
	 *                             API
	 */
	public AttachmentArchiveMetadataReadable expandAttachmentForHumans(String id) throws RestClientException {
		return expandAttachmentForHumansWithHttpInfo(id).getBody();
	}

	/**
	 * Get all metadata for an expanded attachment Returns the metadata for the
	 * contents of an attachment, if it is an archive, and metadata for the
	 * attachment itself. For example, if the attachment is a ZIP archive, then
	 * information about the files in the archive is returned and metadata for the
	 * ZIP archive. Currently, only the ZIP archive format is supported. Use this
	 * operation to retrieve data that is presented to the user, as this operation
	 * returns the metadata for the attachment itself, such as the attachment&#x27;s
	 * ID and name. Otherwise, use [ Get contents metadata for an expanded
	 * attachment](#api-rest-api-3-attachment-id-expand-raw-get), which only returns
	 * the metadata for the attachment&#x27;s contents. This operation can be
	 * accessed anonymously. **[Permissions](#permissions) required:** For the issue
	 * containing the attachment: * *Browse projects* [project
	 * permission](https://confluence.atlassian.com/x/yodKLg) for the project that
	 * the issue is in. * If [issue-level
	 * security](https://confluence.atlassian.com/x/J4lKLg) is configured,
	 * issue-level security permission to view the issue. * If attachments are added
	 * in private comments, the comment-level restriction will be applied.
	 * <p>
	 * <b>200</b> - Returned if the request is successful. If an empty list is
	 * returned in the response, the attachment is empty, corrupt, or not an
	 * archive.
	 * <p>
	 * <b>401</b> - Returned if the authentication credentials are incorrect or
	 * missing.
	 * <p>
	 * <b>403</b> - The user does not have the necessary permission.
	 * <p>
	 * <b>404</b> - Returned if: * the attachment is not found. * attachments are
	 * disabled in the Jira settings.
	 * <p>
	 * <b>409</b> - Returned if the attachment is an archive, but not a supported
	 * archive format.
	 * 
	 * @param id The ID of the attachment. (required)
	 * @return ResponseEntity&lt;AttachmentArchiveMetadataReadable&gt;
	 * @throws RestClientException if an error occurs while attempting to invoke the
	 *                             API
	 */
	public ResponseEntity<AttachmentArchiveMetadataReadable> expandAttachmentForHumansWithHttpInfo(String id)
			throws RestClientException {
		Object postBody = null;
		// verify the required parameter 'id' is set
		if (id == null) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
					"Missing the required parameter 'id' when calling expandAttachmentForHumans");
		}
		// create path and map variables
		final Map<String, Object> uriVariables = new HashMap<String, Object>();
		uriVariables.put("id", id);
		String path = UriComponentsBuilder.fromPath("/rest/api/3/attachment/{id}/expand/human")
				.buildAndExpand(uriVariables).toUriString();

		final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
		final HttpHeaders headerParams = new HttpHeaders();
		final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

		final String[] accepts = { "application/json" };
		final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
		final String[] contentTypes = {};
		final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

		String[] authNames = ApiClient.AUTH_NAMES;

		ParameterizedTypeReference<AttachmentArchiveMetadataReadable> returnType = new ParameterizedTypeReference<AttachmentArchiveMetadataReadable>() {
		};
		return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept,
				contentType, authNames, returnType);
	}

	/**
	 * Get contents metadata for an expanded attachment Returns the metadata for the
	 * contents of an attachment, if it is an archive. For example, if the
	 * attachment is a ZIP archive, then information about the files in the archive
	 * is returned. Currently, only the ZIP archive format is supported. Use this
	 * operation if you are processing the data without presenting it to the user,
	 * as this operation only returns the metadata for the contents of the
	 * attachment. Otherwise, to retrieve data to present to the user, use [ Get all
	 * metadata for an expanded
	 * attachment](#api-rest-api-3-attachment-id-expand-human-get) which also
	 * returns the metadata for the attachment itself, such as the attachment&#x27;s
	 * ID and name. This operation can be accessed anonymously.
	 * **[Permissions](#permissions) required:** For the issue containing the
	 * attachment: * *Browse projects* [project
	 * permission](https://confluence.atlassian.com/x/yodKLg) for the project that
	 * the issue is in. * If [issue-level
	 * security](https://confluence.atlassian.com/x/J4lKLg) is configured,
	 * issue-level security permission to view the issue. * If attachments are added
	 * in private comments, the comment-level restriction will be applied.
	 * <p>
	 * <b>200</b> - Returned if the request is successful. If an empty list is
	 * returned in the response, the attachment is empty, corrupt, or not an
	 * archive.
	 * <p>
	 * <b>401</b> - Returned if the authentication credentials are incorrect or
	 * missing.
	 * <p>
	 * <b>403</b> - The user does not have the necessary permission.
	 * <p>
	 * <b>404</b> - Returned if: * the attachment is not found. * attachments are
	 * disabled in the Jira settings.
	 * <p>
	 * <b>409</b> - Returned if the attachment is an archive, but not a supported
	 * archive format.
	 * 
	 * @param id The ID of the attachment. (required)
	 * @return AttachmentArchiveImpl
	 * @throws RestClientException if an error occurs while attempting to invoke the
	 *                             API
	 */
	public AttachmentArchiveImpl expandAttachmentForMachines(String id) throws RestClientException {
		return expandAttachmentForMachinesWithHttpInfo(id).getBody();
	}

	/**
	 * Get contents metadata for an expanded attachment Returns the metadata for the
	 * contents of an attachment, if it is an archive. For example, if the
	 * attachment is a ZIP archive, then information about the files in the archive
	 * is returned. Currently, only the ZIP archive format is supported. Use this
	 * operation if you are processing the data without presenting it to the user,
	 * as this operation only returns the metadata for the contents of the
	 * attachment. Otherwise, to retrieve data to present to the user, use [ Get all
	 * metadata for an expanded
	 * attachment](#api-rest-api-3-attachment-id-expand-human-get) which also
	 * returns the metadata for the attachment itself, such as the attachment&#x27;s
	 * ID and name. This operation can be accessed anonymously.
	 * **[Permissions](#permissions) required:** For the issue containing the
	 * attachment: * *Browse projects* [project
	 * permission](https://confluence.atlassian.com/x/yodKLg) for the project that
	 * the issue is in. * If [issue-level
	 * security](https://confluence.atlassian.com/x/J4lKLg) is configured,
	 * issue-level security permission to view the issue. * If attachments are added
	 * in private comments, the comment-level restriction will be applied.
	 * <p>
	 * <b>200</b> - Returned if the request is successful. If an empty list is
	 * returned in the response, the attachment is empty, corrupt, or not an
	 * archive.
	 * <p>
	 * <b>401</b> - Returned if the authentication credentials are incorrect or
	 * missing.
	 * <p>
	 * <b>403</b> - The user does not have the necessary permission.
	 * <p>
	 * <b>404</b> - Returned if: * the attachment is not found. * attachments are
	 * disabled in the Jira settings.
	 * <p>
	 * <b>409</b> - Returned if the attachment is an archive, but not a supported
	 * archive format.
	 * 
	 * @param id The ID of the attachment. (required)
	 * @return ResponseEntity&lt;AttachmentArchiveImpl&gt;
	 * @throws RestClientException if an error occurs while attempting to invoke the
	 *                             API
	 */
	public ResponseEntity<AttachmentArchiveImpl> expandAttachmentForMachinesWithHttpInfo(String id)
			throws RestClientException {
		Object postBody = null;
		// verify the required parameter 'id' is set
		if (id == null) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
					"Missing the required parameter 'id' when calling expandAttachmentForMachines");
		}
		// create path and map variables
		final Map<String, Object> uriVariables = new HashMap<String, Object>();
		uriVariables.put("id", id);
		String path = UriComponentsBuilder.fromPath("/rest/api/3/attachment/{id}/expand/raw")
				.buildAndExpand(uriVariables).toUriString();

		final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
		final HttpHeaders headerParams = new HttpHeaders();
		final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

		final String[] accepts = { "application/json" };
		final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
		final String[] contentTypes = {};
		final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

		String[] authNames = ApiClient.AUTH_NAMES;

		ParameterizedTypeReference<AttachmentArchiveImpl> returnType = new ParameterizedTypeReference<AttachmentArchiveImpl>() {
		};
		return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept,
				contentType, authNames, returnType);
	}

	/**
	 * Get attachment metadata Returns the metadata for an attachment. Note that the
	 * attachment itself is not returned. This operation can be accessed
	 * anonymously. **[Permissions](#permissions) required:** * *Browse projects*
	 * [project permission](https://confluence.atlassian.com/x/yodKLg) for the
	 * project that the issue is in. * If [issue-level
	 * security](https://confluence.atlassian.com/x/J4lKLg) is configured,
	 * issue-level security permission to view the issue. * If attachments are added
	 * in private comments, the comment-level restriction will be applied.
	 * <p>
	 * <b>200</b> - Returned if the request is successful.
	 * <p>
	 * <b>401</b> - Returned if the authentication credentials are incorrect or
	 * missing.
	 * <p>
	 * <b>403</b> - Returned if the user does not have the necessary permission.
	 * <p>
	 * <b>404</b> - Returned if: * the attachment is not found. * attachments are
	 * disabled in the Jira settings.
	 * 
	 * @param id The ID of the attachment. (required)
	 * @return AttachmentMetadata
	 * @throws RestClientException if an error occurs while attempting to invoke the
	 *                             API
	 */
	public AttachmentMetadata getAttachment(String id) throws RestClientException {
		return getAttachmentWithHttpInfo(id).getBody();
	}

	/**
	 * Get attachment metadata Returns the metadata for an attachment. Note that the
	 * attachment itself is not returned. This operation can be accessed
	 * anonymously. **[Permissions](#permissions) required:** * *Browse projects*
	 * [project permission](https://confluence.atlassian.com/x/yodKLg) for the
	 * project that the issue is in. * If [issue-level
	 * security](https://confluence.atlassian.com/x/J4lKLg) is configured,
	 * issue-level security permission to view the issue. * If attachments are added
	 * in private comments, the comment-level restriction will be applied.
	 * <p>
	 * <b>200</b> - Returned if the request is successful.
	 * <p>
	 * <b>401</b> - Returned if the authentication credentials are incorrect or
	 * missing.
	 * <p>
	 * <b>403</b> - Returned if the user does not have the necessary permission.
	 * <p>
	 * <b>404</b> - Returned if: * the attachment is not found. * attachments are
	 * disabled in the Jira settings.
	 * 
	 * @param id The ID of the attachment. (required)
	 * @return ResponseEntity&lt;AttachmentMetadata&gt;
	 * @throws RestClientException if an error occurs while attempting to invoke the
	 *                             API
	 */
	public ResponseEntity<AttachmentMetadata> getAttachmentWithHttpInfo(String id) throws RestClientException {
		Object postBody = null;
		// verify the required parameter 'id' is set
		if (id == null) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
					"Missing the required parameter 'id' when calling getAttachment");
		}
		// create path and map variables
		final Map<String, Object> uriVariables = new HashMap<String, Object>();
		uriVariables.put("id", id);
		String path = UriComponentsBuilder.fromPath("/rest/api/3/attachment/{id}").buildAndExpand(uriVariables)
				.toUriString();

		final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
		final HttpHeaders headerParams = new HttpHeaders();
		final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

		final String[] accepts = { "application/json" };
		final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
		final String[] contentTypes = {};
		final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

		String[] authNames = ApiClient.AUTH_NAMES;

		ParameterizedTypeReference<AttachmentMetadata> returnType = new ParameterizedTypeReference<AttachmentMetadata>() {
		};
		return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept,
				contentType, authNames, returnType);
	}

	/**
	 * Get attachment content Returns the contents of an attachment. A
	 * &#x60;Range&#x60; header can be set to define a range of bytes within the
	 * attachment to download. See the [HTTP Range header
	 * standard](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Range)
	 * for details. To return a thumbnail of the attachment, use [Get attachment
	 * thumbnail](#api-rest-api-3-attachment-thumbnail-id-get). This operation can
	 * be accessed anonymously. **[Permissions](#permissions) required:** For the
	 * issue containing the attachment: * *Browse projects* [project
	 * permission](https://confluence.atlassian.com/x/yodKLg) for the project that
	 * the issue is in. * If [issue-level
	 * security](https://confluence.atlassian.com/x/J4lKLg) is configured,
	 * issue-level security permission to view the issue. * If attachments are added
	 * in private comments, the comment-level restriction will be applied.
	 * <p>
	 * <b>200</b> - Returned if the request is successful when &#x60;redirect&#x60;
	 * is set to &#x60;false&#x60;.
	 * <p>
	 * <b>206</b> - Returned if the request is successful when a &#x60;Range&#x60;
	 * header is provided and &#x60;redirect&#x60; is set to &#x60;false&#x60;.
	 * <p>
	 * <b>303</b> - Returned if the request is successful. See the
	 * &#x60;Location&#x60; header for the download URL.
	 * <p>
	 * <b>400</b> - Returned if the range supplied in the &#x60;Range&#x60; header
	 * is malformed.
	 * <p>
	 * <b>401</b> - Returned if the authentication credentials are incorrect.
	 * <p>
	 * <b>403</b> - The user does not have the necessary permission.
	 * <p>
	 * <b>404</b> - Returned if: * the attachment is not found. * attachments are
	 * disabled in the Jira settings.
	 * <p>
	 * <b>416</b> - Returned if the server is unable to satisfy the range of bytes
	 * provided.
	 * 
	 * @param id       The ID of the attachment. (required)
	 * @param redirect Whether a redirect is provided for the attachment download.
	 *                 Clients that do not automatically follow redirects can set
	 *                 this to &#x60;false&#x60; to avoid making multiple requests
	 *                 to download the attachment. (optional, default to true)
	 * @return List&lt;Object&gt;
	 * @throws RestClientException if an error occurs while attempting to invoke the
	 *                             API
	 */
	public List<Object> getAttachmentContent(String id, Boolean redirect) throws RestClientException {
		return getAttachmentContentWithHttpInfo(id, redirect).getBody();
	}

	/**
	 * Get attachment content Returns the contents of an attachment. A
	 * &#x60;Range&#x60; header can be set to define a range of bytes within the
	 * attachment to download. See the [HTTP Range header
	 * standard](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Range)
	 * for details. To return a thumbnail of the attachment, use [Get attachment
	 * thumbnail](#api-rest-api-3-attachment-thumbnail-id-get). This operation can
	 * be accessed anonymously. **[Permissions](#permissions) required:** For the
	 * issue containing the attachment: * *Browse projects* [project
	 * permission](https://confluence.atlassian.com/x/yodKLg) for the project that
	 * the issue is in. * If [issue-level
	 * security](https://confluence.atlassian.com/x/J4lKLg) is configured,
	 * issue-level security permission to view the issue. * If attachments are added
	 * in private comments, the comment-level restriction will be applied.
	 * <p>
	 * <b>200</b> - Returned if the request is successful when &#x60;redirect&#x60;
	 * is set to &#x60;false&#x60;.
	 * <p>
	 * <b>206</b> - Returned if the request is successful when a &#x60;Range&#x60;
	 * header is provided and &#x60;redirect&#x60; is set to &#x60;false&#x60;.
	 * <p>
	 * <b>303</b> - Returned if the request is successful. See the
	 * &#x60;Location&#x60; header for the download URL.
	 * <p>
	 * <b>400</b> - Returned if the range supplied in the &#x60;Range&#x60; header
	 * is malformed.
	 * <p>
	 * <b>401</b> - Returned if the authentication credentials are incorrect.
	 * <p>
	 * <b>403</b> - The user does not have the necessary permission.
	 * <p>
	 * <b>404</b> - Returned if: * the attachment is not found. * attachments are
	 * disabled in the Jira settings.
	 * <p>
	 * <b>416</b> - Returned if the server is unable to satisfy the range of bytes
	 * provided.
	 * 
	 * @param id       The ID of the attachment. (required)
	 * @param redirect Whether a redirect is provided for the attachment download.
	 *                 Clients that do not automatically follow redirects can set
	 *                 this to &#x60;false&#x60; to avoid making multiple requests
	 *                 to download the attachment. (optional, default to true)
	 * @return ResponseEntity&lt;List&lt;Object&gt;&gt;
	 * @throws RestClientException if an error occurs while attempting to invoke the
	 *                             API
	 */
	public ResponseEntity<List<Object>> getAttachmentContentWithHttpInfo(String id, Boolean redirect)
			throws RestClientException {
		Object postBody = null;
		// verify the required parameter 'id' is set
		if (id == null) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
					"Missing the required parameter 'id' when calling getAttachmentContent");
		}
		// create path and map variables
		final Map<String, Object> uriVariables = new HashMap<String, Object>();
		uriVariables.put("id", id);
		String path = UriComponentsBuilder.fromPath("/rest/api/3/attachment/content/{id}").buildAndExpand(uriVariables)
				.toUriString();

		final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
		final HttpHeaders headerParams = new HttpHeaders();
		final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
		queryParams.putAll(apiClient.parameterToMultiValueMap(null, "redirect", redirect));

		final String[] accepts = { "application/json" };
		final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
		final String[] contentTypes = {};
		final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

		String[] authNames = ApiClient.AUTH_NAMES;

		ParameterizedTypeReference<List<Object>> returnType = new ParameterizedTypeReference<List<Object>>() {
		};
		return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept,
				contentType, authNames, returnType);
	}

	public InputStream streamAttachmentContent(String id, String attachmentFileName, String attachmentContentType,
			String attachmentUrl) throws RestClientException, IOException {
		// verify the required parameter 'id' is set
		if (id == null) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
					"Missing the required parameter 'id' when calling getAttachmentContent");
		}
		final String[] accepts = { "application/json" };
		final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
		String[] authNames = ApiClient.AUTH_NAMES;
		return apiClient.getStreamApi(attachmentUrl, accept, authNames);
	}

	/**
	 * Get Jira attachment settings Returns the attachment settings, that is,
	 * whether attachments are enabled and the maximum attachment size allowed. Note
	 * that there are also [project
	 * permissions](https://confluence.atlassian.com/x/yodKLg) that restrict whether
	 * users can create and delete attachments. This operation can be accessed
	 * anonymously. **[Permissions](#permissions) required:** None.
	 * <p>
	 * <b>200</b> - Returned if the request is successful.
	 * <p>
	 * <b>401</b> - Returned if the authentication credentials are incorrect or
	 * missing.
	 * 
	 * @return AttachmentSettings
	 * @throws RestClientException if an error occurs while attempting to invoke the
	 *                             API
	 */
	public AttachmentSettings getAttachmentMeta() throws RestClientException {
		return getAttachmentMetaWithHttpInfo().getBody();
	}

	/**
	 * Get Jira attachment settings Returns the attachment settings, that is,
	 * whether attachments are enabled and the maximum attachment size allowed. Note
	 * that there are also [project
	 * permissions](https://confluence.atlassian.com/x/yodKLg) that restrict whether
	 * users can create and delete attachments. This operation can be accessed
	 * anonymously. **[Permissions](#permissions) required:** None.
	 * <p>
	 * <b>200</b> - Returned if the request is successful.
	 * <p>
	 * <b>401</b> - Returned if the authentication credentials are incorrect or
	 * missing.
	 * 
	 * @return ResponseEntity&lt;AttachmentSettings&gt;
	 * @throws RestClientException if an error occurs while attempting to invoke the
	 *                             API
	 */
	public ResponseEntity<AttachmentSettings> getAttachmentMetaWithHttpInfo() throws RestClientException {
		Object postBody = null;
		String path = UriComponentsBuilder.fromPath("/rest/api/3/attachment/meta").build().toUriString();

		final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
		final HttpHeaders headerParams = new HttpHeaders();
		final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

		final String[] accepts = { "application/json" };
		final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
		final String[] contentTypes = {};
		final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

		String[] authNames = ApiClient.AUTH_NAMES;

		ParameterizedTypeReference<AttachmentSettings> returnType = new ParameterizedTypeReference<AttachmentSettings>() {
		};
		return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept,
				contentType, authNames, returnType);
	}

	/**
	 * Get attachment thumbnail Returns the thumbnail of an attachment. To return
	 * the attachment contents, use [Get attachment
	 * content](#api-rest-api-3-attachment-content-id-get). This operation can be
	 * accessed anonymously. **[Permissions](#permissions) required:** For the issue
	 * containing the attachment: * *Browse projects* [project
	 * permission](https://confluence.atlassian.com/x/yodKLg) for the project that
	 * the issue is in. * If [issue-level
	 * security](https://confluence.atlassian.com/x/J4lKLg) is configured,
	 * issue-level security permission to view the issue. * If attachments are added
	 * in private comments, the comment-level restriction will be applied.
	 * <p>
	 * <b>200</b> - Returned if the request is successful when &#x60;redirect&#x60;
	 * is set to &#x60;false&#x60;.
	 * <p>
	 * <b>303</b> - Returned if the request is successful. See the
	 * &#x60;Location&#x60; header for the download URL.
	 * <p>
	 * <b>400</b> - Returned if the request is invalid.
	 * <p>
	 * <b>401</b> - Returned if the authentication credentials are incorrect.
	 * <p>
	 * <b>403</b> - The user does not have the necessary permission.
	 * <p>
	 * <b>404</b> - Returned if: * the attachment is not found. * attachments are
	 * disabled in the Jira settings. * &#x60;fallbackToDefault&#x60; is
	 * &#x60;false&#x60; and the request thumbnail cannot be downloaded.
	 * 
	 * @param id                The ID of the attachment. (required)
	 * @param redirect          Whether a redirect is provided for the attachment
	 *                          download. Clients that do not automatically follow
	 *                          redirects can set this to &#x60;false&#x60; to avoid
	 *                          making multiple requests to download the attachment.
	 *                          (optional, default to true)
	 * @param fallbackToDefault Whether a default thumbnail is returned when the
	 *                          requested thumbnail is not found. (optional, default
	 *                          to true)
	 * @param width             The maximum width to scale the thumbnail to.
	 *                          (optional)
	 * @param height            The maximum height to scale the thumbnail to.
	 *                          (optional)
	 * @return List&lt;Object&gt;
	 * @throws RestClientException if an error occurs while attempting to invoke the
	 *                             API
	 */
	public List<Object> getAttachmentThumbnail(String id, Boolean redirect, Boolean fallbackToDefault, Integer width,
			Integer height) throws RestClientException {
		return getAttachmentThumbnailWithHttpInfo(id, redirect, fallbackToDefault, width, height).getBody();
	}

	/**
	 * Get attachment thumbnail Returns the thumbnail of an attachment. To return
	 * the attachment contents, use [Get attachment
	 * content](#api-rest-api-3-attachment-content-id-get). This operation can be
	 * accessed anonymously. **[Permissions](#permissions) required:** For the issue
	 * containing the attachment: * *Browse projects* [project
	 * permission](https://confluence.atlassian.com/x/yodKLg) for the project that
	 * the issue is in. * If [issue-level
	 * security](https://confluence.atlassian.com/x/J4lKLg) is configured,
	 * issue-level security permission to view the issue. * If attachments are added
	 * in private comments, the comment-level restriction will be applied.
	 * <p>
	 * <b>200</b> - Returned if the request is successful when &#x60;redirect&#x60;
	 * is set to &#x60;false&#x60;.
	 * <p>
	 * <b>303</b> - Returned if the request is successful. See the
	 * &#x60;Location&#x60; header for the download URL.
	 * <p>
	 * <b>400</b> - Returned if the request is invalid.
	 * <p>
	 * <b>401</b> - Returned if the authentication credentials are incorrect.
	 * <p>
	 * <b>403</b> - The user does not have the necessary permission.
	 * <p>
	 * <b>404</b> - Returned if: * the attachment is not found. * attachments are
	 * disabled in the Jira settings. * &#x60;fallbackToDefault&#x60; is
	 * &#x60;false&#x60; and the request thumbnail cannot be downloaded.
	 * 
	 * @param id                The ID of the attachment. (required)
	 * @param redirect          Whether a redirect is provided for the attachment
	 *                          download. Clients that do not automatically follow
	 *                          redirects can set this to &#x60;false&#x60; to avoid
	 *                          making multiple requests to download the attachment.
	 *                          (optional, default to true)
	 * @param fallbackToDefault Whether a default thumbnail is returned when the
	 *                          requested thumbnail is not found. (optional, default
	 *                          to true)
	 * @param width             The maximum width to scale the thumbnail to.
	 *                          (optional)
	 * @param height            The maximum height to scale the thumbnail to.
	 *                          (optional)
	 * @return ResponseEntity&lt;List&lt;Object&gt;&gt;
	 * @throws RestClientException if an error occurs while attempting to invoke the
	 *                             API
	 */
	public ResponseEntity<List<Object>> getAttachmentThumbnailWithHttpInfo(String id, Boolean redirect,
			Boolean fallbackToDefault, Integer width, Integer height) throws RestClientException {
		Object postBody = null;
		// verify the required parameter 'id' is set
		if (id == null) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
					"Missing the required parameter 'id' when calling getAttachmentThumbnail");
		}
		// create path and map variables
		final Map<String, Object> uriVariables = new HashMap<String, Object>();
		uriVariables.put("id", id);
		String path = UriComponentsBuilder.fromPath("/rest/api/3/attachment/thumbnail/{id}")
				.buildAndExpand(uriVariables).toUriString();

		final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
		final HttpHeaders headerParams = new HttpHeaders();
		final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
		queryParams.putAll(apiClient.parameterToMultiValueMap(null, "redirect", redirect));
		queryParams.putAll(apiClient.parameterToMultiValueMap(null, "fallbackToDefault", fallbackToDefault));
		queryParams.putAll(apiClient.parameterToMultiValueMap(null, "width", width));
		queryParams.putAll(apiClient.parameterToMultiValueMap(null, "height", height));

		final String[] accepts = { "application/json" };
		final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
		final String[] contentTypes = {};
		final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

		String[] authNames = ApiClient.AUTH_NAMES;

		ParameterizedTypeReference<List<Object>> returnType = new ParameterizedTypeReference<List<Object>>() {
		};
		return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept,
				contentType, authNames, returnType);
	}

	/**
	 * Delete attachment Deletes an attachment from an issue. This operation can be
	 * accessed anonymously. **[Permissions](#permissions) required:** For the
	 * project holding the issue containing the attachment: * *Delete own
	 * attachments* [project permission](https://confluence.atlassian.com/x/yodKLg)
	 * to delete an attachment created by the calling user. * *Delete all
	 * attachments* [project permission](https://confluence.atlassian.com/x/yodKLg)
	 * to delete an attachment created by any user.
	 * <p>
	 * <b>204</b> - Returned if the request is successful.
	 * <p>
	 * <b>403</b> - Returned if the user does not have the necessary permission.
	 * <p>
	 * <b>404</b> - Returned if: * the attachment is not found. * attachments are
	 * disabled in the Jira settings.
	 * 
	 * @param id The ID of the attachment. (required)
	 * @throws RestClientException if an error occurs while attempting to invoke the
	 *                             API
	 */
	public void removeAttachment(String id) throws RestClientException {
		removeAttachmentWithHttpInfo(id);
	}

	/**
	 * Delete attachment Deletes an attachment from an issue. This operation can be
	 * accessed anonymously. **[Permissions](#permissions) required:** For the
	 * project holding the issue containing the attachment: * *Delete own
	 * attachments* [project permission](https://confluence.atlassian.com/x/yodKLg)
	 * to delete an attachment created by the calling user. * *Delete all
	 * attachments* [project permission](https://confluence.atlassian.com/x/yodKLg)
	 * to delete an attachment created by any user.
	 * <p>
	 * <b>204</b> - Returned if the request is successful.
	 * <p>
	 * <b>403</b> - Returned if the user does not have the necessary permission.
	 * <p>
	 * <b>404</b> - Returned if: * the attachment is not found. * attachments are
	 * disabled in the Jira settings.
	 * 
	 * @param id The ID of the attachment. (required)
	 * @return ResponseEntity&lt;Void&gt;
	 * @throws RestClientException if an error occurs while attempting to invoke the
	 *                             API
	 */
	public ResponseEntity<Void> removeAttachmentWithHttpInfo(String id) throws RestClientException {
		Object postBody = null;
		// verify the required parameter 'id' is set
		if (id == null) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
					"Missing the required parameter 'id' when calling removeAttachment");
		}
		// create path and map variables
		final Map<String, Object> uriVariables = new HashMap<String, Object>();
		uriVariables.put("id", id);
		String path = UriComponentsBuilder.fromPath("/rest/api/3/attachment/{id}").buildAndExpand(uriVariables)
				.toUriString();

		final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
		final HttpHeaders headerParams = new HttpHeaders();
		final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

		final String[] accepts = {};
		final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
		final String[] contentTypes = {};
		final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

		String[] authNames = ApiClient.AUTH_NAMES;

		ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {
		};
		return apiClient.invokeAPI(path, HttpMethod.DELETE, queryParams, postBody, headerParams, formParams, accept,
				contentType, authNames, returnType);
	}

}
