package Sticker;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;

public class stickerGenerator {
    
    private static final font[] fonts = {
        new font(new File("resources/assets/fonts/impact.ttf"), Font.TRUETYPE_FONT, 30),
        new font(new File("resources/assets/fonts/Bing_Bam_Boum.ttf"), Font.TRUETYPE_FONT, 24),
        new font(new File("resources/assets/fonts/orange_juice.ttf"), Font.TRUETYPE_FONT, 30),
        new font(new File("resources/assets/fonts/Hey_Comic.ttf"), Font.TRUETYPE_FONT, 28),
        new font(new File("resources/assets/fonts/Take_Coffee.ttf"), Font.TRUETYPE_FONT, 26),
        new font(new File("resources/assets/fonts/Glossy_Sheen_Shine.ttf"), Font.TRUETYPE_FONT, 32),
        new font(new File("resources/assets/fonts/Janda_Manatee.ttf"), Font.TRUETYPE_FONT, 28)
    };

    private static String[] messages = {
        "So vai",
        "Excelente",
        "Exceptionnel",
        "Muito bom",
        "Sem palavras",
        "Obra prima",
        "Topzera"
    };

    public void create(InputStream inputStream, String fileName) throws Exception{
        //Randomiza a fonte e a mensagem da descrição
        Random rand = new Random();
        int TextSelected = rand.nextInt(messages.length);
        int FontSelected = rand.nextInt(fonts.length);
        //carrega a imagem
        BufferedImage img = ImageIO.read(inputStream);

        //salva as informaçoes de tamanho da imagem em variaveis
        int width = img.getWidth();
        int height = img.getHeight();

        //cria a figurinha
        BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        Graphics2D graphics = (Graphics2D) newImg.getGraphics();
        
        //adiciona a imagem
        graphics.drawImage(img, 0, 0, null);

        //adiciona o sticker com o texto
        int widthSticker = width*250/674;
        graphics.drawImage(stickerGenerator.assessment(widthSticker, stickerGenerator.messages[TextSelected],FontSelected), width - widthSticker, 0, null);
        //salva a imagem
        ImageIO.write(newImg, "png", new File(fileName.replaceAll("[^\\w\\.@/-]", "_")));
    }

    private static BufferedImage assessment(int width,String text, int fontIndex) throws IOException{

        BufferedImage selo = ImageIO.read(new File("resources/assets/Selo.png"));

        int height = (int)(width*149/205);
        Image ScaleSelo = selo.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = (Graphics2D) newImg.getGraphics();
        
        graphics.drawImage(ScaleSelo, 0, 0, null);

        try {
            Font ftCreate = Font.createFont(fonts[fontIndex].Type(), fonts[fontIndex].file()).deriveFont(fonts[fontIndex].Size()*width/205.0f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(ftCreate);
            graphics.setFont(ftCreate);
        } catch (Exception e) {
            graphics.setFont(new Font(Font.SANS_SERIF, Font.BOLD, fonts[fontIndex].Size()*width/205));
        }

        FontMetrics fontMetrics = graphics.getFontMetrics();

        int textWidth = fontMetrics.stringWidth(text);

        graphics.drawString(text, (int) ((width - textWidth)/2), 85*width/205);

        AffineTransform transform = new AffineTransform();

        transform.translate(40*width/205,-30*width/205);
        transform.rotate(Math.toRadians(22.5f));

        int dimension = (int) (Math.sqrt(2)*(width+height));

        BufferedImage newImgRt = new BufferedImage(dimension, dimension, BufferedImage.TRANSLUCENT);
        Graphics2D graphicsRt = (Graphics2D) newImgRt.getGraphics();
        graphicsRt.drawImage(newImg, transform, null);

        return newImgRt;
    }
}
