/*
 * Copyright 2018 Group 5.
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

package edu.psu.ist411;

import edu.psu.ist411.presentation.UserViewPageMessageBodyWriter;
import edu.psu.ist411.presentation.UsersController;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.config.SwaggerConfigLocator;
import io.swagger.jaxrs.config.SwaggerContextService;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ApplicationPath;

/**
 * Configuration for Jersey.
 *
 * @author Tyler Suehr
 * @author David Wong
 * @author Steven Weber
 * @author Win Ton
 */
@Configuration
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        configureJersey();
        configureSwagger();
    }

    public void configureJersey() {
        register(UsersController.class);
        register(UserViewPageMessageBodyWriter.class);

        property(ServletProperties.FILTER_FORWARD_ON_404, true);
    }

    public void configureSwagger() {
        final BeanConfig swaggerConfig = new BeanConfig();
        swaggerConfig.setBasePath("/api");
        swaggerConfig.setPrettyPrint(true);
        SwaggerConfigLocator.getInstance().putConfig(SwaggerContextService.CONFIG_ID_DEFAULT, swaggerConfig);

        packages(getClass().getPackage().getName(), ApiListingResource.class.getPackage().getName());
    }

//    public void configureSwagger() {
//        register(ApiListingResource.class);
//        register(SwaggerSerializers.class);
//
//        final BeanConfig beanConfig = new BeanConfig();
//        beanConfig.setVersion("1.0.0");
//        beanConfig.setSchemes(new String[] {"http"});
//        beanConfig.setHost("localhost:8080");
//        beanConfig.setBasePath("/api");
//        beanConfig.setResourcePackage("io.swagger.resources");
//        beanConfig.setPrettyPrint(true);
//        beanConfig.setScan(true);
//    }
}