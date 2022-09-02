package com.brashmonkey.spriter.examples;

import com.brashmonkey.spriter.examples.desktop.TestBase;

public class HtmlTestBase extends TestBase {
	public int width;
	public int height;

	private HtmlTestBase(String path, int width, int height) {
		super(path);
		this.width = width;
		this.height = height;
	}

	public static HtmlTestBase createMultiPlatform(final String path, int width, int height, final String title) {
		return new HtmlTestBase(path, width, height);
	}
}
