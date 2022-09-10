package reflection.api;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("rawtypes")
public class Reflector implements Investigator {
    private Class<?> objClass;
    private Object myObj;

    public Reflector(){} // default constructor

    @Override
    public void load(Object anInstanceOfSomething) {
        myObj = anInstanceOfSomething;
        objClass = myObj.getClass();
    }

    @Override
    public int getTotalNumberOfMethods() {
        return objClass.getDeclaredMethods().length;
    }

    @Override
    public int getTotalNumberOfConstructors() {
        return objClass.getDeclaredConstructors().length;
    }

    @Override
    public int getTotalNumberOfFields() {
        return objClass.getDeclaredFields().length;
    }

    @Override
    public Set<String> getAllImplementedInterfaces() {
        Set<String> setOfStrings = new HashSet<>();
        Class<?>[] implementedInterfaces = objClass.getInterfaces();
        for (Class<?> implementedInterface : implementedInterfaces) {
            setOfStrings.add(implementedInterface.getSimpleName());
        }
        return setOfStrings;
    }

    @Override
    public int getCountOfConstantFields() {
        int numOfFinalFields = 0;
        Field[] Fields = objClass.getDeclaredFields();
        for (Field field : Fields) {
            if (Modifier.isFinal(field.getModifiers())) {
                numOfFinalFields++;
            }
        }
        return numOfFinalFields;
    }

    @Override
    public int getCountOfStaticMethods() {
        int numOfStaticMethods = 0;
        Method[] methods = objClass.getDeclaredMethods();
        for (Method method : methods) {
            if (Modifier.isStatic(method.getModifiers())) {
                numOfStaticMethods++;
            }
        }
        return numOfStaticMethods;
    }

    @Override
    public boolean isExtending() {
        boolean res = false;
        Class<?> superClass = objClass.getSuperclass();
        if (superClass != null && !superClass.getSimpleName().equals("Object")) {res = true;}
        return res;
    }

    @Override
    public String getParentClassSimpleName() {
        Class<?> superClass = objClass.getSuperclass();
        if (superClass != null && !superClass.getSimpleName().equals("Object")) {
            return superClass.getSimpleName();
        }
        return null;
    }

    @Override
    public boolean isParentClassAbstract() {
        Class superClass = objClass.getSuperclass();
       int classMod = superClass.getModifiers();
        return Modifier.isAbstract(classMod);
    }

    @Override
    public Set<String> getNamesOfAllFieldsIncludingInheritanceChain() {
        Set<String> setOfStrings = new HashSet<>();
        Class<?> currentClass = objClass;
        while (!currentClass.getSimpleName().equals("Object")) {
           Field[] fields = currentClass.getDeclaredFields();
           for (Field field : fields) {
               setOfStrings.add(field.getName());
           }
            currentClass = currentClass.getSuperclass();
        }
        return setOfStrings;
    }

    @Override
    public int invokeMethodThatReturnsInt(String methodName, Object... args) {
        Method[] methods = objClass.getDeclaredMethods();
        Method methodToInvoke = null;
        for(Method method: methods) {
            if (method.getName().equals(methodName) && method.getGenericReturnType().getTypeName().equals("int")) {
                methodToInvoke = method;
            }
        }
        int res;

        try {
            res = (int) (methodToInvoke != null ? methodToInvoke.invoke(myObj, args) : null);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    @Override
    public Object createInstance(int numberOfArgs, Object... args) {

        Constructor[] ctors = objClass.getConstructors();
        Constructor ctor = null;
        int i;
        for (i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == numberOfArgs) {
                break;
            }
        }
            try {
                return ctor != null ? ctor.newInstance(args) : null;
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

    @Override
    public Object elevateMethodAndInvoke(String name, Class<?>[] parametersTypes, Object... args) {
        Method method;
        try {
            method = objClass.getDeclaredMethod(name, parametersTypes);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        method.setAccessible(true);
        Object res = null;
        try {
            res = method.invoke(myObj, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return res;
    }
    @Override
    public String getInheritanceChain(String delimiter) {
        List<String> classes = new ArrayList<>();
        Class currentClass = objClass;
       while(!currentClass.getSimpleName().equals("Object")) {
           classes.add(currentClass.getSimpleName());
           currentClass = currentClass.getSuperclass();
       }
       StringBuilder result = new StringBuilder();
        result.append("Object");
        for (int i = classes.size() - 1; i >= 0; i--) {
            result.append(delimiter).append(classes.get(i));
        }
        return result.toString();
    }
}