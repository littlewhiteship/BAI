package cn.edu.seu.bai_backend.ibuy.service;

import cn.edu.seu.bai_backend.ibuy.entity.Allmsales;
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
public interface IAllmsalesService extends IService<Allmsales> {
    //类型转换，将Allmsales转换为标准输出类型IntroMerchandise
    public IntroMerchandise changeStoIM(Allmsales allmsales);

    //获得按照销量排序的Allmcredit类型的List
    public List<Allmsales> getIntroSMList(String content,String platform,boolean isPriceStrice,int lowestPrice,int highestPrice);

    //按照所给比例计算，改变list中的分数
    public List<Allmsales> changSalesScore(List<Allmsales> allmsalesList, float salesscore);

    //对List<Allmsales>按照信誉降序进行重排序
    public List<Allmsales> reArrangeSales(List<Allmsales> allmsalesList);
}
