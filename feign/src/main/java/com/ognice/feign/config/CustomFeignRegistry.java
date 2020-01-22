package com.ognice.feign.config;

import com.ognice.feign.annotation.CustomFeignClient;
import com.ognice.feign.annotation.GetRequest;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Set;

public class CustomFeignRegistry implements ImportBeanDefinitionRegistrar, EnvironmentAware, BeanClassLoaderAware, ResourceLoaderAware, BeanFactoryAware {
    Environment environment;
    ClassLoader classLoader;
    ResourceLoader resourceLoader;
    BeanFactory beanFactory;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        //扫描注解
        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false, environment) {
                    @Override
                    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                        if (beanDefinition.getMetadata().isInterface()) {
                            Class<?> target = null;
                            try {
                                target = ClassUtils.forName(beanDefinition.getMetadata().getClassName(), classLoader);
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                            return !target.isAnnotation();
                        }
                        return super.isCandidateComponent(beanDefinition);
                    }
                };
        scanner.setResourceLoader(resourceLoader);
        AnnotationTypeFilter annotationTypeFilter = new AnnotationTypeFilter(CustomFeignClient.class);
        scanner.addIncludeFilter(annotationTypeFilter);
        Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents("com.ognice");
        for (BeanDefinition bean : candidateComponents) {
            if (bean instanceof AnnotatedBeanDefinition) {
                String beanClassName = bean.getBeanClassName();
                // 船舰bean动态代理
                Object proxy = null;
                try {
                    proxy = proxy((AnnotatedBeanDefinition) bean);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                ((DefaultListableBeanFactory) beanFactory).registerSingleton(beanClassName, proxy);
            }

        }
    }

    private Object proxy(AnnotatedBeanDefinition annotatedBeanDefinition) throws ClassNotFoundException {
        String className = annotatedBeanDefinition.getMetadata().getClassName();
        Class<?> target = Class.forName(className);
        InvocationHandler invocationHandler = (proxy, method, args) -> {
            CustomFeignClient annotation = target.getAnnotation(CustomFeignClient.class);
            String url = annotation.url();
            StringBuilder stringBuilder = new StringBuilder(url);
            GetRequest methodAnnotation = method.getAnnotation(GetRequest.class);
            if (methodAnnotation != null) {
                stringBuilder.append(methodAnnotation.path()).append("?");
                RestTemplate restTemplate = new RestTemplate();
                Parameter[] parameters = method.getParameters();
                Arrays.stream(parameters).forEach(p -> {
                    stringBuilder.append(p.getName()).append("={").append(p.getName()).append("}&");
                });
                return restTemplate.getForObject(stringBuilder.toString(), String.class, args);
            }
            throw new IllegalArgumentException("bad request setting");
        };
        Object o = Proxy.newProxyInstance(classLoader, new Class[]{target}, invocationHandler);
        return o;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
    //扫描CustonFeignClient注解
    // 生成动态代理
    // 注入bean到容器

}
