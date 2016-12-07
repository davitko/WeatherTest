package netgloo.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

public class IndexController {
	
	@Value("${APPID}") public String APPID;
	@Value("${openWeatherURL}") public String openWeatherURL;

	@RequestMapping("/")
	@ResponseBody
	public String index() {
		return "index";
	}
	
	@RequestMapping("/addCity")
	public String addCity() {
		String cityName = null;
		getCityInformation(cityName);
		return "index";
	}
	
	private void getCityInformation(String cityName)
	{
	    String uri = openWeatherURL + "q=" + cityName + "&appid=" + APPID;
	     
	    RestTemplate restTemplate = new RestTemplate();
	    String result = restTemplate.getForObject(uri, String.class);
	     
	    System.out.println(result);
	}


}
