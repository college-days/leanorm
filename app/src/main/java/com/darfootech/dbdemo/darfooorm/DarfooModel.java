package com.darfootech.dbdemo.darfooorm;

import java.lang.reflect.Field;

/**
 * Created by zjh on 2015/5/24.
 */
public abstract class DarfooModel {
    public final void getFields(String flag) {
        System.out.println("flag -> " + flag);
        for (Field field : getClass().getFields()) {
            System.out.println("field name -> " + field.getName());
            Class<?> fieldType = field.getType();
            System.out.println("field type name -> " + fieldType.getSimpleName());
        }
    }
}
