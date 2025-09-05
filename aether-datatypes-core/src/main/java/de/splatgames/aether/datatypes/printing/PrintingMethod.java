package de.splatgames.aether.datatypes.printing;

import java.io.PrintStream;

/**
 * The {@link PrintingMethod} enum is used to specify the method of printing an object to the console or a file.
 * <p>
 * It's used to specify the method of printing an object to the console or a file.
 * To specify a printing method, use this enum or one of the other print methods.
 * </p>
 * <p>
 * Common usage of this enum is included in the {@link de.splatgames.aether.datatypes.numbers.FixedPrecisionDecimal FixedPrecisionDecimal} class.
 * In this class the {@link PrintingMethod} enum is used to specify the method of printing the object to the console or a file/logger.
 * </p>
 *
 * @see de.splatgames.aether.datatypes.numbers.FixedPrecisionDecimal FixedPrecisionDecimal
 * @see de.splatgames.aether.datatypes.printing.Printable Printable
 * @since 1.0.0
 */
public enum PrintingMethod {
    /**
     * Represents the standard output stream.
     * <p>
     * This is used to print the object to the standard output stream.
     * A standard output stream is typically the {@link System#out} stream.
     * </p>
     */
    SYSTEM_OUT(System.out),
    /**
     * Represents the standard error output stream.
     * <p>
     * This is used to print the object to the standard error output stream.
     * A standard error output stream is typically the {@link System#err} stream.
     * </p>
     */
    SYSTEM_ERR(System.err),
    /**
     * Represents the {@link java.util.logging.Logger Logger} object.
     * <p>
     * This is used to print the object to a {@link java.util.logging.Logger Logger} object.
     * A {@link java.util.logging.Logger Logger} object is typically a logger object from the {@link java.util.logging} package.
     * </p>
     */
    JAVA_UTIL_LOGGER;

    /**
     * The {@link PrintStream} object to print the object to.
     *
     * <p>
     * If this is {@code null}, getting this value will throw a {@link NullPointerException}.
     * Make sure to check if this is {@code null} before getting the value because it's not <code>null</code> safe.
     * </p>
     *
     * @see PrintStream
     */
    private final PrintStream out;

    /**
     * Creates a new {@link PrintingMethod} without a {@link PrintStream} object.
     */
    PrintingMethod() {
        this.out = null;
    }

    /**
     * Creates a new {@link PrintingMethod} with a {@link PrintStream} object.
     *
     * @param out The {@link PrintStream} object to print the object to.
     */
    PrintingMethod(final PrintStream out) {
        this.out = out;
    }

    /**
     * Gets the {@link PrintStream} object to print the object to.
     *
     * <p>
     * If this is {@code null}, getting this value will throw a {@link NullPointerException}.
     * Make sure to check if this is {@code null} before getting the value because it's not <code>null</code> safe.
     * </p>
     *
     * @return The {@link PrintStream} object to print the object to.
     */
    public PrintStream getPrintStream() {
        return out;
    }
}
