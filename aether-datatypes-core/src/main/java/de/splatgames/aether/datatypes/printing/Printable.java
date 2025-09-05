package de.splatgames.aether.datatypes.printing;

import java.io.PrintStream;

/**
 * The {@link Printable} interface is used
 * to specify that an object can be printed to the console or a file via a {@link PrintStream} object.
 * <p>
 * The {@link Printable} interface is used to specify that a object can be printed to the console or a file via a {@link PrintStream} object.
 * It's used to print the object to the console or a file via a {@link PrintStream} object.
 * To print a object, use this interface or one of the other print methods.
 * </p>
 *
 * @param <PRINTED> The type of the object that should be printed.
 */
public interface Printable<PRINTED> {

    /**
     * Prints the value of this object to the console or a file via a {@link PrintStream} object.
     * <p>
     * The printing method is used to specify how the object should be printed.
     * It's used to print the object to the console or a file via a {@link PrintStream} object.
     * To print a object, use this method or one of the other print methods.
     * </p>
     */
    void print();

    /**
     * Prints the value of this object to the console or a file via a {@link PrintStream} object.
     * <p>
     * The printing method is used to specify how the object should be printed.
     * It's used to print the object to the console or a file via a {@link PrintStream} object.
     * To print a object, use this method or one of the other print methods.
     * </p>
     *
     * @param out The {@link PrintStream} object to print the object to.
     */
    void print(final PrintStream out);

    /**
     * Prints the value of this object to the console or a file via a {@link PrintStream} object and returns the object.
     * <p>
     * The printing method is used to specify how the object should be printed.
     * It's used to print the object to the console or a file via a {@link PrintStream} object.
     * To print a object, use this method or one of the other print methods.
     * </p>
     *
     * @return The object that was printed. (The {@link PRINTED} object itself)
     */
    PRINTED printAndReturn();

    /**
     * Prints the value of this object to the console or a file via a {@link PrintStream} object and returns the object.
     * <p>
     * The printing method is used to specify how the object should be printed.
     * It's used to print the object to the console or a file via a {@link PrintStream} object.
     * To print a object, use this method or one of the other print methods.
     * </p>
     *
     * @param out The {@link PrintStream} object to print the object to.
     * @return The object that was printed. (The object itself)
     */
    PRINTED printAndReturn(final PrintStream out);
}
