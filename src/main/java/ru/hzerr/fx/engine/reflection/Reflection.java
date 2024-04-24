package ru.hzerr.fx.engine.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class Reflection {

    public static Object invokeMethodFrom(Object from, Method method, Object... args) throws InvocationTargetException, IllegalAccessException {
        method.setAccessible(true);
        return method.invoke(from, method, args);
    }

    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return clazz.getDeclaredMethod(methodName, parameterTypes);
    }
}
