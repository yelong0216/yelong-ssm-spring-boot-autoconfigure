/**
 * 
 */
package org.yelong.ssm.spring.boot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.yelong.ssm.SsmModelProperties;

/**
 * @author 彭飞
 * @date 2019年10月18日下午5:06:37
 * @version 1.2
 */
@ConfigurationProperties(SsmBootModelProperties.SSM_PREFIX)
@Deprecated
public class SsmBootModelProperties extends SsmModelProperties{

	public static final String SSM_PREFIX = "yelong.ssm";
	
}
