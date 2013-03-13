package kr.dev.parktrio.puzzle2d;

import java.util.Random;

public class PuzzleProblem {
	private int xCount;
	private int yCount;

	private int[] problemState;

	public PuzzleProblem(int size) {
		this(size, size);
	}

	public PuzzleProblem(int xSize, int ySize) {
		this.xCount = xSize;
		this.yCount = ySize;

		this.generateProblem();
	}

	private void generateProblem() {
		this.problemState = new int[this.xCount * this.yCount];

		Random rand = new Random();
		for (int i=0; i < this.problemState.length; i++)
		{
			this.problemState[i] = i;
		}

		for (int i=0; i < this.problemState.length * 2; i++)
		{
			int direction = rand.nextInt(4);
			int xIndex = rand.nextInt(this.xCount);
			int yIndex = rand.nextInt(this.yCount);

			switch (direction) {
			case 0:	// up
				if (!this.moveUp(xIndex, yIndex))
				{
					this.moveDown(xIndex, yIndex);
				}
				break;
			case 1:	// down
				if (!this.moveDown(xIndex, yIndex))
				{
					this.moveUp(xIndex, yIndex);
				}
				break;
			case 2:	// left
				if (!this.moveLeft(xIndex, yIndex))
				{
					this.moveRight(xIndex, yIndex);
				}
				break;
			case 3:	// right
				if (!this.moveRight(xIndex, yIndex))
				{
					this.moveLeft(xIndex, yIndex);
				}
				break;
			default:
				break;
			}
		}
	}

	private boolean swapSell(int fromIndex, int toIndex) {
		if (fromIndex < 0 || fromIndex >= this.problemState.length
				|| toIndex < 0 || toIndex >= this.problemState.length)
		{
			return false;
		}

		int temp = this.problemState[fromIndex];
		this.problemState[fromIndex] = this.problemState[toIndex];
		this.problemState[toIndex] = temp;

		return true;
	}

	private int xyToLinearIndex(int xIndex, int yIndex) {
		if (xIndex < 0 || xIndex >= this.xCount
				|| yIndex < 0 || yIndex >= this.yCount)
		{
			return -1;
		}

		return (yIndex * this.xCount) + xIndex;
	}

	public boolean moveTile(int xIndex, int yIndex) {
		if (xIndex < 0 || xIndex >= this.xCount
				|| yIndex < 0 || yIndex >= this.yCount)
		{
			return false;
		}

		if (getValue(xIndex - 1, yIndex) == this.problemState.length-1)
		{
			return moveLeft(xIndex, yIndex);
		}

		if (getValue(xIndex + 1, yIndex) == this.problemState.length-1)
		{
			return moveRight(xIndex, yIndex);
		}

		if (getValue(xIndex, yIndex - 1) == this.problemState.length-1)
		{
			return moveUp(xIndex, yIndex);
		}

		if (getValue(xIndex, yIndex + 1) == this.problemState.length-1)
		{
			return moveDown(xIndex, yIndex);
		}

		return false;
	}

	private boolean moveUp(int xIndex, int yIndex) {
		if (xIndex < 0 || xIndex >= this.xCount
				|| yIndex < 0 || yIndex >= this.yCount)
		{
			return false;
		}

		int fromIndex = this.xyToLinearIndex(xIndex, yIndex);
		int toIndex = this.xyToLinearIndex(xIndex, yIndex - 1);

		if (fromIndex == -1
				|| toIndex == -1)
		{
			return false;
		}
		else
		{
			return swapSell(fromIndex, toIndex);
		}
	}

	private boolean moveDown(int xIndex, int yIndex) {
		if (xIndex < 0 || xIndex >= this.xCount
				|| yIndex < 0 || yIndex >= this.yCount)
		{
			return false;
		}

		int fromIndex = this.xyToLinearIndex(xIndex, yIndex);
		int toIndex = this.xyToLinearIndex(xIndex, yIndex + 1);

		if (fromIndex == -1
				|| toIndex == -1)
		{
			return false;
		}
		else
		{
			return swapSell(fromIndex, toIndex);
		}
	}

	private boolean moveLeft(int xIndex, int yIndex) {
		if (xIndex < 0 || xIndex >= this.xCount
				|| yIndex < 0 || yIndex >= this.yCount)
		{
			return false;
		}

		int fromIndex = this.xyToLinearIndex(xIndex, yIndex);
		int toIndex = this.xyToLinearIndex(xIndex - 1, yIndex);

		if (fromIndex == -1
				|| toIndex == -1)
		{
			return false;
		}
		else
		{
			return swapSell(fromIndex, toIndex);
		}
	}

	private boolean moveRight(int xIndex, int yIndex) {
		if (xIndex < 0 || xIndex >= this.xCount
				|| yIndex < 0 || yIndex >= this.yCount)
		{
			return false;
		}

		if (!this.isMovable(xIndex, yIndex))
		{
			return false;
		}

		int fromIndex = this.xyToLinearIndex(xIndex, yIndex);
		int toIndex = this.xyToLinearIndex(xIndex + 1, yIndex);

		if (fromIndex == -1
				|| toIndex == -1)
		{
			return false;
		}
		else
		{
			return swapSell(fromIndex, toIndex);
		}
	}

	public int getValue(int xIndex, int yIndex) {
		if (xIndex < 0 || xIndex >= this.xCount
				|| yIndex < 0 || yIndex >= this.yCount)
		{
			return -1;
		}

		return this.problemState[this.xyToLinearIndex(xIndex, yIndex)];
	}

	public boolean isMovable(int xIndex, int yIndex) {
		if (xIndex < 0 || xIndex >= this.xCount
				|| yIndex < 0 || yIndex >= this.yCount)
		{
			return false;
		}

		if (getValue(xIndex - 1, yIndex) == this.problemState.length-1)
		{
			return true;
		}

		if (getValue(xIndex + 1, yIndex) == this.problemState.length-1)
		{
			return true;
		}

		if (getValue(xIndex, yIndex - 1) == this.problemState.length-1)
		{
			return true;
		}

		if (getValue(xIndex, yIndex + 1) == this.problemState.length-1)
		{
			return true;
		}

		return false;
	}

	public boolean isResolved() {
		for (int i=0; i<this.problemState.length; i++)
		{
			if (i != this.problemState[i])
			{
				return false;
			}
		}

		return true;
	}

	public void refreshProblem(int xSize, int ySize) {
		this.xCount = xSize;
		this.yCount = ySize;

		this.refreshProblem();
	}

	public void refreshProblem() {
		this.generateProblem();
	}

	public int getXCount() {
		return xCount;
	}

	public int getYCount() {
		return yCount;
	}

	public int freeTileIndex() {
		return this.problemState.length - 1;
	}


}
