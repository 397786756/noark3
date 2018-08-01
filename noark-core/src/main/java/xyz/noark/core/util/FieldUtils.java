/*
 * Copyright © 2018 www.noark.xyz All Rights Reserved.
 * 
 * 感谢您选择Noark框架，希望我们的努力能为您提供一个简单、易用、稳定的服务器端框架 ！
 * 除非符合Noark许可协议，否则不得使用该文件，您可以下载许可协议文件：
 * 
 * 		http://www.noark.xyz/LICENSE
 *
 * 1.未经许可，任何公司及个人不得以任何方式或理由对本框架进行修改、使用和传播;
 * 2.禁止在本项目或任何子项目的基础上发展任何派生版本、修改版本或第三方版本;
 * 3.无论你对源代码做出任何修改和改进，版权都归Noark研发团队所有，我们保留所有权利;
 * 4.凡侵犯Noark版权等知识产权的，必依法追究其法律责任，特此郑重法律声明！
 */
package xyz.noark.core.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 属性工具类.
 *
 * @since 3.0
 * @author 小流氓(176543888@qq.com)
 */
public class FieldUtils {

	private static final int PREFIX_IS_METHOD_INDEX = 2;
	private static final int PREFIX_GET_METHOD_INDEX = 3;
	private static final int PREFIX_SET_METHOD_INDEX = 3;

	/**
	 * 强制给一个属性{@link Field}写入值.
	 * 
	 * @param target 目标对象
	 * @param field 要写入的属性
	 * @param value 要写入的值
	 */
	public static void writeField(final Object target, final Field field, final Object value) {
		if (!field.isAccessible()) {
			field.setAccessible(true);
		}
		try {
			field.set(target, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(target.getClass() + " 的 " + field.getName() + " 属性无法注入.", e);
		}
	}

	/**
	 * 获取指定类的所有属性，包含父类的属性.
	 * 
	 * @param klass 指定类
	 * @return 指定类的属性集合.
	 */
	public static List<Field> getAllField(final Class<?> klass) {
		List<Field> result = new ArrayList<>();
		for (Class<?> target = klass; target != Object.class; target = target.getSuperclass()) {
			for (Field field : target.getDeclaredFields()) {
				result.add(field);
			}
		}
		return result;
	}

	/**
	 * 生成Get方法名.
	 * <p>
	 * 
	 * @param field 属性
	 * @return Get方法名
	 */
	public static String genGetMethodName(Field field) {
		int len = field.getName().length();
		if (field.getType() == boolean.class) {
			StringBuilder sb = new StringBuilder(len + 2);
			sb.append("is").append(field.getName());
			if (Character.isLowerCase(sb.charAt(PREFIX_IS_METHOD_INDEX))) {
				sb.setCharAt(PREFIX_IS_METHOD_INDEX, Character.toUpperCase(sb.charAt(PREFIX_IS_METHOD_INDEX)));
			}
			return sb.toString();
		} else {
			StringBuilder sb = new StringBuilder(len + 3);
			sb.append("get").append(field.getName());
			if (Character.isLowerCase(sb.charAt(PREFIX_GET_METHOD_INDEX))) {
				sb.setCharAt(PREFIX_GET_METHOD_INDEX, Character.toUpperCase(sb.charAt(PREFIX_GET_METHOD_INDEX)));
			}
			return sb.toString();
		}
	}

	/**
	 * 生成Set方法名.
	 * 
	 * @param field 属性
	 * @return Set方法名
	 */
	public static String genSetMethodName(Field field) {
		int len = field.getName().length();
		StringBuilder sb = new StringBuilder(len + 3);
		sb.append("set").append(field.getName());
		if (Character.isLowerCase(sb.charAt(PREFIX_SET_METHOD_INDEX))) {
			sb.setCharAt(PREFIX_SET_METHOD_INDEX, Character.toUpperCase(sb.charAt(PREFIX_SET_METHOD_INDEX)));
		}
		return sb.toString();
	}

	/**
	 * 利用反射，扫描出此类所有属性(包含父类中子类没有重写的属性)
	 * 
	 * @param klass 指定类.
	 * @param annotations 标识属性的注解
	 * @return 返回此类所有属性.
	 */
	public static Field[] scanAllField(final Class<?> klass, List<Class<?>> annotations) {
		// 为了返回是有序的添加过程，这里使用LinkedHashMap
		Map<String, Field> fieldMap = new LinkedHashMap<String, Field>();
		scanField(klass, fieldMap, annotations);
		return fieldMap.values().toArray(new Field[fieldMap.size()]);
	}

	/**
	 * 递归的方式拉取属性，这样父类的属性就在上面了...
	 * 
	 * @param klass 类
	 * @param fieldMap 所有属性集合
	 * @param annotations 标识属性的注解
	 */
	private static void scanField(final Class<?> klass, Map<String, Field> fieldMap, List<Class<?>> annotations) {
		Class<?> superClass = klass.getSuperclass();
		if (!Object.class.equals(superClass)) {
			scanField(superClass, fieldMap, annotations);
		}
		// 属性判定
		for (Field f : klass.getDeclaredFields()) {
			// Static和Final的不要
			if (Modifier.isStatic(f.getModifiers()) || Modifier.isFinal(f.getModifiers())) {
				continue;
			}
			// 子类已重写或内部类中的不要
			if (fieldMap.containsKey(f.getName()) || f.getName().startsWith("this$")) {
				continue;
			}
			// 没有指定的注解不要
			for (Annotation a : f.getAnnotations()) {
				if (annotations.contains(a.annotationType())) {
					fieldMap.put(f.getName(), f);
					break;
				}
			}
		}
	}
}