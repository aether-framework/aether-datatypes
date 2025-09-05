/**
 * Provides classes for handling and manipulating immutable, arbitrary-precision signed large decimal numbers with fixed precision.
 *
 * <p>
 * The main class in this package is {@link de.splatgames.aether.datatypes.numbers.FixedPrecisionDecimal FixedPrecisionDecimal}, which allows for precise arithmetic and
 * printing operations on large decimal numbers. The precision of these numbers is fixed, ensuring that the number
 * of digits to the right of the decimal point is maintained across various calculations.
 * </p>
 * <p>
 * There are also several classes in this package that are also other data-types, such as {@link de.splatgames.aether.datatypes.numbers.TinyInteger TinyInteger}.
 * The {@link de.splatgames.aether.datatypes.numbers.TinyInteger TinyInteger} class is a class
 * for handling small integers with a range of -128 to 127 for <code>Signed-</code> and 0 to 255 for <code>Unsigned</code>-{@link de.splatgames.aether.datatypes.numbers.TinyInteger TinyIntegers}.
 * This could be useful for applications that require small integers with a smaller range than the standard {@link java.lang.Integer Integer} class.
 * It is mostly used on Embedded Systems, where memory is limited.
 * </p>
 *
 * <p>
 * Future classes in this package may include:
 * <ul>
 *   <li>{@code ComplexNumber}: A class for handling complex numbers with real and imaginary parts, supporting operations like addition, subtraction, multiplication, and division.</li>
 *   <li>{@code RationalNumber}: A class for representing rational numbers as fractions, providing methods for arithmetic operations, simplification, and conversion to and from decimal representation.</li>
 *   <li>{@code Vector}: A class for handling mathematical vectors, supporting operations such as dot product, cross product, and vector norms.</li>
 *   <li>{@code Matrix}: A class for matrix operations, including addition, multiplication, inversion, and determinant calculation.</li>
 * </ul>
 * </p>
 *
 * <p>
 * All classes in this package are designed with immutability in mind, ensuring thread-safety and predictability in multi-threaded applications.
 * The main class, {@link de.splatgames.aether.datatypes.numbers.FixedPrecisionDecimal FixedPrecisionDecimal}, integrates with the {@link java.math.BigDecimal BigDecimal} class for its underlying representation,
 * and provides various methods for constructing instances from different numerical types.
 * </p>
 *
 * <h2>Usage Example:</h2>
 * <pre>{@code
 * FixedPrecisionDecimal decimal1 = FixedPrecisionDecimal.valueOf("123.456", 3);
 * FixedPrecisionDecimal decimal2 = FixedPrecisionDecimal.valueOf("78.123", 3);
 * FixedPrecisionDecimal result = decimal1.add(decimal2);
 * result.print(); // Output: 201.579
 * }</pre>
 *
 * <p>
 * Future extensions to this package will aim to provide comprehensive support for various numerical types and operations,
 * catering to a wide range of scientific, financial, and engineering applications.
 * </p>
 *
 * <p>
 * Each class in this package will follow the principles of immutability and high precision, leveraging the {@link java.math.BigDecimal BigDecimal}
 * and other Java standard libraries where appropriate.
 * </p>
 *
 * @see java.math.BigDecimal BigDecimal
 * @see java.math.RoundingMode RoundingMode
 * @see java.math.MathContext MathContext
 * @see de.splatgames.aether.datatypes.printing.Printable Printable
 * @see de.splatgames.aether.datatypes.printing.PrintingMethod PrintingMethod
 * @since 1.0.0
 */
package de.splatgames.aether.datatypes.numbers;
