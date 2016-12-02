package Driver;

import model.ImageObject;

import java.util.ArrayList;

import controller.HistogramController;
import model.*;

public class Driver {
	public static void main(String[] Args){
		test();
	}
	
	public static void getHistogram(){
		//this method is just for testing, not part of the final driver.
		ImageObject CHimg1 = ImageController.convertImageCH("images/777/", "0000.jpg");
		CHimg1.printPercentage();
		CHimg1.printCheck();
		System.out.println("done");
	}
	
	public static void test(){
		ArrayList<ImageObject> testVar = new HistogramController().getHistogramOfAllFrames();
		
		for(int x=0; x<testVar.size();x++){
			testVar.get(x).printCheck();
		}
	}
	
	
}
