/**
 * 
 */
package org.yelong.ssm.spring.boot.autoconfigure;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.yelong.support.orm.mybaits.interceptor.MyBatisPlaceholderMapInterceptor;

import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;

/**
 * 添加mybatis参数拦截器<br/>
 * 该类在存在分页插件时不进行注入
 * @author PengFei
 */
@Configuration
@ConditionalOnBean(SqlSessionFactory.class)
@ConditionalOnMissingBean(PageHelperAutoConfiguration.class)
@AutoConfigureAfter(MybatisAutoConfiguration.class)
public class MyBatisParamInterceptorAutoConfiguration {

	@Autowired
	private List<SqlSessionFactory> sqlSessionFactoryList;

	@PostConstruct
	public void addMyBaticParamInterceptor() {
		MyBatisPlaceholderMapInterceptor interceptor = new MyBatisPlaceholderMapInterceptor();
		sqlSessionFactoryList.forEach( x -> x.getConfiguration().addInterceptor(interceptor) );
	}
	
}
