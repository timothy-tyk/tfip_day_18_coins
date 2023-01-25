package cointracker.application.model;

import java.io.Serializable;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;

public class Stats implements Serializable{
  private int total;
  private int totalCoins;
  private int totalMarkets;
  private int totalExchanges;
  private String totalMarketCap;
  private String total24hVolume;

  public int getTotal() {
    return total;
  }
  public void setTotal(int total) {
    this.total = total;
  }
  public int getTotalCoins() {
    return totalCoins;
  }
  public void setTotalCoins(int totalCoins) {
    this.totalCoins = totalCoins;
  }
  public int getTotalMarkets() {
    return totalMarkets;
  }
  public void setTotalMarkets(int totalMarkets) {
    this.totalMarkets = totalMarkets;
  }
  public int getTotalExchanges() {
    return totalExchanges;
  }
  public void setTotalExchanges(int totalExchanges) {
    this.totalExchanges = totalExchanges;
  }
  public String getTotalMarketCap() {
    return totalMarketCap;
  }
  public void setTotalMarketCap(String totalMarketCap) {
    this.totalMarketCap = totalMarketCap;
  }
  public String getTotal24hVolume() {
    return total24hVolume;
  }
  public void setTotal24hVolume(String total24hVolume) {
    this.total24hVolume = total24hVolume;
  }

  // Methods

  public JsonObjectBuilder toJson(){
    return Json.createObjectBuilder()
            .add("total",this.getTotal())
            .add("totalCoins", this.getTotalCoins())
            .add("totalMarkets", this.getTotalMarkets())
            .add("totalExchanges", this.getTotalExchanges())
            .add("totalMarketCap", this.getTotalMarketCap())
            .add("total24hVolume", this.getTotal24hVolume());
  }
  


}
