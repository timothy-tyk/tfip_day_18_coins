package cointracker.application.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cointracker.application.model.Coin;
import cointracker.application.model.Market;
import cointracker.application.services.CoinServices;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping()
public class CoinController {

  @Autowired
  CoinServices coinServices;
  
  @GetMapping(path = "/coins", produces = MediaType.APPLICATION_JSON_VALUE)
  public String getCoinMarket(Model model){
   Optional<Market> coinsList = coinServices.getListOfCoins();
  //  System.out.println(coinsList.get().getMarket().get(0).getName());
   model.addAttribute("market", coinsList.get().getMarket());
    return "market";
  }

 @PostMapping(path = "/coins")
 public String addToCoinsList(Coin coin, Model model, HttpServletResponse response){
  //  Add to Redis
  int result = coinServices.saveToRedis(coin);
  if (result==0){
    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    return "redirect:/market";
  }
  else{
    response.setStatus(HttpServletResponse.SC_CREATED);
    return "redirect:/list";
  }
 }

  @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
  public String getCoinList(Model model) {
    // Get from Redis
    List<Coin> coins = coinServices.retrieveFromRedis();
    model.addAttribute("coins", coins);
      return "coinList";
  }
  

}
