package com.example.poc.legacrm.ws;

import java.util.Collections;
import java.util.List;

import javax.security.auth.callback.CallbackHandler;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;
import org.springframework.ws.soap.security.xwss.callback.SpringPlainTextPasswordValidationCallbackHandler;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
	private final UserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;

	public WebServiceConfig(UserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder) {
		this.userDetailsService = userDetailsService;
		this.passwordEncoder = passwordEncoder;
	}

	@Bean
	public ServletRegistrationBean messageDispatcherServlet(
			ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		return new ServletRegistrationBean(servlet, "/ws/*");
	}

	@Bean(name = "customer")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema customerSchema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("CustomerPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://customer.legacrm.poc.example.com");
		wsdl11Definition.setSchema(customerSchema);
		return wsdl11Definition;
	}

	@Bean
	public XsdSchema customerSchema() {
		return new SimpleXsdSchema(new ClassPathResource("customer.xsd"));
	}

	@Bean
	public XwsSecurityInterceptor securityInterceptor() throws Exception {
		XwsSecurityInterceptor securityInterceptor = new XwsSecurityInterceptor();
		securityInterceptor.setCallbackHandler(callbackHandler());
		securityInterceptor
				.setPolicyConfiguration(new ClassPathResource("security-policy.xml"));
		return securityInterceptor;
	}

	@Bean
	public CallbackHandler callbackHandler() throws Exception {
		SpringPlainTextPasswordValidationCallbackHandler handler = new SpringPlainTextPasswordValidationCallbackHandler();

		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(this.userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(this.passwordEncoder);
		daoAuthenticationProvider.afterPropertiesSet();

		ProviderManager providerManager = new ProviderManager(
				Collections.singletonList(daoAuthenticationProvider));
		providerManager.afterPropertiesSet();

		handler.setAuthenticationManager(providerManager);
		return handler;
	}

	@Override
	public void addInterceptors(List<EndpointInterceptor> interceptors) {
		try {
			interceptors.add(securityInterceptor());
		}
		catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

}