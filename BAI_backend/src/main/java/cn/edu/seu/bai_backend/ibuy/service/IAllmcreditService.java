package cn.edu.seu.bai_backend.ibuy.service;

import cn.edu.seu.bai_backend.ibuy.entity.Allmcredit;
import cn.edu.seu.bai_backend.ibuy.entity.Allmprice;
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
public interface IAllmcreditService extends IService<Allmcredit> {

    //类型转换，将Allmcredit转换为标准输出类型IntroMerchandise
    public IntroMerchandise changeCtoIM(Allmcredit allmcredit);

    //获得按照信誉排序的Allmcredit类型的List
    public List<Allmcredit> getIntroCMList(String content,String platform,boolean isPriceStrice,int lowestPrice,int highestPrice);

    //按照所给比例计算，改变list中的分数
    public List<Allmcredit> changeCreditScore(List<Allmcredit> allmcreditList,float creditscore);

    //合并三个列表，按分数高低整合成一个IntroMerchandise表格
    public List<IntroMerchandise> combineThreeList(List<Allmcredit> allmcreditList, List<Allmprice> allmpriceList, List<Allmsales> allmsalesList);

    //输入三个重要度，输出为推荐列表
    public List<IntroMerchandise> getIntroMList(float creditscore,float pricescore,float salescredit,String content,String platform,boolean isPriceStrice,int lowest,int highest);

    //对List<Allmcredit>按照信誉降序进行重排序
    public List<Allmcredit> reArrangeCredit(List<Allmcredit> allmcreditList);

    public List<IntroMerchandise> mergeThreeScore(List<Allmcredit> allmcreditList,List<Allmsales> allmsalesList,List<Allmprice> allmpriceList);


}
