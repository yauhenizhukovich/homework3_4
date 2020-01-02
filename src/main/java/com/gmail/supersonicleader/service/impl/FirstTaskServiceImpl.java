package com.gmail.supersonicleader.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

import com.gmail.supersonicleader.service.FirstTaskService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FirstTaskServiceImpl implements FirstTaskService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static final String FILE_WITH_COMMANDS_TO_READ = "src/main/resources/sql_commands_first_task.txt";

    @Override
    public void runFirstTask() {
        File file = new File(FILE_WITH_COMMANDS_TO_READ);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
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
