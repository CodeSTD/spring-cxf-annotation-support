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

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.BusWiringBeanFactoryPostProcessor;
import org.apache.cxf.bus.spring.Jsr250BeanPostProcessor;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import com.codestd.spring.cxf.annotation.Endpoint;

/**
 * @author jaune(WangChengwei)
 * @since 1.0.0
 */
public class EndpointBeanProcessor implements 
	BeanFactoryPostProcessor, DisposableBean, BeanPostProcessor, ApplicationContextAware{

	private final String COMMA_SPLIT_PATTERN = ",";
	
	private ApplicationContext applicationContext;
	
	private String annotationPackage;
	
	private String[] annotationPackages;
	
	public void setAnnotationPackage(String annotationPackage) {
		this.annotationPackage = annotationPackage;
		if(!StringUtils.isEmpty(this.annotationPackage))
			this.annotationPackages = this.annotationPackage.split(this.COMMA_SPLIT_PATTERN);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		
		if(!this.isMatchPackage(bean))return bean;
		
		Endpoint endpoint = bean.getClass().getAnnotation(Endpoint.class);
		
		if(endpoint != null){
			String id = endpoint.id();
			
		}
		
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

	@Override
	public void destroy() throws Exception {
		
	}
	
	private boolean isMatchPackage(Object bean){
		if (annotationPackages == null || annotationPackages.length == 0) {
            return true;
        }
        String beanClassName = bean.getClass().getName();
        for (String pkg : annotationPackages) {
            if (beanClassName.startsWith(pkg)) {
                return true;
            }
        }
        return false;
	}
	
	public static final void setBlocking(ApplicationContext ctx, EndpointImpl impl) {
        AutowireCapableBeanFactory fact = ctx.getAutowireCapableBeanFactory();
        if (fact instanceof DefaultListableBeanFactory) {
            DefaultListableBeanFactory dlbf = (DefaultListableBeanFactory)fact;
            for (BeanPostProcessor bpp : dlbf.getBeanPostProcessors()) {
                if (CommonAnnotationBeanPostProcessor.class.isInstance(bpp)) {
                    impl.getServerFactory().setBlockPostConstruct(true);
                    impl.getServerFactory().setBlockInjection(false);
                    return;
                }
                if (bpp instanceof Jsr250BeanPostProcessor) {
                    impl.getServerFactory().setBlockInjection(true);
                }
            }
        }
    }
	
	@NoJSR250Annotations
    public static class SpringEndpointImpl extends EndpointImpl
        implements ApplicationContextAware {
    
        boolean checkBlockConstruct;
        
        public SpringEndpointImpl(Object o) {
            super(o instanceof Bus ? (Bus)o : null,
                o instanceof Bus ? null : o);
        }

        public SpringEndpointImpl(Bus bus, Object implementor) {
            super(bus, implementor);
        }
        
        public void setCheckBlockConstruct(Boolean b) {
            checkBlockConstruct = b;
        }
        
        public void setApplicationContext(ApplicationContext ctx) throws BeansException {
            if (checkBlockConstruct) {
                setBlocking(ctx, this);
            }
            if (getBus() == null) {
                setBus(BusWiringBeanFactoryPostProcessor.addDefaultBus(ctx));
            }
        }
    }

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		if (annotationPackage == null || annotationPackage.length() == 0) {
            return;
        }
		
		if (beanFactory instanceof BeanDefinitionRegistry) {
			BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry)beanFactory;
			ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(beanDefinitionRegistry,true);
			
			AnnotationTypeFilter filter = new AnnotationTypeFilter(Endpoint.class);
			scanner.addIncludeFilter(filter);
			
			scanner.scan(annotationPackages);
		}
	}

}
