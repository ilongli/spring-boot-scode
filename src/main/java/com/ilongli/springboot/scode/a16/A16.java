package com.ilongli.springboot.scode.a16;

import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

/**
 * 切点匹配
 * @author ilongli
 * @date 2023/5/9 10:46
 */
public class A16 {

    public static void main(String[] args) throws NoSuchMethodException {
        System.out.println("==========T1==========");
        AspectJExpressionPointcut pt1 = new AspectJExpressionPointcut();
        pt1.setExpression("execution(* bar())");
        System.out.println(pt1.matches(T1.class.getMethod("foo"), T1.class));
        System.out.println(pt1.matches(T1.class.getMethod("bar"), T1.class));

        System.out.println("==========T2==========");
        AspectJExpressionPointcut pt2 = new AspectJExpressionPointcut();
        pt2.setExpression("@annotation(org.springframework.transaction.annotation.Transactional)");
        System.out.println(pt2.matches(T1.class.getMethod("foo"), T1.class));
        System.out.println(pt2.matches(T1.class.getMethod("bar"), T1.class));

        System.out.println("==========T3==========");
        StaticMethodMatcherPointcut pt3 = new StaticMethodMatcherPointcut() {
            @Override
            public boolean matches(Method method, Class<?> targetClass) {
                // 检查方法上是否加了Transactional注解
                MergedAnnotations annotations = MergedAnnotations.from(method);
                if (annotations.isPresent(Transactional.class)) {
                    return true;
                }
                // 查看类上是否加了Transactional注解
                annotations = MergedAnnotations.from(targetClass, MergedAnnotations.SearchStrategy.TYPE_HIERARCHY);
                if (annotations.isPresent(Transactional.class)) {
                    return true;
                }

                return false;
            }
        };
        System.out.println(pt3.matches(T1.class.getMethod("foo"), T1.class));
        System.out.println(pt3.matches(T1.class.getMethod("bar"), T1.class));
        System.out.println(pt3.matches(T2.class.getMethod("foo"), T2.class));
        System.out.println(pt3.matches(T3.class.getMethod("foo"), T3.class));

    }


    static class T1 {
        @Transactional
        public void foo() {}
        public void bar() {}
    }

    @Transactional
    static class T2 {
        public void foo() {}
    }

    @Transactional
    interface I3 {
        void foo();
    }
    static class T3 implements I3 {
        @Override
        public void foo() {}
    }
}
