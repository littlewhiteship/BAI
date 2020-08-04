package cn.edu.seu.bai_backend.spider;

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

public class sshopcreditspider {

    //private static Logger logger = LoggerFactory.getLogger(similarshopspider.class);
    //private static Logger logger = (Logger) LoggerFactory.getLogger(sshopcreditspider.class);
    private static Logger logger = LoggerFactory.getLogger(sshopcreditspider.class);
    private static final  String REQ_URL = "https://shopsearch.taobao.com/browse/shop_search.htm?q=%E5%A5%B3%E8%A3%85&imgfile=&commend=all&ssid=s5-e&search_type=shop&sourceId=tb.index&spm=a21bo.2017.201856-taobao-item.1&ie=utf8&initiative_id=tbindexz_20170306";

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
            System.out.println(content);
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
        Elements elements = document.select("ul.clearfix");
        // 3. 数据解析
        for(Element item : elements){
            String name = item.select("li.list-img").select("a").attr("title");
            String url = item.select("li.list-img").select("a").attr("href");
            String tmpmssdescriptionpoints = item.select("div.shop-mark>ul").select("li>li.attrs").get(1).select("li>a").text();
            float mssdescriptionpoints = Float.valueOf(tmpmssdescriptionpoints);
            String tmpmsslogisticsspeedpoints = item.select("div.shop-mark>ul>li").get(2).select("a").text();
            //String score = item.select("div.star > span.rating_num").html();
            //String number = item.select("div.star > span").last().html();

            // 4. 数据处理
            /*
            Pattern pattern = Pattern.compile("\\d*");
            Matcher matcher = pattern.matcher(number);
            number = matcher.find() ? matcher.group() : "0";
             */
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(name).append("\t").append(url).append("\t").append(tmpmssdescriptionpoints).append("\t").append(tmpmsslogisticsspeedpoints);
            logger.info(stringBuilder.toString());
        }
    }

    public static void main(String[] args) {
        sshopcreditspider sshopcreditspider = new sshopcreditspider();
        sshopcreditspider.requestData();
    }

}
