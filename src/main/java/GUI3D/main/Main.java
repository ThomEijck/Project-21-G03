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
import gameLogic.pieces.Piece;
import gameLogic.util.GameManager;
import gameLogic.util.MiniMax.*;
import gameLogic.util.Move;
import gameLogic.util.Position;
import org.lwjgl.glfw.GLFW;
import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;
import org.lwjglx.util.vector.Vector4f;
import GUI3D.terrain.Terrain;
import GUI3D.MoveMenu.MoveBox;

import java.util.ArrayList;
import java.util.List;

public class Main implements Runnable {

	public Thread game;
	public Window window;
	public static final int WIDTH = 1600, HEIGHT = 900;
	public int i = 0;

	public Loader loader = new Loader();

	public RawModel chessModel;
	public RawModel borderModel;
	public RawModel tableModel;
	public RawModel pawnModel;
	public RawModel rookModel;
	public RawModel horseModel;
	public RawModel bishopModel;
	public RawModel queenModel;
	public RawModel kingModel;
	public RawModel diceModel;


	public ModelData modelDataChess;
	public ModelData modelDataBorder;
	public ModelData modelDataTable;
	public ModelData modelDataPawn;
	public ModelData modelDataRook;
	public ModelData modelDataHorse;
	public ModelData modelDataBishop;
	public ModelData modelDataQueen;
	public ModelData modelDataKing;
	public ModelData modelDataDice;

	public ModelTexture textureChess;
	public ModelTexture textureBorder;
	public ModelTexture textureTable;
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
	public ModelTexture textureDice;

	public TexturedModel texturedModelChess;
	public TexturedModel texturedModelBorder;
	public TexturedModel texturedModelTable;
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
	public TexturedModel texturedModelDice;

	public MasterRenderer renderer;

	public Player board;
	public Player border;
	public Player table;
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
	public Player rookw3;
	public Player horsew1;
	public Player horsew2;
	public Player horsew3;
	public Player bishopw1;
	public Player bishopw2;
	public Player bishopw3;
	public Player queenw;
	public Player queenw2;
	public Player kingw;

	public Player rookb1;
	public Player rookb2;
	public Player rookb3;
	public Player horseb1;
	public Player horseb2;
	public Player horseb3;
	public Player bishopb1;
	public Player bishopb2;
	public Player bishopb3;
	public Player queenb;
	public Player queenb2;
	public Player kingb;
	public Player dice;
	public Player kingWinnerBlack;
	public Player kingWinnerWhite;

	public Player dicew1;
	public Player dicew2;
	public Player dicew3;
	public Player dicew4;
	public Player dicew5;
	public Player dicew6;
	public Player diceb1;
	public Player diceb2;
	public Player diceb3;
	public Player diceb4;
	public Player diceb5;
	public Player diceb6;

	public Camera camera;

	public List<Light> lights;
	public List<Entity> entities;
	public List<Player> players;
	public static Terrain terrain;

	public TerrainTexture backgroundTexture;
	public TerrainTexture rTexture;
	public TerrainTexture gTexture;
	public TerrainTexture bTexture;
	public TerrainTexturePack texturePack;
	public TerrainTexture blendMap;

	public GUITexture whiteWin;
	public GUITexture blackWin;
	public GUITexture draw;
	public GUIRenderer guiRenderer;

	private static final float RED = 0.5f;
	private static final float GREEN = 0.5f;
	private static final float BLUE = 0.5f;

	//ai
	public TDMatrixEvaluatorUtil TDevaluator;
	public static GameManager g;
	public MoveMakerUtil moveMaker;
	public ExpectiMiniMaxExecutorUtil emm;
	int depth = 4;
	int diceValue;
	int player;
	public static boolean canOpenMoveBox = true;
	public static boolean isOpenMoveBox = false;
	public static Position posStart;
	public static Position posEnd;
	public static boolean appliedMove = false;
	public static boolean moveHasBeenMade = false;
	public static boolean updateBoard = false;

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
		modelDataChess = OBJFileLoader.loadOBJ("board");
		modelDataBorder = OBJFileLoader.loadOBJ("border");
		modelDataTable = OBJFileLoader.loadOBJ("table");
		modelDataPawn = OBJFileLoader.loadOBJ("bpawn");
		modelDataRook = OBJFileLoader.loadOBJ("brook");
		modelDataHorse = OBJFileLoader.loadOBJ("bhorse");
		modelDataBishop = OBJFileLoader.loadOBJ("bbishop");
		modelDataQueen = OBJFileLoader.loadOBJ("bqueen");
		modelDataKing = OBJFileLoader.loadOBJ("bking");
		modelDataDice = OBJFileLoader.loadOBJ("dice");

