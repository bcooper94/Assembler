# Assembler
MIPS Instruction Assembler.

TODO:

Brandon:
1. Create interface for Cycle with a method "carryOut" that takes an Operation and the instructionCode.
2. Create new InstructionFetch, InstructionDecode, Execute, MemAccess, and WriteBack classes based on Cycle interface with "inbasket", "outbasket", and flags.
3. Counter for each step of pipeline.
4. Public methods for incrementing Simulator PC.

Daniel:
1. Change .data to start at memory index 0.
2. Create new Pipeline class with members for each step in pipeline. Can determine which instructions go through which step in the pipeline.
3. Which instructions (in the test file) have which steps in the pipeline.
4. When a branch/jump is taken, need to flush the pipeline.
5. Count wasted clock cycles (maybe)
