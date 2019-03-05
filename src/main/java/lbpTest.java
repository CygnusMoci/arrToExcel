import sun.applet.Main;
import sun.security.x509.AVA;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author moci
 * @create 2019-03-05 19:37
 **/
public class lbpTest {

    private static final String EdgeUrl ="s:/LBP纹理.jpg";
    private static final String GrayUrl ="s:/灰度图.jpg";

    private static final String EdgeUrlOut ="s:/LBP纹理_Out.jpg";
    private static final String GrayUrlOut ="s:/灰度图_Out.jpg";

    public static void main(String[] args) throws IOException {
        BufferedImage EU = ImageIO.read(new File(EdgeUrl));
        BufferedImage GU = ImageIO.read(new File(GrayUrl));

        int[][] euLbp = new int[16][16];
        int[][] guLbp = new int[4][4];
        int[][] euGray = new int[16][16];
        int[][] guGray = new int[4][4];
        getGray(euGray,EU);
        getGray(guGray,GU);

//        System.out.println("原图灰度值");
//        faceTest.printArray(euGray);
//        LBP(EU,euGray,euLbp);
//        System.out.println("LBP后");
//        faceTest.printArray(euLbp);

        System.out.println("原图灰度值");
        faceTest.printArray(guGray);
        LBP(GU,guGray,guLbp);
        System.out.println("LBP后");
        faceTest.printArray(guLbp);

    }

    public static void getGray(int[][] arr,BufferedImage BI){
        int width = arr.length;
        int heigh = arr[0].length;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < heigh; j++) {
                int rgb = BI.getRGB(i,j);
                int r = (rgb&0xff) >> 16;
                int g = (rgb&0xff) >> 8;
                int b = rgb&0xff ;
                int avg = (r+g+b)/3;
                arr[i][j] = avg;
            }
        }
    }

    public static void LBP(BufferedImage BI,int[][] grayArr,int[][] lbpArr){
        int width = lbpArr.length;
        int heigh = lbpArr[0].length;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < heigh; j++) {
                lbpArr[i][j] = lbpOprt(grayArr,i,j);
            }
        }
    }
    public static int lbpOprt(int[][] arr,int xi,int yi){
        if(xi == 0 || yi == 0 || xi == arr.length-1 || yi == arr[0].length-1 ) return arr[xi][yi];
        StringBuilder lbpSB = new StringBuilder();
        int base = arr[xi][yi];
        lbpSB.append(bp(base,arr[xi-1][yi-1]));
        lbpSB.append(bp(base,arr[xi-1][yi]));
        lbpSB.append(bp(base,arr[xi-1][yi+1]));
        lbpSB.append(bp(base,arr[xi][yi+1]));
        lbpSB.append(bp(base,arr[xi+1][yi+1]));
        lbpSB.append(bp(base,arr[xi+1][yi]));
        lbpSB.append(bp(base,arr[xi+1][yi-1]));
        lbpSB.append(bp(base,arr[xi][yi-1]));
        return Integer.parseInt(lbpSB.toString(),2);
    }
    public static char bp(int a,int b){
        if(a>b) return '1';
        else return '0';
    }


}
