package netgloo.controllers;

import java.io.IOException;
import java.time.Instant;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;

import netgloo.com.java.File.FileFn;
import netgloo.com.java.Integer.IntegerFn;
import netgloo.com.java.Time.TimeFn;
import netgloo.models.CarouselPack;
import netgloo.models.City;
import netgloo.models.CityTimes;
import netgloo.models.WeatherBackground;
import netgloo.models.WeatherBgImgUrl;
import netgloo.models.parsing.OpenWeatherMapObject;
import netgloo.repository.CityRepository;
import netgloo.services.CityServices;
import netgloo.services.FillApp;



@Controller
@RequestMapping(value = {"/"}, method = RequestMethod.GET)
public class IndexController {

	@Value("${APPID}") public String APPID;
	@Value("${openWeatherURL}") public String openWeatherURL;
	@Value("${units}") public String units;
	@Value("${language}") public String language;

	public City lastCity;
	public City detailCity;

	@Autowired
	CityServices cityServices;
	@Autowired
	FillApp fillApp;
	@Autowired
	TimeFn timeFn;


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

		//		City lastCity = cityServices.getLast();
		//		WeatherBackground weatherBg = new WeatherBackground();
		//		if (lastCity != null)
		//			weatherBg.setDisplay("show");
		////			weatherBg.setDisplay("hide");
		//		else 
		//			weatherBg.setDisplay("hide");
		//		String imgName = createImageName(lastCity);
		//		
		//		if (imgName != null)
		//			weatherBg.setUrl(imgName);
		//		model.addAttribute("weatherBg", weatherBg);

		IntegerFn integerFn = new IntegerFn(); 
		Integer carouselIndex = integerFn.getRandomNumberInRange(0, 4);
		CarouselPack carouselPack = fillApp.carouselImg.get(carouselIndex);
		model.addAttribute("carouselPack", carouselPack);

