package ai.gebo.llms.abstraction.layer.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class ToolCallsListener {
	@AllArgsConstructor
	@Getter
	public static class ToolCallExecuted {
		private final String name;
		private final String toolDescription;
		private final String toolInput;
		private final String result;
	}

	private final Vector<ToolCallExecuted> execs = new Vector<>();

	public void addCall(String toolName,String toolDescription, String toolInput, String result) {
		execs.add(new ToolCallExecuted(toolName,toolDescription, toolInput, result));
	}

	public List<ToolCallExecuted> getCalls() {
		return new ArrayList<>(execs);
	}
}
