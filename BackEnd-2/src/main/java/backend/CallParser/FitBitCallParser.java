package backend.CallParser;

import backend.entity.AppUser;
import backend.entity.Pulse;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FitBitCallParser implements CallParser{

    public List<Pulse> getPulses(AppUser user, String startTime, String endTime, String minInMs) {

        String js;
        String accessToken=user.getAccessToken();
        List<Pulse> pulses=new ArrayList<>();

        //System.out.println(accessToken);


        //TODO: CHANGE TIME AND DATE IN THE REQUEST USING startTime AND endTime
        String date = "2018-" + "June-" ;

        //GET https://api.fitbit.com/1/user/-/activities/heart/date/[date]/[end-date]/[detail-level]/time/[start-time]/[end-time].json

        HttpGet get = new HttpGet("https://api.fitbit.com/1/user/-/activities/heart/date/today/1d.json");
        get.addHeader("Authorization" , "Bearer "+accessToken);
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = null;
        try {
            response = client.execute(get);

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
            }
            //OLD WAY:
            //Gson gson = new Gson();
            //heartrate name = gson.fromJson(content.toString(),heartrate.class);
            //TODO: use regex to extract the pulses from the response.

            Pattern p=Pattern.compile("\"value\": [0-9]+"); //using regex we get the pulses and save them
            Matcher m=p.matcher(content.toString());
            int counter =0;
            while(m.find() ) {
                if(counter == 0){
                    counter++;
                    continue;
                }
                Pulse tmp=new Pulse((int)Double.parseDouble(m.group()));
                pulses.add(tmp);
            }

        }
        catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            get.releaseConnection();
        }
        return  pulses;

    }

    //TODO: implement this method
    @Override
    public boolean verifyAndRefresh(AppUser user) {
        return false;
    }
    //TODO: implement this method
    @Override
    public String refreshToken(AppUser user) {
        return null;
    }


}
