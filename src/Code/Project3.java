package Code;

import java.io.IOException;
import java.util.ArrayList;

public class Project3 {
//        generate tf
//        boolean file
//        generate tfidf

    private ArrayList<String> sfxFilesList;
    private String sfxFilesPath;
    private ArrayList<Double[]> tfidf;
    private ArrayList<String> terms;
    private ArrayList<String> docs;
    private ArrayList<Integer> docFreq;
    private Double[] tf;
    private int num_docs;

    public Project3(ArrayList<String> sfxFilesList, String sfxFilesPath) {
        this.sfxFilesPath = sfxFilesPath;
        this.sfxFilesList = sfxFilesList;
        this.num_docs = sfxFilesList.size();
        this.tf = new Double[num_docs];
        this.terms = new ArrayList<>();
        this.docs = new ArrayList<>();
        this.docFreq = new ArrayList<>();
        this.tfidf = new ArrayList<>();
    }

    public void generateInvertedFiles() throws IOException {
        //        Generate Table Frequency Table

        int i = 0;
        for (String fileName : this.sfxFilesList) {
            System.out.println("    --- " + fileName);
            String inputFilePath = this.sfxFilesPath + fileName;
            docs.add(fileName.split(".sfx")[0]);
            FileManager stpFile = new FileManager(inputFilePath);
            ArrayList<String> wordList = stpFile.getWords();

            for (String word : wordList) {
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
                else {
                    terms.add(word);
                    tf = new Double[num_docs];
                    tf[i] = 1.0;
                    tfidf.add(tf);
                    docFreq.add(1);
                }

            }
            i++;





//            String outputFilePath = sfxFilesPath + fileName.split(".sfx")[0] + ".txt";
//            FileManager.writeFile(outputFilePath, FileManager.toFileContent(result)); // Write result to sfx file
        }
        System.out.println("\n_______________________________");
        System.out.print("\t\t");
        for(int x=0; x<terms.size(); x++){
            System.out.print(terms.get(x) + " \t");
        }
        System.out.print("\n");
        for(int x=0; x<tf.length; x++) {
            System.out.print(docs.get(x) + " \t");
            for(int y=0; y<tfidf.size(); y++){
                System.out.print(tfidf.get(y)[x] + " \t");
            }
            System.out.print("\n");
        }
        System.out.print("\nDF \t\t");
        for(int x=0; x<docFreq.size(); x++)
            System.out.print(docFreq.get(x) + " \t\t");
        System.out.print("\n");
        System.out.println("_______________________________\n\n");

//        Generate Boolean File

//        Calculate TFIDF


//        Generate Inverted File

    }

    
}
