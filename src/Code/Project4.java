package Code;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Project4 {
//        Query
//        Calculate cosine
//        Rank Docs
    String query;

    public Project4(String query) {
        this.query = query;
    }

    public void run(ArrayList<String> stopWordsList, Project3 p3) throws IOException {

        ArrayList<String> queryWordsList = new ArrayList<>();
        String[] queryArray = query.split(" ");
        Collections.addAll(queryWordsList, queryArray);


        // Step 1: Remove Stopwords from the query
        ArrayList<String> queryWords = Project1.deleteStopWord(stopWordsList,queryWordsList);

        // Step 2: Stem the query words
        for (String queryWord : queryWords) {
            // Apply Porter's stemming algorithm
//            queryWord = ??
        }

        // Step 3: Calculate TFIDF for query words
        // Read the TFIDFInvertedFile
//        StringBuilder content = new StringBuilder();
//        BufferedReader reader = new BufferedReader(new FileReader("src\\pkg\\CSCI512\\TFIDFInvertedFile.txt"));
//
//        String tfidfLine;
//        String[] words, docs, docFreqString, tfidfRow;
//        Integer[] docFreq;
//        Double[][] tfidf;

//
//        words = reader.readLine().split(" ");
//        reader.readLine();
//        docs = reader.readLine().split(" ");
//        reader.readLine();
//        docFreqString = reader.readLine().split(" ");
//        docFreq = new Integer[docFreqString.length];
//        for(int i=0; i<docFreqString.length; i++) {
//            docFreq[i] = Integer.parseInt(docFreqString[i]);
//        }
//        reader.readLine();
//        tfidf = new Double[docs.length][words.length];
//        for(int i=0; i<docs.length; i++) {
//            tfidfLine = reader.readLine();
//            tfidfRow = tfidfLine.split(" ");
//            for(int j=0; j<tfidfRow.length; j++){
//                tfidf[i][j] = Double.parseDouble(tfidfRow[j]);
//            }
//        }

        // Calculate query TFIDF
        Double[] queryTfidf;
        queryTfidf = new Double[p3.terms.size()];
        Arrays.fill(queryTfidf, 0.0);
        for(int i=0; i<queryWords.size(); i++) {
            for(int j=0; j<p3.terms.size(); j++)
                if(queryWords.get(i).equals(p3.terms.get(j)))
                    queryTfidf[j]++;
        }

        for(int i=0; i<queryTfidf.length; i++)
            queryTfidf[i] = queryTfidf[i] * Math.log10((double)p3.num_docs/p3.docFreq.get(i));

        // Calculate the cosine
        DecimalFormat df = new DecimalFormat("0.000");
        Double temp1, temp2 = 0.0, temp3;
        Double[] cosine = new Double[p3.docs.size()];
        for(int i=0; i<cosine.length; i++) {
            temp1 = temp3 = 0.0;
            for(int j=0; j<queryTfidf.length; j++) {
                temp1 = temp1 + (queryTfidf[j] * p3.tfidf.get(i)[j]);
                if(i==0)
                    temp2 = temp2 + (queryTfidf[j] * queryTfidf[j]);
                temp3 = temp3 + (p3.tfidf.get(i)[j] * p3.tfidf.get(i)[j]);
            }
            // In case the query has no words from words array
            if(temp2==0) {
                System.out.println("There are no relevant documents.");
                return;
            }
            if(temp3==0)
                cosine[i] = 0.0;
            else
                cosine[i] = temp1 / Math.sqrt(temp2 * temp3);
            String s = df.format(cosine[i]);
            cosine[i] = Double.parseDouble(s);
        }

        System.out.println("The retrieved documents in descending order are:");

        ArrayList<Element> elements = new ArrayList<>();
        for (int i = 0; i < cosine.length; i++) {
            elements.add(new Element(i, cosine[i]));
        }

        // Sort and print
        Collections.sort(elements);
        Collections.reverse(elements); // If you want reverse order
        for (Element element : elements) {
            if(element.value > 0.001)
                System.out.println(p3.docs.get(element.index) + " (" + element.value + ")");
        }
    }

}

class Element implements Comparable<Element> {
    int index;
    Double value;

    Element(int index, Double value){
        this.index = index;
        this.value = value;
    }

    @Override
    public int compareTo(Element e) {
        return Double.compare(this.value, e.value);
    }

}