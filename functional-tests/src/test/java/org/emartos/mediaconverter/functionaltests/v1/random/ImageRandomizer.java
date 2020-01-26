package org.emartos.mediaconverter.functionaltests.v1.random;

import org.apache.commons.lang3.ArrayUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImageRandomizer {
//public class ImageRandomizer implements Randomizer<byte[]> {

    private static final Logger LOGGER = Logger.getLogger(ImageRandomizer.class.getName());

    private static final Integer RANDOM_IMG_WIDTH = 640;
    private static final Integer RANDOM_IMG_HEIGHT = 320;
    private static final Integer ALPHA_CHANNEL_BIT_OFFSET = 24;
    private static final Integer RED_CHANNEL_BIT_OFFSET = 16;
    private static final Integer GREEN_CHANNEL_BIT_OFFSET = 8;
    private static final String RANDOM_IMG_FORMAT = "png";

    private Byte[] getRandomImage() {
        BufferedImage img = new BufferedImage(RANDOM_IMG_WIDTH, RANDOM_IMG_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Random random = new Random();
        for (int y = 0; y < RANDOM_IMG_HEIGHT; y++) {
            for (int x = 0; x < RANDOM_IMG_WIDTH; x++) {
                int alpha = random.nextInt(256);
                int red = random.nextInt(256);
                int green = random.nextInt(256);
                int blue = random.nextInt(256);

                int pixel = (alpha << ALPHA_CHANNEL_BIT_OFFSET) | (red << RED_CHANNEL_BIT_OFFSET) |
                        (green << GREEN_CHANNEL_BIT_OFFSET) | blue;
                img.setRGB(x, y, pixel);
            }
        }
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img, RANDOM_IMG_FORMAT, baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return ArrayUtils.toObject(imageInByte);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "IO Exception during image randomize");
        }
        return null;
    }


    public Byte[] getRandomValue() {
        return this.getRandomImage();
    }
}
