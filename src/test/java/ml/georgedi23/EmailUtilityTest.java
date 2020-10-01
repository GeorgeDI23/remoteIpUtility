package ml.georgedi23;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class EmailUtilityTest {

    @Mock
    private TransportWrapper transportWrapper;

    EmailUtility emailUtility;
    Map<String, String> givenEmailSetup;
    Session givenSession;

    @Before
    public void setUp() throws Exception {
        this.emailUtility = new EmailUtility("targetTest",
                "sourceTest", "testCreds", transportWrapper);
        this.givenEmailSetup = new HashMap<>();
        givenEmailSetup.put("subject", "testSubject");
        givenEmailSetup.put("bodyText", "testBodyText");
        givenEmailSetup.put("sourceEmail", "testSourceEmail");
        givenEmailSetup.put("targetEmail", "testTargetEmail");
        givenSession = emailUtility.getGmailSession("testSessionSource",
                "testSessionCredentials");
    }

    @Test
    public void sendEmailCallTest() throws MessagingException {
        //Given - default emailUtility

        //When
        emailUtility.sendEmail("test", "test");

        //Then
        Mockito.verify(transportWrapper).send(any(MimeMessage.class));
    }

    @Test
    public void sendEmailSubjectTest() throws MessagingException {
        //Given
        String expected = "givenTestSubject";

        //When
        emailUtility.sendEmail(expected, "test");
        ArgumentCaptor<Message> argumentCaptor = ArgumentCaptor.forClass(Message.class);
        Mockito.verify(transportWrapper).send(argumentCaptor.capture());
        Message capturedArgument = argumentCaptor.getValue();
        String actual = capturedArgument.getSubject();

        //Then
        assertEquals(expected, actual);
    }

    @Test
    public void sendEmailBodyTest() throws MessagingException, IOException {
        //Given
        String expected = "givenTestBody";

        //When
        emailUtility.sendEmail("test", expected);
        ArgumentCaptor<Message> argumentCaptor = ArgumentCaptor.forClass(Message.class);
        Mockito.verify(transportWrapper).send(argumentCaptor.capture());
        Message capturedArgument = argumentCaptor.getValue();
        String actual = capturedArgument.getContent().toString();

        //Then
        assertEquals(expected, actual);
    }

    @Test
    public void sendEmailSessionTest() throws MessagingException, IOException {
        //Given
        String expected = "sourceTest";

        //When
        emailUtility.sendEmail("test", "test");
        ArgumentCaptor<Message> argumentCaptor = ArgumentCaptor.forClass(Message.class);
        Mockito.verify(transportWrapper).send(argumentCaptor.capture());
        Message capturedArgument = argumentCaptor.getValue();
        String actual = capturedArgument.getFrom()[0].toString();

        //Then
        assertEquals(expected, actual);
    }

    @Test
    public void sendEmailErrorTest() throws MessagingException, IOException {
        //Given
        String expected = "Transmission failure: javax.mail.MessagingException";
        Mockito.doThrow(new MessagingException()).when(transportWrapper).send(any(MimeMessage.class));

        //When
        String actual = emailUtility.sendEmail("test", "test");

        //Then
        assertEquals(expected, actual);
    }

    @Test
    public void sendEmailNoErrorTest() throws MessagingException {
        //Given
        String expected = "Transmission Successful";

        //When
        String actual = emailUtility.sendEmail("test", "test");

        //Then
        assertEquals(expected, actual);
    }

    @Test
    public void createMimeMessageSourceTest() throws MessagingException {
        //Given
        String expected = givenEmailSetup.get("sourceEmail");

        //When
        MimeMessage message = emailUtility.createMimeMessage(givenEmailSetup, givenSession);
        String actual = message.getFrom()[0].toString();

        //Then
        assertEquals(expected, actual);
    }

    @Test
    public void createMimeMessageTargetTest() throws MessagingException {
        //Given
        String expected = givenEmailSetup.get("targetEmail");

        //When
        MimeMessage message = emailUtility.createMimeMessage(givenEmailSetup, givenSession);
        String actual = message.getAllRecipients()[0].toString();

        //Then
        assertEquals(expected, actual);
    }

    @Test
    public void createMimeMessageSingleTargetTest() throws MessagingException {
        //Given
        int expected = 1;

        //When
        MimeMessage message = emailUtility.createMimeMessage(givenEmailSetup, givenSession);
        int actual = message.getAllRecipients().length;

        //Then
        assertEquals(expected, actual);
    }

    @Test
    public void createMimeMessageSubjectTest() throws MessagingException {
        //Given
        String expected = givenEmailSetup.get("subject");

        //When
        MimeMessage message = emailUtility.createMimeMessage(givenEmailSetup, givenSession);
        String actual = message.getSubject();

        //Then
        assertEquals(expected, actual);
    }

    @Test
    public void createMimeMessageBodyTest() throws MessagingException, IOException {
        //Given
        String expected = givenEmailSetup.get("bodyText");

        //When
        MimeMessage message = emailUtility.createMimeMessage(givenEmailSetup, givenSession);
        String actual = message.getContent().toString();

        //Then
        assertEquals(expected, actual);
    }

    @Test
    public void getGmailSessionHostTest() {
        //Given
        String expected = "smtp.gmail.com";

        //When
        Properties properties = givenSession.getProperties();
        String actual = properties.getProperty("mail.smtp.host");

        //Then
        assertEquals(expected, actual);
    }

    @Test
    public void getGmailSessionPortTest() {
        //Given
        String expected = "465";

        //When
        Properties properties = givenSession.getProperties();
        String actual = properties.getProperty("mail.smtp.port");

        //Then
        assertEquals(expected, actual);
    }
    @Test
    public void getGmailSessionSSLTest() {
        //Given
        String expected = "true";

        //When
        Properties properties = givenSession.getProperties();
        String actual = properties.getProperty("mail.smtp.ssl.enable");

        //Then
        assertEquals(expected, actual);
    }

    @Test
    public void getGmailSessionAuthTest() {
        //Given
        String expected = "true";

        //When
        Properties properties = givenSession.getProperties();
        String actual = properties.getProperty("mail.smtp.auth");

        //Then
        assertEquals(expected, actual);
    }

    @Test
    public void getGmailSessionSocketFactoryTest() {
        //Given
        String expected = "javax.net.ssl.SSLSocketFactory";

        //When
        Properties properties = givenSession.getProperties();
        String actual = properties.getProperty("mail.smtp.socketFactory.class");

        //Then
        assertEquals(expected, actual);
    }
}