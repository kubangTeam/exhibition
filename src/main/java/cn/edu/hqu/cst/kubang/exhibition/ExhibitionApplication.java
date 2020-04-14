package cn.edu.hqu.cst.kubang.exhibition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableSwagger2
public class ExhibitionApplication {

	@PostConstruct
	public void init(){
		//解决netty启动冲突问题
		//redis和es都是基于netty
		System.setProperty("es.set.netty.runtime.available.processors","false");
	}
	public static void main(String[] args) {
		SpringApplication.run(ExhibitionApplication.class, args);
	}

}
