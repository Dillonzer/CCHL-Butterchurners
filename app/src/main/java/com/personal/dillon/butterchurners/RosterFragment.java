package com.personal.dillon.butterchurners;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

/**
 * Created by Dillon on 2016-03-02
 */
public class RosterFragment extends Fragment {
    ProgressDialog mProgressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_roster, container, false);

        return view;

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //CALL CLASS
        new JsoupRun().execute();
    }



    private class JsoupRun extends AsyncTask<Void, Void, Void> {
        String url = "http://stats.adultrechockey.ca/c-regina/ashnregina/en/stats/statdisplay.php?type=skaters&team_id=3642&season_id=76&league_id=6&division_id=-1&confId=0";
        Elements centerAlign;
        Elements leftAlign;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setTitle("Roster");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                Document document = Jsoup.connect(url).get();
                // Get all the tds
                centerAlign = document.getElementsByClass("textAlignCenter"); //skip 10 then start
                leftAlign = document.getElementsByClass("textAlignLeft"); //skip 4 then start - space - name - space space


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // CODE GOES HERE
            //Number;GP;G;A;PTS;PIMS;PPG;SHG;PT/G;PIM/G is center align
            //left is name

            TextView tv_number =(TextView) getView().findViewById(R.id.tv_number_roster);
            TextView tv_name = (TextView) getView().findViewById(R.id.tv_name_roster);
            TextView tv_gp = (TextView) getView().findViewById(R.id.tv_gp_roster);
            TextView tv_g = (TextView) getView().findViewById(R.id.tv_g_roster);
            TextView tv_a = (TextView) getView().findViewById(R.id.tv_a_roster);
            TextView tv_pts = (TextView) getView().findViewById(R.id.tv_pts_roster);
            TextView tv_pims = (TextView) getView().findViewById(R.id.tv_pims_roster);
            TextView tv_ppg = (TextView) getView().findViewById(R.id.tv_ppg_roster);
            TextView tv_shg = (TextView) getView().findViewById(R.id.tv_shg_roster);
            TextView tv_ptpg = (TextView) getView().findViewById(R.id.tv_ptpg_roster);
            TextView tv_pimg = (TextView) getView().findViewById(R.id.tv_pimpg_roster);

            String s_number="";
            String s_name="";
            String s_gp="";
            String s_g="";
            String s_a="";
            String s_pts="";
            String s_pims="";
            String s_ppg="";
            String s_shg="";
            String s_ptpg="";
            String s_pimg="";

            int ca_count = 0;
            int la_count = 0;

            for(Element ca : centerAlign)
            {
                if(ca_count < 10)
                {
                    ca_count++;
                } else
                {
                    switch(ca_count)
                    {
                        case 10:
                            s_number += ca.text() + "\n";
                            ca_count++;
                            break;
                        case 11:
                            s_gp += ca.text() + "\n";
                            ca_count++;
                            break;
                        case 12:
                            s_g += ca.text() + "\n";
                            ca_count++;
                            break;
                        case 13:
                            s_a += ca.text() + "\n";
                            ca_count++;
                            break;
                        case 14:
                            s_pts += ca.text() + "\n";
                            ca_count++;
                            break;
                        case 15:
                            s_pims += ca.text() + "\n";
                            ca_count++;
                            break;
                        case 16:
                            s_ppg += ca.text() + "\n";
                            ca_count++;
                            break;
                        case 17:
                            s_shg += ca.text() + "\n";
                            ca_count++;
                            break;
                        case 18:
                            s_ptpg += ca.text() + "\n";
                            ca_count++;
                            break;
                        case 19:
                            s_pimg += ca.text() + "\n";
                            ca_count=10;
                            break;
                        default:
                            ca_count = 10;
                            break;
                    }
                }
            }

            for (Element la : leftAlign)
            {
                if(la_count < 4)
                {
                    la_count++;
                } else
                {
                    switch(la_count)
                    {
                        case 4:
                            la_count++;
                            break;
                        case 5:
                            s_name  += la.text() + "\n";
                            la_count++;
                            break;
                        case 6:
                            la_count++;
                            break;
                        case 7:
                            la_count=4;
                            break;
                        default:
                            la_count=4;
                            break;
                    }
                }
            }

            tv_number.setText(s_number);
            tv_name.setText(s_name);
            tv_a.setText(s_a);
            tv_g.setText(s_g);
            tv_gp.setText(s_gp);
            tv_pts.setText(s_pts);
            tv_pims.setText(s_pims);
            tv_ppg.setText(s_ppg);
            tv_shg.setText(s_shg);
            tv_ptpg.setText(s_ptpg);
            tv_pimg.setText(s_pimg);
            mProgressDialog.dismiss();
        }
    }
}


