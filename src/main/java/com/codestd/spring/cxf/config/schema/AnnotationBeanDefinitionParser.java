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
package com.codestd.spring.cxf.config.schema;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * 
 * @author clwang
 *
 */
public class AnnotationBeanDefinitionParser implements BeanDefinitionParser {
	
	private final Class<?> beanClass;
    
	public AnnotationBeanDefinitionParser(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);
        
        String id = element.getAttribute("id");
        if(id == null || id.length() == 0 ){
        	String name = element.getAttribute("name");
        	if(!StringUtils.isEmpty(name)) id = name;
        	else id = beanClass.getName();
        }
        
        if (parserContext.getRegistry().containsBeanDefinition(id))  {
    		throw new IllegalStateException("Duplicate spring bean id " + id);
        }
        parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
        
        String annotationPackage = element.getAttribute("package");
        if(!StringUtils.isEmpty(annotationPackage))
        	beanDefinition.getPropertyValues().add("annotationPackage", annotationPackage);
        
		return beanDefinition;
	}

}
