package com.inventics.ekalarogya.training.main;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.inventics.ekalarogya.training.BuildConfig;;
import com.inventics.ekalarogya.training.R;
import com.inventics.ekalarogya.training.adapters.spinner.VillageListArrayAdapter;
import com.inventics.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.inventics.ekalarogya.training.dialogs.MonthYearPickerDialog;
import com.inventics.ekalarogya.training.dialogs.ProgressDialogFragment;
import com.inventics.ekalarogya.training.models.VillageListModel;
import com.inventics.ekalarogya.training.rest.RestUtils;
import com.inventics.ekalarogya.training.rest.response.BaseResponse;
import com.inventics.ekalarogya.training.rest.service.UserService;
import com.inventics.ekalarogya.training.utils.ToastUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class GKVReport  extends AppCompatActivity  implements RetroAPICallback {

    private static final String TAG = "SanchMWRDetailActivity";
    private static final int PDF_REPORT =1 ;
    @BindView(R.id.pdfView)
    PDFView pdfView;
    @BindView(R.id.tvDate)
    TextView tvDate;
    private static final int GET_VILLAGE_LIST=115;
    @BindView(R.id.tvSearch)
    TextView tvSearch;
//    @BindView(R.id.rvDailyAttendanceReport)
//    RecyclerView recyclerView;
//    @BindView(R.id.btnSubmit)
//    Button btnSubmit;
    String selectYear, selectMonth;
    @BindView(R.id.fbDownload)
    FloatingActionButton fbDownload;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    VillageListArrayAdapter adapter;
    private List<VillageListModel> villName = new ArrayList<>();
    @BindView(R.id.vllSpinner)
    Spinner vllSpinner;

//    @BindView(R.id.toolbar)
//    Toolbar toolbar;
    String pdflink,vid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gkvreport);
        ButterKnife.bind(this);
//        setUpToolbar();
        getVillageList();

        progress_bar.setVisibility(View.GONE);
        vllSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                VillageListModel clickedItem = (VillageListModel)
                        parent.getItemAtPosition(position);
                String name = clickedItem.getVillage_name();
                fbDownload.setVisibility(View.GONE);
                vid= clickedItem.getVillage_id();
                if(vid==null){
                    ToastUtils.shortToast(getResources().getString(R.string.select_village));
                } else if(selectYear==null){
                    ToastUtils.shortToast(getResources().getString(R.string.select));
                }else{
                    pdfList();
                }
