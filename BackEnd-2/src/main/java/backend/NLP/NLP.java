package backend.NLP;


import backend.entity.Event;
import backend.entity.EventTag;

import java.io.*;
import java.nio.Buffer;

import static backend.entity.EventTag.Rest;
import static backend.entity.EventTag.Sport;

public class NLP {


    public static EventTag RunNLP(String eventName) throws IOException {

        String line="";
        try {
            // set up the command and parameter
            String lastLine="";
            ProcessBuilder pb = new ProcessBuilder("D:\\home\\python364x86\\python.exe","D:\\home\\python364x86\\NLP.py","\""+eventName+"\"");
            Process p = pb.start();BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while((line = in.readLine())!=null){
                lastLine += line;
            }
            return lastLine.contains("1")==true ? Sport : Rest;


        }
        catch (Exception e){
            System.out.println(e.toString());
        }

        return EventTag.Rest;

    }

    public static String TestingRunNLP(String eventName){

        String line="";

        try {
            String lastLine = "";
            ProcessBuilder pb = new ProcessBuilder("D:\\home\\python364x86\\python.exe", "D:\\home\\python364x86\\NLP.py", "\"" + eventName + "\"");
            Process p = pb.start();
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            InputStream error = p.getErrorStream();
            InputStreamReader isrerror = new InputStreamReader(error);
            BufferedReader bre = new BufferedReader(isrerror);
            String err="";
            while ((line = bre.readLine()) != null) {
                err+=line;
            }
            while ((line = in.readLine()) != null) {
                lastLine += line;

            }
            return "err"+err+ "line"+lastLine;

    }catch (Exception e){
            System.out.println(e.toString());
        }

        return "here";
    }
}



