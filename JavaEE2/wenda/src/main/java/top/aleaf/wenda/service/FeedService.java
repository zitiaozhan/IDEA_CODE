package top.aleaf.wenda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.aleaf.wenda.mapper.FeedMapper;
import top.aleaf.wenda.model.Feed;

import java.util.List;

@Service
public class FeedService {
    @Autowired
    private FeedMapper feedMapper;

    public List<Feed> getUserFeeds(int maxId, List<Integer> userIds, int count) {
        return feedMapper.selectUserFeeds(maxId, userIds, count);
    }

    public boolean addFeed(Feed feed) {
        return feedMapper.insert(feed) > 0;
    }

    public Feed getById(int id) {
        return feedMapper.selectByPrimaryKey(id);
    }
}
