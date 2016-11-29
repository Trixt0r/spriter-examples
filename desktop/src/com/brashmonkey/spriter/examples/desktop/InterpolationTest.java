package com.brashmonkey.spriter.examples.desktop;

import static com.brashmonkey.spriter.examples.desktop.TestBase.*;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.math.MathUtils;
import com.brashmonkey.spriter.TweenedAnimation;
import com.brashmonkey.spriter.PlayerTweener;

public class InterpolationTest {
	public static void main(String[] args){
		create("GreyGuy/player.scml", "Simple interpolation test");
		infoPosition.y = -250;
		information = "Press left mouse button to change left animation.\n"
						+"Press right mouse button to change right animation.\n"
						+"Scroll to change interpolation weight.";
		test = new ApplicationAdapter() {
			PlayerTweener inter;
			
			public void create(){
				inter = new PlayerTweener(data.getEntity(0));
				inter.updatePlayers = false;
				players.add(inter.getFirstPlayer());
				players.add(inter.getSecondPlayer());
				players.add(inter);
				
				inter.getFirstPlayer().setPosition(-400, 0);
				inter.getSecondPlayer().setPosition(400, 0);
				
				final AnimationSwitchTest.AnimationSwitcher switcher = new AnimationSwitchTest.AnimationSwitcher(inter.getFirstPlayer());
				
				addInputProcessor(new InputAdapter(){		
					public boolean touchDown(int x, int y, int p, int b){
						if(b == Buttons.LEFT) switcher.player = inter.getFirstPlayer();
						else if(b == Buttons.RIGHT) switcher.player = inter.getSecondPlayer();
						return false;
					}
					
					public boolean scrolled(int am){
						float weight = ((TweenedAnimation)inter.getAnimation()).weight;
						weight -= (float)am/10f;
						((TweenedAnimation)inter.getAnimation()).weight = MathUtils.clamp(weight, 0f, 1f);
						return false;
					}
				});
				
				addInputProcessor(switcher);
			}
		};
	}
}
