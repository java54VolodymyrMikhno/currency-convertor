package telran.currency.service;

import java.util.*;
import java.util.stream.Collectors;

public class AbstractCurrencyConvertor implements CurrencyConvertor {
protected Map<String, Double> rates; //key - currency ISO code;
//value - amount of code's units in 1 EUR
	@Override
	public List<String> strongestCurrencies(int amount) {
		// TODO Auto-generated method stub
		return rates.entrySet().stream()
				.sorted(Map.Entry.<String,Double>comparingByValue().reversed())
				.limit(amount)
				.map(Map.Entry::getKey)
				.toList();
	}

	@Override
	public List<String> weakestCurrencies(int amount) {
		return rates.entrySet().stream()
	            .sorted(Map.Entry.comparingByValue())
	            .limit(amount)
	            .map(Map.Entry::getKey)
	            .collect(Collectors.toList());
	}

	@Override
	public double convert(String codeFrom, String codeTo, int amount) {
		double rateFrom = rates.getOrDefault(codeFrom, 1.0);
		double rateTo = rates.getOrDefault(codeTo, 1.0);
		return amount*(rateTo/rateFrom);
	}

	@Override
	public HashSet<String> getAllCodes() {
		
		return new HashSet<>(rates.keySet());
	}

}