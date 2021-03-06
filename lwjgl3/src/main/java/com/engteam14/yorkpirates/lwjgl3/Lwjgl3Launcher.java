package com.engteam14.yorkpirates.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.engteam14.yorkpirates.YorkPirates;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
	/** Constructor class, creates the main application */
	public static void main(String[] args) {
		createApplication();
	}

	/** Creates base lwjgl3 application */
	private static Lwjgl3Application createApplication() {
		return new Lwjgl3Application(new YorkPirates(), getDefaultConfiguration());
	}

	/** Creates base lwjgl3 application configuration */
	private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
		Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
		configuration.setTitle( "York Pirates!");
		configuration.useVsync(true);
		//// Limits FPS to the refresh rate of the currently active monitor.
		configuration.setForegroundFPS(60);
		//// If you remove the above line and set Vsync to false, you can get unlimited FPS, which can be
		//// useful for testing performance, but can also be very stressful to some hardware.
		//// You may also need to configure GPU drivers to fully disable Vsync; this can cause screen tearing.

		configuration.setMaximized(true);
		configuration.setResizable(true);

		configuration.setWindowIcon("icon_128.png", "icon_64.png", "icon_32.png", "icon_16.png");

		return configuration;
	}
}
