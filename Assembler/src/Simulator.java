import java.util.HashMap;

/**
 * A MIPS simulator.
 */
public class Simulator {
    private int PC;
    private int[] registers;
    private HashMap<Integer, Integer> memory;
    
    public Simulator() {
        PC = 0;
        registers = new int[32];
        memory = new HashMap<Integer, Integer>();
    }
}
