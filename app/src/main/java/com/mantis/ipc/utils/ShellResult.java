package com.mantis.ipc.utils;

public class ShellResult {
        public String errorMsg;
        public int result;
        public String successMsg;

        public ShellResult(int result) {
            this.result = result;
        }

        public ShellResult(int result, String successMsg, String errorMsg) {
            this.result = result;
            this.successMsg = successMsg;
            this.errorMsg = errorMsg;
        }
    }