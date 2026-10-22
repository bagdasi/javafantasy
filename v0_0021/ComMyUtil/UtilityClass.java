package ComMyUtil;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import org.opencv.core.Size;
import org.opencv.core.CvType;
import org.opencv.videoio.VideoWriter;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.*;



public class UtilityClass {
    public BufferedImage matToBufferedImage(Mat mat) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (mat.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        byte[] b = new byte[mat.channels() * mat.cols() * mat.rows()];
        mat.get(0, 0, b);
        BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
        return image;
    }
	public Mat bufferedImageToMat(BufferedImage image) {
		Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
		BufferedImage safeImage = convertTo3ByteBGR(image);
		byte[] pixels = ((DataBufferByte) safeImage.getRaster().getDataBuffer()).getData();
		mat.put(0, 0, pixels);
		return mat;
	}
	public static BufferedImage convertTo3ByteBGR(BufferedImage image) {
		BufferedImage newImage = new BufferedImage(
			image.getWidth(), image.getHeight(),
			BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g = newImage.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return newImage;
	}
}