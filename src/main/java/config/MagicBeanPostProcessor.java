package config;

import config.annotation.Magic;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class MagicBeanPostProcessor implements BeanPostProcessor {
    private final Map<String, Class<?>> stringClassHashMap = new HashMap<>();

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (stringClassHashMap.containsKey(beanName)) {
            var beanClass = stringClassHashMap.get(beanName);
            var enhancer = new Enhancer();
            enhancer.setSuperclass(beanClass);
            MethodInterceptor methodInterceptor = (obj, method, ars, proxy) -> CompletableFuture.runAsync(() -> run(obj, method, ars, proxy));
            enhancer.setCallback(methodInterceptor);
            return enhancer.create();
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        var magicRequired = Arrays.stream(beanClass.getDeclaredMethods())
                .anyMatch(m -> m.isAnnotationPresent(Magic.class));
        if (magicRequired) {
            stringClassHashMap.put(beanName, beanClass);
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @SneakyThrows
    private void run(Object obj, java.lang.reflect.Method method, Object[] args,
                     MethodProxy proxy) {
        proxy.invokeSuper(obj, args);
    }
}
