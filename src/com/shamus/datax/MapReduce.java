package com.shamus.datax;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javafx.util.Pair;

/**
 * Performs a map reduce of bi-grams on a list of paired strings.
 *
 * @author drose
 */
public class MapReduce {

  /**
   * Reduces a list of bigrams to a map with count.
   *
   * @param list the list of all bigrams as pairs.
   * @return a reduced map that counts the occurrences of each bigram.
   */
  public static Map<Pair<String, String>, Integer> reduceToMap(List<Pair<String, String>> list) {
    //mapping every word to one
    Map<Pair<String, String>, Integer> bigramMap = list.stream()
        .collect(Collectors.toMap(Function.identity(), key -> 1, Integer::sum));
    return bigramMap;
  }
}
