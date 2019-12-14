package com.jt.config;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.pool.PoolStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * 关闭连接的配置文件
 *
 * @Auther WangHai
 * @Date 2019/12/4
 * @Describle what
 */
@Component // 交给Spring容器管理
public class HttpClientClose extends Thread {
    @Autowired
    private PoolingHttpClientConnectionManager manager;

    // 开关 volatile表示欧线程可变数据，一个线程修改，则其他线程立即修改
    private volatile boolean shutdown;

    public HttpClientClose() {
        // 线程开启启动
        this.start();
    }

    // 重写run方法

    @Override
    public void run() {
        try {
            // 如果服务器没有关闭，执行下面线程
            while (!shutdown) {
                synchronized (this) {

                    wait(5000); // 等待5秒

                    System.out.println("线程开始执行，关闭超时连接");
                }
                // 下面关闭超时连接
                PoolStats stats = manager.getTotalStats();

                int available = stats.getAvailable(); // 获取可用的线程的数量

                int pending = stats.getPending(); // 获得阻塞线程的数量

                int leased = stats.getLeased(); // 获取当前正在使用的连接数量

                int max = stats.getMax(); // 应该是最大连接数

                manager.closeExpiredConnections();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        super.run();
    }

    @PreDestroy // 在销毁前执行该方法
    public void shutdown() {
        shutdown = true;
        synchronized (this) {
            notifyAll(); // 全部唤醒等待的线程
        }
    }
}
