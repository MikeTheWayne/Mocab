package com.waynegames.mocab;

import java.util.Random;

public class Word {

	private String english;
	private String translation;

	private int engX, engY;
	private int trnX, trnY;

	private boolean engSelect, trnSelect;
	private boolean show;

	public Word(String english, String translation, int x1, int y1, int x2, int y2) {
		this.english = english;
		this.translation = translation;

		this.engSelect = false;
		this.trnSelect = false;
		this.show = true;

		this.engX = x1;
		this.engY = y1;
		this.trnX = x2;
		this.trnY = y2;
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
		return (int) (6 * width * (engX / 360f));
	}

	public int getEngY(int height) {
		return (int) (2 * height * (engY / 180f));
	}

	public int getTrnX(int width) {
		return (int) (6 * width * (trnX / 360f));
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
