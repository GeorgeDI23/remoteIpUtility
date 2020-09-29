package ml.georgedi23;

import org.junit.Before;
import org.junit.Test;
import java.util.regex.Pattern;
import static org.junit.Assert.*;

public class IPUtilityTest {

    public IPUtility ipUtility;

    @Before
    public void init(){
        ipUtility = new IPUtility();
    }

    @Test
    public void getPublicIpTest(){
        //Given - not perfect regex, but sufficient
        Pattern pattern = Pattern.compile("\\b\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\b");

        //When
        String ip = ipUtility.getPublicIp();
        boolean returnsValidIP = pattern.matcher(ip).matches();

        //Then
        assertTrue(returnsValidIP);
    }
}