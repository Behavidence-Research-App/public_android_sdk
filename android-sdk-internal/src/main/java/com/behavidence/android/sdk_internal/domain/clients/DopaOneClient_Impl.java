package com.behavidence.android.sdk_internal.domain.clients;

import android.content.Context;
import android.util.Log;

import com.behavidence.android.sdk_internal.data.interfaces.DopaOneService;
import com.behavidence.android.sdk_internal.data.room_model.DopaOne.MedicationDataEntity;
import com.behavidence.android.sdk_internal.data.room_model.DopaOne.RawSensorDataEntity;
import com.behavidence.android.sdk_internal.data.room_model.DopaOne.SensorDataEntity;
import com.behavidence.android.sdk_internal.data.room_repository.RawSensorDataDao;
import com.behavidence.android.sdk_internal.domain.helpers.SensorManagerHelperClass;
import com.behavidence.android.sdk_internal.domain.interfaces.DopaOneClient;
import com.behavidence.android.sdk_internal.domain.model.DopaOneEvents.SensorDataResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DopaOneClient_Impl extends ClientParent implements DopaOneClient {

    private static final String DOPA_ONE_FILE = "dopa_one_file";
    private static final String LAST_RAW_SENSOR_EVENT_TIME = "last_raw_sensor_event_time";
    private static final int CHUNK_SIZE = 700;

    private DopaOneService _service;

    public DopaOneClient_Impl(Context context) {

        super(context);
        _service = services.dopaOne();
    }

    @Override
    public void initialize(Context context) {
        try {
            new SensorManagerHelperClass(
                    context,
                    (gyroSamples) -> {
                        processSamples(gyroSamples, "Gyro");
                        return null;
                    }
                    ,
                    (accelerometerSample) -> {
                        processSamples(accelerometerSample, "Accelerometer");
                        return null;
                    }
            );
        } catch (Exception e) {
            Log.e("Exception in DopeOneClient ", e.getMessage());
        }
    }

    /**
     * Processes the collected sensor samples, passes them to the view model for further processing,
     * and inserts the results into the database.
     *
     * @param samples    The list of sensor data samples.
     * @param sensorType The type of sensor data being processed (e.g., "Gyro" or "Accelerometer").
     */
    private void processSamples(List<SensorDataResponse> samples, String sensorType) {

        try {
            for(SensorDataResponse sample: samples){
                insertRawSensorData(
                        new RawSensorDataEntity(
                                sample.getTimestamp(),
                                sample.getSensorType(),
                                sample.getX(),
                                sample.getY(),
                                sample.getZ()
                        )
                );
            }
        } catch (Exception e) {
            Log.e("Room Data Insertion Exception in DopaOneClient ", e.toString());
        }

    }

    public void fetchRawEventsToSendAndPost(Context context) {

        long lastTime = getLastRawSensorEventsTime(context);
        List<RawSensorDataEntity> events = loadRawEventsAfterTime(lastTime);

        if (!events.isEmpty()) {
             List<List<RawSensorDataEntity>> chunkedEvents = chunked(events, CHUNK_SIZE);

            for (List<RawSensorDataEntity> chunk: chunkedEvents) {
                _service.postRawSensorData(chunk);
                setLastRawSensorEventsTime(context, chunk.get(chunk.size()-1).getTimestamp());
            }


        }

    }

    public void insertSensorData(SensorDataEntity data){
        database.sensorDataDao().insertSensorData(data);
    }

    public List<SensorDataEntity> getAllSensorData(){
        return database.sensorDataDao().getAllSensorData();
    }

    public List<SensorDataEntity> getSensorDataForDateRange(Long startTimeMillis, Long endTimeMillis){
        return database.sensorDataDao().getSensorDataForDateRange(startTimeMillis, endTimeMillis);
    }

    public List<SensorDataEntity> loadEventsAfterTime(Long lastTime){
        return database.sensorDataDao().loadEventsAfterTime(lastTime);
    }

    public void insertRawSensorData(RawSensorDataEntity data){
        database.rawSensorDataDao().insertRawSensorData(data);
    }

    public List<RawSensorDataEntity> getAllRawSensorData(){
        return database.rawSensorDataDao().getAllRawSensorData();
    }

    public List<RawSensorDataEntity> getRawSensorDataForDateRange(Long startTimeMillis, Long endTimeMillis){
        return database.rawSensorDataDao().getSensorDataForDateRange(startTimeMillis, endTimeMillis);
    }

    public List<RawSensorDataEntity> loadRawEventsAfterTime(Long lastTime){
        return database.rawSensorDataDao().loadEventsAfterTime(lastTime);
    }

    public void insertMedicationData(MedicationDataEntity data) {
        database.medicationDataDao().insertMedicationData(data);
    }

    public List<MedicationDataEntity> getAllMedicationData(){
        return database.medicationDataDao().getAllMedicationData();
    }

    private long getLastRawSensorEventsTime(Context context){
        return context.getSharedPreferences(
                DOPA_ONE_FILE,
                Context.MODE_PRIVATE
        ).getLong(LAST_RAW_SENSOR_EVENT_TIME, ((new Date()).getTime() - (7 * 24 * 60 * 60 * 1000)));
    }

    private void setLastRawSensorEventsTime(Context context, long timestamp){
        context.getSharedPreferences(DOPA_ONE_FILE, Context.MODE_PRIVATE).edit()
                .putLong(LAST_RAW_SENSOR_EVENT_TIME, timestamp)
                .apply();
    }

    public static <T> List<List<T>> chunked(List<T> list, int chunkSize) {
        if (chunkSize <= 0) {
            throw new IllegalArgumentException("Chunk size must be greater than 0.");
        }

        List<List<T>> chunks = new ArrayList<>();
        for (int i = 0; i < list.size(); i += chunkSize) {
            int end = Math.min(list.size(), i + chunkSize);
            chunks.add(new ArrayList<>(list.subList(i, end)));
        }
        return chunks;
    }
}
