package ai.gebo.architecture.agents.services.impl;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.architecture.agents.model.IGCopiedEntity;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.architecture.patterns.IGRuntimeConfigurationDao;
import ai.gebo.architecture.persistence.IGCodeGeneratorEntityHandler;
import ai.gebo.model.base.GBaseObject;

/**
 * Makes the code of a copy from the code of its original, instead of from its
 * description: {@code <original code>-<n>}, the first free slot from {@code n = 1}
 * on. An original whose code already ends with {@code -<n>} (a copy, typically) is
 * counted on from its root: its copy takes the next free {@code <root>-<n>}, never
 * {@code <root>-<n>-<m>}. A code is taken when its DAO finds it, among the stored
 * entities and the ones declared by configuration beans alike. The other entities
 * keep the code generated from their description.
 * <p>
 * The DAO is resolved lazily through the {@link IGRuntimeBinder}: the DAOs store
 * through the persistence layer, which holds the code generators.
 */
public abstract class GAbstractCopyCodeGenerator<Type extends GBaseObject & IGCopiedEntity>
		implements IGCodeGeneratorEntityHandler<Type> {
	private static final Logger LOGGER = LoggerFactory.getLogger(GAbstractCopyCodeGenerator.class);
	/** The slot suffix of an original that is itself a copy, or numbered like one. */
	private static final Pattern SLOT_SUFFIX_PATTERN = Pattern.compile("-\\d+$");
	protected final IGRuntimeBinder runtimeBinder;

	protected GAbstractCopyCodeGenerator(IGRuntimeBinder runtimeBinder) {
		this.runtimeBinder = runtimeBinder;
	}

	/** The DAO telling whether a code is taken. */
	protected abstract IGRuntimeConfigurationDao<Type> dao();

	@Override
	public boolean isAppliedToExtendingClass() {
		return false;
	}

	@Override
	public String generateCode(Type entity, String pregeneratedCode) {
		final String copiedFrom = entity.getCopiedFromCode();
		if (copiedFrom == null || copiedFrom.isBlank()) {
			return pregeneratedCode;
		}
		final String root = SLOT_SUFFIX_PATTERN.matcher(copiedFrom.trim()).replaceFirst("");
		final IGRuntimeConfigurationDao<Type> dao = dao();
		int slot = 1;
		String code = root + "-" + slot;
		while (dao.findByCode(code) != null) {
			slot++;
			code = root + "-" + slot;
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Code of the copy of:" + copiedFrom + " (" + getAffectedClass().getSimpleName() + "): " + code
					+ " instead of the one from its description:" + pregeneratedCode);
		}
		return code;
	}
}
