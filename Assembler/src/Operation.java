import java.util.HashMap;

/**
 * Represent each supported operation with its func/op code and instruction mode.
 */
public enum Operation {
    AND(0x24, InstructType.REGISTER) {
        public int apply(int instructCode, int[] registers) {
            return instructCode;
        }
    },
    
    OR(0x25, InstructType.REGISTER) {
        public int apply(int instructCode, int[] registers) {
            return instructCode;
        }
    },
    
    ADD(0x20, InstructType.REGISTER) {
        public int apply(int instructCode, int[] registers) {
            return instructCode;
        }
    },
    
    ADDU(0x21, InstructType.REGISTER) {
        public int apply(int instructCode, int[] registers) {
            return instructCode;
        }
    },
    
    SLL(0x00, InstructType.REGISTER) {
        public int apply(int instructCode, int[] registers) {
            return instructCode;
        }
    },
    
    SRL(0x02, InstructType.REGISTER) {
        public int apply(int instructCode, int[] registers) {
            return instructCode;
        }
    },
    
    SRA(0x03, InstructType.REGISTER) {
        public int apply(int instructCode, int[] registers) {
            return instructCode;
        }
    },
    
    SUB(0x22, InstructType.REGISTER) {
        public int apply(int instructCode, int[] registers) {
            return instructCode;
        }
    },
    
    SLTU(0x2b, InstructType.REGISTER) {
        public int apply(int instructCode, int[] registers) {
            return instructCode;
        }
    },
    
    ADDIU(0x24000000, InstructType.IMMEDIATE) {
        public int apply(int instructCode, int[] registers) {
            return instructCode;
        }
    },
    
    SLTIU(0x2C000000, InstructType.IMMEDIATE) {
        public int apply(int instructCode, int[] registers) {
            return instructCode;
        }
    },
    
    BEQ(0x10000000, InstructType.IMMEDIATE) {
        public int apply(int instructCode, int[] registers) {
            return instructCode;
        }
    },
    
    BNE(0x14000000, InstructType.IMMEDIATE) {
        public int apply(int instructCode, int[] registers) {
            return instructCode;
        }
    },
    
    LW(0x8C000000, InstructType.IMMEDIATE) {
        public int apply(int instructCode, int[] registers) {
            return instructCode;
        }
    },
    
    SW(0xAC000000, InstructType.IMMEDIATE) {
        public int apply(int instructCode, int[] registers) {
            return instructCode;
        }
    },
    
    J(0x08000000, InstructType.JUMP) {
        public int apply(int instructCode, int[] registers) {
            return instructCode;
        }
    },
    
    JR(0x08, InstructType.REGISTER) {
        public int apply(int instructCode, int[] registers) {
            return instructCode;
        }
    },
    
    JAL(0x0C000000, InstructType.JUMP) {
        public int apply(int instructCode, int[] registers) {
            return instructCode;
        }
    };
    
    private final int opValue;
    private final InstructType type;
    private static HashMap<Integer, Operation> opMap;
    
    static {
        for (Operation op : Operation.values()) {
            opMap.put(op.opValue, op);
        }
    }
    
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
    
    public Operation getOperation(int instructCode) {
        return opMap.get(instructCode & 0xFC00003F);
    }
    
    /**
     * Apply the operation.
     * @param instructCode  Given full instruction code.
     * @param registers   Array of all registers.
     * @return
     */
    public int apply(int instructCode, int[] registers) {
        return instructCode;
    }
}
