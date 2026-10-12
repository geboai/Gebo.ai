/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.ai.model;

import lombok.Data;

/**
 * Lightweight projection of a {@link GPromptTemplateConfig} for list views:
 * carries only the identity and display fields needed to render a prompt
 * templates table (use code, language, description) plus the code to open the
 * full editor and the static/dynamic flag, without shipping the (potentially
 * large) system/user template texts.
 */
@Data
public class GPromptTemplateLightView {
	private String code;
	private String promptUse;
	private String langCode;
	private String description;
	private Boolean configDeclarated;

	public static GPromptTemplateLightView of(GPromptTemplateConfig config) {
		GPromptTemplateLightView view = new GPromptTemplateLightView();
		view.setCode(config.getCode());
		view.setPromptUse(config.getPromptUse());
		view.setLangCode(config.getLangCode());
		view.setDescription(config.getDescription());
		view.setConfigDeclarated(config.getConfigDeclarated());
		return view;
	}
}
