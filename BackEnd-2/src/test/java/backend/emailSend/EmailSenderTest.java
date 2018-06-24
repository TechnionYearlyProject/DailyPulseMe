package backend.emailSend;

import backend.entity.Subscription;
import org.junit.Test;

import java.util.ArrayList;

public class EmailSenderTest {
    @Test
    public void emptyMailTest(){
        EmailSender.sendMail(new ArrayList<>());
    }
    @Test
    public void MailTest(){
        ArrayList<Subscription> to=new ArrayList<>();
        to.add(new Subscription("m_1995_abd@hotmail.com","moh"));
        EmailSender.sendMail(to);
    }
}
