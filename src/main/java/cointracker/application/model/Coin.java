package cointracker.application.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;

public class Coin implements Serializable{
  private String symbol;
  private String name;
  private String iconUrl;
  private String price;
  private String change;
  
  // Getters and Setters
  public String getSymbol() {
    return symbol;
  }
  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getIconUrl() {
    return iconUrl;
  }
  public void setIconUrl(String iconUrl) {
    this.iconUrl = iconUrl;
  }
  public String getPrice() {
    return price;
  }
  public void setPrice(String price) {
    this.price = price;
  }
  public String getChange() {
    return change;
  }
  public void setChange(String change) {
    this.change = change;
  }

  // Methods

  public static Coin create(String json){
    Coin coin = new Coin();
    try(InputStream is = new ByteArrayInputStream(json.getBytes())) {
      JsonReader jsonReader = Json.createReader(is);
      JsonObject jsonObj = jsonReader.readObject();
      coin.setSymbol(jsonObj.getString("symbol"));
      coin.setName(jsonObj.getString("name"));
      coin.setIconUrl(jsonObj.getString("iconUrl"));
      coin.setPrice(jsonObj.getString("price"));
      coin.setChange(jsonObj.getString("change"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return coin;
  }

  public JsonObjectBuilder toJson(){
    return Json.createObjectBuilder()
            .add("symbol", this.getSymbol())
            .add("name", this.getName())
            .add("iconUrl", this.getIconUrl())
            .add("price", this.getPrice())
            .add("change", this.getChange());
  }

  
}
