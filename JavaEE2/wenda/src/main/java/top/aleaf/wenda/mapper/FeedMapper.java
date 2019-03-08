package top.aleaf.wenda.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import top.aleaf.wenda.model.Feed;

import java.util.List;

@Repository
public interface FeedMapper extends Mapper<Feed> {
    /**
     * 从数据库拉取数据的模式
     * @param maxId     增量查询，若第一次看完了最新的10个（假设为100），下次直接拉取最新的第11-20个新鲜事即可（id为81-90）
     * @param userIds   用户关注的所有用户的id
     * @param count     用户获取新鲜事的条数
     * @return
     */
    List<Feed> selectUserFeeds(@Param("maxId") int maxId,
                               @Param("userIds") List<Integer> userIds,
                               @Param("count") int count);
}
