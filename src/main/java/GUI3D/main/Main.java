package GUI3D.main;

import GUI3D.GUI.GUIRenderer;
import GUI3D.GUI.GUITexture;
import GUI3D.engine.graphics.Loader;
import GUI3D.engine.graphics.MasterRenderer;
import GUI3D.engine.graphics.models.RawModel;
import GUI3D.engine.graphics.models.TexturedModel;
import GUI3D.engine.graphics.textures.ModelTexture;
import GUI3D.engine.graphics.textures.TerrainTexture;
import GUI3D.engine.graphics.textures.TerrainTexturePack;
import GUI3D.engine.graphics.textures.objConverter.ModelData;
import GUI3D.engine.graphics.textures.objConverter.OBJFileLoader;
import GUI3D.engine.io.Input;
import GUI3D.engine.io.Window;
import GUI3D.entities.Camera;
import GUI3D.entities.Entity;
import GUI3D.entities.Light;
import GUI3D.entities.Player;
import org.lwjgl.glfw.GLFW;
import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;
import org.lwjglx.util.vector.Vector4f;
import GUI3D.terrain.Terrain;

import java.util.ArrayList;
import java.util.List;

public class Main implements Runnable {

	public Thread game;
	public Window window;
	public static final int WIDTH = 1600, HEIGHT = 900;
	public int i = 0;

	public Loader loader = new Loader();

	public RawModel chessModel;
	public RawModel pawnModel;
	public RawModel rookModel;
	public RawModel horseModel;
	public RawModel bishopModel;
	public RawModel queenModel;
	public RawModel kingModel;

	public ModelData modelDataChess;
	public ModelData modelDataPawn;
	public ModelData modelDataRook;
	public ModelData modelDataHorse;
	public ModelData modelDataBishop;
	public ModelData modelDataQueen;
	public ModelData modelDataKing;

	public ModelTexture textureChess;
	public ModelTexture texturePawnBlack;
	public ModelTexture texturePawnWhite;
	public ModelTexture textureRookBlack;
	public ModelTexture textureRookWhite;
	public ModelTexture textureHorseBlack;
	public ModelTexture textureHorseWhite;
	public ModelTexture textureBishopBlack;
	public ModelTexture textureBishopWhite;
	public ModelTexture textureQueenBlack;
	public ModelTexture textureQueenWhite;
	public ModelTexture textureKingBlack;
	public ModelTexture textureKingWhite;

	public TexturedModel texturedModelChess;
	public TexturedModel texturedModelPawnBlack;
	public TexturedModel texturedModelPawnWhite;
	public TexturedModel texturedModelRookBlack;
	public TexturedModel texturedModelRookWhite;
	public TexturedModel texturedModelHorseBlack;
	public TexturedModel texturedModelHorseWhite;
	public TexturedModel texturedModelBishopBlack;
	public TexturedModel texturedModelBishopWhite;
	public TexturedModel texturedModelQueenBlack;
	public TexturedModel texturedModelQueenWhite;
	public TexturedModel texturedModelKingBlack;
	public TexturedModel texturedModelKingWhite;

	public MasterRenderer renderer;

	public Player player;
	public Player pawnb1;
	public Player pawnb2;
	public Player pawnb3;
	public Player pawnb4;
	public Player pawnb5;
	public Player pawnb6;
	public Player pawnb7;
	public Player pawnb8;
	public Player pawnw1;
	public Player pawnw2;
	public Player pawnw3;
	public Player pawnw4;
	public Player pawnw5;
	public Player pawnw6;
	public Player pawnw7;
	public Player pawnw8;
	public Player rookw1;
	public Player rookw2;
	public Player horsew1;
	public Player horsew2;
	public Player bishopw1;
	public Player bishopw2;
	public Player queenw;
	public Player kingw;

	public Player rookb1;
	public Player rookb2;
	public Player horseb1;
	public Player horseb2;
	public Player bishopb1;
	public Player bishopb2;
	public Player queenb;
	public Player kingb;

	public Camera camera;

	public List<Light> lights;
	public List<Entity> entities;
	public List<Player> players;
	public List<Terrain> terrains;

	public static Terrain terrain;
	public static Terrain terrain1;
	public static Terrain terrain2;
	public static Terrain terrain3;
	public static Terrain terrain4;
	public static Terrain terrain5;
	public static Terrain terrain6;
	public static Terrain terrain7;
	public static Terrain terrain8;
	public static Terrain terrain9;
	public static Terrain terrain10;
	public static Terrain terrain11;
	public static Terrain terrain12;
	public static Terrain terrain13;
	public static Terrain terrain14;
	public static Terrain terrain15;
	public static Terrain terrain16;
	public static Terrain terrain17;
	public static Terrain terrain18;
	public static Terrain terrain19;
	public static Terrain terrain20;
	public static Terrain terrain21;
	public static Terrain terrain22;


