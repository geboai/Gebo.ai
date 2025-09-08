package ai.gebo.architecture.documents.cache.service.impl;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractCacheEntryCleanupService<Type extends AbstractCachedEntry, RepositoryType extends AbstractCachedEntryRepository<Type>> {
	protected final RepositoryType repository;
	protected final long cacheLastUsedMillisecondAgoExpiration;
	protected final Logger LOGGER = LoggerFactory.getLogger(AbstractCacheEntryCleanupService.class);
	/********************************************************
	 * Cleans filesystem resources
	 * @param data
	 */
	protected abstract void cleanupResources(Type data);

	@Scheduled(initialDelay = 10000, fixedRate = 120000)
	public void checkExpirationTick() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin checkExpirationTick()");
		}
		try {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.add(GregorianCalendar.SECOND, (int) (-1 * cacheLastUsedMillisecondAgoExpiration / 1000));
			Date expiredThreshold = calendar.getTime();
			Stream<Type> dataStream = repository.findByLastAccessedLessThan(expiredThreshold);
			dataStream.forEach(this::cleanupResources);
			repository.deleteByLastAccessedLessThan(expiredThreshold);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("End checkExpirationTick()");
			}
		} catch (Throwable th) {
			LOGGER.error("Exception in checkExpirationTick()", th);
		}
	}
}
