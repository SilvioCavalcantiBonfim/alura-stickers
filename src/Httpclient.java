import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class Httpclient {
    private final String endPoint;

    public Httpclient(String endPoint) {
        this.endPoint = endPoint;
    }

    public String request(){
        try {
            URI uri = URI.create(this.getEndPoint());
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
            HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getEndPoint() {
        return endPoint;
    }

}
