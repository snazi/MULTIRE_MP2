package model;

public class DifferenceMetric {

	int differenceMetric;
	String filename;
	
	public DifferenceMetric(int differenceMetric, String filename){
		this.differenceMetric = differenceMetric;
		this.filename = filename;
	}

	public int getDifferenceMetric() {
		return differenceMetric;
	}

	public String getFilename() {
		return filename;
	}
}
