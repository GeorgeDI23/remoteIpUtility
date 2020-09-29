package ml.georgedi23;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class IPUtility {

    private String iPAddressServiceURL = "http://bot.whatismyipaddress.com";

    public String getPublicIp() {
        try {
            URL url_name = new URL(iPAddressServiceURL);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url_name.openStream()));
            return bufferedReader.readLine();
        } catch (Exception e) {
            return "Unable to acquire IP Address: " + e.toString();
        }
    }
}
