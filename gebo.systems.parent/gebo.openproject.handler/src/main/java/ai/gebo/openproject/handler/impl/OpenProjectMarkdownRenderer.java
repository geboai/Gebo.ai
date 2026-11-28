/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.openproject.handler.impl;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Renders the OpenProject items as markdown documents: a title, a list of the
 * item's fields, its text (OpenProject keeps descriptions as markdown already)
 * and what hangs from it (relations, children, attachments, comments and
 * changes, agenda items). Users are named through a resolver, since the API
 * gives some links without their titles. The text of an internal comment is
 * never part of its work package's document: it is rendered as a document of
 * its own.
 */
final class OpenProjectMarkdownRenderer {

	/** Resolves the name of the user a link of a resource points at. */
	interface UserNames extends BiFunction<Map<String, Object>, String, String> {
	}

	private OpenProjectMarkdownRenderer() {
	}

	private static void field(StringBuilder out, String label, Object value) {
		if (value != null && !value.toString().isBlank()) {
			out.append("- ").append(label).append(": ").append(value).append('\n');
		}
	}

	private static void text(StringBuilder out, String heading, String text) {
		if (text != null && !text.isBlank()) {
			out.append("\n## ").append(heading).append("\n\n").append(text.trim()).append('\n');
		}
	}

	private static void attachments(StringBuilder out, List<Map<String, Object>> attachments) {
		if (attachments == null || attachments.isEmpty()) {
			return;
		}
		out.append("\n## Attachments\n\n");
		for (Map<String, Object> attachment : attachments) {
			out.append("- ").append(OpenProjectHal.text(attachment, "fileName"));
			Long size = OpenProjectHal.longValue(attachment, "fileSize");
			if (size != null) {
				out.append(" (").append(size).append(" bytes)");
			}
			String description = OpenProjectHal.text(attachment, "description.raw");
			if (description != null) {
				out.append(": ").append(description.trim());
			}
			out.append('\n');
		}
	}

	private static String percent(Integer value) {
		return value != null ? value + "%" : null;
	}

	static String renderWorkPackage(Map<String, Object> wp, List<Map<String, Object>> relations,
			List<Map<String, Object>> children, List<Map<String, Object>> attachments,
			List<Map<String, Object>> activities, UserNames users, String baseUri) {
		String id = OpenProjectHal.string(wp, "id");
		String type = OpenProjectHal.linkTitle(wp, "type");
		StringBuilder out = new StringBuilder();
		out.append("# ").append(type != null ? type + " " : "").append('#').append(id).append(": ")
				.append(OpenProjectHal.string(wp, "subject") != null ? OpenProjectHal.string(wp, "subject") : "")
				.append("\n\n");
		field(out, "Project", OpenProjectHal.linkTitle(wp, "project"));
		field(out, "Status", OpenProjectHal.linkTitle(wp, "status"));
		field(out, "Priority", OpenProjectHal.linkTitle(wp, "priority"));
		field(out, "Author", users.apply(wp, "author"));
		field(out, "Assignee", OpenProjectHal.linkTitle(wp, "assignee"));
		field(out, "Accountable", OpenProjectHal.linkTitle(wp, "responsible"));
		field(out, "Category", OpenProjectHal.linkTitle(wp, "category"));
		field(out, "Version", OpenProjectHal.linkTitle(wp, "version"));
		String parent = OpenProjectHal.linkId(wp, "parent");
		field(out, "Parent work package",
				parent != null ? "#" + parent + " " + nonNull(OpenProjectHal.linkTitle(wp, "parent")) : null);
		field(out, "Start date", OpenProjectHal.text(wp, "startDate"));
		field(out, "Finish date", OpenProjectHal.text(wp, "dueDate"));
		field(out, "Duration", OpenProjectHal.text(wp, "duration"));
		field(out, "Work", OpenProjectHal.text(wp, "estimatedTime"));
		field(out, "Remaining work", OpenProjectHal.text(wp, "remainingTime"));
		field(out, "% Complete", percent(OpenProjectHal.integer(wp, "percentageDone")));
		field(out, "Created at", OpenProjectHal.text(wp, "createdAt"));
		field(out, "Updated at", OpenProjectHal.text(wp, "updatedAt"));
		field(out, "URL", OpenProjectNodes.workPackageUrl(baseUri, id));
		out.append("\n## Description\n\n").append(nonNull(OpenProjectHal.text(wp, "description.raw")).trim())
				.append('\n');
		if (children != null && !children.isEmpty()) {
			out.append("\n## Child work packages\n\n");
			for (Map<String, Object> child : children) {
				out.append("- #").append(OpenProjectHal.string(child, "id")).append(' ')
						.append(nonNull(OpenProjectHal.string(child, "subject")));
				String status = OpenProjectHal.linkTitle(child, "status");
				if (status != null) {
					out.append(" (").append(status).append(')');
				}
				out.append('\n');
			}
		}
		if (relations != null && !relations.isEmpty()) {
			out.append("\n## Relations\n\n");
			for (Map<String, Object> relation : relations) {
				boolean outgoing = id.equals(OpenProjectHal.linkId(relation, "from"));
				String kind = OpenProjectHal.text(relation, outgoing ? "type" : "reverseType");
				String other = outgoing ? "to" : "from";
				out.append("- ").append(kind != null ? kind.replace('_', ' ') : "relates").append(" #")
						.append(OpenProjectHal.linkId(relation, other)).append(' ')
						.append(nonNull(OpenProjectHal.linkTitle(relation, other)));
				String description = OpenProjectHal.text(relation, "description");
				if (description != null) {
					out.append(": ").append(description.trim());
				}
				out.append('\n');
			}
		}
		attachments(out, attachments);
		activities(out, activities, users);
		return out.toString();
	}

