package com.mantis.ipc.utils;

import android.util.Log;



import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public class ShellUtils {
    private final static String SPACE = " ";
    public static final String CMD_EXIT = "exit\n";
    public static final String CMD_LINE_END = "\n";
    public static final String CMD_SH = "sh";
    public static final String CMD_SU = "su";

    public static boolean checkRootPermission() {
        return execCommand("echo root", true, false).result == 0;
    }

    public static ShellResult execCommand(String command, boolean isRoot) {
        return execCommand(new String[]{command}, isRoot, true);
    }

    public static ShellResult execCommand(List<String> commands, boolean isRoot) {
        String[] strArr;
        if (commands == null) {
            strArr = null;
        } else {
            strArr = (String[]) commands.toArray(new String[0]);
        }
        return execCommand(strArr, isRoot, true);
    }

    public static ShellResult execCommand(String[] commands, boolean isRoot) {
        return execCommand(commands, isRoot, true);
    }

    public static ShellResult execCommand(String command, boolean isRoot, boolean isNeedResultMsg) {
        return execCommand(new String[]{command}, isRoot, isNeedResultMsg);
    }

    public static ShellResult execCommand(List<String> commands, boolean isRoot, boolean isNeedResultMsg) {
        String[] strArr;
        if (commands == null) {
            strArr = null;
        } else {
            strArr = (String[]) commands.toArray(new String[0]);
        }
        return execCommand(strArr, isRoot, isNeedResultMsg);
    }

    public static ShellResult execCommand(String[] commands, boolean isRoot, boolean isNeedResultMsg) {
        int result = -1;
        if (commands == null || commands.length == 0) {
            return new ShellResult(-1, null, null);
        }
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(isRoot ? "su" : "sh");
            StreamGobbler errorGobbler = new StreamGobbler(process.getErrorStream(), "Error");
            StreamGobbler outputGobbler = new StreamGobbler(process.getInputStream(), "Output");
            errorGobbler.start();
            outputGobbler.start();

            DataOutputStream outputStream = new DataOutputStream(process.getOutputStream());
            try {
                for (String command : commands) {
                    if (command != null) {
                        outputStream.write(command.getBytes());
                        outputStream.writeBytes("\n");
                        outputStream.flush();
                    }
                }
                outputStream.writeBytes("exit\n");
                outputStream.flush();
                result = process.waitFor();
                if (result == 0) {
                } else {
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    outputStream.close();
                } catch (IOException e4222) {
                    e4222.printStackTrace();
                }

            }
        } catch (IOException e14) {
            e14.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }

        return new ShellResult(result, null, null);
    }

    public static void stopApp(String pkgName) {
        execCommand("am force-stop " + pkgName, true);
    }

    public static void frozenApp(String pkgName) {
        execCommand("pm disable " + pkgName, true);
    }

    public static void unFrozenApp(String pkgName) {
        execCommand("pm enable " + pkgName, true);
    }

    public static void unFrozenAllApp() {
        int i = 0;
        String successMsg = execCommand("pm list packages -d", true).successMsg;
        String[] packages;
        int length;
        if (StringUtil.isEmptyStr(successMsg)) {
            successMsg = execCommand("pm list packages -d", false).successMsg;
            if (!StringUtil.isEmptyStr(successMsg) && successMsg.contains("package:")) {
                packages = successMsg.substring(successMsg.indexOf("package:") + "package:".length()).split("package:");
                length = packages.length;
                while (i < length) {
                    unFrozenApp(packages[i]);
                    i++;
                }
            }
        } else if (successMsg.contains("package:")) {
            packages = successMsg.substring(successMsg.indexOf("package:") + "package:".length()).split("package:");
            length = packages.length;
            while (i < length) {
                unFrozenApp(packages[i]);
                i++;
            }
        }
    }

    public static void killPid(String processName) {
        String pid = getPid(processName);
        if (!"0".equals(pid)) {
            execCommand("kill " + pid, true);
        }
    }

    public static String getPid(String processName) {
        String successMsg = execCommand("ps | grep " + processName, true).successMsg;
        if (StringUtil.isEmptyStr(successMsg)) {
            return "0";
        }
        return Pattern.compile("\\s+").matcher(successMsg).replaceAll(SPACE).split(SPACE)[1];
    }

    public static void closeFireWalls() {
        execCommand("setenforce 0", true);
    }

    public static void chmod777(String path) {
        execCommand("chmod -R 777 " + path, true);
    }

    public static void chmod000(String path) {
        execCommand("chmod -R 000 " + path, true);
    }


//    public static boolean upgradeRootPermission() {
//        Throwable th;
//        Process process = null;
//        DataOutputStream os = null;
//        try {
//            String cmd = "touch /data/roottest.txt";
//            process = Runtime.getRuntime().exec("su");
//            DataOutputStream os2 = new DataOutputStream(process.getOutputStream());
//            try {
//                os2.writeBytes(cmd + "/n");
//                os2.writeBytes("exit/n");
//                os2.flush();
//                if (os2 != null) {
//                    try {
//                        os2.close();
//                    } catch (Exception e) {
//                    }
//                }
//                process.destroy();
//                os = os2;
//                return true;
//            } catch (Exception e2) {
//                os = os2;
//                if (os != null) {
//                    try {
//                        os.close();
//                    } catch (Exception e3) {
//                        return false;
//                    }
//                }
//                process.destroy();
//                return false;
//            } catch (Throwable th2) {
//                th = th2;
//                os = os2;
//                if (os != null) {
//                    try {
//                        os.close();
//                    } catch (Exception e4) {
//                        throw th;
//                    }
//                }
//                process.destroy();
//                throw th;
//            }
//        } catch (Exception e5) {
//            if (os != null) {
//                try {
//                    os.close();
//                } catch (Exception e32) {
//                    return false;
//                }
//            }
//            process.destroy();
//            return false;
//        } catch (Throwable th3) {
////            th = th3;
////            if (os != null) {
////                try {
////                    os.close();
////                } catch (Exception e42) {
////                    throw th;
////                }
////            }
////            process.destroy();
////            throw th;
//            return false;
//        }
//    }

    public static boolean upgradeRootPermission() {
        Process process = null;
        DataOutputStream os = null;
        try {
            Log.i("==---> rootTest", "try it");
            String cmd = "touch /data/roottest.txt";
            process = Runtime.getRuntime().exec("su"); //切换到root帐号
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(cmd + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {
            }
        }
        return true;
    }
}
