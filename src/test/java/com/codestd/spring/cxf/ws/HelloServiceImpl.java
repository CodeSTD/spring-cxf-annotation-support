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
package com.codestd.spring.cxf.ws;

import javax.jws.WebService;

import com.codestd.spring.cxf.annotation.Endpoint;

/**
 * @author jaune(Wang Chengwei)
 * @since 1.0.0
 */
@Endpoint(address="HelloService", id = "HelloServiceEndpoint")
@WebService(endpointInterface="com.codestd.spring.cxf.ws.HelloService")
public class HelloServiceImpl implements HelloService{

	@Override
	public String syHi(String name) {
		return "Hello "+name;
	}

}
