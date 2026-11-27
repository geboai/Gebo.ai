package ai.gebo.architecture.llms.usage.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.llms.usage.model.LLMDailyUsageDetail;
import ai.gebo.architecture.llms.usage.model.LLMUsageDrillDownLevel;
import ai.gebo.security.model.UsersGroup;
import ai.gebo.security.services.IGSecurityDirectory;

/**
 * The LLM usage as an Excel workbook: one line per user, month, provider and model,
 * with the user's groups, the input and output tokens and their costs. It covers the
 * daily documents the monthly dashboard shows for the same drill down criteria.
 * <p>
 * The costs are summed in their currency: a key whose calls were priced in several
 * currencies has one line per currency. The input and output costs are the parts of
 * the total due to the tokens: the total also has the prices per request, and the
 * parts are told only when every priced call of the line recorded them (the calls
 * recorded before they existed did not).
 */
@Service
public class LLMUsageExcelExportService {
	private static final Logger LOGGER = LoggerFactory.getLogger(LLMUsageExcelExportService.class);
	public static final String CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static final String[] HEADERS = { "User", "Groups", "Month", "Provider", "Model", "Currency", "Input tokens",
			"Output tokens", "Input cost", "Output cost", "Total cost" };
	private static final String GROUPS_SEPARATOR = ", ";

	private final MongoTemplate mongoTemplate;
	private final ObjectProvider<IGSecurityDirectory> directory;

	public LLMUsageExcelExportService(MongoTemplate mongoTemplate, ObjectProvider<IGSecurityDirectory> directory) {
		this.mongoTemplate = mongoTemplate;
		this.directory = directory;
	}

	/** One line of the export. Costs are null when not known. */
	public record UsageLine(String username, String groups, int year, int month, String provider, String model,
			String currencyCode, long inputTokens, long outputTokens, Double inputCost, Double outputCost,
			Double totalCost) {
		public String period() {
			return String.format("%04d-%02d", year, month);
		}
	}

	/**
	 * The workbook of the usage the criteria select.
	 *
	 * @param usernameRestriction as in {@link LLMSUsageAggregationService#drillDown}:
	 *                            when not null, only this user's usage
	 */
	public byte[] export(LLMUsageDrillDownLevel criteria, String usernameRestriction) {
		final String username = usernameRestriction != null ? usernameRestriction
				: (criteria != null ? criteria.getUsername() : null);
		final Query query = new Query(LLMSUsageAggregationService.buildMatch(criteria, username, false));
		final List<UsageLine> lines;
		try (Stream<LLMDailyUsageDetail> days = mongoTemplate.stream(query, LLMDailyUsageDetail.class)) {
			lines = lines(days, this::groupsOf);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("LLM usage export of " + lines.size() + " line(s), criteria=" + criteria + " user restriction="
					+ usernameRestriction);
		}
		return workbook(lines, criteria, username);
	}

	/**
	 * The lines of the daily documents, by user, month, provider and model, sorted by
	 * month, user, provider and model.
	 *
	 * @param groupsOf the groups of a user as told in a line
	 */
	static List<UsageLine> lines(Stream<LLMDailyUsageDetail> days, Function<String, String> groupsOf) {
		final Map<Key, Accumulator> byKey = new LinkedHashMap<>();
		days.forEach(day -> byKey.computeIfAbsent(Key.of(day), k -> new Accumulator()).add(day));
		final Map<String, String> groups = new HashMap<>();
		final List<UsageLine> lines = new ArrayList<>();
		byKey.forEach((key, accumulator) -> accumulator.lines(key,
				key.username() != null ? groups.computeIfAbsent(key.username(), groupsOf) : "", lines));
		lines.sort(Comparator.comparingInt(UsageLine::year).thenComparingInt(UsageLine::month)
				.thenComparing(UsageLine::username, Comparator.nullsFirst(Comparator.naturalOrder()))
				.thenComparing(UsageLine::provider, Comparator.nullsFirst(Comparator.naturalOrder()))
				.thenComparing(UsageLine::model, Comparator.nullsFirst(Comparator.naturalOrder()))
				.thenComparing(UsageLine::currencyCode, Comparator.nullsFirst(Comparator.naturalOrder())));
		return lines;
	}

