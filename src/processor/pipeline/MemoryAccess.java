package processor.pipeline;

import processor.Processor;

public class MemoryAccess {
	Processor containingProcessor;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	
	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
	}
	
	public void performMA() {
		if(EX_MA_Latch.isMA_enable() == true) {
			if(EX_MA_Latch.isLoad == true) {
				containingProcessor.getRegisterFile().setValue(EX_MA_Latch.LoadAddr, EX_MA_Latch.aluResult);
			}
			if(EX_MA_Latch.isStore == true) {
				containingProcessor.getMainMemory().setWord(EX_MA_Latch.StoreAddr, EX_MA_Latch.aluResult);
			}

			MA_RW_Latch.WriteAddr = EX_MA_Latch.WriteAddr;
			MA_RW_Latch.aluResult = EX_MA_Latch.aluResult;
			MA_RW_Latch.isEnd = EX_MA_Latch.isEnd;
			EX_MA_Latch.setMA_enable(false);
			MA_RW_Latch.setRW_enable(true);
		}
		//TODO
	}

}
