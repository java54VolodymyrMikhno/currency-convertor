package telran.currency;

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
        Menu menu = new Menu("Currency Conversion Menu", CurrencyItems.getItems(currencyConvertor).toArray(Item[]::new));
        menu.perform(io);
    }
}