		return "index";
	}

	@RequestMapping(value = {"/index"}, method = RequestMethod.GET)	
	public String index2(Model model) {
		model.addAttribute("cities", cityServices.findAll());
		System.out.println("@index: Found " + cityServices.count());

		City lastCity = cityServices.getLast();
		WeatherBackground weatherBg = new WeatherBackground();
		if (lastCity != null)
			//			weatherBg.setDisplay("show");
			weatherBg.setDisplay("hide");
		else 
			weatherBg.setDisplay("hide");
		String imgName = createImageName(lastCity);

		if (imgName != null)
			weatherBg.setUrl(imgName);
		model.addAttribute("weatherBg", weatherBg);

		IntegerFn integerFn = new IntegerFn(); 
		Integer carouselIndex = integerFn.getRandomNumberInRange(0, 4);
		CarouselPack carouselPack = fillApp.carouselImg.get(carouselIndex);
		model.addAttribute("carouselPack", carouselPack);

		return "index";
	}

	@RequestMapping(value = {"/details"}, method = RequestMethod.GET)	
	public String details(Model model) {

		model.addAttribute("detailCity", detailCity);

		CityTimes cityTimes = new CityTimes();
		cityTimes.setDateTime(timeFn.fromUnixToSpecificString(detailCity.getDateTime().longValue(), "hh a"));
		cityTimes.setSunrise(timeFn.fromUnixToSpecificString(detailCity.getSunrise().longValue(), "hh:mm"));
		cityTimes.setSunset(timeFn.fromUnixToSpecificString(detailCity.getSunset().longValue(), "hh:mm"));
		model.addAttribute("cityTimes", cityTimes);

		WeatherBackground weatherBg = new WeatherBackground();
		if (detailCity != null)
			weatherBg.setDisplay("show");
		else 
			weatherBg.setDisplay("hide");
		String imgName = createImageName(detailCity);

		if (imgName != null)
			weatherBg.setUrl(imgName);
		else
			weatherBg.setUrl("default.jpg");
		model.addAttribute("weatherBg", weatherBg);
		return "details";
	}

	@RequestMapping(value = {"/details/{id}"}, method = RequestMethod.GET)	
	public String detailsSelect(@PathVariable("id") long id, Model model) {
		detailCity = cityServices.findById(id);
		model.addAttribute("detailCity", detailCity);

		//		CityTimes cityTimes = new CityTimes();
		//		cityTimes.setDateTime(timeFn.fromUnixToSpecificString(detailCity.getDateTime().longValue(), "hh a"));
		//		cityTimes.setSunrise(timeFn.fromUnixToSpecificString(detailCity.getSunrise().longValue(), "hh:mm"));
		//		cityTimes.setSunset(timeFn.fromUnixToSpecificString(detailCity.getSunset().longValue(), "hh:mm"));
		//		model.addAttribute("cityTimes", cityTimes);
		//		
		//		WeatherBackground weatherBg = new WeatherBackground();
		//		if (lastCity != null)
		//			weatherBg.setDisplay("show");
		//		else 
		//			weatherBg.setDisplay("hide");
		//		String imgName = createImageName(detailCity);
		//		
		//		if (imgName != null)
		//			weatherBg.setUrl(imgName);
		//		else
		//			weatherBg.setUrl("default.jpg");
		//		model.addAttribute("weatherBg", weatherBg);
		return "redirect:/details";
	}

	@RequestMapping("/remove/{id}")
	public String removeCity(@PathVariable("id") long id){
		cityServices.delete(id);
		return "redirect:/";
	}



	//	@RequestMapping(value = {"/wellcome"}, method = RequestMethod.GET)
	//	public String wellcome(Model model) {
	//		//        model.addAttribute("userForm", new User());
	//
	//		return "wellcome";
	//	}
	//
	//	@RequestMapping("/myjsp")
	//	public String hello(Model model, @RequestParam(value="name", required=false, defaultValue="World") String name) {
	//		model.addAttribute("name", name);
	//		return "myJsp";
	//	}
	
	@RequestMapping(value ="/add", method = RequestMethod.GET)
	@ResponseStatus(value=HttpStatus.OK)
	public String addCityGet(Model model) throws IOException {
		return "redirect:/";
	}

	@RequestMapping(value ="/add", method = RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.OK)
	public String addCity(@ModelAttribute("city") City city, BindingResult result, Model model) throws IOException {
		//		String cityName = "Belgrade";
		String cityName = city.getName();
		System.out.println("@addCity:: City name: " + cityName);
		if (cityName != null) {
			try {
				if (isCityExist(cityName)) {
					if (!cityServices.isExistByName(cityName)) {
						City newCity = getCityInformation(cityName);
						//			model.addAttribute("newCity", newCity);
					}else {
						System.out.println("This City already exist!");
					}
				}else {
					System.out.println("This City doesn't exist on Weather Map!");
				}
			}
			catch (Exception e) {
				// TODO: handle exception
				System.out.println("@addCity: Error!");
				e.printStackTrace();
			}

		}else {
			System.out.println("Internal problem City is null!");
		}

		return "redirect:/index";
	}


	public City getCityInformation(String cityName)
	{
		String uri = openWeatherURL + "q=" + cityName + "&units=" + units + "&language=" + language + "&appid=" + APPID;
		RestTemplate restTemplate = new RestTemplate();
		//	    String result = restTemplate.getForObject(uri, String.class);
		OpenWeatherMapObject result = restTemplate.getForObject(uri, OpenWeatherMapObject.class);
		City tmpCity = new City();
		try {
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
			tmpCity.setDateTime(result.getDt());
			cityServices.create(tmpCity);

			//	    System.out.println(result);
			System.out.println(result.toString());
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("@getCityInformation: Error occured!");
		}

		return tmpCity;
	}

	public String createImageName(City city) {
		String result = "";
		WeatherBgImgUrl tmpName = createImageNameObject(city);

		// amsterdam, day, cold, fine, foggy
		result += tmpName.getCityName();
		result += ",";
		result += tmpName.getDayNight();
		result += ",";
		result += tmpName.getTemperatureLevel();
		if (tmpName.getPrecipitation() != null) {
			result += ",";
			result += tmpName.getPrecipitation();
		}
		if (tmpName.getDisasters() != null) {
			result += ",";
			result += tmpName.getDisasters();
		}
		if (fillApp.images.containsKey(result)) {
			return fillApp.images.get(result);
		}
		
		// amsterdam, day, hot
		result += tmpName.getCityName();
		result += ",";
		result += tmpName.getDayNight();
		result += ",";
		result += tmpName.getTemperatureLevel();
		if (fillApp.images.containsKey(result)) {
			return fillApp.images.get(result);
		}

		// default, day, cold, fine, foggy
		result = "";
		result += "default";
		result += ",";
		result += tmpName.getDayNight();
		result += ",";
		result += tmpName.getTemperatureLevel();
		if (tmpName.getPrecipitation() != null) {
			result += ",";
			result += tmpName.getPrecipitation();
		}
		if (tmpName.getDisasters() != null) {
			result += ",";
			result += tmpName.getDisasters();
		}
		if (fillApp.images.containsKey(result)) {
			return fillApp.images.get(result);
		}

		// default, day, normal, raining
		result = "";
		result += "default";
		result += ",";
		result += tmpName.getDayNight();
		result += ",";
		result += tmpName.getTemperatureLevel();
		if (tmpName.getPrecipitation() != null) {
			result += " ";
			result += tmpName.getPrecipitation();
		}
		if (fillApp.images.containsKey(result)) {
			return fillApp.images.get(result);
		}

		// default, day, cold, fine
		result = "";
		result += "default";
		result += ",";
		result += tmpName.getDayNight();
		result += ",";
		result += tmpName.getTemperatureLevel();
		result += ",";
		result += "fine";
		if (fillApp.images.containsKey(result)) {
			return fillApp.images.get(result);
		}
		
		// default, night, cold
		result = "";
		result += "default";
		result += ",";
		result += tmpName.getDayNight();
		result += ",";
		result += tmpName.getTemperatureLevel();
		if (fillApp.images.containsKey(result)) {
			return fillApp.images.get(result);
		}

//		// default, day, normal, fine
//		result = "";
//		result += "default";
//		result += ",";
//		result += tmpName.getDayNight();
//		result += ",";
//		result += "normal";
//		result += ",";
//		result += "fine";
//		if (fillApp.images.containsKey(result)) {
//			return fillApp.images.get(result);
//		}
//
//		// default, day, normal
//		result = "";
//		result += "default";
//		result += ",";
//		result += "day";
//		result += ",";
//		result += "normal";
//		if (fillApp.images.containsKey(result)) {
//			return fillApp.images.get(result);
//		}

		result = "";
		result += "default.jpg";
		return result;
	}

	public WeatherBgImgUrl createImageNameObject(City city) {
		WeatherBgImgUrl weatherImg = new WeatherBgImgUrl();
		weatherImg.setCityName(city.getName());

		TimeFn timeFn = new TimeFn();
		Instant now = Instant.now();
		Instant sunrise = timeFn.fromUnixToInstant(city.getSunrise().longValue());
		Instant sunset = timeFn.fromUnixToInstant(city.getSunset().longValue());
		if (now.isAfter(sunrise) && now.isBefore(sunset))
		{ 
			weatherImg.setDayNight("day");
		}
		else {
			weatherImg.setDayNight("night");
		}

		Double temperature = Double.valueOf(city.getTemperature()) / 100;
		if (temperature >= 25)
			weatherImg.setTemperatureLevel("hot");
		if (temperature < 10)
			weatherImg.setTemperatureLevel("cold");
		if (temperature >= 10 && temperature < 25)
			weatherImg.setTemperatureLevel("normal");

		return weatherImg;
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
		//		List<String> result = fileFn.CSVReaderByColumn("openweather_city_list.txt", "\t", 1);
		List<String> result = fileFn.CSVReaderByRow("openweather_city_list.txt", "\t", 1);
		result.forEach(item -> System.out.println(item));
	}

	@RequestMapping("/citiesExist")
	public Boolean isCityExist(String cityName) throws IOException {
		FileFn fileFn = new FileFn();
		//		Boolean result = fileFn.CSVReaderIsStringExist("openweather_city_list.txt", "\t", "Belgrade");
		Boolean result = fileFn.CSVReaderIsStringExist("openweather_city_list.txt", "\t", cityName);
		System.out.println("@isCityExist Availability City: " + cityName + " on Open Weather Map: " + result );
		return result;
	}

	public void printTree(Map<?, ?> map){
		for (Object key: map.keySet()){
			String value= (String) map.get(key);
			System.out.println("At key= "+ key+ ", the value is "+ value);
		}
	}

}
