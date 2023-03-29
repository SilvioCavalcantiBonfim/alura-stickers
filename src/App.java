import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

import toolkit.JsonParser;
import toolkit.properties;

public class App {
    public static void main(String[] args) throws Exception {

        properties settings = new properties("resources/config.properties");

        stickerGenerator stickerGenerator = new stickerGenerator();

        URI uri = URI.create(settings.getProperty("IMDb.ENDPOINT")+"TopMovies.json");
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
        HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
        String body = response.body();
        List<Map<String, String>> moviesList = JsonParser.parse(body);

        for (Map<String,String> map : moviesList) {
            System.out.println("\033[2mTítulo: \033[0;0m\033[1m"+map.get("title")+"\033[0;0m");
            System.out.println("\033[2mPoster: \033[0;0m\033[1m"+map.get("image")+"\033[0;0m");
            System.out.println("\033[37;45mClassificação: "+map.get("imDbRating")+"\033[0;0m");
            System.out.println();

            InputStream urlInputStream = new URL(map.get("image")).openStream();
            stickerGenerator.create(urlInputStream,"resources/assets/"+map.get("title")+".png", "ImDb: "+map.get("imDbRating"));
        }
    }
}
