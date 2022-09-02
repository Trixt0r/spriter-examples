package com.brashmonkey.spriter.examples.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.brashmonkey.spriter.examples.desktop.AnimationSwitchTest;
import com.brashmonkey.spriter.examples.desktop.TestBase;

public class HtmlLauncher extends GwtApplication {

	@Override
	public GwtApplicationConfiguration getConfig() {
		return new GwtApplicationConfiguration(true);
	}

	@Override
	public ApplicationListener createApplicationListener() {
		TestBase.launchesIndividually = false;
		AnimationSwitchTest.main(null); // Change here the test you want to run
		return TestBase.currentTestBase;
	}
}
