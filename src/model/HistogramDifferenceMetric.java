/*
 * Computes for the Histogram Difference Metric (SDi)
 */

package model;

public class HistogramDifferenceMetric {

	ImageController imageController;
	DifferenceMetric[] differenceMetricArray;
	double averageDifferenceMetric;
	int numFiles;
	
	public HistogramDifferenceMetric(int numFiles){
		imageController = new ImageController();
		differenceMetricArray = new DifferenceMetric[numFiles-1]; //SD: 0 to numFiles-1
		averageDifferenceMetric = 0;
		this.numFiles = numFiles-1;
	}
	
	// Computes for the SDi of a frame
	public int computeDifferenceMetric(int[] frame1, int[] frame2){
		int differenceMetric = 0;
		
		for(int i = 0; i < frame1.length; i++){
			differenceMetric += Math.abs(frame1[i] - frame2[i]);
		}
		
		averageDifferenceMetric += differenceMetric;
		return differenceMetric;
	}
	
	// Add the SDi to the SDi array (differenceMetricArray)
	public void addDifferenceMetricArray(int differenceMetric, String filename, int index){
		differenceMetricArray[index] = new DifferenceMetric(differenceMetric, filename);
	}
	
	// Computes for the average of all the SDi
	public double averageAllDifferenceMetric(){
		averageDifferenceMetric = averageDifferenceMetric/numFiles;
		return averageDifferenceMetric;
	}
	
	// Computes fo the Standard Deviation
	public double stdevAllDifferenceMetric(){
		double stdev = 0;
		
		// Either STDEV formula can be applied, but the first one yields higher results (by 2.something) than second
		// computing for the deviation
//		float powerSum1 = 0;
//		float powerSum2 = 0;
//		for(int i = 0; i < numFiles; i++){
//			powerSum1 += differenceMetricArray[i];
//			powerSum2 += Math.pow(differenceMetricArray[i], 2);
//			
//			stdev = Math.sqrt(i * powerSum2 - Math.pow(powerSum1, 2))/i;
//		}
//		System.out.println(stdev);
		
		// computing for the deviation
		stdev = 0;
		for(int i = 0; i < numFiles; i++){
			stdev += ((differenceMetricArray[i].getDifferenceMetric() - averageDifferenceMetric) * 
					(differenceMetricArray[i].getDifferenceMetric() -averageDifferenceMetric)) / (numFiles);
		}
		stdev = Math.sqrt(stdev);
		
		return stdev;
	}
	
	
	//GETTERS
	public DifferenceMetric[] getDifferenceMetricArray() {
		return differenceMetricArray;
	}
	
	public double getAverageDifferenceMetric() {
		return averageDifferenceMetric;
	}
	
}
