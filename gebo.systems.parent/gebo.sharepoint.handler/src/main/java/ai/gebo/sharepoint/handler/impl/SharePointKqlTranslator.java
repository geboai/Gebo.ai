package ai.gebo.sharepoint.handler.impl;

import java.util.*;
import java.util.stream.Collectors;

import ai.gebo.sharepoint.search.api.SharePointContentAttributeFilter;
import ai.gebo.sharepoint.search.api.SharePointPeopleFilter;
import ai.gebo.sharepoint.search.api.SharePointSearchFilter;

public class SharePointKqlTranslator {

    // Commonly used prefix for Modern Site Pages content type id (base "Site Page")
    // Note: this is a widely cited value in the SharePoint ecosystem; validate in your tenant if needed.
    private static final String MODERN_SITE_PAGE_CONTENTTYPEID_PREFIX =
            "0x0101009D1CB255DA76424F860D91F20E6C4118*";

    public static String createKqlQueryString(SharePointSearchFilter query) {
        if (query == null) return "";

        List<String> clauses = new ArrayList<>();

        SharePointContentAttributeFilter a = query.getContentAttributesFilter();
        if (a != null) {

            // 1) Content kind restriction (DOCUMENT and/or PAGE)
            addContentKindClause(clauses, a.getContentKinds());

            // 2) Title/name terms (best-effort: title:"...")
            addPropertyTermsClause(clauses, "title", normalize(a.getTitleTerms()), a.getTitleTermsMatchMode());

            // 3) Free text terms
            addFreeTextClause(clauses, normalize(a.getTextTerms()), a.getTextTermsMatchMode());

            // 4) Scope by siteUrls/pathPrefixes via path:
            addPropertyAnyAllClause(clauses, "path", normalize(a.getSiteUrls()), a.getSiteUrlsMatchMode(), true);
            addPropertyAnyAllClause(clauses, "path", normalize(a.getPathPrefixes()), a.getPathPrefixesMatchMode(), true);

            // 5) Generic managed properties
            addManagedProperties(clauses, a.getManagedPropertyEquals(), a.getManagedPropertyContains(),
                    a.getManagedPropertiesValuesMatchMode());
        }

        SharePointPeopleFilter p = query.getPeopleFilter();
        if (p != null) {
            // These are schema-dependent; keep them configurable. You can remap property names if your tenant uses different ones.
            addPropertyAnyAllClause(clauses, "Author", normalize(p.getCreatedByList()),
                    SharePointContentAttributeFilter.TextMatchMode.ANY, true);
            addPropertyAnyAllClause(clauses, "LastModifiedBy", normalize(p.getLastModifiedByList()),
                    SharePointContentAttributeFilter.TextMatchMode.ANY, true);
        }

        return clauses.isEmpty()
                ? ""
                : clauses.stream().filter(s -> !s.isBlank()).collect(Collectors.joining(" AND "));
    }

    private static void addContentKindClause(List<String> clauses, List<SharePointContentAttributeFilter.ContentKind> kindsRaw) {
        List<SharePointContentAttributeFilter.ContentKind> kinds =
                (kindsRaw == null || kindsRaw.isEmpty())
                        ? List.of(SharePointContentAttributeFilter.ContentKind.DOCUMENT,
                                  SharePointContentAttributeFilter.ContentKind.PAGE)
                        : kindsRaw.stream().filter(Objects::nonNull).distinct().toList();

        List<String> parts = new ArrayList<>();
        if (kinds.contains(SharePointContentAttributeFilter.ContentKind.DOCUMENT)) {
            // Common SharePoint KQL managed property
            parts.add("IsDocument:true");
        }
        if (kinds.contains(SharePointContentAttributeFilter.ContentKind.PAGE)) {
            // Modern site pages (Site Pages)
            parts.add("ContentTypeId:" + bareOrQuote(MODERN_SITE_PAGE_CONTENTTYPEID_PREFIX));
        }
        if (!parts.isEmpty()) {
            clauses.add(parts.size() > 1 ? "(" + String.join(" OR ", parts) + ")" : parts.get(0));
        }
    }

