/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import { InjectionToken } from "@angular/core";

/**
 * Optional application level flag telling that the company files are not selectable
 * by the user. When it is provided with value true the hosts that pilot the chat
 * control (the chat section and the office assistant) drive it with
 * disableFilesBrowsers=true, so neither the choose-documents panel nor the userspace
 * files browser can be opened. Missing or false leaves the browsers enabled.
 *
 * It lives here, in the lowest layer, because both the chat section (@Gebo.ai/chat-ui)
 * and the office assistant (this library, used by the office plugins) must read it.
 */
export const UI_COMPANY_FILES_NOT_SELECTABLE = new InjectionToken<boolean>("UI_COMPANY_FILES_NOT_SELECTABLE");
