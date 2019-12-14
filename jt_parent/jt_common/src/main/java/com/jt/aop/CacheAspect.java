package com.jt.aop;

import com.jt.annotation.CacheFind;
import com.jt.util.ObjectMapperUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisCluster;

/**
 * @Auther WangHai
 * @Date 2019/12/1
 * @Describle what
 */
@Component
@Aspect
public class CacheAspect {
    // 实行懒加载，项目不需要时，不会加载
    @Autowired(required = false)
    private JedisCluster jedisCluster;

    /**
     * 定义环绕通知
     * 环绕通知，必须将ProceedingJoinPoint，而且必须放在第一位
     *
     * @return
     */
    @Around("@annotation(cacheFind)")
    public Object around(ProceedingJoinPoint joinPoint, CacheFind cacheFind) {
        Object obj = null;

        // 对自定义注解所注解的方法，获取key ：自定义或者类名.方法名::第一个参数值
        String key = getKey(joinPoint, cacheFind);

        String resultJson = jedisCluster.get(key);

        try {
            if (StringUtils.isEmpty(resultJson)) {
                // 执行目标方法,也就是第一次执行数据库查询
                obj = joinPoint.proceed();
                // 将数据转换成json，之后存入缓存
                String json = ObjectMapperUtil.objectToJson(obj);

                // 判断用户有没有传入超时时间 ,并将其存入缓存
                if (cacheFind.second()==0){
                    jedisCluster.set(key, json);
                }else {
                    jedisCluster.setex(key, cacheFind.second(), json);
                }
                System.out.println("第一次执行数据库查询");
            }else {
                // 表示缓存有数据
                Class returnType = getType(joinPoint);
                obj = ObjectMapperUtil.jsonToObject(resultJson, returnType);
                System.out.println("执行了缓存查询");
            }

        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new RuntimeException(throwable);
        }

        return obj;
    }

    /**
     * 根据joinPoint获取其返回值类型 class
     * 1、利用方法对象获取返回值类型
     * @param joinPoint
     * @return
     */
    private Class getType(ProceedingJoinPoint joinPoint){
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        // 获取返回值类型
        Class returnType = signature.getReturnType();

        return returnType;
    }

    /**
     * 获取注解所注解的方法的key
     * 1、判断用户有没有给定key的值
     * 2、没有给自定义值，则进行自己拼接key 类名.方法名::第一个参数值
     *
     * @return
     */
    private String getKey(ProceedingJoinPoint joinPoint, CacheFind cacheFind) {

        // 获取当前方法的名称 类名.方法名
        String className = joinPoint.getSignature().getDeclaringTypeName();

        // 获得备注接的方法的名称
        String methodName = joinPoint.getSignature().getName();

        String key = cacheFind.key();
        if (!StringUtils.isEmpty(key)) {
            // r如果用户给了指定的key
            return className + methodName + "::" + key;
        } else {
            // 如果没给指定的key
            // 获取方法的第一个参数
            Object args = joinPoint.getArgs()[0];
            return className + methodName + "::" + args;
        }

    }
}
