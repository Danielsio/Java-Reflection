import reflection.api.Reflector;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Rectangle req = new Rectangle(1, 1);

        Class currentClass = req.getClass();

        System.out.println(currentClass);
        System.out.println(currentClass.getName());
        System.out.println(currentClass.getSimpleName());
        System.out.println(currentClass.getSuperclass());
        System.out.println(currentClass.getSuperclass().getName());
        System.out.println(currentClass.getSuperclass().getSimpleName());
        System.out.println(currentClass.getSuperclass().getSuperclass());
        System.out.println(currentClass.getSuperclass().getSuperclass().getName());
        System.out.println(currentClass.getSuperclass().getSuperclass().getSimpleName());



    }
}