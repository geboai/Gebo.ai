package ai.gebo.architecture.documents.cache.service.impl;

import java.io.IOException;

import ai.gebo.architecture.documents.access.DocumentContentStreamerException;
import ai.gebo.architecture.documents.cache.service.DocumentCacheAccessException;
import ai.gebo.model.base.TypedInputStream;

@FunctionalInterface
interface SupplierWithException {

	TypedInputStream get() throws DocumentCacheAccessException, DocumentContentStreamerException, IOException;
}