		chessModel = loader.loadToVAO(modelDataChess.getVertices(),modelDataChess.getTextureCoords(),modelDataChess.getNormals(),modelDataChess.getIndices());
		borderModel = loader.loadToVAO(modelDataBorder.getVertices(), modelDataBorder.getTextureCoords(), modelDataBorder.getNormals(), modelDataBorder.getIndices());
		tableModel = loader.loadToVAO(modelDataTable.getVertices(), modelDataTable.getTextureCoords(), modelDataTable.getNormals(), modelDataTable.getIndices());
		pawnModel = loader.loadToVAO(modelDataPawn.getVertices(), modelDataPawn.getTextureCoords(),modelDataPawn.getNormals(),modelDataPawn.getIndices());
		rookModel = loader.loadToVAO(modelDataRook.getVertices(),modelDataRook.getTextureCoords(),modelDataRook.getNormals(),modelDataRook.getIndices());
		horseModel = loader.loadToVAO(modelDataHorse.getVertices(),modelDataHorse.getTextureCoords(),modelDataHorse.getNormals(),modelDataHorse.getIndices());
		bishopModel = loader.loadToVAO(modelDataBishop.getVertices(),modelDataBishop.getTextureCoords(),modelDataBishop.getNormals(),modelDataBishop.getIndices());
		queenModel = loader.loadToVAO(modelDataQueen.getVertices(),modelDataQueen.getTextureCoords(),modelDataQueen.getNormals(),modelDataQueen.getIndices());
		kingModel = loader.loadToVAO(modelDataKing.getVertices(),modelDataKing.getTextureCoords(),modelDataKing.getNormals(),modelDataKing.getIndices());
		diceModel = loader.loadToVAO(modelDataDice.getVertices(),modelDataDice.getTextureCoords(),modelDataDice.getNormals(),modelDataDice.getIndices());

		// create texture for terrain
		backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
		rTexture = new TerrainTexture(loader.loadTexture("dirt"));
		gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
		bTexture = new TerrainTexture(loader.loadTexture("mud"));
		texturePack = new TerrainTexturePack(backgroundTexture, rTexture,gTexture,bTexture);
		blendMap = new TerrainTexture(loader.loadTexture("blendMap"));

		texturedModelChess = new TexturedModel(chessModel, new ModelTexture(loader.loadTexture("board")));
		texturedModelBorder = new TexturedModel(borderModel, new ModelTexture(loader.loadTexture("border")));
		texturedModelTable = new TexturedModel(tableModel, new ModelTexture(loader.loadTexture("table")));
		texturedModelPawnWhite = new TexturedModel(pawnModel, new ModelTexture(loader.loadTexture("wpawn")));
		texturedModelPawnBlack = new TexturedModel(pawnModel, new ModelTexture(loader.loadTexture("bpawn")));
		texturedModelRookWhite = new TexturedModel(rookModel, new ModelTexture(loader.loadTexture("wrook")));
		texturedModelRookBlack = new TexturedModel(rookModel, new ModelTexture(loader.loadTexture("brook")));
		texturedModelHorseWhite = new TexturedModel(horseModel, new ModelTexture(loader.loadTexture("whorse")));
		texturedModelHorseBlack = new TexturedModel(horseModel, new ModelTexture(loader.loadTexture("bhorse")));
		texturedModelBishopWhite = new TexturedModel(bishopModel, new ModelTexture(loader.loadTexture("wbishop")));
		texturedModelBishopBlack = new TexturedModel(bishopModel, new ModelTexture(loader.loadTexture("bbishop")));
		texturedModelQueenWhite = new TexturedModel(queenModel, new ModelTexture(loader.loadTexture("wqueen")));
		texturedModelQueenBlack = new TexturedModel(queenModel, new ModelTexture(loader.loadTexture("bqueen")));
		texturedModelKingWhite = new TexturedModel(kingModel, new ModelTexture(loader.loadTexture("wking")));
		texturedModelKingBlack = new TexturedModel(kingModel, new ModelTexture(loader.loadTexture("bking")));
		texturedModelDice = new TexturedModel(diceModel, new ModelTexture(loader.loadTexture("dice")));

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

