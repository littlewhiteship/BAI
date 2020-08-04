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
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class sshopsalesspider {

    //private static Logger logger = LoggerFactory.getLogger(similarshopspider.class);
    private static Logger logger = (Logger) LoggerFactory.getLogger(sshopsalesspider.class);
    private static final  String REQ_URL = "https://shopsearch.taobao.com/browse/shop_search.htm?initiative_id=tbindexz_20170306&ie=utf8&spm=a21bo.2017.201856-taobao-item.2&sourceId=tb.index&search_type=shop&ssid=s5-e&commend=all&imgfile=&q=%E5%A5%B3%E8%A3%85&suggest=history_1&_input_charset=utf-8&wq=&suggest_query=&source=suggest&sort=sale-desc";

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
            if(statusLine.getStatusCode() != 200){
                return;
            }

            // 5. 获取响应数据
            HttpEntity httpEntity = response.getEntity();
            // 6. 数据格式转换
            String content = EntityUtils.toString(httpEntity);
            // 7. 数据抽取
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
        Elements elements = document.select("div.item");
        // 3. 数据解析
        for(Element item : elements){
            String name = item.select("div.hd > a > span").first().html();
            String score = item.select("div.star > span.rating_num").html();
            String number = item.select("div.star > span").last().html();

            // 4. 数据处理
            Pattern pattern = Pattern.compile("\\d*");
            Matcher matcher = pattern.matcher(number);
            number = matcher.find() ? matcher.group() : "0";
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(name).append("\t").append(score).append("\t").append(number);
            logger.info(stringBuilder.toString());
        }
    }

    public static void main(String[] args) {
        //Spider spider = new Spider();
        //spider.requestData();
    }

}
