/*
 * Copyright (c) 2025 Splatgames.de Software and Contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

/**
 * Provides utility classes for various common operations, primarily focused on stream creation from iterables and iterators.
 *
 * <p>
 * The primary utility class in this package is {@link de.splatgames.aether.datatypes.utils.Streams Streams}, which offers static methods to create streams from iterables and iterators.
 * These methods facilitate the creation of streams using the {@link java.util.stream.StreamSupport StreamSupport} class and the {@link java.util.Spliterators Spliterators} class.
 * The functionality is particularly useful for integrating with the {@link de.splatgames.aether.datatypes.text.String32} class.
 * </p>
 *
 * <h2>Key Classes and Methods:</h2>
 * <ul>
 *     <li>{@link de.splatgames.aether.datatypes.utils.Streams Streams}: Contains methods for creating streams from various sources.
 *         <ul>
 *             <li>{@link de.splatgames.aether.datatypes.utils.Streams#stream(Iterable)}: Creates a sequential stream from an {@link java.lang.Iterable Iterable}.</li>
 *         </ul>
 *     </li>
 * </ul>
 *
 * <h2>Usage Example:</h2>
 * <pre>{@code
 * import de.splatgames.aether.datatypes.utils.Streams;
 * import java.util.List;
 * import java.util.stream.Stream;
 *
 * public class ExampleClass {
 *     public static void main(String[] args) {
 *         List<String> list = List.of("a", "b", "c");
 *         Stream<String> stream = Streams.stream(list);
 *         stream.forEach(System.out::println);
 *     }
 * }
 * }</pre>
 *
 * <h2>Additional Information:</h2>
 * <p>
 * The utility methods provided in this package are designed to simplify the creation of streams, enabling seamless integration with the Stream API.
 * These methods are particularly useful for working with collections and other iterable data structures, providing a more functional approach to data processing.
 * </p>
 *
 * @see java.util.stream.StreamSupport
 * @see java.util.Spliterators
 * @since 1.0.0
 */
package de.splatgames.aether.datatypes.utils;
