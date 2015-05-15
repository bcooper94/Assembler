import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class PipeLineTest {


   /** Fixture initialization (common initialization
    *  for all tests). **/
   @Before public void setUp() {
   }

/*
   @Test 
   public void testPipeLine1() {
       PipeLine pipeLine = new PipeLine();
       int firstInstruct = 0x3c040001;
       int secondInstruct = 0x3c040001;
       int thirdInstruct = 0x3c040001;
       int forthInstruct = 0x3c040001;
       int fifthInstruct = 0x3c040001;
       pipeLine.instructWrappers[0] = new InstructWrapper(firstInstruct); 
       pipeLine.instructWrappers[1] = new InstructWrapper(secondInstruct); 
       pipeLine.instructWrappers[2] = new InstructWrapper(thirdInstruct); 
       pipeLine.instructWrappers[3] = new InstructWrapper(forthInstruct);
       pipeLine.instructWrappers[4] = new InstructWrapper(fifthInstruct);
       pipeLine.chkFinished();
       assertNotNull(pipeLine.instructWrappers[2]);
       assertNotNull(pipeLine.instructWrappers[3]);
       assertNull(pipeLine.instructWrappers[4]);
       pipeLine.pipe(0x1500fff8);
       pipeLine.chkFinished();
       assertNotNull(pipeLine.instructWrappers[2]);
       assertNotNull(pipeLine.instructWrappers[3]);
       assertNull(pipeLine.instructWrappers[4]);
       pipeLine.pipe(0x1500fff8);
       pipeLine.chkFinished();
       assertNotNull(pipeLine.instructWrappers[2]);
       assertNotNull(pipeLine.instructWrappers[3]);
       assertNull(pipeLine.instructWrappers[4]);
       pipeLine.pipe(0x1500fff8);
       pipeLine.chkFinished();
       assertNull(pipeLine.instructWrappers[2]);
       assertNotNull(pipeLine.instructWrappers[3]);
       assertNull(pipeLine.instructWrappers[4]);
   }
   
   @Test 
   public void testPipeLine2() {
       Simulator sim = new Simulator();
       int thirdInstruct = 0x00c23021;
       int forthInstruct = 0x00c23021;
       int fifthInstruct = 0x3c040001;
       
       sim.getPipeLine().instructWrappers[2] = new InstructWrapper(thirdInstruct); 
       sim.getPipeLine().instructWrappers[3] = new InstructWrapper(forthInstruct);
       sim.getPipeLine().instructWrappers[4] = new InstructWrapper(fifthInstruct);
       sim.getPipeLine().chkFinished();
       assertNotNull(sim.getPipeLine().instructWrappers[2]);
       assertNull(sim.getPipeLine().instructWrappers[3]);
       assertNull(sim.getPipeLine().instructWrappers[4]);
   }
   */
}
