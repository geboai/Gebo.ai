/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.openai.integration.client.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.util.StreamUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import ai.gebo.openai.integration.client.model.OpenAIApiConfig;
import ai.gebo.openai.integration.client.model.OpenAIBatchStatus;
import ai.gebo.openai.integration.client.model.OpenAICreateVectorStoreFileBatchRequest;
import ai.gebo.openai.integration.client.model.OpenAICreateVectorStoreFileRequest;
import ai.gebo.openai.integration.client.model.OpenAICreateVectorStoreRequest;
import ai.gebo.openai.integration.client.model.OpenAIDeleteResponse;
import ai.gebo.openai.integration.client.model.OpenAIFile;
import ai.gebo.openai.integration.client.model.OpenAIFileList;
import ai.gebo.openai.integration.client.model.OpenAIFilePurpose;
import ai.gebo.openai.integration.client.model.OpenAIModel;
import ai.gebo.openai.integration.client.model.OpenAIModelsList;
import ai.gebo.openai.integration.client.model.OpenAIResultList;
import ai.gebo.openai.integration.client.model.OpenAIVectorStore;
import ai.gebo.openai.integration.client.model.OpenAIVectorStoreFile;
import ai.gebo.openai.integration.client.model.OpenAIVectorStoreFileBatch;
import ai.gebo.openai.integration.client.model.OpenAIVectorStoreFileBatchPagedList;
import ai.gebo.openai.integration.client.model.OpenAIVectorStoreList;
import ai.gebo.restintegration.abstraction.layer.GeboRestIntegrationException;
import ai.gebo.restintegration.abstraction.layer.RestTemplateWrapperService;

@Service
public class OpenAIApi {
	@Autowired
	RestTemplateWrapperService restTemplateWrapper;
	private static final String MODELS_LIST_PATH = "v1/models";
	private static final String VECTOR_STORE_PATH = "v1/vector_stores";
	private static final String FILES_PATH = "v1/files";

	protected HttpHeaders getHeaders(OpenAIApiConfig config) throws OpenAIClientException {
		HttpHeaders headers = new HttpHeaders();
		if ((config.getApiKey() == null || config.getApiKey().trim().length() == 0) && config.isApiKeyMandatory())
			throw new OpenAIClientException("Missing apiKey");
		if (config.getApiKey() != null) {
			headers.set("Authorization", "Bearer " + config.getApiKey());
		}
		return headers;
	}

	protected String getUrl(OpenAIApiConfig config, String relative) throws OpenAIClientException {
		StringBuffer buffer = new StringBuffer();
		if (config.getBasePath() == null || config.getBasePath().trim().length() == 0)
			throw new OpenAIClientException("Missing basePath");
		buffer.append(config.getBasePath());
		if (!config.getBasePath().endsWith("/")) {
			buffer.append("/");
		}
		buffer.append(relative);
		return buffer.toString();
	}

	public OpenAIModelsList getModels(OpenAIApiConfig config)
			throws OpenAIClientException, GeboRestIntegrationException {
		String url = getUrl(config, MODELS_LIST_PATH);

		HttpEntity<String> request = new HttpEntity<String>(getHeaders(config));
		return restTemplateWrapper.exchangeAndReturn(url, HttpMethod.GET, request, OpenAIModelsList.class);
	}

	public OpenAIVectorStore createVectorStore(OpenAIApiConfig config, OpenAICreateVectorStoreRequest request)
			throws GeboRestIntegrationException, OpenAIClientException {
		String url = getUrl(config, VECTOR_STORE_PATH);
		HttpEntity<OpenAICreateVectorStoreRequest> requestEntity = new HttpEntity<OpenAICreateVectorStoreRequest>(
				request, getHeaders(config));

		return restTemplateWrapper.exchangeAndReturn(url, HttpMethod.POST, requestEntity, OpenAIVectorStore.class);
	};

	public OpenAIVectorStoreFile createVectorStoreFile(OpenAIApiConfig config,
			OpenAICreateVectorStoreFileRequest request, String vector_store_id)
			throws OpenAIClientException, GeboRestIntegrationException {
		String url = getUrl(config, VECTOR_STORE_PATH) + "/" + URLEncoder.encode(vector_store_id) + "/files";
		HttpEntity<OpenAICreateVectorStoreFileRequest> requestEntity = new HttpEntity<OpenAICreateVectorStoreFileRequest>(
				request, getHeaders(config));
		return restTemplateWrapper.exchangeAndReturn(url, HttpMethod.POST, requestEntity, OpenAIVectorStoreFile.class);
	}

