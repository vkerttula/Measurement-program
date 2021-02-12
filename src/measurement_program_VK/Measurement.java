package measurement_program_VK;

import java.io.Serializable;

//Base of measurement data. Including ID, title and x-axis and y-axis label
//Measurement values itself are in ScatterChartData.java

@SuppressWarnings("serial")
public class Measurement extends ScatterChartData implements Serializable{
	
	private int measurementID;    
    private String xLabel;
	private String yLabel;
	private String graphTitle;
    
    public int getMeasurementID() {
		return measurementID;
	}

	public void setMeasurementID(int measurementID) {
		this.measurementID = measurementID;
	}

    public Measurement(int measurementID, String graphTitle, String xLabel, String yLabel) {
    	this.measurementID = measurementID;
    	this.graphTitle = graphTitle;
    	this.xLabel = xLabel;
    	this.yLabel = yLabel;
    }

	public String getxLabel() {
		return xLabel;
	}

	public void setxLabel(String xLabel) {
		this.xLabel = xLabel;
	}

	public String getyLabel() {
		return yLabel;
	}

	public void setyLabel(String yLabel) {
		this.yLabel = yLabel;
	}

	public String getGraphTitle() {
		return graphTitle;
	}

	public void setGraphTitle(String graphTitle) {
		this.graphTitle = graphTitle;
	}
}