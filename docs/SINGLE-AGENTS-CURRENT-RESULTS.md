# Single agent networks: current test results

Outcomes of a live test session of the **single agent network** answering on the
**internal knowledge base only**, with the agent choosing its own tools
(`searchKnowledgeBase`, `deepSearchKnowledgeBase`). No deep search was chosen from
the chat menu: every search below is a tool call decided by the agent.

These are observations with their evidence, not decisions: nothing listed here has
been changed in the code yet.

---

## 1. Setup

| Item | Value |
|---|---|
| Date | 2026-10-04, 19:16 – 19:27 |
| Code | branch `feature/agent-tools-improvements` at `667a6a1ab` (local docker `gebo-monolith`, only the `gebo.ai` container rebuilt) |
| Network | `AGENTIC_LOOP_AGENTS_NETWORK` ("Chat with knowledge base", chip "Agentic chat") |
| Agent model | gpt-4.1 (1M context) |
| Service model (deep search partial analyses) | gpt-4o-mini |
| Ranker | Qwen3-Reranker-4B (regolo.ai) |
| Knowledge base | `biblioteca-esoterica`, project `testi-gnostici-e-antroposofici` |
| Full-text index | OpenSearch, index `kb_chunks` |
| Logging | DEBUG on the agents, tools, search and ranker; TRACE on the KB tool answer, the deep search analyses and the full-text queries |

Documents of the knowledge base seen during the session (no tool lists them, see 4.8):

- *The Secret Doctrine*, Vol. 1 of 4 — H. P. Blavatsky (English, Project Gutenberg)
- *Pistis Sophia svelato* — Samael Aun Weor (Italian)
- *I dodici sensi*, parte 1 — Pietro Archiati (Italian)
- *I grandi segni dei tempi* (1995) — Pietro Archiati (Italian)
- *La Scienza Occulta* — Rudolf Steiner (Italian)
- *Il Quinto Vangelo* (O.O. 148, conferenze non presenti nel libro) — Rudolf Steiner (Italian)
- *Frammenti di un insegnamento sconosciuto* — P. D. Ouspensky (Italian)

---

## 2. Turns

24 turns in one chat. **search** = `searchKnowledgeBase`, **deep** = `deepSearchKnowledgeBase`.
✓ = correct and grounded, ◐ = partial, ✗ = not answered or wrong.

| # | Question (abridged) | Tool(s) | Retrieval (from the logs) | Outcome |
|---|---|---|---|---|
| 1 | Which documents does the KB contain? | search | topK 15, 30 asked, 15 retrieved, 15 kept, lexical 0 | ✗ answers "only one document" |
| 2 | Archiati: the twelve senses (IT) | search | topK 10, 28 retrieved, 10 kept | ✓ complete, grounded |
| 3 | Pistis Sophia: what, structure, characters — deep search asked (IT) | deep, exhaustive | 14 fragments (10 Pistis, 4 SD), lexical 10, 12.3 s, 4 discarded | ✓ |
| 4 | Adamas and the tyrants of the twelve aeons, exact quote (IT) | deep, focused | 7 fragments, 5 discarded, ~13 s | ✓ (quotes not checked against the PDF) |
| 5 | The three fundamental propositions of *The Secret Doctrine* | search | topK 5, lexical query = the whole question, 0 hits | ✗ not found |
| 6 | Compare Archiati (12 senses) and Blavatsky (7 principles) | deep, exhaustive | 15 fragments from 3 documents (5 Archiati), 11 discarded, 1 source kept | ◐ correct answer, Archiati source dropped, false "cites a document not read" warning |
| 7 | The thirteen aeons in Pistis Sophia (IT) | search | topK 5, lexical 1 | ◐ lists only aeons 5–13 |
| 8 | Fohat with one quotation | search | topK 5, lexical 0 | ✓ quotation with page |
| 9 | Report: light and darkness in Pistis Sophia and the Stanzas of Dzyan (IT) | deep, exhaustive | 12 fragments: 10 Pistis, **1 SD**, 18.2 s | ◐ the Dzyan half is barely grounded |
| 10 | The Stanzas on light and darkness, quote (EN) | search | topK 5 | ✓ Stanza III quoted with page |
| 11 | Archiati on the sense of the "I" (IT) | search | topK 5, 13 retrieved | ✓ long, real quotation |
| 12 | Archiati, *I grandi segni dei tempi* (IT) | search | topK 5 | ✓ |
| 13 | Svabhavat, with quotation | search | topK 5, lexical 0, 5 near-empty fragments kept | ✗ not found |
| 14 | The twelve saviours of the Treasury of Light (IT) | search | topK 5, lexical 3 | ✓ |
| 15 | Decision with a deep search: the best introduction for a beginner | **search** (deep asked) | topK 5 | ✗ decided on 5 fragments, compared 2 of 7 documents |
| 16 | Steiner: Saturn, Sun, Moon conditions (IT) | search | topK 5 | ✓ with quotation |
| 17 | Exhaustive deep search: Steiner's evolution vs Blavatsky's Rounds (IT) | deep ×2, exhaustive | 11 fragments from 4 documents, then 11 (10 SD), 15 s + 10 s | ✓ 4 sources |
| 18 | The Dhyan Chohans, quote | search | topK 5, lexical 0 | ✗ only a passing mention |
| 19 | Ahriman in Archiati and Steiner, quote (IT) | search | topK 8, 18 retrieved, 7 of 8 kept fragments near-empty | ✗ **quotation invented** (see 4.2), answered in English |
| 20 | Svabhavat, without naming the book | search | lexical query `Svabhavat`: 0 hits; same near-empty fragments | ✗ |
| 21 | Dhyan Chohans, without naming the book | search | lexical query `Dhyan Chohans`: 0 hits; same near-empty fragments | ✗ |
| 22 | Ouspensky: the centres and self-remembering (IT) | search | topK 5 | ✓ |
| 23 | Steiner, Fifth Gospel: the Baptism in the Jordan (IT) | search | topK 5, lexical 3 | ✗ only covers and publishing data found (said honestly) |
| 24 | Same, deep search asked (IT) | **search** (deep asked) | topK 5 | ✓ quotation verified in the retrieved text |

