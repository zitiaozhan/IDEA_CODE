package top.aleaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = {"top.aleaf.mapper"})
public class BisheApplication {

	public static void main(String[] args) {
		SpringApplication.run(BisheApplication.class, args);
	}

}

