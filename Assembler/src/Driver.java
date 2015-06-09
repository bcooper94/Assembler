import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;

/**https://github.com/dbarraca/Assembler
 * A driver for the assembler program.
 */
public class Driver {

    /*
     * Driver for the assembler.
     */
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        String fileName = "instructionBits";
        Simulator simulator = new Simulator();
        
        try {
            boolean running = true;
            File file = new File("countbits_benchmark_pipeline.asm");
            Parser parser = new Parser(null);

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
            simulator.loadProgram(fileInStrm);
            simulator.loadData(file, symTab);
            fileInStrm.close();
            simulator.simulate();
        }
        catch (Exception e)
        {
            System.out.print(e.getMessage());
        }
        finally {
            sc.close();
            simulator.registerDump();
            simulator.statsPrint();
        }
    }
}
