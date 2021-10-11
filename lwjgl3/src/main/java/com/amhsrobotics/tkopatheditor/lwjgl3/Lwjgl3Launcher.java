package com.amhsrobotics.tkopatheditor.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.amhsrobotics.tkopatheditor.Main;

public class Lwjgl3Launcher {
	public static void main(String[] args) {
		createApplication();
	}

	private static Lwjgl3Application createApplication() {
		return new Lwjgl3Application(new Main(), getDefaultConfiguration());
	}

	private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
		Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
		configuration.setTitle("TKO Path Editor");
		configuration.setWindowedMode(1366, 768);
		configuration.setWindowSizeLimits(1366, 768, 9999, 9999);
		configuration.setBackBufferConfig(8, 8, 8, 8, 16, 0, 3);
		configuration.setResizable(true);
		configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
		return configuration;
	}
}