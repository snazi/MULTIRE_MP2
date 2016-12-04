package Driver;

import java.io.File;
import java.util.ArrayList;

import controller.HistogramController;
import model.*;

public class Driver {
	
	public static void main(String[] Args){
		testSystem();
	}
	
//	public static void getHistogram(){
//		//this method is just for testing, not part of the final driver.
//		ImageObject CHimg1 = ImageController.convertImageCH("images/777/", "0000.jpg");
//		CHimg1.printPercentage();
//		CHimg1.printCheck();
//		System.out.println("done");
//	}
	
	/*public static void testHistogram(){
		String path = "images/777/";
		ArrayList<ImageObject> testVar = new HistogramController().getHistogramOfAllFrames(path);
		
		for(int x=0; x<testVar.size();x++){
			testVar.get(x).printCheck();
		}
	}*/
	
	public static void testSystem(){
		String path = "images/uni/";
		File folder = new File(path);
		int numFiles = folder.listFiles().length; // needed to assign the index for the difference metric
		int[] frame1 = new int[159]; // 159 LUV values
		int[] frame2 = new int[159]; // 159 LUV values
		HistogramDifferenceMetric differenceMetric = new HistogramDifferenceMetric(numFiles);
		
		// Loops through the all of the frames, getting all the color frequency count of each frame
		// and computes for the difference metric
		// Histogram difference metric is used to get the SDi of frame1 and frame2
		for(int i = 0; i < numFiles; i++ ){
			File fileEntry = folder.listFiles()[i];
			String file = fileEntry.getName();
			ImageController imageController = new ImageController();
			
			frame1 = frame2;
			frame2 = imageController.convertImageCH(path, file);
			
			if(i > 0){
				int dM = differenceMetric.computeDifferenceMetric(frame1, frame2);
				differenceMetric.addDifferenceMetricArray(dM, file, i-1); // difference metric is only until maxNumFrame-1
				
//				System.out.println((i-1) + " : " + dM);
			}
		}
		
		ShotBoundary shotBoundary = new ShotBoundary(6, 450); //ShotBoundary(a, Ts)
		shotBoundary.computeTb(differenceMetric.averageAllDifferenceMetric(), differenceMetric.stdevAllDifferenceMetric());
		shotBoundary.getAbruptTransition(differenceMetric.getDifferenceMetricArray());
		shotBoundary.printAbrupt();
		shotBoundary.getGradualTransition(differenceMetric.getDifferenceMetricArray());
		shotBoundary.printGradual();
	}
	
}