		textureRookWhite = texturedModelRookWhite.getTexture();
		textureRookWhite.setShineDamper(5);
		textureRookWhite.setReflectivity((float)0.5);

		textureRookBlack = texturedModelRookBlack.getTexture();
		textureRookBlack.setShineDamper(5);
		textureRookBlack.setReflectivity((float)0.5);


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
		whiteWin = new GUITexture(loader.loadTexture("whitewin"), new Vector2f(100, 100), new Vector2f(30, 30));
		blackWin = new GUITexture(loader.loadTexture("blackwin"), new Vector2f(100, 100), new Vector2f(30, 30));
		draw = new GUITexture(loader.loadTexture("draw"), new Vector2f(100, 100), new Vector2f(30, 30));
		guiRenderer = new GUIRenderer(loader);


		//generate players

		board = new Player(texturedModelChess, new Vector3f((float)3.5,0,(float)3.5),0,90,0,1,1);
		border = new Player(texturedModelBorder, new Vector3f((float)3.5,0,(float)3.5),0,270,0,1,1);
		table = new Player(texturedModelTable, new Vector3f((float)3.5,0,(float)3.5),0,90,0,1,1);
		dice = new Player(texturedModelDice, new Vector3f(10,0,2),270,0,0,1,1);
		kingWinnerWhite = new Player(texturedModelKingWhite, new Vector3f((float)-1.5,(float)0.1,(float)3.5),0,270,0,1,1);
		kingWinnerBlack = new Player(texturedModelKingBlack, new Vector3f((float)-1.5,(float)0.1,(float)3.5),0,270,0,1,1);

// put the camera
		camera = new Camera(board);

