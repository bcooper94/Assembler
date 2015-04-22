import java.util.HashMap;

public class SymbolTable {
    private HashMap<String, Integer> table;
    
    public SymbolTable() {
        table = new HashMap<String, Integer>();
    }
    
    /**
     * Get the address offset.
     */
    public int getOffset(String label) {
        return table.get(label);
    }

    public void addLabel(String label, int lineNum) {
        table.put(label, lineNum * 4);
    }
}
