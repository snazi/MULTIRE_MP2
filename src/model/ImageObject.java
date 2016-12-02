package model;

import java.util.ArrayList;

public class ImageObject {
	
	//there are only 159 LUV values
	int[] histogramOfImage = new int[159];
	int totalPixels;
	ArrayList<Float> percentageOfColors = new ArrayList<Float>();
	
	// For Color Histogram
	public ImageObject(int[] histogram, int pixels){
		histogramOfImage = histogram; // the LUV values
		totalPixels = pixels;
		getFrequencyOfColors();
	}
	
	public void printPercentage(){
		for(int x =0; x<percentageOfColors.size();x++){
			System.out.println(percentageOfColors.get(x));
		}
	}
	
	public void printCheck(){
		float ans = 0;
		
		for(int x =0; x<percentageOfColors.size();x++){
			ans+= percentageOfColors.get(x);
		}
		
		System.out.println(ans);
	}
	
	public void getFrequencyOfColors(){
		
		//159 is the total number of LUV colors we have
		for(int x=0; x<159; x++){
			
				float ans = (float)histogramOfImage[x] / (float)totalPixels; 
				percentageOfColors.add(ans);
			
		}
		
	}
}
