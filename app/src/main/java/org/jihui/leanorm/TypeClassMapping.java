package org.jihui.leanorm;

import java.util.HashMap;

/**
 * Created by zjh on 2015/5/24.
 */
public class TypeClassMapping {
    public static HashMap<Class, String> typeClassMapping = new HashMap<Class, String>();

    // just define a column with Integer type. SQLite sets the column length to 1,2,4,8 depending on your input.
    static {
        typeClassMapping.put(int.class, "INTEGER");
        typeClassMapping.put(long.class, "INTEGER");
        typeClassMapping.put(float.class, "FLOAT");
        typeClassMapping.put(double.class, "DOUBLE");
        typeClassMapping.put(boolean.class, "BOOLEAN");
        typeClassMapping.put(Integer.class, "INTEGER");
        typeClassMapping.put(Long.class, "INTEGER");
        typeClassMapping.put(Float.class, "FLOAT");
        typeClassMapping.put(Double.class, "DOUBLE");
        typeClassMapping.put(Boolean.class, "BOOLEAN");
        typeClassMapping.put(String.class, "VARCHAR");
    }
}
