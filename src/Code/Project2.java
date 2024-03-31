package Code;

import org.tartarus.snowball.ext.PorterStemmer;

import java.util.ArrayList;

public class Project2 {

    public static ArrayList<String> stem(ArrayList<String> wordList) {
        ArrayList<String> newList = new ArrayList<>();
        PorterStemmer stemmer = new PorterStemmer();
        for (String word : wordList) {
            stemmer.setCurrent(word);
            stemmer.stem();
            String stemmedWord = stemmer.getCurrent();
            newList.add(stemmedWord);
        }
        return newList;
    }

}
