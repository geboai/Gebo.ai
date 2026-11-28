/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.redmine.handler.impl;

import java.time.format.DateTimeFormatter;
import java.util.List;

import ai.gebo.redmine.client.model.Attachment;
import ai.gebo.redmine.client.model.CustomFieldValue;
import ai.gebo.redmine.client.model.IdName;
import ai.gebo.redmine.client.model.Issue;
import ai.gebo.redmine.client.model.IssueChild;
import ai.gebo.redmine.client.model.IssueJournalsInner;
import ai.gebo.redmine.client.model.IssueRelation;
import ai.gebo.redmine.client.model.WikiPage;

/**
 * Renders a Redmine issue (its fields, description, related issues,
 * attachments and notes) and a wiki page as markdown documents. Redmine text
 * is kept as written (textile or markdown, as the server is configured).
 */
final class RedmineMarkdownRenderer {
	private static final DateTimeFormatter DATE_TIME = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

	private RedmineMarkdownRenderer() {
	}

	private static String name(IdName value) {
		return value != null ? value.getName() : null;
	}

	private static void field(StringBuilder out, String label, Object value) {
		if (value != null && !value.toString().isBlank()) {
			out.append("- ").append(label).append(": ").append(value).append('\n');
		}
	}

	static String renderIssue(Issue issue, String baseUri) {
		StringBuilder out = new StringBuilder();
		out.append("# ").append(issue.getTracker() != null ? issue.getTracker().getName() + " " : "").append("#")
				.append(issue.getId()).append(": ").append(issue.getSubject() != null ? issue.getSubject() : "")
				.append("\n\n");
		field(out, "Project", name(issue.getProject()));
		field(out, "Status", issue.getStatus() != null ? issue.getStatus().getName() : null);
		field(out, "Priority", name(issue.getPriority()));
		field(out, "Author", name(issue.getAuthor()));
		field(out, "Assignee", name(issue.getAssignedTo()));
		field(out, "Category", name(issue.getCategory()));
		field(out, "Target version", name(issue.getFixedVersion()));
		field(out, "Parent issue", issue.getParent() != null ? "#" + issue.getParent().getId() : null);
		field(out, "Start date", issue.getStartDate());
		field(out, "Due date", issue.getDueDate());
		field(out, "Done", issue.getDoneRatio() != null ? issue.getDoneRatio() + "%" : null);
		field(out, "Estimated hours", issue.getEstimatedHours());
		field(out, "Spent hours", issue.getSpentHours());
		field(out, "Created on", issue.getCreatedOn() != null ? DATE_TIME.format(issue.getCreatedOn()) : null);
		field(out, "Updated on", issue.getUpdatedOn() != null ? DATE_TIME.format(issue.getUpdatedOn()) : null);
		field(out, "Closed on", issue.getClosedOn() != null ? DATE_TIME.format(issue.getClosedOn()) : null);
		if (issue.getCustomFields() != null) {
			for (CustomFieldValue custom : issue.getCustomFields()) {
				field(out, custom.getName(), customValue(custom.getValue()));
			}
		}
		field(out, "URL", baseUri + "/issues/" + issue.getId());
		out.append("\n## Description\n\n")
				.append(issue.getDescription() != null ? issue.getDescription().trim() : "").append("\n");
		List<IssueChild> children = issue.getChildren();
		if (children != null && !children.isEmpty()) {
			out.append("\n## Sub-issues\n\n");
			for (IssueChild child : children) {
				out.append("- #").append(child.getId()).append(' ')
						.append(child.getSubject() != null ? child.getSubject() : "").append('\n');
			}
		}
		List<IssueRelation> relations = issue.getRelations();
		if (relations != null && !relations.isEmpty()) {
			out.append("\n## Related issues\n\n");
			for (IssueRelation relation : relations) {
				Integer other = issue.getId().equals(relation.getIssueId()) ? relation.getIssueToId()
						: relation.getIssueId();
				out.append("- ").append(relation.getRelationType() != null ? relation.getRelationType() : "related")
						.append(" #").append(other).append('\n');
			}
		}
		renderAttachments(out, issue.getAttachments());
		List<IssueJournalsInner> journals = issue.getJournals();
		if (journals != null && journals.stream().anyMatch(j -> j.getNotes() != null && !j.getNotes().isBlank())) {
			out.append("\n## Notes\n");
			for (IssueJournalsInner journal : journals) {
				if (journal.getNotes() == null || journal.getNotes().isBlank()) {
					continue;
				}
				out.append("\n### ").append(journal.getUser() != null ? journal.getUser().getName() : "Unknown");
				if (journal.getCreatedOn() != null) {
					out.append(", ").append(DATE_TIME.format(journal.getCreatedOn()));
				}
				if (Boolean.TRUE.equals(journal.getPrivateNotes())) {
					out.append(" (private note)");
				}
				out.append("\n\n").append(journal.getNotes().trim()).append('\n');
			}
		}
		return out.toString();
	}

	static String renderWikiPage(WikiPage page, String projectId, String baseUri) {
		StringBuilder out = new StringBuilder();
		out.append("# ").append(page.getTitle()).append("\n\n");
		field(out, "Project", page.getProject() != null ? page.getProject().getName() : projectId);
		field(out, "Parent page", page.getParent() != null ? page.getParent().getTitle() : null);
		field(out, "Version", page.getVersion());
		field(out, "Author", name(page.getAuthor()));
		field(out, "Updated on", page.getUpdatedOn() != null ? DATE_TIME.format(page.getUpdatedOn()) : null);
		field(out, "URL", baseUri + "/projects/" + RedmineNodes.encode(projectId) + "/wiki/"
				+ RedmineNodes.encode(page.getTitle()));
		out.append('\n').append(page.getText() != null ? page.getText().trim() : "").append('\n');
		renderAttachments(out, page.getAttachments());
		return out.toString();
	}

	private static void renderAttachments(StringBuilder out, List<Attachment> attachments) {
		if (attachments != null && !attachments.isEmpty()) {
			out.append("\n## Attachments\n\n");
			for (Attachment attachment : attachments) {
				out.append("- ").append(attachment.getFilename());
				if (attachment.getDescription() != null && !attachment.getDescription().isBlank()) {
					out.append(": ").append(attachment.getDescription());
				}
				out.append('\n');
			}
		}
	}

	private static String customValue(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof List<?> list) {
			return String.join(", ", list.stream().map(String::valueOf).toList());
		}
		return value.toString();
	}
}
