package com.osi.user.service.config.intercepter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Configuration
@Component
public class FeignClientInterceptor implements RequestInterceptor {

	//we are creating interceptor as we are calling this feign client service url in get single user details to get hotel details 
	//so before calling this hotel service url we need to pause that and pass bearer in header 
	
	@Autowired //created bean in MyConfig class 
	private OAuth2AuthorizedClientManager manager; //interface
	
	@Override
	public void apply(RequestTemplate template) {
		
		String token = manager
		.authorize(OAuth2AuthorizeRequest.withClientRegistrationId("my-internal-client")
				.principal("internal").build())
		.getAccessToken().getTokenValue(); //withClientRegistrationId this is id that we created in yml file
		
		template.header("Authorization", "Bearer "+token);
		
	}
	
	

}
