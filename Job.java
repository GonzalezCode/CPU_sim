/**
*@author Marcus Gonzalez
*@version 20170407
*/

public class Job {
	int pid;
	int arrivaltime;
	int cputimereq;
	int currentqueue;
	/**
	*Constructor for objects of class Job
	*/
	public Job(){
		pid = 0;
		arrivaltime = 0;
		cputimereq = 0;
		currentqueue = 1;
	}
	/**
	*Overloaded Constructor for objects of class jobs
	*@param arrivalTime the time a job arrives
	*@param pid Process Identifier
	*@param cputimereq the time required for job completion
	*/
	public Job(int arrivaltime,int pid,int cputimereq){
		this.pid = pid;
		this.arrivaltime = arrivaltime;
		this.cputimereq = cputimereq;
		currentqueue = 1;
	}
	/**
	*decrements job time required for completion
	*/
	public void decrementTime(){
		--cputimereq;
	}
	/**
	@return cputimereq returns cpu time required
	*/
	public int getCpuTime(){
		return cputimereq;
	}
	/**
	*@return arrivaltime returns job arrival time
	*/
	public int getArrivalTime(){
		return arrivaltime;
	}
	/**
	*@return pid returns Job Process Identifier
	*/
	public int getPid(){
		return pid;
	}
	/**
	*increments job queue
	*/
	public void incrementQueue(){
		++currentqueue;
	}
	/**
	*@return currentqueue returns the jobs current queue
	*/
	public int getQueue(){
		return currentqueue;
	}
}