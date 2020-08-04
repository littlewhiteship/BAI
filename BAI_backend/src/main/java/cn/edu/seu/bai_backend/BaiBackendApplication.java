package cn.edu.seu.bai_backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.edu.seu.bai_backend.*.mapper")
public class BaiBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaiBackendApplication.class, args);
    }
}