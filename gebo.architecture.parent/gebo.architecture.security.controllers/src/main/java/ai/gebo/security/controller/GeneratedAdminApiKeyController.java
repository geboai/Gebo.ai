package ai.gebo.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/GeneratedAdminApiKeyController")
public class GeneratedAdminApiKeyController {

}
