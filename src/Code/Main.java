package Code;

import java.util.ArrayList;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    static String stopWordsFilePath = "src\\sourceFiles\\stopWords.txt";
    static String txtFilesPath = "src\\txtFiles\\";
    static String stpFilesPath = "src\\stpFiles\\";
    static String sfxFilesPath = "src\\sfxFiles\\";

    public static void main(String[] args) throws IOException {

        System.out.println("- Started Processing Files\n");

        //Get Stop words
        System.out.println("  -- Started loading Stop Words");
        FileManager stopWordsFile = new FileManager(stopWordsFilePath);
        ArrayList<String> stopWordsList = stopWordsFile.getWords();
        System.out.println("  -- Stop Words list loaded\n");

        // Project 1
        FileManager txtFilesDir = new FileManager(txtFilesPath);
        ArrayList<String> txtFilesList = txtFilesDir.getFileList("doc", ".txt");
        System.out.println("  -- Started processing txt Files");

        for (String fileName : txtFilesList) {
            System.out.println("    --- " + fileName);
            String inputFilePath = txtFilesPath + fileName;
            String outputFilePath = stpFilesPath + fileName.split(".txt")[0] + ".stp";

            FileManager txtFile = new FileManager(inputFilePath);
            String wordListString = txtFile.getWordsString(" ");
            ArrayList<String> wordList = Project1.removePunctuation(wordListString); // Remove Punctuation
            wordList = Project1.deleteStopWord(stopWordsList, wordList); // Delete Stop Words
            FileManager.writeFile(outputFilePath, FileManager.toFileContent(wordList)); // Write result to stp file
        }
        System.out.println("  -- txt Files processed successfully\n");

        // Project 2
        System.out.println("  -- Started processing stp Files");
        FileManager stpFilesDir = new FileManager(stpFilesPath);
        ArrayList<String> stpFilesList = stpFilesDir.getFileList("doc", ".stp");
        for (String fileName : stpFilesList) {
            System.out.println("    --- " + fileName);
            String inputFilePath = stpFilesPath + fileName;
            String outputFilePath = sfxFilesPath + fileName.split(".stp")[0] + ".sfx";

            FileManager stpFile = new FileManager(inputFilePath);
            ArrayList<String> stpList = stpFile.getWords();
            ArrayList<String> result = Project2.stem(stpList); // Stem List of words
            FileManager.writeFile(outputFilePath, FileManager.toFileContent(result)); // Write result to sfx file
        }
        System.out.println("  -- stp Files processed successfully\n");

        // TODO Generate inverted File
        System.out.println("- Finished Processing Files\n");

        // Take query from user and return relevant information
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter your query: ");
        String query = scanner.nextLine();
        System.out.println("The system will process your query later (" + query + ")!");
    }
}
