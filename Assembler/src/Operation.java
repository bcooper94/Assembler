import java.util.HashMap;

/**
 * Represent each supported operation with its func/op code and instruction mode.
 */
public enum Operation {
    AND(0x24, InstructType.REGISTER, 1) {
        public void apply(int instructCode, int[] regs) {
            regs[getRDReg(instructCode)] = regs[getRSReg(instructCode)] & regs[getRTReg(instructCode)];
        }
    },
    
    OR(0x25, InstructType.REGISTER, 1) {
        public void apply(int instructCode, int[] regs) {
            regs[getRDReg(instructCode)] = regs[getRSReg(instructCode)] | regs[getRTReg(instructCode)];
        }
    },
    
    ORI(0x34000000, InstructType.IMMEDIATE, 1) {
        public void apply(int instructCode, int[] regs, int[] memory) {
            regs[getRTReg(instructCode)] = regs[getRSReg(instructCode)] | getImmediate(instructCode);
        }
    },
    
    ADD(0x20, InstructType.REGISTER, 1) {
        public void apply(int instructCode, int[] regs) {
            regs[getRDReg(instructCode)] = regs[getRSReg(instructCode)] + regs[getRTReg(instructCode)];
        }
    },
    
    ADDU(0x21, InstructType.REGISTER, 1) {
        public void apply(int instructCode, int[] regs) {

        }
    },
    
    SLL(0x00, InstructType.REGISTER, 1) {
        public void apply(int instructCode, int[] regs) {
            regs[getRDReg(instructCode)] = regs[getRTReg(instructCode)] << getShamt(instructCode);
        }
    },
    
    SRL(0x02, InstructType.REGISTER, 1) {
        public void apply(int instructCode, int[] regs) {
            int MASK = 0x80000000;
            
            regs[getRDReg(instructCode)] = regs[getRTReg(instructCode)] >> getShamt(instructCode);

            if (regs[getRTReg(instructCode)] < 0) {
                regs[getRDReg(instructCode)] &= ~(MASK >> (getShamt(instructCode) - 1));
            }
        }
    },
    
    SRA(0x03, InstructType.REGISTER, 1) {
        public void apply(int instructCode, int[] regs) {
            regs[getRDReg(instructCode)] = regs[getRTReg(instructCode)] >> getShamt(instructCode);
        }
    },
    
    SUB(0x22, InstructType.REGISTER, 1) {
        public void apply(int instructCode, int[] regs) {
            regs[getRDReg(instructCode)] = regs[getRSReg(instructCode)] - regs[getRTReg(instructCode)];
        }
    },
    
    SLTU(0x2b, InstructType.REGISTER, 1) {
        public void apply(int instructCode, int[] regs) {
            long rs = Integer.toUnsignedLong(regs[getRSReg(instructCode)]);
            long rt = Integer.toUnsignedLong(regs[getRTReg(instructCode)]);
            
            regs[getRDReg(instructCode)] = rs < rt ? 1 : 0;
        }
    },
    
    ADDI(0x20000000, InstructType.IMMEDIATE, 1) {
        public void apply(int instructCode, int[] regs, int[] memory) {
            regs[getRTReg(instructCode)] = regs[getRSReg(instructCode)] + signExtendImmediate(getImmediate(instructCode));
        }
    },
    
    ADDIU(0x24000000, InstructType.IMMEDIATE, 1) {
        public void apply(int instructCode, int[] regs, int[] memory) {
            long rs = Integer.toUnsignedLong(regs[getRSReg(instructCode)]);
            long immediate = Integer.toUnsignedLong(signExtendImmediate(getImmediate(instructCode)));
            
            regs[getRTReg(instructCode)] = (int)(rs + immediate);
        }
    },
    
    SLTIU(0x2C000000, InstructType.IMMEDIATE, 1) {
        public void apply(int instructCode, int[] regs, int[] memory) {
            long rs = Integer.toUnsignedLong(regs[getRSReg(instructCode)]);
            long immediate = Integer.toUnsignedLong(getImmediate(instructCode));
            
            regs[getRTReg(instructCode)] = rs < immediate ? 1 : 0;
        }
    },
    
    BEQ(0x10000000, InstructType.IMMEDIATE, 1) {
        public void apply(int instructCode, int[] regs, int[] memory) {
            if (regs[getRSReg(instructCode)] == regs[getRTReg(instructCode)]) {
//                multiply immediate by 4?
//                newPC += getImmediate(instructCode);
            }
        }
    },
    
