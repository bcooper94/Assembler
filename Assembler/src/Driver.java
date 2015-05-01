import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * A driver for the assembler program.
 */
public class Driver {

    /*
     * Driver for the assembler.
     */
    public static void main(String args[]) {
        String fileName = "instructionBits";

        try {
            //File file = new File("test.asm");

            File file = new File("countbits_benchmark.asm");
            
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
           
            FileOutputStream fileOutStrm = new FileOutputStream(fileName);
            program.writeObjectFile(fileOutStrm);
            fileOutStrm.close();
            
            FileInputStream fileInStrm = new FileInputStream(fileName);
            Simulator simulator = new Simulator();
            simulator.loadProgram(fileInStrm);
            simulator.simulate();
            
            fileInStrm.close();
        }
        catch (Exception e)
        {
            System.out.print(e.getMessage());
        }
    }
}