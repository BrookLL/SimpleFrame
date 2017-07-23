package com.riverlet.lib.util;

import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.riverlet.lib.BuildConfig;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by
 * Author:liujian
 * DATE:17/2/10.
 * Time:上午10:46
 */
public class LogUtil {

    private LogUtil() {
    }

    private static boolean logSwitch = true;
    private static boolean log2FileSwitch = false;
    private static char logFilter = 'v';
    private static String tag = "TAG";
    private static String fullPath = null;
    private static String dir = null;
    private static int stackIndex = 0;
    private static ExecutorService cachedThreadPool;
    private static Handler handler;

    public static void init(boolean logSwitch) {
        init(logSwitch, true, 'v', "tag", null);
    }

    /**
     * 初始化函数
     *
     * @param logSwitch      日志总开关
     * @param log2FileSwitch 日志写入文件开关，设为true需添加权限 {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}
     * @param logFilter      输入日志类型有{@code v, d, i, w, e}<br>v代表输出所有信息，w则只输出警告...
     * @param tag            标签
     */
    public static void init(boolean logSwitch, boolean log2FileSwitch, char logFilter, String tag, String logFilepath) {
        LogUtil.logSwitch = logSwitch;
        LogUtil.log2FileSwitch = log2FileSwitch;
        LogUtil.logFilter = logFilter;
        LogUtil.tag = tag;
        if (TextUtils.isEmpty(logFilepath)) {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                dir = Environment.getExternalStorageDirectory().getPath() + File.separator + BuildConfig.APPLICATION_ID + "/log/";
            } else {
                LogUtil.log2FileSwitch = false;
            }
        } else {
            dir = logFilepath;
        }
        if (cachedThreadPool == null) {
            cachedThreadPool = Executors.newSingleThreadExecutor();
        }
        initFile();
    }

    private static void initFile() {
        Date now = new Date();
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(now);
        String fileName = new SimpleDateFormat("yyyy-MM-dd-HH", Locale.getDefault()).format(now);
        fullPath = dir + fileName + ".txt";
        File file = null;
        if (logSwitch && log2FileSwitch) {
            File path = new File(dir);
            if (!path.exists()) {
                path.mkdirs();
            }
            file = new File(fullPath);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (file != null && file.exists()) {
                String msg =
                        "\n时间：" + date + "\n" +
                                "设备：" + Build.MODEL + "\n" +
                                "App版本号：" + BuildConfig.VERSION_NAME + "\n" +
                                "OS版本号：" + Build.VERSION.SDK_INT + "\n" +
                                "编译时间：" + BuildConfig.buildTime + "\n";
                LogUtil.v("LogFileInit", msg);
            }
        }
    }

    /**
     * Verbose日志
     *
     * @param msg 消息
     */
    public static void v(Object msg) {
        log(tag, msg.toString(), null, 'v');
    }

    /**
     * Verbose日志
     *
     * @param tag 标签
     * @param msg 消息
     */
    public static void v(String tag, Object msg) {
        log(tag, msg.toString(), null, 'v');
    }

    /**
     * Verbose日志
     *
     * @param tag 标签
     * @param msg 消息
     * @param tr  异常
     */
    public static void v(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'v');
    }

    /**
     * Debug日志
     *
     * @param msg 消息
     */
    public static void d(Object msg) {
        log(tag, msg.toString(), null, 'd');
    }

    /**
     * Debug日志
     *
     * @param tag 标签
     * @param msg 消息
     */
    public static void d(String tag, Object msg) {
        log(tag, msg.toString(), null, 'd');
    }

    /**
     * Debug日志
     *
     * @param tag 标签
     * @param msg 消息
     * @param tr  异常
     */
    public static void d(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'd');
    }

    /**
     * Info日志
     *
     * @param msg 消息
     */
    public static void i(Object msg) {
        log(tag, msg.toString(), null, 'i');
    }

    /**
     * Info日志
     *
     * @param tag 标签
     * @param msg 消息
     */
    public static void i(String tag, Object msg) {
        log(tag, msg.toString(), null, 'i');
    }

    /**
     * Info日志
     *
     * @param tag 标签
     * @param msg 消息
     * @param tr  异常
     */
    public static void i(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'i');
    }

    /**
     * Warn日志
     *
     * @param msg 消息
     */
    public static void w(Object msg) {
        log(tag, msg.toString(), null, 'w');
    }

    /**
     * Warn日志
     *
     * @param tag 标签
     * @param msg 消息
     */
    public static void w(String tag, Object msg) {
        log(tag, msg.toString(), null, 'w');
    }

    /**
     * Warn日志
     *
     * @param tag 标签
     * @param msg 消息
     * @param tr  异常
     */
    public static void w(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'w');
    }

    /**
     * Error日志
     *
     * @param msg 消息
     */
    public static void e(Object msg) {
        log(tag, msg.toString(), null, 'e');
    }

    /**
     * Error日志
     *
     * @param tag 标签
     * @param msg 消息
     */
    public static void e(String tag, Object msg) {
        log(tag, msg.toString(), null, 'e');
    }

    /**
     * Error日志
     *
     * @param tag 标签
     * @param msg 消息
     * @param tr  异常
     */
    public static void e(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'e');
    }

    /**
     * 根据tag, msg和等级，输出日志
     *
     * @param tag  标签
     * @param msg  消息
     * @param tr   异常
     * @param type 日志类型
     */
    private static void log(String tag, String msg, Throwable tr, char type) {
        if (msg == null || msg.isEmpty()) {
            return;
        }
        if (logSwitch) {
            if ('e' == type && ('e' == logFilter || 'v' == logFilter)) {
                printLog(generateTag(tag), msg, tr, 'e');
            } else if ('w' == type && ('w' == logFilter || 'v' == logFilter)) {
                printLog(generateTag(tag), msg, tr, 'w');
            } else if ('d' == type && ('d' == logFilter || 'v' == logFilter)) {
                printLog(generateTag(tag), msg, tr, 'd');
            } else if ('i' == type && ('d' == logFilter || 'v' == logFilter)) {
                printLog(generateTag(tag), msg, tr, 'i');
            } else if ('v' == type) {
                printLog(generateTag(tag), msg, tr, 'v');
            }
            if (log2FileSwitch) {
                log2File(type, generateTag(tag), msg + '\n' + Log.getStackTraceString(tr));
            }
        }
    }

    /**
     * 根据tag, msg和等级，输出日志
     *
     * @param tag  标签
     * @param msg  消息
     * @param tr   异常
     * @param type 日志类型
     */
    private static void printLog(final String tag, final String msg, Throwable tr, char type) {
        final int maxLen = 4000;
        for (int i = 0, len = msg.length(); i * maxLen < len; ++i) {
            String subMsg = msg.substring(i * maxLen, (i + 1) * maxLen < len ? (i + 1) * maxLen : len);
            switch (type) {
                case 'e':
                    Log.e(tag, subMsg, tr);
                    break;
                case 'w':
                    Log.w(tag, subMsg, tr);
                    break;
                case 'd':
                    Log.d(tag, subMsg, tr);
                    break;
                case 'i':
                    Log.i(tag, subMsg, tr);
                    break;
                case 'v':
                    Log.v(tag, subMsg, tr);
                    break;
            }
        }
    }

    /**
     * 打开日志文件并写入日志
     *
     * @param type 日志类型
     * @param tag  标签
     * @param msg  信息
     **/
    private synchronized static void log2File(final char type, final String tag, final String msg) {
        File file = new File(fullPath);
        // 如果存在，是文件则返回true，是目录则返回false
        if (!file.exists()) {
            initFile();
        }
        String sessionId = "sessionId";
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault()).format(new Date());
        final String dateLogContent = time + " [" + sessionId + "] [" + type + "] " + tag + " " + msg + '\n';
        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                writeToFile(fullPath, dateLogContent);
            }
        });
    }

    /**
     * 写入文件
     *
     * @param fullPath
     * @param dateLogContent
     */
    private static synchronized void writeToFile(String fullPath, String dateLogContent) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(fullPath, true));
            bw.write(dateLogContent);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 产生tag
     *
     * @return tag
     */
    private static String generateTag(String tag) {
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        if (stackIndex == 0) {
            while (stackIndex < stacks.length && !stacks[stackIndex].getMethodName().equals("generateTag")) {
                ++stackIndex;
            }
            stackIndex += 3;
        }
        if (stackIndex >= stacks.length) {
            return tag;
        }
        StackTraceElement caller = stacks[stackIndex];
        String format = "[" + tag + "] (%s:%d) %s ";
        String fileName = caller.getFileName();
        String methodName = caller.getMethodName();
        int lineNumber = caller.getLineNumber();
        return String.format(format, fileName, lineNumber, methodName);
    }

    public static void setLogSwitch(boolean logSwitch) {
        LogUtil.logSwitch = logSwitch;
    }

    /**
     * 获取日志文件列表
     *
     * @return
     */
    public static String[] getLogFileList() {
        File file = new File(dir);
        return file.list();
    }

    private static StringBuilder readLog() {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(fullPath));
            String buff;
            while ((buff = bufferedReader.readLine()) != null) {
                stringBuilder.append(buff);
                stringBuilder.append("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuilder;
    }

    public static void getLog(final OnReadLogListener logListener) {
        if (handler==null){
            handler=new Handler();
        }
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final StringBuilder stringBuilder = readLog();
                if (stringBuilder != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (logListener != null) {
                                logListener.onReadSuccess(stringBuilder);
                            }
                        }
                    });

                } else {
                    logListener.onReadFailure();
                }
            }
        };
        cachedThreadPool.execute(runnable);
    }

    public static void uninit(){
        if (cachedThreadPool != null) {
            cachedThreadPool.shutdown();
        }
    }
    public interface OnReadLogListener {
        void onReadSuccess(StringBuilder log);

        void onReadFailure();
    }
}