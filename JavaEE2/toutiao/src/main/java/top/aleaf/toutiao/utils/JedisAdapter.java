/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: JedisAdapter
 * Author:   郭新晔
 * Date:     2018/12/13 0013 20:43
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.toutiao.utils;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * 〈〉
 *
 * @create 2018/12/13 0013
 */
@Service
public class JedisAdapter implements InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(JedisAdapter.class);
    private JedisPool pool;

    @Override
    public void afterPropertiesSet() throws Exception {
        pool = new JedisPool("localhost", 6379);
    }

    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.get(key);
        } catch (Exception e) {
            LOGGER.error("Reids get方法" + e.getMessage());
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public void set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.set(key, value);
        } catch (Exception e) {
            LOGGER.error("Reids set方法" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public void lpush(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.lpush(key, value);
        } catch (Exception e) {
            LOGGER.error("Reids lpush方法" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public List<String> brpop(int timeout, String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.brpop(timeout, key);
        } catch (Exception e) {
            LOGGER.error("Reids brpop 方法" + e.getMessage());
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public Long llen(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.llen(key);
        } catch (Exception e) {
            LOGGER.error("Reids llen方法" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0L;
    }

    /**
     * 添加到集合
     *
     * @param key
     * @param value
     * @return
     */
    public long sadd(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sadd(key, value);
        } catch (Exception e) {
            LOGGER.error("Reids Set添加异常" + e.getMessage());
            return 0;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 从集合中移除
     *
     * @param key
     * @param value
     * @return
     */
    public long srem(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.srem(key, value);
        } catch (Exception e) {
            LOGGER.error("Reids Set添加异常" + e.getMessage());
            return 0;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 是否存在集合之中
     *
     * @param key
     * @param value
     * @return
     */
    public boolean sismember(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sismember(key, value);
        } catch (Exception e) {
            LOGGER.error("Reids Set添加异常" + e.getMessage());
            return false;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 集合中元素的个数
     *
     * @param key
     * @return
     */
    public long scard(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.scard(key);
        } catch (Exception e) {
            LOGGER.error("Reids Set添加异常" + e.getMessage());
            return 0;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public void setObject(String key, Object obj) {
        set(key, JSON.toJSONString(obj));
    }

    public <T> T getObject(String key, Class<T> clazz) {
        String value = get(key);
        if (value == null) {
            return null;
        }
        return JSON.parseObject(value, clazz);
    }

    public static void print(int index, Object object) {
        System.out.println(String.format("%d, %s", index, object.toString()));
    }

    /*public static void main(String[] args) {
        Jedis jedis = new Jedis();
        //清除所有KV值
        jedis.flushAll();

        //设置KV
        jedis.set("userName", "haha");
        jedis.set("password", "123456");
        print(1, jedis.get("userName"));
        print(2, jedis.get("password"));
        //key的重命名
        jedis.rename("userName", "uname");
        print(3, jedis.get("uname"));

        //为KV设置过期时间，单位：秒
        jedis.setex("test", 30, "OK");
        print(4, jedis.get("test"));

        //值自增1
        jedis.set("num", "101");
        print(5, jedis.incr("num"));

        //值自增指定值
        print(6, jedis.incrBy("num", 8));

        //列表操作
        String listName = "list";
        int index = 0;
        for (; index < 10; index++) {
            jedis.lpush(listName, "O" + String.valueOf(index));
        }
        //列表范围打印
        print(7, jedis.lrange(listName, 0, index));
        //列表长度获取
        print(8, jedis.llen(listName));
        print(9, jedis.lpop(listName));
        print(10, jedis.llen(listName));
        print(11, jedis.lindex(listName, 2));
        print(12, jedis.linsert(listName, ListPosition.AFTER, "O6", "after"));
        print(13, jedis.linsert(listName, ListPosition.BEFORE, "O6", "before"));
        print(14, jedis.lrange(listName, 0, 12));

        //操作HashSet
        String userKey = "user1";
        jedis.hset(userKey, "name", "Tom");
        jedis.hset(userKey, "age", "16");
        jedis.hset(userKey, "phone", "11511511511");

        print(14, jedis.hgetAll(userKey));
        jedis.hdel(userKey, "phone");
        print(15, jedis.hgetAll(userKey));
        print(16, jedis.hkeys(userKey));
        print(17, jedis.hvals(userKey));
        print(17, jedis.hexists(userKey, "age"));
        //不存在才会设置
        jedis.hsetnx(userKey, "name", "Tata");
        jedis.hsetnx(userKey, "email", "13@qq.com");
        print(18, jedis.hgetAll(userKey));

        //set操作
        String likeNews1 = "newsLike1";
        String likeNews2 = "newsLike2";
        for (int i = 0; i < 10; i++) {
            jedis.sadd(likeNews1, String.valueOf(i));
            jedis.sadd(likeNews2, String.valueOf(i * i));
        }
        print(19, jedis.smembers(likeNews1));
        print(20, jedis.smembers(likeNews2));
        //求交集
        print(21, jedis.sinter(likeNews1, likeNews2));
        //求并集
        print(22, jedis.sunion(likeNews1, likeNews2));
        //求差集
        print(23, jedis.sdiff(likeNews1, likeNews2));
        //删除set元素
        print(24, jedis.sismember(likeNews1, "5"));
        jedis.srem(likeNews1, "5");
        print(25, jedis.smembers(likeNews1));

        print(26, jedis.scard(likeNews1));
        //元素在集合之间移动
        print(27, jedis.smove(likeNews2, likeNews1, "81"));
        print(28, jedis.scard(likeNews1));
        print(29, jedis.smembers(likeNews1));

        //优先队列
        String rankKey = "rankKey";
        jedis.zadd(rankKey, 36, "Liu");
        jedis.zadd(rankKey, 63, "Pi");
        jedis.zadd(rankKey, 56, "Hu");
        jedis.zadd(rankKey, 92, "Zhang");
        jedis.zadd(rankKey, 88, "Yan");
        print(30, jedis.zcard(rankKey));
        //某个区间内的数目
        print(31, jedis.zcount(rankKey, 60, 90));
        //某个人的分数
        print(32, jedis.zscore(rankKey, "Zhang"));
        //升序排序
        print(33, jedis.zrange(rankKey, 0, 2));
        //降序
        print(34, jedis.zrevrange(rankKey, 0, 5));

        for (Tuple tuple : jedis.zrangeByScoreWithScores(rankKey, 0, 100)) {
            print(35, tuple.getElement() + " : " + tuple.getScore());
        }
        print(36, jedis.zrank(rankKey, "Yan"));
        print(37, jedis.zrevrank(rankKey, "Yan"));

        //Jedis池，默认池中有8条资源
        JedisPool pool = new JedisPool();
        for (int i = 0; i < 100; i++) {
            Jedis j = pool.getResource();
            j.get("Ha");
            System.out.println("Pool " + i);
            j.close();  //将使用完的连接放回连接池
        }

        //异步队列（例如，当很多人为你点赞之后，将点赞的事件添加到队列中，当机器空闲时处理通知）
        //判题队列（例如，许多人在做题，当判断题目答案的机器不够用时，增加机器处理同时增加了队列长度）
    }*/
}