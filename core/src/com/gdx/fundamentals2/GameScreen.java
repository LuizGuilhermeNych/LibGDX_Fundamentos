/**
 * 
 */
package com.gdx.fundamentals2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import helper.TileMapHelper;

/**
 * @author luiz.vale
 *
 */
public class GameScreen extends ScreenAdapter{

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private World world;
	private Box2DDebugRenderer box2DebugRenderer;
	
	private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
	private TileMapHelper tileMapHelper;
	
	public GameScreen(OrthographicCamera camera) {
		this.camera = camera;
		this.batch = new SpriteBatch();
		this.world = new World(new Vector2(0, 0), false);
		this.box2DebugRenderer = new Box2DDebugRenderer();
		
		this.tileMapHelper = new TileMapHelper(this);
		this.orthogonalTiledMapRenderer = tileMapHelper.setupMap();
	}
	
	public void update() {
		world.step(1 / 60f, 6, 2);
		cameraUpdate();
		
		batch.setProjectionMatrix(camera.combined);
		orthogonalTiledMapRenderer.setView(camera);
		
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}
	}
	
	private void cameraUpdate() {
		camera.position.set(new Vector3(0, 0, 0));
		camera.update();
	}

	@Override
	public void render(float delta) {
		this.update();
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		orthogonalTiledMapRenderer.render();
		
		batch.begin();
		
		batch.end();
		box2DebugRenderer.render(world, camera.combined.scl(32.0f));
	}

	/**
	 * @return the world
	 */
	public World getWorld() {
		return world;
	}	
	
}