package processor;

public class Clock {
	static long currentTime = 0;
	static long num_stall = 0;
	static long num_wrongpath = 0;
	public static void incrementClock()
	{
		currentTime++;
	}
	
	public static long getCurrentTime()
	{
		return currentTime;
	}
}
