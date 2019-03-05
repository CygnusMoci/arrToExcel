import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author moci
 * @create 2019-03-02 21:08
 **/
public class faceTest {
    private static final String faceUrl ="s:/灰度图.jpg";
    private static final String faceOutUrl ="s:/faceTestOut.jpg";

    public static void main(String[] args) throws IOException {
        BufferedImage BI = ImageIO.read(new File(faceUrl));
        int width = BI.getWidth();
        int heigh = BI.getHeight();
        int[][] img = new int[heigh][width];
        int[][] lbp = new int[heigh][width];
        for (int i = 0; i <heigh; i++) {
            for (int j = 0; j < width; j++) {
                int rgb = BI.getRGB(j,i);
                int a = (rgb&0xff) >> 24;
                int r = (rgb&0xff) >> 16;
                int g = (rgb&0xff) >> 8;
                int b = rgb&0xff ;

                int xr = 12;
                int xg = 59;
                int xb = 131;
                int rgvXAvg = (xr+xg+xb)/3;
                int avg = (r*xr+g*xg+b*xb)/rgvXAvg;

//                int avg = (int) (r*1.89+g*0.54+b*1.80);


                // 防溢出
                if(avg >255) avg = 255;
                else if(avg < 0) avg = 0;

//                 LBP
                img[i][j] = avg;
                avg = lbpTest.lbpOprt(img,i,j);

                int p = (a << 24) | (avg << 16) | (avg << 8) | avg;
                BI.setRGB(j,i,p);
                lbp[i][j] = avg;
            }
        }

        File file = new File(faceOutUrl);
        ImageIO.write(BI,"jpg",file);
//        printArray(lbp);
//        printHstgm(img);
    }

    public static void printArray(int[][] arr){
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                    System.out.print(arr[i][j]+ "    ");
            }
            System.out.println();
        }
    }

    public static void LDP(BufferedImage BI,int[][] arr) {
        int height = arr.length;
        int width = arr[0].length;
        int[][] img = new int[height][width];

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                int a = (BI.getRGB(j,i) & 0xff )>>24;
                int avg = lbpTest.lbpOprt(arr,i,j);
                img[j][i]  = (a << 24) | (avg << 16) | (avg << 8) | avg;
            }
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                BI.setRGB(j,i,img[j][i]);
            }
        }
    }

    public static void printHstgm(int[][] arr){
        int[] Hstgm = new int[255];
        int height = arr.length;
        int width = arr[0].length;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Hstgm[arr[i][j]]++;
            }
        }

        for (int i = 0; i < Hstgm.length; i++) {
            System.out.println(Hstgm[i]);
        }

    }
}
