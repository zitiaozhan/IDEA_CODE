package top.aleaf.sync.score;

import java.util.List;

/**
 * 〈〉
 *
 * @author 郭新晔
 * @create 2019/4/6 0006
 */
public interface ScoreComputer {

    /**
     * 根据ID计算对应项目的科技分
     *
     * @param entityId
     * @return
     */
    boolean computeProjectScore(int entityId);

    /**
     * 获得项目的相关人员编号
     *
     * @param entityId 项目ＩＤ
     * @return 项目的相关人员编号
     */
    List<String> getAuthorNumberList(int entityId);

    /**
     * 清理项目的已分配给作者的科技分
     *
     * @param entityId 项目id
     * @return 是否成功
     */
    boolean clearAuthorScore(int entityId);

    /**
     * 恢复已删除项目清空上一次科技分
     *
     * @param entityId 项目id
     * @return 是否成功
     */
    boolean restore(int entityId);
}