package com.brashmonkey.spriter.examples.desktop;

import static com.brashmonkey.spriter.examples.desktop.TestBase.*;

import java.util.ArrayDeque;
import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.brashmonkey.spriter.Player;
import com.brashmonkey.spriter.Rectangle;
import com.brashmonkey.spriter.Timeline.Key.Object;

public class CullingTest {
	
	public static void main(String[] args){
		create("GreyGuy/player.scml", "Simple culling test");
		infoPosition.y = -250;
		information = "Drag the player around to see which parts will be drawn inside the rectangle. Scroll to resize the rectangle.";
		test = new ApplicationAdapter() {
			Player player;
			Rectangle rect = new Rectangle(-400, 300, 400, -300);
			ArrayDeque<Object> drawingQueue = new ArrayDeque<Object>();
			Vector3 mouse = new Vector3();
			
			public void create(){
				player = new Player(data.getEntity(0));
				addInputProcessor(new InputAdapter(){
					public boolean scrolled(float amountX, float amountY) {
						amountY *= 5;
						rect.left += amountY;
						rect.right -= amountY;
						rect.bottom += amountY;
						rect.top -= amountY;
						rect.calculateSize();
						return false;
					}
				});
			}
			
			public void render(){
				if(Gdx.input.isButtonPressed(Buttons.LEFT)){
					mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0f);
					camera.unproject(mouse);
					player.setPosition(mouse.x, mouse.y);
				}
				player.update();
				Iterator<Object> it = player.objectIterator();
				while(it.hasNext()){
					Object obj = it.next();
					if(player.getBox(obj).isInside(rect)) //If the box is inside the rectangle, add it to the queue
						drawingQueue.addLast(obj);
				}
				
				drawer.draw(this.drawingQueue.iterator(), player.characterMaps);//Draw only objects in the queue
				//drawer.drawBoxes(player);
				drawer.drawObjectBoxes(player, this.drawingQueue.iterator());
				
				drawer.rectangle(rect.left, rect.bottom, rect.size.width, rect.size.height);
				drawingQueue.clear();
			}
		};
	}
}
