package com.brashmonkey.spriter.examples.desktop;

import static com.brashmonkey.spriter.examples.desktop.TestBase.*;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.brashmonkey.spriter.Timeline.Key.Bone;
import com.brashmonkey.spriter.Timeline.Key.Object;
import com.brashmonkey.spriter.Player;

public class CollisionTest {
	
	public static void main(String[] args){
		create("monster/basic_002.scml", "Simple collision test");
		drawBoxes = true;
		information = "Click on one object to drag it around. Hold space to drag bones around.";
		infoPosition.y = -250;
		test = new ApplicationAdapter() {
			Player player;
			Vector3 mouse = new Vector3();
			Vector2 offset = new Vector2();
			Bone b = null;
			Iterator<? extends Bone> iterator;
			
			public void create(){
				player = new Player(data.getEntity(0));//createPlayer(data.getEntity(0));
				player.speed = 0;
				
				addInputProcessor(new AnimationSpeedTest.AnimationSpeedChanger(player));
				addInputProcessor(new InputAdapter(){
					public boolean touchDown(int x, int y, int p, int button){
						while(iterator.hasNext()){
							Object object = (Object)iterator.next();
							if(player.collidesFor(object, mouse.x, mouse.y)){
								b = object;
								offset.set(b.position.x - mouse.x, b.position.y - mouse.y);
								return false;
							}
						}
						return false;
					}
					
					public boolean touchUp(int x, int y, int p, int button){
						b = null;
						return false;
					}
				});
			}
			public void render(){
				mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0f);
				camera.unproject(mouse);
				
				player.update();
				
				if(b != null){
					b.position.set(mouse.x+offset.x, mouse.y+offset.y);
					if(Gdx.input.isKeyPressed(Keys.SPACE)) player.setBone(player.getNameFor(b), (Object)b);
					else player.setObject(player.getNameFor(b), (Object)b);
				}
				
				drawer.draw(player);
				
				drawer.drawBoxes(player);
				
				drawer.setColor(1f, 0, 0, 1);
				
				if(Gdx.input.isKeyPressed(Keys.SPACE)) iterator = player.boneIterator();
				else iterator = player.objectIterator();
				
				while(iterator.hasNext())
					if(player.collidesFor(iterator.next(), mouse.x, mouse.y))
						drawer.drawBox(player.prevBBox);
				
				if(Gdx.input.isKeyPressed(Keys.SPACE)) iterator = player.boneIterator();
				else iterator = player.objectIterator();
			}
		};
	}

}
