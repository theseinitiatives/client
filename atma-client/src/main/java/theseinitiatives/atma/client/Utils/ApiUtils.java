package theseinitiatives.atma.client.Utils;

import theseinitiatives.atma.client.sync.ApiService;
import theseinitiatives.atma.client.sync.RetrofitClient;

public class ApiUtils {
    public static final String BASE_URL = "https://server.p4kloteng.com/";

    public static ApiService getSOService() {
        return RetrofitClient.getClient(BASE_URL).create(ApiService.class);
    }
}
