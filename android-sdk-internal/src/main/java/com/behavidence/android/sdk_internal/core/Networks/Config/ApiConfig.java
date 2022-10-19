package com.behavidence.android.sdk_internal.core.Networks.Config;

/**
 * ApiConfig for setting network call configurations
 */
public class ApiConfig {
    private int noOfRetries;

    /**
     *
     * @param noOfRetries on network call
     */
    public ApiConfig(int noOfRetries) {
        setNoOfRetries(noOfRetries);
    }

    /**
     *
     * @return defalut Api Config with only 2 retries
     */
    public static  ApiConfig getDefault(){
        return new ApiConfig(2);
    }

    /**
     *
     * @param noOfRetries to the server on failure of network call.
     *                    should be less than 6
     */
    public void setNoOfRetries(int noOfRetries) {
        if(noOfRetries>-1&&noOfRetries<6)
        this.noOfRetries = noOfRetries;
    }


    /**
     *
     * @return no of retries on the network call when call fails
     */
    public int getNoOfRetries() {
        return noOfRetries;
    }


}
