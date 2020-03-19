/**
 * 
 */
package org.yelong.ssm.spring.boot.autoconfigure.demo;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.yelong.ssm.spring.boot.autoconfigure.DataSourceBuilderUtils;

/**
 * 第二个数据源配置
 * @author pengfei<yl1430834495@163.com>
 * @date 2019年11月6日下午3:00:07
 * @version 1.2
 */
//@Configuration
@MapperScan(basePackages = "org.yl.ssm.boot.test.secondary")
public class SecondaryDataSourceConfig {

	@Bean(name="secondaryDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.secondary")
	public DataSource getSecondaryDataSource() throws Exception {
		return DataSourceBuilderUtils.createDataSourceBuilder("mysql-model-configuration.properties");
	}
	
	@Bean(name = "secondarySqlSessionFactory")
	public SqlSessionFactory secondarySqlSessionFactory(@Qualifier("secondaryDataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:org/yl/ssm/boot/test/secondary/mapping/*.xml"));
		return bean.getObject();
	}
	
	@Bean("secondarySqlSessionTemplate")
	public SqlSessionTemplate secondarySqlSessionTemplate(@Qualifier("secondarySqlSessionFactory")SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
}