	private static void activities(StringBuilder out, List<Map<String, Object>> activities, UserNames users) {
		if (activities == null || activities.isEmpty()) {
			return;
		}
		StringBuilder history = new StringBuilder();
		for (Map<String, Object> activity : activities) {
			// the text of an internal comment is a document of its own, its changes stay here
			String comment = OpenProjectHal.bool(activity, "internal") ? null
					: OpenProjectHal.text(activity, "comment.raw");
			List<Map<String, Object>> details = OpenProjectHal.list(activity, "details");
			if (comment == null && details.isEmpty()) {
				continue;
			}
			history.append("\n### ").append(nonNull(users.apply(activity, "user"))).append(", ")
					.append(nonNull(OpenProjectHal.text(activity, "createdAt"))).append("\n\n");
			for (Map<String, Object> detail : details) {
				String change = OpenProjectHal.text(detail, "raw");
				if (change != null) {
					history.append("- ").append(change.trim()).append('\n');
				}
			}
			if (comment != null) {
				history.append(details.isEmpty() ? "" : "\n").append(comment.trim()).append('\n');
			}
		}
		if (!history.isEmpty()) {
			out.append("\n## Activity\n").append(history);
		}
	}

	/**
	 * An internal comment of a work package: the work package it belongs to, its
	 * author, its dates and its text.
	 */
	static String renderInternalComment(Map<String, Object> activity, String workPackageId, UserNames users,
			String baseUri) {
		StringBuilder out = new StringBuilder();
		out.append("# Internal comment ").append(nonNull(OpenProjectHal.string(activity, "id")))
				.append(" on work package #").append(workPackageId).append("\n\n");
		field(out, "Work package",
				("#" + workPackageId + " " + nonNull(OpenProjectHal.linkTitle(activity, "workPackage"))).trim());
		field(out, "Author", users.apply(activity, "user"));
		field(out, "Created at", OpenProjectHal.text(activity, "createdAt"));
		field(out, "Updated at", OpenProjectHal.text(activity, "updatedAt"));
		field(out, "URL", OpenProjectNodes.workPackageUrl(baseUri, workPackageId));
		text(out, "Comment", OpenProjectHal.text(activity, "comment.raw"));
		return out.toString();
	}

	static String renderDocument(Map<String, Object> document, List<Map<String, Object>> attachments, String baseUri) {
		String id = OpenProjectHal.string(document, "id");
		StringBuilder out = new StringBuilder();
		out.append("# ").append(nonNull(OpenProjectHal.string(document, "title"))).append("\n\n");
		field(out, "Project", OpenProjectHal.linkTitle(document, "project"));
		field(out, "Category", OpenProjectHal.linkTitle(document, "category"));
		field(out, "Created at", OpenProjectHal.text(document, "createdAt"));
		field(out, "Updated at", OpenProjectHal.text(document, "updatedAt"));
		field(out, "URL", baseUri + "/documents/" + id);
		text(out, "Description", OpenProjectHal.text(document, "description.raw"));
		attachments(out, attachments);
		return out.toString();
	}

	static String renderMeeting(Map<String, Object> meeting, List<Map<String, Object>> agendaItems,
			List<Map<String, Object>> attachments, UserNames users, String baseUri) {
		String id = OpenProjectHal.string(meeting, "id");
		StringBuilder out = new StringBuilder();
		out.append("# Meeting: ").append(nonNull(OpenProjectHal.string(meeting, "title"))).append("\n\n");
		field(out, "Project", OpenProjectHal.linkTitle(meeting, "project"));
		field(out, "Start", OpenProjectHal.text(meeting, "startTime"));
		field(out, "End", OpenProjectHal.text(meeting, "endTime"));
		field(out, "Duration (hours)", OpenProjectHal.text(meeting, "duration"));
		field(out, "Location", OpenProjectHal.text(meeting, "location"));
		field(out, "Author", users.apply(meeting, "author"));
		field(out, "URL", baseUri + "/meetings/" + id);
		if (agendaItems != null && !agendaItems.isEmpty()) {
			out.append("\n## Agenda\n");
			for (Map<String, Object> item : agendaItems) {
				out.append("\n### ").append(nonNull(OpenProjectHal.text(item, "title")));
				String workPackage = OpenProjectHal.linkId(item, "workPackage");
				if (workPackage != null) {
					out.append(" (work package #").append(workPackage).append(' ')
							.append(nonNull(OpenProjectHal.linkTitle(item, "workPackage"))).append(')');
				}
				out.append("\n\n");
				field(out, "Duration (minutes)", OpenProjectHal.text(item, "durationInMinutes"));
				field(out, "Presenter", OpenProjectHal.linkTitle(item, "presenter"));
				String notes = OpenProjectHal.text(item, "notes.raw");
				if (notes != null) {
					out.append('\n').append(notes.trim()).append('\n');
				}
			}
		}
		attachments(out, attachments);
		return out.toString();
	}

	static String renderNews(Map<String, Object> news, UserNames users, String baseUri) {
		String id = OpenProjectHal.string(news, "id");
		StringBuilder out = new StringBuilder();
		out.append("# News: ").append(nonNull(OpenProjectHal.string(news, "title"))).append("\n\n");
		field(out, "Project", OpenProjectHal.linkTitle(news, "project"));
		field(out, "Author", users.apply(news, "author"));
		field(out, "Published at", OpenProjectHal.text(news, "createdAt"));
		field(out, "URL", baseUri + "/news/" + id);
		text(out, "Summary", OpenProjectHal.text(news, "summary"));
		text(out, "Content", OpenProjectHal.text(news, "description.raw"));
		return out.toString();
	}

	private static String nonNull(String value) {
		return value != null ? value : "";
	}
}
