package cointracker.application.model;

import java.io.Serializable;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Response implements Serializable{
  private String status;
  private Data data;

  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
  }
  public Data getData() {
    return data;
  }
  public void setData(Data data) {
    this.data = data;
  }

  // Methods
  public JsonObject createJson(String json){
    System.out.println(json);
    return Json.createObjectBuilder()
            .add("status", this.getStatus())
            .add("data", this.getData().createJson())
            .build();
  }

  
}
