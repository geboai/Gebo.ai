package ai.gebo.llms.chat.client.rest.controllers;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.llms.chat.abstraction.layer.model.GPromptUseInfo;
import ai.gebo.llms.chat.abstraction.layer.services.IGPromptUseInfoDao;
import lombok.AllArgsConstructor;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(path = "api/admin/GeboAdminPromptUseController")
@AllArgsConstructor
public class GeboAdminPromptUseInfoController {
	final IGPromptUseInfoDao promptUseDao;

	@GetMapping(value = "findByModule", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GPromptUseInfo> findByModule(@RequestParam("module") String module) {
		return promptUseDao.findByModule(module);
	}

	@GetMapping(value = "findByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GPromptUseInfo findByCode(@RequestParam("code") String code) {
		return promptUseDao.findByCode(code);
	}

	@GetMapping(value = "findAll", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GPromptUseInfo> findAll() {
		return promptUseDao.getConfigurations();
	}

}
