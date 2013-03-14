package kr.dev.parktrio.puzzle2d;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PuzzleActivity extends Activity implements OnClickListener {
	private PuzzleProblem problem;

	private int xTileSize;
	private int yTileSize;
	private Button[][] tileView;

	private LinearLayout layoutWhole;
	private RelativeLayout layoutPuzzleTiles;
	private TextView textDesc;
	private Button buttonReset;
	private EditText editHard;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_puzzle);

		this.layoutWhole = (LinearLayout) findViewById(R.id.LayoutWhole);
		this.layoutPuzzleTiles = (RelativeLayout) findViewById(R.id.layoutPuzzleTiles);
		this.textDesc = (TextView) findViewById(R.id.textDesc);
		this.buttonReset = (Button) findViewById(R.id.buttonReset);
		this.editHard = (EditText) findViewById(R.id.editHard);

		this.xTileSize = Integer.parseInt(this.editHard.getText().toString());
		this.yTileSize = Integer.parseInt(this.editHard.getText().toString());
		this.problem = new PuzzleProblem(this.xTileSize, this.yTileSize);

		this.buttonReset.setOnClickListener(this);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		this.layoutTiles();

		super.onWindowFocusChanged(hasFocus);
	}

	private void layoutTiles() {
		if (this.tileView == null)
		{
			this.tileView = new Button[this.xTileSize][this.yTileSize];
		}

		int xUnit = (this.layoutPuzzleTiles.getWidth() - ((this.xTileSize -1) * 4)) / this.xTileSize;
		int yUnit = (this.layoutPuzzleTiles.getHeight() - ((this.yTileSize -1) * 4)) / this.yTileSize;
		for (int i=0; i<this.tileView.length; i++)
		{
			for (int j=0; j<this.tileView[i].length; j++)
			{
				this.tileView[i][j] = new Button(this);
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(xUnit, yUnit);
				params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 1);
				params.addRule(RelativeLayout.ALIGN_PARENT_TOP, 1);
				params.setMargins(j*xUnit + j*4, i*yUnit + i*4, 0, 0);
				this.tileView[i][j].setLayoutParams(params);

				this.tileView[i][j].setOnClickListener(this);
				this.layoutPuzzleTiles.addView(this.tileView[i][j]);
			}
		}

		for (int i=0; i<this.tileView.length; i++)
		{
			for (int j=0; j<this.tileView[i].length; j++)
			{
				int value = this.problem.getValue(j, i);
				this.tileView[i][j].setText(String.valueOf(value));
				if (value != this.problem.freeTileIndex())
				{
					this.tileView[i][j].setBackgroundColor(Color.GRAY);
				}
				else
				{
					this.tileView[i][j].setBackgroundColor(Color.BLACK);
				}
			}
		}
	}

	private void removeAllTiles() {
		if (this.tileView == null)
		{
			return;
		}

		for (int i=0; i<this.tileView.length; i++)
		{
			for (int j=0; j<this.tileView[i].length; j++)
			{
				this.layoutPuzzleTiles.removeView(this.tileView[i][j]);
				this.tileView[i][j] = null;
			}
		}

		this.tileView = null;

		this.layoutPuzzleTiles.invalidate();
	}

	@Override
	public void onClick(View v) {
		InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(editHard.getWindowToken(), 0);

		if (v.equals(this.layoutPuzzleTiles))
		{
			return;
		}
		else if (v.equals(this.buttonReset))
		{
			this.removeAllTiles();

			this.xTileSize = Integer.parseInt(this.editHard.getText().toString());
			this.yTileSize = Integer.parseInt(this.editHard.getText().toString());
			this.problem.refreshProblem(this.xTileSize, this.yTileSize);
			this.layoutTiles();

			return;
		}

		int xIndex = -1;
		int yIndex = -1;

		for (int i=0; i<this.tileView.length; i++)
		{
			for (int j=0; j<this.tileView[i].length; j++)
			{
				if (v.equals(this.tileView[i][j]))
				{
					xIndex = j;
					yIndex = i;
				}
			}

			if (xIndex != -1 && yIndex != -1)
			{
				break;
			}
		}

		if (xIndex != -1 && yIndex != -1)
		{
			this.problem.moveTile(xIndex, yIndex);
			this.layoutTiles();

			if (this.problem.isResolved())
			{
				textDesc.setText(R.string.desc_clear);
			}
			else
			{
				textDesc.setText(R.string.desc_gen);
			}
		}

	}

}
