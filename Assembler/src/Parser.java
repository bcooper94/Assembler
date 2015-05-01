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
            line = scanner.nextLine();
            if(Instruction.isInstruction(line)) {
               lineNum++;
            }
            if(line.contains(":")) {
               labelString = line.split(":")[0];
               symTab.addLabel(labelString, lineNum);
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
      Instruction currInstruction;
      Program program = new Program();
<<<<<<< HEAD

      try {
=======
      
//      try {
>>>>>>> dcf7a8109fa1917baaf3be2d0e05c3fdc9f19606
         Scanner scanner = new Scanner(rdr);
         
         while(scanner.hasNext()) {
            line = scanner.nextLine();
<<<<<<< HEAD

            if (Instruction.isInstruction(line)) {
                currInstruction = new Instruction(line, lineNum);
=======
            String formatted = line.split("#")[0].trim();
            if (Instruction.isInstruction(formatted)) {
                currInstruction = new Instruction(formatted, lineNum);
>>>>>>> dcf7a8109fa1917baaf3be2d0e05c3fdc9f19606
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