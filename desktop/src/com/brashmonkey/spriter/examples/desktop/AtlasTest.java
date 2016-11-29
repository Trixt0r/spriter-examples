package com.brashmonkey.spriter.examples.desktop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.brashmonkey.spriter.Loader;
import com.brashmonkey.spriter.Player;
import com.brashmonkey.spriter.SCMLReader;
import com.brashmonkey.spriter.gdx.AtlasLoader;
import com.brashmonkey.spriter.gdx.Drawer;

public class AtlasTest extends ApplicationAdapter{
	
	SpriteBatch batch;
	Player player;
	Drawer drawer;
	Loader<Sprite> loader;

	public void create(){
		this.batch = new SpriteBatch();
		FileHandle scmlFile = Gdx.files.internal("monster/basic.scml");
		
		long time = System.currentTimeMillis();
		SCMLReader reader = new SCMLReader(scmlFile.read());
		
		this.loader = new AtlasLoader(reader.getData(), Gdx.files.internal("monster/monster.atlas"));
		//this.loader = new LibGdxLoader(reader.getData());
		this.loader.load("monster");
		System.out.println(System.currentTimeMillis()-time);
		
		this.drawer = new Drawer(loader, batch, null);
		this.player = new Player(reader.getData().getEntity(0));
	}
	
	public void render(){
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		this.player.update();
		
		this.batch.begin();
		this.drawer.draw(player);
		this.batch.end();
	}
	
	public void dispose(){
		this.loader.dispose();
		this.batch.dispose();
	}
	
	public static void main(String[] args){
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.width = 1280;
		cfg.height = 720;
		
		new LwjglApplication(new AtlasTest(), cfg);
	}
}
