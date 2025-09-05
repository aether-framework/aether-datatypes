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

package de.splatgames.aether.datatypes.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Utility class for creating streams from iterables and iterators.
 * <p>
 * This class provides static methods for creating streams from iterables and iterators.
 * The streams are created using the {@link StreamSupport} class.
 * The {@link StreamSupport} class provides static methods for creating streams from spliterators.
 * The spliterators are created using the {@link Spliterators} class.
 * It is mostly used in the {@link de.splatgames.aether.datatypes.text.String32} class.
 * </p>
 *
 * @author Erik Pförtner
 * @see StreamSupport
 * @see Spliterators
 * @see de.splatgames.aether.datatypes.text.String32 String32
 * @since 1.0.0
 */
public class Streams {

    /**
     * Prevent instantiation of this utility class.
     */
    private Streams() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Returns a sequential stream with the elements of the specified iterable as its source.
     * <p>
     * If the iterable is an instance of {@link Collection}, the stream is created using the {@link Collection#stream()} method.
     * Otherwise, the stream is created using the {@link StreamSupport#stream(java.util.Spliterator, boolean)} method.
     * </p>
     *
     * @param iterable the iterable to create the stream from
     * @param <T> the type of the elements in the stream
     * @return a sequential stream with the elements of the specified iterable as its source
     */
    public static <T> Stream<T> stream(Iterable<T> iterable) {
        return (iterable instanceof Collection)
                ? ((Collection<T>) iterable).stream()
                : StreamSupport.stream(iterable.spliterator(), false);
    }

    /**
     * Returns a sequential stream with the elements of the specified iterator as its source.
     * <p>
     * The stream is created using the {@link StreamSupport#stream(java.util.Spliterator, boolean)} method.
     * The spliterator is created using the {@link Spliterators#spliteratorUnknownSize(Iterator, int)} method.
     * </p>
     *
     * @param iterator the iterator to create the stream from
     * @param <T> the type of the elements in the stream
     * @return a sequential stream with the elements of the specified iterator as its source
     */
    public static <T> Stream<T> stream(Iterator<T> iterator) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, 0), false);
    }
}
