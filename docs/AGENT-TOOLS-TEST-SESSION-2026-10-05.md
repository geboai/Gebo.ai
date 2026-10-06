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

### 4.3 "Answer cites a document not read" warnings, and answers redone for nothing

The check that warns "The answer cites …, not read for this request" fired 6 times
(the first version of this summary counted only the web pages):

| Turn | Flagged | What had happened |
|---|---|---|
| 1 | the 12 KB documents | listed by `browseKnowledgeBaseDocuments` (names, as asked) |
| 7 | `The-Secret-Doctrine-1-of-4.pdf` | analysed by the deep search, then dropped from its sources (see 4.2) |
| 8 | `dialoghi-su-ermetismo.pdf` | read whole by `getKnowledgeBaseDocumentContents` |
| 10 | `25-relnote-issues.html` | a web result of the request |
| 11 | `preview-list.html` | a web result of the request |
| 18 | `GA010_c09.html`, `GA010b_c06.html` | pages of the web deep search |

The same causes discarded answers the user never saw:
- In turn 1 the first answer, built on the listing, was discarded as "used no search tool" and written again.
- In turn 8 the answer citing the document it had just read was discarded, and the document was read a second time (10:58:49 and 10:59:12).

**Causes, verified in the code:**
- The knowledge base browsing tools (`knowledge-base-browsing-tool-source`) were not among the tool sources whose calls are evidence (`AgenticLoopReactiveAgentServiceImpl.EVIDENCE_TOOL_SOURCES`).
- For an analysis, only the deep searches counted, not a document read whole.
- The browsing tools did not record the documents they listed or read.
- A web result is named after its site (`GoogleSearchServiceImpl`: `displayLink`, e.g. `docs.oracle.com`), while the answer cites the page by the file name its address ends with (`preview-list.html`).

**Fixed and re-tested (12:48–12:56):**
- The browsing tools are evidence. For an analysis, a document read whole counts next to the deep searches.
- A document read whole is shared with the answer, and shown among the found documents.
- The names a listing returns count as citable, without becoming documents found.
- A search result is also citable by its address (without its query, also decoded).

| Retest | Before | After |
|---|---|---|
| List the KB documents | 2 iterations, warning on 12 documents | 1 iteration, no warning |
| Read document 6 whole | (turn 8: 2 iterations, read twice, warning) | 1 iteration, read once, no warning, the document in "Found docs" |
| Web: Java 25 features, citing the pages | warnings on `25-relnote-issues.html`, `preview-list.html` | the same pages cited, no warning |

Turn 7's warning, on a source dropped by the deep search, is finding 4.2 and remains.

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

---

## 6. Second session: redrafted prompts, full rebuild (15:44 – 15:55)

