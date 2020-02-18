package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import connection.FileDataProvider;
import connection.NBPDataProvider;
import entity.currency.Currency;
import parser.StringtoCurrencyParser;
import service.CurrencyService;

public class ExchangeControllerTest {

	private final LocalDate dateOfcurrency = LocalDate.of(2020, 02, 9);

	private final String jsonExample = "{\"table\":\"C\",\"currency\":\"OJRO_Z_PLIKU\",\"code\":\"EUR\",\"rates\":[{\"no\":\"029/C/NBP/2020\",\"effectiveDate\":\"2020-02-12\",\"bid\":4.2081,\"ask\":4.2931}]}";
	private final String filePath = "C:\\Users\\daniel.jedra\\Documents\\JSON_created.txt";
//	private final String filePath = "E:JSON_created.txt";

	private final BigDecimal amountForExchange = BigDecimal.valueOf(15);

	@Test
	public void getExchangeRateForDate() {
		CurrencyService service = new CurrencyService(new StringtoCurrencyParser(), new NBPDataProvider());
		Currency exchangeRateForDate = service.getParsedData("A", "EUR", dateOfcurrency);
		Assert.assertEquals(dateOfcurrency.minusDays(2).toString(),
				exchangeRateForDate.getRates().get(0).getEffectiveDate());
	}

//	@Test
//	public void getCurrentExchangeRate() {
//		Currency exchangeRate = controller.getCurrentExchangeRate(ActualExchangeRateTableTypes.A,
//				CurrencyCode.SWEDISH_KORONA);
//		Assert.assertNotNull(exchangeRate);
//		Assert.assertNotNull(exchangeRate.getRate());
//	}
//
//	@Test
//	public void getExchangeRates() {
//		Example exchangeRates = controller.getExchangeRatesTable(ExchangeRatesTableTypes.A);
//		Assert.assertNotNull(exchangeRates);
//		Assert.assertNotNull(exchangeRates.getRates());
//		Assert.assertNotEquals(exchangeRates.getRates(), Collections.EMPTY_LIST);
//		List<String> collect = Stream.of(ExchangeRatesTableTypes.values()).map(d -> d.toString())
//				.collect(Collectors.toList());
//		Assert.assertTrue(collect.contains(exchangeRates.getTable()));
//	}
//
	@Test
	public void getExchangeRateFromFile() {
		createFile();
		CurrencyService service = new CurrencyService(new StringtoCurrencyParser(), new FileDataProvider());
		Currency exchangeRateFromFile = service.getParsedData("A", "EUR", dateOfcurrency);
		Currency converted = new StringtoCurrencyParser().parse(jsonExample);
		Assert.assertEquals(exchangeRateFromFile, converted);
	}

	private void createFile() {
		String path = (filePath);
		Path file = Paths.get(path);
		try {
			Files.write(file, Arrays.asList(jsonExample), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
//
//	@Test
//	public void exchange() {
//		Currency exchangeRate = controller.getCurrentExchangeRate(ActualExchangeRateTableTypes.A,
//				CurrencyCode.SWISS_FRANC);
//		BigDecimal rate = exchangeRate.getRate();
//		BigDecimal countedExchangedValue = Calculations.exchange(amountForExchange, rate);
//		BigDecimal exchanged = controller.exchange(ActualExchangeRateTableTypes.A, CurrencyCode.SWISS_FRANC,
//				amountForExchange);
//		Assert.assertEquals(countedExchangedValue, exchanged);
//	}

}
