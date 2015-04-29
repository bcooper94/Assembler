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
    
    
    private byte decToHex(int dec) {
        byte hex;
        if (dec >= 48 && dec <= 57) {
            hex = (byte)(dec - 48);
        }
        else {
            hex = (byte)(dec - 87);
        }
        return hex;
    }
    
    /**
     * Load program and its data values.
     * @param input
     */
    public void loadProgram(InputStream input) {
        int dec;
        byte[] bytes = new byte[8];
        try{
            //fill memory with opcodes
            for(int address = PC_START; input.available() != 0; address++) {
          
                //find an opcode from input
                for(int ndx = 0; ndx < 8; ndx++) {
                    dec = input.read();
                    bytes[ndx] = decToHex(dec);
                }

                memory[address] = byteArrToInt(bytes);
                endOfText = address;
           //   System.out.println(Integer.toBinaryString(byteArrToInt(bytes)));
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
        int currPC = PC;
        PC++;
        System.out.println(Integer.toBinaryString(accessMemory(currPC)));
        //Operation op = new Operation(accessMemory(currPC) & 0x1F/*, registers*/);
        return PC <= endOfText;
    }
    
    /**
     * Step through a single instruction.
     * @return
     */
    public boolean singleStep() {
       boolean hadNext = executeNextInstruct();
       registerDump();
       return hadNext;
    }
    
    /**
     * Run the rest of the program and print contents of every register upon completion.
     */
    public void run() {
        boolean hasNext = true;
        while(hasNext && (registers[2] != 10 /*|| 
        Operations.getOperation(accessMemory(PC)& 0x1F) != SYSCALL.getOperation*/)) {
            hasNext = executeNextInstruct();
        }
        registerDump();
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
        int newInt = bytes[7];
        
        newInt |= ((int)bytes[6]) << 4;
        newInt |= ((int)bytes[5]) << 8;
        newInt |= ((int)bytes[4]) << 12;
        newInt |= ((int)bytes[3]) << 16;
        newInt |= ((int)bytes[2]) << 20;
        newInt |= ((int)bytes[1]) << 24;
        newInt |= ((int)bytes[0]) << 28;
        
        return newInt;
    }
}
