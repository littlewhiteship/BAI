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
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Pattern;

public class mmtaobaospider {
    private static Logger logger = LoggerFactory.getLogger(mmspider.class);
    private static String REQ_URL = "https://shop243511592.taobao.com/?spm=2013.1.1000126.d21.40645e56v0S2vv";
    private Random ra =new Random();
    private ArrayList<String> urlList = new ArrayList<String>();
    private ArrayList<String> nameList = new ArrayList<String>();
    private ArrayList<String> priceList = new ArrayList<String>();
    private ArrayList<String> salesvolumeList = new ArrayList<String>();
    private ArrayList<String> imgList = new ArrayList<String>();


    @Test
    public void getUrlList() throws InterruptedException {
        // 1. 创建浏览器对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        //针对反爬机制采取的措施
        HttpGet httpGet = new HttpGet(REQ_URL);
        //设置请求头
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.5 Safari/605.1.15");
        //执行请求
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            //获取respnose的状态码
            StatusLine statusLine = response.getStatusLine();
            System.out.println(statusLine.getStatusCode());
            if (statusLine.getStatusCode() != 200) {
                return;
            }
            // 获取响应数据
            HttpEntity httpEntity = response.getEntity();
            // 数据格式转换
            String content = EntityUtils.toString(httpEntity);
            // 提取出可以进入下一层网页的URL
            getUrl(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //System.out.println(urlList.toString());


    private void getUrl(String content) {
        // 1. 数据封装document对象
        Document document = Jsoup.parse(content);
        // 2. jsoup选择器实现数据抽取
        Elements elements = document.select("dl.item");
        // 3. 数据解析

        for(Element item : elements){
            String img = item.select("dt").select("img").attr("src"); ///
            String name = item.select("dd>a").text(); //
            String price = item.select("span.c-price").text(); //
            String url = item.select("dd").select("a").attr("href");
            //String salesvolume = item.select("div.star > span").last().html();

            //数据处理：正则表达式
            Pattern pattern = Pattern.compile("\\d*");
            //Matcher matcher = pattern.matcher(salesvolume);
            //salesvolume = matcher.find() ? matcher.group() : "0";
            urlList.add(url);
            nameList.add(name);
            priceList.add(price);
            imgList.add(img);
            //salesvolumeList.add(salesvolume);
            // StringBuilder stringBuilder = new StringBuilder();
            //stringBuilder.append(name).append("\t").append(price).append("\t").append(img).append("\t").append(url);
            //logger.info(stringBuilder.toString());

        }
    }



    public void requestData() throws InterruptedException {
        //创建HttpClient对象，相当于浏览器对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 封装请求
        for (int i = 0;i<urlList.size();i++) {
            //反反爬机制
            Thread.sleep(10*ra.nextInt(300));
            HttpGet httpGet = new HttpGet("https:"+urlList.get(i));
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.5 Safari/605.1.15");
            // 执行请求
            CloseableHttpResponse response = null;
            try {
                response = httpClient.execute(httpGet);
                //获取respnose的状态码
                StatusLine statusLine = response.getStatusLine();
                System.out.println(statusLine.getStatusCode());
                if (statusLine.getStatusCode() != 200) {
                    parseHtml("", i);
                    continue;
                }
                //获取响应数据
                HttpEntity httpEntity = response.getEntity();
                //数据格式转换
                String content = EntityUtils.toString(httpEntity);
                //System.out.println(content);
                //数据抽取
                parseHtml(content, i);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (response != null) {
                    try {
                        response.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void parseHtml(String content,int index) {
        if (!content.equals("")) {
            //数据封装document对象
            Document document = Jsoup.parse(content);
            //jsoup选择器实现数据抽取
            //Elements item = document.select("div#tm-indcon");
            Elements item = document.select("div#tb-sell-counter");
            //Document document = Jsoup.parse(content);
            //jsoup选择器实现数据抽取
            //Element item = elements.get(index);
            //抽取销售量字段
            //String director = item.select("span>span.attrs").first().select("span.attrs>a").text();
            String salesvolume = item.select("a[href=\"javascript:;\"]").text();
            //Pattern pattern = Pattern.compile("<span class=\"pl\">制片国家/地区:</span> (.*)\n" + " <br>");
            //String salesvolume = item.select("a").text();
            salesvolumeList.add(salesvolume);
            //item.select("span.tm-count").text();
            // 数据处理
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(nameList.get(index)).append("\t").append(priceList.get(index)).append("\t").append(imgList.get(index)).
                    append("\t").append(urlList.get(index)).append("\t").append(salesvolume).append("\t");
            logger.info(stringBuilder.toString());
        }else{
            String msg = "详细页面404丢失";
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(nameList.get(index)).append("\t").append(priceList.get(index)).append("\t").append(urlList.get(index)).
                    append("\t").append(msg).append("\t");
            logger.info(stringBuilder.toString());
        }
    }

    public ArrayList<String> getSalesvolumeList() {
        return salesvolumeList;
    }

    public ArrayList<String> getNameList() {
        return nameList;
    }

    public void setUrl(String shopurl)
    {
        REQ_URL=shopurl;
    }


    public static void main(String[] args) throws InterruptedException {
        mmtaobaospider mmtaobaospider = new mmtaobaospider();
        mmtaobaospider.getUrlList();
        mmtaobaospider.requestData();
    }





}
