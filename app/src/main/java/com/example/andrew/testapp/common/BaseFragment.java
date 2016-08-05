package com.example.andrew.testapp.common;


import android.support.v4.app.Fragment;

import com.example.andrew.testapp.di.IHasComponent;

/**
 * Created by Andrew on 06.07.2016.
 */
public abstract class BaseFragment extends Fragment {

    @SuppressWarnings("unchecked")
    protected <T> T getComponent(Class<T> componentType) {
        return componentType.cast(((IHasComponent<T>)getActivity()).getComponent());
    }

}
