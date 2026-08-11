/**
 * Gebo AI Assistant - ONLYOFFICE plugin.
 *
 * Two modes, both built directly on the generated brain-ai-js-client stub
 * (window.BrainClient, bundled from gebo.js-plugins/brain/brain-ai-js-client
 * - see vendor/README.md):
 *
 *  - "This document" (in document context): read the current selection via
 *    the editor's own plugin API, semantically search the knowledge base for
 *    related content, or ask the model to elaborate/rewrite the selection
 *    and paste the result back in.
 *  - "Knowledge base" (out of document context): an ordinary RAG chat
 *    against the document-sharing system, independent of what's open in the
 *    editor. Routed through GeboChatPipelinesController.executeDefaultChatPipeline
 *    (not the simpler GeboChatController.ragChat), matching how
 *    gebo-ai-reusable-chat.component.ts's callReactiveChat() actually behaves:
 *    it calls streamAgenticChat (-> streamChatPipeline) for ragsystem chat,
 *    with streamRagChat (-> streamRagResponse) left commented out/unused.
 *
 * Uses the non-streaming chat/executeDefaultChatPipeline/semanticSearch
 * methods rather than their stream* counterparts: the generated stub's
 * ApiClient buffers the whole superagent response before resolving (it
 * doesn't do incremental SSE chunk parsing the way the Angular reference
 * control's reactive-chat.service.ts does with a hand-rolled fetch+
 * ReadableStream reader), so streaming endpoints here would just mean
 * waiting for the same full response with extra "data:"-line parsing to
 * strip. Non-streaming is the simpler, equally-correct choice given that
 * constraint - the tradeoff is no incremental token-by-token display.
 */

var state = {
  apiClient: null,
  profilesApi: null,
  pipelinesApi: null,
  ragChatApi: null,
  directChatApi: null,
  searchApi: null,
  currentProfileCode: null,
  currentKnowledgeBaseCodes: [],
  userChatContextCode: null
};

function $(id) {
  return document.getElementById(id);
}

function setStatus(msg, isError) {
  var el = $("status");
  el.textContent = msg || "";
  el.className = isError ? "status error" : "status";
}

function setBusy(busy) {
  document.body.classList.toggle("busy", !!busy);
}

/** Renders text safely (no HTML injection) into a container. */
function renderText(container, text) {
  container.textContent = text || "";
}

function switchTab(tab) {
  ["doc", "kb"].forEach(function (t) {
    $("tab-" + t).classList.toggle("active", t === tab);
    $("panel-" + t).classList.toggle("active", t === tab);
  });
}

async function initClients() {
  var params = new URLSearchParams(window.location.search);
  var baseUrl = params.get("geboBaseUrl") || "http://localhost:13001/brain";

  var token = await window.GeboAuthBridge.getAccessToken();

  var ApiClient = window.BrainClient.ApiClient;
  state.apiClient = new ApiClient();
  state.apiClient.basePath = baseUrl.replace(/\/+$/, "");
  state.apiClient.defaultHeaders["Authorization"] = "Bearer " + token;
  // Gebo's shared security architecture (gebo.architecture.security, used by every
  // Gebo service including this one) dispatches Authorization: Bearer tokens by the
  // X-AuthType header, defaulting to LOCAL_JWT (Gebo's own HMAC token) when absent -
  // a Keycloak-issued bearer token without this header 401s. Verified live against
  // the monolith configured as an OAuth2 resource server: see README.md.
  state.apiClient.defaultHeaders["X-AuthType"] = "OAUTH2";

  state.profilesApi = new window.BrainClient.GeboChatProfileLookupControllerApi(state.apiClient);
  state.pipelinesApi = new window.BrainClient.GeboChatPipelinesControllerApi(state.apiClient);
  state.ragChatApi = new window.BrainClient.GeboRagChatControllerApi(state.apiClient);
  state.directChatApi = new window.BrainClient.GeboChatControllerApi(state.apiClient);
  state.searchApi = new window.BrainClient.GeboUserKnowledgeBaseSemanticSearchControllerApi(state.apiClient);
}

