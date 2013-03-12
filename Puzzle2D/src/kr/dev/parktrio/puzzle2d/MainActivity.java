package kr.dev.parktrio.puzzle2d;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Intent puzzleIntent = new Intent(this, PuzzleActivity.class);
		startActivity(puzzleIntent);
	}

}
