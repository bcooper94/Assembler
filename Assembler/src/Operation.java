/**
 * Represent each supported operation with its func/op code and instruction mode.
 */
public enum Operation {
    AND(0x24, InstructType.REGISTER),
    OR(0x25, InstructType.REGISTER),
    ADD(0x20, InstructType.REGISTER),
    ADDU(0x21, InstructType.REGISTER),
    SLL(0x00, InstructType.REGISTER),
    SRL(0x02, InstructType.REGISTER),
    SRA(0x03, InstructType.REGISTER),
    SUB(0x22, InstructType.REGISTER),
    SLTU(0x2b, InstructType.REGISTER),
    ADDIU(0x24000000, InstructType.IMMEDIATE),
    SLTIU(0x2C000000, InstructType.IMMEDIATE),
    BEQ(0x10000000, InstructType.IMMEDIATE),
    BNE(0x14000000, InstructType.IMMEDIATE),
    LW(0x8C000000, InstructType.IMMEDIATE),
    SW(0xAC000000, InstructType.IMMEDIATE),
    J(0x08000000, InstructType.JUMP),
    JR(0x08, InstructType.REGISTER),
    JAL(0x0C000000, InstructType.JUMP);
    
    private final int opValue;
    private final InstructType type;
    
    private Operation(int opValue, InstructType type) {
        this.opValue = opValue;
        this.type = type;
    }
    
    /**
     * Get the operation's func and op value.
     */
    public int getOpValue() {
        return this.opValue;
    }
    
    /**
     * Get the type of instruction.
     */
    public InstructType getType() {
        return this.type;
    }
}