//                Toast.makeText(AddDiseaseVillage.this, name + clickedItem.getVillage_id()+" selected", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        fbDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadInvoice(pdflink,"GKV Report");

            }
        });
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDateDialog();
                fbDownload.setVisibility(View.GONE);
            }
        });
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(vid==null){
                    ToastUtils.shortToast(getResources().getString(R.string.select_village));
                } else if(selectYear==null){
                    ToastUtils.shortToast(getResources().getString(R.string.select));
                }else{
                    pdfList();
                }

                fbDownload.setVisibility(View.GONE);
            }

        });

    }

    private void pdfList() {
        progress_bar.setVisibility(View.VISIBLE);
        UserService.getGkvReport(this,vid,selectMonth,selectYear,this,PDF_REPORT);
    }

    private void setUpToolbar() {
//        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setTitle("Sanch MWR Report");
//            toolbar.setTitle("Sanch MWR Report");
//        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void getVillageList() {
        UserService.getVillageList(this,this,GET_VILLAGE_LIST);
    }


    @Override
    public void onResponse(Call<?> call, Response<?> response, int requestCode, Object request) {
        switch (requestCode) {
            case PDF_REPORT:
                Log.d("TAG", "onResponse: "+response.isSuccessful());
                if(response.isSuccessful())
                {
                    BaseResponse sanchPdfResposnse=(BaseResponse) response.body();
                    if (sanchPdfResposnse.getStatus().equalsIgnoreCase("success")) {
//                        progress_bar.setVisibility(View.VISIBLE);
                        Log.d(TAG, "onResponse if: "+sanchPdfResposnse.getStatusMessage()+"\n"+sanchPdfResposnse.getStatus());
                        progress_bar.setVisibility(View.VISIBLE);
                        pdflink=BuildConfig.PROD_END_POINT+sanchPdfResposnse.getGkv_file_path();
                        Log.d("TAG", "pdflink: "+pdflink);
//                        fbDownload.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Intent i = new Intent(Intent.ACTION_VIEW);
//                                i.setData(Uri.parse(pdflink));
//                                startActivity(i);
//                            }
//                        });

//                        pdfView.fromUri(Uri.parse(pdflink));
                        new RetrivePDFfromUrl().execute(pdflink);
                        pdfView.setVisibility(View.VISIBLE);
                        progress_bar.setVisibility(View.GONE);

                    }else{
                        progress_bar.setVisibility(View.GONE);
                        Toast.makeText(this, sanchPdfResposnse.getStatus(), Toast.LENGTH_SHORT).show();
//                        Log.d(TAG, "onResponse else: "+"\n"+sanchPdfResposnse.getStatus());

                    }
//
                }
                break;
            case GET_VILLAGE_LIST:
                if (response.isSuccessful()){
                    BaseResponse baseResponse=(BaseResponse) response.body();
                    if (baseResponse!=null) {
                        if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            Log.d("TAG", "onResponse: VIEW_HEAD_FAMILY" + "RestUtils.SUCCESS getmodule");
                            if(baseResponse.getVillageListModelList().size()>0){
                                // we pass our item list and context to our Adapter.
                                Log.d("TAG", "onResponse: size "+baseResponse.getVillageListModelList().size());
                                vllSpinner.setAdapter(adapter);
//                                List<VillageListModel> villageListModelList = new ArrayList<>();
                                try {
                                    for(int i=0; i <=baseResponse.getVillageListModelList().size(); i++){
                                        villName.add(baseResponse.getVillageListModelList().get(i));
                                        Log.d("TAG", "onResponse: size "+baseResponse.getVillageListModelList().get(i).getVillage_name());
                                        adapter = new VillageListArrayAdapter(this, villName);
                                        adapter.notifyDataSetChanged();
                                    }
                                }catch (Exception e){
                                    Log.d("TAG", "onResponse: exception"+e.getMessage());
                                }
                                vllSpinner.setAdapter(adapter);

                            }

                        }else {
                            ToastUtils.shortToast(baseResponse.getStatus() +"\n"+baseResponse.getStatusMessage());
                        }
                    }
                }
                break;
        }

    }


    @Override
    public void onFailure(Call<?> call, Throwable t, int requestCode, Object request) {
        Log.d("TAG", "onFailure: RequestCode "+requestCode+ "  Error : "+t.getMessage());
        ToastUtils.shortToast(t.getMessage());


    }

    @Override
    public void onNoNetwork(int requestCode) {

    }

    private void getDateDialog() {
        MonthYearPickerDialog dialog = new MonthYearPickerDialog();
        dialog.setListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                selectYear = year + "";
                selectMonth = (month) + "";
                tvDate.setText(selectYear + "-" + selectMonth + "-01");

                if(vid==null){
                    ToastUtils.shortToast(getResources().getString(R.string.select_village));
                } else if(selectYear==null){
                    ToastUtils.shortToast(getResources().getString(R.string.select));
                }else{
                    pdfList();
                }
            }
        });
        dialog.show(getSupportFragmentManager(), "MonthYearPickerDialog");
    }
    class RetrivePDFfromUrl extends AsyncTask<String, Void, InputStream> {
        @Override
        protected InputStream doInBackground(String... strings) {
            // we are using inputstream
            // for getting out PDF.
            Log.d(TAG, "doInBackground: pdflink "+strings[0]);
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                // below is the step where we are
                // creating our connection.
                HttpURLConnection urlConnection =(HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    // response is success.
                    // we are getting input stream from url
                    // and storing it in our variable.
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    Log.d(TAG, "doInBackground: "+inputStream);
                }

            } catch (IOException e) {
                // to handle errors.
                e.printStackTrace();
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            // after the execution of our async
            // task we are loading our pdf in our pdf view.
            Log.d(TAG, "onPostExecute: "+inputStream);
            pdfView.fromStream(inputStream).load();
            fbDownload.setVisibility(View.VISIBLE);
            progress_bar.setVisibility(View.GONE);
        }
    }

    private void downloadInvoice(String link, String fileName) {
        UserService.downloadGkvReport(this, link, new RetroAPICallback() {
            @Override
            public void onResponse(Call<?> call, Response<?> response, int requestCode, Object request) {
                if (response.isSuccessful()) {
                    boolean writtenToDisk = writeResponseBodyToDisk((ResponseBody) response.body(), fileName);

                    Log.d("ORDER ADAPTER", "file download was a success? " + writtenToDisk);
                } else {
                    ToastUtils.longToast(getResources().getString(R.string.try_again));
                }
                ProgressDialogFragment.dismissProgress(getSupportFragmentManager());
            }

            @Override
            public void onFailure(Call<?> call, Throwable t, int requestCode, Object request) {
                Log.d("ORDER", "Failed to get response: " + t.getLocalizedMessage());
                ToastUtils.longToast(getResources().getString(R.string.try_again));
            }

            @Override
            public void onNoNetwork(int requestCode) {

            }
        }, 2);
    }

    private boolean writeResponseBodyToDisk(ResponseBody body, String fileName) {
        try {
            // todo change the file location/name according to your needs
//            File futureStudioIconFile = new File(context.getExternalFilesDir(null) + File.separator + fileName);
            File futureStudioIconFile = new File((Environment.getExternalStorageDirectory().getPath()+File.separator+Environment.DIRECTORY_DOWNLOADS)+ File.separator + fileName);
            Log.d("TAG", "writeResponseBodyToDisk: path"+futureStudioIconFile);
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d("ORDER ADAPTER", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
//                Intent target = new Intent(Intent.ACTION_VIEW);
//                target.setDataAndType(Uri.fromFile(futureStudioIconFile), "application/pdf");
//                Intent intent = Intent.createChooser(target, "Open File");

                Log.d("TAG", "writeResponseBodyToDisk: "+String.valueOf(futureStudioIconFile));
//                PdfView.startPdfActivity(context,String.valueOf(futureStudioIconFile));
                ToastUtils.shortToast(fileName+getResources().getString(R.string.download));

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

}