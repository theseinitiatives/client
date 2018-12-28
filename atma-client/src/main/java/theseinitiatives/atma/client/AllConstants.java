package theseinitiatives.atma.client;

public class AllConstants {
    public static final String BASE_URL = "https://atma.theseforall.org";
    public static final String FLAG_SUCCESS = "success";
    public static final String FLAG_FAILED = "failed";
    public static final String FLAG_SEPARATOR = "##";

    public static Boolean MAY_PROCEED = false;
    public static String params = null;

    public static final String MAX_ROW_PER_PAGE = "20";

    public static String convertToDDMMYYYY(String data){
        if(!data.contains("-"))
            return data;
        String[]date = data.split("-");
        if(date[0].length()<4)
            return data;
        return String.format("%s-%s-%s",date[2],date[1],date[0]);
    }
    public static String convertToYYYYMMDD(String data){
        if(!data.contains("-"))
            return data;
        String[]date = data.split("-");
        if(date[2].length()<4)
            return data;
        return String.format("%s-%s-%s",date[2],date[1],date[0]);
    }
}
