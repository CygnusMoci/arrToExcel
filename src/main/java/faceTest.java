import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author moci
 * @create 2019-03-02 21:08
 **/
public class faceTest {
    private static final String faceUrl ="/Users/cygnusmoci/Pictures/faceTest.jpg";
    private static final String faceOutUrl ="/Users/cygnusmoci/Pictures/faceTestOut.jpg";

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

                int avg = (int)(r*0.29+g*0.60+b*0.11)*10;
                img[i][j] = avg;
                avg = lbpOprt(img,i,j);
                int p = (a << 24) | (avg << 16) | (avg << 8) | avg;
                BI.setRGB(j,i,p);
                lbp[i][j] = avg;
            }
        }

        File file = new File(faceOutUrl);
        ImageIO.write(BI,"jpg",file);
        printArray(lbp);
//        printHstgm(img);
    }

    public static void printArray(int[][] arr){
        for (int i = 0; i < arr.length; i+=5) {
            for (int j = 0; j < arr[0].length; j+=5) {
//                if(arr[i][j] == 0)
                    System.out.println(arr[i][j]+ " ");
//                else
//                    System.out.print(1+ " ");
            }
//            System.out.println();
        }
    }

    public static void LDP(BufferedImage BI,int[][] arr) {
        int height = arr.length;
        int width = arr[0].length;
        int[][] img = new int[height][width];

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                int a = (BI.getRGB(j,i) & 0xff )>>24;
                int avg = lbpOprt(arr,i,j);
                img[j][i]  = (a << 24) | (avg << 16) | (avg << 8) | avg;
            }
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                BI.setRGB(j,i,img[j][i]);
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
