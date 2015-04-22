
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
    ADDIU(0x90000000, InstructType.IMMEDIATE),
    SLTIU(0xb0000000, InstructType.IMMEDIATE),
    BEQ(0x40000000, InstructType.IMMEDIATE),
    BNE(0x50000000, InstructType.IMMEDIATE),
    LW(0x23000000, InstructType.IMMEDIATE),
    SW(0x2b000000, InstructType.IMMEDIATE),
    J(0x20000000, InstructType.JUMP),
    JR(0x08, InstructType.REGISTER),
    JAL(0x20000000, InstructType.JUMP);
    
    private final int opValue;
    private final InstructType type;
    
    private Operation(int opValue, InstructType type) {
        this.opValue = opValue;
        this.type = type;
    }
    
    public int getOpValue() {
        return this.opValue;
    }
    
    public InstructType getType() {
        return this.type;
    }
}
