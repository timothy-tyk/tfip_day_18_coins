package cointracker.application.model;

import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;

public class Data {
  private Stats stats;
  private List<Coin> coins;

  public Stats getStats() {
    return stats;
  }
  public void setStats(Stats stats) {
    this.stats = stats;
  }
  public List<Coin> getCoins() {
    return coins;
  }
  public void setCoins(List<Coin> coins) {
    this.coins = coins;
  }

  // Methods
  public JsonObjectBuilder createJson(){
    JsonArrayBuilder arrBld = Json.createArrayBuilder();
    List<JsonObjectBuilder> listOfCoins = this.getCoins().stream().map(coin -> coin.toJson()).toList();
    for(JsonObjectBuilder job : listOfCoins){
      arrBld.add(job);
    }
    return Json.createObjectBuilder()
          .add("stats", this.getStats().toJson())
          .add("coins", arrBld);
  }
  
}