Totals: 12 ✓, 3 ◐, 9 ✗ (turns 20 and 21 counted separately). No error, no
hang, no timeout; every deep search took 10–18 s with a single batch and no
runaway analysis.

---

## 3. What works

- Every turn ran through `AGENTIC_LOOP_AGENTS_NETWORK`; the agent always searched
  before answering.
- The tools room: each KB search is sized on the room its model call leaves
  (about 220k tokens here) and the tool reports what it took.
- Ranking: the KB tool retrieves twice topK and the ranker keeps topK (e.g. 10 of 28,
  8 of 18) when the retrieval gives it something to choose from.
- Deep searches over the knowledge base: small batches (7–15 fragments), one
  partial analysis each, 10–18 s, no runaway, real fragment ids discarded.
- Quotations coming from real fragments are genuine: turn 24's quotation was
  checked against the text the tool returned (it spans two fragments).
- When the retrieval brings nothing useful the agent usually says so (turns 5, 13,
  18, 20, 21, 23) instead of answering from memory.

---

## 4. Findings

### 4.1 The KB search mostly returns near-empty "hub" fragments

**43 of the 88 fragments** returned by `searchKnowledgeBase` in the session had
**fewer than 150 characters of text** (the `META-` header lines excluded). Two
searches returned only such fragments (5 of 5 each) and three almost only (8 of 10,
4 of 5, 7 of 8).

They are page numbers, footnote and index references, title pages:

```
84feee52  'complete explanation of this fact?'
b128695f  '292b.\n394\n1. 302.'
0c46d032  '385\nxviii. 12.'
bedb6f9a  '547\nix. 1.'
6a9c0dde  'cit., pp. 366-8.'
b47ab14a  '6 The Secret Doctrine, Vol. 1 of 4\nLONDON, 1893.\n[001]'
28713bed  '***END OF THE PROJECT GUTENBERG EBOOK THE SECRET DOCTRINE, V…'
```

- The **same fragments come back for unrelated questions** (Svabhavat, Dhyan
  Chohans, Ahriman…), with or without the book's name in the question: they sit
  close to every query in the semantic index.
- The ranker scores them first: for "Svabhavat", `84feee52` ("complete explanation
  of this fact?") scored 0.733 and `6a9c0dde` ("cit., pp. 366-8.") 0.596; all five
  kept fragments were under 110 characters.
- Every chunk carries the `META-TITLE`/`META-SUBTITLE` header of its document, so a
  near-empty chunk is mostly the document's title.
- The same pattern shows on Steiner's *Quinto Vangelo* (turn 23: only covers and
  publishing data).

This explains turns 5, 13, 18, 19, 20, 21 and 23. Likely origin: the chunking of the
PDFs at ingestion (page and index lines become chunks of their own) together with
the metadata header — **not yet verified in the ingestion code**.

### 4.2 An invented quotation when the retrieval brings only noise

Turn 19 presented as a direct quotation from Archiati:

> «Le forze dell'ostacolo sono Lucifero, Arimane e Asura. Lucifero agisce soprattutto
> nel passato, Arimane nel presente e Asura nel futuro…»

The 8 fragments the tool returned (logged at TRACE) contain **no occurrence of
"Arimane"**: 7 were near-empty (4.1). The quotation is generated, not retrieved.

### 4.3 The KB search tool sends the whole question to the full-text index

