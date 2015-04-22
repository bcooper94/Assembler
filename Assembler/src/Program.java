import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class Program {
    private List<Instruction> instructions;
    
    public Program() {
        instructions = new ArrayList<Instruction>();
    }
    
    public void addInstruction(Instruction inst) {
        instructions.add(inst);
    }
    
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
}