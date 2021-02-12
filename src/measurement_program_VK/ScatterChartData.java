package measurement_program_VK;

import java.io.Serializable;

//Stores values of measurement

@SuppressWarnings("serial")
public class ScatterChartData implements Serializable{

	public int xAxisStart = 0, xAxisMax = 140, xAxisSpace = 10; //start value, max value and space between, coming soon
	public int yAxisStart = 0, yAxisMax = 140, yAxisSpace = 5;	//--.--
	public int [] xValue = new int[50];
	public int [] yValue = new int[50];
	public int count = 0;
	
	public int getCount() {
		return count;
	}

	public void insertValues(int xValue2, int yValue2) {
		this.xValue[count] = xValue2;
		this.yValue[count] = yValue2;
		count++;
	}
	
	public int getxValue(int i) {
		return xValue[i];
	}

	public int getyValue(int i) {
		return yValue[i];
	}
	
	public int getxAxisStart() {
		return xAxisStart;
	}

	public void setxAxisStart(int xAxisStart) {
		this.xAxisStart = xAxisStart;
	}

	public int getxAxisMax() {
		return xAxisMax;
	}

	public void setxAxisMax(int xAxisMax) {
		this.xAxisMax = xAxisMax;
	}

	public int getxAxisSpace() {
		return xAxisSpace;
	}

	public void setxAxisSpace(int xAxisSpace) {
		this.xAxisSpace = xAxisSpace;
	}

	public int getyAxisStart() {
		return yAxisStart;
	}

	public void setyAxisStart(int yAxisStart) {
		this.yAxisStart = yAxisStart;
	}

	public int getyAxisMax() {
		return yAxisMax;
	}

	public void setyAxisMax(int yAxisMax) {
		this.yAxisMax = yAxisMax;
	}

	public int getyAxisSpace() {
		return yAxisSpace;
	}

	public void setyAxisSpace(int yAxisSpace) {
		this.yAxisSpace = yAxisSpace;
	}
    
}
