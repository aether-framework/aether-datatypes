package de.splatgames.aether.datatypes.text;

import de.splatgames.aether.datatypes.utils.Streams;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntUnaryOperator;
import java.util.regex.Pattern;

/**
 * The <code>String32</code> class represents a {@link String} of <code>32-bit</code> characters.
 * <p>
 * All characters are stored as <code>32-bit</code> {@link Integer Integers}, which allows for the representation of all Unicode characters.
 * </p>
 * <p>
 * Instead of using the Java {@link String} class, which uses <code>16-bit</code> characters, this class uses <code>32-bit</code> characters.
 * This allows for the representation of all Unicode characters, including emojis and other special characters.
 * They are stored as an array of integers, where each integer represents a single character.
 * </p>
 * <p>
 * The implementation is done by an array of {@link Integer} objects, which store the Unicode code points of the characters.
 * The normal Java {@link String} class uses an array of <code>char</code> objects, which store <code>16-bit</code> characters.
 * This allows the normal {@link String} class to store only characters <code>2^16 - 1</code>, which is <code>65535</code>.
 * Our <code>String32</code> class can store all characters up to <code>2^31 - 1</code>, which is <code>2147483647</code>.
 * </p>
 * <p>
 * Other than the {@link String} class, this class must be called over the {@link String32#valueOf(String)} method.
 * Example:
 * <blockquote><pre>
 *         String32 string32 = String32.valueOf("Hello, World!");
 *         System.out.println(string32.toString());
 * </pre></blockquote>
 * </p>
 * Instead of:
 * <blockquote><pre>
 *     String32 string32 = new String32("Hello, World!");
 *
 *     System.out.println(string32.toString());
 * </pre></blockquote>
 * <p>
 * This is done
 * to ensure that the <code>String32</code> class is used instead of the normal {@link String} class for much larger characters.
 * </p>
 * <p>
 * As the {@link String} class, this class
 * is <code>immutable</code>, which means that the characters cannot be changed after the object is created.
 * You only can read the characters, but not change them.
 * To change the characters, you have to create a new <code>String32</code> object with the new characters.
 * This could be done with our methods implemented in this class.
 * </p>
 * <p>
 * This class also contains certain methods to manipulate new <code>String32</code> objects.
 * For example, the {@link String32#charAt(int)} method returns the character at the specified index.
 * </p>
 * <p>
 * The <code>String32</code> class also contains methods to join multiple <code>String32</code> objects.
 * This is done with the {@link String32#join(Iterable)} and {@link String32#join(String32[])} methods.
 * The {@link String32#join(Iterable)} method joins the given {@link Iterable} of {@link Object} to a single <code>String32</code> object.
 * The {@link String32#join(String32[])} method joins the given {@link Object} array to a single <code>String32</code> object.
 * Both methods return a new {@link Strings32ToJoin} object to join the {@link Object} array or {@link Iterable} of {@link Object}.<br>
 * Example:
 * <blockquote><pre>
 *         String32 string32 = String32.join(List.of("Hello", "World")).with(" ");
 *         // Output: "Hello World"
 *         System.out.println(string32.toString());
 *         </pre></blockquote>
 * This will join the {@link Iterable} of {@link Object} to a single <code>String32</code> object with the given delimiter.
 * In this case it is a space character so that the output will be <code>Hello World</code>.
 * </p>
 *
 * @author Erik Pförtner
 * @implNote This class is <code>immutable</code>, which means that the characters cannot be changed after the object is created.
 * It is also possible to serialize this class because it uses the {@link Serializable} interface.
 * @see String
 * @see Integer
 * @since 1.0.0
 */
public final class String32 implements Serializable, Comparable<String32>, Cloneable {

    /**
     * The <code>serialVersionUID</code> is used to ensure that the class can be serialized correctly.
     * <p>
     * This is the <code>serialVersionUID</code> of the <code>String32</code> class.
     * It is used to ensure that the class can be serialized correctly.
     * </p>
     */
    @Serial
    private static final long serialVersionUID = 2664782218035467567L;

    /**
     * The characters of the <code>String32</code> object.
     * <p>
     * The characters of the <code>String32</code> object are stored as <code>32-bit</code> {@link Integer Integers}.
     * This allows for the representation of all Unicode characters.
     * </p>
     */
    private final int[] characters;
    /**
     * The length of the <code>String32</code> object.
     * <p>
     * The length of the <code>String32</code> object is the number of characters in the <code>String32</code> object.
     * </p>
     */
    private final int length;

    /**
     * Constructs a new <code>String32</code> object that contains the characters of the given {@link String}.
     * <p>
     * This constructor constructs a new <code>String32</code> object that contains the characters of the given {@link String}.
     * The characters are stored as <code>32-bit</code> {@link Integer Integers}, which allows for the representation of all Unicode characters.
     * </p>
     *
     * @param input The {@link String} to convert to a <code>String32</code> object.
     * @throws NullPointerException If the input is <code>null</code>.
     */
    String32(final String input) {
        Objects.requireNonNull(input, "Input cannot be null");

        this.characters = input.codePoints().toArray();
        this.length = this.characters.length;
    }

    /**
     * Returns a new <code>String32</code> object that contains the characters of the given {@link String}.
     * <p>
     * This method returns a new <code>String32</code> object that contains the characters of the given {@link String}.
     * The characters are stored as <code>32-bit</code> {@link Integer Integers}, which allows for the representation of all Unicode characters.
     * </p>
     *
     * @param input The {@link String} to convert to a <code>String32</code> object.
     * @return A new <code>String32</code> object that contains the characters of the given {@link String}.
     * @throws NullPointerException If the input is <code>null</code>.
     */
    public static String32 valueOf(final String input) {
        return new String32(input);
    }

    /**
     * Returns a new <code>String32</code> object that contains the characters of the given {@code int[]}.
     * <p>
     * This method returns a new <code>String32</code> object that contains the characters of the given {@code int[]}.
     * The characters are stored as <code>32-bit</code> {@link Integer Integers}, which allows for the representation of all Unicode characters.
     * </p>
     *
     * @param input The {@code int[]} to convert to a <code>String32</code> object.
     * @return A new <code>String32</code> object that contains the characters of the given {@code int[]}.
     * @throws NullPointerException If the input is <code>null</code>.
     */
    public static String32 valueOf(final int[] input) {
        Objects.requireNonNull(input, "Input cannot be null");
        StringBuilder sb = new StringBuilder();
        for (int i : input) {
            sb.appendCodePoint(i);
        }
        return new String32(sb.toString());
    }

    /**
     * Returns a new <code>String32</code> object that contains the character of the given {@link Integer}.
     * <p>
     * This method returns a new <code>String32</code> object that contains the character of the given {@link Integer}.
     * The character are stored as <code>32-bit</code> {@link Integer Integer}, which allows for the representation of all Unicode characters.
     * </p>
     *
     * @param codePoint The {@link Integer} to convert to a <code>String32</code> object.
     * @return A new <code>String32</code> object that contains the characters of the given {@link Integer} array.
     * @throws NullPointerException If the input is <code>null</code>.
     */
    public static String32 valueOf(final int codePoint) {
        return valueOf(new int[]{codePoint});
    }

    /**
     * Returns a new <code>String32</code> object that contains the characters of the given {@link Character char} array.
     * <p>
     * This method returns a new <code>String32</code> object that contains the characters of the given {@link Character char} array.
     * The characters are stored as <code>32-bit</code> {@link Integer Integers}, which allows for the representation of all Unicode characters.
     * </p>
     *
     * @param input The {@link Character char} array to convert to a <code>String32</code> object.
     * @return A new <code>String32</code> object that contains the characters of the given {@link Character char} array.
     * @throws NullPointerException If the input is <code>null</code>.
     */
    public static String32 valueOf(final char[] input) {
        Objects.requireNonNull(input, "Input cannot be null");
        StringBuilder sb = new StringBuilder();
        for (char c : input) {
            sb.append(c);
        }
        return new String32(sb.toString());
    }

    /**
     * Joins the given {@link Iterable} of {@link Object} to a single <code>String32</code> object.
     * <p>
     * This method is similar to the {@link String#join(CharSequence, Iterable)} method, but it returns a <code>String32</code> object instead of a {@link String} object.
     * The <code>String32</code> object is used to store <code>32-bit</code> characters, which allows for the representation of all Unicode characters.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.join(List.of("Hello", "World")).with(" ");
     *         // Output: "Hello World"
     *         System.out.println(string32.toString());
     * </pre></blockquote>
     * This will join the {@link Iterable} of {@link Object} to a single <code>String32</code> object with the given delimiter.
     * In this case it is a space character so that the output will be <code>Hello World</code>.
     * </p>
     *
     * @param toStringable The {@link Iterable} of {@link Object} to join.
     * @return A new {@link Strings32ToJoin} object to join the {@link Iterable} of {@link Object}.
     * @throws NullPointerException If the {@link Iterable} is <code>null</code>.
     * @see Strings32ToJoin
     * @see String32
     */
    public static Strings32ToJoin join(final Iterable<?> toStringable) {
        Objects.requireNonNull(toStringable, "Iterable cannot be null");
        String32[] strings = Streams.stream(toStringable).map(String::valueOf).map(String32::valueOf).toArray(String32[]::new);
        return new Strings32ToJoin(strings);
    }

    /**
     * Joins the given {@link Object} array to a single <code>String32</code> object.
     * <p>
     * This method is similar to the {@link String#join(CharSequence, CharSequence...)} method, but it returns a <code>String32</code> object instead of a {@link String} object.
     * The <code>String32</code> object is used to store <code>32-bit</code> characters, which allows for the representation of all Unicode characters.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.join("Hello", "World").with(" ");
     *         // Output: "Hello World"
     *         System.out.println(string32.toString());
     * </pre></blockquote>
     * This will join the {@link Object} array to a single <code>String32</code> object with the given delimiter.
     * In this case it is a space character so that the output will be <code>Hello World</code>.
     * </p>
     *
     * @param strings The {@link Object} array to join.
     * @return A new {@link Strings32ToJoin} object to join the {@link Object} array.
     * @throws NullPointerException If the array is <code>null</code>.
     * @see Strings32ToJoin
     * @see String32
     */
    public static Strings32ToJoin join(final String32... strings) {
        Objects.requireNonNull(strings, "Array cannot be null");
        return new Strings32ToJoin(strings);
    }

