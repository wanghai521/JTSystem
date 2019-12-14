package com.jt.util;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HttpClient的工具API
 *
 * @Auther WangHai
 * @Date 2019/12/4
 * @Describle what
 */
@Service
public class HttpClientService {
    @Autowired
    private CloseableHttpClient httpClient;
    @Autowired
    private RequestConfig requestConfig;

    /**
     * 编辑工具API的目的：简化代码，实现松耦合
     * 作用，帮助用户发起Http请求，获取正确的结果并返回给用户
     * 参数设计：1、用户的URL地址 2、Map集合存放参数<参数名，参数值> 3、字符编码
     *
     * @param url
     * @param params
     * @param charset
     * @return
     */
    public String doGet(String url, Map<String, String> params, String charset) {
        // 校验用户是否指定编码格式
        if (StringUtils.isEmpty(charset)) {
            // 用户没有给编码要求，默认就是UTF-8
            charset = "UTF-8";
        }

        // 2、封装URL地址
        if (params != null) {
            url = url + "?";
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                // 拼接参数
                url = url + key + "=" + value + "&";
            }
            // 将多余的&符号去掉
            url = url.substring(0, url.length() - 1);
        }

        // 3、定义HttpGet请求
        HttpGet httpGet = new HttpGet(url);
        // 设置请求超时时间
        httpGet.setConfig(requestConfig);

        // 定义返回值类型
        String result = null;

        try {
            // 执行请求
            CloseableHttpResponse response = httpClient.execute(httpGet);
            // 判断请求是否成功
            if (response.getStatusLine().getStatusCode() == 200) {
                // 这里则表示请求成功
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity, charset);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return result;
    }

    // 重载方法
    public String doGet(String url) {

        return doGet(url, null, null);
    }

    // 重载方法
    public String doGet(String url, Map<String, String> params) {

        return doGet(url, params, null);
    }

    /**
     * 实现post请求
     *
     * @param url
     * @param params
     * @param charset
     * @return
     */
    public String doPost(String url, Map<String, String> params, String charset) {
        // 返回值结果
        String result = null;
        // 定义请求类型
        HttpPost post = new HttpPost(url);
        // 校验用户是否指定编码格式
        if (StringUtils.isEmpty(charset)) {
            // 用户没有给编码要求，默认就是UTF-8
            charset = "UTF-8";
        }

        if (params != null) {
            // 准备List集合信息
            List<NameValuePair> parameters = new ArrayList<>();

            // 将数据封装到List结合集合中
            for (Map.Entry<String, String> entry : params.entrySet()) {
                parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            try {
                // 模拟表单提交
                UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, charset);
                // 将实体对象封装到请求的对象中
                post.setEntity(formEntity);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        try {
            // 发送请求
            CloseableHttpResponse response = httpClient.execute(post);
            // 判断返回值的状态
            if (response.getStatusLine().getStatusCode() == 200) {
                // 说明请求成功
                result = EntityUtils.toString(response.getEntity(), charset);
            } else {
                System.out.println("获取状态码信息：" + response.getStatusLine().getStatusCode());
                throw new RuntimeException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /*重写一些post方法*/
    public String doPost(String url){
        return doPost(url, null, null);
    }
    public String doPost(String url,Map<String,String> params){
        return doPost(url, params, null);
    }
    public String doPost(String url,String charset){
        return doPost(url, null, charset);
    }

}
