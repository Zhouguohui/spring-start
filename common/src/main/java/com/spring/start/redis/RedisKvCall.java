package com.spring.start.redis;

/**
 * Created by 50935 on 2019/11/1.
 */
public interface RedisKvCall {
    /**
     * 将哈希表 key 中的域 field 的值设为 value 。
     *
     * 如果 key 不存在，一个新的哈希表被创建并进行 HSET 操作。
     *
     * 如果域 field 已经存在于哈希表中，旧值将被覆盖。
     *
     * @param key
     * @param field
     * @param value
     * @return
     */
    Long hset(String key, String field, String value);

    /**
     * 返回哈希表 key 中给定域 field 的值。
     *
     * @param key
     * @param field
     * @return
     */
    String hget(String key, String field);

    /**
     * 将字符串值 value 关联到 key 。
     *
     * 如果 key 已经持有其他值， SET 就覆写旧值，无视类型。
     *
     * 对于某个原本带有生存时间（TTL）的键来说， 当 SET 命令成功在这个键上执行时， 这个键原有的 TTL 将被清除。
     *
     * @param key
     * @param value
     * @return
     */
    String set(String key, String value);

    /**
     * 返回 key 所关联的字符串值。
     *
     * 如果 key 不存在那么返回特殊值 nil 。
     *
     * 假如 key 储存的值不是字符串类型，返回一个错误，因为 GET 只能用于处理字符串值。
     *
     * @param key
     * @return
     */
    String get(String key);

    /**
     * 将 key 所储存的值加上增量 increment 。
     *
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCRBY 命令。
     *
     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
     *
     * @param key
     * @param integer
     * @return
     */
    Long incrBy(final String key, final long integer);

    /**
     * 将值 value 关联到 key ，并将 key 的生存时间设为 seconds (以秒为单位)。
     *
     * 如果 key 已经存在， SETEX 命令将覆写旧值。
     *
     * @param key
     * @param seconds
     * @param value
     * @return
     */
    String setex(String key, int seconds, String value);

    /**
     * 将 key 的值设为 value ，当且仅当 key 不存在。
     *
     * 若给定的 key 已经存在，则 SETNX 不做任何动作。
     *
     * SETNX 是『SET if Not eXists』(如果不存在，则 SET)的简写。
     *
     * @param key
     * @param value
     * @return
     */
    Long setnx(String key, String value);

    /**
     * 删除给定的一个key 。
     *
     * 不存在的 key 会被忽略。
     *
     * @param key
     * @return
     */
    Long del(String key);

    /**
     * 检查给定 key 是否存在。
     *
     * @param key
     * @return
     */
    Boolean exists(String key);


    /**
     * 为哈希表 key 中的域 field 的值加上增量 increment 。
     *
     * 增量也可以为负数，相当于对给定域进行减法操作。
     *
     * 如果 key 不存在，一个新的哈希表被创建并执行 HINCRBY 命令。
     *
     * 如果域 field 不存在，那么在执行命令前，域的值被初始化为 0 。
     *
     * 对一个储存字符串值的域 field 执行 HINCRBY 命令将造成一个错误。
     *
     * @param key
     * @param field
     * @param value
     * @return
     */
    Long hincrBy(String key, String field, long value);

    /**
     * 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。
     *
     * @param key
     * @param seconds
     * @return
     */
    Long expire(String key, int seconds);

    /**
     * 以秒为单位，返回给定 key 的剩余生存时间(TTL, time to live)。 当 key 不存在时，返回 -2 。 当 key
     * 存在但没有设置剩余生存时间时，返回 -1 。 否则，以秒为单位，返回 key 的剩余生存时间。
     *
     * @param key
     * @return
     */
    Long ttl(String key);

    /**
     * @param key
     * @param field
     * @return
     */
    Long hdel(final String key, final String field);

}
