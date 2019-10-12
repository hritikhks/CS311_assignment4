package processor.pipeline;

public class MA_RW_LatchType {
	
	boolean RW_enable;
	int aluResult;
	int WriteAddr;
	int LoadAddr;
	boolean isEnd;
	boolean isLoad;
	int currentIns;
	String currentop;
	
	public MA_RW_LatchType()
	{
		RW_enable = false;
		isEnd = false;
		isLoad = false;
		currentIns = -1;
		currentop = "11111";
	}

	public boolean isRW_enable() {
		return RW_enable;
	}

	public void setisLoad(boolean val) {
		this.isLoad = val;
	}

	public boolean getisLoad() {
		return isLoad;
	}

	public void setRW_enable(boolean rW_enable) {
		RW_enable = rW_enable;
	}

	public void setLoadAddr(int a) {
		this.LoadAddr = a;
	}
	public int getLoadAddr() {
		return LoadAddr;
	}


}
