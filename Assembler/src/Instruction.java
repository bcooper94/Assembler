import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Represent a single assembly instruction.
 */
public class Instruction {
    private int instructCode;
    private static final HashMap<String, Operation> operations;
    private static final HashMap<String, Integer> registers;
    private static SymbolTable symbolTable;

    static { // Initialize operation and register mappings
        operations = new HashMap<String, Operation>(18);
        registers = new HashMap<String, Integer>();

        operations.put("and", Operation.AND);
        operations.put("or", Operation.OR);
        operations.put("ori", Operation.ORI);
        operations.put("add", Operation.ADD);
        operations.put("addu", Operation.ADDU);
        operations.put("addi", Operation.ADDI);
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
        operations.put("lui", Operation.LUI);
        operations.put("syscall", Operation.SYSCALL);

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

    /**
     * Create an instruction from a line of assembly code and its line number.
     */
    public Instruction(String sourceLine, int lineNum) {
        instructCode = parseInstruction(sourceLine, lineNum);
    }

    /**
     * Create an instruction from a binary assembly code.
     */
    public Instruction(int code) {
        this.instructCode = code;
    }

    /**
     * Set the Instruction class to use a specific SymbolTable.
     */
    public static void useSymbolTable(SymbolTable symTab) {
        Instruction.symbolTable = symTab;
    }

    public int getinstructCode() {
        return instructCode;
    }

    /**
     * Test if a line of assembly code is an instruction. Used to see if PC needs to be incremented.
     * @param line  A full line in the assembly file.
     * @return whether or not the line is an instruction.
     */
    public static boolean isInstruction(String line) {
        boolean isInstruct = false;
        Set<String> keySet = operations.keySet();
        Iterator<String> keyIter = keySet.iterator();

        while (!isInstruct && keyIter.hasNext()) {
            isInstruct = line.contains(keyIter.next());
        }

        return isInstruct;
    }

    /**
     * Parse a single line of assembly code, returning the binary code as an int.
     * @param line   A full line in the assembly file.
     * @param lineNum  The line number of line.
     * @return the instruction code created from the line.
     */
    private int parseInstruction(String line, int lineNum) {
        String[] arrNotation;
        Operation currOperation;
        String instructionName;
        String[] arguments = line.split(",");
        int instructCode = 0;
        
        if (line.contains(":")) {
            instructionName = line.split(":")[1].trim().split("\\s+")[0];
        }
        else {
            instructionName = line.split("\\s+")[0].trim();
        }
        
        // Syscall special case
        if (instructionName.equals("syscall")) {
            return 0xFC000000;
        }

        if (arguments.length > 1) {
            arguments[0] = arguments[0].substring(arguments[0].indexOf("$"), arguments[0].length()).trim();
        }
        else {
            if (arguments[0].contains(":")) {
                arguments[0] = arguments[0].split(":")[1].trim();
            }
            arguments[0] = arguments[0].split("\\s+")[1];
        }
        if(operations.containsKey(instructionName)) {
            currOperation = operations.get(instructionName);
            instructCode |= currOperation.getOpValue();

            for(int ndx = 0; ndx < arguments.length; ndx++) {
                arguments[ndx] = arguments[ndx].trim();
                if (arguments[ndx].contains("0x")) {
                    arguments[ndx] = arguments[ndx].substring(arguments[ndx].indexOf("x") + 1);
                    arguments[ndx] = "" + Integer.parseInt(arguments[ndx], 16);
                }
            }

            if(currOperation.getType() == InstructType.REGISTER) {
                // special case: JR operation
                if (currOperation == Operation.JR) {
                    instructCode |= regInstruction(currOperation, "$0", arguments[0], "$0");
                }
                else {
                    instructCode |= regInstruction(
                        currOperation, arguments[0], arguments[1], arguments[2]);
                }
            }
            else if(currOperation.getType() == InstructType.IMMEDIATE) {
                // If instruct is using array notation
                if (arguments[1].contains("(") && arguments[1].contains(")")) {
                 //   System.err.println(arguments[1]);
                    arrNotation = arguments[1].split("\\(");
                    arrNotation[1] = arrNotation[1].substring(0, arrNotation[1].length() - 1);
                 //   System.out.println("ArrNotation[0] " + arrNotation[0]);
                 //   System.out.println("ArrNotation[1] " + arrNotation[1]);
                    instructCode |= immedInstruction(
                            currOperation, arguments[0], arrNotation[1], Integer.parseInt(arrNotation[0]));
                }
                else if (currOperation != Operation.LUI){
                    instructCode |= immedInstruction(
                            currOperation, arguments[0], arguments[1], arguments[2], lineNum);
                }
                else {
                    instructCode |= immedInstruction(currOperation, arguments[0], "$0", arguments[1], lineNum);
                }
            }
            else if(currOperation.getType() == InstructType.JUMP){
                instructCode |= jumpInstruction(currOperation, arguments[0], lineNum);
            }
        }

        return instructCode;
    }

    /**
     * Create the binary representation of a register mode instruction.
     * @param currOperation  The operation to be performed.
     * @param rd   Rd register.
     * @param rs   Rs register.
     * @param rt   Rt register.
     * @return instruction code for a register type instruction.
     */
    public static int regInstruction(
            Operation currOperation, String rd, String rs, String rt) {   
        int bits = currOperation.getOpValue();

        bits |= registers.get(rd) << 11;
        bits |= registers.get(rs) << 21;
        if (currOperation.name().equals("SLL")) {
            bits |= Integer.parseInt(rt) << 6;
            bits |= registers.get(rs) << 16;
        }
        else {
            bits |= registers.get(rt) << 16;
        }

        return bits;
    }

    /**
     * Create the binary representation of an immediate mode instruction.
     * @param currOperation  The operation to be performed.
     * @param rs   Rs register.
     * @param rt   Rt register.
     * @param immed  Immediate value.
     * @return instruction code for an immediate type instruction.
     */
    public static int immedInstruction(
            Operation currOperation, String rt, String rs, String immed, int curLine) {
        int bits = currOperation.getOpValue();

        if (immed.matches("-?[0-9]+")) {
            bits |= Integer.parseInt(immed) & 0xFFFF;
        }
        else {
            bits |= (symbolTable.getOffset(immed) - curLine) & 0xFFFF;
        }
        
        bits |= registers.get(rs) << 21;
        bits |= registers.get(rt) << 16;

        return bits;
    }

    /**
     * Create the binary representation of an immediate mode instruction.
     * @param currOperation  The operation to be performed.
     * @param rs   Rs register.
     * @param rt   Rt register.
     * @param immed  Immediate value.
     * @return instruction code for an immediate type instruction.
     */
    public static int immedInstruction(Operation currOperation, String rt, String rs, int immed) {
        int bits =  currOperation.getOpValue();

        bits |= immed;
        bits |= registers.get(rs) << 21;
        bits |= registers.get(rt) << 16;

        return bits;
    }

    /**
     * Create the binary representation of a jump instruction.
     * @param currOperation  Operation to be performed.
     * @param address   Address offset of the instruction of the instruction to jump to.
     * @return instruction code for a jump instruction.
     */
    public static int jumpInstruction(Operation currOperation, String address, int curLine) {
        int bits = currOperation.getOpValue();
        int offset = symbolTable.getOffset(address) == 0 ? Integer.parseInt(address) * 4 : symbolTable.getOffset(address);

        bits |= offset & 0x03FFFFFF;

        return bits;
    }
}