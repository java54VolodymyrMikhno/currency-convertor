package telran.currency;

import telran.currency.service.CurrencyConvertor;
import telran.view.*;
import java.util.*;


public class CurrencyItems {
    private static CurrencyConvertor currencyConvector;

    public static Item[] getItems(CurrencyConvertor currencyConvector) {
        CurrencyItems.currencyConvector = currencyConvector;
        Item[] items = {
                Item.of("Convert currency", CurrencyItems::convert),
                Item.of("View strongest currencies", io -> showCurrencies(io, true)),
                Item.of("View weakest currencies", io -> showCurrencies(io, false)),
                Item.of("View all available currency codes", CurrencyItems::allCodes),
                Item.ofExit()
        };
        return items;
    }

    private static void showCurrencies(InputOutput io, boolean isStrongest) {
        String type = isStrongest ? "strongest" : "weakest";
        int count =
                io.readNumberRange
                        (String.format("Enter the number of %s currencies to view: ", type),
                                "Invalid number!", 1, currencyConvector.getAllCodes().size()).intValue();
        io.writeLine(String.format("The %s currencies:", type));
        List<String> currencies = isStrongest ?
                currencyConvector.strongestCurrencies(count)
                : currencyConvector.weakestCurrencies(count);
        currencies.forEach(currency -> {
            double valueInEur = currencyConvector.convert(currency, "EUR", 1);
            String format = isStrongest ? "%.2f" : "%.8f";

            io.writeLine(String.format("%s: "+format+" EUR", currency, valueInEur));
        });

    }

    private static void allCodes(InputOutput io) {
        Set<String> sortedCode = new TreeSet<>(currencyConvector.getAllCodes());
        io.writeLine("Available currency codes: " + String.join(" ,", sortedCode));
    }



    private static void convert(InputOutput io) {
        String codeFrom = io.readStringOptions("Enter currency code(e.g., USD): ", "Invalid currency code!", currencyConvector.getAllCodes());
        String codeTo = io.readStringOptions("Enter target code(e.g., USD): ", "Invalid currency code!", currencyConvector.getAllCodes());
        int amount = io.readNumberRange("Enter amount: ", "Invalid amount", 1, Integer.MAX_VALUE).intValue();
        double result = currencyConvector.convert(codeFrom, codeTo, amount);
        io.writeLine(String.format("%d %s = %.2f %s", amount, codeFrom, result, codeTo));
    }
}
