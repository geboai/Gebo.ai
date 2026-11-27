package ai.gebo.architecture.llms.usage.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.data.mongodb.core.MongoTemplate;

import ai.gebo.architecture.llms.usage.model.LLMDailyUsageDetail;
import ai.gebo.architecture.llms.usage.model.LLMUsageDrillDownLevel;
import ai.gebo.architecture.llms.usage.service.impl.LLMUsageExcelExportService.UsageLine;
import ai.gebo.security.model.UsersGroup;
import ai.gebo.security.services.IGSecurityDirectory;

class LLMUsageExcelExportServiceTest {
	private static final Function<String, String> GROUPS = username -> "group of " + username;

	private static LLMDailyUsageDetail day(String user, int month, int day, String model, long input, long output) {
		LLMDailyUsageDetail detail = new LLMDailyUsageDetail();
		detail.setUsername(user);
		detail.setProviderId("openai");
		detail.setModelTypeCode("chatgpt-OpenAI");
		detail.setModel(model);
		detail.setYear(2026);
		detail.setMonth(month);
		detail.setDay(day);
		detail.setInputToken(input);
		detail.setOutputToken(output);
		detail.setNrRequests(1);
		return detail;
	}

	private static LLMDailyUsageDetail priced(LLMDailyUsageDetail day, String currency, double cost, Double input,
			Double output) {
		day.setCurrencyCode(currency);
		day.setCost(cost);
		day.setCostSamples(1);
		day.setInputCost(input);
		day.setOutputCost(output);
		day.setCostSplitSamples(input != null ? 1 : 0);
		return day;
	}

	@Test
	void theDaysOfAUserMonthProviderAndModelAreOneLine() {
		List<UsageLine> lines = LLMUsageExcelExportService.lines(List.of(
				priced(day("anna", 11, 1, "gpt", 100, 10), "USD", 0.30, 0.10, 0.20),
				priced(day("anna", 11, 2, "gpt", 200, 20), "USD", 0.60, 0.20, 0.40),
				day("anna", 10, 5, "gpt", 50, 5), day("bruno", 11, 1, "gpt", 7, 3)).stream(), GROUPS);

		assertEquals(3, lines.size());
		UsageLine october = lines.get(0);
		assertEquals("2026-10", october.period());
		assertNull(october.totalCost(), "an unpriced line has no cost");
		assertNull(october.currencyCode());
		UsageLine november = lines.get(1);
		assertEquals("anna", november.username());
		assertEquals("group of anna", november.groups());
		assertEquals("openai", november.provider());
		assertEquals("gpt", november.model());
		assertEquals("USD", november.currencyCode());
		assertEquals(300, november.inputTokens());
		assertEquals(30, november.outputTokens());
		assertEquals(0.30, november.inputCost(), 1e-12);
		assertEquals(0.60, november.outputCost(), 1e-12);
		assertEquals(0.90, november.totalCost(), 1e-12);
		assertEquals("bruno", lines.get(2).username());
	}

	@Test
	void theInputAndOutputCostsAreToldOnlyWhenEveryPricedCallRecordedThem() {
		List<UsageLine> lines = LLMUsageExcelExportService.lines(
				List.of(priced(day("anna", 11, 1, "gpt", 100, 10), "USD", 0.30, 0.10, 0.20),
						priced(day("anna", 11, 2, "gpt", 100, 10), "USD", 0.50, null, null)).stream(),
				GROUPS);

		assertEquals(1, lines.size());
		assertEquals(0.80, lines.get(0).totalCost(), 1e-12);
		assertNull(lines.get(0).inputCost());
		assertNull(lines.get(0).outputCost());
	}

	@Test
	void theUnpricedDaysCountInTheLineOfTheirKeysOnlyCurrency() {
		List<UsageLine> lines = LLMUsageExcelExportService.lines(
				List.of(priced(day("anna", 11, 1, "gpt", 100, 10), "EUR", 0.30, 0.10, 0.20),
						day("anna", 11, 2, "gpt", 40, 4)).stream(),
				GROUPS);

		assertEquals(1, lines.size());
		assertEquals("EUR", lines.get(0).currencyCode());
		assertEquals(140, lines.get(0).inputTokens());
		assertEquals(0.30, lines.get(0).totalCost(), 1e-12);
	}

