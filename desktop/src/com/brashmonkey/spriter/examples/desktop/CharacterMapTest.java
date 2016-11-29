package com.brashmonkey.spriter.examples.desktop;

import static com.brashmonkey.spriter.examples.desktop.TestBase.*;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.brashmonkey.spriter.Entity;
import com.brashmonkey.spriter.Entity.CharacterMap;
import com.brashmonkey.spriter.Player;

public class CharacterMapTest {

	public static void main(String[] args) {
		create("GreyGuy/player.scml", "Character map test");
		test = new ApplicationAdapter() {
			public void create(){
				Entity greyGuy = data.getEntity(0);
				final CharacterMap[] charMaps = {	
											greyGuy.getCharacterMap("sad_face"), 
											greyGuy.getCharacterMap("right_hand_open"), 
											greyGuy.getCharacterMap("left_hand_open")
											};
				final Player player = createPlayer(greyGuy);
				player.characterMaps = new CharacterMap[3];
				addInputProcessor(new InputAdapter(){
					int i = 0;
					public boolean touchDown (int screenX, int screenY, int pointer, int button) {
						if(button != Input.Buttons.RIGHT)
							player.characterMaps[i] = charMaps[i]; //set the character map to the one saved in maps
						else
							player.characterMaps[i] = null; //set the char map to null, to remove it
						i = (player.characterMaps.length+(++i))%player.characterMaps.length;
						return false;
					}
				});
			}
			
			public void render(){
				information = "Left click to add character maps. Right click to remove them.";
			}
		};
	}

}