	/** The groups of a user, by description (by code when it has none); empty when unknown. */
	String groupsOf(String username) {
		final IGSecurityDirectory users = directory.getIfAvailable();
		if (users == null) {
			return "";
		}
		try {
			final List<UsersGroup> groups = users.findGroupsOfUser(username);
			if (groups == null) {
				return "";
			}
			return groups.stream().filter(Objects::nonNull)
					.map(group -> group.getDescription() != null && !group.getDescription().isBlank()
							? group.getDescription().strip()
							: group.getCode())
					.filter(Objects::nonNull).distinct().sorted().collect(Collectors.joining(GROUPS_SEPARATOR));
		} catch (RuntimeException e) {
			LOGGER.warn("Cannot read the groups of " + username + ": exported without them", e);
			return "";
		}
	}

	/** A line's key: the provider is the model type code on the records older than it. */
	private record Key(String username, int year, int month, String provider, String model) {
		static Key of(LLMDailyUsageDetail day) {
			return new Key(day.getUsername(), day.getYear(), day.getMonth(),
					day.getProviderId() != null ? day.getProviderId() : day.getModelTypeCode(), day.getModel());
		}
	}

	/** The usage of one key, per currency; null is the currency of the unpriced days. */
	private static final class Accumulator {
		private final Map<String, Sums> byCurrency = new LinkedHashMap<>();

		void add(LLMDailyUsageDetail day) {
			// a day priced in several currencies has no cost (it cannot be summed): unpriced
			final String currency = day.getCost() != null ? day.getCurrencyCode() : null;
			byCurrency.computeIfAbsent(currency, c -> new Sums()).add(day);
		}

		/**
		 * One line per currency; the unpriced days are in the line of the only currency
		 * when there is one, on a line of their own otherwise.
		 */
		void lines(Key key, String groups, List<UsageLine> lines) {
			final List<String> currencies = byCurrency.keySet().stream().filter(Objects::nonNull).toList();
			final Sums unpriced = byCurrency.get(null);
			if (currencies.size() <= 1) {
				final String currency = currencies.isEmpty() ? null : currencies.get(0);
				final Sums sums = currency != null ? byCurrency.get(currency) : new Sums();
				if (currency != null && unpriced != null) {
					sums.inputTokens += unpriced.inputTokens;
					sums.outputTokens += unpriced.outputTokens;
				} else if (currency == null && unpriced != null) {
					sums.inputTokens = unpriced.inputTokens;
					sums.outputTokens = unpriced.outputTokens;
				}
				lines.add(sums.line(key, groups, currency));
				return;
			}
			for (String currency : currencies) {
				lines.add(byCurrency.get(currency).line(key, groups, currency));
			}
			if (unpriced != null) {
				lines.add(unpriced.line(key, groups, null));
			}
		}
	}

	private static final class Sums {
		long inputTokens;
		long outputTokens;
		double cost;
		long costSamples;
		double inputCost;
		double outputCost;
		long costSplitSamples;

		void add(LLMDailyUsageDetail day) {
			inputTokens += day.getInputToken();
			outputTokens += day.getOutputToken();
			if (day.getCost() != null) {
				cost += day.getCost();
				costSamples += day.getCostSamples();
				if (day.getInputCost() != null && day.getOutputCost() != null) {
					inputCost += day.getInputCost();
					outputCost += day.getOutputCost();
					costSplitSamples += day.getCostSplitSamples();
				}
			}
		}

		UsageLine line(Key key, String groups, String currency) {
			final boolean priced = currency != null && costSamples > 0;
			// the parts are told only when they cover every priced call of the line
			final boolean split = priced && costSplitSamples == costSamples;
			return new UsageLine(key.username(), groups, key.year(), key.month(), key.provider(), key.model(),
					currency, inputTokens, outputTokens, split ? inputCost : null, split ? outputCost : null,
					priced ? cost : null);
		}
	}

