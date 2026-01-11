package ai.gebo.architecture.documents.cache.service.impl;

import java.io.IOException;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.documents.cache.service.DocumentCacheAccessException;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.model.base.TypedInputStream;

@FunctionalInterface
interface SupplierWithException {

	TypedInputStream get() throws DocumentCacheAccessException, GeboContentHandlerSystemException,
			SearchServiceException, IOException;
}