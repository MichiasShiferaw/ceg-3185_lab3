import java.net.*;

import java.io.*;


    /**
     * @class CEG 3185 Lab 3
     * @author Michias Shiferaw
     * @since June 22 2023
     * @version 2.1
     * @param args
     */

public class PacketReceiver extends Thread {


    private ServerSocket ss = null;
    private Socket s = null;
    private DataInputStream in =null;


    public PacketReceiver(int port){
        try{
            ss = new ServerSocket(port);
            System.out.println("Server is up and running");

            s = ss.accept();
            System.out.println("***__Client Connected __***");


            in = new DataInputStream(new BufferedInputStream(s.getInputStream()));

            String data = in.readUTF();

            System.out.println("Datagram : "+ data);
            data = removePad(data);




            System.out.println("Datagram without padding: " + data);

            System.out.println("__________________");

            decodeFunc(data);

        } catch (Exception err){
            System.out.println(err);
        }
    }

    private static String removePad(String str){
        String[] strArr = str.split(" ");
        int len = Integer.parseInt(strArr[1],16);

        return (str.substring(0,(len*2)+(len/2)));
        
    }

    public static boolean ChecksumCalc(String header, String len, String idField, String flags, String tcp, String csum, String ipS, String ipD){

        String p1 = ipS.substring(0, 4);
        String p2 = ipS.substring(5);

        String p3 = ipD.substring(0, 4);
        String p4 = ipD.substring(5);

        int headerDecode = Integer.parseInt(header,16);
        int lengthDecode = Integer.parseInt(len,16);
        int idFieldDecode = Integer.parseInt(idField,16);
        int flagsDecode = Integer.parseInt(flags,16);
        int tcpDecode= Integer.parseInt(tcp,16);
        int csumDecode =Integer.parseInt(csum,16);
        int p1Decode = Integer.parseInt(p1,16);
        int p2Decode =Integer.parseInt(p2,16);
        int p3Decode = Integer.parseInt(p3, 16);
        int p4Decode = Integer.parseInt(p4,16);

        int sum = headerDecode+lengthDecode+idFieldDecode+flagsDecode+tcpDecode+csumDecode+p1Decode+p2Decode+p3Decode+p4Decode;

        String sumHex = Integer.toHexString(sum);


        if (sumHex.length()>4){
            String cry= sumHex.substring(0,1);
            sumHex = sumHex.substring(1);
            int cryD = Integer.parseInt(cry,16);
            int sumHexD = Integer.parseInt(sumHex,16);
            sum=cryD+sumHexD;
            sumHex=Integer.toHexString(sum);
        }

        if (sumHex.equals("ffff")){
            return true;
        }
        return false;

    }

    public static String getAddy(String addy){
        int one = Integer.parseInt(addy.substring(0,2),16);
        int sec =  Integer.parseInt(addy.substring(2,4),16);
        int three =  Integer.parseInt(addy.substring(5,7),16);
        int four =  Integer.parseInt(addy.substring(7),16);

        return (one+"."+sec+"."+three+"."+four);
    
    
    }


    public static String convertToText(String str){
        StringBuilder sb = new StringBuilder();

        for (int i=0; i<str.length();i+=2){
            String c = str.substring(i, i+2);
            sb.append((char) Integer.parseInt(c,16));
        }


        return sb.toString();
    }

    public static void decodeFunc(String input){

        String[] inputArr = input.split(" ");

        String header = inputArr[0];
        String len = inputArr[1];
        String idField = inputArr[2];
        String flags= inputArr[3];
        String tcp = inputArr[4];
        String csum = inputArr[5];
        String ipS = inputArr[6]+" "+inputArr[7];
        String ipD = inputArr[8]+" "+inputArr[9];


        String msg="";

        for (int i=10; i<inputArr.length;i++){
            msg+=inputArr[i];
            
        }


        boolean bool = ChecksumCalc(header, len, idField, flags, tcp, csum, ipS, ipD);

        if (!bool){
            System.out.println("The verification of the checksum demonstrates that the packet received is corrupted. Packet discarded!");
        } else{
            String ipSource = getAddy(ipS);


            int lenPacket = Integer.parseInt(len.substring(2,4),16);

            int payL = Integer.parseInt(len.substring(0,2),16)+20;

            String decMsg = convertToText(msg);

            System.out.println("Receives the data stream and prints to the screen the data received with the following message:");
            System.out.println("The data received from "+ipSource+" is "+decMsg);
            System.out.println("The data has "+(8*payL)+" bites or "+payL+" bytes. Total length of the packet is "+lenPacket+" bytes.");
            System.out.println("The verification of the checksum demonstrates that the packet received is correct.");


        }
    }


    public static void main(String[] args) throws IOException {
        PacketReceiver receiver =new PacketReceiver(4999);
    }
    
}
