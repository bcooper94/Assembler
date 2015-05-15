Skip to content
This repository  
Explore
Gist
Blog
Help
@dbarraca dbarraca
 
 Unwatch 2
  Star 0
  Fork 1
bcooper94/Assembler
 branch: master  Assembler/Assembler/src/InstructionTest.java
@bcooper94bcooper94 9 minutes ago Working on multicycle
1 contributor
RawBlameHistory     40 lines (30 sloc)  1.443 kb
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
        
        //System.out.println(Operation.ADD.apply(0xFFFFFFFF));
        assertEquals(0x010A4820, Instruction.regInstruction(Operation.ADD, "$t1", "$t0", "$t2"));
        assertEquals(0x26DD0028, Instruction.immedInstruction(Operation.ADDIU, "$sp", "$22", "stuff", 1));
        //assertEquals(0x, Instruction.immedInstruction(Operation.LW, "$a0", "$t5", 4));
    }
    
    @Test
    public void testParseInstruction() {
        Instruction test1 = new Instruction("sw $a0,4($a1)", 1);
        
        assertEquals(0xACA40004, test1.getinstructCode());
    }

}
Status API Training Shop Blog About
© 2015 GitHub, Inc. Terms Privacy Security Contact
