# PriorityMapping

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**in** | **Map&lt;String, Long&gt;** | The mapping of priorities for issues being migrated **into** this priority scheme. Key is the old priority ID, value is the new priority ID (must exist in this priority scheme).  E.g. The current priority scheme has priority ID &#x60;10001&#x60;. Issues with priority ID &#x60;10000&#x60; are being migrated into this priority scheme will need mapping to new priorities. The &#x60;in&#x60; mapping would be &#x60;{\&quot;10000\&quot;: 10001}&#x60;. |  [optional]
**out** | **Map&lt;String, Long&gt;** | The mapping of priorities for issues being migrated **out of** this priority scheme. Key is the old priority ID (must exist in this priority scheme), value is the new priority ID (must exist in the default priority scheme). Required for updating an existing priority scheme. Not used when creating a new priority scheme.  E.g. The current priority scheme has priority ID &#x60;10001&#x60;. Issues with priority ID &#x60;10001&#x60; are being migrated out of this priority scheme will need mapping to new priorities. The &#x60;out&#x60; mapping would be &#x60;{\&quot;10001\&quot;: 10000}&#x60;. |  [optional]
