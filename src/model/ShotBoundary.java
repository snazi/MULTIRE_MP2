/*
 * Performs the Shot Boundary (SDi > Tb or Ts) for abrupt transition and gradual transition
 */

package model;

import java.util.ArrayList;

public class ShotBoundary {
	
	int a; // used for Tb threshold; user defined/based on experiments
	double Tb; // the Tb threshold
	int Ts; // the TS threshold; user defined/based on experiments
	ArrayList<String> abruptTransitionList; // contains the Tb shot boundaries
	ArrayList<String> gradualTransitionList; // contains the Ts shot boundaries
	
	public ShotBoundary(int a, int Ts){
		this.a = a;
		this.Ts = Ts;
		this.Tb = 0;
		abruptTransitionList = new ArrayList<String>(0);
		gradualTransitionList = new ArrayList<String>(0);
	}
	
	// Computes for the Shot Boundary (SDi > Tb)
	public ArrayList<Integer> getAbruptTransition(DifferenceMetric[] differenceMetricArray){
		ArrayList<Integer> temp = new ArrayList<Integer>(0);
		for(int i = 0; i < differenceMetricArray.length; i++){
			if(differenceMetricArray[i].getDifferenceMetric() > Tb){
				abruptTransitionList.add(differenceMetricArray[i].getFilename());
				System.out.println("Shot Boundary: " + differenceMetricArray[i].getDifferenceMetric());
				temp.add(i);
			}
		}
		
		return temp;
	}
	
	// Prints the abruptTransitionList
	public void printAbrupt(){
		System.out.println("Abrupt Transition List:");
		if(abruptTransitionList.isEmpty()){
			System.out.println("No abrupt transitions");
		}
		else{
			for(int i = 0; i < abruptTransitionList.size(); i++){
				System.out.println(abruptTransitionList.get(i));
			}
		}
	}
	
	// Computes for the Gradual Transitions
	public void getGradualTransition(DifferenceMetric[] differenceMetricArray){
		ArrayList<String> tempGradualTransitionList = new ArrayList<String>(0);
		int accumulatedDifference = 0;
		String frameStart="";
		String frameEnd;
		int tolerance = 0;
		boolean foundGradualBoundary = false;
		
		for(int i = 0; i < differenceMetricArray.length; i++){
			//iterate over the SDi Array
			
			if(differenceMetricArray[i].getDifferenceMetric() > Tb){
				//if SDi is greater than Threshold we've found our abrupt shot boundary
				System.out.println("Shot Boundary: " + differenceMetricArray[i].getFilename() + 
						"; DifferenceMetric: " + differenceMetricArray[i].getDifferenceMetric());
				
			}else if((differenceMetricArray[i].getDifferenceMetric() < Tb && differenceMetricArray[i].getDifferenceMetric() > Ts) || foundGradualBoundary){
				// if diffMetric > Ts --> potential gradual transition, start accumulating differences from that frame
				// until the transition (diffMetric yata) becomes lower than Ts
				
				//if SDi is less than Tb it isnt an abrupt change but, if SDi is greater than Ts its a possible gradual transition
				//it is possible that the next frame is less than Ts so we can disregard it but count it as part of the interval
				//this is where tolerance comes in. we only allow 3 frames below Ts to be part of the equation
				
				if(!foundGradualBoundary){
					//this means that it has not detected a starting frame or "Fs" in the slides
					// so we set the current frame to be Fs
					frameStart = differenceMetricArray[i].getFilename();
					foundGradualBoundary = true;
				}
				
				if(differenceMetricArray[i].getDifferenceMetric() < Ts){
					// if the frame is less that Ts we can "tolerate" it but only til indicated
					tolerance++;
					if(tolerance >= 6){
						foundGradualBoundary = false;
						tolerance =0;
					}
				}
				
				if(differenceMetricArray[i].getDifferenceMetric() > Ts)
				accumulatedDifference += differenceMetricArray[i].getDifferenceMetric();
				
				if(accumulatedDifference > Tb){
					frameEnd = differenceMetricArray[i].getFilename();
					
					System.out.println("Fs: "+frameStart+" Fe: "+frameEnd);
					
					tolerance=0;
					foundGradualBoundary = false;
					accumulatedDifference = 0;
				}
				
				
			}
		}
	}
	
	

	// Prints the gradualTransitionList
	public void printGradual(){
		System.out.println("Gradual Transition List:");
		if(gradualTransitionList.isEmpty()){
			System.out.println("No gradual transitions");
		}
		else{
			for(int i = 0; i < gradualTransitionList.size(); i++){
				System.out.println(gradualTransitionList.get(i));
			}
		}
	}
	
	// Computes for the Tb threshold
	public double computeTb(double averageDM, double stdev){
		// Tb  = u + ao
		Tb = averageDM + (a*stdev);
		
		System.out.println("Tb: " + Tb);
		
		return Tb;
	}
}
