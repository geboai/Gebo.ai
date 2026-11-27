/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.redmine.handler.impl;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.Date;

import ai.gebo.redmine.client.model.Attachment;
import ai.gebo.redmine.client.model.RedmineFile;
import ai.gebo.redmine.handler.model.RedmineNativePositionObject;
import ai.gebo.redmine.handler.model.RedminePathNodeType;
import ai.gebo.redmine.handler.model.RedmineSection;

/**
 * Factories of the Redmine virtual filesystem nodes. Codes are stable and
 * unique among siblings, names carry the extension of the content, and every
 * node carries its project in its meta infos.
 */
final class RedmineNodes {
	static final String MARKDOWN = "text/markdown";

	private RedmineNodes() {
	}

	private static RedmineNativePositionObject node(RedminePathNodeType type, String id, String code, String name,
			String url, String projectId) {
		RedmineNativePositionObject node = new RedmineNativePositionObject();
		node.setType(type);
		node.setId(id);
		node.setCode(code);
		node.setName(name);
		node.setUrl(url);
		node.meta(RedmineNativePositionObject.REDMINE_PROJECT_ID, projectId);
		return node;
	}

	static Date toDate(OffsetDateTime time) {
		return time != null ? Date.from(time.toInstant()) : null;
	}

	static String encode(String value) {
		return URLEncoder.encode(value, StandardCharsets.UTF_8).replace("+", "%20");
	}

	static RedmineNativePositionObject project(String baseUri, String identifier, String name) {
		RedmineNativePositionObject node = node(RedminePathNodeType.PROJECT, identifier, "project-" + identifier,
				name != null ? name : identifier, baseUri + "/projects/" + encode(identifier), identifier);
		node.meta(RedmineNativePositionObject.REDMINE_PROJECT_IDENTIFIER, identifier);
		return node;
	}

	static RedmineNativePositionObject section(String baseUri, String projectId, RedmineSection section) {
		String path = section == RedmineSection.ISSUES ? "issues" : section == RedmineSection.WIKI ? "wiki" : "files";
		return node(RedminePathNodeType.SECTION, section.name(), path, section.getLabel(),
				baseUri + "/projects/" + encode(projectId) + "/" + path, projectId);
	}

	static String issueLabel(Integer issueId, String subject) {
		return RedmineNavigationUtil.sanitizeName("#" + issueId + (subject != null ? " " + subject : ""));
	}

	static RedmineNativePositionObject issue(String baseUri, String projectId, Integer issueId, String subject) {
		return node(RedminePathNodeType.ISSUE, String.valueOf(issueId), "issue-" + issueId,
				issueLabel(issueId, subject), baseUri + "/issues/" + issueId, projectId)
				.meta(RedmineNativePositionObject.REDMINE_ISSUE_ID, issueId);
	}

	static RedmineNativePositionObject issueContent(String baseUri, String projectId, Integer issueId, String subject,
			OffsetDateTime updatedOn) {
		RedmineNativePositionObject node = node(RedminePathNodeType.ISSUE_CONTENT, String.valueOf(issueId),
				"issue-" + issueId + ".md", issueLabel(issueId, subject) + ".md", baseUri + "/issues/" + issueId,
				projectId).meta(RedmineNativePositionObject.REDMINE_ISSUE_ID, issueId);
		node.setResourceContentType(MARKDOWN);
		node.setResourceModificationTime(toDate(updatedOn));
		return node;
	}

	static RedmineNativePositionObject wikiPage(String baseUri, String projectId, String title) {
		return node(RedminePathNodeType.WIKI_PAGE, title, "wiki-" + title, RedmineNavigationUtil.sanitizeName(title),
				baseUri + "/projects/" + encode(projectId) + "/wiki/" + encode(title), projectId)
				.meta(RedmineNativePositionObject.REDMINE_WIKI_TITLE, title);
	}

	static RedmineNativePositionObject wikiContent(String baseUri, String projectId, String title,
			OffsetDateTime updatedOn) {
		RedmineNativePositionObject node = node(RedminePathNodeType.WIKI_CONTENT, title, "wiki-" + title + ".md",
				RedmineNavigationUtil.sanitizeName(title) + ".md",
				baseUri + "/projects/" + encode(projectId) + "/wiki/" + encode(title), projectId)
				.meta(RedmineNativePositionObject.REDMINE_WIKI_TITLE, title);
		node.setResourceContentType(MARKDOWN);
		node.setResourceModificationTime(toDate(updatedOn));
		return node;
	}

	static RedmineNativePositionObject attachment(String baseUri, String projectId, Attachment attachment) {
		return fileNode(RedminePathNodeType.ATTACHMENT, baseUri, projectId, attachment.getId(),
				attachment.getFilename(), attachment.getContentType(), attachment.getFilesize(),
				attachment.getCreatedOn());
	}

	static RedmineNativePositionObject file(String baseUri, String projectId, RedmineFile file) {
		return fileNode(RedminePathNodeType.FILE, baseUri, projectId, file.getId(), file.getFilename(),
				file.getContentType(), file.getFilesize(), file.getCreatedOn());
	}

	static RedmineNativePositionObject fileNode(RedminePathNodeType type, String baseUri, String projectId, Integer id,
			String filename, String contentType, Integer size, OffsetDateTime createdOn) {
		String prefix = type == RedminePathNodeType.FILE ? "file-" : "attachment-";
		RedmineNativePositionObject node = node(type, String.valueOf(id), prefix + id,
				RedmineNavigationUtil.sanitizeName(filename), baseUri + "/attachments/" + id, projectId)
				.meta(RedmineNativePositionObject.REDMINE_ATTACHMENT_ID, id)
				.meta(RedmineNativePositionObject.REDMINE_FILE_NAME, filename);
		node.setResourceContentType(contentType);
		node.setResourceFileSize(size != null ? size.longValue() : null);
		node.setResourceModificationTime(toDate(createdOn));
		return node;
	}
}
