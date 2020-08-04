package cn.edu.seu.bai_backend.ishop.service;

import cn.edu.seu.bai_backend.ishop.entity.Msstorecredit;
import cn.edu.seu.bai_backend.ishop.entity.Msstoresales;
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
public interface IMsstorecreditService extends IService<Msstorecredit> {
    //获取推荐店铺按信誉排序表
    public List<Msstorecredit> getMsstoreCreditList(String type);

    //public  List<Msstore> tmpgetIntroStoreList();

    //合并两表获得最终Msstorecredit表格
    public List<Msstore> comBineTwoList(List<Msstorecredit> msstorecreditList, List<Msstoresales> msstoresalesList);

    public Msstorecredit changeType(Msstoresales msstoresales);

    public Msstore changeMCTypeToMsstore(Msstorecredit msstorecredit);

    public Msstore changeMSTypeToMsstore(Msstoresales msstoresales);

    //返回前端所需要的同类店铺推荐的数据表
    public  List<Msstore> getIntroStoreList(String type,float credit,float sale);


}
