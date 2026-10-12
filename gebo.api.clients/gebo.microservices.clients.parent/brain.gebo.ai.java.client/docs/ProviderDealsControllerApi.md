# ProviderDealsControllerApi

All URIs are relative to *http://localhost:13001/brain*

Method | HTTP request | Description
------------- | ------------- | -------------
[**assignApiKey**](ProviderDealsControllerApi.md#assignApiKey) | **POST** /api/admin/ProviderDealsController/assignApiKey | 
[**createProviderDeal**](ProviderDealsControllerApi.md#createProviderDeal) | **POST** /api/admin/ProviderDealsController/createProviderDeal | 
[**deleteProviderDeal**](ProviderDealsControllerApi.md#deleteProviderDeal) | **POST** /api/admin/ProviderDealsController/deleteProviderDeal | 
[**getConfiguredProviderIds**](ProviderDealsControllerApi.md#getConfiguredProviderIds) | **GET** /api/admin/ProviderDealsController/getConfiguredProviderIds | 
[**getCurrencies**](ProviderDealsControllerApi.md#getCurrencies) | **GET** /api/admin/ProviderDealsController/getCurrencies | 
[**getProviderApiKeys**](ProviderDealsControllerApi.md#getProviderApiKeys) | **GET** /api/admin/ProviderDealsController/getProviderApiKeys | 
[**getProviderCurrency**](ProviderDealsControllerApi.md#getProviderCurrency) | **GET** /api/admin/ProviderDealsController/getProviderCurrency | 
[**getProviderDeal**](ProviderDealsControllerApi.md#getProviderDeal) | **GET** /api/admin/ProviderDealsController/getProviderDeal | 
[**getProviderDealProviderIds**](ProviderDealsControllerApi.md#getProviderDealProviderIds) | **GET** /api/admin/ProviderDealsController/getProviderDealProviderIds | 
[**getProviderDeals**](ProviderDealsControllerApi.md#getProviderDeals) | **GET** /api/admin/ProviderDealsController/getProviderDeals | 
[**getProviderModelPrices**](ProviderDealsControllerApi.md#getProviderModelPrices) | **GET** /api/admin/ProviderDealsController/getProviderModelPrices | 
[**refreshImportedLimits**](ProviderDealsControllerApi.md#refreshImportedLimits) | **POST** /api/admin/ProviderDealsController/refreshImportedLimits | 
[**removeApiKey**](ProviderDealsControllerApi.md#removeApiKey) | **POST** /api/admin/ProviderDealsController/removeApiKey | 
[**updateDescription**](ProviderDealsControllerApi.md#updateDescription) | **POST** /api/admin/ProviderDealsController/updateDescription | 
[**updateFlatConditions**](ProviderDealsControllerApi.md#updateFlatConditions) | **POST** /api/admin/ProviderDealsController/updateFlatConditions | 
[**updateModelPricing**](ProviderDealsControllerApi.md#updateModelPricing) | **POST** /api/admin/ProviderDealsController/updateModelPricing | 
[**updateProviderCurrency**](ProviderDealsControllerApi.md#updateProviderCurrency) | **POST** /api/admin/ProviderDealsController/updateProviderCurrency | 
[**updateSpendingLimits**](ProviderDealsControllerApi.md#updateSpendingLimits) | **POST** /api/admin/ProviderDealsController/updateSpendingLimits | 

<a name="assignApiKey"></a>
# **assignApiKey**
> OperationStatusGProviderDeal assignApiKey(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ProviderDealsControllerApi;


ProviderDealsControllerApi apiInstance = new ProviderDealsControllerApi();
ProviderDealKeyRequest body = new ProviderDealKeyRequest(); // ProviderDealKeyRequest | 
try {
    OperationStatusGProviderDeal result = apiInstance.assignApiKey(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProviderDealsControllerApi#assignApiKey");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ProviderDealKeyRequest**](ProviderDealKeyRequest.md)|  |

### Return type

[**OperationStatusGProviderDeal**](OperationStatusGProviderDeal.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createProviderDeal"></a>
# **createProviderDeal**
> OperationStatusGProviderDeal createProviderDeal(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ProviderDealsControllerApi;


ProviderDealsControllerApi apiInstance = new ProviderDealsControllerApi();
CreateProviderDealRequest body = new CreateProviderDealRequest(); // CreateProviderDealRequest | 
try {
    OperationStatusGProviderDeal result = apiInstance.createProviderDeal(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProviderDealsControllerApi#createProviderDeal");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**CreateProviderDealRequest**](CreateProviderDealRequest.md)|  |

### Return type

[**OperationStatusGProviderDeal**](OperationStatusGProviderDeal.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deleteProviderDeal"></a>
# **deleteProviderDeal**
> OperationStatusBoolean deleteProviderDeal(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ProviderDealsControllerApi;


ProviderDealsControllerApi apiInstance = new ProviderDealsControllerApi();
ProviderDealKeyRequest body = new ProviderDealKeyRequest(); // ProviderDealKeyRequest | 
try {
    OperationStatusBoolean result = apiInstance.deleteProviderDeal(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProviderDealsControllerApi#deleteProviderDeal");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ProviderDealKeyRequest**](ProviderDealKeyRequest.md)|  |

### Return type

[**OperationStatusBoolean**](OperationStatusBoolean.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getConfiguredProviderIds"></a>
# **getConfiguredProviderIds**
> List&lt;String&gt; getConfiguredProviderIds()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ProviderDealsControllerApi;


ProviderDealsControllerApi apiInstance = new ProviderDealsControllerApi();
try {
    List<String> result = apiInstance.getConfiguredProviderIds();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProviderDealsControllerApi#getConfiguredProviderIds");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**List&lt;String&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getCurrencies"></a>
# **getCurrencies**
> List&lt;GCurrency&gt; getCurrencies()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ProviderDealsControllerApi;


ProviderDealsControllerApi apiInstance = new ProviderDealsControllerApi();
try {
    List<GCurrency> result = apiInstance.getCurrencies();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProviderDealsControllerApi#getCurrencies");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;GCurrency&gt;**](GCurrency.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getProviderApiKeys"></a>
# **getProviderApiKeys**
> OperationStatusListGProviderApiKey getProviderApiKeys(providerId)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ProviderDealsControllerApi;


ProviderDealsControllerApi apiInstance = new ProviderDealsControllerApi();
String providerId = "providerId_example"; // String | 
try {
    OperationStatusListGProviderApiKey result = apiInstance.getProviderApiKeys(providerId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProviderDealsControllerApi#getProviderApiKeys");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **providerId** | **String**|  |

### Return type

[**OperationStatusListGProviderApiKey**](OperationStatusListGProviderApiKey.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getProviderCurrency"></a>
# **getProviderCurrency**
> OperationStatusGProviderCurrency getProviderCurrency(providerId)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ProviderDealsControllerApi;


ProviderDealsControllerApi apiInstance = new ProviderDealsControllerApi();
String providerId = "providerId_example"; // String | 
try {
    OperationStatusGProviderCurrency result = apiInstance.getProviderCurrency(providerId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProviderDealsControllerApi#getProviderCurrency");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **providerId** | **String**|  |

### Return type

[**OperationStatusGProviderCurrency**](OperationStatusGProviderCurrency.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getProviderDeal"></a>
# **getProviderDeal**
> GProviderDeal getProviderDeal(dealId)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ProviderDealsControllerApi;


ProviderDealsControllerApi apiInstance = new ProviderDealsControllerApi();
String dealId = "dealId_example"; // String | 
try {
    GProviderDeal result = apiInstance.getProviderDeal(dealId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProviderDealsControllerApi#getProviderDeal");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **dealId** | **String**|  |

### Return type

[**GProviderDeal**](GProviderDeal.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getProviderDealProviderIds"></a>
# **getProviderDealProviderIds**
> List&lt;String&gt; getProviderDealProviderIds()



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ProviderDealsControllerApi;


ProviderDealsControllerApi apiInstance = new ProviderDealsControllerApi();
try {
    List<String> result = apiInstance.getProviderDealProviderIds();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProviderDealsControllerApi#getProviderDealProviderIds");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**List&lt;String&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getProviderDeals"></a>
# **getProviderDeals**
> List&lt;GProviderDeal&gt; getProviderDeals(providerId)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ProviderDealsControllerApi;


ProviderDealsControllerApi apiInstance = new ProviderDealsControllerApi();
String providerId = "providerId_example"; // String | 
try {
    List<GProviderDeal> result = apiInstance.getProviderDeals(providerId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProviderDealsControllerApi#getProviderDeals");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **providerId** | **String**|  | [optional]

### Return type

[**List&lt;GProviderDeal&gt;**](GProviderDeal.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getProviderModelPrices"></a>
# **getProviderModelPrices**
> OperationStatusListGProviderModelPriceInfo getProviderModelPrices(providerId)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ProviderDealsControllerApi;


ProviderDealsControllerApi apiInstance = new ProviderDealsControllerApi();
String providerId = "providerId_example"; // String | 
try {
    OperationStatusListGProviderModelPriceInfo result = apiInstance.getProviderModelPrices(providerId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProviderDealsControllerApi#getProviderModelPrices");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **providerId** | **String**|  |

### Return type

[**OperationStatusListGProviderModelPriceInfo**](OperationStatusListGProviderModelPriceInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="refreshImportedLimits"></a>
# **refreshImportedLimits**
> OperationStatusGProviderDeal refreshImportedLimits(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ProviderDealsControllerApi;


ProviderDealsControllerApi apiInstance = new ProviderDealsControllerApi();
ProviderDealKeyRequest body = new ProviderDealKeyRequest(); // ProviderDealKeyRequest | 
try {
    OperationStatusGProviderDeal result = apiInstance.refreshImportedLimits(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProviderDealsControllerApi#refreshImportedLimits");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ProviderDealKeyRequest**](ProviderDealKeyRequest.md)|  |

### Return type

[**OperationStatusGProviderDeal**](OperationStatusGProviderDeal.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="removeApiKey"></a>
# **removeApiKey**
> OperationStatusGProviderDeal removeApiKey(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ProviderDealsControllerApi;


ProviderDealsControllerApi apiInstance = new ProviderDealsControllerApi();
ProviderDealKeyRequest body = new ProviderDealKeyRequest(); // ProviderDealKeyRequest | 
try {
    OperationStatusGProviderDeal result = apiInstance.removeApiKey(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProviderDealsControllerApi#removeApiKey");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ProviderDealKeyRequest**](ProviderDealKeyRequest.md)|  |

### Return type

[**OperationStatusGProviderDeal**](OperationStatusGProviderDeal.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateDescription"></a>
# **updateDescription**
> OperationStatusGProviderDeal updateDescription(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ProviderDealsControllerApi;


ProviderDealsControllerApi apiInstance = new ProviderDealsControllerApi();
ProviderDealDescriptionRequest body = new ProviderDealDescriptionRequest(); // ProviderDealDescriptionRequest | 
try {
    OperationStatusGProviderDeal result = apiInstance.updateDescription(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProviderDealsControllerApi#updateDescription");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ProviderDealDescriptionRequest**](ProviderDealDescriptionRequest.md)|  |

### Return type

[**OperationStatusGProviderDeal**](OperationStatusGProviderDeal.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateFlatConditions"></a>
# **updateFlatConditions**
> OperationStatusGProviderDeal updateFlatConditions(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ProviderDealsControllerApi;


ProviderDealsControllerApi apiInstance = new ProviderDealsControllerApi();
ProviderDealFlatConditionsRequest body = new ProviderDealFlatConditionsRequest(); // ProviderDealFlatConditionsRequest | 
try {
    OperationStatusGProviderDeal result = apiInstance.updateFlatConditions(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProviderDealsControllerApi#updateFlatConditions");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ProviderDealFlatConditionsRequest**](ProviderDealFlatConditionsRequest.md)|  |

### Return type

[**OperationStatusGProviderDeal**](OperationStatusGProviderDeal.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateModelPricing"></a>
# **updateModelPricing**
> OperationStatusGProviderDeal updateModelPricing(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ProviderDealsControllerApi;


ProviderDealsControllerApi apiInstance = new ProviderDealsControllerApi();
ProviderDealModelPricingRequest body = new ProviderDealModelPricingRequest(); // ProviderDealModelPricingRequest | 
try {
    OperationStatusGProviderDeal result = apiInstance.updateModelPricing(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProviderDealsControllerApi#updateModelPricing");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ProviderDealModelPricingRequest**](ProviderDealModelPricingRequest.md)|  |

### Return type

[**OperationStatusGProviderDeal**](OperationStatusGProviderDeal.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateProviderCurrency"></a>
# **updateProviderCurrency**
> OperationStatusGProviderCurrency updateProviderCurrency(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ProviderDealsControllerApi;


ProviderDealsControllerApi apiInstance = new ProviderDealsControllerApi();
ProviderCurrencyRequest body = new ProviderCurrencyRequest(); // ProviderCurrencyRequest | 
try {
    OperationStatusGProviderCurrency result = apiInstance.updateProviderCurrency(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProviderDealsControllerApi#updateProviderCurrency");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ProviderCurrencyRequest**](ProviderCurrencyRequest.md)|  |

### Return type

[**OperationStatusGProviderCurrency**](OperationStatusGProviderCurrency.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateSpendingLimits"></a>
# **updateSpendingLimits**
> OperationStatusGProviderDeal updateSpendingLimits(body)



### Example
```java
// Import classes:
//import gebo.microservices.api.client.brain.invoker.ApiException;
//import gebo.microservices.api.client.brain.api.ProviderDealsControllerApi;


ProviderDealsControllerApi apiInstance = new ProviderDealsControllerApi();
ProviderDealSpendingLimitsRequest body = new ProviderDealSpendingLimitsRequest(); // ProviderDealSpendingLimitsRequest | 
try {
    OperationStatusGProviderDeal result = apiInstance.updateSpendingLimits(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProviderDealsControllerApi#updateSpendingLimits");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**ProviderDealSpendingLimitsRequest**](ProviderDealSpendingLimitsRequest.md)|  |

### Return type

[**OperationStatusGProviderDeal**](OperationStatusGProviderDeal.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

