package com.brashmonkey.spriter.examples.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.lwjgl.input.Keyboard;

public class DesktopTestBase extends TestBase {

	private DesktopTestBase(String path, LwjglApplicationConfiguration cfg){
		super(path);
		new LwjglApplication(this, cfg);
		Keyboard.enableRepeatEvents(true);
	}

	public static DesktopTestBase createMultiPlatform(String path, int width, int height, String title) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.width = width;
		cfg.height = height;
		cfg.title = "Test: "+title;
		return new DesktopTestBase(path, cfg);
	}
}
