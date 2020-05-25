import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContentProcessorConcurrent implements Callable<Map<String, List<String>>>{

    //should be unique for any thread
    final List<InputDir> inputs;
    final ContentNormalizer normalizer;

    @Override
    public Map<String, List<String>> call() {
        //check();

        //parsing here, filling result map
        Map<String, List<String>> result = new HashMap<String, List<String>>();

        for(InputDir id : this.inputs){

            for(int i = id.lowerLimit;i<=id.upperLimit;i++){

                String fileName = "";
                String textNormalized = "";

                for(int j=0;++j<=4;){
                    fileName = String.format("%s%d_%d.txt",id.folderPath, i, j);
                    try{
                        //change syntax preprocessing in ContentNoramlizer
                        textNormalized = normalizer.normalizeContent(
                                new String(Files.readAllBytes(Paths.get(fileName)))
                        );

                        //System.out.println(textNormalized);
                        String [] wordsUnnormalized = textNormalized.split(" ");

                        for(String w : wordsUnnormalized){

                            //if there was such word than add new int to list,
                            //check for repeating FileIndexes in list
                            if(result.containsKey(w)){
                                List<String> obsoleteWordPosition = result.get(w);
                                Matcher matcher = Pattern.compile("\\\\(\\w*\\\\\\w*\\\\[0-9]{1,4}_[0-4]\\.txt)").matcher(fileName);
                                if(matcher.find()){
                                    //add index of file where the word is encountered
                                    //if it wasnt already in list
                                    if(!obsoleteWordPosition.contains(matcher.group(1))){
                                        obsoleteWordPosition.add(matcher.group(1));
                                    }
                                } else{
                                    obsoleteWordPosition.add("Invalid fileName");
                                }
                                result.put(w, obsoleteWordPosition);
                            }
                            //else create new
                            else{
                                List<String> initialList = new ArrayList<String>();
                                Matcher matcher = Pattern.compile("\\\\(\\w*\\\\\\w*\\\\[0-9]{1,4}_[0-4]\\.txt)").matcher(fileName);
                                if(matcher.find()){
                                    initialList.add(matcher.group(1));
                                }else{
                                    initialList.add("Invalid fileName");
                                }
                                result.put(w, initialList);
                            }
                        }

                        break;
                    }catch(IOException e){
                        //System.out.println(fileName + " not found");
                        //if not found then try next j
                    }
                }

            }

        }
        //returning processed map
        return result;
    }

    public ContentProcessorConcurrent(List<InputDir> inputs, ContentNormalizer normalizer){
        this.normalizer = normalizer;
        this.inputs = inputs;
    }

    private synchronized void check(){
        for(InputDir id:this.inputs){
            System.out.println(" info :"+id.folderPath+" "+
                    id.lowerLimit+" "+
                    id.upperLimit);
        }

    }

}

