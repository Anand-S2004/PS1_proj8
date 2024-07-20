import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Scanner;

public class ManageOrderItems {
    private static final String BASE_URL = "http://localhost:80/gilhari/v1/anand.OrderItem";
    private static final String BASE_URL_PUT = "http://localhost:80/gilhari/v1/anand.OrderItem/updateEntity";

    public static void main(String[] args) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Enter API operation to perform (1 = POST, 2 = GET, 3 = DELETE, 4 = PUT):");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    insertOrderItem(httpClient, scanner);
                    break;
                case 2:
                    System.out.println("Enter order_item_id to fetch:");
                    int orderItemIdGet = scanner.nextInt();
                    scanner.nextLine(); // Consume newline left-over
                    getOrderItem(httpClient, orderItemIdGet);
                    break;
                case 3:
                    System.out.println("Enter order_item_id to delete:");
                    int orderItemIdDelete = scanner.nextInt();
                    scanner.nextLine(); // Consume newline left-over
                    deleteOrderItem(httpClient, orderItemIdDelete);
                    break;
                case 4:
                    System.out.println("Enter order_item_id to update:");
                    int orderItemIdPut = scanner.nextInt();
                    scanner.nextLine(); // Consume newline left-over
                    updateOrderItem(httpClient, orderItemIdPut, scanner);
                    break;
                default:
                    System.out.println("Invalid choice. Exiting.");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                httpClient.close();
                scanner.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void insertOrderItem(CloseableHttpClient httpClient, Scanner scanner) throws IOException {
        System.out.println("Enter order item details:");
        System.out.print("order_item_id: ");
        int orderItemId = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over
        System.out.print("product_id: ");
        int productId = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over
        System.out.print("price: ");
        int price = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over
        System.out.print("quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over
        System.out.print("order_id: ");
        int orderId = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over

        HttpPost postRequest = new HttpPost(BASE_URL);

        String json = "{" +
                "\"entity\": {" +
                "\"order_item_id\": " + orderItemId + "," +
                "\"product_id\": " + productId + "," +
                "\"price\": " + price + "," +
                "\"quantity\": " + quantity + "," +
                "\"order_id\": " + orderId +
                "}" +
                "}";

        StringEntity entity = new StringEntity(json);
        entity.setContentType("application/json");
        postRequest.setEntity(entity);
        postRequest.addHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(postRequest);

        int statusCode = response.getStatusLine().getStatusCode();
        String responseBody = EntityUtils.toString(response.getEntity());

        System.out.println("Insert Order Item - Response Code: " + statusCode);
        System.out.println("Insert Order Item - Response Body: " + responseBody);
    }

    private static void getOrderItem(CloseableHttpClient httpClient, int orderItemId) throws IOException {
        HttpGet getRequest = new HttpGet(BASE_URL + "?filter=order_item_id=" + orderItemId);
        getRequest.addHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(getRequest);

        int statusCode = response.getStatusLine().getStatusCode();
        String responseBody = EntityUtils.toString(response.getEntity());

        System.out.println("Get Order Item - Response Code: " + statusCode);
        System.out.println("Get Order Item - Response Body: " + responseBody);
    }

    private static void deleteOrderItem(CloseableHttpClient httpClient, int orderItemId) throws IOException {
        HttpDelete deleteRequest = new HttpDelete(BASE_URL + "?filter=order_item_id=" + orderItemId);
        deleteRequest.addHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(deleteRequest);

        int statusCode = response.getStatusLine().getStatusCode();
        String responseBody = EntityUtils.toString(response.getEntity());

        System.out.println("Delete Order Item - Response Code: " + statusCode);
        System.out.println("Delete Order Item - Response Body: " + responseBody);
    }

    private static void updateOrderItem(CloseableHttpClient httpClient, int orderItemId, Scanner scanner) throws IOException {
        System.out.println("Enter updated order item details:");
        System.out.print("product_id: ");
        int productId = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over
        System.out.print("price: ");
        int price = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over
        System.out.print("quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over
        System.out.print("order_id: ");
        int orderId = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over

        HttpPut putRequest = new HttpPut(BASE_URL_PUT);

        String json = "{" +
                "\"entity\": {" +
                "\"order_item_id\": " + orderItemId + "," +
                "\"product_id\": " + productId + "," +
                "\"price\": " + price + "," +
                "\"quantity\": " + quantity + "," +
                "\"order_id\": " + orderId +
                "}" +
                "}";

        StringEntity entity = new StringEntity(json);
        entity.setContentType("application/json");
        putRequest.setEntity(entity);
        putRequest.addHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(putRequest);

        int statusCode = response.getStatusLine().getStatusCode();
        String responseBody = EntityUtils.toString(response.getEntity());

        System.out.println("Update Order Item - Response Code: " + statusCode);
        System.out.println("Update Order Item - Response Body: " + responseBody);
    }
}
