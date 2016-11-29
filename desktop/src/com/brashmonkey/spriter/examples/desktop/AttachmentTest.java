package com.brashmonkey.spriter.examples.desktop;

import static com.brashmonkey.spriter.examples.desktop.TestBase.*;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.math.Vector3;
import com.brashmonkey.spriter.Player;
import com.brashmonkey.spriter.Player.Attachment;
import com.brashmonkey.spriter.Timeline.Key.Bone;
import com.brashmonkey.spriter.Spriter;
import com.brashmonkey.spriter.gdx.Drawer;
import com.brashmonkey.spriter.gdx.Loader;

public class AttachmentTest {
	
	public static void main(String[] args){
		create("monster/basic_002.scml", "Attachment test");
		infoPosition.y = -250;
		test = new ApplicationAdapter() {
			String[] files = {"monster/basic_002.scml", "GreyGuy/player.scml"};
			Player monster, player;
			Attachment attach;
			Vector3 mouse = new Vector3();
			Bone b;
			
			public void create(){
				Spriter.setDrawerDependencies(batch, renderer);
				Spriter.init(Loader.class, Drawer.class);
				for(String file: files)
					Spriter.load(Gdx.files.internal(file).read(), file);
				monster = Spriter.newPlayer(files[0], 0);
				player = Spriter.newPlayer(files[1], 0);
				addInputProcessor(new InputAdapter(){
					public boolean touchDown(int x, int y, int p, int b){
						if(b == Buttons.RIGHT){
							if(monster.speed == 0) monster.speed = 17;
							else monster.speed = 0;
						}
						return false;
					}
				});
				createAttachment();
				monster.attachments.add(attach);
			}
			
			private Bone getBoneUnderMouse(Iterator<? extends Bone> iterator){
				while(iterator.hasNext()){
					Bone object = iterator.next();
					if(monster.collidesFor(object, mouse.x, mouse.y))
						return object;
				}
				return null;
			}
			
			public void render(){
				mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0f);
				camera.unproject(mouse);
				//Check if the mouse position collides with an object or bone
				b = getBoneUnderMouse(monster.boneIterator());
				if(b == null) b= getBoneUnderMouse(monster.objectIterator());
				//If left button has clicked try to attach the player to the clicked object or reset the position.
				if(Gdx.input.isButtonPressed(Buttons.LEFT)){
					if(monster.attachments.contains(attach)) monster.attachments.remove(attach);
					if(b != null){
						attach.setParent(b);
						monster.attachments.add(attach);
					} else{
						player.setPosition(0, 0);
						player.setScale(1f);
						player.setAngle(0f);
					}
				}
				
				information = "Click on a bone or object to attack the grey guy to a part of the monster. Right click to pause the animation of the monster. "
						+ "\n Currently attached to: "+monster.getNameFor(attach.getParent());
				Spriter.updateAndDraw();
				if(b != null){
					Spriter.drawer().setColor(1, 0, 0, 1);
					Spriter.drawer().drawBox(monster.getBox(b));
				}
			}
			
			private void createAttachment(){
				attach = new Attachment(monster.getBone("leftHand")) {
					
					@Override
					protected void setScale(float xscale, float yscale) {
						player.setScale(Math.max(xscale, yscale));
					}
					
					@Override
					protected void setPosition(float x, float y) {
						player.setPosition(x, y);
					}
					
					@Override
					protected void setAngle(float angle) {
						player.setAngle(angle);
					}
				};
			}
		};
	}

}
