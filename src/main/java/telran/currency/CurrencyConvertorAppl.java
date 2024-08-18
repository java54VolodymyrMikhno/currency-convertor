package telran.currency;

import telran.currency.service.CurrencyConvertor;
import telran.currency.service.FixerApiPerDay;
import telran.view.Item;
import telran.view.Menu;
import telran.view.SystemInputOutput;

import java.util.List;

public class CurrencyConvertorAppl {
    public static void main(String[] args) {
        CurrencyConvertor convertor = new FixerApiPerDay();
        List<Item> menuItems = CurrencyItems.getItems(convertor);
        menuItems.add(Item.ofExit());
        Menu menu = new Menu("Currency Conversion Menu",menuItems.toArray(Item[]::new));
        menu.perform(new SystemInputOutput());
    }
}
