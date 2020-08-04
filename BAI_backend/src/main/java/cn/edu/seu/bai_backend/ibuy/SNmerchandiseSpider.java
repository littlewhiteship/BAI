package cn.edu.seu.bai_backend.ibuy;

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
import java.util.ArrayList;
import java.util.regex.Pattern;

public class SNmerchandiseSpider {
    private static Logger logger = LoggerFactory.getLogger(SNmerchandiseSpider.class);
    private static final  String REQ_URL = "https://shop243511592.taobao.com/?spm=2013.1.1000126.d21.40645e56v0S2vv&qq-pf-to=pcqq.group";
    private ArrayList<String> urlList = new ArrayList<String>();
    private ArrayList<String> nameList = new ArrayList<String>();
    private ArrayList<String> scoreList = new ArrayList<String>();
    private ArrayList<String> numberList = new ArrayList<String>();

    @Test
    public void requestData(){
        // 1. 创建HttpClient对象，相当于浏览器对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 2. 封装请求
        HttpGet httpGet = new HttpGet(REQ_URL);

        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.5 Safari/605.1.15");

        // 3. 执行请求
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);

            // 4. 获取respnose的状态码
            StatusLine statusLine = response.getStatusLine();
            System.out.println(statusLine);
            if(statusLine.getStatusCode() != 200){
                return;
            }

            // 5. 获取响应数据
            HttpEntity httpEntity = response.getEntity();
            // 6. 数据格式转换
            String content = EntityUtils.toString(httpEntity);
            // 7. 数据抽取
            //System.out.println(content);
            parseHtml(content);

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

    private void parseHtml(String content) {
        // 1. 数据封装document对象
        Document document = Jsoup.parse(content);
        // 2. jsoup选择器实现数据抽取
        Elements elements = document.select("div.tshop-psm-shop-header2");
        //Element item = document.select("");
        // 3. 数据解析
        for(Element item : elements){
            String score=item.select("span[class=\"dsr-num red\"]").toString();

            //String score = item.select("p").toString();//.select("span[style]").text();
            //String name = item.select("a[target]" ).select("em").text();
            //String price = item.select("div.p-price > strong > i").html();
            //String url = item.select("div.p-img > a").attr("href").toString();

            // 4. 数据处理
            Pattern pattern = Pattern.compile("\\d*");
            //Matcher matcher = pattern.matcher(number);
            //number = matcher.find() ? matcher.group() : "0";
            StringBuilder stringBuilder = new StringBuilder();
            //stringBuilder.append(name).append("\t").append(url).append("\t\t").append(price).append("\t").append(img);
            stringBuilder.append(score);
            logger.info(stringBuilder.toString());
        }
    }



    public static void main(String[] args) throws InterruptedException {
        SNmerchandiseSpider sNmerchandiseSpider = new SNmerchandiseSpider();
        sNmerchandiseSpider.requestData();
    }
}
