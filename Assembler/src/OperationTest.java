import static org.junit.Assert.*;

import org.junit.Test;


public class OperationTest {

    @Test
    public void testOps() {
        int[] regs = new int[32];
        int[] memory = new int[2048];
        
        memory[1000] = 0xDEADBEEF;
        memory[801] = 0xFF00FF;
        memory[1008] = 0xFFFF0001;
//        regs[0] = 11;
        regs[1] = 24;
        regs[2] = 33;
        regs[4] = 801;
        regs[5] = 1000;
        regs[6] = 1;
        
        Operation.ADDIU.apply(0x242001F7, regs, memory);
        Operation.ADDIU.apply(0x249F0BBC, regs, memory);
        Operation.ADDI.apply(0x345EFF9B, regs, memory);
        
        assertEquals(527, regs[0]);
        assertEquals(3805, regs[31]);
        assertEquals(-68, regs[30]);
        
        Operation.ADDI.apply(0x345E0005, regs, memory);
        assertEquals(38, regs[30]);
        
        Operation.ORI.apply(0x34DE00F0, regs, memory);
        assertEquals(0xF1, regs[30]);
        
        Operation.LUI.apply(0x3C1D01A3, regs, memory);
        assertEquals(0x01A30000, regs[29]);
        
        Operation.LW.apply(0x8CBC0008, regs, memory);
        assertEquals(0xFFFF0001, regs[28]);
        
        Operation.SW.apply(0xAC810004, regs, memory);
        assertEquals(24, memory[805]);
    }

}
