# CreateInlineCommentModelInlineCommentProperties

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**textSelection** | **String** | The text to highlight |  [optional]
**textSelectionMatchCount** | **Integer** | The number of matches for the selected text on the page (should be strictly greater than textSelectionMatchIndex) |  [optional]
**textSelectionMatchIndex** | **Integer** | The match index to highlight. This is zero-based. E.g. if you have 3 occurrences of \&quot;hello world\&quot; on a page  and you want to highlight the second occurrence, you should pass 1 for textSelectionMatchIndex and 3 for textSelectionMatchCount. |  [optional]
