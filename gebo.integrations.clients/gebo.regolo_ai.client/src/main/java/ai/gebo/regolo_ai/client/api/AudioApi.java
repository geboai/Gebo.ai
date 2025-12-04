package ai.gebo.regolo_ai.client.api;//jersey3

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.gebo.regolo_ai.client.invoker.ApiClient;
import ai.gebo.regolo_ai.client.invoker.ApiException;
import ai.gebo.regolo_ai.client.invoker.Configuration;
import ai.gebo.regolo_ai.client.invoker.Pair;
import jakarta.ws.rs.core.GenericType;

@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2025-09-02T09:08:15.751524600+02:00[Europe/Rome]")
public class AudioApi {
    private ApiClient apiClient;

    public AudioApi() {
        this(Configuration.getDefaultApiClient());
    }

    public AudioApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Audio Speech
     * Same params as:  https://platform.openai.com/docs/api-reference/audio/createSpeech
     * @return Object
     * @throws ApiException if fails to make API call
     */
    public Object audioSpeechAudioSpeechPost() throws ApiException {
        Object localVarPostBody = null;
        // create path and map variables
        String localVarPath = "/audio/speech";

        // query params
        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();



        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "APIKeyHeader" };

        GenericType<Object> localVarReturnType = new GenericType<Object>() {};
        return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * Audio Speech
     * Same params as:  https://platform.openai.com/docs/api-reference/audio/createSpeech
     * @return Object
     * @throws ApiException if fails to make API call
     */
    public Object audioSpeechV1AudioSpeechPost() throws ApiException {
        Object localVarPostBody = null;
        // create path and map variables
        String localVarPath = "/v1/audio/speech";

        // query params
        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();



        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "APIKeyHeader" };

        GenericType<Object> localVarReturnType = new GenericType<Object>() {};
        return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * Audio Transcriptions
     * Same params as:  https://platform.openai.com/docs/api-reference/audio/createTranscription?lang&#x3D;curl
     * @return Object
     * @throws ApiException if fails to make API call
     */
    public Object audioTranscriptionsAudioTranscriptionsPost() throws ApiException {
        Object localVarPostBody = null;
        // create path and map variables
        String localVarPath = "/audio/transcriptions";

        // query params
        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();



        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = {
            "multipart/form-data"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "APIKeyHeader" };

        GenericType<Object> localVarReturnType = new GenericType<Object>() {};
        return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * Audio Transcriptions
     * Same params as:  https://platform.openai.com/docs/api-reference/audio/createTranscription?lang&#x3D;curl
     * @return Object
     * @throws ApiException if fails to make API call
     */
    public Object audioTranscriptionsV1AudioTranscriptionsPost() throws ApiException {
        Object localVarPostBody = null;
        // create path and map variables
        String localVarPath = "/v1/audio/transcriptions";

        // query params
        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();



        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = {
            "multipart/form-data"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "APIKeyHeader" };

        GenericType<Object> localVarReturnType = new GenericType<Object>() {};
        return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
}
