package main;

import states.MenuState;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SaveManager {
    private static final String[] path = {
            "saves/save1",
            "saves/save2",
            "saves/save3"
    };
    private static final String[] keys = {
            "stage",
            "c_stage",
            "deaths",
            "c_deaths",
            "time",
            "diff"
    };
    private static final Map<String, String> values = new HashMap<>();
    private static int index;
    private File file;
    private int stage;
    private int c_stage;
    private int deaths;
    private int c_deaths;
    private int time;
    private int diff;

    public SaveManager(int index) {
        SaveManager.index = index;
        init();
    }

    private void init() {
        // Create directory if it does not exist
        File dir = new File("saves");
        if(!dir.exists()) {
            dir.mkdir();
        }

        // Create file if it does not exist
        file = new File(path[index]);
        if(!file.exists()) createFile(file);

        // If file is empty or contains incorrect data, insert data template
        if(file.length() == 0) initFile();
        else {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                if(!br.readLine().split("=")[0].equals("stage")) initFile();
                else {
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (line.split("=")[1].equals("-1")) {
                            initFile();
                            break;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Set all variable values to data from file
        setValues();
    }

    private void createFile(File file) {
        boolean success = false;
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for(String key : keys) {
                values.put(key, "-1");
                bw.write(key + "=" + values.get(key));
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setValues() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            stage = Integer.parseInt(br.readLine().split("=")[1]);
            c_stage = Integer.parseInt(br.readLine().split("=")[1]);
            deaths = Integer.parseInt(br.readLine().split("=")[1]);
            c_deaths = Integer.parseInt(br.readLine().split("=")[1]);
            time = Integer.parseInt(br.readLine().split("=")[1]);
            diff = Integer.parseInt(br.readLine().split("=")[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            values.put("stage", String.valueOf(getStage()));
            values.put("c_stage", String.valueOf(getC_stage()));
            values.put("deaths", String.valueOf(getDeaths()));
            values.put("c_deaths", String.valueOf(getC_deaths()));
            values.put("time", String.valueOf(getTime() + MenuState.timeElapsed));
            values.put("diff", String.valueOf(getDiff()));
            for (String key : keys) {
                bw.write(key + "=" + values.get(key));
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getIndex() {
        return index;
    }

    public static void setIndex(int index) {
        SaveManager.index = index;
    }

    public int getStage() {
        return stage;
    }

    public int getC_stage() {
        return c_stage;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getC_deaths() {
        return c_deaths;
    }

    public int getTime() {
        return time;
    }

    public int getDiff() {
        return diff;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public void setC_stage(int c_stage) {
        this.c_stage = c_stage;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public void setC_deaths(int c_deaths) {
        this.c_deaths = c_deaths;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setDiff(int diff) {
        this.diff = diff;
    }
}
