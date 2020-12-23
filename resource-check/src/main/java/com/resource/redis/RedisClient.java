package com.resource.redis;

import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author resource
 * @date 2020/9/2 20:06
 * @description TODO 用于修改配额时，暂存将要修改的配额参数
 */
public class RedisClient {

    //非切片额客户端连接
    private static Jedis jedis = null;
    //非切片连接池
    private static JedisPool jedisPool;
    //切片额客户端连接
    private static ShardedJedis shardedJedis;
    //切片连接池
    private static ShardedJedisPool shardedJedisPool;

    /**
     * 初始化非切片池
     */
    private static void initialPool() {
        //池基本配置
        JedisPoolConfig config = new JedisPoolConfig();
        //控制一个pool最多有多少个状态为idle(空闲)的jedis实例；minIdle：默认值为0
        config.setMaxIdle(5);
        //表示当borrow一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛JedisConnectionException；maxWaitMillis：默认值-1
        config.setMaxWaitMillis(10001);
        //在指定时刻通过pool能够获取到的最大的连接的jedis个数
        config.setMaxTotal(20);
        //获得一个jedis实例的时候是否检查连接可用性（ping()）；如果为true，则得到的jedis实例均是可用的；默认是false
        config.setTestOnBorrow(false);
        jedisPool = new JedisPool(config, "127.0.0.1", 6379);
    }

    /**
     * 初始化切片池
     */
    private static void initialShardedPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(5);
        config.setMaxWaitMillis(10001);
        config.setMaxTotal(20);
        config.setTestOnBorrow(false);
        //slave链接
        List<JedisShardInfo> shards = new ArrayList<>();
        shards.add(new JedisShardInfo("10.2.21.20", 6379));
        //构造池
        shardedJedisPool = new ShardedJedisPool(config, shards);
    }

    static {
        initialPool();
        jedis = jedisPool.getResource();

//        initialShardedPool();
//        shardedJedis = shardedJedisPool.getResource();
    }

    public static Jedis getJedis() {
        return jedis;
    }

    public static void main(String[] args) {
        RedisClient.getJedis().set("jfk","124314");
        System.out.println(RedisClient.getJedis().get("jfk"));
    }
}
