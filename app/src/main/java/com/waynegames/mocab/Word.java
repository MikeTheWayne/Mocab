package com.waynegames.mocab;

import java.util.Random;

public class Word {

	private String english;
	private String translation;

	private int engX, engY;
	private int trnX, trnY;

	private boolean engSelect, trnSelect;
	private boolean show;

	public Word(String english, String translation) {
		this.english = english;
		this.translation = translation;

		this.engSelect = false;
		this.trnSelect = false;
		this.show = true;

		this.generatePositions();
	}

	/**
	 * Generates a random location for both words
	 */
	private void generatePositions() {

		Random random = new Random();

		engX = random.nextInt(360);
		trnX = random.nextInt(360);
		engY = random.nextInt(160) + 10;
		trnY = random.nextInt(160) + 10;

	}

	public String getEnglish() {
		return english;
	}

	public String getTranslation() {
		return translation;
	}

	public int getEngX() {
		return engX;
	}

	public int getEngY() {
		return engY;
	}

	public int getTrnX() {
		return trnX;
	}

	public int getTrnY() {
		return trnY;
	}

	public int getEngX(int width) {
		return (int) (4 * width * (engX / 360f));
	}

	public int getEngY(int height) {
		return (int) (2 * height * (engY / 180f));
	}

	public int getTrnX(int width) {
		return (int) (4 * width * (trnX / 360f));
	}

	public int getTrnY(int height) {
		return (int) (2 * height * (trnY / 180f));
	}

	public boolean isEngSelect() {
		return engSelect;
	}

	public boolean isTrnSelect() {
		return trnSelect;
	}

	public void setEngSelect(boolean engSelect) {
		this.engSelect = engSelect;
	}

	public void setTrnSelect(boolean trnSelect) {
		this.trnSelect = trnSelect;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}
}
