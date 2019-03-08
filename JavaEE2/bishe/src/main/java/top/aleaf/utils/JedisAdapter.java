/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: JedisAdapter
 * Author:   郭新晔
 * Date:     2019/1/22 0022 14:53
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.aleaf.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.Set;

/**
 * 〈〉
 *
 * @create 2019/1/22 0022
 */
@Component
public class JedisAdapter implements InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(JedisAdapter.class);
    private JedisPool pool;

    @Override
    public void afterPropertiesSet() throws Exception {
        pool = new JedisPool("redis://localhost:6379/2");
    }

    public long sadd(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = this.pool.getResource();
            return jedis.sadd(key, value);
        } catch (Exception e) {
            LOGGER.error("redis添加失败：" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public long srem(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = this.pool.getResource();
            return jedis.srem(key, value);
        } catch (Exception e) {
            LOGGER.error("redis移除失败：" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public boolean sismember(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = this.pool.getResource();
            return jedis.sismember(key, value);
        } catch (Exception e) {
            LOGGER.error("redis查询是否存在失败：" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return false;
    }

    public long scard(String key) {
        Jedis jedis = null;
        try {
            jedis = this.pool.getResource();
            return jedis.scard(key);
        } catch (Exception e) {
            LOGGER.error("redis查询计数失败：" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public long lpush(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = this.pool.getResource();
            return jedis.lpush(key, value);
        } catch (Exception e) {
            LOGGER.error("redis插入失败：" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public List<String> lrange(String key, int low, int high) {
        Jedis jedis = null;
        try {
            jedis = this.pool.getResource();
            return jedis.lrange(key, low, high);
        } catch (Exception e) {
            LOGGER.error("redis范围查询失败：" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public List<String> brpop(int timeout, String key) {
        Jedis jedis = null;
        try {
            jedis = this.pool.getResource();
            return jedis.brpop(timeout, key);
        } catch (Exception e) {
            LOGGER.error("redis查询计数失败：" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public Jedis getJedis() {
        return this.pool.getResource();
    }

    public Transaction multi(Jedis jedis) {
        try {
            return jedis.multi();
        } catch (Exception e) {
            LOGGER.error("开始事务异常");
            e.printStackTrace();
        }
        return null;
    }

    public List<Object> exec(Transaction transaction, Jedis jedis) {
        try {
            return transaction.exec();
        } catch (Exception e) {
            LOGGER.error("事务执行异常");
            transaction.discard();
            e.printStackTrace();
        } finally {
            if (transaction != null) {
                transaction.close();
            }
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public Set<String> zrange(String key, int begin, int end) {
        Jedis jedis = null;
        try {
            jedis = this.pool.getResource();
            return jedis.zrange(key, begin, end);
        } catch (Exception e) {
            LOGGER.error("redis-zset查询失败：" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public Set<String> zrevrange(String key, int begin, int end) {
        Jedis jedis = null;
        try {
            jedis = this.pool.getResource();
            return jedis.zrevrange(key, begin, end);
        } catch (Exception e) {
            LOGGER.error("redis-zset查询失败：" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public Double zscore(String key, int userId) {
        Jedis jedis = null;
        try {
            jedis = this.pool.getResource();
            return jedis.zscore(key, String.valueOf(userId));
        } catch (Exception e) {
            LOGGER.error("redis-zset查询失败：" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public long zcard(String key) {
        Jedis jedis = null;
        try {
            jedis = this.pool.getResource();
            return jedis.zcard(key);
        } catch (Exception e) {
            LOGGER.error("redis-zset查询失败：" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }
}