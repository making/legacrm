package com.example.poc.legacrm;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;

@Configuration
public class ClientConfig {
	@Bean
	public Jaxb2Marshaller jaxb2Marshaller() {
		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
		jaxb2Marshaller.setContextPath("com.example.poc.legacrm.customer");
		return jaxb2Marshaller;
	}

	@Bean
	public WebServiceTemplate webServiceTemplate(Jaxb2Marshaller jaxb2Marshaller)
			throws Exception {
		WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
		webServiceTemplate.setMarshaller(jaxb2Marshaller);
		webServiceTemplate.setUnmarshaller(jaxb2Marshaller);
		webServiceTemplate.setDefaultUri("http://localhost:28080/ws");
		//webServiceTemplate.setDefaultUri("https://legacrm.cfapps.io/ws");

		XwsSecurityInterceptor securityInterceptor = new XwsSecurityInterceptor();
		securityInterceptor
				.setPolicyConfiguration(new ClassPathResource("username-token.xml"));
		securityInterceptor.setCallbackHandler(callbacks -> {
		});
		securityInterceptor.afterPropertiesSet();
		webServiceTemplate
				.setInterceptors(new ClientInterceptor[] { securityInterceptor });
		return webServiceTemplate;
	}
}
