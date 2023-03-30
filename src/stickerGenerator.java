import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import toolkit.font;

public class stickerGenerator {

    public final static int[][] colors = {
        {29,15,11},
        {78, 58, 23}, 
        {60,60,60}, 
        {89,60,15},
        {14, 59, 75} 
    };

    public static final font[] fonts = {
        new font(new File("resources/assets/fonts/impact.ttf"), Font.TRUETYPE_FONT),
        new font(new File("resources/assets/fonts/Bing_Bam_Boum.ttf"), Font.TRUETYPE_FONT),
        new font(new File("resources/assets/fonts/orange_juice.ttf"), Font.TRUETYPE_FONT),
        new font(new File("resources/assets/fonts/Hey_Comic.ttf"), Font.TRUETYPE_FONT),
        new font(new File("resources/assets/fonts/Take_Coffee.ttf"), Font.TRUETYPE_FONT),
        new font(new File("resources/assets/fonts/Glossy_Sheen_Shine.ttf"), Font.TRUETYPE_FONT),
        new font(new File("resources/assets/fonts/Janda_Manatee.ttf"), Font.TRUETYPE_FONT)
    };

    public static String[] messages = {
        "So vai",
        "Excelente",
        "Exceptionnel",
        "Muito bom",
        "Sem palavras",
        "Obra prima",
        "Topzera"
    };

    public void create(InputStream inputStream, String fileName, String text, String ImdbRate) throws Exception{
        //Randomiza a fonte e a mensagem da descrição
        Random rand = new Random();
        int FontSelected = rand.nextInt(stickerGenerator.fonts.length);
        int TextSelected = rand.nextInt(stickerGenerator.messages.length);
        //carrega a imagem
        BufferedImage img = ImageIO.read(inputStream);

        //salva as informaçoes da imagem em variaveis
        int width = img.getWidth();
        int height = img.getHeight();
        //define o tamanho da figurinha
        int newHeight = (int) (height*1.08);

        //cria a figurinha
        BufferedImage newImg = new BufferedImage(width, newHeight, BufferedImage.TRANSLUCENT);
        Graphics2D graphics = (Graphics2D) newImg.getGraphics();
        
        //adiciona a imagem
        graphics.drawImage(img, 0, 0, null);
        //adiciona o sticker superior
        graphics.drawImage(this.assessment((int)(width*0.3), Float.parseFloat(ImdbRate)), width - (int)(width*0.3), 0, null);

        //calcula o tamanho da fonte
        int FontSize = (int) (height*150/2552);

        //carrega a fonte Impact
        try {
            Font impact = Font.createFont(fonts[0].getType(), fonts[0].getFile()).deriveFont((Float)(FontSize*1.0f));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(impact);
            graphics.setFont(impact);
        } catch (Exception e) {
            graphics.setFont(new Font(Font.SANS_SERIF, Font.BOLD, FontSize));
        }
        //define a cor
        graphics.setColor(Color.WHITE);

        //calcula o tamanho do texto do sticker
        FontMetrics fontMetrics = graphics.getFontMetrics();
        int fontWidth = fontMetrics.stringWidth(ImdbRate);

        //define as posições do texto
        int positionTextX = (int)(width - (width*0.2 - fontWidth)/2 - fontWidth);
        int positionTextY = (int)((width*0.35 - FontSize)/2);

        //adiciona o texto
        graphics.drawString(ImdbRate, positionTextX,positionTextY);
        try {
            Font bottomTextFont = Font.createFont(fonts[FontSelected].getType(), fonts[FontSelected].getFile()).deriveFont((float) FontSize);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(bottomTextFont);
            graphics.setFont(bottomTextFont);
        } catch (Exception e) {
            graphics.setFont(new Font(Font.SANS_SERIF, Font.BOLD, FontSize));
        }
        
        int fontWidthBottom = fontMetrics.stringWidth(stickerGenerator.messages[TextSelected]);

        graphics.drawString(stickerGenerator.messages[TextSelected], (width - fontWidthBottom)/2, (int)(newHeight - FontSize/2) + 10); 

        //salva a imagem
        ImageIO.write(newImg, "png", new File(fileName.replaceAll("[^\\w\\.@/-]", "_")));
    }

    private static int BooleanToInt(boolean a){
        return a? 1: 0;
    }
    
    private BufferedImage assessment(int width, float imdbRate) throws IOException{

        int ranking = stickerGenerator.BooleanToInt(imdbRate > 2.0f) + stickerGenerator.BooleanToInt(imdbRate > 4.0f) + stickerGenerator.BooleanToInt(imdbRate > 6.0f) + stickerGenerator.BooleanToInt(imdbRate > 8.0f);
        System.out.println(ranking);

        BufferedImage mutable = ImageIO.read(new File("resources/assets/mutable.png"));
        BufferedImage white = ImageIO.read(new File("resources/assets/white.png"));
        BufferedImage shadow = ImageIO.read(new File("resources/assets/shadow.png"));
        //recolor
        for (int y = 0; y < mutable.getHeight(); y++) {
            for (int x = 0; x < mutable.getWidth(); x++) {
                int pixel = mutable.getRGB(x, y);
                int alpha = (pixel>>24)&0xff;
                int red = (pixel>>16)&0xff;
                int green = (pixel>>8)&0xff;
                int blue = pixel&0xff;
                
                pixel = (alpha<<24) | (stickerGenerator.colors[ranking][0]*red/100<<16) | (stickerGenerator.colors[ranking][1]*green/100<<8) | (stickerGenerator.colors[ranking][2]*blue/100);
                mutable.setRGB(x, y, pixel);
            }
        }
        
        //resize
        Image orginalMutable = mutable.getScaledInstance(width, width, Image.SCALE_SMOOTH);
        Image orginalWhite = white.getScaledInstance(width, width, Image.SCALE_SMOOTH);
        Image orginalShadow = shadow.getScaledInstance(width, width, Image.SCALE_SMOOTH);
        
        //draw image
        BufferedImage newImg = new BufferedImage(width, width, BufferedImage.TYPE_INT_ARGB);
        newImg.getGraphics().drawImage(orginalShadow, 0, 0, null);
        newImg.getGraphics().drawImage(orginalWhite, 0, 0, null);
        newImg.getGraphics().drawImage(orginalMutable, 0, 0, null);
        return newImg;
    }
}
