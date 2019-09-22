package com.ilhamrofiqi.submissionfinal.TvShowWidget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class TvShowStackWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new TvShowStackRemoteViewsFactory(this.getApplicationContext());
    }
}