		players = new ArrayList<>();
		players.add(pawnw1 = new Player(texturedModelPawnWhite, new Vector3f(0,0,6),0,0,0,(float) 0.6, 1));
		players.add(pawnw2 = new Player(texturedModelPawnWhite, new Vector3f(1,0,6),0,0,0,(float) 0.6,2));
		players.add(pawnw3 = new Player(texturedModelPawnWhite, new Vector3f(2,0,6),0,0,0,(float) 0.6,3));
		players.add(pawnw4 = new Player(texturedModelPawnWhite, new Vector3f(3,0,6),0,0,0,(float) 0.6,4));
		players.add(pawnw5 = new Player(texturedModelPawnWhite, new Vector3f(4,0,6),0,0,0,(float) 0.6,5));
		players.add(pawnw6 = new Player(texturedModelPawnWhite, new Vector3f(5,0,6),0,0,0,(float) 0.6,6));
		players.add(pawnw7 = new Player(texturedModelPawnWhite, new Vector3f(6,0,6),0,0,0,(float) 0.6,7));
		players.add(pawnw8 = new Player(texturedModelPawnWhite, new Vector3f(7,0,6),0,0,0,(float) 0.6,8));
		players.add(pawnb1 = new Player(texturedModelPawnBlack, new Vector3f(0,0,1),0,0,0,(float) 0.6,1));
		players.add(pawnb2 = new Player(texturedModelPawnBlack, new Vector3f(1,0,1),0,0,0,(float) 0.6,2));
		players.add(pawnb3 = new Player(texturedModelPawnBlack, new Vector3f(2,0,1),0,0,0,(float) 0.6,3));
		players.add(pawnb4 = new Player(texturedModelPawnBlack, new Vector3f(3,0,1),0,0,0,(float) 0.6,4));
		players.add(pawnb5 = new Player(texturedModelPawnBlack, new Vector3f(4,0,1),0,0,0,(float) 0.6,5));
		players.add(pawnb6 = new Player(texturedModelPawnBlack, new Vector3f(5,0,1),0,0,0,(float) 0.6,6));
		players.add(pawnb7 = new Player(texturedModelPawnBlack, new Vector3f(6,0,1),0,0,0,(float) 0.6,7));
		players.add(pawnb8 = new Player(texturedModelPawnBlack, new Vector3f(7,0,1),0,0,0,(float) 0.6,8));
		players.add(rookw1 = new Player(texturedModelRookWhite, new Vector3f(0,0,7),0,90,0,(float) 0.6,1));
		players.add(rookw2 = new Player(texturedModelRookWhite, new Vector3f(7,0,7),0,90,0,(float) 0.6,2));
		players.add(rookb1 = new Player(texturedModelRookBlack, new Vector3f(0,0,0),0,270,0,(float) 0.6,1));
		players.add(rookb2 = new Player(texturedModelRookBlack, new Vector3f(7,0,0),0,270,0,(float) 0.6,2));
		players.add(horsew1 = new Player(texturedModelHorseWhite, new Vector3f(1,0,7),0,90,0,(float) 0.6,1));
		players.add(horsew2 = new Player(texturedModelHorseWhite, new Vector3f(6,0,7),0,90,0,(float) 0.6,2));
		players.add(horseb1 = new Player(texturedModelHorseBlack, new Vector3f(1,0,0),0,270,0,(float) 0.6,1));
		players.add(horseb2 = new Player(texturedModelHorseBlack, new Vector3f(6,0,0),0,270,0,(float) 0.6,2));
		players.add(bishopw1 = new Player(texturedModelBishopWhite, new Vector3f(2,0,7),0,90,0,(float) 0.6,1));
		players.add(bishopw2 = new Player(texturedModelBishopWhite, new Vector3f(5,0,7),0,90,0,(float) 0.6,2));
		players.add(bishopb1 = new Player(texturedModelBishopBlack, new Vector3f(2,0,0),0,270,0,(float) 0.6,1));
		players.add(bishopb2 = new Player(texturedModelBishopBlack, new Vector3f(5,0,0),0,270,0,(float) 0.6,2));
		players.add(queenw = new Player(texturedModelQueenWhite, new Vector3f(3,0,7),0,90,0,(float) 0.6,1));
		players.add(queenb = new Player(texturedModelQueenBlack, new Vector3f(3,0,0),0,270,0,(float) 0.6,1));
		players.add(kingw = new Player(texturedModelKingWhite, new Vector3f(4,0,7),0,90,0,(float) 0.6,1));
		players.add(kingb = new Player(texturedModelKingBlack, new Vector3f(4,0,0),0,270,0,(float) 0.6,1));
		// put the camera


		// AI
		TDevaluator = new TDMatrixEvaluatorUtil();
		g = new GameManager();
		moveMaker = new MoveMakerUtil();
		emm = new ExpectiMiniMaxExecutorUtil(TDevaluator,moveMaker);
		posStart = new Position(0,0);
		posEnd = new Position(0,0);

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

			player = GameManager.getCurrPlayer();
			diceValue = GameManager.getDiceValue();


