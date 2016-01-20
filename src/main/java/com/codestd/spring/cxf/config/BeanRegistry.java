/*
 * Copyright 2015-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codestd.spring.cxf.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Registry Bean
 * @author jaune(Wang Chengwei)
 * @since 1.0.0
 */
public class BeanRegistry implements ApplicationContextAware{
	
	private ApplicationContext applicationContext;
	
	private ConfigurableApplicationContext configurableApplicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		if(applicationContext instanceof ConfigurableApplicationContext){
			this.configurableApplicationContext = (ConfigurableApplicationContext)this.applicationContext;
		}
	}
	
	public BeanRegistry(){
		
	}
	
	public BeanRegistry(ApplicationContext applicationContext){
		this.setApplicationContext(applicationContext);
	}
	
	public BeanDefinition register(Class<?> clazz){
		if(configurableApplicationContext == null)return null;
		BeanDefinitionRegistry beanDefinitonRegistry = 
				(BeanDefinitionRegistry)configurableApplicationContext.getBeanFactory();
		
		BeanDefinitionBuilder beanDefinitionBuilder = this.createBuilder(clazz);
		BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();
		beanDefinitonRegistry.registerBeanDefinition(clazz.getName(),beanDefinition);
		
		return beanDefinition;
	}
	
	private BeanDefinitionBuilder createBuilder(Class<?> clazz){
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
		return beanDefinitionBuilder;
	}

}

	
