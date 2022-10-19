package com.behavidence.android.sdk_internal.core.Auth;

import androidx.annotation.NonNull;

public class AuthConstants {
    public enum AGE_RANGE {
        Age13To17("13-17"),
        Age18To25("18-25"),
        Age26To35("26-35"),
        Age36To55("36-55"),
        Age56To64("56-64"),
        AgeGreaterThan64(">64");
        private final String ageRange;
        AGE_RANGE(@NonNull String ageRange){
            this.ageRange=ageRange;
        }

        @NonNull
        public String getAgeRange() {
            return ageRange;
        }
    }

    public enum DIAGNOSTICS {
        ANXIETY("anxiety"),
        ADHD("adhd"),
        DEPRESSION("depression"),
        DEMENTIA_ALZHEIMER("dementia/alzheimer"),
        OTHER("other"),
        NONE("none");
        private final String diagnostics;
        DIAGNOSTICS(@NonNull String diagnostics){
            this.diagnostics=diagnostics;
        }
        @NonNull
        public String getDiagnostics(){
            return diagnostics;
        }
    }



}
