package com.engteam14.yorkpirates.headless;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.engteam14.yorkpirates.YorkPirates;

/** Launches the headless/testing application. */
public class HeadlessLauncher {

	public static void main(String[] args) {
		createApplication();
	}

	/** Creates base lwjgl3 application */
	private static HeadlessApplication createApplication() {
		return new HeadlessApplication(new YorkPirates(), getDefaultConfiguration());
	}

	private static HeadlessApplicationConfiguration getDefaultConfiguration() {
		HeadlessApplicationConfiguration configuration = new HeadlessApplicationConfiguration();
		return configuration;
	}
}
