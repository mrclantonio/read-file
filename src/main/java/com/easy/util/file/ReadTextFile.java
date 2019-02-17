package com.easy.util.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReadTextFile {
    
    public static void main(String args[]) {
        
        String fileName = "D:\\opt\\files\\ldap_analise_contas.ldif";
        Path path = Paths.get("D:\\opt\\files\\output.txt");
        String cabecalho = "Display Name; Zimbra Mail Alias\n";
        String email = "";
        String name = "";
        
        int delimintador = 0;
        
        try { //(Scanner scanner = new Scanner(new File(fileName))) {
            
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            
            BufferedWriter writer = Files.newBufferedWriter(path);
            
            writer.write(cabecalho);
            while((line=br.readLine())!=null){
//            while (scanner.hasNext()) {
//                String line = scanner.nextLine();
//                System.out.println(scanner.nextLine());
                System.out.println(line);
                
                if (line.indexOf("dn: uid") >= 0) {
                    if (!email.isEmpty() && !name.isEmpty() ) {
                        writer.write(name.trim() + ";" + email.trim() + "\n");
                    }
                    email = "";
                    name = "";
                }
                    
                
                if (line.indexOf("zimbraMailAlias:") >= 0) {
                    email = line.substring(line.indexOf(":") + 1, line.length());
                }
                
                if (line.indexOf("displayName:") >= 0) {
                    delimintador = line.indexOf(":") + (line.indexOf("displayName::") >= 0? 2 : 1);
                    name = line.substring(delimintador, line.length());
                }
                
            }
            
            writer.close();
            br.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
}
