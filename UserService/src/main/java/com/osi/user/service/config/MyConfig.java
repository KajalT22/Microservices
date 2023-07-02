package com.osi.user.service.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.ClientResourcesBuilderCustomizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestTemplate;

import com.osi.user.service.config.intercepter.RestTemplateInterceptor;

@Configuration
public class MyConfig {
	
	@Autowired
	private ClientRegistrationRepository clientRegistrationRepository; //to pass as an args to manager()
	@Autowired
	private OAuth2AuthorizedClientRepository auth2AuthorizedClientRepository;

	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		
		RestTemplate restTemplate = new RestTemplate();
		//seting interceptor
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>(); //as this setInterceptors method accepts args of List<ClientHttpRequestInterceptor>>
		interceptors.add(new RestTemplateInterceptor(
				manager(clientRegistrationRepository,auth2AuthorizedClientRepository)));
		restTemplate.setInterceptors(interceptors);
		
		
		return restTemplate;
	}

	// declaring bean of inteface OAuth2AuthorizedClientManager to be use in FeignClientInterceptor
	@Bean
	public OAuth2AuthorizedClientManager manager(ClientRegistrationRepository clientRegistrationRepository,
			OAuth2AuthorizedClientRepository auth2AuthorizedClientRepository) 
	{
		
		OAuth2AuthorizedClientProvider provider = OAuth2AuthorizedClientProviderBuilder.builder().clientCredentials().build();
		
		DefaultOAuth2AuthorizedClientManager defaultOAuth2AuthorizedClientManager = 
				new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository,auth2AuthorizedClientRepository);
		
		defaultOAuth2AuthorizedClientManager.setAuthorizedClientProvider(provider);
		return defaultOAuth2AuthorizedClientManager;

	}

}
