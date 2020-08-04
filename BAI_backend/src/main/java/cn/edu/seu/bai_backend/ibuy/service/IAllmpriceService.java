package cn.edu.seu.bai_backend.ibuy.service;

import cn.edu.seu.bai_backend.ibuy.entity.Allmprice;
import cn.edu.seu.bai_backend.ibuy.entity.IntroMerchandise;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author nqr
 * @since 2020-07-20
 */
public interface IAllmpriceService extends IService<Allmprice> {
    //类型转换，将Allmprice转换为标准输出类型IntroMerchandise
    public IntroMerchandise changePtoIM(Allmprice allmprice);

    //获得按照价格排序的Allmcredit类型的List
    public List<Allmprice> getIntroPMList(String content,String platform,boolean isPriceStrice,int lowestPrice,int highestPrice);

    //按照所给比例计算，改变list中的分数
    public List<Allmprice> changPriceScore(List<Allmprice> allmpriceList,float pricescore);

    //对List<Allmprice>按照信誉降序进行重排序
    public List<Allmprice> reArrangePrice(List<Allmprice> allmpriceList);

}
