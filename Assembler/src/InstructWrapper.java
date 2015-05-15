/**
 * Represent a single cycle in the instruction pipeline.
 */
public class InstructWrapper {
    private static Simulator sim;
    private int instructCode;
    private Operation operation;
    
    public InstructWrapper(int instructCode) {
        this.instructCode = instructCode;
        operation = Operation.getOperation(instructCode);
    }
    
    /**
     * Set the Simulator for all cycles. Do this before creating any InstructWrappers.
     */
    public static void setSimulator(Simulator simulator) {
        sim = simulator;
    }
    
    /**
     * Get the instruction's MultiCycle type.
     */
    public MultiCycle getCycleType() {
        return operation.getCycleType();
    }
    
    /**
     * Apply the entire operation.
     */
    public void apply() {
        InstructType type = operation.getType();
        
        if (operation == Operation.JR) {
            operation.apply(instructCode, sim.getRegisters(), sim.getMemory(), sim.getPC());
        }
        else if (operation == Operation.SYSCALL) {
            operation.apply(instructCode, sim.getRegisters());
        }
        else if (type == InstructType.REGISTER) {
            operation.apply(instructCode, sim.getRegisters());
        }
        else if (type == InstructType.IMMEDIATE) {
            if (operation == Operation.BEQ || operation == Operation.BNE) {
                operation.apply(instructCode, sim.getRegisters(), sim.getMemory(), sim.getPC(), sim);
            }
            else {
                operation.apply(instructCode, sim.getRegisters(), sim.getMemory());
            }
        }
        else if (type == InstructType.JUMP) {
            operation.apply(instructCode, sim.getRegisters(), sim.getMemory(), sim.getPC());
        }
    }
    
    /**
     * Carry out the step in the pipeline (IF, ID, EX, MEM/WB).
     */
    //public abstract void carryOut(Operation op, int instructionCode);
    
    /** 
     * Determine if the cycle's in basket is available.
     */
    /*
    public boolean ready() {
        return ready;
    }
    */
    
    /**
     * Get the number of times this step of the pipeline has been run.
     */
    /*
    public int getCount() {
        return cycleCount;
    }
    */
}
