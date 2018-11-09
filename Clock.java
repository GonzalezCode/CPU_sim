/**
*@author Marcus Gonzalez
*@version 20170407
*/
public class Clock {
	int time;
	/**
	*Constructor for objects of class clock
	*/
	public Clock(){
		time = 0;
	}
	/**
	*returns clock time
	*@return time increments clock time
	*/
	public int incrementClock(){
		return ++time;
	}
	/**
	*@return time returns system time
	*/
	public int systemTime(){
		return time;
	}
}