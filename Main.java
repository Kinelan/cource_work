import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    //1-4 -> 2000 2250
    //5   -> 8000 9000

    public static void main(String[] argv){

        HashMap<String, List<Integer>> result = new HashMap<String, List<Integer>>();
        int startIndex1To4 = 2000;
        int endIndex1To4 = 2250;
        int startIndex5 = 8000;
        int endIndex5 = 9000;

        for(int i=2000;i<=2250;i++){
            String fileName = "";
            String textNormalized = "";
            for(int j=0;++j<=4;){
                fileName = String.format("D:\\kpi\\3 COURSE 2 SEMESTR\\PAR\\Cource\\aclImdb\\aclImdb\\train\\neg\\%d_%d.txt", i, j); //format
                try{
                    //multiple whitespaces ??? FIX IT
                    //maybe should sort ???
                    textNormalized = new String(Files.readAllBytes(Paths.get(fileName)))
                            .toLowerCase()
                            .replaceAll("[\\.\\,\\?\\!\\-\\t\\ {1,}]", " ")
                            .replaceAll("[^a-z\\ ]", "");

                    //System.out.println(textNormalized);
                    String [] wordsUnnormalized = textNormalized.split(" ");

                    for(String w : wordsUnnormalized){
                        //if there was such word than add new int to list
                        if(result.containsKey(w)){
                            List<Integer> obsoleteWordPosition = result.get(w);
                            obsoleteWordPosition.add(i);
                            result.put(w, obsoleteWordPosition);
                        }
                        //else create new
                        else{
                            List<Integer> initialList = new ArrayList<Integer>();
                            initialList.add(i);
                            result.put(w, initialList);
                        }
                    }

                    break;
                } catch(IOException e){
                    //System.out.println(fileName + " not found");
                    //if not found then try next j
                }

            }
        }

        result.entrySet().forEach(entry -> {
            System.out.println(entry.getKey()+", "+entry.getValue());
        });

        return;
    }
}