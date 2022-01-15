package gameLogic.util.MiniMax;

import gameLogic.pieces.Piece;
import gameLogic.util.Board;
import gameLogic.util.Move;
import gameLogic.util.Position;

import java.util.Random;
import java.util.Vector;

public class TDMatrixEvaluatorUtil implements BoardEvaluatorUtil
{

    float[] imported = {1.0F,1.0F,1.0F,1.0F,1.0F,1.0F,1.0F,1.0F,8.008577F,12.642872F,22.34627F,25.048513F,11.508323F,3.8830197F,5.276826F,14.160573F,3.2819154F,13.533674F,6.3169556F,13.017574F,7.4888535F,-9.268513F,0.61129856F,1.6858582F,-3.5519705F,-14.240644F,7.320185F,-0.8807628F,6.700843F,10.240496F,-6.7206016F,-7.6582828F,-27.797787F,-2.677984F,-1.8476335F,13.637584F,-12.1252365F,-2.2063904F,-10.302335F,-9.623295F,-2.9920845F,-3.0875723F,-4.185953F,-5.0318327F,-21.226494F,-8.017271F,-9.543327F,-13.042521F,-15.5402355F,24.759895F,-12.465248F,15.8079F,2.004916F,25.980013F,-10.581669F,-28.382784F,1.0F,1.0F,1.0F,1.0F,1.0F,1.0F,1.0F,1.0F,18.178324F,5.8816266F,8.664116F,-3.635031F,25.178988F,10.3033495F,17.940887F,7.1958637F,-2.0093043F,17.60536F,26.01187F,23.235405F,12.07584F,14.4063225F,7.4399633F,17.20288F,16.644667F,14.549755F,4.0141306F,17.727137F,5.384719F,26.560724F,30.80459F,14.101095F,5.3715954F,6.154967F,8.981711F,0.42876115F,-0.14977658F,-7.878344F,-5.212058F,-0.593959F,6.190433F,3.8484614F,-1.7953376F,-5.9559083F,12.142134F,5.9396696F,-10.475221F,2.0699043F,-7.1395793F,-6.3610835F,-3.7279036F,-12.02005F,-1.536861F,-8.268659F,-4.781762F,-14.093386F,-4.110511F,0.033864975F,-3.9751005F,-3.3088028F,-2.0689456F,-15.762121F,-11.297871F,2.2947617F,-10.422732F,-0.26311955F,-6.3258915F,-13.811338F,-5.5435843F,-7.63743F,-11.347929F,6.522917F,-5.955106F,-2.2656999F,-3.566503F,-2.0694551F,9.160062F,0.2764374F,15.253149F,-8.131719F,18.912832F,-2.3535795F,-8.313912F,11.819478F,-7.7070527F,1.9207684F,7.16594F,-0.29974288F,-8.322639F,17.741772F,1.297729F,7.842254F,-0.3546535F,6.5527325F,-4.4593544F,5.1924567F,3.8678367F,6.2492332F,13.1064415F,1.1931314F,-2.2504854F,2.1237087F,1.8296598F,2.348896F,6.832925F,0.37536138F,-3.9737685F,-1.2937722F,-1.2261332F,-13.161813F,4.9885006F,0.60342216F,2.2050006F,5.4069023F,8.732979F,-8.527999F,8.214103F,-19.586334F,-18.857693F,12.8564005F,-3.555776F,-5.1781483F,3.86358F,5.4811606F,-3.7268982F,6.5789576F,-8.518907F,-3.8444757F,4.1495647F,8.493787F,0.8386785F,-8.446067F,4.370783F,7.6362863F,8.239846F,-7.565576F,26.309269F,31.259985F,15.712637F,27.790121F,28.714771F,4.8839803F,21.851868F,23.021196F,12.1007F,25.016914F,12.587633F,25.195F,17.775223F,23.244152F,27.12209F,16.541336F,7.7448335F,26.098553F,13.241485F,13.118403F,13.636202F,4.929878F,16.18378F,8.664067F,16.27517F,6.8393383F,31.823107F,14.417511F,9.445595F,22.706654F,2.8702183F,2.0832682F,23.996395F,2.570896F,19.69678F,0.5857966F,11.494021F,1.7042075F,-1.0903292F,-14.323166F,0.46606588F,10.058811F,12.150202F,1.3948262F,5.159606F,6.429748F,2.7545235F,14.230817F,-2.9303515F,8.957117F,7.3596606F,-0.8918352F,-15.04139F,0.9927683F,2.53238F,-8.653784F,21.575333F,17.258318F,21.387463F,1.7311027F,-11.55669F,18.074442F,2.631297F,6.653756F,16.895315F,21.135891F,20.018875F,20.814024F,30.358944F,12.792533F,14.673788F,17.670214F,25.813175F,28.606934F,19.23104F,10.914106F,33.012486F,26.01884F,22.390306F,29.29514F,29.068916F,24.822206F,7.6301365F,26.254286F,35.336338F,15.912843F,18.412006F,16.610533F,8.288552F,23.092485F,15.002521F,14.970836F,15.630508F,18.504501F,23.437567F,20.762688F,6.2092233F,20.80361F,20.167572F,15.722877F,5.405726F,11.116547F,8.426892F,25.67068F,11.6183F,7.708066F,13.228849F,8.715532F,14.4158745F,20.490517F,15.432882F,16.33329F,5.1004095F,4.7217693F,1.6453841F,19.764336F,12.772915F,22.698298F,2.6332545F,5.4880085F,-4.301944F,-4.534242F,2.1534774F,0.043418884F,-15.0591955F,6.2869873F,0.006129265F,3.1988392F,5.010168F,7.74446F,2.710887F,3.8151383F,3.470711F,4.748297F,5.227952F,2.6704357F,21.095638F,19.579163F,9.043333F,1.8620325F,5.6112123F,21.048439F,7.083706F,9.582619F,10.202606F,17.12794F,9.143901F,2.4614098F,6.4414153F,6.667982F,0.8160859F,0.20716122F,7.8228507F,6.9827504F,2.6311755F,2.4132915F,-1.7217157F,-5.6027336F,2.2093062F,-0.3063653F,4.0022125F,-0.3875909F,-10.156935F,2.8099892F,-1.9612961F,-6.7882442F,-3.6990104F,-5.6783357F,7.1296506F,-7.9458413F,-12.685025F,-5.770091F,-5.653134F,-20.77007F,-1.0017741F,-2.9340732F,9.859559F,5.4588923F,-4.0000143F,-17.657927F,-3.7018137F,-11.3443575F,-10.493967F,5.9603853F,5.1914554F,0.03179574F,-5.134054F,2.0123165F,-3.8784728F,-6.605004F,-17.38744F,-10.622975F};
    float[] weights = new float[64*6];
    public TDMatrixEvaluatorUtil()
    {
        initWeights();
    }

