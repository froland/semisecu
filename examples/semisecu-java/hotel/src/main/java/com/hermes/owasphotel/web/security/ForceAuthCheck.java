package com.hermes.owasphotel.web.security;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;

/**
 * Forces re-authentication for security interceptor beans.
 * <p>This is used to authenticate the users on each request as their
 * accounts may have been locked between two requests.</p>
 */
public class ForceAuthCheck implements BeanPostProcessor {
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		if (bean instanceof AbstractSecurityInterceptor) {
			((AbstractSecurityInterceptor) bean).setAlwaysReauthenticate(true);
		}
		return bean;
	}
}