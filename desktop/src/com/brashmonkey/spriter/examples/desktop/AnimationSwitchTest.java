package com.brashmonkey.spriter.examples.desktop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Buttons;
import com.brashmonkey.spriter.Animation;
import com.brashmonkey.spriter.Entity;
import com.brashmonkey.spriter.Player;

import static com.brashmonkey.spriter.examples.desktop.TestBase.*;

public class AnimationSwitchTest {
	public static void main(String[] args){
		create("monster/basic_002.scml", "Animation switching");
		
		test = new ApplicationAdapter() {
			Player player;
			public void create(){
				player = createPlayer(data.getEntity(0));
				addInputProcessor(new AnimationSwitcher(player));
			}
			
			public void render(){
				information = "Press left/right mouse button to switch animations. Current animation: "+player.getAnimation().name;
			}
		};
	}
	
	public static class AnimationSwitcher extends InputAdapter{
		
		public Player player;
		
		public AnimationSwitcher(Player player){
			this.player = player;
		}
		
		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			int dir = 0;
			if(button == Buttons.LEFT) dir = 1;
			if(button == Buttons.RIGHT) dir = -1;
			
			Entity entity = player.getEntity();
			Animation currentAnimation = player.getAnimation();
			
			int nextAnimationIndex = (entity.animations()+currentAnimation.id+dir)%entity.animations();
			Animation nextAnimation = entity.getAnimation(nextAnimationIndex);
			player.setAnimation(nextAnimation);
			
			return false;
		}
	}
}
