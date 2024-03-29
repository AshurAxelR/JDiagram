package com.xrbpowered.jdiagram.chart;

import java.io.PrintStream;
import java.util.Iterator;

import com.xrbpowered.jdiagram.data.NumberFormatter;

public class ValueAxis extends Axis {

	public boolean log = false;
	
	public double min = 0;
	public double max = 0;
	public double gridStep = 0;

	public NumberFormatter numberFmt = NumberFormatter.simple("%.1e");

	public ValueAxis setLogRange(double min, double max) {
		return setRange(true, min, max, 10);
	}

	public ValueAxis setLogRange(double min, double max, double step) {
		return setRange(true, min, max, step);
	}

	public ValueAxis setRange(double min, double max, double step) {
		return setRange(log, min, max, step);
	}

	public ValueAxis setRange(boolean log, double min, double max, double step) {
		this.log = log;
		this.min = min;
		this.max = max;
		this.gridStep = step;
		return this;
	}
	
	@Override
	public ValueAxis setAnchor(Anchor anchor) {
		return (ValueAxis) super.setAnchor(anchor);
	}
	
	@Override
	public ValueAxis setLabel(String label, Anchor labelAnchor) {
		return (ValueAxis) super.setLabel(label, labelAnchor);
	}

	@Override
	public ValueAxis setLabel(String label) {
		return (ValueAxis) super.setLabel(label);
	}
	
	public ValueAxis setNumberFormatter(NumberFormatter numberFmt) {
		this.numberFmt = numberFmt;
		return this;
	}

	public ValueAxis setNumberFmt(String fmt) {
		this.numberFmt = NumberFormatter.simple(fmt);
		return this;
	}

	@Override
	public double calc(double v) {
		if(log)
			return v<=0 ? 0 : Math.log(v/min) / Math.log(max/min);
		else
			return (v-min) / (max-min);
	}

	@Override
	public double zero() {
		return log ? 1 : 0;
	}
	
	public Iterator<Double> gridPoints() {
		return new Iterator<Double>() {
			private Double x = null; 
			@Override
			public boolean hasNext() {
				return (x==null || x<max && nextValue()<=max && Double.isFinite(x)) && gridStep>zero();
			}
			private double nextValue() {
				return (x==null) ? min : log ? x*gridStep : x+gridStep;
			}
			@Override
			public Double next() {
				x = nextValue();
				return x;
			}
		};
	}

	public void gridxNumber(PrintStream out, Chart chart, double v) {
		gridxNumber(out, chart, v, numberFmt.format(v));
	}

	public void gridyNumber(PrintStream out, Chart chart, double v) {
		gridyNumber(out, chart, v, numberFmt.format(v));
	}

}
