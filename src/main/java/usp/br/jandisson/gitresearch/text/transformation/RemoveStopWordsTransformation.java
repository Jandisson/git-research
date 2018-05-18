package usp.br.jandisson.gitresearch.text.transformation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

public class RemoveStopWordsTransformation implements ITextTransformation {


    Logger logger = LoggerFactory.getLogger(RemoveStopWordsTransformation.class);

    @Override
    public String transform(String text) {


        String result="";

        StringTokenizer st = new StringTokenizer(text);

        while (st.hasMoreElements()) {
            String element= st.nextToken();

            if(!isShortWord(element) && !isStopWord(element)) {

                result += element + " ";
            }
        }

        return result;
    }

    private boolean isStopWord(String text){

        return Arrays.stream(this.stopWords).anyMatch(x -> x.equals(text));

    }

    private boolean isShortWord(String text ){

          return text.trim().length() < 2;
    }

    String[] stopWords={
            "a",
            "about",
            "above",
            "after",
            "again",
            "against",
            "all",
            "am",
            "an",
            "and",
            "any",
            "are",
            "aren't",
            "as",
            "at",
            "be",
            "because",
            "been",
            "before",
            "being",
            "below",
            "between",
            "both",
            "but",
            "by",
            "cannot",
            "can't",
            "could",
            "couldn't",
            "did",
            "didn't",
            "do",
            "does",
            "doesn't",
            "doing",
            "don't",
            "down",
            "during",
            "each",
            "few",
            "for",
            "from",
            "further",
            "had",
            "hadn't",
            "has",
            "hasn't",
            "have",
            "haven't",
            "having",
            "he",
            "he'd",
            "he'll",
            "her",
            "here",
            "here's",
            "hers",
            "herself",
            "he's",
            "him",
            "himself",
            "his",
            "how",
            "how's",
            "i",
            "i'd",
            "if",
            "i'll",
            "i'm",
            "in",
            "into",
            "is",
            "isn't",
            "it",
            "its",
            "it's",
            "itself",
            "i've",
            "let's",
            "me",
            "more",
            "most",
            "mustn't",
            "my",
            "myself",
            "no",
            "nor",
            "not",
            "of",
            "off",
            "on",
            "once",
            "only",
            "or",
            "other",
            "ought",
            "our",
            "ours",
            "ourselves",
            "out",
            "over",
            "own",
            "same",
            "shan't",
            "she",
            "she'd",
            "she'll",
            "she's",
            "should",
            "shouldn't",
            "so",
            "some",
            "such",
            "than",
            "that",
            "that's",
            "the",
            "their",
            "theirs",
            "them",
            "themselves",
            "then",
            "there",
            "there's",
            "these",
            "they",
            "they'd",
            "they'll",
            "they're",
            "they've",
            "this",
            "those",
            "through",
            "to",
            "too",
            "under",
            "until",
            "up",
            "very",
            "was",
            "wasn't",
            "we",
            "we'd",
            "we'll",
            "were",
            "we're",
            "weren't",
            "we've",
            "what",
            "what's",
            "when",
            "when's",
            "where",
            "where's",
            "which",
            "while",
            "who",
            "whom",
            "who's",
            "why",
            "why's",
            "with",
            "won't",
            "would",
            "wouldn't",
            "you",
            "you'd",
            "you'll",
            "your",
            "you're",
            "yours",
            "yourself",
            "yourselves",
            "you've"

    };


}
