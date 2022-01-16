package gameLogic.util.MiniMax;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FileReader {

    public static void main(String[] args) throws IOException {
        String fileName= "res/newweights.csv";
        File file= new File(fileName);

        List<List<Float>> lines = new ArrayList<>();
        Scanner inputStream;

        try{
            inputStream = new Scanner(file);

            while(inputStream.hasNext()){
                String line= inputStream.next();
                String[] values = line.split(",");
                Float[] floats = Arrays.stream(values).map(Float::valueOf).toArray(Float[]::new);

                lines.add(Arrays.asList(floats));
            }

            inputStream.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        System.out.println(lines.get(4).get(14));



    }
}