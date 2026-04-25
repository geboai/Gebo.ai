package ai.gebo.architecture.rag.support.layer.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.ai.vectorstore.filter.Filter.Operand;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder.Op;

import ai.gebo.model.DocumentMetaInfos;

public class SemanticSearchMetaDataFilter {
	private Map<String, List<Op>> operandsMap = new HashMap<>();
	private List<Integer> aclAliases = null;
	private List<String> knowledgeBasesCodes = null;
	private List<String> codesList = null;

	public void add(String metaDataField, Op operand) {
		if (!operandsMap.containsKey(metaDataField.toLowerCase())) {
			operandsMap.put(metaDataField.toLowerCase(), new ArrayList<>());
		}
		operandsMap.get(metaDataField.toLowerCase()).add(operand);
	}

	public List<Integer> getAclAliases() {
		return aclAliases;
	}

	public void setAclAliases(List<Integer> aclAliases) {
		this.aclAliases = aclAliases;
	}

	public Filter.Expression build() {
		List<Op> allOperands = new ArrayList<>();
		if (aclAliases != null && !aclAliases.isEmpty()) {
			FilterExpressionBuilder feb = new FilterExpressionBuilder();
			
			allOperands.add(feb.in(DocumentMetaInfos.GEBO_ACL_ALIASES, new ArrayList<Object>(aclAliases)));
		}
		if (knowledgeBasesCodes != null && !knowledgeBasesCodes.isEmpty()) {
			FilterExpressionBuilder feb = new FilterExpressionBuilder();
			allOperands.add(feb.in(DocumentMetaInfos.KNOWLEDGEBASE_CODE, new ArrayList<Object>(knowledgeBasesCodes)));
		}
		if (codesList != null && !codesList.isEmpty()) {
			FilterExpressionBuilder feb = new FilterExpressionBuilder();
			allOperands.add(feb.in(DocumentMetaInfos.CONTENT_CODE, new ArrayList<Object>(codesList)));
		}
		operandsMap.values().forEach(x -> {
			x.forEach(y -> {
				allOperands.add(y);
			});
		});
		FilterExpressionBuilder feb = new FilterExpressionBuilder();
		Op currentOp = null;
		for (Op op : allOperands) {
			if (currentOp == null)
				currentOp = op;
			else
				currentOp = feb.and(currentOp, op);
		}
		return currentOp.build();
	}

	public List<String> getKnowledgeBasesCodes() {
		return knowledgeBasesCodes;
	}

	public void setKnowledgeBasesCodes(List<String> knowledgeBasesCodes) {
		this.knowledgeBasesCodes = knowledgeBasesCodes;
	}

	public List<String> getCodesList() {
		return codesList;
	}

	public void setCodesList(List<String> codesList) {
		this.codesList = codesList;
	}
}
