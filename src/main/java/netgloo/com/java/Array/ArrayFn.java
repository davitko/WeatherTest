package netgloo.com.java.Array;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Controller;

/**
 * 
 * @author Miloš Davitkovic
 *
 *List<E>:
An ordered collection (also known as a sequence). The user of this interface has precise control over where 
in the list each element is inserted. The user can access elements by their integer index 
(position in the list), and search for elements in the list.

Set<E>:
A collection that contains no duplicate elements. More formally, sets contain no pair of elements e1 and e2 
such that e1.equals(e2), and at most one null element. As implied by its name, this interface models the 
mathematical set abstraction.
 */
@Controller
public class ArrayFn {

	public ArrayFn() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 	1. 
	 * 	Object[] oa = new Object[100];
		Collection<Object> co = new ArrayList<Object>();

		// T inferred to be Object
		fromArrayToCollection(oa, co);

		2.
		String[] sa = new String[100];
		Collection<String> cs = new ArrayList<String>();

		// T inferred to be String
		fromArrayToCollection(sa, cs);

		3.
		String[] sa = new String[100];
		Collection<Object> co = new ArrayList<Object>();
		// T inferred to be Object
		fromArrayToCollection(sa, co);

		4.
		Integer[] ia = new Integer[100];
		Collection<Number> cn = new ArrayList<Number>();
		// T inferred to be Number
		fromArrayToCollection(ia, cn);

		5.
		Float[] fa = new Float[100];
		Collection<Number> cn = new ArrayList<Number>();
		// T inferred to be Number
		fromArrayToCollection(fa, cn);

		6.
		Number[] na = new Number[100];
		Collection<Number> cn = new ArrayList<Number>();
		// T inferred to be Number
		fromArrayToCollection(na, cn);

		7.
		Number[] na = new Number[100];
		Collection<Object> co = new ArrayList<Object>();
		// T inferred to be Object
		fromArrayToCollection(na, co);

		8.
		Number[] na = new Number[100];
		Collection<String> cs = new ArrayList<String>();
		// !!! compile-time error !!!
		fromArrayToCollection(na, cs);


	 * @param a
	 * @param c
	 */
	public <T> void ArrayToCollection(T[] a, Collection<T> c) {
		for (T o : a) {
			c.add(o); // Correct
		}
	}

	/**
	 * Generic method printArray to print Array elements on Console
	 *  // Create arrays of Integer, Double and Character
      Integer[] intArray = { 1, 2, 3, 4, 5 };
      Double[] doubleArray = { 1.1, 2.2, 3.3, 4.4 };
      Character[] charArray = { 'H', 'E', 'L', 'L', 'O' };

      System.out.println("Array integerArray contains:");
      printArray(intArray);   // pass an Integer array

      System.out.println("\nArray doubleArray contains:");
      printArray(doubleArray);   // pass a Double array

      System.out.println("\nArray characterArray contains:");
      printArray(charArray);   // pass a Character array
	 * @param inputArray
	 */
	public < E > void printArray( E[] inputArray ) {
		// Display array elements
		for(E element : inputArray) {
			System.out.printf("%s ", element);
		}
		System.out.println();
	}

	/**
	 * Determines the largest of three Comparable objects
	 * System.out.printf("Max of %d, %d and %d is %d\n\n", 
         3, 4, 5, maximum( 3, 4, 5 ));

      System.out.printf("Max of %.1f,%.1f and %.1f is %.1f\n\n",
         6.6, 8.8, 7.7, maximum( 6.6, 8.8, 7.7 ));

      System.out.printf("Max of %s, %s and %s is %s\n","pear",
         "apple", "orange", maximum("pear", "apple", "orange"));
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public <T extends Comparable<T>> T maximum(T x, T y, T z) {
		T max = x;   // assume x is initially the largest

		if(y.compareTo(max) > 0) {
			max = y;   // y is the largest so far
		}

		if(z.compareTo(max) > 0) {
			max = z;   // z is the largest now                 
		}
		return max;   // returns the largest object   
	}

	/**
	 * Java 8
	 * @param iterator
	 * @return
	 */
	public <T> ArrayList<T> IteratorToArrayList(final Iterator<T> iterator) {
		return StreamSupport
				.stream(
						Spliterators
						.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false)
				.collect(
						Collectors.toCollection(ArrayList::new)
						);
	}

	public <E> List<E> IterableToList(Iterable<E> iterable) {
		if(iterable instanceof List) {
			return (List<E>) iterable;
		}
		ArrayList<E> list = new ArrayList<E>();
		if(iterable != null) {
			for(E e: iterable) {
				list.add(e);
			}
		}
		return list;
	}
	
	public List<String> removeDuplicateInList(List<String> inputList) {
		List<String> list = inputList;
	    Set<String> set = new HashSet<String>(list);
	    list.addAll(set);
	    return list;
	}





}
