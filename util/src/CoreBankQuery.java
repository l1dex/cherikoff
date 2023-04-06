import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class CoreBankQuery {
    public static void sync(){
        var client = HttpClient.newHttpClient();
        var httpRequest = HttpRequest.newBuilder(URI.create("http://localhost:8080/accountUtil/replenish"))
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("""
                        {
                            "accountId" : "1111 1111 1111 1111",
                            "money" : 20.0
                        }
                        """))
                .build();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        AtomicInteger counter = new AtomicInteger();
        for(int i = 0; i < 1000; i++){
            executorService.submit(() -> {
                try {
                    var response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                    System.out.println(counter.getAndAdd(1) + " status - " + response.statusCode());
                } catch (IOException e) {
                    System.out.println("IO exception" + e.getMessage());
                } catch (InterruptedException e) {
                    System.out.println("InterruptedException" + e);
                }
            });
        }
    }

    public static void async() {
        var client = HttpClient.newHttpClient();
        var httpRequest = HttpRequest.newBuilder(URI.create("http://localhost:8080/accountUtil/replenish"))
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("""
                        {
                            "accountId" : "1111 1111 1111 1111",
                            "money" : 20.0
                        }
                        """))
                .build();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        AtomicInteger counter = new AtomicInteger();
        for(int i = 0; i < 1000; i++){
            executorService.submit(() -> {
                client.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());
                System.out.println(counter.getAndAdd(1));
            });
        }

    }
    public static void main(String[] args) throws IOException, InterruptedException {
        sync();
    }
}