    public void updateWeights(float[] delta)
    {
        for (int i = 0; i < weights.length; i++) {
            weights[i] += delta[i];
        }
    }

    private void initWeights()
    {
        float[] initWeights = {1,3,3,4,8,100};

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 64; j++) {
                weights[64*i + j] = initWeights[i];
            }
        }

        //weights = imported;
    }

    public float evaluateBoard(Board board)
    {
        Piece[][] pieces = board.getChessBoard();

        float value = 0;

        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces.length; j++) {
                Piece piece = pieces[i][j];
                if(piece == null){continue;}

                value += weights[getWeightIndex(piece)] * getFeatureValue(piece);
            }
        }

        return value;
    }

    public static int getWeightIndex(Piece piece)
    {
        int pieceIndex = (piece.getInt()-1) * 64;
        int row=-1;
        if(piece.getPlayer() == 1)
        {
            row = piece.getPos().getRow();
        }else
        {
            row = 7 - piece.getPos().getRow();
        }
        int positionIndex = row * 8 + piece.getPos().getColumn();
        return pieceIndex + positionIndex;
    }

    public static float getFeatureValue(Piece piece)
    {
        if (piece == null)
        {
            return 0;
        }
        else if(piece.getPlayer() == 1)
        {
            return 1;
        }else
        {
            return -1;
        }

    }


    public String printWeights()
    {
        String output = "";
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                for (int k = 0; k < 8; k++) {
                    output += weights[i * 64 + j* 8 + k];
                    if(k!= 7)
                        output += " ";
                }
                output += "\n";
            }
            output += "\n\n";
        }
        return output;
    }

    public String printAverage()
    {
        float[] values = new float[6];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                for (int k = 0; k < 8; k++) {
                    values[i] += weights[64*i + 8*j + k];
                }
            }
            values[i] /= 64;
        }
        return values[0] + ", " + values[1] + ", "+ values[2] + ", " + values[3] + ", " +values[4] + ", " + values[5];
    }
}
