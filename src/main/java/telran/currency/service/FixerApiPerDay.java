package telran.currency.service;

import org.json.JSONObject;

import java.net.URI;
import java.net.http.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;


public class FixerApiPerDay extends AbstractCurrencyConvertor {
    protected String uriString = "https://data.fixer.io/api/latest?access_key=1b5b8a5320da1eb03907accb1e9c3403";
    private Instant latestRatesTimestamp;

    public FixerApiPerDay() {
        rates = getRates();
    }

    protected HashMap<String, Double> getRates() {
        HashMap<String, Double> ratesMap = new HashMap<>();

        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder(new URI(uriString)).build();
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            JSONObject jsonObject = new JSONObject(httpResponse.body());
            long timestampSeconds = jsonObject.getLong("timestamp");
            latestRatesTimestamp = Instant.ofEpochSecond(timestampSeconds);
            JSONObject jsonRates = jsonObject.getJSONObject("rates");
            jsonRates.keySet().forEach(key -> ratesMap.put(key, jsonRates.getDouble(key)));

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return ratesMap;
    }

    @Override
    public List<String> strongestCurrencies(int amount) {
        refresh();
        return super.strongestCurrencies(amount);
    }

    private void refresh() {
        if (latestRatesTimestamp == null || ChronoUnit.HOURS.between(latestRatesTimestamp, Instant.now()) > 24) {
            rates = getRates();
        }
    }

    @Override
    public List<String> weakestCurrencies(int amount) {
        refresh();
        return super.weakestCurrencies(amount);
    }

}
