package backend.NLP;


import java.io.*;

public class NLP {


    public static void RunNLP() throws IOException {
    // set up the command and parameter
     String pythonScriptPath = "C:\\Users\\USER\\Desktop\\NLP.py";
           String[] cmd = new String[3];
        cmd[0] = "python"; // check version of installed python: python -V
        cmd[1] = pythonScriptPath;
        cmd[2]="playing sport";
    // create runtime to execute external command
        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec(cmd);

    // retrieve output from python script
        BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String line = "";
        while ((line = bfr.readLine()) != null) {
            System.out.print("d");
    // display each output line form python script
            System.out.println(line);
        }
    }
}



