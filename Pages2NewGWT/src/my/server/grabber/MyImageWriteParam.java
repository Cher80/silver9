package my.server.grabber;

import java.util.Locale;

import javax.imageio.plugins.jpeg.JPEGImageWriteParam;

public class MyImageWriteParam extends JPEGImageWriteParam {
    public MyImageWriteParam() {
        super(Locale.getDefault());
    }

    // This method accepts quality levels between 0 (lowest) and 1 (highest) and simply converts
    // it to a range between 0 and 256; this is not a correct conversion algorithm.
    // However, a proper alternative is a lot more complicated.
    // This should do until the bug is fixed.
    public void setCompressionQuality(float quality) {
        if (quality < 0.0F || quality > 1.0F) {
            throw new IllegalArgumentException("Quality out-of-bounds!");
        }
        this.compressionQuality = 256 - (quality * 256);
    }
}