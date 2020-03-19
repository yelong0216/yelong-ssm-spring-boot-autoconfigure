package org.yelong.ssm.spring.boot.autoconfigure;

import org.apache.commons.lang3.ArrayUtils;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.yelong.core.annotation.Nullable;
import org.yelong.core.jdbc.dialect.Dialects;
import org.yelong.ssm.SsmConfiguration;
import org.yelong.ssm.SsmModelProperties;

/**
 * 单数据源属性自动注入
 * 
 * 此单数据源并非是一个数据源，而是此数据源由spring配置的，而不是我们自己配置的数据源。
 * 
 * 单数据源中通过默认的配置进行初始化配置model的bean。
 * modelbean的默认配置详见{@link SsmModelProperties}
 * @author PengFei
 */
@Configuration
@ConditionalOnBean(MybatisAutoConfiguration.class)
@AutoConfigureAfter({MybatisAutoConfiguration.class,SsmMultiDataSourceAutoConfiguration.class})
@ConditionalOnMissingBean(SsmMultiDataSourceAutoConfiguration.class)
@ConditionalOnProperty(prefix = SsmConfiguration.SSM_PROPERTIES_PREFIX,
						name = "dataSource",
						havingValue = "true",
						matchIfMissing = true)
public class SsmSingleDataSourceAutoConfiguration implements ApplicationContextAware{

	public static final String PROPERTIES_PREFIX = SsmConfiguration.SSM_PROPERTIES_PREFIX;

	@Nullable
	private String databaseDialect;

	@Nullable
	private String ssmBeanDefinitionBuilderClassName;

	@Bean
	@ConditionalOnBean(MybatisAutoConfiguration.class)
	public SsmConfiguration ssmConfiguration() throws Exception {
		SsmConfiguration ssmConfiguration = new SsmConfiguration();
		SsmModelProperties ssmModelProperties = new SsmModelProperties();
		ssmModelProperties.setDatabaseDialect(databaseDialect);
		ssmModelProperties.setSsmBeanDefinitionBuilderClassName(ssmBeanDefinitionBuilderClassName);
		ssmConfiguration.setSsmModelProperties( ArrayUtils.toArray(ssmModelProperties));
		return ssmConfiguration;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		Environment environment = applicationContext.getEnvironment();
		this.databaseDialect = environment.getProperty(PROPERTIES_PREFIX+".databaseDialect");
		this.ssmBeanDefinitionBuilderClassName = environment.getProperty(PROPERTIES_PREFIX+".ssmBeanDefinitionBuilderClassName");
		//默认的方言根据url取
		String jdbcUrl = environment.getProperty("spring.datasource.url");
		if( null != jdbcUrl && null == databaseDialect ) {
			databaseDialect = getDatabaseDialect(jdbcUrl);
		}
	}

	private String getDatabaseDialect(String jdbcUrl) {
		for (Dialects spliceSqlType : Dialects.values()) {
			if (jdbcUrl.indexOf(":" + spliceSqlType.name().toLowerCase() + ":") != -1) {
				return spliceSqlType.name();
			}
		}
		return null;
	}
	
}
