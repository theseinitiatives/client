package tgwofficial.atma.client.Utils;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {

    public static String humanizes(String value){

        String caps = StringUtils.capitalize(value);
        return caps;
    }
}
