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

public class InsertUsers {

    private static final String BASE_URL = "http://localhost:80/gilhari/v1/anand.User";
    private static final String BASE_URL_PUT = "http://localhost:80/gilhari/v1/anand.User/updateEntity";

    public static void main(String[] args) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Enter API operation to perform (1 = POST, 2 = GET, 3 = DELETE, 4 = PUT):");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    insertUser(httpClient, scanner);
                    break;
                case 2:
                    System.out.println("Enter user_id to fetch:");
                    int userIdGet = scanner.nextInt();
                    scanner.nextLine(); // Consume newline left-over
                    getUser(httpClient, userIdGet);
                    break;
                case 3:
                    System.out.println("Enter user_id to delete:");
                    int userIdDelete = scanner.nextInt();
                    scanner.nextLine(); // Consume newline left-over
                    deleteUser(httpClient, userIdDelete);
                    break;
                case 4:
                    System.out.println("Enter user_id to update:");
                    int userIdPut = scanner.nextInt();
                    scanner.nextLine(); // Consume newline left-over
                    updateUser(httpClient, userIdPut, scanner);
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

    private static void insertUser(CloseableHttpClient httpClient, Scanner scanner) throws IOException {
        System.out.println("Enter user details:");
        System.out.print("user_id: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over
        System.out.print("username: ");
        String username = scanner.nextLine();
        System.out.print("password: ");
        String password = scanner.nextLine();
        System.out.print("email: ");
        String email = scanner.nextLine();

        HttpPost postRequest = new HttpPost(BASE_URL);

        String json = "{" +
                "\"entity\": {" +
                "\"user_id\": " + userId + "," +
                "\"password\": \"" + password + "\"," +
                "\"email\": \"" + email + "\"," +
                "\"username\": \"" + username + "\"" +
                "}" +
                "}";

        StringEntity entity = new StringEntity(json);
        entity.setContentType("application/json");
        postRequest.setEntity(entity);
        postRequest.addHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(postRequest);

        int statusCode = response.getStatusLine().getStatusCode();
        String responseBody = EntityUtils.toString(response.getEntity());

        System.out.println("Insert User - Response Code: " + statusCode);
        System.out.println("Insert User - Response Body: " + responseBody);
    }

    private static void getUser(CloseableHttpClient httpClient, int userId) throws IOException {
        HttpGet getRequest = new HttpGet(BASE_URL + "?filter=user_id=" + userId);
        getRequest.addHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(getRequest);

        int statusCode = response.getStatusLine().getStatusCode();
        String responseBody = EntityUtils.toString(response.getEntity());

        System.out.println("Get User - Response Code: " + statusCode);
        System.out.println("Get User - Response Body: " + responseBody);
    }

    private static void deleteUser(CloseableHttpClient httpClient, int userId) throws IOException {
        HttpDelete deleteRequest = new HttpDelete(BASE_URL + "?filter=user_id=" + userId);
        deleteRequest.addHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(deleteRequest);

        int statusCode = response.getStatusLine().getStatusCode();
        String responseBody = EntityUtils.toString(response.getEntity());

        System.out.println("Delete User - Response Code: " + statusCode);
        System.out.println("Delete User - Response Body: " + responseBody);
    }

    private static void updateUser(CloseableHttpClient httpClient, int userId, Scanner scanner) throws IOException {
        System.out.println("Enter updated user details:");
        System.out.print("username: ");
        String username = scanner.nextLine();
        System.out.print("password: ");
        String password = scanner.nextLine();
        System.out.print("email: ");
        String email = scanner.nextLine();

        HttpPut putRequest = new HttpPut(BASE_URL_PUT);

        String json = "{" +
                "\"entity\": {" +
                "\"user_id\": " + userId + "," +
                "\"password\": \"" + password + "\"," +
                "\"email\": \"" + email + "\"," +
                "\"username\": \"" + username + "\"" +
                "}" +
                "}";

        StringEntity entity = new StringEntity(json);
        entity.setContentType("application/json");
        putRequest.setEntity(entity);
        putRequest.addHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(putRequest);

        int statusCode = response.getStatusLine().getStatusCode();
        String responseBody = EntityUtils.toString(response.getEntity());

        System.out.println("Update User - Response Code: " + statusCode);
        System.out.println("Update User - Response Body: " + responseBody);
    }
}
