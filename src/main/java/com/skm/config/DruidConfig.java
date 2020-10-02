package com.skm.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

/***
 * 
 * @author sikongming
 * 可能和Spring的版本有关。试了好几个都不行。最后参考：https://blog.csdn.net/yhflyl/article/details/81228669
 * DRUID的监控页面：http://localhost:8080/druid/login.html
 */
@Configuration
public class DruidConfig {

    @Bean
    // 将所有前缀为spring.datasource下的配置项都加载DataSource中
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource() {
        return new DruidDataSource();
    }

    @Bean
    public ServletRegistrationBean<Servlet> druidServlet() {
    	ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
		Map<String,String> initParams = new HashMap<>();
		initParams.put("loginUsername","admin");
		initParams.put("loginPassword","admin");
		initParams.put("allow","");//默认就是允许所有访问
		initParams.put("deny","192.168.15.21");
		bean.setInitParameters(initParams);
		return bean;
    }
    
    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean() {
    	FilterRegistrationBean bean = new FilterRegistrationBean();
		bean.setFilter(new WebStatFilter());
		Map<String,String> initParams = new HashMap<>();
		initParams.put("exclusions","*.js,*.css,/druid/*");
		bean.setInitParameters(initParams);
		bean.setUrlPatterns(Arrays.asList("/*"));
		return  bean;
    }
}