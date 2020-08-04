package cn.edu.seu.bai_backend.ishop.service;

import cn.edu.seu.bai_backend.ishop.entity.Blogger;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author nqr
 * @since 2020-07-29
 */
public interface IBloggerService extends IService<Blogger> {
    public List<Blogger> getIntoBlogger(int shopid);
}
