# Agent tools: live test session of 2026-10-05

Outcomes of a live test session of the **agentic chat** after the changes of the
branch `feature/agent-tools-improvements` (knowledge bases from the chat, full
document reads, accent-folded full-text search, best-effort search calls with
timeouts and no retries, call parameters in the search service signatures). It
repeats the questions that failed in
[the session of 2026-10-04](SINGLE-AGENTS-CURRENT-RESULTS.md) and adds web and
knowledge base + web questions.

These are observations with their evidence, not decisions: nothing listed under
the findings has been changed in the code.

---

## 1. Setup

| Item | Value |
|---|---|
| Date | 2026-10-05, 10:50 – 11:19 (log time) |
| Code | `feature/agent-tools-improvements` at `33b4f4b1c` (local docker `gebo-monolith`, only the `gebo.ai` container rebuilt) |
| Chat | a new "Chat with knowledge base" chat, chip "Agentic chat" (`AGENTIC_LOOP_AGENTS_NETWORK`) |
| Agent model | gpt-4.1 |
| Service model (deep search partial analyses) | gpt-4o-mini |
| Ranker | Qwen3-Reranker-4B (regolo.ai) |
| Knowledge base | `biblioteca-esoterica`, 12 documents |
| Web search | Google search service |
| Search calls | `ai.gebo.search.calls`: retries 0, timeout 60 s, connect 15 s, read 60 s |
| Logging | DEBUG on agents, tools, search and ranker; TRACE on the KB tool answers, the deep search analyses, the full-text queries and the Google search service |

Quotations are marked **verified** when their text was found in what the tool
returned (TRACE of the KB tool answer or of the full-text hits). The contents of
web pages and of full document reads are not traced, so quotations coming from
them could not be verified.

---

## 2. Turns

18 turns in one chat. **search** = `searchKnowledgeBase`, **deep** = `deepSearchKnowledgeBase`,
**read** = full document read, **web** = `searchWeb`, **deepWeb** = `deepSearchWeb`.
✓ = correct and grounded, ◐ = partial, ✗ = not answered or wrong.

