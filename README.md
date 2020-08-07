# WordSuggestionEngine

  <p align="center">
    This project takes an input word from the user and recommends to the user the next three words
    that might closely match what the user wishes to use to finish or proceed with the sentence.
    <br />
    <br />
    <a href="https://github.com/github_username/repo/issues">Report Bug</a>    
  </p>


## About The Project
This project suggest words by vectorizing the learning input into bi-grams.

### Built With
* [com.shamus.datax.MapReduce](https://github.com/davidstevenrose/WordSuggestionEngine/tree/master/src/com/shamus/datax)
* [com.shamus.datax.NGramGenerator](https://github.com/davidstevenrose/WordSuggestionEngine/tree/master/src/com/shamus/datax)

## Getting Started

### Prerequisites
The user who wishes to clone this repository to use the project must provide a text file for 
the program to learn.

The learning file must contain only alpha-numeric text. Each sentence is separated by a new line.

To run the program, the project must be exported as an executable jar file.

## Usage
To run the program, open the command prompt and type in
~~~
    java -jar [path to location of jar file].jar learning_input.txt
~~~

The program will prompt the user to enter a word, which must be alpha-numeric.
If the user enters a word that has not been vectorized or is not alpha-numeric,
the program will return three very common words by default. Otherwise, the 
program will return at most the three closest suggestions to continue the sentence. 

## Licence
[MIT Licence](https://github.com/davidstevenrose/WordSuggestionEngine/blob/master/LICENSE)
