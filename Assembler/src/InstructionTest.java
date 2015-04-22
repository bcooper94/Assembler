import static org.junit.Assert.*;

import org.junit.Test;


public class InstructionTest {

    @Test
    public void test() {
        assertTrue(Instruction.isInstruction("sw    $v1, -4($sp)   # store low order bits"));
        assertTrue(Instruction.isInstruction("lw    $a3, TEST_B_2"));
        assertFalse(Instruction.isInstruction("   doubleFixedAdd:"));
        assertTrue(Instruction.isInstruction("sltu  $t2, $v1, $a1  # test for integer overflow"));
        assertTrue(Instruction.isInstruction("      jal doubleFixedAdd"));
    }
    
    @Test
    public void testRegInstruction() {
        SymbolTable testTable = new SymbolTable();
        testTable.addLabel("LabelTest", 13);
        testTable.addLabel("AnotherLabel", 19);
        testTable.addLabel("stuff", 41);
        testTable.addLabel("something", 951);
        Instruction.useSymbolTable(testTable);
        
        assertEquals(0x010A4820, Instruction.regInstruction(Operation.ADD, "$t1", "$t0", "$t2"));
        assertEquals(0x26DD0031, Instruction.immedInstruction(Operation.ADDIU, "$sp", "$22", "stuff"));
    }

}
