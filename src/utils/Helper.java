package utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

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
}
