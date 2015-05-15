public class PipeLine {
    public InstructWrapper[] instructWrappers;
    private Simulator sim;

    public PipeLine(Simulator sim)
    {
        instructWrappers = new InstructWrapper[5];
        this.sim = sim;
    }
    
    
    public void pipe(int newInstruct) {
        //chkFinished();
        for (int idx = 3; idx >= 0; idx--) {
         //  if(instructWrappers[idx + 1] != null) {
               instructWrappers[idx + 1] = instructWrappers[idx];
           //}
          // System.out.print(instructWrappers[idx+1].getInstructCode());
        }
        
        insert(newInstruct);
    }
    

    public void chkFinished() {
        //if(instructWrappers[2] != null)
         //   instructWrappers[2].apply();
    
        if(instructWrappers[4] != null && instructWrappers[4].getCycleType() == MultiCycle.LOAD) {
            instructWrappers[4].apply();
            instructWrappers[4] = null;
        }
        
        if(instructWrappers[3] != null && (instructWrappers[3].getCycleType() == MultiCycle.STORE || 
           instructWrappers[3].getCycleType() == MultiCycle.REGISTER)) {
            instructWrappers[3].apply();
            instructWrappers[3] = null;
        }

        if(instructWrappers[2] != null && (instructWrappers[2].getCycleType() == MultiCycle.BRANCH || 
           instructWrappers[2].getCycleType() == MultiCycle.JUMP)) {
            instructWrappers[2].apply();
            instructWrappers[2] = null;
        }
        
        if(instructWrappers[1] != null && instructWrappers[1].getCycleType() == MultiCycle.NOP) {
            instructWrappers[1].apply();
            instructWrappers[1] = null;
        }

        

    }
    
    public void insert(int instructCode) {
        instructWrappers[0] = new InstructWrapper(instructCode);
        instructWrappers[0].setSimulator(sim);
    }
    
    //At start make sure to have one in so they are not all null
    public boolean isEmpty() {
        boolean isEmpty = true;
        for(int idx = 0; idx <= 4; idx++) {
           if(instructWrappers[idx] != null) {
              isEmpty = false;
           }
        }
        
        return isEmpty;
    }
}