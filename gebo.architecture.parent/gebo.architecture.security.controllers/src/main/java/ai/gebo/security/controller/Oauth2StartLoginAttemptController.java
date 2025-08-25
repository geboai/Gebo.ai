package ai.gebo.security.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.security.model.SecurityHeaderUtil;
import ai.gebo.security.services.BackendOauth2LoginSPASupportException;
import ai.gebo.security.services.IGBackendOauth2LoginSPASupportService;
import ai.gebo.security.util.CookieUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/public/oauth2LoginAttempt")
@AllArgsConstructor
public class Oauth2StartLoginAttemptController {
	private final IGBackendOauth2LoginSPASupportService backendOauth2LoginService;

	@GetMapping
	public void startLogin(HttpServletRequest request, HttpServletResponse response)
			throws BackendOauth2LoginSPASupportException, GeboCryptSecretException, IOException {
		String remoteAddress = request.getRemoteAddr();
		String registrationId = request.getParameter("registrationId");
		String nextUri = request.getParameter("nextUri");
		if (registrationId == null || registrationId.trim().length() == 0)
			throw new RuntimeException("Missing registrationId");
		String cookieContent = backendOauth2LoginService.registerOauth2LoginAttempt(remoteAddress, nextUri,
				SecurityHeaderUtil.DEFAULT_TENANT, registrationId);
		CookieUtils.addCookie(response, IGBackendOauth2LoginSPASupportService.BACKEND_OAUTH2_COOKIE_NAME, cookieContent,
				1200);
		response.sendRedirect("/oauth2/authorization/" + registrationId);
	}
}
