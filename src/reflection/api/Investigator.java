package reflection.api;

import java.util.Set;

public interface Investigator {

    /**
     * loads in instance of a given class, which you will need to investigate through means of reflection
     * and supply information on...
     *
     * @param anInstanceOfSomething an instance of an unknown type...
     */
    void load(Object anInstanceOfSomething);

    /**
     * returns the total number of the current class methods (not including the super class methods)
     * the total number of methods includes all sorts of modifiers: private, public, static, protected etc.
     * it does not contains constructors of any sort
     *
     * @return total number of methods
     */
    int getTotalNumberOfMethods();

    /**
     * returns the total number of constructors that this class has. (not including the super class constructors)
     * the total number of constructors includes all sorts of modifiers: private, public, protected, default etc.
     *
     * @return total number of constructors
     */
    int getTotalNumberOfConstructors();

    /**
     * returns the total number of fields that this class has.
     * the total number of fields DOES NOT include fields inherited from super class, if such exists
     * and includes all fields of any type (final, static etc.)
     *
     * @return the total number of fields
     */
    int getTotalNumberOfFields();

    /**
     * returns the names of all the interfaces that this class implements
     * (and NOT the ones that are being implemented by it's ancestor in the inheritance chain)
     * Note that by name I mean simple, short Name that is only the interface name,
     * and not its fully qualified name including the package it is located within...
     *
     * @return set of interfaces names
     */
    Set<String> getAllImplementedInterfaces();

    /**
     * returns total number of constant fields (=final), of any type (private, public, static etc.)
     * (not including the super class fields)
     * @return total number of constant fields
     */
    int getCountOfConstantFields();

    /**
     * returns total number of static methods, of any type (private, public etc.),
     * that are declared on the instance itself (and not part of its inheritance chain)
     *
     * @return total number of static methods
     */
    int getCountOfStaticMethods();

    /**
     * checks if the given class extends a super class, that is not Object (which all extend by default, implicitly)
     *
     * @return true if the class extends, false otherwise
     */
    boolean isExtending();

    /**
     * get the name of the direct parent class
     * the name of the class is the short, simple name and not the fully qualified name
     *
     * @return the name of the parent class. null if this class doesn't extend other class
     */
    String getParentClassSimpleName();

    /**
     * checks if the given class parent is of type abstract
     *
     * @return true if the parent class is abstract, false otherwise (also in case the given class does not extends any other class)
     */
    boolean isParentClassAbstract();

    /**
     * get all the names of all fields, including the ones coming from the inheritance chain
     * all fields of any type should exists: private, public, protected, static etc.
     *
     * @return set of fields names
     */
    Set<String> getNamesOfAllFieldsIncludingInheritanceChain();

    /**
     * invokes a method that returns an int value, on the given instance.
     * the method to invoke will be given by its name only.
     * You can assume that there is exactly one such method and that
     * the method is declared on the instance itself and not as part of its inheritance chain
     *
     * @param methodName the name of the method to invoke
     * @param args the arguments to pass to the method, if such exists.
     *             Note: You should not use the arguments in order to extract and identify the method.
     *                   You can do that only (and simply) by its name.
     *                   You just need to pass the arguments AS IS to the method invocation...
     *
     * @return the result returned from the method invocation
     */
    int invokeMethodThatReturnsInt(String methodName, Object... args);

    /**
     * creates a new instance of the given class, using a specific constructor,
     * to be determined by the number of given arguments.
     * No worries: there will be no ambiguity, i.e. two constructors that gets in 2 arguments each one of them...
     * This method should act as a standalone and without any side effects, that is, the newly created
     * instance SHOULD NOT replace the original given instance.
     * @param numberOfArgs number of arguments that a specific constructor has. can be 0.
     * @param args arguments to pass to the constructor
     *
     * @return the newly created instance
     */
    Object createInstance(int numberOfArgs, Object... args);

    /**
     * changes access control of a method and invokes it (== invokes a private method...)
     * you can assume that the method exists by it's name, and matched by the relevant parameters types.
     *
     * @param name name of the method to change its access modifier
     * @param parametersTypes the types of parameters the method accepts
     * @param args arguments to pass to the method invocation AS IS (once elevated)
     *
     * @return the result from the method invocation
     */
    Object elevateMethodAndInvoke(String name, Class<?>[] parametersTypes, Object... args);

    /**
     * explores the inheritance chain of the object. returns a string representing the list of
     * parents this class has, starting from Object, and ending at the class name.
     * all classes names in the list should appear in their simple short name
     * @param delimiter a string to put between each two classes in the chain (e.g. "->")
     * @return a string denotes the inheritance chain.
     * It can look like this (given a decimeter of "->"): Object-><class name>->...-><class name>
     */
    String getInheritanceChain(String delimiter);}
