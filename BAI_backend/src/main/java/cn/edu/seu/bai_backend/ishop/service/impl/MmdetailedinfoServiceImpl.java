package cn.edu.seu.bai_backend.ishop.service.impl;

import cn.edu.seu.bai_backend.ishop.entity.*;
import cn.edu.seu.bai_backend.ishop.mapper.MmdetailedinfoMapper;
import cn.edu.seu.bai_backend.ishop.service.IMmdetailedinfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author nqr
 * @since 2020-07-20
 */
@Service
public class MmdetailedinfoServiceImpl extends ServiceImpl<MmdetailedinfoMapper, Mmdetailedinfo> implements IMmdetailedinfoService {

    @Override
    public float getIntroPrice(int mmid) {
        //输入商品id获取其价格列表

        List<Mmdetailedinfo> mmdetailedinfoList = getMmDetailedInfo(mmid);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("mmdimmid",mmid);
        queryWrapper.orderByAsc("mmdiprice");
        //获得按照价格升序排序列表
        List<Mmdetailedinfo> mmdetailedinfoListPrice = list(queryWrapper);
        System.out.println("输出价格升序");
        System.out.println(mmdetailedinfoListPrice);
        System.out.println("输出价格升序结束");

        if(mmdetailedinfoList.size()<10)
        {
            //返回值为-1表示商家入驻未满10日不予以数据分析
            return -1;
        }
        else{
            //第一个为历史最低价格
            Mmdetailedinfo  mmdetailedinfomin = mmdetailedinfoListPrice.get(0);
            Mmdetailedinfo mmdetailedinfomax = mmdetailedinfoListPrice.get(mmdetailedinfoListPrice.size()-1);
            float minSalePrice = mmdetailedinfomin.getMmdiprice();
            //以最低价的80%作为其进货价格进行最优定价预估
            float purchasePrice = (float) (minSalePrice*0.8);
            //商家制定过的最高价格
            float maxSalePrice = mmdetailedinfomax.getMmdiprice();
            //以商家指定过的最高价格1.2倍作为估计上限
            float topPrice = (float) (maxSalePrice*1.2);

            //遍历价格与销量表，找到获利最高的定价
            System.out.println("最低价格为：");
            System.out.println(minSalePrice);
            System.out.println("进价为:");
            System.out.println(purchasePrice);
            System.out.println("商家制定过的最高价格");
            System.out.println(maxSalePrice);
            System.out.println("估计上限为");
            System.out.println(topPrice);

            int MAX_POINTS = mmdetailedinfoListPrice.size();//定义最大的训练集数据个数
            System.out.println("MAX_POINTS ="+MAX_POINTS);
            double E;
            //DataPoint[] data = new DataPoint[MAX_POINTS+1];  //创建数据集对象数组data[]
            List<DataPoint> dataPointList = new ArrayList<>();
            //创建线性回归类对象line，并且初始化类


        /*
        //按照时间从后往前排序列表
        QueryWrapper queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("mmdimmid",mmid);
        queryWrapper1.orderByDesc("mmdidate");
        mmdetailedinfoListPrice = list(queryWrapper1);
        System.out.println("拟合数据为:");
        System.out.println(mmdetailedinfoListPrice);

         */


            for (int i=0;i<10;i++){
                Mmdetailedinfo mmdetailedinfotmp = mmdetailedinfoListPrice.get(i);
                int salesVolume = mmdetailedinfotmp.getMmdisalesvolume();
                float price = mmdetailedinfotmp.getMmdiprice();
                System.out.println("拟合数据为:");
                System.out.println(price+"   "+salesVolume);
                DataPoint dataPoint = new DataPoint();
                dataPoint.setDataPoint(price,salesVolume);
                //data[i] = dataPoint;
                dataPointList.add(dataPoint);
            }
            RegressionLine line = new RegressionLine(dataPointList);
            //调用printSums方法打印Sum变量
            printSums1(line);
            //调用printLine方法并打印线性方程
            printLine1(line);
            //y = x*line.getA1()+line.getA0();

            //遍历找到推荐定价
            double maxEarning = 0;
            int IntroPrice = 0;
            for(int i = (int) Math.ceil(purchasePrice); i<Math.ceil(topPrice);i++){
                int salesVolume = (int) (i*line.getA1()+line.getA0());
                double tmpEarning = salesVolume*(i-purchasePrice);
                if (tmpEarning>maxEarning){
                    maxEarning = tmpEarning;
                    IntroPrice = i ;
                }
            }
            return IntroPrice;
        }
    }


    @Override
    public int getIntroVolume(int mmid) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("mmdimmid",mmid);
        queryWrapper.orderByDesc("mmdidate");
        //获得按照日期降序排序
        List<Mmdetailedinfo> mmdetailedinfoList = list(queryWrapper);

