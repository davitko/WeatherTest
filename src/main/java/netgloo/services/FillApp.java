package netgloo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Controller;

import netgloo.models.CarouselPack;

@Controller
public class FillApp {

	public TreeMap<String, String> images = new TreeMap<String,String>();
	public List<CarouselPack> carouselImg = new ArrayList<CarouselPack>();
	
	public FillApp() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void fillImgUrls() {
		 String tags = "";
		 String extension = ".jpg";
		 
		 tags = "default";
		 images.put(tags, tags + extension);
		 
		 tags = "";
		 tags = "default,day,cold";
		 images.put(tags, tags + extension);
		 
		 tags = "";
		 tags = "default,day,normal";
		 images.put(tags, tags + extension);
		 
		 tags = "";
		 tags = "default,day,cold,fine";
		 images.put(tags, tags + extension);
		 
		 tags = "";
		 tags = "default,day,cold,raining";
		 images.put(tags, tags + extension);
		 
		 tags = "";
		 tags = "default,day,normal,fine";
		 images.put(tags, tags + extension);
		 
		 
		 // Berlin
		 tags = "";
		 tags = "Berlin,day,hot";
		 images.put(tags, tags + extension);
		 tags = "";
		 tags = "Berlin,night,cold";
		 images.put(tags, tags + extension);
		 tags = "";
		 tags = "Berlin,night,hot";
		 images.put(tags, tags + extension);
		 tags = "";
		 tags = "Berlin,night,normal";
		 images.put(tags, tags + extension);
		 
		 // London
		 tags = "";
		 tags = "London,night,normal";
		 images.put(tags, tags + extension);
//		 tags = "";
//		 tags = "London,night,normal";
//		 images.put(tags, tags + extension);
//		 tags = "";
//		 tags = "London,night,normal";
//		 images.put(tags, tags + extension);
		 
		 // Belgrade
		 tags = "";
		 tags = "Belgrade,day,normal";
		 images.put(tags, tags + extension);
		 tags = "";
		 tags = "Belgrade,night,cold";
		 images.put(tags, tags + extension);
		 tags = "";
		 tags = "Belgrade,night,hot";
		 images.put(tags, tags + extension);
		 
		// Amsterdam
		 tags = "";
		 tags = "Amsterdam,day,cold";
		 images.put(tags, tags + extension);
		 tags = "";
		 tags = "Amsterdam,day,hot";
		 images.put(tags, tags + extension);
		 tags = "";
		 tags = "Amsterdam,day,normal";
		 images.put(tags, tags + extension);
		 tags = "";
		 tags = "Amsterdam,night,hot";
		 images.put(tags, tags + extension);
		 tags = "";
		 tags = "Amsterdam,night,normal";
		 images.put(tags, tags + extension);
	    
//	     printTree(images);
	    
	       
	}
	
//	public void fillCarouselImg() {
//		for (int i = 1; i < 6; i++) {
//			CarouselPack carouselPack = new CarouselPack();
//			carouselPack.setSummer("s" + i + ".jpg");
//			carouselPack.setAutumn("a" + i + ".jpg");
//			carouselPack.setWinter("w" + i + ".jpg");
//			carouselPack.setSpring("s" + i + ".jpg");
//			carouselImg.add(carouselPack);
//		}
//		carouselImg.forEach(item -> System.out.println(item));
//	}
	
	public void fillCarouselImg() {
		for (int i = 1; i < 6; i++) {
			CarouselPack carouselPack = new CarouselPack();
			carouselPack.setIndex(i);
			carouselPack.setSummer("summer.jpg");
			carouselPack.setAutumn("autumn.jpg");
			carouselPack.setWinter("winter.jpg");
			carouselPack.setSpring("spring.jpg");
			carouselImg.add(carouselPack);
		}
//		carouselImg.forEach(item -> System.out.println(item));
	}
	
	public void printTree(Map<?, ?> map){
        for (Object key: map.keySet()){
            String value= (String) map.get(key);
            System.out.println("At key= "+ key+ " the value is "+ value);
        }
    }

}