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

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import com.codestd.spring.cxf.config.EndpointBeanProcessor;
/**
 * 处理命名空间
 * @author jaune(Wang Chengwei)
 * @since 1.0.0
 */
public class WebServiceAnnotationNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		// TODO Auto-generated method stub
		this.registerBeanDefinitionParser("annotation-endpoint", new AnnotationBeanDefinitionParser(EndpointBeanProcessor.class));
	}

}
