package cn.edu.seu.bai_backend.ibuy.service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author nqr
 * @since 2020-07-20
 */
public interface ISpiderService {

    //void requestData1(int page,int type,String content);

    public void start(String content) throws InterruptedException;
}
