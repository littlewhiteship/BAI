package cn.edu.seu.bai_backend.ibuy.service.impl;

import cn.edu.seu.bai_backend.ibuy.entity.Allmcredit;
import cn.edu.seu.bai_backend.ibuy.entity.Allmprice;
import cn.edu.seu.bai_backend.ibuy.entity.Allmsales;
import cn.edu.seu.bai_backend.ibuy.service.IAllmcreditService;
import cn.edu.seu.bai_backend.ibuy.service.IAllmpriceService;
import cn.edu.seu.bai_backend.ibuy.service.IAllmsalesService;
import cn.edu.seu.bai_backend.ibuy.service.ISpiderService;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Random;
import java.util.regex.Pattern;

@Service
public class SpiderServiceImpl implements ISpiderService {
    private static Logger logger = LoggerFactory.getLogger(SpiderServiceImpl.class);
    private static final  String REQ_URL = "https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&enc=utf-8&psort=4&page=2";

    //private List<Allmcredit> allmcreditList = new ArrayList<>();

    //private Random ra =new Random();

    @Autowired
    IAllmcreditService iAllmcreditService;

    @Autowired
    IAllmpriceService iAllmpriceService;

    @Autowired
    IAllmsalesService iAllmsalesService;




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
            parseHtml(content,0);
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

    private void parseHtml(String content,int type) {
        // 1. 数据封装document对象
        Document document = Jsoup.parse(content);
        // 2. jsoup选择器实现数据抽取
        Elements elements = document.select("div.gl-i-wrap");
        // 3. 数据解析
        double score = 100;
        for(Element item : elements){
            score = score*0.95;
            String img = item.select("div.p-img > a").select("img").attr("src").toString();
            img="https:"+img;
            String name = item.select("a[target]" ).select("em").text();
            int length = name.length();

            if(length>60){
                name = name.substring(0,60);
            }

            String price = item.select("div.p-price > strong > i").html();
            String url = item.select("div.p-img > a").attr("href").toString();
            url="https:"+url;

            //若为0加入到credit表中
            if(type==0){
                Allmcredit allmcredit = new Allmcredit();
                allmcredit.setCreditscore((float) score);
                allmcredit.setAmcimage(img);
                allmcredit.setAmcname(name);
                allmcredit.setPrice(Float.valueOf(price));
                allmcredit.setLink(url);
                allmcredit.setPlatform("京东商城");
                allmcredit.setMonthlysales(0);
                System.out.println(allmcredit);
               // allmcreditList.add(allmcredit);
                iAllmcreditService.save(allmcredit);
            }
            //若为3加入销量表中
            else if(type==3){
                Allmsales allmsales = new Allmsales();
                allmsales.setSalesscore((float)score);
                allmsales.setAmsimage(img);
                allmsales.setAmsname(name);
                allmsales.setPrice(Float.valueOf(price));
                allmsales.setLink(url);
                allmsales.setPlatform("京东商城");
                allmsales.setMonthlysales(0);
                System.out.println(allmsales);
                iAllmsalesService.save(allmsales);
            }
            //若为2加入价格表中
            else if(type==2){
                Allmprice allmprice = new Allmprice();
                allmprice.setPricescore((float)score);
                allmprice.setAmpimage(img);
                allmprice.setAmpname(name);
                allmprice.setAmpprice(Float.valueOf(price));
                allmprice.setLink(url);
                allmprice.setPlatform("京东商城");
                allmprice.setMonthlysales(0);
                System.out.println(allmprice);
                iAllmpriceService.save(allmprice);
            }

            // 4. 数据处理
            Pattern pattern = Pattern.compile("\\d*");
            //Matcher matcher = pattern.matcher(number);
            //number = matcher.find() ? matcher.group() : "0";
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(name).append("\t").append(url).append("\t\t").append(price).append("\t").append(img);
            logger.info(stringBuilder.toString());
        }
    }

    public void requestData1(int page,int type,String content){
        //type表示搜索方式 2 表示价格 3 表示销量 0表示综合
        //https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&enc=utf-8&psort=3&page=1
         String REQ_TMP_URL = "https://search.jd.com/Search?keyword=";
        REQ_TMP_URL=REQ_TMP_URL+content+"&enc=utf-8&psort="+type+"&page="+page;
        // 1. 创建HttpClient对象，相当于浏览器对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 2. 封装请求
        HttpGet httpGet = new HttpGet(REQ_TMP_URL);

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
            String content1 = EntityUtils.toString(httpEntity);
            // 7. 数据抽取
            parseHtml(content1,type);
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

    public void start(String content) throws InterruptedException {

        for (int t = 0;t<3;t++)
        {
            int type = 0;
            if(t==0) {
                type = 0;
                //爬取综合页面
            }else if(t==1){
                type = 2;
                //爬取价格页面
            }else if (t==2){
                type =3;
            }
            for(int page = 1;page<2;page++){
                requestData1(page,type,content);
                //type表示搜索方式 2 表示价格 3 表示销量 0表示综合
                Random ra =new Random();
                Thread.sleep(10*ra.nextInt(300));
            }
        }
    }
}

    /*
    public static void main(String[] args) throws InterruptedException {
        JDmerchandiseSpider jDmerchandiseSpider = new JDmerchandiseSpider();
        //jDmerchandiseSpider.requestData();
        for (int t = 0;t<3;t++)
        {
            int type = 0;
            if(t==0) {
                type = 0;
                //爬取综合页面
            }else if(t==1){
                type = 2;
                //爬取价格页面
            }else if (t==2){
                type =3;
            }
            for(int page = 1;page<4;page++){
                jDmerchandiseSpider.requestData1(page,type,"女装");
                //type表示搜索方式 2 表示价格 3 表示销量 0表示综合
                Random ra =new Random();
                Thread.sleep(10*ra.nextInt(300));
            }
        }
    }
   */


