package cn.edu.seu.bai_backend.my;

import cn.edu.seu.bai_backend.my.entity.Userinfo;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.regex.Pattern;

public class spidershoprate {

    private float rate;
    private float maxprice;
    private float minprice;
    private String url;
    private String mmtype;
    private String platform;
    private Userinfo user;

    private static Logger logger = LoggerFactory.getLogger(spidershoprate.class);

    @Test
    public void requestData(){
        System.out.println("创建httpclient访问对象");
        // 1. 创建HttpClient对象，相当于浏览器对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 2. 封装请求
        System.out.println("封装请求");
        HttpGet httpGet = new HttpGet(url);
        System.out.println("设置请求头");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.5 Safari/605.1.15");
        // 3. 执行请求
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            // 4. 获取respnose的状态码
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() != 200){
                return; }
            // 5. 获取响应数据
            HttpEntity httpEntity = response.getEntity();
            // 6. 数据格式转换
            String content = EntityUtils.toString(httpEntity);
            // 7. 数据抽取
            if(platform.equals("天猫")) {
                parseHtml1(content);
            }else if (platform.equals("淘宝")){
                parseHtml2(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void parseHtml1(String content) {
        System.out.println("已进入天猫店铺爬虫");
        // 1. 数据封装document对象
        Document document = Jsoup.parse(content);
        // 2. jsoup选择器实现数据抽取
        Elements elements = document.select("div.main-info");
        // 3. 数据解析
        for(Element item : elements) {
            String score = item.getElementsByIndexEquals(1).select("div.shopdsr-item > div > span").html();
            // 4. 数据处理
            Pattern pattern = Pattern.compile("\\d*");
            rate = Float.valueOf(score);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(score);
            logger.info(stringBuilder.toString());
        }
    }
    private void parseHtml2(String content) {
        System.out.println("已进入淘宝店铺爬虫");
        // 1. 数据封装document对象
        Document document = Jsoup.parse(content);
        // 2. jsoup选择器实现数据抽取
        Elements elements = document.select("div.tshop-psm-shop-header2");
        // 3. 数据解析
        for(Element info:elements){
            /*找到span标签，并将内容转换成字符串*/
            String score =  info.select("span[class=\"dsr-num red\"]").text();
            // 4. 数据处理
            System.out.println(score);
            score = score.substring(4,7);
            //Pattern pattern = Pattern.compile("\\d*");
            rate = Float.valueOf(score);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(score);
            logger.info(stringBuilder.toString());
        }
    }

    public float getRate() {
        return rate;
    }
    public void setRate() {
        requestData();
    }
    public float getMaxprice() {
        return maxprice;
    }
    public void setMaxprice(float maxprice) {
        this.maxprice = maxprice;
    }
    public float getMinprice() {
        return minprice;
    }
    public void setMinprice(float minprice) {
        this.minprice = minprice;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getMmtype() {
        return mmtype;
    }
    public void setMmtype(String mmtype) {
        this.mmtype = mmtype;
    }
    public String getPlatform() {
        return platform;
    }
    public void setPlatform(String platform) {
        this.platform = platform;
    }
    public Userinfo getUser() {
        return user;
    }
    public void setUser(Userinfo user) {
        this.user = user;
    }
}