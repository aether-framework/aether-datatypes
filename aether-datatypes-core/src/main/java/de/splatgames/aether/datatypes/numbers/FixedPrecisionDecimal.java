package de.splatgames.aether.datatypes.numbers;

import de.splatgames.aether.datatypes.printing.Printable;
import de.splatgames.aether.datatypes.printing.PrintingMethod;

import java.io.PrintStream;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Immutable, arbitrary-precision signed large decimal numbers with fixed precision.
 * A <code>FixedPrecisionDecimal</code> object is represented by a {@link BigDecimal} object.
 * It's a wrapper class for the {@link BigDecimal} class with fixed precision.
 * <p>
 * The {@link FixedPrecisionDecimal} class provides operations for arithmetic, comparison, and printing.
 * It also provides a factory method for creating <code>FixedPrecisionDecimal</code> objects.
 * The precision of a <code>FixedPrecisionDecimal</code> object is the number of digits to the right of the decimal point with nearly unlimited precision.
 * The precision is a non-negative integer.
 * </p>
 * <p>
 * A {@link IllegalArgumentException} is thrown if the precision is a negative integer.
 * Also a {@link IllegalArgumentException} is thrown if the scales of two <code>FixedPrecisionDecimal</code> objects do not match.
 * </p>
 * <p>
 * The {@link FixedPrecisionDecimal} class gains you full control over the precision of the decimal numbers.
 * It's useful for financial calculations, scientific calculations, and other applications that require fixed precision.
 * </p>
 * <p>
 * To round up or other rounding operations you can use on many method the {@link RoundingMode} enum to specify the rounding mode.
 * Otherwise the default rounding mode is {@link RoundingMode#HALF_UP}.
 * Rounding modes are used to determine how to round the result of an arithmetic operation.
 * The {@link RoundingMode} enum is used until the constructor of the {@link FixedPrecisionDecimal} class.
 * </p>
 * <p>
 * A solution for <code>Pi</code> is also implemented in the {@link FixedPrecisionDecimal} class.
 * It could be used to calculate the value of <code>Pi</code> with a given scale over the {@link FixedPrecisionDecimal#ofPi(int)} method.
 * Make sure you don't use a scale that is too high over a 32-bit signed {@link Integer}.
 * Also make sure to don't calculate it to high because it could end up in a {@link OutOfMemoryError} or very long calculation time.
 * </p>
 *
 * @author Erik Pförtner
 * @see BigDecimal
 * @see RoundingMode
 * @see MathContext
 * @see Printable
 * @see PrintingMethod
 * @see DecimalFormat
 * @since 1.0.0
 */
public class FixedPrecisionDecimal extends Number implements Comparable<FixedPrecisionDecimal>, Printable<FixedPrecisionDecimal>, Serializable {

    /**
     * The serial version UID.
     *
     * <p>
     * The serial version UID is used to verify that the serialized and deserialized objects have the same version.
     * It's used to ensure that the deserialized object is compatible with the serialized object.
     * </p>
     */
    @Serial
    private static final long serialVersionUID = 9186399745297761955L;

    /**
     * The logger for the {@link FixedPrecisionDecimal} class.
     *
     * <p>
     * The logger is used to log messages to the console or a file.
     * It's used to log information, warnings, and errors.
     * </p>
     */
    private static final Logger LOGGER = Logger.getLogger(FixedPrecisionDecimal.class.getSimpleName());

    /**
     * The K multiplier for the Chudnovsky algorithm.
     * <p>
     * The K multiplier is used in the Chudnovsky algorithm to calculate the value of Pi.
     * It's a constant value that is used in the algorithm.
     * </p>
     *
     * @see <a href="https://en.wikipedia.org/wiki/Chudnovsky_algorithm">Chudnovsky algorithm</a>
     */
    private static final BigDecimal K_MULT = new BigDecimal("545140134");

    /**
     * The L multiplier for the Chudnovsky algorithm.
     * <p>
     * The L multiplier is used in the Chudnovsky algorithm to calculate the value of Pi.
     * It's a constant value that is used in the algorithm.
     * </p>
     *
     * @see <a href="https://en.wikipedia.org/wiki/Chudnovsky_algorithm">Chudnovsky algorithm</a>
     */
    private static final BigDecimal L_MULT = new BigDecimal("13591409");

    /**
     * The X multiplier for the Chudnovsky algorithm.
     * <p>
     * The X multiplier is used in the Chudnovsky algorithm to calculate the value of Pi.
     * It's a constant value that is used in the algorithm.
     * </p>
     *
     * @see <a href="https://en.wikipedia.org/wiki/Chudnovsky_algorithm">Chudnovsky algorithm</a>
     */
    private static final BigDecimal X_MULT = new BigDecimal("-262537412640768000");

    /**
     * The square root of 10005 multiplied by 426880.
     * <p>
     * This constant is used in the Chudnovsky algorithm to calculate the value of Pi.
     * It's a constant value that is used in the algorithm.
     * </p>
     *
     * @see <a href="https://en.wikipedia.org/wiki/Chudnovsky_algorithm">Chudnovsky algorithm</a>
     */
    private static final BigDecimal SQRTC = new BigDecimal("426880");

    /**
     * The constant 10005 as a {@link BigDecimal} object.
     * <p>
     * This constant is used in the Chudnovsky algorithm to calculate the value of Pi.
     * It's a constant value that is used in the algorithm.
     * </p>
     *
     * @see <a href="https://en.wikipedia.org/wiki/Chudnovsky_algorithm">Chudnovsky algorithm</a>
     */
    private static final BigDecimal CONSTANT = new BigDecimal("10005");

    /**
     * The printing method for the {@link FixedPrecisionDecimal} class.
     *
     * <p>
     * The printing method is used to specify how the {@link FixedPrecisionDecimal} object should be printed.
     * It's used to print the {@link FixedPrecisionDecimal} object to the console or a file via a {@link PrintStream} object.
     * To print a {@link FixedPrecisionDecimal} object, use the {@link FixedPrecisionDecimal#print()} method or its variants.
     * </p>
     */
    private static PrintingMethod printingMethod = PrintingMethod.SYSTEM_OUT;

    /**
     * The value of the <code>FixedPrecisionDecimal</code> object.
     *
     * <p>
     * The value is a {@link BigDecimal} object that represents the value of the <code>FixedPrecisionDecimal</code> object.
     * It's the value of the <code>FixedPrecisionDecimal</code> object with the specified precision.
     * </p>
     */
    private final BigDecimal value;

    /**
     * The precision of the <code>FixedPrecisionDecimal</code> object.
     *
     * <p>
     * The precision is a non-negative integer that represents the number of digits to the right of the decimal point.
     * It's used to specify the precision of the <code>FixedPrecisionDecimal</code> object.
     * </p>
     */
    private final int precision;

    /**
     * Constructs a new <code>FixedPrecisionDecimal</code> object with the given value.
     *
     * <p>
     * This constructor is private to prevent the creation of <code>FixedPrecisionDecimal</code> objects
     * without using a factory method like {@link FixedPrecisionDecimal#valueOf(BigDecimal, int)} or {@link FixedPrecisionDecimal#valueOf(String, int)}.
     * </p>
     *
     * @param value The value of the <code>FixedPrecisionDecimal</code> object.
     * @throws IllegalArgumentException If the precision is a negative integer.
     * @throws NullPointerException     If the value is <code>null</code>.
     */
    FixedPrecisionDecimal(final BigDecimal value, final int precision, final RoundingMode roundingMode) {
        Objects.requireNonNull(value, "Value must not be null");
        Objects.requireNonNull(roundingMode, "Rounding mode must not be null");

        if (precision < 0) {
            throw new IllegalArgumentException("Precision must be a non-negative integer: " + precision);
        }

        this.value = value.setScale(precision, roundingMode);
        this.precision = precision;
    }

    /**
     * Returns a <code>FixedPrecisionDecimal</code> object holding the value of the specified {@link BigDecimal} object.
     * <p>
     * The precision of the returned <code>FixedPrecisionDecimal</code> object is set to the specified precision.
     * A precision is a non-negative integer that represents the number of digits to the right of the decimal point.
     * </p>
     *
     * @param value     The value of the <code>FixedPrecisionDecimal</code> object as a {@link BigDecimal} object.
     * @param precision The precision of the <code>FixedPrecisionDecimal</code> object.
     * @return A <code>FixedPrecisionDecimal</code> object holding the value of the specified {@link BigDecimal} object.
     * @throws IllegalArgumentException If the precision is a negative integer.
     * @throws NullPointerException If the value is <code>null</code>.
     */
    public static FixedPrecisionDecimal valueOf(final BigDecimal value, final int precision) {
        return new FixedPrecisionDecimal(value, precision, RoundingMode.HALF_UP);
    }

    /**
     * Returns a <code>FixedPrecisionDecimal</code> object holding the value of the specified {@link BigDecimal} object.
     * <p>
     * The precision of the returned <code>FixedPrecisionDecimal</code> object is set to the specified precision.
     * A precision is a non-negative integer that represents the number of digits to the right of the decimal point.
     * </p>
     *
     * @param value        The value of the <code>FixedPrecisionDecimal</code> object as a {@link BigDecimal} object.
     * @param precision    The precision of the <code>FixedPrecisionDecimal</code> object.
     * @param roundingMode The rounding mode to use.
     * @return A <code>FixedPrecisionDecimal</code> object holding the value of the specified {@link BigDecimal} object.
     * @throws IllegalArgumentException If the precision is a negative integer.
     * @throws NullPointerException If the value is <code>null</code>.
     */
    public static FixedPrecisionDecimal valueOf(final BigDecimal value, final int precision, final RoundingMode roundingMode) {
        return new FixedPrecisionDecimal(value, precision, roundingMode);
    }

    /**
     * Returns a <code>FixedPrecisionDecimal</code> object holding the value of the specified {@link String} object.
     * <p>
     * The precision of the returned <code>FixedPrecisionDecimal</code> object is set to the specified precision.
     * A precision is a non-negative integer that represents the number of digits to the right of the decimal point.
     * </p>
     *
     * @param value     The value of the <code>FixedPrecisionDecimal</code> object as a {@link String} object.
     * @param precision The precision of the <code>FixedPrecisionDecimal</code> object.
     * @return A <code>FixedPrecisionDecimal</code> object holding the value of the specified {@link String} object.
     * @throws NumberFormatException If the string does not contain a parsable number.
     * @throws NullPointerException If the value is <code>null</code>.
     * @throws IllegalArgumentException If the precision is a negative integer.
     */
    public static FixedPrecisionDecimal valueOf(final String value, final int precision) {
        return new FixedPrecisionDecimal(new BigDecimal(value), precision, RoundingMode.HALF_UP);
    }

    /**
     * Returns a <code>FixedPrecisionDecimal</code> object holding the value of the specified {@link String} object.
     * <p>
     * The precision of the returned <code>FixedPrecisionDecimal</code> object is set to the specified precision.
     * A precision is a non-negative integer that represents the number of digits to the right of the decimal point.
     * </p>
     *
     * @param value        The value of the <code>FixedPrecisionDecimal</code> object as a {@link String} object.
     * @param precision    The precision of the <code>FixedPrecisionDecimal</code> object.
     * @param roundingMode The rounding mode to use.
     * @return A <code>FixedPrecisionDecimal</code> object holding the value of the specified {@link String} object.
     * @throws NumberFormatException If the string does not contain a parsable number.
     * @throws NullPointerException If the value is <code>null</code>.
     * @throws IllegalArgumentException If the precision is a negative integer.
     */
    public static FixedPrecisionDecimal valueOf(final String value, final int precision, final RoundingMode roundingMode) {
        return new FixedPrecisionDecimal(new BigDecimal(value), precision, roundingMode);
    }

    /**
     * Returns a <code>FixedPrecisionDecimal</code> object holding the value of the specified <code>int</code> value.
     * <p>
     * The precision of the returned <code>FixedPrecisionDecimal</code> object is set to the specified precision.
     * A precision is a non-negative integer that represents the number of digits to the right of the decimal point.
     * </p>
     *
     * @param value     The value of the <code>FixedPrecisionDecimal</code> object as an <code>int</code> value.
     * @param precision The precision of the <code>FixedPrecisionDecimal</code> object.
     * @return A <code>FixedPrecisionDecimal</code> object holding the value of the specified <code>int</code> value.
     */
    public static FixedPrecisionDecimal valueOf(final int value, final int precision) {
        return new FixedPrecisionDecimal(BigDecimal.valueOf(value), precision, RoundingMode.HALF_UP);
    }

    /**
     * Returns a <code>FixedPrecisionDecimal</code> object holding the value of the specified <code>int</code> value.
     * <p>
     * The precision of the returned <code>FixedPrecisionDecimal</code> object is set to the specified precision.
     * A precision is a non-negative integer that represents the number of digits to the right of the decimal point.
     * </p>
     *
     * @param value        The value of the <code>FixedPrecisionDecimal</code> object as an <code>int</code> value.
     * @param precision    The precision of the <code>FixedPrecisionDecimal</code> object.
     * @param roundingMode The rounding mode to use.
     * @return A <code>FixedPrecisionDecimal</code> object holding the value of the specified <code>int</code> value.
     * @throws NullPointerException If the rounding mode is <code>null</code>.
     */
    public static FixedPrecisionDecimal valueOf(final int value, final int precision, final RoundingMode roundingMode) {
        return new FixedPrecisionDecimal(BigDecimal.valueOf(value), precision, roundingMode);
    }

    /**
     * Returns a <code>FixedPrecisionDecimal</code> object holding the value of the specified <code>long</code> value.
     * <p>
     * The precision of the returned <code>FixedPrecisionDecimal</code> object is set to the specified precision.
     * A precision is a non-negative integer that represents the number of digits to the right of the decimal point.
     * </p>
     *
     * @param value     The value of the <code>FixedPrecisionDecimal</code> object as a <code>long</code> value.
     * @param precision The precision of the <code>FixedPrecisionDecimal</code> object.
     * @return A <code>FixedPrecisionDecimal</code> object holding the value of the specified <code>long</code> value.
     */
    public static FixedPrecisionDecimal valueOf(final long value, final int precision) {
        return new FixedPrecisionDecimal(BigDecimal.valueOf(value), precision, RoundingMode.HALF_UP);
    }

    /**
     * Returns a <code>FixedPrecisionDecimal</code> object holding the value of the specified <code>long</code> value.
     * <p>
     * The precision of the returned <code>FixedPrecisionDecimal</code> object is set to the specified precision.
     * A precision is a non-negative integer that represents the number of digits to the right of the decimal point.
     * </p>
     *
     * @param value        The value of the <code>FixedPrecisionDecimal</code> object as a <code>long</code> value.
     * @param precision    The precision of the <code>FixedPrecisionDecimal</code> object.
     * @param roundingMode The rounding mode to use.
     * @return A <code>FixedPrecisionDecimal</code> object holding the value of the specified <code>long</code> value.
     * @throws NullPointerException If the rounding mode is <code>null</code>.
     */
    public static FixedPrecisionDecimal valueOf(final long value, final int precision, final RoundingMode roundingMode) {
        return new FixedPrecisionDecimal(BigDecimal.valueOf(value), precision, roundingMode);
    }

    /**
     * Returns a <code>FixedPrecisionDecimal</code> object holding the value of the specified <code>double</code> value.
     * <p>
     * The precision of the returned <code>FixedPrecisionDecimal</code> object is set to the specified precision.
     * A precision is a non-negative integer that represents the number of digits to the right of the decimal point.
     * </p>
     *
     * @param value     The value of the <code>FixedPrecisionDecimal</code> object as a <code>double</code> value.
     * @param precision The precision of the <code>FixedPrecisionDecimal</code> object.
     * @return A <code>FixedPrecisionDecimal</code> object holding the value of the specified <code>double</code> value.
     */
    public static FixedPrecisionDecimal valueOf(final double value, final int precision) {
        return new FixedPrecisionDecimal(BigDecimal.valueOf(value), precision, RoundingMode.HALF_UP);
    }

    /**
     * Returns a <code>FixedPrecisionDecimal</code> object holding the value of the specified <code>double</code> value.
     * <p>
     * The precision of the returned <code>FixedPrecisionDecimal</code> object is set to the specified precision.
     * A precision is a non-negative integer that represents the number of digits to the right of the decimal point.
     * </p>
     *
     * @param value        The value of the <code>FixedPrecisionDecimal</code> object as a <code>double</code> value.
     * @param precision    The precision of the <code>FixedPrecisionDecimal</code> object.
     * @param roundingMode The rounding mode to use.
     * @return A <code>FixedPrecisionDecimal</code> object holding the value of the specified <code>double</code> value.
     * @throws NullPointerException If the rounding mode is <code>null</code>.
     */
    public static FixedPrecisionDecimal valueOf(final double value, final int precision, final RoundingMode roundingMode) {
        return new FixedPrecisionDecimal(BigDecimal.valueOf(value), precision, roundingMode);
    }

    /**
     * Returns a <code>FixedPrecisionDecimal</code> object holding the value of the specified <code>float</code> value.
     * <p>
     * The precision of the returned <code>FixedPrecisionDecimal</code> object is set to the specified precision.
     * A precision is a non-negative integer that represents the number of digits to the right of the decimal point.
     * </p>
     *
     * @param value     The value of the <code>FixedPrecisionDecimal</code> object as a <code>float</code> value.
     * @param precision The precision of the <code>FixedPrecisionDecimal</code> object.
     * @return A <code>FixedPrecisionDecimal</code> object holding the value of the specified <code>float</code> value.
     */
    public static FixedPrecisionDecimal valueOf(final float value, final int precision) {
        return new FixedPrecisionDecimal(BigDecimal.valueOf(value), precision, RoundingMode.HALF_UP);
    }

    /**
     * Returns a <code>FixedPrecisionDecimal</code> object holding the value of the specified <code>float</code> value.
     * <p>
     * The precision of the returned <code>FixedPrecisionDecimal</code> object is set to the specified precision.
     * A precision is a non-negative integer that represents the number of digits to the right of the decimal point.
     * </p>
     *
     * @param value        The value of the <code>FixedPrecisionDecimal</code> object as a <code>float</code> value.
     * @param precision    The precision of the <code>FixedPrecisionDecimal</code> object.
     * @param roundingMode The rounding mode to use.
     * @return A <code>FixedPrecisionDecimal</code> object holding the value of the specified <code>float</code> value.
     * @throws NullPointerException If the rounding mode is <code>null</code>.
     */
    public static FixedPrecisionDecimal valueOf(final float value, final int precision, final RoundingMode roundingMode) {
        return new FixedPrecisionDecimal(BigDecimal.valueOf(value), precision, roundingMode);
    }

    /**
     * Returns a <code>FixedPrecisionDecimal</code> object holding the value of the specified <code>FixedPrecisionDecimal</code> object.
     * <p>
     * The precision of the returned <code>FixedPrecisionDecimal</code> object is set to the specified precision.
     * A precision is a non-negative integer that represents the number of digits to the right of the decimal point.
     * </p>
     *
     * @param value     The value of the <code>FixedPrecisionDecimal</code> object as a <code>FixedPrecisionDecimal</code> object.
     * @param precision The precision of the <code>FixedPrecisionDecimal</code> object.
     * @return A <code>FixedPrecisionDecimal</code> object holding the value of the specified <code>FixedPrecisionDecimal</code> object.
     * @throws NullPointerException If the value is <code>null</code>.
     */
    public static FixedPrecisionDecimal valueOf(final FixedPrecisionDecimal value, final int precision) {
        return new FixedPrecisionDecimal(value.value, precision, RoundingMode.HALF_UP);
    }

    /**
     * Returns a <code>FixedPrecisionDecimal</code> object holding the value of the specified <code>FixedPrecisionDecimal</code> object.
     * <p>
     * The precision of the returned <code>FixedPrecisionDecimal</code> object is set to the specified precision.
     * A precision is a non-negative integer that represents the number of digits to the right of the decimal point.
     * </p>
     *
     * @param value        The value of the <code>FixedPrecisionDecimal</code> object as a <code>FixedPrecisionDecimal</code> object.
     * @param precision    The precision of the <code>FixedPrecisionDecimal</code> object.
     * @param roundingMode The rounding mode to use.
     * @return A <code>FixedPrecisionDecimal</code> object holding the value of the specified <code>FixedPrecisionDecimal</code> object.
     * @throws NullPointerException If the value or the rounding mode is <code>null</code>.
     */
    public static FixedPrecisionDecimal valueOf(final FixedPrecisionDecimal value, final int precision, final RoundingMode roundingMode) {
        return new FixedPrecisionDecimal(value.value, precision, roundingMode);
    }

    /**
     * Sets the printing method for the {@link FixedPrecisionDecimal} class.
     *
     * <p>
     * The printing method is used to specify how the {@link FixedPrecisionDecimal} object should be printed.
     * It's used to print the {@link FixedPrecisionDecimal} object to the console or a file via a {@link PrintStream} object.
     * To print a {@link FixedPrecisionDecimal} object, use the {@link FixedPrecisionDecimal#print()} method or its variants.
     * </p>
     *
     * @param printingMethod The printing method to use.
     */
    public static void setPrintingMethod(final PrintingMethod printingMethod) {
        FixedPrecisionDecimal.printingMethod = (printingMethod == null) ? PrintingMethod.SYSTEM_OUT : printingMethod;
    }

    /**
     * Returns the absolute value of the specified <code>FixedPrecisionDecimal</code> object.
     * <p>
     * The absolute value of a number is the non-negative value of the number without regard to its sign.
     * </p>
     *
     * @param value The <code>FixedPrecisionDecimal</code> object whose absolute value is to be returned.
     * @return The absolute value of the specified <code>FixedPrecisionDecimal</code> object.
     * @throws NullPointerException If the value is <code>null</code>.
     */
    public static FixedPrecisionDecimal abs(final FixedPrecisionDecimal value) {
        return new FixedPrecisionDecimal(value.value.abs(), value.precision, RoundingMode.HALF_UP);
    }

    /**
     * Returns the maximum of the two specified <code>FixedPrecisionDecimal</code> objects.
     * <p>
     * The maximum of two numbers is the greater of the two numbers.
     * </p>
     *
     * @param a The first <code>FixedPrecisionDecimal</code> object.
     * @param b The second <code>FixedPrecisionDecimal</code> object.
     * @return The maximum of the two specified <code>FixedPrecisionDecimal</code> objects.
     */
    public static FixedPrecisionDecimal max(final FixedPrecisionDecimal a, final FixedPrecisionDecimal b) {
        return a.compareTo(b) >= 0 ? a : b;
    }

    /**
     * Returns the minimum of the two specified <code>FixedPrecisionDecimal</code> objects.
     * <p>
     * The minimum of two numbers is the smaller of the two numbers.
     * </p>
     *
     * @param a The first <code>FixedPrecisionDecimal</code> object.
     * @param b The second <code>FixedPrecisionDecimal</code> object.
     * @return The minimum of the two specified <code>FixedPrecisionDecimal</code> objects.
     */
    public static FixedPrecisionDecimal min(final FixedPrecisionDecimal a, final FixedPrecisionDecimal b) {
        return a.compareTo(b) <= 0 ? a : b;
    }

    /**
     * Returns the average of the specified <code>FixedPrecisionDecimal</code> objects.
     * <p>
     * The average of a list of numbers is the sum of the numbers divided by the number of numbers.
     * </p>
     *
     * @param values The list of <code>FixedPrecisionDecimal</code> objects.
     * @return The average of the specified <code>FixedPrecisionDecimal</code> objects.
     * @throws NullPointerException     If the values list or any of its elements are <code>null</code>.
     * @throws IllegalArgumentException If the values list is empty or if the precisions of the elements do not match.
     */
    public static FixedPrecisionDecimal average(final List<FixedPrecisionDecimal> values) {
        Objects.requireNonNull(values, "values");
        if (values.isEmpty()) {
            throw new IllegalArgumentException("values must not be empty");
        }
        final int precision = values.get(0).precision;
        BigDecimal sum = BigDecimal.ZERO;
        for (FixedPrecisionDecimal v : values) {
            Objects.requireNonNull(v, "values element");
            if (v.precision != precision) {
                throw new IllegalArgumentException("Precisions must match: " + precision + " != " + v.precision);
            }
            sum = sum.add(v.value);
        }
        BigDecimal avg = sum.divide(BigDecimal.valueOf(values.size()), RoundingMode.HALF_UP);
        return new FixedPrecisionDecimal(avg.setScale(precision, RoundingMode.HALF_UP), precision, RoundingMode.HALF_UP);
    }

    /**
     * Returns the average of the specified <code>FixedPrecisionDecimal</code> objects.
     * <p>
     * The average of a list of numbers is the sum of the numbers divided by the number of numbers.
     * </p>
     *
     * @param values       The list of <code>FixedPrecisionDecimal</code> objects.
     * @param roundingMode The rounding mode to use.
     * @return The average of the specified <code>FixedPrecisionDecimal</code> objects.
     * @throws NullPointerException     If the values list or any of its elements are <code>null</code>.
     * @throws IllegalArgumentException If the values list is empty or if the precisions of the elements do not match.
     */
    public static FixedPrecisionDecimal average(final List<FixedPrecisionDecimal> values, final RoundingMode roundingMode) {
        Objects.requireNonNull(values, "values");
        Objects.requireNonNull(roundingMode, "roundingMode");
        if (values.isEmpty()) {
            throw new IllegalArgumentException("values must not be empty");
        }
        final int precision = values.get(0).precision;
        BigDecimal sum = BigDecimal.ZERO;
        for (FixedPrecisionDecimal v : values) {
            Objects.requireNonNull(v, "values element");
            if (v.precision != precision) {
                throw new IllegalArgumentException("Precisions must match: " + precision + " != " + v.precision);
            }
            sum = sum.add(v.value);
        }
        BigDecimal avg = sum.divide(BigDecimal.valueOf(values.size()), roundingMode);
        return new FixedPrecisionDecimal(avg.setScale(precision, roundingMode), precision, roundingMode);
    }

    /**
     * Returns the sum of the specified <code>FixedPrecisionDecimal</code> objects.
     * <p>
     * The sum of a list of numbers is the total of the numbers.
     * </p>
     *
     * @param values The collection of <code>FixedPrecisionDecimal</code> objects.
     * @return The sum of the specified <code>FixedPrecisionDecimal</code> objects.
     * @throws NullPointerException     If the values collection or any of its elements are <code>null</code>.
     * @throws IllegalArgumentException If the values collection is empty or if the precisions of the elements do not match.
     */
    public static FixedPrecisionDecimal sum(final Collection<FixedPrecisionDecimal> values) {
        Objects.requireNonNull(values, "values");
        if (values.isEmpty()) {
            throw new IllegalArgumentException("values must not be empty");
        }
        final int precision = values.iterator().next().precision;
        BigDecimal sum = BigDecimal.ZERO;
        for (FixedPrecisionDecimal v : values) {
            Objects.requireNonNull(v, "values element");
            if (v.precision != precision) {
                throw new IllegalArgumentException("Precisions must match: " + precision + " != " + v.precision);
            }
            sum = sum.add(v.value);
        }
        return new FixedPrecisionDecimal(sum.setScale(precision, RoundingMode.HALF_UP), precision, RoundingMode.HALF_UP);
    }

    /**
     * Returns the sum of the specified <code>FixedPrecisionDecimal</code> objects.
     * <p>
     * The sum of a list of numbers is the total of the numbers.
     * </p>
     *
     * @param values       The collection of <code>FixedPrecisionDecimal</code> objects.
     * @param roundingMode The rounding mode to use.
     * @return The sum of the specified <code>FixedPrecisionDecimal</code> objects.
     * @throws NullPointerException     If the values collection or any of its elements are <code>null</code>.
     * @throws IllegalArgumentException If the values collection is empty or if the precisions of the elements do not match.
     */
    public static FixedPrecisionDecimal sum(final List<FixedPrecisionDecimal> values, final RoundingMode roundingMode) {
        Objects.requireNonNull(values, "values");
        Objects.requireNonNull(roundingMode, "roundingMode");
        if (values.isEmpty()) {
            throw new IllegalArgumentException("values must not be empty");
        }
        final int precision = values.get(0).precision;
        BigDecimal sum = BigDecimal.ZERO;
        for (FixedPrecisionDecimal v : values) {
            Objects.requireNonNull(v, "values element");
            if (v.precision != precision) {
                throw new IllegalArgumentException("Precisions must match: " + precision + " != " + v.precision);
            }
            sum = sum.add(v.value);
        }
        return new FixedPrecisionDecimal(sum.setScale(precision, roundingMode), precision, roundingMode);
    }

    /**
     * Returns a <code>FixedPrecisionDecimal</code> object holding the value of Pi with the specified scale.
     * <p>
     * The scale of the returned <code>FixedPrecisionDecimal</code> object is set to the specified scale.
     * A scale is a non-negative integer that represents the number of digits to the right of the decimal point.
     * </p>
     * <p>
     * The value of Pi is calculated using the Chudnovsky algorithm.
     * The Chudnovsky algorithm is a fast method for calculating the digits of Pi.
     * </p>
     *
     * @param scale The scale of the <code>FixedPrecisionDecimal</code> object.
     * @return A <code>FixedPrecisionDecimal</code> object holding the value of Pi with the specified scale.
     * @throws IllegalArgumentException If the scale is a negative integer.
     * @see <a href="https://en.wikipedia.org/wiki/Chudnovsky_algorithm">Chudnovsky algorithm</a>
     */
    public static FixedPrecisionDecimal ofPi(final int scale) {
        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal k = BigDecimal.ZERO;
        BigDecimal l = L_MULT;
        BigDecimal x = BigDecimal.ONE;
        BigDecimal m = BigDecimal.ONE;

        MathContext mc = new MathContext(scale, RoundingMode.HALF_UP);

        for (int i = 0; i < scale / 14; i++) {
            BigDecimal numerator = m.multiply(l);
            BigDecimal denominator = x.multiply(BigDecimal.valueOf(i + 1).pow(3));
            sum = sum.add(numerator.divide(denominator, mc));

            k = k.add(BigDecimal.ONE);
            l = l.add(K_MULT);
            x = x.multiply(X_MULT);
            m = m.multiply(BigDecimal.valueOf(i + 1));
        }

        BigDecimal pi = SQRTC.multiply(BigDecimal.valueOf(Math.sqrt(CONSTANT.doubleValue())));
        BigDecimal bd = pi.divide(sum, mc);
        return new FixedPrecisionDecimal(bd, scale, RoundingMode.HALF_UP);
    }

    /**
     * Converts the value of this object to a {@link BigDecimal}.
     *
     * <p>
     * This method is used to convert the value of this object to a BigDecimal.
     * It's used to get the value of this object as a {@link BigDecimal}.
     * </p>
     * <p>
     * Because our <code>FixedPrecisionDecimal</code> object has the BigDecimal value as its underlying value,
     * we can simply return the value of this object as a BigDecimal.
     * </p>
     *
     * @return the value of this object as a {@link BigDecimal}.
     * @see BigDecimal
     */
    public BigDecimal toBigDecimal() {
        return this.value;
    }

    /**
     * Returns the precision of this object.
     *
     * <p>
     * The precision of this object is the number of digits to the right of the decimal point.
     * It's a non-negative integer that represents the precision of this object.
     * </p>
     *
     * @return the precision of this object.
     */
    public int getPrecision() {
        return this.precision;
    }

    /**
     * Compares this <code>FixedPrecisionDecimal</code> with the specified <code>FixedPrecisionDecimal</code>.
     *
     * @param o the FixedPrecisionDecimal to be compared
     * @return a negative integer, zero, or a positive integer as this <code>FixedPrecisionDecimal</code> is less than, equal to,
     * or greater than the specified <code>FixedPrecisionDecimal</code>
     * @see Comparable#compareTo(Object)
     * @throws IllegalArgumentException if the scales of the two <code>FixedPrecisionDecimal</code> objects do not match
     * @throws NullPointerException     if the specified object is null
     * @see #checkPrecision(FixedPrecisionDecimal)
     */
    @Override
    public int compareTo(final FixedPrecisionDecimal o) {
        Objects.requireNonNull(o, "The object to compare to must not be null");
        checkPrecision(o);
        return this.value.compareTo(o.value);
    }

    /**
     * Returns a <code>FixedPrecisionDecimal</code> whose value is <code>(this + other)</code>.
     *
     * @param other the FixedPrecisionDecimal to be added to this <code>FixedPrecisionDecimal</code>
     * @return a <code>FixedPrecisionDecimal</code> whose value is <code>(this + other)</code>
     * @throws IllegalArgumentException if the scales of the two <code>FixedPrecisionDecimal</code> objects do not match
     */
    public FixedPrecisionDecimal add(final FixedPrecisionDecimal other) {
        checkPrecision(other);
        return new FixedPrecisionDecimal(this.value.add(other.value), this.precision, RoundingMode.HALF_UP);
    }

    /**
     * Returns a <code>FixedPrecisionDecimal</code> whose value is <code>(this + other)</code>.
     *
     * @param other        the FixedPrecisionDecimal to be added to this <code>FixedPrecisionDecimal</code>
     * @param roundingMode the rounding mode to use
     * @return a <code>FixedPrecisionDecimal</code> whose value is <code>(this + other)</code>
     * @throws IllegalArgumentException if the scales of the two <code>FixedPrecisionDecimal</code> objects do not match
     */
    public FixedPrecisionDecimal add(final FixedPrecisionDecimal other, final RoundingMode roundingMode) {
        checkPrecision(other);
        return new FixedPrecisionDecimal(this.value.add(other.value), this.precision, roundingMode);
    }

    /**
     * Returns a <code>FixedPrecisionDecimal</code> whose value is <code>(this - other)</code>.
     *
     * @param other the FixedPrecisionDecimal to be subtracted from this <code>FixedPrecisionDecimal</code>
     * @return a <code>FixedPrecisionDecimal</code> whose value is <code>(this - other)</code>
     * @throws IllegalArgumentException if the scales of the two <code>FixedPrecisionDecimal</code> objects do not match
     */
    public FixedPrecisionDecimal subtract(final FixedPrecisionDecimal other) {
        checkPrecision(other);
        return new FixedPrecisionDecimal(this.value.subtract(other.value), this.precision, RoundingMode.HALF_UP);
    }

    /**
     * Returns a <code>FixedPrecisionDecimal</code> whose value is <code>(this - other)</code>.
     *
     * @param other        the FixedPrecisionDecimal to be subtracted from this <code>FixedPrecisionDecimal</code>
     * @param roundingMode the rounding mode to use
     * @return a <code>FixedPrecisionDecimal</code> whose value is <code>(this - other)</code>
     * @throws IllegalArgumentException if the scales of the two <code>FixedPrecisionDecimal</code> objects do not match
     */
    public FixedPrecisionDecimal subtract(final FixedPrecisionDecimal other, final RoundingMode roundingMode) {
        checkPrecision(other);
        return new FixedPrecisionDecimal(this.value.subtract(other.value), this.precision, roundingMode);
    }

    /**
     * Returns a <code>FixedPrecisionDecimal</code> whose value is <code>(this * other)</code>.
     *
     * @param other the FixedPrecisionDecimal to be multiplied by this <code>FixedPrecisionDecimal</code>
     * @return a <code>FixedPrecisionDecimal</code> whose value is <code>(this * other)</code>
     * @throws IllegalArgumentException if the scales of the two <code>FixedPrecisionDecimal</code> objects do not match
     */
    public FixedPrecisionDecimal multiply(final FixedPrecisionDecimal other) {
        checkPrecision(other);
        return new FixedPrecisionDecimal(this.value.multiply(other.value).setScale(this.precision, RoundingMode.HALF_UP), this.precision, RoundingMode.HALF_UP);
    }

    /**
     * Returns a <code>FixedPrecisionDecimal</code> whose value is <code>(this * other)</code>.
     *
     * @param other        the FixedPrecisionDecimal to be multiplied by this <code>FixedPrecisionDecimal</code>
     * @param roundingMode the rounding mode to use
     * @return a <code>FixedPrecisionDecimal</code> whose value is <code>(this * other)</code>
     * @throws IllegalArgumentException if the scales of the two <code>FixedPrecisionDecimal</code> objects do not match
     */
    public FixedPrecisionDecimal multiply(final FixedPrecisionDecimal other, final RoundingMode roundingMode) {
        checkPrecision(other);
        return new FixedPrecisionDecimal(this.value.multiply(other.value).setScale(this.precision, roundingMode), this.precision, roundingMode);
    }

    /**
     * Returns a <code>FixedPrecisionDecimal</code> whose value is <code>(this / other)</code>.
     *
     * @param other the FixedPrecisionDecimal by which this <code>FixedPrecisionDecimal</code> is to be divided
     * @return a <code>FixedPrecisionDecimal</code> whose value is <code>(this / other)</code>
     * @throws IllegalArgumentException if the scales of the two <code>FixedPrecisionDecimal</code> objects do not match
     */
    public FixedPrecisionDecimal divide(final FixedPrecisionDecimal other) {
        checkPrecision(other);
        return new FixedPrecisionDecimal(this.value.divide(other.value, this.precision, RoundingMode.HALF_UP), this.precision, RoundingMode.HALF_UP);
    }

    /**
     * Returns a <code>FixedPrecisionDecimal</code> whose value is <code>(this / other)</code>.
     *
     * @param other        the FixedPrecisionDecimal by which this <code>FixedPrecisionDecimal</code> is to be divided
     * @param roundingMode the rounding mode to use
     * @return a <code>FixedPrecisionDecimal</code> whose value is <code>(this / other)</code>
     * @throws IllegalArgumentException if the scales of the two <code>FixedPrecisionDecimal</code> objects do not match
     */
    public FixedPrecisionDecimal divide(final FixedPrecisionDecimal other, final RoundingMode roundingMode) {
        checkPrecision(other);
        return new FixedPrecisionDecimal(this.value.divide(other.value, this.precision, roundingMode), this.precision, roundingMode);
    }

    /**
     * Returns a <code>FixedPrecisionDecimal</code> whose value is <code>(this % other)</code>.
     *
     * @param other the FixedPrecisionDecimal by which this <code>FixedPrecisionDecimal</code> is to be divided
     * @return a <code>FixedPrecisionDecimal</code> whose value is <code>(this % other)</code>
     * @throws IllegalArgumentException if the scales of the two <code>FixedPrecisionDecimal</code> objects do not match
     */
    public FixedPrecisionDecimal remainder(final FixedPrecisionDecimal other) {
        checkPrecision(other);
        return new FixedPrecisionDecimal(this.value.remainder(other.value), this.precision, RoundingMode.HALF_UP);
    }

    /**
     * Returns a <code>FixedPrecisionDecimal</code> whose value is <code>(this % other)</code>.
     *
     * @param other        the FixedPrecisionDecimal by which this <code>FixedPrecisionDecimal</code> is to be divided
     * @param roundingMode the rounding mode to use
     * @return a <code>FixedPrecisionDecimal</code> whose value is <code>(this % other)</code>
     * @throws IllegalArgumentException if the scales of the two <code>FixedPrecisionDecimal</code> objects do not match
     */
    public FixedPrecisionDecimal remainder(final FixedPrecisionDecimal other, final RoundingMode roundingMode) {
        checkPrecision(other);
        return new FixedPrecisionDecimal(this.value.remainder(other.value), this.precision, roundingMode);
    }

    /**
     * Returns a <code>FixedPrecisionDecimal</code> whose value is <code>(this ^ n)</code>.
     *
     * @param n the power to which this <code>FixedPrecisionDecimal</code> is to be raised
     * @return a <code>FixedPrecisionDecimal</code> whose value is <code>(this ^ n)</code>
     * @throws IllegalArgumentException if the exponent is negative
     */
    public FixedPrecisionDecimal pow(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException("negative exponent not supported");
        }
        return new FixedPrecisionDecimal(this.value.pow(n), this.precision, RoundingMode.HALF_UP);
    }

    /**
     * Returns a <code>FixedPrecisionDecimal</code> whose value is <code>(this ^ n)</code>.
     *
     * @param n            the power to which this <code>FixedPrecisionDecimal</code> is to be raised
     * @param roundingMode the rounding mode to use
     * @return a <code>FixedPrecisionDecimal</code> whose value is <code>(this ^ n)</code>
     */
    public FixedPrecisionDecimal pow(final int n, final RoundingMode roundingMode) {
        return new FixedPrecisionDecimal(this.value.pow(n).setScale(this.precision, roundingMode), this.precision, roundingMode);
    }

    /**
     * Returns the square root of this <code>FixedPrecisionDecimal</code>.
     * <p>
     * The square root of a number is a value that, when multiplied by itself, gives the original number.
     * For example, the square root of 9 is 3 because 3 * 3 = 9.
     * The square root of a negative number is not a real number.
     * </p>
     *
     * @return a <code>FixedPrecisionDecimal</code> whose value is the square root of this <code>FixedPrecisionDecimal</code>
     * @throws ArithmeticException if this <code>FixedPrecisionDecimal</code> is negative
     * @see Math#sqrt(double)
     */
    public FixedPrecisionDecimal sqrt() {
        if (this.value.signum() < 0) {
            throw new ArithmeticException("sqrt of negative value");
        }
        BigDecimal sqrtValue = BigDecimal.valueOf(Math.sqrt(this.value.doubleValue()));
        return new FixedPrecisionDecimal(sqrtValue.setScale(this.precision, RoundingMode.HALF_UP),
                this.precision, RoundingMode.HALF_UP);
    }

    /**
     * Returns the square root of this <code>FixedPrecisionDecimal</code>.
     * <p>
     * The square root of a number is a value that, when multiplied by itself, gives the original number.
     * For example, the square root of 9 is 3 because 3 * 3 = 9.
     * The square root of a negative number is not a real number.
     * </p>
     *
     * @param roundingMode the rounding mode to use
     * @return a <code>FixedPrecisionDecimal</code> whose value is the square root of this <code>FixedPrecisionDecimal</code>
     * @throws ArithmeticException if this <code>FixedPrecisionDecimal</code> is negative
     * @throws NullPointerException if the rounding mode is <code>null</code>
     * @see Math#sqrt(double)
     */
    public FixedPrecisionDecimal sqrt(final RoundingMode roundingMode) {
        if (this.value.signum() < 0) {
            throw new ArithmeticException("sqrt of negative value");
        }
        BigDecimal sqrtValue = BigDecimal.valueOf(Math.sqrt(this.value.doubleValue()));
        return new FixedPrecisionDecimal(sqrtValue.setScale(this.precision, roundingMode), this.precision, roundingMode);
    }

    /**
     * Returns the natural logarithm (base e) of this <code>FixedPrecisionDecimal</code>.
     * <p>
     * The natural logarithm of a number is the power to which the base (e) must be raised to obtain the number.
     * For example, the natural logarithm of 2 is 0.6931471805599453 because e^0.6931471805599453 = 2.
     * </p>
     *
     * @return a <code>FixedPrecisionDecimal</code> whose value is the natural logarithm of this <code>FixedPrecisionDecimal</code>
     * @throws ArithmeticException if this <code>FixedPrecisionDecimal</code> is less than or equal to zero
     * @see Math#log(double)
     */
    public FixedPrecisionDecimal log() {
        if (this.value.signum() <= 0) {
            throw new ArithmeticException("log of non-positive value");
        }
        BigDecimal logValue = BigDecimal.valueOf(Math.log(this.value.doubleValue()));
        return new FixedPrecisionDecimal(logValue.setScale(this.precision, RoundingMode.HALF_UP),
                this.precision, RoundingMode.HALF_UP);
    }

    /**
     * Returns the natural logarithm (base e) of this <code>FixedPrecisionDecimal</code>.
     * <p>
     * The natural logarithm of a number is the power to which the base (e) must be raised to obtain the number.
     * For example, the natural logarithm of 2 is 0.6931471805599453 because e^0.6931471805599453 = 2.
     * </p>
     *
     * @param roundingMode the rounding mode to use
     * @return a <code>FixedPrecisionDecimal</code> whose value is the natural logarithm of this <code>FixedPrecisionDecimal</code>
     * @throws ArithmeticException if this <code>FixedPrecisionDecimal</code> is less than or equal to zero
     * @throws NullPointerException if the rounding mode is <code>null</code>
     * @see Math#log(double)
     */
    public FixedPrecisionDecimal log(final RoundingMode roundingMode) {
        if (this.value.signum() <= 0) {
            throw new ArithmeticException("log of non-positive value");
        }
        BigDecimal logValue = BigDecimal.valueOf(Math.log(this.value.doubleValue()));
        return new FixedPrecisionDecimal(logValue.setScale(this.precision, roundingMode), this.precision, roundingMode);
    }

    /**
     * Returns a <code>FixedPrecisionDecimal</code>
     * whose value is the rounded value of this <code>FixedPrecisionDecimal</code>.
     *
     * @param roundingMode the rounding mode to use
     * @return a <code>FixedPrecisionDecimal</code> whose value is the rounded value of this <code>FixedPrecisionDecimal</code>
     */
    public FixedPrecisionDecimal round(final RoundingMode roundingMode) {
        return new FixedPrecisionDecimal(this.value.setScale(this.precision, roundingMode), this.precision, RoundingMode.HALF_UP);
    }

    /**
     * Returns a <code>FixedPrecisionDecimal</code>
     * whose value is the rounded value of this <code>FixedPrecisionDecimal</code>
     * using the {@link RoundingMode#DOWN} rounding mode.
     *
     * @return a <code>FixedPrecisionDecimal</code> whose value is the rounded value of this
     * <code>FixedPrecisionDecimal</code> using the {@link RoundingMode#DOWN} rounding mode
     */
    public FixedPrecisionDecimal roundDown() {
        return round(RoundingMode.DOWN);
    }

    /**
     * Returns a <code>FixedPrecisionDecimal</code>
     * whose value is the rounded value of this <code>FixedPrecisionDecimal</code>
     * using the {@link RoundingMode#UP} rounding mode.
     *
     * @return a <code>FixedPrecisionDecimal</code> whose value is the rounded value of this
     * <code>FixedPrecisionDecimal</code> using the {@link RoundingMode#UP} rounding mode
     */
    public FixedPrecisionDecimal roundUp() {
        return round(RoundingMode.UP);
    }

    /**
     * Returns a <code>FixedPrecisionDecimal</code>
     * whose value is the rounded value of this <code>FixedPrecisionDecimal</code>
     * using the {@link RoundingMode#HALF_EVEN} rounding mode.
     *
     * @return a <code>FixedPrecisionDecimal</code> whose value is the rounded value of this
     * <code>FixedPrecisionDecimal</code> using the {@link RoundingMode#HALF_EVEN} rounding mode
     */
    public FixedPrecisionDecimal bankersRound() {
        return round(RoundingMode.HALF_EVEN);
    }

    /**
     * Returns a <code>FixedPrecisionDecimal</code> object whose value is the same as this object,
     * but with a new precision.
     * <p>
     * The precision of a <code>FixedPrecisionDecimal</code> object is the number of digits to the right of the decimal point.
     * A precision is a non-negative integer that represents the number of digits to the right of the decimal point.
     * </p>
     * <p>
     * The object that is returned is a new object with the rounded value.
     * So please be sure to store the returned object in a variable or somewhere else because the original object is not changed. (Immutable)
     * </p>
     *
     * @param newScale the new precision to use
     * @return a <code>FixedPrecisionDecimal</code> object with the same value as this object, but with a new precision
     */
    public FixedPrecisionDecimal resizePrecision(final int newScale) {
        return new FixedPrecisionDecimal(this.value.setScale(newScale, RoundingMode.HALF_UP), newScale, RoundingMode.HALF_UP);
    }

    /**
     * Returns a <code>FixedPrecisionDecimal</code> object whose value is the same as this object,
     * but with a new precision.
     * <p>
     * The precision of a <code>FixedPrecisionDecimal</code> object is the number of digits to the right of the decimal point.
     * A precision is a non-negative integer that represents the number of digits to the right of the decimal point.
     * </p>
     * <p>
     * The object that is returned is a new object with the rounded value.
     * So please be sure to store the returned object in a variable or somewhere else because the original object is not changed. (Immutable)
     * </p>
     *
     * @param newScale     the new precision to use
     * @param roundingMode the rounding mode to use
     * @return a <code>FixedPrecisionDecimal</code> object with the same value as this object, but with a new precision
     */
    public FixedPrecisionDecimal resizePrecision(final int newScale, final RoundingMode roundingMode) {
        return new FixedPrecisionDecimal(this.value.setScale(newScale, roundingMode), newScale, roundingMode);
    }

    /**
     * Formats the value with the given pattern using the {@link DecimalFormat} class.
     *
     * <p>
     * The pattern is a {@link String} that specifies the format of the value.
     * It's used to format the value as a string.
     * </p>
     *
     * @param pattern the pattern to use for formatting the value
     * @return the formatted value as a {@link String}
     * @throws IllegalArgumentException if the pattern is invalid
     * @throws NullPointerException     if the pattern is null
     */
    public String format(final String pattern) {
        Objects.requireNonNull(pattern, "pattern must not be null");
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(this.value);
    }

    /**
     * Returns true if this <code>FixedPrecisionDecimal</code> is greater than the specified <code>FixedPrecisionDecimal</code>.
     *
     * @param other the <code>FixedPrecisionDecimal</code> to compare with
     * @return true if this <code>FixedPrecisionDecimal</code> is greater than the specified <code>FixedPrecisionDecimal</code>
     * @throws IllegalArgumentException if the scales of the two <code>FixedPrecisionDecimal</code> objects do not match
     * @throws NullPointerException     if the specified object is <code>null</code>
     * @see #checkPrecision(FixedPrecisionDecimal)
     */
    public boolean isGreaterThan(final FixedPrecisionDecimal other) {
        return compareTo(other) > 0;
    }

    /**
     * Returns true if this <code>FixedPrecisionDecimal</code> is less than the specified <code>FixedPrecisionDecimal</code>.
     *
     * @param other the <code>FixedPrecisionDecimal</code> to compare with
     * @return true if this <code>FixedPrecisionDecimal</code> is less than the specified <code>FixedPrecisionDecimal</code>
     * @throws IllegalArgumentException if the scales of the two <code>FixedPrecisionDecimal</code> objects do not match
     * @throws NullPointerException     if the specified object is <code>null</code>
     * @see #checkPrecision(FixedPrecisionDecimal)
     */
    public boolean isLessThan(final FixedPrecisionDecimal other) {
        return compareTo(other) < 0;
    }

    /**
     * Returns true if this <code>FixedPrecisionDecimal</code> is greater
     * than or equal to the specified <code>FixedPrecisionDecimal</code>.
     *
     * @param other the <code>FixedPrecisionDecimal</code> to compare with
     * @return true if this <code>FixedPrecisionDecimal</code> is greater
     * than or equal to the specified <code>FixedPrecisionDecimal</code>
     * @throws IllegalArgumentException if the scales of the two <code>FixedPrecisionDecimal</code> objects do not match
     * @throws NullPointerException     if the specified object is <code>null</code>
     * @see #checkPrecision(FixedPrecisionDecimal)
     */
    public boolean isEqualTo(final FixedPrecisionDecimal other) {
        return compareTo(other) == 0;
    }

    /**
     * Checks if the precision of the current <code>FixedPrecisionDecimal</code> matches the precision of another <code>FixedPrecisionDecimal</code>.
     *
     * @param other The <code>FixedPrecisionDecimal</code> to compare the precision with
     * @throws IllegalArgumentException if the precisions do not match
     * @throws NullPointerException     if the other <code>FixedPrecisionDecimal</code> is <code>null</code>
     */
    private void checkPrecision(final FixedPrecisionDecimal other) {
        Objects.requireNonNull(other, "other");
        if (this.precision != other.precision) {
            throw new IllegalArgumentException("Precisions must match: " + this.precision + " != " + other.precision);
        }
    }

    /**
     * Returns the value of the specified number as an {@code int}.
     *
     * @return the numeric value represented by this object after conversion
     * to type {@code int}.
     */
    @Override
    public int intValue() {
        return this.value.intValue();
    }

    /**
     * Returns the value of the specified number as a {@code long}.
     *
     * @return the numeric value represented by this object after conversion
     * to type {@code long}.
     */
    @Override
    public long longValue() {
        return this.value.longValue();
    }

    /**
     * Returns the value of the specified number as a {@code float}.
     *
     * @return the numeric value represented by this object after conversion
     * to type {@code float}.
     */
    @Override
    public float floatValue() {
        return this.value.floatValue();
    }

    /**
     * Returns the value of the specified number as a {@code double}.
     *
     * @return the numeric value represented by this object after conversion
     * to type {@code double}.
     */
    @Override
    public double doubleValue() {
        return this.value.doubleValue();
    }

    /**
     * Prints the value of this object to the console or a file via a {@link PrintStream} object.
     * <p>
     * The printing method is used to specify how the object should be printed.
     * It's used to print the object to the console or a file via a {@link PrintStream} object.
     * To print a object, use this method or one of the other print methods.
     * </p>
     */
    @Override
    public void print() {
        switch (printingMethod) {
            case SYSTEM_OUT:
                print(System.out);
                break;
            case SYSTEM_ERR:
                print(System.err);
                break;
            case JAVA_UTIL_LOGGER:
                LOGGER.info(this.value.toString());
                break;
            default:
                throw new IllegalArgumentException("Unsupported printing method: " + printingMethod);
        }
    }

    /**
     * Prints the value of this object to the console or a file via a {@link PrintStream} object.
     * <p>
     * The printing method is used to specify how the object should be printed.
     * It's used to print the object to the console or a file via a {@link PrintStream} object.
     * To print a object, use this method or one of the other print methods.
     * </p>
     *
     * @param out The {@link PrintStream} object to print the object to.
     * @throws NullPointerException If the {@link PrintStream} object is <code>null</code>.
     */
    @Override
    public void print(final PrintStream out) {
        Objects.requireNonNull(out, "The PrintStream object must not be null");
        out.println(this.value);
    }

    /**
     * Prints the value of this object to the console or a file via a {@link PrintStream} object and returns the object.
     * <p>
     * The printing method is used to specify how the object should be printed.
     * It's used to print the object to the console or a file via a {@link PrintStream} object.
     * To print a object, use this method or one of the other print methods.
     * </p>
     *
     * @return The object that was printed. (The object itself)
     */
    @Override
    public FixedPrecisionDecimal printAndReturn() {
        this.print();
        return this;
    }

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
     * @throws NullPointerException If the {@link PrintStream} object is <code>null</code>.
     */
    @Override
    public FixedPrecisionDecimal printAndReturn(final PrintStream out) {
        this.print(out);
        return this;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final FixedPrecisionDecimal that = (FixedPrecisionDecimal) obj;

        return this.value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