			switch (diceValue) {
				case 1:
					dice = new Player(texturedModelDice, new Vector3f(10, (float)-0.3, 2), 0, 0, 180, 1, 1);
					if (player==1){
						dicew1 = new Player(texturedModelPawnWhite,  new Vector3f(10, (float)-1, 5), 0, 0, 0, 1, 1);
						renderer.processEntity(dicew1);
					}
					else{
						diceb1 = new Player(texturedModelPawnBlack,  new Vector3f(10,  (float)-1, 5), 0, 0, 0, 1, 1);
						renderer.processEntity(diceb1);
					}
					break;
				case 2:
					dice = new Player(texturedModelDice, new Vector3f(10, (float)-0.3, 2), 0, 0, 270, 1, 1);
					if (player==1){
						dicew2 = new Player(texturedModelHorseWhite,  new Vector3f(10,  (float)-1, 5), 0, 0, 0, 1, 1);
						renderer.processEntity(dicew2);
					}
					else{
						diceb2 = new Player(texturedModelHorseBlack,  new Vector3f(10,  (float)-1, 5), 0, 0, 0, 1, 1);
						renderer.processEntity(diceb2);
					}
					break;
				case 3:
					dice = new Player(texturedModelDice, new Vector3f(10, (float)-0.3, 2), 90, 0, 0, 1, 1);
					if (player==1){
						dicew3 = new Player(texturedModelBishopWhite,  new Vector3f(10,  (float)-1, 5), 0, 0, 0, 1, 1);
						renderer.processEntity(dicew3);
					}
					else{
						diceb3 = new Player(texturedModelBishopBlack,  new Vector3f(10,  (float)-1, 5), 0, 0, 0, 1, 1);
						renderer.processEntity(diceb3);
					}
					break;
				case 4:
					dice = new Player(texturedModelDice, new Vector3f(10, (float)-0.3, 2), 270, 0, 0, 1, 1);
					if (player==1){
						dicew4 = new Player(texturedModelRookWhite,  new Vector3f(10,  (float)-1, 5), 0, 0, 0, 1, 1);
						renderer.processEntity(dicew4);
					}
					else{
						diceb4 = new Player(texturedModelRookBlack,  new Vector3f(10,  (float)-1, 5), 0, 0, 0, 1, 1);
						renderer.processEntity(diceb4);
					}
					break;
				case 5:
					dice = new Player(texturedModelDice, new Vector3f(10, (float)-0.3, 2), 0, 0, 90, 1, 1);
					if (player==1){
						dicew5 = new Player(texturedModelQueenWhite,  new Vector3f(10,  (float)-1, 5), 0, 0, 0, 1, 1);
						renderer.processEntity(dicew5);
					}
					else{
						diceb5 = new Player(texturedModelQueenBlack,  new Vector3f(10,  (float)-1, 5), 0, 0, 0, 1, 1);
						renderer.processEntity(diceb5);
					}
					break;
				case 6:
					dice = new Player(texturedModelDice, new Vector3f(10, (float)-0.3, 2), 0, 0, 0, 1, 1);
					if (player==1){
						dicew6 = new Player(texturedModelKingWhite,  new Vector3f(10,  (float)-1, 5), 0, 0, 0, 1, 1);
						renderer.processEntity(dicew6);
					}
					else{
						diceb6 = new Player(texturedModelKingBlack,  new Vector3f(10,  (float)-1, 5), 0, 0, 0, 1, 1);
						renderer.processEntity(diceb6);
					}
					break;
				default:
					break;
			}

