package com.shamus.datax;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javafx.util.Pair;

/**
 * Creates a map of NGrams. The static method buildMap creates a bigram reduced map from the given
 * text input.
 *
 * @author drose
 */
public class NGramGenerator {

  private NGramGenerator(){}

  /**
   * Creates a bigram reduced map from the given text input.
   *
   * @param p the directory path to the file for bigram processing
   * @return a map of pairs that are associated with the count they appear in the text input
   * @throws IOException if the file does not exist at path p
   */
  public static Map<Pair<String, String>, Integer> buildBigramMap(Path p) throws IOException {
    Stream<String> content = Files.lines(p);
    //holds all of the grams in the file
    List<String> grams = content
        .map(String::toLowerCase)
        .flatMap(li -> Stream.of(li.split("\\W+")))
        .collect(Collectors.toList());
    //collect bigrams
    List<Pair<String, String>> bigrams = new ArrayList<>();
    IntStream.range(1, grams.size())
        .mapToObj(i -> new Pair(grams.get(i - 1), grams.get(i)))
        .forEach(bigrams::add);
    //all bigrams collected, generating and returning map reduce of bigrams
    return MapReduce.reduceToMap(bigrams);
  }

  /**
   * Creates an n-gram map with the count each key occurred in the document.
   *
   * @param p the directory path to the file for n-gram processing
   * @param n the value of 'n'-gram
   * @return a map of n-grams that are associated with the amount of occurrences in the document
   * @throws IOException Path p does not exist or some other I/O error occurred opening the file
   * @throws IllegalArgumentException if n is less than or equal to 0. In the case n is greater than
   * the amount of words in the document, then n will be reduced to the actual size of the document.
   */
  public static Map<String, Integer> buildNGramMap(Path p, int n) throws IOException{
    if(n <= 0){
      throw new IllegalArgumentException("n = "+ n);
    }
    Stream<String> content = Files.lines(p);
    List<String> grams = content
        .map(String::toLowerCase)
        .flatMap(li -> Stream.of(li.split("\\W+")))
        .collect(Collectors.toList());
    //collect ngrams
    List<String> ngrams = new ArrayList<>();
    if(n > grams.size()){
      n = grams.size();
    }
    int finalN = n;
    IntStream.range(0,grams.size()-n+1)
        .mapToObj(i -> String.join(" ", grams.subList(i,i+finalN)))
        .forEach(ngrams::add);
    return MapReduce.reduceToMap(ngrams);
  }

  /**
   * Comparator for determining the bigrams with highest confidence levels.
   */
  public static class BiGramComparator implements Comparator<Pair<String, Integer>>, Serializable {

    @Override
    public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
      if (o1.getValue() < o2.getValue()) {
        return 1;
      } else if (o1.getValue() > o2.getValue()) {
        return -1;
      }
      return 0;
    }
  }
}
