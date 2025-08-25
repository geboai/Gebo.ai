package ai.gebo.security.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.model.OperationStatus;
import ai.gebo.security.model.SecurityHeaderData;
import ai.gebo.security.services.BackendOauth2LoginSPASupportException;
import ai.gebo.security.services.IGBackendOauth2LoginSPASupportService;
import ai.gebo.security.util.CookieUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/public/oauth2LoginSPAAttemptSuccess")
@AllArgsConstructor
public class Oauth2SPAAuthorizationDeliveryController {
	private final IGBackendOauth2LoginSPASupportService backendOauth2LoginService;
	private static final ObjectMapper objectMapper = new ObjectMapper();

	@GetMapping
	public void endLogin(HttpServletRequest request, HttpServletResponse response)
			throws BackendOauth2LoginSPASupportException, GeboCryptSecretException, IOException {
		String remoteAddress = request.getRemoteAddr();
		Optional<Cookie> cookie = CookieUtils.getCookie(request,
				IGBackendOauth2LoginSPASupportService.BACKEND_OAUTH2_COOKIE_NAME);
		String nextUri = request.getParameter("nextUri");
		if (cookie.isPresent()) {
			String cryptedId = cookie.get().getValue();
			CookieUtils.deleteCookie(request, response,
					IGBackendOauth2LoginSPASupportService.BACKEND_OAUTH2_COOKIE_NAME);
			OperationStatus<SecurityHeaderData> responseStatus = backendOauth2LoginService
					.getAuthorizationData(cryptedId, remoteAddress);
			response.setContentType("text/html");
			PrintWriter writer = response.getWriter();
			writer.println("<html>");
			writer.println("<script language=\"JavaScript\">");
			writer.println("function goToSpa() {");
			writer.println("const data=" + objectMapper.writeValueAsString(responseStatus) + ";");
			writer.println("sessionStorage.setItem(\"RESPONSE_STATUS\",JSON.stringify(data));");
			writer.println("document.location=\"" + nextUri + "\";");
			writer.println("}");
			writer.println("</script>");
			writer.println("<body onload=\"goToSpa();\" ></body>");
			writer.flush();

		} else {
			response.setStatus(404);
		}
	}
}
