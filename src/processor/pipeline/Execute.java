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
			int aluResult;
			// if(OF_EX_Latch.getrs1() == "") OF_EX_Latch.rs1 = "000";
			// if(OF_EX_Latch.getrs2() == "") OF_EX_Latch.rs2 = "000";
			// if(OF_EX_Latch.getrd() == "") OF_EX_Latch.rd = "000";
			// if(OF_EX_Latch.getimm() == "") OF_EX_Latch.imm = "000";
			int rs1val = containingProcessor.getRegisterFile().getValue(Integer.parseInt(OF_EX_Latch.getrs1(),2));
			int rs2val = containingProcessor.getRegisterFile().getValue(Integer.parseInt(OF_EX_Latch.getrs2(),2));
			int rdval = containingProcessor.getRegisterFile().getValue(Integer.parseInt(OF_EX_Latch.getrd(),2));
			// System.out.println(OF_EX_Latch.getimm()); 
			int immval = Integer.parseInt(OF_EX_Latch.imm,2);
			if(immval >= 65536) immval = immval - 65536;
			int WriteAddr = 70000;
			
			switch(OF_EX_Latch.getopcode()) {
				case "00000" : {
					aluResult = rs1val + rs2val;
					WriteAddr = rdval;
					break;
				}
				case "00001" : {
					aluResult = rs1val + immval;
					WriteAddr = rdval;
					break;
				}
				case "00010" : {
					aluResult = rs1val - rs2val;
					WriteAddr = rdval;
					break;
				}
				case "00011" : {
					aluResult = rs1val - immval;
					WriteAddr = rdval;
					break;
				}
				case "00100" : {
					aluResult = rs1val * rs2val;
					WriteAddr = rdval;
					break;
				}
				case "00101" : {
					aluResult = rs1val * immval;
					WriteAddr = rdval;
					break;
				}
				case "00110" : {
					if(rs2val != 0)	{
						aluResult = rs1val / rs2val;
						containingProcessor.getRegisterFile().setValue(31, rs1val % rs2val);
						WriteAddr = rdval;
					}
					break;
				}
				case "00111" : {
					if(immval != 0)	{
						aluResult = rs1val / immval;
						containingProcessor.getRegisterFile().setValue(31, rs1val % immval);
						WriteAddr = rdval;
					}
					break;
				}
				case "01000" : {
					aluResult = rs1val & rs2val;
					WriteAddr = rdval;
					break;
				}
				case "01001" : {
					aluResult = rs1val & immval;
					WriteAddr = rdval;
					break;
				}
				case "01010" : {
					aluResult = rs1val | rs2val;
					WriteAddr = rdval;
					break;
				}
				case "01011" : {
					aluResult = rs1val | immval;
					WriteAddr = rdval;
					break;
				}
				case "01100" : {
					aluResult = rs1val ^ rs2val;
					WriteAddr = rdval;
					break;
				}
				case "01101" : {
					aluResult = rs1val ^ immval;
					WriteAddr = rdval;
					break;
				}
				case "01110" : {
					if(rs2val > rs1val) aluResult = 1;
					else aluResult = 0;
					WriteAddr = rdval;
					break;
				}
				case "01111" : {
					if(rs2val > rs1val) aluResult = 1;
					else aluResult = 0;
					WriteAddr = rdval;
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
					EX_MA_Latch.setisLoad(true);
					EX_MA_Latch.LoadAddr = Integer.parseInt(OF_EX_Latch.getrd());
					break;
				}
				case "10111" : {
					aluResult = containingProcessor.getRegisterFile().getValue(rs1val);
					EX_MA_Latch.setisStore(true);
					EX_MA_Latch.StoreAddr = rdval + immval;
					break;
				}

				case "11000" : {
					EX_IF_Latch.setjmpjmpAddr(rdval + immval);
					break;
				}
				case "11001" : {
					if(rs1val == rdval) EX_IF_Latch.setjmpjmpAddr(immval);
					break;
				}
				case "11010" : {
					if(rs1val != rdval) EX_IF_Latch.setjmpjmpAddr(immval); 
					break;
				}
				case "11011" : {
					if(rs1val < rdval) EX_IF_Latch.setjmpjmpAddr(immval); 
					break;
				}
				case "11100" : {
					if(rs1val > rdval) EX_IF_Latch.setjmpjmpAddr(immval); 
					break;
				}
				case "11101" : {
					EX_MA_Latch.isEnd = true;
					break;
				}
				default : break;

			}
			EX_MA_Latch.WriteAddr = WriteAddr;

			OF_EX_Latch.setEX_enable(false);
			EX_MA_Latch.setMA_enable(true);
		}
		//TODO
	}

}
