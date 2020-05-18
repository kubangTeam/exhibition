package cn.edu.hqu.cst.kubang.exhibition.Utilities;

/**
 * @Author SunChonggao
 * @Date 2020/4/2 16:33
 * @Version 1.0
 * @Description:常量
 */
public interface Constants {
    /**
     * 上传成功，等待审核
     */
    int STATE_WAIT_VERIFY = 1;
    /**
     * 审核通过
     */
    int STATE_IS_PASS = 2;
    /**
     * 审核未通过
     */
    int STATE_IS_REFUSE = 3;
    /**
     * 修改展品优先级后等待审核
     */
    int STATE_WAIT_VERIFY_PRIORITY = 4;
    /**
     * 修改展品信息后等待审核
     */
    int STATE_WAIT_VERIFY_INFO= 5;
    /**
     * 已删除状态
     */
    int STATE_IS_DELETED = 6;
    /**
     * 推荐展品数量
     */
    int COUNT_RECOMMEND_1 = 6;
    /**
     * 推荐展品数量
     */
    int COUNT_RECOMMEND_2= 4;
}
