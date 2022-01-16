package gameLogic.util.MiniMax;

import java.io.IOException;

public class FileWriter {

    public static void main(String[] args) throws IOException {

        Double[][][] arrays = new Double[6][8][8];
        double count = 0;

        for (int i = 0; i < 6; i++) {
            count = 0;
            for (int j = 0; j < 8; j++) {
                for (int k = 0; k < 8; k++) {
                    arrays[i][j][k] = count + i + 1;
                    count++;
                }
            }
        }

        java.io.FileWriter csvWriter = new java.io.FileWriter("res/newweights.csv");

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                for (int k = 0; k < 8; k++) {
                    csvWriter.append(arrays[i][j][k].toString());
                    csvWriter.append(",");
                }
            }
            csvWriter.append("\n");
        }

        csvWriter.flush();
        csvWriter.close();

    }
}
