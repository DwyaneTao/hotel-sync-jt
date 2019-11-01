import com.alibaba.druid.sql.visitor.functions.Char;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fangcang.hotel.sync.data.dto.HttpResponse;
import com.fangcang.hotel.sync.data.util.HttpClientUtil;
import com.fangcang.hotel.sync.data.util.Md5Util;
import com.fangcang.hotel.sync.jt.api.response.Response;
import com.fangcang.hotel.sync.jt.api.response.TokenResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

public class test {


    public static void main(String[] args) throws Exception {
//        String authorization = "Authorization";
//        String groupCode = "0003";
//        String hotelCode = "J571005";
//        String key = "D3174CE3";
//        String timestamp = "1569481803819";
//        String nonce = "79546";
//        String token = "KlSW2cAyiRd401gmuEwByIKZm3bngsK8wOyrNl1CjwItlDFGHuIj6skhwIHZJhsOsBe4LHFc6UUIUQ6zciD4hXV0htHU1RG5sKBoIoydDT8aBEVqjBi0yq3hJIP3iN9-t7r3a69xGmkG_-FE6THypKX_DlRiGRndAetIzcAflU17vcDNFAz7NPpLq981ygLYorcgjLUo6kiahe4gStLifqqshYtFZnstQNKs9e9qP9IGj4AOQRxrrYR3yZl5wackX0bFpM9vw5JoDYCWPfntJKsspFSBoWWmonKEXEWvkB2GUveTOS2IKioGcuAAh2QV";
//        String[] input = new String[] { token, timestamp, nonce, key};
//        String[] keys = arraySort(input);
//        String signature ="";
//        for (String key1 : keys) {
//            signature+=key1;
//            System.out.println(key1);
//        }
//
//
//        System.out.println(signature);
//        System.out.println(Md5Util.md5Encode(Md5Util.md5Encode(signature)));

//

        /**
         * 1.获得访问令牌
         */
        Response response = getToken();
        TokenResponse data = (TokenResponse)response.getData();
        String  token = data.getAccessToken();

        /**
         *2.刷新令牌
         */
//        token = "9qETCuk9voyjcY5-3VqoZEuOQrVunAM9NWofTU4Nrl3sXMdEg0b_rqEsTMEg92q7EXYfh8OIuUnnj7nt2o7QwxhYZg_zqlRAUGG5aTJVdxcfBRZDJVJjHhO7gIxPirHwkZBa38mI7vzJ7NajhhhJ9YMFT29DjxMSQmG7HU34wP2Ch3k5efgvSBvx_7zr_mZy5QLR8VGdPp2IkxYzpJ9p_8QOI_I57UTimVIftfW0Wex0c1ipgONkKFepfrX1RuupkVnQW7mDQtrv5TnwTW9t2cLwYd2JKo2e7-Z7l9RwQ6ENceh_0GJXsUJQwl-k_FVa";
        String authorization = "bearer " + token;
        System.out.println("authorization====="+authorization);
//        String groupCode = "0003";
//        String hotelCode = "J571005";
        String key = "D3174CE3";
        String timestamp = new Date().getTime()+"";
        System.out.println("timestamp====="+timestamp);
        String nonce = "79546";
        String signature =token+timestamp+nonce+key;
        char[] chars = signature.toCharArray();
        Arrays.sort(chars);
        String s = Arrays.toString(chars);
        System.out.println("s======="+s);
        System.out.println("signature====="+Md5Util.md5Encode(Md5Util.md5Encode(s)));


        //doGet(String url, Map<String, String> headerParams, String contentType,int connectTimeout, int socketTimeout)
//HttpResponse response = HttpClientUtil.doPost(URL, request.toJsonOrXmlString(), "application/json", 5000, 15000);

        Map<String,String> map = new HashMap<>();
        map.put("Authorization",authorization);
        map.put("Content-Type","application/json");
        map.put("timestamp",timestamp);
        map.put("nonce",nonce);
        map.put("signature",signature);

//
//        HttpResponse httpResponse = HttpClientUtil.doPost("http://112.17.72.71:8350/api/oauth2/RefreshToken", map, "application/json", 5000, 15000);
//        httpResponse.getContent();

        /**
         * 3.测试酒店基础信息
         */

        String  Url = "http://api/MinReserve/GetHotelList";

        HttpClientUtil.doPost(Url, map, "application/json", 5000, 15000);








    }







    /**
     * 1.获得访问令牌
     */
    public static Response  getToken () throws UnsupportedEncodingException {
        Response<TokenResponse> response = new Response();
        String url = "http://112.17.72.71:8350/api/oauth/GetToken";
        String clientId = "0003^J571005^0003HLtest ^060EE197FD9E4F32";
        clientId = URLEncoder.encode(clientId, "UTF-8");
        String clientSecret = "92df1a43efa4518da423d0ef2631caf9";
        clientSecret = URLEncoder.encode(clientSecret, "UTF-8");
        HttpResponse httpResponse = HttpClientUtil.doGet("http://112.17.72.71:8350/api/OAuth2/GetToken?clientId="+ clientId+"&clientSecret="+clientSecret);
        response = JSONObject.parseObject(httpResponse.getContent(), new TypeReference<Response<TokenResponse>>() {});
        return response;
    }

}
