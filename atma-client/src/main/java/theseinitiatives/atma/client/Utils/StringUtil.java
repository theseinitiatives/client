package theseinitiatives.atma.client.Utils;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.util.Date;

public class StringUtil {

    public static String humanizes(String value){

        String caps = StringUtils.capitalize(value);
        return caps;
    }

    public  static Timestamp dateNow() {
      //  Date date = new Date();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp;
    }

}
