/**
 * 
 */
package org.yelong.ssm.spring.boot.autoconfigure;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.yelong.commons.util.PropertiesUtils;
import org.yelong.core.jdbc.DataSourceProperties;
import org.yelong.support.properties.wired.PropertiesWiredProcessorBuilder;

/**
 * 读取自定义的配置创建DataSource
 * @author PengFei
 */
public class DataSourceBuilderUtils {

	public static final String DATASOURCE_PREFIX = "datasource";
	
	/**
	 * 根据配置中的属性创建
	 * @param <D>
	 * @return {@link DataSource}
	 */
	@SuppressWarnings("unchecked")
	public static <D extends DataSource>  D createDataSourceBuilder(String configLocation) throws Exception{
		DataSourceProperties dataSourceProperties = PropertiesWiredProcessorBuilder.builder(DataSourceProperties.class, PropertiesUtils.load(configLocation), DATASOURCE_PREFIX).wiredObj();
		DataSourceBuilder<D> dataSourceBuilder = (DataSourceBuilder<D>) DataSourceBuilder.create();
		dataSourceBuilder.url(dataSourceProperties.getUrl());
		dataSourceBuilder.username(dataSourceProperties.getUsername());
		dataSourceBuilder.password(dataSourceProperties.getPassword());
		dataSourceBuilder.driverClassName(dataSourceProperties.getDriverClassName());
		return dataSourceBuilder.build();
	}
	
}
