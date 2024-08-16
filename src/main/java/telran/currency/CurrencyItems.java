package telran.currency;

import java.util.List;
import java.util.stream.Collectors;

import telran.currency.service.CurrencyConvertor;
import telran.view.Item;

public class CurrencyItems {
private static CurrencyConvertor currencyConvertor;
public static List<Item> getItems(CurrencyConvertor currencyConvertor) {
	CurrencyItems.currencyConvertor = currencyConvertor;
	
	return currencyConvertor.getAllCodes().stream()
	        .map(code -> Item.of(code, io -> {
	            double valueInEur = currencyConvertor.convert(code, "EUR", 1);
	            System.out.printf("1 %s = %.2f EUR%n", code, valueInEur);
	        }))
	        .collect(Collectors.toList());

}
}