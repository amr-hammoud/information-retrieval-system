package Code;

import java.util.ArrayList;
import java.util.Collections;

public class Project1 {

    public static ArrayList<String> deleteStopWord(ArrayList<String> stopWords, ArrayList arraylist) {
        ArrayList<String> NewList = new ArrayList<>();
        int i = 1;
        while (i < arraylist.size()) {
            if (!stopWords.contains(arraylist.get(i).toString().toLowerCase())) {
                NewList.add((String) arraylist.get(i));
            }
            i++;
        }
        return NewList;
    }

    public static ArrayList<String> removePunctuation(String wordListString) {
        // Remove all punctuation except the dots:
        wordListString = wordListString.replaceAll("[\\p{Punct}&&[^.]]", "");

        // Remove all dots that are followed by space:
        wordListString = wordListString.replaceAll("\\.\\s", " ");

        // Remove numeric values:
        wordListString = wordListString.replaceAll("[0-9]", "");

        ArrayList<String> cleanWordList = new ArrayList<>();

        String[] wordList = wordListString.toLowerCase().split("\\s+");

        Collections.addAll(cleanWordList, wordList);
        return cleanWordList;
    }

}
