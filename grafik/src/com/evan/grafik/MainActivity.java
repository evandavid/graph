package com.evan.grafik;

import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
	
	private final Handler mHandler = new Handler();
	private Runnable mTimer1;
	private Runnable mTimer2;
	private GraphView graphView;
	private GraphViewSeries exampleSeries1;
	private GraphViewSeries exampleSeries2;
	private GraphViewData[] data;
	private double graph2LastXValue = 5d;
	private double x = 1;
	

	private double getRandom() {
		double high = 3;
		double low = 0.5;
		return Math.random() * (high - low) + low;
	}
	
	private double getX(){
		x = x+ 0.1;
		return x;
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		data = new GraphViewData[60];
//		int j = 0;
//		for (double i = 0; i < 5; i+= 0.1) {
//			data[j] = new GraphViewData(i, getRandom());
//			j++;
//		}
		// init example series data
		data = new GraphViewData[] {
				new GraphViewData(getX(), getRandom()),
				new GraphViewData(getX(), getRandom()),
				new GraphViewData(getX(), getRandom()),
				new GraphViewData(getX(), getRandom()),
				new GraphViewData(getX(), getRandom()),
				new GraphViewData(getX(), getRandom()),
				new GraphViewData(getX(), getRandom()),
				new GraphViewData(getX(), getRandom()),
				new GraphViewData(getX(), getRandom()),
				new GraphViewData(getX(), getRandom()),
				new GraphViewData(getX(), getRandom()),
				new GraphViewData(getX(), getRandom()),
				new GraphViewData(getX(), getRandom()),
				new GraphViewData(getX(), getRandom()),
				new GraphViewData(getX(), getRandom()),
				new GraphViewData(getX(), getRandom()),
				new GraphViewData(getX(), getRandom()),
				new GraphViewData(getX(), getRandom()),
				new GraphViewData(getX(), getRandom()),
				new GraphViewData(getX(), getRandom()),
				new GraphViewData(getX(), getRandom()),
				new GraphViewData(getX(), getRandom()),
				new GraphViewData(getX(), getRandom()),
				new GraphViewData(getX(), getRandom()),
				new GraphViewData(getX(), getRandom()),
				new GraphViewData(getX(), getRandom()),
				new GraphViewData(getX(), getRandom()),
				new GraphViewData(getX(), getRandom()),
				new GraphViewData(getX(), getRandom()),
				new GraphViewData(getX(), getRandom()),
				new GraphViewData(getX(), getRandom()),
		};
		exampleSeries1 = new GraphViewSeries(data);

		// graph with dynamically genereated horizontal and vertical labels
			graphView = new LineGraphView(
					this // context
					, "GraphViewDemo" // heading
			);
		graphView.addSeries(exampleSeries1); // data

		LinearLayout layout = (LinearLayout) findViewById(R.id.graph);
		layout.addView(graphView);

		// ----------
		exampleSeries2 = new GraphViewSeries(new GraphViewData[] {
				new GraphViewData(1, 2.0d)
				, new GraphViewData(2, 1.5d)
				, new GraphViewData(2.5, 3.0d) // another frequency
				, new GraphViewData(3, 2.5d)
				, new GraphViewData(4, 1.0d)
				, new GraphViewData(5, 3.0d)
		});

		// graph with custom labels and drawBackground
			graphView = new LineGraphView(
					this
					, "GraphViewDemo"
			);
			((LineGraphView) graphView).setDrawBackground(true);
		graphView.addSeries(exampleSeries2); // data
		graphView.setViewPort(1, 4);
		graphView.setScalable(true);

		layout = (LinearLayout) findViewById(R.id.graph);
		layout.addView(graphView);
	}

	@Override
	protected void onPause() {
		mHandler.removeCallbacks(mTimer1);
		mHandler.removeCallbacks(mTimer2);
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mTimer1 = new Runnable() {
			@Override
			public void run() {
				x = x + 0.1;
				GraphViewData[] tmp = data;
				for (int i = 0; i < (tmp.length-1); i++) {
					tmp[i] = data[i+1];
				}
				tmp[data.length-1] =  new GraphViewData(getX(), getRandom());
				exampleSeries1.resetData(tmp);
				mHandler.postDelayed(this, 100);
			}
		};
		mHandler.postDelayed(mTimer1, 100);

		mTimer2 = new Runnable() {
			@Override
			public void run() {
				graph2LastXValue += 1d;
				exampleSeries2.appendData(new GraphViewData(graph2LastXValue, getRandom()), true);
				mHandler.postDelayed(this, 1000);
			}
		};
		mHandler.postDelayed(mTimer2, 1000);
	}

	
}
