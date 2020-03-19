/**
 * 
 */
package org.yelong.ssm.spring.boot.autoconfigure;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;
import org.yelong.ssm.SsmConfiguration;

/**
 * 多数据源bean注入
 * 
 * 多数据源中以配置文件来配置model bean。
 * 
 * 配置多数据源详见org.yelong.ssm.spring.boot.autoconfigure.demo包
 * 
 * 使用pageHepler分页时，请将pagehelper.autoRuntimeDialect设置为true。用来区分数据库方言
 * 
 * @author PengFei
 */
@Configuration
@ConditionalOnBean(MybatisAutoConfiguration.class)
@AutoConfigureAfter(MybatisAutoConfiguration.class)
@ConditionalOnProperty(prefix = SsmConfiguration.SSM_PROPERTIES_PREFIX,
						name = "multiDataSource",
						havingValue = "true")
public class SsmMultiDataSourceAutoConfiguration implements ApplicationContextAware{
	
	private String [] configLocations;

	@Bean
	public SsmConfiguration ssmConfiguration() throws Exception {
		SsmConfiguration ssmConfiguration = new SsmConfiguration();
		Assert.notNull(configLocations, 
				"启动多数据源model配置时未发现配置文件！");
		ssmConfiguration.setConfigLocations(configLocations);
		return ssmConfiguration;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		Environment environment = applicationContext.getEnvironment();
		String configLocations = environment.getProperty(SsmConfiguration.SSM_PROPERTIES_PREFIX+".configLocations");
		if( StringUtils.isNotBlank(configLocations)) {
			this.configLocations = configLocations.split(",");
		}
	}
	
}
