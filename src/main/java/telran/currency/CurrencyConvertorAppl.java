package telran.currency;

import telran.currency.service.CurrencyConvertor;
import telran.currency.service.FixerApiPerDay;
import telran.view.Item;
import telran.view.Menu;
import telran.view.SystemInputOutput;


public class CurrencyConvertorAppl {
    public static void main(String[] args) {
        CurrencyConvertor convertor = new FixerApiPerDay();
        Item[] menuItems = CurrencyItems.getItems(convertor);
        Menu menu = new Menu("Currency Conversion Menu",menuItems);
        menu.perform(new SystemInputOutput());
    }
}
