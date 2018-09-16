package tgwofficial.atma.client.Utils;

import tgwofficial.atma.client.sync.ApiService;
import tgwofficial.atma.client.sync.RetrofitClient;

public class ApiUtils {
    public static final String BASE_URL = "https://atma.theseforall.org/";

    public static ApiService getSOService() {
        return RetrofitClient.getClient(BASE_URL).create(ApiService.class);
    }
}
