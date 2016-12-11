package netgloo.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;

import netgloo.com.java.File.FileFn;
import netgloo.models.City;
import netgloo.models.parsing.OpenWeatherMapObject;
import netgloo.services.CityServices;



@Controller
@RequestMapping(value = {"/"}, method = RequestMethod.GET)
public class IndexController {
	
	@Value("${APPID}") public String APPID;
	@Value("${openWeatherURL}") public String openWeatherURL;
	@Value("${units}") public String units;
	@Value("${language}") public String language;
	
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
//	@RequestMapping(method = RequestMethod.GET, produces = "text/html;charset=UTF-8")	
	@RequestMapping(method = RequestMethod.GET)	
    public String index(Model model) {
        model.addAttribute("cities", cityServices.findAll());
        System.out.println("@index: Found " + cityServices.count());
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
	
	@RequestMapping(value ="/addCity/add", method = RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.OK)
	public String addCity(@ModelAttribute("city") City city, BindingResult result, Model model) {
//		String cityName = "Belgrade";
		String cityName = city.getName();
		System.out.println("@addCity:: City name: " + cityName);
		if (cityName != null && !cityServices.isExistByName(cityName)) {
			City newCity = getCityInformation(cityName);
//			model.addAttribute("newCity", newCity);
		}else {
			System.out.println("This City already exist!");
		}
		
		return "/";
	}
	
	
	private City getCityInformation(String cityName)
	{
	    String uri = openWeatherURL + "q=" + cityName + "&units=" + units + "&language=" + language + "&appid=" + APPID;
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
	
	@RequestMapping("/testTree")
	public void createTree() {
		 TreeMap<Integer, String> tree = new TreeMap<Integer,String>();
	        tree.put(1, "Default");
	        tree.put(1, "Cities");
	        tree.put(2, "Chuang");
	        tree.put(3, "Frank");
	        tree.put(4, "David");
	        printTree(tree);
	        tree.put(51, "Steve");
	        tree.put(62, "Benny");
	        tree.put(11, "Kevin");
	        tree.put(23, "Paul");
	        printTree(tree);
	        
	        System.out.println("Tree ceilingEntry "+ tree.ceilingEntry(2)); 
	        System.out.println("Tree values "+ tree.values());
	        System.out.println("Tree values "+ tree.size());
	}
	
	@RequestMapping("/testTree2")
	public void createTree2() {
//		 TreeMap<List<String>, String> tree = new TreeMap<List<String>,String>();
		 TreeMap<String, String> tree = new TreeMap<String,String>();
//		 List<String> tags = new ArrayList<String>();
		 String tags;
		 tags = "Default";
	     tree.put(tags, "url1");
	     tags += " Day";
	     tree.put(tags, "url3");  
	     tags += " Sunny"; 
	     tree.put(tags, "url2");
	        printTree(tree);
	    
	        System.out.println("Tree values "+ tree.values());
	        System.out.println("Tree values "+ tree.size());
	        
	        System.out.println("Tree Default "+ tree.get("Default"));
	        System.out.println("Tree Sunny "+ tree.get("Default Day Sunny"));
	}
	
	@RequestMapping("/citiesFromFile")
	public void citiesFromFile() throws IOException {
		FileFn fileFn = new FileFn();
		List<String> result = fileFn.CSVReaderByColumn("openweather_city_list.txt", "\t", 1);
		result.forEach(item -> System.out.println(item));
	}
	
	@RequestMapping("/citiesExist")
	public void isCityExist() throws IOException {
		FileFn fileFn = new FileFn();
		Boolean result = fileFn.CSVReaderIsStringExist("openweather_city_list.txt", "\t", "Belgrade");
		System.out.println("Is city exist: " + result );
	}
	
	public void printTree(Map<?, ?> map){
        for (Object key: map.keySet()){
            String value= (String) map.get(key);
            System.out.println("At key= "+ key+ ", the value is "+ value);
        }
    }

}
