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
package com.codestd.spring.cxf.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于暴露WebService服务，通过在类上加入{@code @Endpoint}注解实现服务暴露的目的。
 * <p>使用此注解的前提是Bean已经加入Spring容器。也就是说Bean在Spring配置文件中配置，或已标注@Service、@Component、@Inject等注解。
 * @author jaune(WangChengwei)
 * @since 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Endpoint {
	
	/**
	 * 此Endpoint在Spring容器中的ID
	 * @return
	 */
	String id();
	
	/**
	 * 服务发布的地址，应神略服务器地址及端口号和项目路径
	 * @return
	 */
	String address();

}
