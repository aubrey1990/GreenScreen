//Aubrey Jenkins
//imageinv.java
//of this program is to take to images the foreground and the background and merge them together as long as all the conditions are met
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;
import java.io.File;
import java.util.Scanner;

public class GreenScreen
{

   /* ******************************************************* */
   /* *********  Method to load an image from a file ******** */
   /* ******************************************************* */
   public static BufferedImage Read_Image (String file)
   {
    BufferedImage image = null;            // declare the image

    // go get the image data from the file
    try
      {
       image = ImageIO.read(new File(file));
      }
    catch (IOException e)
      {
       System.out.println ("Error Opening image file");
      }

    // track the loading of the image
    MediaTracker tracker = new MediaTracker(new Component() {});
    tracker.addImage ( image, 0 );
    try
      {
       tracker.waitForID (0);
      }
    catch (InterruptedException e) {}

    // done, return the image
    return image;
   }


   /* ************************************************ */
   /* *********  M A I N  **************************** */
   /* ************************************************ */
   public static void main ( String[] args )
   {
     System.out.println("Starting Image Test");
     Scanner input = new Scanner(System.in);

     BufferedImage my_img = null;
      BufferedImage back = null;
		 String iname1,iname2;
		 String extension1,extension2;
     int redtol =0;
     int bluetol =0;
     int greentol = 0;
     System.out.print("Enter the foreground image name: ");
     iname1 =input.nextLine();
     System.out.print("Enter the background image name: ");
     iname2 = input.nextLine();
			 extension1 = iname1.substring(iname1.lastIndexOf(".") + 1);// get the file extension by getting the sub string that is after the dot
       extension2 = iname2.substring(iname2.lastIndexOf(".") + 1);// get the file extension by getting the sub string that is after the dot
			 if((extension1.compareTo( "jpg" ) == 0) & (extension2.compareTo( "jpg" ) == 0))// check to make sure that it is a jpg file
		 {
			 my_img = Read_Image (iname1);
       back = Read_Image (iname2);
		 }
		 else
		 {
			 System.out.println("one of the files is the wrong file type");
			 return;
		 }


		int width=my_img.getWidth(null);
		 int height=my_img.getHeight(null);
     int width2=back.getWidth(null);
      int height2=back.getHeight(null);

      System.out.println("Please give a tolereance for your red 0-100 (default red value to check is 100): ");
      redtol = input.nextInt();
      System.out.println("please give a tolereance for your blue 0-100 (defualt blue vaule to check is 100): ");
      bluetol = input.nextInt();

      if(redtol > 100 || redtol<0 || bluetol>100 || bluetol<0)
      {
        System.out.println("Your tolereance For red or blue is too high or too low try again");
        return;
      }
      System.out.println("please give a tolerance for your green 0-200 (defualt green value to check is 255): ");
      greentol= input.nextInt();
      if(greentol>200 || greentol<0)
      {
        System.out.println("The green tolerance is too high or too low please try again");
        return;
      }
    if((width>width2) || (height2<height))
    {
      System.out.println("the background image is too small to be part of the green screen");
      return;
    }
		 for(int y=0; y<height;y++){


     for (int x=0; x<width; x++)
       {
          int rgb1 = my_img.getRGB(x,y);
          int rgb2 = back.getRGB(x,y);

          int alpha = ((rgb1 >> 24) & 0xff);
          int red   = ((rgb1>> 16) & 0xff);
          int green = ((rgb1 >>  8) & 0xff);
          int blue  = ((rgb1 ) & 0xff);
          if((red >= 100-redtol)&&(red <= 100+redtol)&&(blue >= 100-bluetol) && (blue <= 100+bluetol) && (green>=255-greentol))//checks the colors against a tolerance to see if it should replace with background image
          {
            my_img.setRGB(x,y,rgb2);//takes background image and puts it in the place of greenscreen spots on foreground image
          }
          else
          {
          my_img.setRGB(x,y,rgb1);//keep same pixel there
          }

         }


			 }
			 File newimage = null;
			 String tag="inv_";
			 String newname= "combined";
			 try
			 {
				 newimage= new File(newname);
				 ImageIO.write(my_img,"jpg",newimage);
			 }
			 catch(IOException e)
			 {
      		System.out.println(e);
    		}

     System.out.println("Done with Image Test");
   }
}
