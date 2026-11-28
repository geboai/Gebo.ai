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
import ai.gebo.redmine.client.model.Issue;
import ai.gebo.redmine.client.model.IssueJournalsInner;
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

	/**
	 * Whether a journal is a private note: its notes are private and say
	 * something. Such a note is a document of its own, never part of the issue
	 * content.
	 */
	static boolean isPrivateNote(IssueJournalsInner journal) {
		return journal != null && Boolean.TRUE.equals(journal.getPrivateNotes()) && journal.getNotes() != null
				&& !journal.getNotes().isBlank();
	}

	/**
	 * The private note of an issue with the given journal id, or null when the
	 * issue (read with its journals) has none: the journal is gone, its notes were
	 * made public or emptied, or the account may not view it any more.
	 */
	static IssueJournalsInner privateNote(Issue issue, Integer journalId) {
		if (issue == null || journalId == null || issue.getJournals() == null) {
			return null;
		}
		for (IssueJournalsInner journal : issue.getJournals()) {
			if (journalId.equals(journal.getId()) && isPrivateNote(journal)) {
				return journal;
			}
		}
		return null;
	}

	static String privateNoteName(Integer issueId, Integer journalId) {
		return RedmineNavigationUtil.sanitizeName("#" + issueId + " private note " + journalId) + ".md";
	}

	/**
	 * A private note of an issue, a markdown document under the issue folder that
	 * says it is private and who wrote it.
	 */
	static RedmineNativePositionObject privateNote(String baseUri, String projectId, Integer issueId,
			IssueJournalsInner journal) {
		RedmineNativePositionObject node = node(RedminePathNodeType.PRIVATE_NOTE, String.valueOf(journal.getId()),
				"private-note-" + journal.getId(), privateNoteName(issueId, journal.getId()),
				baseUri + "/issues/" + issueId, projectId).meta(RedmineNativePositionObject.REDMINE_ISSUE_ID, issueId)
				.meta(RedmineNativePositionObject.REDMINE_JOURNAL_ID, journal.getId())
				.meta(RedmineNativePositionObject.REDMINE_PRIVATE_NOTE, Boolean.TRUE)
				.meta(RedmineNativePositionObject.REDMINE_NOTE_AUTHOR_ID,
						journal.getUser() != null ? journal.getUser().getId() : null);
		node.setResourceContentType(MARKDOWN);
		node.setResourceModificationTime(
				toDate(journal.getUpdatedOn() != null ? journal.getUpdatedOn() : journal.getCreatedOn()));
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
