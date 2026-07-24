---
name: document-gebo-ai-ui-manual
description: Build or extend the screenshot-driven HTML user/admin manual of the live Gebo.ai app (currently at /home/zava/Documents/Gebo.ai/Manual-<date>/index.html) by driving the app in a real browser via claude-in-chrome, capturing screenshots of each screen/flow, and writing them up as numbered "Part" sections. Use when asked to "document" a section of the app, "write the guide", "add a part to the manual", "screenshot the X screen and explain it", or to flesh out per-option/per-provider behavior (e.g. every entry in a dropdown) that a single screenshot can't show.
user-invocable: true
allowed-tools:
  - Read
  - Write
  - Edit
  - Glob
  - Grep
  - Bash
---

# /document-gebo-ai-ui-manual — Extend the screenshot walkthrough manual

Produces/extends a single self-contained HTML file (no build step, no external
assets besides the screenshots themselves) that walks a reader through the live
Gebo.ai UI, screen by screen, with real screenshots and prose explaining what
each screen does and why. It's written by *operating the actual running app*
in a browser, not by reading source code — the whole point is to document what
a user actually sees, including bugs and quirks source-reading alone won't
surface.

## 0. Locate (or start) the manual

- The manual lives outside this git repo, under
  `/home/zava/Documents/Gebo.ai/Manual-<capture-date>/index.html`, with screenshots
  in a sibling `screenshots/` folder. Check for an existing manual directory
  before creating a new one — extend the current one (adding a new `Part`, or
  filling in an existing thin section) rather than starting over, unless the
  user asks for a fresh capture.
- The app must already be running and reachable (typically
  `http://localhost:12999/`) and you must be logged in as admin. If it isn't
  up, use the `gebo-ai-local-install` skill first — don't start documenting
  screens you haven't actually loaded.

## 1. Connect the browser

Use the claude-in-chrome tools (`tabs_context_mcp` → `navigate`/`tabs_create_mcp`
→ `computer`/`find`/`read_page`). Gotchas learned the hard way in this codebase:

- **Wrong/no browser connected.** `list_connected_browsers` may return a
  browser on a different machine (`isLocal:false`) or none. If curl succeeds
  against the app but the browser tool errors loading the same URL, that's the
  tell. Ask the user to connect a local Chrome instance, then `select_browser`
  the new `isLocal:true` entry — always present the choice via
  `AskUserQuestion` rather than silently picking one, per the tool's own rule.
- **Screenshot resolution is capped at ~1.23 megapixels** regardless of the
  requested window size — asking for a huge window doesn't get you a huge
  screenshot. `resize_window` to `2350x1650` before capturing; the actual image
  comes back around `1395x882`, and that's the ceiling — don't chase a bigger
  one.
- **CDP screenshot timeouts** (`Page.captureScreenshot timed out after
  30000ms`) happen intermittently. Just retry the same screenshot call — it
  usually succeeds on the next attempt once you confirm the tab is still alive
  via `tabs_context_mcp`.
- **PrimeNG click flakiness.** Two independent failure modes look identical
  (a click appears to do nothing):
  1. A `p-blockUI`/overlay mask mid-leave-transition (class
     `p-overlay-mask-leave-active`) can sit at `display:flex` and absorb
     clicks for 1-2 seconds after a request completes, even though it isn't
     actually stuck — it clears on its own. If a click seems to do nothing,
     wait ~2s and retry before concluding the app is broken.
  2. Coordinate-based clicks can land on the wrong element after a re-render
     (tree rows shifting, a stale screenshot). Prefer `find` to get a fresh
     `ref` and click by ref over hardcoded coordinates, especially right after
     any action that could re-render the page.
  - If a tree/list looks empty and won't expand, the underlying REST call
    still returns `200` with a genuinely empty array in some cases — check
    `read_network_requests` for the actual endpoint/response before assuming a
    UI bug. If a section needs data that doesn't exist yet (an empty
    knowledge base, no projects), create minimal throwaway data to reach the
    screen rather than giving up — but say so in the writeup and offer to
    clean it up afterward (don't silently leave test data behind in a shared
    install).

## 2. Capture screenshots

- Number screenshots sequentially with a 2-digit prefix and a kebab-case
  description of the screen, e.g. `09-setup-sso-oauth2-provider-dropdown.jpg`.
  Continue the existing numbering when extending a manual — don't restart at
  01 or renumber existing files.
- When a UI element changes shape based on a selection (a dropdown, a radio
  group, a wizard step selector) and the different states aren't visually
  obvious from one screenshot, **capture one screenshot per option**, not just
  the closed dropdown. This is the difference between "documented" and
  "completely documented" — a reader can't infer what the AWS Cognito fields
  look like from a screenshot of the Google fields.
- Save screenshots as `.jpg` into the manual's `screenshots/` folder. Don't
  upload screenshots to any external service to crop/edit them.

## 3. Write it up

Follow the existing HTML structure exactly — read the current `index.html`'s
`<style>` block and an existing `Part` section before writing, to match
conventions rather than re-deriving them:

- One `<section class="part" id="partN">` per logical area, with a
  `.part-head` (`<span class="num">Part N</span><h2>Title</h2>`) and a short
  `.intro` paragraph.
- One `.shot` div per screenshot: an `<h3>` title, a `.cap` paragraph
  explaining what's on screen and why it matters, the `<img>` itself
  (`screenshots/NN-....jpg`), and an optional `.note` callout —
  `.note.info` (context), `.note.ok` (confirms something works),
  `.note.warn` (caution/security note), `.note.bug` (a real defect found while
  documenting, red-bordered). Wrap 2+ related screenshots in a `.pair` div to
  show them side by side (it flex-wraps on its own).
- When documenting per-option behavior (a dropdown's provider list, a
  wizard's branches), give each option its own `.shot`, briefly explaining
  what that option is for and any field that's unique to it — not just "here
  are the other options" as a single closed-dropdown screenshot.
- Update the `nav.toc` list if you added a new top-level Part. Only touch the
  `<header class="hero"> .meta` capture line for a materially new capture pass
  (new app version, new date) — don't rewrite it for a small addition to an
  existing Part.
- If you find a real bug while operating the app (not a documentation gap),
  flag it to the user in your response — don't just quietly note it in a
  `.note.bug` and move on. That's exactly how the AWS S3 icon-sizing and
  BlockUI-lockup bugs in this codebase were originally found: while
  documenting, not while looking for bugs.

## Report

Summarize: which Part(s)/screens were added or extended, how many new
screenshots, and any real product bug spotted while driving the app (called
out separately from documentation gaps).
