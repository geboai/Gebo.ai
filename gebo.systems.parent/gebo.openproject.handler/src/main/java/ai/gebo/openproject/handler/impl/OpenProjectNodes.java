/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.openproject.handler.impl;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import ai.gebo.openproject.handler.model.OpenProjectNativePositionObject;
import ai.gebo.openproject.handler.model.OpenProjectPathNodeType;
import ai.gebo.openproject.handler.model.OpenProjectSection;

/**
 * Factories of the OpenProject virtual filesystem nodes from the HAL resources.
 * Codes are stable and unique among siblings, names carry the extension of the
 * content, URLs point at the OpenProject web pages, and every node carries its
 * project in its meta infos.
 */
final class OpenProjectNodes {
	static final String MARKDOWN = "text/markdown";

	private OpenProjectNodes() {
	}

	private static OpenProjectNativePositionObject node(OpenProjectPathNodeType type, String id, String code, String name,
			String url, String projectId) {
		OpenProjectNativePositionObject node = new OpenProjectNativePositionObject();
		node.setType(type);
		node.setId(id);
		node.setCode(code);
		node.setName(name);
		node.setUrl(url);
		node.meta(OpenProjectNativePositionObject.OPENPROJECT_PROJECT_ID, projectId);
		return node;
	}

	static String encode(String value) {
		return URLEncoder.encode(value, StandardCharsets.UTF_8).replace("+", "%20");
	}

	private static OpenProjectNativePositionObject markdown(OpenProjectNativePositionObject node,
			Map<String, Object> resource) {
		node.setResourceContentType(MARKDOWN);
		java.util.Date updated = OpenProjectHal.date(resource, "updatedAt");
		node.setResourceModificationTime(updated != null ? updated : OpenProjectHal.date(resource, "createdAt"));
		return node;
	}

	static OpenProjectNativePositionObject project(String baseUri, String identifier, String name) {
		OpenProjectNativePositionObject node = node(OpenProjectPathNodeType.PROJECT, identifier, "project-" + identifier,
				name != null ? name : identifier, baseUri + "/projects/" + encode(identifier), identifier);
		node.meta(OpenProjectNativePositionObject.OPENPROJECT_PROJECT_IDENTIFIER, identifier);
		return node;
	}

	static OpenProjectNativePositionObject section(String baseUri, String projectId, OpenProjectSection section) {
		return node(OpenProjectPathNodeType.SECTION, section.name(), section.getWebPath(), section.getLabel(),
				baseUri + "/projects/" + encode(projectId) + "/" + section.getWebPath(), projectId);
	}

	static String workPackageUrl(String baseUri, String id) {
		return baseUri + "/work_packages/" + id;
	}

	static String workPackageLabel(String id, String subject) {
		return OpenProjectNavigationUtil.sanitizeName("#" + id + (subject != null ? " " + subject : ""));
	}

	static OpenProjectNativePositionObject workPackage(String baseUri, String projectId, Map<String, Object> wp) {
		String id = OpenProjectHal.string(wp, "id");
		return node(OpenProjectPathNodeType.WORK_PACKAGE, id, "work-package-" + id,
				workPackageLabel(id, OpenProjectHal.text(wp, "subject")), workPackageUrl(baseUri, id), projectId)
				.meta(OpenProjectNativePositionObject.OPENPROJECT_WORK_PACKAGE_ID, id);
	}

	static OpenProjectNativePositionObject workPackageContent(String baseUri, String projectId, Map<String, Object> wp) {
		String id = OpenProjectHal.string(wp, "id");
		return markdown(node(OpenProjectPathNodeType.WORK_PACKAGE_CONTENT, id, "work-package-" + id + ".md",
				workPackageLabel(id, OpenProjectHal.text(wp, "subject")) + ".md", workPackageUrl(baseUri, id), projectId)
				.meta(OpenProjectNativePositionObject.OPENPROJECT_WORK_PACKAGE_ID, id), wp);
	}

