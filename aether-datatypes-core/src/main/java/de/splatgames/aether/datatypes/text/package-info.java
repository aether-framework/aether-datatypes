/**
 * Provides classes for handling and manipulating text data with extended capabilities beyond the standard {@link java.lang.String String} class.
 *
 * <p>
 * The primary class in this package is {@link de.splatgames.aether.datatypes.text.String32 String32}, which represents a sequence of 32-bit characters.
 * This allows for the representation of all Unicode characters, including those beyond the Basic Multilingual Plane (BMP).
 * Unlike the standard Java {@link java.lang.String String} class, which uses 16-bit characters, {@link de.splatgames.aether.datatypes.text.String32 String32} uses 32-bit integers to store characters.
 * </p>
 *
 * <p>
 * The {@link de.splatgames.aether.datatypes.text.String32 String32} class provides various methods for common string operations such as joining, splitting, replacing, and formatting strings.
 * It also offers additional functionality like bitwise operations, case transformations, padding, and more, ensuring a comprehensive set of tools
 * for text manipulation.
 * </p>
 *
 * <h2>Usage Example:</h2>
 * <pre>{@code
 * public class ExampleClass {
 *     public static void main(String[] args) {
 *         String32 string32 = String32.valueOf("Hello, World!");
 *         System.out.println(string32.toUpperCase());
 *     }
 * }
 * }</pre>
 *
 * <p>
 * Future additions to this package might include:
 * <ul>
 *     <li>{@code String32Builder}: A mutable counterpart to {@link de.splatgames.aether.datatypes.text.String32 String32}, providing efficient text concatenation and modification.</li>
 *     <li>{@code String32Utils}: Utility methods for common string operations like trimming, padding, and splitting with enhanced capabilities.</li>
 *     <li>{@code String32Formatter}: Advanced formatting options for {@link de.splatgames.aether.datatypes.text.String32 String32} objects, similar to {@link java.util.Formatter}.</li>
 * </ul>
 * </p>
 *
 * @see java.lang.String String
 * @see de.splatgames.aether.datatypes.text.String32 String32
 * @see java.io.Serializable Serializable
 * @since 1.0.0
 */
package de.splatgames.aether.datatypes.text;
