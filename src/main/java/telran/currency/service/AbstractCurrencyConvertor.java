package telran.currency.service;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class AbstractCurrencyConvertor implements CurrencyConvertor {
	protected Map<String, Double> rates;

	@Override
	public List<String> strongestCurrencies(int amount) {
		return getCurrencies(amount, Map.Entry.comparingByValue());
	}

	private List<String> getCurrencies(int amount, Comparator<Map.Entry<String, Double>> comparator) {
        return rates.entrySet().stream()
                .sorted(comparator)
                .limit(amount)
                .map(Map.Entry::getKey)
                .toList();
    }

	@Override
	public List<String> weakestCurrencies(int amount) {
		return getCurrencies(amount, Map.Entry.<String, Double>comparingByValue().reversed());
	}

	@Override
	public double convert(String codeFrom, String codeTo, int amount) {
		double rateFrom = rates.getOrDefault(codeFrom, 1.0);
		double rateTo = rates.getOrDefault(codeTo, 1.0);
		return amount * (rateTo / rateFrom);
	}

	@Override
	public HashSet<String> getAllCodes() {

		return new HashSet<>(rates.keySet());
	}

}