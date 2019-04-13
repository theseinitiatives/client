package theseinitiatives.atma.client.Utils;

import theseinitiatives.atma.client.AllConstants;
import theseinitiatives.atma.client.sync.ApiService;
import theseinitiatives.atma.client.sync.RetrofitClient;

public class ApiUtils {

    public static ApiService getSOService() {
        return RetrofitClient.getClient(AllConstants.BASE_URL).create(ApiService.class);
    }
}
