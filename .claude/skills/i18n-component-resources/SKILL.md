---
name: i18n-component-resources
description: Add or refresh the multilanguage resources of an Angular component in gebo.ui/projects/gebo-ai-reusable-ui/src/assets/i18n/en.json (and optionally a translated <lang>.json), by reading the component's template/providers and deriving the module/entity/component/field keys the gebo.multilanguage.support system expects. Use when asked to "make a component multilanguage", "add the i18n resources for X", "translate component X", "update en.json for X", or when a new control's labels/tooltips are still hardcoded English on screen.
user-invocable: true
allowed-tools:
  - Read
  - Edit
  - Write
  - Glob
  - Grep
  - Bash
  - PowerShell
---

# /i18n-component-resources — Wire an Angular component into the Gebo multilanguage system

Produces the `en.json` block (and, on request, translated `<lang>.json` blocks) for one
Angular component of `gebo-ai-reusable-ui` — or of any app that consumes the library.

`en.json` is not a translation file. It is the **source-of-truth catalogue of English UI
text**: at runtime the recorder writes what it sees into Mongo, and the backend merges
that with this bundled file. Editing it by hand is how a component gets into the
catalogue without having to run the recorder.

---

## 1. The key shape — four levels, no more, no less

`gebo.architecture.parent/gebo.multilanguage.support` stores one row per text with
`moduleId / entityId / componentId / fieldId`, and
`UIExistingTextService.getCurrentLanguageResources()` nests them in exactly that order:

```json
{
  "<moduleId>": {
    "<entityId>": {
      "<componentId>": {
        "<fieldId>": "The English text"
      }
    }
  }
}
```

`extractUIExistingText()` (`field-translation-container/text-language-resources.ts`)
returns `undefined` unless **all four** resolve. A block with a wrong module or entity
name is not a partial match — it is simply never found, silently.

### Where each level comes from

| Level | Source | How to read it |
|---|---|---|
| `moduleId` | `GEBO_AI_MODULE` provider | `providers: [{ provide: GEBO_AI_MODULE, useValue: "GeboAIFooModule" }]` on the component, else on its NgModule |
| `entityId` | `GEBO_AI_FIELD_HOST` provider | `useValue: fieldHostComponentName("GeboAIFooComponent")` |
| `componentId` | the element's `id` attribute in the template | `<span id="SaveHint" …>` → `SaveHint` |
| `fieldId` | which attribute the directive read | see the table in §2 |

Both providers can sit on the component or on the NgModule; the component wins. Grep for
them before writing anything:

```bash
grep -n "GEBO_AI_MODULE\|GEBO_AI_FIELD_HOST\|fieldHostComponentName" \
  <component>.component.ts <component>.module.ts
```

**`GEBO_AI_FIELD_HOST` must use `useValue`, never `useExisting`.** `fieldHostComponentName()`
returns an *instance*, and `useExisting` treats its value as a token to alias — Angular
then throws `NullInjectorError` resolving that object, and `@Optional()` on the directive's
parameter does not cover the inner lookup. Every working control in the library uses
`useValue`; if you find `useExisting` here, that is the bug, fix it first or nothing you
add to `en.json` will ever resolve.

---

## 2. What the directives read

Everything is opt-in per element: **no `id`, no resource.** Two directives cover the
template cases.

### `[gebo-ai-text]` — element body

Reads `innerText` and replaces it after lookup. One field only:

| Template | fieldId |
|---|---|
| `<span id="Foo" gebo-ai-text>Some text</span>` | `label` |

### `[gebo-ai-label]` — element attributes

Reads up to three attributes, aliased onto three field ids:

| Attribute on the element | fieldId |
|---|---|
| `label`, `legend`, `header` | `label` |
| `title` | `help` |
| `placeholder` | `placeholder` |

`label`/`legend`/`header` collapse onto the same `label` field, first one wins. Works on a
native element *and* on a PrimeNG component — for the latter the matching
`P*LabelTarget` adapter in `primeng-components-multilanguage-adapters.directive.ts` pushes
the translation into the component's own input, so check an adapter exists for the tag you
are labelling.

### Programmatic text — not template-driven

`GeboAITranslationService` has helpers for text that never appears as an attribute. They
take module/entity explicitly, and the `componentId` is the object's `id`:

| Helper | fieldIds it produces |
|---|---|
| `translateMessage(mod, ent, id, ToastMessageOptions)` | `summary`, `detail` |
| `translateConfirmation(mod, ent, ExtendedConfirmation)` | `message`, `header`, `acceptLabel`, `rejectLabel` |
| `translateMenuItems` / `translateMegaMenuItems(mod, ent, items[])` | `label`, `help`, `tooltip` |
| `translateBackendMessage(s)(GUserMessage)` | `summary`, `detail`, always under module `BackendMSGSModule` / entity `GUserMessage` |

If the component raises toasts or confirmations through these, their ids belong in the
same `en.json` block as the template ids.

---

## 3. Procedure

1. **Read the template** and list every element carrying `gebo-ai-text` or `gebo-ai-label`,
   with its `id` and the attributes present. Also grep the `.ts` for
   `translateMessage|translateConfirmation|translateMenuItems|translateBackendMessage`.
2. **Resolve `moduleId` / `entityId`** from the providers (§1).
3. **Report anything untranslatable before editing** — a visible static string with no
   `id`/directive produces no resource. Either add `id="…" gebo-ai-text` to it (preferred,
   it is a one-line template change) or say explicitly that it stays English. Do not
   silently invent a resource for an element that cannot look it up.
4. **Append the block to `en.json`** (§4). Text values must match the template's current
   English *exactly*, including the ellipsis character (`…`, U+2026) where the template
   uses one — a mismatch does not break lookup (that is by id) but it does corrupt the
   catalogue the translators work from.
