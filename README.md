# Assembler
MIPS Instruction Assembler.

TODO:

Brandon:
* Create class for instructwrapper to hold instrcution and actually apply operation
* Counter for each step of pipeline.
* Public methods for incrementing Simulator PC.

Daniel:
* Create new Pipeline class with members for each step in pipeline. Can determine which instructions go through which step in the pipeline.
* Which instructions (in the test file) have which steps in the pipeline.
* When a branch/jump is taken, need to flush the pipeline.
* Count wasted clock cycles (maybe)

* Create multicycle type
* Determine any special cases
