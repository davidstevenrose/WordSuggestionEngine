package com.company.main;

import com.company.datax.NGramGenerator;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.stream.Collectors;
import javafx.util.Pair;

/**
 * This program performs a map reduce on a bigram to determine three suggested words to insert into
 * a sentence given the first word as input. The confidence threshold is 65%.
 *
 * @author drose
 */
public class Main {

  private static final String[] connectors = {"the", "this", "of"};

  public static void printRecomendation(String s) {
    System.out.println("Your next word might be: " + s);
  }

  /**
   * Main procedure.
   *
   * @param args the text file of lines to open
   */
  public static void main(String[] args) {
    //input is a list of strings separated by line
    Path filePath = Paths.get(args[0]);
    //parse input
    Map<Pair<String, String>, Integer> bigrams = null;
    try {
      bigrams = NGramGenerator.buildMap(filePath);
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
    String word;
    Scanner sc = new Scanner(System.in, "UTF-8");
    System.out.println("Enter a word");
    word = sc.next();
    sc.close();

    //the sum of values in bigramsFiltered is the number of items with variable word
    float totalWord = (float) bigrams.entrySet()
        .stream().filter(entry -> entry.getKey().getKey().equals(word))
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue))
        .values().stream()
        .reduce(Integer::sum).orElse(0);
    //if the word has no bigrams, return list of connectors and end program
    if (totalWord == 0f) {
      Arrays.asList(connectors).forEach(Main::printRecomendation);
      System.exit(0);
    }

    //fill array of confidence levels, but only if confidence > 65%
    PriorityQueue<Pair<String, Integer>> highestConfidence = new PriorityQueue<>(3,
        new NGramGenerator.BiGramComparator());
    bigrams.entrySet()
        .stream().filter(entry -> entry.getKey().getKey().equals(word))
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue))
        .entrySet().stream()
        .filter(entry -> (float) entry.getValue() / totalWord > 0.65f)
        //collect the second value in bigram and the confidence
        .map(entry -> new Pair(entry.getKey().getValue(), entry.getValue()))
        .forEach(highestConfidence::add);
    //select top three results
    int recIndex = 0;
    int connIndex = 0;
    String[] recommendation = new String[3];
    while (!highestConfidence.isEmpty() && recIndex < 3) {
      recommendation[recIndex] = highestConfidence.poll().getKey();
      recIndex++;
    }
    while (recIndex < 3 && connIndex < connectors.length) {
      recommendation[recIndex] = connectors[connIndex];
      connIndex++;
      recIndex++;
    }
    //print top three results
    Arrays.asList(recommendation).forEach(Main::printRecomendation);
  }
}
