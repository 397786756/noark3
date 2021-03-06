/*
 * Copyright © 2018 www.noark.xyz All Rights Reserved.
 *
 * 感谢您选择Noark框架，希望我们的努力能为您提供一个简单、易用、稳定的服务器端框架 ！
 * 除非符合Noark许可协议，否则不得使用该文件，您可以下载许可协议文件：
 *
 *        http://www.noark.xyz/LICENSE
 *
 * 1.未经许可，任何公司及个人不得以任何方式或理由对本框架进行修改、使用和传播;
 * 2.禁止在本项目或任何子项目的基础上发展任何派生版本、修改版本或第三方版本;
 * 3.无论你对源代码做出任何修改和改进，版权都归Noark研发团队所有，我们保留所有权利;
 * 4.凡侵犯Noark版权等知识产权的，必依法追究其法律责任，特此郑重法律声明！
 */
package xyz.noark.xml;

import org.xml.sax.helpers.DefaultHandler;
import xyz.noark.core.annotation.tpl.TplAttr;
import xyz.noark.core.converter.ConvertManager;
import xyz.noark.core.converter.Converter;
import xyz.noark.core.exception.ConvertException;
import xyz.noark.core.exception.TplAttrRequiredException;
import xyz.noark.core.exception.UnrealizedException;
import xyz.noark.core.util.ClassUtils;
import xyz.noark.core.util.FieldUtils;
import xyz.noark.core.util.StringUtils;

import java.lang.reflect.Field;

/**
 * 抽象的XML处理器.
 * <p>
 * XML格式分两种:<br>
 * 一、为单一对象，根节点为Object.<br>
 * 二、为数组对象，根节点为Array.
 *
 * @author 小流氓[176543888@qq.com]
 * @since 3.1
 */
abstract class AbstractXmlHandler<T> extends DefaultHandler {
    protected final Class<T> klass;
    protected final String tplFileName;

    protected AbstractXmlHandler(Class<T> klass, String tplFileName) {
        this.klass = klass;
        this.tplFileName = tplFileName;
    }

    /**
     * 构建对象.
     *
     * @param data  数据
     * @param fixEl 是否修正EL表达式
     */
    protected T buildObject(ObjectData data, boolean fixEl) {
        if (fixEl) {
            data.fillExpression();
        }

        T result = ClassUtils.newInstance(klass);

        for (Field field : klass.getDeclaredFields()) {
            TplAttr attr = field.getAnnotation(TplAttr.class);
            if (attr == null || StringUtils.isEmpty(attr.name())) {
                continue;
            }

            String value = data.getValue(attr.name());
            if (StringUtils.isEmpty(value)) {
                if (attr.required()) {
                    throw new TplAttrRequiredException(klass, field, attr);
                }
                continue;
            }

            Converter<?> converter = this.getConverter(field);
            try {
                FieldUtils.writeField(result, field, converter.convert(field, value));
            } catch (Exception e) {
                throw new ConvertException(tplFileName + " >> " + field.getName() + " >> " + value + "-->" + converter.buildErrorMsg(), e);
            }
        }
        return result;
    }

    private Converter<?> getConverter(Field field) {
        Converter<?> result = ConvertManager.getInstance().getConverter(field.getType());
        if (result == null) {
            throw new UnrealizedException("XML配置解析时，发现未实现的类型. field=(" + field.getType().getName() + ")" + field.getName());
        }
        return result;
    }
}