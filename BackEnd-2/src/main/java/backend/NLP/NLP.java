package backend.NLP;


import backend.entity.Event;
import backend.entity.EventTag;

import java.io.*;

import static backend.entity.EventTag.Rest;
import static backend.entity.EventTag.Sport;

public class NLP {


    public static EventTag RunNLP(String eventName) throws IOException {

        String line="";
        try {
            // set up the command and parameter
            String pythonScriptPath = "C:\\Users\\USER\\Desktop\\tria1.py";
            String[] cmd = new String[2];
            cmd[0] = "python"; // check version of installed python: python -V
            cmd[1] = pythonScriptPath;
            //   cmd[2]=eventName;
            // create runtime to execute external command
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec(cmd);

            // retrieve output from python script
            BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            // read the output
            line =  bfr.readLine();
//
            //  while ((line = bfr.readLine()) != null) {
            //       tmp += line;
            // }


        }
        catch (Exception e){
            System.out.println(e.toString());
        }
        return line.compareTo("1") == 0 ? Sport : Rest;
    }

    public static void TestingRunNLP(){

        try {
            System.out.println(RunNLP("NLP here").toString());
        }
        catch (Exception e){
            System.out.println(e.toString());
        }

    }
}



