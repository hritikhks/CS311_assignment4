package processor.pipeline;

import processor.Processor;

public class OperandFetch {
	Processor containingProcessor;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	
	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
	}
	
	public void performOF()
	{
		if(IF_OF_Latch.isOF_enable())
		{
			//TODO
			
			String ins = Integer.toBinaryString(IF_OF_Latch.getInstruction());
			while(ins.length() < 32) ins = "0" + ins;

			String opcode = ins.substring(0, 5);
			String imm = new String();
			String rs1 = new String();
			String rs2 = new String();
			String rd = new String();
			int op = Integer.parseInt(opcode,2);

			String aluSignal = new String();

			if(op == 0) {
				rs1 = ins.substring(5,10);
				rs2 = ins.substring(10,15);
				rd = ins.substring(15, 20);
			}
			else if(0 < op && op < 22) {
				rs1 = ins.substring(5,10);
				if(op % 2 == 0) {
					rs2 = ins.substring(10,15);
					rd = ins.substring(15, 20);
				}
				else {
					rs1 = ins.substring(10,15);
					imm = ins.substring(15, 32);
				}
			}
			else if(op == 22 || op == 23 || (24 < op && op < 29)) {
				rs1 = ins.substring(5,10);
				rs1 = ins.substring(10,15);
				imm = ins.substring(15, 32);
			} 
			else {
				rd = ins.substring(5,10);
				imm = ins.substring(10, 32);
			}

			String jmpwhere = new String();

			if(op > 23 && op < 29) {
				if(op == 24) jmpwhere = Integer.toString(Integer.parseInt(rd,2) + Integer.parseInt(imm,2));
				else jmpwhere = imm;
			}
			else jmpwhere = "noo";


			OF_EX_Latch.setjmpwhere(jmpwhere);



			OF_EX_Latch.setopcode(opcode);
			OF_EX_Latch.setrd(rd);
			OF_EX_Latch.setrs1(rs1);
			OF_EX_Latch.setrs2(rs2);
			OF_EX_Latch.setimm(imm);

			// System.out.println(opcode);
			// System.out.println();
			// System.out.println(rs1);
			// System.out.println();
			// System.out.println(rs2);
			// System.out.println();
			// System.out.println(rd);
			// System.out.println();
			// System.out.println(imm);
			IF_OF_Latch.setOF_enable(false);
			OF_EX_Latch.setEX_enable(true);
		}
	}

}
