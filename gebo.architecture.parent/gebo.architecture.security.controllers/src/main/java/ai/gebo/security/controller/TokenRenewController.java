package ai.gebo.security.controller;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.security.model.SecurityHeaderData;
import ai.gebo.security.model.SecurityHeaderUtil;
import ai.gebo.security.services.IGSecurityAuditLoggerService;
import ai.gebo.security.services.IGSecurityAuditLoggerService.SecurityEvent;
import ai.gebo.security.services.SecurityAuditTaxonomy;
import ai.gebo.security.services.impl.LocalJwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@RestController
@PreAuthorize("hasAnyRole('USER','ADMIN','APPLICATION')")
@RequestMapping("/api/users/TokenRenewController")
@AllArgsConstructor
public class TokenRenewController {

	private final LocalJwtTokenProvider tokenProvider;
	private final IGSecurityAuditLoggerService securityAuditLoggerService;

	@GetMapping(value = "renew", produces = MediaType.APPLICATION_JSON_VALUE)
	public SecurityHeaderData renew(HttpServletRequest request) {
		SecurityHeaderData data = SecurityHeaderUtil.getSecurityHeaderData(request);
		SecurityEvent event = securityAuditLoggerService.newSecurityEvent();
		event.setEventType(SecurityAuditTaxonomy.EventType.SESSION_MANAGEMENT);
		event.setCategory(SecurityAuditTaxonomy.Category.SESSION_MANAGEMENT);
		event.setAction(SecurityAuditTaxonomy.Action.SESSION_TOKEN_RENEW);
		try {
			SecurityHeaderData renewed = tokenProvider.renew(data);
			event.setOutcome(SecurityAuditTaxonomy.Outcome.SUCCESS);
			return renewed;
		} catch (RuntimeException e) {
			event.setOutcome(SecurityAuditTaxonomy.Outcome.FAILURE);
			throw e;
		} finally {
			securityAuditLoggerService.log(event);
		}
	}
}
