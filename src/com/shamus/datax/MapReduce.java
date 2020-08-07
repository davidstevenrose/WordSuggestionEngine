package com.shamus.datax;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Performs a map reduce of objects in a list and returns a Map<E, Integer>.
 *
 * @author drose
 */
public class MapReduce {

  private MapReduce(){}

  /**
   * Reduces a list of objects to a map with count.
   *
   * @param list the list of all objects of type E.
   * @return a reduced map that counts the occurrences of each object.
   *  Key type: Object E
   *  Value type: Integer
   */
  public static <E> Map<E, Integer> reduceToMap(List<E> list) {
    //mapping every word to one
    Map<E, Integer> reducedMap = list.stream()
        .collect(Collectors.toMap(Function.identity(), key -> 1, Integer::sum));
    return reducedMap;
  }
}
