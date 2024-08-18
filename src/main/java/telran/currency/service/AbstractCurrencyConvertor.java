package telran.currency.service;

import java.util.*;

public class AbstractCurrencyConvertor implements CurrencyConvertor{
    protected Map<String, Double> rates;
    @Override
    public List<String> strongestCurrencies(int amount) {
        return getCurrencies(amount, Comparator.naturalOrder());
    }

    private List<String> getCurrencies(int amount, Comparator<Double> comparator) {
        return  rates.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(comparator))
                .limit(amount)
                .map(Map.Entry::getKey)
                .toList();
    }

    @Override
    public List<String> weakestCurrencies(int amount) {
        return getCurrencies(amount, Comparator.reverseOrder());
    }

    @Override
    public double convert(String codeFrom, String codeTo, int amount) {
        double fromRate = getRates(codeFrom);
        double toRate = getRates(codeTo);
        return amount*(toRate/fromRate);
    }

    private Double getRates(String code) {
        Double rate = rates.get(code);
        if(rate == null){
            throw new RuntimeException("Unknown currency code "+code);
        }
        return  rate;
    }

    @Override
    public HashSet<String> getAllCodes() {
        return new HashSet<>(rates.keySet());
    }
}
