package telran.currency.service;

import java.net.URI;
import java.net.http.*;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

import org.json.JSONObject;

public class FixerApiPerDay extends AbstractCurrencyConvertor {
   protected String uriString = "https://data.fixer.io/api/latest?access_key=1b5b8a5320da1eb03907accb1e9c3403";
   private Instant lastRatesTimestamp;
   public FixerApiPerDay() {
	   rates = getRates();
   }
protected HashMap<String, Double> getRates() {
	HashMap<String, Double> ratesMap = new HashMap<>();
	try {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(new URI(uriString)).build();
        HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
        JSONObject jsonObject = new JSONObject(response.body());
        long timestampSeconds = jsonObject.getLong("timestamp");
        lastRatesTimestamp = Instant.ofEpochSecond(timestampSeconds);
        JSONObject jsonRates = jsonObject.getJSONObject("rates");

        for (String key : jsonRates.keySet()) {
            ratesMap.put(key, jsonRates.getDouble(key));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return ratesMap;
}


@Override
public List<String> strongestCurrencies(int amount) {
	refresh();
	return super.strongestCurrencies(amount);
}
@Override
public List<String> weakestCurrencies(int amount) {
	refresh();
	return super.weakestCurrencies(amount);
}
@Override
public double convert(String codeFrom, String codeTo, int amount) {
	refresh();
	return super.convert(codeFrom, codeTo, amount);
}
private void refresh() {
	if(lastRatesTimestamp == null || ChronoUnit.HOURS.between(lastRatesTimestamp, Instant.now())>24) {
		rates = getRates();
	}
	
}
}