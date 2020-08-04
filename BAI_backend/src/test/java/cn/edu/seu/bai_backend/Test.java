package cn.edu.seu.bai_backend;

import cn.edu.seu.bai_backend.ibuy.service.impl.SpiderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

@SpringBootTest
public class Test {
    @Autowired
    private SpiderServiceImpl spiderServiceImpl;


    @org.junit.jupiter.api.Test
    public void TestJDSpider() throws InterruptedException {


        Random ra =new Random();
        /*
        spiderServiceImpl.start("手机");
        Thread.sleep(30*ra.nextInt(300));
        spiderServiceImpl.start("数码相机");
        Thread.sleep(30*ra.nextInt(300));
        spiderServiceImpl.start("移动电源");
        Thread.sleep(28*ra.nextInt(300));
        spiderServiceImpl.start("智能手环");
        Thread.sleep(28*ra.nextInt(300));
         */
        spiderServiceImpl.start("女装");
        spiderServiceImpl.start("华为手机");
    }

}
