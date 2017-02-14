package com.real.simhotel.events;

import android.widget.Switch;

/**
 * Created by liudan on 2017/1/17.
 */
public class EventCode {

    //实训状态

    public class TraingingCode {
        public static final int TRAINING_BUILDING = 10;

        public static final int TRAINING_BUILDED = 11;

        public static final int TRAINING_HIRE_START = 20;

        public static final int TRAINING_HIRE_PUSH_APPLICANT = 27;

        public static final int TRAINING_HIRE_PUSH_RESULT = 28;

        public static final int TRAINING_HIRE_FINISHED = 29;
    }

    //小组状态
    public class GroupCode{
        public static final int GROUP_CEO_HOTEL_INITING = 112;

        public static final int GROUP_CEO_HOTEL_INITTED = 113;

        public static final int GROUP_CEO_HIRE_ING = 121;

        public static final int GROUP_CEO_HIRE_CONFIRM = 122;

        public static final int GROUP_CEO_HIRE_REJECT = 123;

        public static final int GROUP_HR_HIRE_BIDDING = 124;

        public static final int GROUP_HR_HIRE_RESULT_SHOW = 125;

        public static final int GROUP_HR_HIRE_FINISH = 126;


        public static final int GROUP_CEO_LOAN_ING = 131;

        public static final int GROUP_CEO_LOAN_CONFIRM = 132;

        public static final int GROUP_CEO_LOAN_REJECT = 133;
    }

//    public static final int TRAINING_HOTEL_INITED = 12;
//
//    public static final int TRAINING_HOTEL_CEO_INITING = 13;
//
//    public static final int TEACHER_START_HIRE = 20;
//
//    public static final int CEO_THINKING_HIRE = 21;
//
//    public static final int CEO_CONFIRM_HIRE = 22;
//
//    public static final int CEO_REJECT_HIRE = 23;
//
//    public static final int TRAINING_BID_START = 24;
//
//    public static final int TRAINING_BID_ING = 25;
//
//    public static final int TRAINING_BID_FINISH = 26;
//
//    public static final int TRAINING_BID_RESULT = 27;
//
//    public static final int TRAIINIG_BID_RESULT_SHOWING = 28;
//
//    public static final int TRAINING_BID_OVER = 29;
//
//
//    public static final int CEO_CONFIRM_LOAN = 31;
//
//    public static final int CEO_REJECT_LOAN = 32;
}
