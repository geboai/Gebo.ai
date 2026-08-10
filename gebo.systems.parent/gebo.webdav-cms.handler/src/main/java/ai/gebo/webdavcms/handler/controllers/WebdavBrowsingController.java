package ai.gebo.webdavcms.handler.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.model.OperationStatus;
import ai.gebo.model.virtualfs.BrowseParam;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.model.virtualfs.PathInfo;
import ai.gebo.model.virtualfs.VFilesystemReference;
import ai.gebo.model.virtualfs.VirtualFilesystemNavigationTreeStatus;
import ai.gebo.systems.abstraction.layer.VirtualFilesystemBrowsingException;
import ai.gebo.webdavcms.handler.GWebdavBrowsingContext;
import ai.gebo.webdavcms.handler.impl.WebdavBrowsingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(value = "api/admin/WebdavBrowsingController")
public class WebdavBrowsingController {

	@Autowired
	WebdavBrowsingService browsingService;

	@GetMapping(value = "getWebdavRoots", produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GVirtualFilesystemRoot>> getWebdavRoots(
			@RequestParam("systemCode") String systemCode) throws VirtualFilesystemBrowsingException {
		return browsingService.getRoots(GWebdavBrowsingContext.of(systemCode));
	}

	@PostMapping(value = "browseWebdavPath", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<PathInfo>> browseWebdavPath(@RequestBody BrowseParam param,
			@RequestParam("systemCode") String systemCode) throws VirtualFilesystemBrowsingException {
		return browsingService.browse(param, GWebdavBrowsingContext.of(systemCode));
	}

	@PostMapping(value = "getWebdavNavigationStatus", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<VirtualFilesystemNavigationTreeStatus>> getWebdavNavigationStatus(
			@RequestParam("systemCode") String systemCode,
			@NotNull @Valid @RequestBody List<VFilesystemReference> references)
			throws VirtualFilesystemBrowsingException {
		return browsingService.navigationStatus(references, GWebdavBrowsingContext.of(systemCode));
	}
}