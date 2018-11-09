import java.util.*;
import java.io.*;
/**
*@author Marcus Gonzalez
*@version 20170407
*/
public class MFQ {
	PrintWriter pw;
	int totalJobs;
	int queueTime;
	ObjectQueueInterface inputQueue;
	ObjectQueueInterface q1;
	ObjectQueueInterface q2;
	ObjectQueueInterface q3;
	ObjectQueueInterface q4;
	ObjectQueueInterface arrival;
	CPU cpu;
	/**
	*Constructor for objects of class MFQ
	*@param pw prints to "csis.txt"
	*/
	public MFQ(PrintWriter pw){
		this.pw = pw;
		inputQueue = new ObjectQueue();
		q1 = new ObjectQueue();
		q2 = new ObjectQueue();
		q3 = new ObjectQueue();
		q4 = new ObjectQueue();
		arrival = new ObjectQueue();
		cpu = new CPU();
		totalJobs = 0;
		queueTime = 0;
	}
	/**
	*Gets jobs from mfq.txt and stores in inputQueue
	*/
	public void getJobs()throws IOException{
		Scanner sc = new Scanner(new File("mfq.txt"));
		int pid = 0;
		int arrivalTime = 0;
		int cpuTime = 0;
		while(sc.hasNextLine()){
			++totalJobs;
			arrivalTime = sc.nextInt();
			arrival.insert(arrivalTime);
			pid = sc.nextInt();
			cpuTime = sc.nextInt();
			Job job = new Job(arrivalTime, pid, cpuTime);
			
			inputQueue.insert(job);
		}
	}
	/**
	*prints simulation header
	*/
	public void outputHeader(){
		System.out.println("Event\tSystem Time\tPID\tCPU Time req\t\tTotal time in system\tLowest Queue");
		pw.println("Event\t\tSystem Time\tPID\tCPU Time req\tTotal time in system\tLowest Queue\n");
	}
	/**
	*calls method to run simulation
	*/
	public void runSimulation(){
		simulation();
	}
	/**
	*Runs simulation using multiple queues
	*/
	private void simulation(){
		Clock clock = new Clock();
		Job onCPU = new Job();
		Job newJob = new Job();
		int totalTime = 0;
	
		while (!jobComplete()){
			clock.incrementClock();
			//check for arrival
			if(!arrival.isEmpty()&&(int)arrival.query()==clock.systemTime()){
				newJob = (Job)inputQueue.query();
				System.out.printf("Arrival\t%d\t%d\t%d\n",clock.systemTime(),newJob.getPid(),newJob.getCpuTime());
				pw.printf("Arrival\t%d\t\t\t%d\t%d\n",clock.systemTime(),newJob.getPid(),newJob.getCpuTime());
				q1.insert(inputQueue.remove());
				arrival.remove();
			}
			//handle jobs on cpu
			if(cpu.cpuBusy()){
				onCPU.decrementTime();
				cpu.decrementQuantum();
				checkQueue();
				if (onCPU.getCpuTime()==0) {
					totalTime = clock.systemTime() - onCPU.getArrivalTime();
					cpu.addJobTime(totalTime);
					System.out.printf("Departure\t%d\t%d\t%d\t%d\n",clock.systemTime(),onCPU.getPid(),totalTime,onCPU.getQueue());
					pw.printf("Departure\t%d\t\t\t%d\t\t\t\t%d\t\t\t\t%d\n",clock.systemTime(),onCPU.getPid(),totalTime,onCPU.getQueue());
					cpu.setBusyFlag(false);
					cpu.resetQuantum();
					onCPU = (Job)submitJob(onCPU);
				}
				//check for preemptions
				else if(!q1.isEmpty() || cpu.checkQuantum()==true){
					if(onCPU.getQueue()!=4){
						onCPU.incrementQueue();
					}
						switch (onCPU.getQueue()) {//send job to next lower level queue after queue is incremented
							case 2:q2.insert(onCPU);break;
							case 3:q3.insert(onCPU);break;
							case 4:q4.insert(onCPU);break;
						}
						cpu.setBusyFlag(false);
						cpu.resetQuantum();
						onCPU = (Job)submitJob(onCPU);
								}
							}
			else{//submit job
				cpu.incrementIdleTime();
				checkQueue();
				cpu.resetQuantum();
				onCPU = (Job)submitJob(onCPU);
			}
		}
		
	}
	/**
	*@return onCPU returns job that has the CPU
	*Puts job with highest priority on CPU
	*/
	private Job submitJob(Job onCPU){
		if(q1.isEmpty() && q2.isEmpty() && q3.isEmpty() && !q4.isEmpty()){
			onCPU = (Job)q4.remove();
			cpu.setQuantum(onCPU.getQueue());
			cpu.setBusyFlag(true);
		}
		if(q1.isEmpty() && q2.isEmpty() && !q3.isEmpty()){
			onCPU = (Job)q3.remove();
			cpu.setQuantum(onCPU.getQueue());
			cpu.setBusyFlag(true);
		}
		if(q1.isEmpty() && !q2.isEmpty()){
			onCPU = (Job)q2.remove();
			cpu.setQuantum(onCPU.getQueue());
			cpu.setBusyFlag(true);
		}
		if(!q1.isEmpty()){
			onCPU = (Job)q1.remove();
			cpu.setQuantum(onCPU.getQueue());
			cpu.setBusyFlag(true);
		}
		return onCPU;
	}
	/**
	*@return boolean returns true if all queues are empty
	*/
	private boolean jobComplete(){
		if(inputQueue.isEmpty())
			if(q1.isEmpty())
				if(q2.isEmpty())
					if(q3.isEmpty())
						if(q4.isEmpty())
							if(!cpu.cpuBusy())
								return true;
		return false;
	}
	/**
	*Outputs System stats
	*/
	public void outStats(){
		float responseTime = 1;
		System.out.println("\nTotal Jobs\t\tTotal Time of Jobs\tAvg. Response Time\tAverage Turnaround\tAvg. Wait\t\tSystem Avg.\tTotal CPU Idle");
		pw.println("\nTotal Jobs\t\tTotal Time of Jobs\tAvg. Response Time\tAverage Turnaround\tAvg. Wait\t\tSystem Avg.\tTotal CPU Idle");
		System.out.printf("%d\t\t\t%d\t\t\t%.2f\t\t\t%.2f\t\t\t%.2f\t\t\t%.2f\t\t\t%d",totalJobs,cpu.totalJobTime(),responseTime,(float)cpu.totalJobTime()/(float)totalJobs,(float)queueTime/(float)totalJobs,(float)totalJobs/(float)cpu.totalJobTime(),cpu.getIdleTime());
		pw.printf("%d\t\t\t%d\t\t\t\t%.2f\t\t\t\t%.2f\t\t\t\t%.2f\t\t\t%.2f\t\t\t%d",totalJobs,cpu.totalJobTime(),responseTime,(float)cpu.totalJobTime()/(float)totalJobs,(float)queueTime/(float)totalJobs,(float)totalJobs/(float)cpu.totalJobTime(),cpu.getIdleTime());

		
	}
	/**
	*Calculates total job wait time
	*/
	private void checkQueue(){
		if (!q1.isEmpty()|!q2.isEmpty()|!q3.isEmpty()|!q4.isEmpty())
			++queueTime;
		
	}
	
	
}