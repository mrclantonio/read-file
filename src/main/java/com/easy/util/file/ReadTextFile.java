package com.easy.util.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadTextFile {
    
    public static void main(String args[]) {
        
        ReadTextFile rtf = new ReadTextFile();
        rtf.buildFileCSV();
        
//        String fileName = "D:\\opt\\files\\ldap_analise_contas.ldif";
//        Path path = Paths.get("D:\\opt\\files\\output.txt");
//        String cabecalho = "Display Name; Zimbra Mail Alias\n";
//        String email = "";
//        String name = "";
//        
//        int delimintador = 0;
//        
//        try { //(Scanner scanner = new Scanner(new File(fileName))) {
//            
//            BufferedReader br = new BufferedReader(new FileReader(fileName));
//            String line;
//            String broqueRegister = "# distributionList";
//            
//            BufferedWriter writer = Files.newBufferedWriter(path);
//            
//            writer.write(cabecalho);
//            while((line=br.readLine())!=null){
//                System.out.println(line);
//                
//                if (line.indexOf(broqueRegister) >= 0) {
//                    if (!email.isEmpty() && !name.isEmpty() ) {
//                        writer.write(name.trim() + ";" + email.trim() + "\n");
//                    }
//                    email = "";
//                    name = "";
//                }
//                    
//                
//                if (line.indexOf("zimbraMailAlias:") >= 0) {
//                    email = line.substring(line.indexOf(":") + 1, line.length());
//                }
//                
//                if (line.indexOf("displayName:") >= 0) {
//                    delimintador = line.indexOf(":") + (line.indexOf("displayName::") >= 0? 2 : 1);
//                    name = line.substring(delimintador, line.length());
//                }
//                
//            }
//            
//            writer.close();
//            br.close();
//            
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        
    }
    
    public void buildFileCSV() {
        
        List<String> columns = createListOfColumnsByNames("zimbraMailAlias,zimbraMailForwardingAddress");
        
        Map<String, StringBuffer> record = null;
        
        String fileName = "D:\\opt\\files\\list-distribuicao.txt";
        Path path = Paths.get("D:\\opt\\files\\output.csv");
        String cabecalho = "Zimbra Mail Alias; Zimbra Mail Forwarding Address\n";
        
        try { //(Scanner scanner = new Scanner(new File(fileName))) {
            
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            String broqueRegister = "# distributionList";
            
            BufferedWriter writer = Files.newBufferedWriter(path);
            String column = "";
            StringBuffer value = new StringBuffer("");
            int position = 0;
            
            record = new HashMap<>();
            writer.write(cabecalho);
            
            while((line=br.readLine())!=null){
                System.out.println(line);
                
                if (line.indexOf(broqueRegister) >= 0) {
                    if (record.size() == columns.size() ) {
                        StringBuffer lineOut = new StringBuffer();
                        int qtd = 0;
                        for (String key : columns) {
                            lineOut.append(record.get(key).toString().trim());
                            qtd++;
                            if (qtd < columns.size()) lineOut.append(";");
                        }
                        writer.write(lineOut.toString() + "\n");
                    }
                    record.clear();
                    record = new HashMap<>();
                }
                
                
                column = (line.indexOf(":") > 0? line.substring(0, line.indexOf(":")): "");
                
                if (columns.contains(column)) {
                    
                    value = record.get(column);
                    if (value == null) {
                        value = new StringBuffer("");
                        record.put(column, value);
                    } else {
                        value.append(", ");
                    }
                    position = line.indexOf(":") + (line.indexOf(column + "::") >= 0? 2 : 1);
                    value.append(line.substring(position, line.length()).trim());
                }
                
            }
            
            writer.close();
            br.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    
    private List<String> createListOfColumnsByNames(String names) {
        return Arrays.asList(names.split(","));
    }
    
}
