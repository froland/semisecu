package com.hermes.owasphotel.web.security;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;

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