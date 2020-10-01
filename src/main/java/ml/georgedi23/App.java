package ml.georgedi23;

import javax.mail.internet.AddressException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class App
{
    public static void main( String[] args ) throws AddressException {
        // Get environment variables
        String targetEmail = System.getenv("targetEmail");
        String sourceEmail = System.getenv("sourceEmail");
        String sourceCredentials = System.getenv("credentials");

        EmailUtility emailUtility = new EmailUtility(targetEmail, sourceEmail,
                sourceCredentials, new TransportWrapper());
        Timer timer = new Timer();
        IPUtility ipUtility = new IPUtility();

        // Run every 5 minutes
        timer.schedule( new TimerTask() {
            public void run(){
                String myCurrentIp = ipUtility.getPublicIp();
                String currentDateTime = new Date().toString();
                String statusResult = emailUtility.sendEmail("IP Address at " + currentDateTime, myCurrentIp);
                // ToDo - Log statusResult to file
            }
        }, 0, 5*60*1000);
    }
}
