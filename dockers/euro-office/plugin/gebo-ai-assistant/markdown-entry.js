// Build entry for vendor/markdown.bundle.js - renders a chat/elaboration response's
// Markdown into sanitized HTML, exposed as window.GeboMarkdown.renderSafeHtml(text).
//
// Sanitizing matters here specifically because this content is model-generated and,
// for RAG/knowledge-base chat, influenced by retrieved documents - a prompt-injection
// attempt in a poisoned source document could get the model to emit a <script>/
// onerror= payload. That would execute inside this plugin's iframe (which holds the
// user's bearer token in memory) if inserted via innerHTML unsanitized, so every
// render path (on-screen display AND document insertion) goes through DOMPurify.
import { marked } from "marked";
import DOMPurify from "dompurify";

export function renderSafeHtml(markdownText) {
  const html = marked.parse(markdownText || "");
  return DOMPurify.sanitize(html);
}
