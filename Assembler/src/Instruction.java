import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


public class Instruction {
    private int instructCode;
    private static final HashMap<String, Operation> operations;
    private static final HashMap<String, Integer> registers;
    private static SymbolTable symbolTable;
    
    static { // Initialize operation and register mappings
        operations = new HashMap<String, Operation>(18);
        registers = new HashMap<String, Integer>();
        
        operations.put("and", Operation.ADD);
        operations.put("or", Operation.OR);
        operations.put("add", Operation.ADD);
        operations.put("addu", Operation.ADDU);
        operations.put("sll", Operation.SLL);
        operations.put("srl", Operation.SRL);
        operations.put("sra", Operation.SRA);
        operations.put("sub", Operation.SUB);
        operations.put("sltu", Operation.SLTU);
        operations.put("addiu", Operation.ADDIU);
        operations.put("sltiu", Operation.SLTIU);
        operations.put("beq", Operation.BEQ);
        operations.put("bne", Operation.BNE);
        operations.put("lw", Operation.LW);
        operations.put("sw", Operation.SW);
        operations.put("j", Operation.J);
        operations.put("jr", Operation.JR);
        operations.put("jal", Operation.JAL);
        
        registers.put("$zero", 0);
        registers.put("$0", 0);
        registers.put("$at", 1);
        registers.put("$1", 1);
        registers.put("$v0", 2);
        registers.put("$2", 2);
        registers.put("$v1", 3);
        registers.put("$3", 3);
        registers.put("$a0", 4);
        registers.put("$4", 4);
        registers.put("$a1", 5);
        registers.put("$5", 5);
        registers.put("$a2", 6);
        registers.put("$6", 6);
        registers.put("$a3", 7);
        registers.put("$7", 7);
        registers.put("$t0", 8);
        registers.put("$8", 8);
        registers.put("$t1", 9);
        registers.put("$9", 9);
        registers.put("$t2", 10);
        registers.put("$10", 10);
        registers.put("$t3", 11);
        registers.put("$11", 11);
        registers.put("$t4", 12);
        registers.put("$12", 12);
        registers.put("$t5", 13);
        registers.put("$13", 13);
        registers.put("$t6", 14);
        registers.put("$14", 14);
        registers.put("$t7", 15);
        registers.put("$15", 15);
        registers.put("$s0", 16);
        registers.put("$16", 16);
        registers.put("$s1", 17);
        registers.put("$17", 17);
        registers.put("$s2", 18);
        registers.put("$18", 18);
        registers.put("$s3", 19);
        registers.put("$19", 19);
        registers.put("$s4", 20);
        registers.put("$20", 20);
        registers.put("$s5", 21);
        registers.put("$21", 21);
        registers.put("$s6", 22);
        registers.put("$22", 22);
        registers.put("$s7", 23);
        registers.put("$23", 23);
        registers.put("$t8", 24);
        registers.put("$24", 24);
        registers.put("$t9", 25);
        registers.put("$25", 25);
        registers.put("$k0", 26);
        registers.put("$26", 26);
        registers.put("$k1", 27);
        registers.put("$27", 27);
        registers.put("$gp", 28);
        registers.put("$28", 28);
        registers.put("$sp", 29);
        registers.put("$29", 29);
        registers.put("$fp", 30);
        registers.put("$30", 30);
        registers.put("$ra", 31);
        registers.put("$31", 31);
    }
    
    public Instruction(String sourceLine, int lineNum) {
        instructCode = parseInstruction(sourceLine, lineNum);
    }
    
    public Instruction(int code) {
        this.instructCode = code;
    }
    
    public static void useSymbolTable(SymbolTable symTab) {
         Instruction.symbolTable = symTab;
    }
    
    public int getinstructCode() {
        return instructCode;
    }
    
    public static boolean isInstruction(String line) {
        boolean isInstruct = false;
        Set<String> keySet = operations.keySet();
        Iterator<String> keyIter = keySet.iterator();

        while (!isInstruct && keyIter.hasNext()) {
            isInstruct = line.contains(keyIter.next());
        }

        return isInstruct;
    }
    
    private int parseInstruction(String line, int lineNum) {
        String formatted = line.split("#")[0].trim();
        int[] registers = {0, 0, 0};
        
        String[] arguments = formatted.split(",");
        arguments[0] = arguments[0].split("\\s+")[1];
        String instructionName = formatted.split("\\s+")[0].trim();

        Operation currOperation;
        int InstructCode = 0;
           
        if(operations.containsKey(instructionName)) {
         
           //instruction name starts with j its a jump
           //check if instruct code larger hex FF, its an immediate
           //otherwise its a register
           
           currOperation = operations.get(instructionName);
           InstructCode |= currOperation.getOpValue();
           
           for(int ndx = 0; ndx < arguments.length; ndx++) {
              arguments[ndx] = arguments[ndx].trim();
           }
           
          // System.out.print(instructionName);
          // System.out.print(Arrays.toString(arguments));
           
           if(currOperation.getType() == InstructType.REGISTER) {
              regInstruction(currOperation, arguments[0], arguments[1], arguments[2]);
           }
           else if(currOperation.getType() == InstructType.IMMEDIATE) {
              immedInstruction(currOperation, arguments[0], arguments[1], arguments[2]);
           }
           else if(currOperation.getType() == InstructType.JUMP){
              jumpInstruction(currOperation, arguments[0]);
           }
        }

        return 0;
    }

    public static int regInstruction(Operation currOperation, String rd, String rs, String rt) {
        int bits = currOperation.getOpValue();

        bits |= registers.get(rd) << 11;
        bits |= registers.get(rt) << 16;
        bits |= registers.get(rs) << 21;

        return bits;
    }

    public static int immedInstruction(Operation currOperation, String rt, String rs, String immed) {
        int bits =  currOperation.getOpValue();

        bits |= symbolTable.getOffset(immed) == 0 ? Integer.parseInt(immed) << 2 : symbolTable.getOffset(immed);
        bits |= registers.get(rs) << 21;
        bits |= registers.get(rt) << 16;

        return bits;
    }

    public static int jumpInstruction(Operation currOperation, String address) {
        int bits = currOperation.getOpValue();
        
        bits |= symbolTable.getOffset(address) == 0 ? Integer.parseInt(address) << 2 : symbolTable.getOffset(address);
        
        return bits;
    }
}