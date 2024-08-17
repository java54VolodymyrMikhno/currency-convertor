package telran.currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import telran.currency.service.CurrencyConvertor;

import telran.view.Item;

public class CurrencyItems {
    private static CurrencyConvertor currencyConvertor;

    public static List<Item> getItems(CurrencyConvertor currencyConvertor) {
        CurrencyItems.currencyConvertor = currencyConvertor;
        HashSet<String> codes = new HashSet<>(currencyConvertor.getAllCodes());

        return List.of(
            Item.of("Convert currency", io -> {
                String codeFrom = io.readStringOptions("Enter currency code (e.g., USD): ", "Invalid currency code!", codes);
                String codeTo = io.readStringOptions("Enter target currency code (e.g., EUR): ", "Invalid currency code!", codes);
                int amount = io.readInt("Enter amount: ", "Invalid amount!");
                double result = currencyConvertor.convert(codeFrom, codeTo, amount);
                io.writeLine(String.format("%d %s = %.2f %s", amount, codeFrom, result, codeTo));
            }),
            Item.of("View all available currency codes", io -> {
                io.writeLine("Available currencies: " + String.join(", ", codes));
            }),
            Item.of("View strongest currencies", io -> {
                int count = io.readInt("Enter the number of strongest currencies to view: ", "Invalid number!");
                currencyConvertor.strongestCurrencies(count).forEach(code -> {
                    BigDecimal valueInEur = BigDecimal.valueOf(currencyConvertor.convert(code, "EUR", 1))
                            .setScale(2, RoundingMode.HALF_UP);
                    io.writeLine(code + ": " + valueInEur + " EUR");
                });
            }),
            Item.of("View weakest currencies", io -> {
                int count = io.readInt("Enter the number of weakest currencies to view: ", "Invalid number!");
                currencyConvertor.weakestCurrencies(count).forEach(code -> {
                    BigDecimal valueInEur = BigDecimal.valueOf(currencyConvertor.convert(code, "EUR", 1))
                            .setScale(8, RoundingMode.HALF_UP);
                    
                    io.writeLine(code + ": " + valueInEur.stripTrailingZeros().toPlainString() + " EUR");
                });
            }),
            Item.ofExit()
        );
    }
}
