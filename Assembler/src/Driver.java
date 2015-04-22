import java.io.FileReader;
import java.io.File;

public class Driver {
   
   public static void main(String args[]) {
   
      try {
         File file = new File("test1.asm");
         Parser parser = new Parser();
         
         //pass 1 = labels
         FileReader symRdr = new FileReader(file);
         parser.parseLabels(symRdr);
         symRdr.close();
         
         //pass 2 = instructions
         FileReader instRdr = new FileReader(file);
         parser.parseInstructions(instRdr);
         instRdr.close();
               
      }
      catch (Exception e)
      {
         System.out.print(e.getMessage());
      }
   }
}