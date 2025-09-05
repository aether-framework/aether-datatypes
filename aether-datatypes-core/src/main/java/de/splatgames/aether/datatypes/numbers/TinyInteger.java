package de.splatgames.aether.datatypes.numbers;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * A <code>TinyInteger</code> object holds a smaller range of values in an <code>8-Bit</code> range.
 * <p>
 * This can be particularly useful when you want to save memory or restrict the range of values.
 * This class is most beneficial in embedded systems with limited memory.
 * Even though the underlying object is an {@link Integer}, operations on this object are more efficient
 * than those on an {@link Integer} because of the smaller range.
 * </p>
 *
 * <h2>Unsigned Range</h2>
 * <p>
 * The unsigned range of a <code>TinyInteger</code> is from 0 to 255.
 * If there's an overflow or underflow, the value will be normalized to this range.
 * </p>
 *
 * <h2>Signed Range</h2>
 * <p>
 * The signed range of a <code>TinyInteger</code> is from -128 to 127.
 * Similarly, if there's an overflow or underflow, the value will be normalized to this range.
 * </p>
 *
 * <h3>Check Signed Status</h3>
 * <p>
 * You can determine whether the <code>TinyInteger</code> is signed or unsigned by calling the method {@link TinyInteger#isSigned()}.
 * Typically, the <code>TinyInteger</code> is unsigned. To create a signed <code>TinyInteger</code>,
 * use the {@link TinyInteger#signedValueOf(int)} method. Note that using a signed <code>TinyInteger</code>
 * is not recommended due to the potential for unexpected behavior.
 * </p>
 *
 * <h3>Example: Overflow and Underflow</h3>
 * <p>
 * Here's an example demonstrating overflow and underflow:
 * </p>
 * <blockquote><pre>
 * TinyInteger tinyMax = TinyInteger.valueOf(TinyInteger.MAX_VALUE);
 * TinyInteger tinyMin = TinyInteger.valueOf(TinyInteger.MIN_VALUE);
 * TinyInteger tinyPlus = tinyMax.add(TinyInteger.valueOf(2));
 * TinyInteger tinyMinus = tinyMin.subtract(TinyInteger.valueOf(2));
 *
 * // TinyInteger Max Value: 255
 * System.out.println("TinyInteger Max Value: " + tinyMax);
 * // TinyInteger Min Value: 0
 * System.out.println("TinyInteger Min Value: " + tinyMin);
 * // TinyInteger Behavior: 1
 * System.out.println("TinyInteger Behavior: " + tinyPlus);
 * // TinyInteger Behavior: 254
 * System.out.println("TinyInteger Behavior: " + tinyMinus);
 * </pre></blockquote>
 * <p>
 * In this example, the <code>TinyInteger</code> normalizes values to the specified range.
 * Note that no notification is provided when a value is normalized; you need to check the value yourself.
 * </p>
 *
 * <h3>Use Cases</h3>
 * <p>
 * The <code>TinyInteger</code> class is highly useful for embedded systems or situations where memory conservation is crucial,
 * or where you need to restrict the range of values.
 * </p>
 *
 * @author Erik Pförtner
 * @apiNote The signed range isn't recommended for use due to the potential for unexpected behavior.
 * @see TinyInteger#MAX_VALUE
 * @see TinyInteger#MIN_VALUE
 * @see TinyInteger#SIGNED_MAX_VALUE
 * @see TinyInteger#SIGNED_MIN_VALUE
 * @see TinyInteger#ZERO
 * @since 1.0.0
 */
public class TinyInteger extends Number implements Serializable, Comparable<TinyInteger> {

    /**
     * This is the maximum value that a <code>TinyInteger</code> can hold.
     * <p>
     * Please note that this value is the unsigned maximum value.
     * To get the signed maximum value, use {@link TinyInteger#SIGNED_MAX_VALUE}.
     * </p>
     */
    public static final int MAX_VALUE = 255;
    /**
     * This is the minimum value that a <code>TinyInteger</code> can hold.
     * <p>
     * Please note that this value is the unsigned minimum value.
     * To get the signed minimum value, use {@link TinyInteger#SIGNED_MIN_VALUE}.
     * </p>
     */
    public static final int MIN_VALUE = 0;
    /**
     * This is the maximum value that a signed <code>TinyInteger</code> can hold.
     * <p>
     * Please note that this value is the signed maximum value.
     * To get the unsigned maximum value, use {@link TinyInteger#MAX_VALUE}.
     * </p>
     */
    public static final int SIGNED_MAX_VALUE = 127;
    /**
     * This is the minimum value that a signed <code>TinyInteger</code> can hold.
     * <p>
     * Please note that this value is the signed minimum value.
     * To get the unsigned minimum value, use {@link TinyInteger#MIN_VALUE}.
     * </p>
     */
    public static final int SIGNED_MIN_VALUE = -128;

    /**
     * The <code>serialVersionUID</code> is used to ensure that the class can be serialized correctly.
     * <p>
     * This is the <code>serialVersionUID</code> of the <code>TinyInteger</code> class.
     * It is used to ensure that the class can be serialized correctly.
     * </p>
     */
    @Serial
    private static final long serialVersionUID = -8407872800255535787L;

    /**
     * This is the <code>TinyInteger</code> object that represents the value <code>0</code>.
     * <p>
     * This object is immutable and can be used to represent the value <code>0</code>.
     * </p>
     */
    public static final TinyInteger ZERO = new TinyInteger(0);

    /**
     * This is the <code>TinyInteger</code> object that represents the value <code>1</code>.
     * <p>
     * This object is immutable and can be used to represent the value <code>1</code>.
     * </p>
     */
    public static final TinyInteger ONE = new TinyInteger(1);

    /**
     * The value of the <code>TinyInteger</code> object.
     */
    private final int value;
    /**
     * The signed status of the <code>TinyInteger</code> object.
     */
    private final boolean signed;

    /**
     * Constructs a new <code>TinyInteger</code> object with the specified value and signed status.
     *
     * @param value  the value of the <code>TinyInteger</code> object.
     * @param signed the signed status of the <code>TinyInteger</code> object.
     */
    TinyInteger(final int value, final boolean signed) {
        this.value = normalize(value, signed);
        this.signed = signed;
    }

    /**
     * Constructs a new <code>TinyInteger</code> object with the specified value.
     * <p>
     * The <code>TinyInteger</code> object will be unsigned.
     * If you want to create a signed <code>TinyInteger</code> object, use the {@link TinyInteger#signedValueOf(int)} method.
     * Note that using a signed <code>TinyInteger</code> object is not recommended due to the potential for unexpected behavior or memory-leaks.
     * </p>
     *
     * @param value the value of the <code>TinyInteger</code> object.
     */
    public TinyInteger(final int value) {
        this(value, false);
    }

    /**
     * Returns a <code>TinyInteger</code> object representing the specified value.
     * <p>
     * The <code>TinyInteger</code> object will be unsigned.
     * If you want to create a signed <code>TinyInteger</code> object, use the {@link TinyInteger#signedValueOf(int)} method.
     * Note that using a signed <code>TinyInteger</code> object is not recommended due to the potential for unexpected behavior or memory-leaks.
     * </p>
     *
     * @param value the value of the <code>TinyInteger</code> object.
     * @return a <code>TinyInteger</code> object representing the specified value.
     */
    public static TinyInteger valueOf(final int value) {
        return new TinyInteger(value);
    }

    /**
     * Returns a signed <code>TinyInteger</code> object representing the specified value.
     * <p>
     * The <code>TinyInteger</code> object will be signed.
     * Note that using a signed <code>TinyInteger</code> object is not recommended due to the potential for unexpected behavior or memory-leaks.
     * </p>
     *
     * @param value the value of the <code>TinyInteger</code> object.
     * @return a signed <code>TinyInteger</code> object representing the specified value.
     * @apiNote Use this method only if you need a signed <code>TinyInteger</code> object.
     * @deprecated This method is unsafe due to the potential for unexpected behavior or memory-leaks.
     */
    @Deprecated
    public static TinyInteger signedValueOf(final int value) {
        return new TinyInteger(value, true);
    }

    /**
     * Adds the specified <code>TinyInteger</code> object to this object.
     * <p>
     * Example:
     * <blockquote><pre>
     *         TinyInteger a = TinyInteger.valueOf(10);
     *         TinyInteger b = TinyInteger.valueOf(20);
     *         TinyInteger c = a.add(b);
     *         // c = 30
     *         System.out.println(c);
     * </pre></blockquote>
     * </p>
     * <p>
     * Note that the <code>TinyInteger</code> object will normalize the value to the specified range.
     * </p>
     *
     * @param other the <code>TinyInteger</code> object to add to this object.
     * @return a new <code>TinyInteger</code> object representing the sum of this object and the specified object.
     * @throws NullPointerException if the specified object is <code>null</code>.
     */
    public TinyInteger add(final TinyInteger other) {
        Objects.requireNonNull(other, "The specified TinyInteger object must not be null");
        return new TinyInteger(this.value + other.value, this.signed);
    }

    /**
     * Subtracts the specified <code>TinyInteger</code> object from this object.
     * <p>
     * Example:
     * <blockquote><pre>
     *         TinyInteger a = TinyInteger.valueOf(10);
     *         TinyInteger b = TinyInteger.valueOf(20);
     *         TinyInteger c = a.subtract(b);
     *         // c = -10
     *         System.out.println(c);
     * </pre></blockquote>
     * </p>
     * <p>
     * Note that the <code>TinyInteger</code> object will normalize the value to the specified range.
     * </p>
     *
     * @param other the <code>TinyInteger</code> object to subtract from this object.
     * @return a new <code>TinyInteger</code> object representing the difference between this object and the specified object.
     * @throws NullPointerException if the specified object is <code>null</code>.
     */
    public TinyInteger subtract(final TinyInteger other) {
        Objects.requireNonNull(other, "The specified TinyInteger object must not be null");
        return new TinyInteger(this.value - other.value, this.signed);
    }

    /**
     * Multiplies this object by the specified <code>TinyInteger</code> object.
     * <p>
     * Example:
     * <blockquote><pre>
     *         TinyInteger a = TinyInteger.valueOf(10);
     *         TinyInteger b = TinyInteger.valueOf(20);
     *         TinyInteger c = a.multiply(b);
     *         // c = 200
     *         System.out.println(c);
     * </pre></blockquote>
     * </p>
     * <p>
     * Note that the <code>TinyInteger</code> object will normalize the value to the specified range.
     * </p>
     *
     * @param other the <code>TinyInteger</code> object to multiply this object by.
     * @return a new <code>TinyInteger</code> object representing the product of this object and the specified object.
     * @throws NullPointerException if the specified object is <code>null</code>.
     */
    public TinyInteger multiply(final TinyInteger other) {
        Objects.requireNonNull(other, "The specified TinyInteger object must not be null");
        return new TinyInteger(this.value * other.value, this.signed);
    }

    /**
     * Divides this object by the specified <code>TinyInteger</code> object.
     * <p>
     * Example:
     * <blockquote><pre>
     *         TinyInteger a = TinyInteger.valueOf(10);
     *         TinyInteger b = TinyInteger.valueOf(20);
     *         TinyInteger c = a.divide(b);
     *         // c = 0
     *         System.out.println(c);
     * </pre></blockquote>
     * </p>
     * <p>
     * Note that the <code>TinyInteger</code> object will normalize the value to the specified range.
     * </p>
     *
     * @param other the <code>TinyInteger</code> object to divide this object by.
     * @return a new <code>TinyInteger</code> object representing the quotient of this object and the specified object.
     * @throws ArithmeticException  if the specified object is <code>0</code>.
     * @throws NullPointerException if the specified object is <code>null</code>.
     */
    public TinyInteger divide(final TinyInteger other) {
        Objects.requireNonNull(other, "The specified TinyInteger object must not be null");
        if (other.value == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return new TinyInteger(this.value / other.value, this.signed);
    }

    /**
     * Checks if the <code>TinyInteger</code> object is signed.
     * <p>
     * Make sure to use this method only if you need to determine whether the <code>TinyInteger</code> object is signed.
     * Most of the time, the <code>TinyInteger</code> object is unsigned.
     * </p>
     *
     * @return {@code true} if the <code>TinyInteger</code> object is signed; {@code false} otherwise.
     */
    public boolean isSigned() {
        return this.signed;
    }

    /**
     * Normalizes the specified value to the specified range.
     *
     * @param unchecked the value to normalize.
     * @param signed    the signed status of the value.
     * @return the normalized value.
     */
    private int normalize(final int unchecked, final boolean signed) {
        int value = unchecked;
        int range;
        if (signed) {
            range = SIGNED_MAX_VALUE - SIGNED_MIN_VALUE + 1;
            value = (value - SIGNED_MIN_VALUE) % range;
            if (value < 0) {
                value += range;
            }
            value += SIGNED_MIN_VALUE;
        } else {
            range = MAX_VALUE - MIN_VALUE + 1;
            value = (value - MIN_VALUE) % range;
            if (value < 0) {
                value += range;
            }
        }
        return value;
    }

    /**
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure {@link Integer#signum
     * signum}{@code (x.compareTo(y)) == -signum(y.compareTo(x))} for
     * all {@code x} and {@code y}.  (This implies that {@code
     * x.compareTo(y)} must throw an exception if and only if {@code
     * y.compareTo(x)} throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies
     * {@code x.compareTo(z) > 0}.
     *
     * <p>Finally, the implementor must ensure that {@code
     * x.compareTo(y)==0} implies that {@code signum(x.compareTo(z))
     * == signum(y.compareTo(z))}, for all {@code z}.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     * @apiNote It is strongly recommended, but <i>not</i> strictly required that
     * {@code (x.compareTo(y)==0) == (x.equals(y))}. Generally speaking, any
     * class that implements the {@code Comparable} interface and violates
     * this condition should clearly indicate this fact. The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     */
    @Override
    public int compareTo(final TinyInteger o) {
        Objects.requireNonNull(o, "The specified TinyInteger object must not be null");
        return Integer.compare(this.value, o.value);
    }

    /**
     * Returns the value of the specified number as an {@code int}.
     *
     * @return the numeric value represented by this object after conversion
     * to type {@code int}.
     */
    @Override
    public int intValue() {
        return this.value;
    }

    /**
     * Returns the value of the specified number as a {@code long}.
     *
     * @return the numeric value represented by this object after conversion
     * to type {@code long}.
     */
    @Override
    public long longValue() {
        return this.value;
    }

    /**
     * Returns the value of the specified number as a {@code float}.
     *
     * @return the numeric value represented by this object after conversion
     * to type {@code float}.
     */
    @Override
    public float floatValue() {
        return this.value;
    }

    /**
     * Returns the value of the specified number as a {@code double}.
     *
     * @return the numeric value represented by this object after conversion
     * to type {@code double}.
     */
    @Override
    public double doubleValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return Integer.toString(this.value);
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(this.value);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof TinyInteger ti) {
            return this.value == ti.value && this.signed == ti.signed;
        }

        return false;
    }
}
