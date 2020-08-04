package cn.edu.seu.bai_backend.ibuy.service.impl;

import cn.edu.seu.bai_backend.ibuy.entity.Allmcredit;
import cn.edu.seu.bai_backend.ibuy.entity.Allmprice;
import cn.edu.seu.bai_backend.ibuy.entity.Allmsales;
import cn.edu.seu.bai_backend.ibuy.entity.IntroMerchandise;
import cn.edu.seu.bai_backend.ibuy.mapper.AllmcreditMapper;
import cn.edu.seu.bai_backend.ibuy.service.IAllmcreditService;
import cn.edu.seu.bai_backend.ibuy.service.IAllmpriceService;
import cn.edu.seu.bai_backend.ibuy.service.IAllmsalesService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class AllmcreditServiceImpl extends ServiceImpl<AllmcreditMapper, Allmcredit> implements IAllmcreditService {

    @Autowired
    IAllmpriceService iAllmpriceService;

    @Autowired
    IAllmsalesService iAllmsalesService;

    @Autowired
    SpiderServiceImpl spiderServiceImpl;

    @Override
    public IntroMerchandise changeCtoIM(Allmcredit allmcredit) {
        IntroMerchandise introMerchandise =new IntroMerchandise();
        String amsname = allmcredit.getAmcname();
        Float price =allmcredit.getPrice();
        String link = allmcredit.getLink();
        int monthlysales = allmcredit.getMonthlysales();
        String platform = allmcredit.getPlatform();
        String amsimage = allmcredit.getAmcimage();
        introMerchandise.setIntroMerchandise(amsname,price,link,monthlysales,platform,amsimage);
        return introMerchandise;
    }

    @Override
    public List<Allmcredit> getIntroCMList(String content,String platform,boolean isPriceStrice,int lowestPrice,int highestPrice) {
        QueryWrapper<Allmcredit> queryWrapper = new QueryWrapper<>();
        //按照信誉分数降序排序
        queryWrapper.eq("platform",platform);
        queryWrapper.between("price",lowestPrice,highestPrice);
        queryWrapper.like("amcname",content);
        queryWrapper.orderByDesc("creditscore");
        List<Allmcredit> allmcreditList = list(queryWrapper);
        return allmcreditList;
    }

    @Override
    public List<Allmcredit> changeCreditScore(List<Allmcredit> allmcreditList,float creditscore) {
        List<Allmcredit> allmcreditList1 = new ArrayList<>();
        for (int i=0;i<allmcreditList.size();i++){
            float tmpCreditScore =allmcreditList.get(i).getCreditscore();
            //改变creditScore的值
            tmpCreditScore=tmpCreditScore*creditscore/100;
            Allmcredit allmcredit = allmcreditList.get(i);
            allmcredit.setCreditscore(tmpCreditScore);
            System.out.println("tmpCreditScore="+tmpCreditScore);
            System.out.println("creditScore="+allmcredit.getCreditscore());
            //改变List中的值
            //allmcreditList.set(i,allmcredit);
            allmcreditList1.add(allmcredit);
        }
        System.out.println("changeCreditScore");
        System.out.println(allmcreditList1);
        return allmcreditList1;
    }

    @Override
    public List<IntroMerchandise> combineThreeList(List<Allmcredit> allmcreditList, List<Allmprice> allmpriceList, List<Allmsales> allmsalesList) {
        boolean isCChanged = false;
        boolean isPChanged = false;
        boolean isSChanged = false;

        //比较Allmprice中是否有店铺与Allcredit中的店铺重复，若有，则将Allmprice的分数加入Allmprice中
        //将Allmprice中店铺的得分置为0
        for (int i=0;i<allmpriceList.size();i++){
            Allmprice allmprice = allmpriceList.get(i);
            for (int j=0;j<allmcreditList.size();j++){
                Allmcredit allmcredit = allmcreditList.get(j);
                //如果出现在allmcredit和allmprice搜索中有重复商品
                if (allmcredit.getAmcname().equals(allmprice.getAmpname())){
                    float tmpSumScore = allmcredit.getCreditscore()+allmprice.getPricescore();
                    float a = 0;
                    allmcredit.setCreditscore(tmpSumScore);
                    allmprice.setPricescore(a);
                    allmcreditList.set(j,allmcredit);
                    allmpriceList.set(i,allmprice);
                    isCChanged = true;
                    isPChanged = true;
                    System.out.println("Allmprice中有与Allcredit中的重复");
                    System.out.println("allmcredit="+allmcredit);
                    System.out.println("allmprice="+allmprice);
                }
            }
        }


        //比较Allmsales中是否有店铺与Allcredit中的店铺重复，若有，则将Allmprice的分数加入Allcredit中
        //将Allmsales中店铺的得分置为0
        for (int i=0;i<allmsalesList.size();i++){
            Allmsales allmsales = allmsalesList.get(i);
            for (int j=0;j<allmcreditList.size();j++){
                Allmcredit allmcredit = allmcreditList.get(j);
                //如果出现在Allmsales和Allcredit搜索中有重复商品
                if (allmcredit.getAmcname().equals(allmsales.getAmsname())){
                    float tmpSumScore = allmcredit.getCreditscore()+allmsales.getSalesscore();
                    float a = 0;
                    allmcredit.setCreditscore(tmpSumScore);
                    allmsales.setSalesscore(a);
                    allmcreditList.set(j,allmcredit);
                    allmsalesList.set(i,allmsales);
                    isCChanged = true;
                    isSChanged = true;
                    System.out.println("Allmsales中有与Allcredit重复");
                    System.out.println("allmcredit="+allmcredit);
                    System.out.println("allmsales="+allmsales);
                }
            }
        }

        //比较Allmsales中是否有店铺与Allmprice中的店铺重复
        //若三张表中都有该商品则不做处理
        //若该商品只在Allmsales中Allmprice中，则将Allmprice的分数加入Allmsales中
        for (int i=0;i<allmsalesList.size();i++){
            Allmsales allmsales = allmsalesList.get(i);
            for(int j=0;j<allmpriceList.size();j++){
                Allmprice allmprice =allmpriceList.get(j);
                //如果出现在Allmsales和Allmprice搜索中有重复商品
                if (allmsales.getAmsname().equals(allmprice.getAmpname())){
                    boolean isExitinC = false;
                    for (int p=0;p<allmcreditList.size();p++){
                        Allmcredit allmcredit = allmcreditList.get(p);
                        if (allmcredit.getAmcname().equals(allmsales.getAmsname())){
                            isExitinC =true;
                            break;
                        }
                    }
                    //若该商品只在Allmsales中Allmprice中，则将Allmsales的分数加入Allmprice中
                    if (isExitinC==false){
                        float tmpSumScore = allmprice.getPricescore()+allmsales.getSalesscore();
                        float a = 0;
                        allmprice.setPricescore(tmpSumScore);
                        allmsales.setSalesscore(a);
                        allmpriceList.set(j,allmprice);
                        allmsalesList.set(i,allmsales);
                        isPChanged = true;
                        isSChanged = true;
                        System.out.println("Allmsales中有与Allmprice重复且不在Credit中");
                        System.out.println("allmprice="+allmprice);
                        System.out.println("allmsales="+allmsales);
                    }
                }
            }
        }

        System.out.println("重新排序前的顺序");
        System.out.println(allmcreditList);
        System.out.println(allmpriceList);
        System.out.println(allmsalesList);

        //分别对三个可能有已经改变顺序的进行重新排序
        if (isCChanged){
            allmcreditList = reArrangeCredit(allmcreditList);
            System.out.println("allmcreditList重新排序"+allmcreditList);
        }
        if (isPChanged){
            allmpriceList = iAllmpriceService.reArrangePrice(allmpriceList);
            System.out.println("allmpriceList重新排序"+allmpriceList);
        }
        if (isSChanged){
            allmsalesList = iAllmsalesService.reArrangeSales(allmsalesList);
            System.out.println("allmsalesList重新排序"+allmsalesList);
        }
        //完成重复比较部分与重排序部分

        //进行三列归并merge
        List<IntroMerchandise> introMerchandiseList = new ArrayList<>();
        introMerchandiseList = mergeThreeScore(allmcreditList,allmsalesList,allmpriceList);

        return introMerchandiseList;
    }

    @Override
    public List<IntroMerchandise> getIntroMList(float creditscore, float pricescore, float salescredit,String content,String platform,boolean isPriceStrice,int lowest,int highest) {

        //获取按三种方式排序的商品列表
        List<Allmcredit> allmcreditList = getIntroCMList(content,platform,isPriceStrice,lowest,highest);
        List<Allmprice> allmpriceList = iAllmpriceService.getIntroPMList(content,platform,isPriceStrice,lowest,highest);
        List<Allmsales> allmsalesList = iAllmsalesService.getIntroSMList(content,platform,isPriceStrice,lowest,highest);

        //按照每种排序方式权重改变其对应分数
        List<Allmcredit> allmcreditList1 = changeCreditScore(allmcreditList,creditscore);
        allmpriceList = iAllmpriceService.changPriceScore(allmpriceList,pricescore);
        allmsalesList = iAllmsalesService.changSalesScore(allmsalesList,salescredit);
        System.out.println("改变权重后的各个列表分数值");
        System.out.println(allmcreditList1);
        System.out.println(allmpriceList);
        System.out.println(allmsalesList);

        //合并三个列列表
        List<IntroMerchandise> introMerchandiseList = new ArrayList<>();
        introMerchandiseList = combineThreeList(allmcreditList1,allmpriceList,allmsalesList);

        return introMerchandiseList;
    }

    @Override
    public List<Allmcredit> reArrangeCredit(List<Allmcredit> allmcreditList) {
        //对allmcreditList按照价格分降序重新排序,选择排序
        //对前n-1个与后边的比较，每次确定一个当前最大的
        for(int i=0;i<allmcreditList.size()-1;i++){
            Allmcredit allmcrediti = allmcreditList.get(i);
            //记录当前最高分数与最高分数位置
            int pMax = i;
            float tmpMaxScore = allmcrediti.getCreditscore();
            Allmcredit maxAllmcredit = allmcreditList.get(i);
            for(int j=i+1;j<allmcreditList.size();j++){
                Allmcredit allmcreditj = allmcreditList.get(j);
                //若当前的分数高则换
                if(allmcreditj.getCreditscore()>tmpMaxScore){
                    pMax = j;
                    tmpMaxScore = allmcreditj.getCreditscore();
                    maxAllmcredit=allmcreditj;
                }
            }
            //一轮比较后确定当前最大的位置与值
            //与i处进行互换
            allmcreditList.set(pMax,allmcrediti);
            allmcreditList.set(i,maxAllmcredit);
        }
        return allmcreditList;
    }

    @Override
    public List<IntroMerchandise> mergeThreeScore(List<Allmcredit> allmcreditList, List<Allmsales> allmsalesList, List<Allmprice> allmpriceList) {
        List<IntroMerchandise> introMerchandiseArrayList = new ArrayList<>();
        int creditP = 0;
        int salesP = 0;
        int priceP = 0;
        //若有哪个还没到底
        while(creditP<allmcreditList.size()||salesP<allmsalesList.size()||priceP<allmpriceList.size()){
            /*
            Allmcredit allmcredit = allmcreditList.get(creditP);
            Allmprice allmprice = allmpriceList.get(priceP);
            Allmsales allmsales = allmsalesList.get(salesP);
             */

            //如果三个都没到底
            if(creditP<allmcreditList.size()&&salesP<allmsalesList.size()&&priceP<allmpriceList.size()){
                Allmcredit allmcredit = allmcreditList.get(creditP);
                Allmprice allmprice = allmpriceList.get(priceP);
                Allmsales allmsales = allmsalesList.get(salesP);

                //如果其中有数已经为0，则该列结束
                //为0列只可能是allmprice或allmsales
                if(allmprice.getPricescore()==0){
                    priceP=allmpriceList.size();
                }else if(allmsales.getSalesscore()==0){
                    salesP=allmsalesList.size();
                }
                //若均非0才进行三个之间的大小比较
                else {
                    //比出其中最大的，加到list中，并改变指针位置
                    //若最大的是allmcredit
                    if(allmcredit.getCreditscore()>=allmprice.getPricescore()&&allmcredit.getCreditscore()>=allmsales.getSalesscore()){
                        IntroMerchandise introMerchandise = changeCtoIM(allmcredit);
                        System.out.println(allmcredit.getCreditscore());
                        creditP++;
                        introMerchandiseArrayList.add(introMerchandise);
                    }
                    //若最大的是allmprice
                    else if(allmprice.getPricescore()>=allmcredit.getCreditscore()&&allmprice.getPricescore()>=allmsales.getSalesscore()){
                        IntroMerchandise introMerchandise = iAllmpriceService.changePtoIM(allmprice);
                        System.out.println(allmprice.getPricescore());
                        priceP++;
                        introMerchandiseArrayList.add(introMerchandise);
                    }
                    //若最大的是allmsales
                    else{
                        IntroMerchandise introMerchandise = iAllmsalesService.changeStoIM(allmsales);
                        System.out.println(allmsales.getSalesscore());
                        salesP++;
                        introMerchandiseArrayList.add(introMerchandise);
                    }
                }
            }

            //如果有两个没到底
            //若allmpriceList到底了
            if(creditP<allmcreditList.size()&&salesP<allmsalesList.size()&&priceP==allmpriceList.size()){
                Allmcredit allmcredit = allmcreditList.get(creditP);
                Allmsales allmsales = allmsalesList.get(salesP);

                //如果其中有数已经为0，则该列结束
                //为0列只可能是allmsales
                if(allmsales.getSalesscore()==0){
                    salesP=allmsalesList.size();
                }
                //若两列均非0才进行比较
                else {
                    //allmcredit大
                    if(allmcredit.getCreditscore()>=allmsales.getSalesscore()){
                        IntroMerchandise introMerchandise = changeCtoIM(allmcredit);
                        System.out.println(allmcredit.getCreditscore());
                        creditP++;
                        introMerchandiseArrayList.add(introMerchandise);
                    }
                    //allmsales大
                    else {
                        IntroMerchandise introMerchandise = iAllmsalesService.changeStoIM(allmsales);
                        System.out.println(allmsales.getSalesscore());
                        salesP++;
                        introMerchandiseArrayList.add(introMerchandise);
                    }
                }

            }
            //若allmsalesList到底了
            else if(creditP<allmcreditList.size()&&salesP==allmsalesList.size()&&priceP<allmpriceList.size()){
                Allmcredit allmcredit = allmcreditList.get(creditP);
                Allmprice allmprice = allmpriceList.get(priceP);

                //如果其中有数已经为0，则该列结束
                //为0列只可能是allmprice
                if(allmprice.getPricescore()==0){
                    priceP=allmpriceList.size();
                }
                //若两列均非0才进行比较
                else {
                    //allmcredit大
                    if(allmcredit.getCreditscore()>=allmprice.getPricescore()){
                        IntroMerchandise introMerchandise = changeCtoIM(allmcredit);
                        System.out.println(allmcredit.getCreditscore());
                        creditP++;
                        introMerchandiseArrayList.add(introMerchandise);
                    }
                    //allmprice大
                    else {
                        IntroMerchandise introMerchandise = iAllmpriceService.changePtoIM(allmprice);
                        System.out.println(allmprice.getPricescore());
                        priceP++;
                        introMerchandiseArrayList.add(introMerchandise);
                    }
                }
            }
            //若allmcreditList到底了
            else if (creditP==allmcreditList.size()&&salesP<allmsalesList.size()&&priceP<allmpriceList.size())
            {
                Allmprice allmprice = allmpriceList.get(priceP);
                Allmsales allmsales = allmsalesList.get(salesP);

                //如果其中有数已经为0，则该列结束
                //为0列可能是allmprice或allmsales
                if (allmprice.getPricescore() == 0) {
                    priceP = allmpriceList.size();
                } else if (allmsales.getSalesscore() == 0) {
                    salesP = allmsalesList.size();
                }
                //若两列均非0才进行比较
                else {
                    //allmprice大
                    if (allmprice.getPricescore() >= allmsales.getSalesscore()) {
                        IntroMerchandise introMerchandise = iAllmpriceService.changePtoIM(allmprice);
                        System.out.println(allmprice.getPricescore());
                        priceP++;
                        introMerchandiseArrayList.add(introMerchandise);
                    }
                    //allmsales大
                    else {
                        IntroMerchandise introMerchandise = iAllmsalesService.changeStoIM(allmsales);
                        System.out.println(allmsales.getSalesscore());
                        salesP++;
                        introMerchandiseArrayList.add(introMerchandise);
                    }
                }
            }
            //如果有一个没到底

            //若creditP还没到底
            if(creditP<allmcreditList.size()&&salesP==allmsalesList.size()&&priceP==allmpriceList.size()){
                //一个个将creditP往里加，直到无，因为creditP无0分
                Allmcredit allmcredit = allmcreditList.get(creditP);
                while (creditP<allmcreditList.size()){
                    allmcredit = allmcreditList.get(creditP);
                    System.out.println(allmcredit.getCreditscore());
                    IntroMerchandise introMerchandise = changeCtoIM(allmcredit);
                    creditP++;
                    introMerchandiseArrayList.add(introMerchandise);
                }
            }
            //若salesP还没到底
            else if (creditP==allmcreditList.size()&&salesP<allmsalesList.size()&&priceP==allmpriceList.size()){
                //一个个将salesP往里加，直到出现零分或者无
                Allmsales allmsales = allmsalesList.get(salesP);
                while (salesP<allmsalesList.size()){
                    if (allmsales.getSalesscore()==0){
                        salesP=allmsalesList.size();
                        break;
                    }
                    allmsales = allmsalesList.get(salesP);
                    IntroMerchandise introMerchandise = iAllmsalesService.changeStoIM(allmsales);
                    System.out.println(allmsales.getSalesscore());
                    salesP++;
                    introMerchandiseArrayList.add(introMerchandise);
                }
            }
            //若priceP还没到底
            else if (creditP==allmcreditList.size()&&salesP==allmsalesList.size()&&priceP<allmpriceList.size()){
                //一个个将salesP往里加，直到出现零分或者无
                Allmprice allmprice = allmpriceList.get(priceP);
                while (priceP<allmpriceList.size()){
                    if (allmprice.getPricescore()==0){
                        priceP=allmpriceList.size();
                        break;
                    }
                    allmprice = allmpriceList.get(priceP);
                    IntroMerchandise introMerchandise = iAllmpriceService.changePtoIM(allmprice);
                    System.out.println(allmprice.getPricescore());
                    priceP++;
                    introMerchandiseArrayList.add(introMerchandise);
                }
            }

        }


        return introMerchandiseArrayList;
    }


}
