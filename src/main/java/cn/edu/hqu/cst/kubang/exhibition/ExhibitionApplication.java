package cn.edu.hqu.cst.kubang.exhibition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class ExhibitionApplication {
    //程序入口
	public static void main(String[] args) {
		SpringApplication.run(ExhibitionApplication.class, args);

		//nothing
		System.out.println("启动程序");
	}

}
