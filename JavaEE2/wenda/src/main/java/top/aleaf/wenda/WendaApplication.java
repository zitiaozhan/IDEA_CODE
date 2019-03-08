package top.aleaf.wenda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = {"top.aleaf.wenda.mapper"})
public class WendaApplication {

	public static void main(String[] args) {
		SpringApplication.run(WendaApplication.class, args);
	}

}

