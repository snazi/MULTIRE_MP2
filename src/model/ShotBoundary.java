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
	public void getAbruptTransition(DifferenceMetric[] differenceMetricArray){
		for(int i = 0; i < differenceMetricArray.length; i++){
			if(differenceMetricArray[i].getDifferenceMetric() > Tb){
				abruptTransitionList.add(differenceMetricArray[i].getFilename());
				System.out.println("Shot Boundary: " + differenceMetricArray[i].getDifferenceMetric());
			}
		}
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
		
		for(int i = 0; i < differenceMetricArray.length; i++){
			if(differenceMetricArray[i].getDifferenceMetric() > Tb){
				System.out.println("Shot Boundary: " + differenceMetricArray[i].getFilename() + 
						"; DifferenceMetric: " + differenceMetricArray[i].getDifferenceMetric());
			}
			else if(differenceMetricArray[i].getDifferenceMetric() < Tb && differenceMetricArray[i].getDifferenceMetric() > Ts){
				// if diffMetric > Ts --> potential gradual transition, start accumulating differences from that frame
				// until the transition (diffMetric yata) becomes lower than Ts
				tempGradualTransitionList.add(differenceMetricArray[i].getFilename());
				accumulatedDifference += differenceMetricArray[i].getDifferenceMetric();
				
				System.out.println("Potential Gradual:" + differenceMetricArray[i].getFilename() + 
						"; DifferenceMetric: " + differenceMetricArray[i].getDifferenceMetric());
			}
			else if(differenceMetricArray[i].getDifferenceMetric() < Ts){
				// diffMetric < Ts --> stop accumulating --> compare if accumulatedDiff > Tb
				// if yes, then transfer tempGradualTransList to gradualTransList
				if(!tempGradualTransitionList.isEmpty()){
					if(accumulatedDifference > Tb){
						String gradualTransition = "";
						
						for(int j = 0; j < tempGradualTransitionList.size(); j++){
							gradualTransition += tempGradualTransitionList.get(j);
							if(j != tempGradualTransitionList.size()-1){
								gradualTransition += ", ";
							}
						}
						gradualTransitionList.add(gradualTransition);
						System.out.println("-------");
					}
					tempGradualTransitionList.clear();
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
