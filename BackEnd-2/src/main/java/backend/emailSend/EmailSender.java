package backend.emailSend;

import backend.entity.Subscription;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender {

    private static String USER_NAME = "dailypulseme18";  // GMail user name (just the part before "@gmail.com")
    private static String PASSWORD = "Dailypulseme2018"; // GMail password

    public static void sendMail(ArrayList<Subscription> to) {
        if(to.size() == 0) {
            return;
        }
        String from = USER_NAME;
        String pass = PASSWORD;
        // list of recipient email addresses
        String subject = "DailyPulseMe weekly reminder";
        String body = "We hope that you stayed healthy this week!\nCome check our website https://dailypulse.azurewebsites.net/ to see if you have been doing well this week too!\n We know that you can do it!";
        ArrayList<Subscription> oneRec =  new ArrayList<Subscription>();
        for(Subscription sub : to) {
            oneRec.add(sub);
            sendFromGMail(from, pass,oneRec , subject, "Hello "+sub.getName()+",\n"+body);
            oneRec.remove(0);
            System.out.println("GOT HERE - SEND EMAILS");
        }
    }

    public static void sendFromGMail(String from, String pass, ArrayList<Subscription> to, String subject, String body) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.size()];

            // To get the array of addresses
            for( int i = 0; i < to.size(); i++ ) {
                toAddress[i] = new InternetAddress(to.get(i).getEmail());
            }

            System.out.println("to adress length "+ toAddress.length);
            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }
}