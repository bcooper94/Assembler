import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileInputStream;
//import java.io.PipedOutputStream;

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
            File file = new File("test1.asm");
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

            PrintWriter writer = new PrintWriter(fileName);
            program.writeObjFileBinString(writer);
            writer.close();
            
            FileInputStream fileInStrm = new FileInputStream(fileName);
           
            /*//PipedOutputStream pipedOutStrm = new PipedOutputStream();
            //PrintWriter writer = new PrintWriter(pipedOutStrm);
            //program.writeObjFileBinString(writer);
            
            writer.flush();
            
            PipedInputStream pipedInStrm = new PipedInputStream(pipedOutStrm);
            System.out.print(pipedInStrm.read());
            pipedOutStrm.close();
            pipedInStrm.close();
            */
            
            Simulator simulator = new Simulator();
            simulator.loadProgram(fileInStrm);
            simulator.run();
            //simulator.singleStep();
            
            fileInStrm.close();
            
        }
        catch (Exception e)
        {
            System.out.print(e.getMessage());
        }
    }
}