| # | Question (abridged) | Tool(s) | Retrieval (from the logs) | Outcome |
|---|---|---|---|---|
| 1 | How many documents does the KB hold? List them with uniqueId and title (IT) | KB listing | 12 documents | ✓ all 12 with uniqueId and file name (was ✗ "only one document") |
| 2 | Archiati: the twelve senses (IT) | search, read | 8 of 20 kept; document 2 read whole (134 chunks) | ✓ |
| 3 | The exact quotation on Fohat, with the page (IT) | search ×2, read | 5 of 13, 10 of 19 kept; *The Secret Doctrine* read whole (1388 chunks) | ✓ genuine passage, page not verified |
| 4 | Deep search: Pistis Sophia, structure, characters (IT) | deep, deepWeb (agent's choice) | KB 16 fragments, 11.6 s; web 63 fragments from 17 pages, **147.6 s** | ✓ KB + web, structured |
| 5 | Svabhavat in *The Secret Doctrine*, with a quotation (IT) | search | 5 of 10 kept | ✓ **verified** (was ✗ twice) |
| 6 | Archiati and Steiner on Ahriman, a textual quotation (IT) | search, web | 8 of 21 kept; 31 web results | ✓ Archiati quotation **verified**; Steiner's from a web page, attributed to it (was ✗ invented quotation) |
| 7 | Exhaustive deep search: Steiner's evolution vs Blavatsky's Rounds (IT) | deep, exhaustive | 12 fragments from 5 documents, 15.7 s, 8 discarded, 3 sources | ◐ grounded comparison; *The Secret Doctrine* used but dropped from the sources (see 4.2) |
| 8 | Read the whole document with uniqueId 4, part by part, with quotations (IT) | read | 332 of 332 chunks | ✓ dialogue by dialogue, with quotations |
| 9 | Web: latest stable Spring Boot and its date (IT) | web | 13 distinct results | ◐ "4.1.1, released 23 May 2024": the date is wrong (see 4.4) |
| 10 | Web: main new features of Java 25 (EN) | web | 19 distinct results | ✓ JEPs 506, 510, 511, 512, 513, 503… correct |
| 11 | Follow-up: which of those is still in preview, one sentence (EN) | web | | ◐ JEP 470, 502, 505 right; JEP 507 missed; not one sentence |
| 12 | KB: the Great Adamas in Pistis Sophia, then a modern scholarly reading from the web (IT) | search, web ×2 | 5 of 13 kept, lexical hits on "Adamas"; 25 + 29 web results | ✓ |
| 13 | Who are the Dhyan Chohans, a quotation from the KB (IT) | search | full-text query `Dhyan Chohans` matched `Dhyân Chohans` | ✓ **verified** (was ✗ twice) |
| 14 | Fifth Gospel: the Baptism in the Jordan, a textual passage (IT) | search | 5 of 14 kept | ✓ **verified** (was ✗ covers only) |
| 15 | With a deep search, decide the best introduction for a beginner (IT) | deep, exhaustive | 19 fragments from 6 documents, 11.4 s, 0 discarded | ◐ deep search used as asked (was plain search); 6 of 12 documents compared |
| 16 | Without searching: my first question and how many documents you listed (IT) | none | | ✓ exact question, 12 documents |
| 17 | What the KB says on the proof of Pythagoras' theorem, only from the documents (IT) | search | | ✓ says there is no proof, one passage cited and **verified** |
| 18 | Report: Steiner's Guardian of the Threshold in the KB vs current web sources (IT) | search, deep, deepWeb | KB 24 fragments from 6 documents, 15.1 s, 20 discarded; web 61 fragments from 16 pages, **163.7 s, ran away** | ◐ structured report; KB quotation not verifiable; claims the KB holds Steiner's *L'Iniziazione* (see 4.5) |

**Totals:** 12 ✓, 6 ◐, 0 ✗ in the knowledge base chat. The chat without knowledge bases (2 turns, see 4.7) got 2 ✗.

**For comparison, 2026-10-04:** 12 ✓, 3 ◐, 9 ✗ over 24 turns.

**Run health:**
- No error answer, no hang, no search call timeout.
- No system was unavailable, so no "could not search" notice was produced.

---

## 3. What works

- **The KB of the chat.** Every KB tool searched only `biblioteca-esoterica`, the KB of the chat profile.
- **Listing and full reads.**
  - The KB can be listed: all 12 documents with their uniqueId.
  - A document can be read whole from the vector store (332 of 332, 134 of 134, 1388 of 1388 chunks).
- **The full-text leg.**
  - It now gets the agent's short queries (`[Adamas, Pistis Sophia]`, `[Dhyan Chohans]`, `[Battesimo nel Giordano, Quinto Vangelo, …]`), no longer the whole question.
  - Unaccented spellings match accented text.
  - Every question that failed for lack of lexical hits on 2026-10-04 is now answered and verified: Svabhavat, Dhyan Chohans, Fifth Gospel.
- **No invented quotations.** Every KB quotation that could be checked was found in the text the tool returned. The Ahriman question, which got an invented quotation on 2026-10-04, got a real one.
- **Ranking.** The ranker chooses among about twice topK (8 of 20, 8 of 21, 5 of 14…).
- **Explicit deep searches are followed.** All three requests (turns 4, 7, 15) used the deep search tool. In turn 15 it had been ignored before.
- **KB deep searches** are short: 11–16 s each, one batch of 12–24 fragments.
- **Web searches run through the best-effort calls.**
  - Each result download uses the call parameters (`SearchCallParameters[connectTimeout=PT15S, readTimeout=PT1M, retries=0]` in the log).
  - One page with an untrusted TLS certificate was skipped, and the turn went on.
- **Memory and honesty.**
  - The conversation memory works without tool calls (turn 16).
  - The agent says so when the documents hold nothing on the question (turn 17).

---

## 4. Findings

### 4.1 The web deep search analyses a whole search in one large batch, slowly

Both web deep searches put every fragment into a single partial analysis by gpt-4o-mini:

| Turn | Batch | Time | Note |
|---|---|---|---|
| 4 | 63 fragments, 46,930 tokens, 17 pages | 147.6 s | |
| 18 | 61 fragments, 56,948 tokens, 16 pages | 163.7 s | `WARN … partial analysis ran away in 163643 ms, 27557 character(s): 570 irrelevant entr(ies) for 61 fragment(s), 13 distinct` |

- The analysis budget is `tokensBudget:85333`, so the whole search fits in one batch.
- The KB deep searches send 12–24 fragments (3–7k tokens) and take 11–16 s.
- In turn 18 the model repeated 13 fragment ids 570 times. The run-away detection noticed it, but only after the 163 s.
- These two analyses account for most of the session's waiting time.

Not changed: the batch size and the budget are limits to decide.

### 4.2 A deep search can still drop a source it used

Turn 7:
- The partial analysis read 5 fragments of *The Secret Doctrine* and 2 of *La Scienza Occulta*, among others.
- It listed 8 of the 12 fragments as irrelevant, and the sources kept were 3 documents without *The Secret Doctrine*.
- The answer says *The Secret Doctrine* is "cited in the analysis, though not explicitly listed among the documents analysed".

Same as finding 4.6 of 2026-10-04.

### 4.3 "Answer cites a document not read" warnings

The check fired 6 times, and every flagged name is a web page:

| Turn | Pages flagged |
|---|---|
| 10 | `25-relnote-issues.html` |
| 11 | `preview-list.html` |
| 18 | `GA010_c09.html`, `GA010b_c06.html` |

In turn 18 these pages came from the web deep search, whose analysis discarded 13 fragments. Whether the check compares the citations with every page read, or only with the kept sources, has not been verified in the code.

### 4.4 A wrong date in a web answer

Turn 9 answered "Spring Boot 4.1.1, released on 23 May 2024":
- The search results of the same session include "Spring Boot 4.0.0 available now" of November 2025, so the date is impossible.
- 23 May 2024 is the release date of Spring Boot 3.3.0.

Web page contents are not traced, so whether the model mixed two rows of a release table, or the page text did, cannot be told from the logs.

### 4.5 An unsupported statement about the knowledge base

Turn 18 says the knowledge base contains Steiner's *L'Iniziazione*. None of the 12 documents is that book, and no traced analysis or tool answer mentions it.

### 4.6 Smaller observations

- **Follow-up instructions.** Turn 11 did not answer in one sentence, and missed JEP 507, still in preview in Java 25.
- **Deep search coverage.** Turn 15 decided "the best introduction" on fragments of 6 of the 12 documents.
- **A handled download failure logged as three ERRORs.** A web page with an untrusted certificate (`blog.doubleslash.de`, `PKIX path building failed`) produced a stack trace at ERROR in `GMonolithicDocumentContentStreamerImpl`, `DocumentsCacheServiceImpl` and `DocumentsChunkServiceImpl`. The failure was handled and the page skipped.
- **Neo4j warnings.** In the session the graph queries produced 42 warnings about properties, labels and relationships that do not exist (`knowledgeBaseCode`, `code`, `entity_in_chunk`, `discovered_entity_alias`), plus 12 deprecation warnings. The graph of this KB looks empty or partial; not investigated.
- **Contents logged at INFO.** `GraphDataExtractionServiceImpl` logs the LLM output (`LLM OUTPUT=>entity;person;Grande Adamas;…`) at INFO, 18 times in the session, while contents belong at TRACE.

### 4.7 A chat without knowledge bases presents web results as its knowledge base

A second chat was created with the **"Chat"** option (profile "Chat with gpt-4.1", no
knowledge base, "Agentic chat"), served by `AgenticLoopPureChatNetworkAgentService`.

| # | Question | Tools | Outcome |
|---|---|---|---|
| A | How many documents does this chat's KB hold? List them with uniqueId (IT) | searchWeb (40 results), deepSearchWeb (23 fragments) | ✗ "the knowledge base of this chat contains 40 documents", followed by 40 uuids |
| B | Search the KB for what *The Secret Doctrine* says on Fohat, with a quotation (IT) | searchWeb only | ✗ "the search in the knowledge base did not return a quotation", citing `www.immagineperduta.it` |

**The scope is right.**
- The agent mounts 6 of 15 tools, without the 9 knowledge base tools (`mounts 6 of 15 tool(s), without the internal knowledge base tools [browseKnowledgeBaseFolders, …, searchKnowledgeBase, …, deepSearchKnowledgeBase]`).
- No KB content reached the chat.

**The answers are wrong.**
- The uuids of turn A are the ids of the web fragments. For example `2bbb6d9e-…` is `community.esri.com/…/creating-unique-id-based-on-count` in the deep search trace.
- In turn B, a web search is reported as a knowledge base search.
- Turn A also shows internal identifiers, which the prompt forbids ("Never show internal identifiers (fragment ids, document codes, uuids)").
- Turn A spent a web search and a web deep search on generic "knowledge base" pages.

**Cause, verified in the code and the log.**
- `AgenticLoopPureChatReactiveAgentServiceImpl` only filters the KB tools out of the mounted ones.
- It resolves the same prompt as the KB chat (`resolvePrompt(...) useCode:default-chat-agent-prompt`).
- `default-chat-agent-system-prompt.txt` names the knowledge base among the sources ("only the knowledge base, or the web", "look in the knowledge base"), and nothing tells the agent that this chat has no knowledge base.

**Fixed and re-tested (12:39).** The free chats' agent now has its own prompt,
`pure-chat-agent-prompt` (`pure-chat-agent-system-prompt.txt`). It is the KB chat's
prompt plus a "This chat has no knowledge base" section, and its examples name the web
and the external systems.

| # | Question | Tools | Outcome |
|---|---|---|---|
| A | Same as A above | none | ✓ "this chat has no internal knowledge base and no documents of its own", offers a web search |
| B | Same as B above | none | ✓ says it cannot search a knowledge base here, offers a web search |
| C | "Yes, search the web, with a quotation" | searchWeb | ✓ cites `www.immagineperduta.it` as a web site; says no direct quotation on Fohat was found. Both loop iterations reach the conclusion, so the answer repeats it |

---

## 5. Not covered by this session

- **A search system out of service or not answering.**
  - No system was down, so the "could not search" notices to the model and the agents' status notices were not seen live.
  - The unit tests cover them: `BestEffortSearchCallsTest`, `ContributionStatusNoticesTest`, `AbstractDeepSearchToolTest`.
- **Native searchers.** Jira, Confluence, SharePoint and Google Drive are not configured locally.
- **Remote connectors.** The microservice REST search clients were not exercised: there are no connector microservices locally.
- **Outside the chat.** A2A and the MCP export, which use all the knowledge bases visible to the caller.
