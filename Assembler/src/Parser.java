import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;
import java.io.File;

/**
 * Create a file parser to read through assembly code in two passes.
 */
public class Parser {

   public Parser() {
       
   }
   
   /**
    * Create the symbol table from a file.
    * @param rdr  Reader to read contents from.
    * @return A populated symbol table for the assembler.
    */
   public SymbolTable parseLabels(Reader rdr) {
      String line;
      int lineNum = 1;
      String labelString;
      SymbolTable symTab = new SymbolTable();
      
      try {
         Scanner scanner = new Scanner(rdr);
         while(scanner.hasNext()) {
            line = scanner.nextLine().split("#")[0].trim();
            if(line.contains(":")) {
               labelString = line.split(":")[0];
               symTab.addLabel(labelString, lineNum);
            }        
            if(Instruction.isInstruction(line)) {
                lineNum++;
            }
         }
         scanner.close();
      }
      catch (Exception e) {
         System.out.print(e.getMessage());
      }
      
      return symTab;
   }
   
   /**
    * Create the Program from a file.
    * @param rdr  Reader to read contents from.
    * @return A full Program to be written to an object file.
    */
   public Program parseInstructions(Reader rdr) throws IOException {
      int lineNum = 1;
      String line;
      String instructionName;
      Instruction currInstruction;
      Program program = new Program();
      
//      try {
         Scanner scanner = new Scanner(rdr);
         
         while(scanner.hasNext()) {
            line = scanner.nextLine();
            String formatted = line.split("#")[0].trim();
            if (Instruction.isInstruction(formatted)) {
                currInstruction = new Instruction(formatted, lineNum);
                program.addInstruction(currInstruction);
                lineNum++;
            }
         }
         scanner.close();
//      }
//      catch (Exception e)
//      {
//         System.out.print(e.getMessage());
//      }
      
      return program;
   }
}