	public OpenAIDeleteResponse deleteVectorStoreFile(OpenAIApiConfig config, String vector_store_id, String id)
			throws OpenAIClientException, GeboRestIntegrationException {
		String url = getUrl(config, VECTOR_STORE_PATH) + "/" + URLEncoder.encode(vector_store_id) + "/"
				+ URLEncoder.encode(id);
		HttpEntity<String> request = new HttpEntity<String>(getHeaders(config));
		return restTemplateWrapper.exchangeAndReturn(url, HttpMethod.DELETE, request, OpenAIDeleteResponse.class);
	}

	public OpenAIVectorStoreFile getVectorStoreFile(OpenAIApiConfig config, String vector_store_id, String id)
			throws GeboRestIntegrationException, OpenAIClientException {
		String url = getUrl(config, VECTOR_STORE_PATH) + "/" + URLEncoder.encode(vector_store_id) + "/"
				+ URLEncoder.encode(id);
		HttpEntity<String> request = new HttpEntity<String>(getHeaders(config));
		return restTemplateWrapper.exchangeAndReturn(url, HttpMethod.GET, request, OpenAIVectorStoreFile.class);
	}

	public OpenAIVectorStoreList getVectorStores(OpenAIApiConfig config, int limit, String afterID, String beforeID)
			throws OpenAIClientException, GeboRestIntegrationException {
		String url = getUrl(config, VECTOR_STORE_PATH);
		url = url + "?limit=" + limit;
		if (afterID != null) {
			url = url + "&after=" + URLEncoder.encode(afterID);
		}
		if (beforeID != null) {
			url = url + "&before=" + URLEncoder.encode(beforeID);
		}
		HttpEntity<String> request = new HttpEntity<String>(getHeaders(config));
		return restTemplateWrapper.exchangeAndReturn(url, HttpMethod.GET, request, OpenAIVectorStoreList.class);
	};

	public OpenAIVectorStore getVectorStore(OpenAIApiConfig config, String id)
			throws OpenAIClientException, GeboRestIntegrationException {
		String url = getUrl(config, VECTOR_STORE_PATH) + "/" + URLEncoder.encode(id);
		HttpEntity<String> request = new HttpEntity<String>(getHeaders(config));
		return restTemplateWrapper.exchangeAndReturn(url, HttpMethod.GET, request, OpenAIVectorStore.class);
	}

	public OpenAIDeleteResponse deleteVectorStore(OpenAIApiConfig config, String id)
			throws OpenAIClientException, GeboRestIntegrationException {
		String url = getUrl(config, VECTOR_STORE_PATH) + "/" + URLEncoder.encode(id);
		HttpEntity<String> request = new HttpEntity<String>(getHeaders(config));
		return restTemplateWrapper.exchangeAndReturn(url, HttpMethod.DELETE, request, OpenAIDeleteResponse.class);
	}

	public OpenAIVectorStoreFileBatch createVectorStoreFileBatch(OpenAIApiConfig config,
			OpenAICreateVectorStoreFileBatchRequest request, String vector_store_id)
			throws OpenAIClientException, GeboRestIntegrationException {
		String url = getUrl(config, VECTOR_STORE_PATH) + "/" + URLEncoder.encode(vector_store_id) + "/file_batches";
		HttpEntity<OpenAICreateVectorStoreFileBatchRequest> requestEntity = new HttpEntity<OpenAICreateVectorStoreFileBatchRequest>(
				request, getHeaders(config));
		return restTemplateWrapper.exchangeAndReturn(url, HttpMethod.POST, requestEntity,
				OpenAIVectorStoreFileBatch.class);
	}

	public OpenAIVectorStoreFileBatch getVectorStoreFileBatch(OpenAIApiConfig config, String vector_store_id, String id)
			throws OpenAIClientException, GeboRestIntegrationException {
		String url = getUrl(config, VECTOR_STORE_PATH) + "/" + URLEncoder.encode(vector_store_id) + "/file_batches/"
				+ URLEncoder.encode(id);
		HttpEntity<String> request = new HttpEntity<String>(getHeaders(config));
		return restTemplateWrapper.exchangeAndReturn(url, HttpMethod.GET, request, OpenAIVectorStoreFileBatch.class);
	}

