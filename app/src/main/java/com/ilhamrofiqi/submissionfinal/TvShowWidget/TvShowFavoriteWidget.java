package com.ilhamrofiqi.submissionfinal.TvShowWidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.ilhamrofiqi.submissionfinal.R;

/**
 * Implementation of App Widget functionality.
 */
public class TvShowFavoriteWidget extends AppWidgetProvider {

    private static final String TOAST_ACTION_TV_SHOW = "com.ilhamrofiqi.submissionfinal.TvShowWidget.TOAST_ACTION_TV_SHOW";
    public static final String EXTRA_ITEM_TV_SHOW = "com.ilhamrofiqi.submissionfinal.TvShowWidget.EXTRA_ITEM_TV_SHOW";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context, TvShowStackWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.tv_show_favorite_widget);
        views.setRemoteAdapter(R.id.stack_view_tv_show, intent);
        views.setEmptyView(R.id.stack_view_tv_show, R.id.empty_view_tv_show);

        Intent toastIntent = new Intent(context, TvShowFavoriteWidget.class);
        toastIntent.setAction(TvShowFavoriteWidget.TOAST_ACTION_TV_SHOW);
        toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.stack_view_tv_show, toastPendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction() != null) {
            if (intent.getAction().equals(TOAST_ACTION_TV_SHOW)) {
                int viewIndex = intent.getIntExtra(EXTRA_ITEM_TV_SHOW, 0);
                Toast.makeText(context, "Touched view " + viewIndex, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}