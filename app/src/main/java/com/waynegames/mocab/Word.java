package com.waynegames.mocab;

import java.util.Random;

public class Word {

	private String english;
	private String translation;

	private int engX, engY;
	private int trnX, trnY;

	public Word(String english, String translation) {
		this.english = english;
		this.translation = translation;

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

}
