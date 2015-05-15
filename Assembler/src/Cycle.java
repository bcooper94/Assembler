/**
 * Represent a single cycle in the instruction pipeline.
 */
public interface Cycle {
    
    /**
     * Carry out the step in the pipeline (IF, ID, EX, MEM/WB).
     */
    public void carryOut(Operation op, int instructionCode);
}