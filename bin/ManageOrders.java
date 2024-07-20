import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class ManageOrders {
    public static void main(String[] args) throws Throwable {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String endpoint = "http://localhost:80/gilhari/v1/anand.Order"; // Replace with your actual endpoint

        try {
            HttpPost postRequest = new HttpPost(endpoint);

            // Create Timestamp for order_date
            String orderDateString = "2024-07-10 12:00:00";  // Replace with your actual order date string
            Timestamp orderDate = Timestamp.valueOf(orderDateString);
            long orderDateMillis = orderDate.getTime();

            // Manually construct the JSON string
            String json = "{" +
                    "\"entity\": {" +
                    "\"order_id\": 1," +
                    "\"customer_id\": 1," +
                    "\"order_date\": " + orderDateMillis + "," +
                    "\"status\": \"pending\"" +
                "}" +
            "}";

            StringEntity entityBody = new StringEntity(json);
            entityBody.setContentType("application/json");
            postRequest.setEntity(entityBody);
            postRequest.addHeader("Content-Type", "application/json");

            // Debug print statements
            System.out.println("Endpoint: " + endpoint);
            System.out.println("Request JSON: " + json);
            System.out.println("Request Headers: " + postRequest.getAllHeaders());

            HttpResponse response = httpClient.execute(postRequest);

            // Process the response
            int statusCode = response.getStatusLine().getStatusCode();
            String responseBody = EntityUtils.toString(response.getEntity());

            System.out.println("Response Code: " + statusCode);
            System.out.println("Response Body: " + responseBody);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