			if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_1) && !isOpenMoveBox) {

				while (GameManager.getGameState() == 0 && !moveHasBeenMade) {
					Move m = null;

					System.out.println(diceValue);


					if (player == 1) {
						m = emm.findBestMove(g.getBoard(), player, depth, diceValue);
						g.movePiece(m, true);
						GameManager.pieceMoved();
					} else {

						// ai vs ai
						m = emm.findBestMove(g.getBoard(), player, depth, diceValue);
						g.movePiece(m, true);
						GameManager.pieceMoved();
						moveHasBeenMade = true;


						/*
						isOpenMoveBox = true;

						MoveBox.create();


						while(!appliedMove){
							//ai vs player
							if(!isOpenMoveBox){
								break;
							}
						}

						 */

					}
					moveHasBeenMade = true;
					appliedMove = false;
					players.clear();
					updateBoard = true;
				}
				moveHasBeenMade = false;

				Piece[][] pieces = g.getBoard().getChessBoard();

				if (updateBoard) {

					for (int i = 0; i < pieces.length; i++) {
						for (int j = 0; j < pieces.length; j++) {
							Piece piece = pieces[i][j];
							if (piece == null) {
								continue;
							} else {
								if (piece.getPlayer() == 1) {
									if (piece.getInt() == 1) {
										switch (piece.getPieceNumber()) {
											case 1:
												pawnw1 = new Player(texturedModelPawnWhite, new Vector3f(piece.getPos().column, 0, piece.getPos().row), 0, 0, 0, (float) 0.6, 1);
												players.add(pawnw1);
												break;
											case 2:
												pawnw2 = new Player(texturedModelPawnWhite, new Vector3f(piece.getPos().column, 0, piece.getPos().row), 0, 0, 0, (float) 0.6, 2);
												players.add(pawnw2);
												break;
											case 3:
												pawnw3 = new Player(texturedModelPawnWhite, new Vector3f(piece.getPos().column, 0, piece.getPos().row), 0, 0, 0, (float) 0.6, 3);
												players.add(pawnw3);
												break;
											case 4:
												pawnw4 = new Player(texturedModelPawnWhite, new Vector3f(piece.getPos().column, 0, piece.getPos().row), 0, 0, 0, (float) 0.6, 4);
												players.add(pawnw4);
												break;
											case 5:
												pawnw5 = new Player(texturedModelPawnWhite, new Vector3f(piece.getPos().column, 0, piece.getPos().row), 0, 0, 0, (float) 0.6, 5);
												players.add(pawnw5);
												break;
											case 6:
												pawnw6 = new Player(texturedModelPawnWhite, new Vector3f(piece.getPos().column, 0, piece.getPos().row), 0, 0, 0, (float) 0.6, 6);
												players.add(pawnw6);
												break;
											case 7:
												pawnw7 = new Player(texturedModelPawnWhite, new Vector3f(piece.getPos().column, 0, piece.getPos().row), 0, 0, 0, (float) 0.6, 7);
												players.add(pawnw7);
												break;
											case 8:
												pawnw8 = new Player(texturedModelPawnWhite, new Vector3f(piece.getPos().column, 0, piece.getPos().row), 0, 0, 0, (float) 0.6, 8);
												players.add(pawnw8);
												break;
											default:
												break;
										}
									} else if (piece.getInt() == 2) {
										switch (piece.getPieceNumber()) {
											case 1:
												horsew1 = new Player(texturedModelHorseWhite, new Vector3f(piece.getPos().column, 0, piece.getPos().row), 0, 90, 0, (float) 0.6, 1);
												players.add(horsew1);
												break;
											case 2:
												horsew2 = new Player(texturedModelHorseWhite, new Vector3f(piece.getPos().column, 0, piece.getPos().row), 0, 90, 0, (float) 0.6, 2);
												players.add(horsew2);
												break;
											case 3:
												horsew3 = new Player(texturedModelHorseWhite, new Vector3f(piece.getPos().column, 0, piece.getPos().row), 0, 90, 0, (float) 0.6, 3);
												players.add(horsew3);
												break;
											default:
												break;
										}
									} else if (piece.getInt() == 3) {
										switch (piece.getPieceNumber()) {
											case 1:
												bishopw1 = new Player(texturedModelBishopWhite, new Vector3f(piece.getPos().column, 0, piece.getPos().row), 0, 90, 0, (float) 0.6, 1);
												players.add(bishopw1);
												break;
											case 2:
												bishopw2 = new Player(texturedModelBishopWhite, new Vector3f(piece.getPos().column, 0, piece.getPos().row), 0, 90, 0, (float) 0.6, 2);
												players.add(bishopw2);
												break;
											case 3:
												bishopw3 = new Player(texturedModelBishopWhite, new Vector3f(piece.getPos().column, 0, piece.getPos().row), 0, 90, 0, (float) 0.6, 3);
												players.add(bishopw3);
												break;
											default:
												break;
										}
									} else if (piece.getInt() == 4) {
										switch (piece.getPieceNumber()) {
											case 1:
												rookw1 = new Player(texturedModelRookWhite, new Vector3f(piece.getPos().column, 0, piece.getPos().row), 0, 90, 0, (float) 0.6, 1);
												players.add(rookw1);
												break;
											case 2:
												rookw2 = new Player(texturedModelRookWhite, new Vector3f(piece.getPos().column, 0, piece.getPos().row), 0, 90, 0, (float) 0.6, 2);
												players.add(rookw2);
												break;
											case 3:
												rookw3 = new Player(texturedModelRookWhite, new Vector3f(piece.getPos().column, 0, piece.getPos().row), 0, 90, 0, (float) 0.6, 3);
												players.add(rookw3);
												break;
											default:
												break;
										}
									} else if (piece.getInt() == 5) {
										switch (piece.getPieceNumber()) {
											case 1:
												queenw = new Player(texturedModelQueenWhite, new Vector3f(piece.getPos().column, 0, piece.getPos().row), 0, 90, 0, (float) 0.6, 1);
												players.add(queenw);
												break;
											case 2:
												queenw2 = new Player(texturedModelQueenWhite, new Vector3f(piece.getPos().column, 0, piece.getPos().row), 0, 90, 0, (float) 0.6, 2);
												players.add(queenw2);
												break;
											default:
												break;
										}
									} else if (piece.getInt() == 6) {
										switch (piece.getPieceNumber()) {
											case 1:
												kingw = new Player(texturedModelKingWhite, new Vector3f(piece.getPos().column, 0, piece.getPos().row), 0, 90, 0, (float) 0.6, 1);
												players.add(kingw);
												break;
											default:
												break;
										}
									}
								} else if (piece.getPlayer() == 2) {
									if (piece.getInt() == 1) {
										switch (piece.getPieceNumber()) {
											case 1:
												pawnb1 = new Player(texturedModelPawnBlack, new Vector3f(piece.getPos().column, 0, piece.getPos().row), 0, 0, 0, (float) 0.6, 1);
												players.add(pawnb1);
												break;
											case 2:
												pawnb2 = new Player(texturedModelPawnBlack, new Vector3f(piece.getPos().column, 0, piece.getPos().row), 0, 0, 0, (float) 0.6, 2);
												players.add(pawnb2);
												break;
											case 3:
												pawnb3 = new Player(texturedModelPawnBlack, new Vector3f(piece.getPos().column, 0, piece.getPos().row), 0, 0, 0, (float) 0.6, 3);
												players.add(pawnb3);
												break;
											case 4:
												pawnb4 = new Player(texturedModelPawnBlack, new Vector3f(piece.getPos().column, 0, piece.getPos().row), 0, 0, 0, (float) 0.6, 4);
												players.add(pawnb4);
												break;
											case 5:
												pawnb5 = new Player(texturedModelPawnBlack, new Vector3f(piece.getPos().column, 0, piece.getPos().row), 0, 0, 0, (float) 0.6, 5);
												players.add(pawnb5);
												break;
											case 6:
												pawnb6 = new Player(texturedModelPawnBlack, new Vector3f(piece.getPos().column, 0, piece.getPos().row), 0, 0, 0, (float) 0.6, 6);
												players.add(pawnb6);
												break;
											case 7:
												pawnb7 = new Player(texturedModelPawnBlack, new Vector3f(piece.getPos().column, 0, piece.getPos().row), 0, 0, 0, (float) 0.6, 7);
												players.add(pawnb7);
												break;
											case 8:
												pawnb8 = new Player(texturedModelPawnBlack, new Vector3f(piece.getPos().column, 0, piece.getPos().row), 0, 0, 0, (float) 0.6, 8);
												players.add(pawnb8);
												break;
											default:
												break;
										}
									} else if (piece.getInt() == 2) {
										switch (piece.getPieceNumber()) {
											case 1:
												horseb1 = new Player(texturedModelHorseBlack, new Vector3f(piece.getPos().column, 0, piece.getPos().row), 0, 270, 0, (float) 0.6, 1);
												players.add(horseb1);
												break;
											case 2:
												horseb2 = new Player(texturedModelHorseBlack, new Vector3f(piece.getPos().column, 0, piece.getPos().row), 0, 270, 0, (float) 0.6, 2);
												players.add(horseb2);
												break;
											case 3:
												horseb3 = new Player(texturedModelHorseBlack, new Vector3f(piece.getPos().column, 0, piece.getPos().row), 0, 270, 0, (float) 0.6, 3);
												players.add(horseb3);
												break;
											default:
												break;
										}
									} else if (piece.getInt() == 3) {
										switch (piece.getPieceNumber()) {
											case 1:
												bishopb1 = new Player(texturedModelBishopBlack, new Vector3f(piece.getPos().column, 0, piece.getPos().row), 0, 270, 0, (float) 0.6, 1);
												players.add(bishopb1);
												break;
											case 2:
												bishopb2 = new Player(texturedModelBishopBlack, new Vector3f(piece.getPos().column, 0, piece.getPos().row), 0, 270, 0, (float) 0.6, 2);
												players.add(bishopb2);
												break;
											case 3:
												bishopb3 = new Player(texturedModelBishopBlack, new Vector3f(piece.getPos().column, 0, piece.getPos().row), 0, 270, 0, (float) 0.6, 3);
												players.add(bishopb3);
												break;
											default:
												break;
										}
									} else if (piece.getInt() == 4) {
										switch (piece.getPieceNumber()) {
											case 1:
												rookb1 = new Player(texturedModelRookBlack, new Vector3f(piece.getPos().column, 0, piece.getPos().row), 0, 270, 0, (float) 0.6, 1);
												players.add(rookb1);
												break;
											case 2:
												rookb2 = new Player(texturedModelRookBlack, new Vector3f(piece.getPos().column, 0, piece.getPos().row), 0, 270, 0, (float) 0.6, 2);
												players.add(rookb2);
												break;
											case 3:
												rookb3 = new Player(texturedModelRookBlack, new Vector3f(piece.getPos().column, 0, piece.getPos().row), 0, 270, 0, (float) 0.6, 3);
												players.add(rookb3);
												break;
											default:
												break;
										}
									} else if (piece.getInt() == 5) {
										switch (piece.getPieceNumber()) {
											case 1:
												queenb = new Player(texturedModelQueenBlack, new Vector3f(piece.getPos().column, 0, piece.getPos().row), 0, 270, 0, (float) 0.6, 1);
												players.add(queenb);
												break;
											case 2:
												queenb2 = new Player(texturedModelQueenBlack, new Vector3f(piece.getPos().column, 0, piece.getPos().row), 0, 270, 0, (float) 0.6, 2);
												players.add(queenb2);
												break;
											default:
												break;
										}
									} else if (piece.getInt() == 6) {
										switch (piece.getPieceNumber()) {
											case 1:
												kingb = new Player(texturedModelKingBlack, new Vector3f(piece.getPos().column, 0, piece.getPos().row), 0, 270, 0, (float) 0.6, 1);
												players.add(kingb);
												break;
											default:
												break;
										}
									}
								}

							}
						}
					}
					updateBoard = false;
				}
			}

			if (GameManager.getGameState() == 1) {
				renderer.processEntity(kingWinnerWhite);
				kingWinnerBlack = new Player(texturedModelKingBlack, new Vector3f((float)7.5,(float)0.5,(float)3.5),0,180,90,(float)0.7,1);
				renderer.processEntity(kingWinnerBlack);
			} if (GameManager.getGameState() == 2) {
				renderer.processEntity(kingWinnerBlack);
				kingWinnerWhite = new Player(texturedModelKingWhite, new Vector3f((float)7.5,(float)0.5,(float)3.5),0,180,90,(float)0.7,1);
				renderer.processEntity(kingWinnerWhite);
			}
			else if(GameManager.getGameState()==3) {
				kingWinnerWhite = new Player(texturedModelKingWhite, new Vector3f(5,0,(float)3.5),0,270,0,1,1);
				kingWinnerBlack = new Player(texturedModelKingBlack, new Vector3f(-5,0,(float)3.5),0,270,0,1,1);

				renderer.processEntity(kingWinnerWhite);
				renderer.processEntity(kingWinnerBlack);
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

		if(GameManager.getGameState() == 1){
			guiRenderer.render(whiteWin);
		}
		if(GameManager.getGameState() == 2){
			guiRenderer.render(blackWin);
		}
		if(GameManager.getGameState() ==3){
			guiRenderer.render(draw);
		}

        // render scene from different angles/ distant
		renderer.processEntity(board);
		renderer.processEntity(border);
		renderer.processEntity(table);
		renderer.processEntity(dice);

		for(Player pieces : players){
			renderer.processEntity(pieces);
		}

		renderer.processTerrain(terrain);

		renderer.render(lights,camera, new Vector4f(0,-1,0, 100));


		window.swapBuffers();

	}

	public static int getWIDTH() {
		return WIDTH;
	}

	public static int getHEIGHT() {
		return HEIGHT;
	}

}