The two single agent prompts were redrafted (`2d0d9803d`): where the answer comes from (the sources, or the model's own knowledge said to be so), strict citations (never an invented address), a section on searching again, every iteration shown to the user. The whole reactor was rebuilt (`mvn clean install -DskipTests -P angular-ui,bootables`, 189 modules) and only the `gebo.ai` container redeployed. The prompts are model agnostic; this installation runs gpt-4.1, others run Qwen ~100B, gpt-oss-120b or other models.

Every web address cited was checked against the addresses the web searches returned in the logs; quotations against the traced tool answers.

### Chat with knowledge base

| # | Question (abridged) | Tools | Outcome |
|---|---|---|---|
| K1 | The documents of the KB, with uniqueId | listing | ✓ 12 documents, one iteration, no warning |
| K2 | In short, what is Pythagoras' theorem (previous deploy, same prompt but the search section) | none | ✓ "from general knowledge, not from the documents" |
| K3 | Fohat in *The Secret Doctrine*, a quotation with the page | 1 KB search | ✗ "not found" after one search (see 6.3) |
| K4 | The Dhyan Chohans, a quotation | KB search | ✓ **verified** (both passages) |
| K5 | Archiati and Steiner on Ahriman, quotations | KB searches | ✓ both Archiati quotations **verified**; "no Steiner quotation in the fragments", none invented |
| K6 | Read document 3 whole, 5 points with quotations | read | ✓ honest: the document has no vectorized text, nothing invented; a "not read" warning on its name (6.3) |
| K7 | Exhaustive deep search: Steiner's evolution vs the Rounds | deep KB | ◐ grounded; one quotation is an Italian rendering of an English passage (6.3) |
| K8 | Latest stable Spring Boot and its date, citing the pages | web ×2 | ◐ both addresses returned by the search; "3.5.16" and no date (6.3) |
| K9 | The Great Adamas in the KB, then a modern reading from the web | KB, web | ◐ both addresses returned; general knowledge said to be so; KB "no direct passage" after one search (6.3) |
| K10 | The proof of Pythagoras' theorem, only from the documents | KB search | ✓ none; the passages it mentions are the returned ones |
| K11 | Without searching: the first question and the documents listed | none | ✓ |

### Chat without knowledge base

| # | Question (abridged) | Tools | Outcome |
|---|---|---|---|
| P1 | The documents of this chat's KB, what they say on Fohat | none | ✓ the chat has no knowledge base; offers the web |
| P2 | In short, what photosynthesis is | none | ✓ "from my general knowledge, not from external sources" |
| P3 | Web: when Java 25 was released, what is still in preview, citing the pages | web | ◐ both addresses returned; no release date, JEP numbers wrong (6.3) |
| P4 | Deep web search: a short report on commercial nuclear fusion, citing the sources | deep web | ✓ 6 of 6 addresses returned by the searches (PDF reports); 89 fragments in two batches, 12 s and 19 s |
| P5 | Without searching: what we talked about | none | ✓ |

**Totals:** with knowledge base 6 ✓, 4 ◐, 1 ✗; without 4 ✓, 1 ◐. **No invented address: 13 of 13 cited addresses were returned by a tool.** The only errors in the log: the MCP tools export at startup (code on develop, see the audit) and one web page download, handled.

### 6.1 What the redraft changed, seen live
- Answers from the model's own knowledge are labelled as such and cite nothing (K2, P2, part of K9).
- Addresses are cited exactly as returned, never made up (K8, K9, P3, P4).
- The chat without knowledge base says so and does not search (P1).
- No duplicated conclusion: every turn ran in one iteration.

### 6.2 Web deep search speed
The batch now fills half of the service model context (`ddc009e4f`): P4's 89 fragments went in two batches (4 and 85), 12 s and 19 s, no runaway. The morning's single batches took 148 s and 164 s.

### 6.3 Remaining findings (diagnosed, not changed)
- **No second search when the first is poor (K3, K9).** The prompt says to search again before saying a source has nothing; the agent did not. The first search returned the near-empty "hub" fragments of finding 4.1 of 2026-10-04 ("complete explanation of this fact?", "Footnotes", "314. 511 Isis Unveiled, I. 341."), ranked above the passages the full-text leg had found. A prompt rule depends on the model: a model-agnostic remedy would be in the knowledge base tool (the fragments it returns, or a note when they are near-empty).
- **"Search the web: question? Cite the pages" classified as PURE_SEARCH (P3).** Its format lists pages with a reason each, so the question (the date) was not answered.
- **Wrong facts from the web (K8, P3, and the morning's turn 9).** JEP numbers paired with the wrong features twice; "3.5.16" as the latest Spring Boot. The pages' text is not traced: whether the model or the page mixed them cannot be told.
- **A translation shown as a quotation (K7).** The deep search's partial analysis rendered an English passage of *The Secret Doctrine* in Italian, in quotation marks, and the answer kept it.
- **"Not read" warning on a document named by the reading tool without text (K6).** The tool named it but had no text to read; the answer cited it only to say so.
- **The analysis judged all the fragments irrelevant while using them (K7).** Handled by the code (the documents stay the sources, WARN logged).

---

## 7. Third session: analytical requests (18:22 – 18:44)

After the rule on analytical requests (`016af74d1`: plan the searches across the subject's angles and detailed aspects, check the deep search's analysis against them, complete what is missing), the quotation rule (`7a1073b87`) and the platform's link and reference format rules (`10d9f94d4`). Only the `gebo.ai` container redeployed; gpt-4.1.

| # | Chat | Request (abridged) | Tools | Outcome |
|---|---|---|---|---|
| A1 | KB | Detailed analysis: Christ in the KB, comparing works and authors | 1 deep KB (5 searches, 23 fragments, 1 source) | ◐ grounded, says the coverage is centred on Steiner's Fifth Gospel; no completing search for the other authors (Archiati's *I grandi segni dei tempi* speaks of Christ); "not read" warning on `Vangeli-non-canonici.pdf` |
| A2 | KB | Analysis: Archiati's twelve senses vs Blavatsky's seven principles | 1 deep KB (31 fragments, 27 discarded, 3 sources) | ✓ structured, grounded on both authors; no completing search |
| A3 | KB | Decision: which document to start from for anthroposophy | 1 deep KB (31 fragments, 3 sources) | ✓ recommendation, tradeoffs, what would change it |
| A4 | KB + web | Report: Steiner's Guardian of the Threshold in the KB vs the web | deep KB (16 fragments, 15 s) + deep web (96 fragments) | ◐ 6 of 6 links returned by the searches; web batch of 73 fragments **ran away** (168 s, 626 repeated entries); some loosely related web sources |
| A5 | KB | Without searching: the topics so far | none | ✓ |
| N1 | no KB | Comparative analysis Spring AI vs LangChain4j for enterprise RAG, with sources | 1 deep web (5 searches on distinct angles, 81 fragments, ~35 s) | ✓ 6 of 6 links returned; the searches covered the comparison and each framework's architecture, tool calling, vector stores, observability, maturity |
| N2 | no KB | An analysis of the KB documents on Steiner | none | ✓ no knowledge base here, offers the web; the evidence gate (ANALISYS) discarded the first, correct, tool-free answer and asked again |

**What the rule changed:** the angles are planned (N1's searches cover each aspect asked). **What it did not:** no turn completed its deep search with further searches; every analytical turn ran one deep search per source and answered (A1 left authors out). A rule in the prompt depends on the model: a model-agnostic way would be in code (for example the deep search result telling the agent which documents or angles it covered).

**Other observations:** a web deep search batch of 73 fragments still ran away at half the service model context (A4); N1's 72-fragment batch did not. The evidence gate retries an analysis answered without tools even when no tool can answer it (N2).

## 8. Fourth session: deep search coverage (2026-10-06, 06:43 – 06:54)

After the coverage report of the deep search tools (`3ba4238ff`) and the coverage gate of the agentic loop (`f4596fdf9`), with the default rules (thin when fewer than 2 documents are used of 3 or more found; knowledge base only, when more than half of the documents found were read in at most 2 fragments, or when the documents of the knowledge bases no search reached are as many as the documents used; or when the analysis reports something missing; FOCUSED never thin; gate on). Only the `gebo.ai` container redeployed; gpt-4.1.

| # | Chat | Request (abridged) | Tools | Coverage and gate | Outcome |
|---|---|---|---|---|---|
| C1 (= A1) | KB | Detailed analysis: Christ in the KB, comparing works and authors | iteration 1: deep KB (5 searches, 20 fragments, 2 sources); iteration 2: KB search (5 queries), 2 documents read whole | thin: 5 of 7 documents read in at most 2 fragments, 5 not reached → answer discarded, completed | ✓ answer by author (Steiner, Gurdjieff/Ouspensky, gnostic texts, Archiati), with divergences and limits; no leak of the discarded text; ~1 min 40 s |
| C2 (= A2) | KB | Analysis: Archiati's twelve senses vs Blavatsky's seven principles | iteration 1: 2 deep KB; iteration 2: the same 2 deep KB again | thin both times (3 of 8 used, 4 not reached; 2 of 3 used, 2 barely read, 9 not reached) → discarded, redone | ◐ grounded, but ~37 s spent on deep searches that repeat the first ones |
| C3 | KB | A date in Archiati's *I grandi segni dei tempi*, asking for a FOCUSED deep search | 1 KB search (the model chose a plain search) | gate not involved | ✓ 5 s |
| C4 (= N1) | no KB | Comparative analysis Spring AI vs LangChain4j for enterprise RAG, with sources | 1 deep web (5 searches, 75 fragments, 18 sources) | 18 of 19 used: not thin, streamed as usual | ✓ no warning; the 73-fragment web batch **ran away** again (170 s, see 4.1) |

**What works:** the gate completes a thin knowledge base analysis for gpt-4.1 without relying on the prompt (C1, where A1 left authors out); a well covered web analysis is not slowed (C4); the result keeps the coverage before the analysis.

**Finding: the knowledge base rules fire on a targeted analysis.** With a knowledge base of 12 documents and a question about two named authors (C2), "not reached ≥ used" and "barely read" hold for every deep search: the documents not reached are mostly unrelated to the question, and the gate costs an iteration. The agent then repeats the same deep searches rather than the focused searches or full reads the note suggests. Not changed: the thresholds, and whether "not reached" should count only for broad questions, are a decision to take.

**Limit by design:** the gate holds the first iteration only (as the evidence gate): a deep search made in a later iteration reports its coverage but is not gated.

## 9. Fifth session: coverage rules for any kind of document (2026-10-06, 07:40 – 08:09)

Section 8 showed the knowledge base rules firing on a targeted analysis. Judged against an enterprise corpus (contracts, manuals, tickets, mails, thousands of documents) two of them do not hold in general: the documents of the knowledge base no search reached are always many in a large one, and a ticket or a mail is read whole in one or two fragments. The rules were rewritten (`577f37396`): the coverage is thin when the analysis reports something missing, when documents found were left unread, when most sources were read in part out of a longer document (where it can be read whole and its length is known, the documents named), or when the analysis rests on too few of the documents it used or left unread; the documents judged irrelevant were read, not missed, and the documents not reached are only reported. A deep search repeating the searches of an earlier one of the request is refused without counting. Only the `gebo.ai` container redeployed; gpt-4.1.

| # | Chat | Request (abridged) | Tools | Coverage and gate | Outcome |
|---|---|---|---|---|---|
| C5 (= A1) | KB | Detailed analysis: Christ in the KB, comparing works and authors | 1 deep KB (21 fragments, 2 sources) | 2 of 6 used, the other 4 read and judged irrelevant, none unread: not thin | ◐ one iteration; but the batch **ran away** (154 s) and its irrelevant list came from the runaway output (see below) |
| C6 (= A2) | KB | Analysis: Archiati's twelve senses vs Blavatsky's seven principles | 1 deep KB (20 fragments, 4 sources) | 4 of 6 used: not thin | ✓ ~30 s instead of ~1 min 40 s (C2) |
| C7 (= N1) | no KB | Comparative analysis Spring AI vs LangChain4j, with sources | 1 deep web: one batch of 73 fragments, its model stream **timed out** | before the fix: 20 of 20 sources kept although nothing was read | ✗ found a general bug, fixed (below) |
| C8 (= N1) | no KB | the same, after the fix | deep web (batch of 72 timed out) → `FAILED`, then 9 web searches | the agent is told the deep search failed | ✓ answer on the 18 documents the searches returned, 6 links all returned, no warning |

**Bug found and fixed (`59f395995`, `577f37396`).** A batch whose model call failed was dropped by the batch coordinator without telling which fragments it held: the deep search counted them as analysed, kept every document found as a source and passed on a text the coordinator wrote without documents. The documents of a failed batch are now unprocessed; a fragment left unread is never a source; an analysis that read nothing is a `FAILED` deep search, and the agent goes on with the search tools. The fix is in the shared coordinator, so the deep search pipelines get it too.

**Still open, to decide:** the web deep search analyses all it found in one batch (72-73 fragments at half the service model context) and that batch ran away or timed out in every web deep search of this session (3 of 3), and once on the knowledge base (C5, 21 fragments). When a batch runs away its list of irrelevant fragments is not reliable either (C5 discarded a document that speaks of the subject). Finding 4.1; batch size, timeout and the runaway handling are a decision to take.

**Data:** the length of a knowledge base document reaches the coverage from its semantic (vector store) fragments; the full-text index does not store it, so a document found only by full text has no known length and is never judged read in part.

## 10. Sixth session: the runaway of the partial analyses (2026-10-06, 09:25 – 09:32)

After `76d4b94f4`, in both deep searches (the agents' tools and the deep search pipeline): **A** the streamed batch call stops as soon as the irrelevant list gives more entries than the batch has fragments, and a list that ran away is ignored; **B** the fragments of a batch are given to the model numbered 1..n, the numbers mapped back to their ids. Only the `gebo.ai` container redeployed; agent model gpt-4.1, batch (service) model gpt-4o-mini.

| # | Chat | Request (abridged) | Batches (fragments: time) | Irrelevant list | Outcome |
|---|---|---|---|---|---|
| R1 (= A1) | KB | Christ in the KB, comparing works and authors | 17: 11.5 s | 6 of 17, plausible | ✓ 28 s for the turn (A1 earlier: 154 s runaway); 1 source of 6 documents, see 10.2 |
| R2 (= A2) | KB | Archiati's twelve senses vs Blavatsky's seven principles | 22: 11.2 s; 15: 9.2 s | 11 of 22; 0 of 15 | ✓ 47 s, two deep searches, one per author |
| R3 | KB | Decision: which document to start from for anthroposophy | 19: 8.3 s | 2 of 19 | ◐ coverage thin (two sources read in 2 of 864 and 2 of 568 fragments), the answer not held: see 10.3 |
| R4 (= N1) | no KB | Spring AI vs LangChain4j, with sources | 74: 20.7 s | **1..74, all** | ◐ 32 s for the deep search (earlier: 170-190 s and lost); the "all irrelevant yet analysed" rule kept the 21 pages; 9 links |
| R5 | no KB | PostgreSQL vs MongoDB for document management, with sources | 8: 12.9 s; 70: 20.3 s | 3 of 8; **1..67 of 70** | ✗ 67 fragments wrongly discarded: 3 of 22 pages kept as sources, the answer cites 6 links (all returned by the searches), 3 of them missing from Found docs |

No batch ran away or timed out (the stop condition was never needed in these turns); every deep search finished in 8-21 s per batch.

### 10.1 What A and B achieved

- **Time and reliability:** the 72-74 fragment web batch went from a runaway of 170-190 s that lost the whole batch to 20 s with an analysis, three times out of three.
- **The stop condition** is pinned by unit tests (the stream is cancelled and nothing more read); it did not trigger live, so whether the provider's HTTP stream is actually closed on cancel has not been observed.
- **The deep search pipeline** (`FullReactiveDeepsearchWorker`) has the same change, verified by its unit tests and compilation only.

### 10.2 What remains wrong: the irrelevant list itself

With numbers the failure of the batch model changed form: on the two large web batches it enumerated the whole range (1..74, 1..67 of 70) while writing a full analysis. A complete enumeration is caught by the existing rule (all fragments judged irrelevant yet analysed: they stay sources), a partial one is not and discards real sources (R5). A negative list ("which fragments did you NOT use") is unreliable whatever the ids: long UUIDs make it repeat itself, short numbers make it enumerate. On the small knowledge base batches (8-22 fragments) the lists were plausible.

### 10.3 Other findings of this session

- **The coverage gate applies only to the deliverables that require evidence** (an analysis, a search): R3 (DECISION) used a deep search whose coverage asked to be completed, and the answer was not held.
- **KB deep search retrieval concentrated on one document:** R1 got 11 of its 17 fragments from one book; the analysis across authors rests on one source, and the coverage is not thin because the other documents were read and judged irrelevant.
- **The partial analyses build links from knowledge base paths** (`https://biblioteca-esoterica/...pdf`), against the prompt's url rule; they did not reach the final answers (the agent's link rule held), but they travel in the tool result.
- **A cited page missing from Found docs is not warned:** the unread-citation warning checks document names, not web addresses.

## 11. Audit and remediation list (2026-10-06)

General (any model, any kind of document), ordered by impact. *Decision* marks a cap, limit, retry or retrieval policy: diagnosed here, to be decided.

| # | Finding | Impact | Remediation | Kind |
|---|---|---|---|---|
| 1 | The irrelevant list of a partial analysis is unreliable on large batches: it repeats itself (UUIDs) or enumerates the range (numbers); a partial enumeration discards real sources (R5) | Wrong sources and coverage, answer links missing from Found docs | **C: a positive list** — each extraction block cites the fragment numbers it rests on; the sources are the cited fragments, the irrelevant list goes. Also gives the coverage real per-document use. Interim alternative: a batch whose list covers most of the batch while writing an analysis is treated as "not judged" | Code + shared prompt |
| 2 | The web deep search analyses everything in one batch (72-74 fragments, ~62k tokens at half the service model context) | One batch carries all the risk; no early stop or fold applies | Smaller batches (share or fragments per batch) — fewer list errors, parallelism, early stop | *Decision* |
| 3 | The coverage gate applies only to deliverables requiring evidence (R3) | A decision or report on a thin deep search is not completed | Attach the coverage gate whenever deep searches are mounted, whatever the deliverable | Code, small |
| 4 | KB deep search retrieval concentrated on one document (R1: 11 of 17 fragments) | A cross-document analysis rests on one source | A per-document share in the deep search retrieval (the full-text leg already keeps 3 per document, the semantic one has none) | *Decision* (retrieval policy) |
| 5 | A cited web page missing from Found docs is not warned | Answer and sources disagree silently | Extend the unread-citation check to the addresses the answer cites | Code, small |
| 6 | Partial analyses build links from knowledge base paths | Made-up links in the tool result (not in the final answers seen) | Drop from the analysis the links that are no source's address, in code | Code, small |
| 7 | The length of a KB document is known only from its vector fragments (the full-text index does not store it) | A document found only by full text is never judged read in part | Index the chunk count in the full-text index | *Decision* (indexing) |
| 8 | The coverage gate holds the first iteration only | A deep search made later is not gated | Hold any iteration that answers on a pending coverage, still once per request | Code, design choice |
| 9 | The evidence gate retries an analysis answered without tools even when no tool can answer it (N2) | One wasted iteration | Do not require evidence when the request names a source the chat does not have | Code |
| 10 | "Cerca sul web: ..." classified PURE_SEARCH (earlier sessions) | Answer shaped as a search list | Classification prompt | Prompt |
| 11 | Stream cancel not observed live | Tokens may keep being billed after a stop | Verify on a provider with a forced runaway (or in the client's logs) | Verification |
| 12 | Wrong facts copied from web pages (JEP numbers, earlier sessions) | Source quality | None in code: the answer cites the page | Accepted |
| 13 | Startup "Error exporting MCP tools - Not authenticated" (pre-existing on develop) | Log noise | Separate issue | Out of scope |

Recommended next: **1 (C)**, then **3**, **5** and **6** (small, general, no limit), then decide **2** and **4**.

## 12. Seventh session: loading the results of an open network (2026-10-06, 10:41 – 10:43)

After `5ba15fda9`: each search service says how its results are loaded; the web providers load as an open network (all candidates at once, grouped by host, 2 at a time per host with 500 ms between requests, a host skipped after 2 failed pages, 60 s per page and 60 s for the whole loading, all configurable under `ai.gebo.search.open-network-loading`), and every result that gives nothing is told to the agent as `documentsNotRead`, with its reason. Only the `gebo.ai` container redeployed; gpt-4.1.

| # | Chat | Request | Loading | Outcome |
|---|---|---|---|---|
| L1 (= N1) | no KB | Spring AI vs LangChain4j, with sources (deep web) | 26 candidates from 21 hosts in ~1 s (pages cached by earlier runs); medium.com skipped after 2 failed pages; 19 read, 7 not loaded, each with its reason | ◐ 21 documents told as not read (7 not loaded, 14 not read or judged not relevant), none cited; the 73-fragment batch listed 67 fragments irrelevant again (audit item 1): 5 sources |
| L2 | no KB | Latest stable PostgreSQL and its date, citing the pages (search) | 12 candidates from 10 hosts in ~2 s, all loaded | ✗ the ranker kept nextcloud.com and chirpstack.io and left out the postgresql.org pages (release notes, 17 and 18 press kits), told as "no passage serves the search objective"; the answer gave **16.2 of February 2024** from training with a `postgresql.org/download` link no search returned, and no warning |

**What works:** the loading is per source; the web pages load at once and a failing site stops being asked; the agent gets every result that gave nothing with its reason, and none of them reaches Found docs.

**New finding (L2):** when the ranking leaves out the pages that hold the answer, the model answers from its training with a made-up link on the right domain. Two general gaps meet: the ranking of a small search (topK 4, 1500 tokens) can drop the authoritative page, and nothing in code catches a cited address that no tool returned (audit items 5 and 6).

**Not observed live:** a page past its deadline, a capped pool, the loading phase ending (covered by unit tests with real timings).

| # | Finding | Impact | Remediation | Kind |
|---|---|---|---|---|
| 14 | A search's ranking can leave out the pages that hold the answer (L2: the official release notes and press kits) and the model then answers from training with a made-up link | Wrong, ungrounded answer that looks cited | In code: a cited address that no tool returned is removed or flagged (items 5 and 6); and the ranking of a plain search to be reviewed (how many documents it keeps, whether a document whose title matches the question can be dropped) | Code (5, 6) + *Decision* (ranking) |

## 13. Eighth session: made-up addresses removed by code (2026-10-06, 11:59 – 12:03)

After `b798c735a` (audit items 5 and 6): the answer of the single agent chats streams through a guard that removes every web address no tool of the request returned (except their documents not read), nor the user or the chat gave, warning the user with the addresses removed; a deep search analysis loses the links that are no address of the documents found nor in their fragments. Only the `gebo.ai` container redeployed; gpt-4.1.

| # | Chat | Request | Outcome |
|---|---|---|---|
| G1 (= L2) | no KB | Latest stable PostgreSQL and its date, citing the pages | ◐ the made-up `postgresql.org/about/news/postgresql-164-released-2800/` was **removed** from the answer; but the model did not search at all: classified PURE_SEARCH, iteration 1 answered without tools and was discarded, iteration 2 (not gated) answered again from training ("16.4, 9 May 2024"), no documents. See item 15 |
| G2 (= N1) | no KB | Spring AI vs LangChain4j, with sources (deep web) | ✓ no address removed: the 9 links of the answer were all returned by the searches; 14 sources, 13 documents told as not read |
| G3 (= A1) | KB | Christ in the KB, comparing works and authors | ✓ the analysis made up 4 links from the knowledge base paths (`https://biblioteca-esoterica/...pdf`): **removed** before the agent read it; the answer gives no address |

**Item 15 (new):** the evidence gate discards a tool-free answer once, and the next iteration is not gated: a model that does not search a second time answers from its training. The guard of item 5 removes its made-up links, not its unsupported facts.

| # | Finding | Impact | Remediation | Kind |
|---|---|---|---|---|
| 15 | After an evidence discard the next iteration is not gated (G1: answered twice without searching) | An answer that needed the sources rests on training | Keep the evidence gate on the iteration after a discard; on the last iteration show the answer with a warning that no source was searched | Code, design choice |

Items 5 and 6 of section 11 are done (`b798c735a`).

## 14. Ninth session: document ids, top documents, the end of PURE_SEARCH (2026-10-06, 13:03 - 14:03)

After `15f940802` (no model-chosen maxTokens: a search's contents take a third of the room its model call leaves), `feb48c72c` (every document a tool returns carries a short id of the request, #1 #2...; the search tools rank every chunk and keep the topK best documents with all their chunks, no irrelevance filter; each answer ends with ANSWER-DOCUMENTS listing the ids it rests on, removed while streaming, and Found docs are those) and `a2d384b78` (PURE_SEARCH removed everywhere; the request understanding outputs searchRequested; evidence is required for an analysis or when the user asked to search, and the request stays held until it searches). Only the `gebo.ai` container redeployed; gpt-4.1.

| # | Chat | Request | Outcome |
|---|---|---|---|
| I1 | KB | Archiati on the twelve senses, citing the documents | ✓ 32 fragments from the 5 best of 6 documents; the answer listed #3 #2; Found docs = those 2 of 5 |
| I2 | KB | Detailed analysis, Christ across works and authors (deep search) | ✓ 9 sources with their ids, listed by the answer; 4 made-up knowledge base links removed from the analysis |
| I3 | no KB | Latest stable PostgreSQL and its date (classified PURE_SEARCH, before a2d384b78) | ✗ no tool in 5 iterations, answer shown with the "no source searched" warning, made-up links removed |
| I4 | no KB | the same, after a2d384b78 | ✓ QA with searchRequested=true, searchWeb in iteration 1, 4 documents; "18.6, 11 August 2026" from endoflife.date, the link returned by the search, Found docs = that page |
| I5 | no KB | Java 25 news, citing the pages | ✓ SUMMARY with searchRequested=true, searched at once, 6 documents, the answer rests on 3 |
| I6 | KB | Find the documents about the twelve senses | ✓ SUMMARY with searchRequested=true, 8 documents, all listed |

**Found during the session:** a closing given as two separate rules (the ids line, then the control marker) made gpt-4.1 drop both, 3 times out of 3; one ordered closing rule fixed it. PURE_SEARCH requests searched once out of five: the deliverable ("the found documents are the deliverable") led the model to list pages from memory; removing it and asking a plain yes/no (searchRequested) made the model search every time in this session.

**Outside this repository:** Gebo.ai.pro's office query rewriting prompt still mentions PURE_SEARCH (read as QA by the parsing), and the generated API clients of Enterprise.Gebo.ai, gebo.unooffice.plugin and rassegna.gebo.ai still list it: to be regenerated there. The generated clients of this repository do not carry searchRequested yet (regeneration).

## 15. Tenth session: the loop goes on from its last outcome (2026-10-06, log time 12:40 - 13:14)

The single-agent loop now remembers as the report writer does: the story of the previous iterations keeps the texts shown to the user (part of the answer), the last discarded draft whole with why it was discarded, older drafts only by their tool calls, and the documents the tools returned so far with their ids. Only the `gebo.ai` container redeployed; gpt-4.1.

| # | Chat | Request | Tools | Outcome |
|---|---|---|---|---|
| M1 (= A2) | KB | Archiati's twelve senses vs Blavatsky's seven principles, first build | iteration 1: 1 deep KB (8 fragments, 5 documents), coverage thin, discarded; iterations 2-5: no tool | ✗ the evidence gate, kept after the coverage discard, discarded 4 rewrites of a draft that rested on the first search: ~70 s lost, fixed (below) |
| M2 (= A2) | KB | the same, after the fix | 1 deep KB (7 fragments, 4 documents, coverage thin) + the two documents read whole (67k and 177k tokens) in the same iteration | ✓ the model completed the coverage itself, no discard; grounded on both authors, Found docs = the 2 documents the answer lists; 2 min 26 s, of which ~1 min 50 s the final model call over ~250k tokens |
| M3 (= A1) | KB | Christ across the works and authors of the knowledge base | 1 deep KB (31 fragments, 8 documents) | ✓ 8 of 8 documents used, coverage not thin, 3 made-up knowledge base addresses removed from the analysis; 78 s (earlier sessions: 1 source, centred on Steiner) |
| M4 (= N1) | no KB | Spring AI vs LangChain4j for enterprise RAG, with sources | 1 deep web (5 searches, 24 candidates from 18 hosts loaded in 1.5 s, 5 not loaded with their reasons) | ✓ the 72-fragment batch (64k tokens) analysed in 80 s, no runaway; the answer rests on 6 of the 19 pages read, no address removed; 1 min 44 s |

**Found and fixed:** the evidence gate asks an answer needing the sources to search them, and the loop applies it until the request has searched once. Keeping it after a coverage discard asked the next iteration for new evidence although the request had searched: the model, now seeing its draft, rewrote it without a tool, and each rewrite was discarded up to the last iteration. After a coverage discard the next iteration needs no new evidence; an answer without a search is still discarded when it cites documents this request did not read (the chat's documents and every document the tools returned or listed count as read). The coverage retry stays once per request.

**Observed, no change:** reading two whole documents put ~250k tokens in one model call (within the room computed for gpt-4.1's context); the final call took ~1 min 50 s.

## 16. Full retest on a clean rebuild (2026-10-06, log time 14:31 - 17:51)

Clean rebuild of the whole reactor (189 modules), only the `gebo.ai` container redeployed, TRACE on the agents, the tools, the deep searches and the pipeline steps, DEBUG on the retrieval layer. Every question of this session's series rerun (52 turns in 4 chats: 34 on the `biblioteca-esoterica` knowledge base, 18 without knowledge base), each turn's whole log saved, every quotation of the answers checked against what the tools returned. gpt-4.1, service model gpt-4o-mini.

| Round | Code | OK | Partial | Failed | Quotations verified |
|---|---|---|---|---|---|
| 1 (stopped at K10) | `37fc9d388` + the loop memory | 9 | 1 | 0 | - |
| 2 | + `d9547f899` (retrieval merge) | 42 | 4 | 6 | 145 / 145 |
| 3 | + `299408e89` (gates, ids, guards) | 46 | 3 | 3 | 204 / 204 |

**Found and fixed:**
- **Retrieval merge** (`d9547f899`): `AIDocumentsSet.join` compared fragments by their code, the code of their document, so a document found by two legs (semantic probes, full text, graph) kept only one leg's fragments. The Pistis Sophia deep search read 3 fragments of it; after the fix 27, and the answer has the six-book structure it missed. Fragments are told apart by their chunk id, documents grouped by their code, everywhere (joins, the multi-hop merge, the vector and full-text grouping).
- **Loop memory** (R1, a regression of `ca7c59222`): a draft discarded for resting on no source was given to build on, and gpt-4.1 rewrote it 4 times instead of searching (K32, W11). Now only why it was discarded is told; a coverage discard keeps the draft whole. K32 and W11 pass.
- **Requests bound to the sources** (F4): "Using the knowledge base...", "What does the knowledge base say..." were classified searchRequested=false and answered without any search (K14, K25). Examples in the prompt were not enough for gpt-4o-mini; a reminder next to the question was. K14 now searches and finds only the Pythagorean triangle as a symbol.
- **Coverage for every deliverable and iteration** (P2, P3, O1): the coverage gate is built whenever deep searches are mounted and carried after a continue; the redo is once per request, and a coverage no search completed is told with the answer (K21, W12: "The sources were covered in part").
- **Document ids** (F1, F1b): gpt-4.1 lists the knowledge base uniqueIds in place of the request's ids once the chat showed them; a uniqueId now names its document, an id that is both names the one the answer cites (K17: 7 of 7 documents in Found docs, 4 before).
- **Guards**: an address ends at full-width punctuation (a real link was removed, W08); a file name inside an address or cited with its words spaced is no document not read (W12, K14).
- **Notifications**: the search tools tell the user what they read in fragments of documents.

**Still open:**
- K25: in a chat whose history already holds the answer, gpt-4.1 does not search again even when told to, 5 iterations out of 5 (the answer is shown with the "no source searched" warning). Forcing the tool call was tried and reverted (`03c1be81e`).
- W13: with F4, asking a chat without knowledge base about its documents costs 5 iterations (the evidence gate retries to the last iteration, as decided); W24: the model then searched the web and presented the pages as "the knowledge base documents".
- searchRequested=never is output by gpt-4o-mini for English ("Without searching anything") but not for Italian ("Senza cercare nulla").
- The closing (ANSWER-DOCUMENTS and control marker) is missing in 7 to 10 answers of 52; the documents are then the ones the answer cites. The ids sometimes appear inline in the answer ("(#3, #1)").
- Wrong facts read from pages (W08: the site's "Last Published" date given as Maven 3.9.16's release date, 2026-04-13 in the history page; W03: JEP 507 missed).
