package com.myretail.exception;

public enum Error {
        SERVICE_UNAVAILABLE("UNAVAILABLE", "Service Unavailable"),
        NOT_FOUND("NOT_FOUND", "Not found"),
        SERVER_ERROR("SERVER_ERROR","Unable to process");

        private final String code;
        private final String description;

        private Error(String code, String description) {
            this.code = code;
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public String getCode() {
            return code;
        }

        public static String getMessage(String code){
            for (Error e :Error.values()){
                if(code.equalsIgnoreCase(e.getCode()))
                    return e.getDescription();
            }
            return "Generic error";
        }
}
