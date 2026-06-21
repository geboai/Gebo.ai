package ai.gebo.llms.chat.client.rest.controllers;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.llms.deepsearch.service.IGDeepSearchService;
import ai.gebo.model.base.GBaseObject;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "api/users/GeboDeepSearchController")
@AllArgsConstructor
public class GeboDeepSearchController {
	final IGDeepSearchService deepSearchService;
	

	@GetMapping(value = "getDeepSearchDataSources", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GBaseObject> getDeepSearchDataSources() {

		return this.deepSearchService.getDeepSearchActiveHandlers();
	}
}
