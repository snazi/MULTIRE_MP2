package controller;

import java.util.ArrayList;

import model.*;

public class HistogramController {

	public String getFileName(int num){
		//function returns the file name
		if(num < 10){
			return "000"+num+"";
		}else if(num < 100){
			return "00"+num+"";
		}else if(num < 1000){
			return "0"+num+"";
		}
		return "file";
	}
	
	public ArrayList<ImageObject> getHistogramOfAllFrames(){
		//returns ArrayList of each frame's histogram
		
		ArrayList<ImageObject> everything = new ArrayList<ImageObject>();
		for(int x = 0; x<721; x++){
			ImageObject temp = ImageController.convertImageCH("images/777/", getFileName(x)+".jpg");
			everything.add(temp);
		}
		
		return everything;
		
	}
}
