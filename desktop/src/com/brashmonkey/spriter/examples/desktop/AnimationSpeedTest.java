package com.brashmonkey.spriter.examples.desktop;

import static com.brashmonkey.spriter.examples.desktop.TestBase.*;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.brashmonkey.spriter.Player;

public class AnimationSpeedTest {
	
	public static void main(String[] args){
		create("monster/basic_002.scml", "Animation speed test");
		test = new ApplicationAdapter() {
			Player player;
			public void create(){
				player = createPlayer(data.getEntity(0));
				
				addInputProcessor(new AnimationSpeedChanger(player));
				addInputProcessor(new AnimationSwitchTest.AnimationSwitcher(player));
			}
			
			public void render(){
				information = "Scroll with the mouse wheel (or press left/right keys) to increase or decrease playback speed. Current speed: "+player.speed;
			}
		};
	}
	
	public static class AnimationSpeedChanger extends InputAdapter{
		
		Player player;
		
		public AnimationSpeedChanger(Player player){
			this.player = player;
		}
		
		@Override
		public boolean scrolled(float amountX, float amountY) {
			float scale = 1f;
			if(Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) scale *= 5f;
			player.speed -= amountY*scale;
			return false;
		}
		
		@Override
		public boolean keyDown(int keycode){
			int amount = 0;
			float scale = 1f;
			if(Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) scale *= 5f;
			if(keycode == Keys.LEFT) amount = 1;
			else if(keycode == Keys.RIGHT) amount = -1;
			player.speed -= amount*scale;
			return false;
		}
	}

}