	@Test
	void costsInSeveralCurrenciesAreOneLinePerCurrency() {
		List<UsageLine> lines = LLMUsageExcelExportService.lines(
				List.of(priced(day("anna", 11, 1, "gpt", 100, 10), "EUR", 0.30, 0.10, 0.20),
						priced(day("anna", 11, 2, "gpt", 200, 20), "USD", 0.70, 0.30, 0.40)).stream(),
				GROUPS);

		assertEquals(2, lines.size());
		assertEquals("EUR", lines.get(0).currencyCode());
		assertEquals(100, lines.get(0).inputTokens());
		assertEquals("USD", lines.get(1).currencyCode());
		assertEquals(0.70, lines.get(1).totalCost(), 1e-12);
	}

	@Test
	void theGroupsOfAUserAreReadOnce() {
		List<String> asked = new ArrayList<>();
		LLMUsageExcelExportService.lines(
				List.of(day("anna", 11, 1, "gpt", 1, 1), day("anna", 11, 1, "other", 1, 1)).stream(), username -> {
					asked.add(username);
					return "";
				});
		assertEquals(List.of("anna"), asked);
	}

	@Test
	@SuppressWarnings("unchecked")
	void theGroupsAreTheirDescriptionsSorted() {
		IGSecurityDirectory directory = mock(IGSecurityDirectory.class);
		UsersGroup sales = new UsersGroup();
		sales.setCode("g-2");
		sales.setDescription("Sales");
		UsersGroup coded = new UsersGroup();
		coded.setCode("g-1");
		UsersGroup admins = new UsersGroup();
		admins.setCode("g-3");
		admins.setDescription("Admins");
		when(directory.findGroupsOfUser("anna")).thenReturn(List.of(sales, coded, admins));
		ObjectProvider<IGSecurityDirectory> provider = mock(ObjectProvider.class);
		when(provider.getIfAvailable()).thenReturn(directory);

		LLMUsageExcelExportService service = new LLMUsageExcelExportService(mock(MongoTemplate.class), provider);

		assertEquals("Admins, Sales, g-1", service.groupsOf("anna"));
		when(directory.findGroupsOfUser("bruno")).thenThrow(new IllegalStateException("directory down"));
		assertEquals("", service.groupsOf("bruno"), "a user is exported without groups when they cannot be read");
	}

	@Test
	void theWorkbookHasTheColumnsAndTheNumbersAsNumbers() throws IOException {
		List<UsageLine> lines = LLMUsageExcelExportService.lines(
				List.of(priced(day("anna", 11, 1, "gpt", 1500, 300), "USD", 0.30, 0.10, 0.20),
						day("bruno", 11, 1, "gpt", 7, 3)).stream(),
				GROUPS);
		LLMUsageDrillDownLevel criteria = new LLMUsageDrillDownLevel();
		criteria.setYear(2026);

		byte[] bytes = LLMUsageExcelExportService.workbook(lines, criteria, null);

		try (XSSFWorkbook workbook = new XSSFWorkbook(new ByteArrayInputStream(bytes))) {
			Sheet sheet = workbook.getSheet("LLM usage");
			Row header = sheet.getRow(0);
			List<String> names = new ArrayList<>();
			header.forEach(cell -> names.add(cell.getStringCellValue()));
			assertEquals(List.of("User", "Groups", "Month", "Provider", "Model", "Currency", "Input tokens",
					"Output tokens", "Input cost", "Output cost", "Total cost"), names);
			Row anna = sheet.getRow(1);
			assertEquals("anna", anna.getCell(0).getStringCellValue());
			assertEquals("group of anna", anna.getCell(1).getStringCellValue());
			assertEquals("2026-11", anna.getCell(2).getStringCellValue());
			assertEquals(1500d, anna.getCell(6).getNumericCellValue());
			assertEquals(0.10, anna.getCell(8).getNumericCellValue(), 1e-12);
			assertEquals(0.30, anna.getCell(10).getNumericCellValue(), 1e-12);
			Row bruno = sheet.getRow(2);
			assertEquals("", bruno.getCell(10).toString(), "no cost is an empty cell, not zero");
			assertEquals(2, sheet.getLastRowNum());
			String notes = workbook.getSheet("Notes").getRow(1).getCell(1).getStringCellValue();
			assertTrue(notes.contains("year 2026"), notes);
		}
	}
}