    /**
     * Returns the character at the specified index.
     * <p>
     * This method returns the character at the specified index.
     * The index is zero-based, which means that the first character has the index <code>0</code>.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello, World!");
     *         System.out.println(string32.charAt(0));
     *         // Output: 72
     * </pre></blockquote>
     * This will return the character at the index <code>0</code>, which is the character <code>H</code>.
     * The character <code>H</code> has the Unicode code point <code>72</code>.
     * </p>
     *
     * @param index The index of the character to return.
     * @return The character at the specified index.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     */
    public int charAt(final int index) {
        if (index < 0 || index >= this.length) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + this.length);
        }
        return characters[index];
    }

    /**
     * Returns the length of the <code>String32</code> object.
     * <p>
     * This method returns the length of the <code>String32</code> object.<br>
     * The length is the number of characters in the <code>String32</code> object.
     * </p>
     *
     * @return The length of the <code>String32</code> object.
     */
    public int length() {
        return this.length;
    }

    /**
     * Returns a new <code>String32</code> object that is a substring of this <code>String32</code> object.
     * <p>
     * This method returns a new <code>String32</code> object that is a substring of this <code>String32</code> object.
     * The substring begins at the specified <code>beginIndex</code> and extends to the character at index <code>endIndex - 1</code>.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello, World!");
     *         System.out.println(string32.substring(0, 5));
     *         // Output: "Hello"
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is a substring of the <code>String32</code> object.
     * The substring begins at the index <code>0</code> and extends to the character at index <code>5 - 1 = 4</code>.
     * The substring is <code>Hello</code>.
     * </p>
     *
     * @param beginIndex The beginning index, inclusive.
     * @param endIndex   The ending index, exclusive.
     * @return The new <code>String32</code> object that is a substring of this <code>String32</code> object.
     * @throws IndexOutOfBoundsException If the <code>beginIndex</code> is negative, or <code>endIndex</code> is larger than the length of the <code>String32</code> object, or <code>beginIndex</code> is larger than <code>endIndex</code>.
     */
    public String32 substring(final int beginIndex, final int endIndex) {
        if (beginIndex < 0 || endIndex > this.length || beginIndex > endIndex) {
            throw new IndexOutOfBoundsException("BeginIndex: " + beginIndex + ", EndIndex: " + endIndex + ", Length: " + this.length);
        }
        return String32.valueOf(Arrays.copyOfRange(this.characters, beginIndex, endIndex));
    }

    /**
     * Converts the <code>String32</code> object to a standard Java {@link String}.
     * <p>
     * This method converts the <code>String32</code> object to a standard Java {@link String}.
     * The characters are converted from <code>32-bit</code> {@link Integer Integers} to <code>16-bit</code> {@link Character Characters}.
     * </p>
     *
     * @return The standard Java {@link String} representation of the <code>String32</code> object.
     */
    public byte[] toUtf32BE() {
        byte[] bytes = new byte[this.length * 4];
        for (int i = 0; i < this.length; i++) {
            int character = this.characters[i];
            bytes[i * 4] = (byte) (character >> 24);
            bytes[i * 4 + 1] = (byte) (character >> 16);
            bytes[i * 4 + 2] = (byte) (character >> 8);
            bytes[i * 4 + 3] = (byte) character;
        }
        return bytes;
    }

    /**
     * Returns the characters of the <code>String32</code> object.
     * <p>
     * This method returns the characters of the <code>String32</code> object.
     * The characters are stored as <code>32-bit</code> {@link Integer Integers}.
     * </p>
     *
     * @return The characters of the <code>String32</code> object.
     */
    public int[] getCharacters() {
        return Arrays.copyOf(this.characters, this.length);
    }

    /**
     * Returns a new <code>String32</code> object that is formatted with the given arguments.
     * <p>
     * This method returns a new <code>String32</code> object that is formatted with the given arguments.
     * The arguments are used to replace the placeholders in the <code>String32</code> object.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello, %s!");
     *         System.out.println(string32.format("World"));
     *         // Output: "Hello, World!"
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is formatted with the given arguments.
     * The placeholder <code>%s</code> is replaced with the argument <code>World</code>.
     * The output will be <code>Hello, World!</code>.
     * </p>
     *
     * @param args The arguments to replace the placeholders in the <code>String32</code> object.
     * @return The new <code>String32</code> object that is formatted with the given arguments.
     */
    public String32 format(final Object... args) {
        return String32.valueOf(String.format(Locale.ROOT, toString(), args));
    }

    /**
     * Returns a new <code>String32</code> object that is formatted with the given arguments and locale.
     * <p>
     * This method returns a new <code>String32</code> object that is formatted with the given arguments and locale.
     * The arguments are used to replace the placeholders in the <code>String32</code> object.
     * The locale is used to format the arguments according to the specified locale.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello, %s!");
     *         System.out.println(string32.format(Locale.US, "World"));
     *         // Output: "Hello, World!"
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is formatted with the given arguments and locale.
     * The placeholder <code>%s</code> is replaced with the argument <code>World</code>.
     * The output will be <code>Hello, World!</code>.
     * </p>
     *
     * @param locale The locale to format the arguments.
     * @param args   The arguments to replace the placeholders in the <code>String32</code> object.
     * @return The new <code>String32</code> object that is formatted with the given arguments and locale.
     * @throws NullPointerException If the locale is <code>null</code>.
     */
    public String32 format(final Locale locale, final Object... args) {
        Objects.requireNonNull(locale, "Locale cannot be null");
        return String32.valueOf(String.format(locale, toString(), args));
    }

    /**
     * Returns a new <code>String32</code> object that is replaced with the given target and replacement.
     * <p>
     * This method returns a new <code>String32</code> object that is replaced with the given target and replacement.
     * The target is the {@link String32} to be replaced, and the replacement is the {@link String32} to replace the target.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello, World!");
     *         System.out.println(string32.replace(String32.valueOf("World"), String32.valueOf("Universe")));
     *         // Output: "Hello, Universe!"
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is replaced with the given target and replacement.
     * The target <code>World</code> is replaced with the replacement <code>Universe</code>.
     * The output will be <code>Hello, Universe!</code>.
     * </p>
     *
     * @param target      The {@link String32} to be replaced.
     * @param replacement The {@link String32} to replace the target.
     * @return The new <code>String32</code> object that is replaced with the given target and replacement.
     * @throws NullPointerException If the target or replacement is <code>null</code>.
     */
    public String32 replace(final String32 target, final String32 replacement) {
        Objects.requireNonNull(target, "Target cannot be null");
        Objects.requireNonNull(replacement, "Replacement cannot be null");
        return String32.valueOf(toString().replace(target.toString(), replacement.toString()));
    }

    /**
     * Returns a new <code>String32</code> object that is replaced with all occurrences of the given delimiter and replacement.
     * <p>
     * This method returns a new <code>String32</code> object that is replaced with all occurrences of the given delimiter and replacement.
     * The delimiter is the {@link String32} to be replaced, and the replacement is the {@link String32} to replace the delimiter.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello, World! World!");
     *         System.out.println(string32.replaceAll(String32.valueOf("World"), String32.valueOf("Universe")));
     *         // Output: "Hello, Universe! Universe!"
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is replaced with all occurrences of the given delimiter and replacement.
     * The delimiter <code>World</code> is replaced with the replacement <code>Universe</code>.
     * The output will be <code>Hello, Universe! Universe!</code>.
     * </p>
     *
     * @param delimiter   The {@link String32} to be replaced.
     * @param replacement The {@link String32} to replace the delimiter.
     * @return The new <code>String32</code> object that is replaced with all occurrences of the given delimiter and replacement.
     * @throws NullPointerException If the delimiter or replacement is <code>null</code>.
     */
    public String32 replaceAll(final String32 delimiter, final String32 replacement) {
        Objects.requireNonNull(delimiter, "Delimiter cannot be null");
        Objects.requireNonNull(replacement, "Replacement cannot be null");
        return String32.valueOf(toString().replaceAll(delimiter.toString(), replacement.toString()));
    }

    /**
     * Returns a new <code>String32</code> object that is replaced with all occurrences of the given regex and replacement.
     * <p>
     * This method returns a new <code>String32</code> object that is replaced with all occurrences of the given regex and replacement.
     * The regex is the {@link Pattern} to be replaced, and the replacement is the {@link String32} to replace the regex.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello, World! World!");
     *         System.out.println(string32.replaceAll(Pattern.compile("World"), String32.valueOf("Universe")));
     *         // Output: "Hello, Universe! Universe!"
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is replaced with all occurrences of the given regex and replacement.
     * The regex <code>World</code> is replaced with the replacement <code>Universe</code>.
     * The output will be <code>Hello, Universe! Universe!</code>.
     * </p>
     *
     * @param regex       The {@link Pattern} to be replaced.
     * @param replacement The {@link String32} to replace the regex.
     * @return The new <code>String32</code> object that is replaced with all occurrences of the given regex and replacement.
     * @throws NullPointerException If the regex or replacement is <code>null</code>.
     */
    public String32 replaceAll(final Pattern regex, final String32 replacement) {
        Objects.requireNonNull(regex, "Regex cannot be null");
        Objects.requireNonNull(replacement, "Replacement cannot be null");
        return String32.valueOf(regex.matcher(toString()).replaceAll(replacement.toString()));
    }

    /**
     * Returns a new <code>String32</code> object that is replaced with the first occurrence of the given regex and replacement.
     * <p>
     * This method returns a new <code>String32</code> object that is replaced with the first occurrence of the given regex and replacement.
     * The regex is the {@link String32} to be replaced, and the replacement is the {@link String32} to replace the regex.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello, World!");
     *         System.out.println(string32.replaceFirst(String32.valueOf("World"), String32.valueOf("Universe")));
     *         // Output: "Hello, Universe!"
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is replaced with the first occurrence of the given regex and replacement.
     * The regex <code>World</code> is replaced with the replacement <code>Universe</code>.
     * The output will be <code>Hello, Universe!</code>.
     * </p>
     *
     * @param regex       The {@link String32} to be replaced.
     * @param replacement The {@link String32} to replace the regex.
     * @return The new <code>String32</code> object that is replaced with the first occurrence of the given regex and replacement.
     * @throws NullPointerException If the regex or replacement is <code>null</code>.
     */
    public String32 replaceFirst(final String32 regex, final String32 replacement) {
        Objects.requireNonNull(regex, "Regex cannot be null");
        Objects.requireNonNull(replacement, "Replacement cannot be null");
        return String32.valueOf(toString().replaceFirst(regex.toString(), replacement.toString()));
    }

    /**
     * Returns a new <code>String32</code> object that is converted to lower case.
     * <p>
     * This method returns a new <code>String32</code> object that is converted to lower case.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello, World!");
     *         System.out.println(string32.toLowerCase());
     *         // Output: "hello, world!"
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is converted to lower case.
     * The output will be <code>hello, world!</code>.
     * </p>
     *
     * @return The new <code>String32</code> object that is converted to lower case.
     */
    public String32 toLowerCase() {
        return String32.valueOf(toString().toLowerCase());
    }

    /**
     * Returns a new <code>String32</code> object that is converted to upper case.
     * <p>
     * This method returns a new <code>String32</code> object that is converted to upper case.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello, World!");
     *         System.out.println(string32.toUpperCase());
     *         // Output: "HELLO, WORLD!"
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is converted to upper case.
     * The output will be <code>HELLO, WORLD!</code>.
     * </p>
     *
     * @return The new <code>String32</code> object that is converted to upper case.
     */
    public String32 toUpperCase() {
        return String32.valueOf(toString().toUpperCase());
    }

    /**
     * Returns a new <code>String32</code> object that is trimmed.
     * <p>
     * This method returns a new <code>String32</code> object that is trimmed.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("  Hello, World!  ");
     *         System.out.println(string32.trim());
     *         // Output: "Hello, World!"
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is trimmed.
     * The output will be <code>Hello, World!</code>.
     * </p>
     *
     * @return The new <code>String32</code> object that is trimmed.
     */
    public String32 trim() {
        return String32.valueOf(toString().trim());
    }

    /**
     * Returns a new <code>String32</code> object that is concatenated with the given <code>String32</code> object.
     * <p>
     * This method returns a new <code>String32</code> object that is concatenated with the given <code>String32</code> object.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello").concat(String32.valueOf(", World!"));
     *         // Output: "Hello, World!"
     *         System.out.println(string32.toString());
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is concatenated with the given <code>String32</code> object.
     * The output will be <code>Hello, World!</code>.
     * </p>
     *
     * @param str The <code>String32</code> object to concatenate with.
     * @return The new <code>String32</code> object that is concatenated with the given <code>String32</code> object.
     * @throws NullPointerException If the <code>String32</code> object is <code>null</code>.
     */
    public String32 concat(final String32 str) {
        Objects.requireNonNull(str, "String32 to concatenate cannot be null");
        return String32.valueOf(toString().concat(str.toString()));
    }

    /**
     * Returns <code>true</code> if the <code>String32</code> object contains the given <code>String32</code> object.
     * <p>
     * This method returns <code>true</code> if the <code>String32</code> object contains the given <code>String32</code> object.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello, World!");
     *         System.out.println(string32.contains(String32.valueOf("World")));
     *         // Output: true
     * </pre></blockquote>
     * This will return <code>true</code> if the <code>String32</code> object contains the given <code>String32</code> object.
     * </p>
     *
     * @param str The <code>String32</code> object to check if it is contained.
     * @return <code>true</code> if the <code>String32</code> object contains the given <code>String32</code> object, <code>false</code> otherwise.
     * @throws NullPointerException If the <code>String32</code> object is <code>null</code>.
     */
    public boolean contains(final String32 str) {
        Objects.requireNonNull(str, "String32 to check cannot be null");
        return toString().contains(str.toString());
    }

    /**
     * Returns <code>true</code> if the <code>String32</code> object ends with the given <code>String32</code> object.
     * <p>
     * This method returns <code>true</code> if the <code>String32</code> object ends with the given <code>String32</code> object.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello, World!");
     *         System.out.println(string32.endsWith(String32.valueOf("World!")));
     *         // Output: true
     * </pre></blockquote>
     * This will return <code>true</code> if the <code>String32</code> object ends with the given <code>String32</code> object.
     * </p>
     *
     * @param suffix The <code>String32</code> object to check if it is the suffix.
     * @return <code>true</code> if the <code>String32</code> object ends with the given <code>String32</code> object, <code>false</code> otherwise.
     * @throws NullPointerException If the <code>String32</code> object is <code>null</code>.
     */
    public boolean endsWith(final String32 suffix) {
        Objects.requireNonNull(suffix, "Suffix cannot be null");
        return toString().endsWith(suffix.toString());
    }

    /**
     * Returns <code>true</code> if the <code>String32</code> object starts with the given <code>String32</code> object.
     * <p>
     * This method returns <code>true</code> if the <code>String32</code> object starts with the given <code>String32</code> object.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello, World!");
     *         System.out.println(string32.startsWith(String32.valueOf("Hello")));
     *         // Output: true
     * </pre></blockquote>
     * This will return <code>true</code> if the <code>String32</code> object starts with the given <code>String32</code> object.
     * </p>
     *
     * @param prefix The <code>String32</code> object to check if it is the prefix.
     * @return <code>true</code> if the <code>String32</code> object starts with the given <code>String32</code> object, <code>false</code> otherwise.
     * @throws NullPointerException If the <code>String32</code> object is <code>null</code>.
     */
    public boolean startsWith(final String32 prefix) {
        Objects.requireNonNull(prefix, "Prefix cannot be null");
        return toString().startsWith(prefix.toString());
    }

    /**
     * Returns <code>true</code> if the <code>String32</code> object is empty.
     * <p>
     * This method returns <code>true</code> if the <code>String32</code> object is empty.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("");
     *         System.out.println(string32.isEmpty());
     *         // Output: true
     * </pre></blockquote>
     * This will return <code>true</code> if the <code>String32</code> object is empty.
     * </p>
     *
     * @return <code>true</code> if the <code>String32</code> object is empty, <code>false</code> otherwise.
     */
    public boolean isEmpty() {
        return toString().isEmpty();
    }

    /**
     * Returns <code>true</code> if the <code>String32</code> object matches the given regex.
     * <p>
     * This method returns <code>true</code> if the <code>String32</code> object matches the given regex.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello, World!");
     *         System.out.println(string32.matches(String32.valueOf("Hello, World!")));
     *         // Output: true
     * </pre></blockquote>
     * This will return <code>true</code> if the <code>String32</code> object matches the given regex.
     * </p>
     *
     * @param regex The regex to match.
     * @return <code>true</code> if the <code>String32</code> object matches the given regex, <code>false</code> otherwise.
     * @throws NullPointerException If the regex is <code>null</code>.
     */
    public boolean matches(final String32 regex) {
        Objects.requireNonNull(regex, "Regex cannot be null");
        return toString().matches(regex.toString());
    }

    /**
     * Returns a new <code>String32</code> object that is reversed.
     * <p>
     * This method returns a new <code>String32</code> object that is reversed.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello, World!");
     *         System.out.println(string32.reverse());
     *         // Output: "!dlroW ,olleH"
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is reversed.
     * The output will be <code>!dlroW ,olleH</code>.
     * </p>
     *
     * @return The new <code>String32</code> object that is reversed.
     */
    public String32 reverse() {
        int[] reversedChars = new int[this.length];
        for (int i = 0; i < this.length; i++) {
            reversedChars[i] = this.characters[length - 1 - i];
        }
        return new String32(new String(reversedChars, 0, reversedChars.length));
    }

    /**
     * Returns the index within this <code>String32</code> object of the first occurrence of the specified character.
     * <p>
     * This method returns the index within this <code>String32</code> object of the first occurrence of the specified character.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello, World!");
     *         System.out.println(string32.indexOf('o'));
     *         // Output: 4
     * </pre></blockquote>
     * This will return the index within this <code>String32</code> object of the first occurrence of the specified character.
     * The character <code>o</code> is at the index <code>4</code>.
     * </p>
     *
     * @param codePoint The character to search for.
     * @return The index of the first occurrence of the specified character, or <code>-1</code> if the character is not found.
     */
    public int indexOf(final int codePoint) {
        for (int i = 0; i < this.length; i++) {
            if (this.characters[i] == codePoint) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns the index within this <code>String32</code> object of the last occurrence of the specified character.
     * <p>
     * This method returns the index within this <code>String32</code> object of the last occurrence of the specified character.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello, World!");
     *         System.out.println(string32.lastIndexOf('o'));
     *         // Output: 8
     * </pre></blockquote>
     * This will return the index within this <code>String32</code> object of the last occurrence of the specified character.
     * The character <code>o</code> is at the index <code>8</code>.
     * </p>
     *
     * @param codePoint The character to search for.
     * @return The index of the last occurrence of the specified character, or <code>-1</code> if the character is not found.
     */
    public int lastIndexOf(final int codePoint) {
        for (int i = this.length - 1; i >= 0; i--) {
            if (this.characters[i] == codePoint) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns a new array of <code>String32</code> objects that is split by the given literal.
     * <p>
     * This method returns a new array of <code>String32</code> objects that is split by the given literal.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello, World!");
     *         String32[] split = string32.splitLiteral(String32.valueOf(", "));
     *         // Output: ["Hello", "World!"]
     *         System.out.println(Arrays.toString(split));
     * </pre></blockquote>
     * This will return a new array of <code>String32</code> objects that is split by the given literal.
     * The output will be <code>["Hello", "World!"]</code>.
     * </p>
     *
     * @param delimiter The literal to split by.
     * @return The array of new <code>String32</code> objects that is split by the given literal.
     * @throws NullPointerException If the delimiter is null.
     */
    public String32[] splitLiteral(final String32 delimiter) {
        Objects.requireNonNull(delimiter, "Delimiter cannot be null");
        String[] splitStrings = toString().split(Pattern.quote(delimiter.toString()));
        String32[] result = new String32[splitStrings.length];
        for (int i = 0; i < splitStrings.length; i++) {
            result[i] = new String32(splitStrings[i]);
        }
        return result;
    }

    /**
     * Returns a new array of <code>String32</code> objects that is split by the given regex.
     * <p>
     * This method returns a new array of <code>String32</code> objects that is split by the given regex.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello, World!");
     *         String32[] split = string32.split(Pattern.compile(", "));
     *         // Output: ["Hello", "World!"]
     *         System.out.println(Arrays.toString(split));
     * </pre></blockquote>
     * This will return a new array of <code>String32</code> objects that is split by the given regex.
     * The output will be <code>["Hello", "World!"]</code>.
     * </p>
     *
     * @param regex The regex to split by.
     * @return The array of new <code>String32</code> objects that is split by the given regex.
     * @throws NullPointerException If the regex is null.
     */
    public String32[] split(final Pattern regex) {
        Objects.requireNonNull(regex, "Regex cannot be null");
        String[] splitStrings = regex.split(toString());
        String32[] result = new String32[splitStrings.length];
        for (int i = 0; i < splitStrings.length; i++) {
            result[i] = new String32(splitStrings[i]);
        }
        return result;
    }

    /**
     * Returns a new array of <code>String32</code> objects that is split by the given literal with a limit on the number of splits.
     * <p>
     * This method splits this <code>String32</code> instance into substrings around matches of the specified
     * <b>literal delimiter</b>.
     * The split is performed literally (no regular expressions are interpreted).
     * </p>
     *
     * <p><b>Example:</b></p>
     * <blockquote><pre>
     * String32 string32 = String32.valueOf("Hello, World, Again!");
     * String32[] split = string32.splitLiteral(String32.valueOf(", "), 2);
     * // Output: ["Hello", "World, Again!"]
     * System.out.println(Arrays.toString(split));
     * </pre></blockquote>
     *
     * @param delimiter The literal sequence to split by. Must not be {@code null}.
     * @param limit     The maximum number of resulting substrings:
     *                  <ul>
     *                      <li>If <b>limit &gt; 0</b>: at most {@code limit} substrings are returned.</li>
     *                      <li>If <b>limit == 0</b>: a single-element array containing this String32 instance is returned.</li>
     *                      <li>If <b>limit &lt; 0</b>: the behavior is the same as {@link String#split(String, int)}.</li>
     *                  </ul>
     *
     * @return An array of <code>String32</code> objects, split using the given literal delimiter.
     *
     * @throws NullPointerException If the delimiter is {@code null}.
     */
    public String32[] splitLiteral(final String32 delimiter, final int limit) {
        Objects.requireNonNull(delimiter, "Delimiter cannot be null");

        if (limit == 0) {
            return new String32[]{this};
        }

        String[] splitStrings = toString().split(Pattern.quote(delimiter.toString()), limit);
        String32[] result = new String32[splitStrings.length];
        for (int i = 0; i < splitStrings.length; i++) {
            result[i] = new String32(splitStrings[i]);
        }
        return result;
    }

    /**
     * Returns a new array of <code>String32</code> objects that is split by the given regular expression with a limit on the number of splits.
     * <p>
     * This method splits this <code>String32</code> instance into substrings around matches of the specified
     * <b>regular expression</b>.
     * Unlike {@link #splitLiteral(String32, int)}, the pattern is treated as a full regex.
     * </p>
     *
     * <p><b>Example:</b></p>
     * <blockquote><pre>
     * String32 string32 = String32.valueOf("Hello,   World,   Again!");
     * // Split on one or more whitespace characters
     * String32[] split = string32.split(String32.valueOf("\\s+"), 3);
     * // Output: ["Hello,", "World,", "Again!"]
     * System.out.println(Arrays.toString(split));
     * </pre></blockquote>
     *
     * @param regex The regular expression to split by. Must not be {@code null}.
     * @param limit The maximum number of resulting substrings:
     *              <ul>
     *                  <li>If <b>limit &gt; 0</b>: at most {@code limit} substrings are returned.</li>
     *                  <li>If <b>limit == 0</b>: a single-element array containing this String32 instance is returned.</li>
     *                  <li>If <b>limit &lt; 0</b>: the behavior is the same as {@link String#split(String, int)}.</li>
     *              </ul>
     *
     * @return An array of <code>String32</code> objects, split using the given regular expression.
     *
     * @throws NullPointerException If the regex is {@code null}.
     */
    public String32[] split(final String32 regex, final int limit) {
        Objects.requireNonNull(regex, "Regex cannot be null");

        if (limit == 0) {
            return new String32[]{this};
        }

        String[] splitStrings = toString().split(regex.toString(), limit);
        String32[] result = new String32[splitStrings.length];
        for (int i = 0; i < splitStrings.length; i++) {
            result[i] = new String32(splitStrings[i]);
        }
        return result;
    }

    /**
     * Returns <code>true</code> if the <code>String32</code> object is equal to the given <code>String32</code> object, ignoring case considerations.
     * <p>
     * This method returns <code>true</code> if the <code>String32</code> object is equal to the given <code>String32</code> object, ignoring case considerations.
     * </p>
     *
     * @param other The <code>String32</code> object to compare with.
     * @return <code>true</code> if the <code>String32</code> object is equal to the given <code>String32</code> object, ignoring case considerations, <code>false</code> otherwise.
     */
    public boolean equalsIgnoreCase(final String32 other) {
        return other != null && toString().equalsIgnoreCase(other.toString());
    }

    /**
     * Returns a new <code>String32</code> object that is replaced with the given target and replacement, ignoring case considerations.
     * <p>
     * This method returns a new <code>String32</code> object that is replaced with the given target and replacement, ignoring case considerations.
     * The target is the {@link String32} to be replaced, and the replacement is the {@link String32} to replace the target.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello, World!");
     *         System.out.println(string32.replaceIgnoreCase(String32.valueOf("world"), String32.valueOf("Universe")));
     *         // Output: "Hello, Universe!"
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is replaced with the given target and replacement, ignoring case considerations.
     * The target <code>World</code> is replaced with the replacement <code>Universe</code>.
     * The output will be <code>Hello, Universe!</code>.
     * </p>
     *
     * @param target      The {@link String32} to be replaced.
     * @param replacement The {@link String32} to replace the target.
     * @return The new <code>String32</code> object that is replaced with the given target and replacement, ignoring case considerations.
     * @throws NullPointerException If the target or replacement is <code>null</code>.
     */
    public String32 replaceIgnoreCase(final String32 target, final String32 replacement) {
        Objects.requireNonNull(target, "Target cannot be null");
        Objects.requireNonNull(replacement, "Replacement cannot be null");
        return String32.valueOf(toString().replaceAll("(?i)" + Pattern.quote(target.toString()), replacement.toString()));
    }

    /**
     * Returns a new <code>String32</code> object that is padded left with the given total length and pad character.
     * <p>
     * This method returns a new <code>String32</code> object that is padded left with the given total length and pad character.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello");
     *         System.out.println(string32.padLeft(10, ' '));
     *         // Output: "     Hello"
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is padded left with the given total length and pad character.
     * The output will be <code>     Hello</code>.
     * </p>
     *
     * @param totalLength The total length of the new <code>String32</code> object.
     * @param padChar     The character to pad the new <code>String32</code> object with.
     * @return The new <code>String32</code> object that is padded left with the given total length and pad character.
     */
    public String32 padLeft(final int totalLength, final char padChar) {
        if (totalLength <= this.length) {
            return this;
        }
        char[] padding = new char[totalLength - this.length];
        Arrays.fill(padding, padChar);
        return new String32(new String(padding) + this);
    }

    /**
     * Returns a new <code>String32</code> object that is padded left with the given total length and pad code point.
     * <p>
     * This method returns a new <code>String32</code> object that is padded left with the given total length and pad code point.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello");
     *         System.out.println(string32.padLeft(10, ' '));
     *         // Output: "     Hello"
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is padded left with the given total length and pad code point.
     * The output will be <code>     Hello</code>.
     * </p>
     *
     * @param totalLength  The total length of the new <code>String32</code> object.
     * @param padCodePoint The code point to pad the new <code>String32</code> object with.
     * @return The new <code>String32</code> object that is padded left with the given total length and pad code point.
     */
    public String32 padLeft(final int totalLength, final int padCodePoint) {
        if (totalLength <= this.length) {
            return this;
        }
        String padStr = new String(new int[]{padCodePoint}, 0, 1);
        return new String32(padStr.repeat(Math.max(0, totalLength - this.length)) + this);
    }

    /**
     * Returns a new <code>String32</code> object that is padded right with the given total length and pad character.
     * <p>
     * This method returns a new <code>String32</code> object that is padded right with the given total length and pad character.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello");
     *         System.out.println(string32.padRight(10, ' '));
     *         // Output: "Hello     "
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is padded right with the given total length and pad character.
     * The output will be <code>Hello     </code>.
     * </p>
     *
     * @param totalLength The total length of the new <code>String32</code> object.
     * @param padChar     The character to pad the new <code>String32</code> object with.
     * @return The new <code>String32</code> object that is padded right with the given total length and pad character.
     */
    public String32 padRight(final int totalLength, final char padChar) {
        if (totalLength <= this.length) {
            return this;
        }
        char[] padding = new char[totalLength - this.length];
        Arrays.fill(padding, padChar);
        return new String32(this + new String(padding));
    }

    /**
     * Returns a new <code>String32</code> object that is padded right with the given total length and pad code point.
     * <p>
     * This method returns a new <code>String32</code> object that is padded right with the given total length and pad code point.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello");
     *         System.out.println(string32.padRight(10, ' '));
     *         // Output: "Hello     "
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is padded right with the given total length and pad code point.
     * The output will be <code>Hello     </code>.
     * </p>
     *
     * @param totalLength  The total length of the new <code>String32</code> object.
     * @param padCodePoint The code point to pad the new <code>String32</code> object with.
     * @return The new <code>String32</code> object that is padded right with the given total length and pad code point.
     */
    public String32 padRight(final int totalLength, final int padCodePoint) {
        if (totalLength <= this.length) {
            return this;
        }
        String padStr = new String(new int[]{padCodePoint}, 0, 1);
        return new String32(this + padStr.repeat(Math.max(0, totalLength - this.length)));
    }

    /**
     * Returns a new <code>String32</code> object that is padded center with the given total length and pad character.
     * <p>
     * This method returns a new <code>String32</code> object that is padded center with the given total length and pad character.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello");
     *         System.out.println(string32.padCenter(10, ' '));
     *         // Output: "  Hello   "
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is padded center with the given total length and pad character.
     * The output will be <code>  Hello   </code>.
     * </p>
     *
     * @param totalLength The total length of the new <code>String32</code> object.
     * @param padChar     The character to pad the new <code>String32</code> object with.
     * @return The new <code>String32</code> object that is padded center with the given total length and pad character.
     */
    public String32 padCenter(final int totalLength, final char padChar) {
        if (totalLength <= this.length) {
            return this;
        }
        int totalPadding = totalLength - this.length;
        int paddingLeft = totalPadding / 2;
        int paddingRight = totalPadding - paddingLeft;
        char[] leftPadding = new char[paddingLeft];
        char[] rightPadding = new char[paddingRight];
        Arrays.fill(leftPadding, padChar);
        Arrays.fill(rightPadding, padChar);
        return new String32(new String(leftPadding) + this + new String(rightPadding));
    }

    /**
     * Returns a new <code>String32</code> object that is padded center with the given total length and pad code point.
     * <p>
     * This method returns a new <code>String32</code> object that is padded center with the given total length and pad code point.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello");
     *         System.out.println(string32.padCenter(10, ' '));
     *         // Output: "  Hello   "
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is padded center with the given total length and pad code point.
     * The output will be <code>  Hello   </code>.
     * </p>
     *
     * @param totalLength  The total length of the new <code>String32</code> object.
     * @param padCodePoint The code point to pad the new <code>String32</code> object with.
     * @return The new <code>String32</code> object that is padded center with the given total length and pad code point.
     */
    public String32 padCenter(final int totalLength, final int padCodePoint) {
        if (totalLength <= this.length) {
            return this;
        }
        int totalPadding = totalLength - this.length;
        int paddingLeft = totalPadding / 2;
        int paddingRight = totalPadding - paddingLeft;
        String padStr = new String(new int[]{padCodePoint}, 0, 1);
        return new String32(padStr.repeat(Math.max(0, paddingLeft)) + this + padStr.repeat(Math.max(0, paddingRight)));
    }

    /**
     * Returns a new <code>String32</code> object that is removed with the given code point.
     * <p>
     * This method returns a new <code>String32</code> object that is removed with the given code point.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello");
     *         System.out.println(string32.remove('o'));
     *         // Output: "Hell"
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is removed with the given code point.
     * The output will be <code>Hell</code>.
     * </p>
     *
     * @param codePoint The code point to remove.
     * @return The new <code>String32</code> object that is removed with the given code point.
     */
    public String32 remove(final int codePoint) {
        return String32.valueOf(toString().replace(new String(new int[]{codePoint}, 0, 1), ""));
    }

    /**
     * Returns a new <code>String32</code> object that is repeated the given number of times.
     * <p>
     * This method returns a new <code>String32</code> object that is repeated the given number of times.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello");
     *         System.out.println(string32.repeat(3));
     *         // Output: "HelloHelloHello"
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is repeated the given number of times.
     * The output will be <code>HelloHelloHello</code>.
     * </p>
     *
     * @param times The number of times to repeat the <code>String32</code> object.
     * @return The new <code>String32</code> object that is repeated the given number of times.
     */
    public String32 repeat(final int times) {
        if (times <= 0) {
            return String32.valueOf("");
        }
        return new String32(String.valueOf(this).repeat(times));
    }

    /**
     * Returns a new <code>String32</code> object that is removed all occurrences of the given code point.
     * <p>
     * This method returns a new <code>String32</code> object that is removed all occurrences of the given code point.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello, World!");
     *         System.out.println(string32.removeAll('o'));
     *         // Output: "Hell, Wrld!"
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is removed all occurrences of the given code point.
     * The output will be <code>Hell, Wrld!</code>.
     * </p>
     *
     * @param codePoint The code point to remove all occurrences of.
     * @return The new <code>String32</code> object that is removed all occurrences of the given code point.
     */
    public String32 removeAll(final int codePoint) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.length; i++) {
            if (this.characters[i] != codePoint) {
                sb.appendCodePoint(this.characters[i]);
            }
        }
        return new String32(sb.toString());
    }

    /**
     * Returns a new <code>String32</code> object that is stripped by removing leading and trailing whitespace.
     * <p>
     * This method returns a new <code>String32</code> object that is stripped by removing leading and trailing whitespace.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("  Hello, World!  ");
     *         System.out.println(string32.strip());
     *         // Output: "Hello, World!"
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is stripped by removing leading and trailing whitespace.
     * The output will be <code>Hello, World!</code>.
     * </p>
     *
     * @return The new <code>String32</code> object that is stripped by removing leading and trailing whitespace.
     */
    public String32 strip() {
        return stripLeading().stripTrailing();
    }

    /**
     * Checks if a code point is considered blank (whitespace).
     * This includes standard whitespace characters and additional Unicode whitespace characters.
     *
     * @param codePoint The code point to check.
     * @return true if the code point is considered blank, false otherwise.
     */
    private static boolean isBlankCp(final int codePoint) {
        // Check for standard whitespace characters
        if (Character.isWhitespace(codePoint)) {
            return true;
        }
        // Check for additional Unicode whitespace characters
        return codePoint == 0x00A0 || // NO-BREAK SPACE
                codePoint == 0x1680 || // OGHAM SPACE MARK
                (codePoint >= 0x2000 && codePoint <= 0x200A) || // EN QUAD to HAIR SPACE
                codePoint == 0x202F || // NARROW NO-BREAK SPACE
                codePoint == 0x205F || // MEDIUM MATHEMATICAL SPACE
                codePoint == 0x3000;   // IDEOGRAPHIC SPACE
    }

    /**
     * Returns a new <code>String32</code> object that is stripped leading.
     * <p>
     * This method returns a new <code>String32</code> object that is stripped leading.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("  Hello, World!  ");
     *         System.out.println(string32.stripLeading());
     *         // Output: "Hello, World!  "
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is stripped leading.
     * The output will be <code>Hello, World!  </code>.
     * </p>
     *
     * @return The new <code>String32</code> object that is stripped leading.
     */
    public String32 stripLeading() {
        int start = 0;
        while (start < this.length && (Character.isWhitespace(this.characters[start]) || isBlankCp(this.characters[start]))) {
            start++;
        }
        return new String32(new String(this.characters, start, this.length - start));
    }

    /**
     * Returns a new <code>String32</code> object that is stripped trailing.
     * <p>
     * This method returns a new <code>String32</code> object that is stripped trailing.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("  Hello, World!  ");
     *         System.out.println(string32.stripTrailing());
     *         // Output: "  Hello, World!"
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is stripped trailing.
     * The output will be <code>  Hello, World!</code>.
     * </p>
     *
     * @return The new <code>String32</code> object that is stripped trailing.
     */
    public String32 stripTrailing() {
        int end = this.length;
        while (end > 0 && (Character.isWhitespace(this.characters[end - 1]) || isBlankCp(this.characters[end - 1]))) {
            end--;
        }
        return new String32(new String(this.characters, 0, end));
    }

    /**
     * Returns a new <code>String32</code> object that is removed all whitespace characters.
     * <p>
     * This method returns a new <code>String32</code> object that is removed all whitespace characters.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("  Hello, World!  ");
     *         System.out.println(string32.removeWhitespace());
     *         // Output: "Hello,World!"
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is removed all whitespace characters.
     * The output will be <code>Hello,World!</code>.
     * </p>
     *
     * @return The new <code>String32</code> object that is removed all whitespace characters.
     */
    public String32 removeWhitespace() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.length; i++) {
            if (!(Character.isWhitespace(this.characters[i]) || isBlankCp(this.characters[i]))) {
                sb.appendCodePoint(this.characters[i]);
            }
        }
        return new String32(sb.toString());
    }

    /**
     * Returns a new <code>String32</code> object that is stripped.
     * <p>
     * This method returns a new <code>String32</code> object that is stripped.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("  Hello, World!  ");
     *         System.out.println(string32.strip());
     *         // Output: "Hello, World!"
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is stripped.
     * The output will be <code>Hello, World!</code>.
     * </p>
     *
     * @return The new <code>String32</code> object that is stripped.
     */
    public String32 capitalize() {
        if (this.length == 0) {
            return this;
        }
        int firstCodePoint = Character.toUpperCase(this.characters[0]);
        StringBuilder sb = new StringBuilder();
        sb.appendCodePoint(firstCodePoint);
        for (int i = 1; i < this.length; i++) {
            sb.appendCodePoint(this.characters[i]);
        }
        return new String32(sb.toString());
    }

    /**
     * Returns a new <code>String32</code> object that is decapitalized.
     * <p>
     * This method returns a new <code>String32</code> object that is decapitalized.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello, World!");
     *         System.out.println(string32.decapitalize());
     *         // Output: "hello, World!"
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is decapitalized.
     * The output will be <code>hello, World!</code>.
     * </p>
     *
     * @return The new <code>String32</code> object that is decapitalized.
     */
    public String32 decapitalize() {
        if (this.length == 0) {
            return this;
        }
        int firstCodePoint = Character.toLowerCase(this.characters[0]);
        StringBuilder sb = new StringBuilder();
        sb.appendCodePoint(firstCodePoint);
        for (int i = 1; i < this.length; i++) {
            sb.appendCodePoint(this.characters[i]);
        }
        return new String32(sb.toString());
    }

    /**
     * Returns the <code>String32</code> object as an {@link Optional} object.
     * <p>
     * This method returns the <code>String32</code> object as an {@link Optional} object.
     * </p>
     *
     * @return The <code>String32</code> object as an {@link Optional} object.
     */
    public Optional<String32> toOptional() {
        return Optional.of(this);
    }

    /**
     * Returns the <code>String32</code> object as an {@link Optional} object if it is not empty.
     * <p>
     * This method returns the <code>String32</code> object as an {@link Optional} object if it is not empty.
     * </p>
     *
     * @return The <code>String32</code> object as an {@link Optional} object if it is not empty.
     * @deprecated Use {@link #ifPresent(Consumer)} instead.
     */
    @Deprecated
    public Optional<String32> toOptionalIfNotEmpty() {
        return isEmpty() ? Optional.empty() : Optional.of(this);
    }

    /**
     * Checks if the given code point is a surrogate.
     * <p>
     * A surrogate is a code point in the range 0xD800 to 0xDFFF.
     * </p>
     *
     * @param cp The code point to check.
     * @return <code>true</code> if the code point is a surrogate, <code>false</code> otherwise.
     */
    private static boolean isSurrogate(final int cp) {
        return cp >= 0xD800 && cp <= 0xDFFF;
    }

    /**
     * Sanitizes the given code point.
     * <p>
     * If the code point is less than 0 or greater than 0x10FFFF, or if it is a surrogate, it will be replaced with 0xFFFD.
     * </p>
     *
     * @param cp The code point to sanitize.
     * @return The sanitized code point.
     */
    private static int sanitizeCp(final int cp) {
        if (cp < 0 || cp > 0x10FFFF || isSurrogate(cp)) {
            return 0xFFFD;
        }
        return cp;
    }

    /**
     * Maps a unary operation over the <code>String32</code> object.
     * <p>
     * This method applies the given unary operation to each code point in the <code>String32</code> object.
     * The resulting code points are sanitized and used to create a new <code>String32</code> object.
     * </p>
     *
     * @param op The unary operation to apply.
     * @return A new <code>String32</code> object resulting from the unary operation.
     * @throws NullPointerException if the unary operation is null.
     */
    private String32 mapUnary(final IntUnaryOperator op) {
        Objects.requireNonNull(op, "op cannot be null");
        int[] out = new int[this.length];
        for (int i = 0; i < this.length; i++) {
            out[i] = sanitizeCp(op.applyAsInt(this.characters[i]));
        }
        return String32.valueOf(out);
    }


    /**
     * Maps a binary operation over the <code>String32</code> object and another <code>String32</code> object.
     * <p>
     * This method applies the given binary operation to each pair of code points from the two <code>String32</code> objects.
     * The resulting code points are sanitized and used to create a new <code>String32</code> object.
     * The length of the resulting <code>String32</code> object is the minimum of the lengths of the two input <code>String32</code> objects.
     * </p>
     *
     * @param other The other <code>String32</code> object to use in the binary operation.
     * @param op    The binary operation to apply.
     * @return A new <code>String32</code> object resulting from the binary operation.
     * @throws NullPointerException if the other <code>String32</code> object or the binary operation is null.
     */
    private String32 mapBinary(final String32 other, final IntBinaryOperator op) {
        Objects.requireNonNull(other, "other");
        Objects.requireNonNull(op, "op cannot be null");
        int len = Math.min(this.length, other.length);
        int[] out = new int[len];
        for (int i = 0; i < len; i++) {
            out[i] = sanitizeCp(op.applyAsInt(this.characters[i], other.characters[i]));
        }
        return String32.valueOf(out);
    }

    /**
     * Returns a new <code>String32</code> object that is bitwise AND with the given <code>String32</code> object.
     * <p>
     * This method returns a new <code>String32</code> object that is bitwise AND with the given <code>String32</code> object.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello");
     *         System.out.println(string32.bitwiseAnd(String32.valueOf("World")));
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is bitwise AND with the given <code>String32</code> object.
     * </p>
     *
     * @param other The <code>String32</code> object to bitwise AND with.
     * @return The new <code>String32</code> object that is bitwise AND with the given <code>String32</code> object.
     * @throws NullPointerException if the other <code>String32</code> object is null.
     */
    public String32 bitwiseAnd(final String32 other) {
        return mapBinary(other, (a, b) -> a & b);
    }

    /**
     * Returns a new <code>String32</code> object that is bitwise OR with the given <code>String32</code> object.
     * <p>
     * This method returns a new <code>String32</code> object that is bitwise OR with the given <code>String32</code> object.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello");
     *         System.out.println(string32.bitwiseOr(String32.valueOf("World")));
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is bitwise OR with the given <code>String32</code> object.
     * </p>
     *
     * @param other The <code>String32</code> object to bitwise OR with.
     * @return The new <code>String32</code> object that is bitwise OR with the given <code>String32</code> object.
     * @throws NullPointerException if the other <code>String32</code> object is null.
     */
    public String32 bitwiseOr(final String32 other) {
        return mapBinary(other, (a, b) -> a | b);
    }

    /**
     * Returns a new <code>String32</code> object that is bitwise XOR with the given <code>String32</code> object.
     * <p>
     * This method returns a new <code>String32</code> object that is bitwise XOR with the given <code>String32</code> object.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello");
     *         System.out.println(string32.bitwiseXor(String32.valueOf("World")));
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is bitwise XOR with the given <code>String32</code> object.
     * </p>
     *
     * @param other The <code>String32</code> object to bitwise XOR with.
     * @return The new <code>String32</code> object that is bitwise XOR with the given <code>String32</code> object.
     * @throws NullPointerException if the other <code>String32</code> object is null.
     */
    public String32 bitwiseXor(final String32 other) {
        return mapBinary(other, (a, b) -> a ^ b);
    }

    /**
     * Returns a new <code>String32</code> object that is bitwise NOT.
     * <p>
     * This method returns a new <code>String32</code> object that is bitwise NOT.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello");
     *         System.out.println(string32.bitwiseNot());
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is bitwise NOT.
     * </p>
     *
     * @return The new <code>String32</code> object that is bitwise NOT.
     */
    public String32 bitwiseNot() {
        return mapUnary(a -> ~a);
    }

    /**
     * Returns a new <code>String32</code> object that is left shifted by the given number of bits.
     * <p>
     * This method returns a new <code>String32</code> object that is left shifted by the given number of bits.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello");
     *         System.out.println(string32.leftShift(1));
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is left shifted by the given number of bits.
     * </p>
     *
     * @param n The number of bits to shift.
     * @return The new <code>String32</code> object that is left shifted by the given number of bits.
     */
    public String32 leftShift(final int n) {
        return mapUnary(a -> a << n);
    }

    /**
     * Returns a new <code>String32</code> object that is right shifted by the given number of bits.
     * <p>
     * This method returns a new <code>String32</code> object that is right shifted by the given number of bits.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello");
     *         System.out.println(string32.rightShift(1));
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is right shifted by the given number of bits.
     * </p>
     *
     * @param n The number of bits to shift.
     * @return The new <code>String32</code> object that is right shifted by the given number of bits.
     */
    public String32 rightShift(final int n) {
        return mapUnary(a -> a >> n);
    }

    /**
     * Returns a new <code>String32</code> object that is unsigned right shifted by the given number of bits.
     * <p>
     * This method returns a new <code>String32</code> object that is unsigned right shifted by the given number of bits.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello");
     *         System.out.println(string32.rightShiftUnsigned(1));
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is unsigned right shifted by the given number of bits.
     * </p>
     *
     * @param n The number of bits to shift.
     * @return The new <code>String32</code> object that is unsigned right shifted by the given number of bits.
     */
    public String32 rightShiftUnsigned(final int n) {
        return mapUnary(a -> a >>> n);
    }

    /**
     * Returns <code>true</code> if the <code>String32</code> object is a palindrome.
     * <p>
     * This method returns <code>true</code> if the <code>String32</code> object is a palindrome.
     * A palindrome is a word, phrase, number, or other sequence of characters that reads the same forward and backward.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("racecar");
     *         System.out.println(string32.isPalindrome());
     *         // Output: true
     *         string32 = String32.valueOf("hello");
     *         System.out.println(string32.isPalindrome());
     *         // Output: false
     *         string32 = String32.valueOf("madam");
     *         System.out.println(string32.isPalindrome());
     *         // Output: true
     *         string32 = String32.valueOf("12321");
     *         System.out.println(string32.isPalindrome());
     *         // Output: true
     *         </pre></blockquote>
     * This will return <code>true</code> if the <code>String32</code> object is a palindrome.
     * The output will be <code>true</code> for <code>racecar</code>, <code>madam</code>, and <code>12321</code>.
     * The output will be <code>false</code> for <code>hello</code>.
     * </p>
     *
     * @return <code>true</code> if the <code>String32</code> object is a palindrome, <code>false</code> otherwise.
     * @see <a href="https://en.wikipedia.org/wiki/Palindrome">Palindrome - Wikipedia</a>
     */
    public boolean isPalindrome() {
        for (int i = 0; i < this.length / 2; i++) {
            if (this.characters[i] != this.characters[this.length - 1 - i]) {
                return false;
            }
        }
        return true;
    }

    // shuffle, ngrams, invertCase, countOccurrences, distinct, replaceMultiple, sortCharacters

    /**
     * Returns a new <code>String32</code> object that is shuffled.
     * <p>
     * This method returns a new <code>String32</code> object that is shuffled.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello, World!");
     *         System.out.println(string32.shuffle());
     *         // Output: "W,odHrll eoel!"
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is shuffled.
     * The output will be something like <code>W,odHrll eoel!</code>.
     * </p>
     *
     * @return The new <code>String32</code> object that is shuffled.
     */
    public String32 shuffle() {
        int[] shuffled = Arrays.copyOf(this.characters, this.length);
        for (int i = this.length - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            int temp = shuffled[i];
            shuffled[i] = shuffled[j];
            shuffled[j] = temp;
        }
        return new String32(new String(shuffled, 0, shuffled.length));
    }

    /**
     * Returns an array of <code>String32</code> objects that are n-grams.
     * <p>
     * This method returns an array of <code>String32</code> objects that are n-grams.
     * An n-gram is a contiguous sequence of n items from a given sample of text or speech.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello, World!");
     *         System.out.println(Arrays.toString(string32.ngrams(2)));
     *         // Output: ["He", "el", "ll", "lo", "o,", ", ", " W", "Wo", "or", "rl", "ld", "d!"]
     * </pre></blockquote>
     * This will return an array of <code>String32</code> objects that are n-grams.
     * The output will be <code>["He", "el", "ll", "lo", "o,", ", ", " W", "Wo", "or", "rl", "ld", "d!"]</code>.
     * </p>
     *
     * @param n The number of items in the n-gram.
     * @return An array of <code>String32</code> objects that are n-grams.
     * @throws IllegalArgumentException If n is less than one or greater than the length of the string.
     */
    public String32[] ngrams(final int n) {
        if (n <= 0 || n > this.length) {
            throw new IllegalArgumentException("n must be between 1 and the length of the string");
        }
        String32[] ngrams = new String32[this.length - n + 1];
        for (int i = 0; i <= this.length - n; i++) {
            ngrams[i] = substring(i, i + n);
        }
        return ngrams;
    }

    /**
     * Returns a new <code>String32</code> object that is inverted case.
     * <p>
     * This method returns a new <code>String32</code> object that is inverted case.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello, World!");
     *         System.out.println(string32.invertCase());
     *         // Output: "hELLO, wORLD!"
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is inverted case.
     * The output will be <code>hELLO, wORLD!</code>.
     * </p>
     *
     * @return The new <code>String32</code> object that is inverted case.
     */
    public String32 invertCase() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.length; i++) {
            int codePoint = this.characters[i];
            if (Character.isUpperCase(codePoint)) {
                sb.appendCodePoint(Character.toLowerCase(codePoint));
            } else if (Character.isLowerCase(codePoint)) {
                sb.appendCodePoint(Character.toUpperCase(codePoint));
            } else {
                sb.appendCodePoint(codePoint);
            }
        }
        return new String32(sb.toString());
    }

    /**
     * Returns the number of occurrences of the given <code>String32</code> object.
     * <p>
     * This method returns the number of occurrences of the given <code>String32</code> object.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello, World!");
     *         System.out.println(string32.countOccurrences(String32.valueOf("o")));
     *         // Output: 2
     * </pre></blockquote>
     * This will return the number of occurrences of the given <code>String32</code> object.
     * The output will be <code>2</code>.
     * </p>
     *
     * @param substring The <code>String32</code> object to count the occurrences of.
     * @return The number of occurrences of the given <code>String32</code> object.
     * @apiNote If the given <code>String32</code> object is empty or null, this method will return 0.
     */
    public int countOccurrences(final String32 substring) {
        if (substring == null || substring.isEmpty()) {
            return 0;
        }
        String str = toString();
        String sub = substring.toString();
        int count = 0;
        int idx = 0;
        while ((idx = str.indexOf(sub, idx)) != -1) {
            count++;
            idx += sub.length();
        }
        return count;
    }

    /**
     * Returns a new <code>String32</code> object that is distinct.
     * <p>
     * This method returns a new <code>String32</code> object that is distinct.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello, World!");
     *         System.out.println(string32.distinct());
     *         // Output: "Helo, Wrd!"
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is distinct.
     * The output will be <code>Helo, Wrd!</code>.
     * </p>
     *
     * @return The new <code>String32</code> object that is distinct.
     */
    public String32 distinct() {
        return String32.valueOf(toString().chars().distinct().toArray());
    }

    /**
     * Returns a new <code>String32</code> object that is replaced with the given targets and replacement.
     * <p>
     * This method returns a new <code>String32</code> object that is replaced with the given targets and replacement.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello, World!");
     *         System.out.println(string32.replaceMultiple(new String32[] {String32.valueOf("o"), String32.valueOf("l")}, String32.valueOf("x")));
     *         // Output: "Hexxx, Worxd!"
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is replaced with the given targets and replacement.
     * The output will be <code>Hexxx, Worxd!</code>.
     * </p>
     *
     * @param targets     The <code>String32</code> objects to replace.
     * @param replacement The <code>String32</code> object to replace with.
     * @return The new <code>String32</code> object that is replaced with the given targets and replacement.
     * @apiNote If the given targets array is null or empty, or if the replacement is null, this method will return the original <code>String32</code> object.
     */
    public String32 replaceMultiple(final String32[] targets, final String32 replacement) {
        if (targets == null || targets.length == 0 || replacement == null) {
            return this;
        }

        String result = toString();
        for (String32 target : targets) {
            result = result.replace(target.toString(), replacement.toString());
        }
        return new String32(result);
    }

    /**
     * Returns a new <code>String32</code> object that is sorted characters.
     * <p>
     * This method returns a new <code>String32</code> object that is sorted characters.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello, World!");
     *         System.out.println(string32.sortCharacters());
     *         // Output: " ,HWrdeolol!"
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is sorted characters.
     * The output will be <code> ,HWrdeolol!</code>.
     * </p>
     *
     * @return The new <code>String32</code> object that is sorted characters.
     */
    public String32 sortCharacters() {
        int[] sortedChars = Arrays.copyOf(this.characters, this.length);
        Arrays.sort(sortedChars);
        return String32.valueOf(sortedChars);
    }

    /**
     * Calls the given consumer if the <code>String32</code> object contains the given <code>String32</code> object.
     * <p>
     * This method calls the given consumer if the <code>String32</code> object contains the given <code>String32</code> object.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello, World!");
     *         string32.onContains(String32.valueOf("o"), System.out::println);
     *         // Output: "o"
     * </pre></blockquote>
     * This will call the given consumer if the <code>String32</code> object contains the given <code>String32</code> object.
     * The output will be <code>o</code>.
     * </p>
     *
     * @param substring The <code>String32</code> object to check if it contains.
     * @param consumer  The consumer to call if the <code>String32</code> object contains the given <code>String32</code> object.
     * @return The <code>String32</code> object.
     * @throws NullPointerException if the substring or consumer is <code>null</code>.
     */
    public String32 onContains(final String32 substring, final Consumer<String32> consumer) {
        Objects.requireNonNull(substring, "substring cannot be null");
        Objects.requireNonNull(consumer, "consumer cannot be null");
        if (contains(substring)) {
            consumer.accept(substring);
        }
        return this;
    }

    /**
     * Returns a new <code>String32</code> object that is leet speak.
     * <p>
     * This method returns a new <code>String32</code> object that is leet speak.
     * </p>
     * <p>
     * Leet speak, also known as eleet or leetspeak, is a system of modified spellings used primarily on the Internet.
     * It often uses character replacements in ways that play on the similarity of their glyphs via reflection or other resemblance.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello, World!");
     *         System.out.println(string32.toLeetSpeak());
     *         // Output: "H3110, W0r1d!"
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is leet speak.
     * The output will be <code>H3110, W0r1d!</code>.
     * </p>
     *
     * @return The new <code>String32</code> object that is leet speak.
     */
    public String32 toLeetSpeak() {
        Map<Character, String> leetMap = new HashMap<>();
        leetMap.put('A', "4");
        leetMap.put('E', "3");
        leetMap.put('I', "1");
        leetMap.put('O', "0");
        leetMap.put('S', "5");
        leetMap.put('T', "7");
        leetMap.put('a', "4");
        leetMap.put('e', "3");
        leetMap.put('i', "1");
        leetMap.put('o', "0");
        leetMap.put('s', "5");
        leetMap.put('t', "7");

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.length; i++) {
            char c = (char) this.characters[i];
            sb.append(leetMap.getOrDefault(c, String.valueOf(c)));
        }
        return new String32(sb.toString());
    }

    /**
     * Returns a new <code>String32</code> object that is transformed by the given transformation.
     * <p>
     * This method returns a new <code>String32</code> object that is transformed by the given transformation.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         Function&lt;String32, String32&gt; transformation = string32 -&gt; string32.toLeetSpeak();
     *         String32 string32 = String32.valueOf("Hello, World!");
     *         System.out.println(string32.applyTransformation(transformation));
     *         // Output: "H3110, W0r1d!"
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is transformed by the given transformation.
     * The output will be <code>H3110, W0r1d!</code>.
     * </p>
     *
     * @param transformation The transformation to apply.
     * @return The new <code>String32</code> object that is transformed by the given transformation.
     * @throws NullPointerException if the transformation is <code>null</code>.
     */
    public String32 applyTransformation(final Function<String32, String32> transformation) {
        Objects.requireNonNull(transformation, "transformation cannot be null");
        return transformation.apply(this);
    }

    /**
     * Performs the given action for each character of the <code>String32</code> object.
     * <p>
     * This method performs the given action for each character of the <code>String32</code> object.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello, World!");
     *         string32.forEachCharacter(System.out::println);
     *         // Output:
     *         // 72
     *         // 101
     *         // 108
     *         // 108
     *         // 111
     *         // 44
     *         // 32
     *         // 87
     *         // 111
     *         // 114
     *         // 108
     *         // 100
     *         // 33
     * </pre></blockquote>
     * This will perform the given action for each character of the <code>String32</code> object.
     * The output will be the character codes of the characters of the <code>String32</code> object.
     * </p>
     *
     * @param action The action to be performed for each character.
     * @throws NullPointerException if the action is <code>null</code>.
     */
    public void forEachCharacter(final Consumer<Integer> action) {
        Objects.requireNonNull(action, "action cannot be null");
        for (int i = 0; i < length; i++) {
            action.accept(this.characters[i]);
        }
    }

    /**
     * Returns an {@link Optional} containing the <code>String32</code> object if it is not empty.
     * <p>
     * This method returns an {@link Optional} containing the <code>String32</code> object if it is not empty.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.valueOf("Hello, World!");
     *         System.out.println(string32.toOptionalIfNotEmpty());
     *         // Output: Optional[Hello, World!]
     * </pre></blockquote>
     * This will return an {@link Optional} containing the <code>String32</code> object if it is not empty.
     * The output will be <code>Optional[Hello, World!]</code>.
     * </p>
     *
     * @return An {@link Optional} containing the <code>String32</code> object if it is not empty.
     * @throws NullPointerException if the action is <code>null</code>.
     * @apiNote If the <code>String32</code> object is empty, this method will return an empty {@link Optional}.
     */
    public Optional<String32> ifPresent(final Consumer<String32> action) {
        Objects.requireNonNull(action, "action cannot be null");
        if (this.length > 0) {
            action.accept(this);
            return Optional.of(this);
        }
        return Optional.empty();
    }

    /**
     * Returns a new <code>String32</code> object that is mapped by the given mapper.
     * <p>
     * This method returns a new <code>String32</code> object that is mapped by the given mapper.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         Function&lt;Integer, Integer&gt; mapper = i -&gt; i + 1;
     *         String32 string32 = String32.valueOf("Hello, World!");
     *         System.out.println(string32.mapCharacters(mapper));
     *         // Output: "Ifmmp-!Xpsme!"
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is mapped by the given mapper.
     * The output will be <code>Ifmmp-!Xpsme!</code>.
     * </p>
     *
     * @param mapper The mapper to apply.
     * @return The new <code>String32</code> object that is mapped by the given mapper.
     * @throws NullPointerException if the mapper is <code>null</code>.
     */
    public String32 mapCharacters(final Function<Integer, Integer> mapper) {
        Objects.requireNonNull(mapper, "mapper cannot be null");
        int[] mappedChars = Arrays.stream(this.characters).map(mapper::apply).toArray();
        return String32.valueOf(mappedChars);
    }

    /**
     * Returns a new <code>String32</code> object that is reduced by the given accumulator.
     * <p>
     * This method returns a new <code>String32</code> object that is reduced by the given accumulator.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         IntBinaryOperator accumulator = (a, b) -&gt; a + b;
     *         String32 string32 = String32.valueOf("Hello, World!");
     *         System.out.println(string32.reduceCharacters(0, accumulator));
     *         // Output: "1116"
     * </pre></blockquote>
     * This will return a new <code>String32</code> object that is reduced by the given accumulator.
     * The output will be <code>1116</code>.
     * </p>
     *
     * @param identity    The identity value.
     * @param accumulator The accumulator to apply.
     * @return The new <code>String32</code> object that is reduced by the given accumulator.
     * @throws NullPointerException if the accumulator is <code>null</code>.
     * @apiNote The resulting <code>String32</code> object will contain a single character representing the result of the reduction.
     */
    public String32 reduceCharacters(final int identity, final IntBinaryOperator accumulator) {
        Objects.requireNonNull(accumulator, "accumulator cannot be null");
        int result = Arrays.stream(this.characters).reduce(identity, accumulator);
        return String32.valueOf(new int[]{result});
    }

    /**
     * Returns the {@link String} representation of the <code>String32</code> object.
     * <p>
     * This method returns the {@link String} representation of the <code>String32</code> object.
     * The {@link String} representation is the {@link String} that contains the characters of the <code>String32</code> object.
     * </p>
     *
     * @return The {@link String} representation of the <code>String32</code> object.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.length; i++) {
            sb.appendCodePoint(this.characters[i]);
        }
        return sb.toString();
    }

    /**
     * Compares this <code>String32</code> object to the specified object.
     * <p>
     * This method compares this <code>String32</code> object to the specified object.
     * The result is <code>true</code> if and only if the argument is not <code>null</code> and is a <code>String32</code> object that represents the same sequence of characters as this object.
     * </p>
     *
     * @param obj The object to compare this <code>String32</code> object against.
     * @return <code>true</code> if the given object represents a <code>String32</code> equivalent to this <code>String32</code> object,
     * <code>false</code> otherwise.
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof String32 other) {
            if (this.length != other.length) {
                return false;
            }
            for (int i = 0; i < length; i++) {
                if (this.characters[i] != other.characters[i]) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Returns the hash code of the <code>String32</code> object.
     * <p>
     * This method returns the hash code of the <code>String32</code> object.
     * The hash code is calculated by the hash code of the characters of the <code>String32</code> object.
     * </p>
     *
     * @return The hash code of the <code>String32</code> object.
     */
    @Override
    public int hashCode() {
        int result = 1;
        for (int character : this.characters) {
            result = 31 * result + character;
        }
        return result;
    }

    /**
     * Compares this object with the specified object for order.  Returns a
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
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     * @throws NullPointerException if the specified object is null
     * @apiNote It is strongly recommended, but <i>not</i> strictly required that
     * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
     * class that implements the {@code Comparable} interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     */
    @Override
    public int compareTo(final String32 o) {
        int minLength = Math.min(this.length, o.length);
        for (int i = 0; i < minLength; i++) {
            if (this.characters[i] != o.characters[i]) {
                return Integer.compare(this.characters[i], o.characters[i]);
            }
        }
        return this.length - o.length;
    }

    /**
     * Returns a clone of the <code>String32</code> object.
     * <p>
     * This method returns a clone of the <code>String32</code> object.
     * </p>
     *
     * @return A clone of the <code>String32</code> object.
     * @throws CloneNotSupportedException If the object's class does not support the {@link Cloneable} interface.
     */
    @Override
    protected String32 clone() throws CloneNotSupportedException {
        return (String32) super.clone();
    }

    /**
     * The <code>Strings32ToJoin</code> class represents a <code>String32</code> object that is used to join multiple <code>String32</code> objects.
     * <p>
     * This class is used to join multiple <code>String32</code> objects with a delimiter.
     * The delimiter is a {@link String} that is inserted between the <code>String32</code> objects.
     * </p>
     * <p>
     * Example:
     * <blockquote><pre>
     *         String32 string32 = String32.join("Hello", "World").with(" ");
     *         // Output: "Hello World"
     *         System.out.println(string32.toString());
     * </pre></blockquote>
     * This will join the <code>String32</code> objects <code>Hello</code> and <code>World</code> with a space character.
     * The output will be <code>Hello World</code>.
     * </p>
     *
     * @see String32
     */
    public static class Strings32ToJoin {
        /**
         * The <code>String32</code> objects to join.
         * <p>
         * The <code>String32</code> objects to join are stored as an array of <code>String32</code> objects.
         * </p>
         */
        private final String32[] strings;

        /**
         * Constructs a new {@link Strings32ToJoin} object that contains the <code>String32</code> objects to join.
         * <p>
         * This constructor constructs a new {@link Strings32ToJoin} object that contains the <code>String32</code> objects to join.
         * The <code>String32</code> objects are stored as an array of <code>String32</code> objects.
         * </p>
         *
         * @param strings The <code>String32</code> objects to join.
         */
        Strings32ToJoin(final String32... strings) {
            this.strings = strings;
        }

        /**
         * Joins the <code>String32</code> objects with the given delimiter.
         * <p>
         * This method joins the <code>String32</code> objects with the given delimiter.
         * The delimiter is a {@link String} that is inserted between the <code>String32</code> objects.
         * </p>
         * <p>
         * Example:
         * <blockquote><pre>
         *         String32 string32 = String32.join("Hello", "World").with(" ");
         *         // Output: "Hello World"
         *         System.out.println(string32.toString());
         * </pre></blockquote>
         * This will join the <code>String32</code> objects <code>Hello</code> and <code>World</code> with a space character.
         * The output will be <code>Hello World</code>.
         * </p>
         *
         * @param delimiter The delimiter to insert between the <code>String32</code> objects.
         * @return The joined <code>String32</code> object.
         */
        public String32 with(final String delimiter) {
            return with(delimiter, null);
        }

        /**
         * Joins the <code>String32</code> objects with the given delimiter and escape string.
         * <p>
         * This method joins the <code>String32</code> objects with the given delimiter and escape string.
         * The delimiter is a {@link String} that is inserted between the <code>String32</code> objects.
         * The escape string is a <code>String32</code> object that is inserted before and after each <code>String32</code> object.
         * </p>
         * <p>
         * Example:
         * <blockquote><pre>
         *         String32 string32 = String32.join("Hello", "World").with(" ", String32.valueOf("\""));
         *         // Output: "\"Hello\" \"World\""
         *         System.out.println(string32.toString());
         * </pre></blockquote>
         * This will join the <code>String32</code> objects <code>Hello</code> and <code>World</code> with a space character.
         * The escape string is a double quote character, which is inserted before and after each <code>String32</code> object.
         * The output will be <code>"Hello" "World"</code>.
         * </p>
         *
         * @param delimiter    The delimiter to insert between the <code>String32</code> objects.
         * @param escapeString The escape string to insert before and after each <code>String32</code> object.
         * @return The joined <code>String32</code> object.
         * @throws NullPointerException if the delimiter is <code>null</code>.
         */
        public String32 with(final String delimiter, final String32 escapeString) {
            Objects.requireNonNull(delimiter, "delimiter cannot be null");
            if (this.strings == null || this.strings.length == 0) {
                return String32.valueOf("");
            }
            String32 escape = escapeString == null ? String32.valueOf("") : escapeString;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < this.strings.length; i++) {
                String32 s = this.strings[i];
                if (s != null) {
                    sb.append(escape);
                    sb.append(s);
                    sb.append(escape);
                }
                if (i < this.strings.length - 1) {
                    sb.append(delimiter);
                }
            }
            return String32.valueOf(sb.toString());
        }
    }
}
