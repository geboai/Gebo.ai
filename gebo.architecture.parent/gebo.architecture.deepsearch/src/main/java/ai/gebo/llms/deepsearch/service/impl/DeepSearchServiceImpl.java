package ai.gebo.llms.deepsearch.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.expression.BeanResolver;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.deepsearch.config.DeepSearchDefaultConfig;
import ai.gebo.llms.deepsearch.model.AbstractDeepSearchEvent;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchDocumentAnalisysResultStep;
import ai.gebo.llms.deepsearch.model.DeepSearchProcessedEvent;
import ai.gebo.llms.deepsearch.model.DeepSearchRequest;
import ai.gebo.llms.deepsearch.model.DeepSearchResponse;
import ai.gebo.llms.deepsearch.model.DeepSearchState;
import ai.gebo.llms.deepsearch.repository.DeepSearchConfigRepository;
import ai.gebo.llms.deepsearch.repository.DeepSearchDocumentAnalisysResultStepRepository;
import ai.gebo.llms.deepsearch.repository.DeepSearchRequestRepository;
import ai.gebo.llms.deepsearch.repository.DeepSearchResponseRepository;
import ai.gebo.llms.deepsearch.service.IGDeepSearchService;
import ai.gebo.security.repository.UserRepository.UserInfos;
import ai.gebo.security.services.IGSecurityService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink.OverflowStrategy;

@Service
@AllArgsConstructor
public class DeepSearchServiceImpl implements IGDeepSearchService {
	final DeepSearchDefaultConfig defaultDeepsearchConfig;
	final DeepSearchConfigRepository configRepository;
	final IGRuntimeBinder runtimeBinder;
	final IGSecurityService securityService;
	final DeepSearchRequestRepository requestsRepository;
	final DeepSearchDocumentAnalisysResultStepRepository stepsRepository;
	final DeepSearchResponseRepository responseRepository;
	final ExecutorService deepSearchExecutor = Executors
			.newSingleThreadExecutor(r -> new Thread(r, "deep-search-thread"));

	@Override
	public Flux<AbstractDeepSearchEvent> searchAsync(DeepSearchRequest request) throws LLMConfigException {
		final UserInfos userInfos = securityService.getCurrentUser();
		request.setUsername(userInfos.getUsername());
		final DeepsearchWorker deepSearchWorker = runtimeBinder.getImplementationOf(DeepsearchWorker.class);
		List<DeepSearchConfig> data = configRepository.findAll();
		DeepSearchConfig configuration = data != null && data.size() > 0 ? data.get(0) : defaultDeepsearchConfig;
		return Flux.create(sink -> {

			AtomicBoolean cancelled = new AtomicBoolean(false);
			sink.onCancel(() -> cancelled.set(true));
			sink.onDispose(() -> cancelled.set(true));

			Future<?> future = deepSearchExecutor.submit(() -> {
				final DeepSearchState state = new DeepSearchState();
				final List<AbstractDeepSearchEvent> history = new ArrayList<AbstractDeepSearchEvent>();
				try {
					AbstractDeepSearchEvent thisStepResult = null;
					do {
						thisStepResult = deepSearchWorker.nextStep(request, history, state, configuration, userInfos);
						// Emit “started”
						if (thisStepResult != null) {
							sink.next(thisStepResult);
						} else {
							sink.complete();
						}
						if (thisStepResult instanceof DeepSearchProcessedEvent end) {
							sink.complete();
						}
					} while (thisStepResult == null || thisStepResult instanceof DeepSearchProcessedEvent);

				} catch (Throwable t) {
					sink.error(t);
				}

			});

			// if the downstream disposes, cancel the worker
			sink.onDispose(() -> future.cancel(true));

		}, OverflowStrategy.BUFFER); // choose strategy carefully (see note below)
	}

	@Override
	public Page<DeepSearchRequest> myDeepsearchPaged(Pageable pageable) {

		return requestsRepository.findByUsername(securityService.getCurrentUser().getUsername(), pageable);
	}

	@Override
	public List<DeepSearchRequest> allMyDeepsearches() {

		return requestsRepository.findByUsername(securityService.getCurrentUser().getUsername());
	}

	@Override
	public Page<DeepSearchDocumentAnalisysResultStep> analisysDetailsPaged(String deepSearchCode, Pageable pageable) {
		findDeepSearchRequest(deepSearchCode);
		return stepsRepository.findByDeepsearchCode(deepSearchCode, pageable);
	}

	@Override
	public List<DeepSearchDocumentAnalisysResultStep> analisysDetails(String deepSearchCode) {
		findDeepSearchRequest(deepSearchCode);
		return stepsRepository.findByDeepsearchCode(deepSearchCode);
	}

	@Override
	public DeepSearchResponse findDeepSearchResponse(String deepSearchCode) {
		findDeepSearchRequest(deepSearchCode);
		Optional<DeepSearchResponse> result = responseRepository.findByDeepsearchCode(deepSearchCode);
		return result.isPresent() ? result.get() : null;
	}

	@Override
	public DeepSearchRequest findDeepSearchRequest(String deepSearchCode) {
		Optional<DeepSearchRequest> data = requestsRepository.findById(deepSearchCode);
		if (data.isPresent()) {
			securityService.checkBeingCreator(data.get());
		}
		return data.isPresent() ? data.get() : null;
	}

	@Override
	@Transactional
	public void deleteDeepSearch(String deepSearchCode) {
		findDeepSearchRequest(deepSearchCode);
		requestsRepository.deleteById(deepSearchCode);
		responseRepository.deleteByDeepsearchCode(deepSearchCode);
		stepsRepository.deleteByDeepsearchCode(deepSearchCode);
	}

}
