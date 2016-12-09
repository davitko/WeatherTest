package netgloo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import netgloo.models.City;
import netgloo.models.parsing.OpenWeatherMapObject;
import netgloo.services.CityServices;



@Controller
@RequestMapping(value = {"/"}, method = RequestMethod.GET)
public class IndexController {
	
	@Value("${APPID}") public String APPID;
	@Value("${openWeatherURL}") public String openWeatherURL;
	
	@Autowired
	CityServices cityServices;
	
	
	public IndexController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param model
	 * @return This string will be suffixed and prefixed with suffix and prefix defined in 
	 * view resolver(see spring-servlet.xml above) to form the real view file name.
	 */
	@RequestMapping(method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public String index(Model model) {
//        model.addAttribute("userForm", new User());

        return "index";
    }
	
	
	
	@RequestMapping(value = {"/wellcome"}, method = RequestMethod.GET)
    public String wellcome(Model model) {
//        model.addAttribute("userForm", new User());

        return "wellcome";
    }
	
	@RequestMapping("/myjsp")
    public String hello(Model model, @RequestParam(value="name", required=false, defaultValue="World") String name) {
        model.addAttribute("name", name);
        return "myJsp";
    }
	
	@RequestMapping(value ="/addCity", method = RequestMethod.GET)
	public String addCity(Model model) {
		String cityName = "Belgrade";
		City newCity = getCityInformation(cityName);
		model.addAttribute("newCity", newCity);
		return "index";
	}
	
	
	private City getCityInformation(String cityName)
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
	    return tmpCity;
	}

}
