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

public class ManageCustomers {
    private static final String BASE_URL = "http://localhost:80/gilhari/v1/anand.Customer";
    private static final String BASE_URL_PUT = "http://localhost:80/gilhari/v1/anand.Customer/updateEntity";

    public static void main(String[] args) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Enter API operation to perform (1 = POST, 2 = GET, 3 = DELETE, 4 = PUT):");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    insertCustomer(httpClient, scanner);
                    break;
                case 2:
                    System.out.println("Enter customer_id to fetch:");
                    int customerIdGet = scanner.nextInt();
                    scanner.nextLine(); // Consume newline left-over
                    getCustomer(httpClient, customerIdGet);
                    break;
                case 3:
                    System.out.println("Enter customer_id to delete:");
                    int customerIdDelete = scanner.nextInt();
                    scanner.nextLine(); // Consume newline left-over
                    deleteCustomer(httpClient, customerIdDelete);
                    break;
                case 4:
                    System.out.println("Enter customer_id to update:");
                    int customerIdPut = scanner.nextInt();
                    scanner.nextLine(); // Consume newline left-over
                    updateCustomer(httpClient, customerIdPut, scanner);
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

    private static void insertCustomer(CloseableHttpClient httpClient, Scanner scanner) throws IOException {
        System.out.println("Enter customer details:");
        System.out.print("customer_id: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over
        System.out.print("first_name: ");
        String firstName = scanner.nextLine();
        System.out.print("last_name: ");
        String lastName = scanner.nextLine();
        System.out.print("email: ");
        String email = scanner.nextLine();
        System.out.print("phone: ");
        String phone = scanner.nextLine();

        HttpPost postRequest = new HttpPost(BASE_URL);

        String json = "{" +
                "\"entity\": {" +
                "\"customer_id\": " + customerId + "," +
                "\"first_name\": \"" + firstName + "\"," +
                "\"last_name\": \"" + lastName + "\"," +
                "\"email\": \"" + email + "\"," +
                "\"phone\": \"" + phone + "\"" +
                "}" +
                "}";

        StringEntity entity = new StringEntity(json);
        entity.setContentType("application/json");
        postRequest.setEntity(entity);
        postRequest.addHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(postRequest);

        int statusCode = response.getStatusLine().getStatusCode();
        String responseBody = EntityUtils.toString(response.getEntity());

        System.out.println("Insert Customer - Response Code: " + statusCode);
        System.out.println("Insert Customer - Response Body: " + responseBody);
    }

    private static void getCustomer(CloseableHttpClient httpClient, int customerId) throws IOException {
        HttpGet getRequest = new HttpGet(BASE_URL + "?filter=customer_id=" + customerId);
        getRequest.addHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(getRequest);

        int statusCode = response.getStatusLine().getStatusCode();
        String responseBody = EntityUtils.toString(response.getEntity());

        System.out.println("Get Customer - Response Code: " + statusCode);
        System.out.println("Get Customer - Response Body: " + responseBody);
    }

    private static void deleteCustomer(CloseableHttpClient httpClient, int customerId) throws IOException {
        HttpDelete deleteRequest = new HttpDelete(BASE_URL + "?filter=customer_id=" + customerId);
        deleteRequest.addHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(deleteRequest);

        int statusCode = response.getStatusLine().getStatusCode();
        String responseBody = EntityUtils.toString(response.getEntity());

        System.out.println("Delete Customer - Response Code: " + statusCode);
        System.out.println("Delete Customer - Response Body: " + responseBody);
    }

    private static void updateCustomer(CloseableHttpClient httpClient, int customerId, Scanner scanner) throws IOException {
        System.out.println("Enter updated customer details:");
        System.out.print("first_name: ");
        String firstName = scanner.nextLine();
        System.out.print("last_name: ");
        String lastName = scanner.nextLine();
        System.out.print("email: ");
        String email = scanner.nextLine();
        System.out.print("phone: ");
        String phone = scanner.nextLine();

        HttpPut putRequest = new HttpPut(BASE_URL_PUT);

        String json = "{" +
                "\"entity\": {" +
                "\"customer_id\": " + customerId + "," +
                "\"first_name\": \"" + firstName + "\"," +
                "\"last_name\": \"" + lastName + "\"," +
                "\"email\": \"" + email + "\"," +
                "\"phone\": \"" + phone + "\"" +
                "}" +
                "}";

        StringEntity entity = new StringEntity(json);
        entity.setContentType("application/json");
        putRequest.setEntity(entity);
        putRequest.addHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(putRequest);

        int statusCode = response.getStatusLine().getStatusCode();
        String responseBody = EntityUtils.toString(response.getEntity());

        System.out.println("Update Customer - Response Code: " + statusCode);
        System.out.println("Update Customer - Response Body: " + responseBody);
    }
}
