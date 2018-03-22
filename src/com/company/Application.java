package com.company;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Application implements PropertyHelper {
    private String[] args;
    private String path;

    public Application(String[] args, String path) {
        this.args = args;
        this.path = path;
    }

    @Override
    public String stringValue(String name) {
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            int equalsPos = 0;
            while (arg.charAt(equalsPos) != '=') {
                equalsPos++;
            }
            if (Objects.equals(arg.substring(0, equalsPos), name)) {
                return arg.substring(equalsPos + 1);
            }
        }

        Properties p = System.getProperties();
        Enumeration keys = p.keys();
        while (keys.hasMoreElements()) {
            String key = (String) (keys.nextElement());
            String value = (String) (p.get(key));
            if (Objects.equals(key, name)) {
                return value;
            }
        }

        Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
            if (Objects.equals(envName, name)) {
                return env.get(envName);
            }
        }

        return getArgumentValueFromFile(name);
    }

    @Override
    public Integer integerValue(String name) {
        return Integer.parseInt(stringValue(name));
    }

    @Override
    public Double doubleValue(String name) {
        return Double.parseDouble(stringValue(name));
    }

    private String getArgumentValueFromFile(String name) {
        if (name.length() == 0) return null;
        Scanner in = null;
        try {
            in = new Scanner(new FileReader(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while (in.hasNext()) {
            line = in.next();
            if (Objects.equals(line.substring(0, name.length()), name)) {
                in.close();
                return line.substring(name.length() + 1);
            }
        }
        in.close();
        return null;
    }
}
