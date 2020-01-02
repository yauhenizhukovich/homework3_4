package com.gmail.supersonicleader;

import com.gmail.supersonicleader.service.FirstTaskService;
import com.gmail.supersonicleader.service.SecondTaskService;
import com.gmail.supersonicleader.service.impl.FirstTaskServiceImpl;
import com.gmail.supersonicleader.service.impl.SecondTaskServiceImpl;

public class App {

    public static void main(String[] args) {
        FirstTaskService firstTaskService = new FirstTaskServiceImpl();
        firstTaskService.runFirstTask();

        SecondTaskService secondTaskService = new SecondTaskServiceImpl();
        secondTaskService.runSecondTask();
    }

}
