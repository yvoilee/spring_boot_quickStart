package ai.leo.quickstart.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class MergeDataServiceTest {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    //    public static final String PATH_PREFIX = "/data/";
    public static final String PATH_PREFIX = "/Users/yvoilee/Workspaces/dataset/";
//    public static final String PATH_PREFIX = "C:\\Dev\\FitmeWorkSpaces\\temptxt\\";
    public static final int RADIX = 2;
    private static final String error_speech = "\"speech\": \"\"";

    public boolean mergeData(String newFileName, JSONArray fileList) {
        File newFileDir = new File(PATH_PREFIX + newFileName);
        File newFile = new File(PATH_PREFIX + newFileName + "/" + "training_data.txt");
        File newFileSlot = new File(PATH_PREFIX + newFileName + "/" + "training_data_slot.txt");
        File errorFile = new File(PATH_PREFIX + newFileName + "/" + "error_data.txt");
        if (!newFileDir.exists())
            newFileDir.mkdir();
        int fileCount = fileList.size();
        //数据加载入list
        List<JSONObject> list_json_object = new ArrayList<>();
        JSONObject jsonObject = null;
        File fileSource = null;
        BufferedReader bufferedReader = null;
        String line = null;
        try {
            errorFile.createNewFile();
            newFileSlot.createNewFile();
            BufferedWriter errorBufferWrite = new BufferedWriter(new FileWriter(errorFile));
            BufferedWriter slotBufferWrite = new BufferedWriter(new FileWriter(newFileSlot));
            for (int i = 0; i < fileCount; i++) {
                fileSource = new File(PATH_PREFIX + fileList.getString(i) + "/" + "training_data.txt");
                if (fileSource.exists()) {
                    bufferedReader = new BufferedReader(new FileReader(fileSource));
                    while ((line = bufferedReader.readLine()) != null) {
                        if (line.indexOf(error_speech) != -1) {
                            errorBufferWrite.write(line + "\n");
                            continue;
                        } else {
                            slotBufferWrite.write(line + "\n");
                        }
                        try {
                            jsonObject = JSONObject.parseObject(line);
                            list_json_object.add(jsonObject);
                        } catch (JSONException e) {
                            continue;
                        }
                    }
                    bufferedReader.close();
                }
            }
            errorBufferWrite.close();
            slotBufferWrite.close();

            Collections.shuffle(list_json_object);
            newFile.createNewFile();
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(newFile));
            JSONObject jsonLine = new JSONObject();
            JSONArray jsonArray = null;
            JSONArray jsonArray1 = null;
            int random_step = 0;
            int pos = 0;
            int listSize = list_json_object.size();
            for (int i = 0; i < listSize; ) {
                int random_storyLine = new Random().nextInt(6);
                int isMix = 0;
                int step = 0;
                try {
                    jsonArray = list_json_object.get(i).getJSONArray("actions");
                    for (int j = 0; j < random_storyLine && (i + j + 1 < listSize); j++) {
                        jsonArray.addAll(list_json_object.get(i + j + 1).getJSONArray("actions"));
                    }
                    int random_mix = new Random().nextInt(100);
                    if (random_mix % 10 == 5) {
                        isMix = 1;
                        int random_select = new Random().nextInt(listSize);
                        random_step = new Random().nextInt(3);
                        int int_step = 0;
                        jsonArray1 = list_json_object.get(random_select).getJSONArray("actions");
                        JSONArray jsonArray2 = new JSONArray();
                        for (int k = 0; k < jsonArray1.size(); k++) {
                            logger.info("-----------" + jsonArray1.getString(k));
                            if ("u".equals(jsonArray1.getJSONObject(k).getString("actor")))
                                int_step++;
                            if (int_step <= random_step)
                                jsonArray2.add(jsonArray1.getJSONObject(k));
                        }
                        //开始插入混合
                        pos = 0;
                        int jsonArraySize = jsonArray.size() / 4;
                        int random_pos;
                        if (jsonArraySize == 0)
                            random_pos = 1;
                        else
                            random_pos = new Random().nextInt(jsonArraySize) + 2;
                        for (int k = 0; k < jsonArray.size(); k++) {
                            if ("u".equals(jsonArray.getJSONObject(k).getString("actor")))
                                pos++;
                            if (pos == random_pos) {
                                pos = k;
                                break;
                            }
                        }
                        jsonArray.addAll(pos, jsonArray2);
                    }
                    if (isMix == 0)
                        jsonLine.put("dialog_template_id", "mixSet_joincount_" + (random_storyLine + 1) + "_isMix_" + isMix);
                    else
                        jsonLine.put("dialog_template_id", "mixSet_joincount_" + (random_storyLine + 1) + "_isMix_" + isMix + "_pos_" + pos + "_step_" + (random_step + 1));
                    jsonLine.put("actions", jsonArray);
                    bufferedWriter.write(jsonLine.toJSONString() + "\n");
                    i = i + random_storyLine + 1;
                } catch (Exception e) {
                    e.printStackTrace();
                    i++;
                    continue;
                }
            }
            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("----------error1--------------");
            return false;
        }

        return true;
    }

    public boolean processData(String filePath) {
        logger.debug("-------------processDate方法--------------------");
        File file = new File(PATH_PREFIX + filePath + "/" + "training_data.txt");
        File errorFile = new File(PATH_PREFIX + filePath + "/" + "error_data.txt");
        File fileTemp = new File(PATH_PREFIX + filePath + "/" + "training_data_tmp.txt");

        List<JSONObject> list_json_object = new ArrayList<>();
        JSONObject jsonObject = null;
        BufferedReader bufferedReader = null;
        String line = null;
        try {
            errorFile.createNewFile();
            BufferedWriter errorBufferWrite = new BufferedWriter(new FileWriter(errorFile));
            if (file.exists()) {
                bufferedReader = new BufferedReader(new FileReader(file));
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.indexOf(error_speech) != -1) {
                        errorBufferWrite.write(line + "\n");
                        continue;
                    }
                    try {
                        jsonObject = JSONObject.parseObject(line);
                        list_json_object.add(jsonObject);
                    } catch (JSONException e) {
                        errorBufferWrite.write(line + "\n");
                        continue;
                    }
                }
                bufferedReader.close();
            } else {
                return false;
            }
            errorBufferWrite.close();
            logger.info("------------shuffle------------------------");
            Collections.shuffle(list_json_object);
            fileTemp.createNewFile();
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileTemp));
            JSONObject jsonLine = new JSONObject();
            JSONArray jsonArray = null;
            JSONArray jsonArray1 = null;
            int random_step = 0;
            int pos = 0;
            int listSize = list_json_object.size();
            for (int i = 0; i < listSize; ) {
                int random_storyLine = new Random().nextInt(6);
                int isMix = 0;
                int step = 0;
                try {
                    jsonArray = list_json_object.get(i).getJSONArray("actions");
                    for (int j = 0; j < random_storyLine && (i + j + 1 < listSize); j++) {
                        jsonArray.addAll(list_json_object.get(i + j + 1).getJSONArray("actions"));
                    }
                    int random_mix = new Random().nextInt(100);
                    if (random_mix % 10 == 5) {
                        isMix = 1;
                        int random_select = new Random().nextInt(listSize);
                        random_step = new Random().nextInt(3);
                        int int_step = 0;
                        jsonArray1 = list_json_object.get(random_select).getJSONArray("actions");
                        JSONArray jsonArray2 = new JSONArray();
                        for (int k = 0; k < jsonArray1.size(); k++) {
//                            logger.info("-----------"+jsonArray1.getString(k));
                            if ("u".equals(jsonArray1.getJSONObject(k).getString("actor")))
                                int_step++;
                            if (int_step <= random_step + 1)
                                jsonArray2.add(jsonArray1.getJSONObject(k));
                        }
                        //开始插入混合
                        pos = 0;
                        int jsonArraySize = jsonArray.size() / 4;
                        int random_pos;
                        if (jsonArraySize == 0)
                            random_pos = 2;
                        else
                            random_pos = new Random().nextInt(jsonArraySize) + 2;
                        for (int k = 0; k < jsonArray.size(); k++) {
                            if ("u".equals(jsonArray.getJSONObject(k).getString("actor")))
                                pos++;
                            if (pos == random_pos) {
                                pos = k;
                                break;
                            }
                        }
                        jsonArray.addAll(pos, jsonArray2);
                    }
                    if (isMix == 0)
                        jsonLine.put("dialog_template_id", "mixSet_joincount_" + (random_storyLine + 1) + "_isMix_" + isMix);
                    else
                        jsonLine.put("dialog_template_id", "mixSet_joincount_" + (random_storyLine + 1) + "_isMix_" + isMix + "_pos_" + pos + "_step_" + (random_step + 1));
                    jsonLine.put("actions", jsonArray);
                    bufferedWriter.write(jsonLine.toJSONString() + "\n");
                    i = i + random_storyLine + 1;
                } catch (Exception e) {
                    e.printStackTrace();
                    i++;
                    continue;
                }
            }
            bufferedWriter.close();
            file.renameTo(new File(PATH_PREFIX + filePath + "/" + "training_data_slot.txt"));
            fileTemp.renameTo(new File(PATH_PREFIX + filePath + "/" + "training_data.txt"));

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    private boolean dataMethod(){
        return true;
    }

    public static void main(String[] args) {
        MergeDataServiceTest mergeDataServiceTest = new MergeDataServiceTest();
        JSONArray jsonArray = new JSONArray();
        jsonArray.add("set1");
        jsonArray.add("set2");
//        jsonArray.add("set6");
        System.out.println("-------begin---------------");
//        mergeDataServiceTest.mergeData("setok", jsonArray);
        mergeDataServiceTest.processData("set4");
        System.out.println("-------end---------------");

    }
}