async function loadProfiles() {
  var page = await state.profilesApi.getAllChatProfileConfigurationLoookup({ page: 0, pageSize: 50 });
  var entries = (page && page.content) || [];
  var select = $("profileSelect");
  select.innerHTML = "";
  entries.forEach(function (entry) {
    var opt = document.createElement("option");
    opt.value = entry.code;
    opt.textContent = entry.description || entry.code;
    select.appendChild(opt);
  });
  if (entries.length > 0) {
    state.currentProfileCode = entries[0].code;
    await loadKnowledgeBases(state.currentProfileCode);
  } else {
    setStatus("No chat profiles visible to this user.", true);
  }
}

async function loadKnowledgeBases(profileCode) {
  var kbs = await state.ragChatApi.getVisibleKnowledgeBasesByProfileCode(profileCode);
  var list = Array.isArray(kbs) ? kbs : (kbs ? [kbs] : []);
  state.currentKnowledgeBaseCodes = list
    .map(function (kb) { return typeof kb === "string" ? kb : (kb && (kb.code || kb.knowledgeBaseCode)); })
    .filter(Boolean);
}

function onProfileChange() {
  state.currentProfileCode = $("profileSelect").value;
  state.userChatContextCode = null;
  $("kbMessages").innerHTML = "";
  loadKnowledgeBases(state.currentProfileCode).catch(function (e) {
    setStatus(e.message || String(e), true);
  });
}

/** Pulls the current selection out of the document via the editor's own plugin API. */
function getSelectedText() {
  return new Promise(function (resolve) {
    window.Asc.plugin.executeMethod(
      "GetSelectedText",
      [{ Numbering: false, Math: false, TableCellSeparator: "\n", ParaSeparator: "\n", TabSymbol: String.fromCharCode(9) }],
      function (data) {
        resolve(data || "");
      }
    );
  });
}

function pasteIntoDocument(text) {
  window.Asc.plugin.executeMethod("PasteText", [text]);
}

async function onUseSelection() {
  setBusy(true);
  setStatus("Reading selection from the document...");
  try {
    var text = await getSelectedText();
    $("docText").value = text;
    setStatus(text ? "" : "Nothing is selected in the document - select some text first.", !text);
  } catch (e) {
    setStatus(e.message || String(e), true);
  } finally {
    setBusy(false);
  }
}

async function onSearchRelated() {
  var text = $("docText").value.trim();
  if (!text) {
    setStatus("Use “Read selection” first, or type text to search for.", true);
    return;
  }
  if (state.currentKnowledgeBaseCodes.length === 0) {
    setStatus("The selected chat profile has no visible knowledge bases to search.", true);
    return;
  }
  setBusy(true);
  setStatus("Searching related content...");
  try {
    var body = new window.BrainClient.SemanticQueryParam(text, state.currentKnowledgeBaseCodes);
    body.topK = 5;
    var results = await state.searchApi.semanticSearch(body);
    renderSearchResults(results);
    setStatus("");
  } catch (e) {
    setStatus(e.message || String(e), true);
  } finally {
    setBusy(false);
  }
}

function renderSearchResults(results) {
  var container = $("searchResults");
  container.innerHTML = "";
  var items = Array.isArray(results) ? results : (results && results.content) || [];
  if (items.length === 0) {
    var empty = document.createElement("div");
    empty.className = "muted";
    empty.textContent = "No related content found.";
    container.appendChild(empty);
    return;
  }
  items.forEach(function (item) {
    var row = document.createElement("div");
    row.className = "result-row";
    var title = document.createElement("div");
    title.className = "result-title";
    renderText(title, item.name || item.documentCode || "Untitled document");
    var meta = document.createElement("div");
    meta.className = "result-meta";
    renderText(meta, [item.knowledgeBaseCode, item.contentType].filter(Boolean).join(" · "));
    row.appendChild(title);
    row.appendChild(meta);
    container.appendChild(row);
  });
}