    private static void addPropertyTermsClause(List<String> clauses,
                                               String property,
                                               List<String> terms,
                                               SharePointContentAttributeFilter.TextMatchMode mode) {
        if (terms.isEmpty()) return;
        String joinOp = (mode == SharePointContentAttributeFilter.TextMatchMode.ALL) ? " AND " : " OR ";
        String inner = terms.stream()
                .map(t -> property + ":" + kqlQuote(t))
                .collect(Collectors.joining(joinOp));
        clauses.add(terms.size() > 1 ? "(" + inner + ")" : inner);
    }

    private static void addFreeTextClause(List<String> clauses,
                                          List<String> terms,
                                          SharePointContentAttributeFilter.TextMatchMode mode) {
        if (terms.isEmpty()) return;
        String joinOp = (mode == SharePointContentAttributeFilter.TextMatchMode.ALL) ? " AND " : " OR ";
        String inner = terms.stream().map(SharePointKqlTranslator::kqlQuote).collect(Collectors.joining(joinOp));
        clauses.add(terms.size() > 1 ? "(" + inner + ")" : inner);
    }

    private static void addPropertyAnyAllClause(List<String> clauses,
                                                String property,
                                                List<String> values,
                                                SharePointContentAttributeFilter.TextMatchMode mode,
                                                boolean quoteValues) {
        if (values.isEmpty()) return;
        SharePointContentAttributeFilter.TextMatchMode m =
                (mode == null ? SharePointContentAttributeFilter.TextMatchMode.ANY : mode);
        String joinOp = (m == SharePointContentAttributeFilter.TextMatchMode.ALL) ? " AND " : " OR ";

        String inner = values.stream()
                .map(v -> property + ":" + (quoteValues ? kqlQuote(v) : bareOrQuote(v)))
                .collect(Collectors.joining(joinOp));
        clauses.add(values.size() > 1 ? "(" + inner + ")" : inner);
    }

    private static void addManagedProperties(List<String> clauses,
                                             Map<String, List<String>> equalsMp,
                                             Map<String, List<String>> containsMp,
                                             SharePointContentAttributeFilter.TextMatchMode valuesMode) {
        SharePointContentAttributeFilter.TextMatchMode m =
                (valuesMode == null ? SharePointContentAttributeFilter.TextMatchMode.ANY : valuesMode);

        if (equalsMp != null) {
            for (var e : equalsMp.entrySet()) {
                String prop = safeManagedProperty(e.getKey());
                List<String> vals = normalize(e.getValue());
                if (prop == null || vals.isEmpty()) continue;

                String joinOp = (m == SharePointContentAttributeFilter.TextMatchMode.ALL) ? " AND " : " OR ";
                String inner = vals.stream().map(v -> prop + ":" + kqlQuote(v)).collect(Collectors.joining(joinOp));
                clauses.add(vals.size() > 1 ? "(" + inner + ")" : inner);
            }
        }

        if (containsMp != null) {
            for (var e : containsMp.entrySet()) {
                String prop = safeManagedProperty(e.getKey());
                List<String> vals = normalize(e.getValue());
                if (prop == null || vals.isEmpty()) continue;

                String joinOp = (m == SharePointContentAttributeFilter.TextMatchMode.ALL) ? " AND " : " OR ";
                String inner = vals.stream().map(v -> prop + ":" + kqlQuote(v)).collect(Collectors.joining(joinOp));
                clauses.add(vals.size() > 1 ? "(" + inner + ")" : inner);
            }
        }
    }

    private static List<String> normalize(List<String> list) {
        if (list == null) return List.of();
        return list.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .distinct()
                .collect(Collectors.toList());
    }

    private static String kqlQuote(String raw) {
        String s = raw.trim();
        if (s.length() >= 2 && ((s.startsWith("\"") && s.endsWith("\"")) || (s.startsWith("'") && s.endsWith("'")))) {
            s = s.substring(1, s.length() - 1);
        }
        s = s.replace("\\", "\\\\").replace("\"", "\\\"");
        return "\"" + s + "\"";
    }

    private static String bareOrQuote(String token) {
        String t = token.trim();
        if (t.matches("^[A-Za-z0-9._*:-]+$")) return t; // allow '*' and ':' often used in KQL patterns
        return kqlQuote(t);
    }

    private static String safeManagedProperty(String raw) {
        if (raw == null) return null;
        String p = raw.trim();
        if (p.isEmpty()) return null;
        if (!p.matches("^[A-Za-z][A-Za-z0-9_]*$")) return null; // prevent injection via property name
        return p;
    }
}