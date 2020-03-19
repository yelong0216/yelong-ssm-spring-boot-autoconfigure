/**
 * 
 */
package org.yelong.ssm.spring.boot.autoconfigure;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.IterableUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.yelong.support.orm.mybaits.interceptor.ModelResultSetHandlerInteceptor;

/**
 * @author 彭飞
 * @date 2019年10月29日下午2:38:18
 * @version 1.2
 */
@Configuration
@ConditionalOnBean(SqlSessionFactory.class)
@AutoConfigureAfter(MybatisAutoConfiguration.class)
public class ModelResultInterceptorAutoConfiguration {

	@Autowired
	private List<SqlSessionFactory> sqlSessionFactoryList;

	@PostConstruct
	public void addMyBaticParamInterceptor() {
		ModelResultSetHandlerInteceptor interceptor = new ModelResultSetHandlerInteceptor();
		sqlSessionFactoryList.forEach( x -> {
			List<Interceptor> interceptors = x.getConfiguration().getInterceptors();
			//			if(!CollectionUtils.exists(interceptors, y->{
			//				return y instanceof ModelResultSetHandlerInteceptor;
			//			})) {
			//				x.getConfiguration().addInterceptor(interceptor);
			//			}
			if(!IterableUtils.matchesAny(interceptors, y->{
				return y instanceof ModelResultSetHandlerInteceptor;
			})) {
				x.getConfiguration().addInterceptor(interceptor);
			}
		});
	}

}
