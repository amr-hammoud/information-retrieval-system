package Code;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Project3 {
//        generate tf
//        boolean file
//        generate tfidf

    private ArrayList<String> sfxFilesList;
    private String sfxFilesPath, invertedFilesPath;
    private ArrayList<Double[]> tfidf;
    private ArrayList<String> terms;
    private ArrayList<String> docs;
    private ArrayList<Integer> docFreq;
    private Double[] tf;
    private int num_docs;

    public Project3(ArrayList<String> sfxFilesList, String sfxFilesPath, String invertedFilesPath) {
        this.invertedFilesPath = invertedFilesPath;
        this.sfxFilesPath = sfxFilesPath;
        this.sfxFilesList = sfxFilesList;
        this.num_docs = sfxFilesList.size();
        this.tf = new Double[num_docs];
        this.terms = new ArrayList<>();
        this.docs = new ArrayList<>();
        this.docFreq = new ArrayList<>();
        this.tfidf = new ArrayList<>();
    }

    public void run() {
        try {
            this.calculateTFAndDF();
            this.printTFAndDF();
            this.createBooleanAndTFIDFFiles();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    private void calculateTFAndDF() throws IOException {
        int i = 0;

//        Iterate through each file
        for (String fileName : this.sfxFilesList) {

//            Add file name to the docs list
            docs.add(fileName.split(".sfx")[0]);

//            Read the wordlist from the file
            String inputFilePath = this.sfxFilesPath + fileName;
            FileManager stpFile = new FileManager(inputFilePath);
            ArrayList<String> wordList = stpFile.getWords();

//            Iterate through each word
            for (String word : wordList) {
//                If the word is already in the terms list:
//                  Update the TF (term frequency) for the current document.
//                  Increment the document frequency (DF) for the term.
                if(terms.contains(word)){
                    tf = tfidf.get(terms.indexOf(word));
                    if(tf[i] != null)
                        tf[i]++;
                    else {
                        tf[i] = 1.0;
                        int lastDocFreqValue = docFreq.get(terms.indexOf(word));
                        docFreq.set(terms.indexOf(word), lastDocFreqValue + 1);
                    }
                    tfidf.set(terms.indexOf(word), tf);
                }
//              If the word is not in the terms list:
//                  Add the word to the terms list.
//                  Initialize a new array for TF for the current document.
//                  Increment the DF for the term.
                else {
                    terms.add(word);
                    tf = new Double[num_docs];
                    tf[i] = 1.0;
                    tfidf.add(tf);
                    docFreq.add(1);
                }
            }

//            Logging for tracking
            System.out.println("    --- " + fileName);
            i++;
        }
    }

    private void createBooleanAndTFIDFFiles() throws IOException {
        // Create boolean and TFIDF inverted files
        createInvertedFile("booleanInvertedFile.txt", generateBooleanInvertedFileContent());
        createInvertedFile("TFIDFInvertedFile.txt", generateTFIDFInvertedFileContent());
    }

    private void createInvertedFile(String fileName, String content) throws IOException {
        String filePath = invertedFilesPath + fileName;

        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.print(content);
        }
    }

    private String generateBooleanInvertedFileContent() {
        StringBuilder content = new StringBuilder();

        content.append(" \t\t ");
        for (Object item : terms) {
            content.append(item).append(" \t ");
        }
        content.append("\n");
        for (int i = 0; i < num_docs; i++) {
            content.append(docs.get(i)).append(" \t ");
            for (int j = 0; j < terms.size(); j++) {
                if(tfidf.get(j)[i] !=  null) {
                    content.append(Math.round(tfidf.get(j)[i])).append(" \t\t ");
                } else {
                    content.append(0) .append(" \t\t ");
                }

            }
            content.append("\n");
        }

        return content.toString();
    }

    private String generateTFIDFInvertedFileContent() {
        StringBuilder content = new StringBuilder();

        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);

        content.append(" \t ");
        for (Object item : terms) {
            content.append(item).append(" \t ");
        }
        content.append("\n");
        for (int i = 0; i < num_docs; i++) {
            content.append(docs.get(i)).append(" \t ");
            for (int j = 0; j < terms.size(); j++) {
                if(tfidf.get(j)[i] != null) {
                    double value = tfidf.get(j)[i] * Math.log10((double)num_docs/docFreq.get(j));
                    content.append(df.format(value)).append(" \t ");
                } else {
                    content.append(0.0) .append(" \t ");
                }

            }
            content.append("\n");
        }

        System.out.println("\n____________ TFIDF ___________________");
        System.out.print(content);
        System.out.println("______________________________________\n\n");

        return content.toString();
    }

    private void printTFAndDF() {
//                Print Term Frequency Table
        System.out.println("\n______________  TF  __________________");
        System.out.print("\t");
        for(int i=0; i<terms.size(); i++){
            System.out.print(terms.get(i) + " \t");
        }
        System.out.print("\n");
        for(int i=0; i<tf.length; i++) {
            System.out.print(docs.get(i) + " \t");
            for(int j=0; j<tfidf.size(); j++){
                if(tfidf.get(j)[i] != null) {
                    System.out.print(Math.round(tfidf.get(j)[i]) + " \t");
                } else {
                    System.out.print(0 + " \t");
                }

            }
            System.out.print("\n");
        }
//        System.out.print("\nDF \t\t");
//        for(int x=0; x<docFreq.size(); x++)
//            System.out.print(docFreq.get(x) + " \t\t");
//        System.out.print("\n");
        System.out.println("______________________________________\n");
    }

    
}
