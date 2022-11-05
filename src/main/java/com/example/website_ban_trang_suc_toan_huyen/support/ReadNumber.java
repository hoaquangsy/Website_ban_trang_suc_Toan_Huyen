package com.example.website_ban_trang_suc_toan_huyen.support;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class ReadNumber {
    private static final String NUM_0 = "không";
    private static final String NUM_1 = "một";
    private static final String NUM_2 = "hai";
    private static final String NUM_3 = "ba";
    private static final String NUM_4 = "bốn";
    private static final String NUM_5 = "năm";
    private static final String NUM_6 = "sáu";
    private static final String NUM_7 = "bảy";
    private static final String NUM_8 = "tám";
    private static final String NUM_9 = "chín";
    private static final String NUM_10 = "mười";

    private static final String OTHER_NUM_0 = "linh";
    private static final String OTHER_NUM_1 = "mốt";
    private static final String OTHER_NUM_5 = "năm";
    private static final String POWER_1 = "mươi";
    private static final String POWER_2 = "trăm";
    private static final String POWER_3 = "nghìn";
    private static final String POWER_6 = "triệu";
    private static final String POWER_9 = "tỷ";

    private static final String[] number = {NUM_0, NUM_1, NUM_2, NUM_3, NUM_4, NUM_5, NUM_6,  NUM_7, NUM_8, NUM_9};

    private static String read(Long num){
        return StringUtils.capitalize(num == null ? "" : String.join("",readNum(num.toString())));

    }

    private static ArrayList<String> readNum( String num){
        ArrayList<String> result =new ArrayList<>();
        ArrayList<String> listNum =split(num,3);

        while (listNum.size() != 0 ){
            switch (listNum.size()%3){
                case 1:
                    result.addAll(read3(listNum.get(0)));
                    break;
                case 2:
                    ArrayList<String> thousand = read3(listNum.get(0));
                    if(!thousand.isEmpty()){
                        result.addAll(thousand);
                        result.add(POWER_3);
                    }
                    break;
                case 0 :
                    ArrayList<String> million = read3(listNum.get(0));
                    if(!million.isEmpty()){
                        result.addAll(million);
                        result.add(POWER_6);
                    }
                    break;
            }
            if(listNum.size() == (listNum.size()/3) *3 +1 && listNum.size() !=1)
                result.add(POWER_9);
            listNum.remove(0);
        }
        return result;
    }

    private static ArrayList<String> read3(String a ){
        ArrayList<String> result =new ArrayList<>();
        int num = Integer.parseInt(a);
        if(num == 0)
            return result;

        int hundreds =num <100 ? -1 : Integer.parseInt(a.substring(0,1));
        int tens = num <10 ? -1 :Integer.parseInt(a.substring(1,2));
        int units = Integer.parseInt(a.substring(2,3));

        if(hundreds!=-1){
            result.add(number[hundreds]);
            result.add(POWER_2);
        }

        switch (tens){
            case -1:
                break;
            case 1:
                result.add(NUM_10);
                break;
            case 0:
                if(units != 0)
                    result.add(NUM_0);
                break;
            default:result.add(number[tens]);
                result.add(POWER_1);
                break;
        }

        switch (units){
            case 1:
                if((tens!=0)&&(tens!=1)&&(tens!=-1))
                    result.add(OTHER_NUM_1);
                else result.add(number[units]);
                break;
            case 5:
                if((tens!=0)&&(tens!=-1))
                    result.add(OTHER_NUM_5);
                else result.add(number[units]);
                break;
            case 0:
                if(result.isEmpty()) result.add(number[units]);
                break;
            default:result.add(number[tens]);
                result.add(POWER_1);
                break;
        }
        return result;
    }

    private static ArrayList<String> split (String str, int chunkSize){
        int du =str.length() % chunkSize;
        if(du!=0){
            StringBuilder stringBuilder = new StringBuilder(str);
            for (int i = 0; i<(chunkSize-du); i++) stringBuilder.insert(0,0);
            str =stringBuilder.toString();
        }
        return splitStringEvery(str, chunkSize);
    }

    private static ArrayList<String> splitStringEvery (String s, int interval){
        int arrayLength =(int) Math.ceil(((s.length()/(double) interval)));
        String[] result = new String[arrayLength];
        int j =0  ;
        int lastIndex = result.length -1;
            for (int i = 0; i<lastIndex; i++){
           result[i] =s.substring(j,j+interval);
           j+= interval;
        }
         result[lastIndex] =s.substring(j);
            return new ArrayList<>(Arrays.asList(result));
    }

}
