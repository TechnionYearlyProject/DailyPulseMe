package backend.googleFitApi;

import backend.entity.AppUser;
import backend.entity.Pulse;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GoogleCallParser {
    public static List<Pulse> parseCall(AppUser user,String startTime,String endTime , String bucket){
        List<Pulse> pulses=new ArrayList<>();
        String accessToken=user.getGoogleFitAccessToken();
        //// check if the access token has expired TODO
        HttpPost post=new HttpPost("https://www.googleapis.com/fitness/v1/users/me/dataset:aggregate");
        post.addHeader("Content-Type","application/json;encoding=utf-8");
        post.addHeader("Authorization" , "Bearer "+ accessToken );
        StringEntity str=null;
        try {
            str = new StringEntity("{\n" +
                    "  \"aggregateBy\": [{\n" +
                    "    \"dataTypeName\": \"com.google.heart_rate.bpm\"\n" +
                    "  }],\n" +
                    "  \"bucketByTime\": { \"durationMillis\": "+ bucket+ " },\n" +
                    "  \"startTimeMillis\": "+ startTime +",\n" +
                    "  \"endTimeMillis\": "+endTime +"\n" +
                    "}");

        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        post.setEntity(str);
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = null;
        try {
            response = client.execute(post);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            (response.getEntity().getContent())
                    )
            );
            StringBuilder content = new StringBuilder();
            String line;
            while (null != (line = br.readLine())) {
                content.append(line);
              //  System.out.println(line);
            }
            //  System.out.println(content.toString());

            Pattern p=Pattern.compile("([0-9]+)\\.([0-9])");
            Matcher m=p.matcher(content.toString());
            int counter=0;
            while(m.find() ) {
                if(counter%3==0){
                    Pulse tmp=new Pulse((int)Double.parseDouble(m.group()));
                    pulses.add(tmp);
                }

                counter++;
            }

        }
        catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            post.releaseConnection();
        }
        return pulses;
    }
}
