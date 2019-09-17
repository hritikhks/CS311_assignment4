package processor.pipeline;

import processor.Processor;

public class Execute {
	Processor containingProcessor;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	EX_IF_LatchType EX_IF_Latch;
	
	public Execute(Processor containingProcessor, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch, EX_IF_LatchType eX_IF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	}
	
	public void performEX() {
		if(OF_EX_Latch.isEX_enable()) {
			EX_IF_Latch.setjmpwhere(OF_EX_Latch.getjmphere());
			int aluResult;

			int rs1val = Integer.parseInt(OF_EX_Latch.getrs1());
			int rs2val = Integer.parseInt(OF_EX_Latch.getrs2());
			int rdval = Integer.parseInt(OF_EX_Latch.getrd());
			int immval = Integer.parseInt(OF_EX_Latch.getimm());
			
			switch(OF_EX_Latch.getopcode()) {
				case "00000" : {
					aluResult = rs1val + rs2val;
					break;
				}
				case "00001" : {
					aluResult = rs1val + immval;
					break;
				}
				case "00010" : {
					aluResult = rs1val - rs2val;
					break;
				}
				case "00011" : {
					aluResult = rs1val - immval;
					break;
				}
				case "00100" : {
					aluResult = rs1val * rs2val;
					break;
				}
				case "00101" : {
					aluResult = rs1val * immval;
					break;
				}
				case "00110" : {
					aluResult = rs1val / rs2val;
					break;
				}
				case "00111" : {
					aluResult = rs1val / immval;
					break;
				}
				case "01000" : {
					aluResult = rs1val & rs2val;
					break;
				}
				case "01001" : {
					aluResult = rs1val & immval;
					break;
				}
				case "01010" : {
					aluResult = rs1val | rs2val;
					break;
				}
				case "01011" : {
					aluResult = rs1val | immval;
					break;
				}
				case "01100" : {
					aluResult = rs1val ^ rs2val;
					break;
				}
				case "01101" : {
					aluResult = rs1val ^ immval;
					break;
				}
				case "01110" : {
					if(rs2val > rs1val) aluResult = 1;
					else aluResult = 0;
					break;
				}
				case "01111" : {
					if(rs2val > rs1val) aluResult = 1;
					else aluResult = 0;
					break;
				}

				case "10000" : {
					aluResult = rs1val << rs2val;
					break;
				}
				case "10001" : {
					aluResult = rs1val << immval;
					break;
				}
				case "10010" : {
					aluResult = rs1val >> rs2val;
					break;
				}
				case "10011" : {
					aluResult = rs1val >> immval;
					break;
				}
				case "10100" : {
					aluResult = rs1val >>> rs2val;
					break;
				}
				case "10101" : {
					aluResult = rs1val >>> immval;
					break;
				}

				case "10110" : {
					aluResult = containingProcessor.getMainMemory().getWord(rs1val + immval);
				}
				case "10111" : {
					aluResult = containingProcessor.getMainMemory().getWord(rdval + immval);
				}

			}

			if(OF_EX_Latch.getopcode() == "10110") {
				EX_MA_Latch.setisLoad(true);
				EX_MA_Latch.LoadAddr = Integer.parseInt(OF_EX_Latch.getrd());
			}
			else EX_MA_Latch.setisLoad(false);

			if(OF_EX_Latch.getopcode() == "10111") {
				EX_MA_Latch.setisStore(true);
				EX_MA_Latch.StoreAddr = Integer.parseInt(OF_EX_Latch.getrs1());
			}
			else EX_MA_Latch.setisStore(false);


			OF_EX_Latch.setEX_enable(false);
			EX_MA_Latch.setMA_enable(true);
		}
		//TODO
	}

}
