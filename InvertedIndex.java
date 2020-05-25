import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class InvertedIndex {

    private final int threads;
    private final List<InputDir> inputs;
    private ContentNormalizer normalizer;
    
    private List<List<InputDir>> divideInputByThreads(){
        int filesToProcess = 0;
        for(InputDir id : inputs){
            filesToProcess += id.upperLimit-id.lowerLimit;
        }

        List<List<InputDir>> result = new ArrayList<List<InputDir>>();
        int lastAssignedInput = 0;
        int lastAssignedInputFileIndex = inputs.get(0).lowerLimit;

        for(int i=0;i<threads;i++){
            List<InputDir> currentThreadInputs = new ArrayList<InputDir>();

            //yetToAppoint should be relatively equal for every thread
            int yetToAppoint = filesToProcess/threads;

            while(yetToAppoint>0){

                //what if its last eleemt of inputs and yetToAppoint is bigger than its size!!!
                //shouldnt apply to the last element of inputs list
                if(yetToAppoint>inputs.get(lastAssignedInput).upperLimit - lastAssignedInputFileIndex){

                    currentThreadInputs.add(new InputDir(
                            lastAssignedInputFileIndex,
                            inputs.get(lastAssignedInput).upperLimit,
                            inputs.get(lastAssignedInput).folderPath
                    ));

                    yetToAppoint -= inputs.get(lastAssignedInput).upperLimit - inputs.get(lastAssignedInput).lowerLimit;
                    lastAssignedInput+=1;

                    //setting lower last assigned input index to next file`s lower limit
                    if(lastAssignedInput < inputs.size())
                        lastAssignedInputFileIndex = inputs.get(lastAssignedInput).lowerLimit;

                }else if (yetToAppoint == inputs.get(lastAssignedInput).upperLimit - lastAssignedInputFileIndex){

                    currentThreadInputs.add(new InputDir(
                            lastAssignedInputFileIndex, // check !!!
                            inputs.get(lastAssignedInput).upperLimit,
                            inputs.get(lastAssignedInput).folderPath
                    ));

                    yetToAppoint = 0;
                    lastAssignedInput += 1;
                    //check array bounds {if its not the last [input] obj than go to next}
                    if(lastAssignedInput < inputs.size())
                        lastAssignedInputFileIndex = inputs.get(lastAssignedInput).lowerLimit;
                }else{

                    currentThreadInputs.add(new InputDir(
                            lastAssignedInputFileIndex,
                            i!=threads-1 ? // if the last folder, than appoint up to the end
                                    lastAssignedInputFileIndex+yetToAppoint : inputs.get(lastAssignedInput).upperLimit, //not to the end of file
                            inputs.get(lastAssignedInput).folderPath
                    ));


                    //last assigned input stays unchanged, but index updates
                    lastAssignedInputFileIndex+=yetToAppoint+1;

                    yetToAppoint = 0;
                }

              //System.out.println("yettoAppoint = " + yetToAppoint +" thread = " + i +" info :"+currentThreadInputs.get(currentThreadInputs.size()-1).folderPath+" "+
                       //currentThreadInputs.get(currentThreadInputs.size()-1).lowerLimit+" "+
                       //currentThreadInputs.get(currentThreadInputs.size()-1).upperLimit);
            }
            result.add(currentThreadInputs);
        }
        return result;
    }
    
    }
