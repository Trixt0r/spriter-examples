package com.brashmonkey.spriter.examples.desktop;

import com.badlogic.gdx.utils.Align;
import org.lwjgl.input.Keyboard;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.brashmonkey.spriter.Data;
import com.brashmonkey.spriter.Entity;
import com.brashmonkey.spriter.Player;
import com.brashmonkey.spriter.SCMLReader;
import com.brashmonkey.spriter.gdx.Loader;
import com.brashmonkey.spriter.gdx.Drawer;

public class TestBase implements ApplicationListener{
	
	private String path;
	private FileHandle scmlHandle;
	private SCMLReader reader;
	
	public static Data data;
	public static final Array<Player> players = new Array<Player>();
	public static Loader loader;
	public static Drawer drawer;
	public static boolean drawBoxes = false;
	public static boolean drawBones = false;
	
	public static ShapeRenderer renderer;
	public static SpriteBatch batch;
	public static OrthographicCamera camera;
	public static ApplicationListener test;
	public static BitmapFont font;
	public static String information = "";
	public static Vector2 infoPosition = new Vector2(0, 0);
	private static final InputMultiplexer input = new InputMultiplexer();
	
	public static Player createPlayer(Entity entity){
		Player player = new Player(entity);
		players.add(player);
		return player;
	}
	
	public TestBase(String path, LwjglApplicationConfiguration cfg){
		this.path = path;
		new LwjglApplication(this, cfg);
		Keyboard.enableRepeatEvents(true);
	}
	
	public static void addInputProcessor(InputProcessor processor){
		input.addProcessor(processor);
	}

	public void create() {
		renderer = new ShapeRenderer();
		batch = new SpriteBatch();
		font = new BitmapFont();
		camera = new OrthographicCamera();
		
		if(path != null){
			scmlHandle = Gdx.files.internal(path);
			reader = new SCMLReader(scmlHandle.read());
			data = reader.getData();
			
			loader = new Loader(data);
			loader.load(scmlHandle.file());
			
			drawer = new Drawer(loader, batch, renderer);
		}
		
		Gdx.input.setInputProcessor(input);
		
		if(test != null) test.create();
	}

	public void resize(int width, int height) {
		camera.setToOrtho(false, width, height);
		camera.position.set(0, 0, 0);
		if(test != null) test.resize(width, height);
	}

	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		
		renderer.setProjectionMatrix(camera.combined);
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		renderer.begin(ShapeType.Line);
		
		for(Player player: players)
			player.update();
		for(Player player: players)
			drawer.draw(player);
			
		if(drawBones)
			for(Player player: players)
				drawer.drawBones(player);
		if(drawBoxes)
			for(Player player: players)
				drawer.drawBoxes(player);
		
		if(test != null) test.render();

		if(information != null)
			font.draw(batch, information, infoPosition.x, infoPosition.y, 0, Align.center, false);
		batch.end();
		renderer.end();
	}

	public void pause() {
		if(test != null) test.pause();
	}

	public void resume() {
		if(test != null) test.resume();
	}

	public void dispose() {
		renderer.dispose();
		batch.dispose();
		if(loader != null) loader.dispose();
		font.dispose();
		if(test != null) test.dispose();
	}
	
	public static void create(String path, int width, int height, String title){
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.width = width;
		cfg.height = height;
		cfg.title = "Test: "+title;
		new TestBase(path, cfg);
	}
	
	public static void create(String path, String title){
		create(path, 1280, 720, title);
	}
	
	public static void create(String path, int width, int height){
		StackTraceElement[] stack = Thread.currentThread ().getStackTrace ();
		StackTraceElement main = stack[stack.length - 1];
		String title = main.getClassName();
		try {
			title = Class.forName(main.getClassName()).getSimpleName();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		create(path, width, height, title);
	}
	
	public static void create(String path){
		create(path, 1280, 720);
	}
	
	public static void main(String[] args){
		create("monster/basic_002.scml");
		TestBase.test = new ApplicationAdapter() {
			public void create(){
				players.add(new Player(data.getEntity(0)));
			}
		};
	}

}
