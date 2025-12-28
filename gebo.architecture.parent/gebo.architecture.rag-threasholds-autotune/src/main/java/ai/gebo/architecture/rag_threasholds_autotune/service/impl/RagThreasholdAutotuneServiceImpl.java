package ai.gebo.architecture.rag_threasholds_autotune.service.impl;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.rag_threasholds_autotune.config.RagThreasholdAutotuneConfig;
import ai.gebo.architecture.rag_threasholds_autotune.model.OptimizedThreashold;
import ai.gebo.architecture.rag_threasholds_autotune.model.ThreasholdAutotuneProcessResult;
import ai.gebo.architecture.rag_threasholds_autotune.repository.ThreasholdAutotuneProcessResultRepository;
import ai.gebo.architecture.rag_threasholds_autotune.service.IRagThreasholdAutotuneService;
import ai.gebo.llms.abstraction.layer.services.BaseLlmsInvokingService;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.vectorstores.repository.VectorizedContentRepository;
import lombok.AllArgsConstructor;

@Service
public class RagThreasholdAutotuneServiceImpl extends BaseLlmsInvokingService implements IRagThreasholdAutotuneService {
	private final ThreasholdAutotuneProcessResultRepository resultRepo;
	private final VectorizedContentRepository vectorizedContentsRepository;
	private final RagThreasholdAutotuneConfig config;

	public RagThreasholdAutotuneServiceImpl(IGChatModelRuntimeConfigurationDao chatModelsConfigDao,
			IGEmbeddingModelRuntimeConfigurationDao embeddingModelsConfigDao,
			ThreasholdAutotuneProcessResultRepository resultRepo,
			VectorizedContentRepository vectorizedContentsRepository, RagThreasholdAutotuneConfig config) {
		super(chatModelsConfigDao, embeddingModelsConfigDao);
		this.resultRepo = resultRepo;
		this.vectorizedContentsRepository = vectorizedContentsRepository;
		this.config = config;
	}

	@Override
	public OptimizedThreashold findByVectorStoreId(String vectorStoreId) {
		List<ThreasholdAutotuneProcessResult> data = resultRepo.findByVectorStoreId(vectorStoreId);
		return data.isEmpty() ? null : data.get(0).getThreasholds();
	}

	@Override
	public OptimizedThreashold findByEmbeddingModelCode(String embeddingModelCode) {

		List<ThreasholdAutotuneProcessResult> data = resultRepo.findByEmbeddingModelCode(embeddingModelCode);
		return data.isEmpty() ? null : data.get(0).getThreasholds();
	}

	@Override
	public OptimizedThreashold findByKnowledgeBase(String knowledgeBaseCode) {
		List<ThreasholdAutotuneProcessResult> data = resultRepo.findByRootKnowledgeBase(knowledgeBaseCode);
		return data.isEmpty() ? null : data.get(0).getThreasholds();
	}

	@Override
	public void processAutotune(String vectorStoreId) {
		List<ThreasholdAutotuneProcessResult> data = resultRepo.findByVectorStoreId(vectorStoreId);
		ThreasholdAutotuneProcessResult lastEntry = data.isEmpty() ? null : data.get(0);
		long count = vectorizedContentsRepository.countByIdVectorStoreId(vectorStoreId);
		long lastCardinality = lastEntry != null && lastEntry.getVectorStoreVectorizedCount() != null
				? lastEntry.getVectorStoreVectorizedCount()
				: 0l;
		boolean runOptimization = lastEntry == null;
		if (lastEntry != null && lastEntry.getProcessedDateTime() != null) {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.add(GregorianCalendar.DAY_OF_YEAR, -1 * config.getDayElapsedWithoutTuning());
			Date dateThreashold = calendar.getTime();
			runOptimization = lastEntry.getProcessedDateTime().before(dateThreashold);
		}
		if (!runOptimization && lastEntry != null && lastCardinality > 0l) {
			double delta = Math.abs(count - lastCardinality);
			double lastCardinalityD = lastCardinality;
			double deltaPercent = delta / lastCardinalityD * 100.0;
			runOptimization = deltaPercent >= config.getDocumentsCardinalityAddedPercentTrigger();
		}
		if (runOptimization) {

		}
	}

}
