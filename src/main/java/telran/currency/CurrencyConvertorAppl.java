package telran.currency;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import telran.currency.service.CurrencyConvertor;
import telran.currency.service.FixerApiPerDay;
import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;
import telran.view.SystemInputOutput;

public class CurrencyConvertorAppl {

    public static void main(String[] args) {
        
        InputOutput io = new SystemInputOutput();

        CurrencyConvertor currencyConvertor = new FixerApiPerDay();

        Item[] menuItems = {
            Item.of("Convert currency", input -> {
                String codeFrom = input.readString("Enter currency code (e.g., USD): ");
                String codeTo = input.readString("Enter target currency code (e.g., EUR): ");
                int amount = input.readInt("Enter amount: ", "Please enter a valid amount!");
                Set<String> allCodes = currencyConvertor.getAllCodes();

                if (!allCodes.contains(codeFrom)|| !allCodes.contains(codeTo)) {
                    input.writeLine("Currency code  is not available in the list.");
                    
                }
                else {
                	 double result = currencyConvertor.convert(codeFrom, codeTo, amount);
                     input.writeLine(String.format("%d %s = %.2f %s", amount, codeFrom, result, codeTo));
                }

               
            }),
            Item.of("View all available currency codes", input -> {
            	 Set<String> sortedCodes = new TreeSet<>(currencyConvertor.getAllCodes());
                 input.writeLine("Available currencies: " + String.join(", ", sortedCodes));
            }),
            Item.of("View list of currencies", input -> {
                List<Item> items = CurrencyItems.getItems(currencyConvertor);
                items.forEach(item -> item.perform(input));
            }),
            Item.ofExit()
        };

        Menu menu = new Menu("Currency Conversion Menu", menuItems);
        menu.perform(io);
    }
}
