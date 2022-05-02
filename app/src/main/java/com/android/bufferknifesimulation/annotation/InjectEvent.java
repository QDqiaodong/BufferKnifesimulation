package com.android.bufferknifesimulation.annotation;

import android.app.Activity;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @description desc
 * @Author qiaodong
 * @Date 2022/2/24-9:50 上午
 */
public class InjectEvent {

    public static void inject(Activity activity) throws Exception{
        injectEvent(activity);
        injectView(activity);
    }

    private static void injectView(Activity activity){
        Field[] declaredFields = activity.getClass().getDeclaredFields();
        for(Field field:declaredFields){
            BindView annotation = field.getAnnotation(BindView.class);
            if(annotation == null){
                return;
            }
            int value = annotation.value();
            if(value > 0){
                try {
                    Method findViewById = activity.getClass().getMethod("findViewById", int.class);
                    findViewById.setAccessible(true);
                    Object view = findViewById.invoke(activity, value);
                    field.set(activity,view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void injectEvent(Activity activity) throws Exception {
        Class<? extends Activity> activityClass = activity.getClass();
        Method[] declaredMethods = activityClass.getDeclaredMethods();
        for (Method method : declaredMethods) {
            Annotation[] annotations = method.getDeclaredAnnotations();

            for (Annotation annotation : annotations) {
                Class<? extends Annotation> annotationType = annotation.annotationType();
                if (annotationType.isAnnotationPresent(EventType.class)) {
                    EventType eventType = annotationType.getAnnotation(EventType.class);
                    String listenerSetter = eventType.listenerSetter();
                    Class listenerType = eventType.listenerType();

                    Method valueMethod = annotationType.getDeclaredMethod("value");
                    int[] viewIds = (int[]) valueMethod.invoke(annotation);
                    method.setAccessible(true);


                    ListenerInvocationHandler<Activity> handler = new ListenerInvocationHandler(activity, method);
                    Object listenerProxy = Proxy.newProxyInstance(listenerType.getClassLoader(),
                            new Class[]{listenerType}, handler);

                    for (int id:viewIds){
                        View view =  activity.findViewById(id);
                        Method setter = view.getClass().getMethod(listenerSetter, listenerType);
                        setter.invoke(view,listenerProxy);
                    }
                }

            }

        }
    }

    /**
     * 还可能在自定义view注入，所以是泛型： T = Activity/View
     *
     * @param <T>
     */
    static class ListenerInvocationHandler<T> implements InvocationHandler {

        private Method method;
        private T target;

        public ListenerInvocationHandler(T target, Method method) {
            this.target = target;
            this.method = method;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return this.method.invoke(target, args);
        }
    }
}
