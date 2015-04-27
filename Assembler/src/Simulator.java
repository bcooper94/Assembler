import java.io.InputStream;
import java.util.HashMap;

/**
 * A MIPS simulator.
 */
public class Simulator {
    private static final int PC_START = 200;
    private static final int MEM_SIZE = 1000;
    private int PC;
    private int[] registers;
    private int[] memory;
    private int cycleCount;
    private int instructCount;
    private int memoryRefs;
    
    public Simulator() {
        PC = PC_START;
        registers = new int[32];
        memory = new int[MEM_SIZE];
        cycleCount = 0;
        instructCount = 0;
        memoryRefs = 0;
    }
    
    /**
     * Load program and its data values.
     * @param input
     */
    public void loadProgram(InputStream input) {
        
    }
    
    /**
     * Access memory at address.
     * @param address  Address to access.
     * @return Contents at <code>address.</code>
     */
    private int accessMemory(int address) {
        return 0;
    }
    
    /**
     * Execute a single instruction.
     * @param instructCode
     */
    private boolean executeNextInstruct() {
        return false;
    }
    
    /**
     * Step through a single instruction.
     * @return
     */
    public boolean singleStep() {
        return false;
    }
    
    /**
     * Run the rest of the program and print contents of every register upon completion.
     */
    public void run() {
        
    }
    
    /**
     * Print the contents of every register.
     */
    public void registerDump() {
        
    }
    
    /**
     * Convert a byte aray (little endian) to an int.
     */
    public static int byteArrToInt(byte[] bytes) {
        int newInt = bytes[0];
        
        newInt |= ((int)bytes[1]) << 8;
        newInt |= ((int)bytes[2]) << 16;
        newInt |= ((int)bytes[3]) << 24;
        
        return newInt;
    }
}
