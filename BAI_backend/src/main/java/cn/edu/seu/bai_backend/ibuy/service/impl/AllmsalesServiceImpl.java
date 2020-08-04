package cn.edu.seu.bai_backend.ibuy.service.impl;

import cn.edu.seu.bai_backend.ibuy.entity.Allmsales;
import cn.edu.seu.bai_backend.ibuy.entity.IntroMerchandise;
import cn.edu.seu.bai_backend.ibuy.mapper.AllmsalesMapper;
import cn.edu.seu.bai_backend.ibuy.service.IAllmsalesService;
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
public class AllmsalesServiceImpl extends ServiceImpl<AllmsalesMapper, Allmsales> implements IAllmsalesService {

    @Override
    public IntroMerchandise changeStoIM(Allmsales allmsales) {
        IntroMerchandise introMerchandise =new IntroMerchandise();
        String amsname = allmsales.getAmsname();
        Float price =allmsales.getPrice();
        String link = allmsales.getLink();
        int monthlysales = allmsales.getMonthlysales();
        String platform = allmsales.getPlatform();
        String amsimage = allmsales.getAmsimage();
        introMerchandise.setIntroMerchandise(amsname,price,link,monthlysales,platform,amsimage);
        return introMerchandise;
    }

    @Override
    public List<Allmsales> getIntroSMList(String content,String platform,boolean isPriceStrice,int lowestPrice,int highestPrice) {
        QueryWrapper<Allmsales> queryWrapper = new QueryWrapper<>();
        //按照销量分数降序排序
        queryWrapper.eq("platform",platform);
        queryWrapper.between("price",lowestPrice,highestPrice);
        queryWrapper.like("amsname",content);
        queryWrapper.orderByDesc("salesscore");
        List<Allmsales> allmsalesList = list(queryWrapper);
        return allmsalesList;
    }

    @Override
    public List<Allmsales> changSalesScore(List<Allmsales> allmsalesList, float salesscore) {
        for (int i=0;i<allmsalesList.size();i++){
            float tmpSalesScore =allmsalesList.get(i).getSalesscore();
            //改变creditScore的值
            tmpSalesScore=tmpSalesScore*salesscore/100;
            Allmsales allmsales = allmsalesList.get(i);
            allmsales.setSalesscore(tmpSalesScore);
            //改变List中的值
            allmsalesList.set(i,allmsales);
        }
        return allmsalesList;
    }

    @Override
    public List<Allmsales> reArrangeSales(List<Allmsales> allmsalesList) {
        //对allmsalesList按照价格分降序重新排序,选择排序
        //对前n-1个与后边的比较，每次确定一个当前最大的
        for(int i=0;i<(allmsalesList.size()-1);i++){
            Allmsales allmsalesi = allmsalesList.get(i);
            //记录当前最高分数与最高分数位置
            int pMax = i;
            float tmpMaxScore = allmsalesi.getSalesscore();
            Allmsales maxAllmsales = allmsalesList.get(i);
            for(int j=i+1;j<allmsalesList.size();j++){
                Allmsales allmsalesj = allmsalesList.get(j);
                //若当前的分数高则换
                if(allmsalesj.getSalesscore()>tmpMaxScore){
                    pMax = j;
                    tmpMaxScore = allmsalesj.getSalesscore();
                    maxAllmsales=allmsalesj;
                }
            }
            //一轮比较后确定当前最大的位置与值
            //与i处进行互换
            allmsalesList.set(pMax,allmsalesi);
            allmsalesList.set(i,maxAllmsales);
        }
        System.out.println("Allmsales排序后的顺序"+allmsalesList);
        return allmsalesList;
    }

}
