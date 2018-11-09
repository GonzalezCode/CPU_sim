import java.awt.*;
/**
*@author Marcus Gonzalez
*@version 20170407
*/
public class CPU {
	int quantum;
	int queue;
	boolean flag;
	int time;
	int idle;
	/**
	*Constructor for objects of class CPU
	*/
	public CPU(){
		idle = 0;
		time = 0;
		quantum = 0;
		queue = 0;
		flag = false;
			}
	/**
	*@param flag boolean variable
	*sets busy flag to true or false
	*/
	public void setBusyFlag(boolean flag){
		this.flag = flag;
	}
	/**
	*@return flag returns true if cpu is busy
	*/
	public boolean cpuBusy(){
		return flag;
	}
	/**
	*@param queue the current que of the job
	*sets quantum to the time allotted to each queue
	*/
	public void setQuantum(int queue){
		this.queue = queue;
		
		switch (queue) {
			case 1:quantum = 2;break;
			case 2:quantum = 4;break;
			case 3:quantum = 8;break;
			case 4:quantum = 16;break;
		}
	}
	/**
	*@return quantum returns a decremented quantum
	*/
	public int decrementQuantum(){
		return --quantum;
	}
	/**
	*Resets the quantum to 0
	*/
	public void resetQuantum(){
		quantum = 0;
	}
	/**
	*@return quantum returns true if quantum is 0
	*/
	public boolean checkQuantum(){
		return quantum == 0;
	}
	/**
	*@param time job time to be added to total of all jobs
	*/
	public void addJobTime(int time){
		this.time +=time;
	}
	/**
	*@return time returns total job time
	*/
	public int totalJobTime(){
		return time;
	}
	/**
	*increments cpu idle time
	*/
	public void incrementIdleTime(){
		 ++idle;
	}
	/**
	*@return idle returns total cpu idle time
	*/
	public int getIdleTime(){
		return idle;
	}
}