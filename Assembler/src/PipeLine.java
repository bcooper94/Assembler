public class PipeLine {
    public InstructWrapper[] instructWrappers;
    private Simulator sim;
    private int cycleCount;

    public PipeLine(Simulator sim)
    {
        instructWrappers = new InstructWrapper[5];
        this.sim = sim;
        cycleCount = 0;
    }
    
    public void pipe(int newInstruct) {
        for (int idx = 3; idx >= 0; idx--) {
            instructWrappers[idx + 1] = instructWrappers[idx];
        }
        
        cycleCount++;
        insert(newInstruct);
    }

    public void chkFinished() {
        boolean clearPipe = false;
    
        if(instructWrappers[4] != null && instructWrappers[4].getCycleType() == MultiCycle.LOAD) {
            clearPipe = instructWrappers[4].apply();
            instructWrappers[4] = null;
        }
        
        if(instructWrappers[3] != null && (instructWrappers[3].getCycleType() == MultiCycle.STORE || 
           instructWrappers[3].getCycleType() == MultiCycle.REGISTER)) {
            clearPipe = instructWrappers[3].apply();
            instructWrappers[3] = null;
        }

        if(instructWrappers[2] != null && (instructWrappers[2].getCycleType() == MultiCycle.BRANCH || 
           instructWrappers[2].getCycleType() == MultiCycle.JUMP)) {
            clearPipe = instructWrappers[2].apply();
            instructWrappers[2] = null;
        }
        
        if(instructWrappers[1] != null && instructWrappers[1].getCycleType() == MultiCycle.NOP) {
            clearPipe = instructWrappers[1].apply();
            instructWrappers[1] = null;
        }
        
        if (clearPipe) {
            clearPipe();
        }

//        sim.setPC(sim.getPC() + 1);
    }
    
    public void insert(int instructCode) {
        instructWrappers[0] = new InstructWrapper(instructCode);
        instructWrappers[0].setSimulator(sim);
        
        //System.out.print(sim.getPC()+" "+*/instructWrappers[0].getCycleType()+"\n");
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
    
    public int getCycleCount() {
       return cycleCount;
    }
    
    private void clearPipe() {
        instructWrappers = new InstructWrapper[5];
        /*for(int idx = 0; idx < 4; idx++) {
            instructWrappers[idx] = null;
        } */
    }
}