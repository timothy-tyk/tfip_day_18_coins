package cointracker.application.model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Market implements Serializable{
  private List<Coin> market;

  public List<Coin> getMarket() {
    return market;
  }

  public void setMarket(List<Coin> market) {
    this.market = market;
  }

  // Methods

  public static Market createJson(String json){
    Market mkt = new Market();
    try(InputStream is = new ByteArrayInputStream(json.getBytes())) {
      JsonReader jsonReader = Json.createReader(is);
      JsonObject jsonObjList = jsonReader.readObject(); 
      // System.out.println("objList: "+jsonObjList);
      mkt.market = jsonObjList.getJsonArray("coins").stream().map(coin->Coin.create(coin.toString())).toList();
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    return mkt;

    // JsonArrayBuilder arrBld = Json.createArrayBuilder();
    // List<JsonObjectBuilder> coins = this.getMarket().stream().map(coin-> coin.toJson()).toList();
    // for (JsonObjectBuilder coin : coins){
    //   arrBld.add(coin);
    // }
    // return Json.createObjectBuilder()
    //         .add("coins",arrBld)
    //         .build();
  }
}
