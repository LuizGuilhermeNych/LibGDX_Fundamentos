/**
 * 
 */
package com.gdx.fundamentals2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * @author luiz.vale
 *
 */
public class Boot extends Game{

	public static Boot INSTANCE;
	private int widthScreen, heightScreen;
	private OrthographicCamera orthographicCamera;
	
	public Boot() {
		INSTANCE = this;
	}
	
	@Override
	public void create() {
		this.widthScreen = Gdx.graphics.getWidth();
		this.heightScreen = Gdx.graphics.getHeight();
		this.orthographicCamera = new OrthographicCamera();
		this.orthographicCamera.setToOrtho(false, widthScreen, heightScreen);
		setScreen(new GameScreen(orthographicCamera));
	}
	
	
}