async function onElaborate() {
  var text = $("docText").value.trim();
  var instructions = $("instructions").value.trim();
  if (!text) {
    setStatus("Use “Read selection” first, or type text to elaborate.", true);
    return;
  }
  if (!state.currentProfileCode) {
    setStatus("No chat profile selected.", true);
    return;
  }
  setBusy(true);
  setStatus("Elaborating...");
  $("elaborateResult").classList.remove("visible");
  try {
    var query = (instructions ? instructions + ":\n\n" : "Elaborate on the following text:\n\n") + text;
    var request = {
      id: cryptoRandomId(),
      chatProfileCode: state.currentProfileCode,
      query: query,
      streamResponse: false
    };
    var response = await state.directChatApi.chat(request);
    renderText($("elaborateResult"), response.queryResponse || "");
    $("elaborateResult").classList.add("visible");
    setStatus("");
  } catch (e) {
    setStatus(e.message || String(e), true);
  } finally {
    setBusy(false);
  }
}

function onInsertElaboration() {
  var text = $("elaborateResult").textContent;
  if (text) {
    pasteIntoDocument(text);
    setStatus("Inserted into the document.");
  }
}

function appendKbMessage(role, text) {
  var container = $("kbMessages");
  var row = document.createElement("div");
  row.className = "message " + role;
  var label = document.createElement("div");
  label.className = "message-role";
  label.textContent = role === "user" ? "You" : "Gebo AI";
  var body = document.createElement("div");
  body.className = "message-body";
  renderText(body, text);
  row.appendChild(label);
  row.appendChild(body);
  container.appendChild(row);
  container.scrollTop = container.scrollHeight;
  return body;
}

async function onSendKbMessage() {
  var input = $("kbInput");
  var text = input.value.trim();
  if (!text || !state.currentProfileCode) return;
  input.value = "";
  appendKbMessage("user", text);
  var responseBody = appendKbMessage("assistant", "...");
  setBusy(true);
  try {
    var request = {
      id: cryptoRandomId(),
      chatProfileCode: state.currentProfileCode,
      userChatContextCode: state.userChatContextCode,
      query: text,
      streamResponse: false
    };
    // GeboChatPipelinesController/executeDefaultChatPipeline, not the simpler
    // GeboChatController/ragChat - matches gebo-ai-reusable-chat.component.ts's
    // callReactiveChat(), which routes ragsystem chat through streamChatPipeline
    // (ragChat/streamRagResponse is explicitly left unused there in favor of it).
    var pipelineBody = { request: request };
    var response = await state.pipelinesApi.executeDefaultChatPipeline(pipelineBody);
    state.userChatContextCode = response.userChatContextCode || state.userChatContextCode;
    renderText(responseBody, response.queryResponse || "(empty response)");
    setStatus("");
  } catch (e) {
    renderText(responseBody, "Error: " + (e.message || String(e)));
    setStatus(e.message || String(e), true);
  } finally {
    setBusy(false);
  }
}

function onNewKbChat() {
  state.userChatContextCode = null;
  $("kbMessages").innerHTML = "";
}

function cryptoRandomId() {
  if (window.crypto && window.crypto.randomUUID) return window.crypto.randomUUID();
  return "id-" + Date.now() + "-" + Math.random().toString(16).slice(2);
}

async function main() {
  $("tab-doc").addEventListener("click", function () { switchTab("doc"); });
  $("tab-kb").addEventListener("click", function () { switchTab("kb"); });
  $("profileSelect").addEventListener("change", onProfileChange);
  $("useSelectionBtn").addEventListener("click", onUseSelection);
  $("searchBtn").addEventListener("click", onSearchRelated);
  $("elaborateBtn").addEventListener("click", onElaborate);
  $("insertBtn").addEventListener("click", onInsertElaboration);
  $("kbSendBtn").addEventListener("click", onSendKbMessage);
  $("kbInput").addEventListener("keydown", function (e) {
    if (e.key === "Enter" && !e.shiftKey) {
      e.preventDefault();
      onSendKbMessage();
    }
  });
  $("kbNewChatBtn").addEventListener("click", onNewKbChat);

  setBusy(true);
  setStatus("Connecting...");
  try {
    await initClients();
    await loadProfiles();
    setStatus("");
  } catch (e) {
    setStatus(e.message || String(e), true);
  } finally {
    setBusy(false);
  }
}

window.Asc.plugin.init = function () {
  main();
};
