package cointracker.application.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;

import cointracker.application.model.Coin;
import cointracker.application.model.Data;
import cointracker.application.model.Market;
import cointracker.application.model.Response;


@Service
public class CoinServices {
  
  @Value("${COIN_RANKING_API_KEY}")
  private String COIN_RANKING_API_KEY; //from Railway Variables
  
  private static final String COIN_RANKING_LIST_URL = "https://coinranking1.p.rapidapi.com/coins";
  
  public Optional<Market> getListOfCoins(){
    // final String COIN_RANKING_API_KEY = System.getenv("COIN_RANKING_API_KEY"); // from System env
    final String API_HOST = "coinranking1.p.rapidapi.com";
    final String refCurUuid = "yhjMzLPhuIDl";
    final String timePeriod = "24h";
    final String limit = "10";
    String coinListUrl = UriComponentsBuilder.fromUriString(COIN_RANKING_LIST_URL)
                          .queryParam("referenceCurrencyUuid", refCurUuid)
                          .queryParam("timePeriod", timePeriod)
                          .queryParam("limit", limit)
                          .toUriString();
    // Create Headers
    final HttpHeaders headers = new HttpHeaders();
    headers.set("X-RapidAPI-Key", COIN_RANKING_API_KEY);
    headers.set("X-RapidAPI-Host", API_HOST);

    // Create Entity with Header
    final HttpEntity<String> entity = new HttpEntity<String>(headers);
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> response = restTemplate.exchange(coinListUrl,HttpMethod.GET,entity,String.class);

    // Response response = Response.createJson(response.getBody());
    String listOfCoins = response.getBody();

    // Gson stuff
    Gson gson = new Gson();
    Response res = gson.fromJson(listOfCoins, Response.class);
    Data data = res.getData();
    
    // Change List of Coin to string to simulate getting back string from API lol
    String dataStr = gson.toJson(data);
    // System.out.println(coinsStr);

    Market coinMarket = (Market) Market.createJson(dataStr);


    if(coinMarket == null){
      return Optional.empty();
    }
    return Optional.of(coinMarket);
  }

  @Autowired
  RedisTemplate<String, Object> redisTemplate;
  public int saveToRedis(final Coin coin){
    System.out.println(coin.getSymbol());
    String symbol = coin.getSymbol();
    redisTemplate.opsForValue().set(symbol, coin);
    Coin fromRedis = (Coin) redisTemplate.opsForValue().get(symbol);
    if(fromRedis == null){
      return 0;
    }
    else{
      return 1;
    }
  }

  public List<Coin> retrieveFromRedis(){
    List<Coin> userCoins = new ArrayList<Coin>();
    Set<String> keys = redisTemplate.keys("*");
  
    for(String key : keys){
      Coin coin = (Coin) redisTemplate.opsForValue().get(key);
      userCoins.add(coin);
    }
    return userCoins;
  }
  

}
