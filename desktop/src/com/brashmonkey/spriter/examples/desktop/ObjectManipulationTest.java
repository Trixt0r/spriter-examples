package com.brashmonkey.spriter.examples.desktop;

import static com.brashmonkey.spriter.examples.desktop.TestBase.*;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.brashmonkey.spriter.Player;
import com.brashmonkey.spriter.Timeline.Key.Bone;
import com.brashmonkey.spriter.Timeline.Key.Object;

public class ObjectManipulationTest {
	
	public static void main(String[] args){
		create("monster/basic_002.scml", "Object manipulation");
		//drawBones = true;
		information = "Press left mouse button to move the torso bone.\n"
						+"Press right mouse button to change the angle of rightUpperArm bone.\n"
						+ "Press middle mouse button to rotate the head sprite.";
		infoPosition.y = -200;
		test = new ApplicationAdapter() {
			Player player;
			
			public void create(){
				this.player = new Player(data.getEntity(0));//createPlayer(data.getEntity(0));
				//players.removeIndex(0);
			}
			
			Vector3 v = new Vector3();
			Vector2 mouse = new Vector2();
			public void render(){
				
				v.set(Gdx.input.getX(), Gdx.input.getY(), 0f);
				camera.unproject(v);
				
				player.update();//Update the player before manipulating objects
				
				if(Gdx.input.isButtonPressed(Buttons.LEFT)){
					player.setBone("torso", v.x, v.y);//Set the torso to the mouse position
				}
				else if(Gdx.input.isButtonPressed(Buttons.RIGHT)){
					String boneName = "rightUpperArm";
					Bone bone = player.getBone(boneName);
					mouse.set(bone.position.x - v.x, bone.position.y - v.y);
					bone.angle = mouse.angle();//We set the angle absolute, previous angle is not taken into consideration
					player.setBone(boneName, bone);
				}
				else if(Gdx.input.isButtonPressed(Buttons.MIDDLE)){
					String objectName = "object_001";
					Object object = player.getObject(objectName);
					mouse.set(object.position.x - v.x, object.position.y - v.y);
					object.angle += mouse.angle()+90;//We set the angle relative the current animation angle
					player.setObject(objectName, object);
				}
				
				drawer.draw(player);
			
				drawer.drawBones(player);
			}
		};
	}

}
