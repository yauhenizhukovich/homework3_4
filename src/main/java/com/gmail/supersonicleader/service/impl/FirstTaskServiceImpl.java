package com.gmail.supersonicleader.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.gmail.supersonicleader.service.FirstTaskService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FirstTaskServiceImpl implements FirstTaskService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static final String FILE_WITH_COMMANDS_TO_READ = "sql_commands_first_task.txt";

    @Override
    public void runFirstTask() {
        try (
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream(FILE_WITH_COMMANDS_TO_READ);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
        ) {
            String line = bufferedReader.readLine();
            while (line != null) {
                logger.info(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

}
