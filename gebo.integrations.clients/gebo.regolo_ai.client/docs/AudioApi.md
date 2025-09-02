# AudioApi

All URIs are relative to *https://api.regolo.ai*

Method | HTTP request | Description
------------- | ------------- | -------------
[**audioSpeechAudioSpeechPost**](AudioApi.md#audioSpeechAudioSpeechPost) | **POST** /audio/speech | Audio Speech
[**audioSpeechV1AudioSpeechPost**](AudioApi.md#audioSpeechV1AudioSpeechPost) | **POST** /v1/audio/speech | Audio Speech
[**audioTranscriptionsAudioTranscriptionsPost**](AudioApi.md#audioTranscriptionsAudioTranscriptionsPost) | **POST** /audio/transcriptions | Audio Transcriptions
[**audioTranscriptionsV1AudioTranscriptionsPost**](AudioApi.md#audioTranscriptionsV1AudioTranscriptionsPost) | **POST** /v1/audio/transcriptions | Audio Transcriptions

<a name="audioSpeechAudioSpeechPost"></a>
# **audioSpeechAudioSpeechPost**
> Object audioSpeechAudioSpeechPost()

Audio Speech

Same params as:  https://platform.openai.com/docs/api-reference/audio/createSpeech

### Example
```java
// Import classes:
//import ai.gebo.regolo_ai.client.invoker.ApiClient;
//import ai.gebo.regolo_ai.client.invoker.ApiException;
//import ai.gebo.regolo_ai.client.invoker.Configuration;
//import ai.gebo.regolo_ai.client.invoker.auth.*;
//import ai.gebo.regolo_ai.client.api.AudioApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: APIKeyHeader
ApiKeyAuth APIKeyHeader = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
APIKeyHeader.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//APIKeyHeader.setApiKeyPrefix("Token");

AudioApi apiInstance = new AudioApi();
try {
    Object result = apiInstance.audioSpeechAudioSpeechPost();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AudioApi#audioSpeechAudioSpeechPost");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**Object**

### Authorization

[APIKeyHeader](../README.md#APIKeyHeader)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="audioSpeechV1AudioSpeechPost"></a>
# **audioSpeechV1AudioSpeechPost**
> Object audioSpeechV1AudioSpeechPost()

Audio Speech

Same params as:  https://platform.openai.com/docs/api-reference/audio/createSpeech

### Example
```java
// Import classes:
//import ai.gebo.regolo_ai.client.invoker.ApiClient;
//import ai.gebo.regolo_ai.client.invoker.ApiException;
//import ai.gebo.regolo_ai.client.invoker.Configuration;
//import ai.gebo.regolo_ai.client.invoker.auth.*;
//import ai.gebo.regolo_ai.client.api.AudioApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: APIKeyHeader
ApiKeyAuth APIKeyHeader = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
APIKeyHeader.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//APIKeyHeader.setApiKeyPrefix("Token");

AudioApi apiInstance = new AudioApi();
try {
    Object result = apiInstance.audioSpeechV1AudioSpeechPost();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AudioApi#audioSpeechV1AudioSpeechPost");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**Object**

### Authorization

[APIKeyHeader](../README.md#APIKeyHeader)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="audioTranscriptionsAudioTranscriptionsPost"></a>
# **audioTranscriptionsAudioTranscriptionsPost**
> Object audioTranscriptionsAudioTranscriptionsPost()

Audio Transcriptions

Same params as:  https://platform.openai.com/docs/api-reference/audio/createTranscription?lang&#x3D;curl

### Example
```java
// Import classes:
//import ai.gebo.regolo_ai.client.invoker.ApiClient;
//import ai.gebo.regolo_ai.client.invoker.ApiException;
//import ai.gebo.regolo_ai.client.invoker.Configuration;
//import ai.gebo.regolo_ai.client.invoker.auth.*;
//import ai.gebo.regolo_ai.client.api.AudioApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: APIKeyHeader
ApiKeyAuth APIKeyHeader = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
APIKeyHeader.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//APIKeyHeader.setApiKeyPrefix("Token");

AudioApi apiInstance = new AudioApi();
try {
    Object result = apiInstance.audioTranscriptionsAudioTranscriptionsPost();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AudioApi#audioTranscriptionsAudioTranscriptionsPost");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**Object**

### Authorization

[APIKeyHeader](../README.md#APIKeyHeader)

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: application/json

<a name="audioTranscriptionsV1AudioTranscriptionsPost"></a>
# **audioTranscriptionsV1AudioTranscriptionsPost**
> Object audioTranscriptionsV1AudioTranscriptionsPost()

Audio Transcriptions

Same params as:  https://platform.openai.com/docs/api-reference/audio/createTranscription?lang&#x3D;curl

### Example
```java
// Import classes:
//import ai.gebo.regolo_ai.client.invoker.ApiClient;
//import ai.gebo.regolo_ai.client.invoker.ApiException;
//import ai.gebo.regolo_ai.client.invoker.Configuration;
//import ai.gebo.regolo_ai.client.invoker.auth.*;
//import ai.gebo.regolo_ai.client.api.AudioApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: APIKeyHeader
ApiKeyAuth APIKeyHeader = (ApiKeyAuth) defaultClient.getAuthentication("APIKeyHeader");
APIKeyHeader.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//APIKeyHeader.setApiKeyPrefix("Token");

AudioApi apiInstance = new AudioApi();
try {
    Object result = apiInstance.audioTranscriptionsV1AudioTranscriptionsPost();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AudioApi#audioTranscriptionsV1AudioTranscriptionsPost");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**Object**

### Authorization

[APIKeyHeader](../README.md#APIKeyHeader)

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: application/json