5. **Verify**, then optionally add translated languages (§5).

### Skip these

Interpolated or bound content (`{{ error() }}`, `[innerHTML]`, backend-supplied
descriptions). Those are data, not UI chrome, and have no stable id.

---

## 4. Editing `en.json` safely

Path: `gebo.ui/projects/gebo-ai-reusable-ui/src/assets/i18n/en.json`
(shipped to the backend as `/static/assets/i18n/en.json`, loaded by
`gebo.ui/src/main/java/ai/gebo/ui/ExistingBundledTextResourcesDaoImpl.java`).

Its own formatting: **4-space indent, LF, no trailing newline**, top-level keys in
insertion order. Other `<lang>.json` files use a *different* style — Jackson's
`"key" : value` with 2-space indent — because they come out of the translation tool.
**Match whatever the file you are editing already uses.**

Never hand-edit with a text splice; round-trip it so the JSON cannot end up malformed,
and keep the diff append-only:

```bash
cd gebo.ui/projects/gebo-ai-reusable-ui/src/assets/i18n && python - <<'PY'
import json, io, collections
p = 'en.json'
d = json.load(io.open(p, encoding='utf-8'), object_pairs_hook=collections.OrderedDict)
d.setdefault('GeboAIFooModule', collections.OrderedDict())['GeboAIFooComponent'] = \
    collections.OrderedDict([
        ('SaveBtn',  collections.OrderedDict([('label', 'Save'), ('help', 'Save the changes')])),
        ('SaveHint', collections.OrderedDict([('label', 'Nothing to save yet')])),
    ])
with io.open(p, 'w', encoding='utf-8', newline='\n') as f:
    json.dump(d, f, indent=4, ensure_ascii=False); f.write('\n')
PY
```

`setdefault` matters: several components share one module (all of
`GeboAIChatControlModule`'s controls live under one key), so adding an entity must not
replace a module that already exists.

**Line endings.** These files are stored LF but checked out CRLF. Writing CRLF makes git
show the *whole file* as rewritten. Always write `newline='\n'`, then confirm the diff is
only your addition:

```bash
git diff --stat -- gebo.ui/projects/gebo-ai-reusable-ui/src/assets/i18n/en.json
```

A few dozen insertions and **zero deletions** is right. Thousands of both means the line
endings flipped — normalise with `raw.replace(b'\r\n', b'\n')` and re-check.

---

## 5. Adding a translated language

Same block, same keys, translated values, in that file's own formatting. Render it with
the file's separator style rather than reformatting the whole file:

```python
json.dumps({name: body}, indent=2, separators=(',', ' : '), ensure_ascii=False)
```

Then assert key parity against `en.json` — a missing key falls back to the English source
text with no warning:

```python
def keys(d, pfx=''):
    out = set()
    for k, v in d.items():
        out |= keys(v, pfx + '/' + k) if isinstance(v, dict) else {pfx + '/' + k}
    return out
print(keys(en[mod]) - keys(it[mod]))   # must be empty
```

---

## 6. Verify

```bash
cd gebo.ui
python -c "import json,io;d=json.load(io.open('projects/gebo-ai-reusable-ui/src/assets/i18n/en.json',encoding='utf-8'));print(len(d),'modules')"
npx ng build gebo-ai-reusable-ui        # only if the template/providers changed
```

To see it actually applied, the whole chain has to be alive — the resource file is the
last link, not the first. In order:

1. `assets/i18n/<lang>.json` is fetched (check `performance.getEntriesByType('resource')`
   in the page — if nothing i18n-shaped appears, the loader is misconfigured, not your JSON);
2. `assets/languages-choice.json` is fetched, or the language dropdown is empty;
3. a language component is mounted somewhere (`<gebo-ai-main-language-choice>` in the
   desktop shell, `<gebo-ai-main-micro-language>` in the office assistant footer) — nothing
   calls `changeActualLanguage` on startup without one;
4. only then does a directive swap the text.

---

## 7. Gotchas that cost real time

- **Silent total failure is the norm here.** A wrong module name, a missing `id`, or a
  `useExisting` provider all produce "English on screen", identical to having no resource
  at all. Verify the four key parts against the providers before assuming the JSON is wrong.
- **`en.json` is merged, not replaced.** `UIExistingTextService` layers Mongo-recorded rows
  *over* the bundled file, so a stale row recorded from an older build can mask what you
  add here. If the app shows text you cannot find in `en.json`, look in the
  `UIExistingText` collection.
- **The recorder only runs when enabled.** `GeboAITranslationService.recordingOn` comes from
  `getUiTextResourcesModule()`; with it off, nothing you render is captured, which is the
  usual reason a component "should have registered itself" and did not.
- **`[class$='-flag']` styling lives in `src/styles/flags.scss`**, split out of the library
  `styles.scss` so a host can include only the flags. A host that includes neither shows no
  flag — relevant whenever a language chooser renders blank circles.
- **Hosts not served from `/`.** `GeboAITranslationService.assetsUrl` resolves against
  `document.baseURI`, and the ngx-translate loader prefix should be **relative**
  (`assets/i18n/`). A root-absolute `/assets/i18n/` 404s wherever the app is served from a
  sub-path — an office plugin under `/sdkjs-plugins/<plugin>/`, for instance.
- **`@ngx-translate/http-loader` v18 reads paths from `resources`.** A top-level
  `{prefix, suffix}` is accepted and ignored, leaving the loader with nothing to request:
  `useValue: { resources: [{ prefix: 'assets/i18n/', suffix: '.json' }] }`.

---

## Report

When this skill is used, state: which component (module/entity), which ids and fieldIds
were added, which visible strings were deliberately left untranslated and why, whether a
template or provider change was needed, and the `git diff --stat` line proving the edit was
append-only.