    BNE(0x14000000, InstructType.IMMEDIATE, 1) {
        public void apply(int instructCode, int[] regs, int[] memory) {
//            int newPC = pc + 1;
            
            if (regs[getRSReg(instructCode)] != regs[getRTReg(instructCode)]) {
                // multiply immediate by 4?
//                newPC += getImmediate(instructCode);
            }
        }
    },
    
    LW(0x8C000000, InstructType.IMMEDIATE, 1) {
        public void apply(int instructCode, int[] regs, int[] memory) {
            regs[getRTReg(instructCode)] = memory[(getImmediate(instructCode)) + regs[getRSReg(instructCode)]];
        }
    },
    
    LUI(0x3C000000, InstructType.IMMEDIATE, 1) {
        public void apply(int instructCode, int[] regs, int[] memory) {
            regs[getRTReg(instructCode)] = getImmediate(instructCode) << 16;
        }
    },
    
    SW(0xAC000000, InstructType.IMMEDIATE, 1) {
        public void apply(int instructCode, int[] regs, int[] memory) {
            memory[(getImmediate(instructCode)) + regs[getRSReg(instructCode)]] = regs[getRTReg(instructCode)];
        }
    },
    
    J(0x08000000, InstructType.JUMP, 1) {
        public void apply(int instructCode, int[] regs, int[] memory) {
//            return instructCode & 0x3FFFFFF;
        }
    },
    
    JR(0x08, InstructType.REGISTER, 1) {
        public void apply(int instructCode, int[] regs, int[] memory) {
//            return 0;
        }
    },
    
    JAL(0x0C000000, InstructType.JUMP, 1) {
        public void apply(int instructCode, int[] regs, int[] memory) {
//            return 0;
        }
    },
    
    SYSCALL(0x0C, InstructType.REGISTER, 0) {
        public void apply(int instructCode, int[] regs, int[] memory) {
        }
    };
    
    private static final int OPCODE_MASK = 0xFC000000;
    private static final int RS_MASK = 0x03E00000;
    private static final int RT_MASK = 0x001F0000;
    private static final int RD_MASK = 0x0000F800;
    private static final int SHAMT_MASK = 0x000007C0;
    private static final int FUNC_MASK = 0x0000003F;
    private final int opValue;
    private final InstructType type;
    private final int cycles;
    private static HashMap<Integer, Operation> opMap;
    
    static {
        opMap = new HashMap<Integer, Operation>();
        
        for (Operation op : Operation.values()) {
            opMap.put(op.opValue, op);
        }
    }
    
    private Operation(int opValue, InstructType type, int cycles) {
        this.opValue = opValue;
        this.type = type;
        this.cycles = cycles;
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
    
    public static Operation getOperation(int instructCode) {
        if ((instructCode & 0xFC000000) > 0) {
            return opMap.get(instructCode & 0xFC000000);
        }
        
        return opMap.get(instructCode & 0x0000003F);
    }
    
    /**
     * Get the cycles per instruction of an instruction.
     */
    public int getCyclesPerInstruct() {
        return this.cycles;
    }
    
    /**
     * Apply the register mode operation.
     * @param instructCode  Given full instruction code.
     * @param regs   Array of all regs.
     */
    public void apply(int instructCode, int[] regs) {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Apply the immediate mode operation.
     * @param instructCode   Full instruction code.
     * @param regs  Array of all registers.
     * @param memory   Array of all memory.
     */
    public void apply(int instructCode, int[] regs, int[] memory) {
        throw new UnsupportedOperationException();
    }
    
    private static int getRDReg(int instructCode) {
        return (instructCode & RD_MASK) >> 11;
    }
    
    private static int getRTReg(int instructCode) {
        return (instructCode & RT_MASK) >> 16;
    }
    
    private static int getRSReg(int instructCode) {
        return (instructCode & RS_MASK) >> 21;
    }
    
    private static int getShamt(int instructCode) {
        return (instructCode & SHAMT_MASK) >> 6;
    }
    
    private static int getImmediate(int instructCode) {
        return instructCode & 0xFFFF;
    }
    
    private static int signExtendImmediate(int immediate) {
        int newVal = immediate;
        
        if ((immediate & 0x00008000) > 0) {
            newVal |= 0xFFFF0000;
        }
        
        return newVal;
    }
}
