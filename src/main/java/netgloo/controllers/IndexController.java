package netgloo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import netgloo.models.City;
import netgloo.models.parsing.OpenWeatherMapObject;
import netgloo.services.CityServices;



@Controller
public class IndexController {
	
	@Value("${APPID}") public String APPID;
	@Value("${openWeatherURL}") public String openWeatherURL;
	
	@Autowired
	CityServices cityServices;

	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
//        model.addAttribute("userForm", new User());

        return "index";
    }
	
	@RequestMapping("/addCity")
	public String addCity() {
		String cityName = "Belgrade";
		getCityInformation(cityName);
		return "index";
	}
	
	
	private void getCityInformation(String cityName)
	{
	    String uri = openWeatherURL + "q=" + cityName + "&appid=" + APPID;
	     
	    RestTemplate restTemplate = new RestTemplate();
//	    String result = restTemplate.getForObject(uri, String.class);
	    OpenWeatherMapObject result = restTemplate.getForObject(uri, OpenWeatherMapObject.class);
	    City tmpCity = new City();
	    tmpCity.setCountry(result.getSys().getCountry());
	    tmpCity.setDeleted(false);
	    tmpCity.setHumidity(result.getMain().getHumidity().toString());
	    tmpCity.setName(result.getName());
	    tmpCity.setLatitude(result.getCoord().getLat());
	    tmpCity.setLongitude(result.getCoord().getLon());
	    tmpCity.setPressure(result.getMain().getPressure().toString());
	    tmpCity.setServerid(result.getId());
	    tmpCity.setSunrise(result.getSys().getSunrise());
	    tmpCity.setSunset(result.getSys().getSunset());
	    tmpCity.setTemperature(result.getMain().getTemp().toString());
	    tmpCity.setVisibility(result.getVisibility().toString());
	    tmpCity.setWind_speed(result.getWind().getSpeed().toString());
	    cityServices.create(tmpCity);
	     
//	    System.out.println(result);
	    System.out.println(result.toString());
	    
	}


}
