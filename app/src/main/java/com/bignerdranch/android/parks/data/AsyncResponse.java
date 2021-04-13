package com.bignerdranch.android.parks.data;

import com.bignerdranch.android.parks.model.Park;

import java.util.List;

public interface AsyncResponse {
    void processPark(List<Park> parks);

}
