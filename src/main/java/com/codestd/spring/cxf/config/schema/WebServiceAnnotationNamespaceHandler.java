package com.codestd.spring.cxf.config.schema;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import com.codestd.spring.cxf.config.EndpointBeanProcessor;

public class WebServiceAnnotationNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		// TODO Auto-generated method stub
		this.registerBeanDefinitionParser("annotation-endpoint", new AnnotationBeanDefinitionParser(EndpointBeanProcessor.class));
	}

}
