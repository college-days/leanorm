package com.darfootech.dbdemo.darfooorm;

import java.util.HashMap;

/**
 * Created by zjh on 2015/5/24.
 */
public class TypeClassMapping {
    public static HashMap<Class, String> typeClassMapping = new HashMap<Class, String>();

    static {
        typeClassMapping.put(Integer.class, "INTEGER");
        typeClassMapping.put(Long.class, "INTEGER");
        typeClassMapping.put(String.class, "VARCHAR");
    }
}
