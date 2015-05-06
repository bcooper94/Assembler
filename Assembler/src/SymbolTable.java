import java.util.HashMap;

/**
 * Represent an assembler's symbol table.
 */
public class SymbolTable {
    private HashMap<String, Integer> table;
    
    public SymbolTable() {
        table = new HashMap<String, Integer>();
    }
    
    /**
     * Get the address offset.w
     */
    public int getOffset(String label) {
        return table.get(label);
    }

    /**
     * Add a single symbol to the table.
     * @param label   Label's name.
     * @param lineNum   Offset that label appears at in the file.
     */
    public void addLabel(String label, int lineNum) {
        table.put(label, lineNum);
    }
}
