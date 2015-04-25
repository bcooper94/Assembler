import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Represent an assembly program.
 */
public class Program {
    private List<Instruction> instructions;
    
    public Program() {
        instructions = new ArrayList<Instruction>();
    }
    
    public void addInstruction(Instruction inst) {
        instructions.add(inst);
    }
    
    /**
     * Write the actual binary assembly code to a stream.
     * @param stream   OutputStream to write output to.
     */
    public void writeObjectFile(OutputStream stream) {
        byte[] toWrite;
        ByteBuffer buffer;

        try {
            for (Instruction instruct : instructions) {
                buffer = ByteBuffer.allocate(4);
                buffer.putInt(instruct.getinstructCode());
                toWrite = buffer.array();
                stream.write(toWrite);
            }
        }
        catch (IOException except) {
            System.err.println("Unable to write buffer.");
        }
    }
    
    /**
     * Write a binary string representation of the assembly program.
     * @param write   PrintWriter to write binary instructions to.
     */
    public void writeObjFileBinString(PrintWriter write) {
        try {
            for (Instruction instruct : instructions) {
                write.println(Integer.toHexString(instruct.getinstructCode()));
            }
        }
        catch (Exception except) {
            System.err.println("Unable to print line.");
        }
    }
}