package utils;

import java.awt.AlphaComposite;  
import java.awt.Color;  
import java.awt.Graphics2D;  
import java.awt.RenderingHints;  
import java.awt.image.BufferedImage;  
import java.io.File;  
import java.io.IOException;  
import javax.imageio.ImageIO;  
  
/** 
 * 
 * @author jzavaleta 
 */  
public class ImageUtils {  
  
    /** 
     * Carga una variable BufferedImage desde un archivo tipo imagen 
     * 
     * @param pathname la ruta en el disco del archivo de la imagen 
     *  
     * @return BufferedImage con la imagen cargada desde el archivo 
     */  
    public static BufferedImage loadImage(String pathname) {  
        BufferedImage bufim = null;  
        try {  
            bufim = ImageIO.read(new File(pathname));  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return bufim;  
    }  
  
    /** 
     * Redimensiona una imagen BufferedImage 
     * 
     * @param bufferedImage la imagen que se desea redimensionar 
     * @param newW el nuevo ancho que se desea redimensionar 
     * @param newH el nuevo alto que se desea redimensionar 
     * 
     * @return BufferedImage redimensionada 
     */  
    public static BufferedImage resize(BufferedImage bufferedImage, int newW, int newH) {  
        int w = bufferedImage.getWidth();  
        int h = bufferedImage.getHeight();  
        BufferedImage bufim = new BufferedImage(newW, newH, bufferedImage.getType());  
        Graphics2D g = bufim.createGraphics();  
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);  
        g.drawImage(bufferedImage, 0, 0, newW, newH, 0, 0, w, h, null);  
        g.dispose();  
        return bufim;  
    }  
  
    /** 
     * Rota una imagen BufferedImage 
     * 
     * @param bufferedImage la imagen que se desea rotar 
     * @param angle los grados que se desea rotar 
     * 
     * @return BufferedImage rotada 
     */  
    public static BufferedImage rotate(BufferedImage bufferedImage, int angle) {  
        int w = bufferedImage.getWidth();  
        int h = bufferedImage.getHeight();  
        BufferedImage bufim = new BufferedImage(w, h, bufferedImage.getType());  
        Graphics2D g = bufim.createGraphics();  
        g.rotate(Math.toRadians(angle), w / 2, h / 2);  
        g.drawImage(bufferedImage, null, 0, 0);  
        return bufim;  
    }  
  
    /** 
     * Guarda una BufferedImage al disco en formato ".png" o ".jpg" 
     * 
     * @param bufferedImage la imagen que se desea guardar 
     * @param pathname la ruta del archivo en el cual se desea guardar la imagen incluyendo el nombre del archivo y su formato 
     */  
    public static void saveImage(BufferedImage bufferedImage, String pathname) {  
        try {  
            String format = (pathname.endsWith(".png")) ? "png" : "jpg";  
            ImageIO.write(bufferedImage, format, new File(pathname));  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
    /** 
     * Le aplica la transparencia seleccionada a una BufferedImage 
     * 
     * @param bufferedImage la imagen que se desea hacer transparente algun color 
     * @param transparency variable tipo <code>float</code> entre el rango 0.0 - 1.0 que indica el porcentaje de transparencia 
     * 
     * @return BufferedImage con el porcentaje de transparencia seleccionada 
     */  
    public static BufferedImage makeTranslucentImage(BufferedImage bufferedImage, float transparency) {  
        BufferedImage bufim = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TRANSLUCENT);  
        Graphics2D g = bufim.createGraphics();  
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));  
        g.drawImage(bufferedImage, null, 0, 0);  
        g.dispose();  
        return bufim;  
    }  
  
    /** 
     * Hace que el color seleccionado sea transparente en un BufferedImage 
     * 
     * @param bufferedImage la imagen que se desea hacer transparente algun color 
     * @param color el color que se desea hacer transparente 
     * 
     * @return BufferedImage con el color seleccionado transparente 
     */  
    public static BufferedImage makeColorTransparent(BufferedImage bufferedImage, Color color) {  
        BufferedImage bufim = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_ARGB);  
        Graphics2D g = bufim.createGraphics();  
        g.setComposite(AlphaComposite.Src);  
        g.drawImage(bufferedImage, null, 0, 0);  
        g.dispose();  
        for (int i = 0; i < bufim.getHeight(); i++) {  
            for (int j = 0; j < bufim.getWidth(); j++) {  
                if (bufim.getRGB(j, i) == color.getRGB()) {  
                    bufim.setRGB(j, i, 0x8F1C1C);  
                }  
            }  
        }  
        return bufim;  
    }  
  
    /** 
     * Corta una BufferedImage en filas y/o columnas del mismo tamaño 
     * 
     * @param bufferedImage la imagen que se desea cortar 
     * @param rows el numero filas en las que se desea cortar la imagen 
     * @param cols el numero de columnas en las que se desea cortar la image 
     * 
     * @return BufferedImare[] devuelve un arreglo tipo BufferedImage con las imagenes cortadas de la imagen original 
     */  
    public static BufferedImage[] splitImage(BufferedImage bufferedImage, int rows, int cols) {  
        int w = bufferedImage.getWidth() / cols;  
        int h = bufferedImage.getHeight() / rows;  
        int num = 0;  
        BufferedImage imgs[] = new BufferedImage[w * h];  
        for (int y = 0; y < rows; y++) {  
            for (int x = 0; x < cols; x++) {  
                imgs[num] = new BufferedImage(w, h, bufferedImage.getType());  
                // Tell the graphics to draw only one block of the image  
                Graphics2D g = imgs[num].createGraphics();  
                g.drawImage(bufferedImage, 0, 0, w, h, w * x, h * y, w * x + w, h * y + h, null);  
                g.dispose();  
                num++;  
            }  
        }  
        return imgs;  
    }  
  
    /** 
     * Gira una imagen en sentido vertival 
     * 
     * @param bufferedImage la imagen que se desea girar 
     * 
     * @return BufferedImage es la imagen girada 
     */  
    public static BufferedImage flipVertical(BufferedImage bufferedImage) {  
        int w = bufferedImage.getWidth();  
        int h = bufferedImage.getHeight();  
        BufferedImage bufim = new BufferedImage(w, h, bufferedImage.getColorModel().getTransparency());  
        Graphics2D g = bufim.createGraphics();  
        g.drawImage(bufferedImage, 0, 0, w, h, 0, h, w, 0, null);  
        g.dispose();  
        return bufim;  
    }  
  
    /** 
     * Gira una imagen en sentido horizontal 
     * 
     * @param bufferedImage la imagen que se desea girar 
     * 
     * @return BufferedImage es la imagen girada 
     */  
    public static BufferedImage flipHorizontal(BufferedImage bufferedImage) {  
        int w = bufferedImage.getWidth();  
        int h = bufferedImage.getHeight();  
        BufferedImage bufim = new BufferedImage(w, h, bufferedImage.getType());  
        Graphics2D g = bufim.createGraphics();  
        g.drawImage(bufferedImage, 0, 0, w, h, w, 0, 0, h, null);  
        g.dispose();  
        return bufim;  
    }  
}  