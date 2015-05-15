
public enum MultiCycle {
    LOAD(5), STORE(4), REGISTER(4), BRANCH(3), JUMP(3), NOP(2), SYSCALL(0);
    
    private final int cycles;
    
    private MultiCycle(int cycles) {
        this.cycles = cycles;
    }
    
    public int getCycles() {
        return cycles;
    }
}
