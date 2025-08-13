package ai.gebo.security.services.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import ai.gebo.security.services.IGOAuth2AuthenticationSuccessHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GOAuth2AuthenticationSuccessHandler implements IGOAuth2AuthenticationSuccessHandler {
	private final static Logger LOGGER = LoggerFactory.getLogger(GOAuth2AuthenticationSuccessHandler.class);

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin onAuthenticationSuccess(..)");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End onAuthenticationSuccess(..)");
		}
	}

}