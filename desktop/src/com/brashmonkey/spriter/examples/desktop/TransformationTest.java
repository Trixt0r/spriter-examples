package com.brashmonkey.spriter.examples.desktop;

import static com.brashmonkey.spriter.examples.desktop.TestBase.*;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.brashmonkey.spriter.Player;

public class TransformationTest {
	
	public static void main(String[] args){
		create("monster/basic_002.scml", "Transformation test");
		test = new ApplicationAdapter(){
			Player player;
			
			public void create(){
				player = createPlayer(data.getEntity(0));
				infoPosition.y = -200;
				addInputProcessor(new InputAdapter(){
					public boolean touchDragged(int x, int y, int pointer){
						if(Gdx.input.isButtonPressed(Buttons.LEFT)){
							float baseScale = .01f;
							float scale = 1+new Vector2(Gdx.input.getDeltaX(), Gdx.input.getDeltaY()).len()*baseScale;
							if(Gdx.input.isKeyPressed(Keys.CONTROL_LEFT))
								player.scale(scale);//Scale the player
							else if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
								player.scale(1f/(scale));//Shrink the player
							else
								player.translatePosition(Gdx.input.getDeltaX(), -Gdx.input.getDeltaY());//Set the position of the player
						}else if(Gdx.input.isButtonPressed(Buttons.RIGHT)){
							if(Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)){
								float dx = -Gdx.input.getDeltaX();
								float dy = Gdx.input.getDeltaY();
								Vector2 v = new Vector2(dx, dy);
								v.rotate(-player.getAngle());
								//Change the center of the player
								player.translatePivot(v.x, v.y);
							}
							else{
								Vector3 vec = new Vector3(x,y,0f);
								camera.unproject(vec);
								float angle = new Vector2(vec.x-player.getX(), vec.y-player.getY()).angle();
								//Set the angle of the player
								player.setAngle(angle);
							}
						}
						
						return false;
					}
					
					public boolean keyDown(int keycode){
						if(keycode == Keys.ENTER){
							//Reset all values
							player.setPosition(0,0);
							player.setScale(1f);
							player.setAngle(0f);
							player.setPivot(0, 0);
							if(player.flippedX() == -1) player.flipX();
							if(player.flippedY() == -1) player.flipY();
						}
						//Flip the player
						if(keycode == Keys.LEFT && player.flippedX() == 1)
							player.flipX();
						else if(keycode == Keys.RIGHT && player.flippedX() == -1)
							player.flipX();
						if(keycode == Keys.UP && player.flippedY() == -1)
							player.flipY();
						else if(keycode == Keys.DOWN && player.flippedY() == 1)
							player.flipY();
						return false;
					}
				});
			}
			
			public void render(){
				information = "Drag the mouse to move the entity around. Current position: "+player.getX()+", "+player.getY()+"\n"
							+ "Drag the mouse with left click around and press ctrl/shift to scale/shrink the entity. Current scale: "+player.getScale()+"\n"
							+ "Press right mouse button to change the angle. Current angle: "+player.getAngle()+"\n"
							+ "Press ctrl and right mouse button to change the pivot point of the entity. Current pivot position: "+player.getPivotX()+", "+player.getPivotY()+"\n"
							+ "Press left/right up/down to flip the entity. Press enter to reset all values.";
				renderer.setColor(1, 0, 0, 1);
				//renderer.begin(ShapeType.Line);
				renderer.circle(player.getX(), player.getY(), 5f);
				//renderer.end();
			}
		};
	}

}
