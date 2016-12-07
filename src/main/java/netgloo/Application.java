package netgloo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
//@EnableAutoConfiguration
@ComponentScan()
@EnableJpaRepositories
public class Application {

  public static void main(String[] args) {
	  ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
    
    try {
//    	context.getBean(FillDataBase.class).fillNodes();
//		context.getBean(FillDataBase.class).fillRoads();
//		context.getBean(FillDataBase.class).fillCar();

	}catch (Exception e) {
		// TODO: handle exception
		System.out.println("Exeption during App running: " + e);
	}
  }

}
