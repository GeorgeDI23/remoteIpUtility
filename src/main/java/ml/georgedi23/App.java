package ml.georgedi23;

public class App 
{
    public static void main( String[] args ) {
        IPUtility ipUtility = new IPUtility();
        String myCurrentIp = ipUtility.getPublicIp();
        System.out.println( myCurrentIp );

        String targetEmail = System.getenv("targetEmail");
        String sourceEmail = System.getenv("sourceEmail");
        String sourceCredentials = System.getenv("credentials");

        // log to local file datetime + message return
    }
}
