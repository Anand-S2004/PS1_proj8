import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Scanner;

public class InsertCategories {

    private static final String BASE_URL = "http://localhost:80/gilhari/v1/anand.Category";
    private static final String BASE_URL_PUT = "http://localhost:80/gilhari/v1/anand.Category/updateEntity";

    public static void main(String[] args) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Enter API operation to perform (1 = POST, 2 = GET, 3 = DELETE, 4 = PUT):");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    insertCategory(httpClient, scanner);
                    break;
                case 2:
                    System.out.println("Enter category_id to fetch:");
                    int categoryIdGet = scanner.nextInt();
                    scanner.nextLine(); // Consume newline left-over
                    getCategory(httpClient, categoryIdGet);
                    break;
                case 3:
                    System.out.println("Enter category_id to delete:");
                    int categoryIdDelete = scanner.nextInt();
                    scanner.nextLine(); // Consume newline left-over
                    deleteCategory(httpClient, categoryIdDelete);
                    break;
                case 4:
                    System.out.println("Enter category_id to update:");
                    int categoryIdPut = scanner.nextInt();
                    scanner.nextLine(); // Consume newline left-over
                    updateCategory(httpClient, categoryIdPut, scanner);
                    break;
                default:
                    System.out.println("Invalid choice. Exiting.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
                scanner.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void insertCategory(CloseableHttpClient httpClient, Scanner scanner) throws IOException {
        System.out.println("Enter category details:");
        System.out.print("category_id: ");
        int categoryId = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over
        System.out.print("name: ");
        String name = scanner.nextLine();
        System.out.print("description: ");
        String description = scanner.nextLine();

        HttpPost postRequest = new HttpPost(BASE_URL);

        String json = "{" +
                "\"entity\": {" +
                "\"category_id\": " + categoryId + "," +
                "\"name\": \"" + name + "\"," +
                "\"description\": \"" + description + "\"" +
                "}" +
                "}";

        StringEntity entity = new StringEntity(json);
        entity.setContentType("application/json");
        postRequest.setEntity(entity);
        postRequest.addHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(postRequest);

        int statusCode = response.getStatusLine().getStatusCode();
        String responseBody = EntityUtils.toString(response.getEntity());

        System.out.println("Insert Category - Response Code: " + statusCode);
        System.out.println("Insert Category - Response Body: " + responseBody);
    }

    private static void getCategory(CloseableHttpClient httpClient, int categoryId) throws IOException {
        HttpGet getRequest = new HttpGet(BASE_URL + "?filter=category_id=" + categoryId);
        getRequest.addHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(getRequest);

        int statusCode = response.getStatusLine().getStatusCode();
        String responseBody = EntityUtils.toString(response.getEntity());

        System.out.println("Get Category - Response Code: " + statusCode);
        System.out.println("Get Category - Response Body: " + responseBody);
    }

    private static void deleteCategory(CloseableHttpClient httpClient, int categoryId) throws IOException {
        HttpDelete deleteRequest = new HttpDelete(BASE_URL + "?filter=category_id=" + categoryId);
        deleteRequest.addHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(deleteRequest);

        int statusCode = response.getStatusLine().getStatusCode();
        String responseBody = EntityUtils.toString(response.getEntity());

        System.out.println("Delete Category - Response Code: " + statusCode);
        System.out.println("Delete Category - Response Body: " + responseBody);
    }

    private static void updateCategory(CloseableHttpClient httpClient, int categoryId, Scanner scanner) throws IOException {
        System.out.println("Enter updated category details:");
        System.out.print("name: ");
        String name = scanner.nextLine();
        System.out.print("description: ");
        String description = scanner.nextLine();

        HttpPut putRequest = new HttpPut(BASE_URL_PUT);

        String json = "{" +
                "\"entity\": {" +
                "\"category_id\": " + categoryId + "," +
                "\"name\": \"" + name + "\"," +
                "\"description\": \"" + description + "\"" +
                "}" +
                "}";

        StringEntity entity = new StringEntity(json);
        entity.setContentType("application/json");
        putRequest.setEntity(entity);
        putRequest.addHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(putRequest);

        int statusCode = response.getStatusLine().getStatusCode();
        String responseBody = EntityUtils.toString(response.getEntity());

        System.out.println("Update Category - Response Code: " + statusCode);
        System.out.println("Update Category - Response Body: " + responseBody);
    }
}