	public OpenAIVectorStoreFileBatchPagedList listVectorStoreFileBatch(OpenAIApiConfig config, String vector_store_id,
			String batch_id, int limit, String after, String before, OpenAIBatchStatus filter)
			throws OpenAIClientException, GeboRestIntegrationException {
		String url = getUrl(config, VECTOR_STORE_PATH) + "/" + URLEncoder.encode(vector_store_id) + "/file_batches/"
				+ URLEncoder.encode(batch_id) + "/files?limit=" + limit;
		if (after != null && after.trim().length() > 0) {
			url += "&after=" + URLEncoder.encode(after);
		}
		if (before != null && before.trim().length() > 0) {
			url += "&before=" + URLEncoder.encode(before);
		}
		if (filter != null) {
			url += "&filter=" + filter.name();
		}
		HttpEntity<String> request = new HttpEntity<String>(getHeaders(config));
		return restTemplateWrapper.exchangeAndReturn(url, HttpMethod.GET, request,
				OpenAIVectorStoreFileBatchPagedList.class);
	}

	public OpenAIFile uploadOpenAIFile(OpenAIApiConfig config, OpenAIFilePurpose purpose, String contentType,
			InputStream is) throws OpenAIClientException, GeboRestIntegrationException {
		String url = getUrl(config, FILES_PATH);
		HttpHeaders header = getHeaders(config);
		header.setContentType(MediaType.MULTIPART_FORM_DATA);
		InputStreamResource resource = new InputStreamResource(is);
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("file", resource);
		body.add("purpose", purpose.getPurposeCode());
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, header);
		return restTemplateWrapper.exchangeAndReturn(url, HttpMethod.POST, requestEntity, OpenAIFile.class);
	}

	public OpenAIFileList listOpenAIFiles(OpenAIApiConfig config, int limit, String after, OpenAIFilePurpose purpose)
			throws OpenAIClientException, GeboRestIntegrationException {
		String url = getUrl(config, FILES_PATH) + "?limit=" + limit;
		if (after != null && after.trim().length() > 0) {
			url += "&after=" + URLEncoder.encode(after);
		}

		if (purpose != null) {
			url += "&purpose=" + purpose.getPurposeCode();
		}
		HttpEntity<String> request = new HttpEntity<String>(getHeaders(config));
		return restTemplateWrapper.exchangeAndReturn(url, HttpMethod.GET, request, OpenAIFileList.class);
	}

	public OpenAIFile getOpenAIFile(OpenAIApiConfig config, String id)
			throws OpenAIClientException, GeboRestIntegrationException {
		String url = getUrl(config, FILES_PATH) + "/" + URLEncoder.encode(id);
		HttpEntity<String> request = new HttpEntity<String>(getHeaders(config));
		return restTemplateWrapper.exchangeAndReturn(url, HttpMethod.GET, request, OpenAIFile.class);
	}

	public OpenAIDeleteResponse deleteOpenAIFile(OpenAIApiConfig config, String id)
			throws OpenAIClientException, GeboRestIntegrationException {
		String url = getUrl(config, FILES_PATH) + "/" + URLEncoder.encode(id);
		HttpEntity<String> request = new HttpEntity<String>(getHeaders(config));
		return restTemplateWrapper.exchangeAndReturn(url, HttpMethod.DELETE, request, OpenAIDeleteResponse.class);
	}

	public File getOpenAIFileContent(OpenAIApiConfig config, String id)
			throws OpenAIClientException, GeboRestIntegrationException, URISyntaxException {
		String url = getUrl(config, FILES_PATH) + "/" + URLEncoder.encode(id) + "/content";
		HttpEntity<String> request = new HttpEntity<String>(getHeaders(config));

		RequestCallback callback = null;
		ResponseExtractor<File> extractor = (r) -> {
			File ret = File.createTempFile("download", "tmp");
			FileCopyUtils.copy(r.getBody(), new FileOutputStream(ret));
			return ret;
		};
		return restTemplateWrapper.execute(url, HttpMethod.GET, callback, extractor);
	}
}
