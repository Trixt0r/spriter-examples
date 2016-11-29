package com.brashmonkey.spriter.examples.desktop;

import static com.brashmonkey.spriter.examples.desktop.TestBase.*;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.MathUtils;
import com.brashmonkey.spriter.PlayerTweener;

public class CompositionTest {
	public static void main(String[] args){
		create("GreyGuy/player.scml", "Simple compisition test");
		infoPosition.y = -250;
		information = "Click to change animations for the legs. Scroll to change the weight for the chest bone.";
		
		test = new ApplicationAdapter() {
			 PlayerTweener tweener;
			 
			 public void create(){
				 tweener = new PlayerTweener(data.getEntity(0));
				 players.add(tweener);
				 tweener.getFirstPlayer().setAnimation("shoot");
				 tweener.getSecondPlayer().setAnimation("walk");
				 tweener.baseBoneName = "chest";
				 tweener.setWeight(0f);
				 
				 tweener.getFirstPlayer().speed = 50;
				 
				 tweener.setBaseAnimation("walk");
				 addInputProcessor(new AnimationSwitchTest.AnimationSwitcher(tweener.getSecondPlayer()));
				 addInputProcessor(new InputAdapter(){
						public boolean scrolled(int am){
							tweener.setWeight(tweener.getWeight() - (float)am/10f);
							tweener.setWeight(MathUtils.clamp(tweener.getWeight(), 0f, 1f));
							return false;
						}
					});
			 }
			 
			 public void render(){
				 tweener.setBaseAnimation(tweener.getSecondPlayer().getAnimation());
			 }
		};
	}

}
