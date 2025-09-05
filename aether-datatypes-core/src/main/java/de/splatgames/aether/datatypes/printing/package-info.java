/**
 * Provides interfaces and enums for printing objects to the console, a file, or a logger.
 *
 * <p>
 * The primary interface in this package is {@link de.splatgames.aether.datatypes.printing.Printable Printable}, which defines the methods required for an object to be printable
 * to various outputs such as console or file via a {@link java.io.PrintStream PrintStream}. The {@link de.splatgames.aether.datatypes.printing.Printable Printable} interface includes methods for
 * printing the object, printing to a specific {@link java.io.PrintStream PrintStream}, and printing while returning the object itself.
 * </p>
 *
 * <p>
 * The {@link de.splatgames.aether.datatypes.printing.PrintingMethod PrintingMethod} enum specifies the method of printing, including options for standard output, standard error, and
 * logging via {@link java.util.logging.Logger Logger}. This enum allows the specification of the printing target, making it flexible
 * to switch between different outputs.
 * </p>
 *
 * <p>
 * Future classes in this package may include:
 * <ul>
 *   <li>{@code FilePrinter}: A class for printing objects to files, providing additional methods for file handling and formatting.</li>
 *   <li>{@code NetworkPrinter}: A class for printing objects to network streams, allowing integration with remote logging systems.</li>
 *   <li>{@code BufferedPrinter}: A class that buffers printed output and allows batch processing or delayed printing.</li>
 * </ul>
 * </p>
 *
 * <h2>Usage Example:</h2>
 * <pre>{@code
 * public class ExampleClass implements Printable<ExampleClass> {
 *     private String data;
 *
 *     public ExampleClass(String data) {
 *         this.data = data;
 *     }
 *
 *     @Override
 *     public void print() {
 *         System.out.println(data);
 *     }
 *
 *     @Override
 *     public void print(PrintStream out) {
 *         out.println(data);
 *     }
 *
 *     @Override
 *     public ExampleClass printAndReturn() {
 *         print();
 *         return this;
 *     }
 *
 *     @Override
 *     public ExampleClass printAndReturn(PrintStream out) {
 *         print(out);
 *         return this;
 *     }
 * }
 * }</pre>
 *
 * <p>
 * This package aims to provide a consistent and flexible approach to printing objects across different types of outputs, ensuring
 * that printing operations can be easily integrated and customized within applications.
 * </p>
 *
 * @see java.io.PrintStream PrintStream
 * @see java.util.logging.Logger Logger
 * @see de.splatgames.aether.datatypes.printing.Printable Printable
 * @see de.splatgames.aether.datatypes.printing.PrintingMethod PrintingMethod
 * @since 1.0.0
 */
package de.splatgames.aether.datatypes.printing;
