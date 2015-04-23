import static org.junit.Assert.*;

import java.awt.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;


public class ProgramTest {

    @Test
    public void testWriteObjectFile() {
        try {
            FileInputStream inTest;
            FileWriter writer;
            Program testProg = new Program();
            FileOutputStream output = new FileOutputStream(new File("testOut.txt"));
            
            testProg.addInstruction(new Instruction(0xFFFFFFFF));
            testProg.addInstruction(new Instruction(0x000100FF));
            testProg.addInstruction(new Instruction(0xDEADBEEF));
            
            testProg.writeObjectFile(output);
            output.close();
            
            inTest = new FileInputStream(new File("testOut.txt"));
            
            assertEquals(inTest.read(), 0xFF);
            assertEquals(inTest.read(), 0xFF);
            assertEquals(inTest.read(), 0xFF);
            assertEquals(inTest.read(), 0xFF);
            assertEquals(inTest.read(), 0x00);
            assertEquals(inTest.read(), 0x01);
            assertEquals(inTest.read(), 0x00);
            assertEquals(inTest.read(), 0xFF);
            assertEquals(inTest.read(), 0xDE);
            assertEquals(inTest.read(), 0xAD);
            assertEquals(inTest.read(), 0xBE);
            assertEquals(inTest.read(), 0xEF);
            
            inTest.close();
            
            
        }
        catch (IOException except) {
            System.err.println("Unable to write to file.");
        }
    }

}