`InternalKnowledgeBaseSearchToolSource` passes the agent's queries to the semantic
leg only; the lexical leg receives `List.of(param.getQuery())`, the whole question:

```java
documentsSearchService.getObject().search(param.getQuery(), semanticQueries, semanticFilter,
        List.of(param.getQuery()), fullTextFilter, param.getQuery(), retrievalTopK, retrievalTokens);
```

The full-text query is a `multi_match` with operator **AND**
(`OpenSearchFullTextChunkSearchService.buildMainQuery`): every word of the question
must be in one chunk. Traced query of turn 5:

```json
{"multi_match":{"fields":["content^4","document_title^2","meta.*^0.5"],"operator":"and",
 "query":"What are the three fundamental propositions of The Secret Doctrine? Please quote them."}}
```

Result: **0 lexical hits in 11 of the 14 KB tool searches**. The deep search tool
passes its own short queries and does get lexical hits (10, 3, 10 chunks in turns
3, 4, 17).

Side effect on ranking: the hybrid retrieval splits the request between the two legs,
so with an empty lexical leg the "retrieve twice topK then rank" step can receive
only topK fragments and has nothing to choose from (turn 1: 15 retrieved, 15 kept).

### 4.4 Accented words do not match in the full-text index

When the lexical query was a single term it still found nothing: `Svabhavat` → 0,
`Dhyan Chohans` → 0 (turns 20, 21). The text writes **Svâbhâvat** and **Dhyân
Chohans**: the index does not fold accents, so the unaccented spelling a user or a
model types never matches.

### 4.5 The language of the queries skews the retrieval

The agent writes its queries in the user's language. On a mixed Italian/English
knowledge base, Italian queries pull the Italian documents and starve the English
one: turn 9 (light and darkness in Pistis Sophia **and** the Stanzas of Dzyan)
analysed 10 Pistis Sophia fragments and 1 *Secret Doctrine* fragment. The same
subject asked in English (turn 10) found and quoted the stanza. Turn 19 also
answered in English an Italian question.

### 4.6 A deep search can drop a source it used

Turn 6: the partial analysis read 5 Archiati fragments and used them in the answer,
but listed them among the irrelevant ones (11 of 15 discarded); the sources kept
only *The Secret Doctrine*, and the answer showed the warning "cites a document not
read for this request: parte1.pdf".

### 4.7 Explicit deep search requests are followed half of the time

Deep search explicitly asked: turns 3, 15, 17, 24 → `deepSearchKnowledgeBase` was
called in 3 and 17, `searchKnowledgeBase` in 15 and 24. The agent also chose a deep
search on its own in turns 4, 6 and 9. The deep search tool's description says to
use it "only when the answer needs many documents (a report, an analysis, a
comparison, a decision), not for a fact a plain search finds"; turn 15 (a decision)
still got a plain search with topK 5.

### 4.8 The knowledge base cannot be listed

No tool lists the documents of a knowledge base: "which documents does it contain?"
is answered from whatever a similarity search returns. Turn 1 answered "only one
document" on a knowledge base holding at least seven; turn 15 compared two of them.

### 4.9 "Exhaustive" deep searches read few fragments

The exhaustive deep searches analysed 11–15 fragments each (a few per document),
whatever the size of the knowledge base.

---

## 5. Open points (decisions to take)

| Point | Options to evaluate |
|---|---|
| 4.1 near-empty chunks | find their origin in the PDF ingestion and chunking; whether such chunks should be indexed at all; the weight of the metadata header in the embedded and ranked text |
| 4.2 invented quotations | how the agent prompt treats quotations when the retrieved text does not contain them |
| 4.3 lexical leg of the KB tool | send the agent's queries to the full-text leg as the deep search tool does; the AND operator of the full-text query |
| 4.4 accents | accent folding in the `kb_chunks` analyzer (needs a re-index) |
| 4.5 languages | queries in the documents' languages as well as the user's |
| 4.6 dropped sources | how the irrelevant fragments of an analysis affect its sources |
| 4.7, 4.8 | the deep search tool description; a tool listing a knowledge base's documents |

---

## 6. How the evidence was collected

- Each turn's server log lines (tool calls, retrieval legs, ranking, deep search
  batches) were read from `/opt/gebo.ai/logs/ai.gebo.monolithic.app.log` in the
  `gebo.ai` container, by the turn's start time.
- The text the KB tool returned to the agent is logged at TRACE between
  `<KNOWLEDGE_BASE_TOOL_ANSWER>` tags; the fragment lengths (4.1) and the quotation
  checks (4.2, turn 24) were computed on it.
- The full-text queries are logged at TRACE between `<FULLTEXT_QUERY>` tags
  (`OpenSearchFullTextChunkSearchService`), the ranker's scores at DEBUG by
  `GeboStandardRankerClient`.
