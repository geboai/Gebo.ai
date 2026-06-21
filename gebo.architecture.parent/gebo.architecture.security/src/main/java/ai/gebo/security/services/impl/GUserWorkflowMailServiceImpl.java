package ai.gebo.security.services.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ai.gebo.security.config.GeboUserWorkflowsConfig;
import ai.gebo.security.model.UserWorkflowTicket;
import ai.gebo.security.model.UserWorkflowType;
import ai.gebo.security.services.IGUserWorkflowMailService;
import jakarta.annotation.PostConstruct;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GUserWorkflowMailServiceImpl implements IGUserWorkflowMailService {

	private static final String USER_WORKFLOW_LANDING_RELATIVE_URL = "/ui/user-workflows/land?ticket=";

	private static final Logger LOGGER = LoggerFactory.getLogger(GUserWorkflowMailServiceImpl.class);

	private final GeboUserWorkflowsConfig workflowsConfig;

	@PostConstruct
	public void validateConfigurationOnStartup() {
		List<String> errors = validateConfigurationForEnabledWorkflows();

		if (errors.isEmpty()) {
			LOGGER.info(
					"Gebo user workflow mail configuration loaded. activationWorkflowEnabled={}, forgotPasswordWorkflowEnabled={}",
					workflowsConfig.isActivationWorkflowEnabled(), workflowsConfig.isForgotPasswordWorkflowEnabled());
			return;
		}

		LOGGER.error(
				"Gebo user workflow mail configuration has {} error(s). "
						+ "The application will continue to start, but enabled user workflows may fail at runtime.",
				errors.size());

		for (String error : errors) {
			LOGGER.error("Gebo user workflow mail configuration error: {}", error);
		}
	}

	@Override
	public void sendTicket(UserWorkflowTicket ticket) {
		validateTicketAndRuntimeConfig(ticket);

		try {
			Session session = createMailSession();

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(workflowsConfig.getMailSender(), "Gebo.ai"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(ticket.getEmail(), false));

			message.setSubject(buildSubject(ticket), StandardCharsets.UTF_8.name());
			message.setContent(buildHtmlBody(ticket), "text/html; charset=UTF-8");

			Transport.send(message);

		} catch (MessagingException | UnsupportedEncodingException e) {
			throw new UserWorkflowMailException("Cannot send user workflow email to " + ticket.getEmail(), e);
		}
	}

	private List<String> validateConfigurationForEnabledWorkflows() {
		List<String> errors = new ArrayList<>();

		boolean atLeastOneWorkflowEnabled = workflowsConfig.isActivationWorkflowEnabled()
				|| workflowsConfig.isForgotPasswordWorkflowEnabled();

		if (!atLeastOneWorkflowEnabled) {
			LOGGER.info("Gebo user workflow mail service loaded with all mail workflows disabled.");
			return errors;
		}

		if (!StringUtils.hasText(workflowsConfig.getMailServer())) {
			errors.add("mailServer is required when at least one user workflow is enabled");
		}

		if (workflowsConfig.getMailPort() == null) {
			errors.add("mailPort is required when at least one user workflow is enabled");
		} else if (workflowsConfig.getMailPort() <= 0 || workflowsConfig.getMailPort() > 65535) {
			errors.add("mailPort must be between 1 and 65535");
		}

		if (!StringUtils.hasText(workflowsConfig.getMailSender())) {
			errors.add("mailSender is required when at least one user workflow is enabled");
		}

		if (!StringUtils.hasText(workflowsConfig.getGeboReachableBaseAddress())) {
			errors.add("geboReachableBaseAddress is required when at least one user workflow is enabled");
		}

		if (StringUtils.hasText(workflowsConfig.getMailUserName())
				&& !StringUtils.hasText(workflowsConfig.getMailPassword())) {
			errors.add("mailPassword is required when mailUserName is configured");
		}

		if (!StringUtils.hasText(workflowsConfig.getMailUserName())
				&& StringUtils.hasText(workflowsConfig.getMailPassword())) {
			errors.add("mailPassword is configured but mailUserName is empty");
		}

		if (workflowsConfig.getTicketValidityTimeoutMS() <= 0) {
			errors.add("ticketValidityTimeoutMS must be greater than zero");
		}

		return errors;
	}

	private void validateTicketAndRuntimeConfig(UserWorkflowTicket ticket) {
		if (ticket == null) {
			throw new IllegalArgumentException("User workflow ticket cannot be null");
		}

		if (ticket.getType() == null) {
			throw new IllegalArgumentException("User workflow ticket type cannot be null");
		}

		if (!StringUtils.hasText(ticket.getTicket())) {
			throw new IllegalArgumentException("User workflow ticket value cannot be empty");
		}

		if (!StringUtils.hasText(ticket.getEmail())) {
			throw new IllegalArgumentException("User workflow email cannot be empty");
		}

		if (ticket.getType() == UserWorkflowType.ACTIVATION && !workflowsConfig.isActivationWorkflowEnabled()) {
			throw new IllegalStateException("User activation workflow is disabled");
		}

		if (ticket.getType() == UserWorkflowType.FORGOT_PASSWORD
				&& !workflowsConfig.isForgotPasswordWorkflowEnabled()) {
			throw new IllegalStateException("Forgot password workflow is disabled");
		}

		List<String> configErrors = validateConfigurationForEnabledWorkflows();

		if (!configErrors.isEmpty()) {
			throw new IllegalStateException(
					"Invalid Gebo user workflow mail configuration: " + String.join("; ", configErrors));
		}
	}

	private Session createMailSession() {
		Properties props = new Properties();

		props.put("mail.smtp.host", workflowsConfig.getMailServer());
		props.put("mail.smtp.port", String.valueOf(workflowsConfig.getMailPort()));

		boolean authEnabled = StringUtils.hasText(workflowsConfig.getMailUserName());

		props.put("mail.smtp.auth", String.valueOf(authEnabled));
		props.put("mail.smtp.starttls.enable", String.valueOf(workflowsConfig.isMailStartTlsEnabled()));
		props.put("mail.smtp.starttls.required", "false");
		props.put("mail.smtp.connectiontimeout", String.valueOf(workflowsConfig.getConnectiontimeout()));
		props.put("mail.smtp.timeout", String.valueOf(workflowsConfig.getTimeout()));
		props.put("mail.smtp.writetimeout",String.valueOf(workflowsConfig.getWriteTimeout()));

		if (!authEnabled) {
			return Session.getInstance(props);
		}

		return Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(workflowsConfig.getMailUserName(), workflowsConfig.getMailPassword());
			}
		});
	}

	private String buildSubject(UserWorkflowTicket ticket) {
		return switch (ticket.getType()) {
		case ACTIVATION -> "Activate your Gebo.ai account";
		case FORGOT_PASSWORD -> "Reset your Gebo.ai password";
		};
	}

	private String buildHtmlBody(UserWorkflowTicket ticket) {
		String actionUrl = buildActionUrl(ticket);
		String escapedUrl = htmlEscape(actionUrl);
		String escapedEmail = htmlEscape(ticket.getEmail());

		String title;
		String intro;
		String buttonText;

		switch (ticket.getType()) {
		case ACTIVATION -> {
			title = "Activate your Gebo.ai account";
			intro = "Your Gebo.ai account has been created. Click the button below to activate it and choose your password.";
			buttonText = "Activate account";
		}
		case FORGOT_PASSWORD -> {
			title = "Reset your Gebo.ai password";
			intro = "We received a request to reset the password for your Gebo.ai account. Click the button below to choose a new password.";
			buttonText = "Reset password";
		}
		default -> throw new IllegalArgumentException("Unsupported user workflow type: " + ticket.getType());
		}

		int validityMinutes = Math.max(1, workflowsConfig.getTicketValidityTimeoutMS() / 60000);

		return """
				<!DOCTYPE html>
				<html>
				<head>
				    <meta charset="UTF-8">
				    <title>%s</title>
				</head>
				<body style="margin:0;padding:0;background-color:#f6f7f9;font-family:Arial,Helvetica,sans-serif;color:#222;">
				    <table width="100%%" cellpadding="0" cellspacing="0" style="background-color:#f6f7f9;padding:24px;">
				        <tr>
				            <td align="center">
				                <table width="600" cellpadding="0" cellspacing="0" style="background-color:#ffffff;border-radius:8px;padding:32px;border:1px solid #e5e7eb;">
				                    <tr>
				                        <td>
				                            <h1 style="font-size:22px;margin:0 0 16px 0;color:#111827;">%s</h1>
				                            <p style="font-size:15px;line-height:1.5;margin:0 0 16px 0;">
				                                Hello,
				                            </p>
				                            <p style="font-size:15px;line-height:1.5;margin:0 0 24px 0;">
				                                %s
				                            </p>
				                            <p style="font-size:15px;line-height:1.5;margin:0 0 24px 0;">
				                                Account: <strong>%s</strong>
				                            </p>
				                            <p style="text-align:center;margin:32px 0;">
				                                <a href="%s"
				                                   style="background-color:#2563eb;color:#ffffff;text-decoration:none;padding:12px 22px;border-radius:6px;font-size:15px;font-weight:bold;display:inline-block;">
				                                    %s
				                                </a>
				                            </p>
				                            <p style="font-size:13px;line-height:1.5;color:#6b7280;margin:0 0 12px 0;">
				                                This link is valid for approximately %d minutes.
				                            </p>
				                            <p style="font-size:13px;line-height:1.5;color:#6b7280;margin:0 0 12px 0;">
				                                If the button does not work, copy and paste this link into your browser:
				                            </p>
				                            <p style="font-size:13px;line-height:1.5;word-break:break-all;margin:0 0 24px 0;">
				                                <a href="%s" style="color:#2563eb;">%s</a>
				                            </p>
				                            <p style="font-size:13px;line-height:1.5;color:#6b7280;margin:0;">
				                                If you did not request this operation, you can safely ignore this email.
				                            </p>
				                        </td>
				                    </tr>
				                </table>
				            </td>
				        </tr>
				    </table>
				</body>
				</html>
				"""
				.formatted(htmlEscape(title), htmlEscape(title), htmlEscape(intro), escapedEmail, escapedUrl,
						htmlEscape(buttonText), validityMinutes, escapedUrl, escapedUrl);
	}

	private String buildActionUrl(UserWorkflowTicket ticket) {
		String baseAddress = workflowsConfig.getGeboReachableBaseAddress();

		if (baseAddress.endsWith("/")) {
			baseAddress = baseAddress.substring(0, baseAddress.length() - 1);
		}

		String encodedTicket = urlEncode(ticket.getTicket());
		//baseAddress="http://localhost:4200/";
		return baseAddress + USER_WORKFLOW_LANDING_RELATIVE_URL + encodedTicket;
	}

	private String urlEncode(String value) {
		return URLEncoder.encode(value, StandardCharsets.UTF_8);
	}

	private String htmlEscape(String value) {
		if (value == null) {
			return "";
		}

		return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;")
				.replace("'", "&#39;");
	}

	public static class UserWorkflowMailException extends RuntimeException {
		public UserWorkflowMailException(String message, Throwable cause) {
			super(message, cause);
		}
	}
}