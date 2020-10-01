package ml.georgedi23;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;

public class TransportWrapper {

    public void send(Message message) throws MessagingException {
        Transport.send(message);
    }
}
