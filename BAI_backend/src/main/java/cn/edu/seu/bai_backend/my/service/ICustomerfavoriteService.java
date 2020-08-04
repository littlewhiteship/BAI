package cn.edu.seu.bai_backend.my.service;

import cn.edu.seu.bai_backend.my.entity.Customerfavorite;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author songyifan
 * @since 2020-07-20
 */
public interface ICustomerfavoriteService extends IService<Customerfavorite> {

    List<Customerfavorite> MyFavorite(String account);
    //查看收藏商品信息
    List<Customerfavorite> MFdelete(String account,Integer FavoriteID);
    //删除收藏信息
    void addmfav(String account,String url);
    //添加收藏信息
}
