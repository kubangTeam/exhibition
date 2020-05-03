package cn.edu.hqu.cst.kubang.exhibition.Utilities;

/**
 * @Author SunChonggao
 * @Date 2020/4/2 16:33
 * @Version 1.0
 * @Description:常量
 */
public interface Constants {
    /**
     * 保存成功，等待上传
     */
    int STATE_IS_ON_READY = 0;
    /**
     * 审核未通过
     */
    int STATE_IS_ON_REFUSE = 3;
    /**
     * 审核通过
     */
    int STATE_IS_ON_SHOW = 2;
    /**
     * 已删除状态
     */
    int STATE_IS_DELETED = 4;
    /**
     * 推荐展品数量
     */
    int COUNT_RECOMMEND = 6;
}
