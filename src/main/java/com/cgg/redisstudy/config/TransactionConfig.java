package com.cgg.redisstudy.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.annotation.Resource;

/**
 * @author cgg
 */
@Configuration
@Slf4j
public class TransactionConfig {

    private static final String AOP_METHOD_PACKAGE = "execution(* com.cgg.redisstudy..*.*(..))";

    @Resource
    private PlatformTransactionManager platformTransactionManager;

    @Bean
    public TransactionInterceptor txAdvice() {
        DefaultTransactionAttribute defaultTransactionAttribute = new DefaultTransactionAttribute();
        defaultTransactionAttribute.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        /**
         * 关闭spring内部只读事务优化
         */
        defaultTransactionAttribute.setReadOnly(false);
        NameMatchTransactionAttributeSource nameMatchTransactionAttributeSource = new NameMatchTransactionAttributeSource();
        nameMatchTransactionAttributeSource.addTransactionalMethod("add*", defaultTransactionAttribute);
        nameMatchTransactionAttributeSource.addTransactionalMethod("set*", defaultTransactionAttribute);
        nameMatchTransactionAttributeSource.addTransactionalMethod("save*", defaultTransactionAttribute);
        nameMatchTransactionAttributeSource.addTransactionalMethod("update*", defaultTransactionAttribute);
        return new TransactionInterceptor(platformTransactionManager, nameMatchTransactionAttributeSource);
    }

    @Bean
    public Advisor advisor() {
        AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
        aspectJExpressionPointcut.setExpression(AOP_METHOD_PACKAGE);
        return new DefaultPointcutAdvisor(aspectJExpressionPointcut, txAdvice());
    }

}