	public TerrainTexture backgroundTexture;
	public TerrainTexture rTexture;
	public TerrainTexture gTexture;
	public TerrainTexture bTexture;
	public TerrainTexturePack texturePack;
	public TerrainTexture blendMap;


	public List<GUITexture> guis;
	public GUITexture gui;
	public GUIRenderer guiRenderer;

	private static final float RED = 0.5f;
	private static final float GREEN = 0.5f;
	private static final float BLUE = 0.5f;


	private enum STATE{
		MENU,
		GAME
	};

	private STATE State = STATE.MENU;


	public void start() {
		game = new Thread(this, "Dice Chess");
		game.start();
	}

	/**
	 * create window with terrain
	 * @throws Exception if OBJFileLoader couldn't load model or Loader couldn't load texture
	 */
	public void init() throws Exception {
		window = new Window(WIDTH, HEIGHT, "Dice Chess");
		window.setBackgroundColor(RED,GREEN,BLUE);
		window.create();
		renderer = new MasterRenderer(loader);


		// load objects to RawModels
		modelDataChess = OBJFileLoader.loadOBJ("chessmodel");
		modelDataPawn = OBJFileLoader.loadOBJ("pawn");
		//modelDataRook = OBJFileLoader.loadOBJ("rook");
		modelDataHorse = OBJFileLoader.loadOBJ("horse");
		modelDataBishop = OBJFileLoader.loadOBJ("bishop");
		modelDataQueen = OBJFileLoader.loadOBJ("queen");
		modelDataKing = OBJFileLoader.loadOBJ("king");

		chessModel = loader.loadToVAO(modelDataChess.getVertices(),modelDataChess.getTextureCoords(),modelDataChess.getNormals(),modelDataChess.getIndices());
		pawnModel = loader.loadToVAO(modelDataPawn.getVertices(), modelDataPawn.getTextureCoords(),modelDataPawn.getNormals(),modelDataPawn.getIndices());
		//rookModel = loader.loadToVAO(modelDataRook.getVertices(),modelDataRook.getTextureCoords(),modelDataRook.getNormals(),modelDataRook.getIndices());
		horseModel = loader.loadToVAO(modelDataHorse.getVertices(),modelDataHorse.getTextureCoords(),modelDataHorse.getNormals(),modelDataHorse.getIndices());
		bishopModel = loader.loadToVAO(modelDataBishop.getVertices(),modelDataBishop.getTextureCoords(),modelDataBishop.getNormals(),modelDataBishop.getIndices());
		queenModel = loader.loadToVAO(modelDataQueen.getVertices(),modelDataQueen.getTextureCoords(),modelDataQueen.getNormals(),modelDataQueen.getIndices());
		kingModel = loader.loadToVAO(modelDataKing.getVertices(),modelDataKing.getTextureCoords(),modelDataKing.getNormals(),modelDataKing.getIndices());

		// create texture for terrain
		backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
		rTexture = new TerrainTexture(loader.loadTexture("dirt"));
		gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
		bTexture = new TerrainTexture(loader.loadTexture("mud"));
		texturePack = new TerrainTexturePack(backgroundTexture, rTexture,gTexture,bTexture);
		blendMap = new TerrainTexture(loader.loadTexture("blendMap"));

		texturedModelChess = new TexturedModel(chessModel, new ModelTexture(loader.loadTexture("chesstex")));
		texturedModelPawnWhite = new TexturedModel(pawnModel, new ModelTexture(loader.loadTexture("pawntex")));
		texturedModelPawnBlack = new TexturedModel(pawnModel, new ModelTexture(loader.loadTexture("pawntex2")));
		//texturedModelRookWhite = new TexturedModel(rookModel, new ModelTexture(loader.loadTexture("rooktex")));
		//texturedModelRookBlack = new TexturedModel(rookModel, new ModelTexture(loader.loadTexture("rooktex2")));
		texturedModelHorseWhite = new TexturedModel(horseModel, new ModelTexture(loader.loadTexture("horsetex")));
		texturedModelHorseBlack = new TexturedModel(horseModel, new ModelTexture(loader.loadTexture("horsetex2")));
		texturedModelBishopWhite = new TexturedModel(bishopModel, new ModelTexture(loader.loadTexture("bishoptex")));
		texturedModelBishopBlack = new TexturedModel(bishopModel, new ModelTexture(loader.loadTexture("bishoptex2")));
		texturedModelQueenWhite = new TexturedModel(queenModel, new ModelTexture(loader.loadTexture("queentex")));
		texturedModelQueenBlack = new TexturedModel(queenModel, new ModelTexture(loader.loadTexture("queentex2")));
		texturedModelKingWhite = new TexturedModel(kingModel, new ModelTexture(loader.loadTexture("kingtex")));
		texturedModelKingBlack = new TexturedModel(kingModel, new ModelTexture(loader.loadTexture("kingtex2")));

		// set shine and reflectivity of each textured model
		textureChess = texturedModelChess.getTexture();
		textureChess.setShineDamper(5);
		textureChess.setReflectivity((float)0.5);

		texturePawnWhite = texturedModelPawnWhite.getTexture();
		texturePawnWhite.setShineDamper(5);
		texturePawnWhite.setReflectivity((float)0.5);

		texturePawnBlack = texturedModelPawnBlack.getTexture();
		texturePawnBlack.setShineDamper(5);
		texturePawnBlack.setReflectivity((float)0.5);

/*		textureRookWhite = texturedModelRookWhite.getTexture();
		textureRookWhite.setShineDamper(5);
		textureRookWhite.setReflectivity((float)0.5);

		textureRookBlack = texturedModelRookBlack.getTexture();
		textureRookBlack.setShineDamper(5);
		textureRookBlack.setReflectivity((float)0.5);

 */

		textureHorseWhite = texturedModelHorseWhite.getTexture();
		textureHorseWhite.setShineDamper(5);
		textureHorseWhite.setReflectivity((float)0.5);

		textureHorseBlack = texturedModelHorseBlack.getTexture();
		textureHorseBlack.setShineDamper(5);
		textureHorseBlack.setReflectivity((float)0.5);

		textureBishopWhite = texturedModelBishopWhite.getTexture();
		textureBishopWhite.setShineDamper(5);
		textureBishopWhite.setReflectivity((float)0.5);

		textureBishopBlack = texturedModelBishopBlack.getTexture();
		textureBishopBlack.setShineDamper(5);
		textureBishopBlack.setReflectivity((float)0.5);

		textureQueenWhite = texturedModelQueenWhite.getTexture();
		textureQueenWhite.setShineDamper(5);
		textureQueenWhite.setReflectivity((float)0.5);

		textureQueenBlack = texturedModelQueenBlack.getTexture();
		textureQueenBlack.setShineDamper(5);
		textureQueenBlack.setReflectivity((float)0.5);

		textureKingWhite = texturedModelKingWhite.getTexture();
		textureKingWhite.setShineDamper(5);
		textureKingWhite.setReflectivity((float)0.5);

		textureKingBlack = texturedModelKingBlack.getTexture();
		textureKingBlack.setShineDamper(5);
		textureKingBlack.setReflectivity((float)0.5);




		// generate terrain
		terrain = new Terrain(0,0,loader,texturePack, blendMap,"heightmap");


		// generate light
		lights = new ArrayList<>();
		lights.add(new Light(new Vector3f(1000,1000,300), new Vector3f(1f,1f,1f)));

		// create and render small GUI
		guis = new ArrayList<>();
		gui = new GUITexture(loader.loadTexture("mud"), new Vector2f(0.75f, 0.75f), new Vector2f(0.25f, 0.25f));
		guis.add(gui);
		guiRenderer = new GUIRenderer(loader);


		//generate players

		player = new Player(texturedModelChess, new Vector3f((float)3.5,(float)1.2,(float)3.5),0,0,0,1);
// put the camera
		camera = new Camera(player);


		players = new ArrayList<>();
		players.add(pawnw1 = new Player(texturedModelPawnWhite, new Vector3f((float)0,(float)1.75,(float)1),0,0,0,(float) 0.6));
		players.add(pawnw2 = new Player(texturedModelPawnWhite, new Vector3f((float)1,(float)1.75,(float)1),0,0,0,(float) 0.6));
		players.add(pawnw3 = new Player(texturedModelPawnWhite, new Vector3f((float)2,(float)1.75,(float)1),0,0,0,(float) 0.6));
		players.add(pawnw4 = new Player(texturedModelPawnWhite, new Vector3f((float)3,(float)1.75,(float)1),0,0,0,(float) 0.6));
		players.add(pawnw5 = new Player(texturedModelPawnWhite, new Vector3f((float)4,(float)1.75,(float)1),0,0,0,(float) 0.6));
		players.add(pawnw6 = new Player(texturedModelPawnWhite, new Vector3f((float)5,(float)1.75,(float)1),0,0,0,(float) 0.6));
		players.add(pawnw7 = new Player(texturedModelPawnWhite, new Vector3f((float)6,(float)1.75,(float)1),0,0,0,(float) 0.6));
		players.add(pawnw8 = new Player(texturedModelPawnWhite, new Vector3f((float)7,(float)1.75,(float)1),0,0,0,(float) 0.6));
		players.add(pawnb1 = new Player(texturedModelPawnBlack, new Vector3f((float)0,(float)1.75,(float)6),0,0,0,(float) 0.6));
		players.add(pawnb2 = new Player(texturedModelPawnBlack, new Vector3f((float)1,(float)1.75,(float)6),0,0,0,(float) 0.6));
		players.add(pawnb3 = new Player(texturedModelPawnBlack, new Vector3f((float)2,(float)1.75,(float)6),0,0,0,(float) 0.6));
		players.add(pawnb4 = new Player(texturedModelPawnBlack, new Vector3f((float)3,(float)1.75,(float)6),0,0,0,(float) 0.6));
		players.add(pawnb5 = new Player(texturedModelPawnBlack, new Vector3f((float)4,(float)1.75,(float)6),0,0,0,(float) 0.6));
		players.add(pawnb6 = new Player(texturedModelPawnBlack, new Vector3f((float)5,(float)1.75,(float)6),0,0,0,(float) 0.6));
		players.add(pawnb7 = new Player(texturedModelPawnBlack, new Vector3f((float)6,(float)1.75,(float)6),0,0,0,(float) 0.6));
		players.add(pawnb8 = new Player(texturedModelPawnBlack, new Vector3f((float)7,(float)1.75,(float)6),0,0,0,(float) 0.6));
		//players.add(rookw1 = new Player(texturedModelRookWhite, new Vector3f((float)53.5,(float)1.5,(float)52.5),0,0,0,(float) 0.8));
		//players.add(rookw2 = new Player(texturedModelRookWhite, new Vector3f((float)53.5,(float)1.5,(float)52.5),0,0,0,(float) 0.8));
		//players.add(rookb1 = new Player(texturedModelRookBlack, new Vector3f((float)53.5,(float)1.5,(float)52.5),0,0,0,(float) 0.8));
		//players.add(rookb2 = new Player(texturedModelRookBlack, new Vector3f((float)53.5,(float)1.5,(float)52.5),0,0,0,(float) 0.8));
		players.add(horsew1 = new Player(texturedModelHorseWhite, new Vector3f((float)1,(float)1.75,(float)0),0,180,0,(float) 0.8));
		players.add(horsew2 = new Player(texturedModelHorseWhite, new Vector3f((float)6,(float)1.75,(float)0),0,180,0,(float) 0.8));
		players.add(horseb1 = new Player(texturedModelHorseBlack, new Vector3f((float)1,(float)1.75,(float)7),0,0,0,(float) 0.8));
		players.add(horseb2 = new Player(texturedModelHorseBlack, new Vector3f((float)6,(float)1.75,(float)7),0,0,0,(float) 0.8));
		players.add(bishopw1 = new Player(texturedModelBishopWhite, new Vector3f((float)2,(float)1.75,(float)0),0,0,0,(float) 0.8));
		players.add(bishopw2 = new Player(texturedModelBishopWhite, new Vector3f((float)5,(float)1.75,(float)0),0,0,0,(float) 0.8));
		players.add(bishopb1 = new Player(texturedModelBishopBlack, new Vector3f((float)2,(float)1.75,(float)7),0,0,0,(float) 0.8));
		players.add(bishopb2 = new Player(texturedModelBishopBlack, new Vector3f((float)5,(float)1.75,(float)7),0,0,0,(float) 0.8));
		players.add(queenw = new Player(texturedModelQueenWhite, new Vector3f((float)3,(float)1.75,(float)0),0,0,0,(float) 0.8));
		players.add(queenb = new Player(texturedModelQueenBlack, new Vector3f((float)3,(float)1.75,(float)7),0,0,0,(float) 0.8));
		players.add(kingw = new Player(texturedModelKingWhite, new Vector3f((float)4,(float)1.75,(float)0),0,0,0,(float) 0.8));
		players.add(kingb = new Player(texturedModelKingBlack, new Vector3f((float)4,(float)1.75,(float)7),0,0,0,(float) 0.8));

		// put the camera


	}

	public void run() {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// render the scene continuously with updated scene
		while (!window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
			update();
			render();
			if (Input.isKeyDown(GLFW.GLFW_KEY_F11)) window.setFullscreen(!window.isFullscreen());
			if (Input.getMouseX()>50 && Input.getMouseX()<100 && Input.getMouseY()>50 && Input.getMouseY()<100){
				pawnb1.move(terrain,new Vector2f());
				horsew1.move(terrain,new Vector2f(5,5));
			}

		}

		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		window.destroy();
	}

	private void update() {
		window.update();
	}

	private void render() {
		camera.move();
		i++;

        // render scene from different angles/ distant
		renderer.processEntity(player);

		for(Player pieces : players){
			renderer.processEntity(pieces);
		}
		renderer.processTerrain(terrain);

		renderer.render(lights,camera, new Vector4f(0,-1,0, 100000));


		window.swapBuffers();

	}

	public static int getWIDTH() {
		return WIDTH;
	}

	public static int getHEIGHT() {
		return HEIGHT;
	}

}
