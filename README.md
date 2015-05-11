# Assembler
MIPS Instruction Assembler.

TODO:

Brandon:
* Create interface for Cycle with a method "carryOut" that takes an Operation and the instructionCode.
* Create new InstructionFetch, InstructionDecode, Execute, MemAccess, and WriteBack classes based on Cycle interface with "inbasket", "outbasket", and flags.
* Counter for each step of pipeline.
* Public methods for incrementing Simulator PC.

Daniel:
* Change .data to start at memory index 0.
* Create new Pipeline class with members for each step in pipeline. Can determine which instructions go through which step in the pipeline.
* Which instructions (in the test file) have which steps in the pipeline.
* When a branch/jump is taken, need to flush the pipeline.
* Count wasted clock cycles (maybe)
