/**
 * 
 */
package helper;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.gdx.fundamentals2.GameScreen;

/**
 * @author luiz.vale
 *
 */
public class TileMapHelper {
	
	private TiledMap tiledMap;
	private GameScreen gameScreen;
	
	public TileMapHelper(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
	}
	
	public OrthogonalTiledMapRenderer setupMap() {
		tiledMap = new TmxMapLoader().load("debug_map.tmx");
		parseMapObjects(tiledMap.getLayers().get("platforms").getObjects());
		return new OrthogonalTiledMapRenderer(tiledMap);
	}
	
	private void parseMapObjects(MapObjects mapObjects) {
		for(MapObject mapObject : mapObjects) {
			if(mapObject instanceof PolygonMapObject) {
				createStaticBody((PolygonMapObject) mapObject);
			}
		}
	}
	
	private void createStaticBody(PolygonMapObject polygonMapObject) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		Body body = gameScreen.getWorld().createBody(bodyDef);
		
		Shape shape = createPolygonShape(polygonMapObject);
		body.createFixture(shape, 1000);
		shape.dispose();
	}

	private Shape createPolygonShape(PolygonMapObject polygonMapObject) {
		float[] vertices = polygonMapObject.getPolygon().getTransformedVertices();
		Vector2[] worldVertices = new Vector2[vertices.length / 2];
		
		for(int i = 0; i < vertices.length / 2; i++) {
			Vector2 current = new Vector2(vertices[i * 2] / 32.0f, vertices[i * 2 + 1] / 32.0f);
			worldVertices[i] = current;
		}
		
		PolygonShape shape = new PolygonShape();
		shape.set(worldVertices);
		return shape;
	}
}