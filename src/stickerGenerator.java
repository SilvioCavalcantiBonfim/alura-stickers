import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class stickerGenerator {
    public void create(InputStream inputStream, String fileName, String text) throws Exception{
        BufferedImage img = ImageIO.read(inputStream);

        int width = img.getWidth();
        int height = img.getHeight();
        int newHeight = (int) (height*1.08);
        BufferedImage newImg = new BufferedImage(width, newHeight, BufferedImage.TRANSLUCENT);

        Graphics2D graphics = (Graphics2D) newImg.getGraphics();
        graphics.drawImage(img, 0, 0, null);

        int FontSize = (int) (height*60/2552);
        graphics.setFont(new Font(Font.SANS_SERIF, Font.BOLD, FontSize));
        graphics.setColor(Color.ORANGE);

        FontMetrics fontMetrics = graphics.getFontMetrics();
        int fontWidth = fontMetrics.stringWidth(text);

        graphics.drawString(text, (width - fontWidth)/2, newHeight - ((newHeight - height) - FontSize)/2);

        ImageIO.write(newImg, "png", new File(fileName.replaceAll("[^\\w\\.@/-]", "_")));
    }
}
