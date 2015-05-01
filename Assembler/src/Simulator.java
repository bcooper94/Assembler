import java.io.InputStream;
import java.util.HashMap;
import java.util.Arrays;
import java.io.IOException;
//import java.io.Integer;

/**
 * A MIPS simulator.
 */
public class Simulator {
    private static final int PC_START = 200;
    private static final int MEM_SIZE = 1000;
    private int PC;
    private int[] registers;
    private int[] memory;
    private int endOfText;
    private int cycleCount;
    private int instructCount;
    private int memoryRefs;
    
    public Simulator() {
        PC = PC_START;
        registers = new int[32];        
        memory = new int[MEM_SIZE];
        endOfText = PC_START;
        cycleCount = 0;
        instructCount = 0;
        memoryRefs = 0;
    }
    
    /**
     * Load program and its data values.
     * @param input
     */
    public void loadProgram(InputStream input) {
        int dec;
        byte[] bytes = new byte[4];
        try{
            for(int address = PC_START; input.available() != 0; address++) {
                input.read(bytes);
                memory[address] = byteArrToInt(bytes);
                endOfText = address;
            }
        }
        catch(IOException except) {
           System.err.println("Unable to write buffer.");
        }
    }
    
    /**
     * Access memory at address.
     * @param address  Address to access.
     * @return Contents at <code>address.</code>
     */
    private int accessMemory(int address) {
        return memory[address];
    }
    
    /**
     * Execute a single instruction.
     * @param instructCode
     */
    private boolean executeNextInstruct() {
        Operation op;
        int instructCode = memory[PC++];
        
        op = Operation.getOperation(instructCode);
        
        if(op.getType() == InstructType.REGISTER) {
            if (op == Operation.JR) {
                op.apply(instructCode, registers, memory);
            }
            else {
                op.apply(instructCode, registers);
            }
        }
        else {
            op.apply(instructCode, registers, memory);
        }
        
        return PC <= endOfText;
    }
    
    /**
     * Step through a single instruction.
     * @return
     */
    public boolean singleStep() {
       boolean hadNext = executeNextInstruct();
       registerDump();
       statsPrint();
       return hadNext;
    }
    
    /**
     * Run the rest of the program and print contents of every register upon completion.
     */
    public void run() {
        while(executeNextInstruct() && (registers[2] != 10 || 
        Operation.getOperation(memory[PC]) != Operation.getOperation(0x0C)));
        registerDump();
        statsPrint();
    }
    /**
     * Print performacne statistics including number of instructions, Clock cycles and memory references
     */
    public void statsPrint() {
        System.out.print("\nInstruction Count: " + instructCount +
                         "\nClock cycles: " + cycleCount +
                         "\nMemory References: " + memoryRefs + "\n");
    }
    
    /**
     * Print the contents of every register.
     */
    public void registerDump() {
        System.out.println("\n" + Arrays.toString(registers));
    }
    
    /**
     * Convert a byte aray (little endian) to an int.
     */
        public static int byteArrToInt(byte[] bytes) {
          int newInt = (bytes[0] & 0xFF) << 24;
          newInt |= (((int)bytes[1]) & 0xFF) << 16;
          newInt |= (((int)bytes[2]) & 0xFF) << 8;
          newInt |= ((int)bytes[3]) & 0xFF;
        return newInt;
    }
    
}