        if(mmdetailedinfoList.size()<10)
        {
            //返回值为-1表示商家入驻未满10日不予以数据分析
            return -1;
        }
        else{
            double tmpintroVolume = 0;
            double[] weight = {0.3,0.2,0.12,0.08,0.07,0.06,0.05,0.04,0.04,0.04};
            //进行数据分析
            for(int i=0;i<10;i++){
                double wi = weight[i];
                tmpintroVolume = tmpintroVolume + wi*mmdetailedinfoList.get(i).getMmdisalesvolume();
                System.out.println("wi = "+wi);
                System.out.println("SV="+mmdetailedinfoList.get(i).getMmdisalesvolume());
                System.out.println("tmpintroVolume"+tmpintroVolume);
            }
            int introVolume = (int) (tmpintroVolume*7);
            return introVolume;
        }
    }


    @Override
    public List<Mmdetailedinfo> getMmDetailedInfo(int mmid) {
        //获得该商品按时间从近到远排列的list
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("mmdimmid",mmid);
        queryWrapper.orderByDesc("mmdidate");
        List<Mmdetailedinfo> mmdetailedinfoList = list(queryWrapper);
        return mmdetailedinfoList;
    }

    @Override
    public List<PriceandSalesVolume> getPriceandVolume(int mmid) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("mmdimmid",mmid);
        queryWrapper.orderByAsc("mmdiprice");
        //获得按照价格升序排序列表
        List<Mmdetailedinfo> mmdetailedinfoList = list(queryWrapper);
        List<PriceandSalesVolume> priceandSalesVolumeList = new ArrayList<>();
        int mMDIP=0;
        for(int i=0;i<mmdetailedinfoList.size();i++){
            Mmdetailedinfo mmdetailedinfo = mmdetailedinfoList.get(i);
            PriceandSalesVolume priceandSalesVolume = changeMmditoPS(mmdetailedinfo);
            //如果有多日售价同，则返回的销量为均值
            System.out.println("i = "+i);
            System.out.println("mMDIP = "+mMDIP);
            System.out.println("当前价格为："+priceandSalesVolume.getMmdiprice());
            if (i==0){
                System.out.println("i==0");
                priceandSalesVolumeList.add(priceandSalesVolume);
                mMDIP++;
            }
            if (i>0){
                System.out.println("i>0");
                //获得加入数组的前一个数据看价格是否同
                PriceandSalesVolume priceandSalesVolume1 = priceandSalesVolumeList.get(mMDIP-1);
                //若俩个价格相同，则销量取均值
                System.out.println("上一个价格："+priceandSalesVolume1.getMmdiprice());
                float priceNow = priceandSalesVolume.getMmdiprice();
                float pricePre = priceandSalesVolume1.getMmdiprice();
                if(Math.abs(priceNow-pricePre)<0.1){
                    System.out.println("两个价格相同");
                    PriceandSalesVolume tmpPriceandSalesVolume=new PriceandSalesVolume();
                    int tmpVolume = (int) ((priceandSalesVolume.getMmdisalesvolume()+priceandSalesVolume1.getMmdisalesvolume())/2);
                    tmpPriceandSalesVolume.setPriceandSalesVolume(priceandSalesVolume.getMmdiprice(),tmpVolume);
                    priceandSalesVolumeList.set(mMDIP-1,tmpPriceandSalesVolume);
                }else{
                    System.out.println("两个价格不同");
                    priceandSalesVolumeList.add(priceandSalesVolume);
                    mMDIP++;
                }
            }
        }
        return priceandSalesVolumeList;
    }

    //打印回归方程方法
    public void printLine1(RegressionLine line){
        System.out.println("\n回归线公式：y = "+line.getA1()
                +"x + " + line.getA0());
        //System.out.println("Hello World!");
        System.out.println("误差： R^2 = " + line.getR());
    }

    @Override
    public void printSums1(RegressionLine line) {
        System.out.println("\n数据点个数 n = "+
                line.getDataPointCount());
        System.out.println("\nSumX = "+line.getSumX());
        System.out.println("SumY = "+line.getSumY());
        System.out.println("SumXX = "+line.getSumXX());
        System.out.println("SumXY = "+line.getSumXY());
        System.out.println("SumYY = "+line.getSumYY());
    }

    @Override
    public PriceandSalesVolume changeMmditoPS(Mmdetailedinfo mmdetailedinfo) {
        PriceandSalesVolume priceandSalesVolume = new PriceandSalesVolume();
        float price = mmdetailedinfo.getMmdiprice();
        int saleVolume = mmdetailedinfo.getMmdisalesvolume();
        priceandSalesVolume.setPriceandSalesVolume(price,saleVolume);
        return priceandSalesVolume;
    }

    @Override
    public List<DailySV> getDailySV(int mmid) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("mmdimmid",mmid);
        queryWrapper.orderByDesc("mmdidate");
        //获得按照时间降序排序列表
        List<Mmdetailedinfo> mmdetailedinfoList = list(queryWrapper);
        List<DailySV> dailySVList = new ArrayList<>();
        for (int i = 0;i<mmdetailedinfoList.size();i++){
            Mmdetailedinfo mmdetailedinfo = mmdetailedinfoList.get(i);
            DailySV dailySV = changeToDSV(mmdetailedinfo);
            dailySVList.add(dailySV);
        }
        return dailySVList;
    }

    public DailySV changeToDSV(Mmdetailedinfo mmdetailedinfo){
        DailySV dailySV = new DailySV();
        int SV = mmdetailedinfo.getMmdisalesvolume();
        LocalDate localDate = mmdetailedinfo.getMmdidate();
        dailySV.setDailySV(SV,localDate);
        return dailySV;
    }


}


