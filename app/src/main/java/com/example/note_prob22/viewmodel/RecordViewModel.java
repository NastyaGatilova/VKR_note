package com.example.note_prob22.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.note_prob22.classes.Record;

import java.util.ArrayList;
import java.util.List;

public class RecordViewModel extends AndroidViewModel {
    /// MutableLiveData<ArrayList<Record>> listRecord = new MutableLiveData<>();


    public RecordViewModel(@NonNull Application application) {
        super(application);
    }
//    public LiveData<ArrayList<Record>> getListUpdateCity() {
//        return listRecord;
//    }
//
//    public void addRecord(Record rec) {
//        ArrayList<Record> currentList = listRecord.getValue();
//        if (currentList == null) {
//            currentList = new ArrayList<>();
//        }
//        currentList.add(rec);
//
//        listRecord.setValue(currentList);
//    }
//
//    public void updateRecord(Record rec) {
//        ArrayList<Record> currentList = listRecord.getValue();
//        if (currentList != null) {
//            int index = currentList.indexOf(rec);
//            if (index != -1) {
//                currentList.set(index, rec);
//                listRecord.setValue(currentList);
//            }
//        }
//    }
//
//    public void deleteWeather(Record rec) {
//        ArrayList<Record> currentList = listRecord.getValue();
//        if (currentList != null) {
//            currentList.remove(rec);
//            listRecord.setValue(currentList);
//        }
//    }


    private MutableLiveData<List<Record>> myDataList;

    public MutableLiveData<List<Record>> getMyDataList() {
        if (myDataList == null) {
            myDataList = new MutableLiveData<List<Record>>();
        }
        return myDataList;
    }

    public void setMyDataList(List<Record> myDataList) {
        this.myDataList.setValue(myDataList);
    }
}
