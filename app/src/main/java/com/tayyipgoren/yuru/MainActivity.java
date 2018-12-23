package com.tayyipgoren.yuru;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;

import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.DataPoint;

import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;

import com.google.android.gms.fitness.result.DataSourcesResult;
import com.tayyipgoren.yuru.yuru.Achivement;
import com.tayyipgoren.yuru.yuru.ClassStorageHandler;
import com.tayyipgoren.yuru.yuru.CustomPagerAdapter;
import com.tayyipgoren.yuru.yuru.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import devlight.io.library.ntb.NavigationTabBar;
import pl.pawelkleczkowski.customgauge.CustomGauge;

public class MainActivity extends AppCompatActivity implements
        OnDataPointListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener
{
    public static String LOG_TAG = "MAIN_ACTIVITY";

    //Google Fit API
    private static final int REQUEST_OAUTH = 1;
    private static final String AUTH_PENDING = "auth_state_pending";
    private boolean authInProgress = false;
    private GoogleApiClient mApiClient;

    //User
    public static User user;

    public CustomGauge gauge;
    public TextView gaugeTextView;
    public TextView cointTextView;

    public static Handler mHandler;

    public static Boolean isTimerRunning = false;

    private static Timer timer;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initUI();

        timer = new Timer();
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                updateStepTextView(MainActivity.this);
            }
        };

        if (savedInstanceState != null) {
            authInProgress = savedInstanceState.getBoolean(AUTH_PENDING);
        }

        //Connect to Google Fitness API
        mApiClient = new GoogleApiClient.Builder(this)
                .addApi(Fitness.SENSORS_API)
                .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mApiClient.connect();

        //Try User
        this.user = User.getFakeUser();
        this.user.addAchivement(new Achivement("Deneme 1 achivement"));
        this.user.addAchivement(new Achivement("Deneme 2 achivement"));
        this.user.addAchivement(new Achivement("Deneme 3 achivement"));
        this.user.addAchivement(new Achivement("Deneme 4 achivement"));
        this.user.addAchivement(new Achivement("Deneme 5 achivement"));
        this.user.addAchivement(new Achivement("Deneme 6 achivement"));

        startTimer();
    }

    private void updateStepTextView(MainActivity activity)
    {
        Long steps = MainActivity.user.getSteps();
        Long points = MainActivity.user.getPoints();
        activity.gauge.setValue(steps.intValue());
        activity.gaugeTextView.setText(steps.toString());
        activity.cointTextView.setText(points.toString());
        // this.textView.setText(Float.toString(this.user.getSteps()));
    }

    protected static void startTimer() {
        isTimerRunning = true;
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                mHandler.obtainMessage(1).sendToTarget();
            }
        }, 0, 1000);
    }


    @Override
    public void onConnected(Bundle bundle) {
        DataSourcesRequest dataSourceRequest = new DataSourcesRequest.Builder()
                .setDataTypes( DataType.TYPE_STEP_COUNT_CUMULATIVE )
                .setDataSourceTypes( DataSource.TYPE_RAW )
                .build();

        ResultCallback<DataSourcesResult> dataSourcesResultCallback = new ResultCallback<DataSourcesResult>() {
            @Override
            public void onResult(DataSourcesResult dataSourcesResult) {
                for( DataSource dataSource : dataSourcesResult.getDataSources() ) {
                    if( DataType.TYPE_STEP_COUNT_CUMULATIVE.equals( dataSource.getDataType() ) ) {
                        registerFitnessDataListener(dataSource, DataType.TYPE_STEP_COUNT_CUMULATIVE);
                    }
                }
            }
        };

        Fitness.SensorsApi.findDataSources(mApiClient, dataSourceRequest)
                .setResultCallback(dataSourcesResultCallback);
    }

    private void registerFitnessDataListener(DataSource dataSource, DataType dataType) {

        SensorRequest request = new SensorRequest.Builder()
                .setDataSource( dataSource )
                .setDataType( dataType )
                .setSamplingRate( 3, TimeUnit.SECONDS )
                .build();

        Fitness.SensorsApi.add( mApiClient, request, this )
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        if (status.isSuccess()) {
                            Log.e( "GoogleFit", "SensorApi successfully added" );
                        }
                    }
                });
    }

    @Override
    public void onDataPoint(DataPoint dataPoint) {
        for( final Field field : dataPoint.getDataType().getFields() ) {
            final Value value = dataPoint.getValue( field );
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Field: " + field.getName() + " Value: " + value, Toast.LENGTH_SHORT).show();
                    MainActivity.user.setSteps(value.asInt());
                }
            });
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if( !authInProgress ) {
            try {
                authInProgress = true;
                connectionResult.startResolutionForResult( MainActivity.this, REQUEST_OAUTH );
            } catch(IntentSender.SendIntentException e ) {

            }
        } else {
            Log.e( "GoogleFit", "authInProgress" );
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( requestCode == REQUEST_OAUTH ) {
            authInProgress = false;
            if( resultCode == RESULT_OK ) {
                if( !mApiClient.isConnecting() && !mApiClient.isConnected() ) {
                    mApiClient.connect();
                }
            } else if( resultCode == RESULT_CANCELED ) {
                Log.e( "GoogleFit", "RESULT_CANCELED" );
            }
        } else {
            Log.e("GoogleFit", "requestCode NOT request_oauth");
        }
    }


    private boolean initUI()
    {
        gauge = findViewById(R.id.gauge2);
        gaugeTextView = findViewById(R.id.gaugeTextView);
        cointTextView = findViewById(R.id.main_point_text_view);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.vp_horizontal_ntb);
        viewPager.setAdapter(new CustomPagerAdapter(this));

        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        String[] colors = getResources().getStringArray(R.array.polluted_waves);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.qr_code),
                        Color.parseColor(colors[0])
                ).title(getString(R.string.QR))
                        .badgeTitle("NTB")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.cup),
                        Color.parseColor(colors[1])
                ).title(getString(R.string.TOP_LIST))
                        .badgeTitle("with")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.home),
                        Color.parseColor(colors[2])
                ).title(getString(R.string.HOME))
                        .badgeTitle("state")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.coupon),
                        Color.parseColor(colors[3])
                ).title(getString(R.string.COUPONS))
                        .badgeTitle("Coupon")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.user),
                        Color.parseColor(colors[4])
                ).title(getString(R.string.PROFILE))
                        .badgeTitle("777")
                        .build()
        );
        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 2);
        navigationTabBar.setBehaviorEnabled(true);

        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(final NavigationTabBar.Model model, final int index) {
            }

            @Override
            public void onEndTabSelected(final NavigationTabBar.Model model, final int index) {
                model.hideBadge();
            }
        });
        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {

            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });

        final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.parent);
