package cn.edu.seu.bai_backend.ibuy.service.impl;

import cn.edu.seu.bai_backend.ibuy.entity.Allmprice;
import cn.edu.seu.bai_backend.ibuy.entity.IntroMerchandise;
import cn.edu.seu.bai_backend.ibuy.mapper.AllmpriceMapper;
import cn.edu.seu.bai_backend.ibuy.service.IAllmpriceService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
public class AllmpriceServiceImpl extends ServiceImpl<AllmpriceMapper, Allmprice> implements IAllmpriceService {

    @Override
    public IntroMerchandise changePtoIM(Allmprice allmprice) {
        IntroMerchandise introMerchandise =new IntroMerchandise();
        String amsname = allmprice.getAmpname();
        Float price =allmprice.getAmpprice();
        String link = allmprice.getLink();
        int monthlysales = allmprice.getMonthlysales();
        String platform = allmprice.getPlatform();
        String amsimage = allmprice.getAmpimage();
        introMerchandise.setIntroMerchandise(amsname,price,link,monthlysales,platform,amsimage);
        return introMerchandise;
    }

    @Override
    public List<Allmprice> getIntroPMList(String content,String platform,boolean isPriceStrice,int lowestPrice,int highestPrice) {
        QueryWrapper<Allmprice> queryWrapper = new QueryWrapper<>();
        //按照信誉分数降序排序
        //按照信誉分数降序排序
        queryWrapper.eq("platform",platform);
        queryWrapper.between("ampprice",lowestPrice,highestPrice);
        queryWrapper.like("ampname",content);
        queryWrapper.orderByDesc("pricescore");
        List<Allmprice> allmpriceList = list(queryWrapper);
        return allmpriceList;
    }

    @Override
    public List<Allmprice> changPriceScore(List<Allmprice> allmpriceList, float pricescore) {
        for (int i=0;i<allmpriceList.size();i++){
            float tmpPriceScore =allmpriceList.get(i).getPricescore();
            //改变creditScore的值
            tmpPriceScore=tmpPriceScore*pricescore/100;
            Allmprice allmprice = allmpriceList.get(i);
            allmprice.setPricescore(tmpPriceScore);
            //改变List中的值
            allmpriceList.set(i,allmprice);
        }
        return allmpriceList;
    }

    @Override
    public List<Allmprice> reArrangePrice(List<Allmprice> allmpriceList) {
        //对allmpriceList按照价格分降序重新排序,选择排序
        //对前n-1个与后边的比较，每次确定一个当前最大的
        for(int i=0;i<allmpriceList.size()-1;i++){
            Allmprice allmpricei = allmpriceList.get(i);
            //记录当前最高分数与最高分数位置
            int pMax = i;
            float tmpMaxScore = allmpricei.getPricescore();
            Allmprice maxAllmprice = allmpriceList.get(i);
            for(int j=i+1;j<allmpriceList.size();j++){
                Allmprice allmpricej = allmpriceList.get(j);
                //若当前的分数高则换
                if(allmpricej.getPricescore()>tmpMaxScore){
                    pMax = j;
                    tmpMaxScore = allmpricej.getPricescore();
                    maxAllmprice=allmpricej;
                }
            }
            //一轮比较后确定当前最大的位置与值
            //与i处进行互换
            allmpriceList.set(pMax,allmpricei);
            allmpriceList.set(i,maxAllmprice);
        }
        return allmpriceList;
    }
}
