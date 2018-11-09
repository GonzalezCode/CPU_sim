import java.io.*;
/**
*@author Marcus Gonzalez/ID:011017835
*@version 20170407
*/
class Driver {
	public static void main(String[] args) throws IOException {
		PrintWriter pw = new PrintWriter(new FileWriter("csis.txt"));
		MFQ mfq = new MFQ(pw);
		mfq.getJobs();
		mfq.outputHeader();
		mfq.runSimulation();
		mfq.outStats();
		pw.close();
	}
}