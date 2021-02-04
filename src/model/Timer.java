package model;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import it.unical.mat.embasp.languages.asp.SymbolicConstant;

@Id("timer")
public class Timer {
    @Param(0)
    private int time;

	public Timer(int t) {
		this.time=t;
		}
    public Timer() {
    }
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;   
    }
}