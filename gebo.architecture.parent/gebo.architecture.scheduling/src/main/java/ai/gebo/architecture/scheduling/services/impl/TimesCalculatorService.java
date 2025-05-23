/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.scheduling.services.impl;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.multithreading.IGRunnableFactory;
import ai.gebo.architecture.scheduling.model.ReindexTimeComponent;
import ai.gebo.architecture.scheduling.model.ReindexTimeComponentMetaInfo;
import ai.gebo.architecture.scheduling.model.ReindexTimeStructureMetaInfo;
import ai.gebo.architecture.scheduling.services.IGReindexTimeStructureMetaInfoDao;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.scheduling.ReindexingFrequency;
import ai.gebo.knlowledgebase.model.scheduling.ReindexingProgrammedTable;
import ai.gebo.knlowledgebase.model.scheduling.ReindexingTime;
import jakarta.validation.constraints.NotNull;

/**
 * AI generated comments
 * Service class responsible for calculating run times based on scheduling data.
 */
@Service
class TimesCalculatorService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TimesCalculatorService.class);

	@Autowired
	IGReindexTimeStructureMetaInfoDao dao;

	private static final DateFormat dtformat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
	
	// Array representing the days of the week
	private final int DAY_OF_WEEK[] = new int[] { GregorianCalendar.MONDAY, GregorianCalendar.TUESDAY,
			GregorianCalendar.WEDNESDAY, GregorianCalendar.THURSDAY, GregorianCalendar.FRIDAY,
			GregorianCalendar.SATURDAY, GregorianCalendar.SUNDAY };

	/**
	 * Calculate the nearest future date for the given scheduled table entries based on current and last run times.
	 * 
	 * @param reindexingProgrammedTable The programmed schedule table.
	 * @param now The current date and time.
	 * @param lastRun The last execution date and time.
	 * @return The nearest future date that meets the schedule.
	 */
	private Date calculateNearerFuture(ReindexingProgrammedTable reindexingProgrammedTable, Date now, Date lastRun) {
		Date date = null;
		if (reindexingProgrammedTable.getTimes() != null) {
			ReindexTimeStructureMetaInfo metaData = dao.findByCode(reindexingProgrammedTable.getFrequency().name());
			for (ReindexingTime times : reindexingProgrammedTable.getTimes()) {
				Date futureDate = getNearerFutureDateTime(reindexingProgrammedTable.getFrequency(), metaData, times,
						now, lastRun);

				if (futureDate != null) {
					if (date == null)
						date = futureDate;
					else {
						date = date.before(futureDate) ? date : futureDate;
					}
				}
			}
		}
		return date;
	}

	/**
	 * Gets the maximum past start date time for a running schedule based on the endpoint.
	 * 
	 * @param endpoint The project endpoint that contains the schedules.
	 * @return The maximum past start date time.
	 */
	Date getMaximumPastStartDateTimeRunningSchedule(GProjectEndpoint endpoint) {
		Date now = new Date();
		Date maxDate = null;
		List<ReindexingProgrammedTable> schedules = endpoint.getProgrammedTables();
		if (schedules != null) {
			for (ReindexingProgrammedTable reindexingProgrammedTable : schedules) {
				List<Date> options = readNearerPast(reindexingProgrammedTable, now);
				if (options != null) {
					for (Date option : options) {
						if (maxDate == null)
							maxDate = option;
						else {
							maxDate = maxDate.after(option) ? maxDate : option;
						}
					}
				}
			}
		}
		LOGGER.info("For endpoint with code:" + endpoint.getCode() + " maximum past date:" + maxDate);
		return maxDate;
	}

	/**
	 * Gets the nearest future date and time based on different scheduling frequencies.
	 * 
	 * @param frequency The frequency of reindexing.
	 * @param metaData Meta-information regarding scheduling.
	 * @param times The scheduled times.
	 * @param now The current date and time.
	 * @param lastRun The last run date and time.
	 * @return The nearest future date and time.
	 */
	private Date getNearerFutureDateTime(ReindexingFrequency frequency, ReindexTimeStructureMetaInfo metaData,
			ReindexingTime times, Date now, Date lastRun) {
		Date outDate = null;

		switch (frequency) {
		case DATES: {
			if (times.getTimeComponent().size() == 1) {
				Date exactTime = new Date(times.getTimeComponent().get(0));
				if (exactTime.after(now) || exactTime.after(now)) {
					outDate = exactTime;
				}
			}
		}
			break;
		case DAILY: {
			GregorianCalendar gc = new GregorianCalendar();
			gc.set(GregorianCalendar.SECOND,0);
			gc.set(GregorianCalendar.MILLISECOND,0);
			for (int i = 0; i < metaData.getPeriodComponents().length; i++) {
				set(gc, metaData.getPeriodComponents()[i].getTimeUnity(), times.getTimeComponent().get(i));
			}
			Date ref=gc.getTime();
			if (lastRun != null && ref.before(lastRun)) {
				gc.add(GregorianCalendar.DAY_OF_YEAR, 1);
			}
			if (gc.getTime().after(now))
				outDate = gc.getTime();
		}
			break;
		case HOURLY: {
			GregorianCalendar gc = new GregorianCalendar();
			gc.set(GregorianCalendar.SECOND,0);
			gc.set(GregorianCalendar.MILLISECOND,0);
			
			for (int i = 0; i < metaData.getPeriodComponents().length; i++) {
				set(gc, metaData.getPeriodComponents()[i].getTimeUnity(), times.getTimeComponent().get(i));
			}
			if (lastRun != null && gc.getTime().before(lastRun)) {
				gc.add(GregorianCalendar.HOUR_OF_DAY, 1);
			}
			if (gc.getTime().after(now)) {
				outDate = gc.getTime();
			} else {
				gc.add(GregorianCalendar.HOUR_OF_DAY, +1);
				if (gc.getTime().after(now)) {
					outDate = gc.getTime();
				}
			}
		}
			break;
		case WEEKLY: {
			GregorianCalendar gc = new GregorianCalendar();
			gc.set(GregorianCalendar.SECOND,0);
			gc.set(GregorianCalendar.MILLISECOND,0);
			
			for (int i = 0; i < metaData.getPeriodComponents().length; i++) {
				set(gc, metaData.getPeriodComponents()[i].getTimeUnity(), times.getTimeComponent().get(i));
			}
			if (lastRun != null && gc.getTime().before(lastRun)) {
				gc.add(GregorianCalendar.DAY_OF_YEAR, 7);
			}
			if (gc.getTime().after(now))
				outDate = gc.getTime();
		}
			break;
		}
		return outDate;
	}

	/**
	 * Gets the next run date and time for a given endpoint and last run date.
	 * 
	 * @param endpoint The project endpoint containing the scheduled tables.
	 * @param lastRun The last execution date and time.
	 * @return The next run date and time.
	 */
	Date getNextRunDateTime(GProjectEndpoint endpoint, Date lastRun) {
		Date now = new Date();
		Date nearerFuture = null;
		List<ReindexingProgrammedTable> timeTables = endpoint.getProgrammedTables();
		if (timeTables != null) {
			for (ReindexingProgrammedTable reindexingProgrammedTable : timeTables) {
				Date potentiallyNext = calculateNearerFuture(reindexingProgrammedTable, now, lastRun);
				if (potentiallyNext == null)
					continue;
				if (nearerFuture == null) {
					nearerFuture = potentiallyNext;
				} else {
					nearerFuture = potentiallyNext.before(nearerFuture) ? potentiallyNext : nearerFuture;
				}
			}
		}
		return nearerFuture;
	}

	/**
	 * Reads and returns the nearest past date options based on the scheduled table and current date.
	 * 
	 * @param reindexingProgrammedTable The scheduled table containing time entries.
	 * @param now The current date and time.
	 * @return A list of the nearest past dates.
	 */
	private List<Date> readNearerPast(ReindexingProgrammedTable reindexingProgrammedTable, Date now) {
		List<Date> out = new ArrayList<Date>();
		if (reindexingProgrammedTable.getFrequency() == null)
			return List.of();

		Date maximumGuard = now;
		ReindexTimeStructureMetaInfo metaInfos = this.dao.findByCode(reindexingProgrammedTable.getFrequency().name());
		List<ReindexingTime> times = reindexingProgrammedTable.getTimes();
		if (times != null) {
			for (ReindexingTime reindexingTime : times) {
				Long creationTime = reindexingTime.getCreatedTime();
				Date minimimGuard = creationTime != null ? new Date(creationTime.longValue()) : null;
				if (reindexingTime.getTimeComponent() != null
						&& reindexingTime.getTimeComponent().size() == metaInfos.getPeriodComponents().length) {
					logTimeEvaluation(reindexingProgrammedTable.getFrequency(), metaInfos.getPeriodComponents(),
							reindexingTime.getTimeComponent());
					GregorianCalendar gregorianCalendar = new GregorianCalendar();
					boolean canBeValid = false;
					// Going backward in time components so on the higher level we can go to the
					// previous period
					for (int i = metaInfos.getPeriodComponents().length - 1; i >= 0; i--) {
						canBeValid = canBeValid || setNearestOnMaximumEdge(gregorianCalendar,
								metaInfos.getPeriodComponents()[i], reindexingTime.getTimeComponent().get(i),
								minimimGuard, maximumGuard, reindexingProgrammedTable.getFrequency());
					}
					if (canBeValid && gregorianCalendar.getTime().before(now)) {
						out.add(gregorianCalendar.getTime());
					}
				}
			}
		}
		LOGGER.info(
				"Evaluated rearNearerPast for frequency:" + reindexingProgrammedTable.getFrequency() + " => " + out);
		return out;
	}

	/**
	 * Sets a specific time component in the GregorianCalendar instance.
	 * 
	 * @param gc The calendar instance in which to set time components.
	 * @param timeUnity The time unity to be set.
	 * @param value The value of the time component.
	 */
	private void set(GregorianCalendar gc, ReindexTimeComponent timeUnity, Long value) {
		int timeComponent = value.intValue();
		switch (timeUnity) {
		case DATE: {
			gc.setTimeInMillis(timeComponent);

		}
			break;
		case HOUR: {
			gc.set(GregorianCalendar.HOUR_OF_DAY, timeComponent);

		}
			break;
		case DAY_OF_WEEK: {
			gc.set(GregorianCalendar.DAY_OF_WEEK, DAY_OF_WEEK[timeComponent]);

		}
			break;
		case MINUTES: {
			gc.set(GregorianCalendar.MINUTE, timeComponent);

		}
			break;
		case WEEK_OF_MONTH: {
		}
			break;
		}

	}

	/**
	 * Sets the nearest valid date based on the maximum edge constraints.
	 * 
	 * @param gc The calendar instance to be modified.
	 * @param meta The metadata of the time component.
	 * @param timeComponent The specified time component value.
	 * @param minimimGuard The minimum guard date to consider.
	 * @param maximumGuard The maximum guard date to consider.
	 * @param reindexingFrequency The frequency of reindexing.
	 * @return A boolean indicating if the date is valid.
	 */
	private boolean setNearestOnMaximumEdge(GregorianCalendar gc, ReindexTimeComponentMetaInfo meta, Long timeComponent,
			Date minimimGuard, Date maximumGuard, ReindexingFrequency reindexingFrequency) {
		boolean canBeValid = true;
		long minValue = minimimGuard != null ? minimimGuard.getTime() : 0l;
		long maxValue = maximumGuard != null ? maximumGuard.getTime() : System.currentTimeMillis();
		switch (meta.getTimeUnity()) {
		case DATE: {
			gc.setTimeInMillis(timeComponent);
			long thisTime = gc.getTimeInMillis();
			canBeValid = thisTime >= minValue && thisTime <= maxValue;

		}
			break;
		case HOUR: {
			gc.set(GregorianCalendar.HOUR_OF_DAY, timeComponent.intValue());

		}
			break;
		case DAY_OF_WEEK: {
			gc.set(GregorianCalendar.DAY_OF_WEEK, DAY_OF_WEEK[timeComponent.intValue()]);
			// Check if we are in the future
			long thisTime = gc.getTimeInMillis();
			if (thisTime > maxValue) {
				// If in the future, try the week before
				gc.add(GregorianCalendar.WEEK_OF_YEAR, -1);
			}

		}
			break;
		case MINUTES: {
			gc.set(GregorianCalendar.MINUTE, timeComponent.intValue());
			if (reindexingFrequency == ReindexingFrequency.HOURLY) {
				// If it is an HOURLY frequency, we can try the one before or after
				GregorianCalendar gcCal = new GregorianCalendar();
				gcCal.setTimeInMillis(maxValue);
				int hour = gcCal.get(GregorianCalendar.HOUR_OF_DAY);
				int minutes = gcCal.get(GregorianCalendar.MINUTE);
				if (timeComponent.intValue() >= minutes) {
					gc.set(GregorianCalendar.HOUR_OF_DAY, hour);
				} else {
					gc.set(GregorianCalendar.HOUR_OF_DAY, hour);
					gc.add(GregorianCalendar.HOUR_OF_DAY, -1);
				}
			}
			long thisTime = gc.getTimeInMillis();
			canBeValid = thisTime >= minValue && thisTime <= maxValue;
		}
			break;
		case WEEK_OF_MONTH: {
		}
			break;
		}
		return canBeValid;
	}

	/**
	 * Logs the time evaluation details specifying frequency and time components.
	 * 
	 * @param frequency The frequency of the schedule.
	 * @param periodComponents The component details of the scheduling period.
	 * @param timeComponent The time components values.
	 */
	private void logTimeEvaluation(ReindexingFrequency frequency, ReindexTimeComponentMetaInfo[] periodComponents,
			List<Long> timeComponent) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Frequency:" + frequency.name());
		for (int i = 0; i < timeComponent.size(); i++) {
			buffer.append(" " + periodComponents[i].getLabel() + ":" + timeComponent.get(i));
		}

		LOGGER.info(buffer.toString());

	}

	/**
	 * Converts the scheduled times into human-readable display time values.
	 * 
	 * @param programmedTable The list of scheduled tables.
	 * @return A list of formatted display time strings.
	 */
	List<String> getDisplayTimeValues(List<ReindexingProgrammedTable> programmedTable) {

		List<String> outValues = new ArrayList<String>();
		for (ReindexingProgrammedTable pt : programmedTable) {
			ReindexTimeStructureMetaInfo metaInfos = dao.findByCode(pt.getFrequency().name());
			if (pt.getTimes() != null) {

				for (ReindexingTime timeInstance : pt.getTimes()) {
					StringBuffer buffer = new StringBuffer();

					for (int i = 0; i < Math.min(metaInfos.getPeriodComponents().length,
							timeInstance.getTimeComponent().size()); i++) {
						ReindexTimeComponentMetaInfo metaInfo = metaInfos.getPeriodComponents()[i];
						Long timeComponent = timeInstance.getTimeComponent().get(i);
						switch (metaInfo.getTimeUnity()) {
						case DATE: {
							buffer.append(dtformat.format(new Date(timeComponent.longValue())));
							buffer.append(" ");
						}
							break;
						case DAY_OF_WEEK: {
							if (timeComponent.longValue() >= 0 && timeComponent
									.longValue() < ReindexTimeComponentMetaInfo.DAY_OF_WEEK_LIST.size()) {
								buffer.append(
										ReindexTimeComponentMetaInfo.DAY_OF_WEEK_LIST.get(timeComponent.intValue()));
								buffer.append(" ");
							}
						}
							break;
						case HOUR: {
							buffer.append("Time: " + timeComponent.intValue());
							buffer.append(":");
						}
							break;
						case MINUTES: {
							buffer.append("" + timeComponent.intValue());
						}
							break;
						case WEEK_OF_MONTH: {
							buffer.append("Week of month:" + timeComponent.intValue());
							buffer.append(" ");
						}
							break;
						}
					}
					outValues.add(buffer.toString());
				}
			}
		}
		return outValues;
	}
}