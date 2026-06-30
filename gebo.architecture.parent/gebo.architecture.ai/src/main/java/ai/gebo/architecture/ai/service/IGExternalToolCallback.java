package ai.gebo.architecture.ai.service;

import org.springframework.ai.tool.ToolCallback;

public interface IGExternalToolCallback extends ToolCallback {
	public static boolean isExternalTool(ToolCallback tool) {
		return tool instanceof IGExternalToolCallback;
	}
}
