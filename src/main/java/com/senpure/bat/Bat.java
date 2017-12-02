package com.senpure.bat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;

/**
 * Created by 罗中正 on 2017/12/1 0001.
 */
public class Bat {

    public static  void  runCommand(String command)
    {
        BufferedReader br = null;

        try {
            Process p = Runtime.getRuntime().exec(command);

            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            // StringBuilder sb = new StringBuilder();
            int count = 0;


            while ((line = br.readLine()) != null || br.ready()) {

                line = URLDecoder.decode(line, "utf-8");


                System.out.println(line);
            }

            // analyzeClass(clazzs);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public static void main(String[] args) {

        StringBuilder sb = new StringBuilder();

        String command="";
        System.out.println("command="+command);


    }
}
