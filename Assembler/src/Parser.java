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
      int lineNum = 0;
      String labelString;
      SymbolTable symTab = new SymbolTable();
      
      try {
         Scanner scanner = new Scanner(rdr);
         
         System.out.print("Labels and their line numbers, Remember to comment this out later\n");
         while(scanner.hasNext()) {
            line = scanner.nextLine();
            if(Instruction.isInstruction(line)) {
               lineNum++;
            }
            if(line.contains(":")) {
               labelString = line.split(":")[0];
               symTab.addLabel(labelString, lineNum);
               System.out.print(labelString + " " + lineNum + " \n");  //this is to be commented later
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
   public Program parseInstructions(Reader rdr) {
      int lineNum = 1;
      String line;
      String instructionName;
      Instruction currInstruction;
      Program program = new Program();
      
      try {
         Scanner scanner = new Scanner(rdr);
         
         System.out.print("Instructions and their line numbers, Remember to comment this out later\n");
         while(scanner.hasNext()) {
            line = scanner.nextLine();
            if (Instruction.isInstruction(line)) {
                lineNum++;
                currInstruction = new Instruction(line, lineNum); 
                program.addInstruction(currInstruction);
            }
         }
         scanner.close();
      }
      catch (Exception e)
      {
         System.out.print(e.getMessage());
      }
      
      return program;
   }
}