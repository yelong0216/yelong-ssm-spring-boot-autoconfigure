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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.yelong.ssm.spring.boot.autoconfigure.DataSourceBuilderUtils;

/**
 * 主数据源配置
 * @author pengfei<yl1430834495@163.com>
 * @date 2019年11月6日下午3:00:07
 * @version 1.2
 */
//@Configuration
@MapperScan(basePackages = "org.yl.ssm.boot.test.primary")
public class PrimaryDataSourceConfig {

	@Primary
	@Bean(name="primaryDataSource")
	public DataSource getSecondaryDataSource() throws Exception {
		return DataSourceBuilderUtils.createDataSourceBuilder("oracle-model-configuration.properties");
	}
	
	@Primary
	@Bean(name = "primarySqlSessionFactory")
	public SqlSessionFactory primarySqlSessionFactory(@Qualifier("primaryDataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:org/yl/ssm/boot/test/primary/mapping/*.xml"));
		return bean.getObject();
	}
	
	@Primary
	@Bean("primarySqlSessionTemplate")
	public SqlSessionTemplate primarySqlSessionTemplate(@Qualifier("primarySqlSessionFactory")SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
}
