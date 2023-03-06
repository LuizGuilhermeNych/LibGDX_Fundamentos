/**
 * 
 */
package com.gdx.fundamentals2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import views.*;

/**
 * @author luiz.vale
 *
 */
public class Boot extends Game{

	public static Boot INSTANCE;
	private int widthScreen, heightScreen;
	private OrthographicCamera orthographicCamera;

	private LoadingScreen loadingScreen;
	private PreferencesScreen preferencesScreen;
	private MenuScreen menuScreen;
	private MainScreen mainScreen;
	private EndScreen endScreen;
	private GameScreen customScreen;
	private AppPreferences appPreferences;

	public final static int MENU = 0;
	public final static int PREFERENCES = 1;
	public final static int APPLICATION = 2;
	public final static int ENDGAME = 3;
	public final static int CUSTOM = 4;
	
	public Boot() {
		INSTANCE = this;
	}
	
	@Override
	public void create() {
//		this.widthScreen = Gdx.graphics.getWidth();
//		this.heightScreen = Gdx.graphics.getHeight();
//		this.orthographicCamera = new OrthographicCamera();
//		this.orthographicCamera.setToOrtho(false, widthScreen, heightScreen);
		loadingScreen = new LoadingScreen(this);
		appPreferences = new AppPreferences();
		setScreen(loadingScreen);
//		setScreen(new GameScreen(orthographicCamera));
	}

	public AppPreferences getPreferences(){
		return this.appPreferences;
	}
	public void changeScreen(int screen){
		switch (screen){
			case MENU:
				if (menuScreen == null) menuScreen = new MenuScreen(this);
				this.setScreen(menuScreen);
			break;
			case PREFERENCES:
				if(preferencesScreen == null) preferencesScreen = new PreferencesScreen(this);
				this.setScreen(preferencesScreen);
				break;
			case APPLICATION:
				if(mainScreen == null) mainScreen = new MainScreen(this);
				this.setScreen(mainScreen);
			break;
			case ENDGAME:
				if(endScreen == null) endScreen = new EndScreen(this);
				this.setScreen(endScreen);
			break;
		}
	}
}