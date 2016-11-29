package com.brashmonkey.spriter.examples.desktop;

import static com.brashmonkey.spriter.examples.desktop.TestBase.*;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.brashmonkey.spriter.CCDResolver;
import com.brashmonkey.spriter.IKObject;
import com.brashmonkey.spriter.IKResolver;
import com.brashmonkey.spriter.Player;
import com.brashmonkey.spriter.Timeline.Key.Bone;

public class InverseKinematicsTest {
	public static void main(String[] args){
		create("GreyGuy/player.scml", "Simple IK test");
		infoPosition.y = -250;
		information = "Press left mouse button to apply inverse kinematics to a bone.";
		
		test = new ApplicationAdapter() {
			
			Player player;
			IKResolver resolver;
			IKObject ikObject;
			Vector3 mouse = new Vector3();
			Bone b = null;
			
			@Override
			public void create() {
				player = new Player(data.getEntity(0));//createPlayer(data.getEntity(0));
				resolver = new CCDResolver(player);
				ikObject = new IKObject(0, 0, 2, 5);
				
				addInputProcessor(new InputAdapter(){

					public boolean touchDown(int x, int y, int p, int button){
						Iterator<Bone> iterator = player.boneIterator();
						while(iterator.hasNext()){
							Bone object = iterator.next();
							if(player.collidesFor(object, mouse.x, mouse.y))
								b = object;
						}
						return false;
					}
					
					public boolean touchUp(int x, int y, int p, int button){
						b = null;
						return false;
					}
				});
			}

			@Override
			public void render() {
				mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0f);
				camera.unproject(mouse);
				if(Gdx.input.isButtonPressed(Buttons.LEFT) && b!= null) resolver.mapIKObject(ikObject, b);
				else resolver.unmapIKObject(ikObject);
				
				player.update();
				
				ikObject.x = mouse.x;
				ikObject.y = mouse.y;
				resolver.resolve();
				
				drawer.draw(player);
				drawer.drawBones(player);
				if(b != null){
					drawer.setColor(0, 1, 0, 1);
					drawer.drawBone(b, player.getObjectInfoFor(b).size);
				}
			}
		};
	}

}
