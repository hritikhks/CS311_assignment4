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
		if(EX_MA_Latch.isMA_enable()) {
			if(EX_MA_Latch.isNop == true) {
				MA_RW_Latch.isNop = true;
				MA_RW_Latch.setrd("111111");
			}
			else {
				MA_RW_Latch.isNop = false;
				
				// System.out.println("in memacc" + EX_MA_Latch.getLoadAddr() + " " + EX_MA_Latch.aluResult + " " + EX_MA_Latch.isLoad);
				// if(EX_MA_Latch.isLoad == true) {
				// 	EX_MA_Latch.isLoad = false;
				// 	// System.out.println("in memacc cond " + EX_MA_Latch.getLoadAddr() + " " + EX_MA_Latch.aluResult);
				// 	containingProcessor.getRegisterFile().setValue(EX_MA_Latch.getLoadAddr(), EX_MA_Latch.aluResult);
				// }
				int StoreAddr = containingProcessor.getRegisterFile().getValue(Integer.parseInt(EX_MA_Latch.getrd(),2)) + Integer.parseInt(EX_MA_Latch.getimm(),2);
				int rs1val = containingProcessor.getRegisterFile().getValue(Integer.parseInt(EX_MA_Latch.getrs1(),2));
				if(EX_MA_Latch.isStore == true) {
					EX_MA_Latch.isStore = false;
					// System.out.println("in storeuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu" + EX_MA_Latch.StoreAddr + " " + EX_MA_Latch.aluResult + " " + EX_MA_Latch.isStore);
				
					containingProcessor.getMainMemory().setWord(StoreAddr, rs1val);
				}
				// System.out.println();

				if(EX_MA_Latch.isLoad == true) {
					MA_RW_Latch.isLoad = EX_MA_Latch.isLoad;
					EX_MA_Latch.isLoad = false;
					int LoadAddr = Integer.parseInt(EX_MA_Latch.getrd(),2);
					int LoadContent = containingProcessor.getMainMemory().getWord(rs1val + Integer.parseInt(EX_MA_Latch.getimm(),2));
					MA_RW_Latch.LoadContent = LoadContent;
					MA_RW_Latch.LoadAddr = LoadAddr;
				}
				
				MA_RW_Latch.WriteAddr = EX_MA_Latch.WriteAddr;
				MA_RW_Latch.aluResult = EX_MA_Latch.aluResult;
				
				MA_RW_Latch.isEnd = EX_MA_Latch.isEnd;
				// EX_MA_Latch.setMA_enable(false);

				MA_RW_Latch.setrd(EX_MA_Latch.getrd());
				MA_RW_Latch.setrs1(EX_MA_Latch.getrs1());
				MA_RW_Latch.setrs2(EX_MA_Latch.getrs2());
				MA_RW_Latch.setimm(EX_MA_Latch.getimm());

				MA_RW_Latch.currentIns = EX_MA_Latch.currentIns;
				MA_RW_Latch.currentop = EX_MA_Latch.currentop;
				System.out.println("MA " + EX_MA_Latch.currentIns);

				if(EX_MA_Latch.currentop.equals("11101")) {
					EX_MA_Latch.setMA_enable(false);
				}
				if(MA_RW_Latch.isRW_enable() == true) {
					if(MA_RW_Latch.getrd().equals(EX_MA_Latch.getrs1()) == true || MA_RW_Latch.getrd().equals(EX_MA_Latch.getrs2()) == true) EX_MA_Latch.isConfEX_MA = true;
				}
				MA_RW_Latch.setRW_enable(true);
			}
		}
		//TODO
	}

}