	/** The workbook: the usage lines, then a sheet telling what they cover. */
	static byte[] workbook(List<UsageLine> lines, LLMUsageDrillDownLevel criteria, String username) {
		try (XSSFWorkbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			final Font bold = workbook.createFont();
			bold.setBold(true);
			final CellStyle header = workbook.createCellStyle();
			header.setFont(bold);
			final CellStyle tokens = workbook.createCellStyle();
			tokens.setDataFormat(workbook.createDataFormat().getFormat("#,##0"));
			final CellStyle money = workbook.createCellStyle();
			money.setDataFormat(workbook.createDataFormat().getFormat("#,##0.0000"));

			final Sheet sheet = workbook.createSheet("LLM usage");
			final Row head = sheet.createRow(0);
			for (int i = 0; i < HEADERS.length; i++) {
				final Cell cell = head.createCell(i);
				cell.setCellValue(HEADERS[i]);
				cell.setCellStyle(header);
			}
			int r = 1;
			for (UsageLine line : lines) {
				final Row row = sheet.createRow(r++);
				text(row, 0, line.username());
				text(row, 1, line.groups());
				text(row, 2, line.period());
				text(row, 3, line.provider());
				text(row, 4, line.model());
				text(row, 5, line.currencyCode());
				number(row, 6, (double) line.inputTokens(), tokens);
				number(row, 7, (double) line.outputTokens(), tokens);
				number(row, 8, line.inputCost(), money);
				number(row, 9, line.outputCost(), money);
				number(row, 10, line.totalCost(), money);
			}
			final int[] widths = { 32, 32, 10, 16, 28, 10, 16, 16, 14, 14, 14 };
			for (int i = 0; i < widths.length; i++) {
				sheet.setColumnWidth(i, widths[i] * 256);
			}
			sheet.createFreezePane(0, 1);
			sheet.setAutoFilter(new CellRangeAddress(0, Math.max(0, r - 1), 0, HEADERS.length - 1));

			final Sheet notes = workbook.createSheet("Notes");
			int n = 0;
			n = note(notes, n, "Generated",
					LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
			n = note(notes, n, "Filter", filterOf(criteria, username));
			n = note(notes, n, "Costs",
					"An evaluation from the configured model prices: they may differ from the costs billed by the suppliers.");
			n = note(notes, n, "Total cost",
					"The input and output costs plus the prices per request, when a model has one.");
			n = note(notes, n, "Input and output cost",
					"Empty when some priced calls of the line were recorded before the input and output parts of the cost were.");
			n = note(notes, n, "Currency",
					"Costs in different currencies are not summed: a key priced in several currencies has one line per currency. Empty when no call of the line was priced, or a model with a flat monthly fee.");
			notes.setColumnWidth(0, 24 * 256);
			notes.setColumnWidth(1, 120 * 256);
			workbook.write(out);
			return out.toByteArray();
		} catch (IOException e) {
			throw new UncheckedIOException("Cannot write the LLM usage workbook", e);
		}
	}

	/** The criteria applied, as the notes tell them. */
	static String filterOf(LLMUsageDrillDownLevel c, String username) {
		final List<String> told = new ArrayList<>();
		if (c != null) {
			add(told, "provider", c.getProviderId());
			add(told, "provider service", c.getModelTypeCode());
			add(told, "model type", c.getModelType());
			add(told, "model", c.getModel());
			add(told, "caller stack", c.getCallerStack());
			add(told, "year", c.getYear());
			add(told, "month", c.getMonth());
		}
		add(told, "user", username);
		return told.isEmpty() ? "None: all the usage recorded" : String.join("; ", told);
	}

	private static void add(List<String> told, String name, Object value) {
		if (value != null) {
			told.add(name + " " + value);
		}
	}

	private static int note(Sheet sheet, int r, String name, String text) {
		final Row row = sheet.createRow(r);
		row.createCell(0).setCellValue(name);
		row.createCell(1).setCellValue(text);
		return r + 1;
	}

	private static void text(Row row, int column, String value) {
		row.createCell(column).setCellValue(value != null ? value : "");
	}

	private static void number(Row row, int column, Double value, CellStyle style) {
		final Cell cell = row.createCell(column);
		if (value != null) {
			cell.setCellValue(value);
			cell.setCellStyle(style);
		}
	}
}
