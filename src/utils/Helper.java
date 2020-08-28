package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class Helper {

    public  static  void outPutAll(ResultSet res){

        try {
            ResultSetMetaData rsmd =res.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for(int i=1;i<=columnCount;i++){
               System.out.print( rsmd.getColumnName(i)+ "\t");
            }
            System.out.println("\n");
            while (res.next()){
                for(int i=1;i<=columnCount;i++){
                    System.out.print(res.getString(i) + "\t");
                }
                System.out.println();
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }
    public  static  boolean search(String first_value ,String second_value){
             return  first_value.toLowerCase().contains(second_value.toLowerCase());

    }
    public  static  String getToken(){
        Path filePath = Paths.get("src/token.txt");
        String res = null;
        try {
            res = Files.readAllLines(filePath).stream().collect(Collectors.joining());
        } catch (IOException e) {
            return  "";
        }
        return  res;
    }
    public  static  void  deleteToken(){
        Path filePath = Paths.get("src/token.txt");
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
