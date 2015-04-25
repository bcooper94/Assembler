import java.io.FileReader;
import java.io.File;
import java.io.PrintWriter;

/**
 * A driver for the assembler program.
 */
public class Driver {
   
    /*
     * Driver for the assembler.
     */
   public static void main(String args[]) {
   
      try {
         File file = new File("test2.asm");
         Parser parser = new Parser();
         
         //pass 1 = labelsB
         FileReader symRdr = new FileReader(file);
         SymbolTable symTab = parser.parseLabels(symRdr);
         symRdr.close();
         
         Instruction.useSymbolTable(symTab);
         
         //pass 2 = instructions
         FileReader instRdr = new FileReader(file);
         Program program = parser.parseInstructions(instRdr);
         instRdr.close();
         
         
         PrintWriter writer = new PrintWriter(System.out);
         program.writeObjFileBinString(writer);
         writer.close();
      }
      catch (Exception e)
      {
         System.out.print(e.getMessage());
      }
   }
}