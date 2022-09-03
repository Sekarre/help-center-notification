package com.sekarre.helpcenternotification.testutil;

public class JUnitMessageGenerator<FROM, TO> {

    private final Class<FROM> fromClass;
    private final Class<TO> toClass;

    public JUnitMessageGenerator(Class<FROM> fromClass, Class<TO> toClass) {
        this.fromClass = fromClass;
        this.toClass = toClass;
    }

    public String getMessage(String fromFieldName, Object fromValue, String toFieldName, Object toValue) {
        return "Field: " + fromFieldName + " of class: " + fromClass.getSimpleName() + " with value: " + fromValue + " doesn't match field: "
                + toFieldName + " of class " + toClass.getSimpleName() + " with value: " + toValue;
    }
}
