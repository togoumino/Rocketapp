package com.example.sanji.aninterface.pdfContent;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sanji.aninterface.R;

import java.io.File;
import java.io.IOException;

public class PdfListAdapter extends RecyclerView.Adapter<PdfListAdapter.MyViewHolder> {
    private String[] mDataset;


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public Button mBtn;
        private final Context context;
        private final String downloadURL;

        public MyViewHolder(Button b) {
            super(b);
            context = b.getContext();
            downloadURL = "http://" + context.getString(R.string.host) + ":" + context.getString(R.string.rest_port) + "/config/miscFiles/";
            mBtn = b;
        }
    }

    public PdfListAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    @Override
    public PdfListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        Button v = (Button) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.read_pdf_btn, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final String fname = mDataset[position];
        holder.mBtn.setText(fname);

        holder.mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download(holder.downloadURL, fname);
                view(holder.context, fname);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }


    public void download(String url, String fname) {
        new DownloadFile().execute(url + fname.split("\\.")[0], fname);
    }

    public void view(Context context, String fname) {
        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/" + fname);
        Uri path = Uri.fromFile(pdfFile);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            context.startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    private class DownloadFile extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory);
            folder.mkdirs();

            File pdfFile = new File(folder, fileName);

            try {
                pdfFile.createNewFile();
            } catch (IOException e) {
                System.err.println("Can't create file");
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
            FileDownloader.downloadFile(fileUrl, pdfFile);
            return null;
        }
    }
}