//        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View v) {
//                for (int i = 0; i < navigationTabBar.getModels().size(); i++) {
//                    final NavigationTabBar.Model model = navigationTabBar.getModels().get(i);
//                    navigationTabBar.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            final String title = String.valueOf(new Random().nextInt(15));
//                            if (!model.isBadgeShowed()) {
//                                model.setBadgeTitle(title);
//                                model.showBadge();
//                            } else model.updateBadgeTitle(title);
//                        }
//                    }, i * 100);
//                }
//
//                coordinatorLayout.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        final Snackbar snackbar = Snackbar.make(navigationTabBar, "Coordinator NTB", Snackbar.LENGTH_SHORT);
//                        snackbar.getView().setBackgroundColor(Color.parseColor("#9b92b3"));
//                        ((TextView) snackbar.getView().findViewById(R.id.snackbar_text))
//                                .setTextColor(Color.parseColor("#423752"));
//                        snackbar.show();
//                    }
//                }, 1000);
//            }
//        });

        final CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.toolbar);
        collapsingToolbarLayout.setExpandedTitleColor(Color.parseColor("#009F90AF"));


//        navigationTabBar.setTitleMode(NavigationTabBar.TitleMode.ACTIVE);
//        navigationTabBar.setBadgeGravity(NavigationTabBar.BadgeGravity.BOTTOM);
//        navigationTabBar.setBadgePosition(NavigationTabBar.BadgePosition.CENTER);
        navigationTabBar.setTypeface("fonts/custom_font.ttf");
        navigationTabBar.setIsBadged(true);
        navigationTabBar.setIsTitled(true);
//        navigationTabBar.setIsTinted(true);
//        navigationTabBar.setIsBadgeUseTypeface(true);
//        navigationTabBar.setBadgeBgColor(Color.RED);
//        navigationTabBar.setBadgeTitleColor(Color.BLUE);
        navigationTabBar.setIsSwiped(true);
//        navigationTabBar.setBadgeSize(10);
//        navigationTabBar.setTitleSize(10);
          navigationTabBar.setIsTinted(true);
//        navigationTabBar.setIconSizeFraction((float)(0.2));

        return true;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle(R.string.exit)
                .setMessage(R.string.exit_confirm)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton(R.string.no, null).show();
    }

    public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
            final View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.item_list, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.txt.setText(String.format("Navigation Item #%d", position));
        }

        @Override
        public int getItemCount() {
            return 20;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView txt;

            public ViewHolder(final View itemView) {
                super(itemView);
                txt = (TextView) itemView.findViewById(R.id.txt_vp_item_list);
            }
        }
    }
}
