package model;

import java.util.ArrayList;

public class keyframe {
	
	//function returns an arraylist of the index of the keyframe per shot
	public ArrayList<Integer> getKeyframe(ArrayList<int []> frameHistograms, ArrayList<Integer> boundary){
		
		ArrayList<Integer> keyframes = new ArrayList<Integer>(0);
		ArrayList<int []> averageHistograms = new ArrayList<int []>(0);
		
		if(boundary.isEmpty()){
			//if there is no boundary specifically in mjack video, then there is only 1 keyframe to look for
			//we use the function to iterate over the whole video per frame instead of iteratiing until the boundary
			averageHistograms.add(getAverageHistogram(frameHistograms));
		}else{
			//else there is a boundary so there is at least 1 keyframe to look for
			ArrayList<int []> temp = new ArrayList<int []>(0);
			for(int i=0;i<frameHistograms.size();i++){
				//add the arraylist to another one in order to feed it to the function
				temp.add(frameHistograms.get(i));
				if(boundary.contains(i)){
					//this means we've hit a boundary
					int[] temp1 = getAverageHistogram(temp);
					averageHistograms.add(temp1);// adding to the average histogram is just to check the values for debugging purposes
					keyframes.add(getClosest(temp, temp1));
					temp = new ArrayList<int []>(0);
				}
				
				if(i == frameHistograms.size()-1){
					//from last boundary to the last frame
					int[] temp1 = getAverageHistogram(temp);
					averageHistograms.add(temp1);
					keyframes.add(getClosest(temp, temp1));
					
				}
			}
		}
		
		for(int nCtr = 0; nCtr<keyframes.size(); nCtr++){
			System.out.println(keyframes.get(nCtr));
		}
		
		return keyframes;
	}
	
	public int[] getAverageHistogram(ArrayList<int []> frameHistograms){
		
		int[] result = new int[159];
		
		for(int i = 0; i<frameHistograms.size(); i++){
			for(int j = 0; j<159;j++){
				result[j]+=frameHistograms.get(i)[j];
			}
		}
		
		for(int j = 0; j<159; j++){
			result[j] /= frameHistograms.size();
		}
		
		return result;
	}
	
	public int getClosest(ArrayList<int []> framesInShot, int[] averageHistogram){
		int lowest =999999;
		int index= 0;
		
		for(int i = 0; i<framesInShot.size(); i++){
			int temp=0;
			for(int j=0; j<159;j++){
				temp+= Math.abs(framesInShot.get(i)[j]-averageHistogram[j]);
			}
			
			if(temp < lowest){
				lowest = temp;
				index = i;
			}
		}
		
		return index;
	}

}
