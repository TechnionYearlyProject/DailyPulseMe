package backend.NLP;


import backend.entity.Event;
import backend.entity.EventTag;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;

import static backend.entity.EventTag.Rest;
import static backend.entity.EventTag.Sport;

public class NLP {


    public static EventTag RunNLP(String eventName) throws IOException {


        String line="";
        String lastLine="";

            // set up the command and parameter
            //String pythonScriptPath = "C:\\Users\\najee\\PycharmProjects\\untitled\\venv\\Scripts";
        //Path currentRelativePath = Paths.get("");
        //String s = currentRelativePath.toAbsolutePath().toString();
        //System.out.println("Current relative path is: " + s);
            ProcessBuilder pb = new ProcessBuilder("D:\\home\\python364x86\\python.exe","D:\\home\\python364x86\\NLP.py","\""+eventName+"\"");
            Process p = pb.start();
          //  System.out.println("hello");
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
        //System.out.println("hello");
        while((line = in.readLine())!=null){
            //System.out.println(line);
            lastLine = line;
        }

        //System.out.println(lastLine);
            //System.out.println(ret);
            //String[] cmd = new String[2];
            //cmd[0] = "cd"; // check version of installed python: python -V
            //cmd[1] = pythonScriptPath;
            //cmd[2]=eventName;
            // create runtime to execute external command
            //Runtime rt = Runtime.getRuntime();
            //Process pr = rt.exec("python NLP.py \"playing football\"");
            //String[] cmd2 = new String[3];
            //cmd2[0] = "python"; // check version of installed python: python -V
            //cmd2[1] = pythonScriptPath;
            //cmd2[2] = eventName;
            //Runtime rt2 = Runtime.getRuntime();
            //Process pr2 = rt2.exec(cmd2);
            // retrieve output from python script
            //BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            // read the output
            //line =  bfr.readLine();
//
            //  while ((line = bfr.readLine()) != null) {
            //       tmp += line;
            // }

            //System.out.println(ret);
            //System.out.println("HELLL" + ret);
            //return ret == 1 ? Sport : Rest;

       // return ret==1 ? Sport : Rest;
        return lastLine.compareTo("1")==0 ? Sport : Rest;
    }

    public static void TestingRunNLP(){

        try {
            System.out.println(RunNLP("exam time gym").toString());
        }
        catch (Exception e){
            System.out.println(e.toString());
        }

    }
}



