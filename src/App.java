import java.io.InputStream;
import java.net.URL;
import java.util.List;
import Extractor.Content;
import Extractor.APIs.IMDB;
import Sticker.stickerGenerator;
import toolkit.properties;

public class App {
    public static void main(String[] args) throws Exception {

        properties settings = new properties("resources/config.properties");

        stickerGenerator stickerGenerator = new stickerGenerator();

        Httpclient request = new Httpclient(settings.getProperty("IMDb.ENDPOINT")+"TopMovies.json"); 

        String json = request.request();

        List<Content> ContentList = (new IMDB()).Extractor(json);

        for (Content item : ContentList) {
            System.out.println("\033[2mTítulo: \033[0;0m\033[1m"+item.Title()+"\033[0;0m");
            System.out.println();

            InputStream urlInputStream = new URL(item.Image()).openStream();
            stickerGenerator.create(urlInputStream,"resources/assets/output/"+item.Title()+".png");
        }
    }
}