	private static String titled(Map<String, Object> resource, String fallback) {
		String title = OpenProjectHal.text(resource, "title");
		return OpenProjectNavigationUtil.sanitizeName(title != null ? title : fallback);
	}

	static OpenProjectNativePositionObject document(String baseUri, String projectId, Map<String, Object> document) {
		String id = OpenProjectHal.string(document, "id");
		return node(OpenProjectPathNodeType.DOCUMENT, id, "document-" + id, titled(document, "Document " + id),
				baseUri + "/documents/" + id, projectId).meta(OpenProjectNativePositionObject.OPENPROJECT_DOCUMENT_ID, id);
	}

	static OpenProjectNativePositionObject documentContent(String baseUri, String projectId,
			Map<String, Object> document) {
		String id = OpenProjectHal.string(document, "id");
		return markdown(node(OpenProjectPathNodeType.DOCUMENT_CONTENT, id, "document-" + id + ".md",
				titled(document, "Document " + id) + ".md", baseUri + "/documents/" + id, projectId)
				.meta(OpenProjectNativePositionObject.OPENPROJECT_DOCUMENT_ID, id), document);
	}

	static OpenProjectNativePositionObject meeting(String baseUri, String projectId, Map<String, Object> meeting) {
		String id = OpenProjectHal.string(meeting, "id");
		return node(OpenProjectPathNodeType.MEETING, id, "meeting-" + id, meetingLabel(meeting, id),
				baseUri + "/meetings/" + id, projectId).meta(OpenProjectNativePositionObject.OPENPROJECT_MEETING_ID, id);
	}

	static OpenProjectNativePositionObject meetingContent(String baseUri, String projectId, Map<String, Object> meeting) {
		String id = OpenProjectHal.string(meeting, "id");
		return markdown(node(OpenProjectPathNodeType.MEETING_CONTENT, id, "meeting-" + id + ".md",
				meetingLabel(meeting, id) + ".md", baseUri + "/meetings/" + id, projectId)
				.meta(OpenProjectNativePositionObject.OPENPROJECT_MEETING_ID, id), meeting);
	}

	/** "Weekly sync 2026-11-24": recurring meetings share their title. */
	static String meetingLabel(Map<String, Object> meeting, String id) {
		String title = OpenProjectHal.text(meeting, "title");
		String start = OpenProjectHal.text(meeting, "startTime");
		String day = start != null && start.length() >= 10 ? " " + start.substring(0, 10) : "";
		return OpenProjectNavigationUtil.sanitizeName((title != null ? title : "Meeting " + id) + day);
	}

	static OpenProjectNativePositionObject news(String baseUri, String projectId, Map<String, Object> news) {
		String id = OpenProjectHal.string(news, "id");
		return markdown(node(OpenProjectPathNodeType.NEWS, id, "news-" + id + ".md", titled(news, "News " + id) + ".md",
				baseUri + "/news/" + id, projectId).meta(OpenProjectNativePositionObject.OPENPROJECT_NEWS_ID, id), news);
	}

	static OpenProjectNativePositionObject attachment(String baseUri, String projectId, Map<String, Object> attachment) {
		String id = OpenProjectHal.string(attachment, "id");
		String fileName = OpenProjectHal.text(attachment, "fileName");
		OpenProjectNativePositionObject node = node(OpenProjectPathNodeType.ATTACHMENT, id, "attachment-" + id,
				OpenProjectNavigationUtil.sanitizeName(fileName != null ? fileName : "attachment-" + id),
				baseUri + "/attachments/" + id, projectId)
				.meta(OpenProjectNativePositionObject.OPENPROJECT_ATTACHMENT_ID, id)
				.meta(OpenProjectNativePositionObject.OPENPROJECT_FILE_NAME, fileName);
		node.setResourceContentType(OpenProjectHal.text(attachment, "contentType"));
		node.setResourceFileSize(OpenProjectHal.longValue(attachment, "fileSize"));
		node.setResourceModificationTime(OpenProjectHal.date(attachment, "createdAt"));
		return node;
	}
}
